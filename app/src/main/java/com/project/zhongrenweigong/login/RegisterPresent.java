package com.project.zhongrenweigong.login;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.net.Api;
import com.project.zhongrenweigong.util.GsonProvider;
import com.project.zhongrenweigong.util.AES;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;


/**
 * 作者：Fuduo on 2019/10/16 10:54
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class RegisterPresent extends XPresent<RegisterActivity> {

    public void register(String idcard, String idcardDeadline,
                      String mbName, String mbPassword, String mbPhone,
                         String sex,String verification) {
        Map<String, String> stringMap = Api.getBasicParamsUidAndToken();
        stringMap.put("idcard", idcard);
        stringMap.put("idcardDeadline", idcardDeadline);
        stringMap.put("mbName", mbName);
        stringMap.put("mbPassword", mbPassword);
        stringMap.put("mbPhone", mbPhone);
        stringMap.put("sex", sex);
        stringMap.put("verification",verification);
        String body = GsonProvider.gson.toJson(stringMap);
        final AES aes = new AES();
        String encode3DesBody = null;
        try {
            byte[] utf8s = body.getBytes("UTF8");
            encode3DesBody = aes.encrypt(utf8s);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/text"),
//                encode3DesBody);
        Api.loginNetManager().register(encode3DesBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
//                        if (error.getType() == OtherError) {
//                            ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
//                        }
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.getCode() == 200) {
                            ToastManager.showShort(getV(), baseModel.getMsg());
                            getV().initRegisterOk();
                        }
                    }
                });
    }
}
