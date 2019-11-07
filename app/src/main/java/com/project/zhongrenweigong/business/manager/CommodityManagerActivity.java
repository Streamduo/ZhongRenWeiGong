package com.project.zhongrenweigong.business.manager;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.CommodityListAdapter;
import com.project.zhongrenweigong.business.bean.CommodityDataBean;
import com.project.zhongrenweigong.business.bean.CommodityListBean;
import com.project.zhongrenweigong.business.bean.DataBean;
import com.project.zhongrenweigong.currency.event.RefreshCommodityEvent;
import com.project.zhongrenweigong.currency.event.RefreshHomeEvent;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.UtilsStyle;
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
import cn.droidlover.xdroidmvp.router.Router;

public class CommodityManagerActivity extends BaseActivity<CommodityManagerPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_right_title)
    TextView teRightTitle;
    @BindView(R.id.recy_commodity_list)
    RecyclerView recyCommodityList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private int currentPage = 1;
    private CommodityListAdapter commodityListAdapter;
    private LoginMsg userAccent;
    private Dialog deleteCommodityDialog;

    @Override
    public void initView() {
        teTitle.setText("商品编辑");
        teRightTitle.setText("新增菜品");
        recyCommodityList.setLayoutManager(new LinearLayoutManager(this));
        commodityListAdapter = new CommodityListAdapter(R.layout.item_commodity_list);
        recyCommodityList.setAdapter(commodityListAdapter);
        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                getP().findAllGoods(currentPage, userAccent.mbId);
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                getP().findAllGoods(currentPage, userAccent.mbId);
            }
        });
        recyCommodityList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CommodityDataBean item = commodityListAdapter.getItem(position);
                if (view.getId() == R.id.te_commodity_delete) {
                    showDeleteCommodityDialog(item.goodsId, position);
                } else if (view.getId() == R.id.te_commodity_edit) {
                    Router.newIntent(CommodityManagerActivity.this)
                            .putSerializable("CommodityDataBean", item)
                            .to(AddCommodityActivity.class)
                            .launch();
                }
            }
        });
    }

    @Override
    public void initAfter() {
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
        getP().findAllGoods(currentPage, userAccent.mbId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_commodity_manager;
    }

    @Override
    public CommodityManagerPresent bindPresent() {
        return new CommodityManagerPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teRightTitle.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_right_title:
                Router.newIntent(CommodityManagerActivity.this).to(AddCommodityActivity.class).launch();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    public void setData(CommodityListBean commodityListBean) {
        int pageSize = commodityListBean.pageSize;
        List<CommodityDataBean> data = commodityListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                commodityListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                smRefresh.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                commodityListAdapter.addData(data);
            }
        } else {
            getDataError();
        }
        if (currentPage == pageSize) {
            smRefresh.setEnableLoadMore(false);
        }
    }

    public void getDataError() {
        if (currentPage == 1) {
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshCommodityEvent refreshCommodityEvent) {
        currentPage = 1;
        getP().findAllGoods(currentPage, userAccent.mbId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void deleteItem(int position) {
        commodityListAdapter.remove(position);
    }

    private void showDeleteCommodityDialog(final String goodsId, final int position) {
        deleteCommodityDialog = new Dialog(this, R.style.dialog_bottom_full);
        deleteCommodityDialog.setCanceledOnTouchOutside(true);
        deleteCommodityDialog.setCancelable(true);
        Window window = deleteCommodityDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(this, R.layout.dialog_layout_two_line, null);
        TextView teLineOne = (TextView) view.findViewById(R.id.te_line_one);
        TextView teLineTwo = (TextView) view.findViewById(R.id.te_line_two);
        TextView teOk = (TextView) view.findViewById(R.id.te_ok);
        TextView teCancel = (TextView) view.findViewById(R.id.te_cancel);
        teLineOne.setText("删除该商品");
        teLineTwo.setText("确定删除该商品？");

        teOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getP().deleteGoods(goodsId, position);
                deleteCommodityDialog.dismiss();
            }
        });

        teCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                deleteCommodityDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        deleteCommodityDialog.show();
    }

}
