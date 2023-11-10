package com.p020kl.commonbase.data.p021db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/* renamed from: com.kl.commonbase.data.db.DaoMaster */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 4;

    public static void createAllTables(Database database, boolean z) {
        BGEntityDao.createTable(database, z);
        BPEntityDao.createTable(database, z);
        BTEntityDao.createTable(database, z);
        ECGEntityDao.createTable(database, z);
        Spo2EntityDao.createTable(database, z);
        UserInfoEntityDao.createTable(database, z);
    }

    public static void dropAllTables(Database database, boolean z) {
        BGEntityDao.dropTable(database, z);
        BPEntityDao.dropTable(database, z);
        BTEntityDao.dropTable(database, z);
        ECGEntityDao.dropTable(database, z);
        Spo2EntityDao.dropTable(database, z);
        UserInfoEntityDao.dropTable(database, z);
    }

    public static DaoSession newDevSession(Context context, String str) {
        return new DaoMaster(new DevOpenHelper(context, str).getWritableDb()).newSession();
    }

    public DaoMaster(SQLiteDatabase sQLiteDatabase) {
        this((Database) new StandardDatabase(sQLiteDatabase));
    }

    public DaoMaster(Database database) {
        super(database, 4);
        registerDaoClass(BGEntityDao.class);
        registerDaoClass(BPEntityDao.class);
        registerDaoClass(BTEntityDao.class);
        registerDaoClass(ECGEntityDao.class);
        registerDaoClass(Spo2EntityDao.class);
        registerDaoClass(UserInfoEntityDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(this.f2141db, IdentityScopeType.Session, this.daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType identityScopeType) {
        return new DaoSession(this.f2141db, identityScopeType, this.daoConfigMap);
    }

    /* renamed from: com.kl.commonbase.data.db.DaoMaster$OpenHelper */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String str) {
            super(context, str, 4);
        }

        public OpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory) {
            super(context, str, cursorFactory, 4);
        }

        public void onCreate(Database database) {
            Log.i("greenDAO", "Creating tables for schema version 4");
            DaoMaster.createAllTables(database, false);
        }
    }

    /* renamed from: com.kl.commonbase.data.db.DaoMaster$DevOpenHelper */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String str) {
            super(context, str);
        }

        public DevOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory) {
            super(context, str, cursorFactory);
        }

        public void onUpgrade(Database database, int i, int i2) {
            Log.i("greenDAO", "Upgrading schema from version " + i + " to " + i2 + " by dropping all tables");
            DaoMaster.dropAllTables(database, true);
            onCreate(database);
        }
    }
}
