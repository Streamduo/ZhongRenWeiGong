package com.project.zhongrenweigong.mine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhongrenweigong.business.BusinessDynamicFragment;
import com.project.zhongrenweigong.business.HomePageXinXiFragment;
import com.project.zhongrenweigong.mine.MineShopListFragment;
import com.project.zhongrenweigong.mine.bean.CategoryListsBean;

import java.util.List;


public class ShopListPageAdapter extends FragmentPagerAdapter {
    private List<CategoryListsBean> categoryLists;

    public ShopListPageAdapter(FragmentManager fm,List<CategoryListsBean> categoryLists) {
        super(fm);
        this.categoryLists = categoryLists;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MineShopListFragment.getInstance(categoryLists.get(position).categoryId);
            case 1:
                return MineShopListFragment.getInstance(categoryLists.get(position).categoryId);
            case 2:
                return MineShopListFragment.getInstance(categoryLists.get(position).categoryId);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryLists.get(position).categoryName;
    }
}
