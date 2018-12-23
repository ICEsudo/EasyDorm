package com.easydorm.easydorm.entity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.LoginActivity;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.PostRequestInterface;
import com.easydorm.easydorm.http.URLManager;
import com.easydorm.easydorm.main.MainActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        Retrofit retrofit = new Retrofit.Builder().baseUrl(URLManager.baseUrl).build();

        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);

        Call<ResponseBody> call = getRequestInterface.refreshToken(refreshToken);

        Log.d("token", "try refresh token");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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

            Retrofit retrofit = new Retrofit.Builder().baseUrl(URLManager.baseUrl).build();
            PostRequestInterface postRequestInterface = retrofit.create(PostRequestInterface.class);
            Call<ResponseBody> call = postRequestInterface.checkToken(accessToken);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        if(response.body() == null) {
                            Log.d("token", "check response is null");
                        } else {
                            String str = new String(response.body().bytes());
                            JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
                            int code = jsonObject.get("code").getAsInt();
                            if(code == 1) {
                                Log.d("token", "check success");
                                result = true;
                            } else if(code == 2) {
                                Log.d("token", "check failed");
                            } else {
                                Log.d("token", "check token is expired");
                                result = refresh();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
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
