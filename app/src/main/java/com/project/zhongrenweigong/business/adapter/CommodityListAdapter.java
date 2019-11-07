package com.project.zhongrenweigong.business.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.CommodityDataBean;
import com.project.zhongrenweigong.business.bean.CommodityListBean;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class CommodityListAdapter extends BaseQuickAdapter<CommodityDataBean,BaseViewHolder> {

    public CommodityListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityDataBean item) {
        GlideDownLoadImage.getInstance().loadImage(mContext,item.goodsTitleUrl,
                (ImageView)helper.getView(R.id.img_commodity));
        helper.setText(R.id.te_commodity_name,item.name);
        helper.setText(R.id.te_commodity_price,"价格："+item.price);
        helper.addOnClickListener(R.id.te_commodity_edit);
        helper.addOnClickListener(R.id.te_commodity_delete);
    }
}
