package com.easydorm.easydorm.entity;

import android.content.SharedPreferences;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.SPUtil;

import io.reactivex.annotations.NonNull;

public class UserInfo {

    private String userId;
    private String password;
    private String nickName;
    private int userType;
    private int uId;

    private boolean isPWRemembered;
    private boolean isLogined;

    private String avatarPath;
    private String avatarUrl;

    private UserInfoBean userInfoBean;

    private SharedPreferences sp = SPUtil.getUserInfo();

    public UserInfo() {
        uId = sp.getInt("uId", -1);
        setUserType(0);
    }

    public UserInfo(int userType) {
        uId = sp.getInt("uId", -1);
        setUserType(userType);
    }

    public int getuId() {
        if(uId == -1) {
            sp.getInt("uId", -1);
        }
        return uId;
    }

    public UserInfo setuId(int uId) {
        this.uId = uId;
        sp.edit().putInt("uId", uId).apply();
        return this;
    }

    public UserInfoBean getUserInfoBean() {
        if(userInfoBean == null) userInfoBean = new UserInfoBean();
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public boolean isPWRemembered() {
        isPWRemembered = sp.getBoolean("isPWRemembered", false);
        return isPWRemembered;
    }
    public UserInfo setPWRemembered(boolean PWRemembered) {
        isPWRemembered = PWRemembered;
        sp.edit().putBoolean("isPWRemembered", isPWRemembered).apply();
        return this;
    }


    public String getPassword() {
        if(password == null || password.equals("")) {
            password = sp.getString("password", "");
        }
        return password;
    }
    public UserInfo setPassword(@NonNull String password) {
        this.password = password;
        sp.edit().putString("password", password).apply();
        return this;
    }


    public boolean isLogined() {
        isLogined = sp.getBoolean("isLogined", false);
        return isLogined;
    }
    public UserInfo setLogined(boolean logined) {
        isLogined = logined;
        sp.edit().putBoolean("isLogined", isLogined).apply();
        return this;
    }


    public String getUserId() {
        if(userId == null || userId.equals("")) {
            userId = sp.getString("userId", "");
        }
        return userId;
    }
    public UserInfo setUserId(@NonNull String userId) {
        this.userId = userId;
        sp.edit().putString("userId", userId).apply();
        return this;
    }


    public int getUserType() {
        userType = sp.getInt("userType", 0);
        if(userType < 0 || userType > 2) setUserType(0);
        return userType;
    }
    private UserInfo setUserType(int userType) {
        if(userType < 0 || userType > 2) userType = 0;
        this.userType = userType;
        sp.edit().putInt("userType", userType).apply();
        return this;
    }


    public String getNickName() {
        if(nickName == null || nickName.equals("")) {
            nickName = sp.getString("nickName", "");
        }
        return nickName;
    }
    public UserInfo setNickName(@NonNull String nickName) {
        this.nickName = nickName;
        sp.edit().putString("nickName", nickName).apply();
        return this;
    }


    public String getAvatarUrl() {
        if (avatarUrl == null || avatarUrl.equals("")) {
            avatarUrl = sp.getString("avatarUrl", "");
            if (avatarUrl == null || avatarUrl.equals("")) {
                if(userInfoBean != null ) {
                    avatarUrl = Constants.Url.baseUrl + userInfoBean.getPicture();
                }
            }
        }
        return avatarUrl;
    }

    public UserInfo setAvatarUrl(@NonNull String avatarUrl) {
        this.avatarUrl = avatarUrl;
        userInfoBean.setPicture(avatarUrl);
        sp.edit().putString("avatarUrl", Constants.Url.baseUrl + avatarUrl).apply();
        return this;
    }


    public String getAvatarPath() {
        if(avatarPath == null || avatarPath.equals("")) {
            avatarPath = sp.getString("avatarPath", "");
        }
        return avatarPath;
    }

    public UserInfo setAvatarPath(@NonNull String avatarPath) {
        this.avatarPath = avatarPath;
        sp.edit().putString("avatarPath", avatarPath).apply();
        return this;
    }

}
