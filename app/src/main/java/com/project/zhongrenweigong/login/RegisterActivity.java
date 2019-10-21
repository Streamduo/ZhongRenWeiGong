package com.project.zhongrenweigong.login;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.MerchantCertificationActivity;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;

public class RegisterActivity extends BaseActivity<RegisterPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.ed_phone_num)
    EditText edPhoneNum;
    @BindView(R.id.te_send_ems)
    TextView teSendEms;
    @BindView(R.id.ed_ems_num)
    EditText edEmsNum;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_queren_password)
    EditText edQuerenPassword;
    @BindView(R.id.te_next_shiming)
    TextView teNext;
    @BindView(R.id.vs_shiming)
    ViewStub vsShiming;
    @BindView(R.id.line_register)
    LinearLayout lineRegister;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.vs_xinxi_edit)
    ViewStub vsXinxiEdit;
    @BindView(R.id.vs_register_ok)
    ViewStub vsRegisterOk;
    private View shiMingView;
    private View xinXiEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        setFullScren(true);
        teTitle.setText("注册账号");
    }

    @Override
    public void initAfter() {
        teBack.setOnClickListener(this);
        teSendEms.setOnClickListener(this);
        teNext.setOnClickListener(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterPresent bindPresent() {
        return new RegisterPresent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_send_ems:
                Router.newIntent(this).to(MerchantCertificationActivity.class).launch();
                break;
//            case R.id.te_business:
//                Router.newIntent(this).to(MerchantCertificationActivity.class).launch();
//                break;
            case R.id.te_next_shiming:
                initShiMing();
                break;
        }
    }

    private void initShiMing() {
        teTitle.setText("实名/人脸认证");
        imgTop.setBackgroundResource(R.mipmap.geren_people_face);
        lineRegister.setVisibility(View.GONE);

        shiMingView = vsShiming.inflate();
        RelativeLayout rlShibie = shiMingView.findViewById(R.id.rl_shibie);
        ImageView imgCardJust = shiMingView.findViewById(R.id.img_card_just);
        ImageView imgCardBack = shiMingView.findViewById(R.id.img_card_back);
        EditText edEmsNum = shiMingView.findViewById(R.id.ed_ems_num);
        EditText edCardNum = shiMingView.findViewById(R.id.ed_card_num);
        EditText edCardDate = shiMingView.findViewById(R.id.ed_card_date);
        final CheckBox ckGeren = shiMingView.findViewById(R.id.ck_geren);
        final CheckBox ckShangjia = shiMingView.findViewById(R.id.ck_shangjia);
        Spinner spIndustry = shiMingView.findViewById(R.id.sp_industry);
        TextView teNextXinxi = (TextView) shiMingView.findViewById(R.id.te_next_xinxi);

        teNextXinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRegisterOk();
            }
        });

        ckGeren.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckShangjia.setChecked(false);
//                    imgTop.setBackgroundResource(R.mipmap.geren_people_face);
                }
            }
        });
        ckShangjia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckGeren.setChecked(false);
//                    imgTop.setBackgroundResource(R.mipmap.register_num02);
                }
            }
        });

    }

    private void initRegisterOk() {
        teTitle.setText("注册成功");
        imgTop.setBackgroundResource(R.mipmap.register_ok);
        shiMingView.setVisibility(View.GONE);
        vsRegisterOk.inflate();

    }

    private void initXinxiEdit() {
        teTitle.setText("信息编辑");
        imgTop.setBackgroundResource(R.mipmap.register_num03);
        shiMingView.setVisibility(View.GONE);

        xinXiEditView = vsXinxiEdit.inflate();
        RecyclerView recyPeopleList = shiMingView.findViewById(R.id.recy_people_list);
        Spinner spCommodityIndustry = shiMingView.findViewById(R.id.sp_commodity_industry);
        RelativeLayout rlSelectArea = xinXiEditView.findViewById(R.id.rl_select_area);
        TextView teArea = (TextView) shiMingView.findViewById(R.id.te_area);
        ImageView imgUpload = shiMingView.findViewById(R.id.img_upload);
        ImageView upLoad = shiMingView.findViewById(R.id.upload);
        EditText edShopName = shiMingView.findViewById(R.id.ed_shop_name);
        EditText edPhoneNum = shiMingView.findViewById(R.id.ed_phone_num);
        EditText edAddress = shiMingView.findViewById(R.id.ed_address);
        TextView teJump = (TextView) shiMingView.findViewById(R.id.te_jump);
        TextView teOk = (TextView) shiMingView.findViewById(R.id.te_ok);
    }

}
