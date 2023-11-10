package p028io.jsonwebtoken.impl.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.Collections;

/* renamed from: io.jsonwebtoken.impl.lang.Services */
public final class Services {
    private static final List<ClassLoaderAccessor> CLASS_LOADER_ACCESSORS = Collections.arrayToList(new ClassLoaderAccessor[]{new ClassLoaderAccessor() {
        public ClassLoader getClassLoader() {
            return Thread.currentThread().getContextClassLoader();
        }
    }, new ClassLoaderAccessor() {
        public ClassLoader getClassLoader() {
            return Services.class.getClassLoader();
        }
    }, new ClassLoaderAccessor() {
        public ClassLoader getClassLoader() {
            return ClassLoader.getSystemClassLoader();
        }
    }});

    /* renamed from: io.jsonwebtoken.impl.lang.Services$ClassLoaderAccessor */
    private interface ClassLoaderAccessor {
        ClassLoader getClassLoader();
    }

    private Services() {
    }

    public static <T> List<T> loadAll(Class<T> cls) {
        Assert.notNull(cls, "Parameter 'spi' must not be null.");
        for (ClassLoaderAccessor classLoader : CLASS_LOADER_ACCESSORS) {
            List<T> loadAll = loadAll(cls, classLoader.getClassLoader());
            if (!loadAll.isEmpty()) {
                return java.util.Collections.unmodifiableList(loadAll);
            }
        }
        throw new UnavailableImplementationException(cls);
    }

    private static <T> List<T> loadAll(Class<T> cls, ClassLoader classLoader) {
        ServiceLoader<S> load = ServiceLoader.load(cls, classLoader);
        ArrayList arrayList = new ArrayList();
        Iterator<S> it = load.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static <T> T loadFirst(Class<T> cls) {
        Assert.notNull(cls, "Parameter 'spi' must not be null.");
        for (ClassLoaderAccessor classLoader : CLASS_LOADER_ACCESSORS) {
            T loadFirst = loadFirst(cls, classLoader.getClassLoader());
            if (loadFirst != null) {
                return loadFirst;
            }
        }
        throw new UnavailableImplementationException(cls);
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.Class, java.lang.Class<T>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T loadFirst(java.lang.Class<T> r0, java.lang.ClassLoader r1) {
        /*
            java.util.ServiceLoader r0 = java.util.ServiceLoader.load(r0, r1)
            java.util.Iterator r1 = r0.iterator()
            boolean r1 = r1.hasNext()
            if (r1 == 0) goto L_0x0017
            java.util.Iterator r0 = r0.iterator()
            java.lang.Object r0 = r0.next()
            return r0
        L_0x0017:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p028io.jsonwebtoken.impl.lang.Services.loadFirst(java.lang.Class, java.lang.ClassLoader):java.lang.Object");
    }
}
