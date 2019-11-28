package com.project.zhongrenweigong.business.teach;

import android.os.Bundle;
import android.os.Parcelable;
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
import com.project.zhongrenweigong.business.bean.TeachDataBean;
import com.project.zhongrenweigong.business.bean.TeachListBean;
import com.project.zhongrenweigong.business.bean.TeacherDataBean;
import com.project.zhongrenweigong.business.bean.TeacherListBean;
import com.project.zhongrenweigong.business.teach.adapter.TeachListAdapter;
import com.project.zhongrenweigong.business.teach.adapter.TeacherListAdapter;
import com.project.zhongrenweigong.currency.event.RefreshIndustrySearchEvent;
import com.project.zhongrenweigong.mine.MineHomePageActivity;
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
public class TeachListFragment extends BaseFragment<TeachListFrgementPresent> {
    Unbinder unbinder;
    @BindView(R.id.recy_list)
    RecyclerView recyTeachList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int index;
    private int currentPage = 1;
    private TeachListAdapter teachListAdapter;
    private TeacherListAdapter teacherListAdapter;
    private String teachName = "";
    private String teacherName = "";
    private String lat = "1";
    private String lng = "1";
    private String province;
    private BDLocation bdLocation;

    public static TeachListFragment getInstance(int index, BDLocation bdLocation) {
        TeachListFragment homePageXinXiFragment = new TeachListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putParcelable("bdLocation", bdLocation);
        homePageXinXiFragment.setArguments(bundle);
        return homePageXinXiFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        bdLocation = bundle.getParcelable("bdLocation");
        recyTeachList.setLayoutManager(new LinearLayoutManager(getContext()));
        if (index == 0) {
            teachListAdapter = new TeachListAdapter(R.layout.item_teach_mechanism_list);
            recyTeachList.setAdapter(teachListAdapter);
            teachListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    TeachDataBean item = teachListAdapter.getItem(position);
                    String shopId = item.shopId;
                    Router.newIntent(getActivity())
                            .putString("shopId", shopId)
                            .putInt("shopType", BusinessHomePageActivity.SHOP_TYPE_TEACH)
                            .to(BusinessHomePageActivity.class)
                            .launch();
                }
            });
        } else {
            teacherListAdapter = new TeacherListAdapter(R.layout.item_teach_personal_list);
            recyTeachList.setAdapter(teacherListAdapter);
            teacherListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    TeacherDataBean item = teacherListAdapter.getItem(position);
                    String mbId = item.mbId;
                    Router.newIntent(getActivity())
                            .putString("mbId", mbId)
                            .to(MineHomePageActivity.class)
                            .launch();
                }
            });
        }

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                if (index == 0) {
                    getEducationTeach(teachName);
                } else {
                    getEducationTeacher(teacherName);
                }
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                if (index == 0) {
                    getEducationTeach(teachName);
                } else {
                    getEducationTeacher(teacherName);
                }
            }
        });

    }

    public void getEducationTeach(String name) {
        teachName = name;
        if (province == null || province.equals("")) {
            getDataError();
            return;
        }
        getP().getEducationTeach(currentPage, name, province, lat, lng);
    }

    public void getEducationTeacher(String name) {
        teacherName = name;
        if (province == null || province.equals("")) {
            getDataError();
            return;
        }
        getP().getEducationTeacher(currentPage, name, province, lat, lng);
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
            getEducationTeach("");
        } else if (index == 1) {
            getEducationTeacher("");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public TeachListFrgementPresent bindPresent() {
        return new TeachListFrgementPresent();
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

            if (refreshIndustrySearchEvent.index == 0) {//搜索机构
                getEducationTeach(searchText);
            } else if (refreshIndustrySearchEvent.index == 1) {//搜索个人
                getEducationTeacher(searchText);
            }else if (refreshIndustrySearchEvent.index == 2) {//全部数据
                if (index == 0) {
                    getEducationTeach(searchText);
                } else if (index == 1) {
                    getEducationTeacher(searchText);
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

    public void setTeacherData(TeacherListBean teacherListBean) {
        if (teacherListAdapter == null) {
            return;
        }
        int pageSize = teacherListBean.pageSize;
        List<TeacherDataBean> data = teacherListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                teacherListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                teacherListAdapter.addData(data);
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
            if (index == 0) {
                QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, teachListAdapter, smRefresh);
            } else if (index == 1) {
                QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, teacherListAdapter, smRefresh);
            }
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }

    public void setTeachData(TeachListBean teachListBean) {
        if (teachListAdapter == null) {
            return;
        }
        int pageSize = teachListBean.pageSize;
        List<TeachDataBean> data = teachListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                teachListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                teachListAdapter.addData(data);
                smRefresh.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        } else {
            getDataError();
        }

        if (currentPage == pageSize) {
            smRefresh.setEnableLoadMore(false);
        }
    }
}
