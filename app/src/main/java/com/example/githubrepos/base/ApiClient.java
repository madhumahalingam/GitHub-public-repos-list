package com.example.githubrepos.base;

import com.example.githubrepos.config.ApiParams;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static ApiParams apiParams;

    public ApiClient(){
    }

    public static ApiInterface chargeCenterApiInterface(){
        apiParams = new ApiParams();
        if (retrofit == null) {
            OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Print details of every request through retrofit.
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(apiParams.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(defaultHttpClient)
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
