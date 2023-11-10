package p027id.zelory.compressor.constraint;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006¨\u0006\b"}, mo31393d2 = {"size", "", "Lid/zelory/compressor/constraint/Compression;", "maxFileSize", "", "stepSize", "", "maxIteration", "compressor_release"}, mo31394k = 2, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.SizeConstraintKt */
/* compiled from: SizeConstraint.kt */
public final class SizeConstraintKt {
    public static /* synthetic */ void size$default(Compression compression, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 10;
        }
        if ((i3 & 4) != 0) {
            i2 = 10;
        }
        size(compression, j, i, i2);
    }

    public static final void size(Compression compression, long j, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(compression, "$this$size");
        compression.constraint(new SizeConstraint(j, i, i2, 0, 8, (DefaultConstructorMarker) null));
    }
}
