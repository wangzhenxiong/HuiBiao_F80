package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.greendao.daos.TestRecordDao;
import com.dy.huibiao_f80.mvp.contract.RecordContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class RecordModel extends BaseModel implements RecordContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<TestRecord>> load(boolean start,String examinationId,String examinerId,String examId) {
        return Observable.create(new ObservableOnSubscribe<List<TestRecord>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<TestRecord>> emitter) throws Exception {
                QueryBuilder<TestRecord> testRecordQueryBuilder = DBHelper.getTestRecordDao().queryBuilder();
                if (start){
                    if (!examinationId.isEmpty()) {
                        testRecordQueryBuilder= testRecordQueryBuilder
                                .where(TestRecordDao.Properties.ExaminationId.eq(examinationId));
                    }
                    if (!examinerId.isEmpty()) {
                        testRecordQueryBuilder= testRecordQueryBuilder
                                .where(TestRecordDao.Properties.ExaminerId.eq(examinerId));
                    }
                    if (!examId.isEmpty()) {
                        testRecordQueryBuilder= testRecordQueryBuilder
                                .where(TestRecordDao.Properties.Exam_id.eq(examId));
                    }
                }else {
                    testRecordQueryBuilder= testRecordQueryBuilder
                            .where(TestRecordDao.Properties.ExaminationId.eq(""));
                    testRecordQueryBuilder= testRecordQueryBuilder
                            .where(TestRecordDao.Properties.ExaminerId.eq(""));
                    testRecordQueryBuilder= testRecordQueryBuilder
                            .where(TestRecordDao.Properties.ExaminerId.eq(""));
                }

                List<TestRecord> list = testRecordQueryBuilder.orderDesc(TestRecordDao.Properties.Testingtime).list();
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<String>> loadLocaProject(String keyword) {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                TreeSet<String> set=new TreeSet<String>();


                QueryBuilder<ProjectFGGD> jtjTestItemQueryBuilder = DBHelper.getProjectFGGDDao().queryBuilder();
                if (!keyword.isEmpty()){
                    jtjTestItemQueryBuilder=jtjTestItemQueryBuilder.where(ProjectFGGDDao.Properties.ProjectName.like("%"+keyword+"%"));
                }
                List<ProjectFGGD> items = jtjTestItemQueryBuilder.where(ProjectFGGDDao.Properties.Isdefault.eq(true)).list();
                for (int i = 0; i < items.size(); i++) {
                    ProjectFGGD e = items.get(i);
                    set.add(e.getProjectName());
                }
                QueryBuilder<ProjectJTJ> fggdTestItemQueryBuilder = DBHelper.getProjectJTJDao().queryBuilder();
                if (!keyword.isEmpty()){
                    fggdTestItemQueryBuilder=fggdTestItemQueryBuilder.where(ProjectJTJDao.Properties.ProjectName.like("%"+keyword+"%"));
                }
                List<ProjectJTJ> value = fggdTestItemQueryBuilder.where(ProjectJTJDao.Properties.Isdefault.eq(true)).list();
                for (int i = 0; i < value.size(); i++) {
                    ProjectJTJ e = value.get(i);
                    set.add(e.getProjectName());
                }
                emitter.onNext(new ArrayList<>(set));
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<TestRecord>> seach(String testmoudle, String testproject, String jujdger,String examinationId,String examinerId,String examId) {
        return Observable.create(new ObservableOnSubscribe<List<TestRecord>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<TestRecord>> emitter) throws Exception {
                QueryBuilder<TestRecord> testRecordQueryBuilder = DBHelper.getTestRecordDao().queryBuilder();
                if (!testmoudle.equals("选择检测方式")) {
                    testRecordQueryBuilder=testRecordQueryBuilder.where(TestRecordDao.Properties.Test_moudle.eq(testmoudle));
                }
                if (!testproject.equals("选择检测项目")) {
                    testRecordQueryBuilder=testRecordQueryBuilder.where(TestRecordDao.Properties.Test_project.eq(testproject));
                }
                if (!jujdger.equals("选择判定结果")) {
                    if (jujdger.equals("其它")){
                        testRecordQueryBuilder=testRecordQueryBuilder
                                .where(TestRecordDao.Properties.Decisionoutcome.notEq("合格"))
                                .where(TestRecordDao.Properties.Decisionoutcome.notEq("不合格"))
                                .where(TestRecordDao.Properties.Decisionoutcome.notEq("无效"))
                                .where(TestRecordDao.Properties.Decisionoutcome.notEq("可疑"));
                    }else {
                        testRecordQueryBuilder=testRecordQueryBuilder.where(TestRecordDao.Properties.Decisionoutcome.eq(jujdger));
                    }
                }
                if (!examinationId.isEmpty()) {
                    testRecordQueryBuilder= testRecordQueryBuilder
                            .where(TestRecordDao.Properties.ExaminationId.eq(examinationId));
                }
                if (!examinerId.isEmpty()) {
                    testRecordQueryBuilder= testRecordQueryBuilder
                            .where(TestRecordDao.Properties.ExaminerId.eq(examinerId));
                }
                if (!examId.isEmpty()) {
                    testRecordQueryBuilder= testRecordQueryBuilder
                            .where(TestRecordDao.Properties.Exam_id.eq(examId));
                }
                List<TestRecord> list = testRecordQueryBuilder.orderDesc(TestRecordDao.Properties.Testingtime).list();
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}