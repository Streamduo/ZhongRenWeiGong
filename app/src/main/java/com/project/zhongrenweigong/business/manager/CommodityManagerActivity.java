package com.project.zhongrenweigong.business.manager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;

public class CommodityManagerActivity extends BaseActivity<CommodityManagerPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_right_title)
    TextView teRightTitle;
    @BindView(R.id.recy_commodity_list)
    RecyclerView recyCommodityList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;

    @Override
    public void initView() {
        UtilsStyle.statusBarLightMode(this);
        teTitle.setText("商品编辑");
        teRightTitle.setText("新增菜品");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_commodity_manager;
    }

    @Override
    public CommodityManagerPresent bindPresent() {
        return new CommodityManagerPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teRightTitle.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_right_title:
                Router.newIntent(CommodityManagerActivity.this).to(AddCommodityActivity.class).launch();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
