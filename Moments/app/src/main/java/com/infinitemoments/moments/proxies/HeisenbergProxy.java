package com.infinitemoments.moments.proxies;

import com.infinitemoments.moments.objects.Credentials;
import com.infinitemoments.moments.objects.User;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Salman on 2/15/2015.
 */
public interface HeisenbergProxy {
    @POST("/users")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    void postUser(@Body User signUp,
                  Callback<User> cb);

    @POST("/login")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    void postLogin(@Body Credentials credentials,
                   Callback<User> cb);
}
