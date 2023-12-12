package com.vinay.logstack.services;

import com.vinay.logstack.data.request.LogRequest;
import com.vinay.logstack.data.request.QueryRequest;
import org.elasticsearch.action.search.SearchRequest;

public interface ESQueryBuilderService {

    public SearchRequest getESSearchQuery(QueryRequest request,Integer from , Integer size);
}
