package com.learn.ad.search.vo;

import com.learn.ad.search.vo.feature.DistrictFeature;
import com.learn.ad.search.vo.feature.FeatureRelation;
import com.learn.ad.search.vo.feature.ItFeature;
import com.learn.ad.search.vo.feature.KeywordFeature;
import com.learn.ad.search.vo.media.AdSlot;
import com.learn.ad.search.vo.media.App;
import com.learn.ad.search.vo.media.Device;
import com.learn.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String mediaId;
    private RequestInfo requestInfo;
    private FeatureInfo featureInfo;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo{
        private String requestId;
        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo{
        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;
        private FeatureRelation relation= FeatureRelation.AND;
    }


}
