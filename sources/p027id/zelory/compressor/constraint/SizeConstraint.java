package p027id.zelory.compressor.constraint;

import android.graphics.Bitmap;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import p027id.zelory.compressor.UtilKt;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\t\u001a\u00020\u0005X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, mo31393d2 = {"Lid/zelory/compressor/constraint/SizeConstraint;", "Lid/zelory/compressor/constraint/Constraint;", "maxFileSize", "", "stepSize", "", "maxIteration", "minQuality", "(JIII)V", "iteration", "isSatisfied", "", "imageFile", "Ljava/io/File;", "satisfy", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.SizeConstraint */
/* compiled from: SizeConstraint.kt */
public final class SizeConstraint implements Constraint {
    private int iteration;
    private final long maxFileSize;
    private final int maxIteration;
    private final int minQuality;
    private final int stepSize;

    public SizeConstraint(long j, int i, int i2, int i3) {
        this.maxFileSize = j;
        this.stepSize = i;
        this.maxIteration = i2;
        this.minQuality = i3;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ SizeConstraint(long j, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, (i4 & 2) != 0 ? 10 : i, (i4 & 4) != 0 ? 10 : i2, (i4 & 8) != 0 ? 10 : i3);
    }

    public boolean isSatisfied(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return file.length() <= this.maxFileSize || this.iteration >= this.maxIteration;
    }

    public File satisfy(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        boolean z = true;
        int i = this.iteration + 1;
        this.iteration = i;
        Integer valueOf = Integer.valueOf(100 - (i * this.stepSize));
        int intValue = valueOf.intValue();
        int i2 = this.minQuality;
        if (intValue < i2) {
            z = false;
        }
        if (!z) {
            valueOf = null;
        }
        if (valueOf != null) {
            i2 = valueOf.intValue();
        }
        return UtilKt.overWrite$default(file, UtilKt.loadBitmap(file), (Bitmap.CompressFormat) null, i2, 4, (Object) null);
    }
}
