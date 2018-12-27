package com.easydorm.easydorm.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easydorm.easydorm.EasyDormApp;

public class SPUtil {


    public static SharedPreferences getUserInfo() {
        return EasyDormApp.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getAppConstants() {
        return EasyDormApp.getContext().getSharedPreferences("appConstants", Context.MODE_PRIVATE);
    }

    public static void clearUserInfo() {
        EasyDormApp.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit().clear().apply();
    }

}
