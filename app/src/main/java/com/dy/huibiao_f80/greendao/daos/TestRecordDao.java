package com.dy.huibiao_f80.greendao.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.dy.huibiao_f80.greendao.TestRecord;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEST_RECORD".
*/
public class TestRecordDao extends AbstractDao<TestRecord, Long> {

    public static final String TABLENAME = "TEST_RECORD";

    /**
     * Properties of entity TestRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SysCode = new Property(1, String.class, "sysCode", false, "SYS_CODE");
        public final static Property Gallery = new Property(2, int.class, "gallery", false, "GALLERY");
        public final static Property Samplenum = new Property(3, String.class, "samplenum", false, "SAMPLENUM");
        public final static Property Samplename = new Property(4, String.class, "samplename", false, "SAMPLENAME");
        public final static Property Sampletype = new Property(5, String.class, "sampletype", false, "SAMPLETYPE");
        public final static Property FoodCode = new Property(6, String.class, "foodCode", false, "FOOD_CODE");
        public final static Property Symbol = new Property(7, String.class, "symbol", false, "SYMBOL");
        public final static Property Cov = new Property(8, String.class, "cov", false, "COV");
        public final static Property Cov_unit = new Property(9, String.class, "cov_unit", false, "COV_UNIT");
        public final static Property Stand_num = new Property(10, String.class, "stand_num", false, "STAND_NUM");
        public final static Property Prosecutedunits = new Property(11, String.class, "prosecutedunits", false, "PROSECUTEDUNITS");
        public final static Property Prosecutedunits_adress = new Property(12, String.class, "prosecutedunits_adress", false, "PROSECUTEDUNITS_ADRESS");
        public final static Property Dilutionratio = new Property(13, double.class, "dilutionratio", false, "DILUTIONRATIO");
        public final static Property Everyresponse = new Property(14, double.class, "everyresponse", false, "EVERYRESPONSE");
        public final static Property Controlvalue = new Property(15, String.class, "controlvalue", false, "CONTROLVALUE");
        public final static Property SerialNumber = new Property(16, String.class, "serialNumber", false, "SERIAL_NUMBER");
        public final static Property Testresult = new Property(17, String.class, "testresult", false, "TESTRESULT");
        public final static Property Decisionoutcome = new Property(18, String.class, "decisionoutcome", false, "DECISIONOUTCOME");
        public final static Property Inspector = new Property(19, String.class, "inspector", false, "INSPECTOR");
        public final static Property Testingtime = new Property(20, Long.class, "testingtime", false, "TESTINGTIME");
        public final static Property Longitude = new Property(21, double.class, "longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(22, double.class, "latitude", false, "LATITUDE");
        public final static Property Testsite = new Property(23, String.class, "testsite", false, "TESTSITE");
        public final static Property Test_method = new Property(24, String.class, "test_method", false, "TEST_METHOD");
        public final static Property Test_project = new Property(25, String.class, "test_project", false, "TEST_PROJECT");
        public final static Property Test_moudle = new Property(26, String.class, "test_moudle", false, "TEST_MOUDLE");
        public final static Property Isupload = new Property(27, int.class, "isupload", false, "ISUPLOAD");
        public final static Property Unique_testproject = new Property(28, String.class, "unique_testproject", false, "UNIQUE_TESTPROJECT");
        public final static Property Test_unit_name = new Property(29, String.class, "test_unit_name", false, "TEST_UNIT_NAME");
        public final static Property Test_unit_reserved = new Property(30, String.class, "test_unit_reserved", false, "TEST_UNIT_RESERVED");
        public final static Property Retest = new Property(31, int.class, "retest", false, "RETEST");
        public final static Property ParentSysCode = new Property(32, String.class, "parentSysCode", false, "PARENT_SYS_CODE");
        public final static Property MethodsDetectionLimit = new Property(33, String.class, "methodsDetectionLimit", false, "METHODS_DETECTION_LIMIT");
        public final static Property SamplingID = new Property(34, Long.class, "samplingID", false, "SAMPLING_ID");
    }


    public TestRecordDao(DaoConfig config) {
        super(config);
    }
    
    public TestRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEST_RECORD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SYS_CODE\" TEXT," + // 1: sysCode
                "\"GALLERY\" INTEGER NOT NULL ," + // 2: gallery
                "\"SAMPLENUM\" TEXT," + // 3: samplenum
                "\"SAMPLENAME\" TEXT," + // 4: samplename
                "\"SAMPLETYPE\" TEXT," + // 5: sampletype
                "\"FOOD_CODE\" TEXT," + // 6: foodCode
                "\"SYMBOL\" TEXT," + // 7: symbol
                "\"COV\" TEXT," + // 8: cov
                "\"COV_UNIT\" TEXT," + // 9: cov_unit
                "\"STAND_NUM\" TEXT," + // 10: stand_num
                "\"PROSECUTEDUNITS\" TEXT," + // 11: prosecutedunits
                "\"PROSECUTEDUNITS_ADRESS\" TEXT," + // 12: prosecutedunits_adress
                "\"DILUTIONRATIO\" REAL NOT NULL ," + // 13: dilutionratio
                "\"EVERYRESPONSE\" REAL NOT NULL ," + // 14: everyresponse
                "\"CONTROLVALUE\" TEXT," + // 15: controlvalue
                "\"SERIAL_NUMBER\" TEXT," + // 16: serialNumber
                "\"TESTRESULT\" TEXT," + // 17: testresult
                "\"DECISIONOUTCOME\" TEXT," + // 18: decisionoutcome
                "\"INSPECTOR\" TEXT," + // 19: inspector
                "\"TESTINGTIME\" INTEGER," + // 20: testingtime
                "\"LONGITUDE\" REAL NOT NULL ," + // 21: longitude
                "\"LATITUDE\" REAL NOT NULL ," + // 22: latitude
                "\"TESTSITE\" TEXT," + // 23: testsite
                "\"TEST_METHOD\" TEXT," + // 24: test_method
                "\"TEST_PROJECT\" TEXT," + // 25: test_project
                "\"TEST_MOUDLE\" TEXT," + // 26: test_moudle
                "\"ISUPLOAD\" INTEGER NOT NULL ," + // 27: isupload
                "\"UNIQUE_TESTPROJECT\" TEXT," + // 28: unique_testproject
                "\"TEST_UNIT_NAME\" TEXT," + // 29: test_unit_name
                "\"TEST_UNIT_RESERVED\" TEXT," + // 30: test_unit_reserved
                "\"RETEST\" INTEGER NOT NULL ," + // 31: retest
                "\"PARENT_SYS_CODE\" TEXT," + // 32: parentSysCode
                "\"METHODS_DETECTION_LIMIT\" TEXT," + // 33: methodsDetectionLimit
                "\"SAMPLING_ID\" INTEGER);"); // 34: samplingID
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEST_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TestRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sysCode = entity.getSysCode();
        if (sysCode != null) {
            stmt.bindString(2, sysCode);
        }
        stmt.bindLong(3, entity.getGallery());
 
        String samplenum = entity.getSamplenum();
        if (samplenum != null) {
            stmt.bindString(4, samplenum);
        }
 
        String samplename = entity.getSamplename();
        if (samplename != null) {
            stmt.bindString(5, samplename);
        }
 
        String sampletype = entity.getSampletype();
        if (sampletype != null) {
            stmt.bindString(6, sampletype);
        }
 
        String foodCode = entity.getFoodCode();
        if (foodCode != null) {
            stmt.bindString(7, foodCode);
        }
 
        String symbol = entity.getSymbol();
        if (symbol != null) {
            stmt.bindString(8, symbol);
        }
 
        String cov = entity.getCov();
        if (cov != null) {
            stmt.bindString(9, cov);
        }
 
        String cov_unit = entity.getCov_unit();
        if (cov_unit != null) {
            stmt.bindString(10, cov_unit);
        }
 
        String stand_num = entity.getStand_num();
        if (stand_num != null) {
            stmt.bindString(11, stand_num);
        }
 
        String prosecutedunits = entity.getProsecutedunits();
        if (prosecutedunits != null) {
            stmt.bindString(12, prosecutedunits);
        }
 
        String prosecutedunits_adress = entity.getProsecutedunits_adress();
        if (prosecutedunits_adress != null) {
            stmt.bindString(13, prosecutedunits_adress);
        }
        stmt.bindDouble(14, entity.getDilutionratio());
        stmt.bindDouble(15, entity.getEveryresponse());
 
        String controlvalue = entity.getControlvalue();
        if (controlvalue != null) {
            stmt.bindString(16, controlvalue);
        }
 
        String serialNumber = entity.getSerialNumber();
        if (serialNumber != null) {
            stmt.bindString(17, serialNumber);
        }
 
        String testresult = entity.getTestresult();
        if (testresult != null) {
            stmt.bindString(18, testresult);
        }
 
        String decisionoutcome = entity.getDecisionoutcome();
        if (decisionoutcome != null) {
            stmt.bindString(19, decisionoutcome);
        }
 
        String inspector = entity.getInspector();
        if (inspector != null) {
            stmt.bindString(20, inspector);
        }
 
        Long testingtime = entity.getTestingtime();
        if (testingtime != null) {
            stmt.bindLong(21, testingtime);
        }
        stmt.bindDouble(22, entity.getLongitude());
        stmt.bindDouble(23, entity.getLatitude());
 
        String testsite = entity.getTestsite();
        if (testsite != null) {
            stmt.bindString(24, testsite);
        }
 
        String test_method = entity.getTest_method();
        if (test_method != null) {
            stmt.bindString(25, test_method);
        }
 
        String test_project = entity.getTest_project();
        if (test_project != null) {
            stmt.bindString(26, test_project);
        }
 
        String test_moudle = entity.getTest_moudle();
        if (test_moudle != null) {
            stmt.bindString(27, test_moudle);
        }
        stmt.bindLong(28, entity.getIsupload());
 
        String unique_testproject = entity.getUnique_testproject();
        if (unique_testproject != null) {
            stmt.bindString(29, unique_testproject);
        }
 
        String test_unit_name = entity.getTest_unit_name();
        if (test_unit_name != null) {
            stmt.bindString(30, test_unit_name);
        }
 
        String test_unit_reserved = entity.getTest_unit_reserved();
        if (test_unit_reserved != null) {
            stmt.bindString(31, test_unit_reserved);
        }
        stmt.bindLong(32, entity.getRetest());
 
        String parentSysCode = entity.getParentSysCode();
        if (parentSysCode != null) {
            stmt.bindString(33, parentSysCode);
        }
 
        String methodsDetectionLimit = entity.getMethodsDetectionLimit();
        if (methodsDetectionLimit != null) {
            stmt.bindString(34, methodsDetectionLimit);
        }
 
        Long samplingID = entity.getSamplingID();
        if (samplingID != null) {
            stmt.bindLong(35, samplingID);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TestRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String sysCode = entity.getSysCode();
        if (sysCode != null) {
            stmt.bindString(2, sysCode);
        }
        stmt.bindLong(3, entity.getGallery());
 
        String samplenum = entity.getSamplenum();
        if (samplenum != null) {
            stmt.bindString(4, samplenum);
        }
 
        String samplename = entity.getSamplename();
        if (samplename != null) {
            stmt.bindString(5, samplename);
        }
 
        String sampletype = entity.getSampletype();
        if (sampletype != null) {
            stmt.bindString(6, sampletype);
        }
 
        String foodCode = entity.getFoodCode();
        if (foodCode != null) {
            stmt.bindString(7, foodCode);
        }
 
        String symbol = entity.getSymbol();
        if (symbol != null) {
            stmt.bindString(8, symbol);
        }
 
        String cov = entity.getCov();
        if (cov != null) {
            stmt.bindString(9, cov);
        }
 
        String cov_unit = entity.getCov_unit();
        if (cov_unit != null) {
            stmt.bindString(10, cov_unit);
        }
 
        String stand_num = entity.getStand_num();
        if (stand_num != null) {
            stmt.bindString(11, stand_num);
        }
 
        String prosecutedunits = entity.getProsecutedunits();
        if (prosecutedunits != null) {
            stmt.bindString(12, prosecutedunits);
        }
 
        String prosecutedunits_adress = entity.getProsecutedunits_adress();
        if (prosecutedunits_adress != null) {
            stmt.bindString(13, prosecutedunits_adress);
        }
        stmt.bindDouble(14, entity.getDilutionratio());
        stmt.bindDouble(15, entity.getEveryresponse());
 
        String controlvalue = entity.getControlvalue();
        if (controlvalue != null) {
            stmt.bindString(16, controlvalue);
        }
 
        String serialNumber = entity.getSerialNumber();
        if (serialNumber != null) {
            stmt.bindString(17, serialNumber);
        }
 
        String testresult = entity.getTestresult();
        if (testresult != null) {
            stmt.bindString(18, testresult);
        }
 
        String decisionoutcome = entity.getDecisionoutcome();
        if (decisionoutcome != null) {
            stmt.bindString(19, decisionoutcome);
        }
 
        String inspector = entity.getInspector();
        if (inspector != null) {
            stmt.bindString(20, inspector);
        }
 
        Long testingtime = entity.getTestingtime();
        if (testingtime != null) {
            stmt.bindLong(21, testingtime);
        }
        stmt.bindDouble(22, entity.getLongitude());
        stmt.bindDouble(23, entity.getLatitude());
 
        String testsite = entity.getTestsite();
        if (testsite != null) {
            stmt.bindString(24, testsite);
        }
 
        String test_method = entity.getTest_method();
        if (test_method != null) {
            stmt.bindString(25, test_method);
        }
 
        String test_project = entity.getTest_project();
        if (test_project != null) {
            stmt.bindString(26, test_project);
        }
 
        String test_moudle = entity.getTest_moudle();
        if (test_moudle != null) {
            stmt.bindString(27, test_moudle);
        }
        stmt.bindLong(28, entity.getIsupload());
 
        String unique_testproject = entity.getUnique_testproject();
        if (unique_testproject != null) {
            stmt.bindString(29, unique_testproject);
        }
 
        String test_unit_name = entity.getTest_unit_name();
        if (test_unit_name != null) {
            stmt.bindString(30, test_unit_name);
        }
 
        String test_unit_reserved = entity.getTest_unit_reserved();
        if (test_unit_reserved != null) {
            stmt.bindString(31, test_unit_reserved);
        }
        stmt.bindLong(32, entity.getRetest());
 
        String parentSysCode = entity.getParentSysCode();
        if (parentSysCode != null) {
            stmt.bindString(33, parentSysCode);
        }
 
        String methodsDetectionLimit = entity.getMethodsDetectionLimit();
        if (methodsDetectionLimit != null) {
            stmt.bindString(34, methodsDetectionLimit);
        }
 
        Long samplingID = entity.getSamplingID();
        if (samplingID != null) {
            stmt.bindLong(35, samplingID);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TestRecord readEntity(Cursor cursor, int offset) {
        TestRecord entity = new TestRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sysCode
            cursor.getInt(offset + 2), // gallery
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // samplenum
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // samplename
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sampletype
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // foodCode
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // symbol
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // cov
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // cov_unit
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // stand_num
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // prosecutedunits
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // prosecutedunits_adress
            cursor.getDouble(offset + 13), // dilutionratio
            cursor.getDouble(offset + 14), // everyresponse
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // controlvalue
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // serialNumber
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // testresult
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // decisionoutcome
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // inspector
            cursor.isNull(offset + 20) ? null : cursor.getLong(offset + 20), // testingtime
            cursor.getDouble(offset + 21), // longitude
            cursor.getDouble(offset + 22), // latitude
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // testsite
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // test_method
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // test_project
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // test_moudle
            cursor.getInt(offset + 27), // isupload
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // unique_testproject
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // test_unit_name
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // test_unit_reserved
            cursor.getInt(offset + 31), // retest
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // parentSysCode
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // methodsDetectionLimit
            cursor.isNull(offset + 34) ? null : cursor.getLong(offset + 34) // samplingID
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TestRecord entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSysCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGallery(cursor.getInt(offset + 2));
        entity.setSamplenum(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSamplename(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSampletype(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFoodCode(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSymbol(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCov(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCov_unit(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStand_num(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setProsecutedunits(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setProsecutedunits_adress(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setDilutionratio(cursor.getDouble(offset + 13));
        entity.setEveryresponse(cursor.getDouble(offset + 14));
        entity.setControlvalue(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setSerialNumber(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setTestresult(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setDecisionoutcome(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setInspector(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setTestingtime(cursor.isNull(offset + 20) ? null : cursor.getLong(offset + 20));
        entity.setLongitude(cursor.getDouble(offset + 21));
        entity.setLatitude(cursor.getDouble(offset + 22));
        entity.setTestsite(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setTest_method(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setTest_project(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setTest_moudle(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setIsupload(cursor.getInt(offset + 27));
        entity.setUnique_testproject(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setTest_unit_name(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setTest_unit_reserved(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setRetest(cursor.getInt(offset + 31));
        entity.setParentSysCode(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setMethodsDetectionLimit(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setSamplingID(cursor.isNull(offset + 34) ? null : cursor.getLong(offset + 34));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TestRecord entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TestRecord entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TestRecord entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
