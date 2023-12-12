package com.vinay.logstack.services.Impl;

import com.vinay.logstack.services.ESClientService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ESClientServiceImpl implements ESClientService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public SearchResponse getSearchResponse(SearchRequest searchRequest) throws Exception {
        SearchResponse searchResponse= new SearchResponse();
        long startTime= System.currentTimeMillis();
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("[ESClientHelperService]Time taken to execute search request is :{} ", System.currentTimeMillis()-startTime);
            if(searchResponse.status().getStatus()!=200){
                log.error("[ESClientHelperService] Exception occurred while getting search response from ES");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return searchResponse;
    }
}
