package com.project.zhongrenweigong.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.adapter.BusinessWorkerListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MineShopListFragment extends BaseFragment<MineShopListPresent> {
    Unbinder unbinder;
    @BindView(R.id.recy_shop_list)
    RecyclerView recyShopList;
    private int index;

    public static MineShopListFragment getInstance(int index) {//String shopId
        MineShopListFragment homePageXinXiFragment = new MineShopListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        homePageXinXiFragment.setArguments(bundle);
        return homePageXinXiFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        recyShopList.setLayoutManager(new LinearLayoutManager(getContext()));
//        workerListAdapter = new BusinessWorkerListAdapter(R.layout.item_people_list);
//        recyShopList.setAdapter(workerListAdapter);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_shop_list;
    }

    @Override
    public MineShopListPresent bindPresent() {
        return new MineShopListPresent();
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
