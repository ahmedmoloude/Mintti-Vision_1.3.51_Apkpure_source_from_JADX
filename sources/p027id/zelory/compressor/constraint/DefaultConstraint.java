package p027id.zelory.compressor.constraint;

import android.graphics.Bitmap;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import p027id.zelory.compressor.UtilKt;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, mo31393d2 = {"Lid/zelory/compressor/constraint/DefaultConstraint;", "Lid/zelory/compressor/constraint/Constraint;", "width", "", "height", "format", "Landroid/graphics/Bitmap$CompressFormat;", "quality", "(IILandroid/graphics/Bitmap$CompressFormat;I)V", "isResolved", "", "isSatisfied", "imageFile", "Ljava/io/File;", "satisfy", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.DefaultConstraint */
/* compiled from: DefaultConstraint.kt */
public final class DefaultConstraint implements Constraint {
    private final Bitmap.CompressFormat format;
    private final int height;
    private boolean isResolved;
    private final int quality;
    private final int width;

    public DefaultConstraint() {
        this(0, 0, (Bitmap.CompressFormat) null, 0, 15, (DefaultConstructorMarker) null);
    }

    public DefaultConstraint(int i, int i2, Bitmap.CompressFormat compressFormat, int i3) {
        Intrinsics.checkParameterIsNotNull(compressFormat, DublinCoreProperties.FORMAT);
        this.width = i;
        this.height = i2;
        this.format = compressFormat;
        this.quality = i3;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DefaultConstraint(int i, int i2, Bitmap.CompressFormat compressFormat, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 612 : i, (i4 & 2) != 0 ? 816 : i2, (i4 & 4) != 0 ? Bitmap.CompressFormat.JPEG : compressFormat, (i4 & 8) != 0 ? 80 : i3);
    }

    public boolean isSatisfied(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return this.isResolved;
    }

    public File satisfy(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        File overWrite = UtilKt.overWrite(file, UtilKt.determineImageRotation(file, UtilKt.decodeSampledBitmapFromFile(file, this.width, this.height)), this.format, this.quality);
        this.isResolved = true;
        return overWrite;
    }
}
