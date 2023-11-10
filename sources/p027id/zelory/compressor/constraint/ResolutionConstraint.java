package p027id.zelory.compressor.constraint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import p027id.zelory.compressor.UtilKt;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo31393d2 = {"Lid/zelory/compressor/constraint/ResolutionConstraint;", "Lid/zelory/compressor/constraint/Constraint;", "width", "", "height", "(II)V", "isSatisfied", "", "imageFile", "Ljava/io/File;", "satisfy", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.constraint.ResolutionConstraint */
/* compiled from: ResolutionConstraint.kt */
public final class ResolutionConstraint implements Constraint {
    private final int height;
    private final int width;

    public ResolutionConstraint(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public boolean isSatisfied(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        if (UtilKt.calculateInSampleSize(options, this.width, this.height) <= 1) {
            return true;
        }
        return false;
    }

    public File satisfy(File file) {
        Intrinsics.checkParameterIsNotNull(file, "imageFile");
        return UtilKt.overWrite$default(file, UtilKt.determineImageRotation(file, UtilKt.decodeSampledBitmapFromFile(file, this.width, this.height)), (Bitmap.CompressFormat) null, 0, 12, (Object) null);
    }
}
