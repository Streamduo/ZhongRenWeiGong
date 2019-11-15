package com.project.zhongrenweigong.business.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.PlatformGetAreaManagerBean;
import com.project.zhongrenweigong.business.bean.TestImgBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

/**
 * 作者：Fuduo on 2019/10/29 13:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class TestImgListAdapter extends BaseQuickAdapter<TestImgBean, BaseViewHolder> {

    public TestImgListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestImgBean item) {
        GlideDownLoadImage.getInstance().loadImage(mContext, item.getTestImgName(),
                (ImageView) helper.getView(R.id.test_imageview),R.mipmap.default_list_big);
    }
}
