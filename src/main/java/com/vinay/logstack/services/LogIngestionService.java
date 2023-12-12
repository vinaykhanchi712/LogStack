package com.vinay.logstack.services;

import com.vinay.logstack.data.request.LogRequest;


public interface LogIngestionService {

    public String processIngestedLogs(LogRequest request) throws Exception;
}
