package com.project.zhongrenweigong.business.teach;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.flyco.tablayout.SlidingTabLayout;
import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.baidumap.LocationService;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.BusinessListActivity;
import com.project.zhongrenweigong.business.adapter.TeachListPageAdapter;
import com.project.zhongrenweigong.business.bean.TeachListBean;
import com.project.zhongrenweigong.currency.event.SearchEvent;
import com.project.zhongrenweigong.util.KeyboardUtils;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.view.MyViewPager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeachListActivity extends BaseActivity<TeachListPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_mine_address)
    TextView teMineAddress;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.discover_list_vp)
    ViewPager discoverListVp;
    private LocationService locationService;
    public String province = "北京市";
    private String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        teTitle.setText("教育");
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchText = edSearch.getText().toString();
                    if (searchText != null && !searchText.equals("")) {
                        EventBus.getDefault().post(new SearchEvent(searchText, discoverListVp.getCurrentItem()));
                        //关闭软键盘
                        KeyboardUtils.hideSoftInput(TeachListActivity.this);
                    } else {
                        EventBus.getDefault().post(new SearchEvent(searchText, discoverListVp.getCurrentItem()));
                    }
                    return true;
                }
                return false;
            }
        });
        TeachListPageAdapter teachListPageAdapter = new TeachListPageAdapter(getSupportFragmentManager());
        discoverListVp.setAdapter(teachListPageAdapter);
        tabLayout.setViewPager(discoverListVp);
        discoverListVp.setCurrentItem(0);
        discoverListVp.setOffscreenPageLimit(2);
        tabLayout.onPageSelected(0);
        tabLayout.getTitleView(0).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        discoverListVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < 2; i++) {
                    if (position == i) { //选中的字体大小
                        tabLayout.getTitleView(i).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    } else {
                        tabLayout.getTitleView(i).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
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

    }

    @Override
    public void onResume() {
        super.onResume();
        locationService = App.getInstance().locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_teach_list;
    }

    @Override
    public TeachListPresent bindPresent() {
        return new TeachListPresent();
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

    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                province = location.getProvince();
//                double longitude = location.getLongitude();//经度
//                double latitude = location.getLatitude();//纬度
//                String district = location.getDistrict();//地区
//                teMineAddress.setText(province + "." + district);
                if (location.getLocType() == BDLocation.TypeServerError) {//"服务端网络定位失败，可以反馈IMEI号和大体定位时间到
                    // loc-bugs@baidu.com，会有人追查原因"
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    //"网络不同导致定位失败，请检查网络是否通畅"
                    showToastShort("定位失败，请检查网络后重试");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    showToastShort("定位失败，请检查网络后重试");
                    //"无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            super.onConnectHotSpotMessage(s, i);
        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * @param locType 当前定位类型
         * @param diagnosticType 诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */
        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.unregisterListener(mListener);
        locationService.stop();
    }
}
