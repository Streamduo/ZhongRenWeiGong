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

public class VoucherMessageListAdapter extends BaseQuickAdapter<MessageListsBean, BaseViewHolder> {

    public VoucherMessageListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListsBean item) {
        String role = item.role;
        String checkStatus = item.checkStatus;
        if (role.equals("1")) {
            helper.setText(R.id.te_msg_title, item.mbName + "ID：" + item.mbId);
            helper.setVisible(R.id.img_voucher_status, true);
            GlideDownLoadImage.getInstance().loadCircleImage(mContext, item.mbHeadUrl,
                    (ImageView) helper.getView(R.id.img_msg), R.mipmap.big_default_user_head);
        } else {
            helper.setText(R.id.te_msg_title, item.shopName);
            helper.setVisible(R.id.img_voucher_status, false);
            GlideDownLoadImage.getInstance().loadCircleImage(mContext, item.shopLogo,
                    (ImageView) helper.getView(R.id.img_msg), R.mipmap.default_shop_head);
        }

        if (checkStatus.equals("0")) {
            helper.setText(R.id.te_voucher_status, "上传凭证待审核");
        } else if (checkStatus.equals("1")) {
            helper.setText(R.id.te_voucher_status, "上传凭证审核未通过");
            helper.setBackgroundRes(R.id.img_voucher_status, R.mipmap.msg_shenhe_error);
        } else {
            helper.setText(R.id.te_voucher_status, "上传凭证审核已通过");
            helper.setBackgroundRes(R.id.img_voucher_status, R.mipmap.msg_shenghe_ok);
        }
        String isRead = item.isRead;
        if (isRead.equals("0")) {
            helper.setVisible(R.id.view_red_little, true);
            helper.setTextColor(R.id.te_msg_title, mContext.getResources().getColor(R.color.FF000000));
        } else {
            helper.setVisible(R.id.view_red_little, false);
            helper.setTextColor(R.id.te_msg_title, mContext.getResources().getColor(R.color.app_text_99));
        }
        helper.setText(R.id.te_mechanism_address, item.shopAddr);
        helper.setText(R.id.te_mechanism_date, item.time);
    }
}
