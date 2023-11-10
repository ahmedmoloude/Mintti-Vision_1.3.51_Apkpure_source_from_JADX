package androidx.test.espresso.core.internal.deps.desugar;

import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public final class ThrowableExtension {
    static final int API_LEVEL;
    static final AbstractDesugaringStrategy STRATEGY;

    static abstract class AbstractDesugaringStrategy {
        protected static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];

        AbstractDesugaringStrategy() {
        }

        public abstract void printStackTrace(Throwable th, PrintWriter printWriter);
    }

    static final class ConcurrentWeakIdentityHashMap {
        private final ConcurrentHashMap<WeakKey, List<Throwable>> map = new ConcurrentHashMap<>(16, 0.75f, 10);
        private final ReferenceQueue<Throwable> referenceQueue = new ReferenceQueue<>();

        private static final class WeakKey extends WeakReference<Throwable> {
            private final int hash;

            public WeakKey(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
                super(th, referenceQueue);
                Objects.requireNonNull(th, "The referent cannot be null");
                this.hash = System.identityHashCode(th);
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

            public int hashCode() {
                return this.hash;
            }
        }

        ConcurrentWeakIdentityHashMap() {
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
    }

    static final class MimicDesugaringStrategy extends AbstractDesugaringStrategy {
        private final ConcurrentWeakIdentityHashMap map = new ConcurrentWeakIdentityHashMap();

        MimicDesugaringStrategy() {
        }

        public void printStackTrace(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
            List<Throwable> list = this.map.get(th, false);
            if (list != null) {
                synchronized (list) {
                    for (Throwable printStackTrace : list) {
                        printWriter.print("Suppressed: ");
                        printStackTrace.printStackTrace(printWriter);
                    }
                }
            }
        }
    }

    static final class NullDesugaringStrategy extends AbstractDesugaringStrategy {
        NullDesugaringStrategy() {
        }

        public void printStackTrace(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
        }
    }

    static final class ReuseDesugaringStrategy extends AbstractDesugaringStrategy {
        ReuseDesugaringStrategy() {
        }

        public void printStackTrace(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
        }
    }

    public static void printStackTrace(Throwable th, PrintWriter printWriter) {
        STRATEGY.printStackTrace(th, printWriter);
    }

    private static Integer readApiLevelFromBuildVersion() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get((Object) null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    private static boolean useMimicStrategy() {
        return !Boolean.getBoolean("androidx.test.espresso.core.internal.deps.desugar.twr_disable_mimic");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0066  */
    static {
        /*
            java.lang.Integer r0 = readApiLevelFromBuildVersion()     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0014
            int r1 = r0.intValue()     // Catch:{ all -> 0x0026 }
            r2 = 19
            if (r1 < r2) goto L_0x0014
            androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$ReuseDesugaringStrategy r1 = new androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$ReuseDesugaringStrategy     // Catch:{ all -> 0x0026 }
            r1.<init>()     // Catch:{ all -> 0x0026 }
            goto L_0x0060
        L_0x0014:
            boolean r1 = useMimicStrategy()     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0020
            androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$MimicDesugaringStrategy r1 = new androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$MimicDesugaringStrategy     // Catch:{ all -> 0x0026 }
            r1.<init>()     // Catch:{ all -> 0x0026 }
            goto L_0x0060
        L_0x0020:
            androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy r1 = new androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy     // Catch:{ all -> 0x0026 }
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
            java.lang.Class<androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy> r3 = androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension.NullDesugaringStrategy.class
            java.lang.String r3 = r3.getName()
            java.lang.String r4 = java.lang.String.valueOf(r3)
            int r4 = r4.length()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            int r4 = r4 + 133
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
            androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy r1 = new androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension$NullDesugaringStrategy
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
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.desugar.ThrowableExtension.<clinit>():void");
    }
}
