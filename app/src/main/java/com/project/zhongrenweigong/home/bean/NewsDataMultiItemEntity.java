package com.project.zhongrenweigong.home.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 作者：Fuduo on 2019/11/21 15:41
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class NewsDataMultiItemEntity implements MultiItemEntity {
    private int itemType;

    public NewsDataBean newsDataBean;

    public NewsDataMultiItemEntity(NewsDataBean newsDataBean) {
        this.newsDataBean = newsDataBean;
        itemType = newsDataBean.type;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
