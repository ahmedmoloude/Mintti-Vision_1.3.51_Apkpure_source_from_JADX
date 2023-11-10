package com.github.yuweiguocn.library.greendao;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.itextpdf.text.html.HtmlTags;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

public final class MigrationHelper {
    public static boolean DEBUG = false;
    private static final String SQLITE_MASTER = "sqlite_master";
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";
    private static String TAG = "MigrationHelper";
    private static WeakReference<ReCreateAllTableListener> weakListener;

    public interface ReCreateAllTableListener {
        void onCreateAllTables(Database database, boolean z);

        void onDropAllTables(Database database, boolean z);
    }

    public static void migrate(SQLiteDatabase sQLiteDatabase, Class<? extends AbstractDao<?, ?>>... clsArr) {
        printLog("【The Old Database Version】" + sQLiteDatabase.getVersion());
        migrate((Database) new StandardDatabase(sQLiteDatabase), clsArr);
    }

    public static void migrate(SQLiteDatabase sQLiteDatabase, ReCreateAllTableListener reCreateAllTableListener, Class<? extends AbstractDao<?, ?>>... clsArr) {
        weakListener = new WeakReference<>(reCreateAllTableListener);
        migrate(sQLiteDatabase, clsArr);
    }

    public static void migrate(Database database, ReCreateAllTableListener reCreateAllTableListener, Class<? extends AbstractDao<?, ?>>... clsArr) {
        weakListener = new WeakReference<>(reCreateAllTableListener);
        migrate(database, clsArr);
    }

    public static void migrate(Database database, Class<? extends AbstractDao<?, ?>>... clsArr) {
        printLog("【Generate temp table】start");
        generateTempTables(database, clsArr);
        printLog("【Generate temp table】complete");
        WeakReference<ReCreateAllTableListener> weakReference = weakListener;
        ReCreateAllTableListener reCreateAllTableListener = weakReference != null ? (ReCreateAllTableListener) weakReference.get() : null;
        if (reCreateAllTableListener != null) {
            reCreateAllTableListener.onDropAllTables(database, true);
            printLog("【Drop all table by listener】");
            reCreateAllTableListener.onCreateAllTables(database, false);
            printLog("【Create all table by listener】");
        } else {
            dropAllTables(database, true, clsArr);
            createAllTables(database, false, clsArr);
        }
        printLog("【Restore data】start");
        restoreData(database, clsArr);
        printLog("【Restore data】complete");
    }

    private static void generateTempTables(Database database, Class<? extends AbstractDao<?, ?>>... clsArr) {
        for (Class<? extends AbstractDao<?, ?>> daoConfig : clsArr) {
            String str = null;
            DaoConfig daoConfig2 = new DaoConfig(database, daoConfig);
            String str2 = daoConfig2.tablename;
            if (!isTableExists(database, false, str2)) {
                printLog("【New Table】" + str2);
            } else {
                try {
                    str = daoConfig2.tablename.concat("_TEMP");
                    database.execSQL("DROP TABLE IF EXISTS " + str + ";");
                    database.execSQL("CREATE TEMPORARY TABLE " + str + " AS SELECT * FROM `" + str2 + "`;");
                    StringBuilder sb = new StringBuilder();
                    sb.append("【Table】");
                    sb.append(str2);
                    sb.append("\n ---Columns-->");
                    sb.append(getColumnsStr(daoConfig2));
                    printLog(sb.toString());
                    printLog("【Generate temp table】" + str);
                } catch (SQLException e) {
                    Log.e(TAG, "【Failed to generate temp table】" + str, e);
                }
            }
        }
    }

    private static boolean isTableExists(Database database, boolean z, String str) {
        int i;
        if (database == null || TextUtils.isEmpty(str)) {
            return false;
        }
        Cursor cursor = null;
        try {
            Cursor rawQuery = database.rawQuery("SELECT COUNT(*) FROM `" + (z ? SQLITE_TEMP_MASTER : SQLITE_MASTER) + "` WHERE type = ? AND name = ?", new String[]{HtmlTags.TABLE, str});
            if (rawQuery != null) {
                if (rawQuery.moveToFirst()) {
                    i = rawQuery.getInt(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    if (i > 0) {
                        return true;
                    }
                    return false;
                }
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            i = 0;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private static String getColumnsStr(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "no columns";
        }
        StringBuilder sb = new StringBuilder();
        for (String append : daoConfig.allColumns) {
            sb.append(append);
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static void dropAllTables(Database database, boolean z, Class<? extends AbstractDao<?, ?>>... clsArr) {
        reflectMethod(database, "dropTable", z, clsArr);
        printLog("【Drop all table by reflect】");
    }

    private static void createAllTables(Database database, boolean z, Class<? extends AbstractDao<?, ?>>... clsArr) {
        reflectMethod(database, "createTable", z, clsArr);
        printLog("【Create all table by reflect】");
    }

    private static void reflectMethod(Database database, String str, boolean z, Class<? extends AbstractDao<?, ?>>... clsArr) {
        if (clsArr.length >= 1) {
            try {
                for (Class<? extends AbstractDao<?, ?>> declaredMethod : clsArr) {
                    declaredMethod.getDeclaredMethod(str, new Class[]{Database.class, Boolean.TYPE}).invoke((Object) null, new Object[]{database, Boolean.valueOf(z)});
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
        }
    }

    private static void restoreData(Database database, Class<? extends AbstractDao<?, ?>>... clsArr) {
        String str;
        for (Class<? extends AbstractDao<?, ?>> daoConfig : clsArr) {
            DaoConfig daoConfig2 = new DaoConfig(database, daoConfig);
            String str2 = daoConfig2.tablename;
            String concat = daoConfig2.tablename.concat("_TEMP");
            if (isTableExists(database, true, concat)) {
                try {
                    List<TableInfo> access$000 = TableInfo.getTableInfo(database, str2);
                    List<TableInfo> access$0002 = TableInfo.getTableInfo(database, concat);
                    ArrayList arrayList = new ArrayList(access$000.size());
                    ArrayList arrayList2 = new ArrayList(access$000.size());
                    for (TableInfo tableInfo : access$0002) {
                        if (access$000.contains(tableInfo)) {
                            String str3 = '`' + tableInfo.name + '`';
                            arrayList2.add(str3);
                            arrayList.add(str3);
                        }
                    }
                    for (TableInfo tableInfo2 : access$000) {
                        if (tableInfo2.notnull && !access$0002.contains(tableInfo2)) {
                            String str4 = '`' + tableInfo2.name + '`';
                            arrayList2.add(str4);
                            if (tableInfo2.dfltValue != null) {
                                str = "'" + tableInfo2.dfltValue + "' AS ";
                            } else {
                                str = "'' AS ";
                            }
                            arrayList.add(str + str4);
                        }
                    }
                    if (arrayList2.size() != 0) {
                        database.execSQL("REPLACE INTO `" + str2 + "` (" + TextUtils.join(",", arrayList2) + ") SELECT " + TextUtils.join(",", arrayList) + " FROM " + concat + ";");
                        StringBuilder sb = new StringBuilder();
                        sb.append("【Restore data】 to ");
                        sb.append(str2);
                        printLog(sb.toString());
                    }
                    database.execSQL("DROP TABLE " + concat);
                    printLog("【Drop temp table】" + concat);
                } catch (SQLException e) {
                    Log.e(TAG, "【Failed to restore data from temp table 】" + concat, e);
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v4, types: [java.util.List<java.lang.String>] */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0052  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<java.lang.String> getColumns(org.greenrobot.greendao.database.Database r3, java.lang.String r4) {
        /*
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0041 }
            r1.<init>()     // Catch:{ Exception -> 0x0041 }
            java.lang.String r2 = "SELECT * FROM "
            r1.append(r2)     // Catch:{ Exception -> 0x0041 }
            r1.append(r4)     // Catch:{ Exception -> 0x0041 }
            java.lang.String r4 = " limit 0"
            r1.append(r4)     // Catch:{ Exception -> 0x0041 }
            java.lang.String r4 = r1.toString()     // Catch:{ Exception -> 0x0041 }
            android.database.Cursor r3 = r3.rawQuery(r4, r0)     // Catch:{ Exception -> 0x0041 }
            if (r3 == 0) goto L_0x0032
            int r4 = r3.getColumnCount()     // Catch:{ Exception -> 0x002f, all -> 0x002c }
            if (r4 <= 0) goto L_0x0032
            java.lang.String[] r4 = r3.getColumnNames()     // Catch:{ Exception -> 0x002f, all -> 0x002c }
            java.util.List r0 = java.util.Arrays.asList(r4)     // Catch:{ Exception -> 0x002f, all -> 0x002c }
            goto L_0x0032
        L_0x002c:
            r4 = move-exception
            r0 = r3
            goto L_0x0050
        L_0x002f:
            r4 = move-exception
            r0 = r3
            goto L_0x0042
        L_0x0032:
            if (r3 == 0) goto L_0x0037
            r3.close()
        L_0x0037:
            if (r0 != 0) goto L_0x004f
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            goto L_0x004f
        L_0x003f:
            r4 = move-exception
            goto L_0x0050
        L_0x0041:
            r4 = move-exception
        L_0x0042:
            r4.printStackTrace()     // Catch:{ all -> 0x003f }
            if (r0 == 0) goto L_0x004a
            r0.close()
        L_0x004a:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
        L_0x004f:
            return r0
        L_0x0050:
            if (r0 == 0) goto L_0x0055
            r0.close()
        L_0x0055:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.yuweiguocn.library.greendao.MigrationHelper.getColumns(org.greenrobot.greendao.database.Database, java.lang.String):java.util.List");
    }

    /* access modifiers changed from: private */
    public static void printLog(String str) {
        if (DEBUG) {
            Log.d(TAG, str);
        }
    }

    private static class TableInfo {
        int cid;
        String dfltValue;
        String name;
        boolean notnull;

        /* renamed from: pk */
        boolean f320pk;
        String type;

        private TableInfo() {
        }

        public boolean equals(Object obj) {
            return this == obj || (obj != null && getClass() == obj.getClass() && this.name.equals(((TableInfo) obj).name));
        }

        public String toString() {
            return "TableInfo{cid=" + this.cid + ", name='" + this.name + '\'' + ", type='" + this.type + '\'' + ", notnull=" + this.notnull + ", dfltValue='" + this.dfltValue + '\'' + ", pk=" + this.f320pk + '}';
        }

        /* access modifiers changed from: private */
        public static List<TableInfo> getTableInfo(Database database, String str) {
            String str2 = "PRAGMA table_info(`" + str + "`)";
            MigrationHelper.printLog(str2);
            Cursor rawQuery = database.rawQuery(str2, (String[]) null);
            if (rawQuery == null) {
                return new ArrayList();
            }
            ArrayList arrayList = new ArrayList();
            while (rawQuery.moveToNext()) {
                TableInfo tableInfo = new TableInfo();
                boolean z = false;
                tableInfo.cid = rawQuery.getInt(0);
                tableInfo.name = rawQuery.getString(1);
                tableInfo.type = rawQuery.getString(2);
                tableInfo.notnull = rawQuery.getInt(3) == 1;
                tableInfo.dfltValue = rawQuery.getString(4);
                if (rawQuery.getInt(5) == 1) {
                    z = true;
                }
                tableInfo.f320pk = z;
                arrayList.add(tableInfo);
            }
            rawQuery.close();
            return arrayList;
        }
    }
}
