package com.learn.ad.handler;

import com.alibaba.fastjson.JSON;
import com.learn.ad.dump.table.*;
import com.learn.ad.index.DataTable;
import com.learn.ad.index.IndexAware;
import com.learn.ad.index.adplan.AdPlanIndex;
import com.learn.ad.index.adplan.AdPlanObject;
import com.learn.ad.index.adunit.AdUnitIndex;
import com.learn.ad.index.adunit.AdUnitObject;
import com.learn.ad.index.creative.CreativeIndex;
import com.learn.ad.index.creative.CreativeObject;
import com.learn.ad.index.creativeunit.CreativeUnitIndex;
import com.learn.ad.index.creativeunit.CreativeUnitObject;
import com.learn.ad.index.district.UnitDistrictIndex;
import com.learn.ad.index.interest.UnitInterestIndex;
import com.learn.ad.index.keword.UnitKeywordIndex;
import com.learn.ad.constant.OpType;
import com.learn.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Slf4j
public class AdLevelDataHandler {

    public  static  void handleLevel2(AdPlanTable planTable, OpType type){
        AdPlanObject planObject= new AdPlanObject(planTable.getId(),planTable.getUserId(),planTable.getPlanStatus(),planTable.getStartDate(),planTable.getEndDate());
        handleBinLogEvent(DataTable.of(AdPlanIndex.class),planObject.getPlanId(),planObject,type);
    }

    public  static  void handleLevel2(AdCreativeTable adCreativeTable, OpType type){
        CreativeObject creativeObject= new CreativeObject(adCreativeTable.getAdId(),adCreativeTable.getName(),
                adCreativeTable.getType(),adCreativeTable.getMaterialType(),adCreativeTable.getHeight(),
                adCreativeTable.getWidth(),adCreativeTable.getAuditStatus(),adCreativeTable.getAdUrl());
        handleBinLogEvent(DataTable.of(CreativeIndex.class),creativeObject.getAdId(),creativeObject,type);
    }

    public static void handleLevel3(AdUnitTable unitTable,OpType type){
        AdPlanObject adPlanObject=DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if(adPlanObject==null){
            log.error("handlerLevel3, found ADpLanObject error: {}", unitTable.getPlanId());
            return;
        }
        AdUnitObject adUnitObject = new AdUnitObject(unitTable.getUnitId(),unitTable.getUnitStatus(),unitTable.getPositionType(),unitTable.getPlanId(),adPlanObject);
        handleBinLogEvent(DataTable.of(AdUnitIndex.class),unitTable.getUnitId(),adUnitObject,type);
    }
    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable,OpType type){
        if (type==OpType.UPDATE){
            log.error("CreativeUnitIndex not support update");
            return;
        }
        AdUnitObject unitObject= DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject=DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());
        if(unitObject==null || creativeObject==null){
            log.error("creativeUnitTable index error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }
        CreativeUnitObject creativeUnitObject= new CreativeUnitObject(creativeUnitTable.getAdId(),creativeUnitTable.getUnitId());
        handleBinLogEvent(DataTable.of(CreativeUnitIndex.class), CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(),creativeUnitObject.getUnitId().toString()),creativeUnitObject,type);

    }

    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType type){
        if(type== OpType.UPDATE){
            log.error("district index can not support update");
            return;
        }
        AdUnitObject unitObject= DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if(unitObject == null){
            log.error("AdUnitDistrictTable index error: {}",unitDistrictTable.getUnitId());
        }
        String key=CommonUtils.stringConcat(unitDistrictTable.getProvince(),unitDistrictTable.getCity());
        Set<Long> value= new HashSet<>(Collections.singleton(unitDistrictTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitDistrictIndex.class),key,value,type);

    }
    public static void handleLevel4(AdUnitItTable unitItTable, OpType type){
        if (type==OpType.UPDATE){
            log.error("it Index can't support update");
        }
        AdUnitObject unitObject=DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if(unitObject == null){
            log.error("AdUnitDistrictTable index error: {}",unitItTable.getUnitId());
        }
        Set<Long> value= new HashSet<>(Collections.singleton(unitItTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitInterestIndex.class),unitItTable.getItTag(),value,type);

    }
    public static void handleLevel4(AdUnitKeywordTable unitKeywordTable, OpType type){
        if (type==OpType.UPDATE){
            log.error("keyword index can't support update");
        }
        AdUnitObject unitObject=DataTable.of(AdUnitIndex.class).get(unitKeywordTable.getUnitId());
        if(unitObject == null){
            log.error("AdUnit key word table index error: {}",unitKeywordTable.getUnitId());
        }
        Set<Long> value= new HashSet<>(Collections.singleton(unitKeywordTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitKeywordIndex.class),unitKeywordTable.getKeyword(),value,type);

    }

    private static <K,V> void handleBinLogEvent(IndexAware<K,V> index, K key, V value, OpType type){


        switch(type){
            case ADD:
                index.add(key,value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
