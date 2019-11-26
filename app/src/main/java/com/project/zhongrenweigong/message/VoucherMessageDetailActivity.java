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
import butterknife.OnClick;

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
    @BindView(R.id.te_cance)
    TextView teCance;
    @BindView(R.id.te_return_goods)
    TextView teReturnGoods;
    @BindView(R.id.rl_pass)
    RelativeLayout rlPass;
    private MessageListsBean messageListsBean;
    private String role;
    private String voucherId;
    private UploadImgListAdapter uploadImgListAdapter;
    private List<UploadImgBean> imgList = new ArrayList<>();
    private LoginMsg userAccent;
    private String messageId;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
        Intent intent = getIntent();
        messageListsBean = (MessageListsBean) intent.getSerializableExtra("messageListsBean");
        role = messageListsBean.role;
        voucherId = messageListsBean.voucherId;
        messageId = messageListsBean.messageId;
        String shopName = messageListsBean.shopName;
        String mbId = messageListsBean.mbId;
        String checkStatus = messageListsBean.checkStatus;
        if (role.equals("1")) {
            teTitle.setText("ID：" + mbId);
            rlBusiness.setVisibility(View.VISIBLE);
        } else {
            teTitle.setText(shopName);
            if (checkStatus.equals("0")) {
                teNoPass.setText("上传凭证待审核");
                rlNoPass.setVisibility(View.VISIBLE);
            } else if (checkStatus.equals("1")) {
                teNoPass.setText("未通过");
                rlNoPass.setVisibility(View.VISIBLE);
            } else {
                rlPass.setVisibility(View.VISIBLE);
            }
        }
        recyImgList.setLayoutManager(new GridLayoutManager(this, 3));
        recyImgList.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 20));
        uploadImgListAdapter = new UploadImgListAdapter(R.layout.item_upload_voucher);
        uploadImgListAdapter.setShowDelete(false);
        recyImgList.setAdapter(uploadImgListAdapter);
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
        teNoPass.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_pass:
                getP().updateVoucherStatus(userAccent.mbId,messageId,1);
                break;
            case R.id.te_refuse:
                getP().updateVoucherStatus(userAccent.mbId,messageId,2);
                break;
            case R.id.te_cance:
                finish();
                break;
            case R.id.te_return_goods:

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
            List<String> voucherImages = data.voucherImages;
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
