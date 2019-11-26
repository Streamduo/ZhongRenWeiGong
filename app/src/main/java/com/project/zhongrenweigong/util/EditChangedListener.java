package com.project.zhongrenweigong.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import cn.droidlover.xdroidbase.kit.ToastManager;

/**
 * 作者：Fuduo on 2019/11/26 09:39
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class EditChangedListener implements TextWatcher {
    private CharSequence temp;// 监听前的文本
    private int editStart;// 光标开始位置
    private int editEnd;// 光标结束位置

    // 需要提示的输入框
    private EditText mEditTextMsg;
    private Context mContext;
    // 大小
    private int totleCount;
    // 显示的文本
    private TextView textview;

    public EditChangedListener(EditText mEditTextMsg, Context mContext,
                               int totleCount, TextView textview) {
        super();
        this.mEditTextMsg = mEditTextMsg;
        this.mContext = mContext;
        this.totleCount = totleCount;
        this.textview = textview;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        textview.setText(s.length() + "/" + totleCount);
        temp = s;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textview.setText(s.length() + "/" + totleCount);
    }

    @Override
    public void afterTextChanged(Editable s) {
        /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
        editStart = mEditTextMsg.getSelectionStart();
        editEnd = mEditTextMsg.getSelectionEnd();
        if (temp.length() > totleCount) {
            ToastManager.showShort(mContext, "你输入的字数已经超过了限制！");
            s.delete(editStart - 1, editEnd);
            int tempSelection = editStart;
            mEditTextMsg.setText(s);
            mEditTextMsg.setSelection(tempSelection);
        }
    }
}
