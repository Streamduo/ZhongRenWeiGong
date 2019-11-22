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
    private VoucherMessageListAdapter messageListAdapter;

    public static VoucherMessageFragment getInstance(int index){
        VoucherMessageFragment voucherMessageFragment = new VoucherMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        voucherMessageFragment.setArguments(bundle);
        return voucherMessageFragment;
    }

    @Override
    public void initView() {
        recyMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageListAdapter = new VoucherMessageListAdapter(R.layout.item_voucher_message_list);
        recyMessageList.setAdapter(messageListAdapter);

        messageListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageListsBean item = messageListAdapter.getItem(position);
                String messageId = item.messageId;
                Router.newIntent(getActivity()).putString("messageId",messageId)
                        .to(SystemMessageDetailActivity.class).launch();
            }
        });
    }

    @Override
    public void initAfter() {
        LoginMsg userAccent = AcacheUtils.getInstance(getContext()).getUserAccent();
        getP().getVoucherMessage("444");
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
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setData(SystemMessageBean messageListBean) {
        SystemDataBean data = messageListBean.getData();
        List<MessageListsBean> messageSystemLists = data.messageVoucherLists;
        if (messageSystemLists != null && messageSystemLists.size() > 0) {
            messageListAdapter.setNewData(messageSystemLists);
        }
    }

}
