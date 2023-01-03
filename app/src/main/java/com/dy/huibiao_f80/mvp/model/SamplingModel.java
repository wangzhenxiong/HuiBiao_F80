package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.Sampling;
import com.dy.huibiao_f80.greendao.daos.SamplingDao;
import com.dy.huibiao_f80.mvp.contract.SamplingContract;
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
public class SamplingModel extends BaseModel implements SamplingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SamplingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<Sampling>> seach(String startsampletime, String stopsampletime,String starttime, String stoptime, String testResult, boolean b) {
        return Observable.create(new ObservableOnSubscribe<List<Sampling>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Sampling>> emitter) throws Exception {
                QueryBuilder<Sampling> samplingQueryBuilder = DBHelper.getSamplingDao().queryBuilder();

                if (starttime.equals("无")||stoptime.isEmpty()){

                }else {
                    Long starte = DataUtils.getNowtimeYYIMMIDD(starttime);
                    Long stop = DataUtils.getNowtimeYYIMMIDD(stoptime);
                    LogUtils.d(starte+"  "+(stop+ 86400000));
                    samplingQueryBuilder = samplingQueryBuilder.where(SamplingDao.Properties.TestingTime.between(starte, stop + 86400000));
                }

                if (startsampletime.equals("无")||stopsampletime.isEmpty()){

                }else {
                    Long starte = DataUtils.getNowtimeYYIMMIDD(startsampletime);
                    Long stop = DataUtils.getNowtimeYYIMMIDD(stopsampletime);
                    LogUtils.d(starte+"  "+(stop+ 86400000));
                    samplingQueryBuilder = samplingQueryBuilder.where(SamplingDao.Properties.CreationTime.between(starte, stop + 86400000));
                }

                if (!testResult.equals("选择判定结果")){
                    samplingQueryBuilder=samplingQueryBuilder.where(SamplingDao.Properties.TestResult.eq(testResult));
                }
                List<Sampling> list = samplingQueryBuilder.orderDesc(SamplingDao.Properties.TestingTime).list();
                LogUtils.d(list);
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Sampling>> load(boolean b) {
        return Observable.create(new ObservableOnSubscribe<List<Sampling>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Sampling>> emitter) throws Exception {
                List<Sampling> list = DBHelper.getSamplingDao().queryBuilder().orderDesc(SamplingDao.Properties.CreationTime).list();
                LogUtils.d(list);
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}