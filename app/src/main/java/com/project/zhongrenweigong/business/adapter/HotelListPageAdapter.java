package com.project.zhongrenweigong.business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.baidu.location.BDLocation;
import com.project.zhongrenweigong.business.hotel.HotelListFragment;
import com.project.zhongrenweigong.business.house.HouseListFragment;


public class HotelListPageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"默认排序", "优质酒店"};
    private BDLocation bdLocation;

    public HotelListPageAdapter(FragmentManager fm, BDLocation bdLocation) {
        super(fm);
        this.bdLocation = bdLocation;
    }

    @Override
    public Fragment getItem(int position) {
    switch (position) {
            case 0:
                return HotelListFragment.getInstance(0, bdLocation);
            case 1:
                return HotelListFragment.getInstance(1, bdLocation);
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
