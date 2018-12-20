package com.easydorm.easydorm.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easydorm.easydorm.EasyDormApp;

public class SPUtil {

    private static Context context = EasyDormApp.getContext();

    public static SharedPreferences getUserInfo() {
        return context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getAppConstants() {
        return context.getSharedPreferences("appConstants", Context.MODE_PRIVATE);
    }



}
