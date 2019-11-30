package com.project.zhongrenweigong.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.IndustryDataBean;
import com.project.zhongrenweigong.mine.bean.BankCardDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BankCardListAdapter extends BaseQuickAdapter<BankCardDataBean, BaseViewHolder> {

    public BankCardListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardDataBean item) {
        GlideDownLoadImage.getInstance().loadImage(mContext, item.cardImgUrl,
                (ImageView) helper.getView(R.id.img_bank_icon), R.mipmap.fang_list_default);
        helper.setText(R.id.te_bank_num, item.bankName + "(" + item.desensitizationCardNumber + ")");
        int select = item.select;
        if (select == 1) {
            helper.setVisible(R.id.img_select, true);
        } else {
            helper.setVisible(R.id.img_select, false);
        }
    }

    public void setOtherFalse(int position) {
        List<BankCardDataBean> data = getData();
        for (int i = 0; i < data.size(); i++) {
            BankCardDataBean bankCardDataBean = data.get(i);
            if (i == position){
                bankCardDataBean.select = 1;
            }else {
                bankCardDataBean.select = 0;
            }
        }
        notifyDataSetChanged();
    }
}
