package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/10/29 11:54
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessShopListBean extends BaseModel {

    /**
     * shopName : 哇哈哈
     * mcId : 111
     * shopCategory : 1
     * shopLogo : null
     * isOpen : null
     * isQualificationAuth : null
     * shopGrade : null
     * isQualified : null
     * isFreeze : 0
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public int pageSize;
}
