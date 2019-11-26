package com.project.zhongrenweigong.util;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;

/**
 * 作者：Fuduo on 2019/11/26 09:02
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class QueShengManager {
    public static final int QUESHENG_TYPE_1 = 0;

    public static void setEmptyView(int type, BaseQuickAdapter baseQuickAdapter, ViewGroup viewGroup) {
        switch (type) {
            case 0:
                baseQuickAdapter.setEmptyView(R.layout.layout_no_data_quesheng, viewGroup);
                break;
        }
    }
}
