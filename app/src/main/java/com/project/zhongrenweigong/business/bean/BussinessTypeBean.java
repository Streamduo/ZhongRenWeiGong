package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/28 15:57
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BussinessTypeBean extends BaseModel {

    /**
     * categoryId : 3
     * categoryName : 教育
     */

    private List<BusinessTypeDataBean> data;

    public List<BusinessTypeDataBean> getData() {
        return data;
    }

    public void setData(List<BusinessTypeDataBean> data) {
        this.data = data;
    }
}
