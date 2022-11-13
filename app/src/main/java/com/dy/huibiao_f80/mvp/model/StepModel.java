package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.mvp.contract.StepContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class StepModel extends BaseModel implements StepContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StepModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override
    public Observable<List<File>> getfiles(String path)  {
        return Observable.just(getfileslist(path)).subscribeOn(Schedulers.io());
    }
    public List<File> getfileslist(String path){
        List<File> fileList=new ArrayList<>();
        List<File> files = FileUtils.listFilesInDir(path);
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            String name = file.getName();
            if (name.endsWith(".pdf")){
                fileList.add(file);
            }
        }
        return fileList;
    }
}