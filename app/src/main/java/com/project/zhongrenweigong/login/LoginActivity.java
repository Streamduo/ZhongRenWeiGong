package com.project.zhongrenweigong.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;

public class LoginActivity extends BaseActivity<LoginPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.ed_phone_num)
    EditText edPhoneNum;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.img_login)
    ImageView imgLogin;
    @BindView(R.id.te_register)
    TextView teRegister;
    @BindView(R.id.te_find_password)
    TextView teFindPassword;
    @BindView(R.id.te_title)
    TextView teTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        teTitle.setText("登录");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresent bindPresent() {
        return new LoginPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teFindPassword.setOnClickListener(this);
        teRegister.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_register:
                Router.newIntent(this).to(RegisterActivity.class).launch();
                break;
            case R.id.te_find_password:
                break;
        }
    }
}
