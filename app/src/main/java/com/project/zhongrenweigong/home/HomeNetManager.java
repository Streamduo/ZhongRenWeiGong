package com.project.zhongrenweigong.home;

import com.project.zhongrenweigong.business.bean.BusinessShopListBean;
import com.project.zhongrenweigong.home.bean.AddressBean;
import com.project.zhongrenweigong.home.bean.HomeViewPagerBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：Fuduo on 2019/10/23 10:40
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public interface HomeNetManager {

    /**
     *  获取首页轮播图
     * @return
     */
    @POST("findSlideshow")
    Flowable<HomeViewPagerBean> findSlideshow();

    /**
     *  获取已开放地区接口
     * @return
     */
    @POST("memberGetOpenAddr")
    Flowable<AddressBean> memberGetOpenAddr();


    /**
     *  根据拼音或者汉字查询以开放城市
     * @return
     */
    @POST("merchantfindOpenAddr")
    Flowable<AddressBean> merchantfindOpenAddr(@Body RequestBody body);
}
