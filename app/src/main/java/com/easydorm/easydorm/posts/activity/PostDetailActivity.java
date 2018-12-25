package com.easydorm.easydorm.posts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.R;

public class PostDetailActivity extends BaseActivity {

    Toolbar toolbar;
    TextView textView;
    ImageView toolbarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        initView();
        initListener();
    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar_post_detail).findViewById(R.id.toolbar_back);
        textView = toolbar.findViewById(R.id.toolbar_back_text_title);
        textView.setText("");
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
}
