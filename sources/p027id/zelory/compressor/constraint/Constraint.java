package p027id.zelory.compressor.constraint;

import java.io.File;
import kotlin.Metadata;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, mo31393d2 = {"Lid/zelory/compressor/constraint/Constraint;", "", "isSatisfied", "", "imageFile", "Ljava/io/File;", "satisfy", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.Constraint */
/* compiled from: Constraint.kt */
public interface Constraint {
    boolean isSatisfied(File file);

    File satisfy(File file);
}
