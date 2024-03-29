package com.project.zhongrenweigong.business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.baidu.location.BDLocation;
import com.project.zhongrenweigong.business.BusinessDynamicFragment;
import com.project.zhongrenweigong.business.HomePageXinXiFragment;
import com.project.zhongrenweigong.business.teach.TeachListFragment;


public class TeachListPageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"机构", "个人"};
    private BDLocation bdLocation;

    public TeachListPageAdapter(FragmentManager fm, BDLocation bdLocation) {
        super(fm);
        this.bdLocation = bdLocation;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TeachListFragment.getInstance(0, bdLocation);
            case 1:
                return TeachListFragment.getInstance(1, bdLocation);
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
