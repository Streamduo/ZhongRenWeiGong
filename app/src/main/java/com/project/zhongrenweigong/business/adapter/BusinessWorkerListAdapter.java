package com.project.zhongrenweigong.business.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.business.bean.EmployeesBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessWorkerListAdapter extends BaseQuickAdapter<EmployeesBean, BaseViewHolder> {

    public BusinessWorkerListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmployeesBean item) {
        GlideDownLoadImage.getInstance().loadCircleImage(mContext, item.employeesTitleUrl,
                (ImageView) helper.getView(R.id.img_people_head));
    }
}
