package p035me.yokeyword.fragmentation;

import p035me.yokeyword.fragmentation.helper.ExceptionHandler;

/* renamed from: me.yokeyword.fragmentation.Fragmentation */
public class Fragmentation {
    public static final int BUBBLE = 2;
    static volatile Fragmentation INSTANCE = null;
    public static final int NONE = 0;
    public static final int SHAKE = 1;
    private boolean debug;
    private ExceptionHandler handler;
    private int mode = 2;

    public static Fragmentation getDefault() {
        if (INSTANCE == null) {
            synchronized (Fragmentation.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Fragmentation(new FragmentationBuilder());
                }
            }
        }
        return INSTANCE;
    }

    Fragmentation(FragmentationBuilder fragmentationBuilder) {
        boolean access$000 = fragmentationBuilder.debug;
        this.debug = access$000;
        if (access$000) {
            this.mode = fragmentationBuilder.mode;
        } else {
            this.mode = 0;
        }
        this.handler = fragmentationBuilder.handler;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean z) {
        this.debug = z;
    }

    public ExceptionHandler getHandler() {
        return this.handler;
    }

    public void setHandler(ExceptionHandler exceptionHandler) {
        this.handler = exceptionHandler;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int i) {
        this.mode = i;
    }

    public static FragmentationBuilder builder() {
        return new FragmentationBuilder();
    }

    /* renamed from: me.yokeyword.fragmentation.Fragmentation$FragmentationBuilder */
    public static class FragmentationBuilder {
        /* access modifiers changed from: private */
        public boolean debug;
        /* access modifiers changed from: private */
        public ExceptionHandler handler;
        /* access modifiers changed from: private */
        public int mode;

        public FragmentationBuilder debug(boolean z) {
            this.debug = z;
            return this;
        }

        public FragmentationBuilder stackViewMode(int i) {
            this.mode = i;
            return this;
        }

        public FragmentationBuilder handleException(ExceptionHandler exceptionHandler) {
            this.handler = exceptionHandler;
            return this;
        }

        public Fragmentation install() {
            Fragmentation.INSTANCE = new Fragmentation(this);
            return Fragmentation.INSTANCE;
        }
    }
}
