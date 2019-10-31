package com.project.zhongrenweigong.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Fuduo on 2019/10/29 10:42
 * 邮箱：duoendeavor@163.com
 * 意图：
 */

public class CompensationRecordFragement extends BaseFragment<CompensationRecordPresent> {

    @BindView(R.id.recy_compensation_list)
    RecyclerView recyCompensationList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    Unbinder unbinder;
    private int index;

    public static CompensationRecordFragement getInstance(int index){
        CompensationRecordFragement fragement = new CompensationRecordFragement();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        fragement.setArguments(bundle);
        return fragement;
    }

    @Override
    public void initView() {
        Bundle arguments = getArguments();
        index = arguments.getInt("index");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_compensation_layout;
    }

    @Override
    public CompensationRecordPresent bindPresent() {
        return new CompensationRecordPresent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
