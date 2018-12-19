package com.easydorm.easydorm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.easydorm.easydorm.annotation.LoginRequired;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

    }
}
