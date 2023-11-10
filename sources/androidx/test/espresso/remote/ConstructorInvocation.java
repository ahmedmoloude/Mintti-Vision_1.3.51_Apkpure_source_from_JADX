package androidx.test.espresso.remote;

import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.cache.Cache;
import androidx.test.espresso.core.internal.deps.guava.cache.CacheBuilder;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public final class ConstructorInvocation {
    private static final String TAG = "ConstructorInvocation";
    private static final Cache<ConstructorKey, Constructor<?>> constructorCache = CacheBuilder.newBuilder().maximumSize(256).build();
    private final Class<? extends Annotation> annotationClass;
    private final Class<?> clazz;
    private final Class<?>[] parameterTypes;

    public ConstructorInvocation(Class<?> cls, Class<? extends Annotation> cls2, Class<?>... clsArr) {
        this.clazz = (Class) Preconditions.checkNotNull(cls, "clazz cannot be null!");
        this.annotationClass = cls2;
        this.parameterTypes = clsArr;
    }

    static void invalidateCache() {
        constructorCache.invalidateAll();
    }

    public Object invokeConstructor(Object... objArr) {
        return invokeConstructorExplosively(objArr);
    }

    private static final class ConstructorKey {
        private final Class<?>[] parameterTypes;
        private final Class<?> type;

        public ConstructorKey(Class<?> cls, Class<?>[] clsArr) {
            this.type = cls;
            this.parameterTypes = clsArr;
        }

        public int hashCode() {
            return (this.type.hashCode() * 31) + Arrays.hashCode(this.parameterTypes);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ConstructorKey constructorKey = (ConstructorKey) obj;
            if (!this.type.equals(constructorKey.type)) {
                return false;
            }
            return Arrays.equals(this.parameterTypes, constructorKey.parameterTypes);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a9, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00aa, code lost:
        r4 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ac, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ad, code lost:
        r4 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00af, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ec, code lost:
        throw new androidx.test.espresso.remote.RemoteProtocolException(java.lang.String.format(java.util.Locale.ROOT, "No constructor found for clazz: %s. Available constructors: %s", new java.lang.Object[]{r13.clazz.getName(), java.util.Arrays.asList(r13.clazz.getConstructors())}), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ed, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0103, code lost:
        throw new androidx.test.espresso.remote.RemoteProtocolException(java.lang.String.format(java.util.Locale.ROOT, "Cannot create instance of %s", new java.lang.Object[]{r13.clazz.getName()}), r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0104, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x011a, code lost:
        throw new androidx.test.espresso.remote.RemoteProtocolException(java.lang.String.format(java.util.Locale.ROOT, "Cannot create instance of %s", new java.lang.Object[]{r13.clazz.getName()}), r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x013c, code lost:
        androidx.test.internal.util.LogUtil.logDebug(TAG, "%s(%s)", r13.clazz.getSimpleName(), java.util.Arrays.toString(r14));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x014f, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c8 A[Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9, all -> 0x00af }, ExcHandler: NoSuchMethodException (r0v4 'e' java.lang.NoSuchMethodException A[CUSTOM_DECLARE, Catch:{  }]), Splitter:B:1:0x0013] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ed A[Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9, all -> 0x00af }, ExcHandler: InstantiationException (r3v3 'e' java.lang.InstantiationException A[CUSTOM_DECLARE, Catch:{  }]), Splitter:B:1:0x0013] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0104 A[Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9, all -> 0x00af }, ExcHandler: IllegalAccessException (r3v2 'e' java.lang.IllegalAccessException A[CUSTOM_DECLARE, Catch:{  }]), Splitter:B:1:0x0013] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Object invokeConstructorExplosively(java.lang.Object... r14) {
        /*
            r13 = this;
            java.lang.String r0 = "Cannot create instance of %s"
            java.lang.String r1 = "%s(%s)"
            java.lang.String r2 = "ConstructorInvocation"
            androidx.test.espresso.remote.ConstructorInvocation$ConstructorKey r3 = new androidx.test.espresso.remote.ConstructorInvocation$ConstructorKey
            java.lang.Class<?> r4 = r13.clazz
            java.lang.Class<?>[] r5 = r13.parameterTypes
            r3.<init>(r4, r5)
            r4 = 0
            r5 = 2
            r6 = 0
            r7 = 1
            androidx.test.espresso.core.internal.deps.guava.cache.Cache<androidx.test.espresso.remote.ConstructorInvocation$ConstructorKey, java.lang.reflect.Constructor<?>> r8 = constructorCache     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            java.lang.Object r8 = r8.getIfPresent(r3)     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            java.lang.reflect.Constructor r8 = (java.lang.reflect.Constructor) r8     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            if (r8 != 0) goto L_0x0078
            java.lang.String r4 = "Cache miss for constructor: %s(%s). Loading into cache."
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.Class<?> r10 = r13.clazz     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.String r10 = r10.getSimpleName()     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            r9[r6] = r10     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.String r10 = java.util.Arrays.toString(r14)     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            r9[r7] = r10     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            androidx.test.internal.util.LogUtil.logDebug((java.lang.String) r2, (java.lang.String) r4, (java.lang.Object[]) r9)     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.Class<? extends java.lang.annotation.Annotation> r4 = r13.annotationClass     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            if (r4 == 0) goto L_0x004f
            java.lang.Class<?> r4 = r13.clazz     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.reflect.Constructor[] r4 = r4.getDeclaredConstructors()     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            int r9 = r4.length     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            r10 = 0
        L_0x003e:
            if (r10 >= r9) goto L_0x004f
            r11 = r4[r10]     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.Class<? extends java.lang.annotation.Annotation> r12 = r13.annotationClass     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            boolean r12 = r11.isAnnotationPresent(r12)     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            if (r12 == 0) goto L_0x004c
            r4 = r11
            goto L_0x0050
        L_0x004c:
            int r10 = r10 + 1
            goto L_0x003e
        L_0x004f:
            r4 = r8
        L_0x0050:
            if (r4 != 0) goto L_0x0060
            java.lang.Class<?> r8 = r13.clazz     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            java.lang.Class<?>[] r9 = r13.parameterTypes     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            java.lang.reflect.Constructor r4 = r8.getConstructor(r9)     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            goto L_0x0060
        L_0x005b:
            r0 = move-exception
            goto L_0x00b2
        L_0x005d:
            r0 = move-exception
            goto L_0x011b
        L_0x0060:
            if (r4 == 0) goto L_0x0064
            r8 = 1
            goto L_0x0065
        L_0x0064:
            r8 = 0
        L_0x0065:
            java.lang.String r9 = "No constructor found for annotation: %s, or parameter types: %s"
            java.lang.Class<? extends java.lang.annotation.Annotation> r10 = r13.annotationClass     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            java.lang.Class<?>[] r11 = r13.parameterTypes     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            java.util.List r11 = java.util.Arrays.asList(r11)     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkState(r8, r9, r10, r11)     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            androidx.test.espresso.core.internal.deps.guava.cache.Cache<androidx.test.espresso.remote.ConstructorInvocation$ConstructorKey, java.lang.reflect.Constructor<?>> r8 = constructorCache     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            r8.put(r3, r4)     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            goto L_0x008e
        L_0x0078:
            java.lang.String r3 = "Cache hit for constructor: %s(%s)."
            java.lang.Object[] r4 = new java.lang.Object[r5]     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.Class<?> r9 = r13.clazz     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.String r9 = r9.getSimpleName()     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            r4[r6] = r9     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            java.lang.String r9 = java.util.Arrays.toString(r14)     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            r4[r7] = r9     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            androidx.test.internal.util.LogUtil.logDebug((java.lang.String) r2, (java.lang.String) r3, (java.lang.Object[]) r4)     // Catch:{ InvocationTargetException -> 0x00ac, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x00a9 }
            r4 = r8
        L_0x008e:
            r4.setAccessible(r7)     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            java.lang.Object r0 = r4.newInstance(r14)     // Catch:{ InvocationTargetException -> 0x005d, IllegalAccessException -> 0x0104, InstantiationException -> 0x00ed, NoSuchMethodException -> 0x00c8, SecurityException -> 0x005b }
            java.lang.Object[] r3 = new java.lang.Object[r5]
            java.lang.Class<?> r4 = r13.clazz
            java.lang.String r4 = r4.getSimpleName()
            r3[r6] = r4
            java.lang.String r14 = java.util.Arrays.toString(r14)
            r3[r7] = r14
            androidx.test.internal.util.LogUtil.logDebug((java.lang.String) r2, (java.lang.String) r1, (java.lang.Object[]) r3)
            return r0
        L_0x00a9:
            r0 = move-exception
            r4 = r8
            goto L_0x00b2
        L_0x00ac:
            r0 = move-exception
            r4 = r8
            goto L_0x011b
        L_0x00af:
            r0 = move-exception
            goto L_0x013c
        L_0x00b2:
            androidx.test.espresso.remote.RemoteProtocolException r3 = new androidx.test.espresso.remote.RemoteProtocolException     // Catch:{ all -> 0x00af }
            java.util.Locale r8 = java.util.Locale.ROOT     // Catch:{ all -> 0x00af }
            java.lang.String r9 = "Constructor not accessible: %s"
            java.lang.Object[] r10 = new java.lang.Object[r7]     // Catch:{ all -> 0x00af }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x00af }
            r10[r6] = r4     // Catch:{ all -> 0x00af }
            java.lang.String r4 = java.lang.String.format(r8, r9, r10)     // Catch:{ all -> 0x00af }
            r3.<init>(r4, r0)     // Catch:{ all -> 0x00af }
            throw r3     // Catch:{ all -> 0x00af }
        L_0x00c8:
            r0 = move-exception
            androidx.test.espresso.remote.RemoteProtocolException r3 = new androidx.test.espresso.remote.RemoteProtocolException     // Catch:{ all -> 0x00af }
            java.util.Locale r4 = java.util.Locale.ROOT     // Catch:{ all -> 0x00af }
            java.lang.String r8 = "No constructor found for clazz: %s. Available constructors: %s"
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ all -> 0x00af }
            java.lang.Class<?> r10 = r13.clazz     // Catch:{ all -> 0x00af }
            java.lang.String r10 = r10.getName()     // Catch:{ all -> 0x00af }
            r9[r6] = r10     // Catch:{ all -> 0x00af }
            java.lang.Class<?> r10 = r13.clazz     // Catch:{ all -> 0x00af }
            java.lang.reflect.Constructor[] r10 = r10.getConstructors()     // Catch:{ all -> 0x00af }
            java.util.List r10 = java.util.Arrays.asList(r10)     // Catch:{ all -> 0x00af }
            r9[r7] = r10     // Catch:{ all -> 0x00af }
            java.lang.String r4 = java.lang.String.format(r4, r8, r9)     // Catch:{ all -> 0x00af }
            r3.<init>(r4, r0)     // Catch:{ all -> 0x00af }
            throw r3     // Catch:{ all -> 0x00af }
        L_0x00ed:
            r3 = move-exception
            androidx.test.espresso.remote.RemoteProtocolException r4 = new androidx.test.espresso.remote.RemoteProtocolException     // Catch:{ all -> 0x00af }
            java.util.Locale r8 = java.util.Locale.ROOT     // Catch:{ all -> 0x00af }
            java.lang.Object[] r9 = new java.lang.Object[r7]     // Catch:{ all -> 0x00af }
            java.lang.Class<?> r10 = r13.clazz     // Catch:{ all -> 0x00af }
            java.lang.String r10 = r10.getName()     // Catch:{ all -> 0x00af }
            r9[r6] = r10     // Catch:{ all -> 0x00af }
            java.lang.String r0 = java.lang.String.format(r8, r0, r9)     // Catch:{ all -> 0x00af }
            r4.<init>(r0, r3)     // Catch:{ all -> 0x00af }
            throw r4     // Catch:{ all -> 0x00af }
        L_0x0104:
            r3 = move-exception
            androidx.test.espresso.remote.RemoteProtocolException r4 = new androidx.test.espresso.remote.RemoteProtocolException     // Catch:{ all -> 0x00af }
            java.util.Locale r8 = java.util.Locale.ROOT     // Catch:{ all -> 0x00af }
            java.lang.Object[] r9 = new java.lang.Object[r7]     // Catch:{ all -> 0x00af }
            java.lang.Class<?> r10 = r13.clazz     // Catch:{ all -> 0x00af }
            java.lang.String r10 = r10.getName()     // Catch:{ all -> 0x00af }
            r9[r6] = r10     // Catch:{ all -> 0x00af }
            java.lang.String r0 = java.lang.String.format(r8, r0, r9)     // Catch:{ all -> 0x00af }
            r4.<init>(r0, r3)     // Catch:{ all -> 0x00af }
            throw r4     // Catch:{ all -> 0x00af }
        L_0x011b:
            androidx.test.espresso.remote.RemoteProtocolException r3 = new androidx.test.espresso.remote.RemoteProtocolException     // Catch:{ all -> 0x00af }
            java.util.Locale r8 = java.util.Locale.ROOT     // Catch:{ all -> 0x00af }
            java.lang.String r9 = "Cannot invoke constructor %s with constructorParams [%s] on clazz %s"
            r10 = 3
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00af }
            r10[r6] = r4     // Catch:{ all -> 0x00af }
            java.lang.String r4 = java.util.Arrays.toString(r14)     // Catch:{ all -> 0x00af }
            r10[r7] = r4     // Catch:{ all -> 0x00af }
            java.lang.Class<?> r4 = r13.clazz     // Catch:{ all -> 0x00af }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x00af }
            r10[r5] = r4     // Catch:{ all -> 0x00af }
            java.lang.String r4 = java.lang.String.format(r8, r9, r10)     // Catch:{ all -> 0x00af }
            r3.<init>(r4, r0)     // Catch:{ all -> 0x00af }
            throw r3     // Catch:{ all -> 0x00af }
        L_0x013c:
            java.lang.Object[] r3 = new java.lang.Object[r5]
            java.lang.Class<?> r4 = r13.clazz
            java.lang.String r4 = r4.getSimpleName()
            r3[r6] = r4
            java.lang.String r14 = java.util.Arrays.toString(r14)
            r3[r7] = r14
            androidx.test.internal.util.LogUtil.logDebug((java.lang.String) r2, (java.lang.String) r1, (java.lang.Object[]) r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.remote.ConstructorInvocation.invokeConstructorExplosively(java.lang.Object[]):java.lang.Object");
    }
}
