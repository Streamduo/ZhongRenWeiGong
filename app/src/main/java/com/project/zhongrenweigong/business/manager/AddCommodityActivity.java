package com.project.zhongrenweigong.business.manager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCommodityActivity extends BaseActivity<AddCommodityPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.ed_commodity_name)
    EditText edCommodityName;
    @BindView(R.id.ed_commodity_price)
    EditText edCommodityPrice;
    @BindView(R.id.img_upload)
    ImageView imgUpload;

    @Override
    public void initView() {
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_commodity;
    }

    @Override
    public AddCommodityPresent bindPresent() {
        return new AddCommodityPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgSend.setOnClickListener(this);
        imgUpload.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.img_send:

                break;
            case R.id.img_upload:

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
