package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.bean.WeiGongTestBean;
import com.project.zhongrenweigong.home.bean.AddressBean;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
import com.project.zhongrenweigong.home.bean.HomeViewPagerBean;
import com.project.zhongrenweigong.home.bean.MessageListBean;
import com.project.zhongrenweigong.message.bean.SystemMessageBean;

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
     * 获取消息详情
     *
     * @return
     */
    @POST("getMessageDetailById")
    Flowable<MessageListBean> getMessageDetail(@Body RequestBody body);

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
    Call<BaseModel> uploadCardImage(@Url() String url, @Query("text") String text, @Query("shopId") String shopId,
                                    @Part List<MultipartBody.Part> list);

    /**
     * 获取新闻信息
     *
     * @return
     */
    @POST("getNewsList")
    Flowable<HomeRecommendBean> getNewsList(@Body RequestBody body);


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
     * @describe 上传举报资料图片
     */
    @POST()
    Call<BaseModel> uploadReport(@Url() String url,
                                 @Body RequestBody Body);


}
