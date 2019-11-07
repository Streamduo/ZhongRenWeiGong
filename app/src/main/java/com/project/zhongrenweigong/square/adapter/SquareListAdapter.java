package com.project.zhongrenweigong.square.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.square.bean.SquareDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/30 16:10
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class SquareListAdapter extends BaseQuickAdapter<SquareDataBean, BaseViewHolder> {

    public SquareListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SquareDataBean item) {
        int type = item.type;
        if (type == 0) {
            helper.setVisible(R.id.img_video, true);
        } else {
            helper.setVisible(R.id.img_video, false);
        }
        String coverPictureUrl = item.coverPictureUrl;
        if (coverPictureUrl != null && !coverPictureUrl.equals("")) {
            GlideDownLoadImage.getInstance().loadCircleImageTopRole(helper.itemView.getContext(),
                    coverPictureUrl, (ImageView) helper.getView(R.id.img_text_video_cover),
                    4, R.mipmap.default_list_big);
        }
        helper.setText(R.id.te_intro, item.content);
    }
}
