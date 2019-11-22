package com.project.zhongrenweigong.message;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.home.bean.MessageListBean;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemMessageDetailActivity extends BaseActivity<MessageDetailPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_msg_title)
    TextView teMsgTitle;
    @BindView(R.id.img_sender_head)
    ImageView imgSenderHead;
    @BindView(R.id.te_send_platform)
    TextView teSendPlatform;
    @BindView(R.id.te_send_date)
    TextView teSendDate;
    @BindView(R.id.img_mechanism_head)
    ImageView imgMechanismHead;
    @BindView(R.id.te_mechanism_title)
    TextView teMechanismTitle;
    @BindView(R.id.te_mechanism_intro)
    TextView teMechanismIntro;
    @BindView(R.id.te_mechanism_address)
    TextView teMechanismAddress;
    @BindView(R.id.te_mechanism_phone)
    TextView teMechanismPhone;
    private String typeId;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        typeId = getIntent().getStringExtra("typeId");

    }

    @Override
    public void initAfter() {
        getP().getMessageDetail(typeId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_message_detail;
    }

    @Override
    public MessageDetailPresent bindPresent() {
        return null;
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

    public void setData(MessageListBean messageListBean) {

    }

}
