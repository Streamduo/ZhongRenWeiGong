package com.project.zhongrenweigong.currency;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Toast;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.log.XLog;

public class ActivitySelectImage extends AppCompatActivity implements OnClickListener {

    public static final String TAG = "ActivitySelectImage";

    public static final int REQ_SELECT_IMAGE = 1000;
    public static final int CHOSE_CARD = 1001;
    public static final int CHOSE_CARD_1 = 1007;
    public static final int CHOSE_PHOTO = 1002;
    public static final int CHOSE_IMAGE = 1003;

    public static final int TREND_SELECT_IMAGE = 1006;

    private static final String prefix = "file://";
    public static String TYPE_TREND = "trend_select_pic";
    //类型：
    private String type = "";

    /**
     * 发布动态：选择图片
     *
     * @param activity
     */
    public static void selectImage(Activity activity) {
        Intent intent = new Intent(activity, ActivitySelectImage.class);
        intent.putExtra("type", TYPE_TREND);
        activity.startActivityForResult(intent, TREND_SELECT_IMAGE);
    }

    public static void selectImageForCard(Activity activity) {
        Intent intent = new Intent(activity, ActivitySelectImage.class);
        intent.putExtra("type", "card");
        activity.startActivityForResult(intent, CHOSE_CARD);
    }

    public static void selectImageForCard1(Activity activity) {
        Intent intent = new Intent(activity, ActivitySelectImage.class);
        intent.putExtra("type", "card1");
        activity.startActivityForResult(intent, CHOSE_CARD_1);
    }

    public static void selectImageForImage(Activity activity) {
        Intent intent = new Intent(activity, ActivitySelectImage.class);
        intent.putExtra("type", "image");
        activity.startActivityForResult(intent, CHOSE_IMAGE);
    }

    public static void selectImageForPhoto(Activity activity) {
        Intent intent = new Intent(activity, ActivitySelectImage.class);
        intent.putExtra("type", "photo");
        activity.startActivityForResult(intent, CHOSE_PHOTO);
    }

    public static void selectAndCropImage(BaseActivity activity, int nMaxWidth, double dRatio) {
        Intent intent = new Intent(activity, ActivitySelectImage.class);
        intent.putExtra("max_width", nMaxWidth);
        intent.putExtra("ratio", dRatio);
        intent.putExtra("type", "photo");
        activity.startActivityForResult(intent, REQ_SELECT_IMAGE);
    }

    private final int REQ_SELECT_PHOTO = 0;
    private final int REQ_TAKE_PHOTO = 1;
    private final int REQ_CROP_IMAGE = 2;
    private int m_nMaxWidth;
    private double m_dRatio;

    private static final int REQUEST_PERMISSION = 100;
    private static final int REQUEST_TAKE_PHOTO = 0;// 拍照
    private static final int REQUEST_CROP = 1;// 裁剪
    private static final int SCAN_OPEN_PHONE = 2;// 相册
    private Uri imgUri; // 拍照时返回的uri
    private Uri mCutUri;// 图片裁剪时返回的uri
    private boolean hasPermission = false;
    private File imgFile;// 拍照保存的图片文件

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_image);
        checkPermissions();
        findViewById(R.id.btnTakePhoto).setOnClickListener(this);
        findViewById(R.id.btnSelectPhoto).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
        findViewById(R.id.rlParent).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return false;
            }
        });

        m_nMaxWidth = getIntent().getIntExtra("max_width", 0);
        m_dRatio = getIntent().getDoubleExtra("ratio", 0);
        type = getIntent().getStringExtra("type");
    }

    // finish activity when touch out side
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect boundRect = new Rect();
        getWindow().getDecorView().getHitRect(boundRect);
        if (!boundRect.contains((int) ev.getX(), (int) ev.getY()))
            finish();

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int nId = v.getId();
        if (nId == R.id.btnSelectPhoto) {
            checkPermissions();
            if (hasPermission) {
                openGallery();
            }
        } else if (nId == R.id.btnTakePhoto) {
            checkPermissions();
            if (hasPermission) {
                takePhoto();
            }
        } else
            finish();

    }


    // 拍照
    private void takePhoto() {
        // 要保存的文件名
        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        String fileName = "photo_" + time;
        // 创建一个文件夹
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/take_photo";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 要保存的图片文件
        imgFile = new File(file, fileName + ".jpeg");
        // 将file转换成uri
        // 注意7.0及以上与之前获取的uri不一样了，返回的是provider路径
        imgUri = getUriForFile(this, imgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 添加Uri读取权限
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        // 或者
//        grantUriPermission("com.rain.takephotodemo", imgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 添加图片保存位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void openGallery() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI);
        }

        if (null != type) {
            if (type.equals("card")) {
                startActivityForResult(intent, CHOSE_CARD);
            } else if (type.equals("card1")) {
                startActivityForResult(intent, CHOSE_CARD_1);
            } else  if (type.equals("photo")) {
                startActivityForResult(intent, CHOSE_PHOTO);
            } else  if (type.equals("image")) {
                startActivityForResult(intent, CHOSE_IMAGE);
            } else  if (type.equals(TYPE_TREND)) {
                startActivityForResult(intent, TREND_SELECT_IMAGE);
            } else {
                startActivityForResult(intent, TREND_SELECT_IMAGE);
            }
        } else {
            startActivityForResult(intent, SCAN_OPEN_PHONE);
        }
    }


    // 图片裁剪
    private void cropPhoto(Uri uri, boolean fromCapture) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        // 注意一定要添加该项权限，否则会提示无法裁剪
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("scale", true);

        // 设置裁剪区域的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // 设置裁剪区域的宽度和高度
        intent.putExtra("outputX", m_nMaxWidth);
        intent.putExtra("outputY", (int) (m_nMaxWidth * m_dRatio));

        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // 图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);

        // 指定裁剪完成以后的图片所保存的位置,pic info显示有延时
        if (fromCapture) {
            // 如果是使用拍照，那么原先的uri和最终目标的uri一致,注意这里的uri必须是Uri.fromFile生成的
            mCutUri = Uri.fromFile(imgFile);
        } else { // 从相册中选择，那么裁剪的图片保存在take_photo中
            String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
            String fileName = "photo_" + time;
            File mCutFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/take_photo/", fileName + ".jpeg");
            if (!mCutFile.getParentFile().exists()) {
                mCutFile.getParentFile().mkdirs();
            }
            mCutUri = Uri.fromFile(mCutFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutUri);
        Toast.makeText(this, "剪裁图片", Toast.LENGTH_SHORT).show();
        // 以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(uri);
        this.sendBroadcast(intentBc);

        startActivityForResult(intent, REQUEST_CROP); //设置裁剪参数显示图片至ImageVie
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 拍照并进行裁剪
                case REQUEST_TAKE_PHOTO:
                    Log.e(TAG, "onActivityResult: imgUri:REQUEST_TAKE_PHOTO:" + imgUri.toString());
                    setImage();
                    break;

                // 裁剪后设置图片
                case REQUEST_CROP:
                    finish(mCutUri, imgFile == null ? "" : imgFile.getAbsolutePath());
                    Log.e(TAG, "onActivityResult: imgUri:REQUEST_CROP:" + mCutUri.toString());
                    break;
                case CHOSE_CARD:
                case CHOSE_CARD_1:
                case CHOSE_PHOTO:
                case CHOSE_IMAGE:
                case SCAN_OPEN_PHONE:
                    if (data != null && data.getData() != null) {
                        Log.e(TAG, "onActivityResult: SCAN_OPEN_PHONE:" + data.getData().toString());
                        Uri dataData = data.getData();
                        checkSelectedPicPath(dataData);
                    } else {
                        finish();
                    }
                    break;

                default:
                    break;
            }
        } else {
            Log.d("Main", "获取图片失败");
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    private void setImage() {
        if (imgUri != null) {
            checkSelectedPicPath(imgUri);
        } else {
            finish();
        }
    }

    private void finish(Uri uri, String path) {
        Intent intent = new Intent();
        intent.setData(uri);
        intent.putExtra("path", path);
        if (null != type) {
            if (type.equals("card")) {
                setResult(CHOSE_CARD, intent);
            } else if (type.equals("card1")) {
                setResult(CHOSE_CARD_1, intent);
            } else if (type.equals("photo")) {
                setResult(CHOSE_PHOTO, intent);
            }  else if (type.equals("image")) {
                setResult(CHOSE_IMAGE, intent);
            } else if (type.equals(TYPE_TREND)) {
                setResult(TREND_SELECT_IMAGE, intent);
            } else {
                setResult(TREND_SELECT_IMAGE, intent);
            }
        } else {
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    /**
     * Matches code in MediaProvider.computeBucketValues. Should be a common
     * function.
     */
    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    public static String getLastCameraCapturedImage(Context context) {
        String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
        String CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME);

        final String[] projection = {Images.Media.DATA, Images.ImageColumns.DATE_ADDED};
        final String selection = Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};
        final String sortby = Images.ImageColumns.DATE_ADDED + " DESC LIMIT 1";
        final Cursor cursor = context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI,
                projection, selection, selectionArgs, sortby);

        ArrayList<String> result = new ArrayList<String>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                result.add(data);
                Log.e("ID = ", cursor.getInt(0) + "");
            } while (cursor.moveToNext());
        }
        cursor.close();

        return result.get(0);
    }

    public static Uri getUriFromPath(Context context, String szFilePath) {
        Uri fileUri = Uri.parse(szFilePath);
        String filePath = fileUri.getPath();
        Cursor c = context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, null, "_data ='" + filePath + "'", null, null);
        c.moveToNext();
        int id = c.getInt(0);
        Uri uri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id);

        return uri;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static String getRealPathFromURI_3(Context context, Uri contentUri) {
        String szRetPath = null;

        try {
            String[] proj = {Images.Media.DATA};
            CursorLoader loader = new CursorLoader(context.getApplicationContext(), contentUri, proj, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(Images.Media.DATA);
            cursor.moveToFirst();
            szRetPath = cursor.getString(column_index);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return szRetPath;
    }

    /**
     * 检查选择图片的路径，对图片进行裁剪
     *
     * @param uri 图片的路径
     */
    private void checkSelectedPicPath(Uri uri) {
        if (null != uri && (!"".equals(uri))) {
            if (type.equals("card")) {
                finish(uri, imgFile == null ? "" : imgFile.getAbsolutePath());
            } else if (type.equals("card1")) {
                finish(uri, imgFile == null ? "" : imgFile.getAbsolutePath());
            } else if (type.equals("choosePhoto")) {
                finish(uri, imgFile == null ? "" : imgFile.getAbsolutePath());
            } else if (type.equals("fromcard")) {
                finish(uri, imgFile == null ? "" : imgFile.getAbsolutePath());
            } else if (type.equals("photo")) {
                cropPhoto(uri, false);
            } else if (type.equals("image")) {
                finish(uri, imgFile == null ? "" : imgFile.getAbsolutePath());
            } else  if (type.equals(TYPE_TREND)) {
                finish(uri, imgFile == null ? "" : imgFile.getAbsolutePath());
            } else {
                finish(uri, imgFile == null ? "" : imgFile.getAbsolutePath());
            }
        } else {
            //失败后返回
            ToastManager.showShort(this, "图片选择失败");
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static Uri getOutputMediaFileUri() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "wake");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                XLog.e("failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                + ".jpg");

        return Uri.fromFile(mediaFile);
    }

    public static String pathAddPreFix(String path) {
        if (path == null) {
            path = "";
        }

        if (!path.startsWith(prefix)) {
            path = prefix + path;
        }
        return path;
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查是否有存储和拍照权限
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    ) {
                hasPermission = true;
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISSION);
            }
        } else {
            hasPermission = true;
        }
    }

    // 从file中获取uri
    // 7.0及以上使用的uri是contentProvider content://com.rain.takephotodemo.FileProvider/images/photo_20180824173621.jpg
    // 6.0使用的uri为file:///storage/emulated/0/take_photo/photo_20180824171132.jpg
    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "ccom.project.zhongrenweigong.provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasPermission = true;
            } else {
                Toast.makeText(this, "权限授予失败！", Toast.LENGTH_SHORT).show();
                hasPermission = false;
            }
        }
    }

}

