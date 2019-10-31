package com.project.zhongrenweigong.currency.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.currency.bean.SearchHistoryBean;


/**
 * Created by phz on 2017/12/15.
 */

public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistoryBean,BaseViewHolder> {

    private int type;

    public SearchHistoryAdapter(int layoutResId,int type) {
        super(layoutResId);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistoryBean item) {

        if (type == 0){
            int adapterPosition = helper.getAdapterPosition();
            if (adapterPosition == 0){
                helper.setVisible(R.id.te_history,false);
                helper.setVisible(R.id.te_nearby,true);
            }else {
                helper.setVisible(R.id.te_nearby,false);
                helper.setVisible(R.id.te_history,true);
            }
        }
        helper.setText(R.id.te_history,item.searchHistory);
    }
}
