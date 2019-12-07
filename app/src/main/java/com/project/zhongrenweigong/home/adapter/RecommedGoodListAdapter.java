package com.project.zhongrenweigong.home.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.bean.NewsBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;


/**
 * Created by phz on 2017/12/15.
 */

public class RecommedGoodListAdapter extends BaseQuickAdapter<NewsBean,BaseViewHolder> {

    public RecommedGoodListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {

        GlideDownLoadImage.getInstance().loadImage(mContext, item.getCoverUrl(),
                (ImageView) helper.getView(R.id.img_good_things), R.mipmap.vegetable_default);
        GlideDownLoadImage.getInstance().loadCircleImage(mContext, item.getAuthorHeadUrl(),
                (ImageView) helper.getView(R.id.img_thing_sender_head), R.mipmap.big_default_user_head);
        helper.setText(R.id.te_things_title,item.getTitle());
        helper.setText(R.id.te_thing_sender_name,item.getAuthor());
        helper.setText(R.id.te_thing_sees,String.valueOf(item.getLookNum()));
        helper.setText(R.id.te_thing_zan,String.valueOf(item.getLikeNum()));
        String isLike = item.getIsLike();
        if (isLike.equals("1")){
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.thing_zan_selected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView)helper.getView(R.id.te_thing_zan)).setCompoundDrawables(drawable,null,null,null);
        }else {
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.thing_zan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView)helper.getView(R.id.te_thing_zan)).setCompoundDrawables(drawable,null,null,null);
        }
        helper.addOnClickListener(R.id.te_good_things_share);
    }
}
