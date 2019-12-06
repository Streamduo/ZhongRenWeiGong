package com.project.zhongrenweigong.home.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.detail_player)
    StandardGSYVideoPlayer detailPlayer;
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
    private Context context;
    private Activity activity;
    public View headerView;

    public HomeRecommedHeader(Context context, Activity activity) {
        headerView = LayoutInflater.from(context).inflate(R.layout.layout_home_recommed_header, null);
        ButterKnife.bind(this, headerView);
        this.context = context;
        this.activity = activity;
        init();
    }

    private void init() {

    }
}
