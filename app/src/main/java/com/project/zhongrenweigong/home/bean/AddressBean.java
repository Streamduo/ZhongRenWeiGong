package com.project.zhongrenweigong.home.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/10/30 17:50
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class AddressBean extends BaseModel {

    /**
     * addrId : string
     * address : string
     * zipCode : string
     */

    private List<AddressDataBean> data;

    public List<AddressDataBean> getData() {
        return data;
    }

    public void setData(List<AddressDataBean> data) {
        this.data = data;
    }
}
