package com.project.zhongrenweigong.business;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.App;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.baidumap.LocationService;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.BusinessWorkerListAdapter;
import com.project.zhongrenweigong.business.adapter.VegetableListAdapter;
import com.project.zhongrenweigong.business.bean.BusinessHomeDataBean;
import com.project.zhongrenweigong.business.bean.EmployeesBean;
import com.project.zhongrenweigong.business.bean.GoodsListsBean;
import com.project.zhongrenweigong.business.bean.ShopHomePageBean;
import com.project.zhongrenweigong.business.teach.TeachListActivity;
import com.project.zhongrenweigong.currency.NavigationActivity;
import com.project.zhongrenweigong.mine.BusinessMineHomePageActivity;
import com.project.zhongrenweigong.mine.MineHomePageActivity;
import com.project.zhongrenweigong.util.KeyboardUtils;
import com.project.zhongrenweigong.util.SpacingItemDecoration;
import com.project.zhongrenweigong.util.SystemUtil;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;
import me.iwf.photopicker.PhotoPreview;

/**
 * 作者：Fuduo on 2019/10/21 14:34
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class BusinessHomePageActivity extends BaseActivity<BusinessHomePagePresent> {
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.img_trademark)
    ImageView imgTrademark;
    @BindView(R.id.te_right_title)
    TextView teRightTitle;
    @BindView(R.id.te_mine_address)
    TextView teMineAddress;
    @BindView(R.id.img_legal_head)
    ImageView imgLegalHead;
    @BindView(R.id.te_legal_name)
    TextView teLegalName;
    @BindView(R.id.te_legal_id)
    TextView teLegalId;
    @BindView(R.id.te_legal_renzheng)
    TextView teLegalRenzheng;
    @BindView(R.id.img_legal_isshow)
    ImageView imgLegalIsshow;
    @BindView(R.id.rl_legal)
    RelativeLayout rlLegal;
    @BindView(R.id.recy_shareholder)
    RecyclerView recyShareholder;
    @BindView(R.id.line_shareholder)
    LinearLayout lineShareholder;
    @BindView(R.id.recy_worker)
    RecyclerView recyWorker;
    @BindView(R.id.line_worker)
    LinearLayout lineWorker;
    @BindView(R.id.te_shop_address)
    TextView teShopAddress;
    @BindView(R.id.te_shop_phone)
    TextView teShopPhone;
    @BindView(R.id.te_shop_fans)
    TextView teShopFans;
    @BindView(R.id.te_shop_time)
    TextView teShopTime;
    @BindView(R.id.recy_vegetable)
    RecyclerView recyVegetable;
    @BindView(R.id.te_shop_test)
    TextView teShopTest;
    @BindView(R.id.te_shop_intro)
    TextView teShopIntro;
    @BindView(R.id.te_shop_tuijian)
    TextView teShopTuijian;
    @BindView(R.id.te_upload_voucher)
    TextView teUploadVoucher;
    @BindView(R.id.view_three)
    View viewThree;
    @BindView(R.id.view_four)
    View viewFour;
    private LocationService locationService;
    private String shopId;
    private VegetableListAdapter vegetableListAdapter;
    private BusinessWorkerListAdapter workerListAdapter;
    private BusinessWorkerListAdapter holderListAdapter;
    private String address;
    private String shopPhone;
    private ArrayList<String> imgList = new ArrayList<>();
    private int shopType;
    private String legalId;

    @Override
    public void initView() {
        teRightTitle.setText("关注");
        Intent intent = getIntent();
        shopType = intent.getIntExtra("shopType", 0);
        shopId = "1111";//getIntent().getStringExtra("shopId")
        if (shopType == 4) {//汽车行业
            teShopTuijian.setText("推荐车型");
            viewFour.setVisibility(View.VISIBLE);
            viewThree.setVisibility(View.GONE);
            teUploadVoucher.setVisibility(View.VISIBLE);
        }

        recyShareholder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyShareholder.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 40));
        holderListAdapter = new BusinessWorkerListAdapter(R.layout.item_people_list);
        recyShareholder.setAdapter(holderListAdapter);

        recyWorker.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyWorker.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 40));
        workerListAdapter = new BusinessWorkerListAdapter(R.layout.item_people_list);
        recyWorker.setAdapter(workerListAdapter);

        recyVegetable.setLayoutManager(new GridLayoutManager(this, 3));
        recyVegetable.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 20));
        vegetableListAdapter = new VegetableListAdapter(R.layout.item_vegetable_list);
        recyVegetable.setAdapter(vegetableListAdapter);
        recyVegetable.setHasFixedSize(true);

        vegetableListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<GoodsListsBean> data = vegetableListAdapter.getData();
                for (GoodsListsBean goodsListsBean : data) {
                    imgList.add(goodsListsBean.goodsTitleUrl);
                }
                PhotoPreview.builder()
                        .setPhotos(imgList)
                        .setCurrentItem(position)
                        .setShowDeleteButton(false)
                        .start(BusinessHomePageActivity.this);
            }
        });

        holderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EmployeesBean item = holderListAdapter.getItem(position);
                String employeesId = item.employeesId;

                Router.newIntent(BusinessHomePageActivity.this)
                        .putString("mbId", employeesId)
                        .putString("shopId", shopId)
                        .to(BusinessMineHomePageActivity.class)
                        .launch();
            }
        });
        workerListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EmployeesBean item = holderListAdapter.getItem(position);
                String employeesId = item.employeesId;

                Router.newIntent(BusinessHomePageActivity.this)
                        .putString("mbId", employeesId)
                        .to(MineHomePageActivity.class)
                        .launch();
            }
        });
    }

    @Override
    public void initAfter() {
        getP().getShopHomepage(shopId);
    }

    public void setHeadData(BusinessHomeDataBean businessHomeDataBean) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_business_home_page;
    }

    @Override
    public BusinessHomePagePresent bindPresent() {
        return new BusinessHomePagePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgLegalHead.setOnClickListener(this);
        rlLegal.setOnClickListener(this);
        teRightTitle.setOnClickListener(this);
        teShopTest.setOnClickListener(this);
        teShopAddress.setOnClickListener(this);
        teShopPhone.setOnClickListener(this);
        teUploadVoucher.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.img_legal_head:
                Router.newIntent(BusinessHomePageActivity.this)
                        .putString("mbId", legalId)
                        .putString("shopId", shopId)
                        .to(BusinessMineHomePageActivity.class)
                        .launch();
                break;
            case R.id.rl_legal:
                if (lineShareholder.getVisibility() == View.GONE) {
                    lineShareholder.setVisibility(View.VISIBLE);
                    lineWorker.setVisibility(View.VISIBLE);
                    imgLegalIsshow.setBackgroundResource(R.mipmap.legal_show);
                } else {
                    lineShareholder.setVisibility(View.GONE);
                    lineWorker.setVisibility(View.GONE);
                    imgLegalIsshow.setBackgroundResource(R.mipmap.legal_hide);
                }
                break;
            case R.id.te_shop_address:
                Router.newIntent(BusinessHomePageActivity.this)
                        .putString("address", address)
                        .to(NavigationActivity.class).launch();
                break;
            case R.id.te_right_title:

                break;
            case R.id.te_shop_phone:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                showCallPhoneDialog();
                break;
            case R.id.te_shop_test:
                Router.newIntent(BusinessHomePageActivity.this)
                        .putString("address", address)
                        .putString("shopId", shopId)
                        .to(WeiGongTestActivity.class).launch();
                break;
            case R.id.te_upload_voucher:
                Router.newIntent(BusinessHomePageActivity.this)
                        .putString("shopId", shopId)
                        .to(UploadVoucherActivity.class).launch();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
//                String province = location.getProvince();
//                double longitude = location.getLongitude();//经度
//                double latitude = location.getLatitude();//纬度
                String district = location.getDistrict();//地区
                String street = location.getStreet();//街道
                teMineAddress.setText(district + "." + street);
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

        @Override
        public void onLocDiagnosticMessage(int i, int i1, String s) {
            super.onLocDiagnosticMessage(i, i1, s);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationService.unregisterListener(mListener);
        locationService.stop();
    }

    public void setData(ShopHomePageBean shopHomePageBean) {
        ShopHomePageBean.ShopHomePageDataBean data = shopHomePageBean.getData();
        if (data == null) {
            return;
        }
        GlideDownLoadImage.getInstance().loadImage(mContext, data.shopLogo,
                imgTrademark, R.mipmap.fang_list_default);
        GlideDownLoadImage.getInstance().loadCircleImage(mContext, data.headUrl,
                imgLegalHead);
        teTitle.setText(data.shopName);
        legalId = data.mcId;
        teLegalId.setText("ID:" + legalId);
        String isMerchantAuth = data.isMerchantAuth;
        if (isMerchantAuth.equals("1")) {
            teLegalRenzheng.setText("商家认证:已认证");
        } else {
            teLegalRenzheng.setText("商家认证:未认证");
        }
        SpannableStringBuilder span = new SpannableStringBuilder("缩进" + data.details);
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        address = data.detailedAddr;
        teShopIntro.setText(span);
        teShopAddress.setText(address);
        shopPhone = data.mcPhone;
        teShopPhone.setText(shopPhone);
        teShopFans.setText(data.fansNum);
        teShopTime.setText("营业时间:" + data.beignTime + "-" + data.endTime);

        List<GoodsListsBean> goodsLists = data.goodsLists;
        if (goodsLists != null && goodsLists.size() > 0) {
            vegetableListAdapter.setNewData(goodsLists);
        }

        List<EmployeesBean> shareholder = data.shareholder;
        if (shareholder != null && shareholder.size() > 0) {
            holderListAdapter.setNewData(shareholder);
        }

        List<EmployeesBean> employees = data.employees;
        if (employees != null && employees.size() > 0) {
            workerListAdapter.setNewData(employees);
        }

    }

    private void showCallPhoneDialog() {
        final Dialog callPhoneDialog = new Dialog(this, R.style.dialog_bottom_full);
        callPhoneDialog.setCanceledOnTouchOutside(true);
        callPhoneDialog.setCancelable(true);
        Window window = callPhoneDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(this, R.layout.dialog_layout_call_phone, null);
        TextView teLineOne = (TextView) view.findViewById(R.id.te_line_one);
        TextView teOk = (TextView) view.findViewById(R.id.te_ok);
        TextView teCancel = (TextView) view.findViewById(R.id.te_cancel);
        teLineOne.setText(shopPhone);

        teOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + shopPhone));
                startActivity(intent);
                callPhoneDialog.dismiss();
            }
        });

        teCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                callPhoneDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        callPhoneDialog.show();
    }

}
