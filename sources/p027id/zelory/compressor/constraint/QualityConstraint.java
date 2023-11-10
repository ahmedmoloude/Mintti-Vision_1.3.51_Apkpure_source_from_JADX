package p027id.zelory.compressor.constraint;

import android.graphics.Bitmap;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import p027id.zelory.compressor.UtilKt;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo31393d2 = {"Lid/zelory/compressor/constraint/QualityConstraint;", "Lid/zelory/compressor/constraint/Constraint;", "quality", "", "(I)V", "isResolved", "", "isSatisfied", "imageFile", "Ljava/io/File;", "satisfy", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.QualityConstraint */
/* compiled from: QualityConstraint.kt */
public final class QualityConstraint implements Constraint {
    private boolean isResolved;
    private final int quality;

    public QualityConstraint(int i) {
        this.quality = i;
    }

    public boolean isSatisfied(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return this.isResolved;
    }

    public File satisfy(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        File overWrite$default = UtilKt.overWrite$default(file, UtilKt.loadBitmap(file), (Bitmap.CompressFormat) null, this.quality, 4, (Object) null);
        this.isResolved = true;
        return overWrite$default;
    }
}
