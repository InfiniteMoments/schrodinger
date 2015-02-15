package com.infinitemoments.moments;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Salman on 2/15/2015.
 */
public interface HeisenbergProxy {
    @FormUrlEncoded
    @POST("/users")
    void postUser(@Field("email") String email,
                  @Field("password") String password,
                  @Field("name") String name,
                  Callback<User> cb);
}
