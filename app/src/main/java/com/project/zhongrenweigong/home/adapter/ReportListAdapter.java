package com.project.zhongrenweigong.home.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.home.bean.AddressDataBean;
import com.project.zhongrenweigong.home.bean.ReportBean;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidbase.kit.ToastManager;


/**
 * Created by phz on 2017/12/15.
 */

public class ReportListAdapter extends BaseQuickAdapter<ReportBean, BaseViewHolder> {

    private List<ReportBean> reportBeanList = new ArrayList<>();
    public OnSelectedClickListener onSelectedClickListener;

    public void setOnSelectedClickListener(OnSelectedClickListener onSelectedClickListener) {
        this.onSelectedClickListener = onSelectedClickListener;
    }

    public ReportListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ReportBean item) {
        final int isSelected = item.getIsSelected();

        if (isSelected == 1) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.report_selected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            ((TextView) helper.getView(R.id.te_report))
                    .setCompoundDrawables(drawable,
                            null, null, null);
        } else {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.report_noselected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            ((TextView) helper.getView(R.id.te_report))
                    .setCompoundDrawables(drawable,
                            null, null, null);
        }
        helper.setText(R.id.te_report, item.getReportIntro());
        final int adapterPosition = helper.getAdapterPosition();
        helper.getView(R.id.te_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected == 1) {
                    item.setIsSelected(0);
                    setOtherNoSelected(adapterPosition);
                    if (reportBeanList.size() <= 0) {
                        onSelectedClickListener.onClickNoSelect();
                    }
                    notifyDataSetChanged();
                } else {
                    if (reportBeanList.size() <= 4) {
                        onSelectedClickListener.onClickSelect();
                        item.setIsSelected(1);
                        reportBeanList.add(item);
                        notifyDataSetChanged();
                    } else {
                        ToastManager.showShort(mContext, "举报原因最多选择四个");
                    }
                }
            }
        });
    }

    private void setOtherNoSelected(int position) {
        for (int i = 0; i < reportBeanList.size(); i++) {
            ReportBean reportBean = reportBeanList.get(i);
            if (position == i) {
                reportBean.setIsSelected(0);
                reportBeanList.remove(i);
            }
        }
    }

    public List<ReportBean> getReportBeanList() {
        return reportBeanList;
    }

    public interface OnSelectedClickListener {
        void onClickSelect();

        void onClickNoSelect();
    }
}
