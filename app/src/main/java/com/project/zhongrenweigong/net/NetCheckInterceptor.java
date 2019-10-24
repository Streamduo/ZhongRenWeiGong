package com.project.zhongrenweigong.net;



import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.util.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by k on 2017/6/24.
 */

public class NetCheckInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean available = NetworkUtils.isNetworkAvailable(App.app);
        Response response;
        if (available) {
            response = chain.proceed(request);
        } else {
            throw new NoNetException();
        }
        return response;
    }

}
