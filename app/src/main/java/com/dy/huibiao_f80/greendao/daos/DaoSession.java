package com.dy.huibiao_f80.greendao.daos;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.dy.huibiao_f80.greendao.Sampling;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.JTJPoint;
import com.dy.huibiao_f80.greendao.TestRecord;

import com.dy.huibiao_f80.greendao.daos.SamplingDao;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.greendao.daos.JTJPointDao;
import com.dy.huibiao_f80.greendao.daos.TestRecordDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig samplingDaoConfig;
    private final DaoConfig projectFGGDDaoConfig;
    private final DaoConfig projectJTJDaoConfig;
    private final DaoConfig jTJPointDaoConfig;
    private final DaoConfig testRecordDaoConfig;

    private final SamplingDao samplingDao;
    private final ProjectFGGDDao projectFGGDDao;
    private final ProjectJTJDao projectJTJDao;
    private final JTJPointDao jTJPointDao;
    private final TestRecordDao testRecordDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        samplingDaoConfig = daoConfigMap.get(SamplingDao.class).clone();
        samplingDaoConfig.initIdentityScope(type);

        projectFGGDDaoConfig = daoConfigMap.get(ProjectFGGDDao.class).clone();
        projectFGGDDaoConfig.initIdentityScope(type);

        projectJTJDaoConfig = daoConfigMap.get(ProjectJTJDao.class).clone();
        projectJTJDaoConfig.initIdentityScope(type);

        jTJPointDaoConfig = daoConfigMap.get(JTJPointDao.class).clone();
        jTJPointDaoConfig.initIdentityScope(type);

        testRecordDaoConfig = daoConfigMap.get(TestRecordDao.class).clone();
        testRecordDaoConfig.initIdentityScope(type);

        samplingDao = new SamplingDao(samplingDaoConfig, this);
        projectFGGDDao = new ProjectFGGDDao(projectFGGDDaoConfig, this);
        projectJTJDao = new ProjectJTJDao(projectJTJDaoConfig, this);
        jTJPointDao = new JTJPointDao(jTJPointDaoConfig, this);
        testRecordDao = new TestRecordDao(testRecordDaoConfig, this);

        registerDao(Sampling.class, samplingDao);
        registerDao(ProjectFGGD.class, projectFGGDDao);
        registerDao(ProjectJTJ.class, projectJTJDao);
        registerDao(JTJPoint.class, jTJPointDao);
        registerDao(TestRecord.class, testRecordDao);
    }
    
    public void clear() {
        samplingDaoConfig.clearIdentityScope();
        projectFGGDDaoConfig.clearIdentityScope();
        projectJTJDaoConfig.clearIdentityScope();
        jTJPointDaoConfig.clearIdentityScope();
        testRecordDaoConfig.clearIdentityScope();
    }

    public SamplingDao getSamplingDao() {
        return samplingDao;
    }

    public ProjectFGGDDao getProjectFGGDDao() {
        return projectFGGDDao;
    }

    public ProjectJTJDao getProjectJTJDao() {
        return projectJTJDao;
    }

    public JTJPointDao getJTJPointDao() {
        return jTJPointDao;
    }

    public TestRecordDao getTestRecordDao() {
        return testRecordDao;
    }

}
