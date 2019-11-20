package com.project.zhongrenweigong.home;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.home.adapter.MessageListAdapter;
import com.project.zhongrenweigong.home.bean.MessageDataBean;
import com.project.zhongrenweigong.home.bean.MessageListBean;
import com.project.zhongrenweigong.util.UtilsStyle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailActivity extends BaseActivity<MessageDetailPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.xitong_msg_list)
    RecyclerView xitongMsgList;
    private String typeId;
    private MessageListAdapter messageListDetailAdapter;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        typeId = getIntent().getStringExtra("typeId");
        if (typeId.equals("0")) {
            teTitle.setText("系统通知");
        } else {
            teTitle.setText("赔付通知");
        }
        xitongMsgList.setLayoutManager(new LinearLayoutManager(this));
        messageListDetailAdapter = new MessageListAdapter(R.layout.item_xitong_msg_list);
        xitongMsgList.setAdapter(messageListDetailAdapter);
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
        List<MessageDataBean> data = messageListBean.getData();
        if (data != null && data.size() > 0) {
            messageListDetailAdapter.setNewData(data);
        }
    }

}
