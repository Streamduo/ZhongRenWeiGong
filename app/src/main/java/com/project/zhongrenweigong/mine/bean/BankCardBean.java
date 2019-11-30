package com.project.zhongrenweigong.mine.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/30 14:56
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BankCardBean extends BaseModel {

    /**
     * mbId : 358023735078424576
     * bankName : 中国邮政储蓄银行
     * cardNumber : ****6385
     * cardType : 储蓄卡
     * cardImgUrl : https://apimg.alipay.com/combo.png?d=cashier&t=PSBC
     */

    private List<BankCardDataBean> lists;

    public List<BankCardDataBean> getData() {
        return lists;
    }

    public void setData(List<BankCardDataBean> data) {
        this.lists = data;
    }
}
