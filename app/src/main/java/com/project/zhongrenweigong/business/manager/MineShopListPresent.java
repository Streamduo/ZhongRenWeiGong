package com.project.zhongrenweigong.business.manager;

import com.project.zhongrenweigong.business.bean.BussinessTypeBean;
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
 * 作者：Fuduo on 2019/11/28 15:31
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MineShopListPresent extends XPresent<MineShopListActivity> {
    public void getMyShopCategory(String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getMyShopCategory(requestBody)
                .compose(XApi.<BussinessTypeBean>getApiTransformer())
                .compose(XApi.<BussinessTypeBean>getScheduler())
                .compose(getV().<BussinessTypeBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<BussinessTypeBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BussinessTypeBean bussinessTypeBean) {
                        if (bussinessTypeBean.getCode() == 200) {
                            getV().setData(bussinessTypeBean);
                        } else {
                            ToastManager.showShort(getV(), bussinessTypeBean.msg);
                        }
                    }
                });
    }
}
