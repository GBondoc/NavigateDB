package com.navigatedb.ws.io.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name="entities")
public class EntityEntity implements Serializable {
    public static final long serialVersionUID = 8749986052501608713L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String entityId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
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
