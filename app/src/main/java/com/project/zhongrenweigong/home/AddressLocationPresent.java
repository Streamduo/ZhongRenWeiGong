package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.home.bean.AddressBean;
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
 * 作者：Fuduo on 2019/10/30 15:03
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class AddressLocationPresent extends XPresent<AddressLocationActivity> {

    public void memberGetOpenAddr() {
        BusinessApi.homeNetManager().memberGetOpenAddr()
                .compose(XApi.<AddressBean>getApiTransformer())
                .compose(XApi.<AddressBean>getScheduler())
                .compose(getV().<AddressBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<AddressBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(AddressBean addressBean) {
                        if (addressBean.getCode() == 200) {
                            getV().setData(addressBean);
                        }else {
                            ToastManager.showShort(getV(),addressBean.msg);
                        }
                    }
                });
    }

    public void merchantfindOpenAddr(String addressName) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();

        stringMap.put("addressName", addressName);

        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.homeNetManager().merchantfindOpenAddr(requestBody)
                .compose(XApi.<AddressBean>getApiTransformer())
                .compose(XApi.<AddressBean>getScheduler())
                .compose(getV().<AddressBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<AddressBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "搜索地区失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(AddressBean addressBean) {
                        if (addressBean.getCode() == 200) {
                            getV().setSearchData(addressBean);
                        }else {
                            ToastManager.showShort(getV(),addressBean.msg);
                        }
                    }
                });
    }

}
