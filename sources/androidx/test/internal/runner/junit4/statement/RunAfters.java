package androidx.test.internal.runner.junit4.statement;

import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunAfters extends UiThreadStatement {
    private final List<FrameworkMethod> afters;
    private final Statement next;
    /* access modifiers changed from: private */
    public final Object target;

    public RunAfters(FrameworkMethod frameworkMethod, Statement statement, List<FrameworkMethod> list, Object obj) {
        super(statement, shouldRunOnUiThread(frameworkMethod));
        this.next = statement;
        this.afters = list;
        this.target = obj;
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void evaluate() throws java.lang.Throwable {
        /*
            r7 = this;
            java.util.concurrent.CopyOnWriteArrayList r0 = new java.util.concurrent.CopyOnWriteArrayList
            r0.<init>()
            r1 = 0
            org.junit.runners.model.Statement r2 = r7.next     // Catch:{ all -> 0x0039 }
            r2.evaluate()     // Catch:{ all -> 0x0039 }
            java.util.List<org.junit.runners.model.FrameworkMethod> r2 = r7.afters
            java.util.Iterator r2 = r2.iterator()
        L_0x0011:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x006b
            java.lang.Object r3 = r2.next()
            org.junit.runners.model.FrameworkMethod r3 = (org.junit.runners.model.FrameworkMethod) r3
            boolean r4 = shouldRunOnUiThread(r3)
            if (r4 == 0) goto L_0x002c
            androidx.test.internal.runner.junit4.statement.RunAfters$1 r4 = new androidx.test.internal.runner.junit4.statement.RunAfters$1
            r4.<init>(r3, r0)
            runOnUiThread(r4)
            goto L_0x0011
        L_0x002c:
            java.lang.Object r4 = r7.target     // Catch:{ all -> 0x0034 }
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ all -> 0x0034 }
            r3.invokeExplosively(r4, r5)     // Catch:{ all -> 0x0034 }
            goto L_0x0011
        L_0x0034:
            r3 = move-exception
            r0.add(r3)
            goto L_0x0011
        L_0x0039:
            r2 = move-exception
            r0.add(r2)     // Catch:{ all -> 0x006f }
            java.util.List<org.junit.runners.model.FrameworkMethod> r2 = r7.afters
            java.util.Iterator r2 = r2.iterator()
        L_0x0043:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x006b
            java.lang.Object r3 = r2.next()
            org.junit.runners.model.FrameworkMethod r3 = (org.junit.runners.model.FrameworkMethod) r3
            boolean r4 = shouldRunOnUiThread(r3)
            if (r4 == 0) goto L_0x005e
            androidx.test.internal.runner.junit4.statement.RunAfters$1 r4 = new androidx.test.internal.runner.junit4.statement.RunAfters$1
            r4.<init>(r3, r0)
            runOnUiThread(r4)
            goto L_0x0043
        L_0x005e:
            java.lang.Object r4 = r7.target     // Catch:{ all -> 0x0066 }
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ all -> 0x0066 }
            r3.invokeExplosively(r4, r5)     // Catch:{ all -> 0x0066 }
            goto L_0x0043
        L_0x0066:
            r3 = move-exception
            r0.add(r3)
            goto L_0x0043
        L_0x006b:
            org.junit.runners.model.MultipleFailureException.assertEmpty(r0)
            return
        L_0x006f:
            r2 = move-exception
            java.util.List<org.junit.runners.model.FrameworkMethod> r3 = r7.afters
            java.util.Iterator r3 = r3.iterator()
        L_0x0076:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x009e
            java.lang.Object r4 = r3.next()
            org.junit.runners.model.FrameworkMethod r4 = (org.junit.runners.model.FrameworkMethod) r4
            boolean r5 = shouldRunOnUiThread(r4)
            if (r5 == 0) goto L_0x0091
            androidx.test.internal.runner.junit4.statement.RunAfters$1 r5 = new androidx.test.internal.runner.junit4.statement.RunAfters$1
            r5.<init>(r4, r0)
            runOnUiThread(r5)
            goto L_0x0076
        L_0x0091:
            java.lang.Object r5 = r7.target     // Catch:{ all -> 0x0099 }
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ all -> 0x0099 }
            r4.invokeExplosively(r5, r6)     // Catch:{ all -> 0x0099 }
            goto L_0x0076
        L_0x0099:
            r4 = move-exception
            r0.add(r4)
            goto L_0x0076
        L_0x009e:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.internal.runner.junit4.statement.RunAfters.evaluate():void");
    }
}
