package com.example.project.api.requestManager;

import android.content.Context;
import android.util.Log;

import com.example.project.Helper.HttpHelper;
import com.example.project.api.CollectionAPI;
import com.example.project.api.OnFetchDataListener;
import com.example.project.config.ApiConfig;
import com.example.project.models.Account;
import com.example.project.models.Register;
import com.example.project.models.Root;
import com.example.project.models.Tags;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestTagsManager {

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(HttpHelper.getUnsafeOkHttpClient())
            .baseUrl(ApiConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private final CollectionAPI collectionAPI = retrofit.create(CollectionAPI.class);
    private final Context context;

    public RequestTagsManager(Context context) { this.context = context; }

    public void getTags(OnFetchDataListener<Tags> listener) {
        Call<Tags> call = collectionAPI.getTags();

        call.enqueue(new Callback<Tags>() {
            @Override public void onResponse(Call<Tags> call, Response<Tags> response) {
                listener.onFetchData(response);
            }
            @Override public void onFailure(Call<Tags> call, Throwable t) {

                listener.onFetchError(t);
            }
        });
    }

}
