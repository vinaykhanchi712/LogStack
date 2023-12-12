package com.vinay.logstack.data.enums;

public enum ESIndex {

    LOGSTACK("logstack");

    private final String index;

    private ESIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
