package com.project.zhongrenweigong.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseFragment;
import com.project.zhongrenweigong.util.ScreenUtils;
import com.project.zhongrenweigong.util.StatusBarUtils;
import com.project.zhongrenweigong.util.SystemUtil;
import com.project.zhongrenweigong.view.DownloadProgressDialog;
import com.qiniu.pili.droid.shortvideo.PLShortVideoTranscoder;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.router.Router;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_LOW_MEMORY;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_NO_VIDEO_TRACK;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SRC_DST_SAME_FILE_PATH;

/**
 * 作者：Fuduo on 2019/10/17 10:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class SendPublishFragment extends BaseFragment<SquarePresent> {

    public static final String TAG = "SendPublishFragment";
    Unbinder unbinder;
    @BindView(R.id.te_camera)
    TextView teCamera;
    @BindView(R.id.te_photos)
    TextView tePhotos;

    private String outputDir;
    private boolean isCompressing;
    private String destPath;
    private float percentage;
    private DownloadProgressDialog progressDialog;

    @Override
    public void onResume() {
        super.onResume();
        StatusBarUtils.with(getActivity()).init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtils.with(getActivity()).init();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.dialog_layout_recording;
    }

    @Override
    public SquarePresent bindPresent() {
        return new SquarePresent();
    }

    @Override
    public void setListener() {
        teCamera.setOnClickListener(this);
        tePhotos.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_camera:
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
                break;
            case R.id.te_photos:
                Intent intente = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intente, 2);
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Router.newIntent(getActivity())
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

                    Cursor cursor = getActivity().getContentResolver().query(selectedVideo,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String videopath = cursor.getString(columnIndex);
                    cursor.close();
                    if (getFile(videopath) > 15) {
                        startCompressed(videopath);
                    } else {
                        destPath = videopath;
                        Router.newIntent(getActivity())
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
        outputDir = SystemUtil.getSystemFilePath(getContext());
        destPath = outputDir + File.separator + "VID_compress.mp4";

        //PLShortVideoTranscoder初始化，三个参数，第一个context，第二个要压缩文件的路径，第三个视频压缩后输出的路径
        PLShortVideoTranscoder mShortVideoTranscoder = new PLShortVideoTranscoder(getContext(), videoPath, destPath);
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        int height = ScreenUtils.getScreenHeight(getContext()); // 视频高度
        int width = ScreenUtils.getScreenWidth(getContext()); // 视频宽度
        showProgressDialog();
        mShortVideoTranscoder.transcode(width, height, height * width, false, new PLVideoSaveListener() {


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
                getActivity().runOnUiThread(new Runnable() {
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
                        Router.newIntent(getActivity())
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
        progressDialog = new DownloadProgressDialog(getActivity());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
