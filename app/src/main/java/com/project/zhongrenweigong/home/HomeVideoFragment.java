package com.project.zhongrenweigong.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.adapter.BusinessWorkerListAdapter;
import com.project.zhongrenweigong.home.adapter.HomeVideoListAdapter;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
import com.project.zhongrenweigong.home.bean.HomeVideoBean;
import com.project.zhongrenweigong.home.bean.HomeVideoDataBean;
import com.project.zhongrenweigong.home.bean.NewsDataBean;
import com.project.zhongrenweigong.home.bean.NewsDataMultiItemEntity;
import com.project.zhongrenweigong.util.QueShengManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeVideoFragment extends BaseFragment<HomeVideoPresent> {
    Unbinder unbinder;
    @BindView(R.id.recy_list)
    RecyclerView recyVideoList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int index;
    private int currentPage = 1;;
    private HomeVideoListAdapter homeVideoListAdapter;

    public static HomeVideoFragment getInstance(int index) {
        HomeVideoFragment homePageXinXiFragment = new HomeVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        homePageXinXiFragment.setArguments(bundle);
        return homePageXinXiFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        recyVideoList.setLayoutManager(new LinearLayoutManager(getContext()));
        homeVideoListAdapter = new HomeVideoListAdapter(R.layout.item_journalism_video);
        recyVideoList.setAdapter(homeVideoListAdapter);
        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                initAfter();
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                initAfter();
            }
        });
    }

    @Override
    public void initAfter() {
        getP().getVideo(currentPage);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public HomeVideoPresent bindPresent() {
        return new HomeVideoPresent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    public void setData(HomeVideoBean homeVideoBean) {
        int pageSize = homeVideoBean.pageSize;
        List<HomeVideoDataBean> data = homeVideoBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                homeVideoListAdapter.setNewData(data);
            } else {
                homeVideoListAdapter.addData(data);
            }
        } else {
            getDataError();
        }
        if (currentPage == pageSize) {
            smRefresh.setEnableLoadMore(false);
        }
    }

    public void getDataError() {
        if (currentPage == 1) {
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, homeVideoListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
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
