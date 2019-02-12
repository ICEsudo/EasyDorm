package com.easydorm.easydorm.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.Utils.HttpUtil;
import com.easydorm.easydorm.Utils.ToastUtil;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.entity.Comment;
import com.easydorm.easydorm.entity.ExtendBean;
import com.easydorm.easydorm.http.PostRequestInterface;
import com.easydorm.easydorm.posts.activity.PostDetailActivity;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InputDialogFragment extends DialogFragment {

    private Dialog dialog;
    @BindView(R.id.dialog_input_edit_text) EditText inputText;
    @BindView(R.id.dialog_input_send_button) Button sendButton;


    private int backType = 1;
    private int pId = -1, tId = -1, pType = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_input_layout, container, false);
        ButterKnife.bind(this, view);

        initListener();

        return view;
    }


    private void initListener() {
        inputText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) clearFocus();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBack();
            }
        });
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Activity activity = getActivity();
        if(activity != null ) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        }

        activity = null;
        return super.onCreateDialog(savedInstanceState);

    }



    private void sendBack() {
        String content = inputText.getText().toString();
        if(content.equals("")) {
            ToastUtil.toast("请输入评论");
            return;
        }
        PostRequestInterface postRequestInterface = HttpUtil.getPostRequestInterface();
        Call<BaseResponse> call;
        switch (backType) {
            case 1:
                call = postRequestInterface.createBack(EasyDormApp.getUser().getUserToken().getAccessToken(), pId, content, 1);
                break;
            case 2:
                call = postRequestInterface.createBack(EasyDormApp.getUser().getUserToken().getAccessToken(), tId, pId, content, 2);
                break;
            default:
                call = postRequestInterface.createBack(EasyDormApp.getUser().getUserToken().getAccessToken(), tId, pId, content, 3, pType);

        }
        if(call != null) {
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    BaseResponse baseResponse = response.body();
                    if(baseResponse != null) {
                        if(baseResponse.getCode() == 1) {
                            ToastUtil.toast("评论成功");
                            Activity activity = getActivity();
                            if(activity != null) {
                                ((PostDetailActivity) activity).load();
                            }
                            inputText.setText("");
                            clearFocus();
                        } else {
                            ToastUtil.toast("评论失败");
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

    }


    public void setBackType(int backType) {
        this.backType = backType;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void settId(int tId) {
        this.tId = tId;
        setpId(tId);
    }

    public void setpType(int pType) {
        this.pType = pType;
    }

    public boolean hasFocus() {
        return inputText.hasFocus();
    }

    public void getFocus(Comment comment) {
        setpId(comment.getForumBack().getBId());
        inputText.setHint("回复"+comment.getForumBack().getNickName());
        inputText.requestFocus();
        Activity activity = getActivity();
        if(activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(inputText, 0);
        }
        setBackType(2);
    }

    public void clearFocus() {
        inputText.clearFocus();
        Activity activity = getActivity();
        if(activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
        }
        setpId(tId);
        inputText.setHint("回复帖子");
        setBackType(1);
    }


}
