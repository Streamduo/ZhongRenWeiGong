package com.project.zhongrenweigong.business;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Fuduo on 2019/10/21 11:24
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessListActivity extends BaseActivity<BussinessListPresent> {
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_search)
    TextView teSearch;
    @BindView(R.id.img_saoyosao)
    ImageView imgSaoyosao;
    @BindView(R.id.recy_people_list)
    RecyclerView recyPeopleList;
    @BindView(R.id.recy_business_fenlei)
    RecyclerView recyBusinessFenlei;
    @BindView(R.id.recy_business_list)
    RecyclerView recyBusinessList;

    @Override
    public void initView() {

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bussiness_list;
    }

    @Override
    public BussinessListPresent bindPresent() {
        return new BussinessListPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
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
