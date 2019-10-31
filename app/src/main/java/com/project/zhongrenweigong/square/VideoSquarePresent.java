package com.project.zhongrenweigong.square;

import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.net.SquareApi;
import com.project.zhongrenweigong.square.bean.SquareListBean;
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
 * 作者：Fuduo on 2019/10/24 11:53
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class VideoSquarePresent extends XPresent<VideoSquareFragement> {

    public void getPlazaMsg(int currentPage,String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();

        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("type", String.valueOf(1));
        stringMap.put("mbId", mbId);

        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        SquareApi.squareNetManager().getPlazaMsg(requestBody)
                .compose(XApi.<SquareListBean>getApiTransformer())
                .compose(XApi.<SquareListBean>getScheduler())
                .compose(getV().<SquareListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<SquareListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(SquareListBean squareListBean) {
                        if (squareListBean.getCode() == 200) {
                            getV().setData(squareListBean);
                        }
                    }
                });
    }

}
