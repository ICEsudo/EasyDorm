package com.easydorm.easydorm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.Utils.StringUtil;
import com.easydorm.easydorm.Utils.ToastUtil;
import com.easydorm.easydorm.entity.User;
import com.easydorm.easydorm.http.PostRequestInterface;
import com.orhanobut.logger.Logger;


import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.easydorm.easydorm.Utils.Constants.IntentResult.TAKE_PHOTO;
import static com.easydorm.easydorm.Utils.Constants.Permission.*;


public class UserInfoActivity extends BaseActivity
        implements EasyPermissions.PermissionCallbacks,TakePhoto.TakeResultListener,InvokeListener {


    Toolbar toolbar;
    TextView textView;
    ImageView toolbarIcon;
    private CircleImageView avatarView;

    TakePhoto takePhoto;
    private CropOptions cropOptions;
    private CompressConfig compressConfig;
    private Uri imageUri;

    private InvokeParam invokeParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ActivityCollector.addActivity(this);

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
        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] items = {"拍照选取", "从相册选择", "取消"};
                AlertDialog.Builder listDialog = new AlertDialog.Builder(UserInfoActivity.this);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if(EasyPermissions.hasPermissions(UserInfoActivity.this, Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    takePhotoFromCamera();
                                } else {
                                    EasyPermissions.requestPermissions(UserInfoActivity.this,
                                            "易舍需要使用相机的权限,您尚未授权,点击确认开始授权",
                                            CAMERA_AND_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                }
                                break;
                            case 1:
                                if(EasyPermissions.hasPermissions(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    takePhotoFromGallery();
                                } else {
                                    EasyPermissions.requestPermissions(UserInfoActivity.this,
                                            "易舍需要读取相册的权限,您尚未授权,点击确认开始授权",
                                            WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                }
                                break;
                        }
                    }
                });
                listDialog.show();
            }
        });
    }


    private void initData() {
        takePhoto = getTakePhoto();
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        compressConfig=new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig,true);
    }

    private Uri getImageCropUri() {
        File file=new File(Environment.getExternalStorageDirectory(),
                "/EasyDorm/" + EasyDormApp.getCurUserId() + "/avatar/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }


    void takePhotoFromCamera() {
        imageUri = getImageCropUri();
        takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);
    }


    void takePhotoFromGallery() {
        imageUri = getImageCropUri();
        takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
    }


    public TakePhoto getTakePhoto() {
        if(takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                takePhotoFromGallery();
                break;
            case CAMERA_AND_STORAGE:
                takePhotoFromCamera();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            default:
                ToastUtil.toast("已拒绝");
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        String iconPath = result.getImage().getOriginalPath();
        uploadAvatar(this, iconPath);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam=invokeParam;
        }
        return type;
    }

    public void uploadAvatar(Context context,  String iconPath) {

        User user = EasyDormApp.getUser();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseImage)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequestInterface postRequestInterface = retrofit.create(PostRequestInterface.class);

        File file = new File(iconPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        String fileName = StringUtil.makeFileName(user.getUserInfo().getUserId(), file.getName());

        MultipartBody.Part avatar = MultipartBody.Part.createFormData("file", fileName, requestBody);

        Call<ResponseBody> call = postRequestInterface.uploadFile(user.getUserToken().getAccessToken(), avatar);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null) {
                    try {
                        ToastUtil.toast(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.toast("服务器异常");
                }
                if(response.code() == 200) {
                    EasyDormApp.getUser().getUserInfo().setAvatarUrl(Constants.Url.baseImage+"/static/"+fileName).setAvatarPath(iconPath);
                    Glide.with(context).load(iconPath).into(avatarView);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtil.toast("请求失败");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String avatarPath = EasyDormApp.getUser().getUserInfo().getAvatarPath();
        String avatarUrl = EasyDormApp.getUser().getUserInfo().getAvatarUrl();
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.avatar)
                .error(R.mipmap.avatar);
        if(avatarPath != null && !avatarPath.equals("")) {
            Glide.with(this).load(avatarPath).apply(options).into(avatarView);
        } else if(avatarUrl != null && !avatarUrl.equals("")) {
            Glide.with(this).load(avatarUrl).apply(options).into(avatarView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
