package com.project.zhongrenweigong.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;

public class FactoryFragment {

    public static final String TAG = "FactoryFragment";

    private static final String[] TAGS = {HomeFragment.TAG, SquareFragment.TAG, MineFragment.TAG};
    private FragmentManager fragmentManager;
    private int currentIndex = -1;

    public FactoryFragment(Bundle savedInstanceState, FragmentManager manager) {
        this.fragmentManager = manager;
        if (savedInstanceState != null) { // 恢复
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment;
        for (String tag : TAGS) {
            fragment = fragmentManager.findFragmentByTag(tag);
            hideFragment(transaction, fragment);
        }
        transaction.commit();
    }

    private BaseFragment createFragByTag(String tag) {
        switch (tag) {
            case HomeFragment.TAG:
                return new HomeFragment();
            case SquareFragment.TAG:
                return new SquareFragment();
            case MineFragment.TAG:
                return new MineFragment();
        }
        return null;
    }

    public void changeToFragment(int index) {
        if (currentIndex == index) {
            return;
        }
        currentIndex = index;
        String tag = TAGS[index];
        Fragment to = fragmentManager.findFragmentByTag(tag);
        if (to == null) {
            to = createFragByTag(tag);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        if (to != null && to.isAdded()) {
            transaction.show(to);
        } else {
            transaction.add(R.id.main_container, to, tag);
        }

        transaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction transaction, Fragment fragment) {
        if (transaction != null && fragment != null && fragment.isAdded()) {
            transaction.hide(fragment);
        }
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        Fragment fragment;
        for (String tag : TAGS) {
            fragment = fragmentManager.findFragmentByTag(tag);
            hideFragment(transaction, fragment);
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
