package com.project.zhongrenweigong.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.MainActivity;
import com.project.zhongrenweigong.util.StatusBarUtils;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.project.zhongrenweigong.util.SharedPrefConstants.USERTYPE;

public class SplashActivity extends AppCompatActivity {

    public static final int MAY_BE_READY = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MAY_BE_READY:
                    if (aBoolean) {
                        goToGuide();
                    } else {
                        goToHome();
                    }
            }
        }
    };

    private boolean aBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!this.isTaskRoot()) {
//            Intent intent = getIntent();
//            if (intent != null) {
//                String action = intent.getAction();
//                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
//                    finish();
//                }
//            }
//        }
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_splash);
        aBoolean = SharedPref.getInstance(this).getBoolean(USERTYPE, true);
        setUpSlogan();
    }

    private void setUpSlogan() {
        handler.postDelayed(runnable, 2 * 1000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(MAY_BE_READY);
        }
    };

    private void goToHome() {
        Router.newIntent(SplashActivity.this).to(MainActivity.class).launch();
        finish();
    }

    private void goToGuide() {
        Router.newIntent(SplashActivity.this).to(GuideActivity.class).launch();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
