package com.project.zhongrenweigong.business;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.business.adapter.BusinessWorkerListAdapter;
import com.project.zhongrenweigong.business.bean.BusinessHomeDataBean;
import com.project.zhongrenweigong.business.bean.BusinessHomePageBean;
import com.project.zhongrenweigong.business.bean.EmployeesBean;
import com.project.zhongrenweigong.business.bean.EmployeesListBean;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：Fuduo on 2019/10/21 15:47
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class HomePageXinXiFragment extends BaseFragment<HomePageXinXiPresent> {
    @BindView(R.id.img_faren)
    ImageView imgFaren;
    @BindView(R.id.recy_worker_list)
    RecyclerView recyWorkerList;
    @BindView(R.id.te_shop_address)
    TextView teShopAddress;
    @BindView(R.id.recy_jc_people_list)
    RecyclerView recyJcPeopleList;
    Unbinder unbinder;
    @BindView(R.id.recy_zizhi)
    RecyclerView recyZizhi;
    @BindView(R.id.recy_testing)
    RecyclerView recyTesting;
    private BusinessWorkerListAdapter workerListAdapter;
    private String shopId;

    public static HomePageXinXiFragment getInstance(String shopId) {
        HomePageXinXiFragment homePageXinXiFragment = new HomePageXinXiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("shopId",shopId);
        homePageXinXiFragment.setArguments(bundle);
        return homePageXinXiFragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        shopId = bundle.getString("shopId", this.shopId);
        recyWorkerList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        workerListAdapter = new BusinessWorkerListAdapter(R.layout.item_people_list);
        recyWorkerList.setAdapter(workerListAdapter);
    }

    @Override
    public void initAfter() {
        getP().getMerchantHead("444",shopId);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_homepage_xinxi;
    }

    @Override
    public HomePageXinXiPresent bindPresent() {
        return new HomePageXinXiPresent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    public void setData(BusinessHomePageBean businessHomePageBean) {
        ((BusinessHomePageActivity) Objects.requireNonNull(getActivity())).setHeadData(businessHomePageBean.getData());

        BusinessHomeDataBean businessHomeDataBean = businessHomePageBean.getData();
        GlideDownLoadImage.getInstance().loadCircleImage(getActivity(),
                businessHomeDataBean.legalPerson.headUrl, imgFaren);

        List<EmployeesBean> employees = businessHomeDataBean.employees.employees;
        if (employees != null && employees.size() > 0) {
            workerListAdapter.setNewData(employees);
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
}
