package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/5 13:41
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class CommodityListBean extends BaseModel {


    /**
     * goodsId : string
     * goodsTitleUrl : string
     * mcId : string
     * name : string
     * price : 0
     * status : string
     * type : string
     */

    private List<CommodityDataBean> data;

    public List<CommodityDataBean> getData() {
        return data;
    }

    public void setData(List<CommodityDataBean> data) {
        this.data = data;
    }
}
