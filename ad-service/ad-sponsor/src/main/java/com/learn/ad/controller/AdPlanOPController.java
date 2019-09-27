package com.learn.ad.controller;

import com.alibaba.fastjson.JSON;
import com.learn.ad.enitiy.AdPlan;
import com.learn.ad.exception.AdException;
import com.learn.ad.service.IAdPlanService;
import com.learn.ad.vo.AdPlanGetRequest;
import com.learn.ad.vo.AdPlanRequest;
import com.learn.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class AdPlanOPController {

    private final IAdPlanService adPlanService;

    public AdPlanOPController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }


    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor createAdPlan-> {}", JSON.toJSONString(request));
        return adPlanService.createPlan(request);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException{
        log.info("ad-sponsor getPlanByIds-> {}", JSON.toJSONString(request));
        return adPlanService.getAdPlanByIds(request);

    }

    @PostMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor updateAdPlan-> {}", JSON.toJSONString(request));
        return adPlanService.updateAdPlanRequest(request);
    }

    @PostMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor deleteAdPlan-> {}", JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }
}
