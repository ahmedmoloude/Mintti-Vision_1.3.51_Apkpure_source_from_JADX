package com.p020kl.commonbase.data.p021db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.p020kl.commonbase.bean.BTEntity;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* renamed from: com.kl.commonbase.data.db.BTEntityDao */
public class BTEntityDao extends AbstractDao<BTEntity, Long> {
    public static final String TABLENAME = "BTENTITY";

    /* renamed from: com.kl.commonbase.data.db.BTEntityDao$Properties */
    public static class Properties {
        public static final Property CreateTime = new Property(3, Long.TYPE, "createTime", false, "CREATE_TIME");
        public static final Property Day = new Property(9, Integer.TYPE, "day", false, "DAY");
        public static final Property DocId = new Property(6, String.class, "docId", false, "DOC_ID");
        public static final Property MacAddress = new Property(10, String.class, "macAddress", false, "MAC_ADDRESS");
        public static final Property ModifyTime = new Property(4, Long.TYPE, "modifyTime", false, "MODIFY_TIME");
        public static final Property Month = new Property(8, Integer.TYPE, "month", false, "MONTH");
        public static final Property Note = new Property(5, String.class, "note", false, "NOTE");
        public static final Property Temperature = new Property(2, Double.TYPE, "temperature", false, "TEMPERATURE");
        public static final Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public static final Property Year = new Property(7, Integer.TYPE, "year", false, "YEAR");
        public static final Property _id = new Property(0, Long.class, "_id", true, "_id");
    }

    /* access modifiers changed from: protected */
    public final boolean isEntityUpdateable() {
        return true;
    }

    public BTEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public BTEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        String str = z ? "IF NOT EXISTS " : "";
        database.execSQL("CREATE TABLE " + str + "\"BTENTITY\" (" + "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + "\"USER_ID\" TEXT," + "\"TEMPERATURE\" REAL NOT NULL ," + "\"CREATE_TIME\" INTEGER NOT NULL ," + "\"MODIFY_TIME\" INTEGER NOT NULL ," + "\"NOTE\" TEXT," + "\"DOC_ID\" TEXT," + "\"YEAR\" INTEGER NOT NULL ," + "\"MONTH\" INTEGER NOT NULL ," + "\"DAY\" INTEGER NOT NULL ," + "\"MAC_ADDRESS\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"BTENTITY\"");
        database.execSQL(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(DatabaseStatement databaseStatement, BTEntity bTEntity) {
        databaseStatement.clearBindings();
        Long l = bTEntity.get_id();
        if (l != null) {
            databaseStatement.bindLong(1, l.longValue());
        }
        String userId = bTEntity.getUserId();
        if (userId != null) {
            databaseStatement.bindString(2, userId);
        }
        databaseStatement.bindDouble(3, bTEntity.getTemperature());
        databaseStatement.bindLong(4, bTEntity.getCreateTime());
        databaseStatement.bindLong(5, bTEntity.getModifyTime());
        String note = bTEntity.getNote();
        if (note != null) {
            databaseStatement.bindString(6, note);
        }
        String docId = bTEntity.getDocId();
        if (docId != null) {
            databaseStatement.bindString(7, docId);
        }
        databaseStatement.bindLong(8, (long) bTEntity.getYear());
        databaseStatement.bindLong(9, (long) bTEntity.getMonth());
        databaseStatement.bindLong(10, (long) bTEntity.getDay());
        String macAddress = bTEntity.getMacAddress();
        if (macAddress != null) {
            databaseStatement.bindString(11, macAddress);
        }
    }

    /* access modifiers changed from: protected */
    public final void bindValues(SQLiteStatement sQLiteStatement, BTEntity bTEntity) {
        sQLiteStatement.clearBindings();
        Long l = bTEntity.get_id();
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String userId = bTEntity.getUserId();
        if (userId != null) {
            sQLiteStatement.bindString(2, userId);
        }
        sQLiteStatement.bindDouble(3, bTEntity.getTemperature());
        sQLiteStatement.bindLong(4, bTEntity.getCreateTime());
        sQLiteStatement.bindLong(5, bTEntity.getModifyTime());
        String note = bTEntity.getNote();
        if (note != null) {
            sQLiteStatement.bindString(6, note);
        }
        String docId = bTEntity.getDocId();
        if (docId != null) {
            sQLiteStatement.bindString(7, docId);
        }
        sQLiteStatement.bindLong(8, (long) bTEntity.getYear());
        sQLiteStatement.bindLong(9, (long) bTEntity.getMonth());
        sQLiteStatement.bindLong(10, (long) bTEntity.getDay());
        String macAddress = bTEntity.getMacAddress();
        if (macAddress != null) {
            sQLiteStatement.bindString(11, macAddress);
        }
    }

    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public BTEntity readEntity(Cursor cursor, int i) {
        Cursor cursor2 = cursor;
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 5;
        int i5 = i + 6;
        int i6 = i + 10;
        return new BTEntity(cursor2.isNull(i2) ? null : Long.valueOf(cursor2.getLong(i2)), cursor2.isNull(i3) ? null : cursor2.getString(i3), cursor2.getDouble(i + 2), cursor2.getLong(i + 3), cursor2.getLong(i + 4), cursor2.isNull(i4) ? null : cursor2.getString(i4), cursor2.isNull(i5) ? null : cursor2.getString(i5), cursor2.getInt(i + 7), cursor2.getInt(i + 8), cursor2.getInt(i + 9), cursor2.isNull(i6) ? null : cursor2.getString(i6));
    }

    public void readEntity(Cursor cursor, BTEntity bTEntity, int i) {
        int i2 = i + 0;
        String str = null;
        bTEntity.set_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        bTEntity.setUserId(cursor.isNull(i3) ? null : cursor.getString(i3));
        bTEntity.setTemperature(cursor.getDouble(i + 2));
        bTEntity.setCreateTime(cursor.getLong(i + 3));
        bTEntity.setModifyTime(cursor.getLong(i + 4));
        int i4 = i + 5;
        bTEntity.setNote(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 6;
        bTEntity.setDocId(cursor.isNull(i5) ? null : cursor.getString(i5));
        bTEntity.setYear(cursor.getInt(i + 7));
        bTEntity.setMonth(cursor.getInt(i + 8));
        bTEntity.setDay(cursor.getInt(i + 9));
        int i6 = i + 10;
        if (!cursor.isNull(i6)) {
            str = cursor.getString(i6);
        }
        bTEntity.setMacAddress(str);
    }

    /* access modifiers changed from: protected */
    public final Long updateKeyAfterInsert(BTEntity bTEntity, long j) {
        bTEntity.set_id(Long.valueOf(j));
        return Long.valueOf(j);
    }

    public Long getKey(BTEntity bTEntity) {
        if (bTEntity != null) {
            return bTEntity.get_id();
        }
        return null;
    }

    public boolean hasKey(BTEntity bTEntity) {
        return bTEntity.get_id() != null;
    }
}
