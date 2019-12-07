package com.project.zhongrenweigong.currency.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.currency.bean.SearchHistoryBean;


/**
 * Created by phz on 2017/12/15.
 */

public class SearchNewsHistoryAdapter extends BaseQuickAdapter<SearchHistoryBean, BaseViewHolder> {

    public SearchNewsHistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistoryBean item) {
        int adapterPosition = helper.getAdapterPosition();
        if (adapterPosition % 2 != 0) {
            helper.setVisible(R.id.view_line,true);
        }else {
            helper.setVisible(R.id.view_line,false);
        }
        helper.setText(R.id.te_history, item.searchHistory);
    }
}
