package p040pl.com.salsoft.sqlitestudioremote.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.SQLiteStudioDbService */
public class SQLiteStudioDbService {
    private static final Pattern DOWNGRADE_PATT = Pattern.compile(".*downgrade\\s+database\\s+from\\s+version\\s+(\\d+)\\s+to\\s+(\\d+)");
    private Context context;
    private HashMap<String, SQLiteDatabase> managedDatabases = new HashMap<>();

    public SQLiteStudioDbService(Context context2) {
        this.context = context2.getApplicationContext();
    }

    public List<String> getDbList() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.context.databaseList()) {
            if (!str.endsWith("-journal")) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public boolean deleteDb(String str) {
        return this.context.deleteDatabase(str);
    }

    public void releaseAll() {
        for (SQLiteDatabase close : this.managedDatabases.values()) {
            close.close();
        }
        this.managedDatabases.clear();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00da, code lost:
        if (r0 == null) goto L_0x00df;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public p040pl.com.salsoft.sqlitestudioremote.internal.QueryResults exec(java.lang.String r3, java.lang.String r4) {
        /*
            r2 = this;
            android.database.sqlite.SQLiteDatabase r3 = r2.getDb(r3)
            r0 = 0
            android.database.Cursor r0 = r3.rawQuery(r4, r0)     // Catch:{ SQLiteAbortException -> 0x00d2, SQLiteAccessPermException -> 0x00c7, SQLiteBindOrColumnIndexOutOfRangeException -> 0x00bc, SQLiteBlobTooBigException -> 0x00b1, SQLiteCantOpenDatabaseException -> 0x00a6, SQLiteConstraintException -> 0x009b, SQLiteDatabaseCorruptException -> 0x0090, SQLiteDatabaseLockedException -> 0x0085, SQLiteDatatypeMismatchException -> 0x007a, SQLiteDiskIOException -> 0x006f, SQLiteDoneException -> 0x0063, SQLiteFullException -> 0x0057, SQLiteMisuseException -> 0x004b, SQLiteOutOfMemoryException -> 0x003f, SQLiteReadOnlyDatabaseException -> 0x0033, SQLiteTableLockedException -> 0x0027, SQLiteException -> 0x001b }
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r3 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ SQLiteAbortException -> 0x00d2, SQLiteAccessPermException -> 0x00c7, SQLiteBindOrColumnIndexOutOfRangeException -> 0x00bc, SQLiteBlobTooBigException -> 0x00b1, SQLiteCantOpenDatabaseException -> 0x00a6, SQLiteConstraintException -> 0x009b, SQLiteDatabaseCorruptException -> 0x0090, SQLiteDatabaseLockedException -> 0x0085, SQLiteDatatypeMismatchException -> 0x007a, SQLiteDiskIOException -> 0x006f, SQLiteDoneException -> 0x0063, SQLiteFullException -> 0x0057, SQLiteMisuseException -> 0x004b, SQLiteOutOfMemoryException -> 0x003f, SQLiteReadOnlyDatabaseException -> 0x0033, SQLiteTableLockedException -> 0x0027, SQLiteException -> 0x001b }
            r3.<init>()     // Catch:{ SQLiteAbortException -> 0x00d2, SQLiteAccessPermException -> 0x00c7, SQLiteBindOrColumnIndexOutOfRangeException -> 0x00bc, SQLiteBlobTooBigException -> 0x00b1, SQLiteCantOpenDatabaseException -> 0x00a6, SQLiteConstraintException -> 0x009b, SQLiteDatabaseCorruptException -> 0x0090, SQLiteDatabaseLockedException -> 0x0085, SQLiteDatatypeMismatchException -> 0x007a, SQLiteDiskIOException -> 0x006f, SQLiteDoneException -> 0x0063, SQLiteFullException -> 0x0057, SQLiteMisuseException -> 0x004b, SQLiteOutOfMemoryException -> 0x003f, SQLiteReadOnlyDatabaseException -> 0x0033, SQLiteTableLockedException -> 0x0027, SQLiteException -> 0x001b }
            r3.readResults(r0)     // Catch:{ SQLiteAbortException -> 0x00d2, SQLiteAccessPermException -> 0x00c7, SQLiteBindOrColumnIndexOutOfRangeException -> 0x00bc, SQLiteBlobTooBigException -> 0x00b1, SQLiteCantOpenDatabaseException -> 0x00a6, SQLiteConstraintException -> 0x009b, SQLiteDatabaseCorruptException -> 0x0090, SQLiteDatabaseLockedException -> 0x0085, SQLiteDatatypeMismatchException -> 0x007a, SQLiteDiskIOException -> 0x006f, SQLiteDoneException -> 0x0063, SQLiteFullException -> 0x0057, SQLiteMisuseException -> 0x004b, SQLiteOutOfMemoryException -> 0x003f, SQLiteReadOnlyDatabaseException -> 0x0033, SQLiteTableLockedException -> 0x0027, SQLiteException -> 0x001b }
            if (r0 == 0) goto L_0x00e0
            r0.close()
            goto L_0x00e0
        L_0x0018:
            r3 = move-exception
            goto L_0x00e1
        L_0x001b:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_ERROR     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x0027:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_LOCKED     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x0033:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_READONLY     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x003f:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_NOMEM     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x004b:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_MISUSE     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x0057:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_FULL     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x0063:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_DONE     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x006f:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_IOERR     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x007a:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_MISMATCH     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x0085:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_BUSY     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x0090:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_CORRUPT     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x009b:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_CONSTRAINT     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x00a6:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_CANTOPEN     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x00b1:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_TOOBIG     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x00bc:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_RANGE     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x00c7:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_PERM     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
            goto L_0x00dc
        L_0x00d2:
            r3 = move-exception
            pl.com.salsoft.sqlitestudioremote.internal.QueryResults r4 = new pl.com.salsoft.sqlitestudioremote.internal.QueryResults     // Catch:{ all -> 0x0018 }
            pl.com.salsoft.sqlitestudioremote.internal.ErrorCode r1 = p040pl.com.salsoft.sqlitestudioremote.internal.ErrorCode.SQLITE_ABORT     // Catch:{ all -> 0x0018 }
            r4.<init>(r3, r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x00df
        L_0x00dc:
            r0.close()
        L_0x00df:
            r3 = r4
        L_0x00e0:
            return r3
        L_0x00e1:
            if (r0 == 0) goto L_0x00e6
            r0.close()
        L_0x00e6:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: p040pl.com.salsoft.sqlitestudioremote.internal.SQLiteStudioDbService.exec(java.lang.String, java.lang.String):pl.com.salsoft.sqlitestudioremote.internal.QueryResults");
    }

    private SQLiteDatabase getDb(String str) {
        SQLiteDatabase sQLiteDatabase;
        if (this.managedDatabases.containsKey(str)) {
            return this.managedDatabases.get(str);
        }
        try {
            sQLiteDatabase = tryToGetDb(str, 1);
        } catch (SQLiteException e) {
            Matcher matcher = DOWNGRADE_PATT.matcher(e.getMessage());
            if (matcher.find()) {
                sQLiteDatabase = tryToGetDb(str, Integer.parseInt(matcher.group(1)));
            } else {
                throw e;
            }
        }
        this.managedDatabases.put(str, sQLiteDatabase);
        return sQLiteDatabase;
    }

    private SQLiteDatabase tryToGetDb(String str, int i) {
        return new SQLiteStudioDbOpenHelper(this.context, str, i).getWritableDatabase();
    }
}
