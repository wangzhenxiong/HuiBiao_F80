package com.dy.huibiao_f80.mvp.presenter;

import android.app.AlertDialog;
import android.app.Application;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.RecordContract;
import com.dy.huibiao_f80.mvp.ui.adapter.DialogChoseProjectAdapter;
import com.dy.huibiao_f80.mvp.ui.adapter.TestRecrdAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class RecordPresenter extends BasePresenter<RecordContract.Model, RecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    List<TestRecord> testRecordList;
    @Inject
    TestRecrdAdapter testRecrdAdapter;
    private boolean isSeach;

    @Inject
    public RecordPresenter(RecordContract.Model model, RecordContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    public void load(String examinationId,String examinerId,String examId) {
        mModel.load(examinationId,examinerId,examId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<TestRecord>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<TestRecord> list) {
                        testRecordList.clear();
                        testRecordList.addAll(list);
                        testRecrdAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void makeDialogChoseProject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mRootView.getActivity());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("选择检测项目");
        View view2 = View.inflate(mRootView.getActivity(), R.layout.dialog_choseproject_layout, null);
        EditText keyword = (EditText) view2.findViewById(R.id.keyword);
        Button btnseach = (Button) view2.findViewById(R.id.btn_seach);
        RecyclerView recyclerView = (RecyclerView) view2.findViewById(R.id.re_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRootView.getActivity(), 1);
        ArmsUtils.configRecyclerView(recyclerView, gridLayoutManager);
        ArrayList<String> data = new ArrayList<>();
        DialogChoseProjectAdapter dialogChoseProjectAdapter = new DialogChoseProjectAdapter(R.layout.dialog_choseproject_layout_item, data);
        recyclerView.setAdapter(dialogChoseProjectAdapter);


        mModel.loadLocaProject("")
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(2, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                    @Override
                    public void onNext(List<String> messages) {
                        data.clear();
                        data.addAll(messages);
                        dialogChoseProjectAdapter.notifyDataSetChanged();

                    }
                });

        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSeach) {
                    isSeach = false;
                    keyword.setText("");
                    btnseach.setText("搜索");
                    mModel.loadLocaProject("")
                            .subscribeOn(Schedulers.io())
                            .retryWhen(new RetryWithDelay(2, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                            .doOnSubscribe(disposable -> {
                                mRootView.showLoading();
                            }).subscribeOn(AndroidSchedulers.mainThread())
                            .doFinally(() -> {
                                mRootView.hideLoading();
                            }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                            .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                                @Override
                                public void onNext(List<String> messages) {
                                    data.clear();
                                    data.addAll(messages);
                                    dialogChoseProjectAdapter.notifyDataSetChanged();

                                }
                            });
                } else {

                    String s = keyword.getText().toString();
                    if (s.isEmpty()) {
                        ArmsUtils.snackbarText("请输入检测项目名称");
                    } else {
                        isSeach = true;
                        mModel.loadLocaProject(s)
                                .subscribeOn(Schedulers.io())
                                .retryWhen(new RetryWithDelay(2, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                                .doOnSubscribe(disposable -> {
                                    mRootView.showLoading();
                                }).subscribeOn(AndroidSchedulers.mainThread())
                                .doFinally(() -> {
                                    mRootView.hideLoading();
                                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                                .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                                    @Override
                                    public void onNext(List<String> messages) {
                                        data.clear();
                                        data.addAll(messages);
                                        dialogChoseProjectAdapter.notifyDataSetChanged();

                                    }
                                });
                        btnseach.setText("取消");
                    }

                }
            }
        });


        builder.setView(view2);
        AlertDialog alertDialog = builder.create();
        dialogChoseProjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String s = data.get(position);
                mRootView.setChosedProject(s);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void seach( String testmoudle, String testproject, String jujdger,String examinationId,String examinerId,String examId) {
        mModel.seach(testmoudle, testproject, jujdger,examinationId,examinerId,examId).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<TestRecord>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<TestRecord> list) {
                        testRecordList.clear();
                        testRecordList.addAll(list);
                        testRecrdAdapter.notifyDataSetChanged();
                    }
                });
    }
}