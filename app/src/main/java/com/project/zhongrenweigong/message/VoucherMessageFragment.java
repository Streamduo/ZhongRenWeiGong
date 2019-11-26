package com.project.zhongrenweigong.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.currency.event.RefreshMessageEvent;
import com.project.zhongrenweigong.home.MessageFragment;
import com.project.zhongrenweigong.home.adapter.MessageListAdapter;
import com.project.zhongrenweigong.home.bean.MessageDataBean;
import com.project.zhongrenweigong.home.bean.MessageListBean;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.message.adapter.SystemMessageListAdapter;
import com.project.zhongrenweigong.message.adapter.VoucherMessageListAdapter;
import com.project.zhongrenweigong.message.bean.MessageListsBean;
import com.project.zhongrenweigong.message.bean.SystemDataBean;
import com.project.zhongrenweigong.message.bean.SystemMessageBean;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.QueShengManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class VoucherMessageFragment extends BaseFragment<VoucherMessageListFragmentPresent> {

    public static final String TAG = "MessageFragment";
    Unbinder unbinder;
    @BindView(R.id.recy_message_list)
    RecyclerView recyMessageList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private VoucherMessageListAdapter messageListAdapter;
    private int currentPage = 1;
    private LoginMsg userAccent;

    public static VoucherMessageFragment getInstance(int index) {
        VoucherMessageFragment voucherMessageFragment = new VoucherMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        voucherMessageFragment.setArguments(bundle);
        return voucherMessageFragment;
    }

    @Override
    public void initView() {
        userAccent = AcacheUtils.getInstance(getContext()).getUserAccent();
        recyMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageListAdapter = new VoucherMessageListAdapter(R.layout.item_voucher_message_list);
        recyMessageList.setAdapter(messageListAdapter);

        messageListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageListsBean item = messageListAdapter.getItem(position);
                item.isRead = "1";
                messageListAdapter.notifyItemChanged(position);
                Router.newIntent(getActivity()).putSerializable("messageListsBean", item)
                        .to(VoucherMessageDetailActivity.class).launch();
            }
        });

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                initAfter();
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                initAfter();
            }
        });
    }

    @Override
    public void initAfter() {
        getP().getVoucherMessage(userAccent.mbId, currentPage);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_message_list;
    }

    @Override
    public VoucherMessageListFragmentPresent bindPresent() {
        return new VoucherMessageListFragmentPresent();
    }

    @Override
    public void setListener() {
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
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

    public void setData(SystemMessageBean messageListBean) {
        int pageSize = messageListBean.pageSize;
        SystemDataBean data = messageListBean.getData();
        int noReadNum = data.noReadNum;
        List<MessageListsBean> messageSystemLists = data.messageVoucherLists;
        if (messageSystemLists != null && messageSystemLists.size() > 0) {
            if (currentPage == 1) {
                messageListAdapter.setNewData(messageSystemLists);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                smRefresh.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                messageListAdapter.addData(messageSystemLists);
            }
        } else {
            getDataError();
        }
        if (noReadNum > 0) {
            MessageFragment messageFragment = (MessageFragment) getParentFragment();
            if (messageFragment != null) {
                messageFragment.teMsgVoucherSize.setVisibility(View.VISIBLE);
                messageFragment.teMsgVoucherSize.setText(String.valueOf(noReadNum));
            }
        }
        if (currentPage == pageSize) {
            smRefresh.setEnableLoadMore(false);
        }
    }

    public void getDataError() {
        if (currentPage == 1) {
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1,messageListAdapter,smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshMessageEvent refreshMessageEvent) {
        initAfter();
    }
}
