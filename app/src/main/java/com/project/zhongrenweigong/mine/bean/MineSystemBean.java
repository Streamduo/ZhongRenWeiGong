package com.project.zhongrenweigong.mine.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/13 15:58
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MineSystemBean extends BaseModel {


    /**
     * mbId : 444
     * sumGrade : 100
     * professionGrade : 25
     * everydayGrade : 25
     * integrityGrade : 25
     * socialContributionGrade : 25
     * professionBehavior : [{"behaviorTypeId":"0","title":"扶老太太过马路","date":"2019-05-08","grade":3}]
     * everydayBehavior : [{"behaviorTypeId":"0","title":"扶老太太过马路","date":"2019-05-08","grade":3}]
     * integrityBehavior : [{"behaviorTypeId":"0","title":"扶老太太过马路","date":"2019-05-08","grade":3}]
     * socialContributionBehavior : [{"behaviorTypeId":"0","title":"扶老太太过马路","date":"2019-05-08","grade":3}]
     */

    private SystemDataBean data;

    public SystemDataBean getData() {
        return data;
    }

    public void setData(SystemDataBean data) {
        this.data = data;
    }
}
