package com.learn.ad.service;

import com.learn.ad.vo.CreativeRequest;
import com.learn.ad.vo.CreativeResponse;

public interface ICreativeService {
    CreativeResponse createCreative(CreativeRequest request);
}
