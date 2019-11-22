package com.project.zhongrenweigong.message;

import com.project.zhongrenweigong.home.bean.MessageListBean;
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
public class MessageDetailPresent extends XPresent<SystemMessageDetailActivity> {

    public void getMessageDetail(String typeId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("typeId", typeId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getMessageDetail(requestBody)
                .compose(XApi.<MessageListBean>getApiTransformer())
                .compose(XApi.<MessageListBean>getScheduler())
                .compose(getV().<MessageListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<MessageListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(MessageListBean messageListBean) {
                        if (messageListBean.getCode() == 200) {
                            getV().setData(messageListBean);
                        }else {
                            ToastManager.showShort(getV(),messageListBean.msg);
                        }
                    }
                });
    }

}
