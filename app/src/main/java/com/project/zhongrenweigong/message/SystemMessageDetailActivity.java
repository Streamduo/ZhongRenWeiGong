package com.project.zhongrenweigong.message;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.message.bean.MessageListsBean;
import com.project.zhongrenweigong.message.bean.SystemAndActivityBean;
import com.project.zhongrenweigong.message.bean.SystemAndActivityDataBean;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemMessageDetailActivity extends BaseActivity<SystemMessageDetailPresent> {

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
    @BindView(R.id.te_msg_intro)
    TextView teMsgIntro;

    private MessageListsBean messageListsBean;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        messageListsBean = (MessageListsBean) getIntent().getSerializableExtra("MessageListsBean");
        if (messageListsBean == null || messageListsBean.messageId == null) {
            return;
        } else {
            getP().getMessageDetail(messageListsBean.messageId);
        }
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
        return R.layout.activity_message_detail;
    }

    @Override
    public SystemMessageDetailPresent bindPresent() {
        return new SystemMessageDetailPresent();
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

    public void setData(SystemAndActivityBean systemAndActivityBean) {
        SystemAndActivityDataBean data = systemAndActivityBean.getData();
        teTitle.setText(data.messageMain);
        teMsgTitle.setText(data.title);
        teSendDate.setText(data.time);
        GlideDownLoadImage.getInstance().loadCircleImage(mContext, data.messageMainLogo,
                imgSenderHead, R.mipmap.big_default_user_head);
        teSendPlatform.setText(data.messageMain);
        teMsgIntro.setText(data.content);
    }
}
