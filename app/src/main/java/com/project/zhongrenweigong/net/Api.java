package com.project.zhongrenweigong.net;

import com.project.zhongrenweigong.BuildConfig;
import com.project.zhongrenweigong.login.LoginNetManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.internal.Utils;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by liyalong on 2017/12/20.
 */

public class Api {

    //测试环境
    private static String TEST_SERVER = "http://192.168.3.20:9999/";
    //正式环境
    private static String RELEASE_SERVER = "http://api.wakeyoga.com/";

    public static final String BASE_PATH = (BuildConfig.BUILD_TYPE.equals("debug") ? TEST_SERVER : RELEASE_SERVER);
    private static LoginNetManager loginNetManager;

    public static LoginNetManager loginNetManager() {
        if (loginNetManager == null) {
            synchronized (Api.class) {
                if (loginNetManager == null) {
                    loginNetManager = XApi.getInstance().getRetrofit(BASE_PATH, true).create(LoginNetManager.class);
                }
            }
        }
        return loginNetManager;
    }

    public static Map<String, String> getBasicParamsUidAndToken() {

        Map<String, String> params = new HashMap<>();
//        params.put("uid",String.valueOf(userHelper.getId()));
//        params.put("tok", userHelper.getToken());
//        params.put("channel", Market.getMarket(Utils.getApp()));
//        params.put("v", ApiConst.VERSION_NEW);
//        params.put("site", ApiConst.SITE);
//        params.put("vapp", VersionUtils.getCurrentVersionName(Utils.getApp()));
        return params;
    }

}
