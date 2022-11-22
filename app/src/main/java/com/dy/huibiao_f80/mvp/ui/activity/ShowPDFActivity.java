package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.di.component.DaggerShowPDFComponent;
import com.dy.huibiao_f80.mvp.contract.ShowPDFContract;
import com.dy.huibiao_f80.mvp.presenter.ShowPDFPresenter;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ShowPDFActivity extends BaseActivity<ShowPDFPresenter> implements ShowPDFContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.parent_layout)
    LinearLayout mParentLayout;
    @BindView(R.id.pdfView)
    PDFView mPdfview;
    @Inject
    AlertDialog mSportdialog;


    private String name;
    private String from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mToolbarTitle.setText(name);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShowPDFComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_showpdf; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Intent intent = getIntent();
        from = intent.getStringExtra("data");// 文件地址
        String type = intent.getStringExtra("type");//1 法律法规 2检测标准
        String where = intent.getStringExtra("where");//1本地 2网络

        if ("1".equals(where)) {
            //  "GB 2707-2016食品安全国家标准鲜（冻）畜、禽产品.pdf"
            final File path = FileUtils.getFileByPath(from);
            name = path.getName();
            getpdffile(path);

        } else if ("2".equals(where)) {
            if ("".equals(from)) {
                ArmsUtils.snackbarText("文件地址不存在");
                killMyself();
                return;
            }
            if (!from.endsWith(".pdf")) {
                ArmsUtils.snackbarText("文件格式不兼容，请修改为PDF格式文件");
                killMyself();
                return;
            }
            LogUtils.d(from);
            LogUtils.d(type);
            LogUtils.d(where);
            //mPdfview.fromUri(Uri.parse(from));
            showLoading();
            FileUtils.deleteDir("/mnt/internal_sd/dayuan/stand_laws.pdf");
            mPresenter.downLoadPDF(type, from);
        }

    }


    private void getpdffile(File path) {


        if (path != null && !"".equals(path)) {

            //mPdfname.setText(name);
            mToolbarTitle.setText(name);
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                    //判断PDF文件格式是否正确
                    emitter.onNext(check(path.getAbsolutePath()));
                    //emitter.onNext(true);
                    //check(path.getAbsolutePath());
                    // PdfDocument document=new PdfDocument();

                }
            }).doOnError(new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    ArmsUtils.snackbarText("打开pdf文件出错！请检查pdf文件是否损坏，路径是否正确！");
                    finish();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ErrorHandleSubscriber<Boolean>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean){
                                mPdfview
                                        .fromFile(path)
                                        // .showMinimap(false)
                                        .enableSwipe(true)
                                        .load();
                            } else {
                                ArmsUtils.snackbarText("pdf文件损坏");
                                finish();
                            }

                        }
                    });

        } else {
            ArmsUtils.snackbarText("暂无pdf文件！");
            finish();
        }

    }

    /**
     * 利用itext打开pdf文档
     */
    private static boolean check(String file) {
        boolean flag1 = false;
        int n = 0;
        try {
            Document document = new Document(new PdfReader(file).getPageSize(1));
            document.open();
            PdfReader reader = new PdfReader(file);
            n = reader.getNumberOfPages();
            if (n != 0) {
                flag1 = true;
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return flag1;

    }


    @Override
    public void showLoading() {
        if (!mSportdialog.isShowing()) {
            mSportdialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mSportdialog.isShowing()) {
            mSportdialog.dismiss();
        }
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
    public Context getActivity() {
        return this;
    }

    @Override
    public void success() {
        hideLoading();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final File path = FileUtils.getFileByPath("/mnt/internal_sd/dayuan/stand_laws.pdf");
                String[] split = from.split("/");
                name = split[split.length-1];
                getpdffile(path);
            }
        });
    }

    @Override
    public void fail() {
        hideLoading();
        ArmsUtils.snackbarText("查看失败");
        killMyself();
    }
}