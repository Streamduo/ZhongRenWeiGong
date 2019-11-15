package com.project.zhongrenweigong.business;

import com.project.zhongrenweigong.business.bean.ShopHomePageBean;
import com.project.zhongrenweigong.net.BusinessApi;
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
 * 作者：Fuduo on 2019/10/21 14:35
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessHomePagePresent extends XPresent<BusinessHomePageActivity> {

    public void getShopHomepage(String shopId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("shopId", shopId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getShopHomepage(requestBody)
                .compose(XApi.<ShopHomePageBean>getApiTransformer())
                .compose(XApi.<ShopHomePageBean>getScheduler())
                .compose(getV().<ShopHomePageBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<ShopHomePageBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(ShopHomePageBean shopHomePageBean) {
                        if (shopHomePageBean.getCode() == 200) {
                            getV().setData(shopHomePageBean);
                        }else {
                            ToastManager.showShort(getV(),shopHomePageBean.msg);
                        }
                    }
                });
    }
}
