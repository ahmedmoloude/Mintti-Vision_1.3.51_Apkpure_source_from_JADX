package com.p020kl.commonbase.data.p021db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.p020kl.commonbase.bean.BPEntity;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* renamed from: com.kl.commonbase.data.db.BPEntityDao */
public class BPEntityDao extends AbstractDao<BPEntity, Long> {
    public static final String TABLENAME = "BPENTITY";

    /* renamed from: com.kl.commonbase.data.db.BPEntityDao$Properties */
    public static class Properties {
        public static final Property CreateTime = new Property(5, Long.TYPE, "createTime", false, "CREATE_TIME");
        public static final Property Day = new Property(11, Integer.TYPE, "day", false, "DAY");
        public static final Property DiastolicPressure = new Property(3, Integer.TYPE, "diastolicPressure", false, "DIASTOLIC_PRESSURE");
        public static final Property DocId = new Property(8, String.class, "docId", false, "DOC_ID");
        public static final Property HeartRate = new Property(4, Integer.TYPE, "heartRate", false, "HEART_RATE");
        public static final Property MacAddress = new Property(12, String.class, "macAddress", false, "MAC_ADDRESS");
        public static final Property ModifyTime = new Property(6, Long.TYPE, "modifyTime", false, "MODIFY_TIME");
        public static final Property Month = new Property(10, Integer.TYPE, "month", false, "MONTH");
        public static final Property Note = new Property(7, String.class, "note", false, "NOTE");
        public static final Property SystolicPressure = new Property(2, Integer.TYPE, "systolicPressure", false, "SYSTOLIC_PRESSURE");
        public static final Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public static final Property Year = new Property(9, Integer.TYPE, "year", false, "YEAR");
        public static final Property _id = new Property(0, Long.class, "_id", true, "_id");
    }

    /* access modifiers changed from: protected */
    public final boolean isEntityUpdateable() {
        return true;
    }

    public BPEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public BPEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        String str = z ? "IF NOT EXISTS " : "";
        database.execSQL("CREATE TABLE " + str + "\"BPENTITY\" (" + "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + "\"USER_ID\" TEXT," + "\"SYSTOLIC_PRESSURE\" INTEGER NOT NULL ," + "\"DIASTOLIC_PRESSURE\" INTEGER NOT NULL ," + "\"HEART_RATE\" INTEGER NOT NULL ," + "\"CREATE_TIME\" INTEGER NOT NULL ," + "\"MODIFY_TIME\" INTEGER NOT NULL ," + "\"NOTE\" TEXT," + "\"DOC_ID\" TEXT," + "\"YEAR\" INTEGER NOT NULL ," + "\"MONTH\" INTEGER NOT NULL ," + "\"DAY\" INTEGER NOT NULL ," + "\"MAC_ADDRESS\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"BPENTITY\"");
        database.execSQL(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(DatabaseStatement databaseStatement, BPEntity bPEntity) {
        databaseStatement.clearBindings();
        Long l = bPEntity.get_id();
        if (l != null) {
            databaseStatement.bindLong(1, l.longValue());
        }
        String userId = bPEntity.getUserId();
        if (userId != null) {
            databaseStatement.bindString(2, userId);
        }
        databaseStatement.bindLong(3, (long) bPEntity.getSystolicPressure());
        databaseStatement.bindLong(4, (long) bPEntity.getDiastolicPressure());
        databaseStatement.bindLong(5, (long) bPEntity.getHeartRate());
        databaseStatement.bindLong(6, bPEntity.getCreateTime());
        databaseStatement.bindLong(7, bPEntity.getModifyTime());
        String note = bPEntity.getNote();
        if (note != null) {
            databaseStatement.bindString(8, note);
        }
        String docId = bPEntity.getDocId();
        if (docId != null) {
            databaseStatement.bindString(9, docId);
        }
        databaseStatement.bindLong(10, (long) bPEntity.getYear());
        databaseStatement.bindLong(11, (long) bPEntity.getMonth());
        databaseStatement.bindLong(12, (long) bPEntity.getDay());
        String macAddress = bPEntity.getMacAddress();
        if (macAddress != null) {
            databaseStatement.bindString(13, macAddress);
        }
    }

    /* access modifiers changed from: protected */
    public final void bindValues(SQLiteStatement sQLiteStatement, BPEntity bPEntity) {
        sQLiteStatement.clearBindings();
        Long l = bPEntity.get_id();
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String userId = bPEntity.getUserId();
        if (userId != null) {
            sQLiteStatement.bindString(2, userId);
        }
        sQLiteStatement.bindLong(3, (long) bPEntity.getSystolicPressure());
        sQLiteStatement.bindLong(4, (long) bPEntity.getDiastolicPressure());
        sQLiteStatement.bindLong(5, (long) bPEntity.getHeartRate());
        sQLiteStatement.bindLong(6, bPEntity.getCreateTime());
        sQLiteStatement.bindLong(7, bPEntity.getModifyTime());
        String note = bPEntity.getNote();
        if (note != null) {
            sQLiteStatement.bindString(8, note);
        }
        String docId = bPEntity.getDocId();
        if (docId != null) {
            sQLiteStatement.bindString(9, docId);
        }
        sQLiteStatement.bindLong(10, (long) bPEntity.getYear());
        sQLiteStatement.bindLong(11, (long) bPEntity.getMonth());
        sQLiteStatement.bindLong(12, (long) bPEntity.getDay());
        String macAddress = bPEntity.getMacAddress();
        if (macAddress != null) {
            sQLiteStatement.bindString(13, macAddress);
        }
    }

    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public BPEntity readEntity(Cursor cursor, int i) {
        Cursor cursor2 = cursor;
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 7;
        int i5 = i + 8;
        int i6 = i + 12;
        return new BPEntity(cursor2.isNull(i2) ? null : Long.valueOf(cursor2.getLong(i2)), cursor2.isNull(i3) ? null : cursor2.getString(i3), cursor2.getInt(i + 2), cursor2.getInt(i + 3), cursor2.getInt(i + 4), cursor2.getLong(i + 5), cursor2.getLong(i + 6), cursor2.isNull(i4) ? null : cursor2.getString(i4), cursor2.isNull(i5) ? null : cursor2.getString(i5), cursor2.getInt(i + 9), cursor2.getInt(i + 10), cursor2.getInt(i + 11), cursor2.isNull(i6) ? null : cursor2.getString(i6));
    }

    public void readEntity(Cursor cursor, BPEntity bPEntity, int i) {
        int i2 = i + 0;
        String str = null;
        bPEntity.set_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        bPEntity.setUserId(cursor.isNull(i3) ? null : cursor.getString(i3));
        bPEntity.setSystolicPressure(cursor.getInt(i + 2));
        bPEntity.setDiastolicPressure(cursor.getInt(i + 3));
        bPEntity.setHeartRate(cursor.getInt(i + 4));
        bPEntity.setCreateTime(cursor.getLong(i + 5));
        bPEntity.setModifyTime(cursor.getLong(i + 6));
        int i4 = i + 7;
        bPEntity.setNote(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 8;
        bPEntity.setDocId(cursor.isNull(i5) ? null : cursor.getString(i5));
        bPEntity.setYear(cursor.getInt(i + 9));
        bPEntity.setMonth(cursor.getInt(i + 10));
        bPEntity.setDay(cursor.getInt(i + 11));
        int i6 = i + 12;
        if (!cursor.isNull(i6)) {
            str = cursor.getString(i6);
        }
        bPEntity.setMacAddress(str);
    }

    /* access modifiers changed from: protected */
    public final Long updateKeyAfterInsert(BPEntity bPEntity, long j) {
        bPEntity.set_id(Long.valueOf(j));
        return Long.valueOf(j);
    }

    public Long getKey(BPEntity bPEntity) {
        if (bPEntity != null) {
            return bPEntity.get_id();
        }
        return null;
    }

    public boolean hasKey(BPEntity bPEntity) {
        return bPEntity.get_id() != null;
    }
}
