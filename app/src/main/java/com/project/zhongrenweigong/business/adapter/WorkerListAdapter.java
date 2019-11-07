package com.project.zhongrenweigong.business.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.CommodityDataBean;
import com.project.zhongrenweigong.business.bean.WorkerDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class WorkerListAdapter extends BaseQuickAdapter<WorkerDataBean,BaseViewHolder> {

    public WorkerListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkerDataBean item) {
        GlideDownLoadImage.getInstance().loadCircleImage(mContext,item.employeesTitleUrl,
                (ImageView)helper.getView(R.id.img_worker));
        helper.setText(R.id.te_worker_name,item.employeesName);
        helper.setText(R.id.te_workeer_id,item.employeesId);
        helper.addOnClickListener(R.id.te_worker_delete);
    }
}
