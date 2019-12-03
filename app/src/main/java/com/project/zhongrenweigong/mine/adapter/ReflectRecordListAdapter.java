package com.project.zhongrenweigong.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.mine.bean.BankCardDataBean;
import com.project.zhongrenweigong.mine.bean.ListsBean;
import com.project.zhongrenweigong.mine.bean.ReflectListsBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ReflectRecordListAdapter extends BaseQuickAdapter<ReflectListsBean, BaseViewHolder> {

    public ReflectRecordListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReflectListsBean item) {
        helper.setText(R.id.te_reflect_intro, "零钱提现到" +
                item.bankName + "(" + item.cardNumber + ")");
        helper.setText(R.id.te_reflect_date, item.dateTime);
        helper.setText(R.id.te_reflect_money, String.valueOf(item.money));
    }
}
