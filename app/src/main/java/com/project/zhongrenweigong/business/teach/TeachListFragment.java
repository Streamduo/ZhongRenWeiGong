package com.project.zhongrenweigong.business.teach;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.BusinessListActivity;
import com.project.zhongrenweigong.mine.MineShopListPresent;
import com.project.zhongrenweigong.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class TeachListFragment extends BaseFragment<TeachListFrgementPresent> {
    Unbinder unbinder;
    @BindView(R.id.recy_shop_list)
    RecyclerView recyTeachList;
    private int index;

    public static TeachListFragment getInstance(int index) {//String shopId
        TeachListFragment homePageXinXiFragment = new TeachListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        homePageXinXiFragment.setArguments(bundle);
        return homePageXinXiFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        index = bundle.getInt("index", 0);
        recyTeachList.setLayoutManager(new LinearLayoutManager(getContext()));
//        workerListAdapter = new BusinessWorkerListAdapter(R.layout.item_people_list);
//        recyShopList.setAdapter(workerListAdapter);

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_shop_list;
    }

    @Override
    public TeachListFrgementPresent bindPresent() {
        return new TeachListFrgementPresent();
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
