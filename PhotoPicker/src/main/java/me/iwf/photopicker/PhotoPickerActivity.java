package me.iwf.photopicker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.entity.Photo;
import me.iwf.photopicker.event.OnItemCheckListener;
import me.iwf.photopicker.fragment.ImagePagerFragment;
import me.iwf.photopicker.fragment.PhotoPickerFragment;

import static android.widget.Toast.LENGTH_LONG;
import static me.iwf.photopicker.PhotoPicker.DEFAULT_COLUMN_NUMBER;
import static me.iwf.photopicker.PhotoPicker.DEFAULT_INIT_COUNT;
import static me.iwf.photopicker.PhotoPicker.DEFAULT_MAX_COUNT;
import static me.iwf.photopicker.PhotoPicker.EXTRA_GRID_COLUMN;
import static me.iwf.photopicker.PhotoPicker.EXTRA_INIT_COUNT;
import static me.iwf.photopicker.PhotoPicker.EXTRA_MAX_COUNT;
import static me.iwf.photopicker.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static me.iwf.photopicker.PhotoPicker.EXTRA_PREVIEW_ENABLED;
import static me.iwf.photopicker.PhotoPicker.EXTRA_SHOW_CAMERA;
import static me.iwf.photopicker.PhotoPicker.EXTRA_SHOW_GIF;
import static me.iwf.photopicker.PhotoPicker.KEY_SELECTED_PHOTOS;
import static me.iwf.photopicker.PhotoPicker.REQUEST_CODE;

public class PhotoPickerActivity extends AppCompatActivity {

    private PhotoPickerFragment pickerFragment;
    private ImagePagerFragment imagePagerFragment;

    private int maxCount = DEFAULT_MAX_COUNT;
    private int initCount = DEFAULT_INIT_COUNT;

    /**
     * to prevent multiple calls to inflate menu
     */
    private boolean menuIsInflated = false;

    private boolean showGif = false;
    private int columnNumber = DEFAULT_COLUMN_NUMBER;
    private ArrayList<String> originalPhotos = null;
    private TextView pickerTextTitle;

    private TextView pickerTextDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean showCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        boolean showGif = getIntent().getBooleanExtra(EXTRA_SHOW_GIF, false);
        boolean previewEnabled = getIntent().getBooleanExtra(EXTRA_PREVIEW_ENABLED, true);

        setShowGif(showGif);

        setContentView(R.layout.__picker_activity_photo_picker);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        findViewById(R.id.picker_image_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerActivity.this.finish();
            }
        });
        pickerTextDone = (TextView) findViewById(R.id.picker_text_done);

        maxCount = getIntent().getIntExtra(EXTRA_MAX_COUNT, DEFAULT_MAX_COUNT);
        initCount = getIntent().getIntExtra(EXTRA_INIT_COUNT, DEFAULT_INIT_COUNT);
        columnNumber = getIntent().getIntExtra(EXTRA_GRID_COLUMN, DEFAULT_COLUMN_NUMBER);
        originalPhotos = getIntent().getStringArrayListExtra(EXTRA_ORIGINAL_PHOTOS);

        pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if (pickerFragment == null) {
            pickerFragment = PhotoPickerFragment
                    .newInstance(showCamera, showGif, previewEnabled, columnNumber, maxCount, originalPhotos);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, pickerFragment, "tag")
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
            @Override
            public boolean onItemCheck(int position, Photo photo, final int selectedItemCount) {

                if (initCount + selectedItemCount > 0) {
                    pickerTextDone.setVisibility(View.VISIBLE);
                } else {
                    pickerTextDone.setVisibility(View.GONE);
                }

//        pickerTextDone.setEnabled(initCount + selectedItemCount > 0);
//
//        if (maxCount <= 1) {
//          List<String> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
//          if (!photos.contains(photo.getPath())) {
//            photos.clear();
//            pickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
//          }
//          pickerTextDone.setText(getSpannabelDoneText(initCount + selectedItemCount>maxCount?maxCount:initCount + selectedItemCount, maxCount));
//          return true;
//        }
//
                if (initCount + selectedItemCount > maxCount) {
                    Toast.makeText(getActivity(), getString(R.string.__picker_over_max_count_tips, maxCount),
                            LENGTH_LONG).show();
                    return false;
                }
//        pickerTextDone.setText(getSpannabelDoneText(initCount + selectedItemCount, maxCount));
                return true;
            }
        });

        pickerTextTitle = (TextView) findViewById(R.id.picker_text_title);
        pickerTextTitle.setText("所有图片");
        pickerTextTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        pickerTextTitle.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_photo_picker_arraw_up,0);

                pickerFragment.showDir();
            }
        });

        pickerTextDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
                intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        if (originalPhotos != null && originalPhotos.size() > 0) {
            pickerTextDone.setVisibility(View.VISIBLE);
        } else {
            pickerTextDone.setVisibility(View.GONE);
        }
//    if (originalPhotos != null && originalPhotos.size() > 0) {
//      pickerTextDone.setEnabled(true);
//      pickerTextDone.setText(getSpannabelDoneText(originalPhotos.size() + initCount, maxCount));
//    } else {
//      pickerTextDone.setText(getSpannabelDoneText(initCount, maxCount));
//      pickerTextDone.setEnabled(false);
//    }
    }




    /**
     * Overriding this method allows us to run our exit animation first, then exiting
     * the activity when it complete.
     */
    @Override
    public void onBackPressed() {
        if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
            imagePagerFragment.runExitAnimation(new Runnable() {
                public void run() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    }
                }
            });
        } else {
            super.onBackPressed();
        }
    }


    public void addImagePagerFragment(ImagePagerFragment imagePagerFragment) {
        this.imagePagerFragment = imagePagerFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, this.imagePagerFragment)
                .addToBackStack(null)
                .commit();
    }

    public PhotoPickerActivity getActivity() {
        return this;
    }

    public boolean isShowGif() {
        return showGif;
    }

    public void setShowGif(boolean showGif) {
        this.showGif = showGif;
    }

    public void setDirText(String name) {
        pickerTextTitle.setText(name);
        pickerTextTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_photo_picker_arraw_down, 0);
    }

    private SpannableStringBuilder getSpannabelDoneText(int origin, int max) {
        String count = String.format("(%s/%s)", origin, max);
        String done = "完成";

        SpannableStringBuilder ssb = new SpannableStringBuilder(count + done);

        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#41b3b5")), 0, count.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#5a7183")), count.length(), count.length() + done.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
