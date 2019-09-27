package com.learn.ad.controller;

import com.alibaba.fastjson.JSON;
import com.learn.ad.service.ICreativeService;
import com.learn.ad.vo.CreativeRequest;
import com.learn.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreativeOPController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @RequestMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request){
        log.info("ad-sponsor: createCreative-> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
