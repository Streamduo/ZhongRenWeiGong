package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/6 09:56
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessCategoryListBean extends BaseModel {


    /**
     * categoryId : 0
     * categoryName : 餐饮
     */

    private List<CategoryDataBean> data;

    public List<CategoryDataBean> getData() {
        return data;
    }

    public void setData(List<CategoryDataBean> data) {
        this.data = data;
    }
}
