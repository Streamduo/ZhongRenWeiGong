package com.project.zhongrenweigong.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.CheckInputUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidbase.kit.ToastManager;

public class FindPasswordActivity extends BaseActivity<FindPasswordPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_phone_num)
    EditText edPhoneNum;
    @BindView(R.id.ed_ems_num)
    EditText edEmsNum;
    @BindView(R.id.te_send_ems)
    TextView teSendEms;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_second_password)
    EditText edSecondPassword;
    @BindView(R.id.img_ok)
    ImageView imgOk;
    private CountDownTimer timer;
    private String phoneNum;

    @Override
    public void initView() {
        teTitle.setText("找回密码");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_find_password;
    }

    @Override
    public FindPasswordPresent bindPresent() {
        return new FindPasswordPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teSendEms.setOnClickListener(this);
        imgOk.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_send_ems:
                String phone = edPhoneNum.getText().toString();
                phoneNum = phone.replaceAll("\\D", "");
                if (TextUtils.isEmpty(phoneNum)) {
                    showToastShort(getString(R.string.phonenumber_null));
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(phoneNum)) {
                    showToastShort(getResources().getString(R.string.phonenumber_error));
                    return;
                }
                getPhoneCode();//成功之后开始倒计时
                break;
            case R.id.img_ok:
                String phoneNumText = edPhoneNum.getText().toString();
                String phoneNum = phoneNumText.replaceAll("\\D", "");
                String textEmsNum = edEmsNum.getText().toString();
                String passwordText = edPassword.getText().toString();
                String querenPasswordText = edSecondPassword.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    showToastShort(getString(R.string.phonenumber_null));
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(phoneNum)) {
                    ToastManager.showShort(FindPasswordActivity.this, getString(R.string.phonenumber_error));
                    return;
                }

                if (textEmsNum == null || textEmsNum.equals("")) {
                    ToastManager.showShort(FindPasswordActivity.this, getString(R.string.code_null));
                    return;
                }
                if (textEmsNum.length() < 6) {
                    ToastManager.showShort(FindPasswordActivity.this, getString(R.string.code_error));
                    return;
                }

                if (passwordText == null || passwordText.equals("")) {
                    ToastManager.showShort(FindPasswordActivity.this, getString(R.string.password_null));
                    return;
                }
                if (passwordText.length() < 6) {
                    ToastManager.showShort(FindPasswordActivity.this, getString(R.string.password_cn_6));
                    return;
                }

                if (!CheckInputUtil.checkPassword(passwordText)) {
                    ToastManager.showShort(FindPasswordActivity.this, getString(R.string.password_error));
                }

                if (querenPasswordText == null || querenPasswordText.equals("")) {
                    ToastManager.showShort(FindPasswordActivity.this, getString(R.string.queren_password_null));
                    return;
                }

                if (!querenPasswordText.equals(passwordText)) {
                    ToastManager.showShort(FindPasswordActivity.this, getString(R.string.same_password_error));
                    return;
                }
                getP().findPwd(phoneNum, textEmsNum, passwordText, querenPasswordText);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private void getPhoneCode() {
        getP().getVerification(phoneNum);
    }

    public void sendEmsFail() {
        teSendEms.setEnabled(true);
    }

    public void sendEmsSuccess() {
        teSendEms.setEnabled(true);
        initCountDownTimer();
    }

    private void initCountDownTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                teSendEms.setEnabled(false);
                teSendEms.setText(l / 1000 + "s");
            }

            @Override
            public void onFinish() {
                teSendEms.setText("再次发送");
                teSendEms.setEnabled(true);
            }
        }.start();
    }

    private void cancleTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

}
