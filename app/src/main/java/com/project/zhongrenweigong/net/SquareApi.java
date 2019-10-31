package com.project.zhongrenweigong.net;

import com.project.zhongrenweigong.home.HomeNetManager;
import com.project.zhongrenweigong.square.SquareNetManager;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.net.XApi;

public class SquareApi {

    //测试环境
    private static String TEST_SERVER = "http://192.168.3.20:1111/";
    //正式环境
    private static String RELEASE_SERVER = "http://api.wakeyoga.com/";

    public static final String BASE_PATH = (XDroidConf.DEV ? TEST_SERVER : RELEASE_SERVER);
    private static SquareNetManager squareNetManager;

    public static SquareNetManager squareNetManager() {
        if (squareNetManager == null) {
            synchronized (SquareApi.class) {
                if (squareNetManager == null) {
                    squareNetManager = XApi.getInstance().getRetrofit(BASE_PATH, true).create(SquareNetManager.class);
                }
            }
        }
        return squareNetManager;
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
