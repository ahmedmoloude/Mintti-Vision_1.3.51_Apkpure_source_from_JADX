package androidx.test.services.storage;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import androidx.test.internal.util.Checks;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.services.storage.file.HostedFile;
import androidx.test.services.storage.file.PropertyFile;
import androidx.test.services.storage.internal.TestStorageUtil;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public final class TestStorage {
    private static final String PROPERTIES_FILE_NAME = "properties.dat";
    private static final String TAG = "TestStorage";
    private final ContentResolver contentResolver;

    public TestStorage() {
        this(InstrumentationRegistry.getInstrumentation().getTargetContext().getContentResolver());
    }

    public TestStorage(@Nonnull ContentResolver contentResolver2) {
        this.contentResolver = contentResolver2;
    }

    public static Uri getInputFileUri(@Nonnull String str) {
        Checks.checkNotNull(str);
        return HostedFile.buildUri(HostedFile.FileHost.TEST_FILE, str);
    }

    public static Uri getOutputFileUri(@Nonnull String str) {
        Checks.checkNotNull(str);
        return HostedFile.buildUri(HostedFile.FileHost.OUTPUT, str);
    }

    public InputStream openInputFile(@Nonnull String str) throws FileNotFoundException {
        return TestStorageUtil.getInputStream(getInputFileUri(str), this.contentResolver);
    }

    public String getInputArg(@Nonnull String str) {
        Checks.checkNotNull(str);
        Uri buildUri = PropertyFile.buildUri(PropertyFile.Authority.TEST_ARGS, str);
        Cursor cursor = null;
        try {
            cursor = doQuery(this.contentResolver, buildUri);
            if (cursor.getCount() == 0) {
                throw new TestStorageException(String.format("Query for URI '%s' did not return any results. Make sure the argName is actually being passed in as a test argument.", new Object[]{buildUri}));
            } else if (cursor.getCount() <= 1) {
                cursor.moveToFirst();
                return cursor.getString(PropertyFile.Column.VALUE.getPosition());
            } else {
                throw new TestStorageException(String.format("Query for URI '%s' returned more than one result. Weird.", new Object[]{buildUri}));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Map<String, String> getInputArgs() {
        Cursor cursor = null;
        try {
            cursor = doQuery(this.contentResolver, PropertyFile.buildUri(PropertyFile.Authority.TEST_ARGS));
            return getProperties(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public OutputStream openOutputFile(@Nonnull String str) throws FileNotFoundException {
        Checks.checkNotNull(str);
        return TestStorageUtil.getOutputStream(getOutputFileUri(str), this.contentResolver);
    }

    public void addOutputProperties(Map<String, Serializable> map) {
        if (map != null && !map.isEmpty()) {
            Map<String, Serializable> outputProperties = getOutputProperties();
            outputProperties.putAll(map);
            ObjectOutputStream objectOutputStream = null;
            try {
                ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new BufferedOutputStream(TestStorageUtil.getOutputStream(getPropertyFileUri(), this.contentResolver)));
                try {
                    objectOutputStream2.writeObject(outputProperties);
                    silentlyClose((OutputStream) objectOutputStream2);
                } catch (FileNotFoundException e) {
                    e = e;
                    ObjectOutputStream objectOutputStream3 = objectOutputStream2;
                    throw new TestStorageException("Unable to create file", e);
                } catch (IOException e2) {
                    e = e2;
                    objectOutputStream = objectOutputStream2;
                    throw new TestStorageException("I/O error occurred during reading test properties.", e);
                } catch (Throwable th) {
                    th = th;
                    objectOutputStream = objectOutputStream2;
                    silentlyClose((OutputStream) objectOutputStream);
                    throw th;
                }
            } catch (FileNotFoundException e3) {
                e = e3;
                throw new TestStorageException("Unable to create file", e);
            } catch (IOException e4) {
                e = e4;
                throw new TestStorageException("I/O error occurred during reading test properties.", e);
            } catch (Throwable th2) {
                th = th2;
                silentlyClose((OutputStream) objectOutputStream);
                throw th;
            }
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0048 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:27:0x0048=Splitter:B:27:0x0048, B:22:0x0039=Splitter:B:22:0x0039} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Map<java.lang.String, java.io.Serializable> getOutputProperties() {
        /*
            r7 = this;
            android.net.Uri r0 = getPropertyFileUri()
            r1 = 0
            android.content.ContentResolver r2 = r7.contentResolver     // Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0037, ClassNotFoundException -> 0x0035, all -> 0x0032 }
            java.io.InputStream r2 = androidx.test.services.storage.internal.TestStorageUtil.getInputStream(r0, r2)     // Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0037, ClassNotFoundException -> 0x0035, all -> 0x0032 }
            java.io.ObjectInputStream r3 = new java.io.ObjectInputStream     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030, ClassNotFoundException -> 0x002e }
            r3.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0030, ClassNotFoundException -> 0x002e }
            java.lang.Object r1 = r3.readObject()     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x0029, ClassNotFoundException -> 0x0027, all -> 0x0024 }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x0029, ClassNotFoundException -> 0x0027, all -> 0x0024 }
            if (r1 != 0) goto L_0x001d
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x0029, ClassNotFoundException -> 0x0027, all -> 0x0024 }
            r1.<init>()     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x0029, ClassNotFoundException -> 0x0027, all -> 0x0024 }
        L_0x001d:
            silentlyClose((java.io.InputStream) r3)
            silentlyClose((java.io.InputStream) r2)
            return r1
        L_0x0024:
            r0 = move-exception
            r1 = r3
            goto L_0x0061
        L_0x0027:
            r0 = move-exception
            goto L_0x002a
        L_0x0029:
            r0 = move-exception
        L_0x002a:
            r1 = r3
            goto L_0x0039
        L_0x002c:
            r1 = r3
            goto L_0x0048
        L_0x002e:
            r0 = move-exception
            goto L_0x0039
        L_0x0030:
            r0 = move-exception
            goto L_0x0039
        L_0x0032:
            r0 = move-exception
            r2 = r1
            goto L_0x0061
        L_0x0035:
            r0 = move-exception
            goto L_0x0038
        L_0x0037:
            r0 = move-exception
        L_0x0038:
            r2 = r1
        L_0x0039:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x0060 }
            java.lang.String r4 = "Failed to read recorded stats!"
            android.util.Log.w(r3, r4, r0)     // Catch:{ all -> 0x0060 }
        L_0x0040:
            silentlyClose((java.io.InputStream) r1)
            silentlyClose((java.io.InputStream) r2)
            goto L_0x005a
        L_0x0047:
            r2 = r1
        L_0x0048:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x0060 }
            java.lang.String r4 = "%s: does not exist, we must be the first call."
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0060 }
            r6 = 0
            r5[r6] = r0     // Catch:{ all -> 0x0060 }
            java.lang.String r0 = java.lang.String.format(r4, r5)     // Catch:{ all -> 0x0060 }
            android.util.Log.i(r3, r0)     // Catch:{ all -> 0x0060 }
            goto L_0x0040
        L_0x005a:
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            return r0
        L_0x0060:
            r0 = move-exception
        L_0x0061:
            silentlyClose((java.io.InputStream) r1)
            silentlyClose((java.io.InputStream) r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.services.storage.TestStorage.getOutputProperties():java.util.Map");
    }

    private static Uri getPropertyFileUri() {
        return HostedFile.buildUri(HostedFile.FileHost.EXPORT_PROPERTIES, PROPERTIES_FILE_NAME);
    }

    private static Cursor doQuery(ContentResolver contentResolver2, Uri uri) {
        Checks.checkNotNull(contentResolver2);
        Checks.checkNotNull(uri);
        Cursor query = contentResolver2.query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        if (query != null) {
            return query;
        }
        throw new TestStorageException(String.format("Failed to resolve query for URI: %s", new Object[]{uri}));
    }

    private static Map<String, String> getProperties(Cursor cursor) {
        Checks.checkNotNull(cursor);
        HashMap hashMap = new HashMap();
        while (cursor.moveToNext()) {
            hashMap.put(cursor.getString(PropertyFile.Column.NAME.getPosition()), cursor.getString(PropertyFile.Column.VALUE.getPosition()));
        }
        return hashMap;
    }

    private static void silentlyClose(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    private static void silentlyClose(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException unused) {
            }
        }
    }
}
