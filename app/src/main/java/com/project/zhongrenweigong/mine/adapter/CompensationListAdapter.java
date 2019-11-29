package com.project.zhongrenweigong.mine.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.mine.bean.ListsBean;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class CompensationListAdapter extends BaseQuickAdapter<ListsBean, BaseViewHolder> {

    private int type;

    public CompensationListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListsBean item) {
        if (type == 1) {
            helper.setText(R.id.te_integra_name, item.shopName);
            helper.setText(R.id.te_integra_branch, String.valueOf(item.money));
        } else {
            helper.setText(R.id.te_integra_name, item.detail);
            helper.setText(R.id.te_integra_branch, String.valueOf(item.quantity));
        }
        helper.setText(R.id.te_integra_date, item.date);
    }

    public void setType(int type) {
        this.type = type;
    }
}
