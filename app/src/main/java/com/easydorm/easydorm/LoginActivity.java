package com.easydorm.easydorm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.easydorm.easydorm.Utils.MD5Util;
import com.easydorm.easydorm.Utils.SPUtil;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.URLManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {

    private EditText idEditText, pwEditText;
    private Button loginButton;
    private CheckBox rememberButton;
    private TextView forgetText;
    private SharedPreferences sp;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();
        initData();

    }

    private void initData() {
        sp = SPUtil.getUserInfo();
        rememberButton.setChecked(sp.getBoolean("rememberPassword", false));
        if(rememberButton.isChecked()) {
            idEditText.setText(sp.getString("userId", ""));
            pwEditText.setText(sp.getString("password", ""));
            spinner.setSelection(sp.getInt("level", 0));
        }
    }

    private void initView() {
        idEditText = findViewById(R.id.login_edit_text_id);
        pwEditText = findViewById(R.id.login_edit_text_pw);
        loginButton = findViewById(R.id.login_button_login);
        rememberButton = findViewById(R.id.login_button_remember);
        forgetText = findViewById(R.id.login_text_forget);
        spinner = findViewById(R.id.login_spinner);
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
        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                Toast.makeText(LoginActivity.this, "慢慢想别急", Toast.LENGTH_SHORT).show();
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


    public void login(final Context context, String id, String pw, int level) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLManager.baseUrl)
                .build();

        GetRequestInterface getRequestInterface = retrofit.create(GetRequestInterface.class);

        Call<ResponseBody> call = getRequestInterface.login(id, pw, level);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body() == null) {
                        Toast.makeText(context, "服务器异常", Toast.LENGTH_SHORT).show();
                    } else {
                        String str = new String(response.body().bytes());
                        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
                        Toast.makeText(context, jsonObject.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        if(jsonObject.get("code").getAsInt() == 1) {
                            //TODO

                            save();

                            finish();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void save() {
        String id = idEditText.getText().toString();
        String pw = pwEditText.getText().toString();
        int userType = spinner.getSelectedItemPosition();
        sp.edit().putString("userId", id)
                .putString("password", pw)
                .putInt("userType", userType)
                .putBoolean("rememberPassword", rememberButton.isChecked())
                .putString("accessToken", "fakeTokenForTest")
                .apply();
    }
}

