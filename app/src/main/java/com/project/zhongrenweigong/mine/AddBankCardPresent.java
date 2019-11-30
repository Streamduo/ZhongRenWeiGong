package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.base.BaseModel;
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
 * 作者：Fuduo on 2019/11/29 10:33
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class AddBankCardPresent extends XPresent<AddBankCardActivity> {

    public void addBoundBankCard(String mbId, String cardNo, String cardholder) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        stringMap.put("cardholder", cardholder);
        stringMap.put("cardNo", cardNo);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.mineNetManager().addBoundBankCard(requestBody)
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
                            ToastManager.showLong(getV(), "添加成功");
                            getV().finish();
                        } else {
                            ToastManager.showLong(getV(), baseModel.msg);
                        }
                    }
                });
    }
}
