package com.project.zhongrenweigong.home;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.square.adapter.SquareVideoPageAdapter;
import com.project.zhongrenweigong.util.StatusBarUtils;
import com.project.zhongrenweigong.util.TablayoutUtil;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * 作者：Fuduo on 2019/10/17 10:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class SquareFragment extends BaseFragment<SquarePresent> {

    public static final String TAG = "SquareFragment";
    @BindView(R.id.img_beat)
    ImageView imgBeat;
    @BindView(R.id.tab_square_page)
    TabLayout tabSquarePage;
    @BindView(R.id.vp_homepage)
    ViewPager vpHomepage;
    Unbinder unbinder;
    private EasyPopup mCirclePop;

    @Override
    public void onResume() {
        super.onResume();
        StatusBarUtils.with(getActivity()).init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtils.with(getActivity()).init();
    }

    @Override
    public void initView() {
        SquareVideoPageAdapter discoverAdapter = new SquareVideoPageAdapter(getChildFragmentManager());
        vpHomepage.setAdapter(discoverAdapter);
        tabSquarePage.setupWithViewPager(vpHomepage);
        TablayoutUtil.setIndicator(tabSquarePage, 30, 30);
        vpHomepage.setCurrentItem(0);

        vpHomepage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = vpHomepage.getCurrentItem();
                if (currentItem == 1) {
                    boolean isTourist = SharedPref.getInstance(getContext()).getBoolean(Constans.ISTOURIST, true);
                    if (isTourist) {
                        Router.newIntent(getActivity()).to(LoginActivity.class).launch();
                        vpHomepage.setCurrentItem(0);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_square;
    }

    @Override
    public SquarePresent bindPresent() {
        return new SquarePresent();
    }

    @Override
    public void setListener() {
        imgBeat.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_beat:
                showSendPublishDialog(getContext(), R.layout.layout_send_publish, imgBeat);
                break;
        }
    }

    private void showSendPublishDialog(Context context, int layoutid, View v) {

        //是否允许点击PopupWindow之外的地方消失
        //允许背景变暗
        //变暗的透明度(0-1)，0为完全透明
        //变暗的背景颜色
        //指定任意 ViewGroup 背景变暗
        mCirclePop = EasyPopup.create()
                .setContentView(context, layoutid)
                .setAnimationStyle(R.style.ActionSheetDialogStyle)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .apply();
        mCirclePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        TextView teBeatImg = (TextView) mCirclePop.findViewById(R.id.te_beat_img);
        TextView teBeatVideo = (TextView) mCirclePop.findViewById(R.id.te_beat_video);

        if (teBeatImg != null) {
            teBeatImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCirclePop.dismiss();

                }
            });
        }

        teBeatVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
                // 打开视频录制,短视频sdk，暂时只支持api 18以上的版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                } else {
                    ToastManager.showShort(getContext(), "手机版本过低，暂不支持录制");
                }
            }
        });

        /**
         * 相对anchor view显示，适用 宽高不为match_parent
         *
         * @param anchor
         * @param yGravity  垂直方向的对齐方式
         * @param xGravity  水平方向的对齐方式
         * @param x            水平方向的偏移
         * @param y            垂直方向的偏移
         */
        mCirclePop.showAtAnchorView(v, YGravity.BELOW, XGravity.LEFT, 80, 5);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
