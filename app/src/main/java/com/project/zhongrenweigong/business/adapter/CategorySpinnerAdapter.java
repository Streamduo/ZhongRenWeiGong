package com.project.zhongrenweigong.business.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.business.bean.CategoryDataBean;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/6 11:13
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class CategorySpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryDataBean> list;

    public CategorySpinnerAdapter(@NonNull Context context, List<CategoryDataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 获取LayoutInflater对象
        LayoutInflater inflater = LayoutInflater.from(context);
        // 装载列表项视图
        View itemView = inflater.inflate(R.layout.item_spinner, null);
        if (itemView != null) {
            TextView teCategoryName = (TextView) itemView.findViewById(R.id.te_category_name);
            CategoryDataBean categoryDataBean = list.get(position);
            teCategoryName.setText(categoryDataBean.categoryName);
        }
        return itemView;
    }
}
