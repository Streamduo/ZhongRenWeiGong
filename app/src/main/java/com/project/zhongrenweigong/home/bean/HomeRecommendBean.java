package com.project.zhongrenweigong.home.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/21 15:34
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeRecommendBean extends BaseModel {

    public static final int STYLE_0 = 0;
    public static final int STYLE_1= 1;
    public static final int STYLE_2 = 2;

    /**
     * title : 新闻联播
     * copyright : 凤凰网
     * time : 6时前
     * timestamp : 1574299822667
     * detail : 新闻联播
     * imagesUrl : ["https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574309857463&di=0a65f48a726e840268a64a8b19901c7b&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20111126%2FImg326978346.jpg"]
     * coverUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574309857464&di=1844df9bad9f2d33264ecf9a28d69ac0&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20110921%2FImg320047355.jpg
     * videoUrl :
     * transmitNum : 0
     * type : 0
     */

    private List<NewsDataBean> data;

    public List<NewsDataBean> getData() {
        return data;
    }

    public void setData(List<NewsDataBean> data) {
        this.data = data;
    }
}
