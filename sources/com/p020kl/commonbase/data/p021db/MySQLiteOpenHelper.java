package com.p020kl.commonbase.data.p021db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.p020kl.commonbase.data.p021db.DaoMaster;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/* renamed from: com.kl.commonbase.data.db.MySQLiteOpenHelper */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory) {
        super(context, str, cursorFactory);
    }

    public void onUpgrade(Database database, int i, int i2) {
        MigrationHelper.migrate(database, (MigrationHelper.ReCreateAllTableListener) new MigrationHelper.ReCreateAllTableListener() {
            public void onCreateAllTables(Database database, boolean z) {
                DaoMaster.createAllTables(database, z);
            }

            public void onDropAllTables(Database database, boolean z) {
                DaoMaster.dropAllTables(database, z);
            }
        }, (Class<? extends AbstractDao<?, ?>>[]) new Class[]{UserInfoEntityDao.class, ECGEntityDao.class, BPEntityDao.class, BTEntityDao.class, Spo2EntityDao.class});
    }
}
