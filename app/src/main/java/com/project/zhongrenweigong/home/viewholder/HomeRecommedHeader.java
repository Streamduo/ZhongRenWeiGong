package com.project.zhongrenweigong.home.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.NewsDetailActivity;
import com.project.zhongrenweigong.home.adapter.RecommedTopicListAdapter;
import com.project.zhongrenweigong.home.bean.NewsBean;
import com.project.zhongrenweigong.home.bean.RecommedTopDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * 作者：Fuduo on 2019/12/5 17:12
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeRecommedHeader {

    @BindView(R.id.te_placement_title)
    TextView tePlacementTitle;
    @BindView(R.id.img_placement_cover)
    ImageView imgPlacementCover;
    @BindView(R.id.te_sender_date)
    TextView teSenderDate;
    @BindView(R.id.te_placement_share)
    TextView tePlacementShare;
    @BindView(R.id.line_placement)
    LinearLayout linePlacement;
    @BindView(R.id.recy_topic_list)
    RecyclerView recyTopicList;
    @BindView(R.id.img_fengshang_cover)
    ImageView imgFengshangCover;
    @BindView(R.id.rl_play)
    RelativeLayout rlPlay;
    @BindView(R.id.rl_yinying)
    RelativeLayout rlYinying;
    @BindView(R.id.te_fengshang_title)
    TextView teFengshangTitle;
    @BindView(R.id.te_share_fengshang)
    TextView teShareFengshang;
    @BindView(R.id.line_fengshang)
    LinearLayout lineFengshang;
    @BindView(R.id.line_hot_topic)
    LinearLayout lineHotTopic;
    @BindView(R.id.te_small_title)
    TextView teSmallTitle;
    private Context context;
    private Activity activity;
    public View headerView;
    private NewsBean placementBean;
    private onShareClickListener onShareClickListener;
    private RecommedTopicListAdapter listAdapter;
    private NewsBean newsFengShangBean;

    public HomeRecommedHeader(Context context, Activity activity) {
        headerView = LayoutInflater.from(context).inflate(R.layout.layout_home_recommed_header, null);
        ButterKnife.bind(this, headerView);
        this.context = context;
        this.activity = activity;
        init();
    }

    private void init() {
        tePlacementShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareClickListener.onClick(placementBean);
            }
        });
        teShareFengshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareClickListener.onClick(placementBean);
            }
        });
        imgPlacementCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.newIntent(activity)
                        .putString("type","4")
                        .putSerializable("newsbean", placementBean)
                        .to(NewsDetailActivity.class).launch();
            }
        });
        recyTopicList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        listAdapter = new RecommedTopicListAdapter(R.layout.item_hot_topic_list);
        recyTopicList.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsBean item = listAdapter.getItem(position);
                Router.newIntent(activity)
                        .putString("type","5")
                        .putSerializable("newsbean", item)
                        .to(NewsDetailActivity.class).launch();
            }
        });
        recyTopicList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NewsBean item = listAdapter.getItem(position);
                onShareClickListener.onClick(item);
            }
        });
    }

    public void setData(RecommedTopDataBean recommedTopDataBean) {
        List<NewsBean> spreadFashionList = recommedTopDataBean.getSpreadFashionList();
        List<NewsBean> hotTopicList = recommedTopDataBean.getHotTopicList();
        List<NewsBean> stickList = recommedTopDataBean.getStickList();
        if (stickList != null && stickList.size() > 0) {
            linePlacement.setVisibility(View.VISIBLE);
            placementBean = stickList.get(0);
            tePlacementTitle.setText(placementBean.getTitle());
            teSenderDate.setText(placementBean.getAuthor() + "  " + placementBean.getTimestamp());
            GlideDownLoadImage.getInstance().loadImage(context, placementBean.getCoverUrl(),
                    imgPlacementCover, R.mipmap.vegetable_default);
        } else {
            linePlacement.setVisibility(View.GONE);
        }

        if (hotTopicList != null && hotTopicList.size() > 0) {
            lineHotTopic.setVisibility(View.VISIBLE);
            listAdapter.setNewData(hotTopicList);
        } else {
            lineHotTopic.setVisibility(View.GONE);
        }
        if (spreadFashionList != null && spreadFashionList.size() > 0) {
            newsFengShangBean = spreadFashionList.get(0);
            GlideDownLoadImage.getInstance().loadImage(context, newsFengShangBean.getCoverUrl(),
                    imgFengshangCover, R.mipmap.vegetable_default);
            teFengshangTitle.setText(newsFengShangBean.getTitle());
            teSmallTitle.setText(newsFengShangBean.getTopical());
        }
    }

    public interface onShareClickListener {
        void onClick(NewsBean newsBean);
    }

    public void setOnShareClickListener(HomeRecommedHeader.onShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }
}
