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

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAllActivity() {
        for(Activity activity : activities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    public static void finishToLoginActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
        finishAllActivity();
//        SPUtil.clearUserInfo();
    }

}
