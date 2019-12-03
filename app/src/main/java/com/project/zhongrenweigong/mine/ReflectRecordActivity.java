package com.project.zhongrenweigong.mine;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.bean.IndustryDataBean;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.adapter.ReflectRecordListAdapter;
import com.project.zhongrenweigong.mine.bean.ReflectBean;
import com.project.zhongrenweigong.mine.bean.ReflectDataBean;
import com.project.zhongrenweigong.mine.bean.ReflectListsBean;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.QueShengManager;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReflectRecordActivity extends BaseActivity<ReflectRecordPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_reflect_date)
    TextView teReflectDate;
    @BindView(R.id.te_reflect_money)
    TextView teReflectMoney;
    @BindView(R.id.line_select_date)
    LinearLayout lineSelectDate;
    @BindView(R.id.recy_business_list)
    RecyclerView recyBusinessList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int currentPage = 1;
    private ReflectRecordListAdapter reflectRecordListAdapter;
    private String year;
    private String month;
    private LoginMsg userAccent;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("提现明细");
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        teReflectDate.setText(year + "年" + month + "月");
        recyBusinessList.setLayoutManager(new LinearLayoutManager(this));
        reflectRecordListAdapter = new ReflectRecordListAdapter(R.layout.item_reflect_record_list);
        recyBusinessList.setAdapter(reflectRecordListAdapter);
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
        getP().getWithdrawDepositDetail(currentPage, userAccent.mbId, year, month);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reflect_record;
    }

    @Override
    public ReflectRecordPresent bindPresent() {
        return new ReflectRecordPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        lineSelectDate.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.line_select_date:
                initTimePicker();
                break;
        }
    }

    private void initTimePicker() {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.i("pvTime", "onTimeSelect");
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat monthFormat = new SimpleDateFormat("HH");
                year = yearFormat.format(date);
                month = monthFormat.format(date);
                teReflectDate.setText(year + "年" + month + "月");
                initAfter();
            }
        }).setType(new boolean[]{true, true, false, false, false, false})
                .isDialog(false) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .setCancelColor(getResources().getColor(R.color.app_text_33))
                .setSubmitColor(getResources().getColor(R.color.app_369EFF))
                .isAlphaGradient(true)
                .build();
        pvTime.show();
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

    public void setData(ReflectBean reflectBean) {
        int pageSize = reflectBean.pageSize;
        ReflectDataBean data = reflectBean.getData();
        List<ReflectListsBean> lists = data.lists;
        double sumMoney = data.sumMoney;
        teReflectMoney.setText("提现¥" + String.valueOf(sumMoney));
        if (lists != null && lists.size() > 0) {
            if (currentPage == 1) {
                reflectRecordListAdapter.setNewData(lists);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                reflectRecordListAdapter.addData(lists);
                smRefresh.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        } else {
            getDataError();
        }

        if (currentPage == pageSize) {
            smRefresh.setEnableLoadMore(false);
        }
    }

    public void getDataError() {
        if (currentPage == 1) {
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, reflectRecordListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }
}
