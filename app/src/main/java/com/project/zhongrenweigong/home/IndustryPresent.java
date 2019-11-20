package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.home.bean.HomeViewPagerBean;
import com.project.zhongrenweigong.net.HomeMainApi;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：Fuduo on 2019/10/17 10:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class IndustryPresent extends XPresent<IndustryFragment> {

    public void getVerification() {
//        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
//        String body = GsonProvider.gson.toJson(stringMap);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
//                body);
        HomeMainApi.homeNetManager().findSlideshow()
                .compose(XApi.<HomeViewPagerBean>getApiTransformer())
                .compose(XApi.<HomeViewPagerBean>getScheduler())
                .compose(getV().<HomeViewPagerBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<HomeViewPagerBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getActivity(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(HomeViewPagerBean homeViewPagerBean) {
                        if (homeViewPagerBean.getCode() == 200) {
                            getV().initViewPager(homeViewPagerBean.getData());
                        }
                    }
                });
    }

}
