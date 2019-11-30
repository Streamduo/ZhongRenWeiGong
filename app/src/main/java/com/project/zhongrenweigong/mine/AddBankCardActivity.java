package com.project.zhongrenweigong.mine;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.currency.bean.NavigationBean;
import com.project.zhongrenweigong.currency.event.RefreshIndustrySearchEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.CheckInputUtil;
import com.project.zhongrenweigong.util.GsonProvider;
import com.project.zhongrenweigong.util.UtilsStyle;

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

public class AddBankCardActivity extends BaseActivity<AddBankCardPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_card_people)
    EditText edCardPeople;
    @BindView(R.id.ed_card_num)
    EditText edCardNum;
    @BindView(R.id.te_next)
    TextView teNext;
    private LoginMsg userAccent;
    private String cardNum;
    private String cardPeople;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("添加银行卡");
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_bankcard;
    }

    @Override
    public AddBankCardPresent bindPresent() {
        return new AddBankCardPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teNext.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_next:
                cardPeople = edCardPeople.getText().toString();
                cardNum = edCardNum.getText().toString();
                if (TextUtils.isEmpty(cardPeople)) {
                    showToastShort("持卡人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(cardNum)) {
                    showToastShort("银行卡号不能为空");
                    return;
                }
                checkingCard(cardNum);
                break;
        }
    }

    private void checkingCard(String card) {
        //创建OkHttpClient对象
        OkHttpClient okhttpClient = new OkHttpClient();
        //创建Request对象
        Request request = new Request.Builder()
                .url("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?" +
                        "_input_charset=utf-8&cardNo=" + card + "&cardBinCheck=true")//请求的地址,根据需求带参
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
                        boolean validated = jsonObject.getBoolean("validated");
                        if (validated) {
                            getP().addBoundBankCard(userAccent.mbId, cardNum, cardPeople);
                        }else {
                            showToastShort("银行卡号错误");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }
}
