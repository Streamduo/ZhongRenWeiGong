package com.project.zhongrenweigong.currency;


import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.BusinessListActivity;
import com.project.zhongrenweigong.business.adapter.BusinessListAdapter;
import com.project.zhongrenweigong.business.bean.BusinessShopListBean;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.util.KeyboardUtils;
import com.project.zhongrenweigong.util.QueShengManager;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity<SearchResultPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.recy_business_list)
    RecyclerView recyBusinessList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int currentPage = 1;
    private BusinessListAdapter listAdapter;
    private String searchText;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        searchText = getIntent().getStringExtra("searchText");
        edSearch.setText(searchText);
        edSearch.setSelection(searchText.length());
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchText = edSearch.getText().toString();
                    if (searchText != null && !searchText.equals("")) {
                        //关闭软键盘
                        KeyboardUtils.hideSoftInput(SearchResultActivity.this);
                        currentPage = 1;
                        getP().findAllShopByLikeName(currentPage, searchText);
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
                getP().findAllShopByLikeName(currentPage, searchText);
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                getP().findAllShopByLikeName(currentPage, searchText);
            }
        });
    }

    @Override
    public void initAfter() {
        recyBusinessList.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new BusinessListAdapter(R.layout.item_business_list);
        recyBusinessList.setAdapter(listAdapter);
        getP().findAllShopByLikeName(currentPage, searchText);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    public SearchResultPresent bindPresent() {
        return new SearchResultPresent();
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
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, listAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }

}
