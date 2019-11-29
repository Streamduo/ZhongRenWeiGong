package com.project.zhongrenweigong.mine.bean;

import com.project.zhongrenweigong.base.BaseModel;

/**
 * 作者：Fuduo on 2019/11/29 14:35
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class IntegralCompensationBean extends BaseModel {

    /**
     * lists : [{"mcId":"1","compensateId":"1","mbId":"358023735078424576","shopId":"1","shopName":"1","date":"2016-05-05","money":1000.25},{"mcId":"2","compensateId":"2","mbId":"358023735078424576","shopId":"2","shopName":"3","date":"2016-05-05","money":2000.55}]
     * sumMoney : 3000.8
     */

    private IntegralCompensationDataBean data;

    public IntegralCompensationDataBean getData() {
        return data;
    }

    public void setData(IntegralCompensationDataBean data) {
        this.data = data;
    }
}
