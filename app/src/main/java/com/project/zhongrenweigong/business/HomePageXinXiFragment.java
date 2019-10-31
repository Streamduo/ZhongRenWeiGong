package com.project.zhongrenweigong.business;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomePageXinXiFragment extends BaseFragment<HomePageXinXiPresent> {
    @BindView(R.id.recy_faren_list)
    RecyclerView recyFarenList;
    @BindView(R.id.recy_worker_list)
    RecyclerView recyWorkerList;
    @BindView(R.id.te_shop_address)
    TextView teShopAddress;
    @BindView(R.id.recy_jc_people_list)
    RecyclerView recyJcPeopleList;
    Unbinder unbinder;
    @BindView(R.id.recy_zizhi)
    RecyclerView recyZizhi;
    @BindView(R.id.recy_testing)
    RecyclerView recyTesting;

    @Override
    public void initView() {

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_homepage_xinxi;
    }

    @Override
    public HomePageXinXiPresent bindPresent() {
        return new HomePageXinXiPresent();
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
