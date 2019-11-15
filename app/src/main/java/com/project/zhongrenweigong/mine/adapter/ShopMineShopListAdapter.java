package com.project.zhongrenweigong.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.mine.bean.GoodDeedBean;
import com.project.zhongrenweigong.mine.bean.ShopListDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ShopMineShopListAdapter extends BaseQuickAdapter<ShopListDataBean, BaseViewHolder> {

    public ShopMineShopListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListDataBean item) {
        GlideDownLoadImage.getInstance().loadImage(mContext,item.shopLogo,
                (ImageView) helper.getView(R.id.img_shop_head),R.mipmap.default_shop_head);
        helper.setText(R.id.te_shop_name, item.shopName);
        helper.setText(R.id.te_intro, item.details);
        helper.setText(R.id.te_shop_address,item.detailedAddr);
        helper.setText(R.id.te_shop_phone,item.mcPhone);
    }
}
