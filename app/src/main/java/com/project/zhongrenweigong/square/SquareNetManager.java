package com.project.zhongrenweigong.square;

import com.project.zhongrenweigong.home.bean.HomeViewPagerBean;
import com.project.zhongrenweigong.square.bean.SquareListBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：Fuduo on 2019/10/23 10:40
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public interface SquareNetManager {

    /**
     *  获取广场信息
     * @return
     */
    @POST("getPlazaMsg")
    Flowable<SquareListBean> getPlazaMsg(@Body RequestBody body);
}
