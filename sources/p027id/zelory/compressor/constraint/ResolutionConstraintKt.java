package p027id.zelory.compressor.constraint;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0006"}, mo31393d2 = {"resolution", "", "Lid/zelory/compressor/constraint/Compression;", "width", "", "height", "compressor_release"}, mo31394k = 2, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.ResolutionConstraintKt */
/* compiled from: ResolutionConstraint.kt */
public final class ResolutionConstraintKt {
    public static final void resolution(Compression compression, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(compression, "$this$resolution");
        compression.constraint(new ResolutionConstraint(i, i2));
    }
}
