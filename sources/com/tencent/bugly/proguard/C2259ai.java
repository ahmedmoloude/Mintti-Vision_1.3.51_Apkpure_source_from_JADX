package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.support.p005v4.media.session.PlaybackStateCompat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.tencent.bugly.proguard.ai */
/* compiled from: BUGLY */
public final class C2259ai {

    /* renamed from: b */
    private static C2259ai f1526b;

    /* renamed from: a */
    public C2258ah f1527a;

    /* renamed from: c */
    private final C2365w f1528c;

    /* renamed from: d */
    private final Context f1529d;

    /* renamed from: e */
    private Map<Integer, Long> f1530e = new HashMap();

    /* renamed from: f */
    private long f1531f;

    /* renamed from: g */
    private long f1532g;

    /* renamed from: h */
    private LinkedBlockingQueue<Runnable> f1533h = new LinkedBlockingQueue<>();

    /* renamed from: i */
    private LinkedBlockingQueue<Runnable> f1534i = new LinkedBlockingQueue<>();
    /* access modifiers changed from: private */

    /* renamed from: j */
    public final Object f1535j = new Object();

    /* renamed from: k */
    private long f1536k = 0;

    /* renamed from: l */
    private int f1537l = 0;

    /* renamed from: b */
    static /* synthetic */ int m578b(C2259ai aiVar) {
        int i = aiVar.f1537l - 1;
        aiVar.f1537l = i;
        return i;
    }

    private C2259ai(Context context) {
        this.f1529d = context;
        this.f1528c = C2365w.m1033a();
    }

    /* renamed from: a */
    public static synchronized C2259ai m570a(Context context) {
        C2259ai aiVar;
        synchronized (C2259ai.class) {
            if (f1526b == null) {
                f1526b = new C2259ai(context);
            }
            aiVar = f1526b;
        }
        return aiVar;
    }

    /* renamed from: a */
    public static synchronized C2259ai m569a() {
        C2259ai aiVar;
        synchronized (C2259ai.class) {
            aiVar = f1526b;
        }
        return aiVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo28008a(int r13, com.tencent.bugly.proguard.C2329bq r14, java.lang.String r15, java.lang.String r16, com.tencent.bugly.proguard.C2258ah r17, long r18, boolean r20) {
        /*
            r12 = this;
            r0 = r14
            int r3 = r0.f1811g
            byte[] r4 = com.tencent.bugly.proguard.C2251ae.m552a((java.lang.Object) r14)
            com.tencent.bugly.proguard.aj r9 = new com.tencent.bugly.proguard.aj     // Catch:{ all -> 0x0024 }
            r11 = r12
            android.content.Context r1 = r11.f1529d     // Catch:{ all -> 0x0022 }
            r0 = r9
            r2 = r13
            r5 = r15
            r6 = r16
            r7 = r17
            r8 = r20
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0022 }
            r7 = 1
            r8 = 1
            r5 = r12
            r6 = r9
            r9 = r18
            r5.m575a(r6, r7, r8, r9)     // Catch:{ all -> 0x0022 }
            return
        L_0x0022:
            r0 = move-exception
            goto L_0x0026
        L_0x0024:
            r0 = move-exception
            r11 = r12
        L_0x0026:
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x002f
            r0.printStackTrace()
        L_0x002f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2259ai.mo28008a(int, com.tencent.bugly.proguard.bq, java.lang.String, java.lang.String, com.tencent.bugly.proguard.ah, long, boolean):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m572a(int r14, int r15, byte[] r16, java.lang.String r17, java.lang.String r18, com.tencent.bugly.proguard.C2258ah r19, boolean r20) {
        /*
            r13 = this;
            com.tencent.bugly.proguard.aj r0 = new com.tencent.bugly.proguard.aj     // Catch:{ all -> 0x0027 }
            r12 = r13
            android.content.Context r2 = r12.f1529d     // Catch:{ all -> 0x0025 }
            r9 = 0
            r10 = 0
            r11 = 0
            r1 = r0
            r3 = r14
            r4 = r15
            r5 = r16
            r6 = r17
            r7 = r18
            r8 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x0025 }
            r1 = 0
            r2 = 0
            r14 = r13
            r15 = r0
            r16 = r20
            r17 = r1
            r18 = r2
            r14.m575a(r15, r16, r17, r18)     // Catch:{ all -> 0x0025 }
            return
        L_0x0025:
            r0 = move-exception
            goto L_0x0029
        L_0x0027:
            r0 = move-exception
            r12 = r13
        L_0x0029:
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0032
            r0.printStackTrace()
        L_0x0032:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2259ai.m572a(int, int, byte[], java.lang.String, java.lang.String, com.tencent.bugly.proguard.ah, boolean):void");
    }

    /* renamed from: a */
    public final void mo28009a(int i, C2329bq bqVar, String str, String str2, C2258ah ahVar, boolean z) {
        m572a(i, bqVar.f1811g, C2251ae.m552a((Object) bqVar), str, str2, ahVar, z);
    }

    /* renamed from: a */
    public final long mo28006a(boolean z) {
        long j;
        long b = C2273ap.m661b();
        int i = z ? 5 : 3;
        List<C2368y> a = this.f1528c.mo28167a(i);
        if (a == null || a.size() <= 0) {
            j = z ? this.f1532g : this.f1531f;
        } else {
            j = 0;
            try {
                C2368y yVar = a.get(0);
                if (yVar.f1992e >= b) {
                    j = C2273ap.m676d(yVar.f1994g);
                    if (i == 3) {
                        this.f1531f = j;
                    } else {
                        this.f1532g = j;
                    }
                    a.remove(yVar);
                }
            } catch (Throwable th) {
                C2265al.m605a(th);
            }
            if (a.size() > 0) {
                this.f1528c.mo28169a(a);
            }
        }
        C2265al.m609c("[UploadManager] Local network consume: %d KB", Long.valueOf(j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
        return j;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final synchronized void mo28010a(long j, boolean z) {
        int i = z ? 5 : 3;
        C2368y yVar = new C2368y();
        yVar.f1989b = i;
        yVar.f1992e = C2273ap.m661b();
        yVar.f1990c = "";
        yVar.f1991d = "";
        yVar.f1994g = C2273ap.m675c(j);
        this.f1528c.mo28172b(i);
        this.f1528c.mo28171a(yVar);
        if (z) {
            this.f1532g = j;
        } else {
            this.f1531f = j;
        }
        C2265al.m609c("[UploadManager] Network total consume: %d KB", Long.valueOf(j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
    }

    /* renamed from: a */
    public final synchronized void mo28007a(int i, long j) {
        if (i >= 0) {
            this.f1530e.put(Integer.valueOf(i), Long.valueOf(j));
            C2368y yVar = new C2368y();
            yVar.f1989b = i;
            yVar.f1992e = j;
            yVar.f1990c = "";
            yVar.f1991d = "";
            yVar.f1994g = new byte[0];
            this.f1528c.mo28172b(i);
            this.f1528c.mo28171a(yVar);
            C2265al.m609c("[UploadManager] Uploading(ID:%d) time: %s", Integer.valueOf(i), C2273ap.m642a(j));
            return;
        }
        C2265al.m611e("[UploadManager] Unknown uploading ID: %d", Integer.valueOf(i));
    }

    /* renamed from: a */
    public final synchronized long mo28005a(int i) {
        if (i >= 0) {
            Long l = this.f1530e.get(Integer.valueOf(i));
            if (l != null) {
                return l.longValue();
            }
        } else {
            C2265al.m611e("[UploadManager] Unknown upload ID: %d", Integer.valueOf(i));
        }
        return 0;
    }

    /* renamed from: b */
    public final boolean mo28011b(int i) {
        if (C2349p.f1912c) {
            C2265al.m609c("Uploading frequency will not be checked if SDK is in debug mode.", new Object[0]);
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis() - mo28005a(i);
        C2265al.m609c("[UploadManager] Time interval is %d seconds since last uploading(ID: %d).", Long.valueOf(currentTimeMillis / 1000), Integer.valueOf(i));
        if (currentTimeMillis >= 30000) {
            return true;
        }
        C2265al.m604a("[UploadManager] Data only be uploaded once in %d seconds.", 30L);
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005c, code lost:
        m573a(r4, (java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable>) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005f, code lost:
        if (r6 <= 0) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0061, code lost:
        com.tencent.bugly.proguard.C2265al.m609c("[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)", java.lang.Integer.valueOf(r6), java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0083, code lost:
        r0 = com.tencent.bugly.proguard.C2263ak.m595a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0087, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0089, code lost:
        r0.mo28017a(new com.tencent.bugly.proguard.C2259ai.C22612(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m579b() {
        /*
            r10 = this;
            com.tencent.bugly.proguard.ak r0 = com.tencent.bugly.proguard.C2263ak.m595a()
            java.util.concurrent.LinkedBlockingQueue r1 = new java.util.concurrent.LinkedBlockingQueue
            r1.<init>()
            java.util.concurrent.LinkedBlockingQueue r2 = new java.util.concurrent.LinkedBlockingQueue
            r2.<init>()
            java.lang.Object r3 = r10.f1535j
            monitor-enter(r3)
            java.lang.String r4 = "[UploadManager] Try to poll all upload task need and put them into temp queue (pid=%d | tid=%d)"
            r5 = 2
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ all -> 0x0092 }
            int r7 = android.os.Process.myPid()     // Catch:{ all -> 0x0092 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0092 }
            r8 = 0
            r6[r8] = r7     // Catch:{ all -> 0x0092 }
            int r7 = android.os.Process.myTid()     // Catch:{ all -> 0x0092 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0092 }
            r9 = 1
            r6[r9] = r7     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.C2265al.m609c(r4, r6)     // Catch:{ all -> 0x0092 }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r4 = r10.f1533h     // Catch:{ all -> 0x0092 }
            int r4 = r4.size()     // Catch:{ all -> 0x0092 }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r6 = r10.f1534i     // Catch:{ all -> 0x0092 }
            int r6 = r6.size()     // Catch:{ all -> 0x0092 }
            if (r4 != 0) goto L_0x0048
            if (r6 != 0) goto L_0x0048
            java.lang.String r0 = "[UploadManager] There is no upload task in queue."
            java.lang.Object[] r1 = new java.lang.Object[r8]     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.C2265al.m609c(r0, r1)     // Catch:{ all -> 0x0092 }
            monitor-exit(r3)     // Catch:{ all -> 0x0092 }
            return
        L_0x0048:
            if (r0 == 0) goto L_0x0050
            boolean r0 = r0.mo28020c()     // Catch:{ all -> 0x0092 }
            if (r0 != 0) goto L_0x0051
        L_0x0050:
            r6 = 0
        L_0x0051:
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r0 = r10.f1533h     // Catch:{ all -> 0x0092 }
            m576a(r0, r1, r4)     // Catch:{ all -> 0x0092 }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r0 = r10.f1534i     // Catch:{ all -> 0x0092 }
            m576a(r0, r2, r6)     // Catch:{ all -> 0x0092 }
            monitor-exit(r3)     // Catch:{ all -> 0x0092 }
            r10.m573a((int) r4, (java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable>) r1)
            if (r6 <= 0) goto L_0x0083
            java.lang.String r0 = "[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)"
            r1 = 3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)
            r1[r8] = r3
            int r3 = android.os.Process.myPid()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r1[r9] = r3
            int r3 = android.os.Process.myTid()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r1[r5] = r3
            com.tencent.bugly.proguard.C2265al.m609c(r0, r1)
        L_0x0083:
            com.tencent.bugly.proguard.ak r0 = com.tencent.bugly.proguard.C2263ak.m595a()
            if (r0 == 0) goto L_0x0091
            com.tencent.bugly.proguard.ai$2 r1 = new com.tencent.bugly.proguard.ai$2
            r1.<init>(r6, r2)
            r0.mo28017a(r1)
        L_0x0091:
            return
        L_0x0092:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0092 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2259ai.m579b():void");
    }

    /* renamed from: a */
    private static void m576a(LinkedBlockingQueue<Runnable> linkedBlockingQueue, LinkedBlockingQueue<Runnable> linkedBlockingQueue2, int i) {
        int i2 = 0;
        while (i2 < i) {
            Runnable peek = linkedBlockingQueue.peek();
            if (peek != null) {
                try {
                    linkedBlockingQueue2.put(peek);
                    linkedBlockingQueue.poll();
                } catch (Throwable th) {
                    C2265al.m611e("[UploadManager] Failed to add upload task to temp urgent queue: %s", th.getMessage());
                }
                i2++;
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    private void m573a(int i, LinkedBlockingQueue<Runnable> linkedBlockingQueue) {
        C2263ak a = C2263ak.m595a();
        if (i > 0) {
            C2265al.m609c("[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(i), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        }
        int i2 = 0;
        while (i2 < i) {
            final Runnable poll = linkedBlockingQueue.poll();
            if (poll != null) {
                synchronized (this.f1535j) {
                    if (this.f1537l < 2 || a == null) {
                        C2265al.m604a("[UploadManager] Create and start a new thread to execute a upload task: %s", "BUGLY_ASYNC_UPLOAD");
                        if (C2273ap.m648a((Runnable) new Runnable() {
                            public final void run() {
                                poll.run();
                                synchronized (C2259ai.this.f1535j) {
                                    C2259ai.m578b(C2259ai.this);
                                }
                            }
                        }, "BUGLY_ASYNC_UPLOAD") != null) {
                            synchronized (this.f1535j) {
                                this.f1537l++;
                            }
                        } else {
                            C2265al.m610d("[UploadManager] Failed to start a thread to execute asynchronous upload task,will try again next time.", new Object[0]);
                            m577a(poll, true);
                        }
                    } else {
                        a.mo28017a(poll);
                    }
                }
                i2++;
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    private boolean m577a(Runnable runnable, boolean z) {
        if (runnable == null) {
            C2265al.m604a("[UploadManager] Upload task should not be null", new Object[0]);
            return false;
        }
        try {
            C2265al.m609c("[UploadManager] Add upload task to queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            synchronized (this.f1535j) {
                if (z) {
                    this.f1533h.put(runnable);
                } else {
                    this.f1534i.put(runnable);
                }
            }
            return true;
        } catch (Throwable th) {
            C2265al.m611e("[UploadManager] Failed to add upload task to queue: %s", th.getMessage());
            return false;
        }
    }

    /* renamed from: a */
    private void m574a(Runnable runnable, long j) {
        if (runnable == null) {
            C2265al.m610d("[UploadManager] Upload task should not be null", new Object[0]);
            return;
        }
        C2265al.m609c("[UploadManager] Execute synchronized upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        Thread a = C2273ap.m648a(runnable, "BUGLY_SYNC_UPLOAD");
        if (a == null) {
            C2265al.m611e("[UploadManager] Failed to start a thread to execute synchronized upload task, add it to queue.", new Object[0]);
            m577a(runnable, true);
            return;
        }
        try {
            a.join(j);
        } catch (Throwable th) {
            C2265al.m611e("[UploadManager] Failed to join upload synchronized task with message: %s. Add it to queue.", th.getMessage());
            m577a(runnable, true);
            m579b();
        }
    }

    /* renamed from: a */
    private void m575a(Runnable runnable, boolean z, boolean z2, long j) {
        C2265al.m609c("[UploadManager] Add upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (z2) {
            m574a(runnable, j);
            return;
        }
        m577a(runnable, z);
        m579b();
    }
}
