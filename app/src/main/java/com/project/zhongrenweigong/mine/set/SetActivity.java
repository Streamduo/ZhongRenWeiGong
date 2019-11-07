package com.project.zhongrenweigong.mine.set;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.ActivityManager;
import com.project.zhongrenweigong.util.LoginOutUtils;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.util.XCache;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.project.zhongrenweigong.currency.Constans.ADDRES;
import static com.project.zhongrenweigong.currency.Constans.ISTOURIST;

public class SetActivity extends BaseActivity<SetPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.rl_upload_head)
    RelativeLayout rlUploadHead;
    @BindView(R.id.te_phone)
    TextView tePhone;
    @BindView(R.id.rl_change_phone)
    RelativeLayout rlChangePhone;
    @BindView(R.id.rl_password_manager)
    RelativeLayout rlPasswordManager;
    @BindView(R.id.rl_from_company)
    RelativeLayout rlFromCompany;
    @BindView(R.id.te_exit_login)
    TextView teExitLogin;

    @Override
    public void initView() {
        teTitle.setText("设置");
    }

    @Override
    public void initAfter() {
        LoginMsg userAccent = AcacheUtils.getInstance(this).getUserAccent();
        if (userAccent != null && userAccent.mbId != null && !userAccent.mbId.equals("")) {
            GlideDownLoadImage.getInstance().loadCircleImage(this, userAccent.headUrl,
                    imgHead, R.mipmap.user_default_head);
            tePhone.setText(userAccent.mbPhone);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_set;
    }

    @Override
    public SetPresent bindPresent() {
        return new SetPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        rlUploadHead.setOnClickListener(this);
        rlChangePhone.setOnClickListener(this);
        rlPasswordManager.setOnClickListener(this);
        rlFromCompany.setOnClickListener(this);
        teExitLogin.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.rl_upload_head:

                break;
            case R.id.rl_change_phone:
                Router.newIntent(SetActivity.this).to(ChangePhoneActivity.class).launch();
                break;
            case R.id.rl_password_manager:
                Router.newIntent(SetActivity.this).to(ChangePasswordActivity.class).launch();
                break;
            case R.id.rl_from_company:

                break;
            case R.id.te_exit_login:
                LoginOutUtils.loginOut(SetActivity.this);
                Router.newIntent(SetActivity.this).putInt("isLoginOut",1).to(LoginActivity.class).launch();
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
