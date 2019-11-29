package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.business.bean.IndustryListBean;
import com.project.zhongrenweigong.business.car.CarListFragment;
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
 * 作者：Fuduo on 2019/11/14 10:59
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MyFollowListFrgementPresent extends XPresent<MyFollowListFragment> {

    public void getLikeShopByCategoryId(int currentPage, String categoryId, String mbId,
                           String lat, String lng) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("categoryId", categoryId);
        stringMap.put("mbId", mbId);
        stringMap.put("lat", lat);
        stringMap.put("lng", lng);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getLikeShopByCategoryId(requestBody)
                .compose(XApi.<IndustryListBean>getApiTransformer())
                .compose(XApi.<IndustryListBean>getScheduler())
                .compose(getV().<IndustryListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<IndustryListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(IndustryListBean industryListBean) {
                        if (industryListBean.getCode() == 200) {
                            getV().setFollowData(industryListBean);
                        } else {
                            ToastManager.showShort(getV().getContext(), industryListBean.msg);
                        }
                    }
                });
    }
}
