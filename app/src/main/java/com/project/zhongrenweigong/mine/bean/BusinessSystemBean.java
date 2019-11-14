package com.project.zhongrenweigong.mine.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/13 17:37
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessSystemBean extends BaseModel {

    /**
     * mbName : 3
     * mbHeadUrl : http://b-ssl.duitang.com/uploads/item/201804/30/20180430223158_stfyy.thumb.700_0.jpeg
     * mbId : 444
     * isMerchantAuth : 1
     * oathUrl :
     * individualSystemGrade : 100
     * categoryLists : [{"categoryId":"0","categoryName":"餐饮"},{"categoryId":"2","categoryName":"教育"},{"categoryId":"1","categoryName":"汽车"}]
     */

    private ShopDataBean data;

    public ShopDataBean getData() {
        return data;
    }

    public void setData(ShopDataBean data) {
        this.data = data;
    }
}
