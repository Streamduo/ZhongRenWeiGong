package com.project.zhongrenweigong.mine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhongrenweigong.business.BusinessDynamicFragment;
import com.project.zhongrenweigong.business.HomePageXinXiFragment;
import com.project.zhongrenweigong.mine.CompensationRecordFragement;


public class CompensationRecordPageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"全部", "收入", "支出"};

    public CompensationRecordPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CompensationRecordFragement.getInstance(0);
            case 1:
                return CompensationRecordFragement.getInstance(1);
            case 2:
                return CompensationRecordFragement.getInstance(2);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
