package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.mine.bean.BusinessShopListBean;
import com.project.zhongrenweigong.mine.bean.BusinessSystemBean;
import com.project.zhongrenweigong.mine.bean.MineSystemBean;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.LoginApi;
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
 * 作者：Fuduo on 2019/11/13 13:39
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessMineHomePagePresent extends XPresent<BusinessMineHomePageActivity> {

    public void getMerchantPersonalHomepage(String mcId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getMerchantPersonalHomepage(requestBody)
                .compose(XApi.<BusinessSystemBean>getApiTransformer())
                .compose(XApi.<BusinessSystemBean>getScheduler())
                .compose(getV().<BusinessSystemBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<BusinessSystemBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BusinessSystemBean businessSystemBean) {
                        if (businessSystemBean.getCode() == 200) {
                            getV().setData(businessSystemBean);
                        }else {
                            ToastManager.showLong(getV(), businessSystemBean.msg);
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
