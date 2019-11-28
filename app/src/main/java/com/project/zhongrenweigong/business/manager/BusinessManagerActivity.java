package com.project.zhongrenweigong.business.manager;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.bean.IndustryDataBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

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
    @BindView(R.id.te_business_name)
    TextView teBusinessName;
    private IndustryDataBean industryDataBean;

    @Override
    public void initView() {
        teTitle.setText("商家管理");
        industryDataBean = (IndustryDataBean) getIntent().getSerializableExtra("IndustryDataBean");
    }

    @Override
    public void initAfter() {
        if (industryDataBean == null) {
            return;
        }
        GlideDownLoadImage.getInstance().loadCircleImage(this, industryDataBean.shopLogo,
                imgBusinessHead, R.mipmap.user_default_head);
        teBusinessName.setText(industryDataBean.shopName);

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
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_right_title:

                break;
            case R.id.rl_manage_commodity:
                Router.newIntent(BusinessManagerActivity.this).to(CommodityManagerActivity.class).launch();
                break;
            case R.id.rl_shop_xinxi:
                Router.newIntent(BusinessManagerActivity.this).to(EditStoreIntroActivity.class).launch();
                break;
            case R.id.rl_shop_notice:
                if (industryDataBean == null) {
                    return;
                }
                Router.newIntent(BusinessManagerActivity.this)
                        .putString("shopId", industryDataBean.shopId).to(SendLooseActivity.class).launch();
                break;
            case R.id.rl_worker_manager:
                Router.newIntent(BusinessManagerActivity.this).to(WorkerManagerActivity.class).launch();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
