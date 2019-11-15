package com.project.zhongrenweigong.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * @author ixiao QQ:3035882611
 * @name wakeyoga_android_git
 * @class name：com.wakeyoga.wakeyoga.views
 * @class 实现顶部悬浮
 * @time 2018/5/21 13:52
 * @change
 * @chang time
 * @class describe
 */
public class ObservableScrollview extends NestedScrollView {

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollview(Context context) {
        super(context);
    }

    public ObservableScrollview(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface ScrollViewListener {

        void onScrollChanged(ObservableScrollview scrollView, int x, int y, int oldx, int oldy);

    }

}
