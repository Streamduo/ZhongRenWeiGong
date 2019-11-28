package com.project.zhongrenweigong.home;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.manager.BusinessManagerActivity;
import com.project.zhongrenweigong.business.manager.MineShopListActivity;
import com.project.zhongrenweigong.currency.event.RefreshMineEvent;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.AboutActivity;
import com.project.zhongrenweigong.mine.EditMineHomePageActivity;
import com.project.zhongrenweigong.mine.MyIntegralCompensationActivity;
import com.project.zhongrenweigong.mine.MyWalletActivity;
import com.project.zhongrenweigong.mine.ProfessionalCertificationActivity;
import com.project.zhongrenweigong.mine.StatementActivity;
import com.project.zhongrenweigong.mine.YiJianFanKuiActivity;
import com.project.zhongrenweigong.mine.adapter.GoodDeedListAdapter;
import com.project.zhongrenweigong.mine.bean.MineSystemBean;
import com.project.zhongrenweigong.mine.bean.SystemDataBean;
import com.project.zhongrenweigong.mine.set.SetActivity;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.LoginOutUtils;
import com.project.zhongrenweigong.util.StatusBarUtils;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;
import com.project.zhongrenweigong.view.disk.DataItem;
import com.project.zhongrenweigong.view.disk.DiscView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * 作者：Fuduo on 2019/10/17 10:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MineFragment extends BaseFragment<MinePresent> {

    public static final String TAG = "MineFragment";
    @BindView(R.id.img_user_head)
    ImageView imgUserHead;
    @BindView(R.id.te_user_name)
    TextView teUserName;
    @BindView(R.id.te_user_id)
    TextView teUserId;
    @BindView(R.id.rl_mine_industry)
    RelativeLayout rlMineIndustry;
    Unbinder unbinder;
    @BindView(R.id.te_user_morality)
    TextView teUserMorality;
    @BindView(R.id.te_work_authentication)
    TextView teWorkAuthentication;
    @BindView(R.id.te_jifen)
    TextView teJifen;
    @BindView(R.id.te_mine_coupon)
    TextView teMineCoupon;
    @BindView(R.id.te_pay)
    TextView tePay;
    @BindView(R.id.te_guanzhu)
    TextView teGuanzhu;
    @BindView(R.id.te_daode)
    TextView teDaode;
    @BindView(R.id.rl_mine_daode)
    RelativeLayout rlMineDaode;
    @BindView(R.id.te_mine_renzheng_status)
    TextView teMineRenzhengStatus;
    @BindView(R.id.rl_mine_renzheng)
    RelativeLayout rlMineRenzheng;
    @BindView(R.id.te_business_renzheng_status)
    TextView teBusinessRenzhengStatus;
    @BindView(R.id.rl_business_renzheng)
    RelativeLayout rlBusinessRenzheng;
    @BindView(R.id.rl_mine_about_our)
    RelativeLayout rlMineAboutOur;
    @BindView(R.id.rl_mine_fankui)
    RelativeLayout rlMineFankui;

    @BindView(R.id.cs_staistics)
    DiscView csStaistics;
    @BindView(R.id.te_total_score)
    TextView teTotalScore;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.rl_more_worker)
    RelativeLayout rlMoreWorker;
    @BindView(R.id.recy_worke_list)
    RecyclerView recyWorkeList;
    @BindView(R.id.rl_more_orality)
    RelativeLayout rlMoreOrality;
    @BindView(R.id.recy_orality_list)
    RecyclerView recyOralityList;
    @BindView(R.id.rl_more_sincerity)
    RelativeLayout rlMoreSincerity;
    @BindView(R.id.recy_sincerity_list)
    RecyclerView recySincerityList;
    @BindView(R.id.rl_more_dedication)
    RelativeLayout rlMoreDedication;
    @BindView(R.id.recy_dedication_list)
    RecyclerView recyDedicationList;
    @BindView(R.id.scro_all)
    ScrollView scroAll;
    @BindView(R.id.rl_change_login)
    RelativeLayout rlChangeLogin;
    @BindView(R.id.scro_mine)
    ScrollView scroMine;
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
    private Dialog exitLoginDialog;

    @Override
    public void onResume() {
        super.onResume();
        StatusBarUtils.with(getActivity()).init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtils.with(getActivity()).init();
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        setData();
        recyDedicationList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyOralityList.setLayoutManager(new LinearLayoutManager(getContext()));
        recySincerityList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyWorkeList.setLayoutManager(new LinearLayoutManager(getContext()));

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
        LoginMsg userAccent = AcacheUtils.getInstance(getContext()).getUserAccent();
        getP().getIndividualSystem(userAccent.mbId);
        scroMine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (scroMine.getScrollY() == 0) {
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
                                scroMine.setVisibility(View.GONE);
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
                                    scroMine.setVisibility(View.VISIBLE);
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
        return R.layout.fragment_mine;
    }

    @Override
    public MinePresent bindPresent() {
        return new MinePresent();
    }

    @Override
    public void setListener() {
        imgUserHead.setOnClickListener(this);

        rlMineDaode.setOnClickListener(this);
        rlMineRenzheng.setOnClickListener(this);
        rlBusinessRenzheng.setOnClickListener(this);
        rlMineIndustry.setOnClickListener(this);
        rlMineAboutOur.setOnClickListener(this);
        rlMineFankui.setOnClickListener(this);
        rlChangeLogin.setOnClickListener(this);

        teJifen.setOnClickListener(this);
        teMineCoupon.setOnClickListener(this);
        tePay.setOnClickListener(this);
        teGuanzhu.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_user_head:
                Router.newIntent(getActivity()).to(EditMineHomePageActivity.class).launch();
                break;
            case R.id.te_jifen:
                MyIntegralCompensationActivity.start(getContext(),2);
                break;
            case R.id.te_mine_coupon:

                break;
            case R.id.te_pay:
                Router.newIntent(getActivity()).to(MyWalletActivity.class).launch();
                break;
            case R.id.te_guanzhu:

                break;
            case R.id.rl_mine_daode:
                scroMine.setVisibility(View.GONE);
                break;
            case R.id.rl_mine_renzheng:
                Router.newIntent(getActivity()).to(ProfessionalCertificationActivity.class).launch();
                break;
            case R.id.rl_business_renzheng:
                break;
            case R.id.rl_mine_industry:
                Router.newIntent(getActivity()).to(MineShopListActivity.class).launch();
                break;
            case R.id.rl_mine_fankui:
                Router.newIntent(getActivity()).to(YiJianFanKuiActivity.class).launch();
                break;
            case R.id.rl_mine_about_our:
                Router.newIntent(getActivity()).to(AboutActivity.class).launch();
                break;
            case R.id.rl_change_login:
                showExitLoginDialog();
                break;
        }
    }

    private void showExitLoginDialog() {
        exitLoginDialog = new Dialog(getContext(), R.style.dialog_bottom_full);
        exitLoginDialog.setCanceledOnTouchOutside(true);
        exitLoginDialog.setCancelable(true);
        Window window = exitLoginDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(getContext(), R.layout.dialog_layout_two_line, null);
        TextView teLineOne = (TextView) view.findViewById(R.id.te_line_one);
        TextView teLineTwo = (TextView) view.findViewById(R.id.te_line_two);
        TextView teOk = (TextView) view.findViewById(R.id.te_ok);
        TextView teCancel = (TextView) view.findViewById(R.id.te_cancel);
        teLineOne.setText("退出登录");
        teLineTwo.setText("确定退出登录？");

        teOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                exitLoginDialog.dismiss();
                LoginOutUtils.loginOut(getActivity());
                Router.newIntent(getActivity()).putInt("isLoginOut",1).to(LoginActivity.class).launch();
                getActivity().finish();

            }
        });

        teCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                exitLoginDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        exitLoginDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setMoralityData(MineSystemBean mineSystemBean) {
        SystemDataBean data = mineSystemBean.getData();
        if (data != null) {
            teTotalScore.setText(String.valueOf(data.sumGrade));
            teUserMorality.setText("道德综合分数：" + String.valueOf(data.sumGrade));
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

    private void setData() {
        LoginMsg loginMsg = AcacheUtils.getInstance(getContext()).getUserAccent();
        if (loginMsg != null && loginMsg.mbId != null && !loginMsg.mbId.equals("")) {
            GlideDownLoadImage.getInstance().loadCircleImage(this, loginMsg.headUrl, imgUserHead, R.mipmap.big_default_user_head);
            teUserName.setText(loginMsg.mbName);
            teUserId.setText("ID:" + loginMsg.mbId);
            if (loginMsg.isAuthMerchant.equals("1")) {
                teBusinessRenzhengStatus.setVisibility(View.VISIBLE);
            } else {
                teBusinessRenzhengStatus.setVisibility(View.GONE);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshMineEvent refreshMineEvent) {
        setData();
    }
}
