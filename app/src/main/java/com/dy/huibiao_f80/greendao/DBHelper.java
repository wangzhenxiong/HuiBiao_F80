package com.dy.huibiao_f80.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.greendao.daos.DaoMaster;
import com.dy.huibiao_f80.greendao.daos.DaoSession;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.greendao.daos.SamplingDao;
import com.dy.huibiao_f80.greendao.daos.TestRecordDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * 　 ┏┓　  ┏┓+ +
 * 　┏┛┻━━ ━┛┻┓ + +
 * 　┃　　　　 ┃
 * 　┃　　　　 ┃  ++ + + +
 * 　┃████━████+
 * 　┃　　　　 ┃ +
 * 　┃　　┻　  ┃
 * 　┃　　　　 ┃ + +
 * 　┗━┓　  ┏━┛
 * 　  ┃　　┃
 * 　  ┃　　┃　　 + + +
 * 　  ┃　　┃
 * 　  ┃　　┃ + 神兽保佑,代码无bug
 * 　  ┃　　┃
 * 　  ┃　　┃　　+
 * 　  ┃　 　┗━━━┓ + +
 * 　　┃ 　　　　 ┣┓
 * 　　┃ 　　　 ┏┛
 * 　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　 ┃┫┫ ┃┫┫
 * 　　 ┗┻┛ ┗┻┛+ + + +
 *
 * @author: wangzhenxiong
 * @data: 10/11/22 3:08 PM
 * Description:
 */
public class DBHelper extends DaoMaster.OpenHelper{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }
        , TestRecordDao.class
        , ProjectJTJDao.class
        , ProjectFGGDDao.class
        , SamplingDao.class
        );
    }

    private static DBHelper helper;

    public static TestRecordDao getTestRecordDao() {
        if (null == helper) {
            helper= new DBHelper(MyAppLocation.myAppLocation, "f80.db", null);
        }
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getTestRecordDao();
    }
    public static ProjectJTJDao getProjectJTJDao() {
        if (null == helper) {
            helper= new DBHelper(MyAppLocation.myAppLocation, "f80.db", null);
        }
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getProjectJTJDao();
    }

    public static ProjectFGGDDao getProjectFGGDDao() {
        if (null == helper) {
            helper= new DBHelper(MyAppLocation.myAppLocation, "f80.db", null);
        }
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getProjectFGGDDao();
    }

    public static SamplingDao getSamplingDao() {
        if (null == helper) {
            helper= new DBHelper(MyAppLocation.myAppLocation, "f80.db", null);
        }
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getSamplingDao();
    }

}
