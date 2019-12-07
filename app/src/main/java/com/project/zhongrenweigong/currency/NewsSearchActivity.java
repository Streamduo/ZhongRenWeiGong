package com.project.zhongrenweigong.currency;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
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
import com.project.zhongrenweigong.currency.adapter.SearchNewsHistoryAdapter;
import com.project.zhongrenweigong.currency.bean.SearchHistoryBean;
import com.project.zhongrenweigong.util.DividerGridItemDecoration;
import com.project.zhongrenweigong.util.KeyboardUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.project.zhongrenweigong.currency.SearchHistoryManager.SEARCH_NEWS_HISTORY;

/**
 * 作者：Fuduo on 2019/12/7 10:07
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class NewsSearchActivity extends BaseActivity<NewsSearchPresent> {
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.recy_tuijian_list)
    RecyclerView recyTuijianList;
    @BindView(R.id.img_delete_history)
    ImageView imgDeleteHistory;
    @BindView(R.id.recy_history_search_list)
    RecyclerView recyHistorySearchList;
    @BindView(R.id.line_history_search)
    LinearLayout lineHistorySearch;
    private SearchNewsHistoryAdapter searchHistoryAdapter;
    private List<SearchHistoryBean> searchHistoryList;

    @Override
    public void initView() {

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = edSearch.getText().toString();
                    if (searchText != null && !searchText.equals("")) {
                        //关闭软键盘
                        KeyboardUtils.hideSoftInput(NewsSearchActivity.this);
                        SearchHistoryManager.getInstance(NewsSearchActivity.this).
                                saveSearchHistory(new SearchHistoryBean().setContent(searchText), SEARCH_NEWS_HISTORY);
                    }
                    return true;
                }
                return false;
            }
        });

        searchHistoryAdapter = new SearchNewsHistoryAdapter(R.layout.item_search_news);
        recyHistorySearchList.setLayoutManager(new GridLayoutManager(this, 2));
        recyHistorySearchList.setAdapter(searchHistoryAdapter);

        searchHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchHistoryBean item = (SearchHistoryBean) adapter.getItem(position);
                String searchKey = item.searchHistory;
                if (searchKey != null && !searchKey.equals("")) {
                    edSearch.setText(searchKey);
                    edSearch.setSelection(searchKey.length());
                    SearchHistoryManager.getInstance(NewsSearchActivity.this).
                            saveSearchHistory(new SearchHistoryBean().setContent(searchKey), SEARCH_NEWS_HISTORY);
                }
            }
        });
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_news_search;
    }

    @Override
    public NewsSearchPresent bindPresent() {
        return new NewsSearchPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgDeleteHistory.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.img_search:
                String searchText = edSearch.getText().toString();
                if (searchText != null && !searchText.equals("")) {
                    //关闭软键盘
                    KeyboardUtils.hideSoftInput(NewsSearchActivity.this);
                    SearchHistoryManager.getInstance(NewsSearchActivity.this).
                            saveSearchHistory(new SearchHistoryBean().setContent(searchText), SEARCH_NEWS_HISTORY);

                }
                break;
            case R.id.img_delete_history:
                SearchHistoryManager.getInstance(NewsSearchActivity.this).romoveHistory(SEARCH_NEWS_HISTORY);
                searchHistoryAdapter.getData().clear();
                searchHistoryAdapter.notifyDataSetChanged();
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
        searchHistoryList = SearchHistoryManager.getInstance(NewsSearchActivity.this).getSearchHistory(SEARCH_NEWS_HISTORY);
        if (searchHistoryList != null && searchHistoryList.size() > 0) {
            lineHistorySearch.setVisibility(View.VISIBLE);
            searchHistoryAdapter.setNewData(searchHistoryList);
        } else {
            lineHistorySearch.setVisibility(View.GONE);
        }
    }
}
