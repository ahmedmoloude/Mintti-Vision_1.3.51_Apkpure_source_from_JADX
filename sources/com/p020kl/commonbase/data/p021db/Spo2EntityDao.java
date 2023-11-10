package com.p020kl.commonbase.data.p021db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.p020kl.commonbase.bean.Spo2Entity;
import com.p020kl.commonbase.constants.Constants;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* renamed from: com.kl.commonbase.data.db.Spo2EntityDao */
public class Spo2EntityDao extends AbstractDao<Spo2Entity, Long> {
    public static final String TABLENAME = "SPO2_ENTITY";

    /* renamed from: com.kl.commonbase.data.db.Spo2EntityDao$Properties */
    public static class Properties {
        public static final Property CreateTime = new Property(4, Long.TYPE, "createTime", false, "CREATE_TIME");
        public static final Property Day = new Property(10, Integer.TYPE, "day", false, "DAY");
        public static final Property DocId = new Property(7, String.class, "docId", false, "DOC_ID");
        public static final Property HeartRate = new Property(2, Integer.TYPE, "heartRate", false, "HEART_RATE");
        public static final Property MacAddress = new Property(11, String.class, "macAddress", false, "MAC_ADDRESS");
        public static final Property ModifyTime = new Property(5, Long.TYPE, "modifyTime", false, "MODIFY_TIME");
        public static final Property Month = new Property(9, Integer.TYPE, "month", false, "MONTH");
        public static final Property Note = new Property(6, String.class, "note", false, "NOTE");
        public static final Property Spo2 = new Property(3, Double.TYPE, Constants.SPO2_TYPE, false, Constants.SPO2);
        public static final Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public static final Property Year = new Property(8, Integer.TYPE, "year", false, "YEAR");
        public static final Property _id = new Property(0, Long.class, "_id", true, "_id");
    }

    /* access modifiers changed from: protected */
    public final boolean isEntityUpdateable() {
        return true;
    }

    public Spo2EntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public Spo2EntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        String str = z ? "IF NOT EXISTS " : "";
        database.execSQL("CREATE TABLE " + str + "\"SPO2_ENTITY\" (" + "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + "\"USER_ID\" TEXT," + "\"HEART_RATE\" INTEGER NOT NULL ," + "\"SPO2\" REAL NOT NULL ," + "\"CREATE_TIME\" INTEGER NOT NULL ," + "\"MODIFY_TIME\" INTEGER NOT NULL ," + "\"NOTE\" TEXT," + "\"DOC_ID\" TEXT," + "\"YEAR\" INTEGER NOT NULL ," + "\"MONTH\" INTEGER NOT NULL ," + "\"DAY\" INTEGER NOT NULL ," + "\"MAC_ADDRESS\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"SPO2_ENTITY\"");
        database.execSQL(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(DatabaseStatement databaseStatement, Spo2Entity spo2Entity) {
        databaseStatement.clearBindings();
        Long l = spo2Entity.get_id();
        if (l != null) {
            databaseStatement.bindLong(1, l.longValue());
        }
        String userId = spo2Entity.getUserId();
        if (userId != null) {
            databaseStatement.bindString(2, userId);
        }
        databaseStatement.bindLong(3, (long) spo2Entity.getHeartRate());
        databaseStatement.bindDouble(4, spo2Entity.getSpo2());
        databaseStatement.bindLong(5, spo2Entity.getCreateTime());
        databaseStatement.bindLong(6, spo2Entity.getModifyTime());
        String note = spo2Entity.getNote();
        if (note != null) {
            databaseStatement.bindString(7, note);
        }
        String docId = spo2Entity.getDocId();
        if (docId != null) {
            databaseStatement.bindString(8, docId);
        }
        databaseStatement.bindLong(9, (long) spo2Entity.getYear());
        databaseStatement.bindLong(10, (long) spo2Entity.getMonth());
        databaseStatement.bindLong(11, (long) spo2Entity.getDay());
        String macAddress = spo2Entity.getMacAddress();
        if (macAddress != null) {
            databaseStatement.bindString(12, macAddress);
        }
    }

    /* access modifiers changed from: protected */
    public final void bindValues(SQLiteStatement sQLiteStatement, Spo2Entity spo2Entity) {
        sQLiteStatement.clearBindings();
        Long l = spo2Entity.get_id();
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String userId = spo2Entity.getUserId();
        if (userId != null) {
            sQLiteStatement.bindString(2, userId);
        }
        sQLiteStatement.bindLong(3, (long) spo2Entity.getHeartRate());
        sQLiteStatement.bindDouble(4, spo2Entity.getSpo2());
        sQLiteStatement.bindLong(5, spo2Entity.getCreateTime());
        sQLiteStatement.bindLong(6, spo2Entity.getModifyTime());
        String note = spo2Entity.getNote();
        if (note != null) {
            sQLiteStatement.bindString(7, note);
        }
        String docId = spo2Entity.getDocId();
        if (docId != null) {
            sQLiteStatement.bindString(8, docId);
        }
        sQLiteStatement.bindLong(9, (long) spo2Entity.getYear());
        sQLiteStatement.bindLong(10, (long) spo2Entity.getMonth());
        sQLiteStatement.bindLong(11, (long) spo2Entity.getDay());
        String macAddress = spo2Entity.getMacAddress();
        if (macAddress != null) {
            sQLiteStatement.bindString(12, macAddress);
        }
    }

    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public Spo2Entity readEntity(Cursor cursor, int i) {
        Cursor cursor2 = cursor;
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 6;
        int i5 = i + 7;
        int i6 = i + 11;
        return new Spo2Entity(cursor2.isNull(i2) ? null : Long.valueOf(cursor2.getLong(i2)), cursor2.isNull(i3) ? null : cursor2.getString(i3), cursor2.getInt(i + 2), cursor2.getDouble(i + 3), cursor2.getLong(i + 4), cursor2.getLong(i + 5), cursor2.isNull(i4) ? null : cursor2.getString(i4), cursor2.isNull(i5) ? null : cursor2.getString(i5), cursor2.getInt(i + 8), cursor2.getInt(i + 9), cursor2.getInt(i + 10), cursor2.isNull(i6) ? null : cursor2.getString(i6));
    }

    public void readEntity(Cursor cursor, Spo2Entity spo2Entity, int i) {
        int i2 = i + 0;
        String str = null;
        spo2Entity.set_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        spo2Entity.setUserId(cursor.isNull(i3) ? null : cursor.getString(i3));
        spo2Entity.setHeartRate(cursor.getInt(i + 2));
        spo2Entity.setSpo2(cursor.getDouble(i + 3));
        spo2Entity.setCreateTime(cursor.getLong(i + 4));
        spo2Entity.setModifyTime(cursor.getLong(i + 5));
        int i4 = i + 6;
        spo2Entity.setNote(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 7;
        spo2Entity.setDocId(cursor.isNull(i5) ? null : cursor.getString(i5));
        spo2Entity.setYear(cursor.getInt(i + 8));
        spo2Entity.setMonth(cursor.getInt(i + 9));
        spo2Entity.setDay(cursor.getInt(i + 10));
        int i6 = i + 11;
        if (!cursor.isNull(i6)) {
            str = cursor.getString(i6);
        }
        spo2Entity.setMacAddress(str);
    }

    /* access modifiers changed from: protected */
    public final Long updateKeyAfterInsert(Spo2Entity spo2Entity, long j) {
        spo2Entity.set_id(Long.valueOf(j));
        return Long.valueOf(j);
    }

    public Long getKey(Spo2Entity spo2Entity) {
        if (spo2Entity != null) {
            return spo2Entity.get_id();
        }
        return null;
    }

    public boolean hasKey(Spo2Entity spo2Entity) {
        return spo2Entity.get_id() != null;
    }
}
