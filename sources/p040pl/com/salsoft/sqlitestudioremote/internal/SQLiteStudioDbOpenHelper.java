package p040pl.com.salsoft.sqlitestudioremote.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.SQLiteStudioDbOpenHelper */
public class SQLiteStudioDbOpenHelper extends SQLiteOpenHelper {
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public SQLiteStudioDbOpenHelper(Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public SQLiteStudioDbOpenHelper(Context context, String str, int i) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, i);
    }
}
