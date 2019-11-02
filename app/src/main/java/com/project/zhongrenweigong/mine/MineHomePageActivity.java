package com.project.zhongrenweigong.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineHomePageActivity extends BaseActivity<MineHomePagePresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.img_user_head)
    ImageView imgUserHead;
    @BindView(R.id.te_user_name)
    TextView teUserName;
    @BindView(R.id.te_user_occupation)
    TextView teUserOccupation;
    @BindView(R.id.te_user_id)
    TextView teUserId;
    @BindView(R.id.te_dynamic_size)
    TextView teDynamicSize;
    @BindView(R.id.te_follow_size)
    TextView teFollowSize;
    @BindView(R.id.te_fans_size)
    TextView teFansSize;
    @BindView(R.id.te_guanzhu)
    TextView teGuanzhu;
    @BindView(R.id.te_authentication)
    TextView teAuthentication;
    @BindView(R.id.recy_dynamic)
    RecyclerView recyDynamic;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;

    @Override
    public void initView() {

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine_home_page;
    }

    @Override
    public MineHomePagePresent bindPresent() {
        return new MineHomePagePresent();
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
