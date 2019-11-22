package com.project.zhongrenweigong.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.home.adapter.MessagePageAdapter;
import com.project.zhongrenweigong.util.StatusBarUtils;
import com.project.zhongrenweigong.view.MyViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Fuduo on 2019/10/17 10:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MessageFragment extends BaseFragment<MessageFragmentPresent> {

    public static final String TAG = "MessageFragment";
    Unbinder unbinder;
    @BindView(R.id.te_clear_unread)
    TextView teClearUnread;
    @BindView(R.id.img_msg_sys)
    ImageView imgMsgSys;
    @BindView(R.id.te_msg_sys)
    TextView teMsgSys;
    @BindView(R.id.te_msg_sys_size)
    TextView teMsgSysSize;
    @BindView(R.id.img_msg_activity)
    ImageView imgMsgActivity;
    @BindView(R.id.te_msg_activity)
    TextView teMsgActivity;
    @BindView(R.id.te_msg_activity_size)
    TextView teMsgActivitySize;
    @BindView(R.id.img_msg_voucher)
    ImageView imgMsgVoucher;
    @BindView(R.id.te_msg_voucher)
    TextView teMsgVoucher;
    @BindView(R.id.te_msg_voucher_size)
    TextView teMsgVoucherSize;
    @BindView(R.id.vp_msg)
    MyViewPager vpMsg;
    @BindView(R.id.rl_msg_sys)
    RelativeLayout rlMsgSys;
    @BindView(R.id.rl_msg_activity)
    RelativeLayout rlMsgActivity;
    @BindView(R.id.rl_msg_voucher)
    RelativeLayout rlMsgVoucher;
    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    private float enlargeSize = 1.2f;
    private float size = 1.0f;

    @Override
    public void initView() {
        teBack.setVisibility(View.GONE);
        teTitle.setText("消息中心");
        MessagePageAdapter messagePageAdapter = new MessagePageAdapter(getChildFragmentManager());
        vpMsg.setAdapter(messagePageAdapter);
        vpMsg.setCurrentItem(0);
        vpMsg.setOffscreenPageLimit(3);
        vpMsg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    teMsgSys.setTextColor(getResources().getColor(R.color.FF000000));
                    teMsgActivity.setTextColor(getResources().getColor(R.color.app_text_66));
                    teMsgVoucher.setTextColor(getResources().getColor(R.color.app_text_66));

                    TextPaint sysTp = teMsgSys.getPaint();
                    sysTp.setFakeBoldText(true);
                    TextPaint activityTp = teMsgActivity.getPaint();
                    activityTp.setFakeBoldText(false);
                    TextPaint voucherTp = teMsgVoucher.getPaint();
                    voucherTp.setFakeBoldText(false);
                } else if (position == 1) {
                    teMsgSys.setTextColor(getResources().getColor(R.color.app_text_66));
                    teMsgActivity.setTextColor(getResources().getColor(R.color.FF000000));
                    teMsgVoucher.setTextColor(getResources().getColor(R.color.app_text_66));

                    TextPaint sysTp = teMsgSys.getPaint();
                    sysTp.setFakeBoldText(false);
                    TextPaint activityTp = teMsgActivity.getPaint();
                    activityTp.setFakeBoldText(true);
                    TextPaint voucherTp = teMsgVoucher.getPaint();
                    voucherTp.setFakeBoldText(false);
                } else if (position == 2) {
                    teMsgSys.setTextColor(getResources().getColor(R.color.app_text_66));
                    teMsgActivity.setTextColor(getResources().getColor(R.color.app_text_66));
                    teMsgVoucher.setTextColor(getResources().getColor(R.color.FF000000));

                    TextPaint sysTp = teMsgSys.getPaint();
                    sysTp.setFakeBoldText(false);
                    TextPaint activityTp = teMsgActivity.getPaint();
                    activityTp.setFakeBoldText(false);
                    TextPaint voucherTp = teMsgVoucher.getPaint();
                    voucherTp.setFakeBoldText(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initAfter() {
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public MessageFragmentPresent bindPresent() {
        return new MessageFragmentPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teClearUnread.setOnClickListener(this);
        rlMsgSys.setOnClickListener(this);
        rlMsgActivity.setOnClickListener(this);
        rlMsgVoucher.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_clear_unread:

                break;
            case R.id.rl_msg_sys:
                if (teMsgSysSize.getVisibility() == View.VISIBLE) {
                    teMsgSysSize.setScaleX(enlargeSize);
                    teMsgSysSize.setScaleY(enlargeSize);
                    teMsgActivitySize.setScaleX(size);
                    teMsgActivitySize.setScaleY(size);
                    teMsgVoucherSize.setScaleX(size);
                    teMsgVoucherSize.setScaleY(size);
                }
                imgMsgSys.setScaleX(enlargeSize);
                imgMsgSys.setScaleY(enlargeSize);
                imgMsgActivity.setScaleX(size);
                imgMsgActivity.setScaleY(size);
                imgMsgVoucher.setScaleX(size);
                imgMsgVoucher.setScaleY(size);
                vpMsg.setCurrentItem(0);
                break;
            case R.id.rl_msg_activity:
                if (teMsgActivitySize.getVisibility() == View.VISIBLE) {
                    teMsgSysSize.setScaleX(size);
                    teMsgSysSize.setScaleY(size);
                    teMsgActivitySize.setScaleX(enlargeSize);
                    teMsgActivitySize.setScaleY(enlargeSize);
                    teMsgVoucherSize.setScaleX(size);
                    teMsgVoucherSize.setScaleY(size);
                }
                imgMsgActivity.setScaleX(enlargeSize);
                imgMsgActivity.setScaleY(enlargeSize);
                imgMsgSys.setScaleX(size);
                imgMsgSys.setScaleY(size);
                imgMsgVoucher.setScaleX(size);
                imgMsgVoucher.setScaleY(size);
                vpMsg.setCurrentItem(1);
                break;
            case R.id.rl_msg_voucher:
                if (teMsgVoucherSize.getVisibility() == View.VISIBLE) {
                    teMsgVoucherSize.setScaleX(enlargeSize);
                    teMsgVoucherSize.setScaleY(enlargeSize);
                    teMsgSysSize.setScaleX(size);
                    teMsgSysSize.setScaleY(size);
                    teMsgActivitySize.setScaleX(size);
                    teMsgActivitySize.setScaleY(size);
                }
                imgMsgVoucher.setScaleX(enlargeSize);
                imgMsgVoucher.setScaleY(enlargeSize);
                imgMsgActivity.setScaleX(size);
                imgMsgActivity.setScaleY(size);
                imgMsgSys.setScaleX(size);
                imgMsgSys.setScaleY(size);
                vpMsg.setCurrentItem(2);
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
    public void onResume() {
        super.onResume();
        StatusBarUtils.with(getActivity()).setStatus(getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtils.with(getActivity()).setStatus(getActivity());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
