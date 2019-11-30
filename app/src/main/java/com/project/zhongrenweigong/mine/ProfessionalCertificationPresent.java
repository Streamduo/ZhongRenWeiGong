package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.mine.bean.ProfessionalBean;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.GsonProvider;
import com.project.zhongrenweigong.util.LoginOutUtils;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Fuduo on 2019/11/27 11:09
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ProfessionalCertificationPresent extends XPresent<ProfessionalCertificationActivity> {

    public void authMemberProfession(String mbId, String professionName, String addressDetail,
                                     String authMainName, String lat, String lng) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        stringMap.put("addressDetail", addressDetail);
        stringMap.put("authMainName", authMainName);
        stringMap.put("professionName", professionName);
        stringMap.put("lat", lat);
        stringMap.put("lng", lng);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.mineNetManager().authMemberProfession(requestBody)
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
                        ToastManager.showLong(getV(), baseModel.msg);
                        if (baseModel.getCode() == 200) {
                            getV().finish();
                        }
                    }
                });
    }



    public void getProfessionAuth(String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        LoginApi.mineNetManager().getProfessionAuth(requestBody)
                .compose(XApi.<ProfessionalBean>getApiTransformer())
                .compose(XApi.<ProfessionalBean>getScheduler())
                .compose(getV().<ProfessionalBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<ProfessionalBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(ProfessionalBean professionalBean) {
                        if (professionalBean.getCode() == 200) {
                            getV().setUserCertifiation(professionalBean);
                        }
                    }
                });
    }
}
