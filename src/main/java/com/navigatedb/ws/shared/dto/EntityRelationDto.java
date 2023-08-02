package com.navigatedb.ws.shared.dto;

import java.io.Serializable;

public class EntityRelationDto implements Serializable {

    private static final long serialVersionUID = 5398452689601714082L;

    private long id;

    private String entityRelationId;

    private String channel;

    private EntityDto entityDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public EntityDto getEntityDetails() {
        return entityDetails;
    }

    public void setEntityDetails(EntityDto entityDetails) {
        this.entityDetails = entityDetails;
    }
}
