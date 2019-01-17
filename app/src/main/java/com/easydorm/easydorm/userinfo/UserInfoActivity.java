package com.easydorm.easydorm.userinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.CacheUtil;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.HttpUtil;
import com.easydorm.easydorm.Utils.StringUtil;
import com.easydorm.easydorm.Utils.ToastUtil;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.entity.User;
import com.easydorm.easydorm.entity.UserInfo;
import com.easydorm.easydorm.entity.UserInfoBean;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.PostRequestInterface;


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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.easydorm.easydorm.Utils.Constants.Permission.*;


public class UserInfoActivity extends BaseActivity
        implements EasyPermissions.PermissionCallbacks,TakePhoto.TakeResultListener,InvokeListener {


    Toolbar toolbar;
    TextView textView;
    ImageView toolbarIcon;
    @BindView(R.id.user_info_avatar)
    CircleImageView avatarView;
    @BindView(R.id.user_info_item_nick_name_text) TextView nickNameText;
    @BindView(R.id.user_info_item_introduction_text) TextView introductionText;
    @BindView(R.id.user_info_item_dorm_address_text) TextView dormAdressText;
    @BindView(R.id.user_info_item_phone_number_text) TextView phoneText;
    @BindView(R.id.user_info_item_email_text) TextView emailText;

    TakePhoto takePhoto;
    private CropOptions cropOptions;
    private CompressConfig compressConfig;
    private Uri imageUri;

    private UserInfoBean userInfoBean;
    private boolean editable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);

        Intent intent = getIntent();
        int uId = intent.getIntExtra("uId", EasyDormApp.getUser().getUserInfo().getuId());
        editable = uId == EasyDormApp.getUser().getUserInfo().getuId();

        initView();
        initListener();
        if(editable) initPrivateListener();
        initData();

        getUserInfo(this, uId);

    }

    private void initView() {
        UserInfo userInfo = EasyDormApp.getUser().getUserInfo();
        toolbar = findViewById(R.id.toolbar_user_info).findViewById(R.id.toolbar_back);
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

    private void initPrivateListener() {
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
        updateUserInfo(this, iconPath);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        return PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
    }


    public void getUserInfo(Context context, int uId) {
        GetRequestInterface getRequestInterface = HttpUtil.getGetRequestInterface();
        Call<BaseResponse> call = getRequestInterface.getUserInfo(EasyDormApp.getUser().getUserToken().getAccessToken(), uId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                if(baseResponse != null && (userInfoBean = baseResponse.getExtend().getUserInfo()) != null) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (userInfoBean.getNickname() != null && !userInfoBean.getNickname().equals("")) {
                                nickNameText.setText(userInfoBean.getNickname());
                            }
                            if (userInfoBean.getPicture() != null && !userInfoBean.getPicture().equals("")) {
                                Glide.with(context).load(Constants.Url.baseUrl + userInfoBean.getPicture()).into(avatarView);
                            }
                            if (userInfoBean.getIntroduction() != null && !userInfoBean.getIntroduction().equals("")) {
                                introductionText.setText(userInfoBean.getIntroduction());
                            }
                            if (userInfoBean.getDormaddress() != null && !userInfoBean.getDormaddress().equals("")) {
                                if (userInfoBean.isDormaddressvisiable()) {
                                    dormAdressText.setText(userInfoBean.getDormaddress());
                                } else {
                                    dormAdressText.setText("保密");
                                }
                            }
                            if (userInfoBean.getPhonenumber() != null && !userInfoBean.getPhonenumber().equals("")) {
                                if (userInfoBean.isPhonevisiable()) {
                                    phoneText.setText(userInfoBean.getPhonenumber());
                                } else {
                                    phoneText.setText("保密");
                                }
                            }
                            if (userInfoBean.getEmail() != null && !userInfoBean.getEmail().equals("")) {
                                if (userInfoBean.isEmailvisiable()) {
                                    emailText.setText(userInfoBean.getEmail());
                                } else {
                                    emailText.setText("保密");
                                }
                            }
                        }
                    });
                } else {
                    ToastUtil.toast("服务器异常");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    public void updateUserInfo(Context context,  String iconPath) {
        User user = EasyDormApp.getUser();

        PostRequestInterface postRequestInterface = HttpUtil.getPostRequestInterface();

        if(iconPath == null) {
            iconPath = user.getUserInfo().getAvatarPath();
        }
        File file = new File(iconPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        String fileName = StringUtil.makeFileName(user.getUserInfo().getUserId(), file.getName());
        MultipartBody.Part avatar = MultipartBody.Part.createFormData("picture", fileName, requestBody);

        Map<String, RequestBody> mp = new HashMap<>();
        mp.put("nickName", RequestBody.create(null, ""));
        mp.put("phoneNumber", RequestBody.create(null, ""));
        mp.put("email", RequestBody.create(null, ""));
        mp.put("dormAddress", RequestBody.create(null, ""));
        mp.put("introduction", RequestBody.create(null, ""));

        Call<BaseResponse> call = postRequestInterface.updateUserInfo(user.getUserToken().getAccessToken(), mp, avatar);

        final String finalIconPath = iconPath;
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                if(baseResponse != null) {
                    ToastUtil.toast(baseResponse.getMessage());
                    if(response.code() == 200 && baseResponse.getCode() == 1) {
                        EasyDormApp.getUser().getUserInfo().setAvatarUrl(Constants.Url.baseUrl+"/static/"+fileName).setAvatarPath(finalIconPath);
//                        user.getUserInfo().setNickName(editNickName.getText().toString());
                        CacheUtil.clearGlideAllCache(UserInfoActivity.this);
                        Glide.with(context).load(finalIconPath).into(avatarView);
                    }
                } else {
                    ToastUtil.toast("服务器异常");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }



}
