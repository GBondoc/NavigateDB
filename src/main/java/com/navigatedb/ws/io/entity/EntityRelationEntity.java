package com.navigatedb.ws.io.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "entityrelations")
public class EntityRelationEntity {

    private static final long serialVersionUID = 2250083571236616803L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String entityRelationId;

    @Column(nullable = false)
    private String channel;

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
}
