package com.vinay.logstack.services.Impl;

import com.vinay.logstack.data.enums.KafkaTopic;
import com.vinay.logstack.data.request.LogRequest;
import com.vinay.logstack.services.LogIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogIngestionServiceImpl implements LogIngestionService {



    @Autowired
    private KafkaService kafkaService;

    @Override
    public String processIngestedLogs(LogRequest request) throws Exception {

        kafkaService.pushDataToKafka(request, KafkaTopic.ELASTIC_SEARCH_LOG_INGESTION.getTopic());
        return request.getTraceId();
    }
}
