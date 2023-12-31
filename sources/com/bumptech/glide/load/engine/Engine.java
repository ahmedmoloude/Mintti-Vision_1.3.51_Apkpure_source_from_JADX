package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.core.util.Pools;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DecodeJob;
import com.bumptech.glide.load.engine.EngineResource;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.Executors;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import java.util.Map;

public class Engine implements EngineJobListener, MemoryCache.ResourceRemovedListener, EngineResource.ResourceListener {
    private static final int JOB_POOL_SIZE = 150;
    private static final String TAG = "Engine";
    private static final boolean VERBOSE_IS_LOGGABLE = Log.isLoggable(TAG, 2);
    private final ActiveResources activeResources;
    private final MemoryCache cache;
    private final DecodeJobFactory decodeJobFactory;
    private final LazyDiskCacheProvider diskCacheProvider;
    private final EngineJobFactory engineJobFactory;
    private final Jobs jobs;
    private final EngineKeyFactory keyFactory;
    private final ResourceRecycler resourceRecycler;

    public Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, boolean z) {
        this(memoryCache, factory, glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, (Jobs) null, (EngineKeyFactory) null, (ActiveResources) null, (EngineJobFactory) null, (DecodeJobFactory) null, (ResourceRecycler) null, z);
    }

    Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, Jobs jobs2, EngineKeyFactory engineKeyFactory, ActiveResources activeResources2, EngineJobFactory engineJobFactory2, DecodeJobFactory decodeJobFactory2, ResourceRecycler resourceRecycler2, boolean z) {
        this.cache = memoryCache;
        DiskCache.Factory factory2 = factory;
        LazyDiskCacheProvider lazyDiskCacheProvider = new LazyDiskCacheProvider(factory);
        this.diskCacheProvider = lazyDiskCacheProvider;
        ActiveResources activeResources3 = activeResources2 == null ? new ActiveResources(z) : activeResources2;
        this.activeResources = activeResources3;
        activeResources3.setListener(this);
        this.keyFactory = engineKeyFactory == null ? new EngineKeyFactory() : engineKeyFactory;
        this.jobs = jobs2 == null ? new Jobs() : jobs2;
        this.engineJobFactory = engineJobFactory2 == null ? new EngineJobFactory(glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, this) : engineJobFactory2;
        this.decodeJobFactory = decodeJobFactory2 == null ? new DecodeJobFactory(lazyDiskCacheProvider) : decodeJobFactory2;
        this.resourceRecycler = resourceRecycler2 == null ? new ResourceRecycler() : resourceRecycler2;
        memoryCache.setResourceRemovedListener(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003f, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0053, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <R> com.bumptech.glide.load.engine.Engine.LoadStatus load(com.bumptech.glide.GlideContext r32, java.lang.Object r33, com.bumptech.glide.load.Key r34, int r35, int r36, java.lang.Class<?> r37, java.lang.Class<R> r38, com.bumptech.glide.Priority r39, com.bumptech.glide.load.engine.DiskCacheStrategy r40, java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.Transformation<?>> r41, boolean r42, boolean r43, com.bumptech.glide.load.Options r44, boolean r45, boolean r46, boolean r47, boolean r48, com.bumptech.glide.request.ResourceCallback r49, java.util.concurrent.Executor r50) {
        /*
            r31 = this;
            r1 = r31
            r0 = r45
            r8 = r49
            r9 = r50
            monitor-enter(r31)
            boolean r10 = VERBOSE_IS_LOGGABLE     // Catch:{ all -> 0x00bd }
            if (r10 == 0) goto L_0x0012
            long r2 = com.bumptech.glide.util.LogTime.getLogTime()     // Catch:{ all -> 0x00bd }
            goto L_0x0014
        L_0x0012:
            r2 = 0
        L_0x0014:
            r11 = r2
            com.bumptech.glide.load.engine.EngineKeyFactory r13 = r1.keyFactory     // Catch:{ all -> 0x00bd }
            r14 = r33
            r15 = r34
            r16 = r35
            r17 = r36
            r18 = r41
            r19 = r37
            r20 = r38
            r21 = r44
            com.bumptech.glide.load.engine.EngineKey r13 = r13.buildKey(r14, r15, r16, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x00bd }
            com.bumptech.glide.load.engine.EngineResource r2 = r1.loadFromActiveResources(r13, r0)     // Catch:{ all -> 0x00bd }
            r3 = 0
            if (r2 == 0) goto L_0x0040
            com.bumptech.glide.load.DataSource r0 = com.bumptech.glide.load.DataSource.MEMORY_CACHE     // Catch:{ all -> 0x00bd }
            r8.onResourceReady(r2, r0)     // Catch:{ all -> 0x00bd }
            if (r10 == 0) goto L_0x003e
            java.lang.String r0 = "Loaded resource from active resources"
            logWithTimeAndKey(r0, r11, r13)     // Catch:{ all -> 0x00bd }
        L_0x003e:
            monitor-exit(r31)
            return r3
        L_0x0040:
            com.bumptech.glide.load.engine.EngineResource r2 = r1.loadFromCache(r13, r0)     // Catch:{ all -> 0x00bd }
            if (r2 == 0) goto L_0x0054
            com.bumptech.glide.load.DataSource r0 = com.bumptech.glide.load.DataSource.MEMORY_CACHE     // Catch:{ all -> 0x00bd }
            r8.onResourceReady(r2, r0)     // Catch:{ all -> 0x00bd }
            if (r10 == 0) goto L_0x0052
            java.lang.String r0 = "Loaded resource from cache"
            logWithTimeAndKey(r0, r11, r13)     // Catch:{ all -> 0x00bd }
        L_0x0052:
            monitor-exit(r31)
            return r3
        L_0x0054:
            com.bumptech.glide.load.engine.Jobs r2 = r1.jobs     // Catch:{ all -> 0x00bd }
            r15 = r48
            com.bumptech.glide.load.engine.EngineJob r2 = r2.get(r13, r15)     // Catch:{ all -> 0x00bd }
            if (r2 == 0) goto L_0x006f
            r2.addCallback(r8, r9)     // Catch:{ all -> 0x00bd }
            if (r10 == 0) goto L_0x0068
            java.lang.String r0 = "Added to existing load"
            logWithTimeAndKey(r0, r11, r13)     // Catch:{ all -> 0x00bd }
        L_0x0068:
            com.bumptech.glide.load.engine.Engine$LoadStatus r0 = new com.bumptech.glide.load.engine.Engine$LoadStatus     // Catch:{ all -> 0x00bd }
            r0.<init>(r8, r2)     // Catch:{ all -> 0x00bd }
            monitor-exit(r31)
            return r0
        L_0x006f:
            com.bumptech.glide.load.engine.Engine$EngineJobFactory r2 = r1.engineJobFactory     // Catch:{ all -> 0x00bd }
            r3 = r13
            r4 = r45
            r5 = r46
            r6 = r47
            r7 = r48
            com.bumptech.glide.load.engine.EngineJob r0 = r2.build(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00bd }
            com.bumptech.glide.load.engine.Engine$DecodeJobFactory r14 = r1.decodeJobFactory     // Catch:{ all -> 0x00bd }
            r15 = r32
            r16 = r33
            r17 = r13
            r18 = r34
            r19 = r35
            r20 = r36
            r21 = r37
            r22 = r38
            r23 = r39
            r24 = r40
            r25 = r41
            r26 = r42
            r27 = r43
            r28 = r48
            r29 = r44
            r30 = r0
            com.bumptech.glide.load.engine.DecodeJob r2 = r14.build(r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30)     // Catch:{ all -> 0x00bd }
            com.bumptech.glide.load.engine.Jobs r3 = r1.jobs     // Catch:{ all -> 0x00bd }
            r3.put(r13, r0)     // Catch:{ all -> 0x00bd }
            r0.addCallback(r8, r9)     // Catch:{ all -> 0x00bd }
            r0.start(r2)     // Catch:{ all -> 0x00bd }
            if (r10 == 0) goto L_0x00b6
            java.lang.String r2 = "Started new load"
            logWithTimeAndKey(r2, r11, r13)     // Catch:{ all -> 0x00bd }
        L_0x00b6:
            com.bumptech.glide.load.engine.Engine$LoadStatus r2 = new com.bumptech.glide.load.engine.Engine$LoadStatus     // Catch:{ all -> 0x00bd }
            r2.<init>(r8, r0)     // Catch:{ all -> 0x00bd }
            monitor-exit(r31)
            return r2
        L_0x00bd:
            r0 = move-exception
            monitor-exit(r31)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.Engine.load(com.bumptech.glide.GlideContext, java.lang.Object, com.bumptech.glide.load.Key, int, int, java.lang.Class, java.lang.Class, com.bumptech.glide.Priority, com.bumptech.glide.load.engine.DiskCacheStrategy, java.util.Map, boolean, boolean, com.bumptech.glide.load.Options, boolean, boolean, boolean, boolean, com.bumptech.glide.request.ResourceCallback, java.util.concurrent.Executor):com.bumptech.glide.load.engine.Engine$LoadStatus");
    }

    private static void logWithTimeAndKey(String str, long j, Key key) {
        Log.v(TAG, str + " in " + LogTime.getElapsedMillis(j) + "ms, key: " + key);
    }

    private EngineResource<?> loadFromActiveResources(Key key, boolean z) {
        if (!z) {
            return null;
        }
        EngineResource<?> engineResource = this.activeResources.get(key);
        if (engineResource != null) {
            engineResource.acquire();
        }
        return engineResource;
    }

    private EngineResource<?> loadFromCache(Key key, boolean z) {
        if (!z) {
            return null;
        }
        EngineResource<?> engineResourceFromCache = getEngineResourceFromCache(key);
        if (engineResourceFromCache != null) {
            engineResourceFromCache.acquire();
            this.activeResources.activate(key, engineResourceFromCache);
        }
        return engineResourceFromCache;
    }

    private EngineResource<?> getEngineResourceFromCache(Key key) {
        Resource<?> remove = this.cache.remove(key);
        if (remove == null) {
            return null;
        }
        if (remove instanceof EngineResource) {
            return (EngineResource) remove;
        }
        return new EngineResource<>(remove, true, true);
    }

    public void release(Resource<?> resource) {
        if (resource instanceof EngineResource) {
            ((EngineResource) resource).release();
            return;
        }
        throw new IllegalArgumentException("Cannot release anything but an EngineResource");
    }

    public synchronized void onEngineJobComplete(EngineJob<?> engineJob, Key key, EngineResource<?> engineResource) {
        if (engineResource != null) {
            engineResource.setResourceListener(key, this);
            if (engineResource.isCacheable()) {
                this.activeResources.activate(key, engineResource);
            }
        }
        this.jobs.removeIfCurrent(key, engineJob);
    }

    public synchronized void onEngineJobCancelled(EngineJob<?> engineJob, Key key) {
        this.jobs.removeIfCurrent(key, engineJob);
    }

    public void onResourceRemoved(Resource<?> resource) {
        this.resourceRecycler.recycle(resource);
    }

    public synchronized void onResourceReleased(Key key, EngineResource<?> engineResource) {
        this.activeResources.deactivate(key);
        if (engineResource.isCacheable()) {
            this.cache.put(key, engineResource);
        } else {
            this.resourceRecycler.recycle(engineResource);
        }
    }

    public void clearDiskCache() {
        this.diskCacheProvider.getDiskCache().clear();
    }

    public void shutdown() {
        this.engineJobFactory.shutdown();
        this.diskCacheProvider.clearDiskCacheIfCreated();
        this.activeResources.shutdown();
    }

    public class LoadStatus {

        /* renamed from: cb */
        private final ResourceCallback f305cb;
        private final EngineJob<?> engineJob;

        LoadStatus(ResourceCallback resourceCallback, EngineJob<?> engineJob2) {
            this.f305cb = resourceCallback;
            this.engineJob = engineJob2;
        }

        public void cancel() {
            synchronized (Engine.this) {
                this.engineJob.removeCallback(this.f305cb);
            }
        }
    }

    private static class LazyDiskCacheProvider implements DecodeJob.DiskCacheProvider {
        private volatile DiskCache diskCache;
        private final DiskCache.Factory factory;

        LazyDiskCacheProvider(DiskCache.Factory factory2) {
            this.factory = factory2;
        }

        /* access modifiers changed from: package-private */
        public synchronized void clearDiskCacheIfCreated() {
            if (this.diskCache != null) {
                this.diskCache.clear();
            }
        }

        public DiskCache getDiskCache() {
            if (this.diskCache == null) {
                synchronized (this) {
                    if (this.diskCache == null) {
                        this.diskCache = this.factory.build();
                    }
                    if (this.diskCache == null) {
                        this.diskCache = new DiskCacheAdapter();
                    }
                }
            }
            return this.diskCache;
        }
    }

    static class DecodeJobFactory {
        private int creationOrder;
        final DecodeJob.DiskCacheProvider diskCacheProvider;
        final Pools.Pool<DecodeJob<?>> pool = FactoryPools.threadSafe(150, new FactoryPools.Factory<DecodeJob<?>>() {
            public DecodeJob<?> create() {
                return new DecodeJob<>(DecodeJobFactory.this.diskCacheProvider, DecodeJobFactory.this.pool);
            }
        });

        DecodeJobFactory(DecodeJob.DiskCacheProvider diskCacheProvider2) {
            this.diskCacheProvider = diskCacheProvider2;
        }

        /* access modifiers changed from: package-private */
        public <R> DecodeJob<R> build(GlideContext glideContext, Object obj, EngineKey engineKey, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, boolean z3, Options options, DecodeJob.Callback<R> callback) {
            DecodeJob decodeJob = (DecodeJob) Preconditions.checkNotNull(this.pool.acquire());
            int i3 = this.creationOrder;
            int i4 = i3;
            this.creationOrder = i3 + 1;
            return decodeJob.init(glideContext, obj, engineKey, key, i, i2, cls, cls2, priority, diskCacheStrategy, map, z, z2, z3, options, callback, i4);
        }
    }

    static class EngineJobFactory {
        final GlideExecutor animationExecutor;
        final GlideExecutor diskCacheExecutor;
        final EngineJobListener listener;
        final Pools.Pool<EngineJob<?>> pool = FactoryPools.threadSafe(150, new FactoryPools.Factory<EngineJob<?>>() {
            public EngineJob<?> create() {
                return new EngineJob(EngineJobFactory.this.diskCacheExecutor, EngineJobFactory.this.sourceExecutor, EngineJobFactory.this.sourceUnlimitedExecutor, EngineJobFactory.this.animationExecutor, EngineJobFactory.this.listener, EngineJobFactory.this.pool);
            }
        });
        final GlideExecutor sourceExecutor;
        final GlideExecutor sourceUnlimitedExecutor;

        EngineJobFactory(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, EngineJobListener engineJobListener) {
            this.diskCacheExecutor = glideExecutor;
            this.sourceExecutor = glideExecutor2;
            this.sourceUnlimitedExecutor = glideExecutor3;
            this.animationExecutor = glideExecutor4;
            this.listener = engineJobListener;
        }

        /* access modifiers changed from: package-private */
        public void shutdown() {
            Executors.shutdownAndAwaitTermination(this.diskCacheExecutor);
            Executors.shutdownAndAwaitTermination(this.sourceExecutor);
            Executors.shutdownAndAwaitTermination(this.sourceUnlimitedExecutor);
            Executors.shutdownAndAwaitTermination(this.animationExecutor);
        }

        /* access modifiers changed from: package-private */
        public <R> EngineJob<R> build(Key key, boolean z, boolean z2, boolean z3, boolean z4) {
            return ((EngineJob) Preconditions.checkNotNull(this.pool.acquire())).init(key, z, z2, z3, z4);
        }
    }
}
