package p027id.zelory.compressor.constraint;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0000\u001a\u00020\u0003¨\u0006\u0004"}, mo31393d2 = {"quality", "", "Lid/zelory/compressor/constraint/Compression;", "", "compressor_release"}, mo31394k = 2, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.QualityConstraintKt */
/* compiled from: QualityConstraint.kt */
public final class QualityConstraintKt {
    public static final void quality(Compression compression, int i) {
        Intrinsics.checkParameterIsNotNull(compression, "$this$quality");
        compression.constraint(new QualityConstraint(i));
    }
}
