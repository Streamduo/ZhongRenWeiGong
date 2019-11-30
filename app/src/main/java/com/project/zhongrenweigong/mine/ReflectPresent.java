package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.bean.BankCardBean;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.HomeMainApi;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.AES;
import com.project.zhongrenweigong.util.GsonProvider;

import org.json.JSONArray;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Fuduo on 2019/10/29 10:11
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ReflectPresent extends XPresent<ReflectActivity> {

    public void getBankCardList(String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.mineNetManager().getBankCardList(requestBody)
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
                            String data = baseModel.encryptionData;
                            if (data != null && !data.equals("")) {
                                AES aes = new AES();
                                String json = aes.decrypt(data);
                                BankCardBean bankCardBean = GsonProvider.gson.fromJson(json, BankCardBean.class);
                                getV().setData(bankCardBean);
                            }

                        } else if (baseModel.getCode() == 500) {
                            getV().setNoCard();
                        }
                    }
                });
    }

    public void withdrawalEncryption(String cardNo, int flag, String mbId, String money) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("cardNo", cardNo);
        stringMap.put("flag", String.valueOf(flag));
        stringMap.put("mbId", mbId);
        stringMap.put("money", money);
        String body = GsonProvider.gson.toJson(stringMap);

        final AES aes = new AES();
        String encode3DesBody = null;
        try {
            byte[] utf8s = body.getBytes("UTF8");
            encode3DesBody = aes.encrypt(utf8s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HomeMainApi.messageNetManager().aWithdrawalEncryption(encode3DesBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel BaseModel) {
                        if (BaseModel.getCode() == 200) {
                            ToastManager.showLong(getV(), "发起提现成功");
                            getV().finish();
                        } else {
                            ToastManager.showShort(getV(), BaseModel.msg);
                        }
                    }
                });
    }

}
