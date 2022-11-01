package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;

import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.contract.FGGDProjectContract;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.adapter.SortAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@FragmentScope
public class FGGDProjectPresenter extends BasePresenter<FGGDProjectContract.Model, FGGDProjectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    PinyinComparator mComparator;
    @Inject
    List<BaseProjectMessage> mDateList;
    @Inject
    SortAdapter mAdapter;

    @Inject
    public FGGDProjectPresenter(FGGDProjectContract.Model model, FGGDProjectContract.View rootView) {
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

    /*public void getdata(String keyword) {
        LogUtils.d("开始加载");
        mModel.getFGGDProject(keyword)
                .map(new Function<List<ProjectFGGD>, List<ProjectFGGD>>() {
                    @Override
                    public List<ProjectFGGD> apply(@NonNull List<ProjectFGGD> list) throws Exception {
                        Collections.sort(list, mComparator);
                        return list;
                    }
                }).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<ProjectFGGD>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<ProjectFGGD> list) {
                        LogUtils.d("排序完成");
                        mDateList.clear();
                        mDateList.addAll(list);
                        mAdapter.notifyDataSetChanged();


                    }
                });
    }*/
}