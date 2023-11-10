package androidx.test.runner.internal.deps.desugar;

import java.io.Closeable;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public final class ThrowableExtension {
    private static final String ANDROID_OS_BUILD_VERSION = "android.os.Build$VERSION";
    static final int API_LEVEL;
    static final AbstractDesugaringStrategy STRATEGY;
    public static final String SYSTEM_PROPERTY_TWR_DISABLE_MIMIC = "androidx.test.runner.internal.deps.desugar.twr_disable_mimic";

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0066  */
    static {
        /*
            java.lang.Integer r0 = readApiLevelFromBuildVersion()     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0014
            int r1 = r0.intValue()     // Catch:{ all -> 0x0026 }
            r2 = 19
            if (r1 < r2) goto L_0x0014
            androidx.test.runner.internal.deps.desugar.ThrowableExtension$ReuseDesugaringStrategy r1 = new androidx.test.runner.internal.deps.desugar.ThrowableExtension$ReuseDesugaringStrategy     // Catch:{ all -> 0x0026 }
            r1.<init>()     // Catch:{ all -> 0x0026 }
            goto L_0x0060
        L_0x0014:
            boolean r1 = useMimicStrategy()     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0020
            androidx.test.runner.internal.deps.desugar.ThrowableExtension$MimicDesugaringStrategy r1 = new androidx.test.runner.internal.deps.desugar.ThrowableExtension$MimicDesugaringStrategy     // Catch:{ all -> 0x0026 }
            r1.<init>()     // Catch:{ all -> 0x0026 }
            goto L_0x0060
        L_0x0020:
            androidx.test.runner.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy r1 = new androidx.test.runner.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy     // Catch:{ all -> 0x0026 }
            r1.<init>()     // Catch:{ all -> 0x0026 }
            goto L_0x0060
        L_0x0026:
            r1 = move-exception
            goto L_0x002a
        L_0x0028:
            r1 = move-exception
            r0 = 0
        L_0x002a:
            java.io.PrintStream r2 = java.lang.System.err
            java.lang.Class<androidx.test.runner.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy> r3 = androidx.test.runner.internal.deps.desugar.ThrowableExtension.NullDesugaringStrategy.class
            java.lang.String r3 = r3.getName()
            java.lang.String r4 = java.lang.String.valueOf(r3)
            int r4 = r4.length()
            int r4 = r4 + 133
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "An error has occurred when initializing the try-with-resources desuguring strategy. The default strategy "
            r5.append(r4)
            r5.append(r3)
            java.lang.String r3 = "will be used. The error is: "
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            r2.println(r3)
            java.io.PrintStream r2 = java.lang.System.err
            r1.printStackTrace(r2)
            androidx.test.runner.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy r1 = new androidx.test.runner.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy
            r1.<init>()
        L_0x0060:
            STRATEGY = r1
            if (r0 != 0) goto L_0x0066
            r0 = 1
            goto L_0x006a
        L_0x0066:
            int r0 = r0.intValue()
        L_0x006a:
            API_LEVEL = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.runner.internal.deps.desugar.ThrowableExtension.<clinit>():void");
    }

    public static AbstractDesugaringStrategy getStrategy() {
        return STRATEGY;
    }

    public static void addSuppressed(Throwable th, Throwable th2) {
        STRATEGY.addSuppressed(th, th2);
    }

    public static Throwable[] getSuppressed(Throwable th) {
        return STRATEGY.getSuppressed(th);
    }

    public static void printStackTrace(Throwable th) {
        STRATEGY.printStackTrace(th);
    }

    public static void printStackTrace(Throwable th, PrintWriter printWriter) {
        STRATEGY.printStackTrace(th, printWriter);
    }

    public static void printStackTrace(Throwable th, PrintStream printStream) {
        STRATEGY.printStackTrace(th, printStream);
    }

    public static void closeResource(Throwable th, Object obj) throws Throwable {
        if (obj != null) {
            try {
                if (API_LEVEL >= 19) {
                    ((AutoCloseable) obj).close();
                    return;
                } else if (obj instanceof Closeable) {
                    ((Closeable) obj).close();
                    return;
                } else {
                    obj.getClass().getMethod("close", new Class[0]).invoke(obj, new Object[0]);
                    return;
                }
            } catch (NoSuchMethodException e) {
                e = e;
            } catch (SecurityException e2) {
                e = e2;
            } catch (IllegalAccessException e3) {
                e = e3;
                String valueOf = String.valueOf(obj.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("Fail to call close() on ");
                sb.append(valueOf);
                throw new AssertionError(sb.toString(), e);
            } catch (IllegalArgumentException e4) {
                e = e4;
                String valueOf2 = String.valueOf(obj.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 24);
                sb2.append("Fail to call close() on ");
                sb2.append(valueOf2);
                throw new AssertionError(sb2.toString(), e);
            } catch (ExceptionInInitializerError e5) {
                e = e5;
                String valueOf22 = String.valueOf(obj.getClass());
                StringBuilder sb22 = new StringBuilder(String.valueOf(valueOf22).length() + 24);
                sb22.append("Fail to call close() on ");
                sb22.append(valueOf22);
                throw new AssertionError(sb22.toString(), e);
            } catch (InvocationTargetException e6) {
                throw e6.getCause();
            } catch (Throwable th2) {
                if (th != null) {
                    addSuppressed(th, th2);
                    throw th;
                }
                throw th2;
            }
        } else {
            return;
        }
        String valueOf3 = String.valueOf(obj.getClass());
        StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 32);
        sb3.append(valueOf3);
        sb3.append(" does not have a close() method.");
        throw new AssertionError(sb3.toString(), e);
    }

    private static boolean useMimicStrategy() {
        return !Boolean.getBoolean(SYSTEM_PROPERTY_TWR_DISABLE_MIMIC);
    }

    private static Integer readApiLevelFromBuildVersion() {
        try {
            return (Integer) Class.forName(ANDROID_OS_BUILD_VERSION).getField("SDK_INT").get((Object) null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    static abstract class AbstractDesugaringStrategy {
        protected static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];

        public abstract void addSuppressed(Throwable th, Throwable th2);

        public abstract Throwable[] getSuppressed(Throwable th);

        public abstract void printStackTrace(Throwable th);

        public abstract void printStackTrace(Throwable th, PrintStream printStream);

        public abstract void printStackTrace(Throwable th, PrintWriter printWriter);

        AbstractDesugaringStrategy() {
        }
    }

    static final class ReuseDesugaringStrategy extends AbstractDesugaringStrategy {
        ReuseDesugaringStrategy() {
        }

        public void addSuppressed(Throwable th, Throwable th2) {
            th.addSuppressed(th2);
        }

        public Throwable[] getSuppressed(Throwable th) {
            return th.getSuppressed();
        }

        public void printStackTrace(Throwable th) {
            th.printStackTrace();
        }

        public void printStackTrace(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
        }

        public void printStackTrace(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
        }
    }

    static final class MimicDesugaringStrategy extends AbstractDesugaringStrategy {
        static final String SUPPRESSED_PREFIX = "Suppressed: ";
        private final ConcurrentWeakIdentityHashMap map = new ConcurrentWeakIdentityHashMap();

        MimicDesugaringStrategy() {
        }

        public void addSuppressed(Throwable th, Throwable th2) {
            if (th2 != th) {
                Objects.requireNonNull(th2, "The suppressed exception cannot be null.");
                this.map.get(th, true).add(th2);
                return;
            }
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        }

        public Throwable[] getSuppressed(Throwable th) {
            List<Throwable> list = this.map.get(th, false);
            if (list == null || list.isEmpty()) {
                return EMPTY_THROWABLE_ARRAY;
            }
            return (Throwable[]) list.toArray(EMPTY_THROWABLE_ARRAY);
        }

        public void printStackTrace(Throwable th) {
            th.printStackTrace();
            List<Throwable> list = this.map.get(th, false);
            if (list != null) {
                synchronized (list) {
                    for (Throwable printStackTrace : list) {
                        System.err.print(SUPPRESSED_PREFIX);
                        printStackTrace.printStackTrace();
                    }
                }
            }
        }

        public void printStackTrace(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
            List<Throwable> list = this.map.get(th, false);
            if (list != null) {
                synchronized (list) {
                    for (Throwable printStackTrace : list) {
                        printStream.print(SUPPRESSED_PREFIX);
                        printStackTrace.printStackTrace(printStream);
                    }
                }
            }
        }

        public void printStackTrace(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
            List<Throwable> list = this.map.get(th, false);
            if (list != null) {
                synchronized (list) {
                    for (Throwable printStackTrace : list) {
                        printWriter.print(SUPPRESSED_PREFIX);
                        printStackTrace.printStackTrace(printWriter);
                    }
                }
            }
        }
    }

    static final class ConcurrentWeakIdentityHashMap {
        private final ConcurrentHashMap<WeakKey, List<Throwable>> map = new ConcurrentHashMap<>(16, 0.75f, 10);
        private final ReferenceQueue<Throwable> referenceQueue = new ReferenceQueue<>();

        ConcurrentWeakIdentityHashMap() {
        }

        public List<Throwable> get(Throwable th, boolean z) {
            deleteEmptyKeys();
            List<Throwable> list = this.map.get(new WeakKey(th, (ReferenceQueue<Throwable>) null));
            if (!z || list != null) {
                return list;
            }
            Vector vector = new Vector(2);
            List<Throwable> putIfAbsent = this.map.putIfAbsent(new WeakKey(th, this.referenceQueue), vector);
            return putIfAbsent == null ? vector : putIfAbsent;
        }

        /* access modifiers changed from: package-private */
        public int size() {
            return this.map.size();
        }

        /* access modifiers changed from: package-private */
        public void deleteEmptyKeys() {
            while (true) {
                Reference<? extends Throwable> poll = this.referenceQueue.poll();
                if (poll != null) {
                    this.map.remove(poll);
                } else {
                    return;
                }
            }
        }

        private static final class WeakKey extends WeakReference<Throwable> {
            private final int hash;

            public WeakKey(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
                super(th, referenceQueue);
                Objects.requireNonNull(th, "The referent cannot be null");
                this.hash = System.identityHashCode(th);
            }

            public int hashCode() {
                return this.hash;
            }

            public boolean equals(Object obj) {
                if (obj == null || obj.getClass() != getClass()) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                WeakKey weakKey = (WeakKey) obj;
                if (this.hash == weakKey.hash && get() == weakKey.get()) {
                    return true;
                }
                return false;
            }
        }
    }

    static final class NullDesugaringStrategy extends AbstractDesugaringStrategy {
        public void addSuppressed(Throwable th, Throwable th2) {
        }

        NullDesugaringStrategy() {
        }

        public Throwable[] getSuppressed(Throwable th) {
            return EMPTY_THROWABLE_ARRAY;
        }

        public void printStackTrace(Throwable th) {
            th.printStackTrace();
        }

        public void printStackTrace(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
        }

        public void printStackTrace(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
        }
    }
}
