package com.easydorm.easydorm.entity;

public class User {

    private UserInfo userInfo;
    private UserToken userToken;

    public User(UserToken userToken, UserInfo userInfo) {
        this.userToken = userToken;
        this.userInfo = userInfo;
    }

    public boolean updateUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        return true;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public UserToken getToken() {
        return userToken;
    }


}
