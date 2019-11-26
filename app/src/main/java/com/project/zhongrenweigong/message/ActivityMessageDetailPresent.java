package com.project.zhongrenweigong.message;

import com.project.zhongrenweigong.message.bean.SystemAndActivityBean;
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
 * 作者：Fuduo on 2019/10/21 16:40
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ActivityMessageDetailPresent extends XPresent<ActivityMessageDetailActivity> {

    public void getMessageDetail(String messageId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("messageId", messageId);
        stringMap.put("messageType", String.valueOf(1));
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getMessageDetailContent(requestBody)
                .compose(XApi.<SystemAndActivityBean>getApiTransformer())
                .compose(XApi.<SystemAndActivityBean>getScheduler())
                .compose(getV().<SystemAndActivityBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<SystemAndActivityBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(SystemAndActivityBean systemAndActivityBean) {
                        if (systemAndActivityBean.getCode() == 200) {
                            getV().setData(systemAndActivityBean);
                        } else {
                            ToastManager.showShort(getV(), systemAndActivityBean.msg);
                        }
                    }
                });
    }
}
