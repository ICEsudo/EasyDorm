package com.easydorm.easydorm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

import com.easydorm.easydorm.Utils.MD5Util;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.PostRequestInterface;
import com.easydorm.easydorm.http.URLManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private EditText idEditText, pwEditText, pw2EditText;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initListener();

    }


    private void initView() {
        idEditText = findViewById(R.id.register_edit_text_id);
        pwEditText = findViewById(R.id.register_edit_text_pw);
        pw2EditText = findViewById(R.id.register_edit_text_pw2);
        registerButton = findViewById(R.id.register_button_register);

    }


    private void initListener() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                String pw2 = pw2EditText.getText().toString();
                if(checkInput(id, pw, pw2)) {
                    register(RegisterActivity.this, id, MD5Util.encodeToHex(pw));
                }
            }
        });

    }

    public boolean checkInput(String id, String pw, String pw2) {
        if(!id.equals("")) {
            if(!pw.equals("")) {
                if(!pw2.equals("")) {
                    if(pw.equals(pw2)) {
                        return true;
                    } else {
                        Toast.makeText(this, "两次输入的密码不一样呀", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请设置密码", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请设置账号", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void register(final Context context, String username, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLManager.baseUrl)
                .build();

        PostRequestInterface postRequestInterface = retrofit.create(PostRequestInterface.class);

        Call<ResponseBody> call = postRequestInterface.register(username, password);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
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


}
