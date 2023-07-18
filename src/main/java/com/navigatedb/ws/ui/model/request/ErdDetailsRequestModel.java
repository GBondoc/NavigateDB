package com.navigatedb.ws.ui.model.request;

import java.util.List;

public class ErdDetailsRequestModel {
    private String name;
    private List<EntityDetailsRequestModel> entities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityDetailsRequestModel> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityDetailsRequestModel> entities) {
        this.entities = entities;
    }
}
