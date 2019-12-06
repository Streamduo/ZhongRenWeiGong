package com.project.zhongrenweigong.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhongrenweigong.home.HomeCityFragment;
import com.project.zhongrenweigong.home.HomeNewsFragment;
import com.project.zhongrenweigong.home.HomeRecommendFragment;
import com.project.zhongrenweigong.home.HomeVideoFragment;
import com.project.zhongrenweigong.home.MessageFragment;
import com.project.zhongrenweigong.square.VideoSquareFragement;


public class HomeMainPageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"推荐","新闻","视频"};

    public HomeMainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeRecommendFragment.getInstance(0);
            case 1:
                return HomeNewsFragment.getInstance(1);
            case 2:
                return HomeVideoFragment.getInstance(2);
            case 3:
                return HomeCityFragment.getInstance(3);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
