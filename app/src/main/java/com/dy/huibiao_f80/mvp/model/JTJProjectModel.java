package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.JTJProjectContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class JTJProjectModel extends BaseModel implements JTJProjectContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public JTJProjectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<ProjectJTJ>> getJTJProject(String keyword) {
        return Observable.create(new ObservableOnSubscribe<List<ProjectJTJ>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ProjectJTJ>> emitter) throws Exception {
                QueryBuilder<ProjectJTJ> projectJTJQueryBuilder = DBHelper.getProjectJTJDao().queryBuilder();
                if (null != keyword) {
                    projectJTJQueryBuilder = projectJTJQueryBuilder.where(ProjectJTJDao.Properties.ProjectName.like("%" + keyword + "%"));
                }
                List<ProjectJTJ> list = projectJTJQueryBuilder.where(ProjectJTJDao.Properties.Isdefault.eq(true)).build().list();
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}