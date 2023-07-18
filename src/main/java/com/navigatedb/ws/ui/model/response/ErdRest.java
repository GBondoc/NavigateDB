package com.navigatedb.ws.ui.model.response;

import java.util.List;

public class ErdRest {
    private String erdId;
    private String name;
    private List<EntityRest> entities;

    public String getErdId() {
        return erdId;
    }

    public void setErdId(String erdId) {
        this.erdId = erdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityRest> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityRest> entities) {
        this.entities = entities;
    }
}
