package com.project.zhongrenweigong.business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhongrenweigong.business.BusinessDynamicFragment;
import com.project.zhongrenweigong.business.HomePageXinXiFragment;


public class HomePageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"信息", "动态"};
    private String shopId;

    public HomePageAdapter(FragmentManager fm,String shopId) {
        super(fm);
        this.shopId = shopId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomePageXinXiFragment.getInstance(shopId);
            case 1:
                return new BusinessDynamicFragment();
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
