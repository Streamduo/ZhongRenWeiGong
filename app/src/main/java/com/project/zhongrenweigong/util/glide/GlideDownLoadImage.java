package com.project.zhongrenweigong.util.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.util.ScreenUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.project.zhongrenweigong.util.ScreenUtils.dip2px;


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

        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        if (mContext != null) {
            Glide.with(mContext)
                    .load(resId)
                    .apply(options)
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
    public void loadImage(Context mContext, File file, ImageView view) {

        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        if (mContext != null) {
            Glide.with(mContext)
                    .load(file)
                    .apply(options)
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
    public void loadImage(Context mContext, String string, ImageView view, Drawable defpic) {
        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(mContext)
                .load(string)
                .apply(options)
                .into(view);

    }

    /**
     * @param
     * @name 加载本地图片的重载方法 Context
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImageGif(Context mContext, String string, ImageView view, Transformation<Bitmap> transformation, Drawable defpic) {

        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .dontAnimate()
                .transform(transformation)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(mContext)
                .load(string)
                .apply(options)
                .into(view);

    }

    /**
     * @param
     * @name 加载本地图片的重载方法 Context
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImageGif(Context mContext, String string, ImageView view, Transformation<Bitmap> transformation, int defpic) {

        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .dontAnimate()
                .transform(transformation)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(mContext)
                .load(string)
                .apply(options)
                .into(view);

    }

    /**
     * @param
     * @name 加载本地图片的重载方法 Context
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(Activity activity, Uri uri, ImageView view) {

        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(uri)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment)
                    .load(resId)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(resId)
                    .apply(options)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
    }

    /**
     * 加载网络图片 mContext
     *
     * @param url
     * @param view
     */
    public void loadImage(Context mContext, String url, ImageView view) {
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .error(defpic)
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * 加载长图片 mContext
     *
     * @param url
     */
    @SuppressLint("CheckResult")
    public void loadImage(Context mContext, String url, SimpleTarget<File> simpleTarget) {
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            RequestManager requestManager = Glide.with(mContext);
            requestManager
                    .load(url)
                    .downloadOnly(simpleTarget);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * 加载长图片 mContext
     *
     * @param url
     */
    @SuppressLint("CheckResult")
    public void loadImage(Activity mActivity, String url, SimpleTarget<File> simpleTarget) {
        if (mActivity != null && !mActivity.isDestroyed()) {
            RequestManager requestManager = Glide.with(mActivity);
            requestManager
                    .load(url)
                    .downloadOnly(simpleTarget);
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
    public void loadImageFitCenter(Context mContext, int url, ImageView view) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
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
    public void loadImageOverri(Context mContext, String url, ImageView view, int defpic, int width, int hight) {
        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .error(defpic)
                .centerCrop()
                .dontAnimate()
                .override(width, hight)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            RequestManager requestManager = Glide.with(mContext);
            requestManager
                    .load(url)
                    .apply(options)
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
    public void loadImageAd(Context mContext, String url, ImageView view, int defpic, int width, int hight) {
        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .error(defpic)
                .dontAnimate()
                .centerCrop()
                .override(width, hight)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
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
    public void loadImageOverri(Context mContext, String url, ImageView view, int dp, int defpic, int width, int hight) {
        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .error(defpic)
                .dontAnimate()
                .override(width, hight)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new MultiTransformation(new RoundedCornersTransformation(dip2px(mContext, dp), 0)));
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.fang_list_default)
                .error(R.mipmap.fang_list_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.fang_list_default)
                .fitCenter()
                .dontAnimate()
                .error(R.mipmap.fang_list_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.fang_list_default)
                .error(R.mipmap.fang_list_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (fragment != null && Objects.requireNonNull(fragment.getActivity()).isDestroyed()) {
            Glide.with(fragment)
                    .load(url)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .circleCrop();

        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(resId)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .circleCrop();
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(resId)
                    .apply(options)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,activity is null");
        }
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.user_default_head)
                .error(R.mipmap.user_default_head)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .circleCrop();
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.user_default_head)
                .error(R.mipmap.user_default_head)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .circleCrop();
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(url)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.user_default_head)
                .error(R.mipmap.user_default_head)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .circleCrop();
        if (fragment != null && Objects.requireNonNull(fragment.getActivity()).isDestroyed()) {
            Glide.with(fragment)
                    .load(url)
                    .apply(options)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }

    public void loadCircleImage(Fragment fragment, String url, ImageView view,int resId) {
        RequestOptions options = new RequestOptions()
                .placeholder(resId)
                .error(resId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .circleCrop();
        if (fragment != null && Objects.requireNonNull(fragment.getActivity()).isDestroyed()) {
            Glide.with(fragment)
                    .load(url)
                    .apply(options)
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

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.user_default_head)
                .error(R.mipmap.user_default_head)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(dip2px(fragment.getContext(), dp), 0)));

        if (Objects.requireNonNull(fragment.getActivity()).isDestroyed()) {
            Glide.with(fragment)
                    .load(resId)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.user_default_head)
                .error(R.mipmap.user_default_head)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(dip2px(activity, dp), 0)));
        if (!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(resId)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.fang_list_default)
                .error(R.mipmap.fang_list_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(dip2px(mContext, dp), 0)));
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(resId)
                    .apply(options)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @name 加载bitmap圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadBitmapCircleImageRole(Context mContext, ImageView view, Bitmap bitmap) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.user_default_head)
                .error(R.mipmap.user_default_head)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(dip2px(mContext, 90), 0)));
        if (mContext != null && !((Activity) mContext).isDestroyed() && bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            Glide.with(mContext)
                    .asBitmap()
                    .load(bytes)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.user_default_head)
                .error(R.mipmap.user_default_head)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(dip2px(mContext, size), 0)));
        if (mContext != null && !((Activity) mContext).isDestroyed() && bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            Glide.with(mContext)
                    .asBitmap()
                    .load(bytes)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (mContext != null && !((Activity) mContext).isDestroyed() && bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            Glide.with(mContext)
                    .asBitmap()
                    .load(bytes)
                    .apply(options)
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
        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .error(defpic)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(dip2px(mContext, dp), 0)));
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(resId)
                    .apply(options)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }

    }

    public void loadRoundImageRole(Context mContext, String resId, ImageView view, int defpic) {
        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .error(defpic)
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(resId)
                    .apply(options)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
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
    public void loadCircleImageTopRole(Context mContext, String resId, ImageView view, int dp, int defpic) {
        RequestOptions options = new RequestOptions()
                .placeholder(defpic)
                .error(defpic)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new MultiTransformation(new CenterCrop(), new RoundedCornersTransformation(dip2px(mContext, dp), 0, RoundedCornersTransformation.CornerType.TOP)));
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            Glide.with(mContext)
                    .load(resId)
                    .apply(options)
                    .into(view);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }

    }

}
