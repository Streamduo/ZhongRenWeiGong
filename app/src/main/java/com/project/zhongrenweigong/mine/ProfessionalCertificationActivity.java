package com.project.zhongrenweigong.mine;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfessionalCertificationActivity extends BaseActivity<ProfessionalCertificationPresent> {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_right_title)
    TextView teRightTitle;
    @BindView(R.id.ed_work)
    EditText edWork;
    @BindView(R.id.te_send)
    TextView teSend;
    @BindView(R.id.ed_company)
    EditText edCompany;
    @BindView(R.id.ed_company_address)
    EditText edCompanyAddress;
//    private TextWatcher textWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            if (charSequence.length() > 0) {
//                teSend.setBackgroundResource(R.drawable.bg_bac_339aff_10);
//            } else {
//                teSend.setBackgroundResource(R.drawable.bg_bac_9898a0_10);
//            }
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//
//        }
//    };

    @Override
    public void initView() {
        teTitle.setText("职业认证");
        teRightTitle.setText("编辑");
        teRightTitle.setTextSize(16);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_professional_certification;
    }

    @Override
    public ProfessionalCertificationPresent bindPresent() {
        return new ProfessionalCertificationPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teRightTitle.setOnClickListener(this);
        teSend.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_right_title:
                break;
            case R.id.te_send:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
