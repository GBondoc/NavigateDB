package com.navigatedb.ws.ui.model.request;

public class EntityRelationDetailsRequestModel {
    private String channel;
    private EntityDetailsRequestModel entity;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public EntityDetailsRequestModel getEntity() {
        return entity;
    }

    public void setEntity(EntityDetailsRequestModel entity) {
        this.entity = entity;
    }
}
