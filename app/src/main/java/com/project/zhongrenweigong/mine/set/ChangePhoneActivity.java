package com.project.zhongrenweigong.mine.set;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePhoneActivity extends BaseActivity<ChangePhonePresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_ems_num)
    EditText edEmsNum;
    @BindView(R.id.te_send_ok_01)
    TextView teSendOk01;
    @BindView(R.id.te_num)
    TextView teNum;
    @BindView(R.id.te_phone_num)
    TextView tePhoneNum;
    @BindView(R.id.ed_new_phone)
    EditText edNewPhone;
    @BindView(R.id.ed_ems_code)
    EditText edEmsCode;
    @BindView(R.id.te_send_new_code)
    TextView teSendNewCode;
    @BindView(R.id.te_send_ok_02)
    TextView teSendOk02;
    private CountDownTimer timer;
    private String mbPhone;

    @Override
    public void initView() {
        UtilsStyle.statusBarLightMode(this);
        teTitle.setText("变更手机号");
    }

    @Override
    public void initAfter() {
        LoginMsg userAccent = AcacheUtils.getInstance(this).getUserAccent();
        mbPhone = userAccent.mbPhone;
        tePhoneNum.setText(mbPhone);
        getPhoneCode();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_change_phone;
    }

    @Override
    public ChangePhonePresent bindPresent() {
        return new ChangePhonePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teSendOk01.setOnClickListener(this);
        teNum.setOnClickListener(this);
        edEmsNum.addTextChangedListener(textWatcher);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_num:
                String s = teNum.getText().toString();
                if (s.equals("再次发送")) {
                    teNum.setText("60");
                    getPhoneCode();
                }
                break;
            case R.id.te_send_ok_01:

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private void getPhoneCode() {
        getP().getVerification(mbPhone);
    }

    public void sendEmsSuccess() {
        initCountDownTimer();
    }

    private void initCountDownTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                teNum.setText(l / 1000 + "");
            }

            @Override
            public void onFinish() {
                teNum.setText("再次发送");
            }
        }.start();
    }

    private void cancleTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancleTimer();
    }
}
