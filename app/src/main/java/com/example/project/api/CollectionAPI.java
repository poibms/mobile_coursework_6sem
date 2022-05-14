package com.example.project.api;

import com.example.project.models.Account;
import com.example.project.models.Collect;
import com.example.project.models.FullCollection;
import com.example.project.models.Register;
import com.example.project.models.Tags;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CollectionAPI {
    @POST("user/")
    Call<Account> register(@Body Register register);

    @POST("user/login")
    Call<Account> login(@Body Register login);

    @GET("tags/")
    Call<Tags> getTags();

    @GET("collections/")
    Call<ArrayList<Collect>> getCollections();

    @GET("collections/{id}")
    Call<FullCollection> getCollById(@Path("id") Integer id);

    @GET("collections/user")
    Call<ArrayList<Collect>> getCollByUserId(@Header("authorization") String token);

    @DELETE("collections/{colId}")
    Call<String> deleteColl(@Header("authorization") String token, @Path("colId") Integer id);
}
