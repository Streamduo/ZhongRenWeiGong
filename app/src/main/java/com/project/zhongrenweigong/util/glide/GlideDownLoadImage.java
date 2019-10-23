package com.project.zhongrenweigong.util.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.R;

import java.io.ByteArrayOutputStream;


/**
 * 作者：fuduo
 * 时间 2017-8-9 11:13
 * 邮箱：duoendeavor@163.com
 * <p>
 * 类的意图：Glide加载图片工具类
 */

public class GlideDownLoadImage {

    private static final String TAG = "ImageLoader";

    private static GlideDownLoadImage instance = new GlideDownLoadImage();

    public static GlideDownLoadImage getInstance() {
        return instance;
    }

    /**
     * @param
     * @name 加载本地图片的重载方法 Context
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(Context mContext, int resId, ImageView view) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(resId)
                    .placeholder(R.mipmap.fang_list_default)
                    .error(R.mipmap.fang_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }

    }

    /**
     * @param
     * @name 加载本地图片的重载方法 Context
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(Activity activity, Uri uri, ImageView view) {
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(uri)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }

    }

    /**
     * @param
     * @name 加载本地图片的重载方法 Fragment
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(Fragment fragment, int resId, ImageView view) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment)
                    .load(resId)
                    .placeholder(R.mipmap.fang_list_default)
                    .error(R.mipmap.fang_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }


    /**
     * @param
     * @name 加载本地图片的重载方法 Activity
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(Activity activity, int resId, ImageView view) {
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(resId)
                    .placeholder(R.mipmap.fang_list_default)
                    .error(R.mipmap.fang_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
    }

    /**
     * @param
     * @name 加载本地图片的重载方法 此方法慎用 生命周期长不容易被销毁
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(int resId, ImageView view) {
        Glide.with(App.getContext())
                .load(resId)
                .placeholder(R.mipmap.fang_list_default)
                .error(R.mipmap.fang_list_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * 加载网络图片 mContext
     *
     * @param url
     * @param view
     */
    public void loadImage(Context mContext, String url, ImageView view) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.fang_list_default)
                    .error(R.mipmap.fang_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * 加载网络图片 mContext
     *
     * @param url
     * @param view
     */
    public void loadImage(Context mContext, String url, ImageView view, int defpic) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(url)
                    .placeholder(defpic)
                    .error(defpic)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @param activity
     * @param url
     * @param view
     * @name 加载网络图片重载方法 Activity
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadImage(Activity activity, String url, ImageView view) {
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .placeholder(R.mipmap.fang_list_default)
                    .error(R.mipmap.fang_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
    }

    /**
     * @param activity
     * @param url
     * @param view
     * @name 加载网络图片重载方法 Activity
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadImageAs(Activity activity, String url, ImageView view) {
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .asBitmap().fitCenter()
                    .placeholder(R.mipmap.fang_list_default)
                    .error(R.mipmap.fang_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
    }

    /**
     * @param activity
     * @param url
     * @param view
     * @name 加载网络图片重载方法 Activity
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadImageNew(Activity activity, String url, ImageView view) {
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
    }

    /**
     * @param fragment
     * @param url
     * @param view
     * @name 加载网络图片重载方法 Fragment
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadImage(Fragment fragment, String url, ImageView view) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment)
                    .load(url)
                    .placeholder(R.mipmap.fang_list_default)
                    .error(R.mipmap.fang_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }


    /**
     * @param mContext
     * @param resId
     * @param view
     * @name 加载本地圆图 Context
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Context mContext, int resId, ImageView view) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(resId)
                    .placeholder(R.mipmap.shenhe_ren_head)
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @param activity
     * @param resId
     * @param view
     * @name 加载本地圆图 Activity
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Activity activity, int resId, ImageView view) {
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(resId)
                    .placeholder(R.mipmap.shenhe_ren_head)
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideCircleTransform(activity))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
    }


    /**
     * @param resId
     * @param view
     * @name 加载本地圆图 //基本不用
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(int resId, ImageView view) {
        Glide.with(App.getContext())
                .load(resId)
                .placeholder(R.mipmap.shenhe_ren_head)
                .error(R.mipmap.shenhe_ren_head)
                .bitmapTransform(new GlideCircleTransform(App.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param url
     * @param view
     * @name 加载网络圆图 //基本不用
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(String url, ImageView view) {
        Glide.with(App.getContext())
                .load(url)
                .placeholder(R.mipmap.shenhe_ren_head)
                .error(R.mipmap.shenhe_ren_head)
                .bitmapTransform(new GlideCircleTransform(App.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param url
     * @param view
     * @name 加载网络圆图 Activity
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Activity activity, String url, ImageView view) {
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .placeholder(R.mipmap.shenhe_ren_head)
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideCircleTransform(activity))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
    }

    /**
     * @param mContext
     * @param url
     * @param view
     * @name 加载网络圆图 Context
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Context mContext, String url, ImageView view) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.shenhe_ren_head)
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @param fragment
     * @param url
     * @param view
     * @name 加载网络圆图 Fragment
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Fragment fragment, String url, ImageView view) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment)
                    .load(url)
                    .placeholder(R.mipmap.shenhe_ren_head)
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideCircleTransform(fragment.getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }

    /**
     * @param fragment
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片 Fragment
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Fragment fragment, String resId, ImageView view, int dp) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment)
                    .load(resId)
                    .placeholder(R.mipmap.shenhe_ren_head)
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideRoundTransform(fragment.getContext(), dp))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }

    /**
     * @param activity
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片 Activity
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Activity activity, String resId, ImageView view, int dp) {
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(resId)
                    .placeholder(R.mipmap.shenhe_ren_head)
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideRoundTransform(activity, dp))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
    }

    /**
     * @param mContext
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片 Context
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Context mContext, String resId, ImageView view, int dp) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(resId)
                    .placeholder(R.mipmap.fang_list_default)
                    .error(R.mipmap.fang_list_default)
                    .bitmapTransform(new GlideRoundTransform(mContext, dp))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片 //基本不用
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(String resId, ImageView view, int dp) {
        Glide.with(App.getContext())
                .load(resId)
                .placeholder(R.mipmap.fang_list_default)
                .error(R.mipmap.fang_list_default)
                .bitmapTransform(new GlideRoundTransform(App.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @name 加载bitmap圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadBitmapCircleImageRole(Context mContext, ImageView view, Bitmap bitmap) {
        if (mContext != null && bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            Glide.with(mContext)
                    .load(bytes)
                    .centerCrop()
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideRoundTransform(mContext, 90))
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context or bitmap is null");
        }
    }

    /**
     * @name 加载bitmap带角度的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadBitmapCircleImage(Context mContext, ImageView view, Bitmap bitmap, int size) {
        if (mContext != null && bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            Glide.with(mContext)
                    .load(bytes)
                    .centerCrop()
                    .error(R.mipmap.shenhe_ren_head)
                    .bitmapTransform(new GlideRoundTransform(mContext, size))
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context or bitmap is null");
        }
    }

    /**
     * @name 加载bitmap的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadBitmapImageRole(Context mContext, ImageView view, Bitmap bitmap) {
        if (mContext != null && bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            Glide.with(mContext)
                    .load(bytes)
                    .centerCrop()
                    .error(R.mipmap.fang_list_default)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context or bitmap is null");
        }
    }

    /**
     * @param mContext
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor phz
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole2(Context mContext, String resId, ImageView view, int dp, int defpic) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(resId)
                    .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, dp))
                    .placeholder(defpic)
                    .error(defpic)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void loadRoundImageRole(Context mContext, String resId, ImageView view, int defpic) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(resId)
                    .transform(new CenterCrop(mContext))
                    .placeholder(defpic)
                    .error(defpic)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }


}
