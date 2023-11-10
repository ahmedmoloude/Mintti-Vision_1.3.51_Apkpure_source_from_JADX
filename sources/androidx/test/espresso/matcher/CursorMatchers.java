package androidx.test.espresso.matcher;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public final class CursorMatchers {
    private static final CursorDataRetriever<byte[]> BLOB_MATCHER_APPLIER = new CursorDataRetriever<byte[]>() {
        public void describeTo(Description description) {
            description.appendText("with Blob");
        }

        public byte[] getData(Cursor cursor, int i) {
            return cursor.getBlob(i);
        }
    };
    private static final int COLUMN_NOT_FOUND = -1;
    private static final CursorDataRetriever<Double> DOUBLE_MATCHER_APPLIER = new CursorDataRetriever<Double>() {
        public void describeTo(Description description) {
            description.appendText("with Double");
        }

        public Double getData(Cursor cursor, int i) {
            return Double.valueOf(cursor.getDouble(i));
        }
    };
    private static final CursorDataRetriever<Float> FLOAT_MATCHER_APPLIER = new CursorDataRetriever<Float>() {
        public void describeTo(Description description) {
            description.appendText("with Float");
        }

        public Float getData(Cursor cursor, int i) {
            return Float.valueOf(cursor.getFloat(i));
        }
    };
    private static final CursorDataRetriever<Integer> INT_MATCHER_APPLIER = new CursorDataRetriever<Integer>() {
        public void describeTo(Description description) {
            description.appendText("with Int");
        }

        public Integer getData(Cursor cursor, int i) {
            return Integer.valueOf(cursor.getInt(i));
        }
    };
    private static final CursorDataRetriever<Long> LONG_MATCHER_APPLIER = new CursorDataRetriever<Long>() {
        public void describeTo(Description description) {
            description.appendText("with Long");
        }

        public Long getData(Cursor cursor, int i) {
            return Long.valueOf(cursor.getLong(i));
        }
    };
    private static final int MULTIPLE_COLUMNS_FOUND = -2;
    private static final CursorDataRetriever<Short> SHORT_MATCHER_APPLIER = new CursorDataRetriever<Short>() {
        public void describeTo(Description description) {
            description.appendText("with Short");
        }

        public Short getData(Cursor cursor, int i) {
            return Short.valueOf(cursor.getShort(i));
        }
    };
    private static final CursorDataRetriever<String> STRING_MATCHER_APPLIER = new CursorDataRetriever<String>() {
        public void describeTo(Description description) {
            description.appendText("with String");
        }

        public String getData(Cursor cursor, int i) {
            return cursor.getString(i);
        }
    };
    private static final int USE_COLUMN_PICKER = -3;

    private interface CursorDataRetriever<T> extends SelfDescribing {
        T getData(Cursor cursor, int i);
    }

    private CursorMatchers() {
    }

    public static CursorMatcher withRowBlob(int i, Matcher<byte[]> matcher) {
        return new CursorMatcher(i, (Matcher) matcher, (CursorDataRetriever) BLOB_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowDouble(int i, double d) {
        return withRowDouble(i, (Matcher<Double>) Matchers.m1139is(Double.valueOf(d)));
    }

    public static CursorMatcher withRowFloat(int i, float f) {
        return withRowFloat(i, (Matcher<Float>) Matchers.m1139is(Float.valueOf(f)));
    }

    public static CursorMatcher withRowInt(int i, int i2) {
        return withRowInt(i, (Matcher<Integer>) Matchers.m1139is(Integer.valueOf(i2)));
    }

    public static CursorMatcher withRowLong(int i, long j) {
        return withRowLong(i, (Matcher<Long>) Matchers.m1139is(Long.valueOf(j)));
    }

    public static CursorMatcher withRowShort(int i, Matcher<Short> matcher) {
        return new CursorMatcher(i, (Matcher) matcher, (CursorDataRetriever) SHORT_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowString(int i, String str) {
        return withRowString(i, (Matcher<String>) Matchers.m1139is(str));
    }

    /* access modifiers changed from: private */
    public static int findColumnIndex(Matcher<String> matcher, Cursor cursor) {
        String[] columnNames = cursor.getColumnNames();
        int i = -1;
        for (int i2 = 0; i2 < columnNames.length; i2++) {
            if (matcher.matches(columnNames[i2])) {
                if (i != -1) {
                    return -2;
                }
                i = i2;
            }
        }
        return i;
    }

    public static CursorMatcher withRowBlob(int i, byte[] bArr) {
        return withRowBlob(i, (Matcher<byte[]>) Matchers.m1139is(bArr));
    }

    public static CursorMatcher withRowDouble(int i, Matcher<Double> matcher) {
        return new CursorMatcher(i, (Matcher) matcher, (CursorDataRetriever) DOUBLE_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowFloat(int i, Matcher<Float> matcher) {
        return new CursorMatcher(i, (Matcher) matcher, (CursorDataRetriever) FLOAT_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowInt(int i, Matcher<Integer> matcher) {
        return new CursorMatcher(i, (Matcher) matcher, (CursorDataRetriever) INT_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowLong(int i, Matcher<Long> matcher) {
        return new CursorMatcher(i, (Matcher) matcher, (CursorDataRetriever) LONG_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowShort(int i, short s) {
        return withRowShort(i, (Matcher<Short>) Matchers.m1139is(Short.valueOf(s)));
    }

    public static CursorMatcher withRowString(int i, Matcher<String> matcher) {
        return new CursorMatcher(i, (Matcher) matcher, (CursorDataRetriever) STRING_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowBlob(String str, Matcher<byte[]> matcher) {
        return withRowBlob((Matcher<String>) Matchers.m1139is(str), matcher);
    }

    public static CursorMatcher withRowDouble(String str, double d) {
        return withRowDouble(str, (Matcher<Double>) Matchers.m1139is(Double.valueOf(d)));
    }

    public static CursorMatcher withRowFloat(String str, float f) {
        return withRowFloat(str, (Matcher<Float>) Matchers.m1139is(Float.valueOf(f)));
    }

    public static CursorMatcher withRowInt(String str, int i) {
        return withRowInt(str, (Matcher<Integer>) Matchers.m1139is(Integer.valueOf(i)));
    }

    public static CursorMatcher withRowLong(String str, long j) {
        return withRowLong(str, (Matcher<Long>) Matchers.m1139is(Long.valueOf(j)));
    }

    public static CursorMatcher withRowShort(String str, Matcher<Short> matcher) {
        return withRowShort((Matcher<String>) Matchers.m1139is(str), matcher);
    }

    public static CursorMatcher withRowString(String str, String str2) {
        return withRowString((Matcher<String>) Matchers.m1139is(str), (Matcher<String>) Matchers.m1139is(str2));
    }

    public static CursorMatcher withRowBlob(String str, byte[] bArr) {
        return withRowBlob((Matcher<String>) Matchers.m1139is(str), (Matcher<byte[]>) Matchers.m1139is(bArr));
    }

    public static CursorMatcher withRowDouble(String str, Matcher<Double> matcher) {
        return withRowDouble((Matcher<String>) Matchers.m1139is(str), matcher);
    }

    public static CursorMatcher withRowFloat(String str, Matcher<Float> matcher) {
        return withRowFloat((Matcher<String>) Matchers.m1139is(str), matcher);
    }

    public static CursorMatcher withRowInt(String str, Matcher<Integer> matcher) {
        return withRowInt((Matcher<String>) Matchers.m1139is(str), matcher);
    }

    public static CursorMatcher withRowLong(String str, Matcher<Long> matcher) {
        return withRowLong((Matcher<String>) Matchers.m1139is(str), matcher);
    }

    public static CursorMatcher withRowShort(String str, short s) {
        return withRowShort(str, (Matcher<Short>) Matchers.m1139is(Short.valueOf(s)));
    }

    public static CursorMatcher withRowString(String str, Matcher<String> matcher) {
        return withRowString((Matcher<String>) Matchers.m1139is(str), matcher);
    }

    public static CursorMatcher withRowBlob(Matcher<String> matcher, Matcher<byte[]> matcher2) {
        return new CursorMatcher((Matcher) matcher, (Matcher) matcher2, (CursorDataRetriever) BLOB_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowDouble(Matcher<String> matcher, Matcher<Double> matcher2) {
        return new CursorMatcher((Matcher) matcher, (Matcher) matcher2, (CursorDataRetriever) DOUBLE_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowFloat(Matcher<String> matcher, Matcher<Float> matcher2) {
        return new CursorMatcher((Matcher) matcher, (Matcher) matcher2, (CursorDataRetriever) FLOAT_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowInt(Matcher<String> matcher, Matcher<Integer> matcher2) {
        return new CursorMatcher((Matcher) matcher, (Matcher) matcher2, (CursorDataRetriever) INT_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowLong(Matcher<String> matcher, Matcher<Long> matcher2) {
        return new CursorMatcher((Matcher) matcher, (Matcher) matcher2, (CursorDataRetriever) LONG_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowShort(Matcher<String> matcher, Matcher<Short> matcher2) {
        return new CursorMatcher((Matcher) matcher, (Matcher) matcher2, (CursorDataRetriever) SHORT_MATCHER_APPLIER);
    }

    public static CursorMatcher withRowString(Matcher<String> matcher, Matcher<String> matcher2) {
        return new CursorMatcher((Matcher) matcher, (Matcher) matcher2, (CursorDataRetriever) STRING_MATCHER_APPLIER);
    }

    public static class CursorMatcher extends BoundedMatcher<Object, Cursor> {
        private boolean checkColumns;
        private final int columnIndex;
        private final Matcher<String> columnNameMatcher;
        private final CursorDataRetriever<?> cursorDataRetriever;
        private final Matcher<?> valueMatcher;

        private CursorMatcher(int i, Matcher<?> matcher, CursorDataRetriever<?> cursorDataRetriever2) {
            super(Cursor.class);
            boolean z = false;
            this.checkColumns = false;
            Preconditions.checkArgument(i >= 0 ? true : z);
            this.columnIndex = i;
            this.valueMatcher = (Matcher) Preconditions.checkNotNull(matcher);
            this.cursorDataRetriever = (CursorDataRetriever) Preconditions.checkNotNull(cursorDataRetriever2);
            this.columnNameMatcher = null;
        }

        public void describeTo(Description description) {
            description.appendText("an instance of android.database.Cursor and Rows with column: ");
            int i = this.columnIndex;
            if (i < 0) {
                this.columnNameMatcher.describeTo(description);
            } else {
                StringBuilder sb = new StringBuilder(19);
                sb.append("index = ");
                sb.append(i);
                description.appendText(sb.toString());
            }
            description.appendText(" ").appendDescriptionOf(this.cursorDataRetriever).appendText(" matching ").appendDescriptionOf(this.valueMatcher);
        }

        public boolean matchesSafely(Cursor cursor) {
            int i = this.columnIndex;
            StringDescription stringDescription = new StringDescription();
            if (i >= 0 || (i = CursorMatchers.findColumnIndex(this.columnNameMatcher, cursor)) >= 0) {
                try {
                    Object data = this.cursorDataRetriever.getData(cursor, i);
                    boolean matches = this.valueMatcher.matches(data);
                    if (!matches) {
                        stringDescription.appendText("value at column ").appendValue(Integer.valueOf(i)).appendText(" ");
                        this.valueMatcher.describeMismatch(data, stringDescription);
                    }
                    return matches;
                } catch (CursorIndexOutOfBoundsException e) {
                    stringDescription.appendText("Column index ").appendValue(Integer.valueOf(i)).appendText(" is invalid");
                    if (!this.checkColumns) {
                        return false;
                    }
                    throw new IllegalArgumentException(stringDescription.toString(), e);
                }
            } else {
                if (i == -2) {
                    stringDescription.appendText("Multiple columns in ").appendValue(cursor.getColumnNames()).appendText(" match ").appendDescriptionOf(this.columnNameMatcher);
                } else {
                    stringDescription.appendText("Couldn't find column in ").appendValue(cursor.getColumnNames()).appendText(" matching ").appendDescriptionOf(this.columnNameMatcher);
                }
                if (!this.checkColumns) {
                    return false;
                }
                throw new IllegalArgumentException(stringDescription.toString());
            }
        }

        public CursorMatcher withStrictColumnChecks(boolean z) {
            this.checkColumns = z;
            return this;
        }

        /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<java.lang.String>, java.lang.Object] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private CursorMatcher(org.hamcrest.Matcher<java.lang.String> r2, org.hamcrest.Matcher<?> r3, androidx.test.espresso.matcher.CursorMatchers.CursorDataRetriever<?> r4) {
            /*
                r1 = this;
                java.lang.Class<android.database.Cursor> r0 = android.database.Cursor.class
                r1.<init>(r0)
                r0 = 0
                r1.checkColumns = r0
                java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
                org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
                r1.columnNameMatcher = r2
                java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r3)
                org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
                r1.valueMatcher = r2
                java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r4)
                androidx.test.espresso.matcher.CursorMatchers$CursorDataRetriever r2 = (androidx.test.espresso.matcher.CursorMatchers.CursorDataRetriever) r2
                r1.cursorDataRetriever = r2
                r2 = -3
                r1.columnIndex = r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.CursorMatchers.CursorMatcher.<init>(org.hamcrest.Matcher, org.hamcrest.Matcher, androidx.test.espresso.matcher.CursorMatchers$CursorDataRetriever):void");
        }
    }
}
