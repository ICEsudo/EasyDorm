package com.easydorm.easydorm.annotation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.launch.activity.LoginActivity;
import com.easydorm.easydorm.Utils.SPUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import androidx.fragment.app.Fragment;


@Aspect
public class LoginRequiredAspect {

    private Context context;
    private Activity activity;


    @Pointcut("execution(* com.easydorm.easydorm..*(..))")
    public void allExecution() { }




    @Around("allExecution() && @annotation(loginRequired)")
    public void requireLogin(final ProceedingJoinPoint proceedingJoinPoint, final LoginRequired loginRequired) {
        Log.d("Annotation", ">>>>>>>>>>>LoginRequired");

        context = EasyDormApp.getContext();

        Object target = proceedingJoinPoint.getTarget();
        activity = null;
        if(target instanceof Activity) {
            activity = (Activity) target;
        } else if(target instanceof Fragment) {
            activity = ((Fragment) target).getActivity();
        }

        if(SPUtil.getUserInfo().getBoolean("isLogined", false)) {
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
