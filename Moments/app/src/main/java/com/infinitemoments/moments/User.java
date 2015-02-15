package com.infinitemoments.moments;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Salman on 2/15/2015.
 */
public class User {
    @SerializedName("id")
    public String id;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("name")
    public String name;
    @SerializedName("token")
    public String token;
}
