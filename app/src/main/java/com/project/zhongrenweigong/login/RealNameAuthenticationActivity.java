package com.project.zhongrenweigong.login;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.currency.ActivitySelectImage;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class RealNameAuthenticationActivity extends BaseActivity<RealNameAuthenticationPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.te_select_work)
    TextView teSelectWork;
    @BindView(R.id.te_work)
    TextView teWork;
    @BindView(R.id.img_search_work)
    ImageView imgSearchWork;
    @BindView(R.id.img_card_just)
    ImageView imgCardJust;
    @BindView(R.id.line_just)
    LinearLayout lineJust;
    @BindView(R.id.img_card_back)
    ImageView imgCardBack;
    @BindView(R.id.line_back)
    LinearLayout lineBack;
    @BindView(R.id.ed_card_name)
    EditText edCardName;
    @BindView(R.id.ed_card_num)
    EditText edCardNum;
    @BindView(R.id.te_start_record)
    TextView teStartRecord;

    @Override
    public void initView() {

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_real_name_authentication;
    }

    @Override
    public RealNameAuthenticationPresent bindPresent() {
        return new RealNameAuthenticationPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgCardJust.setOnClickListener(this);
        imgCardBack.setOnClickListener(this);
        teStartRecord.setOnClickListener(this);
        teWork.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_work:
                Router.newIntent(RealNameAuthenticationActivity.this)
                        .to(IndustrySearchActivity.class).launch();
                break;
            case R.id.img_card_just:
                ActivitySelectImage.selectImageForCard(RealNameAuthenticationActivity.this);
                break;
            case R.id.img_card_back:
                ActivitySelectImage.selectImageForCard1(RealNameAuthenticationActivity.this);
                break;
            case R.id.te_start_record:
                Router.newIntent(RealNameAuthenticationActivity.this)
                        .to(DistinguishActivity.class).launch();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == 0) {
            return;
        }
        String path = "";
        path = data.getStringExtra("path");

        if (path.equals("") && !TextUtils.isEmpty(data.getData().getAuthority())) {
            Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
            if (cursor != null) {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(index);
                    cursor.close();
                }
            }
        }
        if (requestCode == ActivitySelectImage.CHOSE_CARD) {
            final Uri uri = data.getData();
            GlideDownLoadImage.getInstance().loadImage(this, uri,
                    imgCardJust);
//            setCompressImg(0, path);
            return;
        }

        if (requestCode == ActivitySelectImage.CHOSE_CARD_1) {
            final Uri uri = data.getData();

            GlideDownLoadImage.getInstance().loadImage(this, uri,
                    imgCardBack);
//            setCompressImg(1, path);
            return;
        }
    }

}
