package com.project.zhongrenweigong.currency;

import cn.droidlover.xdroidmvp.XDroidConf;

public class Constans {

    /**
     * shardpreferences 文件名
     */
    public static final String FIRST_OPEN = "first_open";
    public static final String ADDRES = "address";

    /**
     * cache 文件名
     */
    public static final String USERACCENT = "user_accent";

    public static final String MAP_AK = "XsMT3FsWTEc3X66GGUVTLSYYBksd922y";

    private static String MAP_SAFE_NUM_RELEASE = "0E:09:F8:AD:EB:C2:FB:06:E0:1D:1B:D1:BF:3A:3D:3C:3E:EB:6F:2C;com.project.zhongrenweigong";
    private static String MAP_SAFE_NUM_DEBUG = "FE:6A:F7:CD:1E:F0:AF:C1:96:F5:9F:18:42:D3:0A:3A:A1:47:FD:CF;com.project.zhongrenweigong";

    public static final String MAP_SAFE_NUM = (XDroidConf.DEV ? MAP_SAFE_NUM_DEBUG : MAP_SAFE_NUM_RELEASE);
}
