package com.project.zhongrenweigong.currency;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.currency.adapter.SearchHistoryAdapter;
import com.project.zhongrenweigong.currency.bean.SearchHistoryBean;
import com.project.zhongrenweigong.home.MessageListActivity;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.util.KeyboardUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.project.zhongrenweigong.currency.SearchHistoryManager.SEARCH_BUSINESS_HISTORY;

public class SearchBusinessActivity extends BaseActivity<SearchBusinessPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.img_msg)
    ImageView imgMsg;
    @BindView(R.id.recy_find_search_list)
    RecyclerView recyFindSearchList;
    @BindView(R.id.recy_history_search_list)
    RecyclerView recyHistorySearchList;
    @BindView(R.id.line_find_search)
    LinearLayout lineFindSearch;
    @BindView(R.id.line_history_search)
    LinearLayout lineHistorySearch;
    private boolean isTourist;
    private List<SearchHistoryBean> searchHistoryList;
    private SearchHistoryAdapter searchHistoryAdapter;

    @Override
    public void initView() {
        isTourist = SharedPref.getInstance(this).getBoolean(Constans.ISTOURIST, true);

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = edSearch.getText().toString();
                    if (searchText != null && !searchText.equals("")) {
                        //关闭软键盘
                        KeyboardUtils.hideSoftInput(SearchBusinessActivity.this);
                        SearchHistoryManager.getInstance(SearchBusinessActivity.this).
                                saveSearchHistory(new SearchHistoryBean().setContent(searchText), SEARCH_BUSINESS_HISTORY);
                        Router.newIntent(SearchBusinessActivity.this).
                                putString("searchText", searchText)
                                .to(SearchResultActivity.class)
                                .launch();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initAfter() {
        initHistory();
    }

    private void initHistory() {

        searchHistoryAdapter = new SearchHistoryAdapter(R.layout.item_search_history,1);
        recyHistorySearchList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyHistorySearchList.setAdapter(searchHistoryAdapter);

        searchHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchHistoryBean item = (SearchHistoryBean) adapter.getItem(position);
                String searchKey = item.searchHistory;
                if (searchKey != null && !searchKey.equals("")) {
                    edSearch.setText(searchKey);
                    edSearch.setSelection(searchKey.length());
                    SearchHistoryManager.getInstance(SearchBusinessActivity.this).
                            saveSearchHistory(new SearchHistoryBean().setContent(searchKey), SEARCH_BUSINESS_HISTORY);
                    Router.newIntent(SearchBusinessActivity.this).
                            putString("searchText", searchKey)
                            .to(SearchResultActivity.class)
                            .launch();
                }
            }
        });

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search_business;
    }

    @Override
    public SearchBusinessPresent bindPresent() {
        return new SearchBusinessPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgMsg.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.img_msg:
                if (isTourist) {
                    Router.newIntent(SearchBusinessActivity.this).to(LoginActivity.class).launch();
                } else {
                    Router.newIntent(SearchBusinessActivity.this).to(MessageListActivity.class).launch();
                }
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
        updateSearchHistoryAdapter();
    }

    private void updateSearchHistoryAdapter() {
        searchHistoryList = SearchHistoryManager.getInstance(SearchBusinessActivity.this).getSearchHistory(SEARCH_BUSINESS_HISTORY);
        if (searchHistoryList != null && searchHistoryList.size() > 0) {
            searchHistoryAdapter.setNewData(searchHistoryList);
        } else {
            recyHistorySearchList.setVisibility(View.GONE);
        }
    }
}
