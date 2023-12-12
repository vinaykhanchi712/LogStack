package com.vinay.logstack.controllers;

import com.vinay.logstack.data.request.LogRequest;
import com.vinay.logstack.services.LogIngestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingest")
public class IngestionController {

    @Autowired
    private LogIngestionService logIngestionService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/log"  , method= RequestMethod.POST)
    public ResponseEntity<?> ingestLogs(@RequestBody LogRequest logRequest) throws Exception{
        long startTime = System.currentTimeMillis();
        LOG.info("[IngestionController] Request received to ingest data: {}", logRequest);

        String response =logIngestionService.processIngestedLogs(logRequest);

        LOG.info("[IngestionController] Time taken to complete the API call is :{} ms ",System.currentTimeMillis()-startTime);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
