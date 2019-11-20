package com.project.zhongrenweigong.square.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/10/30 16:06
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class SquareListBean extends BaseModel {

    /**
     * data : [{"commentNum":0,"content":"string","contentUrl":"string","coverPictureUrl":"string","createTime":"2019-10-30T08:05:39.838Z","likeNum":0,"memberId":"string","memberTitleUrl":"string","plazaId":"string","transmitNum":0,"type":"string"}]
     * pageSize : 0
     */

    /**
     * commentNum : 0
     * content : string
     * contentUrl : string
     * coverPictureUrl : string
     * createTime : 2019-10-30T08:05:39.838Z
     * likeNum : 0
     * memberId : string
     * memberTitleUrl : string
     * plazaId : string
     * transmitNum : 0
     * type : string
     */

    private List<SquareDataBean> data;

    public List<SquareDataBean> getData() {
        return data;
    }

    public void setData(List<SquareDataBean> data) {
        this.data = data;
    }
}
