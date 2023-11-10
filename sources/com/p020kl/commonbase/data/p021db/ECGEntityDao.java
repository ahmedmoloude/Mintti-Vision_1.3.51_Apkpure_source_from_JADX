package com.p020kl.commonbase.data.p021db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.text.html.HtmlTags;
import com.p020kl.commonbase.bean.ECGEntity;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* renamed from: com.kl.commonbase.data.db.ECGEntityDao */
public class ECGEntityDao extends AbstractDao<ECGEntity, Long> {
    public static final String TABLENAME = "ECGENTITY";

    /* renamed from: com.kl.commonbase.data.db.ECGEntityDao$Properties */
    public static class Properties {
        public static final Property AvgHr = new Property(4, Integer.TYPE, "avgHr", false, "AVG_HR");

        /* renamed from: Br */
        public static final Property f843Br = new Property(7, Integer.TYPE, HtmlTags.f477BR, false, "BR");
        public static final Property CreateTime = new Property(10, Long.TYPE, "createTime", false, "CREATE_TIME");
        public static final Property Day = new Property(18, Integer.TYPE, "day", false, "DAY");
        public static final Property DocId = new Property(15, String.class, "docId", false, "DOC_ID");
        public static final Property Duration = new Property(9, Long.TYPE, TypedValues.TransitionType.S_DURATION, false, "DURATION");
        public static final Property FileMd5 = new Property(19, String.class, "fileMd5", false, "FILE_MD5");
        public static final Property FilePath = new Property(11, String.class, "filePath", false, "FILE_PATH");
        public static final Property FileUrl = new Property(12, String.class, "fileUrl", false, "FILE_URL");
        public static final Property Hrv = new Property(5, Integer.TYPE, "hrv", false, "HRV");
        public static final Property MacAddress = new Property(20, String.class, "macAddress", false, "MAC_ADDRESS");
        public static final Property ModifyTime = new Property(13, Long.TYPE, "modifyTime", false, "MODIFY_TIME");
        public static final Property Month = new Property(17, Integer.TYPE, "month", false, "MONTH");
        public static final Property Mood = new Property(6, Integer.TYPE, "mood", false, "MOOD");
        public static final Property Note = new Property(14, String.class, "note", false, "NOTE");
        public static final Property RriMax = new Property(2, Integer.TYPE, "rriMax", false, "RRI_MAX");
        public static final Property RriMin = new Property(3, Integer.TYPE, "rriMin", false, "RRI_MIN");
        public static final Property SampleRate = new Property(8, Integer.TYPE, "sampleRate", false, "SAMPLE_RATE");
        public static final Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public static final Property Year = new Property(16, Integer.TYPE, "year", false, "YEAR");
        public static final Property _id = new Property(0, Long.class, "_id", true, "_id");
    }

    /* access modifiers changed from: protected */
    public final boolean isEntityUpdateable() {
        return true;
    }

    public ECGEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public ECGEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        String str = z ? "IF NOT EXISTS " : "";
        database.execSQL("CREATE TABLE " + str + "\"ECGENTITY\" (" + "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + "\"USER_ID\" TEXT NOT NULL ," + "\"RRI_MAX\" INTEGER NOT NULL ," + "\"RRI_MIN\" INTEGER NOT NULL ," + "\"AVG_HR\" INTEGER NOT NULL ," + "\"HRV\" INTEGER NOT NULL ," + "\"MOOD\" INTEGER NOT NULL ," + "\"BR\" INTEGER NOT NULL ," + "\"SAMPLE_RATE\" INTEGER NOT NULL ," + "\"DURATION\" INTEGER NOT NULL ," + "\"CREATE_TIME\" INTEGER NOT NULL ," + "\"FILE_PATH\" TEXT," + "\"FILE_URL\" TEXT," + "\"MODIFY_TIME\" INTEGER NOT NULL ," + "\"NOTE\" TEXT," + "\"DOC_ID\" TEXT," + "\"YEAR\" INTEGER NOT NULL ," + "\"MONTH\" INTEGER NOT NULL ," + "\"DAY\" INTEGER NOT NULL ," + "\"FILE_MD5\" TEXT," + "\"MAC_ADDRESS\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"ECGENTITY\"");
        database.execSQL(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(DatabaseStatement databaseStatement, ECGEntity eCGEntity) {
        databaseStatement.clearBindings();
        Long l = eCGEntity.get_id();
        if (l != null) {
            databaseStatement.bindLong(1, l.longValue());
        }
        databaseStatement.bindString(2, eCGEntity.getUserId());
        databaseStatement.bindLong(3, (long) eCGEntity.getRriMax());
        databaseStatement.bindLong(4, (long) eCGEntity.getRriMin());
        databaseStatement.bindLong(5, (long) eCGEntity.getAvgHr());
        databaseStatement.bindLong(6, (long) eCGEntity.getHrv());
        databaseStatement.bindLong(7, (long) eCGEntity.getMood());
        databaseStatement.bindLong(8, (long) eCGEntity.getBr());
        databaseStatement.bindLong(9, (long) eCGEntity.getSampleRate());
        databaseStatement.bindLong(10, eCGEntity.getDuration());
        databaseStatement.bindLong(11, eCGEntity.getCreateTime());
        String filePath = eCGEntity.getFilePath();
        if (filePath != null) {
            databaseStatement.bindString(12, filePath);
        }
        String fileUrl = eCGEntity.getFileUrl();
        if (fileUrl != null) {
            databaseStatement.bindString(13, fileUrl);
        }
        databaseStatement.bindLong(14, eCGEntity.getModifyTime());
        String note = eCGEntity.getNote();
        if (note != null) {
            databaseStatement.bindString(15, note);
        }
        String docId = eCGEntity.getDocId();
        if (docId != null) {
            databaseStatement.bindString(16, docId);
        }
        databaseStatement.bindLong(17, (long) eCGEntity.getYear());
        databaseStatement.bindLong(18, (long) eCGEntity.getMonth());
        databaseStatement.bindLong(19, (long) eCGEntity.getDay());
        String fileMd5 = eCGEntity.getFileMd5();
        if (fileMd5 != null) {
            databaseStatement.bindString(20, fileMd5);
        }
        String macAddress = eCGEntity.getMacAddress();
        if (macAddress != null) {
            databaseStatement.bindString(21, macAddress);
        }
    }

    /* access modifiers changed from: protected */
    public final void bindValues(SQLiteStatement sQLiteStatement, ECGEntity eCGEntity) {
        sQLiteStatement.clearBindings();
        Long l = eCGEntity.get_id();
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        sQLiteStatement.bindString(2, eCGEntity.getUserId());
        sQLiteStatement.bindLong(3, (long) eCGEntity.getRriMax());
        sQLiteStatement.bindLong(4, (long) eCGEntity.getRriMin());
        sQLiteStatement.bindLong(5, (long) eCGEntity.getAvgHr());
        sQLiteStatement.bindLong(6, (long) eCGEntity.getHrv());
        sQLiteStatement.bindLong(7, (long) eCGEntity.getMood());
        sQLiteStatement.bindLong(8, (long) eCGEntity.getBr());
        sQLiteStatement.bindLong(9, (long) eCGEntity.getSampleRate());
        sQLiteStatement.bindLong(10, eCGEntity.getDuration());
        sQLiteStatement.bindLong(11, eCGEntity.getCreateTime());
        String filePath = eCGEntity.getFilePath();
        if (filePath != null) {
            sQLiteStatement.bindString(12, filePath);
        }
        String fileUrl = eCGEntity.getFileUrl();
        if (fileUrl != null) {
            sQLiteStatement.bindString(13, fileUrl);
        }
        sQLiteStatement.bindLong(14, eCGEntity.getModifyTime());
        String note = eCGEntity.getNote();
        if (note != null) {
            sQLiteStatement.bindString(15, note);
        }
        String docId = eCGEntity.getDocId();
        if (docId != null) {
            sQLiteStatement.bindString(16, docId);
        }
        sQLiteStatement.bindLong(17, (long) eCGEntity.getYear());
        sQLiteStatement.bindLong(18, (long) eCGEntity.getMonth());
        sQLiteStatement.bindLong(19, (long) eCGEntity.getDay());
        String fileMd5 = eCGEntity.getFileMd5();
        if (fileMd5 != null) {
            sQLiteStatement.bindString(20, fileMd5);
        }
        String macAddress = eCGEntity.getMacAddress();
        if (macAddress != null) {
            sQLiteStatement.bindString(21, macAddress);
        }
    }

    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public ECGEntity readEntity(Cursor cursor, int i) {
        Cursor cursor2 = cursor;
        int i2 = i + 0;
        Long valueOf = cursor2.isNull(i2) ? null : Long.valueOf(cursor2.getLong(i2));
        String string = cursor2.getString(i + 1);
        int i3 = cursor2.getInt(i + 2);
        int i4 = cursor2.getInt(i + 3);
        int i5 = cursor2.getInt(i + 4);
        int i6 = cursor2.getInt(i + 5);
        int i7 = cursor2.getInt(i + 6);
        int i8 = cursor2.getInt(i + 7);
        int i9 = cursor2.getInt(i + 8);
        long j = cursor2.getLong(i + 9);
        long j2 = cursor2.getLong(i + 10);
        int i10 = i + 11;
        String string2 = cursor2.isNull(i10) ? null : cursor2.getString(i10);
        int i11 = i + 12;
        String string3 = cursor2.isNull(i11) ? null : cursor2.getString(i11);
        long j3 = cursor2.getLong(i + 13);
        int i12 = i + 14;
        String string4 = cursor2.isNull(i12) ? null : cursor2.getString(i12);
        int i13 = i + 15;
        String string5 = cursor2.isNull(i13) ? null : cursor2.getString(i13);
        int i14 = cursor2.getInt(i + 16);
        int i15 = cursor2.getInt(i + 17);
        int i16 = cursor2.getInt(i + 18);
        int i17 = i + 19;
        String string6 = cursor2.isNull(i17) ? null : cursor2.getString(i17);
        int i18 = i + 20;
        return new ECGEntity(valueOf, string, i3, i4, i5, i6, i7, i8, i9, j, j2, string2, string3, j3, string4, string5, i14, i15, i16, string6, cursor2.isNull(i18) ? null : cursor2.getString(i18));
    }

    public void readEntity(Cursor cursor, ECGEntity eCGEntity, int i) {
        int i2 = i + 0;
        String str = null;
        eCGEntity.set_id(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        eCGEntity.setUserId(cursor.getString(i + 1));
        eCGEntity.setRriMax(cursor.getInt(i + 2));
        eCGEntity.setRriMin(cursor.getInt(i + 3));
        eCGEntity.setAvgHr(cursor.getInt(i + 4));
        eCGEntity.setHrv(cursor.getInt(i + 5));
        eCGEntity.setMood(cursor.getInt(i + 6));
        eCGEntity.setBr(cursor.getInt(i + 7));
        eCGEntity.setSampleRate(cursor.getInt(i + 8));
        eCGEntity.setDuration(cursor.getLong(i + 9));
        eCGEntity.setCreateTime(cursor.getLong(i + 10));
        int i3 = i + 11;
        eCGEntity.setFilePath(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 12;
        eCGEntity.setFileUrl(cursor.isNull(i4) ? null : cursor.getString(i4));
        eCGEntity.setModifyTime(cursor.getLong(i + 13));
        int i5 = i + 14;
        eCGEntity.setNote(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 15;
        eCGEntity.setDocId(cursor.isNull(i6) ? null : cursor.getString(i6));
        eCGEntity.setYear(cursor.getInt(i + 16));
        eCGEntity.setMonth(cursor.getInt(i + 17));
        eCGEntity.setDay(cursor.getInt(i + 18));
        int i7 = i + 19;
        eCGEntity.setFileMd5(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = i + 20;
        if (!cursor.isNull(i8)) {
            str = cursor.getString(i8);
        }
        eCGEntity.setMacAddress(str);
    }

    /* access modifiers changed from: protected */
    public final Long updateKeyAfterInsert(ECGEntity eCGEntity, long j) {
        eCGEntity.set_id(Long.valueOf(j));
        return Long.valueOf(j);
    }

    public Long getKey(ECGEntity eCGEntity) {
        if (eCGEntity != null) {
            return eCGEntity.get_id();
        }
        return null;
    }

    public boolean hasKey(ECGEntity eCGEntity) {
        return eCGEntity.get_id() != null;
    }
}
