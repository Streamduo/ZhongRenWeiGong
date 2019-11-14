package com.project.zhongrenweigong.mine.bean;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/13 16:01
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class SystemDataBean {

    public String mbId;
    public int sumGrade;
    public int professionGrade;
    public int everydayGrade;
    public int integrityGrade;
    public int socialContributionGrade;
    /**
     * behaviorTypeId : 0
     * title : 扶老太太过马路
     * date : 2019-05-08
     * grade : 3
     */
    public List<GoodDeedBean> professionBehavior;
    public List<GoodDeedBean> everydayBehavior;
    public List<GoodDeedBean> integrityBehavior;
    public List<GoodDeedBean> socialContributionBehavior;

}
