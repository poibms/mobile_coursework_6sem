package com.example.project.api;
import retrofit2.Response;

public interface OnFetchDataListener<T> {
    void onFetchData(Response<T> response);
    void onFetchError(Throwable error);
}
