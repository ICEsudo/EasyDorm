package com.easydorm.easydorm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.easydorm.easydorm.annotation.LoginRequired;

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

public class UserInfoActivity extends BaseActivity
        implements TakePhoto.TakeResultListener, InvokeListener {

    private InvokeParam invokeParam;

    private TakePhoto takePhoto;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig;  //压缩参数
    private Uri imageUri;  //图片保存路径


    private Toolbar toolbar;
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
        toolbar.setTitle("返回");
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               imageUri = getImageCropUri();
               takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
            }
        });
    }

    private void initData() {
        //获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        compressConfig=new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig,true);  //设置为需要压缩
    }

    private Uri getImageCropUri() {
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto==null) {
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.d("photo", "success");
        String iconPath = result.getImage().getOriginalPath();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.d("photo", "failed");
        Toast.makeText(this, "Error:" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        Log.d("photo", "cancel");
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        Log.d("photo", "invoke");
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("photo", "result");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }


}
