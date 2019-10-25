package com.project.zhongrenweigong.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.project.zhongrenweigong.util.ActivityManager;
import com.project.zhongrenweigong.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：Fuduo on 2019/10/15 14:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public abstract class BaseActivity<M extends XPresent> extends XActivity<M> implements View.OnClickListener {

    /**
     * 退出程序的时间间隔
     */
    private long exitTime = 0;

    /**
     * 是否允许全屏,此处全屏是指将状态栏都隐藏掉
     **/
    private boolean mAllowFullScreen = false;

    private boolean isFull = true;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 上下文
     **/
    public Context mContext = this;

    /**
     * 运行时权限的监听回调
     **/
    private static PressionListener mListener;
    private ActivityManager activityManager;

//    private SwipeLayout swipeLayout;

    /**
     * 是否可以滑动关闭页面
     */
    protected boolean swipeEnabled = true;

    /**
     * 是否可以在页面任意位置右滑关闭页面，如果是false则从左边滑才可以关闭。
     */
    protected boolean swipeAnyWhere = false;

//    public BaseActivity() {
//
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        swipeLayout = new SwipeLayout(this);
//    }

//    public void setSwipeAnyWhere(boolean swipeAnyWhere) {
//        this.swipeAnyWhere = swipeAnyWhere;
//    }
//
//    public boolean isSwipeAnyWhere() {
//        return swipeAnyWhere;
//    }
//
//    public void setSwipeEnabled(boolean swipeEnabled) {
//        this.swipeEnabled = swipeEnabled;
//    }
//
//    public boolean isSwipeEnabled() {
//        return swipeEnabled;
//    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        initAfter();
        setListener();
        activityManager = ActivityManager.getScreenManager();
        activityManager.pushActivity(this);
        if (isFull){
            StatusBarUtils.with(this).init();
        }
    }

    @Override
    public int getLayoutId() {
        if (mAllowFullScreen) {
            //隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        return bindLayout();
    }

    @Override
    public M newP() {
        return bindPresent();
    }

    /**
     * [找控件]
     *
     * @return
     */
    public abstract void initView();

    /**
     * [找完控件后执行]
     *
     * @return
     */
    public abstract void initAfter();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract M bindPresent();

    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View view) {
        if (fastClick())
            widgetClick(view);
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    public void showToastShort(String toastMsg){
        ToastManager.showShort(this,toastMsg);
    }

    public void showToastLong(String toastMsg){
        ToastManager.showLong(this,toastMsg);
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    /**
     * 6.0以上检查申请权限时调用
     *
     * @param activity 当前使用的Activity
     * @param pression 需要申请检查的权限集合
     * @param listener 是否成功的回调方法
     */
    public static void requestRunTimePression(Activity activity, String[] pression, PressionListener listener) {
        mListener = listener;
        List<String> pressionList = new ArrayList<>();
        for (int i = 0; i < pression.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, pression[i]) != PackageManager.PERMISSION_GRANTED) {
                pressionList.add(pression[i]);
            }
        }

        if (!pressionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, pressionList.toArray(new String[pressionList.size()]), 1);
        } else {
            //do something
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> failureList = new ArrayList<>();
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        int granted = grantResults[i];
                        String pression = permissions[i];
                        if (granted != PackageManager.PERMISSION_GRANTED) {
                            failureList.add(pression);
                        }
                    }
                    if (failureList.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onFailure(failureList);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 描述：退出程序
     */
    protected void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            activityManager.popAllActivity();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityManager.popActivity(this);
        Log.d(TAG, "onDestroy()");
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        swipeLayout.replaceLayer(this);
//    }
//
//    public static int getScreenWidth(Context context) {
//        DisplayMetrics metrics = new DisplayMetrics();
//        WindowManager manager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
//        manager.getDefaultDisplay().getMetrics(metrics);
//        return metrics.widthPixels;
//    }

//    private boolean swipeFinished = false;
//
//    @Override
//    public void finish() {
//        if (swipeFinished) {
//            super.finish();
//            overridePendingTransition(0, 0);
//        } else {
//            swipeLayout.cancelPotentialAnimation();
//            super.finish();
//            overridePendingTransition(0, R.anim.slide_out_right);
//        }
//    }

//    class SwipeLayout extends FrameLayout {
//
//        //private View backgroundLayer;用来设置滑动时的背景色
//        private Drawable leftShadow;
//
//        public SwipeLayout(Context context) {
//            super(context);
//        }
//
//        public SwipeLayout(Context context, AttributeSet attrs) {
//            super(context, attrs);
//        }
//
//        public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//            super(context, attrs, defStyleAttr);
//        }
//
//        public void replaceLayer(Activity activity) {
//            leftShadow = activity.getResources().getDrawable(R.drawable.left_shadow);
//            touchSlop = (int) (touchSlopDP * activity.getResources().getDisplayMetrics().density);
//            sideWidth = (int) (sideWidthInDP * activity.getResources().getDisplayMetrics().density);
//            mActivity = activity;
//            screenWidth = getScreenWidth(activity);
//            setClickable(true);
//            final ViewGroup root = (ViewGroup) activity.getWindow().getDecorView();
//            content = root.getChildAt(0);
//            ViewGroup.LayoutParams params = content.getLayoutParams();
//            ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(-1, -1);
//            root.removeView(content);
//            this.addView(content, params2);
//            root.addView(this, params);
//        }
//
//        @Override
//        protected boolean drawChild(@NonNull Canvas canvas, @NonNull View child, long drawingTime) {
//            boolean result = super.drawChild(canvas, child, drawingTime);
//            final int shadowWidth = leftShadow.getIntrinsicWidth();
//            int left = (int) (getContentX()) - shadowWidth;
//            leftShadow.setBounds(left, child.getTop(), left + shadowWidth, child.getBottom());
//            leftShadow.draw(canvas);
//            return result;
//        }
//
//        boolean canSwipe = false;
//        /**
//         * 超过了touchslop仍然没有达到没有条件，则忽略以后的动作
//         */
//        boolean ignoreSwipe = false;
//        View content;
//        Activity mActivity;
//        int sideWidthInDP = 16;
//        int sideWidth = 72;
//        int screenWidth = 1080;
//        VelocityTracker tracker;
//
//        float downX;
//        float downY;
//        float lastX;
//        float currentX;
//        float currentY;
//
//        int touchSlopDP = 20;
//        int touchSlop = 60;
//
//        @Override
//        public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
//            if (swipeEnabled && !canSwipe && !ignoreSwipe) {
//                if (swipeAnyWhere) {
//                    switch (ev.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            downX = ev.getX();
//                            downY = ev.getY();
//                            currentX = downX;
//                            currentY = downY;
//                            lastX = downX;
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            float dx = ev.getX() - downX;
//                            float dy = ev.getY() - downY;
//                            if (dx * dx + dy * dy > touchSlop * touchSlop) {
//                                if (dy == 0f || Math.abs(dx / dy) > 1) {
//                                    downX = ev.getX();
//                                    downY = ev.getY();
//                                    currentX = downX;
//                                    currentY = downY;
//                                    lastX = downX;
//                                    canSwipe = true;
//                                    tracker = VelocityTracker.obtain();
//                                    return true;
//                                } else {
//                                    ignoreSwipe = true;
//                                }
//                            }
//                            break;
//                    }
//                } else if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getX() < sideWidth) {
//                    canSwipe = true;
//                    tracker = VelocityTracker.obtain();
//                    return true;
//                }
//            }
//            if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
//                ignoreSwipe = false;
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(MotionEvent ev) {
//            return canSwipe || super.onInterceptTouchEvent(ev);
//        }
//
//        boolean hasIgnoreFirstMove;
//
//        @Override
//        public boolean onTouchEvent(@NonNull MotionEvent event) {
//            if (canSwipe) {
//                tracker.addMovement(event);
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        downX = event.getX();
//                        downY = event.getY();
//                        currentX = downX;
//                        currentY = downY;
//                        lastX = downX;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        currentX = event.getX();
//                        currentY = event.getY();
//                        float dx = currentX - lastX;
//                        if (dx != 0f && !hasIgnoreFirstMove) {
//                            hasIgnoreFirstMove = true;
//                            dx = dx / dx;
//                        }
//                        if (getContentX() + dx < 0) {
//                            setContentX(0);
//                        } else {
//                            setContentX(getContentX() + dx);
//                        }
//                        lastX = currentX;
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        tracker.computeCurrentVelocity(10000);
//                        tracker.computeCurrentVelocity(1000, 20000);
//                        canSwipe = false;
//                        hasIgnoreFirstMove = false;
//                        int mv = screenWidth * 3;
//                        if (Math.abs(tracker.getXVelocity()) > mv) {
//                            animateFromVelocity(tracker.getXVelocity());
//                        } else {
//                            if (getContentX() > screenWidth / 3) {
//                                animateFinish(false);
//                            } else {
//                                animateBack(false);
//                            }
//                        }
//                        tracker.recycle();
//                        break;
//                    default:
//                        break;
//                }
//            }
//            return super.onTouchEvent(event);
//        }
//
//        ObjectAnimator animator;
//
//        public void cancelPotentialAnimation() {
//            if (animator != null) {
//                animator.removeAllListeners();
//                animator.cancel();
//            }
//        }
//
//        public void setContentX(float x) {
//            int ix = (int) x;
//            content.setX(ix);
//            invalidate();
//        }
//
//        public float getContentX() {
//            return content.getX();
//        }
//
//
//        /**
//         * 弹回，不关闭，因为left是0，所以setX和setTranslationX效果是一样的
//         *
//         * @param withVel 使用计算出来的时间
//         */
//        private void animateBack(boolean withVel) {
//            cancelPotentialAnimation();
//            animator = ObjectAnimator.ofFloat(this, "contentX", getContentX(), 0);
//            int tmpDuration = withVel ? ((int) (duration * getContentX() / screenWidth)) : duration;
//            if (tmpDuration < 100) {
//                tmpDuration = 100;
//            }
//            animator.setDuration(tmpDuration);
//            animator.setInterpolator(new DecelerateInterpolator());
//            animator.start();
//        }
//
//        private void animateFinish(boolean withVel) {
//            cancelPotentialAnimation();
//            animator = ObjectAnimator.ofFloat(this, "contentX", getContentX(), screenWidth);
//            int tmpDuration = withVel ? ((int) (duration * (screenWidth - getContentX()) / screenWidth)) : duration;
//            if (tmpDuration < 100) {
//                tmpDuration = 100;
//            }
//            animator.setDuration(tmpDuration);
//            animator.setInterpolator(new DecelerateInterpolator());
//            animator.addListener(new Animator.AnimatorListener() {
//
//                @Override
//                public void onAnimationStart(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    if (!mActivity.isFinishing()) {
//                        swipeFinished = true;
//                        mActivity.finish();
//                    }
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//
//                }
//            });
//            animator.start();
//        }
//
//        private final int duration = 200;
//
//        private void animateFromVelocity(float v) {
//            if (v > 0) {
//                if (getContentX() < screenWidth / 3 && v * duration / 1000 + getContentX() < screenWidth / 3) {
//                    animateBack(false);
//                } else {
//                    animateFinish(true);
//                }
//            } else {
//                if (getContentX() > screenWidth / 3 && v * duration / 1000 + getContentX() > screenWidth / 3) {
//                    animateFinish(false);
//                } else {
//                    animateBack(true);
//                }
//            }
//
//        }
//    }

}
