package com.project.zhongrenweigong.business;

import com.project.zhongrenweigong.business.bean.WeiGongTestBean;
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
 * 作者：Fuduo on 2019/11/14 17:06
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class WeiGongTestPresent extends XPresent<WeiGongTestActivity> {
    public void getEvaluatingInfo(String addressName, String shopId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("addressName", addressName);
        stringMap.put("shopId", shopId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getEvaluatingInfo(requestBody)
                .compose(XApi.<WeiGongTestBean>getApiTransformer())
                .compose(XApi.<WeiGongTestBean>getScheduler())
                .compose(getV().<WeiGongTestBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<WeiGongTestBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(WeiGongTestBean weiGongTestBean) {
                        if (weiGongTestBean.getCode() == 200) {
                            getV().setData(weiGongTestBean);
                        } else {
                            ToastManager.showShort(getV(), weiGongTestBean.msg);
                        }
                    }
                });
    }
}
