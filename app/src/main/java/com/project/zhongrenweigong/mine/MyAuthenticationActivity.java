package com.project.zhongrenweigong.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAuthenticationActivity extends BaseActivity<MyAuthenticationPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_user_name)
    TextView teUserName;
    @BindView(R.id.img_authentication)
    ImageView imgAuthentication;
    @BindView(R.id.te_unbind)
    TextView teUnbind;
    @BindView(R.id.te_realname_status)
    TextView teRealnameStatus;
    @BindView(R.id.rl_realname_status)
    RelativeLayout rlRealnameStatus;
    @BindView(R.id.te_face_status)
    TextView teFaceStatus;
    @BindView(R.id.rl_face_status)
    RelativeLayout rlFaceStatus;
    @BindView(R.id.te_bankcard_status)
    TextView teBankcardStatus;
    @BindView(R.id.rl_bankcard_status)
    RelativeLayout rlBankcardStatus;
    @BindView(R.id.te_business_status)
    TextView teBusinessStatus;
    @BindView(R.id.rl_business_status)
    RelativeLayout rlBusinessStatus;

    @Override
    public void initView() {
       teTitle.setText("我的认证");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_authentication;
    }

    @Override
    public MyAuthenticationPresent bindPresent() {
        return new MyAuthenticationPresent();
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
