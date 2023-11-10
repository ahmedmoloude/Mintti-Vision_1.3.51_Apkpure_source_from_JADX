package p027id.zelory.compressor.constraint;

import com.itextpdf.text.Annotation;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p031io.FilesKt;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003H\u0016J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo31393d2 = {"Lid/zelory/compressor/constraint/DestinationConstraint;", "Lid/zelory/compressor/constraint/Constraint;", "destination", "Ljava/io/File;", "(Ljava/io/File;)V", "isSatisfied", "", "imageFile", "satisfy", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.DestinationConstraint */
/* compiled from: DestinationConstraint.kt */
public final class DestinationConstraint implements Constraint {
    private final File destination;

    public DestinationConstraint(File file) {
        Intrinsics.checkParameterIsNotNull(file, Annotation.DESTINATION);
        this.destination = file;
    }

    public boolean isSatisfied(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return Intrinsics.areEqual((Object) file.getAbsolutePath(), (Object) this.destination.getAbsolutePath());
    }

    public File satisfy(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return FilesKt.copyTo$default(file, this.destination, true, 0, 4, (Object) null);
    }
}
