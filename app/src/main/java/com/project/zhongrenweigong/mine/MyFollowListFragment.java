package com.project.zhongrenweigong.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.BusinessHomePageActivity;
import com.project.zhongrenweigong.business.bean.IndustryDataBean;
import com.project.zhongrenweigong.business.bean.IndustryListBean;
import com.project.zhongrenweigong.business.car.CarListFrgementPresent;
import com.project.zhongrenweigong.currency.event.RefreshIndustrySearchEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.adapter.MyFollowListAdapter;
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

import static com.project.zhongrenweigong.business.BusinessHomePageActivity.SHOP_TYPE_CAR;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MyFollowListFragment extends BaseFragment<MyFollowListFrgementPresent> {
    Unbinder unbinder;
    @BindView(R.id.recy_list)
    RecyclerView recyCarList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int index;
    private int currentPage = 1;
    private MyFollowListAdapter followListAdapter;
    private String lat = "1";
    private String lng = "1";
    private BDLocation bdLocation;
    private String categoryId;
    private LoginMsg userAccent;

    public static MyFollowListFragment getInstance(int index, BDLocation bdLocation, String categoryId) {//String shopId
        MyFollowListFragment carListFragment = new MyFollowListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putParcelable("bdLocation", bdLocation);
        bundle.putString("categoryId", categoryId);
        carListFragment.setArguments(bundle);
        return carListFragment;
    }

    @Override
    public void initView() {
        userAccent = AcacheUtils.getInstance(getContext()).getUserAccent();
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        bdLocation = bundle.getParcelable("bdLocation");
        categoryId = bundle.getString("categoryId");

        recyCarList.setLayoutManager(new LinearLayoutManager(getContext()));
        followListAdapter = new MyFollowListAdapter(R.layout.item_industry_list);
        recyCarList.setAdapter(followListAdapter);
        followListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndustryDataBean item = followListAdapter.getItem(position);
                String shopId = item.shopId;
                Router.newIntent(getActivity())
                        .putString("shopId", shopId)
                        .putInt("shopType", Integer.parseInt(categoryId))
                        .to(BusinessHomePageActivity.class)
                        .launch();
            }
        });

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                getLikeShopByCategoryId();
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                getLikeShopByCategoryId();
            }
        });
    }

    public void getLikeShopByCategoryId() {
        getP().getLikeShopByCategoryId(currentPage, categoryId, userAccent.mbId, lat, lng);
    }

    @Override
    public void initAfter() {
        if (bdLocation != null) {
            lat = String.valueOf(bdLocation.getLatitude());
            lng = String.valueOf(bdLocation.getLongitude());
        }
        getLikeShopByCategoryId();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public MyFollowListFrgementPresent bindPresent() {
        return new MyFollowListFrgementPresent();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setFollowData(IndustryListBean industryListBean) {
        int pageSize = industryListBean.pageSize;
        List<IndustryDataBean> data = industryListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                followListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                followListAdapter.addData(data);
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
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, followListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }
}
