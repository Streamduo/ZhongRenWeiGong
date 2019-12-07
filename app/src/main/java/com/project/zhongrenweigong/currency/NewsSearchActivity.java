package com.project.zhongrenweigong.currency;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    public void initView() {

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
                break;
            case R.id.img_delete_history:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
