package com.project.zhongrenweigong.business;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.bean.BusinessShopListBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 作者：Fuduo on 2019/10/23 10:40
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public interface BusinessNetManager {

    /**
     * 查询所有商家
     * @return
     */
    @POST("findShop")
    Flowable<BusinessShopListBean> selectAllShop(@Body RequestBody Body);


    /**
     * 模糊查询所有店铺根据店铺名称
     * @return
     */
    @POST("findAllShopByLikeName")
    Flowable<BusinessShopListBean> findAllShopByLikeName(@Body RequestBody Body);


    /**
     * 商家认证
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("authMerchantEncryptionApi")
    Flowable<BaseModel> authMerchantEncryptionApi(@Field("dataMsg") String dataMsg);

}
