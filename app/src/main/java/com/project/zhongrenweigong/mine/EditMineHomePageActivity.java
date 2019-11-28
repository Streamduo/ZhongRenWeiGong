package com.project.zhongrenweigong.mine;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.manager.BusinessManagerActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.adapter.GoodDeedListAdapter;
import com.project.zhongrenweigong.mine.bean.MineSystemBean;
import com.project.zhongrenweigong.mine.bean.SystemDataBean;
import com.project.zhongrenweigong.mine.set.ChangePasswordActivity;
import com.project.zhongrenweigong.mine.set.ChangePhoneActivity;
import com.project.zhongrenweigong.mine.set.SetActivity;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.view.disk.DataItem;
import com.project.zhongrenweigong.view.disk.DiscView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class EditMineHomePageActivity extends BaseActivity<EditMineHomePagePresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.cs_staistics)
    DiscView csStaistics;
    @BindView(R.id.te_total_score)
    TextView teTotalScore;
    @BindView(R.id.line_head)
    LinearLayout lineHead;
    @BindView(R.id.recy_worke_list)
    RecyclerView recyWorkeList;
    @BindView(R.id.recy_orality_list)
    RecyclerView recyOralityList;
    @BindView(R.id.recy_sincerity_list)
    RecyclerView recySincerityList;
    @BindView(R.id.recy_dedication_list)
    RecyclerView recyDedicationList;
    @BindView(R.id.scro_all)
    ScrollView scroAll;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.te_id_num)
    TextView teIdNum;
    @BindView(R.id.te_authentication_status)
    TextView teAuthenticationStatus;
    @BindView(R.id.rl_shop_authentication)
    RelativeLayout rlShopAuthentication;
    @BindView(R.id.rl_shop_manager)
    RelativeLayout rlShopManager;
    @BindView(R.id.te_orality_num)
    TextView teOralityNum;
    @BindView(R.id.te_mine_authentication_status)
    TextView teMineAuthenticationStatus;
    @BindView(R.id.rl_mine_authentication)
    RelativeLayout rlMineAuthentication;
    @BindView(R.id.te_phone_num)
    TextView tePhoneNum;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.rl_change_psd)
    RelativeLayout rlChangePsd;
    @BindView(R.id.img_xuanshi)
    ImageView imgXuanshi;
    @BindView(R.id.scro_info)
    ScrollView scroInfo;

    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;

    private GoodDeedListAdapter goodDedicationListAdapter;
    private GoodDeedListAdapter goodOralityListAdapter;
    private GoodDeedListAdapter goodSincerityListAdapter;
    private GoodDeedListAdapter goodWorkeListAdapter;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("个人中心");
        LoginMsg userAccent = AcacheUtils.getInstance(this).getUserAccent();
        teIdNum.setText(userAccent.mbId);
        tePhoneNum.setText(userAccent.mbPhone);
        if (userAccent.isAuthMerchant.equals("1")){
            teMineAuthenticationStatus.setVisibility(View.VISIBLE);
        }else {
            teMineAuthenticationStatus.setVisibility(View.GONE);
        }
        recyDedicationList.setLayoutManager(new LinearLayoutManager(this));
        recyOralityList.setLayoutManager(new LinearLayoutManager(this));
        recySincerityList.setLayoutManager(new LinearLayoutManager(this));
        recyWorkeList.setLayoutManager(new LinearLayoutManager(this));

        goodDedicationListAdapter = new GoodDeedListAdapter(R.layout.item_good_deed_list);
        recyDedicationList.setAdapter(goodDedicationListAdapter);
        goodOralityListAdapter = new GoodDeedListAdapter(R.layout.item_good_deed_list);
        recyOralityList.setAdapter(goodOralityListAdapter);
        goodSincerityListAdapter = new GoodDeedListAdapter(R.layout.item_good_deed_list);
        recySincerityList.setAdapter(goodSincerityListAdapter);
        goodWorkeListAdapter = new GoodDeedListAdapter(R.layout.item_good_deed_list);
        recyWorkeList.setAdapter(goodWorkeListAdapter);
    }

    @Override
    public void initAfter() {
        LoginMsg userAccent = AcacheUtils.getInstance(this).getUserAccent();
        getP().getIndividualSystem(userAccent.mbId);
        scroInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (scroInfo.getScrollY() == 0) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mPosX = event.getX();
                            mPosY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            mCurPosX = event.getX();
                            mCurPosY = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            if (mCurPosY - mPosY > 0
                                    && (Math.abs(mCurPosY - mPosY) > 100)) {
                                scroInfo.setVisibility(View.GONE);
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });
        scroAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (scroAll.getScrollY() == 0) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mPosX = event.getX();
                            mPosY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            mCurPosX = event.getX();
                            mCurPosY = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            if (mCurPosY - mPosY > 0
                                    && (Math.abs(mCurPosY - mPosY) > 100)) {
                                if (scroAll.getScrollY() == 0) {
                                    //向下滑動
                                    scroInfo.setVisibility(View.VISIBLE);
                                    return true;
                                }
                            }
                            break;
                    }
                }
                return false;
            }
        });

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_edit_mine_home_page;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public EditMineHomePagePresent bindPresent() {
        return new EditMineHomePagePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        rlShopAuthentication.setOnClickListener(this);
        rlShopManager.setOnClickListener(this);
        rlMineAuthentication.setOnClickListener(this);
        rlPhone.setOnClickListener(this);
        rlChangePsd.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.rl_shop_authentication:
                break;
            case R.id.rl_shop_manager:
                Router.newIntent(EditMineHomePageActivity.this).to(BusinessManagerActivity.class).launch();
                break;
            case R.id.rl_mine_authentication:
                Router.newIntent(EditMineHomePageActivity.this).to(ProfessionalCertificationActivity.class).launch();

                break;
            case R.id.rl_phone:
                Router.newIntent(EditMineHomePageActivity.this).to(ChangePhoneActivity.class).launch();
                break;
            case R.id.rl_change_psd:
                Router.newIntent(EditMineHomePageActivity.this).to(ChangePasswordActivity.class).launch();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public void setMoralityData(MineSystemBean mineSystemBean) {
        SystemDataBean data = mineSystemBean.getData();
        if (data != null) {
            teTotalScore.setText(String.valueOf(data.sumGrade));

            List<DataItem> items = new ArrayList<>();
            items.add(new DataItem(data.professionGrade, "", String.valueOf(data.professionGrade), getResources().getColor(R.color.app_FF3C3C)));
            items.add(new DataItem(data.everydayGrade, "", String.valueOf(data.everydayGrade), getResources().getColor(R.color.app_1B82D2)));
            items.add(new DataItem(data.integrityGrade, "", String.valueOf(data.integrityGrade), getResources().getColor(R.color.app_F49C2E)));
            items.add(new DataItem(data.socialContributionGrade, "", String.valueOf(data.socialContributionGrade), getResources().getColor(R.color.app_29AB91)));
            csStaistics.setItems(items);

            goodWorkeListAdapter.setNewData(data.professionBehavior);
            goodOralityListAdapter.setNewData(data.everydayBehavior);
            goodSincerityListAdapter.setNewData(data.integrityBehavior);
            goodDedicationListAdapter.setNewData(data.socialContributionBehavior);
        }
    }
}
