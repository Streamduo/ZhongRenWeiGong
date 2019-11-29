package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.bean.WeiGongTestBean;
import com.project.zhongrenweigong.home.bean.AddressBean;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
import com.project.zhongrenweigong.home.bean.HomeVideoBean;
import com.project.zhongrenweigong.home.bean.HomeViewPagerBean;
import com.project.zhongrenweigong.home.bean.MessageListBean;
import com.project.zhongrenweigong.message.bean.SystemAndActivityBean;
import com.project.zhongrenweigong.message.bean.SystemMessageBean;
import com.project.zhongrenweigong.message.bean.VoucherMessageDetailBean;
import com.project.zhongrenweigong.mine.bean.IntegralCompensationBean;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 作者：Fuduo on 2019/10/23 10:40
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public interface MessageNetManager {

    /**
     * 获取消息列表
     *
     * @return
     */
    @POST("getMessage")
    Flowable<MessageListBean> getMessageList();

    /**
     * 获取系统和活动消息详情
     *
     * @return
     */
    @POST("getMessageDetailContent")
    Flowable<SystemAndActivityBean> getMessageDetailContent(@Body RequestBody body);

    /**
     * 获取评测信息
     *
     * @return
     */
    @POST("getEvaluatingInfo")
    Flowable<WeiGongTestBean> getEvaluatingInfo(@Body RequestBody body);

    /**
     * @author fuduo
     * @time 2018/1/21  10:38
     * @describe 上传评测图片
     */
    @Multipart
    @POST()
    Call<BaseModel> uploadVoucher(@Url() String url,
                                  @Query("text") String text,
                                  @Query("shopId") String shopId,
                                  @Query("mbId") String mbId,
                                  @Part List<MultipartBody.Part> list);

    /**
     * 获取新闻信息
     *
     * @return
     */
    @POST("getNewsList")
    Flowable<HomeRecommendBean> getNewsList(@Body RequestBody body);

    /**
     * 获取首页视频列表
     *
     * @return
     */
    @POST("getVideo")
    Flowable<HomeVideoBean> getVideo(@Body RequestBody body);


    /**
     * 获取系统消息
     *
     * @return
     */
    @POST("getSystemMessages")
    Flowable<SystemMessageBean> getSystemMessages(@Body RequestBody body);

    /**
     * 获取活动消息
     *
     * @return
     */
    @POST("getActiveMessage")
    Flowable<SystemMessageBean> getActiveMessage(@Body RequestBody body);

    /**
     * 获取凭证消息
     *
     * @return
     */
    @POST("getVoucherMessage")
    Flowable<SystemMessageBean> getVoucherMessage(@Body RequestBody body);

    /**
     * @author fuduo
     * @time 2018/1/21  10:38
     * @describe 上传举报资料图片
     */
    @Multipart
    @POST()
    Call<BaseModel> uploadReport(@Url() String url,
                                 @Query("journalismId") String journalismId,
                                 @Query("content") String content,
                                 @Query("detail") String detail,
                                 @Part List<MultipartBody.Part> list);

    /**
     * @author fuduo
     * @time 2018/1/21  10:38
     * @describe 上传举报资料(无图片)
     */
    @POST()
    Call<BaseModel> uploadReport(@Url() String url,
                                 @Body RequestBody Body);

    /**
     * 获取凭证接口根据ID
     *
     * @return
     */
    @POST("getUploadVoucherById")
    Flowable<VoucherMessageDetailBean> getUploadVoucherById(@Body RequestBody body);


    /**
     * 修改凭证消息审核状态
     *
     * @return
     */
    @POST("updateVoucherStatus")
    Flowable<BaseModel> updateVoucherStatus(@Body RequestBody body);


    /**
     * 消除全部类型未读消息
     *
     * @return
     */
    @POST("updateUnread")
    Flowable<BaseModel> updateUnread(@Body RequestBody body);


    /**
     * @author fuduo
     * @time 2018/1/21  10:38
     * @describe 上传发布活动资料图片
     */
    @Multipart
    @POST()
    Call<BaseModel> addActiveMessage(@Url() String url,
                                     @Query("shopId") String shopId,
                                     @Query("title") String title,
                                     @Query("content") String content,
                                     @Query("address") String address,
                                     @Query("beignTime") String beignTime,
                                     @Query("endTime") String endTime,
                                     @Part List<MultipartBody.Part> list);

    /**
     * @author fuduo
     * @time 2018/1/21  10:38
     * @describe 上传发布活动资料(无图片)
     */
    @POST()
    Call<BaseModel> addActiveMessage(@Url() String url,
                                     @Body RequestBody Body);

    /**
     * 获取赔付明细列表
     *
     * @return
     */
    @POST("getCompensationRecords")
    Flowable<IntegralCompensationBean> getCompensationRecords(@Body RequestBody body);

    /**
     * 获取积分明细列表
     *
     * @return
     */
    @POST("getIntegralSubsidiary")
    Flowable<IntegralCompensationBean> getIntegralSubsidiary(@Body RequestBody body);
}
