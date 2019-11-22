package com.project.zhongrenweigong.message;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoucherMessageDetailActivity extends BaseActivity<VoucherMessagePresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_voucher_intro)
    TextView teVoucherIntro;
    @BindView(R.id.recy_img_list)
    RecyclerView recyImgList;
    @BindView(R.id.te_pass)
    TextView tePass;
    @BindView(R.id.te_refuse)
    TextView teRefuse;
    @BindView(R.id.line_business)
    LinearLayout lineBusiness;
    @BindView(R.id.te_no_pass)
    TextView teNoPass;
    @BindView(R.id.rl_no_pass)
    RelativeLayout rlNoPass;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_voucher_message_detail;
    }

    @Override
    public VoucherMessagePresent bindPresent() {
        return new VoucherMessagePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        tePass.setOnClickListener(this);
        teNoPass.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_pass:
                break;
            case R.id.te_refuse:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }
}
