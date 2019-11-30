package com.project.zhongrenweigong.mine;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
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
import com.project.zhongrenweigong.business.adapter.MineShopListPageAdapter;
import com.project.zhongrenweigong.business.adapter.MyFollowListPageAdapter;
import com.project.zhongrenweigong.business.bean.BusinessTypeDataBean;
import com.project.zhongrenweigong.business.bean.BussinessTypeBean;
import com.project.zhongrenweigong.business.manager.MineShopListPresent;
import com.project.zhongrenweigong.currency.event.RefreshIndustrySearchEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.KeyboardUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFollowListActivity extends BaseActivity<MyFollowShopListPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.discover_list_vp)
    ViewPager discoverListVp;
    @BindView(R.id.vs_no_data)
    ViewStub vsNoData;
    private List<BusinessTypeDataBean> businessTypeDataBeanList;
    private LocationService locationService;
    private BDLocation bdLocation;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("关注");
    }

    private void setViewPager() {
        MyFollowListPageAdapter myFollowListPageAdapter = new MyFollowListPageAdapter(getSupportFragmentManager(),
                businessTypeDataBeanList, bdLocation);
        discoverListVp.setAdapter(myFollowListPageAdapter);
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

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_follow_list;
    }

    @Override
    public MyFollowShopListPresent bindPresent() {
        return new MyFollowShopListPresent();
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

    @Override
    protected void onStart() {
        super.onStart();
        locationService = App.getInstance().locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }

    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                bdLocation = location;
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
            getCatagory();
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

    private void getCatagory() {
        LoginMsg userAccent = AcacheUtils.getInstance(this).getUserAccent();
        getP().getLickShopCatagory(userAccent.mbId);
    }

    public void setCatagoryData(BussinessTypeBean bussinessTypeBean) {
        businessTypeDataBeanList = bussinessTypeBean.getData();
        if (businessTypeDataBeanList != null && businessTypeDataBeanList.size() > 0) {
            setViewPager();
        } else {
            vsNoData.inflate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationService.unregisterListener(mListener);
        locationService.stop();
    }
}
