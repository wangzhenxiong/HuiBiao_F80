package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.StartTestContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
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

@ActivityScope
public class StartTestModel extends BaseModel implements StartTestContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StartTestModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<? extends BaseProjectMessage>> getFGGDProject(String keyword) {
        return Observable.create(new ObservableOnSubscribe<List<? extends BaseProjectMessage>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<? extends BaseProjectMessage>> emitter) throws Exception {
                QueryBuilder<ProjectFGGD> projectFGGDQueryBuilder = DBHelper.getProjectFGGDDao().queryBuilder();
                if (null != keyword) {
                    projectFGGDQueryBuilder = projectFGGDQueryBuilder.where(ProjectFGGDDao.Properties.ProjectName.like("%" + keyword + "%"));
                }
                List<ProjectFGGD> list = projectFGGDQueryBuilder.where(ProjectFGGDDao.Properties.Isdefault.eq(true))
                        .where(ProjectFGGDDao.Properties.FinishState.eq(true)).build().list();
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<? extends BaseProjectMessage>> getJTJProject(String keyword) {
        return Observable.create(new ObservableOnSubscribe<List<? extends BaseProjectMessage>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<? extends BaseProjectMessage>> emitter) throws Exception {
                QueryBuilder<ProjectJTJ> projectJTJQueryBuilder = DBHelper.getProjectJTJDao().queryBuilder();
                if (null != keyword) {
                    projectJTJQueryBuilder = projectJTJQueryBuilder.where(ProjectJTJDao.Properties.ProjectName.like("%" + keyword + "%"));
                }
                List<ProjectJTJ> list = projectJTJQueryBuilder.where(ProjectJTJDao.Properties.Isdefault.eq(true))
                        .where(ProjectJTJDao.Properties.FinishState.eq(true)).build().list();
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}