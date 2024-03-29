package com.project.zhongrenweigong.base;

import android.support.annotation.Keep;

import cn.droidlover.xdroidmvp.net.IModel;

@Keep
public class BaseModel implements IModel {

    public int code;
    public String msg;
    public String encryptionData;
    public int pageSize;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
