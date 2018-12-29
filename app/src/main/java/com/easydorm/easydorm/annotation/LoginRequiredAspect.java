package com.easydorm.easydorm.annotation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.Utils.Constants;
import com.easydorm.easydorm.entity.BaseResponse;
import com.easydorm.easydorm.http.PostRequestInterface;
import com.easydorm.easydorm.http.TokenInterceptor;
import com.easydorm.easydorm.launch.activity.LoginActivity;
import com.easydorm.easydorm.Utils.SPUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.IOException;

import androidx.fragment.app.Fragment;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Aspect
public class LoginRequiredAspect {

    private Context context = EasyDormApp.getContext();
    private Activity activity;


    @Pointcut("execution(* com.easydorm.easydorm..*(..))")
    public void allExecution() { }




    @Around("allExecution() && @annotation(loginRequired)")
    public void requireLogin(final ProceedingJoinPoint proceedingJoinPoint, final LoginRequired loginRequired) {
        Log.d("Annotation", ">>>>>>>>>>>LoginRequired");

        Object target = proceedingJoinPoint.getTarget();
        activity = null;
        if(target instanceof Activity) {
            activity = (Activity) target;
        } else if(target instanceof Fragment) {
            activity = ((Fragment) target).getActivity();
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Url.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        PostRequestInterface postRequestInterface = retrofit.create(PostRequestInterface.class);

        Call<BaseResponse> call = postRequestInterface.checkToken(EasyDormApp.getUser().getUserToken().getAccessToken());

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.body() == null) {
                    Log.d("token", "body is null");
                } else {
                    Log.d("token", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

        if(EasyDormApp.getUser().getUserInfo().isLogined()) {
//            if(EasyDormApp.getUser().getToken().checkToken()) {
                try {
                    proceedingJoinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
//            } else {
//                Toast.makeText(context, "登录信息已过期", Toast.LENGTH_SHORT).show();
//                if (activity != null) {
//                    Intent intent = new Intent(activity, LoginActivity.class);
//                    activity.startActivity(intent);
//                }
//            }
        } else {
            Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show();
            if (activity != null) {
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
            }
        }




    }





}
