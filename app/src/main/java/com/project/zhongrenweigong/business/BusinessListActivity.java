package com.project.zhongrenweigong.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.BusinessTypeListAdapter;
import com.project.zhongrenweigong.business.bean.BusinessTypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.project.zhongrenweigong.home.HomeFragment.TYPE;

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
    private int type;
    private BusinessTypeListAdapter businessTypeListAdapter;

    @Override
    public void initView() {
        setFull(false);
        Intent intent = getIntent();
        type = intent.getIntExtra(TYPE, 0);
    }

    @Override
    public void initAfter() {
        List<BusinessTypeBean> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            BusinessTypeBean businessTypeBean = new BusinessTypeBean();
            businessTypeBean.name = "包子粥店+" + i;
            list.add(businessTypeBean);
        }
        recyBusinessFenlei.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        businessTypeListAdapter = new BusinessTypeListAdapter(R.layout.item_business_fenlei_list);
        recyBusinessFenlei.setAdapter(businessTypeListAdapter);
        businessTypeListAdapter.setNewData(list);

        businessTypeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                businessTypeListAdapter.setItem(position);
                businessTypeListAdapter.notifyDataSetChanged();
            }
        });
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
