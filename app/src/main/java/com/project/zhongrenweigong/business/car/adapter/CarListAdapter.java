package com.project.zhongrenweigong.business.car.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.IndustryDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class CarListAdapter extends BaseQuickAdapter<IndustryDataBean,BaseViewHolder> {

    public CarListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndustryDataBean item) {
        GlideDownLoadImage.getInstance().loadImage(mContext,item.shopLogo,
                (ImageView)helper.getView(R.id.img_mechanism_head),R.mipmap.fang_list_default);
        helper.setText(R.id.te_mechanism_title,item.shopName);
        helper.setText(R.id.te_mechanism_intro,item.details);
        helper.setText(R.id.te_mechanism_address,item.detailedAddr);
        helper.setText(R.id.te_mechanism_phone,item.mcPhone);
    }
}
