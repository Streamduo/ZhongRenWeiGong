package com.project.zhongrenweigong.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.currency.bean.SearchHistoryBean;
import com.project.zhongrenweigong.home.bean.AddressDataBean;


/**
 * Created by phz on 2017/12/15.
 */

public class AddressListAdapter extends BaseQuickAdapter<AddressDataBean,BaseViewHolder> {
    public AddressListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressDataBean item) {
        helper.setText(R.id.te_address_text,item.address);
    }
}
