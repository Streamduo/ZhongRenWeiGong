package com.project.zhongrenweigong.square.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhongrenweigong.square.VideoSquareFragement;


public class SquareVideoPageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"推荐", "关注"};

    public SquareVideoPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return VideoSquareFragement.getInstance(0);
            case 1:
                return VideoSquareFragement.getInstance(1);
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
