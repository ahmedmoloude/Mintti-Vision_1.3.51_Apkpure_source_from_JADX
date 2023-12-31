package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.core.util.Pools;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.List;
import java.util.concurrent.Executor;

public final class SingleRequest<R> implements Request, SizeReadyCallback, ResourceCallback, FactoryPools.Poolable {
    private static final String GLIDE_TAG = "Glide";
    private static final boolean IS_VERBOSE_LOGGABLE = Log.isLoggable(TAG, 2);
    private static final Pools.Pool<SingleRequest<?>> POOL = FactoryPools.threadSafe(150, new FactoryPools.Factory<SingleRequest<?>>() {
        public SingleRequest<?> create() {
            return new SingleRequest<>();
        }
    });
    private static final String TAG = "Request";
    private TransitionFactory<? super R> animationFactory;
    private Executor callbackExecutor;
    private Context context;
    private Engine engine;
    private Drawable errorDrawable;
    private Drawable fallbackDrawable;
    private GlideContext glideContext;
    private int height;
    private boolean isCallingCallbacks;
    private Engine.LoadStatus loadStatus;
    private Object model;
    private int overrideHeight;
    private int overrideWidth;
    private Drawable placeholderDrawable;
    private Priority priority;
    private RequestCoordinator requestCoordinator;
    private List<RequestListener<R>> requestListeners;
    private BaseRequestOptions<?> requestOptions;
    private RuntimeException requestOrigin;
    private Resource<R> resource;
    private long startTime;
    private final StateVerifier stateVerifier;
    private Status status;
    private final String tag;
    private Target<R> target;
    private RequestListener<R> targetListener;
    private Class<R> transcodeClass;
    private int width;

    private enum Status {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CLEARED
    }

    public static <R> SingleRequest<R> obtain(Context context2, GlideContext glideContext2, Object obj, Class<R> cls, BaseRequestOptions<?> baseRequestOptions, int i, int i2, Priority priority2, Target<R> target2, RequestListener<R> requestListener, List<RequestListener<R>> list, RequestCoordinator requestCoordinator2, Engine engine2, TransitionFactory<? super R> transitionFactory, Executor executor) {
        SingleRequest<R> acquire = POOL.acquire();
        if (acquire == null) {
            acquire = new SingleRequest<>();
        }
        acquire.init(context2, glideContext2, obj, cls, baseRequestOptions, i, i2, priority2, target2, requestListener, list, requestCoordinator2, engine2, transitionFactory, executor);
        return acquire;
    }

    SingleRequest() {
        this.tag = IS_VERBOSE_LOGGABLE ? String.valueOf(super.hashCode()) : null;
        this.stateVerifier = StateVerifier.newInstance();
    }

    private synchronized void init(Context context2, GlideContext glideContext2, Object obj, Class<R> cls, BaseRequestOptions<?> baseRequestOptions, int i, int i2, Priority priority2, Target<R> target2, RequestListener<R> requestListener, List<RequestListener<R>> list, RequestCoordinator requestCoordinator2, Engine engine2, TransitionFactory<? super R> transitionFactory, Executor executor) {
        this.context = context2;
        this.glideContext = glideContext2;
        this.model = obj;
        this.transcodeClass = cls;
        this.requestOptions = baseRequestOptions;
        this.overrideWidth = i;
        this.overrideHeight = i2;
        this.priority = priority2;
        this.target = target2;
        this.targetListener = requestListener;
        this.requestListeners = list;
        this.requestCoordinator = requestCoordinator2;
        this.engine = engine2;
        this.animationFactory = transitionFactory;
        this.callbackExecutor = executor;
        this.status = Status.PENDING;
        if (this.requestOrigin == null && glideContext2.isLoggingRequestOriginsEnabled()) {
            this.requestOrigin = new RuntimeException("Glide request origin trace");
        }
    }

    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }

    public synchronized void recycle() {
        assertNotCallingCallbacks();
        this.context = null;
        this.glideContext = null;
        this.model = null;
        this.transcodeClass = null;
        this.requestOptions = null;
        this.overrideWidth = -1;
        this.overrideHeight = -1;
        this.target = null;
        this.requestListeners = null;
        this.targetListener = null;
        this.requestCoordinator = null;
        this.animationFactory = null;
        this.loadStatus = null;
        this.errorDrawable = null;
        this.placeholderDrawable = null;
        this.fallbackDrawable = null;
        this.width = -1;
        this.height = -1;
        this.requestOrigin = null;
        POOL.release(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a4, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void begin() {
        /*
            r3 = this;
            monitor-enter(r3)
            r3.assertNotCallingCallbacks()     // Catch:{ all -> 0x00ad }
            com.bumptech.glide.util.pool.StateVerifier r0 = r3.stateVerifier     // Catch:{ all -> 0x00ad }
            r0.throwIfRecycled()     // Catch:{ all -> 0x00ad }
            long r0 = com.bumptech.glide.util.LogTime.getLogTime()     // Catch:{ all -> 0x00ad }
            r3.startTime = r0     // Catch:{ all -> 0x00ad }
            java.lang.Object r0 = r3.model     // Catch:{ all -> 0x00ad }
            if (r0 != 0) goto L_0x003a
            int r0 = r3.overrideWidth     // Catch:{ all -> 0x00ad }
            int r1 = r3.overrideHeight     // Catch:{ all -> 0x00ad }
            boolean r0 = com.bumptech.glide.util.Util.isValidDimensions(r0, r1)     // Catch:{ all -> 0x00ad }
            if (r0 == 0) goto L_0x0025
            int r0 = r3.overrideWidth     // Catch:{ all -> 0x00ad }
            r3.width = r0     // Catch:{ all -> 0x00ad }
            int r0 = r3.overrideHeight     // Catch:{ all -> 0x00ad }
            r3.height = r0     // Catch:{ all -> 0x00ad }
        L_0x0025:
            android.graphics.drawable.Drawable r0 = r3.getFallbackDrawable()     // Catch:{ all -> 0x00ad }
            if (r0 != 0) goto L_0x002d
            r0 = 5
            goto L_0x002e
        L_0x002d:
            r0 = 3
        L_0x002e:
            com.bumptech.glide.load.engine.GlideException r1 = new com.bumptech.glide.load.engine.GlideException     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "Received null model"
            r1.<init>(r2)     // Catch:{ all -> 0x00ad }
            r3.onLoadFailed(r1, r0)     // Catch:{ all -> 0x00ad }
            monitor-exit(r3)
            return
        L_0x003a:
            com.bumptech.glide.request.SingleRequest$Status r0 = r3.status     // Catch:{ all -> 0x00ad }
            com.bumptech.glide.request.SingleRequest$Status r1 = com.bumptech.glide.request.SingleRequest.Status.RUNNING     // Catch:{ all -> 0x00ad }
            if (r0 == r1) goto L_0x00a5
            com.bumptech.glide.request.SingleRequest$Status r0 = r3.status     // Catch:{ all -> 0x00ad }
            com.bumptech.glide.request.SingleRequest$Status r1 = com.bumptech.glide.request.SingleRequest.Status.COMPLETE     // Catch:{ all -> 0x00ad }
            if (r0 != r1) goto L_0x004f
            com.bumptech.glide.load.engine.Resource<R> r0 = r3.resource     // Catch:{ all -> 0x00ad }
            com.bumptech.glide.load.DataSource r1 = com.bumptech.glide.load.DataSource.MEMORY_CACHE     // Catch:{ all -> 0x00ad }
            r3.onResourceReady(r0, r1)     // Catch:{ all -> 0x00ad }
            monitor-exit(r3)
            return
        L_0x004f:
            com.bumptech.glide.request.SingleRequest$Status r0 = com.bumptech.glide.request.SingleRequest.Status.WAITING_FOR_SIZE     // Catch:{ all -> 0x00ad }
            r3.status = r0     // Catch:{ all -> 0x00ad }
            int r0 = r3.overrideWidth     // Catch:{ all -> 0x00ad }
            int r1 = r3.overrideHeight     // Catch:{ all -> 0x00ad }
            boolean r0 = com.bumptech.glide.util.Util.isValidDimensions(r0, r1)     // Catch:{ all -> 0x00ad }
            if (r0 == 0) goto L_0x0065
            int r0 = r3.overrideWidth     // Catch:{ all -> 0x00ad }
            int r1 = r3.overrideHeight     // Catch:{ all -> 0x00ad }
            r3.onSizeReady(r0, r1)     // Catch:{ all -> 0x00ad }
            goto L_0x006a
        L_0x0065:
            com.bumptech.glide.request.target.Target<R> r0 = r3.target     // Catch:{ all -> 0x00ad }
            r0.getSize(r3)     // Catch:{ all -> 0x00ad }
        L_0x006a:
            com.bumptech.glide.request.SingleRequest$Status r0 = r3.status     // Catch:{ all -> 0x00ad }
            com.bumptech.glide.request.SingleRequest$Status r1 = com.bumptech.glide.request.SingleRequest.Status.RUNNING     // Catch:{ all -> 0x00ad }
            if (r0 == r1) goto L_0x0076
            com.bumptech.glide.request.SingleRequest$Status r0 = r3.status     // Catch:{ all -> 0x00ad }
            com.bumptech.glide.request.SingleRequest$Status r1 = com.bumptech.glide.request.SingleRequest.Status.WAITING_FOR_SIZE     // Catch:{ all -> 0x00ad }
            if (r0 != r1) goto L_0x0085
        L_0x0076:
            boolean r0 = r3.canNotifyStatusChanged()     // Catch:{ all -> 0x00ad }
            if (r0 == 0) goto L_0x0085
            com.bumptech.glide.request.target.Target<R> r0 = r3.target     // Catch:{ all -> 0x00ad }
            android.graphics.drawable.Drawable r1 = r3.getPlaceholderDrawable()     // Catch:{ all -> 0x00ad }
            r0.onLoadStarted(r1)     // Catch:{ all -> 0x00ad }
        L_0x0085:
            boolean r0 = IS_VERBOSE_LOGGABLE     // Catch:{ all -> 0x00ad }
            if (r0 == 0) goto L_0x00a3
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ad }
            r0.<init>()     // Catch:{ all -> 0x00ad }
            java.lang.String r1 = "finished run method in "
            r0.append(r1)     // Catch:{ all -> 0x00ad }
            long r1 = r3.startTime     // Catch:{ all -> 0x00ad }
            double r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1)     // Catch:{ all -> 0x00ad }
            r0.append(r1)     // Catch:{ all -> 0x00ad }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00ad }
            r3.logV(r0)     // Catch:{ all -> 0x00ad }
        L_0x00a3:
            monitor-exit(r3)
            return
        L_0x00a5:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00ad }
            java.lang.String r1 = "Cannot restart a running request"
            r0.<init>(r1)     // Catch:{ all -> 0x00ad }
            throw r0     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.begin():void");
    }

    private void cancel() {
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        this.target.removeCallback(this);
        Engine.LoadStatus loadStatus2 = this.loadStatus;
        if (loadStatus2 != null) {
            loadStatus2.cancel();
            this.loadStatus = null;
        }
    }

    private void assertNotCallingCallbacks() {
        if (this.isCallingCallbacks) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    public synchronized void clear() {
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        if (this.status != Status.CLEARED) {
            cancel();
            Resource<R> resource2 = this.resource;
            if (resource2 != null) {
                releaseResource(resource2);
            }
            if (canNotifyCleared()) {
                this.target.onLoadCleared(getPlaceholderDrawable());
            }
            this.status = Status.CLEARED;
        }
    }

    private void releaseResource(Resource<?> resource2) {
        this.engine.release(resource2);
        this.resource = null;
    }

    public synchronized boolean isRunning() {
        return this.status == Status.RUNNING || this.status == Status.WAITING_FOR_SIZE;
    }

    public synchronized boolean isComplete() {
        return this.status == Status.COMPLETE;
    }

    public synchronized boolean isResourceSet() {
        return isComplete();
    }

    public synchronized boolean isCleared() {
        return this.status == Status.CLEARED;
    }

    public synchronized boolean isFailed() {
        return this.status == Status.FAILED;
    }

    private Drawable getErrorDrawable() {
        if (this.errorDrawable == null) {
            Drawable errorPlaceholder = this.requestOptions.getErrorPlaceholder();
            this.errorDrawable = errorPlaceholder;
            if (errorPlaceholder == null && this.requestOptions.getErrorId() > 0) {
                this.errorDrawable = loadDrawable(this.requestOptions.getErrorId());
            }
        }
        return this.errorDrawable;
    }

    private Drawable getPlaceholderDrawable() {
        if (this.placeholderDrawable == null) {
            Drawable placeholderDrawable2 = this.requestOptions.getPlaceholderDrawable();
            this.placeholderDrawable = placeholderDrawable2;
            if (placeholderDrawable2 == null && this.requestOptions.getPlaceholderId() > 0) {
                this.placeholderDrawable = loadDrawable(this.requestOptions.getPlaceholderId());
            }
        }
        return this.placeholderDrawable;
    }

    private Drawable getFallbackDrawable() {
        if (this.fallbackDrawable == null) {
            Drawable fallbackDrawable2 = this.requestOptions.getFallbackDrawable();
            this.fallbackDrawable = fallbackDrawable2;
            if (fallbackDrawable2 == null && this.requestOptions.getFallbackId() > 0) {
                this.fallbackDrawable = loadDrawable(this.requestOptions.getFallbackId());
            }
        }
        return this.fallbackDrawable;
    }

    private Drawable loadDrawable(int i) {
        return DrawableDecoderCompat.getDrawable((Context) this.glideContext, i, this.requestOptions.getTheme() != null ? this.requestOptions.getTheme() : this.context.getTheme());
    }

    private synchronized void setErrorPlaceholder() {
        if (canNotifyStatusChanged()) {
            Drawable drawable = null;
            if (this.model == null) {
                drawable = getFallbackDrawable();
            }
            if (drawable == null) {
                drawable = getErrorDrawable();
            }
            if (drawable == null) {
                drawable = getPlaceholderDrawable();
            }
            this.target.onLoadFailed(drawable);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ee, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onSizeReady(int r23, int r24) {
        /*
            r22 = this;
            r15 = r22
            monitor-enter(r22)
            com.bumptech.glide.util.pool.StateVerifier r0 = r15.stateVerifier     // Catch:{ all -> 0x00f5 }
            r0.throwIfRecycled()     // Catch:{ all -> 0x00f5 }
            boolean r0 = IS_VERBOSE_LOGGABLE     // Catch:{ all -> 0x00f5 }
            if (r0 == 0) goto L_0x0026
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f5 }
            r1.<init>()     // Catch:{ all -> 0x00f5 }
            java.lang.String r2 = "Got onSizeReady in "
            r1.append(r2)     // Catch:{ all -> 0x00f5 }
            long r2 = r15.startTime     // Catch:{ all -> 0x00f5 }
            double r2 = com.bumptech.glide.util.LogTime.getElapsedMillis(r2)     // Catch:{ all -> 0x00f5 }
            r1.append(r2)     // Catch:{ all -> 0x00f5 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00f5 }
            r15.logV(r1)     // Catch:{ all -> 0x00f5 }
        L_0x0026:
            com.bumptech.glide.request.SingleRequest$Status r1 = r15.status     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.SingleRequest$Status r2 = com.bumptech.glide.request.SingleRequest.Status.WAITING_FOR_SIZE     // Catch:{ all -> 0x00f5 }
            if (r1 == r2) goto L_0x002e
            monitor-exit(r22)
            return
        L_0x002e:
            com.bumptech.glide.request.SingleRequest$Status r1 = com.bumptech.glide.request.SingleRequest.Status.RUNNING     // Catch:{ all -> 0x00f5 }
            r15.status = r1     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r1 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            float r1 = r1.getSizeMultiplier()     // Catch:{ all -> 0x00f5 }
            r2 = r23
            int r2 = maybeApplySizeMultiplier(r2, r1)     // Catch:{ all -> 0x00f5 }
            r15.width = r2     // Catch:{ all -> 0x00f5 }
            r2 = r24
            int r1 = maybeApplySizeMultiplier(r2, r1)     // Catch:{ all -> 0x00f5 }
            r15.height = r1     // Catch:{ all -> 0x00f5 }
            if (r0 == 0) goto L_0x0064
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f5 }
            r1.<init>()     // Catch:{ all -> 0x00f5 }
            java.lang.String r2 = "finished setup for calling load in "
            r1.append(r2)     // Catch:{ all -> 0x00f5 }
            long r2 = r15.startTime     // Catch:{ all -> 0x00f5 }
            double r2 = com.bumptech.glide.util.LogTime.getElapsedMillis(r2)     // Catch:{ all -> 0x00f5 }
            r1.append(r2)     // Catch:{ all -> 0x00f5 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00f5 }
            r15.logV(r1)     // Catch:{ all -> 0x00f5 }
        L_0x0064:
            com.bumptech.glide.load.engine.Engine r1 = r15.engine     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.GlideContext r2 = r15.glideContext     // Catch:{ all -> 0x00f5 }
            java.lang.Object r3 = r15.model     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r4 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.load.Key r4 = r4.getSignature()     // Catch:{ all -> 0x00f5 }
            int r5 = r15.width     // Catch:{ all -> 0x00f5 }
            int r6 = r15.height     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r7 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            java.lang.Class r7 = r7.getResourceClass()     // Catch:{ all -> 0x00f5 }
            java.lang.Class<R> r8 = r15.transcodeClass     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.Priority r9 = r15.priority     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r10 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.load.engine.DiskCacheStrategy r10 = r10.getDiskCacheStrategy()     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r11 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            java.util.Map r11 = r11.getTransformations()     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r12 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            boolean r12 = r12.isTransformationRequired()     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r13 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            boolean r13 = r13.isScaleOnlyOrNoTransform()     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r14 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.load.Options r14 = r14.getOptions()     // Catch:{ all -> 0x00f5 }
            r21 = r0
            com.bumptech.glide.request.BaseRequestOptions<?> r0 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            boolean r0 = r0.isMemoryCacheable()     // Catch:{ all -> 0x00f5 }
            r23 = r0
            com.bumptech.glide.request.BaseRequestOptions<?> r0 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            boolean r16 = r0.getUseUnlimitedSourceGeneratorsPool()     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r0 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            boolean r17 = r0.getUseAnimationPool()     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.request.BaseRequestOptions<?> r0 = r15.requestOptions     // Catch:{ all -> 0x00f5 }
            boolean r18 = r0.getOnlyRetrieveFromCache()     // Catch:{ all -> 0x00f5 }
            java.util.concurrent.Executor r0 = r15.callbackExecutor     // Catch:{ all -> 0x00f5 }
            r15 = r23
            r19 = r22
            r20 = r0
            com.bumptech.glide.load.engine.Engine$LoadStatus r0 = r1.load(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)     // Catch:{ all -> 0x00f1 }
            r1 = r22
            r1.loadStatus = r0     // Catch:{ all -> 0x00ef }
            com.bumptech.glide.request.SingleRequest$Status r0 = r1.status     // Catch:{ all -> 0x00ef }
            com.bumptech.glide.request.SingleRequest$Status r2 = com.bumptech.glide.request.SingleRequest.Status.RUNNING     // Catch:{ all -> 0x00ef }
            if (r0 == r2) goto L_0x00d1
            r0 = 0
            r1.loadStatus = r0     // Catch:{ all -> 0x00ef }
        L_0x00d1:
            if (r21 == 0) goto L_0x00ed
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ef }
            r0.<init>()     // Catch:{ all -> 0x00ef }
            java.lang.String r2 = "finished onSizeReady in "
            r0.append(r2)     // Catch:{ all -> 0x00ef }
            long r2 = r1.startTime     // Catch:{ all -> 0x00ef }
            double r2 = com.bumptech.glide.util.LogTime.getElapsedMillis(r2)     // Catch:{ all -> 0x00ef }
            r0.append(r2)     // Catch:{ all -> 0x00ef }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00ef }
            r1.logV(r0)     // Catch:{ all -> 0x00ef }
        L_0x00ed:
            monitor-exit(r22)
            return
        L_0x00ef:
            r0 = move-exception
            goto L_0x00f7
        L_0x00f1:
            r0 = move-exception
            r1 = r22
            goto L_0x00f7
        L_0x00f5:
            r0 = move-exception
            r1 = r15
        L_0x00f7:
            monitor-exit(r22)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.onSizeReady(int, int):void");
    }

    private static int maybeApplySizeMultiplier(int i, float f) {
        return i == Integer.MIN_VALUE ? i : Math.round(f * ((float) i));
    }

    private boolean canSetResource() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canSetImage(this);
    }

    private boolean canNotifyCleared() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canNotifyCleared(this);
    }

    private boolean canNotifyStatusChanged() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || requestCoordinator2.canNotifyStatusChanged(this);
    }

    private boolean isFirstReadyResource() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        return requestCoordinator2 == null || !requestCoordinator2.isAnyResourceSet();
    }

    private void notifyLoadSuccess() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        if (requestCoordinator2 != null) {
            requestCoordinator2.onRequestSuccess(this);
        }
    }

    private void notifyLoadFailed() {
        RequestCoordinator requestCoordinator2 = this.requestCoordinator;
        if (requestCoordinator2 != null) {
            requestCoordinator2.onRequestFailed(this);
        }
    }

    public synchronized void onResourceReady(Resource<?> resource2, DataSource dataSource) {
        this.stateVerifier.throwIfRecycled();
        this.loadStatus = null;
        if (resource2 == null) {
            onLoadFailed(new GlideException("Expected to receive a Resource<R> with an object of " + this.transcodeClass + " inside, but instead got null."));
            return;
        }
        Object obj = resource2.get();
        if (obj != null) {
            if (this.transcodeClass.isAssignableFrom(obj.getClass())) {
                if (!canSetResource()) {
                    releaseResource(resource2);
                    this.status = Status.COMPLETE;
                    return;
                }
                onResourceReady(resource2, obj, dataSource);
                return;
            }
        }
        releaseResource(resource2);
        StringBuilder sb = new StringBuilder();
        sb.append("Expected to receive an object of ");
        sb.append(this.transcodeClass);
        sb.append(" but instead got ");
        sb.append(obj != null ? obj.getClass() : "");
        sb.append("{");
        sb.append(obj);
        sb.append("} inside Resource{");
        sb.append(resource2);
        sb.append("}.");
        sb.append(obj != null ? "" : " To indicate failure return a null Resource object, rather than a Resource object containing null data.");
        onLoadFailed(new GlideException(sb.toString()));
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a9 A[Catch:{ all -> 0x00bb }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void onResourceReady(com.bumptech.glide.load.engine.Resource<R> r11, R r12, com.bumptech.glide.load.DataSource r13) {
        /*
            r10 = this;
            monitor-enter(r10)
            boolean r6 = r10.isFirstReadyResource()     // Catch:{ all -> 0x00bf }
            com.bumptech.glide.request.SingleRequest$Status r0 = com.bumptech.glide.request.SingleRequest.Status.COMPLETE     // Catch:{ all -> 0x00bf }
            r10.status = r0     // Catch:{ all -> 0x00bf }
            r10.resource = r11     // Catch:{ all -> 0x00bf }
            com.bumptech.glide.GlideContext r11 = r10.glideContext     // Catch:{ all -> 0x00bf }
            int r11 = r11.getLogLevel()     // Catch:{ all -> 0x00bf }
            r0 = 3
            if (r11 > r0) goto L_0x006c
            java.lang.String r11 = "Glide"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bf }
            r0.<init>()     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = "Finished loading "
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.Class r1 = r12.getClass()     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = r1.getSimpleName()     // Catch:{ all -> 0x00bf }
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = " from "
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            r0.append(r13)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = " for "
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.Object r1 = r10.model     // Catch:{ all -> 0x00bf }
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = " with size ["
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            int r1 = r10.width     // Catch:{ all -> 0x00bf }
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = "x"
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            int r1 = r10.height     // Catch:{ all -> 0x00bf }
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = "] in "
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            long r1 = r10.startTime     // Catch:{ all -> 0x00bf }
            double r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1)     // Catch:{ all -> 0x00bf }
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = " ms"
            r0.append(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00bf }
            android.util.Log.d(r11, r0)     // Catch:{ all -> 0x00bf }
        L_0x006c:
            r11 = 1
            r10.isCallingCallbacks = r11     // Catch:{ all -> 0x00bf }
            r7 = 0
            java.util.List<com.bumptech.glide.request.RequestListener<R>> r0 = r10.requestListeners     // Catch:{ all -> 0x00bb }
            if (r0 == 0) goto L_0x0092
            java.util.Iterator r8 = r0.iterator()     // Catch:{ all -> 0x00bb }
            r9 = 0
        L_0x0079:
            boolean r0 = r8.hasNext()     // Catch:{ all -> 0x00bb }
            if (r0 == 0) goto L_0x0093
            java.lang.Object r0 = r8.next()     // Catch:{ all -> 0x00bb }
            com.bumptech.glide.request.RequestListener r0 = (com.bumptech.glide.request.RequestListener) r0     // Catch:{ all -> 0x00bb }
            java.lang.Object r2 = r10.model     // Catch:{ all -> 0x00bb }
            com.bumptech.glide.request.target.Target<R> r3 = r10.target     // Catch:{ all -> 0x00bb }
            r1 = r12
            r4 = r13
            r5 = r6
            boolean r0 = r0.onResourceReady(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x00bb }
            r9 = r9 | r0
            goto L_0x0079
        L_0x0092:
            r9 = 0
        L_0x0093:
            com.bumptech.glide.request.RequestListener<R> r0 = r10.targetListener     // Catch:{ all -> 0x00bb }
            if (r0 == 0) goto L_0x00a5
            java.lang.Object r2 = r10.model     // Catch:{ all -> 0x00bb }
            com.bumptech.glide.request.target.Target<R> r3 = r10.target     // Catch:{ all -> 0x00bb }
            r1 = r12
            r4 = r13
            r5 = r6
            boolean r0 = r0.onResourceReady(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x00bb }
            if (r0 == 0) goto L_0x00a5
            goto L_0x00a6
        L_0x00a5:
            r11 = 0
        L_0x00a6:
            r11 = r11 | r9
            if (r11 != 0) goto L_0x00b4
            com.bumptech.glide.request.transition.TransitionFactory<? super R> r11 = r10.animationFactory     // Catch:{ all -> 0x00bb }
            com.bumptech.glide.request.transition.Transition r11 = r11.build(r13, r6)     // Catch:{ all -> 0x00bb }
            com.bumptech.glide.request.target.Target<R> r13 = r10.target     // Catch:{ all -> 0x00bb }
            r13.onResourceReady(r12, r11)     // Catch:{ all -> 0x00bb }
        L_0x00b4:
            r10.isCallingCallbacks = r7     // Catch:{ all -> 0x00bf }
            r10.notifyLoadSuccess()     // Catch:{ all -> 0x00bf }
            monitor-exit(r10)
            return
        L_0x00bb:
            r11 = move-exception
            r10.isCallingCallbacks = r7     // Catch:{ all -> 0x00bf }
            throw r11     // Catch:{ all -> 0x00bf }
        L_0x00bf:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.onResourceReady(com.bumptech.glide.load.engine.Resource, java.lang.Object, com.bumptech.glide.load.DataSource):void");
    }

    public synchronized void onLoadFailed(GlideException glideException) {
        onLoadFailed(glideException, 5);
    }

    /* JADX INFO: finally extract failed */
    private synchronized void onLoadFailed(GlideException glideException, int i) {
        boolean z;
        this.stateVerifier.throwIfRecycled();
        glideException.setOrigin(this.requestOrigin);
        int logLevel = this.glideContext.getLogLevel();
        if (logLevel <= i) {
            Log.w(GLIDE_TAG, "Load failed for " + this.model + " with size [" + this.width + "x" + this.height + "]", glideException);
            if (logLevel <= 4) {
                glideException.logRootCauses(GLIDE_TAG);
            }
        }
        this.loadStatus = null;
        this.status = Status.FAILED;
        boolean z2 = true;
        this.isCallingCallbacks = true;
        try {
            List<RequestListener<R>> list = this.requestListeners;
            if (list != null) {
                z = false;
                for (RequestListener<R> onLoadFailed : list) {
                    z |= onLoadFailed.onLoadFailed(glideException, this.model, this.target, isFirstReadyResource());
                }
            } else {
                z = false;
            }
            RequestListener<R> requestListener = this.targetListener;
            if (requestListener == null || !requestListener.onLoadFailed(glideException, this.model, this.target, isFirstReadyResource())) {
                z2 = false;
            }
            if (!z && !z2) {
                setErrorPlaceholder();
            }
            this.isCallingCallbacks = false;
            notifyLoadFailed();
        } catch (Throwable th) {
            this.isCallingCallbacks = false;
            throw th;
        }
    }

    public synchronized boolean isEquivalentTo(Request request) {
        boolean z = false;
        if (!(request instanceof SingleRequest)) {
            return false;
        }
        SingleRequest singleRequest = (SingleRequest) request;
        synchronized (singleRequest) {
            if (this.overrideWidth == singleRequest.overrideWidth && this.overrideHeight == singleRequest.overrideHeight && Util.bothModelsNullEquivalentOrEquals(this.model, singleRequest.model) && this.transcodeClass.equals(singleRequest.transcodeClass) && this.requestOptions.equals(singleRequest.requestOptions) && this.priority == singleRequest.priority && listenerCountEquals(singleRequest)) {
                z = true;
            }
        }
        return z;
    }

    private synchronized boolean listenerCountEquals(SingleRequest<?> singleRequest) {
        boolean z;
        synchronized (singleRequest) {
            List<RequestListener<R>> list = this.requestListeners;
            z = false;
            int size = list == null ? 0 : list.size();
            List<RequestListener<R>> list2 = singleRequest.requestListeners;
            if (size == (list2 == null ? 0 : list2.size())) {
                z = true;
            }
        }
        return z;
    }

    private void logV(String str) {
        Log.v(TAG, str + " this: " + this.tag);
    }
}
