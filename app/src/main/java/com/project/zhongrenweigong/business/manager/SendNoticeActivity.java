package com.project.zhongrenweigong.business.manager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendNoticeActivity extends BaseActivity<SendNoticePresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.ed_notice_title)
    EditText edNoticeTitle;
    @BindView(R.id.ed_notice)
    EditText edNotice;

    @Override
    public void initView() {
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
        return R.layout.activity_send_notice;
    }

    @Override
    public SendNoticePresent bindPresent() {
        return new SendNoticePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgSend.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.img_send:
                String noticeTitle = edNoticeTitle.getText().toString();
                String notice = edNotice.getText().toString();
                if (noticeTitle.isEmpty()){
                    showToastShort("公告标题不能为空");
                    return;
                }
                if (notice.isEmpty()){
                    showToastShort("公告内容不能为空");
                    return;
                }
                LoginMsg userAccent = AcacheUtils.getInstance(SendNoticeActivity.this).getUserAccent();
                getP().addAnnouncement(userAccent.mbId,noticeTitle,notice);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
