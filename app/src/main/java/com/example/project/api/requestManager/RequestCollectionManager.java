package com.example.project.api.requestManager;

import android.content.Context;
import android.util.Log;

import com.example.project.Helper.HttpHelper;
import com.example.project.api.CollectionAPI;
import com.example.project.api.OnFetchDataListener;
import com.example.project.config.ApiConfig;
import com.example.project.models.Collect;
import com.example.project.models.FullCollection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestCollectionManager {
    private final Retrofit retrofit = new Retrofit.Builder()
            .client(HttpHelper.getUnsafeOkHttpClient())
            .baseUrl(ApiConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private final CollectionAPI collectionAPI = retrofit.create(CollectionAPI.class);
    private final Context context;

    public RequestCollectionManager(Context context) { this.context = context; }

    public void getCollections(OnFetchDataListener<ArrayList<Collect>> listener) {
        Call<ArrayList<Collect>> call = collectionAPI.getCollections();


        call.enqueue(new Callback<ArrayList<Collect>>() {
            @Override public void onResponse(Call<ArrayList<Collect>> call, Response<ArrayList<Collect>> response) {
                listener.onFetchData(response);
            }
            @Override public void onFailure(Call<ArrayList<Collect>> call, Throwable t) {
                listener.onFetchError(t);
            }
        });
    }

    public void getCollById(OnFetchDataListener<FullCollection> listener, Integer id) {
        Call<FullCollection> call = collectionAPI.getCollById(id);


        call.enqueue(new Callback<FullCollection>() {
            @Override public void onResponse(Call<FullCollection> call, Response<FullCollection> response) {
                listener.onFetchData(response);
            }
            @Override public void onFailure(Call<FullCollection> call, Throwable t) {
                listener.onFetchError(t);
            }
        });
    }

    public void getCollByUserId(OnFetchDataListener<ArrayList<Collect>> listener, String token) {
        Call<ArrayList<Collect>> call = collectionAPI.getCollByUserId("Bearer " + token);


        call.enqueue(new Callback<ArrayList<Collect>>() {
            @Override public void onResponse(Call<ArrayList<Collect>> call, Response<ArrayList<Collect>> response) {
                listener.onFetchData(response);
            }
            @Override public void onFailure(Call<ArrayList<Collect>> call, Throwable t) {
                listener.onFetchError(t);
            }
        });
    }

    public void deleteColl(OnFetchDataListener<String> listener, String token, Integer id) {
        Call<String> call = collectionAPI.deleteColl("Bearer " + token, id);


        call.enqueue(new Callback<String>() {
            @Override public void onResponse(Call<String> call, Response<String> response) {
                listener.onFetchData(response);
            }
            @Override public void onFailure(Call<String> call, Throwable t) {
                listener.onFetchError(t);
            }
        });
    }


}
