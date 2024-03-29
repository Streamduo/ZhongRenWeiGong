package com.project.zhongrenweigong.business;

import com.project.zhongrenweigong.business.bean.BusinessShopListBean;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.GsonProvider;


import java.util.Map;


import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.project.zhongrenweigong.currency.Constans.ADDRES;

/**
 * 作者：Fuduo on 2019/10/18 15:24
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BussinessListPresent extends XPresent<BusinessListActivity> {

    public void selectAllShop(int shopCategory, int currentPage, int flag,
                              String shopName, int shopCategoryDetail, String address,
                              String lat, String lng) {

        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("flag", String.valueOf(flag));
        stringMap.put("thisAddr", address);
        stringMap.put("lat", lat);
        stringMap.put("lng", lng);
        switch (flag) {
            case 0:
                stringMap.put("shopCategory", String.valueOf(shopCategory));
                stringMap.put("shopCategoryDetail", String.valueOf(shopCategoryDetail));
                break;
            case 1:
                stringMap.put("shopName", shopName);
                stringMap.put("shopCategory", String.valueOf(shopCategory));
                stringMap.put("shopCategoryDetail", String.valueOf(shopCategoryDetail));
                break;
        }
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().selectAllShop(requestBody)
                .compose(XApi.<BusinessShopListBean>getApiTransformer())
                .compose(XApi.<BusinessShopListBean>getScheduler())
                .compose(getV().<BusinessShopListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<BusinessShopListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(BusinessShopListBean businessShopListBean) {
                        if (businessShopListBean.getCode() == 200) {
                            getV().setData(businessShopListBean);
                        } else {
                            ToastManager.showShort(getV(), businessShopListBean.msg);
                        }
                    }
                });
    }

}
