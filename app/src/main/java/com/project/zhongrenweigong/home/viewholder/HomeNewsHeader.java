package com.project.zhongrenweigong.home.viewholder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.project.zhongrenweigong.R;

import butterknife.ButterKnife;

/**
 * 作者：Fuduo on 2019/12/5 17:20
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeNewsHeader {
    private Context context;
    private Activity activity;
    public View headerView;

    public HomeNewsHeader(Context context, Activity activity) {
        headerView = LayoutInflater.from(context).inflate(R.layout.layout_home_news_header, null);
        ButterKnife.bind(this, headerView);
        this.context = context;
        this.activity = activity;
        init();
    }

    private void init() {

    }
}
