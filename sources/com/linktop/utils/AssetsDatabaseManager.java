package com.linktop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetsDatabaseManager {

    /* renamed from: c */
    private static AssetsDatabaseManager f973c;

    /* renamed from: a */
    private Map<String, SQLiteDatabase> f974a = new HashMap();

    /* renamed from: b */
    private Context f975b;

    private AssetsDatabaseManager(Context context) {
        this.f975b = context;
    }

    /* renamed from: a */
    public static void m131a() {
        AssetsDatabaseManager assetsDatabaseManager = f973c;
        if (assetsDatabaseManager != null) {
            Map<String, SQLiteDatabase> map = assetsDatabaseManager.f974a;
            for (String str : map.keySet()) {
                map.get(str).close();
            }
            f973c.f974a.clear();
        }
    }

    /* renamed from: a */
    public static void m132a(Context context) {
        if (f973c == null) {
            f973c = new AssetsDatabaseManager(context);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0034 A[SYNTHETIC, Splitter:B:20:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003c A[Catch:{ Exception -> 0x0038 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m133a(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r0 = 0
            r1 = 0
            android.content.Context r2 = r3.f975b     // Catch:{ Exception -> 0x002c }
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch:{ Exception -> 0x002c }
            java.io.InputStream r4 = r2.open(r4)     // Catch:{ Exception -> 0x002c }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x002a }
            r2.<init>(r5)     // Catch:{ Exception -> 0x002a }
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x0027 }
        L_0x0015:
            int r1 = r4.read(r5)     // Catch:{ Exception -> 0x0027 }
            if (r1 <= 0) goto L_0x001f
            r2.write(r5, r0, r1)     // Catch:{ Exception -> 0x0027 }
            goto L_0x0015
        L_0x001f:
            r4.close()     // Catch:{ Exception -> 0x0027 }
            r2.close()     // Catch:{ Exception -> 0x0027 }
            r4 = 1
            return r4
        L_0x0027:
            r5 = move-exception
            r1 = r2
            goto L_0x002f
        L_0x002a:
            r5 = move-exception
            goto L_0x002f
        L_0x002c:
            r4 = move-exception
            r5 = r4
            r4 = r1
        L_0x002f:
            r5.printStackTrace()
            if (r4 == 0) goto L_0x003a
            r4.close()     // Catch:{ Exception -> 0x0038 }
            goto L_0x003a
        L_0x0038:
            r4 = move-exception
            goto L_0x0040
        L_0x003a:
            if (r1 == 0) goto L_0x0043
            r1.close()     // Catch:{ Exception -> 0x0038 }
            goto L_0x0043
        L_0x0040:
            r4.printStackTrace()
        L_0x0043:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.linktop.utils.AssetsDatabaseManager.m133a(java.lang.String, java.lang.String):boolean");
    }

    /* renamed from: b */
    private String m134b() {
        return String.format("/data/data/%s/database", new Object[]{this.f975b.getApplicationInfo().packageName});
    }

    /* renamed from: b */
    private String m135b(String str) {
        return m134b() + "/" + str;
    }

    /* renamed from: c */
    public static AssetsDatabaseManager m136c() {
        return f973c;
    }

    /* renamed from: a */
    public SQLiteDatabase mo27411a(String str) {
        if (this.f974a.get(str) != null) {
            return this.f974a.get(str);
        }
        if (this.f975b == null) {
            return null;
        }
        String b = m134b();
        String b2 = m135b(str);
        File file = new File(b2);
        SharedPreferences sharedPreferences = this.f975b.getSharedPreferences(AssetsDatabaseManager.class.toString(), 0);
        if (!sharedPreferences.getBoolean(str, false) || !file.exists()) {
            File file2 = new File(b);
            if ((!file2.exists() && !file2.mkdirs()) || !m133a(str, b2)) {
                return null;
            }
            sharedPreferences.edit().putBoolean(str, true).apply();
        }
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(b2, (SQLiteDatabase.CursorFactory) null, 16);
        if (openDatabase != null) {
            this.f974a.put(str, openDatabase);
        }
        return openDatabase;
    }
}
