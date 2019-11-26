package com.project.zhongrenweigong.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.manager.BusinessManagerActivity;
import com.project.zhongrenweigong.currency.event.RefreshMineEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.EditMineHomePageActivity;
import com.project.zhongrenweigong.mine.MyAuthenticationActivity;
import com.project.zhongrenweigong.mine.MyWalletActivity;
import com.project.zhongrenweigong.mine.set.SetActivity;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.StatusBarUtils;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public MinePresent bindPresent() {
        return null;
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
                Router.newIntent(getActivity()).to(SetActivity.class).launch();
                break;
            case R.id.te_mine_coupon:
                break;
            case R.id.te_pay:
                Router.newIntent(getActivity()).to(MyWalletActivity.class).launch();
                break;
            case R.id.te_guanzhu:
                break;
            case R.id.rl_mine_daode:

                break;
            case R.id.rl_mine_renzheng:
                break;
            case R.id.rl_business_renzheng:
                break;
            case R.id.rl_mine_industry:
                Router.newIntent(getActivity()).to(BusinessManagerActivity.class).launch();
                break;
            case R.id.rl_mine_fankui:
                Router.newIntent(getActivity()).to(SetActivity.class).launch();
                break;
            case R.id.rl_mine_about_our:
                break;
        }
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

    private void setData() {
        LoginMsg loginMsg = AcacheUtils.getInstance(getContext()).getUserAccent();
        if (loginMsg != null && loginMsg.mbId != null && !loginMsg.mbId.equals("")) {
            GlideDownLoadImage.getInstance().loadCircleImage(this, loginMsg.headUrl, imgUserHead, R.mipmap.big_default_user_head);
            teUserName.setText(loginMsg.mbName);
            teUserId.setText("ID:" + loginMsg.mbId);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshMineEvent refreshMineEvent) {
        setData();
    }
}
