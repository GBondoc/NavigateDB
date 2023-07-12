package com.navigatedb.ws.io.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity(name="erds")
public class ErdEntity implements Serializable {
    public static final long serialVersionUID = -7777183165796025758L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String erdId;

    @Column(nullable = false, length = 100)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
