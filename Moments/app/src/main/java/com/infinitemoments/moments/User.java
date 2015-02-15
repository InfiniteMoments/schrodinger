package com.infinitemoments.moments;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Salman on 2/15/2015.
 */
public class User {
    @SerializedName("id")
    public String id;
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String name;
    @SerializedName("username")
    public String username;
    @SerializedName("token")
    public String token;
}
