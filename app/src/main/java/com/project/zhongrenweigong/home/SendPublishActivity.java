package com.project.zhongrenweigong.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.baidumap.LocationService;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.login.LoginNetManager;
import com.project.zhongrenweigong.login.RegisterActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.net.HomeMainApi;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.view.LoadingDialog;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendPublishActivity extends BaseActivity<SendPublishPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.img_send)
    TextView imgSend;
    @BindView(R.id.ed_report_intro)
    EditText edReportIntro;
    @BindView(R.id.img_journalism_cover)
    ImageView imgJournalismCover;
    @BindView(R.id.rl_play)
    RelativeLayout rlPlay;
    @BindView(R.id.te_mine_address)
    TextView teMineAddress;

    private LocationService locationService;
    private double longitude = 1;
    private double latitude = 1;
    private String videoPath;
    private LoginMsg userAccent;
    private String intro;
    private String address = "北京市朝阳区";

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
        videoPath = getIntent().getStringExtra("videoPath");
        Glide.with(context)
                .load(Uri.fromFile(new File(videoPath)))
                .into(imgJournalismCover);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_send_publish;
    }

    @Override
    public SendPublishPresent bindPresent() {
        return new SendPublishPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgSend.setOnClickListener(this);
        rlPlay.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.img_send:
                intro = edReportIntro.getText().toString();
                if (TextUtils.isEmpty(intro)){
                    showToastShort("请输入视频描述");
                    return;
                }
                uploadLittleVideo();
                break;
            case R.id.rl_play:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                File file = new File(videoPath);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "video/*");
                startActivity(intent);
                break;
        }
    }

    private void uploadLittleVideo() {
        LoadingDialog.show(SendPublishActivity.this);
        File file = new File(videoPath);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HomeMainApi.MESSAGE_BASE_PATH)
                .build();
        MessageNetManager service = retrofit.create(MessageNetManager.class);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mbId", userAccent.mbId)
                .addFormDataPart("describe", intro)
                .addFormDataPart("address", address)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("file"), file))
                .build();

        //如果和rxjava1.x , call就换成 Observable
        Call<BaseModel> call = service.uploadLittleVideo("uploadVideo", requestBody);
        // 执行
        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                LoadingDialog.dismiss(SendPublishActivity.this);
                if (response.isSuccessful()) {
                    showToastShort("上传成功");
                    finish();
                } else {
                    showToastShort("请检查网络设置");
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                showToastShort("请检查网络设置");
                LoadingDialog.dismiss(SendPublishActivity.this);
            }
        });
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
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                address = location.getAddrStr();
                teMineAddress.setText(address);
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
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.unregisterListener(mListener);
        locationService.stop();
    }

}
