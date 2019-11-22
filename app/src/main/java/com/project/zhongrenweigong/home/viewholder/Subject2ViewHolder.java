package com.project.zhongrenweigong.home.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phz on 2018/7/20.
 */

public class Subject2ViewHolder extends BaseViewHolder {

    @BindView(R.id.te_journalism_title)
    TextView teJournalismTitle;
    @BindView(R.id.img_journalism_01)
    ImageView imgJournalism01;
    @BindView(R.id.img_journalism_02)
    ImageView imgJournalism02;
    @BindView(R.id.img_journalism_03)
    ImageView imgJournalism03;
    @BindView(R.id.te_from_date)
    TextView teFromDate;
    @BindView(R.id.te_share_journalism)
    TextView teShareJournalism;

    public Subject2ViewHolder(View view) {
        super(view);
        ButterKnife.bind(view);
    }

}
