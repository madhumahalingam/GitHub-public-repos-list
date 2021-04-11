package com.example.githubrepos.viewmodel;

import android.content.Context;

import com.example.githubrepos.datamanger.ResponseModelDataManager;
import com.example.githubrepos.interfaces.ResponseHandler;
import com.example.githubrepos.interfaces.ViewResponseInterface;
import com.example.githubrepos.pojo.ErrorBody;
import com.example.githubrepos.pojo.jsonrequesetmodel.RepoRequestModel;
import com.example.githubrepos.pojo.jsonresponsemodel.JsonResponseModel;

import java.util.List;


public class ResponseViewModel {

    private ViewResponseInterface newsViewResponseInterface;
    private Context mContext;
    private ResponseModelDataManager academyDataManager;

    public ResponseViewModel(Context mContext, ViewResponseInterface newsViewResponseInterface) {
        this.mContext = mContext;
        this.newsViewResponseInterface = newsViewResponseInterface;
        this.academyDataManager = new ResponseModelDataManager();
    }


    public void getRepos(int repo_id) {
        RepoRequestModel requestModel = new RepoRequestModel();
        requestModel.repo_id = repo_id;
        academyDataManager.callGitHubRepos(requestModel,new ResponseHandler<List<JsonResponseModel>>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(List<JsonResponseModel> item, String message) {
                newsViewResponseInterface.onGetAcademy(item);
            }

            @Override
            public void onTokenExpired(String errorMessage) {
                newsViewResponseInterface.onTokenExpired(errorMessage);
            }

            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                newsViewResponseInterface.onFailure(errorBody, statusCode);
            }
        });
    }
}
