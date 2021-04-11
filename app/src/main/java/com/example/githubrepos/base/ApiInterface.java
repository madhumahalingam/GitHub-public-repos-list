package com.example.githubrepos.base;


import com.example.githubrepos.config.ApiParams;
import com.example.githubrepos.pojo.jsonresponsemodel.JsonResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface ApiInterface {

    @HTTP(method = "GET", path = ApiParams.REPOSITORY_URL, hasBody = false)
    Call<List<JsonResponseModel>> getGitHubRepos(@Query("since")  int repo_id);
}
