package com.project.zhongrenweigong.business.manager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.adapter.UploadActivityImgListAdapter;
import com.project.zhongrenweigong.business.bean.UploadImgBean;
import com.project.zhongrenweigong.home.MessageNetManager;
import com.project.zhongrenweigong.net.HomeMainApi;
import com.project.zhongrenweigong.util.SpacingItemDecoration;
import com.project.zhongrenweigong.view.LoadingDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.log.XLog;
import me.iwf.photopicker.PhotoPicker;
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

import static me.iwf.photopicker.utils.PermissionsConstant.REQUEST_EXTERNAL_READ;

public class SendLooseActivity extends BaseActivity<SendLoosePresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_activity_name)
    EditText edActivityName;
    @BindView(R.id.te_start_time)
    TextView teStartTime;
    @BindView(R.id.te_end_time)
    TextView teEndTime;
    @BindView(R.id.ed_activity_address)
    EditText edActivityAddress;
    @BindView(R.id.ed_activity_intro)
    EditText edActivityIntro;
    @BindView(R.id.recy_img_list)
    RecyclerView recyImgList;
    @BindView(R.id.te_send)
    TextView teSend;

    private List<UploadImgBean> uploadImgBeanList = new ArrayList<>();
    private List<File> files = new ArrayList<>();
    private UploadActivityImgListAdapter uploadImgListAdapter;
    private String startTime;
    private String endTime;
    private String shopId;
    private String activityName;
    private String activityAddress;
    private String activityIntro;

    @Override
    public void initView() {
        teTitle.setText("发布活动");
        shopId = getIntent().getStringExtra("shopId");

        UploadImgBean uploadImgBean = new UploadImgBean();
        uploadImgBean.setImgUri("");
        uploadImgBean.setIndex(1);
        uploadImgBeanList.add(uploadImgBean);
        recyImgList.setLayoutManager(new GridLayoutManager(this, 3));
        recyImgList.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 20));
        uploadImgListAdapter = new UploadActivityImgListAdapter(R.layout.item_upload_activity);
        recyImgList.setAdapter(uploadImgListAdapter);
        uploadImgListAdapter.setNewData(uploadImgBeanList);
        recyImgList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_delete:
                        uploadImgListAdapter.remove(position == 0 ? position : position - 1);
                        break;
                    case R.id.img_add:
                        int size = uploadImgBeanList.size();
                        PhotoPicker.builder()
                                .setPhotoCount(10 - size)
                                .setGridColumnCount(4)
                                .setPreviewEnabled(false)
                                .setShowGif(false)
                                .start(SendLooseActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_send_loose;
    }

    @Override
    public SendLoosePresent bindPresent() {
        return new SendLoosePresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teStartTime.setOnClickListener(this);
        teEndTime.setOnClickListener(this);
        teSend.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_start_time:
                initTimePicker(0);
                break;
            case R.id.te_end_time:
                initTimePicker(1);
                break;
            case R.id.te_send:
                activityName = edActivityName.getText().toString();
                activityAddress = edActivityAddress.getText().toString();
                activityIntro = edActivityIntro.getText().toString();

                if (TextUtils.isEmpty(activityName)) {
                    showToastShort("请输入活动名称");
                    return;
                }
                if (TextUtils.isEmpty(activityAddress)) {
                    showToastShort("请输入活动地址");
                    return;
                }
                if (TextUtils.isEmpty(activityIntro)) {
                    showToastShort("请输入活动介绍");
                    return;
                }
                if (TextUtils.isEmpty(startTime)) {
                    showToastShort("请输入开始时间");
                    return;
                }
                if (TextUtils.isEmpty(endTime)) {
                    showToastShort("请输入结束时间");
                    return;
                }
                List<UploadImgBean> data = uploadImgListAdapter.getData();
                if (data != null && data.size() > 1) {
                    List<String> path = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        UploadImgBean uploadImgBean = data.get(i);
                        String imgUri = uploadImgBean.getImgUri();
                        if (imgUri != null && !imgUri.equals("")) {
                            path.add(imgUri);
                        }
                    }
                    files.clear();
                    setCompressImg(path);
                }
                break;
        }
    }

    private void setCompressImg(final List<String> path) {
        Luban.with(this).
                load(path).
                ignoreBy(1000).
                setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        files.add(file);
                        if (path.size() == files.size()) {
                            uploadPic();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        files.clear();
                        showToastShort("上传错误，请重试！");
                    }
                }).launch();
    }

    /**
     * @author fuduo
     * @time 2018/2/1  18:22
     * @describe 上传图片
     */
    private void uploadPic() {
        LoadingDialog.show(SendLooseActivity.this);

        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HomeMainApi.MESSAGE_BASE_PATH)
                .build();
        MessageNetManager service = retrofit.create(MessageNetManager.class);

        Call<BaseModel> call;
        List<MultipartBody.Part> partList = new ArrayList<>();
        if (files != null && files.size() > 0) {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                partList.add(part);
            }
            call = service.addActiveMessage("addActiveMessage", shopId, activityName,
                    activityIntro, activityAddress, startTime, endTime, partList);
        } else {
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("shopId", shopId)
                    .addFormDataPart("title", activityName)
                    .addFormDataPart("content", activityIntro)
                    .addFormDataPart("address", activityAddress)
                    .addFormDataPart("beignTime", startTime)
                    .addFormDataPart("endTime", endTime)
                    .build();
            call = service.addActiveMessage("addActiveMessage", requestBody);
        }

        // 执行
        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                LoadingDialog.dismiss(SendLooseActivity.this);
                if (response.isSuccessful()) {
                    showToastShort("发布成功");
                    finish();
                } else {
                    showToastShort("发布失败，请检查网络设置");
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                showToastShort("发布失败，请检查网络设置");
                LoadingDialog.dismiss(SendLooseActivity.this);
            }
        });
    }


    private void initTimePicker(final int type) {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (type == 0) {
                    startTime = format.format(date);
                    teStartTime.setText(startTime);
                } else {
                    endTime = format.format(date);
                    teEndTime.setText(endTime);
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(false) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .setCancelColor(getResources().getColor(R.color.app_text_33))
                .setSubmitColor(getResources().getColor(R.color.app_369EFF))
                .isAlphaGradient(true)
                .build();
        pvTime.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            ArrayList<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }

            if (photos != null && photos.size() > 0) {
                List<UploadImgBean> list = new ArrayList<>();
                for (int i = 0; i < photos.size(); i++) {
                    UploadImgBean uploadImgBean = new UploadImgBean();
                    uploadImgBean.setImgUri(photos.get(i));
                    list.add(uploadImgBean);
                }

                //移除加号
                for (int i = 0; i < uploadImgBeanList.size(); i++) {
                    UploadImgBean uploadImgBean = uploadImgBeanList.get(i);
                    if (uploadImgBean.getIndex() == 1) {
                        uploadImgBeanList.remove(i);
                    }
                }

                int size = uploadImgBeanList.size();
                int size1 = list.size();
                if (size + size1 < 9) {
                    UploadImgBean uploadImgBean = new UploadImgBean();
                    uploadImgBean.setIndex(1);
                    list.add(uploadImgBean);
                    uploadImgBeanList.addAll(list);
                    uploadImgListAdapter.setNewData(uploadImgBeanList);
                }
                if (size + size1 == 9) {
                    uploadImgBeanList.addAll(list);
                    uploadImgListAdapter.setNewData(uploadImgBeanList);
                }
            } else {
                XLog.e("未选择图片");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_READ) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setGridColumnCount(4)
                        .setPreviewEnabled(false)
                        .setShowGif(false)
                        .start(this);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }
}
