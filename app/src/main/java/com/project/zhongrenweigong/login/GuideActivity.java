package com.project.zhongrenweigong.login;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.project.zhongrenweigong.util.SharedPrefConstants.USERTYPE;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.guide_vp)
    AutoScrollViewPager guideVp;
    private ArrayList<View> views;
    private GuidePagerAdapter vpAdapter;
    private View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
        SharedPref.getInstance(this).putBoolean(USERTYPE, false);
    }

    private void initView() {
        views = new ArrayList<>();
        LayoutInflater mLi = LayoutInflater.from(this);
        view1 = mLi.inflate(R.layout.guide_view01, null);
        view2 = mLi.inflate(R.layout.guide_view02, null);
        view3 = mLi.inflate(R.layout.guide_view03, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        vpAdapter = new GuidePagerAdapter(views);
        guideVp.setAdapter(vpAdapter);
        guideVp.stopAutoScroll();

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.newIntent(GuideActivity.this).to(MainActivity.class).launch();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        guideVp.stopAutoScroll();
    }

}
