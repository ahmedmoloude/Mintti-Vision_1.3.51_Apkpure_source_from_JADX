package com.p020kl.commonbase.data.p021db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.itextpdf.text.html.HtmlTags;
import com.p020kl.commonbase.bean.UserInfoEntity;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* renamed from: com.kl.commonbase.data.db.UserInfoEntityDao */
public class UserInfoEntityDao extends AbstractDao<UserInfoEntity, String> {
    public static final String TABLENAME = "USER_INFO_ENTITY";

    /* renamed from: com.kl.commonbase.data.db.UserInfoEntityDao$Properties */
    public static class Properties {
        public static final Property Account = new Property(1, String.class, "account", false, "ACCOUNT");
        public static final Property AccountType = new Property(2, String.class, "accountType", false, "ACCOUNT_TYPE");
        public static final Property Age = new Property(5, Integer.TYPE, "age", false, "AGE");
        public static final Property Birthday = new Property(11, String.class, "birthday", false, "BIRTHDAY");
        public static final Property CountryId = new Property(3, String.class, "countryId", false, "COUNTRY_ID");
        public static final Property FaceUrl = new Property(8, String.class, "faceUrl", false, "FACE_URL");
        public static final Property Gender = new Property(6, Integer.TYPE, "gender", false, "GENDER");
        public static final Property Height = new Property(9, Integer.TYPE, HtmlTags.HEIGHT, false, "HEIGHT");

        /* renamed from: Id */
        public static final Property f844Id = new Property(0, String.class, "id", true, "ID");
        public static final Property ModifyTime = new Property(12, Long.TYPE, "modifyTime", false, "MODIFY_TIME");
        public static final Property NickName = new Property(4, String.class, "nickName", false, "NICK_NAME");
        public static final Property Token = new Property(7, String.class, "token", false, "TOKEN");
        public static final Property Weight = new Property(10, Integer.TYPE, "weight", false, "WEIGHT");
    }

    /* access modifiers changed from: protected */
    public final boolean isEntityUpdateable() {
        return true;
    }

    public UserInfoEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public UserInfoEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        String str = z ? "IF NOT EXISTS " : "";
        database.execSQL("CREATE TABLE " + str + "\"USER_INFO_ENTITY\" (" + "\"ID\" TEXT PRIMARY KEY NOT NULL ," + "\"ACCOUNT\" TEXT," + "\"ACCOUNT_TYPE\" TEXT," + "\"COUNTRY_ID\" TEXT," + "\"NICK_NAME\" TEXT," + "\"AGE\" INTEGER NOT NULL ," + "\"GENDER\" INTEGER NOT NULL ," + "\"TOKEN\" TEXT," + "\"FACE_URL\" TEXT," + "\"HEIGHT\" INTEGER NOT NULL ," + "\"WEIGHT\" INTEGER NOT NULL ," + "\"BIRTHDAY\" TEXT," + "\"MODIFY_TIME\" INTEGER NOT NULL );");
    }

    public static void dropTable(Database database, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"USER_INFO_ENTITY\"");
        database.execSQL(sb.toString());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(DatabaseStatement databaseStatement, UserInfoEntity userInfoEntity) {
        databaseStatement.clearBindings();
        databaseStatement.bindString(1, userInfoEntity.getId());
        String account = userInfoEntity.getAccount();
        if (account != null) {
            databaseStatement.bindString(2, account);
        }
        String accountType = userInfoEntity.getAccountType();
        if (accountType != null) {
            databaseStatement.bindString(3, accountType);
        }
        String countryId = userInfoEntity.getCountryId();
        if (countryId != null) {
            databaseStatement.bindString(4, countryId);
        }
        String nickName = userInfoEntity.getNickName();
        if (nickName != null) {
            databaseStatement.bindString(5, nickName);
        }
        databaseStatement.bindLong(6, (long) userInfoEntity.getAge());
        databaseStatement.bindLong(7, (long) userInfoEntity.getGender());
        String token = userInfoEntity.getToken();
        if (token != null) {
            databaseStatement.bindString(8, token);
        }
        String faceUrl = userInfoEntity.getFaceUrl();
        if (faceUrl != null) {
            databaseStatement.bindString(9, faceUrl);
        }
        databaseStatement.bindLong(10, (long) userInfoEntity.getHeight());
        databaseStatement.bindLong(11, (long) userInfoEntity.getWeight());
        String birthday = userInfoEntity.getBirthday();
        if (birthday != null) {
            databaseStatement.bindString(12, birthday);
        }
        databaseStatement.bindLong(13, userInfoEntity.getModifyTime());
    }

    /* access modifiers changed from: protected */
    public final void bindValues(SQLiteStatement sQLiteStatement, UserInfoEntity userInfoEntity) {
        sQLiteStatement.clearBindings();
        sQLiteStatement.bindString(1, userInfoEntity.getId());
        String account = userInfoEntity.getAccount();
        if (account != null) {
            sQLiteStatement.bindString(2, account);
        }
        String accountType = userInfoEntity.getAccountType();
        if (accountType != null) {
            sQLiteStatement.bindString(3, accountType);
        }
        String countryId = userInfoEntity.getCountryId();
        if (countryId != null) {
            sQLiteStatement.bindString(4, countryId);
        }
        String nickName = userInfoEntity.getNickName();
        if (nickName != null) {
            sQLiteStatement.bindString(5, nickName);
        }
        sQLiteStatement.bindLong(6, (long) userInfoEntity.getAge());
        sQLiteStatement.bindLong(7, (long) userInfoEntity.getGender());
        String token = userInfoEntity.getToken();
        if (token != null) {
            sQLiteStatement.bindString(8, token);
        }
        String faceUrl = userInfoEntity.getFaceUrl();
        if (faceUrl != null) {
            sQLiteStatement.bindString(9, faceUrl);
        }
        sQLiteStatement.bindLong(10, (long) userInfoEntity.getHeight());
        sQLiteStatement.bindLong(11, (long) userInfoEntity.getWeight());
        String birthday = userInfoEntity.getBirthday();
        if (birthday != null) {
            sQLiteStatement.bindString(12, birthday);
        }
        sQLiteStatement.bindLong(13, userInfoEntity.getModifyTime());
    }

    public String readKey(Cursor cursor, int i) {
        return cursor.getString(i + 0);
    }

    public UserInfoEntity readEntity(Cursor cursor, int i) {
        Cursor cursor2 = cursor;
        String string = cursor2.getString(i + 0);
        int i2 = i + 1;
        String str = null;
        String string2 = cursor2.isNull(i2) ? null : cursor2.getString(i2);
        int i3 = i + 2;
        String string3 = cursor2.isNull(i3) ? null : cursor2.getString(i3);
        int i4 = i + 3;
        String string4 = cursor2.isNull(i4) ? null : cursor2.getString(i4);
        int i5 = i + 4;
        String string5 = cursor2.isNull(i5) ? null : cursor2.getString(i5);
        int i6 = cursor2.getInt(i + 5);
        int i7 = cursor2.getInt(i + 6);
        int i8 = i + 7;
        String string6 = cursor2.isNull(i8) ? null : cursor2.getString(i8);
        int i9 = i + 8;
        String string7 = cursor2.isNull(i9) ? null : cursor2.getString(i9);
        int i10 = cursor2.getInt(i + 9);
        int i11 = cursor2.getInt(i + 10);
        int i12 = i + 11;
        if (!cursor2.isNull(i12)) {
            str = cursor2.getString(i12);
        }
        return new UserInfoEntity(string, string2, string3, string4, string5, i6, i7, string6, string7, i10, i11, str, cursor2.getLong(i + 12));
    }

    public void readEntity(Cursor cursor, UserInfoEntity userInfoEntity, int i) {
        userInfoEntity.setId(cursor.getString(i + 0));
        int i2 = i + 1;
        String str = null;
        userInfoEntity.setAccount(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 2;
        userInfoEntity.setAccountType(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 3;
        userInfoEntity.setCountryId(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 4;
        userInfoEntity.setNickName(cursor.isNull(i5) ? null : cursor.getString(i5));
        userInfoEntity.setAge(cursor.getInt(i + 5));
        userInfoEntity.setGender(cursor.getInt(i + 6));
        int i6 = i + 7;
        userInfoEntity.setToken(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = i + 8;
        userInfoEntity.setFaceUrl(cursor.isNull(i7) ? null : cursor.getString(i7));
        userInfoEntity.setHeight(cursor.getInt(i + 9));
        userInfoEntity.setWeight(cursor.getInt(i + 10));
        int i8 = i + 11;
        if (!cursor.isNull(i8)) {
            str = cursor.getString(i8);
        }
        userInfoEntity.setBirthday(str);
        userInfoEntity.setModifyTime(cursor.getLong(i + 12));
    }

    /* access modifiers changed from: protected */
    public final String updateKeyAfterInsert(UserInfoEntity userInfoEntity, long j) {
        return userInfoEntity.getId();
    }

    public String getKey(UserInfoEntity userInfoEntity) {
        if (userInfoEntity != null) {
            return userInfoEntity.getId();
        }
        return null;
    }

    public boolean hasKey(UserInfoEntity userInfoEntity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }
}
