package com.project.zhongrenweigong.business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.baidu.location.BDLocation;
import com.project.zhongrenweigong.business.bean.BusinessTypeDataBean;
import com.project.zhongrenweigong.business.hotel.HotelListFragment;
import com.project.zhongrenweigong.business.manager.MineShopListFragment;

import java.util.List;


public class MineShopListPageAdapter extends FragmentPagerAdapter {
//    private String[] titles = new String[]{"餐饮", "医疗", "教育", "房地产", "电商", "酒店"};
    private List<BusinessTypeDataBean> data;

    public MineShopListPageAdapter(FragmentManager fm,List<BusinessTypeDataBean> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MineShopListFragment.getInstance(0, data.get(position).categoryId);
            case 1:
                return MineShopListFragment.getInstance(1, data.get(position).categoryId);
            case 2:
                return MineShopListFragment.getInstance(2, data.get(position).categoryId);
            case 3:
                return MineShopListFragment.getInstance(3, data.get(position).categoryId);
            case 4:
                return MineShopListFragment.getInstance(4, data.get(position).categoryId);
            case 5:
                return MineShopListFragment.getInstance(5, data.get(position).categoryId);
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
