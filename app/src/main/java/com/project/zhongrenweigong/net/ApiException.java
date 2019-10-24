package com.project.zhongrenweigong.net;

/**
 * Created by k on 2017/6/28.
 */

public class ApiException extends RuntimeException {

    public static final ApiException UNKNOWN = new ApiException(-1, "当前网络环境不稳定，请稍后重试","");

    public int code;
    public String msg;
    public String url;

    public ApiException(int code, String msg,String url) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.url = url;
    }
    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
