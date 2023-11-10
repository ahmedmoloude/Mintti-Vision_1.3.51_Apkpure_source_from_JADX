package org.greenrobot.greendao.test;

import android.app.Application;
import android.app.Instrumentation;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import java.util.Random;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.DbUtils;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

public abstract class DbTest extends AndroidTestCase {
    public static final String DB_NAME = "greendao-unittest-db.temp";
    private Application application;

    /* renamed from: db */
    protected Database f2147db;
    protected final boolean inMemory;
    protected final Random random;

    public DbTest() {
        this(true);
    }

    public DbTest(boolean z) {
        this.inMemory = z;
        this.random = new Random();
    }

    /* access modifiers changed from: protected */
    public void setUp() throws Exception {
        DbTest.super.setUp();
        this.f2147db = createDatabase();
    }

    public <T extends Application> T createApplication(Class<T> cls) {
        assertNull("Application already created", this.application);
        try {
            T newApplication = Instrumentation.newApplication(cls, getContext());
            newApplication.onCreate();
            this.application = newApplication;
            return newApplication;
        } catch (Exception e) {
            throw new RuntimeException("Could not create application " + cls, e);
        }
    }

    public void terminateApplication() {
        assertNotNull("Application not yet created", this.application);
        this.application.onTerminate();
        this.application = null;
    }

    public <T extends Application> T getApplication() {
        assertNotNull("Application not yet created", this.application);
        return this.application;
    }

    /* access modifiers changed from: protected */
    public Database createDatabase() {
        SQLiteDatabase sQLiteDatabase;
        if (this.inMemory) {
            sQLiteDatabase = SQLiteDatabase.create((SQLiteDatabase.CursorFactory) null);
        } else {
            getContext().deleteDatabase(DB_NAME);
            sQLiteDatabase = getContext().openOrCreateDatabase(DB_NAME, 0, (SQLiteDatabase.CursorFactory) null);
        }
        return new StandardDatabase(sQLiteDatabase);
    }

    /* access modifiers changed from: protected */
    public void tearDown() throws Exception {
        if (this.application != null) {
            terminateApplication();
        }
        this.f2147db.close();
        if (!this.inMemory) {
            getContext().deleteDatabase(DB_NAME);
        }
        DbTest.super.tearDown();
    }

    /* access modifiers changed from: protected */
    public void logTableDump(String str) {
        Database database = this.f2147db;
        if (database instanceof StandardDatabase) {
            DbUtils.logTableDump(((StandardDatabase) database).getSQLiteDatabase(), str);
            return;
        }
        DaoLog.m1121w("Table dump unsupported for " + this.f2147db);
    }
}
