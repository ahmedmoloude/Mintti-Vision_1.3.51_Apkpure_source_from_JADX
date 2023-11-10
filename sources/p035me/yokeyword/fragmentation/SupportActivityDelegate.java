package p035me.yokeyword.fragmentation;

import android.os.Bundle;
import android.view.MotionEvent;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentationMagician;
import p035me.yokeyword.fragmentation.ExtraTransaction;
import p035me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import p035me.yokeyword.fragmentation.anim.FragmentAnimator;
import p035me.yokeyword.fragmentation.debug.DebugStackDelegate;
import p035me.yokeyword.fragmentation.queue.Action;

/* renamed from: me.yokeyword.fragmentation.SupportActivityDelegate */
public class SupportActivityDelegate {
    private FragmentActivity mActivity;
    private DebugStackDelegate mDebugStackDelegate;
    private int mDefaultFragmentBackground = 0;
    private FragmentAnimator mFragmentAnimator;
    boolean mFragmentClickable = true;
    boolean mPopMultipleNoAnim = false;
    /* access modifiers changed from: private */
    public ISupportActivity mSupport;
    /* access modifiers changed from: private */
    public TransactionDelegate mTransactionDelegate;

    public SupportActivityDelegate(ISupportActivity iSupportActivity) {
        if (iSupportActivity instanceof FragmentActivity) {
            this.mSupport = iSupportActivity;
            FragmentActivity fragmentActivity = (FragmentActivity) iSupportActivity;
            this.mActivity = fragmentActivity;
            this.mDebugStackDelegate = new DebugStackDelegate(fragmentActivity);
            return;
        }
        throw new RuntimeException("Must extends FragmentActivity/AppCompatActivity");
    }

    public ExtraTransaction extraTransaction() {
        return new ExtraTransaction.ExtraTransactionImpl((FragmentActivity) this.mSupport, getTopFragment(), getTransactionDelegate(), true);
    }

    public void onCreate(Bundle bundle) {
        this.mTransactionDelegate = getTransactionDelegate();
        this.mFragmentAnimator = this.mSupport.onCreateFragmentAnimator();
        this.mDebugStackDelegate.onCreate(Fragmentation.getDefault().getMode());
    }

    public TransactionDelegate getTransactionDelegate() {
        if (this.mTransactionDelegate == null) {
            this.mTransactionDelegate = new TransactionDelegate(this.mSupport);
        }
        return this.mTransactionDelegate;
    }

    public void onPostCreate(Bundle bundle) {
        this.mDebugStackDelegate.onPostCreate(Fragmentation.getDefault().getMode());
    }

    public FragmentAnimator getFragmentAnimator() {
        return this.mFragmentAnimator.copy();
    }

    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mFragmentAnimator = fragmentAnimator;
        for (Fragment next : FragmentationMagician.getActiveFragments(getSupportFragmentManager())) {
            if (next instanceof ISupportFragment) {
                SupportFragmentDelegate supportDelegate = ((ISupportFragment) next).getSupportDelegate();
                if (supportDelegate.mAnimByActivity) {
                    supportDelegate.mFragmentAnimator = fragmentAnimator.copy();
                    if (supportDelegate.mAnimHelper != null) {
                        supportDelegate.mAnimHelper.notifyChanged(supportDelegate.mFragmentAnimator);
                    }
                }
            }
        }
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

    public void setDefaultFragmentBackground(int i) {
        this.mDefaultFragmentBackground = i;
    }

    public int getDefaultFragmentBackground() {
        return this.mDefaultFragmentBackground;
    }

    public void showFragmentStackHierarchyView() {
        this.mDebugStackDelegate.showFragmentStackHierarchyView();
    }

    public void logFragmentStackHierarchy(String str) {
        this.mDebugStackDelegate.logFragmentRecords(str);
    }

    public void post(Runnable runnable) {
        this.mTransactionDelegate.post(runnable);
    }

    public void onBackPressed() {
        this.mTransactionDelegate.mActionQueue.enqueue(new Action(3) {
            public void run() {
                if (!SupportActivityDelegate.this.mFragmentClickable) {
                    SupportActivityDelegate.this.mFragmentClickable = true;
                }
                if (!SupportActivityDelegate.this.mTransactionDelegate.dispatchBackPressedEvent(SupportHelper.getActiveFragment(SupportActivityDelegate.this.getSupportFragmentManager()))) {
                    SupportActivityDelegate.this.mSupport.onBackPressedSupport();
                }
            }
        });
    }

    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this.mActivity);
        }
    }

    public void onDestroy() {
        this.mDebugStackDelegate.onDestroy();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return !this.mFragmentClickable;
    }

    public void loadRootFragment(int i, ISupportFragment iSupportFragment) {
        loadRootFragment(i, iSupportFragment, true, false);
    }

    public void loadRootFragment(int i, ISupportFragment iSupportFragment, boolean z, boolean z2) {
        this.mTransactionDelegate.loadRootTransaction(getSupportFragmentManager(), i, iSupportFragment, z, z2);
    }

    public void loadMultipleRootFragment(int i, int i2, ISupportFragment... iSupportFragmentArr) {
        this.mTransactionDelegate.loadMultipleRootTransaction(getSupportFragmentManager(), i, i2, iSupportFragmentArr);
    }

    public void showHideFragment(ISupportFragment iSupportFragment) {
        showHideFragment(iSupportFragment, (ISupportFragment) null);
    }

    public void showHideFragment(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        this.mTransactionDelegate.showHideFragment(getSupportFragmentManager(), iSupportFragment, iSupportFragment2);
    }

    public void start(ISupportFragment iSupportFragment) {
        start(iSupportFragment, 0);
    }

    public void start(ISupportFragment iSupportFragment, int i) {
        this.mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), iSupportFragment, 0, i, 0);
    }

    public void startForResult(ISupportFragment iSupportFragment, int i) {
        this.mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), iSupportFragment, i, 0, 1);
    }

    public void startWithPop(ISupportFragment iSupportFragment) {
        this.mTransactionDelegate.startWithPop(getSupportFragmentManager(), getTopFragment(), iSupportFragment);
    }

    public void startWithPopTo(ISupportFragment iSupportFragment, Class<?> cls, boolean z) {
        this.mTransactionDelegate.startWithPopTo(getSupportFragmentManager(), getTopFragment(), iSupportFragment, cls.getName(), z);
    }

    public void replaceFragment(ISupportFragment iSupportFragment, boolean z) {
        this.mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), iSupportFragment, 0, 0, z ? 10 : 11);
    }

    public void pop() {
        this.mTransactionDelegate.pop(getSupportFragmentManager());
    }

    public void popTo(Class<?> cls, boolean z) {
        popTo(cls, z, (Runnable) null);
    }

    public void popTo(Class<?> cls, boolean z, Runnable runnable) {
        popTo(cls, z, runnable, Integer.MAX_VALUE);
    }

    public void popTo(Class<?> cls, boolean z, Runnable runnable, int i) {
        this.mTransactionDelegate.popTo(cls.getName(), z, runnable, getSupportFragmentManager(), i);
    }

    /* access modifiers changed from: private */
    public FragmentManager getSupportFragmentManager() {
        return this.mActivity.getSupportFragmentManager();
    }

    private ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }
}
