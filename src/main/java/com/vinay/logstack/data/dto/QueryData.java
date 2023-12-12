package com.vinay.logstack.data.dto;

import java.util.HashMap;

public class QueryData {

    private HashMap<String,String> queryMap;

    public QueryData() {
    }

    public QueryData(HashMap<String, String> queryMap) {
        this.queryMap = queryMap;
    }

    public HashMap<String, String> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(HashMap<String, String> queryMap) {
        this.queryMap = queryMap;
    }
}
