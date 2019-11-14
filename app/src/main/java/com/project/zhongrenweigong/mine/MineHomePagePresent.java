package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.mine.bean.MineInfoBean;
import com.project.zhongrenweigong.mine.bean.MineSystemBean;
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
 * 作者：Fuduo on 2019/11/1 11:32
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MineHomePagePresent extends XPresent<MineHomePageActivity> {
    
    public void getPersonalHomepage(String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.mineNetManager().getPersonalHomepage(requestBody)
                .compose(XApi.<MineInfoBean>getApiTransformer())
                .compose(XApi.<MineInfoBean>getScheduler())
                .compose(getV().<MineInfoBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineInfoBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(MineInfoBean mineInfoBean) {
                        if (mineInfoBean.getCode() == 200) {
                            getV().setData(mineInfoBean);
                        }else {
                            ToastManager.showLong(getV(), mineInfoBean.msg);
                        }
                    }
                });
    }

    public void getIndividualSystem(String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.mineNetManager().getIndividualSystem(requestBody)
                .compose(XApi.<MineSystemBean>getApiTransformer())
                .compose(XApi.<MineSystemBean>getScheduler())
                .compose(getV().<MineSystemBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineSystemBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(MineSystemBean mineSystemBean) {
                        if (mineSystemBean.getCode() == 200) {
                            getV().setMoralityData(mineSystemBean);
                        }else {
                            ToastManager.showLong(getV(), mineSystemBean.msg);
                        }
                    }
                });
    }
    
}
