package com.project.zhongrenweigong.message;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.BusinessHomePageActivity;
import com.project.zhongrenweigong.business.adapter.UploadImgListAdapter;
import com.project.zhongrenweigong.business.bean.UploadImgBean;
import com.project.zhongrenweigong.message.bean.MessageListsBean;
import com.project.zhongrenweigong.message.bean.SystemAndActivityBean;
import com.project.zhongrenweigong.message.bean.SystemAndActivityDataBean;
import com.project.zhongrenweigong.util.SpacingItemDecoration;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPreview;

public class ActivityMessageDetailActivity extends BaseActivity<ActivityMessageDetailPresent> {

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
    private List<UploadImgBean> imgBeanList = new ArrayList<>();
    private List<String> images;
    private ArrayList<String> imgList = new ArrayList<>();

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

        recyImgList.setLayoutManager(new GridLayoutManager(this, 3));
        recyImgList.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 20));
        uploadImgListAdapter = new UploadImgListAdapter(R.layout.item_upload_voucher);
        uploadImgListAdapter.setShowDelete(false);
        recyImgList.setAdapter(uploadImgListAdapter);
        uploadImgListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                imgList.addAll(images);
                PhotoPreview.builder()
                        .setPhotos(imgList)
                        .setCurrentItem(position)
                        .setShowDeleteButton(false)
                        .start(ActivityMessageDetailActivity.this);
            }
        });
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
    public ActivityMessageDetailPresent bindPresent() {
        return new ActivityMessageDetailPresent();
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
        teTitle.setText(data.shopName);
        teMsgTitle.setText(data.title);
        teSendDate.setText(data.time);
        GlideDownLoadImage.getInstance().loadCircleImage(mContext, data.shopLogo,
                imgSenderHead, R.mipmap.big_default_user_head);
        teSendPlatform.setText(data.shopName);
        teMsgIntro.setText(data.content);
        images = data.images;
        if (images != null && images.size() > 0) {
            for (String imgUri : images) {
                UploadImgBean uploadImgBean = new UploadImgBean();
                uploadImgBean.setImgUri(imgUri);
                imgBeanList.add(uploadImgBean);
            }
            uploadImgListAdapter.setNewData(imgBeanList);
        }
    }

}
