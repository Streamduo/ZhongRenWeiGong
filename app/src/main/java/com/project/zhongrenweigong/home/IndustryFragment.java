package com.project.zhongrenweigong.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.baidumap.LocationService;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.BusinessListActivity;
import com.project.zhongrenweigong.business.car.CarListActivity;
import com.project.zhongrenweigong.business.commerce.CommerecListActivity;
import com.project.zhongrenweigong.business.hotel.HotelListActivity;
import com.project.zhongrenweigong.business.house.HouseListActivity;
import com.project.zhongrenweigong.business.teach.TeachListActivity;
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.currency.SearchBusinessActivity;
import com.project.zhongrenweigong.currency.event.RefreshHomeEvent;
import com.project.zhongrenweigong.currency.zxing.android.CaptureActivity;
import com.project.zhongrenweigong.home.adapter.UltraViewPagerAdapter;
import com.project.zhongrenweigong.home.bean.DataBean;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.util.ScreenUtils;
import com.project.zhongrenweigong.util.StatusBarUtils;
import com.tmall.ultraviewpager.UltraViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.project.zhongrenweigong.currency.Constans.ADDRES;

/**
 * 作者：Fuduo on 2019/10/17 10:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class IndustryFragment extends BaseFragment<IndustryPresent> {

    public static final String TAG = "IndustryFragment";
    public static final String TYPE = "type";
    @BindView(R.id.ad_viewpager)
    UltraViewPager adViewpager;
    @BindView(R.id.te_address)
    TextView teAddress;
    @BindView(R.id.te_search)
    TextView teSearch;
    @BindView(R.id.img_saoyosao)
    ImageView imgSaoyosao;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.te_food)
    TextView teFood;
    @BindView(R.id.te_teach)
    TextView teTeach;
    @BindView(R.id.te_house)
    TextView teHouse;
    @BindView(R.id.te_car)
    TextView teCar;
    @BindView(R.id.te_retailers)
    TextView teRetailers;
    @BindView(R.id.te_travel)
    TextView teTravel;

    Unbinder unbinder;
    private boolean isTourist;
    private LocationService locationService;

    @Override
    public void onResume() {
        super.onResume();
        StatusBarUtils.with(getActivity()).init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtils.with(getActivity()).init();
    }

    @Override
    public void initView() {
        isTourist = SharedPref.getInstance(getContext()).getBoolean(Constans.ISTOURIST, true);
    }

    public void initViewPager(List<DataBean> carouselEntities) {
        UltraViewPagerAdapter adapter = new UltraViewPagerAdapter(getActivity(), carouselEntities);
        adViewpager.setAdapter(adapter);
        adViewpager.initIndicator();

        adViewpager.setAutoScroll(5000);
        adViewpager.setInfiniteLoop(true);
        adViewpager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusResId(R.mipmap.radio_yellow)
                .setNormalResId(R.mipmap.white_round)
                .setIndicatorPadding(ScreenUtils.dp2px(8))
                .setMargin(0, 0, 0, ScreenUtils.dp2px(8))
                .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)
                .build();
    }

    @Override
    public void initAfter() {
        getP().getVerification();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_industry;
    }

    @Override
    public IndustryPresent bindPresent() {
        return new IndustryPresent();
    }

    @Override
    public void setListener() {
        teAddress.setOnClickListener(this);
        teSearch.setOnClickListener(this);
        imgMessage.setOnClickListener(this);
        imgSaoyosao.setOnClickListener(this);

        teCar.setOnClickListener(this);
        teFood.setOnClickListener(this);
        teHouse.setOnClickListener(this);
        teRetailers.setOnClickListener(this);
        teTeach.setOnClickListener(this);
        teTravel.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_address:
                Router.newIntent(getActivity()).to(AddressLocationActivity.class).launch();
                break;
            case R.id.te_search:
                Router.newIntent(getActivity()).to(SearchBusinessActivity.class).launch();
                break;
            case R.id.img_message:
                if (isTourist) {
                    Router.newIntent(getActivity()).to(LoginActivity.class).launch();
                } else {
                    Router.newIntent(getActivity()).to(MessageListActivity.class).launch();
                }
                break;
            case R.id.img_saoyosao:
                if (isTourist) {
                    Router.newIntent(getActivity()).to(CaptureActivity.class).launch();
                } else {
                    Router.newIntent(getActivity()).to(CaptureActivity.class).launch();
                }
                break;
            case R.id.te_food:
                Router.newIntent(getActivity()).putInt(TYPE, 1)
                        .to(BusinessListActivity.class)
                        .launch();
                break;
            case R.id.te_teach:
                Router.newIntent(getActivity()).putInt(TYPE, 2)
                        .to(TeachListActivity.class)
                        .launch();
                break;
            case R.id.te_house:
                Router.newIntent(getActivity()).putInt(TYPE, 3)
                        .to(HouseListActivity.class)
                        .launch();
                break;
            case R.id.te_car:
                Router.newIntent(getActivity()).putInt(TYPE, 4)
                        .to(CarListActivity.class)
                        .launch();
                break;
            case R.id.te_retailers:
                Router.newIntent(getActivity()).putInt(TYPE, 5)
                        .to(CommerecListActivity.class)
                        .launch();
                break;
            case R.id.te_travel:
                Router.newIntent(getActivity()).putInt(TYPE, 6)
                        .to(HotelListActivity.class)
                        .launch();
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        locationService = App.getInstance().locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
//        int type = getIntent().getIntExtra("from", 0);
//        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        } else if (type == 1) {
            locationService.start();
//        }
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                int tag = 1;
//                StringBuffer sb = new StringBuffer(256);
//                sb.append("time : ");
//                /**
//                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
//                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
//                 */
//                sb.append(location.getTime());
//                sb.append("\nlocType : ");// 定位类型
//                sb.append(location.getLocType());
//                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
//                sb.append(location.getLocTypeDescription());
//                sb.append("\nlatitude : ");// 纬度
//                sb.append(location.getLatitude());
//                sb.append("\nlongtitude : ");// 经度
//                sb.append(location.getLongitude());
//                sb.append("\nradius : ");// 半径
//                sb.append(location.getRadius());
//                sb.append("\nCountryCode : ");// 国家码
//                sb.append(location.getCountryCode());
//                sb.append("\nProvince : ");// 获取省份
//                sb.append(location.getProvince());
//                sb.append("\nCountry : ");// 国家名称
//                sb.append(location.getCountry());
//                sb.append("\ncitycode : ");// 城市编码
//                sb.append(location.getCityCode());
//                sb.append("\ncity : ");// 城市
//                sb.append(location.getCity());
//                sb.append("\nDistrict : ");// 区
//                sb.append(location.getDistrict());
//                sb.append("\nTown : ");// 获取镇信息
//                sb.append(location.getTown());
//                sb.append("\nStreet : ");// 街道
//                sb.append(location.getStreet());
//                sb.append("\naddr : ");// 地址信息
//                sb.append(location.getAddrStr());
//                sb.append("\nStreetNumber : ");// 获取街道号码
//                sb.append(location.getStreetNumber());
//                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
//                sb.append(location.getUserIndoorState());
//                sb.append("\nDirection(not all devices have value): ");
//                sb.append(location.getDirection());// 方向
//                sb.append("\nlocationdescribe: ");
//                sb.append(location.getLocationDescribe());// 位置语义化信息
//                sb.append("\nPoi: ");// POI信息
//                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//                    for (int i = 0; i < location.getPoiList().size(); i++) {
//                        Poi poi = (Poi) location.getPoiList().get(i);
//                        sb.append("poiName:");
//                        sb.append(poi.getName() + ", ");
//                        sb.append("poiTag:");
//                        sb.append(poi.getTags() + "\n");
//                    }
//                }
//                if (location.getPoiRegion() != null) {
//                    sb.append("PoiRegion: ");// 返回定位位置相对poi的位置关系，仅在开发者设置需要POI信息时才会返回，在网络不通或无法获取时有可能返回null
//                    PoiRegion poiRegion = location.getPoiRegion();
//                    sb.append("DerectionDesc:"); // 获取POIREGION的位置关系，ex:"内"
//                    sb.append(poiRegion.getDerectionDesc() + "; ");
//                    sb.append("Name:"); // 获取POIREGION的名字字符串
//                    sb.append(poiRegion.getName() + "; ");
//                    sb.append("Tags:"); // 获取POIREGION的类型
//                    sb.append(poiRegion.getTags() + "; ");
//                    sb.append("\nSDK版本: ");
//                }
//                sb.append(locationService.getSDKVersion()); // 获取SDK版本
//                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                    sb.append("\nspeed : ");
//                    sb.append(location.getSpeed());// 速度 单位：km/h
//                    sb.append("\nsatellite : ");
//                    sb.append(location.getSatelliteNumber());// 卫星数目
//                    sb.append("\nheight : ");
//                    sb.append(location.getAltitude());// 海拔高度 单位：米
//                    sb.append("\ngps status : ");
//                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
//                    sb.append("\ndescribe : ");
//                    sb.append("gps定位成功");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                    // 运营商信息
//                    if (location.hasAltitude()) {// *****如果有海拔高度*****
//                        sb.append("\nheight : ");
//                        sb.append(location.getAltitude());// 单位：米
//                    }
//                    sb.append("\noperationers : ");// 运营商信息
//                    sb.append(location.getOperators());
//                    sb.append("\ndescribe : ");
//                    sb.append("网络定位成功");
//                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                    sb.append("\ndescribe : ");
//                    sb.append("离线定位成功，离线定位结果也是有效的");
//                } else if (location.getLocType() == BDLocation.TypeServerError) {
//                    sb.append("\ndescribe : ");
//                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
//                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//                }
                String province = location.getProvince();
                double longitude = location.getLongitude();//经度
                double latitude = location.getLatitude();//纬度
                showToastShort(province);
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
//            int tag = 2;
//            StringBuffer sb = new StringBuffer(256);
//            sb.append("诊断结果: ");
//            if (locType == BDLocation.TypeNetWorkLocation) {
//                if (diagnosticType == 1) {
//                    sb.append("网络定位成功，没有开启GPS，建议打开GPS会更好");
//                    sb.append("\n" + diagnosticMessage);
//                } else if (diagnosticType == 2) {
//                    sb.append("网络定位成功，没有开启Wi-Fi，建议打开Wi-Fi会更好");
//                    sb.append("\n" + diagnosticMessage);
//                }
//            } else if (locType == BDLocation.TypeOffLineLocationFail) {
//                if (diagnosticType == 3) {
//                    sb.append("定位失败，请您检查您的网络状态");
//                    sb.append("\n" + diagnosticMessage);
//                }
//            } else if (locType == BDLocation.TypeCriteriaException) {
//                if (diagnosticType == 4) {
//                    sb.append("定位失败，无法获取任何有效定位依据");
//                    sb.append("\n" + diagnosticMessage);
//                } else if (diagnosticType == 5) {
//                    sb.append("定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位");
//                    sb.append(diagnosticMessage);
//                } else if (diagnosticType == 6) {
//                    sb.append("定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试");
//                    sb.append("\n" + diagnosticMessage);
//                } else if (diagnosticType == 7) {
//                    sb.append("定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试");
//                    sb.append("\n" + diagnosticMessage);
//                } else if (diagnosticType == 9) {
//                    sb.append("定位失败，无法获取任何有效定位依据");
//                    sb.append("\n" + diagnosticMessage);
//                }
//            } else if (locType == BDLocation.TypeServerError) {
//                if (diagnosticType == 8) {
//                    sb.append("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限");
//                    sb.append("\n" + diagnosticMessage);
//                }
//            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        locationService.unregisterListener(mListener);
        locationService.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshHomeEvent refreshMineEvent) {
        if (refreshMineEvent.address != null) {
            teAddress.setText(refreshMineEvent.address);
            SharedPref.getInstance(getActivity()).put(ADDRES, refreshMineEvent.address);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
