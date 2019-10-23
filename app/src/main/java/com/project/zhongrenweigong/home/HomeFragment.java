package com.project.zhongrenweigong.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.BusinessListActivity;
import com.project.zhongrenweigong.login.LoginActivity;
import com.tmall.ultraviewpager.UltraViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * 作者：Fuduo on 2019/10/17 10:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomeFragment extends BaseFragment<HomePresent> {

    public static final String TAG = "HomeFragment";
    public static final String TYPE = "type";
    @BindView(R.id.ad_viewpager)
    UltraViewPager adViewpager;
    @BindView(R.id.te_address)
    TextView teAddress;
    @BindView(R.id.te_search)
    TextView teSearch;
    @BindView(R.id.img_saoyosao)
    ImageView imgSaoyosao;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.te_food)
    TextView teFood;
    @BindView(R.id.te_teach)
    TextView teTeach;
    @BindView(R.id.te_house)
    TextView teHouse;
    @BindView(R.id.te_car)
    TextView teCar;
    @BindView(R.id.te_retailers)
    TextView teRetailers;
    @BindView(R.id.te_travel)
    TextView teTravel;
    Unbinder unbinder;

    @Override
    public void initView() {

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public HomePresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        teAddress.setOnClickListener(this);
        teSearch.setOnClickListener(this);
        imgMessage.setOnClickListener(this);
        imgSaoyosao.setOnClickListener(this);

        teCar.setOnClickListener(this);
        teFood.setOnClickListener(this);
        teHouse.setOnClickListener(this);
        teRetailers.setOnClickListener(this);
        teTeach.setOnClickListener(this);
        teTravel.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_message:
                Router.newIntent(getActivity()).to(MessageListActivity.class).launch();
                break;
            case R.id.img_saoyosao:
                Router.newIntent(getActivity()).to(LoginActivity.class).launch();
                break;
            case R.id.te_food:
                Router.newIntent(getActivity()).putInt(TYPE, 1)
                        .to(BusinessListActivity.class)
                        .launch();
                break;
            case R.id.te_teach:
                Router.newIntent(getActivity()).putInt(TYPE, 2)
                        .to(BusinessListActivity.class)
                        .launch();
                break;
            case R.id.te_house:
                Router.newIntent(getActivity()).putInt(TYPE, 3)
                        .to(BusinessListActivity.class)
                        .launch();
                break;
            case R.id.te_car:
                Router.newIntent(getActivity()).putInt(TYPE, 4)
                        .to(BusinessListActivity.class)
                        .launch();
                break;
            case R.id.te_retailers:
                Router.newIntent(getActivity()).putInt(TYPE, 5)
                        .to(BusinessListActivity.class)
                        .launch();
                break;
            case R.id.te_travel:
                Router.newIntent(getActivity()).putInt(TYPE, 6)
                        .to(BusinessListActivity.class)
                        .launch();
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
