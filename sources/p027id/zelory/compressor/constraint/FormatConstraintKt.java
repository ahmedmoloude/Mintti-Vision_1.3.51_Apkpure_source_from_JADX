package p027id.zelory.compressor.constraint;

import android.graphics.Bitmap;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0000\u001a\u00020\u0003¨\u0006\u0004"}, mo31393d2 = {"format", "", "Lid/zelory/compressor/constraint/Compression;", "Landroid/graphics/Bitmap$CompressFormat;", "compressor_release"}, mo31394k = 2, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.FormatConstraintKt */
/* compiled from: FormatConstraint.kt */
public final class FormatConstraintKt {
    public static final void format(Compression compression, Bitmap.CompressFormat compressFormat) {
        Intrinsics.checkParameterIsNotNull(compression, "$this$format");
        Intrinsics.checkParameterIsNotNull(compressFormat, DublinCoreProperties.FORMAT);
        compression.constraint(new FormatConstraint(compressFormat));
    }
}
