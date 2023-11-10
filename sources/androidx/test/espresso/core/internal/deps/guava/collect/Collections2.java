package androidx.test.espresso.core.internal.deps.guava.collect;

import com.itextpdf.text.html.HtmlTags;

public final class Collections2 {
    static StringBuilder newStringBuilderForCollection(int i) {
        CollectPreconditions.checkNonnegative(i, HtmlTags.SIZE);
        return new StringBuilder((int) Math.min(((long) i) * 8, 1073741824));
    }
}
