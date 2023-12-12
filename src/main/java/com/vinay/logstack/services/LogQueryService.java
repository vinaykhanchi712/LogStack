package com.vinay.logstack.services;

import com.vinay.logstack.data.request.LogRequest;
import com.vinay.logstack.data.request.QueryRequest;
import com.vinay.logstack.data.response.QueryResponse;

public interface LogQueryService {

    public QueryResponse getLogQueryResult(QueryRequest request,Integer page , Integer size) throws Exception;
}
