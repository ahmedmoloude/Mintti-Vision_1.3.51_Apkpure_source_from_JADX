package p027id.zelory.compressor.constraint;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, mo31393d2 = {"Lid/zelory/compressor/constraint/Compression;", "", "()V", "constraints", "", "Lid/zelory/compressor/constraint/Constraint;", "getConstraints$compressor_release", "()Ljava/util/List;", "constraint", "", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.Compression */
/* compiled from: Compression.kt */
public final class Compression {
    private final List<Constraint> constraints = new ArrayList();

    public final List<Constraint> getConstraints$compressor_release() {
        return this.constraints;
    }

    public final void constraint(Constraint constraint) {
        Intrinsics.checkParameterIsNotNull(constraint, "constraint");
        this.constraints.add(constraint);
    }
}
