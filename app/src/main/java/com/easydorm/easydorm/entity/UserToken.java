package com.easydorm.easydorm.entity;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.HttpUtil;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.PostRequestInterface;
import com.easydorm.easydorm.http.TokenInterceptor;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserToken {

    private String accessToken;
    private String refreshToken;

    private SharedPreferences sp = SPUtil.getUserInfo();

    public UserToken() {

    }

    public UserToken(String accessToken, String refreshToken) {
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
    }

    public String getAccessToken() {
        if(accessToken == null || accessToken.equals("")) {
            accessToken = sp.getString("accessToken", "");
        }
        return accessToken;
    }

    private void setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        sp.edit().putString("accessToken", accessToken).apply();
    }

    public String getRefreshToken() {
        if(refreshToken == null || refreshToken.equals("")) {
            refreshToken = sp.getString("refreshToken", "");
        }
        return refreshToken;
    }

    private void setRefreshToken(@NonNull String refreshToken) {
        this.refreshToken = refreshToken;
        sp.edit().putString("refreshToken", refreshToken).apply();
    }


    public void refreshToken() {
        Retrofit retrofit = HttpUtil.getRetrofit(Constants.Url.baseUrl, null, GsonConverterFactory.create());
        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);

        Call<BaseResponse> call = getRequestInterface.refreshToken(getRefreshToken());

        try {
            Response<BaseResponse> response = call.execute();
            if(response.body() != null) {
                String access = response.headers().get("access_token");
                String ref = response.headers().get("refresh_token");
                if (ref != null) setRefreshToken(ref);
                if (access != null) {
                    setAccessToken(access);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void checkToken() {

        PostRequestInterface postRequestInterface = HttpUtil.getPostRequestInterface();

        Call<BaseResponse> call = postRequestInterface.checkToken(EasyDormApp.getUser().getUserToken().getAccessToken());

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) { }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) { }
        });
    }


}
