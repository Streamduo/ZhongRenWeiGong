package com.project.zhongrenweigong.currency;

import android.content.Context;
import android.text.TextUtils;


import com.project.zhongrenweigong.currency.bean.SearchHistoryBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.droidlover.xdroidmvp.cache.SharedPref;


public class SearchHistoryManager {
    public static final String SEARCH_BUSINESS_HISTORY = "search_business_history";
    public static final String SEARCH_ADDDRESS_HISTORY = "search_address_history";
    public static final String SEARCH_NEWS_HISTORY = "search_news_history";
    public static final String SEARCH_USER = "search_user";
    public static final int BIGSIZE = 10;

    private static SearchHistoryManager INSTANCE;
    private SharedPref sp;

    private SearchHistoryManager(Context context) {
        this.sp = SharedPref.getInstance(context);
    }

    public static SearchHistoryManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SearchHistoryManager(context);
        }
        return INSTANCE;
    }

    /**
     * 保存搜索记录
     */
    public void saveSearchHistory(SearchHistoryBean bean, String type) {
        if (bean.searchHistory.length() < 1) {
            return;
        }
        String longhistory = sp.getString(type, "");
        String[] tmpHistory = longhistory.split(",");
        List<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        List<String> tempHistory = new ArrayList<String>();
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (bean.searchHistory.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, bean.searchHistory);
        }
        if (history.size() > BIGSIZE) {
            tempHistory = history.subList(0, BIGSIZE);
        } else {
            tempHistory = history;
        }
        if (tempHistory.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tempHistory.size(); i++) {
                sb.append(tempHistory.get(i) + ",");
            }
            sp.putString(type, sb.toString());
        } else {
            sp.putString(type, bean.searchHistory + ",");
        }
    }

    /**
     * 读取历史搜索记录
     */
    public List<SearchHistoryBean> getSearchHistory(String type) {

        String longhistory = sp.getString(type, "");
        String[] hisArrays = longhistory.split(",");
        List<SearchHistoryBean> datas = new ArrayList<SearchHistoryBean>();
        if (hisArrays != null && hisArrays.length > 0) {
            for (int i = 0; i < hisArrays.length; i++) {
                if (!TextUtils.isEmpty(hisArrays[i])) {
                    datas.add(new SearchHistoryBean().setContent(hisArrays[i]));
                }

            }
        }
        return datas;
    }

    public void romoveHistory(String type) {
        sp.remove(type);
    }

    /**
     * 移除指定历史记录
     *
     * @param bean
     */
    public void removeHistory(SearchHistoryBean bean, String type) {
        if (bean.searchHistory.length() < 1) {
            return;
        }
        String longhistory = sp.getString(type, "");
        String[] tmpHistory = longhistory.split(",");
        ArrayList<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (bean.searchHistory.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
        }
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.putString(type, sb.toString());
        } else {
            sp.putString(type, "");
        }
    }

    public void clear() {
        INSTANCE = null;
    }

    /**
     * 清除所有数据
     */
    public void removeAllHistory() {
        sp.clear();
    }
}
