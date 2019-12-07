package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.base.BaseModel;
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
 * 作者：Fuduo on 2019/12/6 13:37
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class NewsDetailPresent extends XPresent<NewsDetailActivity> {

    public void homePageLike(String mbId, String likeNewsId,
                             String likeType, String flag) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("likeNewsId", likeNewsId);
        stringMap.put("likeType", likeType);
        stringMap.put("flag", flag);
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().homePageLike(requestBody)
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
                            getV().setLikeStatus(flag);
                        } else {
                            ToastManager.showShort(getV(), baseModel.msg);
                        }
                    }
                });
    }

    public void collect(String mbId, String likeNewsId, String flag) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("collectNewsId", likeNewsId);
        stringMap.put("flag", flag);
        stringMap.put("mbId", mbId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        HomeMainApi.messageNetManager().collect(requestBody)
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
                            getV().setCollectStatus(flag);
                        } else {
                            ToastManager.showShort(getV(), baseModel.msg);
                        }
                    }
                });
    }
}
