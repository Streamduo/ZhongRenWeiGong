package com.project.zhongrenweigong.message.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.message.bean.MessageListsBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;


/**
 * Created by phz on 2017/12/15.
 */

public class SystemMessageListAdapter extends BaseQuickAdapter<MessageListsBean, BaseViewHolder> {
    private int type;

    public SystemMessageListAdapter(int layoutResId, int type) {
        super(layoutResId);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListsBean item) {
        if (type == 1) {
            helper.setText(R.id.te_msg_name, item.shopName);
            GlideDownLoadImage.getInstance().loadCircleImage(mContext, item.shopLogo, (ImageView) helper.getView(R.id.img_msg), R.mipmap.default_shop_head);
        } else {
            helper.setText(R.id.te_msg_name, item.messageMain);
        }
        String isRead = item.isRead;
        if (isRead.equals("0")) {
            helper.setTextColor(R.id.te_msg_name, mContext.getResources().getColor(R.color.FF0F0F0F));
        } else {
            helper.setTextColor(R.id.te_msg_name, mContext.getResources().getColor(R.color.app_ADADAD));
        }
        helper.setText(R.id.te_msg_intro, item.messageIntro);
        helper.setText(R.id.te_msg_date, item.time);
    }
}
