package com.project.zhongrenweigong.business.commerce;

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
import com.project.zhongrenweigong.business.bean.IndustryDataBean;
import com.project.zhongrenweigong.business.bean.IndustryListBean;
import com.project.zhongrenweigong.business.car.CarListActivity;
import com.project.zhongrenweigong.business.commerce.adapter.CommerecListAdapter;
import com.project.zhongrenweigong.currency.event.SearchEvent;
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
public class CommerecListFragment extends BaseFragment<CommerecListFrgementPresent> {
    Unbinder unbinder;
    @BindView(R.id.recy_list)
    RecyclerView recyCarList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int index;
    private int currentPage = 1;
    private CommerecListAdapter commerecListAdapter;
    private String teachName = "";

    public static CommerecListFragment getInstance(int index) {//String shopId
        CommerecListFragment carListFragment = new CommerecListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        carListFragment.setArguments(bundle);
        return carListFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        recyCarList.setLayoutManager(new LinearLayoutManager(getContext()));
        commerecListAdapter = new CommerecListAdapter(R.layout.item_car_shop_list);
        recyCarList.setAdapter(commerecListAdapter);
        commerecListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndustryDataBean item = commerecListAdapter.getItem(position);
                String shopId = item.shopId;
                Router.newIntent(getActivity())
                        .putString("shopId", shopId)
                        .putInt("shopType", 4)
                        .to(BusinessHomePageActivity.class)
                        .launch();
            }
        });

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                if (index == 0) {
                    getVehicle(teachName, 0);
                } else {
                    getVehicle(teachName, 1);
                }
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                if (index == 0) {
                    getVehicle(teachName, 0);
                } else {
                    getVehicle(teachName, 1);
                }
            }
        });

    }

    public void getVehicle(String name, int type) {
        teachName = name;
        String province = ((CommerecListActivity) getActivity()).province;
        if (province == null || province.equals("")) {
            getDataError();
            return;
        }
        getP().getVehicle(currentPage, name, province, type);
    }

    @Override
    public void initAfter() {
        if (index == 0) {
            getVehicle(teachName, 0);
        } else if (index == 1) {
            getVehicle(teachName, 1);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public CommerecListFrgementPresent bindPresent() {
        return new CommerecListFrgementPresent();
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
    public void onEventMainThread(SearchEvent searchEvent) {
        if (searchEvent.searchText != null) {
            currentPage = 1;
            if (searchEvent.index == 0) {
                getVehicle(searchEvent.searchText, 0);
            } else {
                getVehicle(searchEvent.searchText, 1);
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
        if (commerecListAdapter == null) {
            return;
        }
        int pageSize = industryListBean.pageSize;
        List<IndustryDataBean> data = industryListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                commerecListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                commerecListAdapter.addData(data);
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
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, commerecListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }


}
