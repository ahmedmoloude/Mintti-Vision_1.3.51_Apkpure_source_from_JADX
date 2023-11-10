package p035me.yokeyword.fragmentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import androidx.fragment.app.Fragment;
import p035me.yokeyword.fragmentation.anim.FragmentAnimator;

/* renamed from: me.yokeyword.fragmentation.SupportFragment */
public class SupportFragment extends Fragment implements ISupportFragment {
    /* access modifiers changed from: protected */
    public SupportActivity _mActivity;
    final SupportFragmentDelegate mDelegate = new SupportFragmentDelegate(this);

    public SupportFragmentDelegate getSupportDelegate() {
        return this.mDelegate;
    }

    public ExtraTransaction extraTransaction() {
        return this.mDelegate.extraTransaction();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mDelegate.onAttach(activity);
        this._mActivity = (SupportActivity) this.mDelegate.getActivity();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate.onCreate(bundle);
    }

    public Animation onCreateAnimation(int i, boolean z, int i2) {
        return this.mDelegate.onCreateAnimation(i, z, i2);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mDelegate.onActivityCreated(bundle);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mDelegate.onSaveInstanceState(bundle);
    }

    public void onResume() {
        super.onResume();
        this.mDelegate.onResume();
    }

    public void onPause() {
        super.onPause();
        this.mDelegate.onPause();
    }

    public void onDestroyView() {
        this.mDelegate.onDestroyView();
        super.onDestroyView();
    }

    public void onDestroy() {
        this.mDelegate.onDestroy();
        super.onDestroy();
    }

    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        this.mDelegate.onHiddenChanged(z);
    }

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        this.mDelegate.setUserVisibleHint(z);
    }

    @Deprecated
    public void enqueueAction(Runnable runnable) {
        this.mDelegate.enqueueAction(runnable);
    }

    public void post(Runnable runnable) {
        this.mDelegate.post(runnable);
    }

    public void onEnterAnimationEnd(Bundle bundle) {
        this.mDelegate.onEnterAnimationEnd(bundle);
    }

    public void onLazyInitView(Bundle bundle) {
        this.mDelegate.onLazyInitView(bundle);
    }

    public void onSupportVisible() {
        this.mDelegate.onSupportVisible();
    }

    public void onSupportInvisible() {
        this.mDelegate.onSupportInvisible();
    }

    public final boolean isSupportVisible() {
        return this.mDelegate.isSupportVisible();
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return this.mDelegate.onCreateFragmentAnimator();
    }

    public FragmentAnimator getFragmentAnimator() {
        return this.mDelegate.getFragmentAnimator();
    }

    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    public boolean onBackPressedSupport() {
        return this.mDelegate.onBackPressedSupport();
    }

    public void setFragmentResult(int i, Bundle bundle) {
        this.mDelegate.setFragmentResult(i, bundle);
    }

    public void onFragmentResult(int i, int i2, Bundle bundle) {
        this.mDelegate.onFragmentResult(i, i2, bundle);
    }

    public void onNewBundle(Bundle bundle) {
        this.mDelegate.onNewBundle(bundle);
    }

    public void putNewBundle(Bundle bundle) {
        this.mDelegate.putNewBundle(bundle);
    }

    /* access modifiers changed from: protected */
    public void hideSoftInput() {
        this.mDelegate.hideSoftInput();
    }

    /* access modifiers changed from: protected */
    public void showSoftInput(View view) {
        this.mDelegate.showSoftInput(view);
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

    public void popChild() {
        this.mDelegate.popChild();
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

    public void popToChild(Class<?> cls, boolean z) {
        this.mDelegate.popToChild(cls, z);
    }

    public void popToChild(Class<?> cls, boolean z, Runnable runnable) {
        this.mDelegate.popToChild(cls, z, runnable);
    }

    public void popToChild(Class<?> cls, boolean z, Runnable runnable, int i) {
        this.mDelegate.popToChild(cls, z, runnable, i);
    }

    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getFragmentManager());
    }

    public ISupportFragment getTopChildFragment() {
        return SupportHelper.getTopFragment(getChildFragmentManager());
    }

    public ISupportFragment getPreFragment() {
        return SupportHelper.getPreFragment(this);
    }

    public <T extends ISupportFragment> T findFragment(Class<T> cls) {
        return SupportHelper.findFragment(getFragmentManager(), cls);
    }

    public <T extends ISupportFragment> T findChildFragment(Class<T> cls) {
        return SupportHelper.findFragment(getChildFragmentManager(), cls);
    }
}
