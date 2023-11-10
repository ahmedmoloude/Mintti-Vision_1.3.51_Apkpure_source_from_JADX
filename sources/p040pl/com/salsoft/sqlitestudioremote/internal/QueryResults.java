package p040pl.com.salsoft.sqlitestudioremote.internal;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.OperationCanceledException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.QueryResults */
public class QueryResults {
    private List<String> columnNames;
    private List<HashMap<String, Object>> data;
    private ErrorCode errorCode = ErrorCode.SQLITE_OK;
    private String errorMessage;

    public QueryResults() {
    }

    public void readResults(Cursor cursor) {
        this.columnNames = Arrays.asList(cursor.getColumnNames());
        this.data = new ArrayList();
        boolean moveToFirst = cursor.moveToFirst();
        while (moveToFirst) {
            this.data.add(readRow(cursor));
            moveToFirst = cursor.moveToNext();
        }
    }

    public QueryResults(OperationCanceledException operationCanceledException) {
        this.errorMessage = operationCanceledException.getMessage();
        this.errorCode = ErrorCode.SQLITE_INTERRUPT;
    }

    public QueryResults(SQLiteException sQLiteException, ErrorCode errorCode2) {
        this.errorMessage = sQLiteException.getMessage();
        this.errorCode = errorCode2;
    }

    public boolean isError() {
        return this.errorCode != ErrorCode.SQLITE_OK;
    }

    public List<String> getColumnNames() {
        return this.columnNames;
    }

    public List<HashMap<String, Object>> getData() {
        return this.data;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    private HashMap<String, Object> readRow(Cursor cursor) {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            hashMap.put(this.columnNames.get(i), getValue(i, cursor));
        }
        return hashMap;
    }

    private Object getValue(int i, Cursor cursor) {
        int type = cursor.getType(i);
        if (type == 0) {
            return null;
        }
        if (type == 1) {
            return Long.valueOf(cursor.getLong(i));
        }
        if (type == 2) {
            return Double.valueOf(cursor.getDouble(i));
        }
        if (type == 3) {
            return cursor.getString(i);
        }
        if (type == 4) {
            return cursor.getBlob(i);
        }
        throw new SQLiteException("Unknown field type for column number: " + i);
    }
}
