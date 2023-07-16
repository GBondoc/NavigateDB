package com.navigatedb.ws.shared.dto;

import java.io.Serializable;

public class EntityDto implements Serializable {

    public static final long serialVersionUID = -4509749847612323418L;
    private long id;
    private String entityId;
    private String name;
    private long rowCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

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