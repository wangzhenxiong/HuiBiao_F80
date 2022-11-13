package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.mvp.contract.StandMessageContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class StandMessageModel extends BaseModel implements StandMessageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StandMessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<File>> getfiles(String path) {
        return Observable.just(getfileslist(path)).subscribeOn(Schedulers.io());
    }
    public List<File> getfileslist(String path) {
        //boolean b = FileUtils.isFileExists(path);
        FileUtils.makeRootDirectory(path);
        boolean b = FileUtils.createOrExistsFile(path);
        List<File> fileList = new ArrayList<>();
        if (b) {
            //ArmsUtils.snackbarText(path+"\r\n文件地址不存在或者创建失败");
            ArmsUtils.snackbarText(path+"\r\n文件地址不存在");
            return fileList;
        }

        List<File> files = FileUtils.listFilesInDir(path);
        if (null==files){
            ArmsUtils.snackbarText(path+"\r\n文件读取失败");
            return fileList;
        }
        LogUtils.d(path + "--" + files);
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            String name = file.getName();
            if (name.endsWith(".pdf")) {
                fileList.add(file);
            }
        }
        return fileList;
    }
}