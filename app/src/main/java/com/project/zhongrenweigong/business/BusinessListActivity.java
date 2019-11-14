package com.project.zhongrenweigong.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.BusinessListAdapter;
import com.project.zhongrenweigong.business.adapter.BusinessTypeListAdapter;
import com.project.zhongrenweigong.business.bean.BusinessShopListBean;
import com.project.zhongrenweigong.business.bean.BusinessTypeBean;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.util.KeyboardUtils;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.project.zhongrenweigong.currency.Constans.ADDRES;
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
    EditText teSearch;
    @BindView(R.id.img_saoyosao)
    ImageView imgSaoyosao;
    @BindView(R.id.recy_people_list)
    RecyclerView recyPeopleList;
    @BindView(R.id.recy_business_fenlei)
    RecyclerView recyBusinessFenlei;
    @BindView(R.id.recy_business_list)
    RecyclerView recyBusinessList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int type;
    private int currentPage = 1;
    private BusinessTypeListAdapter businessTypeListAdapter;
    private BusinessListAdapter listAdapter;
    private String searchText;

    @Override
    public void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra(TYPE, 0);

        teSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchText = teSearch.getText().toString();
                    if (searchText != null && !searchText.equals("")) {
                        //关闭软键盘
                        KeyboardUtils.hideSoftInput(BusinessListActivity.this);
                        currentPage = 1;
                        getP().selectAllShop(type, currentPage, 1, searchText, 1);
                    } else {
                        showToastShort("请输入搜索内容");
                    }

                    return true;
                }
                return false;
            }
        });

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                if (searchText != null && !searchText.equals("")) {
                    getP().selectAllShop(type, currentPage, 1, searchText, 1);
                } else {
                    getP().selectAllShop(type, currentPage, 0, "", 1);
                }
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                if (searchText != null && !searchText.equals("")) {
                    getP().selectAllShop(type, currentPage, 1, searchText, 1);
                } else {
                    getP().selectAllShop(type, currentPage, 0, "", 1);
                }
            }
        });

        List<BusinessTypeBean> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            BusinessTypeBean businessTypeBean = new BusinessTypeBean();
            businessTypeBean.name = "包子粥店+" + i;
            list.add(businessTypeBean);
        }
        recyBusinessFenlei.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
    public void initAfter() {
        recyBusinessList.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new BusinessListAdapter(R.layout.item_business_list);
        recyBusinessList.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DataBean item = listAdapter.getItem(position);
                Router.newIntent(BusinessListActivity.this).putString("shopId",item.shopId).to(BusinessHomePageActivity.class).launch();
            }
        });

        getP().selectAllShop(type, currentPage, 0, "", 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
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

    public void setData(BusinessShopListBean businessShopListBean) {
        int pageSize = businessShopListBean.pageSize;
        List<DataBean> data = businessShopListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                listAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                smRefresh.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                listAdapter.addData(data);
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
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }
}
