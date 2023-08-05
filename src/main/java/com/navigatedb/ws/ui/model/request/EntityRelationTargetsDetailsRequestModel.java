package com.navigatedb.ws.ui.model.request;

public class EntityRelationTargetsDetailsRequestModel {
    private String entitySenderName;
    private String entityReceiverName;
    private String relationId;

    public String getEntitySenderName() {
        return entitySenderName;
    }

    public void setEntitySenderName(String entitySenderName) {
        this.entitySenderName = entitySenderName;
    }

    public String getEntityReceiverName() {
        return entityReceiverName;
    }

    public void setEntityReceiverName(String entityReceiverName) {
        this.entityReceiverName = entityReceiverName;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}
