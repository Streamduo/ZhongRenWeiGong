package com.project.zhongrenweigong.mine.bean;

import com.project.zhongrenweigong.base.BaseModel;

/**
 * 作者：Fuduo on 2019/12/3 10:28
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ReflectBean extends BaseModel {

    /**
     * sumMoney : 500.01
     * lists : [{"mbId":"358023735078424576","cardNumber":"6385","bankName":"中国邮政储蓄银行","time":"2019-11-30T06:13:25.000+0000","dateTime":"2019-11-30 14:13:25","status":"0","money":50,"sumMoney":500.01,"type":"1"}]
     */

    private ReflectDataBean data;

    public ReflectDataBean getData() {
        return data;
    }

    public void setData(ReflectDataBean data) {
        this.data = data;
    }
}
