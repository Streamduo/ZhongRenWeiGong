package com.project.zhongrenweigong.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_ems_code)
    EditText edEmsCode;
    @BindView(R.id.te_send_ems)
    TextView teSendEms;
    @BindView(R.id.te_login)
    TextView teLogin;
    @BindView(R.id.te_xieyi)
    TextView teXieyi;
    @BindView(R.id.te_change_login)
    TextView teChangeLogin;
    @BindView(R.id.img_login_wb)
    ImageView imgLoginWb;
    @BindView(R.id.img_login_wx)
    ImageView imgLoginWx;
    @BindView(R.id.img_login_qq)
    ImageView imgLoginQq;
    @BindView(R.id.img_show_psd)
    ImageView imgShowPsd;
    @BindView(R.id.rl_psd)
    RelativeLayout rlPsd;
    @BindView(R.id.rl_ems)
    RelativeLayout rlEms;

    private int isLoginOut;
    private int type = 0;
    private int isShow = 0;
    private CountDownTimer timer;
    private String phoneNum;
    private TextWatcher textPhoneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() == 11) {
                teSendEms.setEnabled(true);
                teSendEms.setTextColor(getResources().getColor(R.color.app_FA3C3C));
            } else {
                teSendEms.setEnabled(false);
                teSendEms.setTextColor(getResources().getColor(R.color.app_ABABAB));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private TextWatcher textEmsWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() == 6) {
                teLogin.setEnabled(true);
                teLogin.setBackgroundResource(R.drawable.bg_bac_fa3c3c_25);
            } else {
                teLogin.setEnabled(false);
                teLogin.setBackgroundResource(R.drawable.bg_bac_c2c2c2_25);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private TextWatcher textPsdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >= 6) {
                teLogin.setEnabled(true);
                teLogin.setBackgroundResource(R.drawable.bg_bac_fa3c3c_25);
            } else {
                teLogin.setEnabled(false);
                teLogin.setBackgroundResource(R.drawable.bg_bac_c2c2c2_25);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        teTitle.setText("登录");
        edPhoneNum.addTextChangedListener(textPhoneWatcher);
        SpannableString phoneHint = new SpannableString("请输入手机号");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(10, true);//设置字体大小 true表示单位是sp
        phoneHint.setSpan(ass, 0, phoneHint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edPhoneNum.setHint(new SpannedString(phoneHint));

        edPassword.addTextChangedListener(textPsdWatcher);
        SpannableString psdHint = new SpannableString("请输入密码");//定义hint的值
        AbsoluteSizeSpan assPsd = new AbsoluteSizeSpan(10, true);//设置字体大小 true表示单位是sp
        psdHint.setSpan(assPsd, 0, psdHint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edPassword.setHint(new SpannedString(psdHint));

        edEmsCode.addTextChangedListener(textEmsWatcher);
        SpannableString emsHint = new SpannableString("请输入验证码");//定义hint的值
        AbsoluteSizeSpan assEms = new AbsoluteSizeSpan(10, true);//设置字体大小 true表示单位是sp
        emsHint.setSpan(assEms, 0, emsHint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edEmsCode.setHint(new SpannedString(emsHint));
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
        teLogin.setOnClickListener(this);
        teChangeLogin.setOnClickListener(this);
        imgShowPsd.setOnClickListener(this);
        teXieyi.setOnClickListener(this);
        teSendEms.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
//            case R.id.te_register:
//                Router.newIntent(this).to(RegisterActivity.class).launch();
//                break;
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
            case R.id.te_xieyi:

                break;
            case R.id.img_show_psd:
                String psd = edPassword.getText().toString();
                String passWord = psd.replaceAll("\\D", "");
                if (TextUtils.isEmpty(passWord)) {
                    return;
                }
                if (isShow == 0) {
                    isShow = 1;
                    imgShowPsd.setImageDrawable(getResources().getDrawable(R.mipmap.eye_open));
                    edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isShow = 0;
                    imgShowPsd.setImageDrawable(getResources().getDrawable(R.mipmap.eye_close));
                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                edPassword.setSelection(passWord.length());
                break;
            case R.id.te_change_login:
                if (type == 0) {
                    type = 1;
                    rlEms.setVisibility(View.GONE);
                    rlPsd.setVisibility(View.VISIBLE);
                    teChangeLogin.setText("忘记密码");
                } else if (type == 1) {
                    Router.newIntent(this).to(FindPasswordActivity.class).launch();
                }
                break;
            case R.id.te_login:
                String phoneNumText = edPhoneNum.getText().toString();
                String pn = phoneNumText.replaceAll("\\D", "");
                String passwordText = edPassword.getText().toString();

                String ipAddress = SystemUtil.getIpAddress(LoginActivity.this);

                if (TextUtils.isEmpty(pn)) {
                    ToastManager.showShort(LoginActivity.this, getString(R.string.phonenumber_null));
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(pn)) {
                    ToastManager.showShort(LoginActivity.this, getString(R.string.phonenumber_error));
                    return;
                }
                if (type == 1) {
                    if (passwordText == null || passwordText.equals("")) {
                        ToastManager.showShort(LoginActivity.this, getString(R.string.password_null));
                        return;
                    }
                    if (passwordText.length() < 6) {
                        ToastManager.showShort(LoginActivity.this, getString(R.string.password_cn_6));
                        return;
                    }
                    if (!CheckInputUtil.checkPassword(passwordText)) {
                        ToastManager.showShort(LoginActivity.this, getString(R.string.password_error));
                    }
                } else {

                }
                getP().login("adress", ipAddress, "1", passwordText, phoneNumText, isLoginOut);
                break;
        }
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
                teSendEms.setTextColor(getResources().getColor(R.color.app_ABABAB));
                teSendEms.setText(Html.fromHtml("<font color= '#FA3C3C'>" + l / 1000 + "</font> " + "秒后重新发送"));
            }

            @Override
            public void onFinish() {
                teSendEms.setText("再次发送");
                teSendEms.setEnabled(true);
                teSendEms.setTextColor(getResources().getColor(R.color.app_FA3C3C));
            }
        }.start();
    }

    private void cancleTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancleTimer();
    }
}
