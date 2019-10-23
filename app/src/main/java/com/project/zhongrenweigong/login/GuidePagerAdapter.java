package com.project.zhongrenweigong.login;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 作者：Fuduo on 2019/10/23 09:54
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class GuidePagerAdapter extends PagerAdapter {

    private ArrayList<View> views;

    public GuidePagerAdapter(ArrayList<View> views) {
        this.views = views;
    }


    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object arg1) {
        return (view == arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
