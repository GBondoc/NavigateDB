package com.navigatedb.ws.io.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;

    @OneToMany(mappedBy = "erdDetails", cascade = CascadeType.ALL)
    List<EntityEntity> entities;

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

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }

    public List<EntityEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityEntity> entities) {
        this.entities = entities;
    }
}
