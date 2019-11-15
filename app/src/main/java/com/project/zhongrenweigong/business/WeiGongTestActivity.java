package com.project.zhongrenweigong.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.BusinessWorkerListAdapter;
import com.project.zhongrenweigong.business.adapter.TestImgListAdapter;
import com.project.zhongrenweigong.business.adapter.TestWorkerListAdapter;
import com.project.zhongrenweigong.business.bean.EmployeesBean;
import com.project.zhongrenweigong.business.bean.PlatformGetAreaManagerBean;
import com.project.zhongrenweigong.business.bean.TestImgBean;
import com.project.zhongrenweigong.business.bean.WeiGongTestBean;
import com.project.zhongrenweigong.business.bean.WeiGongTestDataBean;
import com.project.zhongrenweigong.mine.MineHomePageActivity;
import com.project.zhongrenweigong.util.SpacingItemDecoration;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;

public class WeiGongTestActivity extends BaseActivity<WeiGongTestPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.img_tester_head)
    ImageView imgTesterHead;
    @BindView(R.id.te_tester_name)
    TextView teTesterName;
    @BindView(R.id.te_tester_id)
    TextView teTesterId;
    @BindView(R.id.te_tester_region)
    TextView teTesterRegion;
    @BindView(R.id.recy_tester_list)
    RecyclerView recyTesterList;
    @BindView(R.id.recy_test_img_list)
    RecyclerView recyTestImgList;
    @BindView(R.id.img_trademark)
    ImageView imgTrademark;
    private TestWorkerListAdapter workerListAdapter;
    private List<TestImgBean> imgBeanList = new ArrayList<>();
    private TestImgListAdapter testImgListAdapter;
    private String address;
    private String shopId;

    @Override
    public void initView() {
        teTitle.setText("维公测评");
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        shopId = intent.getStringExtra("shopId");
        recyTesterList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyTesterList.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 40));
        recyTestImgList.setLayoutManager(new LinearLayoutManager(this));
        testImgListAdapter = new TestImgListAdapter(R.layout.item_test_list);
        recyTestImgList.setAdapter(testImgListAdapter);
        workerListAdapter = new TestWorkerListAdapter(R.layout.item_people_list);
        recyTesterList.setAdapter(workerListAdapter);
        workerListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PlatformGetAreaManagerBean item = workerListAdapter.getItem(position);
                String mbId = item.mbId;
                Router.newIntent(WeiGongTestActivity.this)
                        .putString("mbId", mbId)
                        .putInt("userType",1)
                        .to(MineHomePageActivity.class)
                        .launch();
            }
        });
    }

    @Override
    public void initAfter() {
        getP().getEvaluatingInfo(address, shopId);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_wei_gong_test;
    }

    @Override
    public WeiGongTestPresent bindPresent() {
        return new WeiGongTestPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public void setData(WeiGongTestBean weiGongTestBean) {
        WeiGongTestDataBean data = weiGongTestBean.getData();
        GlideDownLoadImage.getInstance().loadCircleImage(mContext, data.platformGetAreaManager.headUrl,
                imgTesterHead);
        teTesterName.setText(data.platformGetAreaManager.mbName);
        teTesterId.setText("ID:" + data.platformGetAreaManager.mbId);
        teTesterRegion.setText("管辖区域:" + data.platformGetAreaManager.managementAddress);
        List<PlatformGetAreaManagerBean> platformGetInspector = data.platformGetInspector;
        if (platformGetInspector != null && platformGetInspector.size() > 0) {
            workerListAdapter.setNewData(platformGetInspector);
        }
        List<String> detectionImages = data.detectionImages;
        if (detectionImages != null && detectionImages.size() > 0) {
            for (int i = 0; i < detectionImages.size(); i++) {
                TestImgBean testImgBean = new TestImgBean();
                testImgBean.setTestImgName(detectionImages.get(i));
                imgBeanList.add(testImgBean);
            }
            testImgListAdapter.setNewData(imgBeanList);
        }

    }
}
