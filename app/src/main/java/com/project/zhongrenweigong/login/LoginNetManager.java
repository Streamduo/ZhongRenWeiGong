package com.project.zhongrenweigong.login;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.login.bean.LoginResponseBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 作者：Fuduo on 2019/10/23 10:40
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public interface LoginNetManager {

    /**
     * 密码登录
     *
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("loginEncryptionApi")//testLogin
    Flowable<BaseModel> login(@Field("dataMsg") String dataMsg);

    /**
     * 注册
     *
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("registerMemberEncryptionApi")
    Flowable<BaseModel> register(@Field("dataMsg") String dataMsg);

    /**
     * @author fuduo
     * @time 2018/1/21  10:38
     * @describe 上传身份证正反面图片
     */
    @POST()
    Call<BaseModel> uploadCardImage(@Url() String url,
                                @Body RequestBody Body);
}
