package com.example.project.api;

import com.example.project.models.Account;
import com.example.project.models.Register;
import com.example.project.models.Tags;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CollectionAPI {
    @POST("user/")
    Call<Account> register(@Body Register register);

    @POST("user/login")
    Call<Account> login(@Body Register login);

    @GET("collections")
    Call<ArrayList<Tags>> getTags();

}
