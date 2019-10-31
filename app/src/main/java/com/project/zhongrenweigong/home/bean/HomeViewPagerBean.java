package com.project.zhongrenweigong.home.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/10/29 17:26
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeViewPagerBean extends BaseModel {


    /**
     * slideshowUrl : string
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

}
