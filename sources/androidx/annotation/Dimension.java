package androidx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@MustBeDocumented
@Target(allowedTargets = {AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.ANNOTATION_CLASS})
@Retention(AnnotationRetention.BINARY)
@Documented
@java.lang.annotation.Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
@Metadata(mo31392d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0002\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\n\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0006"}, mo31393d2 = {"Landroidx/annotation/Dimension;", "", "unit", "", "()I", "Companion", "annotation"}, mo31394k = 1, mo31395mv = {1, 7, 1}, mo31397xi = 48)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
/* compiled from: Dimension.kt */
public @interface Dimension {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* renamed from: DP */
    public static final int f93DP = 0;

    /* renamed from: PX */
    public static final int f94PX = 1;

    /* renamed from: SP */
    public static final int f95SP = 2;

    int unit() default 1;

    @Metadata(mo31392d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u0007"}, mo31393d2 = {"Landroidx/annotation/Dimension$Companion;", "", "()V", "DP", "", "PX", "SP", "annotation"}, mo31394k = 1, mo31395mv = {1, 7, 1}, mo31397xi = 48)
    /* compiled from: Dimension.kt */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        /* renamed from: DP */
        public static final int f96DP = 0;

        /* renamed from: PX */
        public static final int f97PX = 1;

        /* renamed from: SP */
        public static final int f98SP = 2;

        private Companion() {
        }
    }
}
