package com.project.zhongrenweigong.mine;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBankCardActivity extends BaseActivity<AddBankCardPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_card_people)
    EditText edCardPeople;
    @BindView(R.id.ed_card_num)
    EditText edCardNum;
    @BindView(R.id.te_next)
    TextView teNext;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("添加银行卡");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_bankcard;
    }

    @Override
    public AddBankCardPresent bindPresent() {
        return new AddBankCardPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teNext.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.te_back:
                finish();
                break;
            case R.id.te_next:
                String cardPeople = edCardPeople.getText().toString();
                String cardNum = edCardNum.getText().toString();
                if (TextUtils.isEmpty(cardPeople)){
                    showToastShort("持卡人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(cardNum)){
                    showToastShort("银行卡号不能为空");
                    return;
                }
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
}
