package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.mine.bean.IntegralCompensationBean;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.HomeMainApi;
import com.project.zhongrenweigong.util.GsonProvider;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Fuduo on 2019/11/28 10:53
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MyIntegralCompensationPresent extends XPresent<MyIntegralCompensationActivity> {

    public void getCompensationRecords(int currentPage, String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getCompensationRecords(requestBody)
                .compose(XApi.<IntegralCompensationBean>getApiTransformer())
                .compose(XApi.<IntegralCompensationBean>getScheduler())
                .compose(getV().<IntegralCompensationBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<IntegralCompensationBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(IntegralCompensationBean integralCompensationBean) {
                        if (integralCompensationBean.getCode() == 200) {
                            getV().setData(integralCompensationBean);
                        } else {
                            ToastManager.showShort(getV(), integralCompensationBean.msg);
                        }
                    }
                });
    }

    public void getIntegralSubsidiary(int currentPage, String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getIntegralSubsidiary(requestBody)
                .compose(XApi.<IntegralCompensationBean>getApiTransformer())
                .compose(XApi.<IntegralCompensationBean>getScheduler())
                .compose(getV().<IntegralCompensationBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<IntegralCompensationBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(IntegralCompensationBean integralCompensationBean) {
                        if (integralCompensationBean.getCode() == 200) {
                            getV().setData(integralCompensationBean);
                        } else {
                            ToastManager.showShort(getV(), integralCompensationBean.msg);
                        }
                    }
                });
    }
}
