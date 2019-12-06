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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.home.adapter.HomeRecommedListAdapter;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
import com.project.zhongrenweigong.home.bean.NewsDataBean;
import com.project.zhongrenweigong.home.bean.NewsDataMultiItemEntity;
import com.project.zhongrenweigong.home.viewholder.HomeRecommedHeader;
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
    private List<NewsDataMultiItemEntity> datas = new ArrayList<>();
    private HomeRecommedListAdapter homeRecommedListAdapter;
    private NewsDataMultiItemEntity newsItem;
    private HomeRecommedHeader homeRecommedHeader;

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
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyJournalismList.setLayoutManager(linearLayoutManager);
        homeRecommedListAdapter = new HomeRecommedListAdapter(datas);
        recyJournalismList.setAdapter(homeRecommedListAdapter);
        homeRecommedListAdapter.setHeaderView(homeRecommedHeader.headerView);
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
                switch (view.getId()) {
                    case R.id.te_share_journalism:
                        showShareDialog(item);
                        break;
                }
            }
        });
        recyJournalismList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(homeRecommedListAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if (!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            homeRecommedListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private void initTopView(){
        if(getActivity()==null){
            return;
        }
        homeRecommedHeader = new HomeRecommedHeader(getContext(),getActivity());

    }

    private void showShareDialog(NewsDataMultiItemEntity item) {
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
        getP().getNewsList(currentPage);

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
                Router.newIntent(getActivity())
                        .putString("journalismId", newsItem.newsDataBean.journalismId)
                        .to(NewsReportActivity.class)
                        .launch();
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
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            GSYVideoManager.onPause();
        }
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
                smRefresh.finishRefresh(1000/*,false*/);
            } else {
                homeRecommedListAdapter.addData(newsDataMultiItemEntities);
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
//            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, homeRecommedListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }

}
