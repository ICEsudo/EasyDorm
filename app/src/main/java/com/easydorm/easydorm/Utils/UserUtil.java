package com.easydorm.easydorm.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easydorm.easydorm.EasyDormApp;

public class UserUtil {

    public static int loginCheck() {
        SharedPreferences sp = SPUtil.getUserInfo();
        String token = sp.getString("accessToken", "");

        //TODO      request and check permission
        if(token!=null && !token.equals("")) {
            return 1;
        }

        return 0;
    }



}
