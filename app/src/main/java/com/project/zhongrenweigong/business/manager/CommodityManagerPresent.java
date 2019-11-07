package com.project.zhongrenweigong.business.manager;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.bean.CommodityListBean;
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
 * 作者：Fuduo on 2019/11/1 14:18
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class CommodityManagerPresent extends XPresent<CommodityManagerActivity> {

    public void findAllGoods(int currentPage,String mcId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().findAllGoods(requestBody)
                .compose(XApi.<CommodityListBean>getApiTransformer())
                .compose(XApi.<CommodityListBean>getScheduler())
                .compose(getV().<CommodityListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<CommodityListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(CommodityListBean commodityListBean) {
                        if (commodityListBean.getCode() == 200) {
                            getV().setData(commodityListBean);
                        }else {
                            ToastManager.showShort(getV(),commodityListBean.msg);
                        }
                    }
                });
    }

    public void deleteGoods(String goodsId, final int position) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("goodsId", goodsId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().deleteGoods(requestBody)
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
                            getV().deleteItem(position);
                        } else {
                            ToastManager.showShort(getV(), baseModel.msg);
                        }
                    }
                });
    }

}
