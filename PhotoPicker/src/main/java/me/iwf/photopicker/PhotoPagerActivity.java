package me.iwf.photopicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import me.iwf.photopicker.fragment.ImagePagerFragment;

import static me.iwf.photopicker.PhotoPicker.KEY_SELECTED_PHOTOS;
import static me.iwf.photopicker.PhotoPreview.EXTRA_CURRENT_ITEM;
import static me.iwf.photopicker.PhotoPreview.EXTRA_PHOTOS;
import static me.iwf.photopicker.PhotoPreview.EXTRA_SHOW_DELETE;

/**
 * Created by donglua on 15/6/24.
 */
public class PhotoPagerActivity extends AppCompatActivity {

    private ImagePagerFragment pagerFragment;

    private boolean showDelete;

//  private ImageView pickerImageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.__picker_activity_photo_pager);

        int currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
        List<String> paths = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        showDelete = getIntent().getBooleanExtra(EXTRA_SHOW_DELETE, true);

        if (pagerFragment == null) {
            pagerFragment =
                    (ImagePagerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPagerFragment);
        }
        pagerFragment.setPhotos(paths, currentItem);

        pagerFragment.getViewPager().addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        updateActionBarTitle();
            }
        });

//    pickerImageBack = (ImageView) findViewById(R.id.picker_image_back);
//    pickerImageBack.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        onBackPressed();
//      }
//    });
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra(KEY_SELECTED_PHOTOS, pagerFragment.getPaths());
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }


//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//
//    if (item.getItemId() == android.R.id.home) {
//      onBackPressed();
//      return true;
//    }
//
//    if (item.getItemId() == R.id.delete) {
//      final int index = pagerFragment.getCurrentItem();
//
//      final String deletedPath =  pagerFragment.getPaths().get(index);
//
//      Snackbar snackbar = Snackbar.make(pagerFragment.getView(), R.string.__picker_deleted_a_photo,
//          Snackbar.LENGTH_LONG);
//
//      if (pagerFragment.getPaths().size() <= 1) {
//
//        // show confirm dialog
//        new AlertDialog.Builder(this)
//            .setTitle(R.string.__picker_confirm_to_delete)
//            .setPositiveButton(R.string.__picker_yes, new DialogInterface.OnClickListener() {
//              @Override public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                pagerFragment.getPaths().remove(index);
//                pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
//                onBackPressed();
//              }
//            })
//            .setNegativeButton(R.string.__picker_cancel, new DialogInterface.OnClickListener() {
//              @Override public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//              }
//            })
//            .show();
//
//      } else {
//
//        snackbar.show();
//
//        pagerFragment.getPaths().remove(index);
//        pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
//      }
//
//      snackbar.setAction(R.string.__picker_undo, new View.OnClickListener() {
//        @Override public void onClick(View view) {
//          if (pagerFragment.getPaths().size() > 0) {
//            pagerFragment.getPaths().add(index, deletedPath);
//          } else {
//            pagerFragment.getPaths().add(deletedPath);
//          }
//          pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
//          pagerFragment.getViewPager().setCurrentItem(index, true);
//        }
//      });
//
//      return true;
//    }
//
//    return super.onOptionsItemSelected(item);
//  }

//  public void updateActionBarTitle() {
//    if (actionBar != null) actionBar.setTitle(
//        getString(R.string.__picker_image_index, pagerFragment.getViewPager().getCurrentItem() + 1,
//            pagerFragment.getPaths().size()));
//  }
}
