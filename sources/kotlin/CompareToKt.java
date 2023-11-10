package kotlin;

import kotlin.jvm.internal.Intrinsics;

@Metadata(mo31392d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000f\n\u0002\b\u0003\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\f¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, mo31393d2 = {"compareTo", "", "T", "", "other", "(Ljava/lang/Comparable;Ljava/lang/Object;)I", "kotlin-stdlib"}, mo31394k = 2, mo31395mv = {1, 7, 1}, mo31397xi = 48)
/* compiled from: compareTo.kt */
public final class CompareToKt {
    private static final <T> int compareTo(Comparable<? super T> comparable, T t) {
        Intrinsics.checkNotNullParameter(comparable, "<this>");
        return comparable.compareTo(t);
    }
}
