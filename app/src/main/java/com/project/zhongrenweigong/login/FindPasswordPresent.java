package com.project.zhongrenweigong.login;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.currency.event.RefreshMineEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.AES;
import com.project.zhongrenweigong.util.GsonProvider;
import com.project.zhongrenweigong.util.XCache;
import com.project.zhongrenweigong.view.LoadingDialog;


import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Fuduo on 2019/10/24 10:21
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class FindPasswordPresent extends XPresent<FindPasswordActivity> {

    public void getVerification(String mcPhone) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mcPhone", mcPhone);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.loginNetManager().getVerification(requestBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "发送验证码失败，请检查网络设置");
                        getV().sendEmsFail();
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        ToastManager.showShort(getV(), baseModel.msg);
                        getV().sendEmsSuccess();
                    }
                });
    }

    public void findPwd(String mcPhone, String verification,
                      String newPwd, String checkPwd) {
        LoadingDialog.show(getV());
        Map<String, String> stringMap = LoginApi.getBasicParamsUidAndToken();
        stringMap.put("mcPhone", mcPhone);
        stringMap.put("verification", verification);
        stringMap.put("newPwd", newPwd);
        stringMap.put("checkPwd", checkPwd);
        String body = GsonProvider.gson.toJson(stringMap);
        final AES aes = new AES();
        String encode3DesBody = null;
        try {
            byte[] utf8s = body.getBytes("UTF8");
            encode3DesBody = aes.encrypt(utf8s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginApi.loginNetManager().findPwd(encode3DesBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        LoadingDialog.dismiss(getV());
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        LoadingDialog.dismiss(getV());
                        if (baseModel.getCode() == 200) {
                            ToastManager.showShort(getV(), baseModel.getMsg());
                        }
                        getV().finish();
                    }
                });
    }

}
