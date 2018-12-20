package com.easydorm.easydorm.entity;

import android.content.SharedPreferences;

import com.easydorm.easydorm.Utils.SPUtil;

public class UserToken {
    private String accessToken;
    private String refreshToken;

    public UserToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        saveToken();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public boolean refresh() {
        //TODO

        saveToken();
        return true;
    }

    public boolean checkToken() {
        SharedPreferences sp = SPUtil.getUserInfo();
        String token = sp.getString("accessToken", "");

        //TODO
        if(token!=null && !token.equals("")) {
            return true;
        }

        return false;
    }

    private void saveToken() {
        SharedPreferences sp = SPUtil.getUserInfo();
        sp.edit().putString("refreshToken", refreshToken)
                .putString("accessToken", accessToken).apply();
    }




}
