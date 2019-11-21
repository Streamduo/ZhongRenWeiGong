package com.project.zhongrenweigong.business;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.adapter.UploadImgListAdapter;
import com.project.zhongrenweigong.business.bean.UploadImgBean;
import com.project.zhongrenweigong.home.MessageNetManager;
import com.project.zhongrenweigong.login.LoginNetManager;
import com.project.zhongrenweigong.login.RegisterActivity;
import com.project.zhongrenweigong.net.LoginApi;
import com.project.zhongrenweigong.util.SpacingItemDecoration;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.view.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class UploadVoucherActivity extends BaseActivity<UploadVoucherPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.ed_voucher_intro)
    EditText edVoucherIntro;
    @BindView(R.id.recy_img_list)
    RecyclerView recyImgList;
    @BindView(R.id.te_upload)
    TextView teUpload;
    private UploadImgListAdapter uploadImgListAdapter;
    private List<UploadImgBean> uploadImgBeanList = new ArrayList<>();
    private String intro;
    private String shopId;

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("上传凭证");
        shopId = getIntent().getStringExtra("shopId");
        UploadImgBean uploadImgBean = new UploadImgBean();
        uploadImgBean.setImgUri("");
        uploadImgBean.setIndex(1);
        uploadImgBeanList.add(uploadImgBean);
        recyImgList.setLayoutManager(new GridLayoutManager(this, 3));
        recyImgList.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 20));
        uploadImgListAdapter = new UploadImgListAdapter(R.layout.item_upload_voucher);
        recyImgList.setAdapter(uploadImgListAdapter);
        uploadImgListAdapter.setNewData(uploadImgBeanList);
        recyImgList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_delete:
                        uploadImgListAdapter.remove(position - 1);
                        break;
                    case R.id.line_add:
                        int size = uploadImgBeanList.size();
                        PhotoPicker.builder()
                                .setPhotoCount(10 - size)
                                .setGridColumnCount(4)
                                .setPreviewEnabled(false)
                                .setShowGif(false)
                                .start(UploadVoucherActivity.this);
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
        return R.layout.activity_upload_voucher;
    }

    @Override
    public UploadVoucherPresent bindPresent() {
        return new UploadVoucherPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teUpload.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_upload:
                intro = edVoucherIntro.getText().toString();
                List<UploadImgBean> data = uploadImgListAdapter.getData();
                List<String> path = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    UploadImgBean uploadImgBean = data.get(i);
                    String imgUri = uploadImgBean.getImgUri();
                    if (imgUri != null && !imgUri.equals("")) {
                        path.add(imgUri);
                    }
                }
                setCompressImg(path);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * @author fuduo
     * @time 2018/2/1  18:22
     * @describe 上传图片
     */
    private void uploadPic(List<File> files) {
        LoadingDialog.show(UploadVoucherActivity.this);
        List<MultipartBody.Part> partList = new ArrayList<>();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partList.add(part);
        }

        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(LoginApi.BASE_PATH)
                .build();
        MessageNetManager service = retrofit.create(MessageNetManager.class);
        Call<BaseModel> call = service.uploadCardImage("uploadTextImages",intro,shopId, partList);
        // 执行
        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    showToastShort(msg);
                    finish();
                }else {
                    showToastShort("请检查网络设置");
                }
                LoadingDialog.dismiss(UploadVoucherActivity.this);
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                showToastShort("请检查网络设置");
                LoadingDialog.dismiss(UploadVoucherActivity.this);
            }
        });
    }

    private void setCompressImg(List<String> path) {
        Luban.with(this).
                load(path).
                ignoreBy(1000).
                setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        setFileList(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToastShort("上传错误，请重试！");
                    }
                }).launch();
    }

    private void setFileList(File file) {
        List<File> files = new ArrayList<>();
        files.add(file);
        uploadPic(files);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
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
