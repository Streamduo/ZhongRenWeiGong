package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.home.bean.HomeVideoBean;
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
 * 作者：Fuduo on 2019/11/23 14:31
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeVideoPresent extends XPresent<HomeVideoFragment> {
    public void getVideo(int currentPage) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("pageNum", String.valueOf(10));
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getVideo(requestBody)
                .compose(XApi.<HomeVideoBean>getApiTransformer())
                .compose(XApi.<HomeVideoBean>getScheduler())
                .compose(getV().<HomeVideoBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<HomeVideoBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(HomeVideoBean homeVideoBean) {
                        if (homeVideoBean.getCode() == 200) {
                            getV().setData(homeVideoBean);
                        }else {
                            ToastManager.showShort(getV().getContext(),homeVideoBean.msg);
                        }
                    }
                });
    }
}
