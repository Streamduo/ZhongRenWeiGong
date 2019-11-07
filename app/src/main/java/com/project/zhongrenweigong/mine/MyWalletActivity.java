package com.project.zhongrenweigong.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;

public class MyWalletActivity extends BaseActivity<MyWalletPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_accent_balance)
    TextView teAccentBalance;
    @BindView(R.id.rl_wallet_tixian)
    RelativeLayout rlWalletTixian;
    @BindView(R.id.rl_wallet_chongzhi)
    RelativeLayout rlWalletChongzhi;
    @BindView(R.id.rl_wallet_pay_record)
    RelativeLayout rlWalletPayRecord;
    @BindView(R.id.rl_wallet_bank_card)
    RelativeLayout rlWalletBankCard;

    @Override
    public void initView() {
        teTitle.setText("赔付钱包");
    }

    @Override
    public void initAfter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public MyWalletPresent bindPresent() {
        return new MyWalletPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        rlWalletTixian.setOnClickListener(this);
        rlWalletChongzhi.setOnClickListener(this);
        rlWalletPayRecord.setOnClickListener(this);
        rlWalletBankCard.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.rl_wallet_tixian:
                Router.newIntent(MyWalletActivity.this).to(ReflectActivity.class).launch();
                break;
            case R.id.rl_wallet_chongzhi:

                break;
            case R.id.rl_wallet_pay_record:

                break;
            case R.id.rl_wallet_bank_card:

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
