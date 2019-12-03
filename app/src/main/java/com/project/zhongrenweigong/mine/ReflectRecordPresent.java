package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.mine.bean.ReflectBean;
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
 * 作者：Fuduo on 2019/12/3 10:32
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ReflectRecordPresent extends XPresent<ReflectRecordActivity> {
    public void getWithdrawDepositDetail(int currentPage, String mbId, String year, String month) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("year", year);
        stringMap.put("month", month);
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().getWithdrawDepositDetail(requestBody)
                .compose(XApi.<ReflectBean>getApiTransformer())
                .compose(XApi.<ReflectBean>getScheduler())
                .compose(getV().<ReflectBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<ReflectBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(ReflectBean reflectBean) {
                        if (reflectBean.getCode() == 200) {
                            getV().setData(reflectBean);
                        } else {
                            ToastManager.showShort(getV(), reflectBean.msg);
                        }
                    }
                });
    }

}
