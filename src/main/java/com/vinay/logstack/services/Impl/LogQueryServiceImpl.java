package com.vinay.logstack.services.Impl;


import com.vinay.logstack.data.dto.QueryData;
import com.vinay.logstack.data.enums.ESIndexField;
import com.vinay.logstack.data.request.QueryRequest;
import com.vinay.logstack.data.response.QueryResponse;
import com.vinay.logstack.services.ESClientService;
import com.vinay.logstack.services.ESQueryBuilderService;
import com.vinay.logstack.services.LogQueryService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LogQueryServiceImpl implements LogQueryService {

    @Autowired
    private ESQueryBuilderService esQueryBuilderService;
    @Autowired
    private ESClientService esClientService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public QueryResponse getLogQueryResult(QueryRequest request,Integer page , Integer size ) throws Exception {
        QueryResponse response= new QueryResponse();

        Integer from= page*size;

        //prepare search query.
        SearchRequest searchRequest=  esQueryBuilderService.getESSearchQuery(request ,from,size);

        //get response from ES.
        SearchResponse searchResponse= esClientService.getSearchResponse(searchRequest);

        //parse ES response in map
        HashMap<String,String> timeStampToDataMap = prepareESResponse(searchResponse);

        //prepare final response
        response.setPage(page);
        response.setPageSize(timeStampToDataMap.size());
        response.setSortOrder(request.getSortOrder());
        response.setTotalSize(searchResponse.getHits().getTotalHits().value);
        response.setData(new QueryData(timeStampToDataMap));

        return response;
    }

    private HashMap<String, String> prepareESResponse(SearchResponse searchResponse) {
        log.info("[LogQueryServiceImpl]parsing ES response");
        HashMap<String,String> response = new HashMap<>();

        if(searchResponse.getHits() != null && searchResponse.getHits().getHits() != null) {
            for (SearchHit hits : searchResponse.getHits().getHits()) {
                Map<String, Object> source = hits.getSourceAsMap();
                //get key.
                String key = (String)source.getOrDefault(ESIndexField.TIMESTAMP.getName(),"");
                //prepare value.
                StringBuilder value = new StringBuilder();
                value.append((String)source.getOrDefault(ESIndexField.TIMESTAMP.getName(),"")).append(" ");
                value.append("traceId:").append("[");
                value.append((String)source.getOrDefault(ESIndexField.TRACE_ID.getName(),"")).append("]").append(" ");
                value.append("commitId:").append("[");
                value.append((String)source.getOrDefault(ESIndexField.COMMIT.getName(),"")).append("]").append(" ");
                value.append("spanId:").append("[");
                value.append((String)source.getOrDefault(ESIndexField.SPAN_ID.getName(),"")).append("]").append(" ");
                value.append("prId:").append("[");
                value.append((String)source.getOrDefault(ESIndexField.PARENT_RESOURCE_ID.getName(),"")).append("]").append(" ");
                value.append("rId:").append("[");
                value.append((String)source.getOrDefault(ESIndexField.RESOURCE_ID.getName(),"")).append("]").append(" ");
                value.append((String)source.getOrDefault(ESIndexField.LEVEL.getName(),"")).append(" ");
                value.append((String)source.getOrDefault(ESIndexField.MESSAGE.getName(),"")).append(" ");

                //fill response.
                response.put(key, value.toString());

            }
        }
        return response;
    }
}
