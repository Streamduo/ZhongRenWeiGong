package com.project.zhongrenweigong.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.zhongrenweigong.R;
import com.project.zhongrenweigong.base.BaseActivity;
import com.project.zhongrenweigong.currency.NewsSearchActivity;
import com.project.zhongrenweigong.home.bean.NewsBean;
import com.project.zhongrenweigong.home.bean.NewsDataMultiItemEntity;
import com.project.zhongrenweigong.login.bean.LoginMsg;
import com.project.zhongrenweigong.mine.AddBankCardActivity;
import com.project.zhongrenweigong.mine.ReflectActivity;
import com.project.zhongrenweigong.mine.adapter.BankCardListAdapter;
import com.project.zhongrenweigong.mine.bean.BankCardDataBean;
import com.project.zhongrenweigong.util.AcacheUtils;
import com.project.zhongrenweigong.util.KeyboardUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidbase.kit.ToastManager;
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
    private String mbId;
    private NewsBean newsbean;
    private String isLike;
    private String type;
    private String isCollect;

    @Override
    public void initView() {
        imgSearch.setVisibility(View.VISIBLE);
        imgShare.setVisibility(View.VISIBLE);
        LoginMsg userAccent = AcacheUtils.getInstance(this).getUserAccent();
        if (userAccent == null) {
            mbId = "4545455445";
        } else {
            mbId = userAccent.mbId;
        }
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        newsbean = (NewsBean) intent.getSerializableExtra("newsbean");
        isLike = newsbean.getIsLike();
        isCollect = newsbean.getIsCollect();
        setLike();
        setCollect();
    }

    private void setCollect() {
        if (isCollect.equals("1")) {
            imgShoucang.setBackgroundResource(R.mipmap.detail_shoucang_selected);
        } else {
            imgShoucang.setBackgroundResource(R.mipmap.detail_shoucang);
        }
    }

    private void setLike() {
        if (isLike.equals("1")) {
            imgDianzan.setBackgroundResource(R.mipmap.detail_dianzan_selected);
        } else {
            imgDianzan.setBackgroundResource(R.mipmap.detail_dianzan);
        }
    }

    @Override
    public void initAfter() {
        initWebViews();
    }

    private void initWebViews() {
        WebSettings ws = webNews.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setTextZoom(100);
        ws.setDomStorageEnabled(true);//打开DOM存储API
        ws.setUseWideViewPort(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ws.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webNews.setWebViewClient(new H5WebViewClient());
        webNews.setWebChromeClient(new H5WebChromeClient());
        webNews.setDownloadListener(new WebViewDownLoadListener());
        webNews.loadUrl(newsbean.getNurl());
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
                Router.newIntent(NewsDetailActivity.this).to(NewsSearchActivity.class).launch();
                break;
            case R.id.img_share:
                showShareDialog();
                break;
            case R.id.img_shoucang:
                setCollect(newsbean.getNid(), isCollect.equals("0") ? "1" : "0");
                break;
            case R.id.img_dianzan:
                setLike(newsbean.getNid(), isLike.equals("0") ? "1" : "0");
                break;
            case R.id.img_edit_comment:
                showCommentDialog();
                break;
            case R.id.te_topic_share:
                showShareDialog();
                break;
        }
    }

    private void setCollect(String newsId, String flag) {
        getP().collect(mbId, newsId, flag);
    }

    private void setLike(String newsId, String flag) {
        getP().homePageLike(mbId, newsId, type, flag);
    }

    private void showShareDialog() {
        final Dialog shareDialog = new Dialog(this, R.style.dialog_bottom_full);
        shareDialog.setCanceledOnTouchOutside(true);
        shareDialog.setCancelable(true);
        Window window = shareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        View view = View.inflate(this, R.layout.dialog_layout_share, null);
        TextView teShareQq = (TextView) view.findViewById(R.id.te_share_qq);
        TextView teShareQqZone = (TextView) view.findViewById(R.id.te_share_qq_zone);
        TextView teShareWx = (TextView) view.findViewById(R.id.te_share_wx);
        TextView teSharePyq = (TextView) view.findViewById(R.id.te_share_pyq);
        TextView teShareDingding = (TextView) view.findViewById(R.id.te_share_dingding);
        TextView teShareWb = (TextView) view.findViewById(R.id.te_share_wb);
        TextView teShareSys = (TextView) view.findViewById(R.id.te_share_sys);
        TextView teShareCopy = (TextView) view.findViewById(R.id.te_share_copy);
        TextView teCancel = (TextView) view.findViewById(R.id.te_cancel);
        RelativeLayout rlParent = (RelativeLayout) view.findViewById(R.id.rl_parent);
        rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.dismiss();
            }
        });
        teShareQq.setOnClickListener(this);
        teShareQqZone.setOnClickListener(this);
        teShareWx.setOnClickListener(this);
        teSharePyq.setOnClickListener(this);
        teShareDingding.setOnClickListener(this);
        teShareWb.setOnClickListener(this);
        teShareCopy.setOnClickListener(this);
        teShareSys.setOnClickListener(this);

        teCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        shareDialog.show();
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

        teSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edComment.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    showToastLong("请输入评论内容");
                    return;
                }
                commentDialog.dismiss();

            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        commentDialog.show();
        KeyboardUtils.showSoftInput(edComment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public void setLikeStatus(String flag) {
        isLike = flag;
        setLike();
    }

    public void setCollectStatus(String flag) {
        isCollect = flag;
        if (flag.equals("1")){
            Toast toast = Toast.makeText(this,"收藏成功",Toast.LENGTH_LONG);
            toast.setGravity(0,0,0);
            toast.show();
        }else {
            Toast toast = Toast.makeText(this,"取消收藏成功",Toast.LENGTH_LONG);
            toast.setGravity(0,0,0);
            toast.show();
        }

        setCollect();
    }

    private class WebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    class H5WebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)
                    && !title.contains("h5.")) {
                setTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
//            Logger.d("progress:%s", newProgress);
//            if (newProgress != 100) {
//                progressBar.setVisibility(View.VISIBLE);
//                progressBar.setProgress(newProgress);
//            } else {
//                progressBar.setVisibility(View.GONE);
//            }
        }
    }

    class H5WebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
//            return openApp(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle();
            if (!TextUtils.isEmpty(title)) {
                setTitle(title);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webNews != null) {
            webNews.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webNews != null) {
            webNews.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webNews != null) {
            webNews.destroy();
            webNews = null;
        }
    }

}
