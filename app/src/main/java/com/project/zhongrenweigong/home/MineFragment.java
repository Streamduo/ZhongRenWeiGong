package com.project.zhongrenweigong.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.currency.event.RefreshMineEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.MyAuthenticationActivity;
import com.project.zhongrenweigong.mine.MyWalletActivity;
import com.project.zhongrenweigong.util.XCache;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.cache.DiskCache;
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
    @BindView(R.id.mine_renzheng)
    ImageView mineRenzheng;
    @BindView(R.id.line_member)
    LinearLayout lineMember;
    @BindView(R.id.rl_mine_wallet)
    RelativeLayout rlMineWallet;
    @BindView(R.id.rl_mine_pay_record)
    RelativeLayout rlMinePayRecord;
    @BindView(R.id.rl_mine_industry)
    RelativeLayout rlMineIndustry;
    @BindView(R.id.rl_mine_help_center)
    RelativeLayout rlMineHelpCenter;
    @BindView(R.id.rl_mine_set)
    RelativeLayout rlMineSet;
    Unbinder unbinder;

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
        mineRenzheng.setOnClickListener(this);
        lineMember.setOnClickListener(this);
        rlMineWallet.setOnClickListener(this);
        rlMinePayRecord.setOnClickListener(this);
        rlMineIndustry.setOnClickListener(this);
        rlMineHelpCenter.setOnClickListener(this);
        rlMineSet.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_user_head:
                break;
            case R.id.mine_renzheng:
                Router.newIntent(getActivity()).to(MyAuthenticationActivity.class).launch();
                break;
            case R.id.line_member:
                break;
            case R.id.rl_mine_wallet:
                Router.newIntent(getActivity()).to(MyWalletActivity.class).launch();
                break;
            case R.id.rl_mine_pay_record:
                break;
            case R.id.rl_mine_industry:
                break;
            case R.id.rl_mine_help_center:
                break;
            case R.id.rl_mine_set:
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
        XCache xCache = new XCache.Builder(getContext()).build();
        LoginMsg loginMsg = (LoginMsg) xCache.getObject(Constans.USERACCENT);
        if (loginMsg != null && loginMsg.mbId != null && !loginMsg.mbId.equals("")) {
            GlideDownLoadImage.getInstance().loadCircleImage(this, loginMsg.headUrl, imgUserHead,R.mipmap.user_default_head);
            teUserName.setText(loginMsg.mbName);
            teUserId.setText("ID:" + loginMsg.mbId);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshMineEvent refreshMineEvent) {
        setData();
    }
}
