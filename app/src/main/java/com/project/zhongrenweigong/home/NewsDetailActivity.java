package com.project.zhongrenweigong.home;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.mine.AddBankCardActivity;
import com.project.zhongrenweigong.mine.ReflectActivity;
import com.project.zhongrenweigong.mine.adapter.BankCardListAdapter;
import com.project.zhongrenweigong.mine.bean.BankCardDataBean;
import com.project.zhongrenweigong.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresent> {

    @BindView(R.id.te_back)
    TextView teBack;
    @BindView(R.id.te_title)
    TextView teTitle;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.web_news)
    WebView webNews;
    @BindView(R.id.te_see_size)
    TextView teSeeSize;
    @BindView(R.id.recy_hot_search)
    RecyclerView recyHotSearch;
    @BindView(R.id.recy_comments)
    RecyclerView recyComments;
    @BindView(R.id.img_shoucang)
    ImageView imgShoucang;
    @BindView(R.id.img_dianzan)
    ImageView imgDianzan;
    @BindView(R.id.img_edit_comment)
    ImageView imgEditComment;
    @BindView(R.id.te_topic_share)
    TextView teTopicShare;
    @BindView(R.id.te_detail_peples)
    TextView teDetailPeples;

    @Override
    public void initView() {
        imgSearch.setVisibility(View.VISIBLE);
        imgShare.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    public NewsDetailPresent bindPresent() {
        return new NewsDetailPresent();
    }

    @Override
    public void setListener() {
        teBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgShoucang.setOnClickListener(this);
        imgDianzan.setOnClickListener(this);
        imgEditComment.setOnClickListener(this);
        teTopicShare.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.te_back:
                finish();
                break;
            case R.id.img_search:
                break;
            case R.id.img_share:
                break;
            case R.id.img_shoucang:
                break;
            case R.id.img_dianzan:
                break;
            case R.id.img_edit_comment:
                showCommentDialog();
                break;
            case R.id.te_topic_share:
                break;
        }
    }

    private void showCommentDialog() {
        final Dialog commentDialog = new Dialog(this, R.style.dialog_bottom_full);
        commentDialog.setCanceledOnTouchOutside(true);
        commentDialog.setCancelable(true);
        Window window = commentDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        View view = View.inflate(this, R.layout.dialog_layout_comment, null);
        EditText edComment = (EditText) view.findViewById(R.id.ed_comment);
        TextView teSend = (TextView) view.findViewById(R.id.te_send);
        KeyboardUtils.showSoftInput(edComment);
        teSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edComment.getText().toString();
                if (TextUtils.isEmpty(comment)){
                    showToastLong("请输入评论内容");
                    return;
                }
                commentDialog.dismiss();

            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        commentDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
