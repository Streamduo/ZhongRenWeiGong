package com.project.zhongrenweigong.business.teach.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.business.bean.TeachDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class TeachListAdapter extends BaseQuickAdapter<TeachDataBean, BaseViewHolder> {

    public TeachListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeachDataBean item) {
        GlideDownLoadImage.getInstance().loadImage(mContext, item.shopLogo,
                (ImageView) helper.getView(R.id.img_mechanism_head), R.mipmap.fang_list_default);
        helper.setText(R.id.te_mechanism_title, item.shopName);
        helper.setText(R.id.te_mechanism_intro, item.details);
        helper.setText(R.id.te_mechanism_address, "距你" + item.distance);
    }
}
