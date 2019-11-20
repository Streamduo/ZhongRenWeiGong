package com.project.zhongrenweigong.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.home.adapter.MessageListAdapter;
import com.project.zhongrenweigong.home.bean.MessageDataBean;
import com.project.zhongrenweigong.home.bean.MessageListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
public class MessageFragment extends BaseFragment<MessageListFragmentPresent> {

    public static final String TAG = "MessageFragment";
    Unbinder unbinder;
    @BindView(R.id.recy_message_list)
    RecyclerView recyMessageList;
    private MessageListAdapter messageListAdapter;

    @Override
    public void initView() {
        recyMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageListAdapter = new MessageListAdapter(R.layout.item_message_list);
        recyMessageList.setAdapter(messageListAdapter);

        messageListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageDataBean item = messageListAdapter.getItem(position);
                String typeId = item.typeId;
                Router.newIntent(getActivity()).putString("typeId",typeId)
                        .to(MessageDetailActivity.class).launch();
            }
        });
    }

    @Override
    public void initAfter() {
        getP().getMessageList();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_message_list;
    }

    @Override
    public MessageListFragmentPresent bindPresent() {
        return new MessageListFragmentPresent();
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

    public void setData(MessageListBean messageListBean) {
        List<MessageDataBean> data = messageListBean.getData();
        if (data != null && data.size() > 0) {
            messageListAdapter.setNewData(data);
        }
    }

}
