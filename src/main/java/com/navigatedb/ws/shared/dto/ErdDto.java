package com.navigatedb.ws.shared.dto;

import java.io.Serializable;
import java.util.List;

public class ErdDto implements Serializable {

    public static final long serialVersionUID = -4824737232509965022L;
    private long id;
    private String erdId;
    private String name;
    private UserDto userDetails;
    private List<EntityDto> entities;

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

    public UserDto getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDto userDetails) {
        this.userDetails = userDetails;
    }

    public List<EntityDto> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityDto> entities) {
        this.entities = entities;
    }
}
