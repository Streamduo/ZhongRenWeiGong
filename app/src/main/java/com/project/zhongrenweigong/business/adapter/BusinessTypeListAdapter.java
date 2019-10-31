package com.project.zhongrenweigong.business.adapter;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.BusinessTypeBean;

/**
 * 作者：Fuduo on 2019/10/22 14:42
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessTypeListAdapter extends BaseQuickAdapter<BusinessTypeBean, BaseViewHolder> {

    private int item;

    //获得点击的下标 由外部传进来进行判断哪个要放大（即点即了哪个或者选中了哪个）
    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public BusinessTypeListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessTypeBean item) {
        helper.setText(R.id.te_type_name, item.name);
        if (getItem() == helper.getAdapterPosition()) {
            //1.1为原来的大小+1的0.1倍放大
            helper.getView(R.id.img_business_fenlei_head).setScaleX(1.1f);
            helper.getView(R.id.img_business_fenlei_head).setScaleY(1.1f);
            helper.setTextColor(R.id.te_type_name, helper.itemView.getContext().getResources().getColor(R.color.app_369EFF));
        } else {
            //缩小同理   1为布局设定的大小
            helper.getView(R.id.img_business_fenlei_head).setScaleX(1f);
            helper.getView(R.id.img_business_fenlei_head).setScaleY(1f);
            helper.setTextColor(R.id.te_type_name, helper.itemView.getContext().getResources().getColor(R.color.app_text_66));
        }
    }
}
