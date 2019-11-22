package com.project.zhongrenweigong.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.base.PressionListener;
import com.project.zhongrenweigong.currency.ActivitySelectImage;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.CheckInputUtil;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;
import com.project.zhongrenweigong.view.LoadingDialog;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.droidlover.xdroidbase.kit.ToastManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class RegisterActivity extends BaseActivity<RegisterPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.ed_phone_num)
    EditText edPhoneNum;
    @BindView(R.id.te_send_ems)
    TextView teSendEms;
    @BindView(R.id.ed_ems_num)
    EditText edEmsNum;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_queren_password)
    EditText edQuerenPassword;
    @BindView(R.id.te_next_shiming)
    TextView teNext;
    @BindView(R.id.vs_shiming)
    ViewStub vsShiming;
    @BindView(R.id.line_register)
    LinearLayout lineRegister;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.vs_register_ok)
    ViewStub vsRegisterOk;
    private View shiMingView;
    private View xinXiEditView;
    private ImageView imgCardJust;
    private ImageView imgCardBack;
    private String peopleType = "1";
    private File justFile;
    private File backFile;
    private String cardNum;
    private String phoneNum;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        teTitle.setText("注册账号");
        requestPermission();
    }

    @Override
    public void initAfter() {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterPresent bindPresent() {
        return new RegisterPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teSendEms.setOnClickListener(this);
        teNext.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_send_ems:
                String phone = edPhoneNum.getText().toString();
                phoneNum = phone.replaceAll("\\D", "");
                if (TextUtils.isEmpty(phoneNum)) {
                    showToastShort(getString(R.string.phonenumber_null));
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(phoneNum)) {
                    showToastShort(getResources().getString(R.string.phonenumber_error));
                    return;
                }
                getPhoneCode();//成功之后开始倒计时

                break;
            case R.id.te_next_shiming:
                String phoneNumText = edPhoneNum.getText().toString();
                String phoneNum = phoneNumText.replaceAll("\\D", "");
                String textEmsNum = edEmsNum.getText().toString();
                String passwordText = edPassword.getText().toString();
                String querenPasswordText = edQuerenPassword.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    showToastShort(getString(R.string.phonenumber_null));
                    return;
                }
                if (!CheckInputUtil.checkPhoneForLogin(phoneNum)) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.phonenumber_error));
                    return;
                }

                if (textEmsNum == null || textEmsNum.equals("")) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.code_null));
                    return;
                }
                if (textEmsNum.length() < 6) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.code_error));
                    return;
                }

                if (passwordText == null || passwordText.equals("")) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.password_null));
                    return;
                }
                if (passwordText.length() < 6) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.password_cn_6));
                    return;
                }

                if (!CheckInputUtil.checkPassword(passwordText)) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.password_error));
                }

                if (querenPasswordText == null || querenPasswordText.equals("")) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.queren_password_null));
                    return;
                }

                if (!querenPasswordText.equals(passwordText)) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.same_password_error));
                    return;
                }
                initShiMing(phoneNum, textEmsNum, passwordText);
                break;
        }
    }

    private void getPhoneCode() {
        getP().getVerification(phoneNum);
    }

    public void sendEmsFail(){
        teSendEms.setEnabled(true);
    }

    public void sendEmsSuccess(){
        teSendEms.setEnabled(true);
        initCountDownTimer();
    }

    private void initCountDownTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                teSendEms.setEnabled(false);
                teSendEms.setText(l / 1000 + "s");
            }

            @Override
            public void onFinish() {
                teSendEms.setText("再次发送");
                teSendEms.setEnabled(true);
            }
        }.start();
    }

    private void cancleTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void initShiMing(final String phoneNum, final String textEmsNum, final String passwordText) {
        teTitle.setText("实名/人脸认证");
        imgTop.setBackgroundResource(R.mipmap.geren_people_face);
        lineRegister.setVisibility(View.GONE);

        shiMingView = vsShiming.inflate();
        RelativeLayout rlShibie = shiMingView.findViewById(R.id.rl_shibie);
        imgCardJust = shiMingView.findViewById(R.id.img_card_just);
        imgCardBack = shiMingView.findViewById(R.id.img_card_back);
        final EditText edUserName = shiMingView.findViewById(R.id.ed_user_name);
        final EditText edCardNum = shiMingView.findViewById(R.id.ed_card_num);
        final EditText edCardDate = shiMingView.findViewById(R.id.ed_card_date);

        final CheckBox ckMan = shiMingView.findViewById(R.id.ck_man);
        final CheckBox ckWoman = shiMingView.findViewById(R.id.ck_woman);

        TextView teNextXinxi = (TextView) shiMingView.findViewById(R.id.te_next_xinxi);

        teNextXinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textUsername = edUserName.getText().toString();
                String textCardNum = edCardNum.getText().toString();
                String cardNum = textCardNum.replaceAll("\\D", "");
                String textCardDate = edCardDate.getText().toString();

                if (textUsername == null || textUsername.equals("")) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.username_null));
                    return;
                }
                if (cardNum == null || cardNum.equals("")) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.usercard_num_null));
                    return;
                }
                if (!CheckInputUtil.IDCardValidate(cardNum)) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.usercard_num_error));
                    return;
                }
                if (textCardDate == null || textCardDate.equals("")) {
                    ToastManager.showShort(RegisterActivity.this, getString(R.string.usercard_date_null));
                    return;
                }
                getP().register(textCardNum, textCardDate, textUsername, passwordText, phoneNum, peopleType, textEmsNum);
            }
        });

        ckMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckWoman.setChecked(false);
                    peopleType = "1";
                }
            }
        });
        ckWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ckMan.setChecked(false);
                    peopleType = "0";
                }
            }
        });
        imgCardJust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textCardNum = edCardNum.getText().toString();
                cardNum = textCardNum.replaceAll("\\D", "");
                if (cardNum == null || cardNum.equals("")) {
                    ToastManager.showShort(RegisterActivity.this, "请先填写身份证号");
                    return;
                }
                ActivitySelectImage.selectImageForCard(RegisterActivity.this);
            }
        });
        imgCardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textCardNum = edCardNum.getText().toString();
                cardNum = textCardNum.replaceAll("\\D", "");
                if (cardNum == null || cardNum.equals("")) {
                    ToastManager.showShort(RegisterActivity.this, "请先填写身份证号");
                    return;
                }
                ActivitySelectImage.selectImageForCard1(RegisterActivity.this);
            }
        });
    }

    public void initRegisterOk() {
        teTitle.setText("注册成功");
        imgTop.setBackgroundResource(R.mipmap.register_ok);
        shiMingView.setVisibility(View.GONE);
        vsRegisterOk.inflate();
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
            setCompressImg(0, path);
            return;
        }

        if (requestCode == ActivitySelectImage.CHOSE_CARD_1) {
            final Uri uri = data.getData();

            GlideDownLoadImage.getInstance().loadImage(this, uri,
                    imgCardBack);
            setCompressImg(1, path);
            return;
        }
    }

    /**
     * @author fuduo
     * @time 2018/2/1  18:22
     * @describe 上传图片
     */
    private void uploadPic() {
        LoadingDialog.show(RegisterActivity.this);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(LoginApi.BASE_PATH)
                .build();
        LoginNetManager service = retrofit.create(LoginNetManager.class);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("idcard", cardNum)
                .addFormDataPart("positive", justFile.getName(), RequestBody.create(MediaType.parse("file"), justFile))
                .addFormDataPart("negative", backFile.getName(), RequestBody.create(MediaType.parse("file"), backFile))
                .build();

        //如果和rxjava1.x , call就换成 Observable
        Call<BaseModel> call = service.uploadCardImage("authMemberNameApi", requestBody);
        // 执行
        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                LoadingDialog.dismiss(RegisterActivity.this);
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    showToastShort(msg);
                }else {
                    showToastShort("请检查网络设置");
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                showToastShort("请检查网络设置");
                LoadingDialog.dismiss(RegisterActivity.this);
            }
        });
    }

    private void setCompressImg(final int index, String path) {
        Luban.with(this).
                load(path).
                ignoreBy(1000).
                setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (index == 0) {
                            justFile = file;
                        } else {
                            backFile = file;
                        }
                        if (justFile != null && backFile != null) {
                            uploadPic();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastShort("上传错误，请重试！");
                    }
                }).launch();
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        requestRunTimePression(RegisterActivity.this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PressionListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onFailure(List<String> failurePression) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("提示")
                        .setMessage("请您去设置中授予拍照和存储的权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                builder.setCancelable(false);
                builder.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancleTimer();
    }
}
