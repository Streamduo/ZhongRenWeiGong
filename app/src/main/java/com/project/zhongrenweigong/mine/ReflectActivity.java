package com.project.zhongrenweigong.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.adapter.BankCardListAdapter;
import com.project.zhongrenweigong.mine.bean.BankCardBean;
import com.project.zhongrenweigong.mine.bean.BankCardDataBean;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;

public class ReflectActivity extends BaseActivity<ReflectPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.img_reflect_bankcard)
    ImageView imgReflectBankcard;
    @BindView(R.id.img_reflect_wx)
    ImageView imgReflectWx;
    @BindView(R.id.img_reflect_zfb)
    ImageView imgReflectZfb;
    @BindView(R.id.te_bank_name)
    TextView teBankName;
    @BindView(R.id.rl_bank)
    RelativeLayout rlBank;
    @BindView(R.id.te_reflect_money)
    TextView teReflectMoney;
    @BindView(R.id.te_reflect_record)
    TextView teReflectRecord;
    @BindView(R.id.te_reflect_all)
    TextView teReflectAll;
    @BindView(R.id.te_reflect_ok)
    TextView teReflectOk;
    @BindView(R.id.ed_reflect_money)
    EditText edReflectMoney;
    private BankCardListAdapter listAdapter;
    private List<BankCardDataBean> bankCardDataBeanList;
    private LoginMsg userAccent;
    private int code;
    private boolean noData;
    private String reflectMoney;
    private int reflectType;
    private String cardNumber;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("提现");
        Intent intent = getIntent();
        reflectType = intent.getIntExtra("reflectType", 0);
        reflectMoney = intent.getStringExtra("reflectMoney");
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
        teReflectMoney.setText(reflectMoney);
    }

    @Override
    public void initAfter() {
        getP().getBankCardList(userAccent.mbId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reflect;
    }

    @Override
    public ReflectPresent bindPresent() {
        return new ReflectPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        rlBank.setOnClickListener(this);
        teReflectRecord.setOnClickListener(this);
        teReflectAll.setOnClickListener(this);
        teReflectOk.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.rl_bank:
                if (noData) {
                    Router.newIntent(ReflectActivity.this).to(AddBankCardActivity.class).launch();
                } else {
                    showAddBankCardDialog();
                }
                break;
            case R.id.te_reflect_record:
                Router.newIntent(ReflectActivity.this).putInt("recordType", 1)
                        .to(CompensationRecordActivity.class)
                        .launch();
                break;
            case R.id.te_reflect_all:
                edReflectMoney.setText(reflectMoney);
                edReflectMoney.setSelection(reflectMoney.length());
                break;
            case R.id.te_reflect_ok:
                String reflectMoney = edReflectMoney.getText().toString();
                if (TextUtils.isEmpty(reflectMoney)){
                    showToastShort("请输入提现金额");
                    return;
                }
                getP().withdrawalEncryption(cardNumber, reflectType, userAccent.mbId, reflectMoney);
                break;
        }
    }

    private void showAddBankCardDialog() {
        final Dialog addBankCardDialog = new Dialog(this, R.style.dialog_bottom_full);
        addBankCardDialog.setCanceledOnTouchOutside(true);
        addBankCardDialog.setCancelable(true);
        Window window = addBankCardDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        View view = View.inflate(this, R.layout.dialog_layout_select_bankcard, null);
        ImageView imgExit = (ImageView) view.findViewById(R.id.img_exit);
        RecyclerView recyBankList = (RecyclerView) view.findViewById(R.id.recy_bank_list);
        TextView teAddBank = (TextView) view.findViewById(R.id.te_add_bank);

        recyBankList.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new BankCardListAdapter(R.layout.item_bank_list);
        recyBankList.setAdapter(listAdapter);
        if (bankCardDataBeanList != null && bankCardDataBeanList.size() > 0) {
            listAdapter.setNewData(bankCardDataBeanList);
        }
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BankCardDataBean item = listAdapter.getItem(position);
                cardNumber = item.cardNumber;
                String desensitizationCardNumber = item.desensitizationCardNumber;
                teBankName.setText(item.bankName + "(" + desensitizationCardNumber + ")");
                listAdapter.setOtherFalse(position);
                addBankCardDialog.dismiss();
            }
        });
        teAddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBankCardDialog.dismiss();
                Router.newIntent(ReflectActivity.this).to(AddBankCardActivity.class).launch();
            }
        });

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBankCardDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        addBankCardDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public void setData(BankCardBean bankCardBean) {
        bankCardDataBeanList = bankCardBean.getData();
        if (bankCardDataBeanList != null && bankCardDataBeanList.size() > 0) {
            BankCardDataBean bankCardDataBean = bankCardDataBeanList.get(0);
            bankCardDataBean.select = 1;
            cardNumber = bankCardDataBean.cardNumber;
            String desensitizationCardNumber = bankCardDataBean.desensitizationCardNumber;
            teBankName.setText(bankCardDataBean.bankName + "(" + desensitizationCardNumber + ")");
        }
    }

    public void setNoCard() {
        noData = true;
        teBankName.setText("请先添加银行卡");
    }
}
