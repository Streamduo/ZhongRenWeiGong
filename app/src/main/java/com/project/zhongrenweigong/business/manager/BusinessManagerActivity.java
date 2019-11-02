package com.project.zhongrenweigong.business.manager;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;

public class BusinessManagerActivity extends BaseActivity<BusinessManagerPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_right_title)
    TextView teRightTitle;
    @BindView(R.id.img_business_head)
    ImageView imgBusinessHead;
    @BindView(R.id.rl_manage_commodity)
    RelativeLayout rlManageCommodity;
    @BindView(R.id.rl_shop_xinxi)
    RelativeLayout rlShopXinxi;
    @BindView(R.id.rl_shop_notice)
    RelativeLayout rlShopNotice;
    @BindView(R.id.rl_worker_manager)
    RelativeLayout rlWorkerManager;

    @Override
    public void initView() {
        teTitle.setText("商家管理");
        teRightTitle.setText("发布动态");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_business_manager;
    }

    @Override
    public BusinessManagerPresent bindPresent() {
        return new BusinessManagerPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teRightTitle.setOnClickListener(this);
        rlManageCommodity.setOnClickListener(this);
        rlShopXinxi.setOnClickListener(this);
        rlShopNotice.setOnClickListener(this);
        rlWorkerManager.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.te_back:
                finish();
                break;
            case R.id.te_right_title:

                break;
            case R.id.rl_manage_commodity:
                Router.newIntent(BusinessManagerActivity.this).to(CommodityManagerActivity.class).launch();
                break;
            case R.id.rl_shop_xinxi:

                break;
            case R.id.rl_shop_notice:
                Router.newIntent(BusinessManagerActivity.this).to(SendNoticeActivity.class).launch();
                break;
            case R.id.rl_worker_manager:

                break;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
