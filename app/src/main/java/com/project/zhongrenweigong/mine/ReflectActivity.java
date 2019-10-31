package com.project.zhongrenweigong.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;

public class ReflectActivity extends BaseActivity<ReflectPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.img_reflect_bankcard)
    ImageView imgReflectBankcard;
    @BindView(R.id.img_reflect_wx)
    ImageView imgReflectWx;
    @BindView(R.id.img_reflect_zfb)
    ImageView imgReflectZfb;
    @BindView(R.id.te_bank_name)
    TextView teBankName;
    @BindView(R.id.rl_bank)
    RelativeLayout rlBank;
    @BindView(R.id.te_reflect_money)
    TextView teReflectMoney;
    @BindView(R.id.te_reflect_record)
    TextView teReflectRecord;
    @BindView(R.id.te_reflect_all)
    TextView teReflectAll;
    @BindView(R.id.te_reflect_ok)
    TextView teReflectOk;

    @Override
    public void initView() {
        UtilsStyle.statusBarLightMode(this);
        teTitle.setText("提现");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reflect;
    }

    @Override
    public ReflectPresent bindPresent() {
        return new ReflectPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        rlBank.setOnClickListener(this);
        teReflectRecord.setOnClickListener(this);
        teReflectAll.setOnClickListener(this);
        teReflectOk.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.rl_bank:
                break;
            case R.id.te_reflect_record:
                Router.newIntent(ReflectActivity.this).putInt("recordType", 1)
                        .to(CompensationRecordActivity.class)
                        .launch();
                break;
            case R.id.te_reflect_all:
                break;
            case R.id.te_reflect_ok:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
