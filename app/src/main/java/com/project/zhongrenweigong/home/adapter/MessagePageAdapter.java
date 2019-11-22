package com.project.zhongrenweigong.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhongrenweigong.business.BusinessDynamicFragment;
import com.project.zhongrenweigong.business.HomePageXinXiFragment;
import com.project.zhongrenweigong.message.ActivityMessageFragment;
import com.project.zhongrenweigong.message.SystemMessageFragment;
import com.project.zhongrenweigong.message.VoucherMessageFragment;


public class MessagePageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"系统消息", "活动消息","凭证消息"};

    public MessagePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SystemMessageFragment.getInstance(0);
            case 1:
                return ActivityMessageFragment.getInstance(1);
            case 2:
                return VoucherMessageFragment.getInstance(2);
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
