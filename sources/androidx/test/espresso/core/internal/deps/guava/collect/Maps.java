package androidx.test.espresso.core.internal.deps.guava.collect;

import androidx.test.espresso.core.internal.deps.guava.base.Function;
import java.util.HashMap;
import java.util.Map;

public final class Maps {

    /* renamed from: androidx.test.espresso.core.internal.deps.guava.collect.Maps$1 */
    class C07531 extends TransformedIterator {
        /* access modifiers changed from: package-private */
        public /* bridge */ /* synthetic */ Object transform(Object obj) {
            throw null;
        }
    }

    private enum EntryFunction implements Function<Map.Entry<?, ?>, Object> {
        KEY {
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getKey();
            }
        },
        VALUE {
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getValue();
            }
        }
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    static String toStringImpl(Map<?, ?> map) {
        StringBuilder newStringBuilderForCollection = Collections2.newStringBuilderForCollection(map.size());
        newStringBuilderForCollection.append('{');
        boolean z = true;
        for (Map.Entry next : map.entrySet()) {
            if (!z) {
                newStringBuilderForCollection.append(", ");
            }
            newStringBuilderForCollection.append(next.getKey());
            newStringBuilderForCollection.append('=');
            newStringBuilderForCollection.append(next.getValue());
            z = false;
        }
        newStringBuilderForCollection.append('}');
        return newStringBuilderForCollection.toString();
    }

    static <V> Function<Map.Entry<?, V>, V> valueFunction() {
        return EntryFunction.VALUE;
    }

    static boolean equalsImpl(Map<?, ?> map, Object obj) {
        if (map == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return map.entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }
}
