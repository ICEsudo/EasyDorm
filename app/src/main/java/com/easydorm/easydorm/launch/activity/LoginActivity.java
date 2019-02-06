package com.easydorm.easydorm.launch.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.Utils.HttpUtil;
import com.easydorm.easydorm.Utils.ImageUtil;
import com.easydorm.easydorm.Utils.MD5Util;
import com.easydorm.easydorm.Utils.NetWorkUtil;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.Utils.ToastUtil;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.entity.User;
import com.easydorm.easydorm.entity.UserInfo;
import com.easydorm.easydorm.entity.UserToken;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_edit_text_id) EditText idEditText;
    @BindView(R.id.login_edit_text_pw) EditText pwEditText;
    @BindView(R.id.login_button_login) Button loginButton;
    @BindView(R.id.login_button_remember) CheckBox rememberButton;
    @BindView(R.id.login_text_remember) TextView rememberText;
    @BindView(R.id.login_text_forget) TextView forgetText;
    @BindView(R.id.login_spinner) Spinner spinner;
    @BindView(R.id.login_button_help) ImageView helpImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);

        initView();
        initListener();
        initData();

    }

    private void initData() {
        NetWorkUtil.checkNetWork();
        UserInfo userInfo = EasyDormApp.getUser().getUserInfo();
        rememberButton.setChecked(userInfo.isPWRemembered());
        if(rememberButton.isChecked()) {
            idEditText.setText(userInfo.getUserId());
            pwEditText.setText(userInfo.getPassword());
            spinner.setSelection(userInfo.getUserType());
        }
    }

    private void initView() {
        TextView textView = findViewById(R.id.login_text);
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/login.ttf");
        textView.setTypeface(tf);
    }

    private void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                int level = spinner.getSelectedItemPosition();
                if(checkInput(id, pw)) {
                    login(LoginActivity.this, id, MD5Util.encodeToHex(pw), level);
                }
            }
        });
        rememberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rememberButton.isChecked()) rememberButton.setChecked(false);
                else rememberButton.setChecked(true);
            }
        });
        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                Toast.makeText(LoginActivity.this, "慢慢想别急", Toast.LENGTH_SHORT).show();
            }
        });
        helpImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HelpActivity.class));
            }
        });
    }

    public boolean checkInput(String id, String pw) {
        if(!id.equals("")) {
            if(!pw.equals("")) {
                return true;
            } else {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public void login(final Context context, String id, String pw, final int level) {

        Retrofit retrofit = HttpUtil.getRetrofit(Constants.Url.baseUrl, null, GsonConverterFactory.create());
        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);
        Call<BaseResponse> call = getRequestInterface.login(id, pw, level);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                if(baseResponse == null) {
                    ToastUtil.toast("服务器异常");
                } else {
//                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if(baseResponse.getCode() == 1) {
                        ToastUtil.toast("登录成功");
                        String userId = idEditText.getText().toString();
                        String refreshToken = response.headers().get("refresh_token");
                        String accessToken = response.headers().get("access_token");
                        EasyDormApp.setCurUserId(userId);
                        EasyDormApp.setUser(new User())
                                .setUserToken(new UserToken(accessToken, refreshToken))
                                .setUserInfo(new UserInfo(level));
                        EasyDormApp.getUser().getUserInfo()
                                .setUserId(userId)
                                .setuId(baseResponse.getExtend().getuId())
                                .setPassword(pwEditText.getText().toString())
                                .setLogined(true)
                                .setPWRemembered(rememberButton.isChecked());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        ToastUtil.toast("登录失败");
                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                ToastUtil.toast("请求失败");
                t.printStackTrace();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

