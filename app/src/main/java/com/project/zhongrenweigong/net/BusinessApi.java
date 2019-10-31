package com.project.zhongrenweigong.net;

import com.project.zhongrenweigong.business.BusinessNetManager;
import com.project.zhongrenweigong.home.HomeNetManager;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.net.XApi;

public class BusinessApi {

    //测试环境
    private static String TEST_SERVER = "http://192.168.3.20:9090/";
    //正式环境
    private static String RELEASE_SERVER = "http://api.wakeyoga.com/";

    public static final String BASE_PATH = (XDroidConf.DEV ? TEST_SERVER : RELEASE_SERVER);
    private static BusinessNetManager businessNetManager;
    private static HomeNetManager homeNetManager;

    public static BusinessNetManager businessNetManager() {
        if (businessNetManager == null) {
            synchronized (BusinessApi.class) {
                if (businessNetManager == null) {
                    businessNetManager = XApi.getInstance().getRetrofit(BASE_PATH, true).create(BusinessNetManager.class);
                }
            }
        }
        return businessNetManager;
    }

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
