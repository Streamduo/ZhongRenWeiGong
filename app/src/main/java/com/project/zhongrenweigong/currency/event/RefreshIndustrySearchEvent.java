package com.project.zhongrenweigong.currency.event;

/**
 * 作者：Fuduo on 2019/11/19 16:17
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class RefreshIndustrySearchEvent {
    public String searchText;
    public String province;
    public String lat, lng;
    public int index;

    public RefreshIndustrySearchEvent(String searchText, int index, String province,
                                      String lat, String lng) {
        this.searchText = searchText;
        this.index = index;
        this.province = province;
        this.lat = lat;
        this.lng = lng;
    }

    public RefreshIndustrySearchEvent(String searchText, int index) {
        this.searchText = searchText;
        this.index = index;
    }
}
