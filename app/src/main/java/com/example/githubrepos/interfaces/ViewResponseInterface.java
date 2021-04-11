package com.example.githubrepos.interfaces;


import com.example.githubrepos.pojo.ErrorBody;
import com.example.githubrepos.pojo.jsonresponsemodel.JsonResponseModel;

import java.util.List;

public interface ViewResponseInterface {
    void onGetAcademy(List<JsonResponseModel> jsonResponseModel);
    void onFailure(ErrorBody errorBody, int statusCode);
    void onTokenExpired(String errorMessage);

}
