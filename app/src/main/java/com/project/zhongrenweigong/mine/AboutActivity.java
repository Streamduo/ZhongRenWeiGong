package com.project.zhongrenweigong.mine;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.util.APKVersionCodeUtils;
import com.project.zhongrenweigong.util.StatusBarUtils;
import com.project.zhongrenweigong.util.UtilsStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_version_code)
    TextView teVersionCode;
    @BindView(R.id.rl_weigong)
    RelativeLayout rlWeigong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_our);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtils.with(this).init();
        }
        ButterKnife.bind(this);
        teVersionCode.setText("维公科技 V" + APKVersionCodeUtils.getVerName(this));
        rlWeigong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementActivity.start(AboutActivity.this, StatementActivity.STATEMENT_TYPE_0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }
}
