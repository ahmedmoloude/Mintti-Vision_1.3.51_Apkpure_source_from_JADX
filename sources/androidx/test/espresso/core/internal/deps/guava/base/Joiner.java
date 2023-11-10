package androidx.test.espresso.core.internal.deps.guava.base;

import java.io.IOException;
import java.util.Iterator;

public class Joiner {
    private final String separator;

    private Joiner(String str) {
        this.separator = (String) Preconditions.checkNotNull(str);
    }

    /* renamed from: on */
    public static Joiner m59on(String str) {
        return new Joiner(str);
    }

    public <A extends Appendable> A appendTo(A a, Iterator it) throws IOException {
        Preconditions.checkNotNull(a);
        if (it.hasNext()) {
            a.append(toString(it.next()));
            while (it.hasNext()) {
                a.append(this.separator);
                a.append(toString(it.next()));
            }
        }
        return a;
    }

    public final String join(Iterable<?> iterable) {
        return join(iterable.iterator());
    }

    /* access modifiers changed from: package-private */
    public CharSequence toString(Object obj) {
        Preconditions.checkNotNull(obj);
        return obj instanceof CharSequence ? (CharSequence) obj : obj.toString();
    }

    public final String join(Iterator<?> it) {
        return appendTo(new StringBuilder(), it).toString();
    }

    public final StringBuilder appendTo(StringBuilder sb, Iterator<?> it) {
        try {
            appendTo(sb, (Iterator) it);
            return sb;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
