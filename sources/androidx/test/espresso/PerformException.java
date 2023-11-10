package androidx.test.espresso;

import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.internal.platform.util.TestOutputEmitter;
import java.util.Locale;

public final class PerformException extends RuntimeException implements EspressoException {
    private static final String MESSAGE_FORMAT = "Error performing '%s' on view '%s'.";
    private final String actionDescription;
    private final String viewDescription;

    public static class Builder {
        /* access modifiers changed from: private */
        public String actionDescription;
        /* access modifiers changed from: private */
        public Throwable cause;
        /* access modifiers changed from: private */
        public String viewDescription;

        public PerformException build() {
            return new PerformException(this);
        }

        public Builder from(PerformException performException) {
            this.actionDescription = performException.getActionDescription();
            this.viewDescription = performException.getViewDescription();
            this.cause = performException.getCause();
            return this;
        }

        public Builder withActionDescription(String str) {
            this.actionDescription = str;
            return this;
        }

        public Builder withCause(Throwable th) {
            this.cause = th;
            return this;
        }

        public Builder withViewDescription(String str) {
            this.viewDescription = str;
            return this;
        }
    }

    private PerformException(Builder builder) {
        super(String.format(Locale.ROOT, MESSAGE_FORMAT, new Object[]{builder.actionDescription, builder.viewDescription}), builder.cause);
        this.actionDescription = (String) Preconditions.checkNotNull(builder.actionDescription);
        this.viewDescription = (String) Preconditions.checkNotNull(builder.viewDescription);
        TestOutputEmitter.dumpThreadStates("ThreadState-PerformException.txt");
    }

    public String getActionDescription() {
        return this.actionDescription;
    }

    public String getViewDescription() {
        return this.viewDescription;
    }
}
