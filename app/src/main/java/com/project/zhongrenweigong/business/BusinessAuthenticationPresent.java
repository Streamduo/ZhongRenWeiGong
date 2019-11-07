package com.project.zhongrenweigong.business;

import com.project.zhongrenweigong.base.BaseModel;

import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.AES;
import com.project.zhongrenweigong.util.GsonProvider;
import com.project.zhongrenweigong.view.LoadingDialog;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：Fuduo on 2019/11/1 17:17
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessAuthenticationPresent extends XPresent<BusinessAuthenticationActivity> {

    public void authMerchantEncryptionApi(String companyName, String licenseNum,
                                          String legalPersonName, String businessArea,
                                          String detailedAddr, String mcId) {
        LoadingDialog.show(getV());
        Map<String, String> stringMap = LoginApi.getBasicParamsUidAndToken();
        stringMap.put("companyName", companyName);
        stringMap.put("licenseNum", licenseNum);
        stringMap.put("legalPersonName", legalPersonName);
        stringMap.put("businessArea", businessArea);
        stringMap.put("detailedAddr", detailedAddr);
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        final AES aes = new AES();
        String encode3DesBody = null;
        try {
            byte[] utf8s = body.getBytes("UTF8");
            encode3DesBody = aes.encrypt(utf8s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BusinessApi.businessNetManager().authMerchantEncryptionApi(encode3DesBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        LoadingDialog.dismiss(getV());
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        LoadingDialog.dismiss(getV());
                        if (baseModel.getCode() == 200) {
                            ToastManager.showShort(getV(), baseModel.getMsg());
                            getV().finish();
                        }else {
                            ToastManager.showShort(getV(),baseModel.msg);
                        }
                    }
                });
    }

}
