package com.project.zhongrenweigong.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhongrenweigong.business.BusinessDynamicFragment;
import com.project.zhongrenweigong.business.HomePageXinXiFragment;
import com.project.zhongrenweigong.home.VideoSquareFragement;


public class SquareVideoPageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"推荐", "关注"};

    public SquareVideoPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new VideoSquareFragement();
            case 1:
                return new VideoSquareFragement();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
