package com.navigatedb.ws.ui.model.request;

public class EntityDetailsRequestModel {

    private String name;
    private long rowCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }
}