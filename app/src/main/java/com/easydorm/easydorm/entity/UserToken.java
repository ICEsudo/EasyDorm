package com.easydorm.easydorm.entity;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.http.GetRequestInterface;

import androidx.annotation.NonNull;
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


    private void refresh(Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);

        Call<BaseResponse> call = getRequestInterface.refreshToken(getRefreshToken());

        Log.d("token", "try refresh token");

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.body() != null) {
                    String access = response.headers().get("access_token");
                    String ref = response.headers().get("refresh_token");
                    if (ref != null) setRefreshToken(ref);
                    if (access != null) {
                        Log.d("token", "access: " + access);
                        setAccessToken(access);
                        Log.d("token", "refresh success");
                    }
                    if (response.body().getCode() == 2) {
                        ActivityCollector.finishToLoginActivity(context);
                    }
                } else {
                    ActivityCollector.finishToLoginActivity(context);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                ActivityCollector.finishToLoginActivity(context);
                Log.d("token", "refresh failed");
            }
        });
        Log.d("token", "refreshed :\n" + getAccessToken() + "\n" + getRefreshToken());

    }


}
