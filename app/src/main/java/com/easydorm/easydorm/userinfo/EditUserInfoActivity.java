package com.easydorm.easydorm.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.ActivityCollector;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUserInfoActivity extends BaseActivity {

    Toolbar toolbar;
    TextView textView, textViewRight;
    ImageView toolbarIcon;

    @BindView(R.id.edit_user_info_title)
    TextView getEditUserInfoTitleText;
    @BindView(R.id.edit_user_info_text)
    EditText editUserInfoText;
    @BindView(R.id.edit_user_info_about)
    TextView editUserInfoAboutText;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);

        intent = getIntent();

        initView();
        initListener();

    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar_edit_user_info).findViewById(R.id.toolbar_back);
        textView = toolbar.findViewById(R.id.toolbar_back_text_title);
        textViewRight = toolbar.findViewById(R.id.toolbar_back_text_right);
        textView.setText(intent.getStringExtra("title"));
        textViewRight.setText("保存");
        textView = toolbar.findViewById(R.id.toolbar_back_text_left);
        toolbarIcon = toolbar.findViewById(R.id.toolbar_back_icon);
        getEditUserInfoTitleText.setText(intent.getStringExtra("title"));
        editUserInfoText.setText(intent.getStringExtra("value"));
        editUserInfoAboutText.setText(intent.getStringExtra("about"));
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
        textViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent result = new Intent();
                result.putExtra("value", editUserInfoText.getText().toString());
                EditUserInfoActivity.this.setResult(intent.getIntExtra("op", 110), result);
                finish();
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }



}
