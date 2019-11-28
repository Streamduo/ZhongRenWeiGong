package com.project.zhongrenweigong.business.manager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.MineShopListPageAdapter;
import com.project.zhongrenweigong.business.bean.BusinessTypeDataBean;
import com.project.zhongrenweigong.business.bean.BussinessTypeBean;
import com.project.zhongrenweigong.currency.event.RefreshIndustrySearchEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.KeyboardUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineShopListActivity extends BaseActivity<MineShopListPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.discover_list_vp)
    ViewPager discoverListVp;
    @BindView(R.id.vs_no_data)
    ViewStub vsNoData;
    private String searchText;
    private List<BusinessTypeDataBean> businessTypeDataBeanList;

    @Override
    public void initView() {
        teTitle.setText("店铺管理");
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchText = edSearch.getText().toString();
                    if (searchText != null && !searchText.equals("")) {
                        //关闭软键盘
                        KeyboardUtils.hideSoftInput(MineShopListActivity.this);
                    }
                    EventBus.getDefault().post(new RefreshIndustrySearchEvent(searchText,
                            discoverListVp.getCurrentItem()));
                    return true;
                }
                return false;
            }
        });

    }

    private void setViewPager() {
        MineShopListPageAdapter mineShopListPageAdapter = new MineShopListPageAdapter(getSupportFragmentManager(), businessTypeDataBeanList);
        discoverListVp.setAdapter(mineShopListPageAdapter);
        tabLayout.setViewPager(discoverListVp);
        discoverListVp.setCurrentItem(0);
        discoverListVp.setOffscreenPageLimit(businessTypeDataBeanList.size());
        tabLayout.onPageSelected(0);
        tabLayout.getTitleView(0).setTextSize(17);
        discoverListVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < businessTypeDataBeanList.size(); i++) {
                    if (position == i) { //选中的字体大小
                        tabLayout.getTitleView(i).setTextSize(17);
                    } else {
                        tabLayout.getTitleView(i).setTextSize(16);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    public void initAfter() {
        LoginMsg userAccent = AcacheUtils.getInstance(this).getUserAccent();
        getP().getMyShopCategory(userAccent.mbId);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine_shop_list;
    }

    @Override
    public MineShopListPresent bindPresent() {
        return new MineShopListPresent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public void setData(BussinessTypeBean bussinessTypeBean) {
        businessTypeDataBeanList = bussinessTypeBean.getData();
        if (businessTypeDataBeanList != null && businessTypeDataBeanList.size() > 0) {
            setViewPager();
        }else {
            vsNoData.inflate();
        }
    }
}
