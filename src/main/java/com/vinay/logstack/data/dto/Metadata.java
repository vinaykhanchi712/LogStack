package com.vinay.logstack.data.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Metadata implements Serializable {

    private String parentResourceId;

    public String getParentResourceId() {
        return parentResourceId;
    }

    public void setParentResourceId(String parentResourceId) {
        this.parentResourceId = parentResourceId;
    }
}
