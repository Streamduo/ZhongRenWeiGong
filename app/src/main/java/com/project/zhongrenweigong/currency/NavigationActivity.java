package com.project.zhongrenweigong.currency;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.baidumap.BaiDuMapUtils;
import com.project.zhongrenweigong.baidumap.LocationService;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.currency.bean.NavigationBean;
import com.project.zhongrenweigong.util.GsonProvider;
import com.project.zhongrenweigong.util.UtilsStyle;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends BaseActivity<NavigationPresent> {

    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.te_shop_addr)
    TextView teShopAddr;
    @BindView(R.id.te_go_shop)
    TextView teGoShop;
    private BaiduMap mBaiduMap;
    private LocationService locService;
    private LinkedList<LocationEntity> locationList = new LinkedList<LocationEntity>(); // 存放历史定位结果的链表，最大存放当前结果的前5次定位结果
    private double longitude;
    private double latitude;
    private String json = "{status:0,result:{location:{lng:116.3084202915042,lat:40.05703033345938}," +
            "precise:1,confidence:80,comprehension:100,level:门址}}";
    private NavigationBean navigationBean;

    @Override
    public void initView() {
        String address = getIntent().getStringExtra("address");
        teShopAddr.setText(address);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
        locService = App.getInstance().locationService;
        LocationClientOption mOption = locService.getDefaultLocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        mOption.setCoorType("bd09ll");
        locService.setLocationOption(mOption);
        locService.registerListener(listener);
        locService.start();
    }

    @Override
    public void initAfter() {
        navigationBean = GsonProvider.gson.fromJson(json, NavigationBean.class);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_navigation;
    }

    @Override
    public NavigationPresent bindPresent() {
        return new NavigationPresent();
    }

    @Override
    public void setListener() {
        imgBack.setOnClickListener(this);
        imgLocation.setOnClickListener(this);
        teGoShop.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_location:
                locService.start();
                break;
            case R.id.te_go_shop:
                if (longitude == 0 || latitude == 0) {
                    return;
                }
                setAddress();
                break;
        }
    }

    private void setAddress() {
        NavigationBean.ResultBean.LocationBean location1 = navigationBean.getResult().getLocation();
        //定义起终点坐标（天安门和百度大厦）
        LatLng startPoint = new LatLng(longitude, latitude);
        LatLng endPoint = new LatLng(location1.getLat(), location1.getLng());

        //构建RouteParaOption参数以及策略
        //也可以通过startName和endName来构造
        RouteParaOption paraOption = new RouteParaOption()
                .startPoint(startPoint)
                .endPoint(endPoint)
                .busStrategyType(RouteParaOption.EBusStrategyType.bus_time_first);
        //调起百度地图
        try {
            BaiduMapRoutePlan.openBaiduMapTransitRoute(paraOption, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //调起结束时及时调用finish方法以释放相关资源
        BaiduMapRoutePlan.finish(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /***
     * 定位结果回调，在此方法中处理定位结果
     */
    BDAbstractLocationListener listener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location != null && (location.getLocType() == 161 || location.getLocType() == 66)) {
                Message locMsg = locHander.obtainMessage();
                Bundle locData;
                locData = Algorithm(location);
                if (locData != null) {
                    locData.putParcelable("loc", location);
                    locMsg.setData(locData);
                    locHander.sendMessage(locMsg);
                }
            }
        }

    };

    /***
     * 平滑策略代码实现方法，主要通过对新定位和历史定位结果进行速度评分，
     * 来判断新定位结果的抖动幅度，如果超过经验值，则判定为过大抖动，进行平滑处理,若速度过快，
     * 则推测有可能是由于运动速度本身造成的，则不进行低速平滑处理 ╭(●｀∀´●)╯
     *
     * @param location
     * @return Bundle
     */
    private Bundle Algorithm(BDLocation location) {
        Bundle locData = new Bundle();
        double curSpeed = 0;
        if (locationList.isEmpty() || locationList.size() < 2) {
            LocationEntity temp = new LocationEntity();
            temp.location = location;
            temp.time = System.currentTimeMillis();
            locData.putInt("iscalculate", 0);
            locationList.add(temp);
        } else {
            if (locationList.size() > 5)
                locationList.removeFirst();
            double score = 0;
            for (int i = 0; i < locationList.size(); ++i) {
                LatLng lastPoint = new LatLng(locationList.get(i).location.getLatitude(),
                        locationList.get(i).location.getLongitude());
                LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
                double distance = DistanceUtil.getDistance(lastPoint, curPoint);
                curSpeed = distance / (System.currentTimeMillis() - locationList.get(i).time) / 1000;
                score += curSpeed * BaiDuMapUtils.EARTH_WEIGHT[i];
            }
            if (score > 0.00000999 && score < 0.00005) { // 经验值,开发者可根据业务自行调整，也可以不使用这种算法
                location.setLongitude(
                        (locationList.get(locationList.size() - 1).location.getLongitude() + location.getLongitude())
                                / 2);
                location.setLatitude(
                        (locationList.get(locationList.size() - 1).location.getLatitude() + location.getLatitude())
                                / 2);
                locData.putInt("iscalculate", 1);
            } else {
                locData.putInt("iscalculate", 0);
            }
            LocationEntity newLocation = new LocationEntity();
            newLocation.location = location;
            newLocation.time = System.currentTimeMillis();
            locationList.add(newLocation);
        }
        return locData;
    }

    /***
     * 接收定位结果消息，并显示在地图上
     */
    private Handler locHander = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                BDLocation location = msg.getData().getParcelable("loc");
                //经度
                longitude = location.getLongitude();
                //纬度
                latitude = location.getLatitude();

                int iscal = msg.getData().getInt("iscalculate");
                if (location != null) {
                    NavigationBean.ResultBean.LocationBean location1 = navigationBean.getResult().getLocation();
                    LatLng point = new LatLng(location1.getLat(), location1.getLng());
                    // 构建Marker图标
                    BitmapDescriptor bitmap = null;
                    if (iscal == 0) {
                        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_openmap_mark); // 非推算结果
                    } else {
                        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_openmap_focuse_mark); // 推算结果
                    }

                    // 构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
                    // 在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
                }
            } catch (Exception e) {
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locService.unregisterListener(listener);
        locService.stop();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    /**
     * 封装定位结果和时间的实体类
     *
     * @author baidu
     */
    class LocationEntity {
        BDLocation location;
        long time;
    }

}
