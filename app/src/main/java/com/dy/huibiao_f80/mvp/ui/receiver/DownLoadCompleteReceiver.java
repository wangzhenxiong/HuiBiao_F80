package com.dy.huibiao_f80.mvp.ui.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.apkfuns.logutils.LogUtils;
import com.jess.arms.BuildConfig;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;


public class DownLoadCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.d("onReceive");
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
            //在广播中取出下载任务的id
            LogUtils.d(BuildConfig.FLAVOR+"已经下载完成");
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            ArmsUtils.snackbarText(BuildConfig.FLAVOR+"已经下载完成");
            DownloadManager.Query query=new DownloadManager.Query();
            DownloadManager dm= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            query.setFilterById(id);
            Cursor c = dm.query(query);
            if (c!=null){
                try {
                    if (c.moveToFirst()){
                        //获取文件下载路径
                        String filename= c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                        LogUtils.d(filename);
                        int status = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                        if (status== DownloadManager.STATUS_SUCCESSFUL){
                            //启动更新
                            Uri uri = Uri.fromFile(new File(filename));
                            if (uri!=null){
                                Intent install=new Intent(Intent.ACTION_VIEW);
                                install.setDataAndType(uri,"application/vnd.android.package-archive");
                                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(install);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return;
                }finally {
                    c.close();
                }

            }
        }
    }
}

