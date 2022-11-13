package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.mvp.contract.VideoContract;
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
public class VideoModel extends BaseModel implements VideoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public VideoModel(IRepositoryManager repositoryManager) {
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
        List<File> files = FileUtils.listFilesInDir(path);
        List<File> files1 = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            String name = file.getName();
            if (name.endsWith(".mp4")){
                files1.add(file);
            }
        }
        return files1;
    }
}