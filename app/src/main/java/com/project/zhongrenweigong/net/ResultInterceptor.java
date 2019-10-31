package com.project.zhongrenweigong.net;

import android.content.Context;

import java.io.IOException;

import cn.droidlover.xdroidbase.kit.ToastManager;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者：Fuduo on 2019/10/23 10:56
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ResultInterceptor implements Interceptor {

    private Context context;

    public ResultInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return checkResponse(request, response);
    }

    private Response checkResponse(Request request, Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        String json = body.string();
//                        if (!request.url().toString().contains("testLogin")){
//
//                        }
//                        BaseModel baseModel = new BaseModel();
//                        Gson gson = new Gson();
//                        baseModel = gson.fromJson(json, BaseModel.class);
//                        Integer status = baseModel.getCode();
//                        onNoNetError();
//                        try {
//                            if (status != null && status != 200) {
//    //                            onError(new ApiException(baseModel.getCode(), baseModel.getMsg()));
//                                String msg = baseModel.getMsg();
//                                if (msg != null && !msg.equals("")) {
//                                    ToastManager.showShort(context, msg);
//                                } else {
//                                    onNoNetError();
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        body = ResponseBody.create(mediaType, json);
//                        if(status != null && status.equals(3)){
//                            ToastUtils.showToast(baseModel.getRespMsg());
//                            Router.newIntent((Activity) context)
//                                    .putString("errorlog",baseModel.getRespMsg())
//                                    .to(LoginActivity.class)
//                                    .launch();
//
//                        }
                        return response.newBuilder().body(body).build();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType == null) return false;

        return ("text".equals(mediaType.subtype())
                || "json".equals(mediaType.subtype())
                || "xml".equals(mediaType.subtype())
                || "html".equals(mediaType.subtype())
                || "webviewhtml".equals(mediaType.subtype())
                || "x-www-form-urlencoded".equals(mediaType.subtype()));
    }

    public void onError(Exception e) {
        if (e instanceof ApiException) { // 接口非200
            ApiException apiException = (ApiException) e;
            onApiError(apiException);
        } else if (e instanceof NoNetException) {
            onNoNetError();
        } else {
            onErrorElse(e);
        }
    }

    private void onNoNetError() {
        ToastManager.showShort(context, "当前网络不可用，请检查你的网络设置");
    }

    private void onApiError(ApiException e) {
//        if(e.code==5008){
//        }else{
        ApiHelper.showCode(e.code, e.msg);
//        }
    }

    private void onErrorElse(Exception e) {
        ToastManager.showShort(context, "当前网络环境不稳定，请稍后重试");
    }

}