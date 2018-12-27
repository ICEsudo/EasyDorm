package com.easydorm.easydorm.entity;

public class User {

    private UserInfo userInfo;
    private UserToken userToken;

    public User() {
        userInfo = new UserInfo();
        userToken = new UserToken();
    }

    public User setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public User setUserToken(UserToken userToken) {
        this.userToken = userToken;
        return this;
    }

    public UserToken getUserToken() {
        return userToken;
    }


}
