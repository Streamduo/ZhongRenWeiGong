package com.project.zhongrenweigong.home.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/27 10:14
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeVideoBean extends BaseModel {

    /**
     * videoId :
     * title : 新闻联播
     * copyright : 新闻联播
     * copyrightUrl : http://img1.imgtn.bdimg.com/it/u=3608155120,1630233151&fm=26&gp=0.jpg
     * videoUrl : https://cloud.video.taobao.com/play/u/3245834217/p/1/e/6/t/1/50253840389.mp4
     * coverUrl : http://img1.imgtn.bdimg.com/it/u=3608155120,1630233151&fm=26&gp=0.jpg
     * isHos : 0
     * transmitNum : 0
     * lookNum : 100
     * duration : 05:05
     */

    private List<HomeVideoDataBean> data;

    public List<HomeVideoDataBean> getData() {
        return data;
    }

    public void setData(List<HomeVideoDataBean> data) {
        this.data = data;
    }
}
