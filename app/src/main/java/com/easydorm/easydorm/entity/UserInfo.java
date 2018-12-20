package com.easydorm.easydorm.entity;

import android.content.SharedPreferences;

import com.easydorm.easydorm.Utils.SPUtil;

public class UserInfo {

    private String nickName;
    private int userType;
    private SharedPreferences sp;

    public UserInfo(int userType) {
        this.userType = userType;
        sp = SPUtil.getUserInfo();
        saveUserInfo();
    }

    public int getUserType() {
        return userType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    private void saveUserInfo() {
        sp.edit().putInt("userType", userType).apply();
    }

}
