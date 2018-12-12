package com.easydorm.easydorm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends BaseActivity {

    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, LoginActivity.class));

        initView();



    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation);
    }

}
