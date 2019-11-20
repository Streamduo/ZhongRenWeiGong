package com.project.zhongrenweigong.currency.event;

/**
 * 作者：Fuduo on 2019/11/19 16:17
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class SearchEvent {
    public String searchText;
    public int index;

    public SearchEvent(String searchText,int index) {
        this.searchText = searchText;
        this.index = index;
    }
}
