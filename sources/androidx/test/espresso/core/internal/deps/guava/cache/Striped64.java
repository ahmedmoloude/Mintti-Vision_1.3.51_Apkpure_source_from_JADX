package androidx.test.espresso.core.internal.deps.guava.cache;

import java.util.Random;
import sun.misc.Unsafe;

abstract class Striped64 extends Number {
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    static final Random rng = new Random();
    static final ThreadLocal<int[]> threadHashCode = new ThreadLocal<>();
    volatile transient long base;
    volatile transient int busy;
    volatile transient Cell[] cells;

    static final class Cell {
        private static final Unsafe UNSAFE;
        private static final long valueOffset;

        /* renamed from: p0 */
        volatile long f199p0;

        /* renamed from: p1 */
        volatile long f200p1;

        /* renamed from: p2 */
        volatile long f201p2;

        /* renamed from: p3 */
        volatile long f202p3;

        /* renamed from: p4 */
        volatile long f203p4;

        /* renamed from: p5 */
        volatile long f204p5;

        /* renamed from: p6 */
        volatile long f205p6;

        /* renamed from: q0 */
        volatile long f206q0;

        /* renamed from: q1 */
        volatile long f207q1;

        /* renamed from: q2 */
        volatile long f208q2;

        /* renamed from: q3 */
        volatile long f209q3;

        /* renamed from: q4 */
        volatile long f210q4;

        /* renamed from: q5 */
        volatile long f211q5;

        /* renamed from: q6 */
        volatile long f212q6;
        volatile long value;

        static {
            try {
                Unsafe access$000 = Striped64.getUnsafe();
                UNSAFE = access$000;
                valueOffset = access$000.objectFieldOffset(Cell.class.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        Cell(long j) {
            this.value = j;
        }

        /* access modifiers changed from: package-private */
        public final boolean cas(long j, long j2) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, j, j2);
        }
    }

    static {
        try {
            Unsafe unsafe = getUnsafe();
            UNSAFE = unsafe;
            Class<Striped64> cls = Striped64.class;
            baseOffset = unsafe.objectFieldOffset(cls.getDeclaredField("base"));
            busyOffset = unsafe.objectFieldOffset(cls.getDeclaredField("busy"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    Striped64() {
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0010, code lost:
        return (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new androidx.test.espresso.core.internal.deps.guava.cache.Striped64.C07461());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0011, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
        throw new java.lang.RuntimeException("Could not initialize intrinsics", r0.getCause());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0005 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static sun.misc.Unsafe getUnsafe() {
        /*
            sun.misc.Unsafe r0 = sun.misc.Unsafe.getUnsafe()     // Catch:{ SecurityException -> 0x0005 }
            return r0
        L_0x0005:
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$1 r0 = new androidx.test.espresso.core.internal.deps.guava.cache.Striped64$1     // Catch:{ PrivilegedActionException -> 0x0011 }
            r0.<init>()     // Catch:{ PrivilegedActionException -> 0x0011 }
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)     // Catch:{ PrivilegedActionException -> 0x0011 }
            sun.misc.Unsafe r0 = (sun.misc.Unsafe) r0     // Catch:{ PrivilegedActionException -> 0x0011 }
            return r0
        L_0x0011:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.Throwable r0 = r0.getCause()
            java.lang.String r2 = "Could not initialize intrinsics"
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.Striped64.getUnsafe():sun.misc.Unsafe");
    }

    /* access modifiers changed from: package-private */
    public final boolean casBase(long j, long j2) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, j, j2);
    }

    /* access modifiers changed from: package-private */
    public final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: fn */
    public abstract long mo11314fn(long j, long j2);

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x00ee A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0023 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void retryUpdate(long r17, int[] r19, boolean r20) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r0 = 1
            r4 = 0
            if (r19 != 0) goto L_0x001b
            java.lang.ThreadLocal<int[]> r5 = threadHashCode
            int[] r6 = new int[r0]
            r5.set(r6)
            java.util.Random r5 = rng
            int r5 = r5.nextInt()
            if (r5 != 0) goto L_0x0018
            r5 = 1
        L_0x0018:
            r6[r4] = r5
            goto L_0x001f
        L_0x001b:
            r5 = r19[r4]
            r6 = r19
        L_0x001f:
            r7 = r5
            r8 = 0
            r5 = r20
        L_0x0023:
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell[] r9 = r1.cells
            if (r9 == 0) goto L_0x00b3
            int r10 = r9.length
            if (r10 <= 0) goto L_0x00b3
            int r11 = r10 + -1
            r11 = r11 & r7
            r11 = r9[r11]
            if (r11 != 0) goto L_0x0063
            int r9 = r1.busy
            if (r9 != 0) goto L_0x0061
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell r9 = new androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell
            r9.<init>(r2)
            int r10 = r1.busy
            if (r10 != 0) goto L_0x0061
            boolean r10 = r16.casBusy()
            if (r10 == 0) goto L_0x0061
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell[] r10 = r1.cells     // Catch:{ all -> 0x005d }
            if (r10 == 0) goto L_0x0056
            int r11 = r10.length     // Catch:{ all -> 0x005d }
            if (r11 <= 0) goto L_0x0056
            int r11 = r11 + -1
            r11 = r11 & r7
            r12 = r10[r11]     // Catch:{ all -> 0x005d }
            if (r12 != 0) goto L_0x0056
            r10[r11] = r9     // Catch:{ all -> 0x005d }
            r9 = 1
            goto L_0x0057
        L_0x0056:
            r9 = 0
        L_0x0057:
            r1.busy = r4
            if (r9 == 0) goto L_0x0023
            goto L_0x00ee
        L_0x005d:
            r0 = move-exception
            r1.busy = r4
            throw r0
        L_0x0061:
            r8 = 0
            goto L_0x00a6
        L_0x0063:
            if (r5 != 0) goto L_0x0067
            r5 = 1
            goto L_0x00a6
        L_0x0067:
            long r12 = r11.value
            long r14 = r1.mo11314fn(r12, r2)
            boolean r11 = r11.cas(r12, r14)
            if (r11 != 0) goto L_0x00ee
            int r11 = NCPU
            if (r10 >= r11) goto L_0x0061
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell[] r11 = r1.cells
            if (r11 == r9) goto L_0x007c
            goto L_0x0061
        L_0x007c:
            if (r8 != 0) goto L_0x0080
            r8 = 1
            goto L_0x00a6
        L_0x0080:
            int r11 = r1.busy
            if (r11 != 0) goto L_0x00a6
            boolean r11 = r16.casBusy()
            if (r11 == 0) goto L_0x00a6
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell[] r8 = r1.cells     // Catch:{ all -> 0x00a2 }
            if (r8 != r9) goto L_0x009e
            int r8 = r10 + r10
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell[] r8 = new androidx.test.espresso.core.internal.deps.guava.cache.Striped64.Cell[r8]     // Catch:{ all -> 0x00a2 }
            r11 = 0
        L_0x0093:
            if (r11 >= r10) goto L_0x009c
            r12 = r9[r11]     // Catch:{ all -> 0x00a2 }
            r8[r11] = r12     // Catch:{ all -> 0x00a2 }
            int r11 = r11 + 1
            goto L_0x0093
        L_0x009c:
            r1.cells = r8     // Catch:{ all -> 0x00a2 }
        L_0x009e:
            r1.busy = r4
            r8 = 0
            goto L_0x0023
        L_0x00a2:
            r0 = move-exception
            r1.busy = r4
            throw r0
        L_0x00a6:
            int r9 = r7 << 13
            r7 = r7 ^ r9
            int r9 = r7 >>> 17
            r7 = r7 ^ r9
            int r9 = r7 << 5
            r7 = r7 ^ r9
            r6[r4] = r7
            goto L_0x0023
        L_0x00b3:
            int r10 = r1.busy
            if (r10 != 0) goto L_0x00e0
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell[] r10 = r1.cells
            if (r10 != r9) goto L_0x00e0
            boolean r10 = r16.casBusy()
            if (r10 == 0) goto L_0x00e0
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell[] r10 = r1.cells     // Catch:{ all -> 0x00dc }
            if (r10 != r9) goto L_0x00d5
            r9 = 2
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell[] r9 = new androidx.test.espresso.core.internal.deps.guava.cache.Striped64.Cell[r9]     // Catch:{ all -> 0x00dc }
            r10 = r7 & 1
            androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell r11 = new androidx.test.espresso.core.internal.deps.guava.cache.Striped64$Cell     // Catch:{ all -> 0x00dc }
            r11.<init>(r2)     // Catch:{ all -> 0x00dc }
            r9[r10] = r11     // Catch:{ all -> 0x00dc }
            r1.cells = r9     // Catch:{ all -> 0x00dc }
            r9 = 1
            goto L_0x00d6
        L_0x00d5:
            r9 = 0
        L_0x00d6:
            r1.busy = r4
            if (r9 != 0) goto L_0x00ee
            goto L_0x0023
        L_0x00dc:
            r0 = move-exception
            r1.busy = r4
            throw r0
        L_0x00e0:
            long r9 = r1.base
            long r11 = r1.mo11314fn(r9, r2)
            boolean r9 = r1.casBase(r9, r11)
            if (r9 != 0) goto L_0x00ee
            goto L_0x0023
        L_0x00ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.Striped64.retryUpdate(long, int[], boolean):void");
    }
}
