package com.easydorm.easydorm.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.entity.User;
import com.easydorm.easydorm.launch.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();
    private static Activity topActivity;

    public static Activity getTopActivity() {
        return topActivity;
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
        topActivity = activity;
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
        if(topActivity != null && activity == topActivity) topActivity = null;
    }

    public static void finishAllActivity() {
        topActivity = null;
        for(Activity activity : activities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    public static void finishToLoginActivity() {
        EasyDormApp.getUser().getUserInfo().setLogined(false);
        topActivity.startActivity(new Intent(topActivity, LoginActivity.class));
        finishAllActivity();
//        SPUtil.clearUserInfo();
    }

}
