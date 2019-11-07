package com.project.zhongrenweigong.util;

import android.app.Activity;
import android.content.Context;

import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.mine.set.SetActivity;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.project.zhongrenweigong.currency.Constans.ADDRES;
import static com.project.zhongrenweigong.currency.Constans.ISTOURIST;

/**
 * 作者：Fuduo on 2019/11/6 10:42
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class LoginOutUtils {

    public static void loginOut(Context context){
        SharedPref.getInstance(context).remove(ADDRES);
        SharedPref.getInstance(context).remove(ISTOURIST);
        XCache xCache = new XCache.Builder(context).build();
        xCache.clear();
        ActivityManager screenManager = ActivityManager.getScreenManager();
        screenManager.popAllActivity();
    }

}
