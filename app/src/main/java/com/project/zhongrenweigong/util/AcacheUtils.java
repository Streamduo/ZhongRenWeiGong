package com.project.zhongrenweigong.util;

import android.app.Activity;
import android.content.Context;

import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.home.MainActivity;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.set.SetActivity;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.project.zhongrenweigong.currency.Constans.USERACCENT;

/**
 * 作者：Fuduo on 2019/10/28 10:05
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class AcacheUtils {

    private static volatile AcacheUtils instance = null;
    private Context context;

    private AcacheUtils(){}

    private AcacheUtils(Context context){
        this.context = context;
    }

    public static AcacheUtils getInstance(Context context){
        if (instance == null){
            synchronized (AcacheUtils.class){
                if (instance == null){
                    instance = new AcacheUtils(context);
                }
            }
        }
        return  instance;
    }

    public LoginMsg getUserAccent(){
        XCache xCache = new XCache.Builder(context).build();
        LoginMsg loginMsg = (LoginMsg) xCache.getObject(USERACCENT);
        return loginMsg;
    }

}
