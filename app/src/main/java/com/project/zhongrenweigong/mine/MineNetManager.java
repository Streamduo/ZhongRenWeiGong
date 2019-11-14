package com.project.zhongrenweigong.mine;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.home.bean.AddressBean;
import com.project.zhongrenweigong.home.bean.HomeViewPagerBean;
import com.project.zhongrenweigong.mine.bean.BusinessSystemBean;
import com.project.zhongrenweigong.mine.bean.MineInfoBean;
import com.project.zhongrenweigong.mine.bean.MineSystemBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：Fuduo on 2019/10/23 10:40
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public interface MineNetManager {

    /**
     *  校验旧手机号码验证码
     * @return
     */
    @POST("checkVerification")
    Flowable<BaseModel> checkVerification(@Body RequestBody body);

    /**
     *  修改手机号码
     * @return
     */
    @POST("updatePhone")
    Flowable<BaseModel> updatePhone(@Body RequestBody body);


    /**
     *  修改密码
     * @return
     */
    @POST("updatePassword")
    Flowable<BaseModel> updatePassword(@Body RequestBody body);

    /**
     *  获取个人主页信息接口
     * @return
     */
    @POST("getPersonalHomepage")
    Flowable<MineInfoBean> getPersonalHomepage(@Body RequestBody body);


    /**
     *  获取个人道德体系
     * @return
     */
    @POST("getIndividualSystem")
    Flowable<MineSystemBean> getIndividualSystem(@Body RequestBody body);

}
