package com.project.zhongrenweigong.message;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.message.bean.VoucherMessageDetailBean;
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
 * 作者：Fuduo on 2019/11/21 15:05
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class VoucherMessageDetailPresent extends XPresent<VoucherMessageDetailActivity> {

    public void getUploadVoucherById(String voucherId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("voucherId", voucherId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getUploadVoucherById(requestBody)
                .compose(XApi.<VoucherMessageDetailBean>getApiTransformer())
                .compose(XApi.<VoucherMessageDetailBean>getScheduler())
                .compose(getV().<VoucherMessageDetailBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<VoucherMessageDetailBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(VoucherMessageDetailBean voucherMessageDetailBean) {
                        if (voucherMessageDetailBean.getCode() == 200) {
                            getV().setData(voucherMessageDetailBean);
                        } else {
                            ToastManager.showShort(getV(), voucherMessageDetailBean.msg);
                        }
                    }
                });
    }

    public void updateVoucherStatus(String mcId, String messageId, int status) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mcId", mcId);
        stringMap.put("messageId", messageId);
        stringMap.put("status", String.valueOf(status));
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().updateVoucherStatus(requestBody)
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
                            ToastManager.showShort(getV(), "审核成功");
                            getV().finish();
                        } else {
                            ToastManager.showShort(getV(), baseModel.msg);
                        }
                    }
                });
    }
}
