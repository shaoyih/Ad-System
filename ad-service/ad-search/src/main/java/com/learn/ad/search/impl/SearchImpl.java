package com.learn.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.learn.ad.index.CommonStatus;
import com.learn.ad.index.DataTable;
import com.learn.ad.index.adunit.AdUnitIndex;
import com.learn.ad.index.adunit.AdUnitObject;
import com.learn.ad.index.creative.CreativeIndex;
import com.learn.ad.index.creative.CreativeObject;
import com.learn.ad.index.creativeunit.CreativeUnitIndex;
import com.learn.ad.index.district.UnitDistrictIndex;
import com.learn.ad.index.interest.UnitInterestIndex;
import com.learn.ad.index.keword.UnitKeywordIndex;
import com.learn.ad.search.ISearch;
import com.learn.ad.search.vo.SearchRequest;
import com.learn.ad.search.vo.SearchResponse;
import com.learn.ad.search.vo.feature.DistrictFeature;
import com.learn.ad.search.vo.feature.FeatureRelation;
import com.learn.ad.search.vo.feature.ItFeature;
import com.learn.ad.search.vo.feature.KeywordFeature;
import com.learn.ad.search.vo.media.AdSlot;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SearchImpl implements ISearch {

    public SearchResponse fallback(SearchRequest request, Throwable e){
        return null;
    }
    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public SearchResponse fetchAds(SearchRequest request) {
        //广告位信息取出
        List<AdSlot> adSlots=request.getRequestInfo().getAdSlots();

        //三个 Feature
        KeywordFeature keywordFeature= request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature=request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature=request.getFeatureInfo().getItFeature();
        FeatureRelation relation=request.getFeatureInfo().getRelation();

        //构造响应对象
        SearchResponse response= new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads= response.getAdSlot2Ads();
        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;

            //根据流量类型 初始化 AdUnit
            Set<Long> adUnitIdSet= DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
            if(relation== FeatureRelation.AND){
                filterDistrictFeature(adUnitIdSet,districtFeature);
                filterItFeature(adUnitIdSet,itFeature);
                filterKeywordFeature(adUnitIdSet,keywordFeature);
                targetUnitIdSet=adUnitIdSet;
            }
            else{
                targetUnitIdSet= getOrRelationUnitIds(adUnitIdSet,keywordFeature,districtFeature,itFeature);
            }
            List<AdUnitObject> unitObjects=DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            filterAdUnitAndPlanStatus(unitObjects,CommonStatus.VALID);
            List<Long> adIds= DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creatives= DataTable.of(CreativeIndex.class).fetch(adIds);

            //通过AdSlot 实现 creativeObject 过滤
            filterCreativeByAdSlot(creatives,adSlot.getWidth(),adSlot.getHeight(),adSlot.getType());
            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creatives));
        }
        log.info("fetchAds: {}-{}", JSON.toJSONString(request),JSON.toJSONString(response));
        return response;

    }

    private Set<Long> getOrRelationUnitIds(Set<Long> adUnit, KeywordFeature keywordFeature, DistrictFeature districtFeature, ItFeature itFeature){
        if(CollectionUtils.isEmpty(adUnit))
            return Collections.emptySet();
        Set<Long> keywordUnitIdSet= new HashSet<>(adUnit);
        Set<Long> districtUnitIdSet= new HashSet<>(adUnit);
        Set<Long> itUnitIdSet= new HashSet<>(adUnit);

        filterDistrictFeature(districtUnitIdSet,districtFeature);
        filterItFeature(itUnitIdSet,itFeature);
        filterKeywordFeature(keywordUnitIdSet,keywordFeature);
        return new HashSet<>(CollectionUtils.union(keywordUnitIdSet,CollectionUtils.union(districtUnitIdSet,itUnitIdSet)));
    }

    private  void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature){
        if(CollectionUtils.isEmpty(adUnitIds))
            return;
        if(CollectionUtils.isNotEmpty(keywordFeature.getKeywords())){
            CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(UnitKeywordIndex.class).match(adUnitId,keywordFeature.getKeywords()));
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature){
        if(CollectionUtils.isEmpty(adUnitIds))
            return;
        if(CollectionUtils.isNotEmpty(districtFeature.getDistricts())){
            CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(UnitDistrictIndex.class).match(adUnitId,districtFeature.getDistricts()));
        }
    }

    private void filterItFeature(Collection<Long> adUnitIds, ItFeature itFeature){
        if(CollectionUtils.isEmpty(adUnitIds))
            return;
        if(CollectionUtils.isNotEmpty(itFeature.getIts())){
            CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(UnitInterestIndex.class).match(adUnitId,itFeature.getIts()));
        }
    }
    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status){
        if(CollectionUtils.isEmpty(unitObjects))
            return;
        CollectionUtils.filter(unitObjects, object-> object.getUnitStatus().equals(status.getStatus()) && object.getAdPlanObject().getPlanStatus().equals(status.getStatus()));
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creativeObjects, Integer width,Integer height,List<Integer> type){
        if(CollectionUtils.isEmpty(creativeObjects))
            return;
        CollectionUtils.filter(creativeObjects,creative-> creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())&&
                creative.getWidth().equals(width) && creative.getHeight().equals(height) &&type.contains(creative.getType()));
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creativeObjects){
        if(CollectionUtils.isEmpty(creativeObjects))
            return Collections.emptyList();
        CreativeObject randomObject= creativeObjects.get(Math.abs(new Random().nextInt())%creativeObjects.size());
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
