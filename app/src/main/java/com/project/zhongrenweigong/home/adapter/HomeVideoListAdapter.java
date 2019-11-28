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

import java.util.List;


/**
 * Created by phz on 2017/12/15.
 */

public class HomeVideoListAdapter extends BaseQuickAdapter<HomeVideoDataBean, BaseViewHolder> {

    public HomeVideoListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeVideoDataBean item) {
        GlideDownLoadImage.getInstance()
                .loadImage(mContext, item.getCoverUrl(), (ImageView) helper.getView(R.id.img_journalism_cover), R.mipmap.vegetable_default);
        helper.setText(R.id.te_journalism_title, item.getTitle());
        helper.setText(R.id.te_video_time, item.getDuration());
        helper.setText(R.id.te_sees_journalism, item.getLookNum());
        helper.setText(R.id.te_share_journalism, item.getTransmitNum());
        helper.addOnClickListener(R.id.rl_play);
    }
}
