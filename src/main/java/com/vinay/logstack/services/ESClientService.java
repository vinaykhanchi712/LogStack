package com.vinay.logstack.services;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

public interface ESClientService {

    public SearchResponse getSearchResponse(SearchRequest searchRequest)throws Exception;
}
