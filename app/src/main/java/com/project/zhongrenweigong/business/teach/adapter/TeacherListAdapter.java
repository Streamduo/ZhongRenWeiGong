package com.project.zhongrenweigong.business.teach.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.TeachDataBean;
import com.project.zhongrenweigong.business.bean.TeacherDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class TeacherListAdapter extends BaseQuickAdapter<TeacherDataBean,BaseViewHolder> {

    public TeacherListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeacherDataBean item) {
        GlideDownLoadImage.getInstance().loadCircleImage(mContext,item.headUrl,
                (ImageView)helper.getView(R.id.img_personal_head),R.mipmap.fang_list_default);
        helper.setText(R.id.te_personal_title,item.mbName);
        helper.setText(R.id.te_personal_id,item.mbId);
        String isAuthMerchant = item.isAuthMerchant;
        if (isAuthMerchant.equals("1")){
            helper.setText(R.id.te_worker_renzheng,"职业认证:已认证");
        }else {
            helper.setText(R.id.te_worker_renzheng,"职业认证:未认证");
        }
        String isAuthProfession = item.isAuthProfession;
        if (isAuthProfession.equals("1")){
            helper.setText(R.id.te_jigou_renzheng,"机构:已认证");
        }else {
            helper.setText(R.id.te_jigou_renzheng,"机构:未认证");
        }
    }
}
