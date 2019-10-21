package com.project.zhongrenweigong.business;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Fuduo on 2019/10/18 15:24
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MerchantCertificationActivity extends BaseActivity<MerchantCertificationPresent> {
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.sp_commodity_industry)
    Spinner spCommodityIndustry;
    @BindView(R.id.ed_shop_name)
    EditText edShopName;
    @BindView(R.id.ed_phone_num)
    EditText edPhoneNum;
    @BindView(R.id.te_area)
    TextView teArea;
    @BindView(R.id.rl_select_area)
    RelativeLayout rlSelectArea;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.rl_dianzhao)
    RelativeLayout rlDianzhao;
    @BindView(R.id.img_upload)
    ImageView imgUpload;
    @BindView(R.id.upload)
    ImageView upload;
    @BindView(R.id.te_ok)
    TextView teOk;
    @BindView(R.id.img_other_upload)
    ImageView imgOtherUpload;
    @BindView(R.id.other_upload)
    ImageView otherUpload;

    @Override
    public void initView() {
        setFullScren(true);
        teTitle.setText("商家认证");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_merchantcertification_layout;
    }

    @Override
    public MerchantCertificationPresent bindPresent() {
        return new MerchantCertificationPresent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
