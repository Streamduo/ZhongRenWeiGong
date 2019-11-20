package com.project.zhongrenweigong.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.home.adapter.HomeMainPageAdapter;
import com.project.zhongrenweigong.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeFragment extends BaseFragment<HomePresent> {

    public static final String TAG = "HomeFragment";
    Unbinder unbinder;
    @BindView(R.id.vp_homepage)
    ViewPager vpHomepage;
    @BindView(R.id.tuijian_tx)
    TextView tuijianTx;
    @BindView(R.id.img_tuijian_new)
    ImageView imgTuijianNew;
    @BindView(R.id.tuijian_btn_layout)
    RelativeLayout tuijianBtnLayout;
    @BindView(R.id.video_tx)
    TextView videoTx;
    @BindView(R.id.img_video_new)
    ImageView imgVideoNew;
    @BindView(R.id.video_btn_layout)
    RelativeLayout videoBtnLayout;
    @BindView(R.id.msg_tx)
    TextView msgTx;
    @BindView(R.id.msg_bottom_line)
    View msgBottomLine;
    @BindView(R.id.msg_btn_layout)
    RelativeLayout msgBtnLayout;
    @BindView(R.id.tuijian_bottom_line)
    View tuijianBottomLine;
    @BindView(R.id.video_bottom_line)
    View videoBottomLine;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        StatusBarUtils.with(getActivity()).setStatus(getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtils.with(getActivity()).setStatus(getActivity());
    }

    @Override
    public void initView() {
        HomeMainPageAdapter homeMainPageAdapter = new HomeMainPageAdapter(getChildFragmentManager());
        vpHomepage.setAdapter(homeMainPageAdapter);
        vpHomepage.setCurrentItem(0);
        vpHomepage.setOffscreenPageLimit(3);
        vpHomepage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void initAfter() {

    }

    private void select(int position) {
        if (position == 0) {
            tuijianTx.setTextColor(getActivity().getResources().getColor(R.color.FF000000));
            tuijianTx.setTextSize(19);
            tuijianBottomLine.setVisibility(View.VISIBLE);
            videoTx.setTextColor(getActivity().getResources().getColor(R.color.app_text_99));
            videoTx.setTextSize(17);
            videoBottomLine.setVisibility(View.GONE);
            msgTx.setTextColor(getActivity().getResources().getColor(R.color.app_text_99));
            msgTx.setTextSize(17);
            msgBottomLine.setVisibility(View.GONE);
            vpHomepage.setCurrentItem(0);
        } else if (position == 1) {
            tuijianTx.setTextColor(getActivity().getResources().getColor(R.color.app_text_99));
            tuijianTx.setTextSize(17);
            tuijianBottomLine.setVisibility(View.GONE);
            videoTx.setTextColor(getActivity().getResources().getColor(R.color.FF000000));
            videoTx.setTextSize(19);
            videoBottomLine.setVisibility(View.VISIBLE);
            msgTx.setTextColor(getActivity().getResources().getColor(R.color.app_text_99));
            msgTx.setTextSize(17);
            msgBottomLine.setVisibility(View.GONE);
            vpHomepage.setCurrentItem(1);
        } else if (position == 2) {
            tuijianTx.setTextColor(getActivity().getResources().getColor(R.color.app_text_99));
            tuijianTx.setTextSize(17);
            tuijianBottomLine.setVisibility(View.GONE);
            videoTx.setTextColor(getActivity().getResources().getColor(R.color.app_text_99));
            videoTx.setTextSize(17);
            videoBottomLine.setVisibility(View.GONE);
            msgTx.setTextColor(getActivity().getResources().getColor(R.color.FF000000));
            msgTx.setTextSize(19);
            msgBottomLine.setVisibility(View.VISIBLE);
            vpHomepage.setCurrentItem(2);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public HomePresent bindPresent() {
        return new HomePresent();
    }

    @Override
    public void setListener() {
        tuijianBtnLayout.setOnClickListener(this);
        videoBtnLayout.setOnClickListener(this);
        msgBtnLayout.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.tuijian_btn_layout:
                select(0);
                break;
            case R.id.video_btn_layout:
                select(1);
                break;
            case R.id.msg_btn_layout:
                select(2);
                break;
        }
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
