package com.easydorm.easydorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    static ArrayList<Activity> activityArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void addActivity(Activity activity) {
        if(!activityArrayList.contains(activity)) {
            activityArrayList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        activityArrayList.remove(activity);
    }

    public void finishAllActivity() {
        for(Activity activity : activityArrayList) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
