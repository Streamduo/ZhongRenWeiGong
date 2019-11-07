package com.project.zhongrenweigong.mine.set;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.login.RegisterActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidbase.kit.ToastManager;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_used_password)
    EditText edUsedPassword;
    @BindView(R.id.ed_new_password)
    EditText edNewPassword;
    @BindView(R.id.ed_queren_password)
    EditText edQuerenPassword;
    @BindView(R.id.te_send_ok)
    TextView teSendOk;

    @Override
    public void initView() {
        teTitle.setText("修改密码");
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
        return R.layout.activity_change_password;
    }

    @Override
    public ChangePasswordPresent bindPresent() {
        return new ChangePasswordPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teSendOk.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_send_ok:
                String oldpsd = edUsedPassword.getText().toString();
                String newpsd = edNewPassword.getText().toString();
                String queRenPsd = edQuerenPassword.getText().toString();

                if (oldpsd == null || oldpsd.equals("")) {
                    showToastShort("请输入原密码");
                    return;
                }

                if (oldpsd.length() < 6) {
                    showToastShort("密码不小于6位");
                    return;
                }

                if (newpsd == null || newpsd.equals("")) {
                    showToastShort("请输入新密码");
                    return;
                }

                if (newpsd.length() < 6) {
                    showToastShort("新密码不小于6位");
                    return;
                }

                if (queRenPsd == null || queRenPsd.equals("")) {
                    showToastShort("请输入确认密码");
                    return;
                }

                if (queRenPsd.length() < 6 || !queRenPsd.equals(newpsd)) {
                    showToastShort("两次密码不一致");
                    return;
                }
                LoginMsg userAccent = AcacheUtils.getInstance(ChangePasswordActivity.this).getUserAccent();
                getP().updatePassword(userAccent.mbId,oldpsd,newpsd,queRenPsd);
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
