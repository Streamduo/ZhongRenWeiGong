package com.project.zhongrenweigong.mine;

import android.content.Intent;
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
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.adapter.GoodDeedListAdapter;
import com.project.zhongrenweigong.mine.bean.MineDataBean;
import com.project.zhongrenweigong.mine.bean.MineInfoBean;
import com.project.zhongrenweigong.mine.bean.MineSystemBean;
import com.project.zhongrenweigong.mine.bean.SystemDataBean;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;
import com.project.zhongrenweigong.view.disk.DataItem;
import com.project.zhongrenweigong.view.disk.DiscView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineHomePageActivity extends BaseActivity<MineHomePagePresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.cs_staistics)
    DiscView csStaistics;
    @BindView(R.id.recy_worke_list)
    RecyclerView recyWorkeList;
    @BindView(R.id.recy_orality_list)
    RecyclerView recyOralityList;
    @BindView(R.id.te_user_name)
    TextView teUserName;
    @BindView(R.id.img_user_head)
    ImageView imgUserHead;
    @BindView(R.id.img_tester)
    ImageView imgTester;
    @BindView(R.id.te_id)
    TextView teId;
    @BindView(R.id.te_morality_branch)
    TextView teMoralityBranch;
    @BindView(R.id.te_renzheng)
    TextView teRenzheng;
    @BindView(R.id.line_mine)
    LinearLayout lineMine;
    @BindView(R.id.scro_all)
    ScrollView scroAll;
    @BindView(R.id.line_head)
    LinearLayout lineHead;
    @BindView(R.id.te_total_score)
    TextView teTotalScore;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.recy_sincerity_list)
    RecyclerView recySincerityList;
    @BindView(R.id.recy_dedication_list)
    RecyclerView recyDedicationList;
    @BindView(R.id.rl_xuanshi)
    RelativeLayout rlXuanshi;
    @BindView(R.id.rl_more_worker)
    RelativeLayout rlMoreWorker;
    @BindView(R.id.rl_more_orality)
    RelativeLayout rlMoreOrality;
    @BindView(R.id.rl_more_sincerity)
    RelativeLayout rlMoreSincerity;
    @BindView(R.id.rl_more_dedication)
    RelativeLayout rlMoreDedication;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;
    private GoodDeedListAdapter goodDedicationListAdapter;
    private GoodDeedListAdapter goodOralityListAdapter;
    private GoodDeedListAdapter goodSincerityListAdapter;
    private GoodDeedListAdapter goodWorkeListAdapter;
    private String mbId;
    private int userType;

    @Override
    public void initView() {
        teTitle.setText("个人中心");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        Intent intent = getIntent();
        userType = intent.getIntExtra("userType", 0);
        mbId = intent.getStringExtra("mbId");
        if (userType == 1) {
            rlTop.setBackgroundResource(R.drawable.bg_bac_43b959_5);
            imgTester.setVisibility(View.VISIBLE);
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
        getP().getPersonalHomepage(mbId);
        getP().getIndividualSystem(mbId);
        lineHead.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
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
                            //向下滑動
                            lineMine.setVisibility(View.GONE);
                        }
                        break;
                }
                return true;
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
                                    lineMine.setVisibility(View.VISIBLE);
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
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine_home_page;
    }

    @Override
    public MineHomePagePresent bindPresent() {
        return new MineHomePagePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        rlXuanshi.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.rl_xuanshi:

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public void setData(MineInfoBean mineInfoBean) {
        MineDataBean mineInfoBeanData = mineInfoBean.getData();
        if (mineInfoBeanData != null) {
            GlideDownLoadImage.getInstance().loadCircleImage(this, mineInfoBeanData.mbHeadUrl, imgUserHead, R.mipmap.img_default_gray_head);
            teUserName.setText(mineInfoBeanData.mbName);
            teId.setText("ID:" + mineInfoBeanData.mbId);
            teMoralityBranch.setText("道德综合分数：" + mineInfoBeanData.individualSystemGrade);
            String isProfession = mineInfoBeanData.isProfession;
            if (isProfession.equals("1")) {
                teRenzheng.setText("职业认证：已认证");
            } else {
                teRenzheng.setText("职业认证：未认证");
            }

        }
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
