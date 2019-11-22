package com.project.zhongrenweigong.home.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phz on 2018/7/18.
 */

public class Subject3ViewHolder extends BaseViewHolder {
    @BindView(R.id.te_journalism_title)
    TextView teJournalismTitle;
    @BindView(R.id.img_journalism_cover)
    ImageView imgJournalismCover;
    @BindView(R.id.rl_play)
    RelativeLayout rlPlay;
    @BindView(R.id.te_from_date)
    TextView teFromDate;

    public Subject3ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }
}
