package p027id.zelory.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import java.io.File;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p031io.FilesKt;
import kotlin.text.StringsKt;
import p028io.jsonwebtoken.JwtParser;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u001a\u0010\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\rH\u0000\u001a\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007\u001a\u0016\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0010\u001a\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\r\u001a*\u0010\u0014\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00102\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0007\u001a*\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\r2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0007\u001a\n\u0010\u001b\u001a\u00020\u0016*\u00020\r\u001a\n\u0010\u001c\u001a\u00020\u0001*\u00020\u0016\"\u0016\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, mo31393d2 = {"separator", "", "kotlin.jvm.PlatformType", "cachePath", "context", "Landroid/content/Context;", "calculateInSampleSize", "", "options", "Landroid/graphics/BitmapFactory$Options;", "reqWidth", "reqHeight", "copyToCache", "Ljava/io/File;", "imageFile", "decodeSampledBitmapFromFile", "Landroid/graphics/Bitmap;", "determineImageRotation", "bitmap", "loadBitmap", "overWrite", "format", "Landroid/graphics/Bitmap$CompressFormat;", "quality", "saveBitmap", "", "destination", "compressFormat", "extension", "compressor_release"}, mo31394k = 2, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.UtilKt */
/* compiled from: Util.kt */
public final class UtilKt {
    private static final String separator = File.separator;

    @Metadata(mo31391bv = {1, 0, 3}, mo31394k = 3, mo31395mv = {1, 1, 16})
    /* renamed from: id.zelory.compressor.UtilKt$WhenMappings */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Bitmap.CompressFormat.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[Bitmap.CompressFormat.PNG.ordinal()] = 1;
            iArr[Bitmap.CompressFormat.WEBP.ordinal()] = 2;
        }
    }

    private static final String cachePath(Context context) {
        StringBuilder sb = new StringBuilder();
        File cacheDir = context.getCacheDir();
        Intrinsics.checkExpressionValueIsNotNull(cacheDir, "context.cacheDir");
        sb.append(cacheDir.getPath());
        String str = separator;
        sb.append(str);
        sb.append("compressor");
        sb.append(str);
        return sb.toString();
    }

    public static final Bitmap.CompressFormat compressFormat(File file) {
        Intrinsics.checkParameterIsNotNull(file, "$this$compressFormat");
        String extension = FilesKt.getExtension(file);
        if (extension != null) {
            String lowerCase = extension.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            int hashCode = lowerCase.hashCode();
            if (hashCode != 111145) {
                if (hashCode == 3645340 && lowerCase.equals("webp")) {
                    return Bitmap.CompressFormat.WEBP;
                }
            } else if (lowerCase.equals("png")) {
                return Bitmap.CompressFormat.PNG;
            }
            return Bitmap.CompressFormat.JPEG;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    public static final String extension(Bitmap.CompressFormat compressFormat) {
        Intrinsics.checkParameterIsNotNull(compressFormat, "$this$extension");
        int i = WhenMappings.$EnumSwitchMapping$0[compressFormat.ordinal()];
        if (i != 1) {
            return i != 2 ? "jpg" : "webp";
        }
        return "png";
    }

    public static final Bitmap loadBitmap(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        Bitmap decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
        Intrinsics.checkExpressionValueIsNotNull(decodeFile, "this");
        return determineImageRotation(file, decodeFile);
    }

    public static final Bitmap decodeSampledBitmapFromFile(File file, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        Intrinsics.checkExpressionValueIsNotNull(decodeFile, "BitmapFactory.decodeFile…eFile.absolutePath, this)");
        Intrinsics.checkExpressionValueIsNotNull(decodeFile, "BitmapFactory.Options().…absolutePath, this)\n    }");
        return decodeFile;
    }

    public static final int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        Pair pair = TuplesKt.m1092to(Integer.valueOf(options.outHeight), Integer.valueOf(options.outWidth));
        int intValue = ((Number) pair.component1()).intValue();
        int intValue2 = ((Number) pair.component2()).intValue();
        int i3 = 1;
        if (intValue > i2 || intValue2 > i) {
            int i4 = intValue / 2;
            int i5 = intValue2 / 2;
            while (i4 / i3 >= i2 && i5 / i3 >= i) {
                i3 *= 2;
            }
        }
        return i3;
    }

    public static final Bitmap determineImageRotation(File file, Bitmap bitmap) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        int attributeInt = new ExifInterface(file.getAbsolutePath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        if (attributeInt == 3) {
            matrix.postRotate(180.0f);
        } else if (attributeInt == 6) {
            matrix.postRotate(90.0f);
        } else if (attributeInt == 8) {
            matrix.postRotate(270.0f);
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Intrinsics.checkExpressionValueIsNotNull(createBitmap, "Bitmap.createBitmap(bitm…map.height, matrix, true)");
        return createBitmap;
    }

    public static final File copyToCache(Context context, File file) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return FilesKt.copyTo$default(file, new File(cachePath(context) + file.getName()), true, 0, 4, (Object) null);
    }

    public static /* synthetic */ File overWrite$default(File file, Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            compressFormat = compressFormat(file);
        }
        if ((i2 & 8) != 0) {
            i = 100;
        }
        return overWrite(file, bitmap, compressFormat, i);
    }

    public static final File overWrite(File file, Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i) {
        File file2;
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        Intrinsics.checkParameterIsNotNull(compressFormat, DublinCoreProperties.FORMAT);
        if (compressFormat == compressFormat(file)) {
            file2 = file;
        } else {
            StringBuilder sb = new StringBuilder();
            String absolutePath = file.getAbsolutePath();
            Intrinsics.checkExpressionValueIsNotNull(absolutePath, "imageFile.absolutePath");
            sb.append(StringsKt.substringBeforeLast$default(absolutePath, ".", (String) null, 2, (Object) null));
            sb.append(JwtParser.SEPARATOR_CHAR);
            sb.append(extension(compressFormat));
            file2 = new File(sb.toString());
        }
        file.delete();
        saveBitmap(bitmap, file2, compressFormat, i);
        return file2;
    }

    public static /* synthetic */ void saveBitmap$default(Bitmap bitmap, File file, Bitmap.CompressFormat compressFormat, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            compressFormat = compressFormat(file);
        }
        if ((i2 & 8) != 0) {
            i = 100;
        }
        saveBitmap(bitmap, file, compressFormat, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0037  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void saveBitmap(android.graphics.Bitmap r2, java.io.File r3, android.graphics.Bitmap.CompressFormat r4, int r5) {
        /*
            java.lang.String r0 = "bitmap"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r2, r0)
            java.lang.String r0 = "destination"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r3, r0)
            java.lang.String r0 = "format"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r4, r0)
            java.io.File r0 = r3.getParentFile()
            if (r0 == 0) goto L_0x0018
            r0.mkdirs()
        L_0x0018:
            r0 = 0
            java.io.FileOutputStream r0 = (java.io.FileOutputStream) r0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0034 }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x0034 }
            r1.<init>(r3)     // Catch:{ all -> 0x0034 }
            r3 = r1
            java.io.OutputStream r3 = (java.io.OutputStream) r3     // Catch:{ all -> 0x0031 }
            r2.compress(r4, r5, r3)     // Catch:{ all -> 0x0031 }
            r1.flush()
            r1.close()
            return
        L_0x0031:
            r2 = move-exception
            r0 = r1
            goto L_0x0035
        L_0x0034:
            r2 = move-exception
        L_0x0035:
            if (r0 == 0) goto L_0x003d
            r0.flush()
            r0.close()
        L_0x003d:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p027id.zelory.compressor.UtilKt.saveBitmap(android.graphics.Bitmap, java.io.File, android.graphics.Bitmap$CompressFormat, int):void");
    }
}
