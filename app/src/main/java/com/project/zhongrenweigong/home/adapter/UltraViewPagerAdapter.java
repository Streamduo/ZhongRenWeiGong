package com.project.zhongrenweigong.home.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.bean.DataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import java.util.List;

public class UltraViewPagerAdapter extends PagerAdapter {

    private List<DataBean> carouselEntities;
    private Activity activity;
    private int sourceType;

    public UltraViewPagerAdapter(Activity activity, List<DataBean> carouselEntities) {
        this.carouselEntities = carouselEntities;
        this.activity = activity;
    }

    public UltraViewPagerAdapter(Activity activity, List<DataBean> carouselEntities, int sourceType) {
        this.carouselEntities = carouselEntities;
        this.activity = activity;
        this.sourceType = sourceType;
    }

    @Override
    public int getCount() {
        return carouselEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_home_viewpager, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.pager_imageview);

        GlideDownLoadImage.getInstance().loadImage(activity,carouselEntities.get(position).slideshowUrl,
                imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (carouselEntities == null || carouselEntities.isEmpty() || position < 0 || position > carouselEntities.size() - 1) {
//                    return;
//                }
//                addLog(carouselEntities.get(position).id, 2);
//                switch (carouselEntities.get(position).activity_carousel_redirect_type) {
//                    case 1://带评论的活动详情页
//                        ActivitiesActivity.start(activity,
//                                String.valueOf(carouselEntities.get(position).source_id),
//                                carouselEntities.get(position).getActivityShareBean());
//                        break;
//                    case 2:
//                        SchemeHelper.shouldOverrideUrlLoading(activity, null, carouselEntities.get(position).activity_carousel_redirect_url, null);
//                        break;
//                    case 3:
//                        OutLinkActivity.startWithShare(activity, carouselEntities.get(position).activity_carousel_redirect_url,
//                                carouselEntities.get(position).getOutlinkShareBean());
//                        break;
//                    case 4:
//                        try {
//                            String clickLink = carouselEntities.get(position).clickLink;
//                            if (clickLink != null && !clickLink.equals("")) {
//                                click(clickLink);
//                            }
//                            SystemUtil.jumpThirdApp(activity, carouselEntities.get(position).activity_carousel_redirect_url);
//                        } catch (Exception e) {
//                        }
//                        break;
//                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
