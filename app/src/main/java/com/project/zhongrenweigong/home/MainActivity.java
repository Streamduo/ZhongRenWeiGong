package com.project.zhongrenweigong.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.currency.event.RefreshMineEvent;
import com.project.zhongrenweigong.login.LoginActivity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.ScreenUtils;
import com.project.zhongrenweigong.util.SystemUtil;
import com.project.zhongrenweigong.view.DownloadProgressDialog;
import com.qiniu.pili.droid.shortvideo.PLShortVideoTranscoder;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.router.Router;

import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_LOW_MEMORY;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_NO_VIDEO_TRACK;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SRC_DST_SAME_FILE_PATH;

public class MainActivity extends BaseActivity<MainPresent> implements CompoundButton.OnCheckedChangeListener {

    public static final String SAVE_KEY_TAB_INDEX = "tab_index";
    private final int SDK_PERMISSION_REQUEST = 127;
    long exitTime;

    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    @BindView(R.id.home_main)
    RadioButton homeMain;
    @BindView(R.id.home_mine)
    RadioButton homeMine;
    @BindView(R.id.home_msg)
    RadioButton homeMsg;
    @BindView(R.id.home_industry)
    RadioButton homeIndustry;
    @BindView(R.id.msg_size)
    TextView msgSize;
    @BindView(R.id.home_center)
    RadioButton homeCenter;
    private FactoryFragment factoryFragment;
    private Dialog recordDialog;

    private String outputDir;
    private boolean isCompressing;
    private String destPath;
    private float percentage;
    private DownloadProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        factoryFragment = new FactoryFragment(savedInstanceState, getSupportFragmentManager());
        selectTab(savedInstanceState);
        getPersimmions();
    }

    @Override
    public void initView() {
        setFull(false);
        homeMine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    LoginMsg userAccent = AcacheUtils.getInstance(MainActivity.this).getUserAccent();
                    if (userAccent == null || userAccent.mbId == null || userAccent.mbId.equals("")) {
                        Router.newIntent(MainActivity.this).to(LoginActivity.class).launch();
                        return true;
                    }
                }
                return false;
            }
        });
        homeMsg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    LoginMsg userAccent = AcacheUtils.getInstance(MainActivity.this).getUserAccent();
                    if (userAccent == null || userAccent.mbId == null || userAccent.mbId.equals("")) {
                        Router.newIntent(MainActivity.this).to(LoginActivity.class).launch();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresent bindPresent() {
        return new MainPresent();
    }

    @Override
    public void setListener() {
        homeIndustry.setOnCheckedChangeListener(this);
        homeMsg.setOnCheckedChangeListener(this);
        homeMain.setOnCheckedChangeListener(this);
        homeMine.setOnCheckedChangeListener(this);
        homeCenter.setOnCheckedChangeListener(this);
    }

    @Override
    public void widgetClick(View v) {
    }

    private void selectTab(Bundle savedInstanceState) {
        int index = 0;

        if (savedInstanceState == null) {
            index = 0;
        } else {
            index = savedInstanceState.getInt(SAVE_KEY_TAB_INDEX, 0);
        }
        switch (index) {
            case -1:
            case 0:
                homeMain.setChecked(true);
                break;
            case 1:
                homeIndustry.setChecked(true);
                break;
            case 2:
                homeMsg.setChecked(true);
                break;
            case 3:
                homeMine.setChecked(true);
                break;
        }
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        factoryFragment = null;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_KEY_TAB_INDEX, factoryFragment.getCurrentIndex());
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }

        if (doubleClickToExit()) {
            super.onBackPressed();
            exit();
        }
    }

    private boolean doubleClickToExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastManager.showShort(this, "再按一次退出 众仁为公");
            exitTime = System.currentTimeMillis();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) {
            return;
        }
        switch (compoundButton.getId()) {
            case R.id.home_main:
                factoryFragment.changeToFragment(0);
                break;
            case R.id.home_industry:
                factoryFragment.changeToFragment(1);
                break;
            case R.id.home_msg:
                factoryFragment.changeToFragment(2);
                break;
            case R.id.home_mine:
                factoryFragment.changeToFragment(3);
                break;
            case R.id.home_center:
                showRecordDialog();
                break;
        }
    }

    private void showRecordDialog() {
        recordDialog = new Dialog(this, R.style.dialog_bottom_full);
        recordDialog.setCanceledOnTouchOutside(true);
        recordDialog.setCancelable(true);
        Window window = recordDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        View view = View.inflate(this, R.layout.dialog_layout_recording, null);
        TextView teCamera = (TextView) view.findViewById(R.id.te_camera);
        TextView tePhotos = (TextView) view.findViewById(R.id.te_photos);
        ImageView exit = (ImageView) view.findViewById(R.id.exit);

        teCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                Uri fileUri = null;
                try {
                    fileUri = Uri.fromFile(createMediaFile()); // create a file to save the video
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //限制时长s
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
                // start the Video Capture Intent
                startActivityForResult(intent, 1);
                recordDialog.dismiss();
            }
        });

        tePhotos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
                recordDialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                recordDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);//设置横向全屏
        recordDialog.show();
    }

    private File createMediaFile() throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "ZhongRenWeiGong");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" + timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                Uri data1 = data.getData();
                //拿到视频保存地址
                String s = data1.toString();
                String[] split = s.split(":");
                String videopath = split[1];
                if (getFile(videopath) > 15) {
                    startCompressed(videopath);
                } else {
                    destPath = videopath;
                    Router.newIntent(MainActivity.this)
                            .putString("videoPath", destPath)
                            .to(SendPublishActivity.class)
                            .launch();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            } else {
                // Video capture failed, advise user
            }
        } else {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri selectedVideo = data.getData();
                    String[] filePathColumn = {MediaStore.Video.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedVideo,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String videopath = cursor.getString(columnIndex);
                    cursor.close();
                    if (getFile(videopath) > 15) {
                        startCompressed(videopath);
                    } else {
                        destPath = videopath;
                        Router.newIntent(MainActivity.this)
                                .putString("videoPath", destPath)
                                .to(SendPublishActivity.class)
                                .launch();
                    }
                }
            }
        }
    }

    private void startCompressed(String videoPath) {
        if (isCompressing) {
            return;
        }
        isCompressing = true;
        outputDir = SystemUtil.getSystemFilePath(this);
        destPath = outputDir + File.separator + "VID_compress.mp4";

        //PLShortVideoTranscoder初始化，三个参数，第一个context，第二个要压缩文件的路径，第三个视频压缩后输出的路径
        PLShortVideoTranscoder mShortVideoTranscoder = new PLShortVideoTranscoder(this, videoPath, destPath);
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        int height = ScreenUtils.getScreenHeight(this); // 视频高度
        int width = ScreenUtils.getScreenWidth(this); // 视频宽度
        showProgressDialog();
        mShortVideoTranscoder.transcode(width, height, height * width, 90,false, new PLVideoSaveListener() {


            @Override
            public void onSaveVideoSuccess(String s) {
                isCompressing = true;
                handler.sendEmptyMessage(103);
            }

            @Override
            public void onSaveVideoFailed(final int errorCode) {
                isCompressing = false;
                mShortVideoTranscoder.cancelTranscode();
                Log.e("snow", "save failed: " + errorCode);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (errorCode) {
                            case ERROR_NO_VIDEO_TRACK:
//                                ToastUtils.getInstance().showToast("该文件没有视频信息！");
                                break;
                            case ERROR_SRC_DST_SAME_FILE_PATH:
//                                ToastUtils.getInstance().showToast("源文件路径和目标路径不能相同！");
                                break;
                            case ERROR_LOW_MEMORY:
//                                ToastUtils.getInstance().showToast("手机内存不足，无法对该视频进行时光倒流！");
                                break;
                            default:
//                                ToastUtils.getInstance().showToast("transcode failed: " + errorCode);
                        }
                    }
                });
            }

            @Override
            public void onSaveVideoCanceled() {
                mShortVideoTranscoder.cancelTranscode();
                handler.sendEmptyMessage(101);
//                LogUtil.e("onSaveVideoCanceled");
            }

            @Override
            public void onProgressUpdate(float percentag) {
                percentage = percentag;
                handler.sendEmptyMessage(100);
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    progressDialog.setProgress(Math.round(percentage * 100));
                    break;
                case 101:
                    Log.i(TAG, "压缩取消了");
                    break;
                case 102:
                    break;
                case 103:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        Router.newIntent(MainActivity.this)
                                .putString("videoPath", destPath)
                                .to(SendPublishActivity.class)
                                .launch();
                    }
//                    Log.i(TAG, "压缩后大小 = " + getFileSize(destPath));
                    break;
            }
            return false;
        }
    });

    private String getFileSize(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return "0 MB";
        } else {
            long size = f.length();
            return (size / 1024f) / 1024f + "MB";
        }
    }

    private void showProgressDialog() {
        progressDialog = new DownloadProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 标题
        progressDialog.setTitle("正在压缩视频");
        // 设置ProgressDialog 提示信息
        progressDialog.setMessage("当前压缩进度:");
        // 设置ProgressDialog 标题图标
        //progressDialog.setIcon(R.drawable.a);
        // 设置ProgressDialog 进度条进度
        // 设置ProgressDialog 的进度条是否不明确
        progressDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMax(100);
    }

    private float getFile(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return 0;
        } else {
            long size = f.length();
            return (size / 1024f) / 1024f;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshMineEvent refreshMineEvent) {
        homeMine.setChecked(true);
    }
}
