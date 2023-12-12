package com.vinay.logstack.controllers;


import com.vinay.logstack.data.request.QueryRequest;
import com.vinay.logstack.data.response.QueryResponse;
import com.vinay.logstack.services.LogQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private LogQueryService logQueryService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/result" ,method = RequestMethod.GET)
    public ResponseEntity<?> searchInLogs(@RequestBody QueryRequest request ,
                                          @RequestParam(value = "page") Integer page ,
                                          @RequestParam(value ="size" ,defaultValue = "10") Integer size ) throws Exception {
        long startTime = System.currentTimeMillis();
        LOG.info("[QueryController] Request received to query data with request: {}",request);

        QueryResponse response=  logQueryService.getLogQueryResult(request,page,size);

        LOG.info("[QueryController] Time taken to complete the API call is :{} ms",System.currentTimeMillis()-startTime);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
