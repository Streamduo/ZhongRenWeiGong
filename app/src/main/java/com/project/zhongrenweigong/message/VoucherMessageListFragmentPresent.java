package com.project.zhongrenweigong.message;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.currency.event.RefreshMessageEvent;
import com.project.zhongrenweigong.home.bean.MessageListBean;
import com.project.zhongrenweigong.message.bean.SystemMessageBean;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.HomeMainApi;
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
 * 作者：Fuduo on 2019/11/7 13:57
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class VoucherMessageListFragmentPresent extends XPresent<VoucherMessageFragment> {

    public void getVoucherMessage(String mbId,int currentPage) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("mbId", mbId);
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("pageNum", String.valueOf(10));
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getVoucherMessage(requestBody)
                .compose(XApi.<SystemMessageBean>getApiTransformer())
                .compose(XApi.<SystemMessageBean>getScheduler())
                .compose(getV().<SystemMessageBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<SystemMessageBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(SystemMessageBean systemMessageBean) {
                        if (systemMessageBean.getCode() == 200) {
                            getV().setData(systemMessageBean);
                        }else {
                            ToastManager.showShort(getV().getContext(),systemMessageBean.msg);
                        }
                    }
                });
    }
}
