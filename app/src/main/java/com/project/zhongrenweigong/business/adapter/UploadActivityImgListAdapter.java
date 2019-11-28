package com.project.zhongrenweigong.business.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.UploadImgBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class UploadActivityImgListAdapter extends BaseQuickAdapter<UploadImgBean, BaseViewHolder> {

    private boolean isShowDelete = true;

    public UploadActivityImgListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UploadImgBean item) {
        if (item.getIndex() == 1) {
            helper.setVisible(R.id.img_add, true);
        } else {
            helper.setVisible(R.id.img_add, false);
            GlideDownLoadImage.getInstance().loadImage(mContext, item.getImgUri(),
                    (ImageView) helper.getView(R.id.img_upload), R.mipmap.vegetable_default);
        }
        helper.setVisible(R.id.img_delete, isShowDelete);
        helper.addOnClickListener(R.id.img_delete);
        helper.addOnClickListener(R.id.img_add);
    }

    public void setShowDelete(boolean showDelete) {
        isShowDelete = showDelete;
    }
}
