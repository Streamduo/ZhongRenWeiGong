package com.project.zhongrenweigong.business.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.CategorySpinnerAdapter;
import com.project.zhongrenweigong.business.bean.BusinessCategoryListBean;
import com.project.zhongrenweigong.business.bean.CategoryDataBean;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.CheckInputUtil;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.util.XCache;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.project.zhongrenweigong.currency.Constans.USERACCENT;

public class EditStoreIntroActivity extends BaseActivity<EditStoreIntroPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.rl_upload_logo)
    RelativeLayout rlUploadLogo;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.te_fenlei)
    TextView teFenlei;
    @BindView(R.id.ed_store_name)
    EditText edStoreName;
    @BindView(R.id.te_start_time)
    TextView teStartTime;
    @BindView(R.id.te_nd_time)
    TextView teNdTime;
    @BindView(R.id.ed_store_phone)
    EditText edStorePhone;
    @BindView(R.id.te_area)
    TextView teArea;
    @BindView(R.id.ed_store_address)
    EditText edStoreAddress;
    @BindView(R.id.ed_store_intro)
    EditText edStoreIntro;
    @BindView(R.id.rl_fenlei)
    RelativeLayout rlFenlei;
    @BindView(R.id.rl_select_area)
    RelativeLayout rlSelectArea;
    @BindView(R.id.te_fenlei_detail)
    TextView teFenleiDetail;
    @BindView(R.id.rl_fenlei_detail)
    RelativeLayout rlFenleiDetail;

    private OptionsPickerView datePickerView;
    // 省数据集合
    private ArrayList<String> mListProvince = new ArrayList<>();
    // 市数据集合
    private ArrayList<ArrayList<String>> mListCity = new ArrayList<>();
    // 区数据集合
    private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<>();
    private JSONObject mJsonObj;
    private String address_str;
    private String startTime;
    private String endTime;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    private CategorySpinnerAdapter categoryDetailSpinnerAdapter;
    private EasyPopup mCategoryPop;
    private EasyPopup mCategoryDetailPop;
    private List<CategoryDataBean> categoryData;
    private String categoryId;
    private String categoryDetailId;
    private List<CategoryDataBean> categoryDetaildata;

    @Override
    public void initView() {
    }

    @Override
    public void initAfter() {
        initJsonData();
        initJsonDatas();
        initAreaPicker();
        getP().getGoodsCategory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_edit_store_intro;
    }

    @Override
    public EditStoreIntroPresent bindPresent() {
        return new EditStoreIntroPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgSend.setOnClickListener(this);
        rlUploadLogo.setOnClickListener(this);
        teFenlei.setOnClickListener(this);
        teFenleiDetail.setOnClickListener(this);
        rlSelectArea.setOnClickListener(this);
        teStartTime.setOnClickListener(this);
        teNdTime.setOnClickListener(this);
    }

    private void showCategoryDialog(Context context, int layoutid, View v) {

        //是否允许点击PopupWindow之外的地方消失
        //允许背景变暗
        //变暗的透明度(0-1)，0为完全透明
        //变暗的背景颜色
        //指定任意 ViewGroup 背景变暗
        mCategoryPop = EasyPopup.create()
                .setContentView(context, layoutid)
                .setAnimationStyle(R.style.ActionSheetDialogStyle)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .apply();

        ListView listCategory = (ListView) mCategoryPop.findViewById(R.id.list_category);
        if (categoryData != null && categoryData.size() > 0) {
            categorySpinnerAdapter = new CategorySpinnerAdapter(EditStoreIntroActivity.this,
                    categoryData);
            listCategory.setAdapter(categorySpinnerAdapter);
        }

        listCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCategoryPop.dismiss();
                CategoryDataBean item = (CategoryDataBean) categorySpinnerAdapter.getItem(i);
                categoryId = item.categoryId;
                teFenlei.setText(item.categoryName);
                getP().getGoodsCategory(item.categoryId);
            }
        });

        /**
         * 相对anchor view显示，适用 宽高不为match_parent
         *
         * @param anchor
         * @param yGravity  垂直方向的对齐方式
         * @param xGravity  水平方向的对齐方式
         * @param x            水平方向的偏移
         * @param y            垂直方向的偏移
         */
        mCategoryPop.showAtAnchorView(v, YGravity.BELOW, XGravity.CENTER, 0, 10);
    }

    private void showCategoryDetailDialog(Context context, int layoutid, View v) {

        //是否允许点击PopupWindow之外的地方消失
        //允许背景变暗
        //变暗的透明度(0-1)，0为完全透明
        //变暗的背景颜色
        //指定任意 ViewGroup 背景变暗
        mCategoryDetailPop = EasyPopup.create()
                .setContentView(context, layoutid)
                .setAnimationStyle(R.style.ActionSheetDialogStyle)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .apply();

        ListView listCategory = (ListView) mCategoryDetailPop.findViewById(R.id.list_category);
        if (categoryDetaildata != null && categoryDetaildata.size() > 0) {
            categoryDetailSpinnerAdapter = new CategorySpinnerAdapter(EditStoreIntroActivity.this,
                    categoryDetaildata);
            listCategory.setAdapter(categoryDetailSpinnerAdapter);
        }

        listCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryDataBean item = (CategoryDataBean) categoryDetailSpinnerAdapter.getItem(i);
                categoryDetailId = item.categoryId;
                teFenleiDetail.setText(item.categoryName);
                mCategoryDetailPop.dismiss();
            }
        });

        /**
         * 相对anchor view显示，适用 宽高不为match_parent
         *
         * @param anchor
         * @param yGravity  垂直方向的对齐方式
         * @param xGravity  水平方向的对齐方式
         * @param x            水平方向的偏移
         * @param y            垂直方向的偏移
         */
        mCategoryDetailPop.showAtAnchorView(v, YGravity.BELOW, XGravity.CENTER, 0, 10);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.rl_select_area:
                datePickerView.show();
                break;
            case R.id.rl_upload_logo:

                break;
            case R.id.te_fenlei:
                showCategoryDialog(EditStoreIntroActivity.this, R.layout.layout_category_list, v);
                break;
            case R.id.te_fenlei_detail:
                if (categoryId != null && !categoryId.equals("")) {
                    showCategoryDetailDialog(EditStoreIntroActivity.this, R.layout.layout_category_list, v);
                } else {
                    showToastShort("请先选择店铺分类");
                }
                break;
            case R.id.te_start_time:
                initTimePicker(0);
                break;
            case R.id.te_nd_time:
                initTimePicker(1);
                break;
            case R.id.img_send:
                String storeName = edStoreName.getText().toString();
                String storePhone = edStorePhone.getText().toString();
                String pn = storePhone.replaceAll("\\D", "");
                String storeAddress = edStoreAddress.getText().toString();
                String storeIntro = edStoreIntro.getText().toString();
                if (TextUtils.isEmpty(storeName)) {
                    showToastShort("请输入店铺名称");
                    return;
                }

                if (TextUtils.isEmpty(pn)) {
                    showToastShort(getString(R.string.phonenumber_null));
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(pn)) {
                    showToastShort(getString(R.string.phonenumber_error));
                    return;
                }

                if (TextUtils.isEmpty(storeAddress)) {
                    showToastShort("请输入店铺详细地址");
                    return;
                }

                if (TextUtils.isEmpty(address_str)) {
                    showToastShort("请输入省市区");
                    return;
                }
                if (TextUtils.isEmpty(categoryId)) {
                    showToastShort("请选择店铺分类");
                    return;
                }
                if (TextUtils.isEmpty(categoryDetailId)) {
                    showToastShort("请选择店铺详细分类");
                    return;
                }
                if (TextUtils.isEmpty(startTime)) {
                    showToastShort("请输入开始时间");
                    return;
                }
                if (TextUtils.isEmpty(endTime)) {
                    showToastShort("请输入结束时间");
                    return;
                }
                LoginMsg userAccent = AcacheUtils.getInstance(EditStoreIntroActivity.this).getUserAccent();
                getP().openShopEncryptionApi(startTime, endTime, storeIntro, storeAddress, pn,
                        userAccent.mbId, address_str, storeName, categoryId, categoryDetailId);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private void initTimePicker(final int type) {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.i("pvTime", "onTimeSelect");
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                if (type == 0) {
                    startTime = format.format(date);
                    teStartTime.setText(startTime);
                } else {
                    endTime = format.format(date);
                    teNdTime.setText(endTime);
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(false) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();
        pvTime.show();
    }

    private void initAreaPicker() {
        datePickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3, View v) {
                if (mListCity.size() > option1 && mListCity.get(option1).size() > option2) {
                    if (mListArea.size() > option1 && mListArea.get(option1).size() > option2
                            && mListArea.get(option1).get(option2).size() > option3) {
                        String prov = mListProvince.get(option1);
                        String city = mListCity.get(option1).get(option2);
                        String area = mListArea.get(option1).get(option2).get(option3);
                        address_str = prov + " " + city + " " + area;
                        teArea.setText(address_str);
                    }
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
        datePickerView.setPicker(mListProvince, mListCity, mListArea);//添加数据
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
                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<>();
                JSONArray jsonCs = jsonP.getJSONArray("city");
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonC = jsonCs.getJSONObject(j);// 获取每个市的Json对象
                    String city = jsonC.getString("name");
                    options2Items_01.add(city);// 添加市数据

                    ArrayList<String> options3Items_01_01 = new ArrayList<>();
                    JSONArray jsonAs = jsonC.getJSONArray("area");
                    for (int k = 0; k < jsonAs.length(); k++) {
                        options3Items_01_01.add(jsonAs.getString(k));// 添加区数据
                    }
                    options3Items_01.add(options3Items_01_01);
                }
                mListProvince.add(province);// 添加省数据
                mListCity.add(options2Items_01);
                mListArea.add(options3Items_01);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    public void setData(BusinessCategoryListBean businessCategoryListBean) {
        categoryData = businessCategoryListBean.getData();
    }

    public void setCategoryDetailData(BusinessCategoryListBean businessCategoryListBean) {
        categoryDetaildata = businessCategoryListBean.getData();
    }
}
