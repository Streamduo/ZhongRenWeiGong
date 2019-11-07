package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

/**
 * 作者：Fuduo on 2019/11/5 16:35
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessNoticeBean extends BaseModel {


    /**
     * announcement : string
     * mcId : string
     * time : 2019-11-05T08:34:45.378Z
     * title : string
     */

    private NoticeDataBean data;

    public NoticeDataBean getData() {
        return data;
    }

    public void setData(NoticeDataBean data) {
        this.data = data;
    }
}
