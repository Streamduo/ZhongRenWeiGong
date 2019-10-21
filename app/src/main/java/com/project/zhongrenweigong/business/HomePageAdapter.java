package com.project.zhongrenweigong.business;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class HomePageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"信息", "动态", "活动"};


    public HomePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomePageXinXiFragment();
//            case 1:
//                return new FollowFragment();
//            case 2:
//                return new NewsFragment();
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
