package com.project.zhongrenweigong.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.mine.adapter.CompensationRecordPageAdapter;
import com.project.zhongrenweigong.util.TablayoutUtil;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompensationRecordActivity extends BaseActivity<CompensationPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.tb_recode_industy)
    TabLayout tbRecodeIndusty;
    @BindView(R.id.vp_record)
    ViewPager vpRecord;
    private int recordType;

    @Override
    public void initView() {
        teTitle.setText("赔付记录");
        recordType = getIntent().getIntExtra("recordType", 0);
    }

    @Override
    public void initAfter() {
        CompensationRecordPageAdapter adapter = new CompensationRecordPageAdapter(getSupportFragmentManager());
        vpRecord.setAdapter(adapter);
        tbRecodeIndusty.setupWithViewPager(vpRecord);
        TablayoutUtil.setIndicator(tbRecodeIndusty, 20, 20);
        vpRecord.setCurrentItem(recordType);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_compensation_record;
    }

    @Override
    public CompensationPresent bindPresent() {
        return new CompensationPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
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
}
