package com.project.zhongrenweigong.business.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessListAdapter extends BaseQuickAdapter<DataBean,BaseViewHolder> {

    public BusinessListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataBean item) {
        GlideDownLoadImage.getInstance().loadCircleImageRole2(mContext,item.shopLogo,
                (ImageView)helper.getView(R.id.img_business_fenlei_head),7,R.mipmap.fang_list_default);
        helper.setText(R.id.te_shop_name,item.shopName);
        helper.setText(R.id.te_shop_people_size,item.fans);
        helper.setText(R.id.te_shop_intro,item.details);
    }
}
