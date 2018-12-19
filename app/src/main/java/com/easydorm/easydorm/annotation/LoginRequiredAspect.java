package com.easydorm.easydorm.annotation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.LoginActivity;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import androidx.fragment.app.Fragment;

@Aspect
public class LoginRequiredAspect {

    private Context context;
    private Activity activity;
    private SharedPreferences sp;


    @Pointcut("execution(* com.easydorm.easydorm..*(..)) && @annotation(loginRequired)")
    public void aspect(LoginRequired loginRequired) { }


    @Around("aspect(loginRequired)")
    public void requiredLogin(final ProceedingJoinPoint proceedingJoinPoint, final LoginRequired loginRequired) {
        Log.d("Annotation", ">>>>>>>>>>>LoginRequired");

        context = EasyDormApp.getContext();
        sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        Object target = proceedingJoinPoint.getTarget();
        activity = null;
        if(target instanceof Activity) {
            activity = (Activity) target;
        } else if(target instanceof Fragment) {
            activity = ((Fragment) target).getActivity();
        }

        if(loginCheck() == 0) {
            Toast.makeText(context, loginRequired.toastStr(), Toast.LENGTH_SHORT).show();
            if (activity != null) {
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
            }
        } else {
            try {
                proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }

    private int loginCheck() {
        String token = sp.getString("accessToken", "");

        //TODO      request and check permission
        if(token!=null && !token.equals("")) {
            return 1;
        }

        return 0;
    }



}
