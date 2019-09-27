package com.learn.ad.index.interest;

import com.learn.ad.index.IndexAware;
import com.learn.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class UnitInterestIndex implements IndexAware<String,Set<Long>> {
    private static Map<String,Set<Long>> itUnitMap;
    private static Map<Long,Set<String>> unitItMap;

    static {
        itUnitMap= new ConcurrentHashMap<>();
        unitItMap= new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("itUnitMap, before add {}", itUnitMap);
        Set<Long> unitIds= CommonUtils.getOrCreate(key,itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for(Long unitId: value){
            Set<String> keywordSet= CommonUtils.getOrCreate(unitId,unitItMap, ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }
        log.info("itUnitMap, after add {}", itUnitMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("unitItMap, before delete {}", unitItMap);
        Set<Long> unitIds=CommonUtils.getOrCreate(key, itUnitMap,ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for(Long unitId: value){
            Set<String> itSet= CommonUtils.getOrCreate(unitId,unitItMap, ConcurrentSkipListSet::new);
            itSet.remove(key);
        }
        log.info("unitItMap, after delete {}", unitItMap);
    }

    public boolean match(Long unitId, List<String> keywords){
        if(unitItMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItMap.get(unitId))){
            Set<String> unitKeywords= unitItMap.get(unitId);
            return CollectionUtils.isSubCollection(keywords,unitKeywords);
        }
        return false;
    }

}
