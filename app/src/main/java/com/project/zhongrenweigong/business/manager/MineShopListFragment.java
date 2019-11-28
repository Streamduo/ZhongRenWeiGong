package com.project.zhongrenweigong.business.manager;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.BusinessHomePageActivity;
import com.project.zhongrenweigong.business.adapter.InsdustryListAdapter;
import com.project.zhongrenweigong.business.bean.IndustryDataBean;
import com.project.zhongrenweigong.business.bean.IndustryListBean;

import com.project.zhongrenweigong.currency.event.RefreshIndustrySearchEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.QueShengManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.router.Router;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MineShopListFragment extends BaseFragment<MineShopListFragmentPresent> {
    Unbinder unbinder;
    @BindView(R.id.recy_list)
    RecyclerView recyCarList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int index;
    private int currentPage = 1;
    private InsdustryListAdapter insdustryListAdapter;
    private String teachName = "";
    private String categoryId;
    private LoginMsg userAccent;

    public static MineShopListFragment getInstance(int index, String categoryId) {//String shopId
        MineShopListFragment carListFragment = new MineShopListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("categoryId", categoryId);
        carListFragment.setArguments(bundle);
        return carListFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        categoryId = bundle.getString("categoryId");
        userAccent = AcacheUtils.getInstance(getContext()).getUserAccent();
        recyCarList.setLayoutManager(new LinearLayoutManager(getContext()));
        insdustryListAdapter = new InsdustryListAdapter(R.layout.item_industry_list);
        recyCarList.setAdapter(insdustryListAdapter);
        insdustryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndustryDataBean item = insdustryListAdapter.getItem(position);
                Router.newIntent(getActivity())
                        .putSerializable("IndustryDataBean", item)
                        .to(BusinessManagerActivity.class)
                        .launch();
            }
        });

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                if (index == 0) {
                    getShopByCategoryId(teachName);
                } else {
                    getShopByCategoryId(teachName);
                }
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                if (index == 0) {
                    getShopByCategoryId(teachName);
                } else {
                    getShopByCategoryId(teachName);
                }
            }
        });

    }

    public void getShopByCategoryId(String name) {
        teachName = name;
        getP().getShopByCategoryId(currentPage, name, categoryId, userAccent.mbId);
    }

    @Override
    public void initAfter() {
        getShopByCategoryId(teachName);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public MineShopListFragmentPresent bindPresent() {
        return new MineShopListFragmentPresent();
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
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshIndustrySearchEvent refreshIndustrySearchEvent) {
        if (refreshIndustrySearchEvent.searchText != null) {
            currentPage = 1;
            String searchText = refreshIndustrySearchEvent.searchText;

            if (refreshIndustrySearchEvent.index == index) {
                getShopByCategoryId(searchText);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setData(IndustryListBean industryListBean) {
        if (insdustryListAdapter == null) {
            return;
        }
        int pageSize = industryListBean.pageSize;
        List<IndustryDataBean> data = industryListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                insdustryListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                insdustryListAdapter.addData(data);
                smRefresh.finishLoadMore(1000/*,false*/);//传入false表示加载失败
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
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, insdustryListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }


}
