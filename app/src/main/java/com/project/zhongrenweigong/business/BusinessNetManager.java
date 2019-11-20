package com.project.zhongrenweigong.business;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.bean.BusinessCategoryListBean;
import com.project.zhongrenweigong.business.bean.BusinessHomePageBean;
import com.project.zhongrenweigong.business.bean.BusinessNoticeBean;
import com.project.zhongrenweigong.business.bean.BusinessShopListBean;
import com.project.zhongrenweigong.business.bean.CarListBean;
import com.project.zhongrenweigong.business.bean.CommodityListBean;
import com.project.zhongrenweigong.business.bean.ShopHomePageBean;
import com.project.zhongrenweigong.business.bean.TeachListBean;
import com.project.zhongrenweigong.business.bean.TeacherListBean;
import com.project.zhongrenweigong.business.bean.WorkerListBean;
import com.project.zhongrenweigong.mine.bean.BusinessSystemBean;

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

    /**
     *
     添加商品信息
     * @return
     */
    @POST("addGoods")
    Flowable<BaseModel> addGoods(@Body RequestBody Body);

    /**
     *
     根据商家ID查询所有商品
     * @return
     */
    @POST("findAllGoodsByMerchantId")
    Flowable<CommodityListBean> findAllGoods(@Body RequestBody Body);


    /**
     *
     删除商品
     * @return
     */
    @POST("deleteGoodsByGoodsId")
    Flowable<BaseModel> deleteGoods(@Body RequestBody Body);

    /**
     *
     修改商品
     * @return
     */
    @POST("updateGoods")
    Flowable<BaseModel> updateGoods(@Body RequestBody Body);

    /**
     *
     新增员工
     * @return
     */
    @POST("bindingEmployees")
    Flowable<BaseModel> addWorker(@Body RequestBody Body);

    /**
     *
     删除员工
     * @return
     */
    @POST("deleteEmployeesById")
    Flowable<BaseModel> deleteWorker(@Body RequestBody Body);

    /**
     *
     根据商家ID查询所有员工
     * @return
     */
    @POST("findEmployeesById")
    Flowable<WorkerListBean> findEmployees(@Body RequestBody Body);

    /**
     *
     发布公告
     * @return
     */
    @POST("addAnnouncement")
    Flowable<BaseModel> addAnnouncement(@Body RequestBody Body);

    /**
     *
     发布公告
     * @return
     */
    @POST("findAllAnnouncement")
    Flowable<BusinessNoticeBean> findAnnouncement(@Body RequestBody Body);

    /**
     *
     获取分类信息接口
     * @return
     */
    @POST("getGoodsCategory")
    Flowable<BusinessCategoryListBean> getGoodsCategory();

    /**
     *
     获取子分类信息接口
     * @return
     */
    @POST("getSeedCategory")
    Flowable<BusinessCategoryListBean> getSeedCategory(@Body RequestBody Body);


    /**
     * 商家信息编辑
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("openShopEncryptionApi")
    Flowable<BaseModel> openShopEncryptionApi(@Field("dataMsg") String dataMsg);

    /**
     *
     根据商家ID获取商家主页头信息
     * @return
     */
    @POST("getMerchantHead")
    Flowable<BusinessHomePageBean> getMerchantHead(@Body RequestBody Body);

    /**
     * 获取商家个人主页
     * @return
     */
    @POST("getMerchantPersonalHomepage")
    Flowable<BusinessSystemBean> getMerchantPersonalHomepage(@Body RequestBody body);

    /**
     * 获取商家个人主页店铺
     * @return
     */
    @POST("getMerchantPersonalHomepageShop")
    Flowable<com.project.zhongrenweigong.mine.bean.BusinessShopListBean> getMerchantPersonalHomepageShop(@Body RequestBody body);

    /**
     * 获取店铺主页
     * @return
     */
    @POST("getShopHomepage")
    Flowable<ShopHomePageBean> getShopHomepage(@Body RequestBody body);

    /**
     * 查询教育信息(机构)
     * @return
     */
    @POST("getEducation")
    Flowable<TeachListBean> getEducationTeach(@Body RequestBody body);

    /**
     * 查询教育信息(个人)
     * @return
     */
    @POST("getEducation")
    Flowable<TeacherListBean> getEducationTeacher(@Body RequestBody body);

    /**
     * 查询汽车信息(机构)
     * @return
     */
    @POST("getVehicle")
    Flowable<CarListBean> getVehicle(@Body RequestBody body);
}
