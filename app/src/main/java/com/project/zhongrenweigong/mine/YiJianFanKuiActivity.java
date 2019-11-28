package com.project.zhongrenweigong.mine;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YiJianFanKuiActivity extends BaseActivity<YiJianFanKuiPesent> {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_report_intro)
    EditText edReportIntro;
    @BindView(R.id.te_max_size)
    TextView teMaxSize;
    @BindView(R.id.te_send)
    TextView teSend;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("意见反馈");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_fanui;
    }

    @Override
    public YiJianFanKuiPesent bindPresent() {
        return new YiJianFanKuiPesent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teSend.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }
}
