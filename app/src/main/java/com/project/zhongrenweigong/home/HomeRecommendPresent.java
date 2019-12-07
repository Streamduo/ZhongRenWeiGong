package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
import com.project.zhongrenweigong.home.bean.RecommedBean;
import com.project.zhongrenweigong.home.bean.RecommedTopBean;
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
 * 作者：Fuduo on 2019/11/16 11:00
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeRecommendPresent extends XPresent<HomeRecommendFragment> {

    public void getGoodDeedMessage(String mbId, int currentPage) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("pageNum", String.valueOf(10));
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getGoodDeedMessage(requestBody)
                .compose(XApi.<RecommedBean>getApiTransformer())
                .compose(XApi.<RecommedBean>getScheduler())
                .compose(getV().<RecommedBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<RecommedBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(RecommedBean recommedBean) {
                        if (recommedBean.getCode() == 200) {
                            getV().setData(recommedBean);
                        } else {
                            ToastManager.showShort(getV().getContext(), recommedBean.msg);
                        }
                    }
                });
    }

    public void getTopData(String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("currentPage", String.valueOf(1));
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getRecommendMessage(requestBody)
                .compose(XApi.<RecommedTopBean>getApiTransformer())
                .compose(XApi.<RecommedTopBean>getScheduler())
                .compose(getV().<RecommedTopBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<RecommedTopBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(RecommedTopBean recommedTopBean) {
                        if (recommedTopBean.getCode() == 200) {
                            getV().setTopData(recommedTopBean);
                        } else {
                            ToastManager.showShort(getV().getContext(), recommedTopBean.msg);
                        }
                    }
                });
    }

}
