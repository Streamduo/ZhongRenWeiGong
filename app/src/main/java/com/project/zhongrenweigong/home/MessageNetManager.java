package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.home.bean.AddressBean;
import com.project.zhongrenweigong.home.bean.HomeViewPagerBean;
import com.project.zhongrenweigong.home.bean.MessageListBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：Fuduo on 2019/10/23 10:40
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public interface MessageNetManager {

    /**
     *  获取消息列表
     * @return
     */
    @POST("getMessage")
    Flowable<MessageListBean> getMessageList();

    /**
     *  获取消息详情
     * @return
     */
    @POST("getMessageDetailById")
    Flowable<MessageListBean> getMessageDetail(@Body RequestBody body);

}
