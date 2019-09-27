package com.learn.ad.client;

import com.learn.ad.client.vo.AdPlan;
import com.learn.ad.client.vo.AdPlanGetRequest;
import com.learn.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SponsorClientHystrix implements  SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1,"Eureka-client-ad-sponsor error");
    }
}
