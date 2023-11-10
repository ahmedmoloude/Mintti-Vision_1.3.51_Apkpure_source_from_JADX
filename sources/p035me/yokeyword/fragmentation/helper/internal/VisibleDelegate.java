package p035me.yokeyword.fragmentation.helper.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentationMagician;
import java.util.List;
import p035me.yokeyword.fragmentation.ISupportFragment;

/* renamed from: me.yokeyword.fragmentation.helper.internal.VisibleDelegate */
public class VisibleDelegate {
    private static final String FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE = "fragmentation_compat_replace";
    private static final String FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE = "fragmentation_invisible_when_leave";
    private boolean mAbortInitVisible = false;
    private boolean mFirstCreateViewCompatReplace = true;
    private Fragment mFragment;
    private Handler mHandler;
    private boolean mInvisibleWhenLeave;
    private boolean mIsFirstVisible = true;
    private boolean mIsSupportVisible;
    private boolean mNeedDispatch = true;
    private Bundle mSaveInstanceState;
    private ISupportFragment mSupportF;
    /* access modifiers changed from: private */
    public Runnable taskDispatchSupportVisible;

    public VisibleDelegate(ISupportFragment iSupportFragment) {
        this.mSupportF = iSupportFragment;
        this.mFragment = (Fragment) iSupportFragment;
    }

    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.mSaveInstanceState = bundle;
            this.mInvisibleWhenLeave = bundle.getBoolean(FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE);
            this.mFirstCreateViewCompatReplace = bundle.getBoolean(FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean(FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE, this.mInvisibleWhenLeave);
        bundle.putBoolean(FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE, this.mFirstCreateViewCompatReplace);
    }

    public void onActivityCreated(Bundle bundle) {
        if (this.mFirstCreateViewCompatReplace || this.mFragment.getTag() == null || !this.mFragment.getTag().startsWith("android:switcher:")) {
            if (this.mFirstCreateViewCompatReplace) {
                this.mFirstCreateViewCompatReplace = false;
            }
            initVisible();
        }
    }

    private void initVisible() {
        if (!this.mInvisibleWhenLeave && !this.mFragment.isHidden() && this.mFragment.getUserVisibleHint()) {
            if ((this.mFragment.getParentFragment() != null && isFragmentVisible(this.mFragment.getParentFragment())) || this.mFragment.getParentFragment() == null) {
                this.mNeedDispatch = false;
                safeDispatchUserVisibleHint(true);
            }
        }
    }

    public void onResume() {
        if (!this.mIsFirstVisible) {
            if (!this.mIsSupportVisible && !this.mInvisibleWhenLeave && isFragmentVisible(this.mFragment)) {
                this.mNeedDispatch = false;
                dispatchSupportVisible(true);
            }
        } else if (this.mAbortInitVisible) {
            this.mAbortInitVisible = false;
            initVisible();
        }
    }

    public void onPause() {
        if (this.taskDispatchSupportVisible != null) {
            getHandler().removeCallbacks(this.taskDispatchSupportVisible);
            this.mAbortInitVisible = true;
        } else if (!this.mIsSupportVisible || !isFragmentVisible(this.mFragment)) {
            this.mInvisibleWhenLeave = true;
        } else {
            this.mNeedDispatch = false;
            this.mInvisibleWhenLeave = false;
            dispatchSupportVisible(false);
        }
    }

    public void onHiddenChanged(boolean z) {
        if (!z && !this.mFragment.isResumed()) {
            onFragmentShownWhenNotResumed();
        } else if (z) {
            safeDispatchUserVisibleHint(false);
        } else {
            enqueueDispatchVisible();
        }
    }

    private void onFragmentShownWhenNotResumed() {
        this.mInvisibleWhenLeave = false;
        dispatchChildOnFragmentShownWhenNotResumed();
    }

    private void dispatchChildOnFragmentShownWhenNotResumed() {
        List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(this.mFragment.getChildFragmentManager());
        if (activeFragments != null) {
            for (Fragment next : activeFragments) {
                if ((next instanceof ISupportFragment) && !next.isHidden() && next.getUserVisibleHint()) {
                    ((ISupportFragment) next).getSupportDelegate().getVisibleDelegate().onFragmentShownWhenNotResumed();
                }
            }
        }
    }

    public void onDestroyView() {
        this.mIsFirstVisible = true;
    }

    public void setUserVisibleHint(boolean z) {
        if (this.mFragment.isResumed() || (!this.mFragment.isAdded() && z)) {
            boolean z2 = this.mIsSupportVisible;
            if (!z2 && z) {
                safeDispatchUserVisibleHint(true);
            } else if (z2 && !z) {
                dispatchSupportVisible(false);
            }
        }
    }

    private void safeDispatchUserVisibleHint(boolean z) {
        if (!this.mIsFirstVisible) {
            dispatchSupportVisible(z);
        } else if (z) {
            enqueueDispatchVisible();
        }
    }

    private void enqueueDispatchVisible() {
        this.taskDispatchSupportVisible = new Runnable() {
            public void run() {
                Runnable unused = VisibleDelegate.this.taskDispatchSupportVisible = null;
                VisibleDelegate.this.dispatchSupportVisible(true);
            }
        };
        getHandler().post(this.taskDispatchSupportVisible);
    }

    /* access modifiers changed from: private */
    public void dispatchSupportVisible(boolean z) {
        if (z && isParentInvisible()) {
            return;
        }
        if (this.mIsSupportVisible == z) {
            this.mNeedDispatch = true;
            return;
        }
        this.mIsSupportVisible = z;
        if (!z) {
            dispatchChild(false);
            this.mSupportF.onSupportInvisible();
        } else if (!checkAddState()) {
            this.mSupportF.onSupportVisible();
            if (this.mIsFirstVisible) {
                this.mIsFirstVisible = false;
                this.mSupportF.onLazyInitView(this.mSaveInstanceState);
            }
            dispatchChild(true);
        }
    }

    private void dispatchChild(boolean z) {
        List<Fragment> activeFragments;
        if (!this.mNeedDispatch) {
            this.mNeedDispatch = true;
        } else if (!checkAddState() && (activeFragments = FragmentationMagician.getActiveFragments(this.mFragment.getChildFragmentManager())) != null) {
            for (Fragment next : activeFragments) {
                if ((next instanceof ISupportFragment) && !next.isHidden() && next.getUserVisibleHint()) {
                    ((ISupportFragment) next).getSupportDelegate().getVisibleDelegate().dispatchSupportVisible(z);
                }
            }
        }
    }

    private boolean isParentInvisible() {
        Fragment parentFragment = this.mFragment.getParentFragment();
        if (parentFragment instanceof ISupportFragment) {
            return !((ISupportFragment) parentFragment).isSupportVisible();
        }
        if (parentFragment == null || parentFragment.isVisible()) {
            return false;
        }
        return true;
    }

    private boolean checkAddState() {
        if (this.mFragment.isAdded()) {
            return false;
        }
        this.mIsSupportVisible = !this.mIsSupportVisible;
        return true;
    }

    private boolean isFragmentVisible(Fragment fragment) {
        return !fragment.isHidden() && fragment.getUserVisibleHint();
    }

    public boolean isSupportVisible() {
        return this.mIsSupportVisible;
    }

    private Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }
}
