package com.project.zhongrenweigong.home;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.home.adapter.HomeRecommedListAdapter;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
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
public class HomeNewsFragment extends BaseFragment<HomeNewsPresent> {

    Unbinder unbinder;
    @BindView(R.id.recy_list)
    RecyclerView recyJournalismList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int index;
    private int currentPage = 1;
    private List<NewsDataMultiItemEntity> datas = new ArrayList<>();
    private HomeRecommedListAdapter homeRecommedListAdapter;

    public static HomeNewsFragment getInstance(int index) {
        HomeNewsFragment homePageXinXiFragment = new HomeNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        homePageXinXiFragment.setArguments(bundle);
        return homePageXinXiFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        recyJournalismList.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecommedListAdapter = new HomeRecommedListAdapter(datas);
        recyJournalismList.setAdapter(homeRecommedListAdapter);
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
        recyJournalismList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDataMultiItemEntity item = homeRecommedListAdapter.getItem(position);
                switch (view.getId()){
                    case R.id.te_share_journalism:
                        showShareDialog();
                        break;
                    case R.id.rl_play:

                        break;
                }
            }
        });
    }

    private void showShareDialog() {
        final Dialog shareDialog = new Dialog(getContext(), R.style.dialog_bottom_full);
        shareDialog.setCanceledOnTouchOutside(true);
        shareDialog.setCancelable(true);
        Window window = shareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        View view = View.inflate(getContext(), R.layout.dialog_layout_share, null);
        TextView teShareQq = (TextView) view.findViewById(R.id.te_share_qq);
        TextView teShareQqZone = (TextView) view.findViewById(R.id.te_share_qq_zone);
        TextView teShareWx = (TextView) view.findViewById(R.id.te_share_wx);
        TextView teSharePyq = (TextView) view.findViewById(R.id.te_share_pyq);
        TextView teShareDingding = (TextView) view.findViewById(R.id.te_share_dingding);
        TextView teShareWb = (TextView) view.findViewById(R.id.te_share_wb);
        TextView teShareCopy = (TextView) view.findViewById(R.id.te_share_copy);
        TextView teCancel = (TextView) view.findViewById(R.id.te_cancel);

        teShareQq.setOnClickListener(this);
        teShareQqZone.setOnClickListener(this);
        teShareWx.setOnClickListener(this);
        teSharePyq.setOnClickListener(this);
        teShareDingding.setOnClickListener(this);
        teShareWb.setOnClickListener(this);
        teShareCopy.setOnClickListener(this);
        teCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        shareDialog.show();
    }

    @Override
    public void initAfter() {
        getP().getNewsList(currentPage);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public HomeNewsPresent bindPresent() {
        return new HomeNewsPresent();
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

    public void setData(HomeRecommendBean homeRecommendBean) {
        int pageSize = homeRecommendBean.pageSize;
        List<NewsDataBean> data = homeRecommendBean.getData();
        if (data != null && data.size() > 0) {
            List<NewsDataMultiItemEntity> newsDataMultiItemEntities = new ArrayList<>();
            for (NewsDataBean newsDataBean : data) {
                newsDataMultiItemEntities.add(new NewsDataMultiItemEntity(newsDataBean));
            }
            if (currentPage == 1) {
                homeRecommedListAdapter.setNewData(newsDataMultiItemEntities);
            }else {
                homeRecommedListAdapter.addData(newsDataMultiItemEntities);
            }
        }else {
            getDataError();
        }
        if (currentPage == pageSize) {
            smRefresh.setEnableLoadMore(false);
        }
    }

    public void getDataError() {
        if (currentPage == 1) {
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, homeRecommedListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }
}
