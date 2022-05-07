package com.example.project.api;

import com.example.project.models.Account;
import com.example.project.models.Register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CollectionAPI {
    @POST("user/")
    Call<Account> register(@Body Register register);

    @POST("user/login")
    @Headers("user-agent: android")
    Call<Account> login(@Body Register login);
}
