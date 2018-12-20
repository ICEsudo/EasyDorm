package com.easydorm.easydorm.entity;

public class User {

    private UserInfo userInfo;
    private UserToken userToken;


    public boolean updateUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        return true;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getToken() {
        return userToken.getAccessToken();
    }

    public boolean refreshToken() {
        return userToken.refresh();
    }

}
