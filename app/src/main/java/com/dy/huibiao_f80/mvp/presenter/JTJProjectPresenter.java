package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;

import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.contract.JTJProjectContract;
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
public class JTJProjectPresenter extends BasePresenter<JTJProjectContract.Model, JTJProjectContract.View> {
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
    public JTJProjectPresenter(JTJProjectContract.Model model, JTJProjectContract.View rootView) {
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
        mModel.getJTJProject(keyword)
                .map(new Function<List<ProjectJTJ>, List<ProjectJTJ>>() {
                    @Override
                    public List<ProjectJTJ> apply(@NonNull List<ProjectJTJ> list) throws Exception {
                        Collections.sort(list, mComparator);
                        return list;
                    }
                }).doOnSubscribe(disposable -> {
            mRootView.showLoading();
        }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<ProjectJTJ>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<ProjectJTJ> list) {
                        LogUtils.d("排序完成");
                        //LogUtils.d(list);
                        mDateList.clear();
                        mDateList.addAll(list);
                        mAdapter.notifyDataSetChanged();


                    }
                });
    }*/
}