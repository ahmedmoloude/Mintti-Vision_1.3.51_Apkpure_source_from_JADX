package p027id.zelory.compressor;

import android.content.Context;
import java.io.File;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import p027id.zelory.compressor.constraint.Compression;
import p027id.zelory.compressor.constraint.Constraint;

@Metadata(mo31391bv = {1, 0, 3}, mo31392d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H@¢\u0006\u0004\b\u0003\u0010\u0004"}, mo31393d2 = {"<anonymous>", "Ljava/io/File;", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo31394k = 3, mo31395mv = {1, 1, 16})
@DebugMetadata(mo32112c = "id.zelory.compressor.Compressor$compress$3", mo32113f = "Compressor.kt", mo32114i = {}, mo32115l = {}, mo32116m = "invokeSuspend", mo32117n = {}, mo32118s = {})
/* renamed from: id.zelory.compressor.Compressor$compress$3 */
/* compiled from: Compressor.kt */
final class Compressor$compress$3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super File>, Object> {
    final /* synthetic */ Function1 $compressionPatch;
    final /* synthetic */ Context $context;
    final /* synthetic */ File $imageFile;
    int label;

    /* renamed from: p$ */
    private CoroutineScope f2006p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    Compressor$compress$3(Function1 function1, Context context, File file, Continuation continuation) {
        super(2, continuation);
        this.$compressionPatch = function1;
        this.$context = context;
        this.$imageFile = file;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        Compressor$compress$3 compressor$compress$3 = new Compressor$compress$3(this.$compressionPatch, this.$context, this.$imageFile, continuation);
        compressor$compress$3.f2006p$ = (CoroutineScope) obj;
        return compressor$compress$3;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((Compressor$compress$3) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            Compression compression = new Compression();
            this.$compressionPatch.invoke(compression);
            File copyToCache = UtilKt.copyToCache(this.$context, this.$imageFile);
            for (Constraint constraint : compression.getConstraints$compressor_release()) {
                while (!constraint.isSatisfied(copyToCache)) {
                    copyToCache = constraint.satisfy(copyToCache);
                }
            }
            return copyToCache;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
