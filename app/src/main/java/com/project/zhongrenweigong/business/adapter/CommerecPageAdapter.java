package com.project.zhongrenweigong.business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhongrenweigong.business.car.CarListFragment;
import com.project.zhongrenweigong.business.commerce.CommerecListFragment;


public class CommerecPageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"默认排序", "优质平台"};

    public CommerecPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CommerecListFragment.getInstance(0);
            case 1:
                return CommerecListFragment.getInstance(1);
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
