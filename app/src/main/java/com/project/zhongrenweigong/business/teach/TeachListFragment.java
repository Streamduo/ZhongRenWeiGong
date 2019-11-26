package com.project.zhongrenweigong.business.teach;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.adapter.BusinessWorkerListAdapter;
import com.project.zhongrenweigong.business.bean.TeachDataBean;
import com.project.zhongrenweigong.business.bean.TeachListBean;
import com.project.zhongrenweigong.business.bean.TeacherDataBean;
import com.project.zhongrenweigong.business.bean.TeacherListBean;
import com.project.zhongrenweigong.business.teach.adapter.TeachListAdapter;
import com.project.zhongrenweigong.business.teach.adapter.TeacherListAdapter;
import com.project.zhongrenweigong.currency.event.RefreshHomeEvent;
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

    public static TeachListFragment getInstance(int index) {//String shopId
        TeachListFragment homePageXinXiFragment = new TeachListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        homePageXinXiFragment.setArguments(bundle);
        return homePageXinXiFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        recyTeachList.setLayoutManager(new LinearLayoutManager(getContext()));
        if (index == 0) {
            teachListAdapter = new TeachListAdapter(R.layout.item_teach_mechanism_list);
            recyTeachList.setAdapter(teachListAdapter);
            teachListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                }
            });
        } else {
            teacherListAdapter = new TeacherListAdapter(R.layout.item_teach_personal_list);
            recyTeachList.setAdapter(teacherListAdapter);
            teacherListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

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
        String province = ((TeachListActivity) getActivity()).province;
        if (province == null || province.equals("")) {
            getDataError();
            return;
        }
        getP().getEducationTeach(currentPage, name, province);
    }

    public void getEducationTeacher(String name) {
        teacherName = name;
        String province = ((TeachListActivity) getActivity()).province;
        if (province == null || province.equals("")) {
            getDataError();
            return;
        }
        getP().getEducationTeacher(currentPage, name, province);
    }

    @Override
    public void initAfter() {
        if (index == 0) {
            getEducationTeach(teachName);
        } else if (index == 1) {
            getEducationTeacher(teacherName);
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
    public void onEventMainThread(SearchEvent searchEvent) {
        if (searchEvent.searchText != null) {
            currentPage = 1;
            if (searchEvent.index == 0) {
                getEducationTeach(searchEvent.searchText);
            } else {
                getEducationTeacher(searchEvent.searchText);
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
