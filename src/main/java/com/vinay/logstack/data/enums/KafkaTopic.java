package com.vinay.logstack.data.enums;

public enum KafkaTopic {

    ELASTIC_SEARCH_LOG_INGESTION("elastic-search-log-ingestion");

    private final String topic;

    private KafkaTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

}
