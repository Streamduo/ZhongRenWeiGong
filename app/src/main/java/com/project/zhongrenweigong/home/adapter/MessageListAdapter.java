package com.project.zhongrenweigong.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.bean.AddressDataBean;
import com.project.zhongrenweigong.home.bean.MessageDataBean;


/**
 * Created by phz on 2017/12/15.
 */

public class MessageListAdapter extends BaseQuickAdapter<MessageDataBean,BaseViewHolder> {
    public MessageListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageDataBean item) {
        String typeId = item.typeId;
        if (typeId.equals("1")){
            helper.setBackgroundRes(R.id.img_msg,R.mipmap.xitong_msg);
        }else {
            helper.setBackgroundRes(R.id.img_msg,R.mipmap.pay_msg);
        }
        helper.setText(R.id.te_msg_name,item.title);
        helper.setText(R.id.te_msg_intro,item.content);
    }
}
