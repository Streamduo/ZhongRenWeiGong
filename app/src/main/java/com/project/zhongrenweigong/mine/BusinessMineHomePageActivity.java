package com.project.zhongrenweigong.mine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.mine.adapter.GoodDeedListAdapter;
import com.project.zhongrenweigong.mine.adapter.ShopListPageAdapter;
import com.project.zhongrenweigong.mine.bean.BusinessSystemBean;
import com.project.zhongrenweigong.mine.bean.CategoryListsBean;
import com.project.zhongrenweigong.mine.bean.MineDataBean;
import com.project.zhongrenweigong.mine.bean.MineSystemBean;
import com.project.zhongrenweigong.mine.bean.ShopDataBean;
import com.project.zhongrenweigong.mine.bean.SystemDataBean;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;
import com.project.zhongrenweigong.view.MyViewPager;
import com.project.zhongrenweigong.view.disk.DataItem;
import com.project.zhongrenweigong.view.disk.DiscView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessMineHomePageActivity extends BaseActivity<BusinessMineHomePagePresent> {


    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.cs_staistics)
    DiscView csStaistics;
    @BindView(R.id.te_total_score)
    TextView teTotalScore;
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
    @BindView(R.id.te_user_name)
    TextView teUserName;
    @BindView(R.id.img_user_head)
    ImageView imgUserHead;
    @BindView(R.id.te_id)
    TextView teId;
    @BindView(R.id.te_morality_branch)
    TextView teMoralityBranch;
    @BindView(R.id.te_renzheng)
    TextView teRenzheng;
    @BindView(R.id.img_xuanshi)
    ImageView imgXuanshi;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.discover_list_vp)
    MyViewPager discoverListVp;
    @BindView(R.id.scro_shop_all)
    ScrollView scroShopAll;
    @BindView(R.id.line_head)
    LinearLayout lineHead;
    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;
    private ShopListPageAdapter shopListPageAdapter;

    private GoodDeedListAdapter goodDedicationListAdapter;
    private GoodDeedListAdapter goodOralityListAdapter;
    private GoodDeedListAdapter goodSincerityListAdapter;
    private GoodDeedListAdapter goodWorkeListAdapter;

    @Override
    public void initView() {
        teTitle.setText("个人中心");
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
        getP().getMerchantPersonalHomepage("444");
        getP().getIndividualSystem("444");
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
                            scroShopAll.setVisibility(View.GONE);
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
                                //向下滑動
                                scroShopAll.setVisibility(View.VISIBLE);
                                return true;
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
        return R.layout.activity_shop_home_page;
    }

    @Override
    public BusinessMineHomePagePresent bindPresent() {
        return new BusinessMineHomePagePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private void setViewPager() {
        tabLayout.setViewPager(discoverListVp);
        discoverListVp.setCurrentItem(0);
        discoverListVp.setOffscreenPageLimit(3);
        tabLayout.onPageSelected(0);
        tabLayout.getTitleView(0).setTextSize(17);
        discoverListVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < 3; i++) {
                    if (position == i) { //选中的字体大小
                        tabLayout.getTitleView(i).setTextSize(17);
                    } else {
                        tabLayout.getTitleView(i).setTextSize(14);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public void setData(BusinessSystemBean businessSystemBean) {
        ShopDataBean shopDataBean = businessSystemBean.getData();
        if (shopDataBean != null) {
            GlideDownLoadImage.getInstance().loadCircleImage(this, shopDataBean.mbHeadUrl, imgUserHead, R.mipmap.img_default_gray_head);
            teUserName.setText(shopDataBean.mbName);
            teId.setText("ID:" + shopDataBean.mbId);
            teMoralityBranch.setText("道德综合分数：" + shopDataBean.individualSystemGrade);
            String isMerchantAuth = shopDataBean.isMerchantAuth;
            if (isMerchantAuth.equals("1")) {
                teRenzheng.setText("商家认证：已认证");
            } else {
                teRenzheng.setText("商家认证：未认证");
            }
            List<CategoryListsBean> categoryLists = shopDataBean.categoryLists;
            shopListPageAdapter = new ShopListPageAdapter(getSupportFragmentManager(), categoryLists);
            discoverListVp.setAdapter(shopListPageAdapter);
            setViewPager();
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
