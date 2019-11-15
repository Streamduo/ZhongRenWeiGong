package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.mine.bean.BusinessShopListBean;
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
 * 作者：Fuduo on 2019/11/13 11:20
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MineShopListPresent extends XPresent<MineShopListFragment> {
    public void getMerchantPersonalHomepageShop(String shopCategoryId, String mcId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("shopCategoryId", shopCategoryId);
        stringMap.put("mcId", mcId);

        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getMerchantPersonalHomepageShop(requestBody)
                .compose(XApi.<BusinessShopListBean>getApiTransformer())
                .compose(XApi.<BusinessShopListBean>getScheduler())
                .compose(getV().<BusinessShopListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<BusinessShopListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BusinessShopListBean businessShopListBean) {
                        if (businessShopListBean.getCode() == 200) {
                            getV().setShopList(businessShopListBean);
                        }else {
                            ToastManager.showShort(getV().getContext(),businessShopListBean.msg);
                        }
                    }
                });
    }
}
