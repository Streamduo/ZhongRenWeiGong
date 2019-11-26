package com.project.zhongrenweigong.message;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.UploadImgListAdapter;
import com.project.zhongrenweigong.business.bean.UploadImgBean;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.message.bean.MessageListsBean;
import com.project.zhongrenweigong.message.bean.VoucherDataBean;
import com.project.zhongrenweigong.message.bean.VoucherMessageDetailBean;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.SpacingItemDecoration;
import com.project.zhongrenweigong.util.UtilsStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPreview;

public class VoucherMessageDetailActivity extends BaseActivity<VoucherMessageDetailPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_voucher_intro)
    TextView teVoucherIntro;
    @BindView(R.id.recy_img_list)
    RecyclerView recyImgList;
    @BindView(R.id.te_pass)
    TextView tePass;
    @BindView(R.id.te_refuse)
    TextView teRefuse;
    @BindView(R.id.rl_business)
    RelativeLayout rlBusiness;
    @BindView(R.id.te_no_pass)
    TextView teNoPass;
    @BindView(R.id.rl_no_pass)
    RelativeLayout rlNoPass;

    private MessageListsBean messageListsBean;
    private String voucherId;
    private UploadImgListAdapter uploadImgListAdapter;
    private List<UploadImgBean> imgList = new ArrayList<>();
    private LoginMsg userAccent;
    private String messageId;
    private List<String> voucherImages;
    private ArrayList<String> imgArrayList = new ArrayList<>();

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
        Intent intent = getIntent();
        messageListsBean = (MessageListsBean) intent.getSerializableExtra("messageListsBean");

        voucherId = messageListsBean.voucherId;
        messageId = messageListsBean.messageId;
        String shopName = messageListsBean.shopName;
        String mbId = messageListsBean.mbId;
        String role = messageListsBean.role;
        String checkStatus = messageListsBean.checkStatus;
        if (role.equals("1")) {
            teTitle.setText("ID：" + mbId);
            if (checkStatus.equals("0")) {
                rlBusiness.setVisibility(View.VISIBLE);
            } else if (checkStatus.equals("1")) {
                teNoPass.setText("已同意");
                rlNoPass.setVisibility(View.VISIBLE);
            } else if (checkStatus.equals("2")) {
                teNoPass.setText("已拒绝");
                rlNoPass.setVisibility(View.VISIBLE);
            }
        } else {
            teTitle.setText(shopName);
            if (checkStatus.equals("0")) {
                teNoPass.setText("上传凭证待审核");
                rlNoPass.setVisibility(View.VISIBLE);
            } else if (checkStatus.equals("1")) {
                teNoPass.setText("已通过");
                rlNoPass.setVisibility(View.VISIBLE);
            } else if (checkStatus.equals("2")) {
                teNoPass.setText("未通过");
                rlNoPass.setVisibility(View.VISIBLE);
            }
        }
        recyImgList.setLayoutManager(new GridLayoutManager(this, 3));
        recyImgList.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 20));
        uploadImgListAdapter = new UploadImgListAdapter(R.layout.item_upload_voucher);
        uploadImgListAdapter.setShowDelete(false);
        recyImgList.setAdapter(uploadImgListAdapter);
        uploadImgListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                imgArrayList.addAll(voucherImages);
                PhotoPreview.builder()
                        .setPhotos(imgArrayList)
                        .setCurrentItem(position)
                        .setShowDeleteButton(false)
                        .start(VoucherMessageDetailActivity.this);
            }
        });
    }

    @Override
    public void initAfter() {
        getP().getUploadVoucherById(voucherId);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_voucher_message_detail;
    }

    @Override
    public VoucherMessageDetailPresent bindPresent() {
        return new VoucherMessageDetailPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        tePass.setOnClickListener(this);
        teRefuse.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_pass://商家通过审核
                getP().updateVoucherStatus(messageListsBean.mbId, messageListsBean.mcId, messageId, 1, voucherId);
                break;
            case R.id.te_refuse://商家拒绝审核
                getP().updateVoucherStatus(messageListsBean.mbId, messageListsBean.mcId, messageId, 2, voucherId);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    public void setData(VoucherMessageDetailBean voucherMessageDetailBean) {
        VoucherDataBean data = voucherMessageDetailBean.getData();
        if (data == null) {
            return;
        } else {
            String text = data.text;
            teVoucherIntro.setText(text);
            voucherImages = data.voucherImages;
            if (voucherImages != null && voucherImages.size() > 0) {
                for (int i = 0; i < voucherImages.size(); i++) {
                    String s = voucherImages.get(i);
                    UploadImgBean uploadImgBean = new UploadImgBean();
                    uploadImgBean.setImgUri(s);
                    imgList.add(uploadImgBean);
                }
                uploadImgListAdapter.setNewData(imgList);
            }
        }
    }

}
