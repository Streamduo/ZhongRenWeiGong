package com.project.zhongrenweigong.business.teach;

import android.content.res.AssetManager;
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
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

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

    private OptionsPickerView datePickerView;
    // 省数据集合
    private ArrayList<String> mListProvince = new ArrayList<>();
    // 市数据集合
    private ArrayList<ArrayList<String>> mListCity = new ArrayList<>();
    private JSONObject mJsonObj;
    private String address_str;

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
        initJsonData();
        initJsonDatas();
        initAreaPicker();
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
        teMineAddress.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_mine_address:
                datePickerView.show();
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

    private void initAreaPicker() {
        datePickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3, View v) {
                if (mListCity.size() > option1 && mListCity.get(option1).size() > option2) {
//                    if (mListArea.size() > option1 && mListArea.get(option1).size() > option2
//                            && mListArea.get(option1).get(option2).size() > option3) {
                        String prov = mListProvince.get(option1);
                        String city = mListCity.get(option1).get(option2);
//                        String area = mListArea.get(option1).get(option2).get(option3);
                        address_str = prov + " " + city;// + " " + area
                        teMineAddress.setText(address_str);
//                    }
                }
            }
        }).setLayoutRes(R.layout.dialog_set_plan_start_time, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);

                TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerView.returnData();
                        datePickerView.dismiss();
                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerView.dismiss();
                    }
                });
            }
        }).setContentTextSize(18)
                .build();
        datePickerView.setPicker(mListProvince, mListCity);//添加数据 , mListArea
    }


    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        AssetManager assets = this.getAssets();
        try {
            InputStream is = assets.open("area.json");
            byte[] buf = new byte[is.available()];
            is.read(buf);
            String json = new String(buf, "UTF-8");
            mJsonObj = new JSONObject(json);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Json数据，并释放Json对象
     */
    private void initJsonDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 获取每个省的Json对象
                String province = jsonP.getString("name");

                ArrayList<String> options2Items_01 = new ArrayList<>();
//                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<>();
                JSONArray jsonCs = jsonP.getJSONArray("city");
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonC = jsonCs.getJSONObject(j);// 获取每个市的Json对象
                    String city = jsonC.getString("name");
                    options2Items_01.add(city);// 添加市数据

//                    ArrayList<String> options3Items_01_01 = new ArrayList<>();
//                    JSONArray jsonAs = jsonC.getJSONArray("area");
//                    for (int k = 0; k < jsonAs.length(); k++) {
//                        options3Items_01_01.add(jsonAs.getString(k));// 添加区数据
//                    }
//                    options3Items_01.add(options3Items_01_01);
                }
                mListProvince.add(province);// 添加省数据
                mListCity.add(options2Items_01);
//                mListArea.add(options3Items_01);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.unregisterListener(mListener);
        locationService.stop();
    }
}
