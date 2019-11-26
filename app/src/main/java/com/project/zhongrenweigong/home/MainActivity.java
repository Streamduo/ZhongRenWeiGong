package com.project.zhongrenweigong.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.currency.Constans;
import com.project.zhongrenweigong.currency.event.RefreshMineEvent;
import com.project.zhongrenweigong.login.LoginActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

public class MainActivity extends BaseActivity<MainPresent> implements CompoundButton.OnCheckedChangeListener {

    public static final String SAVE_KEY_TAB_INDEX = "tab_index";
    private final int SDK_PERMISSION_REQUEST = 127;
    long exitTime;

    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    @BindView(R.id.home_main)
    RadioButton homeMain;
    @BindView(R.id.home_mine)
    RadioButton homeMine;
    @BindView(R.id.home_msg)
    RadioButton homeMsg;
    @BindView(R.id.home_industry)
    RadioButton homeIndustry;
    @BindView(R.id.msg_size)
    TextView msgSize;
    @BindView(R.id.home_center)
    RadioButton homeCenter;
    private FactoryFragment factoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        factoryFragment = new FactoryFragment(savedInstanceState, getSupportFragmentManager());
        selectTab(savedInstanceState);
        getPersimmions();
    }

    @Override
    public void initView() {
        setFull(false);
        homeMine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean isTourist = SharedPref.getInstance(MainActivity.this).getBoolean(Constans.ISTOURIST, true);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (isTourist) {
                        Router.newIntent(MainActivity.this).to(LoginActivity.class).launch();
                        return true;
                    }
                }
                return false;
            }
        });
        homeMsg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean isTourist = SharedPref.getInstance(MainActivity.this).getBoolean(Constans.ISTOURIST, true);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (isTourist) {
                        Router.newIntent(MainActivity.this).to(LoginActivity.class).launch();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresent bindPresent() {
        return new MainPresent();
    }

    @Override
    public void setListener() {
        homeIndustry.setOnCheckedChangeListener(this);
        homeMsg.setOnCheckedChangeListener(this);
        homeMain.setOnCheckedChangeListener(this);
        homeMine.setOnCheckedChangeListener(this);
        homeCenter.setOnCheckedChangeListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {

        }
    }

    private void selectTab(Bundle savedInstanceState) {
        int index = 0;

        if (savedInstanceState == null) {
            index = 0;
        } else {
            index = savedInstanceState.getInt(SAVE_KEY_TAB_INDEX, 0);
        }
        switch (index) {
            case -1:
            case 0:
                homeMain.setChecked(true);
                break;
            case 1:
                homeIndustry.setChecked(true);
                break;
            case 2:
                homeMsg.setChecked(true);
                break;
            case 3:
                homeMine.setChecked(true);
                break;
        }
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        factoryFragment = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_KEY_TAB_INDEX, factoryFragment.getCurrentIndex());
    }

    @Override
    public void onBackPressed() {
        if (doubleClickToExit()) {
            super.onBackPressed();
            exit();
        }
    }

    private boolean doubleClickToExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastManager.showShort(this, "再按一次退出 众仁为公");
            exitTime = System.currentTimeMillis();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) {
            return;
        }
        switch (compoundButton.getId()) {
            case R.id.home_main:
                factoryFragment.changeToFragment(0);
                break;
            case R.id.home_industry:
                factoryFragment.changeToFragment(1);
                break;
            case R.id.home_msg:
                factoryFragment.changeToFragment(2);
                break;
            case R.id.home_mine:
                factoryFragment.changeToFragment(3);
                break;
            case R.id.home_center:

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshMineEvent refreshMineEvent) {
        homeMine.setChecked(true);
    }
}
