package com.project.zhongrenweigong.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.MainActivity;

import cn.droidlover.xdroidmvp.router.Router;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findViewById(R.id.text_yindao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.newIntent(GuideActivity.this).to(MainActivity.class).launch();
                finish();
            }
        });
    }
}
