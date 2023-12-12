package com.vinay.logstack.services.Impl;

import com.vinay.logstack.data.enums.ESIndex;
import com.vinay.logstack.data.enums.ESIndexField;
import com.vinay.logstack.data.request.QueryRequest;
import com.vinay.logstack.services.ESQueryBuilderService;
import io.micrometer.common.util.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ESQueryBuilderServiceImpl implements ESQueryBuilderService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public SearchRequest getESSearchQuery(QueryRequest request , Integer from , Integer size) {
        SearchRequest searchRequest= new SearchRequest(ESIndex.LOGSTACK.getIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(!Objects.isNull(request.getMessage()) && StringUtils.isNotBlank(request.getMessage().trim())){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(ESIndexField.MESSAGE.getName(), request.getMessage()));
        }
        if(!Objects.isNull(request.getLevel()) && StringUtils.isNotBlank(request.getLevel().trim())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(ESIndexField.LEVEL.getName(), request.getLevel()));
        }
        if(!Objects.isNull(request.getTraceId()) && StringUtils.isNotBlank(request.getTraceId().trim())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(ESIndexField.TRACE_ID.getName(), request.getLevel()));
        }
        if(!Objects.isNull(request.getSpanId()) && StringUtils.isNotBlank(request.getSpanId().trim())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(ESIndexField.SPAN_ID.getName(), request.getLevel()));
        }
        if(!Objects.isNull(request.getParentResourceId()) && StringUtils.isNotBlank(request.getParentResourceId().trim())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(ESIndexField.PARENT_RESOURCE_ID.getName(), request.getLevel()));
        }
        if(!Objects.isNull(request.getResourceId()) && StringUtils.isNotBlank(request.getResourceId().trim())){
            boolQueryBuilder.filter(QueryBuilders.termQuery(ESIndexField.RESOURCE_ID.getName(), request.getLevel()));
        }
        if(!Objects.isNull(request.getToDate()) && !Objects.isNull(request.getFromDate()) &&
                StringUtils.isNotBlank(request.getToDate().trim()) && StringUtils.isNotBlank(request.getFromDate().trim()) ){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery(ESIndexField.TIMESTAMP.getName())
                    .from(request.getFromDate(),true)
                    .to(request.getToDate(),true));
        }


        searchSourceBuilder.trackTotalHits(true).from(from).size(size)
                .query(boolQueryBuilder)
                        .sort(ESIndexField.TIMESTAMP.getName(), "asc".equalsIgnoreCase(request.getSortOrder())? SortOrder.ASC:SortOrder.DESC);



        searchRequest.source(searchSourceBuilder);
        log.info("[ESQueryBuilderServiceImpl]ES query formed is : {}", searchRequest.toString());
        return searchRequest;
    }
}
