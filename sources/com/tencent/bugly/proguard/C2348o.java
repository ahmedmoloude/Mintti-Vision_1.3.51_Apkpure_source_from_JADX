package com.tencent.bugly.proguard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;

/* renamed from: com.tencent.bugly.proguard.o */
/* compiled from: BUGLY */
public abstract class C2348o {

    /* renamed from: id */
    public int f1909id;
    public String moduleName;
    public String version;
    public String versionKey;

    public abstract String[] getTables();

    public abstract void init(Context context, boolean z, BuglyStrategy buglyStrategy);

    public void onDbCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onServerStrategyChanged(StrategyBean strategyBean) {
    }

    public void onDbUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            if (getTables() != null) {
                for (String valueOf : getTables()) {
                    sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ".concat(String.valueOf(valueOf)));
                }
                onDbCreate(sQLiteDatabase);
            }
        } catch (Throwable th) {
            if (!C2265al.m608b(th)) {
                th.printStackTrace();
            }
        }
    }

    public void onDbDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            if (getTables() != null) {
                for (String valueOf : getTables()) {
                    sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ".concat(String.valueOf(valueOf)));
                }
                onDbCreate(sQLiteDatabase);
            }
        } catch (Throwable th) {
            if (!C2265al.m608b(th)) {
                th.printStackTrace();
            }
        }
    }
}
