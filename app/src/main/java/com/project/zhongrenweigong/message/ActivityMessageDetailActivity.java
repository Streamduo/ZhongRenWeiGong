package com.project.zhongrenweigong.message;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.UploadImgListAdapter;
import com.project.zhongrenweigong.message.bean.MessageListsBean;
import com.project.zhongrenweigong.util.SpacingItemDecoration;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityMessageDetailActivity extends BaseActivity<SystemMessageDetailPresent> {

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
    @BindView(R.id.recy_img_list)
    RecyclerView recyImgList;

    private MessageListsBean messageListsBean;
    private UploadImgListAdapter uploadImgListAdapter;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        messageListsBean = (MessageListsBean) getIntent().getSerializableExtra("MessageListsBean");
        if (messageListsBean == null) {
            return;
        } else {
//            teTitle.setText(messageListsBean.shopName);
//            teMsgTitle.setText(messageListsBean.messageIntro);
//            GlideDownLoadImage.getInstance().loadCircleImage(mContext, messageListsBean.shopLogo,
//                    imgSenderHead, R.mipmap.big_default_user_head);
//            teSendPlatform.setText(messageListsBean.shopName);
        }

        recyImgList.setLayoutManager(new GridLayoutManager(this, 3));
        recyImgList.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 20));
        uploadImgListAdapter = new UploadImgListAdapter(R.layout.item_upload_voucher);
        recyImgList.setAdapter(uploadImgListAdapter);
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
        return R.layout.activity_activity_message_detail;
    }

    @Override
    public SystemMessageDetailPresent bindPresent() {
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

}
