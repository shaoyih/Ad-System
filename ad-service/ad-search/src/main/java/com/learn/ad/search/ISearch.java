package com.learn.ad.search;

import com.learn.ad.search.vo.SearchRequest;
import com.learn.ad.search.vo.SearchResponse;

public interface ISearch {
    SearchResponse fetchAds(SearchRequest request);
}
