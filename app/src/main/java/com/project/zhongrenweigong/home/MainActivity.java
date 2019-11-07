package com.project.zhongrenweigong.home;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

public class MainActivity extends BaseActivity<MainPresent> implements CompoundButton.OnCheckedChangeListener {

    public static final String SAVE_KEY_TAB_INDEX = "tab_index";
    long exitTime;

    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    @BindView(R.id.home_main)
    RadioButton homeMain;
    @BindView(R.id.home_mine)
    RadioButton homeMine;
    @BindView(R.id.home_square)
    TextView homeSquare;
    @BindView(R.id.home_msg)
    RadioButton homeMsg;
    private FactoryFragment factoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        factoryFragment = new FactoryFragment(savedInstanceState, getSupportFragmentManager());
        selectTab(savedInstanceState);
    }

    @Override
    public void initView() {
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
        homeSquare.setOnClickListener(this);
        homeMsg.setOnCheckedChangeListener(this);
        homeMain.setOnCheckedChangeListener(this);
        homeMine.setOnCheckedChangeListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.home_square:
                factoryFragment.changeToFragment(2);
                homeSquare.setTextColor(getResources().getColor(R.color.app_369EFF));
                homeMsg.setChecked(false);
                homeMain.setChecked(false);
                homeMine.setChecked(false);
                break;
        }
    }

    private void selectTab(Bundle savedInstanceState) {
        int index = 0;

        if (savedInstanceState == null) {
            index = 2;
            homeSquare.performClick();
        } else {
            index = savedInstanceState.getInt(SAVE_KEY_TAB_INDEX, 0);
        }
        switch (index) {
            case -1:
            case 0:
                homeMsg.setChecked(true);
                break;
            case 1:
                homeMain.setChecked(true);
                break;
            case 3:
                homeMine.setChecked(true);
                break;
        }
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

            case R.id.home_msg:
                homeSquare.setTextColor(getResources().getColor(R.color.app_7d7d7d));
                factoryFragment.changeToFragment(0);
                break;
            case R.id.home_main:
                homeSquare.setTextColor(getResources().getColor(R.color.app_7d7d7d));
                factoryFragment.changeToFragment(1);
                break;
            case R.id.home_mine:
                homeSquare.setTextColor(getResources().getColor(R.color.app_7d7d7d));
                factoryFragment.changeToFragment(3);

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshMineEvent refreshMineEvent) {
        homeMine.setChecked(true);
    }

}
