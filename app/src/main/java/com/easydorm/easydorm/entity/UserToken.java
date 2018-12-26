package com.easydorm.easydorm.entity;

import android.content.SharedPreferences;
import android.util.Log;

import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.PostRequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserToken {
    private String accessToken;
    private String refreshToken;

    private boolean result;

    public UserToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        saveToken();
    }

    public String getAccessToken() {
        return accessToken;
    }

    private boolean refresh() {
        result = false;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);

        Call<BaseResponse> call = getRequestInterface.refreshToken(refreshToken);

        Log.d("token", "try refresh token");

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                String access = response.headers().get("access_token");
                String ref = response.headers().get("refresh_token");
                if(ref != null) refreshToken = ref;
                if(access != null) {
                    Log.d("token", "access: " + access);
                    accessToken = access;
                    result = true;
                    Log.d("token", "refresh success");
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("token", "refresh failed");
            }
        });
        Log.d("token", "refreshed :\n" + accessToken + "\n" + refreshToken);
        saveToken();
        return result;
    }

    public boolean checkToken() {
        SharedPreferences sp = SPUtil.getUserInfo();
        String token = sp.getString("accessToken", "");

        Log.d("token", "check: \n" + accessToken + "\n" + refreshToken);

        if(token==null || token.equals("")) {
            Log.d("token", "token is null");
            return false;
        } else {
            //TODO check

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Url.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostRequestInterface postRequestInterface = retrofit.create(PostRequestInterface.class);
            Call<BaseResponse> call = postRequestInterface.checkToken(accessToken);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    if(response.body() == null) {
                        Log.d("token", "check response is null");
                    } else {
                        switch (response.body().getCode()) {
                            case 1:
                                Log.d("token", "check success");
                                result = true;
                                break;
                            case 2:
                                Log.d("token", "check failed");
                                break;
                            case 3:
                                Log.d("token", "check token is expired");
                                result = refresh();
                                break;
                            default:

                        }
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Log.d("token", "check request failed");
                }
            });

        }

        return result;
    }

    private void saveToken() {
        SharedPreferences sp = SPUtil.getUserInfo();
        sp.edit().putString("refreshToken", refreshToken)
                .putString("accessToken", accessToken).apply();
    }




}
