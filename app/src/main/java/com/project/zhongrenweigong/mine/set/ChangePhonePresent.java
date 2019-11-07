package com.project.zhongrenweigong.mine.set;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.GsonProvider;
import com.project.zhongrenweigong.util.LoginOutUtils;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Fuduo on 2019/11/2 16:59
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ChangePhonePresent extends XPresent<ChangePhoneActivity> {


    public void getVerification(String mcPhone, final int type) {
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
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        ToastManager.showShort(getV(), baseModel.msg);
                        if (baseModel.getCode() == 200) {
                            getV().sendEmsSuccess(type);
                        }
                    }
                });
    }

    public void checkVerification(String mcId, String phone, String verification) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("phone", phone);
        stringMap.put("verification", verification);
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.mineNetManager().checkVerification(requestBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        ToastManager.showLong(getV(), baseModel.msg);
                        if (baseModel.getCode() == 200) {
                            getV().next();
                        }
                    }
                });
    }

    public void updatePhone(String mbId, String phone, String verification) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("phone", phone);
        stringMap.put("verification", verification);
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.mineNetManager().updatePhone(requestBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        ToastManager.showLong(getV(), baseModel.msg);
                        if (baseModel.getCode() == 200) {
                            LoginOutUtils.loginOut(getV());
                            Router.newIntent(getV()).putInt("isLoginOut",1)
                                    .to(LoginActivity.class).launch();
                            getV().finish();
                        }
                    }
                });
    }

}
