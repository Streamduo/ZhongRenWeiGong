package com.project.zhongrenweigong.mine;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.baidumap.LocationService;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.currency.bean.NavigationBean;
import com.project.zhongrenweigong.currency.event.RefreshIndustrySearchEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.bean.ProfessionalBean;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.GsonProvider;
import com.project.zhongrenweigong.util.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfessionalCertificationActivity extends BaseActivity<ProfessionalCertificationPresent> {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_right_title)
    TextView teRightTitle;
    @BindView(R.id.ed_work)
    EditText edWork;
    @BindView(R.id.te_send)
    TextView teSend;
    @BindView(R.id.ed_company)
    EditText edCompany;
    @BindView(R.id.ed_company_address)
    EditText edCompanyAddress;
    private LoginMsg userAccent;
    private LocationService locationService;
    private BDLocation bdLocation;
    private double longitude = 1;
    private double latitude = 1;
    private String work;
    private String company;
    private String companyAddress;
    private String professionName;
    private String professionMain;
    private String professionMainAddress;
    private int flag;

    @Override
    public void initView() {
        teTitle.setText("职业认证");
        teRightTitle.setText("编辑");
        teRightTitle.setTextSize(16);
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
    }

    @Override
    public void initAfter() {
        getP().getProfessionAuth(userAccent.mbId);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_professional_certification;
    }

    @Override
    public ProfessionalCertificationPresent bindPresent() {
        return new ProfessionalCertificationPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teRightTitle.setOnClickListener(this);
        teSend.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_right_title:
                edWork.setEnabled(true);
                edWork.setCursorVisible(true);
                edWork.setSelection(professionName.length());

                edCompany.setEnabled(true);
                edCompany.setCursorVisible(true);
                edCompany.setSelection(professionMain.length());
                edCompanyAddress.setEnabled(true);

                edCompanyAddress.setCursorVisible(true);
                edCompanyAddress.setSelection(professionMainAddress.length());

                teSend.setVisibility(View.VISIBLE);
                break;
            case R.id.te_send:
                work = edWork.getText().toString();
                company = edCompany.getText().toString();
                companyAddress = edCompanyAddress.getText().toString();
                if (TextUtils.isEmpty(work)) {
                    showToastShort("职业不能为空");
                    return;
                }
                if (TextUtils.isEmpty(company)) {
                    showToastShort("公司不能为空");
                    return;
                }
                if (TextUtils.isEmpty(companyAddress)) {
                    showToastShort("公司地址不能为空");
                    return;
                }
                changeNavigation(companyAddress);
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
                longitude = bdLocation.getLongitude();
                latitude = bdLocation.getLatitude();

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

    private void changeNavigation(String city) {
        //创建OkHttpClient对象
        OkHttpClient okhttpClient = new OkHttpClient();
        //创建Request对象
        Request request = new Request.Builder()
                .url("http://api.map.baidu.com/geocoding/v3/?address=" + city +
                        "&output=json&ak=" + Constans.MAP_AK +
                        "&mcode=" + Constans.MAP_SAFE_NUM)//请求的地址,根据需求带参
                .build();
        //创建call对象
        Call call = okhttpClient.newCall(request);
        call.enqueue(new Callback() {
            /**
             * 请求失败后执行
             * @param call
             * @param e
             */
            @Override
            public void onFailure(Call call, IOException e) {
            }

            /**
             * 请求成功后执行
             * @param call
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int status = jsonObject.getInt("status");
                        if (status == 0) {
                            NavigationBean navigationBean = GsonProvider.gson.fromJson(json, NavigationBean.class);
                            NavigationBean.ResultBean result = navigationBean.getResult();
                            NavigationBean.ResultBean.LocationBean location = result.getLocation();
                            latitude = location.getLat();
                            longitude = location.getLng();
                            getP().authMemberProfession(userAccent.mbId, work, companyAddress, company,
                                    String.valueOf(flag),String.valueOf(latitude), String.valueOf(longitude));
                        } else {
                            showToastShort("地址输入错误");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationService.unregisterListener(mListener);
        locationService.stop();
    }

    public void setUserCertifiation(ProfessionalBean professionalBean) {
        ProfessionalBean.ProfessionalDataBean data = professionalBean.getData();
        professionName = data.getProfessionName();
        professionMain = data.getProfessionMain();
        professionMainAddress = data.getProfessionMainAddress();

        edWork.setText(professionName);
        edWork.setEnabled(false);
        edWork.setCursorVisible(false);
        edCompany.setText(professionMain);
        edCompany.setEnabled(false);
        edCompany.setCursorVisible(false);
        edCompanyAddress.setText(professionMainAddress);
        edCompanyAddress.setEnabled(false);
        edCompanyAddress.setCursorVisible(false);
        teSend.setVisibility(View.GONE);
    }

    public void setFlag(int i) {
        flag = i;
    }
}
