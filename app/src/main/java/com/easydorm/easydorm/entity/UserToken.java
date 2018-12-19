package com.easydorm.easydorm.entity;

public class UserToken {
    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public boolean refresh() {
        return false;
    }

}
