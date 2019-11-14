package com.project.zhongrenweigong.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.mine.bean.GoodDeedBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class GoodDeedListAdapter extends BaseQuickAdapter<GoodDeedBean, BaseViewHolder> {

    public GoodDeedListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodDeedBean item) {
        helper.setText(R.id.te_good_deed_name, item.title);
        helper.setText(R.id.te_good_deed_date, item.date);
        helper.setText(R.id.te_good_deed_branch, "+" + item.grade);
    }
}
