package com.vinay.logstack.data.enums;

public enum ESIndexField {
    LEVEL("level"),
    MESSAGE("message"),
    SPAN_ID("spanId"),
    TRACE_ID("traceId"),
    RESOURCE_ID("resourceId"),
    PARENT_RESOURCE_ID("parentResourceId"),
    COMMIT("commit"),
    TIMESTAMP("timestamp");

    private final String name;

    private ESIndexField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
