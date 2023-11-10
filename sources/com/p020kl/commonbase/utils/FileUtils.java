package com.p020kl.commonbase.utils;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.p005v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import com.itextpdf.text.Annotation;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/* renamed from: com.kl.commonbase.utils.FileUtils */
public class FileUtils {
    private static final String TAG = "FileUtils";

    public static File getEcgFile(long j) {
        File file = new File(Constants.FILE_PATH_ECG, DateUtils.getFormatDate(j, Constants.TIME_FORMAT_FILE) + ".ecg");
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File getShareFile(String str, String str2) {
        File file = new File(Constants.FILE_SHARE_TEXT + "/" + str + "_" + str2 + Constants.SHARE_TEXT_NAME);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File getNewFirmZipFile(String str) {
        String str2 = Constants.FILE_PATH_ZIP;
        File file = new File(str2, str + ".zip");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    public static void deleteZipFileDir() {
        File file = new File(Constants.FILE_PATH_ZIP);
        if (file.exists()) {
            for (File delete : file.listFiles()) {
                delete.delete();
            }
        }
    }

    public static boolean checkNewFirmZipExist(String str, String str2) {
        String str3 = Constants.FILE_PATH_ZIP;
        File file = new File(str3, str + ".zip");
        if (!file.exists()) {
            return false;
        }
        Log.e(TAG, "checkNewFirmZipExist: " + getFileMd5(file));
        if (getFileMd5(file).equals(str2)) {
            return true;
        }
        file.delete();
        return false;
    }

    public static String formatFileSize(long j) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return decimalFormat.format((double) j) + "B";
        } else if (j < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            return decimalFormat.format(((double) j) / 1024.0d) + "K";
        } else if (j < 1073741824) {
            return decimalFormat.format(((double) j) / 1048576.0d) + "M";
        } else {
            return decimalFormat.format(((double) j) / 1.073741824E9d) + "G";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0068 A[SYNTHETIC, Splitter:B:33:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0073 A[SYNTHETIC, Splitter:B:39:0x0073] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getFileMd5(java.io.File r5) {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.lang.String r2 = "MD5"
            java.security.MessageDigest r2 = java.security.MessageDigest.getInstance(r2)     // Catch:{ Exception -> 0x0062 }
            if (r5 != 0) goto L_0x000c
            return r0
        L_0x000c:
            boolean r3 = r5.exists()     // Catch:{ Exception -> 0x0062 }
            if (r3 != 0) goto L_0x0013
            return r0
        L_0x0013:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0062 }
            r3.<init>(r5)     // Catch:{ Exception -> 0x0062 }
            r5 = 10485760(0xa00000, float:1.469368E-38)
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x005d, all -> 0x005a }
        L_0x001c:
            int r1 = r3.read(r5)     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            if (r1 <= 0) goto L_0x0027
            r4 = 0
            r2.update(r5, r4, r1)     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            goto L_0x001c
        L_0x0027:
            java.math.BigInteger r5 = new java.math.BigInteger     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            r1 = 1
            byte[] r2 = r2.digest()     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            r5.<init>(r1, r2)     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            r1 = 16
            java.lang.String r5 = r5.toString(r1)     // Catch:{ Exception -> 0x005d, all -> 0x005a }
        L_0x0037:
            int r1 = r5.length()     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            r2 = 32
            if (r1 >= r2) goto L_0x0051
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            r1.<init>()     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            java.lang.String r2 = "0"
            r1.append(r2)     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            r1.append(r5)     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x005d, all -> 0x005a }
            goto L_0x0037
        L_0x0051:
            r3.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0059
        L_0x0055:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0059:
            return r5
        L_0x005a:
            r5 = move-exception
            r1 = r3
            goto L_0x0071
        L_0x005d:
            r5 = move-exception
            r1 = r3
            goto L_0x0063
        L_0x0060:
            r5 = move-exception
            goto L_0x0071
        L_0x0062:
            r5 = move-exception
        L_0x0063:
            r5.printStackTrace()     // Catch:{ all -> 0x0060 }
            if (r1 == 0) goto L_0x0070
            r1.close()     // Catch:{ IOException -> 0x006c }
            goto L_0x0070
        L_0x006c:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0070:
            return r0
        L_0x0071:
            if (r1 == 0) goto L_0x007b
            r1.close()     // Catch:{ IOException -> 0x0077 }
            goto L_0x007b
        L_0x0077:
            r0 = move-exception
            r0.printStackTrace()
        L_0x007b:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.commonbase.utils.FileUtils.getFileMd5(java.io.File):java.lang.String");
    }

    public static File createFaceFile(Context context) {
        File rootDataFile = getRootDataFile(context);
        File file = new File(rootDataFile.getAbsolutePath(), SpManager.getUserId() + ".png");
        LoggerUtil.m112d(file.getAbsolutePath());
        try {
            if (!rootDataFile.exists()) {
                rootDataFile.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File getCropFaceFile(Context context) {
        File rootDataFile = getRootDataFile(context);
        return new File(rootDataFile, SpManager.getUserId() + Constants.FILE_NAME_FACE_CROP);
    }

    public static File createCropFaceTempFile(Context context, String str) {
        File rootDataFile = getRootDataFile(context);
        File file = new File(rootDataFile.getAbsolutePath(), SpManager.getUserId() + str);
        LoggerUtil.m112d(file.getAbsolutePath());
        try {
            if (!rootDataFile.exists()) {
                rootDataFile.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File createTempFile(Context context, String str) {
        return new File(context.getExternalCacheDir(), str);
    }

    public static File getRootDataFile(Context context) {
        return context.getFilesDir();
    }

    public static File getNewSoftFile(Context context, String str) {
        return createTempFile(context, str + ".apk");
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
        if (r8 != null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004e, code lost:
        if (r8 != null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0051, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0056  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getDataColumn(android.content.Context r8, android.net.Uri r9, java.lang.String r10, java.lang.String[] r11) {
        /*
            java.lang.String r0 = "_data"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r7 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch:{ IllegalArgumentException -> 0x0033, all -> 0x0031 }
            r6 = 0
            r2 = r9
            r4 = r10
            r5 = r11
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ IllegalArgumentException -> 0x0033, all -> 0x0031 }
            if (r8 == 0) goto L_0x002b
            boolean r9 = r8.moveToFirst()     // Catch:{ IllegalArgumentException -> 0x0029 }
            if (r9 == 0) goto L_0x002b
            int r9 = r8.getColumnIndexOrThrow(r0)     // Catch:{ IllegalArgumentException -> 0x0029 }
            java.lang.String r9 = r8.getString(r9)     // Catch:{ IllegalArgumentException -> 0x0029 }
            if (r8 == 0) goto L_0x0028
            r8.close()
        L_0x0028:
            return r9
        L_0x0029:
            r9 = move-exception
            goto L_0x0035
        L_0x002b:
            if (r8 == 0) goto L_0x0051
        L_0x002d:
            r8.close()
            goto L_0x0051
        L_0x0031:
            r9 = move-exception
            goto L_0x0054
        L_0x0033:
            r9 = move-exception
            r8 = r7
        L_0x0035:
            java.lang.String r10 = "FileUtils"
            java.util.Locale r11 = java.util.Locale.getDefault()     // Catch:{ all -> 0x0052 }
            java.lang.String r0 = "getDataColumn: _data - [%s]"
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0052 }
            r2 = 0
            java.lang.String r9 = r9.getMessage()     // Catch:{ all -> 0x0052 }
            r1[r2] = r9     // Catch:{ all -> 0x0052 }
            java.lang.String r9 = java.lang.String.format(r11, r0, r1)     // Catch:{ all -> 0x0052 }
            android.util.Log.i(r10, r9)     // Catch:{ all -> 0x0052 }
            if (r8 == 0) goto L_0x0051
            goto L_0x002d
        L_0x0051:
            return r7
        L_0x0052:
            r9 = move-exception
            r7 = r8
        L_0x0054:
            if (r7 == 0) goto L_0x0059
            r7.close()
        L_0x0059:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.commonbase.utils.FileUtils.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    public static String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if (Annotation.CONTENT.equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, (String) null, (String[]) null);
            } else if (Annotation.FILE.equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(":");
            if ("primary".equalsIgnoreCase(split[0])) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if (!TextUtils.isEmpty(documentId)) {
                try {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue()), (String) null, (String[]) null);
                } catch (NumberFormatException e) {
                    Log.i(TAG, e.getMessage());
                    return null;
                }
            }
        } else if (isMediaDocument(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
            String str = split2[0];
            if ("image".equals(str)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(str)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
        }
        return null;
    }
}
