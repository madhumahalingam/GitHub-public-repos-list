package com.example.githubrepos.datamanger;

import android.util.Log;

import com.example.githubrepos.base.ApiInterface;
import com.example.githubrepos.interfaces.ResponseHandler;
import com.example.githubrepos.pojo.ErrorBody;
import com.example.githubrepos.pojo.jsonrequesetmodel.RepoRequestModel;
import com.example.githubrepos.pojo.jsonresponsemodel.JsonResponseModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubrepos.base.MyApplication.getApp;

public class ResponseModelDataManager {
    private final String TAG = ResponseModelDataManager.class.getSimpleName();
    private ApiInterface apiInterface;

    public ResponseModelDataManager() {
        this.apiInterface = getApp().getRetrofitInterface();
    }


    public void callGitHubRepos(RepoRequestModel requestModel, final ResponseHandler<List<JsonResponseModel>> academyResponseModel) {
        Call<List<JsonResponseModel>> userLoginCall = apiInterface.getGitHubRepos(requestModel.repo_id);
        userLoginCall.enqueue(new Callback<List<JsonResponseModel>>() {
            @Override
            public void onResponse(Call<List<JsonResponseModel>> call, Response<List<JsonResponseModel>> response) {
                /*
                 * Invoked for a received HTTP response.
                 * <p>
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call {@link Response#isSuccessful()} to determine if the response indicates success.
                 *
                 * @param call
                 * @param response*/

                int statusCode = response.code();
                if (response.isSuccessful()) {
                    academyResponseModel.onSuccess(response.body(), "SuccessModel");
                } else {
                    String serviceResponse = null;
                    try {
                        serviceResponse = response.errorBody().string();
                        ErrorBody errorBody = new Gson().fromJson(serviceResponse, ErrorBody.class);
                        academyResponseModel.onFailure(errorBody, statusCode);
                    } catch (JsonSyntaxException e) {
                        academyResponseModel.onTokenExpired(statusCode + " Something went wrong ");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("IOException", "", e);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<JsonResponseModel>> call, Throwable t) {
                Log.d(TAG, "onTokenExpired: " + t.getMessage());
                academyResponseModel.onTokenExpired(t.getMessage());
            }
        });
    }
}
