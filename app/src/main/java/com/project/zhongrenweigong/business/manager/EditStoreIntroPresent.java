package com.project.zhongrenweigong.business.manager;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.bean.BusinessCategoryListBean;
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
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Fuduo on 2019/11/2 10:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class EditStoreIntroPresent extends XPresent<EditStoreIntroActivity> {

    public void getGoodsCategory() {
        BusinessApi.businessNetManager().getGoodsCategory()
                .compose(XApi.<BusinessCategoryListBean>getApiTransformer())
                .compose(XApi.<BusinessCategoryListBean>getScheduler())
                .compose(getV().<BusinessCategoryListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<BusinessCategoryListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BusinessCategoryListBean businessCategoryListBean) {
                        if (businessCategoryListBean.getCode() == 200) {
                            getV().setData(businessCategoryListBean);
                        } else {
                            ToastManager.showShort(getV(), businessCategoryListBean.msg);
                        }
                    }
                });
    }

    public void getGoodsCategory(String categoryId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("categoryId", categoryId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getSeedCategory(requestBody)
                .compose(XApi.<BusinessCategoryListBean>getApiTransformer())
                .compose(XApi.<BusinessCategoryListBean>getScheduler())
                .compose(getV().<BusinessCategoryListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<BusinessCategoryListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BusinessCategoryListBean businessCategoryListBean) {
                        if (businessCategoryListBean.getCode() == 200) {
                            getV().setCategoryDetailData(businessCategoryListBean);
                        } else {
                            ToastManager.showShort(getV(), businessCategoryListBean.msg);
                        }
                    }
                });
    }

    public void openShopEncryptionApi(String beignTime, String endTime,
                                          String details, String detailedAddr,
                                          String mcPhone, String mcId, String provinces,
                                          String shopName, String shopCategory,
                                          String shopCategoryDetail) {
        LoadingDialog.show(getV());
        Map<String, String> stringMap = LoginApi.getBasicParamsUidAndToken();
        stringMap.put("mcId", mcId);
        stringMap.put("shopName", shopName);
        stringMap.put("shopCategory", shopCategory);
        stringMap.put("shopCategoryDetail", shopCategoryDetail);
        stringMap.put("beignTime", beignTime);
        stringMap.put("endTime", endTime);
        stringMap.put("mcPhone", mcPhone);
        stringMap.put("provinces", provinces);
        stringMap.put("detailedAddr", detailedAddr);
        stringMap.put("details", details);
        String body = GsonProvider.gson.toJson(stringMap);
        final AES aes = new AES();
        String encode3DesBody = null;
        try {
            byte[] utf8s = body.getBytes("UTF8");
            encode3DesBody = aes.encrypt(utf8s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BusinessApi.businessNetManager().openShopEncryptionApi(encode3DesBody)
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
                        ToastManager.showShort(getV(), baseModel.msg);
                        if (baseModel.getCode() == 200) {
                            getV().finish();
                        }
                    }
                });
    }
}
