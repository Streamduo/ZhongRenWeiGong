package com.project.zhongrenweigong.home;

import android.os.Bundle;
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
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.currency.SearchBusinessActivity;
import com.project.zhongrenweigong.currency.SearchHistoryManager;
import com.project.zhongrenweigong.currency.SearchResultActivity;
import com.project.zhongrenweigong.currency.bean.SearchHistoryBean;
import com.project.zhongrenweigong.currency.event.RefreshHomeEvent;
import com.project.zhongrenweigong.home.adapter.AddressHistoryListAdapter;
import com.project.zhongrenweigong.home.adapter.AddressListAdapter;
import com.project.zhongrenweigong.home.adapter.AddressSearchHistoryListAdapter;
import com.project.zhongrenweigong.home.bean.AddressBean;
import com.project.zhongrenweigong.home.bean.AddressDataBean;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.util.KeyboardUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.project.zhongrenweigong.currency.SearchHistoryManager.SEARCH_ADDDRESS_HISTORY;
import static com.project.zhongrenweigong.currency.SearchHistoryManager.SEARCH_BUSINESS_HISTORY;


public class AddressLocationActivity extends BaseActivity<AddressLocationPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.img_msg)
    ImageView imgMsg;
    @BindView(R.id.recy_lately_address_list)
    RecyclerView recyLatelyAddressList;
    @BindView(R.id.line_lately_address)
    LinearLayout lineLatelyAddress;
    @BindView(R.id.recy_history_address_list)
    RecyclerView recyHistoryAddressList;
    @BindView(R.id.line_history_address)
    LinearLayout lineHistoryAddress;
    @BindView(R.id.line_show_history)
    LinearLayout lineShowHistory;
    @BindView(R.id.recy_search_address_list)
    RecyclerView recySearchAddressList;
    @BindView(R.id.line_search_address)
    LinearLayout lineSearchAddress;
    private boolean isTourist;
    private AddressListAdapter listAdapter;
    private List<SearchHistoryBean> searchHistoryList;
    private AddressHistoryListAdapter latelylistAdapter;
    private AddressSearchHistoryListAdapter addressSearchHistoryListAdapter;

    @Override
    public void initView() {
        UtilsStyle.statusBarLightMode(this);
        isTourist = SharedPref.getInstance(this).getBoolean(Constans.ISTOURIST, true);

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = edSearch.getText().toString();
                    if (searchText != null && !searchText.equals("")) {
                        //关闭软键盘
                        KeyboardUtils.hideSoftInput(AddressLocationActivity.this);
                        SearchHistoryManager.getInstance(AddressLocationActivity.this).
                                saveSearchHistory(new SearchHistoryBean().setContent(searchText), SEARCH_ADDDRESS_HISTORY);
                        updateSearchHistoryAdapter();
                        getP().merchantfindOpenAddr(searchText);
                    } else {
                        showToastShort("请输入搜索城市名称");
                    }
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void initAfter() {
        recyHistoryAddressList.setLayoutManager(new GridLayoutManager(this, 3));
        listAdapter = new AddressListAdapter(R.layout.item_address);
        recyHistoryAddressList.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressDataBean addressDataBean = listAdapter.getData().get(position);
                if (addressDataBean != null) {
                    EventBus.getDefault().post(new RefreshHomeEvent(addressDataBean.address));
                }
            }
        });

        recyLatelyAddressList.setLayoutManager(new GridLayoutManager(this, 3));
        latelylistAdapter = new AddressHistoryListAdapter(R.layout.item_address);
        recyLatelyAddressList.setAdapter(latelylistAdapter);
        latelylistAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchHistoryBean searchHistoryBean = latelylistAdapter.getData().get(position);
                if (searchHistoryBean != null) {
                    EventBus.getDefault().post(new RefreshHomeEvent(searchHistoryBean.searchHistory));
                }
            }
        });
        recySearchAddressList.setLayoutManager(new LinearLayoutManager(this));
        addressSearchHistoryListAdapter = new AddressSearchHistoryListAdapter(R.layout.item_search_address);
        recySearchAddressList.setAdapter(addressSearchHistoryListAdapter);
        addressSearchHistoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressDataBean addressDataBean = addressSearchHistoryListAdapter.getData().get(position);
                if (addressDataBean != null) {
                    EventBus.getDefault().post(new RefreshHomeEvent(addressDataBean.address));
                    finish();
                }
            }
        });
        getP().memberGetOpenAddr();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_address_location;
    }

    @Override
    public AddressLocationPresent bindPresent() {
        return new AddressLocationPresent();
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
                    Router.newIntent(AddressLocationActivity.this).to(LoginActivity.class).launch();
                } else {
                    Router.newIntent(AddressLocationActivity.this).to(MessageListActivity.class).launch();
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
        updateSearchHistoryAdapter();
    }

    public void setData(AddressBean addressBean) {
        List<AddressDataBean> data = addressBean.getData();
        if (data != null && data.size() > 0) {
            listAdapter.setNewData(data);
        }
    }

    public void setSearchData(AddressBean addressBean) {
        List<AddressDataBean> data = addressBean.getData();
        if (data != null && data.size() > 0) {
            lineShowHistory.setVisibility(View.GONE);
            lineSearchAddress.setVisibility(View.VISIBLE);
            addressSearchHistoryListAdapter.setNewData(data);
        }
    }

    private void updateSearchHistoryAdapter() {
        searchHistoryList = SearchHistoryManager.getInstance(AddressLocationActivity.this).getSearchHistory(SEARCH_ADDDRESS_HISTORY);
        if (searchHistoryList != null && searchHistoryList.size() > 0) {
            latelylistAdapter.setNewData(searchHistoryList);
        }
    }

}
