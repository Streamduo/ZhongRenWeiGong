package com.project.zhongrenweigong.business.car;

import com.project.zhongrenweigong.business.bean.CarListBean;
import com.project.zhongrenweigong.business.bean.TeacherListBean;
import com.project.zhongrenweigong.business.teach.TeachListFragment;
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
public class CarListFrgementPresent extends XPresent<CarListFragment> {

    public void getVehicle(int currentPage, String name,String thisAddr,int shopCategoryDetail) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("shopCategory", String.valueOf(4));
        stringMap.put("shopCategoryDetail", String.valueOf(shopCategoryDetail));
        stringMap.put("thisAddr", thisAddr);
        stringMap.put("name", name);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getVehicle(requestBody)
                .compose(XApi.<CarListBean>getApiTransformer())
                .compose(XApi.<CarListBean>getScheduler())
                .compose(getV().<CarListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<CarListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(CarListBean carListBean) {
                        if (carListBean.getCode() == 200) {
                            getV().setCarData(carListBean);
                        } else {
                            ToastManager.showShort(getV().getContext(), carListBean.msg);
                        }
                    }
                });
    }
}
