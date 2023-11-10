package androidx.test.runner.screenshot;

import android.os.Build;
import android.os.Environment;
import java.io.File;
import java.util.UUID;

public class BasicScreenCaptureProcessor implements ScreenCaptureProcessor {
    private static String sAndroidDeviceName = Build.DEVICE;
    private static int sAndroidRuntimeVersion = Build.VERSION.SDK_INT;
    protected String mDefaultFilenamePrefix;
    protected File mDefaultScreenshotPath;
    protected String mFileNameDelimiter;
    protected String mTag;

    public BasicScreenCaptureProcessor() {
        this(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "screenshots"));
    }

    BasicScreenCaptureProcessor(File file) {
        this.mTag = "BasicScreenCaptureProcessor";
        this.mFileNameDelimiter = "-";
        this.mDefaultFilenamePrefix = "screenshot";
        this.mDefaultScreenshotPath = file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a0 A[SYNTHETIC, Splitter:B:24:0x00a0] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String process(androidx.test.runner.screenshot.ScreenCapture r7) throws java.io.IOException {
        /*
            r6 = this;
            java.lang.String r0 = "Could not close output steam."
            java.lang.String r1 = r7.getName()
            if (r1 != 0) goto L_0x000d
            java.lang.String r1 = r6.getDefaultFilename()
            goto L_0x0015
        L_0x000d:
            java.lang.String r1 = r7.getName()
            java.lang.String r1 = r6.getFilename(r1)
        L_0x0015:
            java.lang.String r1 = java.lang.String.valueOf(r1)
            android.graphics.Bitmap$CompressFormat r2 = r7.getFormat()
            java.lang.String r2 = r2.toString()
            java.lang.String r2 = r2.toLowerCase()
            java.lang.String r3 = java.lang.String.valueOf(r1)
            int r3 = r3.length()
            r4 = 1
            int r3 = r3 + r4
            java.lang.String r5 = java.lang.String.valueOf(r2)
            int r5 = r5.length()
            int r3 = r3 + r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r3)
            r5.append(r1)
            java.lang.String r1 = "."
            r5.append(r1)
            r5.append(r2)
            java.lang.String r1 = r5.toString()
            java.io.File r2 = r6.mDefaultScreenshotPath
            r2.mkdirs()
            boolean r3 = r2.isDirectory()
            if (r3 != 0) goto L_0x006f
            boolean r3 = r2.canWrite()
            if (r3 == 0) goto L_0x005e
            goto L_0x006f
        L_0x005e:
            java.io.IOException r7 = new java.io.IOException
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r1 = 0
            r0[r1] = r2
            java.lang.String r1 = "The directory %s does not exist and could not be created or is not writable."
            java.lang.String r0 = java.lang.String.format(r1, r0)
            r7.<init>(r0)
            throw r7
        L_0x006f:
            java.io.File r3 = new java.io.File
            r3.<init>(r2, r1)
            r2 = 0
            java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x009d }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ all -> 0x009d }
            r5.<init>(r3)     // Catch:{ all -> 0x009d }
            r4.<init>(r5)     // Catch:{ all -> 0x009d }
            android.graphics.Bitmap r2 = r7.getBitmap()     // Catch:{ all -> 0x009a }
            android.graphics.Bitmap$CompressFormat r7 = r7.getFormat()     // Catch:{ all -> 0x009a }
            r3 = 100
            r2.compress(r7, r3, r4)     // Catch:{ all -> 0x009a }
            r4.flush()     // Catch:{ all -> 0x009a }
            r4.close()     // Catch:{ IOException -> 0x0093 }
            goto L_0x0099
        L_0x0093:
            r7 = move-exception
            java.lang.String r2 = r6.mTag
            android.util.Log.e(r2, r0, r7)
        L_0x0099:
            return r1
        L_0x009a:
            r7 = move-exception
            r2 = r4
            goto L_0x009e
        L_0x009d:
            r7 = move-exception
        L_0x009e:
            if (r2 == 0) goto L_0x00aa
            r2.close()     // Catch:{ IOException -> 0x00a4 }
            goto L_0x00aa
        L_0x00a4:
            r1 = move-exception
            java.lang.String r2 = r6.mTag
            android.util.Log.e(r2, r0, r1)
        L_0x00aa:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.runner.screenshot.BasicScreenCaptureProcessor.process(androidx.test.runner.screenshot.ScreenCapture):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public String getDefaultFilename() {
        String str = this.mDefaultFilenamePrefix;
        String str2 = this.mFileNameDelimiter;
        String str3 = sAndroidDeviceName;
        int i = sAndroidRuntimeVersion;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 11 + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str2).length());
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str2);
        sb.append(i);
        return getFilename(sb.toString());
    }

    /* access modifiers changed from: protected */
    public String getFilename(String str) {
        String str2 = this.mFileNameDelimiter;
        String valueOf = String.valueOf(UUID.randomUUID());
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + String.valueOf(str2).length() + String.valueOf(valueOf).length());
        sb.append(str);
        sb.append(str2);
        sb.append(valueOf);
        return sb.toString();
    }

    static void setAndroidDeviceName(String str) {
        sAndroidDeviceName = str;
    }

    static void setAndroidRuntimeVersion(int i) {
        sAndroidRuntimeVersion = i;
    }
}
