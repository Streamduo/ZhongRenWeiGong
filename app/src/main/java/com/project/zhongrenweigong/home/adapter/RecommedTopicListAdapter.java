package com.project.zhongrenweigong.home.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.bean.AddressDataBean;
import com.project.zhongrenweigong.home.bean.NewsBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;


/**
 * Created by phz on 2017/12/15.
 */

public class RecommedTopicListAdapter extends BaseQuickAdapter<NewsBean,BaseViewHolder> {

    public RecommedTopicListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {

        GlideDownLoadImage.getInstance().loadImage(mContext, item.getCoverUrl(),
                (ImageView) helper.getView(R.id.img_topic_cover), R.mipmap.vegetable_default);
        helper.setText(R.id.te_topic_title,item.getTitle());
        helper.setText(R.id.te_topic_sender,item.getAuthor());
        helper.addOnClickListener(R.id.te_topic_share);

    }
}
