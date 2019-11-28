package com.project.zhongrenweigong.business.manager;

import com.project.zhongrenweigong.business.bean.IndustryListBean;
import com.project.zhongrenweigong.net.BusinessApi;
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
 * 作者：Fuduo on 2019/11/28 15:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MineShopListFragmentPresent extends XPresent<MineShopListFragment> {
    public void getShopByCategoryId(int currentPage, String name, String categoryId,
                         String mbId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("categoryId", categoryId);
        stringMap.put("mbId", mbId);
        stringMap.put("name", name);

        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getShopByCategoryId(requestBody)
                .compose(XApi.<IndustryListBean>getApiTransformer())
                .compose(XApi.<IndustryListBean>getScheduler())
                .compose(getV().<IndustryListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<IndustryListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(IndustryListBean industryListBean) {
                        if (industryListBean.getCode() == 200) {
                            getV().setData(industryListBean);
                        } else {
                            ToastManager.showShort(getV().getContext(), industryListBean.msg);
                        }
                    }
                });
    }
}
