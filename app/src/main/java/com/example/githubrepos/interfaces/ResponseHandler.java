package com.example.githubrepos.interfaces;


import com.example.githubrepos.pojo.ErrorBody;

public interface ResponseHandler<T> {
    void onSuccess(String message);
    void onSuccess(T item, String message);
    void onTokenExpired(String errorMessage);
    void onFailure(ErrorBody errorBody, int statusCode);
}
