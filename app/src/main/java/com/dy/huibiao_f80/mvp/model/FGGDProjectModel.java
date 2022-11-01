package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.mvp.contract.FGGDProjectContract;
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
public class FGGDProjectModel extends BaseModel implements FGGDProjectContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FGGDProjectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<ProjectFGGD>> getFGGDProject(String keyword) {
        return Observable.create(new ObservableOnSubscribe<List<ProjectFGGD>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ProjectFGGD>> emitter) throws Exception {
                QueryBuilder<ProjectFGGD> projectFGGDQueryBuilder = DBHelper.getProjectFGGDDao().queryBuilder();
                if (null != keyword) {
                    projectFGGDQueryBuilder = projectFGGDQueryBuilder.where(ProjectFGGDDao.Properties.ProjectName.like("%" + keyword + "%"));
                }
                List<ProjectFGGD> list = projectFGGDQueryBuilder.where(ProjectFGGDDao.Properties.Isdefault.eq(true)).build().list();
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}