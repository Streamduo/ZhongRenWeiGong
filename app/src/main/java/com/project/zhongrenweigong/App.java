package com.project.zhongrenweigong;

import android.app.Application;
import android.content.Context;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 作者：Fuduo on 2019/10/14 16:09
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class App extends Application {

    public static Context mContext;
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        initXNet();
    }

    private void initXNet() {
        XApi.registerProvider(new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

        });
    }

    public static Context getContext() {
        return mContext;
    }

    public static App getInstance() {
        return app;
    }
}
