package com.project.zhongrenweigong.login;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.login.bean.LoginResponseBean;
import com.project.zhongrenweigong.net.Api;
import com.project.zhongrenweigong.util.AesUtil;
import com.project.zhongrenweigong.util.GsonProvider;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

import static cn.droidlover.xdroidmvp.net.NetError.OtherError;

/**
 * 作者：Fuduo on 2019/10/17 11:22
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class LoginPresent extends XPresent<LoginActivity> {

    public void login(String loginAddr, String loginIp,
                      String loginType, String mbPassword, String mbPhone) {
        Map<String, String> stringMap = Api.getBasicParamsUidAndToken();
        stringMap.put("loginAddr", loginAddr);
        stringMap.put("loginIp", loginIp);
        stringMap.put("loginType", loginType);
        stringMap.put("mbPassword", mbPassword);
        stringMap.put("mbPhone", mbPhone);
        String body = GsonProvider.gson.toJson(stringMap);

        String encode3DesBody = null;
        try {
            encode3DesBody = AesUtil.aesEncrypt(body, AesUtil.ps);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/text"),
//                encode3DesBody);
        Api.loginNetManager().login(encode3DesBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        if (error.getType() == OtherError) {
                            ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                        }
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.getCode() == 200) {
                            ToastManager.showShort(getV(), "登录成功");
                        }
                        String data = baseModel.encryptionData;
                        if (data != null && !data.equals("")) {
                            String json = AesUtil.aesDecrypt(data, AesUtil.ps);
                            LoginMsg loginMsg = GsonProvider.gson.fromJson(json, LoginMsg.class);
                            String mbId = loginMsg.mbId;
                        }
                    }
                });
    }

}
