package com.project.zhongrenweigong.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatementActivity extends AppCompatActivity {

    public static final int STATEMENT_TYPE_0 = 0;
    public static final int STATEMENT_TYPE_1 = 1;
    public static final int STATEMENT_TYPE_2 = 2;

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.vs_guanyu_weigong)
    ViewStub vsGuanyuWeigong;
    @BindView(R.id.vs_jifen_zhinan)
    ViewStub vsJifenZhinan;
    @BindView(R.id.vs_peifu_zhinan)
    ViewStub vsPeifuZhinan;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, StatementActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        StatusBarUtils.with(this).init();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        switch (type) {
            case STATEMENT_TYPE_0:
                teTitle.setText("维公科技");
                vsGuanyuWeigong.inflate();
                break;
            case STATEMENT_TYPE_1:
                teTitle.setText("赔付指南");
                vsPeifuZhinan.inflate();
                break;
            case STATEMENT_TYPE_2:
                teTitle.setText("积分指南");
                vsJifenZhinan.inflate();
                break;
        }

        teBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
