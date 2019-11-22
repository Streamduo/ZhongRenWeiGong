package com.project.zhongrenweigong.home.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phz on 2018/7/18.
 */

public class Subject1ViewHolder extends BaseViewHolder {

    @BindView(R.id.te_journalism_title)
    TextView teJournalismTitle;
    @BindView(R.id.te_from_date)
    TextView teFromDate;
    @BindView(R.id.img_journalism)
    ImageView imgJournalism;

    public Subject1ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }
}
