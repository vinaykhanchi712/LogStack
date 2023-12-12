package com.vinay.logstack.data.response;

import com.vinay.logstack.data.dto.QueryData;

public class QueryResponse {

    private Long totalSize;

    private Integer page ;

    private Integer pageSize;

    private String sortOrder ;

    private QueryData data;

    public QueryResponse() {
    }

    public QueryResponse(Long totalSize, Integer page, Integer pageSize, String sortOrder, QueryData data) {
        this.totalSize = totalSize;
        this.page = page;
        this.pageSize = pageSize;
        this.sortOrder = sortOrder;
        this.data = data;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public QueryData getData() {
        return data;
    }

    public void setData(QueryData data) {
        this.data = data;
    }
}
