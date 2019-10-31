package com.project.zhongrenweigong.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.currency.bean.SearchHistoryBean;
import com.project.zhongrenweigong.home.bean.AddressDataBean;


/**
 * Created by phz on 2017/12/15.
 */

public class AddressHistoryListAdapter extends BaseQuickAdapter<SearchHistoryBean, BaseViewHolder> {

    public String locationCity;

    public AddressHistoryListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistoryBean item) {
        int adapterPosition = helper.getAdapterPosition();
        if (adapterPosition == 0) {
            helper.setVisible(R.id.te_address_text, false);
            helper.setVisible(R.id.te_nearby_address, true);
            helper.setText(R.id.te_nearby_address, locationCity);
        } else {
            helper.setVisible(R.id.te_nearby_address, false);
            helper.setVisible(R.id.te_address_text, true);
        }
        helper.setText(R.id.te_address_text, item.searchHistory);
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }
}
