package com.project.zhongrenweigong.business.manager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.bean.CommodityDataBean;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import java.io.Serializable;

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
    private CommodityDataBean commodityDataBean;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        commodityDataBean = (CommodityDataBean) getIntent().getSerializableExtra("CommodityDataBean");
        if (commodityDataBean != null) {
            edCommodityName.setText(commodityDataBean.name);
            edCommodityName.setSelection(commodityDataBean.name.length());
            edCommodityPrice.setText(String.valueOf(commodityDataBean.price));
            edCommodityPrice.setSelection(String.valueOf(commodityDataBean.price).length());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                String commodityName = edCommodityName.getText().toString();
                String commodityPrice = edCommodityPrice.getText().toString();
                if (commodityName.isEmpty()) {
                    showToastShort("商品名称不能为空");
                }
                if (commodityPrice.isEmpty()) {
                    showToastShort("商品价格不能为空");
                }
                LoginMsg userAccent = AcacheUtils.getInstance(AddCommodityActivity.this).getUserAccent();
                if (commodityDataBean != null) {
                    getP().updateGoods(userAccent.mbId, commodityName, commodityPrice);
                }else {
                    getP().addGoods(userAccent.mbId, commodityName, commodityPrice);
                }
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
