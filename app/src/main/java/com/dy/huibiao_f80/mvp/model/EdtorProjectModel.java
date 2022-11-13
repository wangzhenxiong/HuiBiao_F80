package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.api.ProjectService;
import com.dy.huibiao_f80.bean.UpdateFileMessage;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.ui.widget.OutMoudle;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class EdtorProjectModel extends BaseModel implements EdtorProjectContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EdtorProjectModel(IRepositoryManager repositoryManager) {
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

    @Override
    public Observable<List<OutMoudle>> getJTJJXLs() {
        return Observable.create(new ObservableOnSubscribe<List<OutMoudle>>() {
            @Override
            public void subscribe(ObservableEmitter<List<OutMoudle>> emitter) throws Exception {
                List<OutMoudle> list = new ArrayList<>();
                List<ProjectJTJ> s = DBHelper.getProjectJTJDao().loadAll();
                list.add(new ProjectJTJ().toJxlTitle());
                for (int i = 0; i < s.size(); i++) {
                    ProjectJTJ simple33 = s.get(i);
                    list.add(simple33.toJxlString());
                }
                emitter.onNext(list);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<List<OutMoudle>> getFGGDJXLs() {
        return Observable.create(new ObservableOnSubscribe<List<OutMoudle>>() {
            @Override
            public void subscribe(ObservableEmitter<List<OutMoudle>> emitter) throws Exception {
                List<OutMoudle> list = new ArrayList<>();
                List<ProjectFGGD> s = DBHelper.getProjectFGGDDao().loadAll();
                list.add(new ProjectFGGD().toJxlTitle());
                for (int i = 0; i < s.size(); i++) {
                    ProjectFGGD simple33 = s.get(i);
                    list.add(simple33.toJxlString());
                }
                emitter.onNext(list);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<UpdateFileMessage> upgradeFile(String appName) {
        return mRepositoryManager
                .obtainRetrofitService(ProjectService.class)
                .upgradeFile(appName)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}