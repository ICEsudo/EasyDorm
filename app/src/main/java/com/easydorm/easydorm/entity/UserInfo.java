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

    private boolean isPWRemembered;
    private boolean isLogined;

    private String avatarPath;
    private String avatarUrl;

    private SharedPreferences sp = SPUtil.getUserInfo();

    public UserInfo() {
        setUserType(0);
    }

    public UserInfo(int userType) {
        setUserType(userType);
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
                avatarUrl = Constants.Url.baseUrl + "/static/" + EasyDormApp.getCurUserId() + ".jpg";
            }
        }
        return avatarUrl;
    }
    public UserInfo setAvatarUrl(@NonNull String avatarUrl) {
        this.avatarUrl = avatarUrl;
        sp.edit().putString("avatarUrl", avatarUrl).apply();
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
