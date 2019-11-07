package com.project.zhongrenweigong.net;

import com.project.zhongrenweigong.home.HomeNetManager;
import com.project.zhongrenweigong.home.MessageNetManager;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.net.XApi;

public class HomeMainApi {

    //测试环境
    private static String TEST_SERVER = "http://192.168.3.20:9999/";
    //正式环境
    private static String RELEASE_SERVER = "http://api.wakeyoga.com/";

    public static final String BASE_PATH = (XDroidConf.DEV ? TEST_SERVER : RELEASE_SERVER);
    public static final String MESSAGE_BASE_PATH = "http://192.168.3.20:2222/";
    private static HomeNetManager homeNetManager;

    public static HomeNetManager homeNetManager() {
        if (homeNetManager == null) {
            synchronized (HomeMainApi.class) {
                if (homeNetManager == null) {
                    homeNetManager = XApi.getInstance().getRetrofit(BASE_PATH, true).create(HomeNetManager.class);
                }
            }
        }
        return homeNetManager;
    }

    private static MessageNetManager messageNetManager;

    public static MessageNetManager messageNetManager() {
        if (messageNetManager == null) {
            synchronized (HomeMainApi.class) {
                if (messageNetManager == null) {
                    messageNetManager = XApi.getInstance().getRetrofit(MESSAGE_BASE_PATH, true).create(MessageNetManager.class);
                }
            }
        }
        return messageNetManager;
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
