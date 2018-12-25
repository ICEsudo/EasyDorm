package com.easydorm.easydorm.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.setting.fragment.SettingMainFragment;

public class SettingActivity extends BaseActivity {

    Toolbar toolbar;
    TextView textView;
    ImageView toolbarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addActivity(this);

        initView();
        initListener();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar_setting).findViewById(R.id.toolbar_back);
        textView = toolbar.findViewById(R.id.toolbar_back_text_title);
        textView.setText("设置");
        textView = toolbar.findViewById(R.id.toolbar_back_text_left);
        toolbarIcon = toolbar.findViewById(R.id.toolbar_back_icon);
    }

    private void initListener() {
        View.OnClickListener finishListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        textView.setOnClickListener(finishListener);
        toolbarIcon.setOnClickListener(finishListener);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
