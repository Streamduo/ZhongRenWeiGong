package com.project.zhongrenweigong.mine.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/15 10:24
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessShopListBean extends BaseModel {

    /**
     * shopId : 222
     * shopName : 哈哈哈爱别哈
     * mcId : 346534891132948480
     * shopCategory : 1
     * mcPhone : 212121
     * shopLogo : 
     * details : 突突突
     * likeNum : 1000
     * isOpen : 0
     * isQualificationAuth : 0
     * shopGrade : 
     * isQualified : 1
     * isFreeze : 0
     * beignTime : 
     * endTime : 
     * detailedAddr : 哭哭啼啼
     */

    private List<ShopListDataBean> data;

    public List<ShopListDataBean> getData() {
        return data;
    }

    public void setData(List<ShopListDataBean> data) {
        this.data = data;
    }
}
