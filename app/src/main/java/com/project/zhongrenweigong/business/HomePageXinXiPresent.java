package com.project.zhongrenweigong.business;

import com.project.zhongrenweigong.business.bean.BusinessHomePageBean;
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
 * 作者：Fuduo on 2019/10/21 15:49
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomePageXinXiPresent extends XPresent<HomePageXinXiFragment> {

    public void getMerchantHead(String mcId,String shopId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mcId", mcId);
        stringMap.put("shopId", shopId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getMerchantHead(requestBody)
                .compose(XApi.<BusinessHomePageBean>getApiTransformer())
                .compose(XApi.<BusinessHomePageBean>getScheduler())
                .compose(getV().<BusinessHomePageBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<BusinessHomePageBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BusinessHomePageBean businessHomePageBean) {
                        if (businessHomePageBean.getCode() == 200) {
                            getV().setData(businessHomePageBean);
                        }else {
                            ToastManager.showShort(getV().getContext(),businessHomePageBean.msg);
                        }
                    }
                });
    }

}
