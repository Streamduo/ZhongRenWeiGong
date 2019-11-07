package com.project.zhongrenweigong.business.manager;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.currency.event.RefreshCommodityEvent;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.util.GsonProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Fuduo on 2019/11/1 15:15
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class AddCommodityPresent extends XPresent<AddCommodityActivity> {

    public void addGoods(String mcId, String name, String price) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("name", name);
        stringMap.put("price", price);
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().addGoods(requestBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.getCode() == 200) {
                            EventBus.getDefault().post(new RefreshCommodityEvent());
                            getV().finish();
                        } else {
                            ToastManager.showShort(getV(), baseModel.msg);
                        }
                    }
                });
    }

    public void updateGoods(String mcId, String name, String newPrice) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("name", name);
        stringMap.put("price", newPrice);
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().updateGoods(requestBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.getCode() == 200) {
                            EventBus.getDefault().post(new RefreshCommodityEvent());
                            getV().finish();
                        } else {
                            ToastManager.showShort(getV(), baseModel.msg);
                        }
                    }
                });
    }
}
