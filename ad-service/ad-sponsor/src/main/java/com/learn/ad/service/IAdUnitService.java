package com.learn.ad.service;

import com.learn.ad.enitiy.unit_condition.CreativeUnit;
import com.learn.ad.exception.AdException;
import com.learn.ad.vo.*;

public interface IAdUnitService {
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws  AdException;
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws  AdException;
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws  AdException;

}
