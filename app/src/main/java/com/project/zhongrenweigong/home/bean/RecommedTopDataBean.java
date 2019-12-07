package com.project.zhongrenweigong.home.bean;

import java.util.List;

/**
 * 作者：Fuduo on 2019/12/7 15:59
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class RecommedTopDataBean {

    private List<NewsBean> stickList;
    private List<NewsBean> spreadFashionList;
    private List<NewsBean> hotTopicList;

    public List<NewsBean> getStickList() {
        return stickList;
    }

    public void setStickList(List<NewsBean> stickList) {
        this.stickList = stickList;
    }

    public List<NewsBean> getSpreadFashionList() {
        return spreadFashionList;
    }

    public void setSpreadFashionList(List<NewsBean> spreadFashionList) {
        this.spreadFashionList = spreadFashionList;
    }

    public List<NewsBean> getHotTopicList() {
        return hotTopicList;
    }

    public void setHotTopicList(List<NewsBean> hotTopicList) {
        this.hotTopicList = hotTopicList;
    }
}
