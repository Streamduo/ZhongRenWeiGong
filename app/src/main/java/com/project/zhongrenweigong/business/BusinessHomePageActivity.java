package com.project.zhongrenweigong.business;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.HomePageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Fuduo on 2019/10/21 14:34
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessHomePageActivity extends BaseActivity<BusinessHomePagePresent> {
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_guanzhu_size)
    TextView teGuanzhuSize;
    @BindView(R.id.img_trademark)
    ImageView imgTrademark;
    @BindView(R.id.te_fans_size)
    TextView teFansSize;
    @BindView(R.id.te_company_name)
    TextView teCompanyName;
    @BindView(R.id.te_guanzhu)
    TextView teGuanzhu;
    @BindView(R.id.tab_shop_page)
    TabLayout tabShopPage;
    @BindView(R.id.vp_homepage)
    ViewPager vpHomepage;

    @Override
    public void initView() {
        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager());
        vpHomepage.setAdapter(adapter);
        tabShopPage.setupWithViewPager(vpHomepage);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_business_home_page;
    }

    @Override
    public BusinessHomePagePresent bindPresent() {
        return new BusinessHomePagePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
