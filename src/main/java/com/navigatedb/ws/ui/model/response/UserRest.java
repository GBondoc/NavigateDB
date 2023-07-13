package com.navigatedb.ws.ui.model.response;

import java.util.List;

public class UserRest {
    private String userId;
    private String username;
    private String email;
    private List<ErdRest> erds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ErdRest> getErds() {
        return erds;
    }

    public void setErds(List<ErdRest> erds) {
        this.erds = erds;
    }
}
