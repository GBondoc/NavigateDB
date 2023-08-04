package com.navigatedb.ws.ui.model.response;

public class EntityRelationRest {
    private String entityRelationId;
    private String channel;
    private EntityRest entityRest;

    public String getEntityRelationId() {
        return entityRelationId;
    }

    public void setEntityRelationId(String entityRelationId) {
        this.entityRelationId = entityRelationId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public EntityRest getEntityRest() {
        return entityRest;
    }

    public void setEntityRest(EntityRest entityRest) {
        this.entityRest = entityRest;
    }
}
