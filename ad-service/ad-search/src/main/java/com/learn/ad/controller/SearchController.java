package com.learn.ad.controller;

import com.alibaba.fastjson.JSON;
import com.learn.ad.annotation.IgnoreResponseAdvice;
import com.learn.ad.client.SponsorClient;
import com.learn.ad.client.vo.AdPlan;
import com.learn.ad.client.vo.AdPlanGetRequest;
import com.learn.ad.search.ISearch;
import com.learn.ad.search.vo.SearchRequest;
import com.learn.ad.search.vo.SearchResponse;
import com.learn.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class SearchController {

    private ISearch search;
    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    public SearchController(ISearch search, RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.search = search;
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request){
        log.info("ad-search: fetchAds -> {}", JSON.toJSONString(request));
        return search.fetchAds(request);
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>>getAdPlan(@RequestBody AdPlanGetRequest request){
        log.info("ad-search: getAdPlansBy ->{}", JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);

    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlanByRibbon(@RequestBody AdPlanGetRequest request){
        log.info("ad-search: getAdPlansByRibbon ->{}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",request,CommonResponse.class).getBody();

    }
}
