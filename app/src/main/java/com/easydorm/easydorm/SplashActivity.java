package com.easydorm.easydorm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class SplashActivity extends Activity {

    private MyHandle myHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        myHandle = new MyHandle();

        init();


    }

    private void init() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myHandle.sendEmptyMessage(1);
    }


    class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
    }


}
