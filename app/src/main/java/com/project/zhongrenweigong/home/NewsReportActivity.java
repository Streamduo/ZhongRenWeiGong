package com.project.zhongrenweigong.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.base.PressionListener;
import com.project.zhongrenweigong.business.UploadVoucherActivity;
import com.project.zhongrenweigong.business.adapter.UploadImgListAdapter;
import com.project.zhongrenweigong.business.bean.UploadImgBean;
import com.project.zhongrenweigong.currency.ActivitySelectImage;
import com.project.zhongrenweigong.home.adapter.ReportListAdapter;
import com.project.zhongrenweigong.home.bean.ReportBean;
import com.project.zhongrenweigong.net.HomeMainApi;
import com.project.zhongrenweigong.util.SpacingItemDecoration;
import com.project.zhongrenweigong.util.UtilsStyle;
import com.project.zhongrenweigong.util.glide.GlideDownLoadImage;
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

public class NewsReportActivity extends BaseActivity<NewsReportPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.recy_report_list)
    RecyclerView recyReportList;
    @BindView(R.id.ed_report_intro)
    EditText edReportIntro;
    @BindView(R.id.te_send)
    TextView teSend;
    @BindView(R.id.recy_img_list)
    RecyclerView recyImgList;
    private List<ReportBean> reportBeanList = new ArrayList<>();
    private ReportListAdapter reportListAdapter;
    private String reportIntro;
    private String selectedStr;
    private String journalismId;

    private UploadImgListAdapter uploadImgListAdapter;
    private List<UploadImgBean> uploadImgBeanList = new ArrayList<>();

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setFull(false);
        }
        teTitle.setText("举报");
        journalismId = getIntent().getStringExtra("journalismId");
        recyReportList.setLayoutManager(new GridLayoutManager(this, 2));
        for (int i = 0; i < 7; i++) {
            ReportBean reportBean = new ReportBean();
            reportBean.setId(i);
            switch (i) {
                case 0:
                    reportBean.setReportIntro("标题夸张");
                    break;
                case 1:
                    reportBean.setReportIntro("低俗色情");
                    break;
                case 2:
                    reportBean.setReportIntro("错别字多");
                    break;
                case 3:
                    reportBean.setReportIntro("旧闻重复");
                    break;
                case 4:
                    reportBean.setReportIntro("广告软文");
                    break;
                case 5:
                    reportBean.setReportIntro("内容不实");
                    break;
                case 6:
                    reportBean.setReportIntro("其他问题，我要吐槽");
                    break;
            }
            reportBeanList.add(reportBean);
        }
        reportListAdapter = new ReportListAdapter(R.layout.item_report_list);
        recyReportList.setAdapter(reportListAdapter);
        reportListAdapter.setNewData(reportBeanList);

        reportListAdapter.setOnSelectedClickListener(new ReportListAdapter.OnSelectedClickListener() {
            @Override
            public void onClickSelect() {
                teSend.setBackgroundResource(R.drawable.bg_bac_339aff_10);
            }

            @Override
            public void onClickNoSelect() {
                teSend.setBackgroundResource(R.drawable.bg_bac_c6ddf3_10);
            }
        });

        UploadImgBean uploadImgBean = new UploadImgBean();
        uploadImgBean.setImgUri("");
        uploadImgBean.setIndex(1);
        uploadImgBeanList.add(uploadImgBean);
        recyImgList.setLayoutManager(new GridLayoutManager(this, 3));
        recyImgList.addItemDecoration(new SpacingItemDecoration(LinearLayoutManager.HORIZONTAL, 20));
        uploadImgListAdapter = new UploadImgListAdapter(R.layout.item_upload_voucher);
        uploadImgListAdapter.setShowDelete(false);
        recyImgList.setAdapter(uploadImgListAdapter);
        uploadImgListAdapter.setNewData(uploadImgBeanList);
        recyImgList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.line_add:
                        int size = uploadImgBeanList.size();
                        PhotoPicker.builder()
                                .setPhotoCount(10 - size)
                                .setGridColumnCount(4)
                                .setPreviewEnabled(false)
                                .setShowGif(false)
                                .start(NewsReportActivity.this);
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
        return R.layout.activity_news_report;
    }

    @Override
    public NewsReportPresent bindPresent() {
        return new NewsReportPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        teSend.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.te_send:
                reportIntro = edReportIntro.getText().toString();
                selectedStr = getSelectedStr();
                if (selectedStr.equals("")){
                    showToastShort("请选择举报原因");
                    return;
                }

                List<UploadImgBean> data = uploadImgListAdapter.getData();
                if (data != null && data.size() > 0) {
                    List<String> path = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        UploadImgBean uploadImgBean = data.get(i);
                        String imgUri = uploadImgBean.getImgUri();
                        if (imgUri != null && !imgUri.equals("")) {
                            path.add(imgUri);
                        }
                    }
                    setCompressImg(path);
                }else {
                    uploadPic(null);
                }

                break;
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        UtilsStyle.statusBarLightMode(this);
    }

    private String getSelectedStr() {
        List<ReportBean> reportBeanList = reportListAdapter.getReportBeanList();
        if (reportBeanList!=null&&reportBeanList.size()>0) {
            StringBuilder sb = new StringBuilder();
            for (ReportBean reportBean : reportBeanList) {
                if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                    sb.append(",");
                }
                sb.append(reportBean.getReportIntro());
            }
            return sb.toString();
        }else {
            return "";
        }

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

    private void uploadPic(List<File> files) {
        LoadingDialog.show(NewsReportActivity.this);

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
            call = service.uploadReport("uploadReport", journalismId, selectedStr, reportIntro, partList);
        } else {
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("journalismId", journalismId)
                    .addFormDataPart("content", selectedStr)
                    .addFormDataPart("detail", reportIntro)
                    .build();
            call = service.uploadReport("uploadReport", requestBody);
        }

        // 执行
        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                LoadingDialog.dismiss(NewsReportActivity.this);
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    showToastShort(msg);
                } else {
                    showToastShort("请检查网络设置");
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                showToastShort("请检查网络设置");
                LoadingDialog.dismiss(NewsReportActivity.this);
            }
        });
    }

}
