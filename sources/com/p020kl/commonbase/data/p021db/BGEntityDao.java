package com.p020kl.commonbase.data.p021db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.p020kl.commonbase.bean.BGEntity;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* renamed from: com.kl.commonbase.data.db.BGEntityDao */
public class BGEntityDao extends AbstractDao<BGEntity, Long> {
    public static final String TABLENAME = "BGENTITY";

    /* renamed from: com.kl.commonbase.data.db.BGEntityDao$Properties */
    public static class Properties {
        public static final Property BloodGlucose = new Property(2, Double.TYPE, "bloodGlucose", false, "BLOOD_GLUCOSE");
        public static final Property CorrectionCode = new Property(3, String.class, "correctionCode", false, "CORRECTION_CODE");
        public static final Property CreateTime = new Property(5, Long.TYPE, "createTime", false, "CREATE_TIME");
        public static final Property Day = new Property(11, Integer.TYPE, "day", false, "DAY");
        public static final Property DocId = new Property(8, String.class, "docId", false, "DOC_ID");
        public static final Property MacAddress = new Property(12, String.class, "macAddress", false, "MAC_ADDRESS");
        public static final Property MeasureTime = new Property(4, Integer.TYPE, "measureTime", false, "MEASURE_TIME");
        public static final Property ModifyTime = new Property(6, Long.TYPE, "modifyTime", false, "MODIFY_TIME");
        public static final Property Month = new Property(10, Integer.TYPE, "month", false, "MONTH");
        public static final Property Note = new Property(7, String.class, "note", false, "NOTE");
        public static final Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public static final Property Year = new Property(9, Integer.TYPE, "year", false, "YEAR");
        public static final Property _id = new Property(0, Long.class, "_id", true, "_id");
    }

    /* access modifiers changed from: protected */
    public final boolean isEntityUpdateable() {
        return true;
    }

    public BGEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public BGEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        String str = z ? "IF NOT EXISTS " : "";
        database.execSQL("CREATE TABLE " + str + "\"BGENTITY\" (" + "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + "\"USER_ID\" TEXT," + "\"BLOOD_GLUCOSE\" REAL NOT NULL ," + "\"CORRECTION_CODE\" TEXT," + "\"MEASURE_TIME\" INTEGER NOT NULL ," + "\"CREATE_TIME\" INTEGER NOT NULL ," + "\"MODIFY_TIME\" INTEGER NOT NULL ," + "\"NOTE\" TEXT," + "\"DOC_ID\" TEXT," + "\"YEAR\" INTEGER NOT NULL ," + "\"MONTH\" INTEGER NOT NULL ," + "\"DAY\" INTEGER NOT NULL ," + "\"MAC_ADDRESS\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"BGENTITY\"");
        database.execSQL(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(DatabaseStatement databaseStatement, BGEntity bGEntity) {
        databaseStatement.clearBindings();
        Long l = bGEntity.get_id();
        if (l != null) {
            databaseStatement.bindLong(1, l.longValue());
        }
        String userId = bGEntity.getUserId();
        if (userId != null) {
            databaseStatement.bindString(2, userId);
        }
        databaseStatement.bindDouble(3, bGEntity.getBloodGlucose());
        String correctionCode = bGEntity.getCorrectionCode();
        if (correctionCode != null) {
            databaseStatement.bindString(4, correctionCode);
        }
        databaseStatement.bindLong(5, (long) bGEntity.getMeasureTime());
        databaseStatement.bindLong(6, bGEntity.getCreateTime());
        databaseStatement.bindLong(7, bGEntity.getModifyTime());
        String note = bGEntity.getNote();
        if (note != null) {
            databaseStatement.bindString(8, note);
        }
        String docId = bGEntity.getDocId();
        if (docId != null) {
            databaseStatement.bindString(9, docId);
        }
        databaseStatement.bindLong(10, (long) bGEntity.getYear());
        databaseStatement.bindLong(11, (long) bGEntity.getMonth());
        databaseStatement.bindLong(12, (long) bGEntity.getDay());
        String macAddress = bGEntity.getMacAddress();
        if (macAddress != null) {
            databaseStatement.bindString(13, macAddress);
        }
    }

    /* access modifiers changed from: protected */
    public final void bindValues(SQLiteStatement sQLiteStatement, BGEntity bGEntity) {
        sQLiteStatement.clearBindings();
        Long l = bGEntity.get_id();
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String userId = bGEntity.getUserId();
        if (userId != null) {
            sQLiteStatement.bindString(2, userId);
        }
        sQLiteStatement.bindDouble(3, bGEntity.getBloodGlucose());
        String correctionCode = bGEntity.getCorrectionCode();
        if (correctionCode != null) {
            sQLiteStatement.bindString(4, correctionCode);
        }
        sQLiteStatement.bindLong(5, (long) bGEntity.getMeasureTime());
        sQLiteStatement.bindLong(6, bGEntity.getCreateTime());
        sQLiteStatement.bindLong(7, bGEntity.getModifyTime());
        String note = bGEntity.getNote();
        if (note != null) {
            sQLiteStatement.bindString(8, note);
        }
        String docId = bGEntity.getDocId();
        if (docId != null) {
            sQLiteStatement.bindString(9, docId);
        }
        sQLiteStatement.bindLong(10, (long) bGEntity.getYear());
        sQLiteStatement.bindLong(11, (long) bGEntity.getMonth());
        sQLiteStatement.bindLong(12, (long) bGEntity.getDay());
        String macAddress = bGEntity.getMacAddress();
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

    public BGEntity readEntity(Cursor cursor, int i) {
        Cursor cursor2 = cursor;
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 3;
        int i5 = i + 7;
        int i6 = i + 8;
        int i7 = i + 12;
        return new BGEntity(cursor2.isNull(i2) ? null : Long.valueOf(cursor2.getLong(i2)), cursor2.isNull(i3) ? null : cursor2.getString(i3), cursor2.getDouble(i + 2), cursor2.isNull(i4) ? null : cursor2.getString(i4), cursor2.getInt(i + 4), cursor2.getLong(i + 5), cursor2.getLong(i + 6), cursor2.isNull(i5) ? null : cursor2.getString(i5), cursor2.isNull(i6) ? null : cursor2.getString(i6), cursor2.getInt(i + 9), cursor2.getInt(i + 10), cursor2.getInt(i + 11), cursor2.isNull(i7) ? null : cursor2.getString(i7));
    }

    public void readEntity(Cursor cursor, BGEntity bGEntity, int i) {
        int i2 = i + 0;
        String str = null;
        bGEntity.set_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        bGEntity.setUserId(cursor.isNull(i3) ? null : cursor.getString(i3));
        bGEntity.setBloodGlucose(cursor.getDouble(i + 2));
        int i4 = i + 3;
        bGEntity.setCorrectionCode(cursor.isNull(i4) ? null : cursor.getString(i4));
        bGEntity.setMeasureTime(cursor.getInt(i + 4));
        bGEntity.setCreateTime(cursor.getLong(i + 5));
        bGEntity.setModifyTime(cursor.getLong(i + 6));
        int i5 = i + 7;
        bGEntity.setNote(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 8;
        bGEntity.setDocId(cursor.isNull(i6) ? null : cursor.getString(i6));
        bGEntity.setYear(cursor.getInt(i + 9));
        bGEntity.setMonth(cursor.getInt(i + 10));
        bGEntity.setDay(cursor.getInt(i + 11));
        int i7 = i + 12;
        if (!cursor.isNull(i7)) {
            str = cursor.getString(i7);
        }
        bGEntity.setMacAddress(str);
    }

    /* access modifiers changed from: protected */
    public final Long updateKeyAfterInsert(BGEntity bGEntity, long j) {
        bGEntity.set_id(Long.valueOf(j));
        return Long.valueOf(j);
    }

    public Long getKey(BGEntity bGEntity) {
        if (bGEntity != null) {
            return bGEntity.get_id();
        }
        return null;
    }

    public boolean hasKey(BGEntity bGEntity) {
        return bGEntity.get_id() != null;
    }
}
