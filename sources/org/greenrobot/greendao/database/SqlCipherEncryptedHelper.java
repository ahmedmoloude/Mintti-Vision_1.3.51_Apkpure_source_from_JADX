package org.greenrobot.greendao.database;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

class SqlCipherEncryptedHelper extends SQLiteOpenHelper implements DatabaseOpenHelper.EncryptedHelper {
    private final DatabaseOpenHelper delegate;

    public SqlCipherEncryptedHelper(DatabaseOpenHelper databaseOpenHelper, Context context, String str, int i, boolean z) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, i);
        this.delegate = databaseOpenHelper;
        if (z) {
            SQLiteDatabase.loadLibs(context);
        }
    }

    private Database wrap(SQLiteDatabase sQLiteDatabase) {
        return new EncryptedDatabase(sQLiteDatabase);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.delegate.onCreate(wrap(sQLiteDatabase));
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        this.delegate.onUpgrade(wrap(sQLiteDatabase), i, i2);
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        this.delegate.onOpen(wrap(sQLiteDatabase));
    }

    public Database getEncryptedReadableDb(String str) {
        return wrap(getReadableDatabase(str));
    }

    public Database getEncryptedReadableDb(char[] cArr) {
        return wrap(getReadableDatabase(cArr));
    }

    public Database getEncryptedWritableDb(String str) {
        return wrap(getWritableDatabase(str));
    }

    public Database getEncryptedWritableDb(char[] cArr) {
        return wrap(getWritableDatabase(cArr));
    }
}
