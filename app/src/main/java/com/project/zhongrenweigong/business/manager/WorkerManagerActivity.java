package com.project.zhongrenweigong.business.manager;


import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.business.adapter.WorkerListAdapter;
import com.project.zhongrenweigong.business.bean.CommodityDataBean;
import com.project.zhongrenweigong.business.bean.CommodityListBean;
import com.project.zhongrenweigong.business.bean.WorkerDataBean;
import com.project.zhongrenweigong.business.bean.WorkerListBean;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.QueShengManager;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkerManagerActivity extends BaseActivity<WorkerManagerPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_right_title)
    TextView teRightTitle;
    @BindView(R.id.recy_business_list)
    RecyclerView recyBusinessList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;
    private Dialog addWorkerDialog;
    private WorkerListAdapter workerListAdapter;
    private int currentPage = 1;
    private LoginMsg userAccent;
    private Dialog deleteWorkerDialog;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("员工绑定");
        teRightTitle.setText("新增员工");
        recyBusinessList.setLayoutManager(new LinearLayoutManager(this));
        workerListAdapter = new WorkerListAdapter(R.layout.item_worker_list);
        recyBusinessList.setAdapter(workerListAdapter);
        recyBusinessList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WorkerDataBean item = workerListAdapter.getItem(position);
                if (view.getId() == R.id.te_worker_delete) {
                    showDeleteWorkerDialog(item.employeesId, position);
                }
            }
        });

        smRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getFirstPageData();
            }
        });
        smRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                currentPage++;
                getData();
            }
        });
    }

    @Override
    public void initAfter() {
        userAccent = AcacheUtils.getInstance(this).getUserAccent();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_worker_manager;
    }

    @Override
    public WorkerManagerPresent bindPresent() {
        return new WorkerManagerPresent();
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
                showAddWorkerDialog();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public void getData() {
        getP().findEmployees(currentPage, userAccent.mbId);
    }

    public void getFirstPageData() {
        currentPage = 1;
        getP().findEmployees(currentPage, userAccent.mbId);
    }

    public void setData(WorkerListBean workerListBean) {
        int pageSize = workerListBean.pageSize;
        List<WorkerDataBean> data = workerListBean.getData();
        if (data != null && data.size() > 0) {
            if (currentPage == 1) {
                workerListAdapter.setNewData(data);
                smRefresh.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            } else {
                smRefresh.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                workerListAdapter.addData(data);
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
            QueShengManager.setEmptyView(QueShengManager.QUESHENG_TYPE_1, workerListAdapter, smRefresh);
            smRefresh.finishRefresh(false);
        } else {
            smRefresh.finishLoadMore(false);
        }
    }

    private void showDeleteWorkerDialog(final String employeesId, final int position) {
        deleteWorkerDialog = new Dialog(this, R.style.dialog_bottom_full);
        deleteWorkerDialog.setCanceledOnTouchOutside(true);
        deleteWorkerDialog.setCancelable(true);
        Window window = deleteWorkerDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(this, R.layout.dialog_layout_two_line, null);
        TextView teLineOne = (TextView) view.findViewById(R.id.te_line_one);
        TextView teLineTwo = (TextView) view.findViewById(R.id.te_line_two);
        TextView teOk = (TextView) view.findViewById(R.id.te_ok);
        TextView teCancel = (TextView) view.findViewById(R.id.te_cancel);
        teLineOne.setText("删除该员工");
        teLineTwo.setText("确定删除该员工？");

        teOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getP().deleteWorker(userAccent.mbId, employeesId, position);
                deleteWorkerDialog.dismiss();
            }
        });

        teCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                deleteWorkerDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        deleteWorkerDialog.show();
    }

    private void showAddWorkerDialog() {
        addWorkerDialog = new Dialog(this, R.style.dialog_bottom_full);
        addWorkerDialog.setCanceledOnTouchOutside(true);
        addWorkerDialog.setCancelable(true);
        Window window = addWorkerDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(this, R.layout.dialog_layout_add_worker, null);
        final EditText edWorkerId = (EditText) view.findViewById(R.id.ed_worker_id);
        final EditText edWorkerName = (EditText) view.findViewById(R.id.ed_worker_name);
        TextView teOk = (TextView) view.findViewById(R.id.te_ok);
        TextView teCancel = (TextView) view.findViewById(R.id.te_cancel);

        teOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String workerId = edWorkerId.getText().toString();
                String workerName = edWorkerName.getText().toString();
                if (TextUtils.isEmpty(workerId)) {
                    showToastShort("请输入员工ID");
                    return;
                }
//                if (workerId.length() != 8) {
//                    showToastShort("员工ID格式错误");
//                    return;
//                }
                if (TextUtils.isEmpty(workerName)) {
                    showToastShort("请输入员工姓名");
                    return;
                }
                LoginMsg userAccent = AcacheUtils.getInstance(WorkerManagerActivity.this).getUserAccent();
                getP().addWorker(userAccent.mbId, workerId, workerName);
                addWorkerDialog.dismiss();
            }
        });

        teCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                addWorkerDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        addWorkerDialog.show();
    }

    public void deleteItem(int position) {
        workerListAdapter.remove(position);
    }
}
