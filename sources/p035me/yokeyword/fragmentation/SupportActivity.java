package p035me.yokeyword.fragmentation;

import android.os.Bundle;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;
import p035me.yokeyword.fragmentation.anim.FragmentAnimator;

/* renamed from: me.yokeyword.fragmentation.SupportActivity */
public class SupportActivity extends AppCompatActivity implements ISupportActivity {
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);

    public SupportActivityDelegate getSupportDelegate() {
        return this.mDelegate;
    }

    public ExtraTransaction extraTransaction() {
        return this.mDelegate.extraTransaction();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate.onCreate(bundle);
    }

    /* access modifiers changed from: protected */
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.mDelegate.onPostCreate(bundle);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mDelegate.onDestroy();
        super.onDestroy();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mDelegate.dispatchTouchEvent(motionEvent) || super.dispatchTouchEvent(motionEvent);
    }

    public final void onBackPressed() {
        this.mDelegate.onBackPressed();
    }

    public void onBackPressedSupport() {
        this.mDelegate.onBackPressedSupport();
    }

    public FragmentAnimator getFragmentAnimator() {
        return this.mDelegate.getFragmentAnimator();
    }

    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return this.mDelegate.onCreateFragmentAnimator();
    }

    public void post(Runnable runnable) {
        this.mDelegate.post(runnable);
    }

    public void loadRootFragment(int i, ISupportFragment iSupportFragment) {
        this.mDelegate.loadRootFragment(i, iSupportFragment);
    }

    public void loadRootFragment(int i, ISupportFragment iSupportFragment, boolean z, boolean z2) {
        this.mDelegate.loadRootFragment(i, iSupportFragment, z, z2);
    }

    public void loadMultipleRootFragment(int i, int i2, ISupportFragment... iSupportFragmentArr) {
        this.mDelegate.loadMultipleRootFragment(i, i2, iSupportFragmentArr);
    }

    public void showHideFragment(ISupportFragment iSupportFragment) {
        this.mDelegate.showHideFragment(iSupportFragment);
    }

    public void showHideFragment(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        this.mDelegate.showHideFragment(iSupportFragment, iSupportFragment2);
    }

    public void start(ISupportFragment iSupportFragment) {
        this.mDelegate.start(iSupportFragment);
    }

    public void start(ISupportFragment iSupportFragment, int i) {
        this.mDelegate.start(iSupportFragment, i);
    }

    public void startForResult(ISupportFragment iSupportFragment, int i) {
        this.mDelegate.startForResult(iSupportFragment, i);
    }

    public void startWithPop(ISupportFragment iSupportFragment) {
        this.mDelegate.startWithPop(iSupportFragment);
    }

    public void startWithPopTo(ISupportFragment iSupportFragment, Class<?> cls, boolean z) {
        this.mDelegate.startWithPopTo(iSupportFragment, cls, z);
    }

    public void replaceFragment(ISupportFragment iSupportFragment, boolean z) {
        this.mDelegate.replaceFragment(iSupportFragment, z);
    }

    public void pop() {
        this.mDelegate.pop();
    }

    public void popTo(Class<?> cls, boolean z) {
        this.mDelegate.popTo(cls, z);
    }

    public void popTo(Class<?> cls, boolean z, Runnable runnable) {
        this.mDelegate.popTo(cls, z, runnable);
    }

    public void popTo(Class<?> cls, boolean z, Runnable runnable, int i) {
        this.mDelegate.popTo(cls, z, runnable, i);
    }

    public void setDefaultFragmentBackground(int i) {
        this.mDelegate.setDefaultFragmentBackground(i);
    }

    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }

    public <T extends ISupportFragment> T findFragment(Class<T> cls) {
        return SupportHelper.findFragment(getSupportFragmentManager(), cls);
    }
}
