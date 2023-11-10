package p027id.zelory.compressor.constraint;

import android.graphics.Bitmap;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a2\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0004¨\u0006\t"}, mo31393d2 = {"default", "", "Lid/zelory/compressor/constraint/Compression;", "width", "", "height", "format", "Landroid/graphics/Bitmap$CompressFormat;", "quality", "compressor_release"}, mo31394k = 2, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.DefaultConstraintKt */
/* compiled from: DefaultConstraint.kt */
public final class DefaultConstraintKt {
    public static /* synthetic */ void default$default(Compression compression, int i, int i2, Bitmap.CompressFormat compressFormat, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = 612;
        }
        if ((i4 & 2) != 0) {
            i2 = 816;
        }
        if ((i4 & 4) != 0) {
            compressFormat = Bitmap.CompressFormat.JPEG;
        }
        if ((i4 & 8) != 0) {
            i3 = 80;
        }
        m1152default(compression, i, i2, compressFormat, i3);
    }

    /* renamed from: default  reason: not valid java name */
    public static final void m1152default(Compression compression, int i, int i2, Bitmap.CompressFormat compressFormat, int i3) {
        Intrinsics.checkParameterIsNotNull(compression, "$this$default");
        Intrinsics.checkParameterIsNotNull(compressFormat, DublinCoreProperties.FORMAT);
        compression.constraint(new DefaultConstraint(i, i2, compressFormat, i3));
    }
}
