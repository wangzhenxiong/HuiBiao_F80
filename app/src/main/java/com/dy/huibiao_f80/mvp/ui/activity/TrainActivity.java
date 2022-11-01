package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerTrainComponent;
import com.dy.huibiao_f80.mvp.contract.TrainContract;
import com.dy.huibiao_f80.mvp.presenter.TrainPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class TrainActivity extends BaseActivity<TrainPresenter> implements TrainContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.webview)
    WebView mWebview;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTrainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_train; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
       initWebView(Constants.URL_TRAINING);
    }

    private void initWebView(String url) {
        mWebview.loadUrl(url);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        //设置WebViewClient
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }

            //加载前
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                LogUtils.d("开始加载！！");
            }

            //加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                LogUtils.d("加载完成...");
            }
        });
        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                LogUtils.d(title);
            }

            //进度显示
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    LogUtils.d(newProgress + "%");
                } else {
                    LogUtils.d("100%");
                }
            }
        });
        WebSettings webSettings = mWebview.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript

        webSettings.setJavaScriptEnabled(true);


//支持插件

        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);


        //设置自适应屏幕，两者合用

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小

        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小


//缩放操作

        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。

        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放

        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件


//其他细节操作

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存

        webSettings.setAllowFileAccess(true); //设置可以访问文件

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片

        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebview.addJavascriptInterface(new AndroidToJs(), "androidObj");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    class AndroidToJs extends Object {
        @JavascriptInterface
        public void callByJS() {
            TrainActivity.this.finish();
        }
    }
}