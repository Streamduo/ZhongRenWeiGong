package com.project.zhongrenweigong.square;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.square.adapter.SquareListAdapter;
import com.project.zhongrenweigong.square.bean.SquareDataBean;
import com.project.zhongrenweigong.square.bean.SquareListBean;
import com.project.zhongrenweigong.util.XCache;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Fuduo on 2019/10/24 11:52
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class VideoSquareFragement extends BaseFragment<VideoSquarePresent> {

    @BindView(R.id.recy_video_list)
    RecyclerView recyVideoList;
    Unbinder unbinder;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int squareType;
    private int currentPage = 1;
    private String mbid;
    private SquareListAdapter squareListAdapter;

    public static VideoSquareFragement getInstance(int index) {
        VideoSquareFragement videoSquareFragement = new VideoSquareFragement();
        Bundle bundle = new Bundle();
        bundle.putInt("squareType", index);
        videoSquareFragement.setArguments(bundle);
        return videoSquareFragement;
    }

    @Override
    public void initView() {
        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                getP().getPlazaMsg(currentPage, mbid);
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                getP().getPlazaMsg(currentPage, mbid);
            }
        });
        recyVideoList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        squareListAdapter = new SquareListAdapter(R.layout.item_video_square_list);
        recyVideoList.setAdapter(squareListAdapter);
    }

    @Override
    public void initAfter() {
        Bundle arguments = getArguments();
        squareType = arguments.getInt("squareType");
        if (squareType == 0) {
            mbid = "-1";
        } else {
            XCache cache = new XCache.Builder(getActivity()).build();
            LoginMsg loginMsg = (LoginMsg) cache.getObject(Constans.USERACCENT);
            if (loginMsg != null) {
                mbid = loginMsg.mbId;
            }
        }
        getP().getPlazaMsg(currentPage, mbid);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_video_square_list;
    }

    @Override
    public VideoSquarePresent bindPresent() {
        return new VideoSquarePresent();
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

    public void setData(SquareListBean squareListBean) {
        int pageSize = squareListBean.getPageSize();
        List<SquareDataBean> data = squareListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                squareListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                smRefresh.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                squareListAdapter.addData(data);
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
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }

}
