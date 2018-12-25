package com.easydorm.easydorm.launch.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.EasyDormApp;
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

        sp = SPUtil.getAppConstants();
        if(sp.getBoolean("isFirstLaunch", true)) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
            return;
        }

        initUser();

        setContentView(R.layout.activity_splash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        myHandle = new MyHandle();

        init();

    }

    private void initUser() {
        sp = SPUtil.getUserInfo();
        UserToken userToken = new UserToken(
                sp.getString("accessToken", ""),
                sp.getString("refreshToken", ""));
        UserInfo userInfo = new UserInfo(sp.getInt("userType", 0));
        EasyDormApp.setUser(new User(userToken, userInfo));
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


    @LoginRequired
    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }


    class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                if(SPUtil.getUserInfo().getBoolean("isLogined", false)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
//                startMainActivity();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
