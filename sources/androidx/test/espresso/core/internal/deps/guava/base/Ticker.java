package androidx.test.espresso.core.internal.deps.guava.base;

public abstract class Ticker {
    private static final Ticker SYSTEM_TICKER = new Ticker() {
        public long read() {
            return Platform.systemNanoTime();
        }
    };

    protected Ticker() {
    }

    public static Ticker systemTicker() {
        return SYSTEM_TICKER;
    }

    public abstract long read();
}
