package com.project.zhongrenweigong.business.manager;


import android.app.Dialog;
import android.content.Intent;
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
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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

    @Override
    public void initView() {
        UtilsStyle.statusBarLightMode(this);
        teTitle.setText("员工绑定");
        teRightTitle.setText("新增员工");
    }

    @Override
    public void initAfter() {

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
                if (workerId.length() != 8) {
                    showToastShort("员工ID格式错误");
                    return;
                }
                if (TextUtils.isEmpty(workerName)) {
                    showToastShort("请输入员工姓名");
                    return;
                }
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

}
