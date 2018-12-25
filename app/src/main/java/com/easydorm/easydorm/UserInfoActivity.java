package com.easydorm.easydorm;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

public class UserInfoActivity extends BaseActivity {


    Toolbar toolbar;
    TextView textView;
    ImageView toolbarIcon;
    private CircleImageView avatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();
        initListener();
        initData();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar_user_info).findViewById(R.id.toolbar_back);
        avatarView = findViewById(R.id.user_info_avatar);
        textView = toolbar.findViewById(R.id.toolbar_back_text_title);
        textView.setText("个人信息");
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

    private void initData() {

    }



}
