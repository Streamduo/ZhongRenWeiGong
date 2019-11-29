package com.project.zhongrenweigong.business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.baidu.location.BDLocation;
import com.project.zhongrenweigong.business.bean.BusinessTypeDataBean;
import com.project.zhongrenweigong.business.manager.MineShopListFragment;
import com.project.zhongrenweigong.mine.MyFollowListFragment;

import java.util.List;


public class MyFollowListPageAdapter extends FragmentPagerAdapter {
    private List<BusinessTypeDataBean> data;
    private BDLocation bdLocation;

    public MyFollowListPageAdapter(FragmentManager fm, List<BusinessTypeDataBean> data, BDLocation bdLocation) {
        super(fm);
        this.data = data;
        this.bdLocation = bdLocation;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MyFollowListFragment.getInstance(0, bdLocation, data.get(position).categoryId);
            case 1:
                return MyFollowListFragment.getInstance(1, bdLocation, data.get(position).categoryId);
            case 2:
                return MyFollowListFragment.getInstance(2, bdLocation, data.get(position).categoryId);
            case 3:
                return MyFollowListFragment.getInstance(3, bdLocation, data.get(position).categoryId);
            case 4:
                return MyFollowListFragment.getInstance(4, bdLocation, data.get(position).categoryId);
            case 5:
                return MyFollowListFragment.getInstance(5, bdLocation, data.get(position).categoryId);
        }
        return null;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).categoryName;
    }
}
