package com.project.zhongrenweigong.business.hotel;

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
import com.project.zhongrenweigong.business.hotel.adapter.HotelListAdapter;
import com.project.zhongrenweigong.currency.event.RefreshIndustrySearchEvent;
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
public class HotelListFragment extends BaseFragment<HotelListFrgementPresent> {
    Unbinder unbinder;
    @BindView(R.id.recy_list)
    RecyclerView recyCarList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int index;
    private int currentPage = 1;
    private HotelListAdapter hotelListAdapter;
    private String teachName = "";
    private String lat = "1";
    private String lng = "1";
    private String province;
    private BDLocation bdLocation;

    public static HotelListFragment getInstance(int index, BDLocation bdLocation) {//String shopId
        HotelListFragment carListFragment = new HotelListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putParcelable("bdLocation", bdLocation);
        carListFragment.setArguments(bundle);
        return carListFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        bdLocation = bundle.getParcelable("bdLocation");
        recyCarList.setLayoutManager(new LinearLayoutManager(getContext()));
        hotelListAdapter = new HotelListAdapter(R.layout.item_industry_list);
        recyCarList.setAdapter(hotelListAdapter);
        hotelListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndustryDataBean item = hotelListAdapter.getItem(position);
                String shopId = item.shopId;
                Router.newIntent(getActivity())
                        .putString("shopId", shopId)
                        .putInt("shopType", BusinessHomePageActivity.SHOP_TYPE_HOTEL)
                        .to(BusinessHomePageActivity.class)
                        .launch();
            }
        });

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                if (index == 0) {
                    getHotel(teachName, 0);
                } else {
                    getHotel(teachName, 1);
                }
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                if (index == 0) {
                    getHotel(teachName, 0);
                } else {
                    getHotel(teachName, 1);
                }
            }
        });

    }

    public void getHotel(String name, int type) {
        teachName = name;
        if (province == null || province.equals("")) {
            getDataError();
            return;
        }
        getP().getHotel(currentPage, name, province, type, lat, lng);
    }

    @Override
    public void initAfter() {
        if (bdLocation == null) {
            return;
        }
        lat = String.valueOf(bdLocation.getLatitude());
        lng = String.valueOf(bdLocation.getLongitude());
        province = bdLocation.getProvince();
        if (index == 0) {
            getHotel(teachName, 0);
        } else if (index == 1) {
            getHotel(teachName, 1);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public HotelListFrgementPresent bindPresent() {
        return new HotelListFrgementPresent();
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
        if (refreshIndustrySearchEvent != null) {
            currentPage = 1;
            lat = refreshIndustrySearchEvent.lat;
            lng = refreshIndustrySearchEvent.lng;
            province = refreshIndustrySearchEvent.province;
            String searchText = refreshIndustrySearchEvent.searchText;

            if (refreshIndustrySearchEvent.index == index) {
                getHotel(searchText, index);
            }
            if (refreshIndustrySearchEvent.index == 2) {//全部数据
                if (index == 0) {
                    getHotel(searchText, 0);
                } else if (index == 1) {
                    getHotel(searchText, 1);
                }
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
        if (hotelListAdapter == null) {
            return;
        }
        int pageSize = industryListBean.pageSize;
        List<IndustryDataBean> data = industryListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                hotelListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                hotelListAdapter.addData(data);
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
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, hotelListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }


}
