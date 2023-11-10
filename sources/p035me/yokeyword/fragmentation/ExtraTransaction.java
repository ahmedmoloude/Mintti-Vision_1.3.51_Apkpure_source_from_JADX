package p035me.yokeyword.fragmentation;

import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import p035me.yokeyword.fragmentation.helper.internal.TransactionRecord;

/* renamed from: me.yokeyword.fragmentation.ExtraTransaction */
public abstract class ExtraTransaction {

    /* renamed from: me.yokeyword.fragmentation.ExtraTransaction$DontAddToBackStackTransaction */
    public interface DontAddToBackStackTransaction {
        void add(ISupportFragment iSupportFragment);

        void replace(ISupportFragment iSupportFragment);

        void start(ISupportFragment iSupportFragment);
    }

    public abstract ExtraTransaction addSharedElement(View view, String str);

    public abstract DontAddToBackStackTransaction dontAddToBackStack();

    public abstract void loadRootFragment(int i, ISupportFragment iSupportFragment);

    public abstract void loadRootFragment(int i, ISupportFragment iSupportFragment, boolean z, boolean z2);

    public abstract void popTo(String str, boolean z);

    public abstract void popTo(String str, boolean z, Runnable runnable, int i);

    public abstract void popToChild(String str, boolean z);

    public abstract void popToChild(String str, boolean z, Runnable runnable, int i);

    public abstract void remove(ISupportFragment iSupportFragment, boolean z);

    public abstract void replace(ISupportFragment iSupportFragment);

    public abstract ExtraTransaction setCustomAnimations(int i, int i2);

    public abstract ExtraTransaction setCustomAnimations(int i, int i2, int i3, int i4);

    public abstract ExtraTransaction setTag(String str);

    public abstract void start(ISupportFragment iSupportFragment);

    public abstract void start(ISupportFragment iSupportFragment, int i);

    public abstract void startDontHideSelf(ISupportFragment iSupportFragment);

    public abstract void startDontHideSelf(ISupportFragment iSupportFragment, int i);

    public abstract void startForResult(ISupportFragment iSupportFragment, int i);

    public abstract void startForResultDontHideSelf(ISupportFragment iSupportFragment, int i);

    public abstract void startWithPop(ISupportFragment iSupportFragment);

    public abstract void startWithPopTo(ISupportFragment iSupportFragment, String str, boolean z);

    /* renamed from: me.yokeyword.fragmentation.ExtraTransaction$ExtraTransactionImpl */
    static final class ExtraTransactionImpl<T extends ISupportFragment> extends ExtraTransaction implements DontAddToBackStackTransaction {
        private FragmentActivity mActivity;
        private Fragment mFragment;
        private boolean mFromActivity;
        private TransactionRecord mRecord = new TransactionRecord();
        private T mSupportF;
        private TransactionDelegate mTransactionDelegate;

        ExtraTransactionImpl(FragmentActivity fragmentActivity, T t, TransactionDelegate transactionDelegate, boolean z) {
            this.mActivity = fragmentActivity;
            this.mSupportF = t;
            this.mFragment = (Fragment) t;
            this.mTransactionDelegate = transactionDelegate;
            this.mFromActivity = z;
        }

        public ExtraTransaction setTag(String str) {
            this.mRecord.tag = str;
            return this;
        }

        public ExtraTransaction setCustomAnimations(int i, int i2) {
            this.mRecord.targetFragmentEnter = i;
            this.mRecord.currentFragmentPopExit = i2;
            this.mRecord.currentFragmentPopEnter = 0;
            this.mRecord.targetFragmentExit = 0;
            return this;
        }

        public ExtraTransaction setCustomAnimations(int i, int i2, int i3, int i4) {
            this.mRecord.targetFragmentEnter = i;
            this.mRecord.currentFragmentPopExit = i2;
            this.mRecord.currentFragmentPopEnter = i3;
            this.mRecord.targetFragmentExit = i4;
            return this;
        }

        public ExtraTransaction addSharedElement(View view, String str) {
            if (this.mRecord.sharedElementList == null) {
                this.mRecord.sharedElementList = new ArrayList<>();
            }
            this.mRecord.sharedElementList.add(new TransactionRecord.SharedElement(view, str));
            return this;
        }

        public void loadRootFragment(int i, ISupportFragment iSupportFragment) {
            loadRootFragment(i, iSupportFragment, true, false);
        }

        public void loadRootFragment(int i, ISupportFragment iSupportFragment, boolean z, boolean z2) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.loadRootTransaction(getFragmentManager(), i, iSupportFragment, z, z2);
        }

        public DontAddToBackStackTransaction dontAddToBackStack() {
            this.mRecord.dontAddToBackStack = true;
            return this;
        }

        public void remove(ISupportFragment iSupportFragment, boolean z) {
            this.mTransactionDelegate.remove(getFragmentManager(), (Fragment) iSupportFragment, z);
        }

        public void popTo(String str, boolean z) {
            popTo(str, z, (Runnable) null, Integer.MAX_VALUE);
        }

        public void popTo(String str, boolean z, Runnable runnable, int i) {
            this.mTransactionDelegate.popTo(str, z, runnable, getFragmentManager(), i);
        }

        public void popToChild(String str, boolean z) {
            popToChild(str, z, (Runnable) null, Integer.MAX_VALUE);
        }

        public void popToChild(String str, boolean z, Runnable runnable, int i) {
            if (this.mFromActivity) {
                popTo(str, z, runnable, i);
                return;
            }
            this.mTransactionDelegate.popTo(str, z, runnable, this.mFragment.getChildFragmentManager(), i);
        }

        public void add(ISupportFragment iSupportFragment) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, 0, 2);
        }

        public void start(ISupportFragment iSupportFragment) {
            start(iSupportFragment, 0);
        }

        public void startDontHideSelf(ISupportFragment iSupportFragment) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, 0, 2);
        }

        public void startDontHideSelf(ISupportFragment iSupportFragment, int i) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, i, 2);
        }

        public void start(ISupportFragment iSupportFragment, int i) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, i, 0);
        }

        public void startForResult(ISupportFragment iSupportFragment, int i) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, i, 0, 1);
        }

        public void startForResultDontHideSelf(ISupportFragment iSupportFragment, int i) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, i, 0, 3);
        }

        public void startWithPop(ISupportFragment iSupportFragment) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.startWithPop(getFragmentManager(), this.mSupportF, iSupportFragment);
        }

        public void startWithPopTo(ISupportFragment iSupportFragment, String str, boolean z) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.startWithPopTo(getFragmentManager(), this.mSupportF, iSupportFragment, str, z);
        }

        public void replace(ISupportFragment iSupportFragment) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, 0, 10);
        }

        private FragmentManager getFragmentManager() {
            Fragment fragment = this.mFragment;
            if (fragment == null) {
                return this.mActivity.getSupportFragmentManager();
            }
            return fragment.getFragmentManager();
        }
    }
}
