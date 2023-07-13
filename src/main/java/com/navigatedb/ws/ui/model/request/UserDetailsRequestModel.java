package com.navigatedb.ws.ui.model.request;

import java.util.List;

public class UserDetailsRequestModel {
    private String username;
    private String email;
    private String password;
    private List<ErdDetailsRequestModel> erds;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ErdDetailsRequestModel> getErds() {
        return erds;
    }

    public void setErds(List<ErdDetailsRequestModel> erds) {
        this.erds = erds;
    }
}
