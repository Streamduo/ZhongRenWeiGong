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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
