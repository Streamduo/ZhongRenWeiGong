package com.project.zhongrenweigong.business;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.XCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.project.zhongrenweigong.currency.Constans.USERACCENT;

public class BusinessAuthenticationActivity extends BaseActivity<BusinessAuthenticationPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_company_name)
    EditText edCompanyName;
    @BindView(R.id.ed_license_key)
    EditText edLicenseKey;
    @BindView(R.id.ed_legal_person)
    EditText edLegalPerson;
    @BindView(R.id.te_area)
    TextView teArea;
    @BindView(R.id.rl_select_area)
    RelativeLayout rlSelectArea;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.img_upload_license)
    ImageView imgUploadLicense;
    @BindView(R.id.img_business_license)
    ImageView imgBusinessLicense;
    @BindView(R.id.img_upload_qualifications)
    ImageView imgUploadQualifications;
    @BindView(R.id.img_qualifications)
    ImageView imgQualifications;
    @BindView(R.id.te_ok)
    TextView teOk;
    private OptionsPickerView datePickerView;
    // 省数据集合
    private ArrayList<String> mListProvince = new ArrayList<>();
    // 市数据集合
    private ArrayList<ArrayList<String>> mListCity = new ArrayList<>();
    // 区数据集合
    private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<>();
    private JSONObject mJsonObj;
    private String address_str;

    @Override
    public void initView() {
        teTitle.setText("商家认证");
    }

    @Override
    public void initAfter() {
        initJsonData();
        initJsonDatas();
        initAreaPicker();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_business_authentication;
    }

    @Override
    public BusinessAuthenticationPresent bindPresent() {
        return new BusinessAuthenticationPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        rlSelectArea.setOnClickListener(this);
        imgUploadLicense.setOnClickListener(this);
        imgUploadQualifications.setOnClickListener(this);
        teOk.setOnClickListener(this);
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
            case R.id.img_upload_license:

                break;
            case R.id.img_upload_qualifications:

                break;
            case R.id.te_ok:
                String companyName = edCompanyName.getText().toString();
                String licenseKey = edLicenseKey.getText().toString();
                String legalPerson = edLegalPerson.getText().toString();
                String address = edAddress.getText().toString();
                XCache cache = new XCache.Builder(BusinessAuthenticationActivity.this).build();
                LoginMsg loginMsg = (LoginMsg) cache.getObject(USERACCENT);
                String mbId = loginMsg.mbId;
                if (TextUtils.isEmpty(companyName)) {
                    showToastShort("请输入单位名称");
                    return;
                }
                if (TextUtils.isEmpty(licenseKey)) {
                    showToastShort("请输入许可证号");
                    return;
                }
                if (licenseKey.length() != 18) {
                    showToastShort("许可证号格式错误");
                    return;
                }
                if (TextUtils.isEmpty(legalPerson)) {
                    showToastShort("请输入法人代表");
                    return;
                }
                if (TextUtils.isEmpty(address_str)) {
                    showToastShort("请输入省市区");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    showToastShort("请输入详细地址");
                    return;
                }
                getP().authMerchantEncryptionApi(companyName, licenseKey, legalPerson, address_str, address, mbId);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private void initAreaPicker() {
        datePickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
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

}
