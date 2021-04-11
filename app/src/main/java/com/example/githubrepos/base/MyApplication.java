package com.example.githubrepos.base;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Context mContext;
    public static MyApplication mInstance;
    public static ApiClient techApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getContext();
        techApiClient = new ApiClient();
    }

    public static synchronized Context getContext(){
        return mContext;
    }

    public static MyApplication getApp(){
        if (mInstance != null && mInstance instanceof MyApplication){
            return mInstance;
        }else {
            mInstance = new MyApplication();
            mInstance.onCreate();
            return mInstance;
        }
    }
    public ApiInterface getRetrofitInterface(){
        return (ApiInterface) ApiClient.chargeCenterApiInterface();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
