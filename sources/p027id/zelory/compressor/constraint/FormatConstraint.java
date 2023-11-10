package p027id.zelory.compressor.constraint;

import android.graphics.Bitmap;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import p027id.zelory.compressor.UtilKt;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, mo31393d2 = {"Lid/zelory/compressor/constraint/FormatConstraint;", "Lid/zelory/compressor/constraint/Constraint;", "format", "Landroid/graphics/Bitmap$CompressFormat;", "(Landroid/graphics/Bitmap$CompressFormat;)V", "isSatisfied", "", "imageFile", "Ljava/io/File;", "satisfy", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.FormatConstraint */
/* compiled from: FormatConstraint.kt */
public final class FormatConstraint implements Constraint {
    private final Bitmap.CompressFormat format;

    public FormatConstraint(Bitmap.CompressFormat compressFormat) {
        Intrinsics.checkParameterIsNotNull(compressFormat, DublinCoreProperties.FORMAT);
        this.format = compressFormat;
    }

    public boolean isSatisfied(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return this.format == UtilKt.compressFormat(file);
    }

    public File satisfy(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return UtilKt.overWrite$default(file, UtilKt.loadBitmap(file), this.format, 0, 8, (Object) null);
    }
}
