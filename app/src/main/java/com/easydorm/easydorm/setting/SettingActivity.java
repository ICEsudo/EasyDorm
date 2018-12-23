package com.easydorm.easydorm.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.setting.fragment.SettingMainFragment;

public class SettingActivity extends BaseActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addActivity(this);

        toolbar = findViewById(R.id.toolbar_setting).findViewById(R.id.toolbar_back);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
