package com.project.zhongrenweigong.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.home.adapter.HomeRecommedListAdapter;
import com.project.zhongrenweigong.home.adapter.RecommedGoodListAdapter;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
import com.project.zhongrenweigong.home.bean.NewsBean;
import com.project.zhongrenweigong.home.bean.NewsDataBean;
import com.project.zhongrenweigong.home.bean.NewsDataMultiItemEntity;
import com.project.zhongrenweigong.home.bean.RecommedBean;
import com.project.zhongrenweigong.home.bean.RecommedTopBean;
import com.project.zhongrenweigong.home.viewholder.HomeRecommedHeader;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.QueShengManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.router.Router;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeRecommendFragment extends BaseFragment<HomeRecommendPresent> {

    Unbinder unbinder;
    @BindView(R.id.recy_list)
    RecyclerView recyJournalismList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int index;
    private int currentPage = 1;
    private RecommedGoodListAdapter recommedGoodListAdapter;
    private NewsBean newsItem;
    private HomeRecommedHeader homeRecommedHeader;
    private String mbId;

    public static HomeRecommendFragment getInstance(int index) {
        HomeRecommendFragment homePageXinXiFragment = new HomeRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        homePageXinXiFragment.setArguments(bundle);
        return homePageXinXiFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        initTopView();
        LoginMsg userAccent = AcacheUtils.getInstance(getContext()).getUserAccent();
        if (userAccent == null) {
            mbId = "4545455445";
        } else {
            mbId = userAccent.mbId;
        }
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyJournalismList.setLayoutManager(linearLayoutManager);
        recommedGoodListAdapter = new RecommedGoodListAdapter(R.layout.item_good_things_list);
        recyJournalismList.setAdapter(recommedGoodListAdapter);
        recommedGoodListAdapter.setHeaderView(homeRecommedHeader.headerView);
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
                getP().getGoodDeedMessage(mbId, currentPage);
            }
        });
        recommedGoodListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsBean item = recommedGoodListAdapter.getItem(position);
                Router.newIntent(getActivity())
                        .putString("type", "0")
                        .putSerializable("newsbean", item)
                        .to(NewsDetailActivity.class).launch();
            }
        });
        recyJournalismList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NewsBean item = recommedGoodListAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.te_good_things_share:
                        showShareDialog(item);
                        break;
                }
            }
        });

    }

    private void initTopView() {
        if (getActivity() == null) {
            return;
        }
        homeRecommedHeader = new HomeRecommedHeader(getContext(), getActivity());
        homeRecommedHeader.setOnShareClickListener(new HomeRecommedHeader.onShareClickListener() {
            @Override
            public void onClick(NewsBean newsBean) {
                showShareDialog(newsBean);
            }
        });
    }

    private void showShareDialog(NewsBean item) {
        newsItem = item;
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
        TextView teShareSys = (TextView) view.findViewById(R.id.te_share_sys);
        TextView teShareCopy = (TextView) view.findViewById(R.id.te_share_copy);
        TextView teCancel = (TextView) view.findViewById(R.id.te_cancel);
        RelativeLayout rlParent = (RelativeLayout) view.findViewById(R.id.rl_parent);
        rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.dismiss();
            }
        });
        teShareQq.setOnClickListener(this);
        teShareQqZone.setOnClickListener(this);
        teShareWx.setOnClickListener(this);
        teSharePyq.setOnClickListener(this);
        teShareDingding.setOnClickListener(this);
        teShareWb.setOnClickListener(this);
        teShareCopy.setOnClickListener(this);
        teShareSys.setOnClickListener(this);
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
        getP().getGoodDeedMessage(mbId, currentPage);
        getP().getTopData(mbId);
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public HomeRecommendPresent bindPresent() {
        return new HomeRecommendPresent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_share_qq:
//                Router.newIntent(getActivity())
//                        .putString("journalismId", newsItem.newsDataBean.journalismId)
//                        .to(NewsReportActivity.class)
//                        .launch();
                break;
            case R.id.te_share_qq_zone:
                break;
            case R.id.te_share_wx:
                break;
            case R.id.te_share_pyq:
                break;
            case R.id.te_share_dingding:
                break;
            case R.id.te_share_wb:
                break;
            case R.id.te_share_copy:
                break;
            case R.id.te_share_sys:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Here is the Shared text.");
                //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
                shareIntent = Intent.createChooser(shareIntent, "Here is the title of Select box");
                startActivity(shareIntent);
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //
//    private void resolveData() {
//        for (int i = 0; i < 19; i++) {
//            VideoModel videoModel = new VideoModel();
//            dataList.add(videoModel);
//        }
//        if (recyclerBaseAdapter != null)
//            recyclerBaseAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setData(RecommedBean recommedBean) {
        int pageSize = recommedBean.pageSize;
        List<NewsBean> data = recommedBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                recommedGoodListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);
            } else {
                recommedGoodListAdapter.addData(data);
                smRefresh.finishRefresh(1000/*,false*/);
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

    public void setTopData(RecommedTopBean recommedTopBean) {
        homeRecommedHeader.setData(recommedTopBean.getData());
    }
}
