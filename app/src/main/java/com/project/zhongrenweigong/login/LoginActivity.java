package com.project.zhongrenweigong.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.CheckInputUtil;
import com.project.zhongrenweigong.util.SystemUtil;

import butterknife.BindView;
import cn.droidlover.xdroidbase.kit.ToastManager;
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
    private int isLoginOut;

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
        isLoginOut = getIntent().getIntExtra("isLoginOut", 0);
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
        imgLogin.setOnClickListener(this);
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
                Router.newIntent(this).to(FindPasswordActivity.class).launch();
                break;
            case R.id.img_login:
                String phoneNumText = edPhoneNum.getText().toString();
                String pn = phoneNumText.replaceAll("\\D", "");
                String passwordText = edPassword.getText().toString();

                String ipAddress = SystemUtil.getIpAddress(LoginActivity.this);

                if (TextUtils.isEmpty(pn)) {
                    ToastManager.showShort(LoginActivity.this,getString(R.string.phonenumber_null));
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(pn)) {
                    ToastManager.showShort(LoginActivity.this,getString(R.string.phonenumber_error));
                    return;
                }

                if (passwordText == null || passwordText.equals("")) {
                    ToastManager.showShort(LoginActivity.this,getString(R.string.password_null));
                    return;
                }
                if (passwordText.length() < 6) {
                    ToastManager.showShort(LoginActivity.this,getString(R.string.password_cn_6));
                    return;
                }
                if (!CheckInputUtil.checkPassword(passwordText)){
                    ToastManager.showShort(LoginActivity.this,getString(R.string.password_error));
                }
                getP().login("adress",ipAddress,"1",passwordText,phoneNumText,isLoginOut);
                break;
        }
    }
}
