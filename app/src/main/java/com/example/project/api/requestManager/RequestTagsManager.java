package com.example.project.api.requestManager;

import com.example.project.Helper.HttpHelper;
import com.example.project.api.CollectionAPI;
import com.example.project.api.OnFetchDataListener;
import com.example.project.config.ApiConfig;
import com.example.project.models.Account;
import com.example.project.models.Register;

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

    public void register(OnFetchDataListener<Account> listener, Register register) {
        Call<Account> call = collectionAPI.register(register);

        call.enqueue(new Callback<Account>() {
            @Override public void onResponse(Call<Account> call, Response<Account> response) {
                listener.onFetchData(response);
            }
            @Override public void onFailure(Call<Account> call, Throwable t) {
                listener.onFetchError(t);
            }
        });
    }

    public void login(OnFetchDataListener<Account> listener, Register login) {
        Call<Account> call = collectionAPI.login(login);

        call.enqueue(new Callback<Account>() {
            @Override public void onResponse(Call<Account> call, Response<Account> response) {
                listener.onFetchData(response);
            }
            @Override public void onFailure(Call<Account> call, Throwable t) {
                listener.onFetchError(t);
            }
        });
    }
}
