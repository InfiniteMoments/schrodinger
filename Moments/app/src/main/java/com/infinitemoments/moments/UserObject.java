package com.infinitemoments.moments;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Salman on 2/15/2015.
 */
public class UserObject extends RealmObject {
    private String id;
    private String email;
    private String name;
    private String username;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
