package com.project.zhongrenweigong.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyIntegralCompensationActivity extends BaseActivity<MyIntegralCompensationPresent> {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_right_title)
    TextView teRightTitle;
    @BindView(R.id.te_integral_size)
    TextView teIntegralSize;
    @BindView(R.id.te_yesterday_integral)
    TextView teYesterdayIntegral;
    @BindView(R.id.te_ti_xian)
    TextView teTiXian;
    @BindView(R.id.recy_integral_list)
    RecyclerView recyIntegralList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    @BindView(R.id.te_text1)
    TextView teText1;
    @BindView(R.id.te_text2)
    TextView teText2;
    private int currentPage = 1;
    private int type;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, MyIntegralCompensationActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 1:
                teTitle.setText("我的赔付");
                teText1.setText("我的赔付");
                teText2.setText("赔付明细");
                teRightTitle.setText("赔付指南");
                break;
            case 2:
                teTitle.setText("我的积分");
                teText1.setText("我的积分");
                teText2.setText("积分明细");
                teRightTitle.setText("积分指南");
                break;
        }
        teRightTitle.setTextSize(15);
        recyIntegralList.setLayoutManager(new LinearLayoutManager(this));

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                initAfter();
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                initAfter();
            }
        });
    }

    @Override
    public void initAfter() {
        switch (type) {
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_integra_compensationl;
    }

    @Override
    public MyIntegralCompensationPresent bindPresent() {
        return new MyIntegralCompensationPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teRightTitle.setOnClickListener(this);
        teTiXian.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_right_title:
                StatementActivity.start(MyIntegralCompensationActivity.this, type);
                break;
            case R.id.te_ti_xian:
                switch (type) {
                    case 1:
                        break;
                    case 2:
                        break;
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
