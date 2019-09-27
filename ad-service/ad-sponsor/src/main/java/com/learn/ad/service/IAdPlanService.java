package com.learn.ad.service;

import com.learn.ad.enitiy.AdPlan;
import com.learn.ad.exception.AdException;
import com.learn.ad.vo.AdPlanGetRequest;
import com.learn.ad.vo.AdPlanRequest;
import com.learn.ad.vo.AdPlanResponse;

import java.util.List;

public interface IAdPlanService {
    AdPlanResponse createPlan(AdPlanRequest request)throws AdException;
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws  AdException;
    AdPlanResponse updateAdPlanRequest(AdPlanRequest request) throws AdException;
    void deleteAdPlan(AdPlanRequest request)throws  AdException;
}
