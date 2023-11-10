package p027id.zelory.compressor;

import android.graphics.Bitmap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import p027id.zelory.compressor.constraint.Compression;
import p027id.zelory.compressor.constraint.DefaultConstraintKt;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, mo31393d2 = {"<anonymous>", "", "Lid/zelory/compressor/constraint/Compression;", "invoke"}, mo31394k = 3, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.Compressor$compress$2 */
/* compiled from: Compressor.kt */
final class Compressor$compress$2 extends Lambda implements Function1<Compression, Unit> {
    public static final Compressor$compress$2 INSTANCE = new Compressor$compress$2();

    Compressor$compress$2() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Compression) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(Compression compression) {
        Intrinsics.checkParameterIsNotNull(compression, "$receiver");
        DefaultConstraintKt.default$default(compression, 0, 0, (Bitmap.CompressFormat) null, 0, 15, (Object) null);
    }
}
