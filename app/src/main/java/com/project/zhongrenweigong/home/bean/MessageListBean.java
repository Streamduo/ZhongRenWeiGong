package com.project.zhongrenweigong.home.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/7 13:53
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MessageListBean extends BaseModel {


    /**
     * typeId : 0
     * content : 系统将于晚间00点后进行升级
     * title : 系统通知
     */

    private List<MessageDataBean> data;

    public List<MessageDataBean> getData() {
        return data;
    }

    public void setData(List<MessageDataBean> data) {
        this.data = data;
    }
}
