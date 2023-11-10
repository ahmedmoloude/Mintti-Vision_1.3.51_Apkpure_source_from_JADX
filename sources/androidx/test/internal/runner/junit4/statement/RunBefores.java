package androidx.test.internal.runner.junit4.statement;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunBefores extends UiThreadStatement {
    private final List<FrameworkMethod> befores;
    private final Statement next;
    /* access modifiers changed from: private */
    public final Object target;

    public RunBefores(FrameworkMethod frameworkMethod, Statement statement, List<FrameworkMethod> list, Object obj) {
        super(statement, shouldRunOnUiThread(frameworkMethod));
        this.next = statement;
        this.befores = list;
        this.target = obj;
    }

    public void evaluate() throws Throwable {
        final AtomicReference atomicReference = new AtomicReference();
        for (final FrameworkMethod next2 : this.befores) {
            if (shouldRunOnUiThread(next2)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            next2.invokeExplosively(RunBefores.this.target, new Object[0]);
                        } catch (Throwable th) {
                            atomicReference.set(th);
                        }
                    }
                });
                Throwable th = (Throwable) atomicReference.get();
                if (th != null) {
                    throw th;
                }
            } else {
                next2.invokeExplosively(this.target, new Object[0]);
            }
        }
        this.next.evaluate();
    }
}
