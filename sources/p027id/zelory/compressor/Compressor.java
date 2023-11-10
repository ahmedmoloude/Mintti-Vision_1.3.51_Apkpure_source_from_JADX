package p027id.zelory.compressor;

import android.content.Context;
import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import p027id.zelory.compressor.constraint.Compression;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JF\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\t2\u0019\b\u0002\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, mo31393d2 = {"Lid/zelory/compressor/Compressor;", "", "()V", "compress", "Ljava/io/File;", "context", "Landroid/content/Context;", "imageFile", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "compressionPatch", "Lkotlin/Function1;", "Lid/zelory/compressor/constraint/Compression;", "", "Lkotlin/ExtensionFunctionType;", "(Landroid/content/Context;Ljava/io/File;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "compressor_release"}, mo31394k = 1, mo31395mv = {1, 1, 16})
/* renamed from: id.zelory.compressor.Compressor */
/* compiled from: Compressor.kt */
public final class Compressor {
    public static final Compressor INSTANCE = new Compressor();

    private Compressor() {
    }

    public static /* synthetic */ Object compress$default(Compressor compressor, Context context, File file, CoroutineContext coroutineContext, Function1 function1, Continuation continuation, int i, Object obj) {
        if ((i & 4) != 0) {
            coroutineContext = (CoroutineContext) Dispatchers.getIO();
        }
        CoroutineContext coroutineContext2 = coroutineContext;
        if ((i & 8) != 0) {
            function1 = Compressor$compress$2.INSTANCE;
        }
        return compressor.compress(context, file, coroutineContext2, function1, continuation);
    }

    public final Object compress(Context context, File file, CoroutineContext coroutineContext, Function1<? super Compression, Unit> function1, Continuation<? super File> continuation) {
        return BuildersKt.withContext(coroutineContext, new Compressor$compress$3(function1, context, file, (Continuation) null), continuation);
    }
}
