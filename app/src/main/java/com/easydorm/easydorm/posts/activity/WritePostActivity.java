package com.easydorm.easydorm.posts.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.HttpUtil;
import com.easydorm.easydorm.Utils.ToastUtil;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.http.GetRequestInterface;
import com.easydorm.easydorm.http.PostRequestInterface;

import static com.easydorm.easydorm.Utils.StringUtil.checkNotNull;

public class WritePostActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView, rightTextView;
    ImageView toolbarIcon;

    @BindView(R.id.write_post_edit_text)
    EditText writePostEditText;
    @BindView(R.id.write_title_edit_text)
    EditText writeTitleEditText;
    @BindView(R.id.number_of_text)
    TextView numberOfText;
    @BindView(R.id.write_post_switch)
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);

        initView();
        initListener();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar_write_post).findViewById(R.id.toolbar_back);
        textView = toolbar.findViewById(R.id.toolbar_back_text_title);
        textView.setText("发布帖子");
        textView = toolbar.findViewById(R.id.toolbar_back_text_left);
        rightTextView = toolbar.findViewById(R.id.toolbar_back_text_right);
        rightTextView.setText("发布");
        toolbarIcon = toolbar.findViewById(R.id.toolbar_back_icon);
        numberOfText.setText(String.valueOf(writePostEditText.getText().toString().length())+"字");
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
        rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = writeTitleEditText.getText().toString();
                String content = writePostEditText.getText().toString();
                if(checkInput(title, content)) {
                    ToastUtil.toast("正在发布");
                    createNewPost(title, content, aSwitch.isChecked()?2:1);
                }
            }
        });
        writeTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 30) {
                    numberOfText.setText("标题长度超出限制");
                    numberOfText.setTextColor(Color.rgb(255, 0, 0));
                } else {
                    numberOfText.setText(String.valueOf(writePostEditText.getText().toString().length())+"字");
                    numberOfText.setTextColor(Color.rgb(96, 96, 96));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        writePostEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                numberOfText.setText(String.valueOf(s.length())+"字");
                numberOfText.setTextColor(Color.rgb(96, 96, 96));
                if(s.length() > 20000) {
                    numberOfText.setText("文本长度超出限制");
                    numberOfText.setTextColor(Color.rgb(255, 0, 0));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private boolean checkInput(String title, String content) {
        if(checkNotNull(title)) {
            if(title.length() > 30) {
                ToastUtil.toast("标题长度超出限制");
            } else if(checkNotNull(content)) {
                if(content.length() > 20000) {
                    ToastUtil.toast("文本长度超出限制");
                } else {
                    return true;
                }
            } else {
                ToastUtil.toast("请输入正文");
            }
        } else {
            ToastUtil.toast("请输入标题");
        }
        return false;
    }


    private void createNewPost(String title, String content, int type) {
        PostRequestInterface postRequestInterface = HttpUtil.getPostRequestInterface();
        Call<BaseResponse> call = postRequestInterface.createTopic(EasyDormApp.getUser().getUserToken().getAccessToken(),
                title, content, type);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null) {
                    if(response.body().getCode() == 1) {
                        setResult(233);
                        finish();
                    } else {
                        ToastUtil.toast("发布失败");
                    }
                } else {
                    ToastUtil.toast("服务器异常");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                ToastUtil.toast("请求失败");
            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
