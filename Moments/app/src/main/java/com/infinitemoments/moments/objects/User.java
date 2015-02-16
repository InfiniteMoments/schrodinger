package com.infinitemoments.moments.objects;

import com.google.gson.annotations.SerializedName;
import com.infinitemoments.moments.models.UserObject;

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
    @SerializedName("password")
    public String password;

    public User(){

    }

    public User(UserObject uo){
        id = uo.getId();
        email = uo.getEmail();
        name = uo.getName();
        username=uo.getUsername();
        token = uo.getToken();
    }
}
