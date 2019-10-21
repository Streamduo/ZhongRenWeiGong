package com.project.zhongrenweigong.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.util.StatusBarUtils;

import cn.droidlover.xdroidmvp.router.Router;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtils.with(this).init();
        TextView textStart = findViewById(R.id.text_start);

        textStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.newIntent(SplashActivity.this).to(GuideActivity.class).launch();
                finish();
            }
        });
    }
}
