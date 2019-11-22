package com.project.zhongrenweigong.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.bean.HomeRecommendBean;
import com.project.zhongrenweigong.home.bean.NewsDataBean;
import com.project.zhongrenweigong.home.bean.NewsDataMultiItemEntity;
import com.project.zhongrenweigong.home.viewholder.Subject1ViewHolder;
import com.project.zhongrenweigong.home.viewholder.Subject2ViewHolder;
import com.project.zhongrenweigong.home.viewholder.Subject3ViewHolder;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import java.util.List;

import butterknife.BindView;


/**
 * Created by phz on 2017/12/15.
 */

public class HomeRecommedListAdapter extends BaseMultiItemQuickAdapter<NewsDataMultiItemEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeRecommedListAdapter(List<NewsDataMultiItemEntity> data) {
        super(data);
        addItemType(HomeRecommendBean.STYLE_0, R.layout.item_journalism_type_1);
        addItemType(HomeRecommendBean.STYLE_1, R.layout.item_journalism_type_2);
        addItemType(HomeRecommendBean.STYLE_2, R.layout.item_journalism_type_3);
    }

    @Override
    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        if (layoutResId == R.layout.item_journalism_type_1) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
            return new Subject1ViewHolder(inflate);
        } else if (layoutResId == R.layout.item_journalism_type_2) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
            return new Subject2ViewHolder(inflate);
        } else if (layoutResId == R.layout.item_journalism_type_3) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
            return new Subject3ViewHolder(inflate);
        } else {
            return super.createBaseViewHolder(parent, layoutResId);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsDataMultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case HomeRecommendBean.STYLE_0:
                convertStyle1News(helper, item);
                break;
            case HomeRecommendBean.STYLE_1:
                convertStyle2News(helper, item);
                break;
            case HomeRecommendBean.STYLE_2:
                convertStyle3News(helper, item);
                break;
        }
    }

    private void convertStyle1News(BaseViewHolder baseHelper, NewsDataMultiItemEntity item) {
        final Subject1ViewHolder helper;
        if (baseHelper instanceof Subject1ViewHolder) {
            helper = (Subject1ViewHolder) baseHelper;
        } else {
            return;
        }
        NewsDataBean newsDataBean = item.newsDataBean;
        if (newsDataBean == null) {
            return;
        }
        List<String> imagesUrl = newsDataBean.imagesUrl;
        if (imagesUrl != null && imagesUrl.size() > 0) {
            String url = imagesUrl.get(0);
            GlideDownLoadImage.getInstance()
                    .loadImage(mContext, url, (ImageView) helper.getView(R.id.img_journalism), R.mipmap.vegetable_default);
        }
        helper.setText(R.id.te_journalism_title, newsDataBean.title);
        helper.setText(R.id.te_from_date, newsDataBean.copyright + "    " + newsDataBean.time);
    }

    private void convertStyle2News(BaseViewHolder baseHelper, NewsDataMultiItemEntity item) {

        final Subject2ViewHolder helper;
        if (baseHelper instanceof Subject2ViewHolder) {
            helper = (Subject2ViewHolder) baseHelper;
        } else {
            return;
        }
        NewsDataBean newsDataBean = item.newsDataBean;
        if (newsDataBean == null) {
            return;
        }
        List<String> imagesUrl = newsDataBean.imagesUrl;
        if (imagesUrl != null && imagesUrl.size() > 0) {
            if (imagesUrl.size() == 3) {
                String url = imagesUrl.get(0);
                GlideDownLoadImage.getInstance()
                        .loadImage(mContext, url, (ImageView) helper.getView(R.id.img_journalism_01), R.mipmap.vegetable_default);

                String url1 = imagesUrl.get(1);
                GlideDownLoadImage.getInstance()
                        .loadImage(mContext, url1, (ImageView) helper.getView(R.id.img_journalism_02), R.mipmap.vegetable_default);
                String url2 = imagesUrl.get(2);
                GlideDownLoadImage.getInstance()
                        .loadImage(mContext, url2, (ImageView) helper.getView(R.id.img_journalism_03), R.mipmap.vegetable_default);
            }

            if (imagesUrl.size() == 2) {
                String url = imagesUrl.get(0);
                GlideDownLoadImage.getInstance()
                        .loadImage(mContext, url, (ImageView) helper.getView(R.id.img_journalism_01), R.mipmap.vegetable_default);

                String url1 = imagesUrl.get(1);
                GlideDownLoadImage.getInstance()
                        .loadImage(mContext, url1, (ImageView) helper.getView(R.id.img_journalism_02), R.mipmap.vegetable_default);
            }

            if (imagesUrl.size() == 1) {
                String url = imagesUrl.get(0);
                GlideDownLoadImage.getInstance()
                        .loadImage(mContext, url, (ImageView) helper.getView(R.id.img_journalism_01), R.mipmap.vegetable_default);
            }

        }
        helper.setText(R.id.te_journalism_title, newsDataBean.title);
        helper.setText(R.id.te_from_date, newsDataBean.copyright + "    " + newsDataBean.time);
        helper.addOnClickListener(R.id.te_share_journalism);
    }

    private void convertStyle3News(BaseViewHolder baseHelper, NewsDataMultiItemEntity item) {

        final Subject3ViewHolder helper;
        if (baseHelper instanceof Subject3ViewHolder) {
            helper = (Subject3ViewHolder) baseHelper;
        } else {
            return;
        }

        NewsDataBean newsDataBean = item.newsDataBean;
        if (newsDataBean == null) {
            return;
        }
        GlideDownLoadImage.getInstance()
                .loadImage(mContext, newsDataBean.coverUrl, (ImageView) helper.getView(R.id.img_journalism_cover), R.mipmap.vegetable_default);
        helper.setText(R.id.te_journalism_title, newsDataBean.title);
        helper.setText(R.id.te_from_date, newsDataBean.copyright + "    " + newsDataBean.time);
        helper.addOnClickListener(R.id.rl_play);
    }
}
