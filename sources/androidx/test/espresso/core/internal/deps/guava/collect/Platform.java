package androidx.test.espresso.core.internal.deps.guava.collect;

import java.lang.reflect.Array;
import java.util.Arrays;

final class Platform {
    static <T> T[] copy(Object[] objArr, int i, int i2, T[] tArr) {
        return Arrays.copyOfRange(objArr, i, i2, tArr.getClass());
    }

    static <T> T[] newArray(T[] tArr, int i) {
        return (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i);
    }
}
