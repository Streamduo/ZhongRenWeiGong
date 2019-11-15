package com.project.zhongrenweigong.business.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.GoodsListsBean;
import com.project.zhongrenweigong.business.bean.WorkerDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class VegetableListAdapter extends BaseQuickAdapter<GoodsListsBean,BaseViewHolder> {

    public VegetableListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsListsBean item) {
        GlideDownLoadImage.getInstance().loadImage(mContext,item.goodsTitleUrl,
                (ImageView)helper.getView(R.id.img_vegetable));
        helper.setText(R.id.te_vegetable_name,item.name);
    }
}
