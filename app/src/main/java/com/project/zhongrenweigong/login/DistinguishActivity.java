package com.project.zhongrenweigong.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DistinguishActivity extends BaseActivity<DistinguishPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.te_sort)
    TextView teSort;
    @BindView(R.id.img_pause)
    ImageView imgPause;

    @Override
    public void initView() {
       teTitle.setText("人脸识别");
       imgShare.setBackgroundResource(R.mipmap.change_camera);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_distinguish;
    }

    @Override
    public DistinguishPresent bindPresent() {
        return new DistinguishPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        teSort.setOnClickListener(this);
        imgPause.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.img_share:
                break;
            case R.id.te_sort:
                break;
            case R.id.img_pause:
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
