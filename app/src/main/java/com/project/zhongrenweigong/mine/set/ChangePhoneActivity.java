package com.project.zhongrenweigong.mine.set;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.CheckInputUtil;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.view.NumberEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidbase.kit.ToastManager;

public class ChangePhoneActivity extends BaseActivity<ChangePhonePresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_ems_num)
    NumberEditText edEmsNum;
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
    @BindView(R.id.line_first)
    LinearLayout lineFirst;
    @BindView(R.id.line_second)
    LinearLayout lineSecond;
    private CountDownTimer timer;
    private String mbPhone;
    private String textCode;
    private LoginMsg userAccent;
    private CountDownTimer secondTimer;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("变更手机号");
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
    }

    @Override
    public void initAfter() {
        mbPhone = userAccent.mbPhone;
        tePhoneNum.setText(mbPhone);
        getPhoneCode(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
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
        edEmsNum.setOnInputFinish(new NumberEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String text) {
                textCode = text;
            }
        });
        edEmsNum.setOnInputChangeListener(new NumberEditText.OnInputChangeListener() {
            @Override
            public void onChange(int length) {
                if (length == 4) {
                    teSendOk01.setTextColor(getResources().getColor(R.color.white));
                    teSendOk01.setBackgroundResource(R.drawable.bg_bac_1282ff_5);
                } else {
                    teSendOk01.setTextColor(getResources().getColor(R.color.app_B0B0B0));
                    teSendOk01.setBackgroundResource(R.drawable.bg_bac_e1_5);
                }
            }
        });
        teSendNewCode.setOnClickListener(this);
        teSendOk02.setOnClickListener(this);
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
                    getPhoneCode(1);
                }
                break;
            case R.id.te_send_ok_01:
                if (textCode.isEmpty()) {
                    showToastShort("请输入验证码");
                    return;
                }
                getP().checkVerification(userAccent.mbId, userAccent.mbPhone, textCode);
                break;
            case R.id.te_send_new_code:
                String phone = edNewPhone.getText().toString();
                String pe = phone.replaceAll("\\D", "");
                if (phone.isEmpty()) {
                    showToastShort("请输入新手机号");
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(pe)) {
                    showToastShort(getString(R.string.phonenumber_error));
                    return;
                }

                String code = teSendNewCode.getText().toString();
                if (code.equals("再次发送")) {
                    teSendNewCode.setText("60");
                }
                getPhoneCode(2);
                break;
            case R.id.te_send_ok_02:
                String emsCode = edEmsCode.getText().toString();
                String newPhone = edNewPhone.getText().toString();
                String pn = newPhone.replaceAll("\\D", "");
                if (newPhone.isEmpty()) {
                    showToastShort("请输入新手机号");
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(pn)) {
                    showToastShort(getString(R.string.phonenumber_error));
                    return;
                }
                if (emsCode.isEmpty()) {
                    showToastShort("请输入验证码");
                    return;
                }
                getP().updatePhone(userAccent.mbId, pn, emsCode);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private void getPhoneCode(int type) {
        getP().getVerification(mbPhone, type);
    }

    public void sendEmsSuccess(int type) {
        if (type == 1) {
            initCountDownTimer();
        } else {
            initSecondTimer();
        }
    }

    private void initCountDownTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                teNum.setEnabled(false);
                teNum.setText(l / 1000 + "");
            }

            @Override
            public void onFinish() {
                teNum.setEnabled(true);
                teNum.setText("再次发送");
            }
        }.start();
    }

    private void initSecondTimer() {
        secondTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                teSendNewCode.setEnabled(false);
                teSendNewCode.setText(l / 1000 + "");
            }

            @Override
            public void onFinish() {
                teSendNewCode.setEnabled(true);
                teSendNewCode.setText("再次发送");
            }
        }.start();
    }

    private void cancleTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void cancleSecondTimer() {
        if (secondTimer != null) {
            secondTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancleTimer();
        cancleSecondTimer();
    }

    public void next() {
        cancleTimer();
        lineFirst.setVisibility(View.GONE);
        lineSecond.setVisibility(View.VISIBLE);
    }
}
