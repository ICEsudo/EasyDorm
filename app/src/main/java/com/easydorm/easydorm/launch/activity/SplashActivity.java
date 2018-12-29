package com.easydorm.easydorm.launch.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.Utils.ImageUtil;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.annotation.LoginRequired;
import com.easydorm.easydorm.entity.User;
import com.easydorm.easydorm.entity.UserInfo;
import com.easydorm.easydorm.entity.UserToken;
import com.easydorm.easydorm.main.MainActivity;
import com.easydorm.easydorm.R;

public class SplashActivity extends BaseActivity {

    private MyHandle myHandle;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("life", "Splash start");

        EasyDormApp.setUser(new User());

        sp = SPUtil.getAppConstants();
        if(sp.getBoolean("isFirstLaunch", true)) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
            return;
        }



        setContentView(R.layout.activity_splash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        myHandle = new MyHandle();

        init();

    }

    private void init() {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("life", "Splash end");
        myHandle.sendEmptyMessage(1);
    }


    class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                if(EasyDormApp.getUser().getUserInfo().isLogined()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
