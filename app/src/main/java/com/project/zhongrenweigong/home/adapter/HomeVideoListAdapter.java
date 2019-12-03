package com.project.zhongrenweigong.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.bean.AddressDataBean;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
import com.project.zhongrenweigong.home.bean.HomeVideoDataBean;
import com.project.zhongrenweigong.home.bean.NewsDataBean;
import com.project.zhongrenweigong.home.bean.NewsDataMultiItemEntity;
import com.project.zhongrenweigong.home.viewholder.Subject1ViewHolder;
import com.project.zhongrenweigong.home.viewholder.Subject2ViewHolder;
import com.project.zhongrenweigong.home.viewholder.Subject3ViewHolder;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;


/**
 * Created by phz on 2017/12/15.
 */

public class HomeVideoListAdapter extends BaseQuickAdapter<HomeVideoDataBean, BaseViewHolder> {

    public static final String TAG = "HomeVideoListAdapter";

    public HomeVideoListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeVideoDataBean item) {
        if (item == null) {
            return;
        }
        GlideDownLoadImage.getInstance()
                .loadImage(mContext, item.getCoverUrl(), (ImageView) helper.getView(R.id.img_journalism_cover), R.mipmap.vegetable_default);
        helper.setText(R.id.te_journalism_title, item.getTitle());
        helper.setText(R.id.te_video_time, item.getDuration());
        helper.setText(R.id.te_sees_journalism, item.getLookNum());
        helper.setText(R.id.te_share_journalism, item.getTransmitNum());
        final StandardGSYVideoPlayer gsyVideoPlayer = (StandardGSYVideoPlayer) helper.getView(R.id.detail_player);
        gsyVideoPlayer.setUpLazy(item.getVideoUrl(), true, null, null, "这是title");
        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsyVideoPlayer.startWindowFullscreen(mContext, false, true);
            }
        });
        int adapterPosition = helper.getAdapterPosition();
        //防止错位设置
        gsyVideoPlayer.setPlayTag(TAG);
        gsyVideoPlayer.setPlayPosition(adapterPosition);
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        gsyVideoPlayer.setAutoFullWithSize(true);
        //音频焦点冲突时是否释放
        gsyVideoPlayer.setReleaseWhenLossAudio(false);
        //全屏动画
        gsyVideoPlayer.setShowFullAnimation(true);
        //小屏时不触摸滑动
        gsyVideoPlayer.setIsTouchWiget(false);

        helper.getView(R.id.rl_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setVisible(R.id.img_journalism_cover, false);
                helper.setVisible(R.id.rl_yinying, false);
                helper.setVisible(R.id.detail_player, true);
                helper.setVisible(R.id.te_video_time, false);
                gsyVideoPlayer.startPlayLogic();
            }
        });
    }
}
