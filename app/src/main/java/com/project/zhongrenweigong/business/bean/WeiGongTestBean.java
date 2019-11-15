package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/15 16:09
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class WeiGongTestBean extends BaseModel {

    /**
     * platformGetAreaManager : {"mbId":"1","mbName":"1","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573812466763&di=21fec67e25ae6d530bd76a966065faf3&imgtype=0&src=http%3A%2F%2Fimage.yy.com%2Fyywebalbumbs2bucket%2Fca0bf37d194f443a9b0139b78b3668c5_1451587151580.jpg","managementAddress":"北京市朝阳区"}
     * detectionImages : ["https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573814212698&di=7d27ce452bf5b572af5c7544d6351bbe&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170317%2F0fb3cdf210d7466c80490a3acc50d4de_th.jpg"]
     * platformGetInspector : [{"mbId":"11","mbName":"11","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573812466765&di=9d41d6eb867e9ea21e8ce48e947a1aa9&imgtype=0&src=http%3A%2F%2Fimg1.ciurl.cn%2Fmupload%2F20150528%2Fe4303b0f9943aa2b489766906ef63139.jpg","managementAddress":"北京市朝阳区"},{"mbId":"2","mbName":"2","headUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573812466765&di=9d41d6eb867e9ea21e8ce48e947a1aa9&imgtype=0&src=http%3A%2F%2Fimg1.ciurl.cn%2Fmupload%2F20150528%2Fe4303b0f9943aa2b489766906ef63139.jpg","managementAddress":"北京市朝阳区"}]
     */

    private WeiGongTestDataBean data;

    public WeiGongTestDataBean getData() {
        return data;
    }

    public void setData(WeiGongTestDataBean data) {
        this.data = data;
    }
}
