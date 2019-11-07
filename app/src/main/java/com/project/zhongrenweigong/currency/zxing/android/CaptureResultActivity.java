package com.project.zhongrenweigong.currency.zxing.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 扫描结果
 */
public class CaptureResultActivity extends BaseActivity<CaptureResultPresent> {

    @BindView(R.id.left_button)
    ImageButton leftButton;

    /**
     * @param context
     * @param qrcode:二维码
     * @param type：1     json 需要使用Base64.encodeToString,2--URL 需要URL decode
     */
    public static void start(Context context, String qrcode, int type) {
        Intent starter = new Intent(context, CaptureResultActivity.class);
        starter.putExtra("qrcode", qrcode);
        starter.putExtra("type", type);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_capture_result;
    }

    @Override
    public CaptureResultPresent bindPresent() {
        return new CaptureResultPresent();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }
}
