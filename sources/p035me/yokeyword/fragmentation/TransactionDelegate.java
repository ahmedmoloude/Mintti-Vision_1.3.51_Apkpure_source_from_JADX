package p035me.yokeyword.fragmentation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentationMagician;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import p035me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning;
import p035me.yokeyword.fragmentation.helper.internal.ResultRecord;
import p035me.yokeyword.fragmentation.helper.internal.TransactionRecord;
import p035me.yokeyword.fragmentation.queue.Action;
import p035me.yokeyword.fragmentation.queue.ActionQueue;

/* renamed from: me.yokeyword.fragmentation.TransactionDelegate */
class TransactionDelegate {
    static final int DEFAULT_POPTO_ANIM = Integer.MAX_VALUE;
    static final String FRAGMENTATION_ARG_CONTAINER = "fragmentation_arg_container";
    static final String FRAGMENTATION_ARG_CUSTOM_ENTER_ANIM = "fragmentation_arg_custom_enter_anim";
    static final String FRAGMENTATION_ARG_CUSTOM_EXIT_ANIM = "fragmentation_arg_custom_exit_anim";
    static final String FRAGMENTATION_ARG_CUSTOM_POP_EXIT_ANIM = "fragmentation_arg_custom_pop_exit_anim";
    static final String FRAGMENTATION_ARG_IS_SHARED_ELEMENT = "fragmentation_arg_is_shared_element";
    static final String FRAGMENTATION_ARG_REPLACE = "fragmentation_arg_replace";
    static final String FRAGMENTATION_ARG_RESULT_RECORD = "fragment_arg_result_record";
    static final String FRAGMENTATION_ARG_ROOT_STATUS = "fragmentation_arg_root_status";
    static final String FRAGMENTATION_STATE_SAVE_ANIMATOR = "fragmentation_state_save_animator";
    static final String FRAGMENTATION_STATE_SAVE_IS_HIDDEN = "fragmentation_state_save_status";
    private static final String FRAGMENTATION_STATE_SAVE_RESULT = "fragmentation_state_save_result";
    private static final String TAG = "Fragmentation";
    static final int TYPE_ADD = 0;
    static final int TYPE_ADD_RESULT = 1;
    static final int TYPE_ADD_RESULT_WITHOUT_HIDE = 3;
    static final int TYPE_ADD_WITHOUT_HIDE = 2;
    static final int TYPE_REPLACE = 10;
    static final int TYPE_REPLACE_DONT_BACK = 11;
    ActionQueue mActionQueue;
    private FragmentActivity mActivity;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public ISupportActivity mSupport;

    TransactionDelegate(ISupportActivity iSupportActivity) {
        this.mSupport = iSupportActivity;
        this.mActivity = (FragmentActivity) iSupportActivity;
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mActionQueue = new ActionQueue(handler);
    }

    /* access modifiers changed from: package-private */
    public void post(final Runnable runnable) {
        this.mActionQueue.enqueue(new Action() {
            public void run() {
                runnable.run();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void loadRootTransaction(FragmentManager fragmentManager, int i, ISupportFragment iSupportFragment, boolean z, boolean z2) {
        final int i2 = i;
        final ISupportFragment iSupportFragment2 = iSupportFragment;
        final FragmentManager fragmentManager2 = fragmentManager;
        final boolean z3 = z;
        final boolean z4 = z2;
        enqueue(fragmentManager, new Action(4) {
            public void run() {
                TransactionDelegate.this.bindContainerId(i2, iSupportFragment2);
                String name = iSupportFragment2.getClass().getName();
                TransactionRecord transactionRecord = iSupportFragment2.getSupportDelegate().mTransactionRecord;
                if (!(transactionRecord == null || transactionRecord.tag == null)) {
                    name = transactionRecord.tag;
                }
                TransactionDelegate.this.start(fragmentManager2, (ISupportFragment) null, iSupportFragment2, name, !z3, (ArrayList<TransactionRecord.SharedElement>) null, z4, 10);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void loadMultipleRootTransaction(FragmentManager fragmentManager, int i, int i2, ISupportFragment... iSupportFragmentArr) {
        final FragmentManager fragmentManager2 = fragmentManager;
        final ISupportFragment[] iSupportFragmentArr2 = iSupportFragmentArr;
        final int i3 = i;
        final int i4 = i2;
        enqueue(fragmentManager, new Action(4) {
            public void run() {
                FragmentTransaction beginTransaction = fragmentManager2.beginTransaction();
                int i = 0;
                while (true) {
                    ISupportFragment[] iSupportFragmentArr = iSupportFragmentArr2;
                    if (i < iSupportFragmentArr.length) {
                        Fragment fragment = (Fragment) iSupportFragmentArr[i];
                        TransactionDelegate.this.getArguments(fragment).putInt(TransactionDelegate.FRAGMENTATION_ARG_ROOT_STATUS, 1);
                        TransactionDelegate.this.bindContainerId(i3, iSupportFragmentArr2[i]);
                        beginTransaction.add(i3, fragment, fragment.getClass().getName());
                        if (i != i4) {
                            beginTransaction.hide(fragment);
                        }
                        i++;
                    } else {
                        TransactionDelegate.this.supportCommit(fragmentManager2, beginTransaction);
                        return;
                    }
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void dispatchStartTransaction(FragmentManager fragmentManager, ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2, int i, int i2, int i3) {
        final FragmentManager fragmentManager2 = fragmentManager;
        final ISupportFragment iSupportFragment3 = iSupportFragment;
        final ISupportFragment iSupportFragment4 = iSupportFragment2;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        FragmentManager fragmentManager3 = fragmentManager;
        enqueue(fragmentManager, new Action(i2 == 2 ? 2 : 0) {
            public void run() {
                TransactionDelegate.this.doDispatchStartTransaction(fragmentManager2, iSupportFragment3, iSupportFragment4, i4, i5, i6);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void showHideFragment(final FragmentManager fragmentManager, final ISupportFragment iSupportFragment, final ISupportFragment iSupportFragment2) {
        enqueue(fragmentManager, new Action() {
            public void run() {
                TransactionDelegate.this.doShowHideFragment(fragmentManager, iSupportFragment, iSupportFragment2);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void startWithPop(FragmentManager fragmentManager, ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        final ISupportFragment iSupportFragment3 = iSupportFragment;
        final FragmentManager fragmentManager2 = fragmentManager;
        final ISupportFragment iSupportFragment4 = iSupportFragment2;
        FragmentManager fragmentManager3 = fragmentManager;
        enqueue(fragmentManager, new Action(2) {
            public void run() {
                ISupportFragment access$600 = TransactionDelegate.this.getTopFragmentForStart(iSupportFragment3, fragmentManager2);
                Objects.requireNonNull(access$600, "There is no Fragment in the FragmentManager, maybe you need to call loadRootFragment() first!");
                TransactionDelegate.this.bindContainerId(access$600.getSupportDelegate().mContainerId, iSupportFragment4);
                TransactionDelegate.this.handleAfterSaveInStateTransactionException(fragmentManager2, "popTo()");
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager2);
                access$600.getSupportDelegate().mLockAnim = true;
                if (!FragmentationMagician.isStateSaved(fragmentManager2)) {
                    TransactionDelegate.this.mockStartWithPopAnim(SupportHelper.getTopFragment(fragmentManager2), iSupportFragment4, access$600.getSupportDelegate().mAnimHelper.popExitAnim);
                }
                TransactionDelegate.this.removeTopFragment(fragmentManager2);
                FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager2);
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager2);
            }
        });
        dispatchStartTransaction(fragmentManager, iSupportFragment, iSupportFragment2, 0, 0, 0);
    }

    /* access modifiers changed from: package-private */
    public void startWithPopTo(FragmentManager fragmentManager, ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2, String str, boolean z) {
        final boolean z2 = z;
        final FragmentManager fragmentManager2 = fragmentManager;
        final String str2 = str;
        final ISupportFragment iSupportFragment3 = iSupportFragment;
        final ISupportFragment iSupportFragment4 = iSupportFragment2;
        enqueue(fragmentManager, new Action(2) {
            public void run() {
                boolean z = z2;
                List<Fragment> willPopFragments = SupportHelper.getWillPopFragments(fragmentManager2, str2, z);
                ISupportFragment access$600 = TransactionDelegate.this.getTopFragmentForStart(iSupportFragment3, fragmentManager2);
                Objects.requireNonNull(access$600, "There is no Fragment in the FragmentManager, maybe you need to call loadRootFragment() first!");
                TransactionDelegate.this.bindContainerId(access$600.getSupportDelegate().mContainerId, iSupportFragment4);
                if (willPopFragments.size() > 0) {
                    TransactionDelegate.this.handleAfterSaveInStateTransactionException(fragmentManager2, "startWithPopTo()");
                    FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager2);
                    if (!FragmentationMagician.isStateSaved(fragmentManager2)) {
                        TransactionDelegate.this.mockStartWithPopAnim(SupportHelper.getTopFragment(fragmentManager2), iSupportFragment4, access$600.getSupportDelegate().mAnimHelper.popExitAnim);
                    }
                    TransactionDelegate.this.safePopTo(str2, fragmentManager2, z ? 1 : 0, willPopFragments);
                }
            }
        });
        dispatchStartTransaction(fragmentManager, iSupportFragment, iSupportFragment2, 0, 0, 0);
    }

    /* access modifiers changed from: package-private */
    public void remove(FragmentManager fragmentManager, Fragment fragment, boolean z) {
        final FragmentManager fragmentManager2 = fragmentManager;
        final Fragment fragment2 = fragment;
        final boolean z2 = z;
        enqueue(fragmentManager, new Action(1, fragmentManager) {
            public void run() {
                FragmentTransaction remove = fragmentManager2.beginTransaction().setTransition(8194).remove(fragment2);
                if (z2) {
                    ISupportFragment preFragment = SupportHelper.getPreFragment(fragment2);
                    if (preFragment instanceof Fragment) {
                        remove.show((Fragment) preFragment);
                    }
                }
                TransactionDelegate.this.supportCommit(fragmentManager2, remove);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void pop(final FragmentManager fragmentManager) {
        enqueue(fragmentManager, new Action(1, fragmentManager) {
            public void run() {
                TransactionDelegate.this.handleAfterSaveInStateTransactionException(fragmentManager, "pop()");
                FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager);
                TransactionDelegate.this.removeTopFragment(fragmentManager);
            }
        });
    }

    /* access modifiers changed from: private */
    public void removeTopFragment(FragmentManager fragmentManager) {
        try {
            ISupportFragment backStackTopFragment = SupportHelper.getBackStackTopFragment(fragmentManager);
            if (backStackTopFragment != null) {
                fragmentManager.beginTransaction().setTransition(8194).remove((Fragment) backStackTopFragment).commitAllowingStateLoss();
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void popQuiet(final FragmentManager fragmentManager, final Fragment fragment) {
        enqueue(fragmentManager, new Action(2) {
            public void run() {
                TransactionDelegate.this.mSupport.getSupportDelegate().mPopMultipleNoAnim = true;
                TransactionDelegate.this.removeTopFragment(fragmentManager);
                FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager, fragment.getTag(), 0);
                FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager);
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
                TransactionDelegate.this.mSupport.getSupportDelegate().mPopMultipleNoAnim = false;
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void popTo(String str, boolean z, Runnable runnable, FragmentManager fragmentManager, int i) {
        final String str2 = str;
        final boolean z2 = z;
        final FragmentManager fragmentManager2 = fragmentManager;
        final int i2 = i;
        final Runnable runnable2 = runnable;
        enqueue(fragmentManager, new Action(2) {
            public void run() {
                TransactionDelegate.this.doPopTo(str2, z2, fragmentManager2, i2);
                Runnable runnable = runnable2;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchBackPressedEvent(ISupportFragment iSupportFragment) {
        if (iSupportFragment == null) {
            return false;
        }
        if (!iSupportFragment.onBackPressedSupport() && !dispatchBackPressedEvent((ISupportFragment) ((Fragment) iSupportFragment).getParentFragment())) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void handleResultRecord(Fragment fragment) {
        ResultRecord resultRecord;
        try {
            Bundle arguments = fragment.getArguments();
            if (arguments != null && (resultRecord = (ResultRecord) arguments.getParcelable(FRAGMENTATION_ARG_RESULT_RECORD)) != null) {
                ((ISupportFragment) fragment.getFragmentManager().getFragment(fragment.getArguments(), FRAGMENTATION_STATE_SAVE_RESULT)).onFragmentResult(resultRecord.requestCode, resultRecord.resultCode, resultRecord.resultBundle);
            }
        } catch (IllegalStateException unused) {
        }
    }

    private void enqueue(FragmentManager fragmentManager, Action action) {
        if (fragmentManager == null) {
            Log.w(TAG, "FragmentManager is null, skip the action!");
        } else {
            this.mActionQueue.enqueue(action);
        }
    }

    /* access modifiers changed from: private */
    public void doDispatchStartTransaction(FragmentManager fragmentManager, ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2, int i, int i2, int i3) {
        ArrayList<TransactionRecord.SharedElement> arrayList;
        boolean z;
        String str;
        FragmentManager fragmentManager2 = fragmentManager;
        ISupportFragment iSupportFragment3 = iSupportFragment;
        ISupportFragment iSupportFragment4 = iSupportFragment2;
        int i4 = i3;
        checkNotNull(iSupportFragment4, "toFragment == null");
        if ((i4 == 1 || i4 == 3) && iSupportFragment3 != null) {
            Fragment fragment = (Fragment) iSupportFragment3;
            if (!fragment.isAdded()) {
                Log.w(TAG, fragment.getClass().getSimpleName() + " has not been attached yet! startForResult() converted to start()");
            } else {
                saveRequestCode(fragmentManager2, fragment, (Fragment) iSupportFragment4, i);
            }
        }
        ISupportFragment topFragmentForStart = getTopFragmentForStart(iSupportFragment3, fragmentManager2);
        int i5 = getArguments((Fragment) iSupportFragment4).getInt(FRAGMENTATION_ARG_CONTAINER, 0);
        if (topFragmentForStart == null && i5 == 0) {
            Log.e(TAG, "There is no Fragment in the FragmentManager, maybe you need to call loadRootFragment()!");
            return;
        }
        if (topFragmentForStart != null && i5 == 0) {
            bindContainerId(topFragmentForStart.getSupportDelegate().mContainerId, iSupportFragment4);
        }
        String name = iSupportFragment2.getClass().getName();
        ArrayList<TransactionRecord.SharedElement> arrayList2 = null;
        TransactionRecord transactionRecord = iSupportFragment2.getSupportDelegate().mTransactionRecord;
        if (transactionRecord != null) {
            if (transactionRecord.tag != null) {
                name = transactionRecord.tag;
            }
            boolean z2 = transactionRecord.dontAddToBackStack;
            if (transactionRecord.sharedElementList != null) {
                arrayList2 = transactionRecord.sharedElementList;
            }
            str = name;
            arrayList = arrayList2;
            z = z2;
        } else {
            str = name;
            arrayList = null;
            z = false;
        }
        if (!handleLaunchMode(fragmentManager, topFragmentForStart, iSupportFragment2, str, i2)) {
            start(fragmentManager, topFragmentForStart, iSupportFragment2, str, z, arrayList, false, i3);
        }
    }

    /* access modifiers changed from: private */
    public ISupportFragment getTopFragmentForStart(ISupportFragment iSupportFragment, FragmentManager fragmentManager) {
        if (iSupportFragment == null) {
            return SupportHelper.getTopFragment(fragmentManager);
        }
        if (iSupportFragment.getSupportDelegate().mContainerId == 0) {
            Fragment fragment = (Fragment) iSupportFragment;
            if (fragment.getTag() != null && !fragment.getTag().startsWith("android:switcher:")) {
                throw new IllegalStateException("Can't find container, please call loadRootFragment() first!");
            }
        }
        return SupportHelper.getTopFragment(fragmentManager, iSupportFragment.getSupportDelegate().mContainerId);
    }

    /* access modifiers changed from: private */
    public void start(FragmentManager fragmentManager, ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2, String str, boolean z, ArrayList<TransactionRecord.SharedElement> arrayList, boolean z2, int i) {
        String str2 = str;
        int i2 = i;
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        boolean z3 = i2 == 0 || i2 == 1 || i2 == 2 || i2 == 3;
        Fragment fragment = (Fragment) iSupportFragment;
        Fragment fragment2 = (Fragment) iSupportFragment2;
        Bundle arguments = getArguments(fragment2);
        arguments.putBoolean(FRAGMENTATION_ARG_REPLACE, !z3);
        if (arrayList != null) {
            arguments.putBoolean(FRAGMENTATION_ARG_IS_SHARED_ELEMENT, true);
            Iterator<TransactionRecord.SharedElement> it = arrayList.iterator();
            while (it.hasNext()) {
                TransactionRecord.SharedElement next = it.next();
                beginTransaction.addSharedElement(next.sharedElement, next.sharedName);
            }
        } else if (z3) {
            TransactionRecord transactionRecord = iSupportFragment2.getSupportDelegate().mTransactionRecord;
            if (transactionRecord == null || transactionRecord.targetFragmentEnter == Integer.MIN_VALUE) {
                beginTransaction.setTransition(4097);
            } else {
                beginTransaction.setCustomAnimations(transactionRecord.targetFragmentEnter, transactionRecord.currentFragmentPopExit, transactionRecord.currentFragmentPopEnter, transactionRecord.targetFragmentExit);
                arguments.putInt(FRAGMENTATION_ARG_CUSTOM_ENTER_ANIM, transactionRecord.targetFragmentEnter);
                arguments.putInt(FRAGMENTATION_ARG_CUSTOM_EXIT_ANIM, transactionRecord.targetFragmentExit);
                arguments.putInt(FRAGMENTATION_ARG_CUSTOM_POP_EXIT_ANIM, transactionRecord.currentFragmentPopExit);
            }
        } else {
            arguments.putInt(FRAGMENTATION_ARG_ROOT_STATUS, 1);
        }
        if (iSupportFragment == null) {
            beginTransaction.replace(arguments.getInt(FRAGMENTATION_ARG_CONTAINER), fragment2, str2);
            if (!z3) {
                beginTransaction.setTransition(4097);
                arguments.putInt(FRAGMENTATION_ARG_ROOT_STATUS, z2 ? 2 : 1);
            }
        } else if (z3) {
            beginTransaction.add(iSupportFragment.getSupportDelegate().mContainerId, fragment2, str2);
            if (!(i2 == 2 || i2 == 3)) {
                beginTransaction.hide(fragment);
            }
        } else {
            beginTransaction.replace(iSupportFragment.getSupportDelegate().mContainerId, fragment2, str2);
        }
        if (!z && i2 != 11) {
            beginTransaction.addToBackStack(str2);
        }
        supportCommit(fragmentManager, beginTransaction);
    }

    /* access modifiers changed from: private */
    public void doShowHideFragment(FragmentManager fragmentManager, ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        if (iSupportFragment != iSupportFragment2) {
            FragmentTransaction show = fragmentManager.beginTransaction().show((Fragment) iSupportFragment);
            if (iSupportFragment2 == null) {
                List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(fragmentManager);
                if (activeFragments != null) {
                    for (Fragment next : activeFragments) {
                        if (!(next == null || next == iSupportFragment)) {
                            show.hide(next);
                        }
                    }
                }
            } else {
                show.hide((Fragment) iSupportFragment2);
            }
            supportCommit(fragmentManager, show);
        }
    }

    /* access modifiers changed from: private */
    public void bindContainerId(int i, ISupportFragment iSupportFragment) {
        getArguments((Fragment) iSupportFragment).putInt(FRAGMENTATION_ARG_CONTAINER, i);
    }

    /* access modifiers changed from: private */
    public Bundle getArguments(Fragment fragment) {
        Bundle arguments = fragment.getArguments();
        if (arguments != null) {
            return arguments;
        }
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return bundle;
    }

    /* access modifiers changed from: private */
    public void supportCommit(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        handleAfterSaveInStateTransactionException(fragmentManager, "commit()");
        fragmentTransaction.commitAllowingStateLoss();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0004, code lost:
        r1 = p035me.yokeyword.fragmentation.SupportHelper.findBackStackFragment(r6.getClass(), r7, r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean handleLaunchMode(androidx.fragment.app.FragmentManager r4, p035me.yokeyword.fragmentation.ISupportFragment r5, final p035me.yokeyword.fragmentation.ISupportFragment r6, java.lang.String r7, int r8) {
        /*
            r3 = this;
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.Class r1 = r6.getClass()
            me.yokeyword.fragmentation.ISupportFragment r1 = p035me.yokeyword.fragmentation.SupportHelper.findBackStackFragment(r1, r7, r4)
            if (r1 != 0) goto L_0x000f
            return r0
        L_0x000f:
            r2 = 1
            if (r8 != r2) goto L_0x002e
            if (r6 == r5) goto L_0x002a
            java.lang.Class r4 = r6.getClass()
            java.lang.String r4 = r4.getName()
            java.lang.Class r5 = r5.getClass()
            java.lang.String r5 = r5.getName()
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0042
        L_0x002a:
            r3.handleNewBundle(r6, r1)
            return r2
        L_0x002e:
            r5 = 2
            if (r8 != r5) goto L_0x0042
            r5 = 2147483647(0x7fffffff, float:NaN)
            r3.doPopTo(r7, r0, r4, r5)
            android.os.Handler r4 = r3.mHandler
            me.yokeyword.fragmentation.TransactionDelegate$12 r5 = new me.yokeyword.fragmentation.TransactionDelegate$12
            r5.<init>(r6, r1)
            r4.post(r5)
            return r2
        L_0x0042:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p035me.yokeyword.fragmentation.TransactionDelegate.handleLaunchMode(androidx.fragment.app.FragmentManager, me.yokeyword.fragmentation.ISupportFragment, me.yokeyword.fragmentation.ISupportFragment, java.lang.String, int):boolean");
    }

    /* access modifiers changed from: private */
    public void handleNewBundle(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        Bundle bundle = iSupportFragment.getSupportDelegate().mNewBundle;
        Bundle arguments = getArguments((Fragment) iSupportFragment);
        if (arguments.containsKey(FRAGMENTATION_ARG_CONTAINER)) {
            arguments.remove(FRAGMENTATION_ARG_CONTAINER);
        }
        if (bundle != null) {
            arguments.putAll(bundle);
        }
        iSupportFragment2.onNewBundle(arguments);
    }

    private void saveRequestCode(FragmentManager fragmentManager, Fragment fragment, Fragment fragment2, int i) {
        Bundle arguments = getArguments(fragment2);
        ResultRecord resultRecord = new ResultRecord();
        resultRecord.requestCode = i;
        arguments.putParcelable(FRAGMENTATION_ARG_RESULT_RECORD, resultRecord);
        fragmentManager.putFragment(arguments, FRAGMENTATION_STATE_SAVE_RESULT, fragment);
    }

    /* access modifiers changed from: private */
    public void doPopTo(String str, boolean z, FragmentManager fragmentManager, int i) {
        handleAfterSaveInStateTransactionException(fragmentManager, "popTo()");
        if (fragmentManager.findFragmentByTag(str) == null) {
            Log.e(TAG, "Pop failure! Can't find FragmentTag:" + str + " in the FragmentManager's Stack.");
            return;
        }
        List<Fragment> willPopFragments = SupportHelper.getWillPopFragments(fragmentManager, str, z);
        if (willPopFragments.size() > 0) {
            mockPopToAnim(willPopFragments.get(0), str, fragmentManager, z ? 1 : 0, willPopFragments, i);
        }
    }

    /* access modifiers changed from: private */
    public void safePopTo(String str, FragmentManager fragmentManager, int i, List<Fragment> list) {
        this.mSupport.getSupportDelegate().mPopMultipleNoAnim = true;
        FragmentTransaction transition = fragmentManager.beginTransaction().setTransition(8194);
        for (Fragment remove : list) {
            transition.remove(remove);
        }
        transition.commitAllowingStateLoss();
        FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager, str, i);
        FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
        this.mSupport.getSupportDelegate().mPopMultipleNoAnim = false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0018, code lost:
        r4 = r4.getView();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void mockPopToAnim(androidx.fragment.app.Fragment r4, java.lang.String r5, androidx.fragment.app.FragmentManager r6, int r7, java.util.List<androidx.fragment.app.Fragment> r8, int r9) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof p035me.yokeyword.fragmentation.ISupportFragment
            if (r0 != 0) goto L_0x0008
            r3.safePopTo(r5, r6, r7, r8)
            return
        L_0x0008:
            r0 = r4
            me.yokeyword.fragmentation.ISupportFragment r0 = (p035me.yokeyword.fragmentation.ISupportFragment) r0
            me.yokeyword.fragmentation.SupportFragmentDelegate r1 = r0.getSupportDelegate()
            int r1 = r1.mContainerId
            android.view.ViewGroup r1 = r3.findContainerById(r4, r1)
            if (r1 != 0) goto L_0x0018
            return
        L_0x0018:
            android.view.View r4 = r4.getView()
            if (r4 != 0) goto L_0x001f
            return
        L_0x001f:
            r1.removeViewInLayout(r4)
            android.view.ViewGroup r2 = r3.addMockView(r4, r1)
            r3.safePopTo(r5, r6, r7, r8)
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r9 != r5) goto L_0x003e
            me.yokeyword.fragmentation.SupportFragmentDelegate r5 = r0.getSupportDelegate()
            android.view.animation.Animation r5 = r5.getExitAnim()
            if (r5 != 0) goto L_0x004c
            me.yokeyword.fragmentation.TransactionDelegate$13 r5 = new me.yokeyword.fragmentation.TransactionDelegate$13
            r5.<init>()
            goto L_0x004c
        L_0x003e:
            if (r9 != 0) goto L_0x0046
            me.yokeyword.fragmentation.TransactionDelegate$14 r5 = new me.yokeyword.fragmentation.TransactionDelegate$14
            r5.<init>()
            goto L_0x004c
        L_0x0046:
            androidx.fragment.app.FragmentActivity r5 = r3.mActivity
            android.view.animation.Animation r5 = android.view.animation.AnimationUtils.loadAnimation(r5, r9)
        L_0x004c:
            r4.startAnimation(r5)
            android.os.Handler r6 = r3.mHandler
            me.yokeyword.fragmentation.TransactionDelegate$15 r7 = new me.yokeyword.fragmentation.TransactionDelegate$15
            r7.<init>(r2, r4, r1)
            long r4 = r5.getDuration()
            r6.postDelayed(r7, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p035me.yokeyword.fragmentation.TransactionDelegate.mockPopToAnim(androidx.fragment.app.Fragment, java.lang.String, androidx.fragment.app.FragmentManager, int, java.util.List, int):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0010, code lost:
        r3 = r0.getView();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mockStartWithPopAnim(p035me.yokeyword.fragmentation.ISupportFragment r8, p035me.yokeyword.fragmentation.ISupportFragment r9, android.view.animation.Animation r10) {
        /*
            r7 = this;
            r0 = r8
            androidx.fragment.app.Fragment r0 = (androidx.fragment.app.Fragment) r0
            me.yokeyword.fragmentation.SupportFragmentDelegate r8 = r8.getSupportDelegate()
            int r8 = r8.mContainerId
            android.view.ViewGroup r6 = r7.findContainerById(r0, r8)
            if (r6 != 0) goto L_0x0010
            return
        L_0x0010:
            android.view.View r3 = r0.getView()
            if (r3 != 0) goto L_0x0017
            return
        L_0x0017:
            r6.removeViewInLayout(r3)
            android.view.ViewGroup r5 = r7.addMockView(r3, r6)
            me.yokeyword.fragmentation.SupportFragmentDelegate r8 = r9.getSupportDelegate()
            me.yokeyword.fragmentation.TransactionDelegate$16 r9 = new me.yokeyword.fragmentation.TransactionDelegate$16
            r1 = r9
            r2 = r7
            r4 = r10
            r1.<init>(r3, r4, r5, r6)
            r8.mEnterAnimListener = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p035me.yokeyword.fragmentation.TransactionDelegate.mockStartWithPopAnim(me.yokeyword.fragmentation.ISupportFragment, me.yokeyword.fragmentation.ISupportFragment, android.view.animation.Animation):void");
    }

    private ViewGroup addMockView(View view, ViewGroup viewGroup) {
        C248617 r0 = new ViewGroup(this.mActivity) {
            /* access modifiers changed from: protected */
            public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            }
        };
        r0.addView(view);
        viewGroup.addView(r0);
        return r0;
    }

    private ViewGroup findContainerById(Fragment fragment, int i) {
        Object obj;
        if (fragment.getView() == null) {
            return null;
        }
        Fragment parentFragment = fragment.getParentFragment();
        if (parentFragment == null) {
            obj = this.mActivity.findViewById(i);
        } else if (parentFragment.getView() != null) {
            obj = parentFragment.getView().findViewById(i);
        } else {
            obj = findContainerById(parentFragment, i);
        }
        if (obj instanceof ViewGroup) {
            return (ViewGroup) obj;
        }
        return null;
    }

    private static <T> void checkNotNull(T t, String str) {
        Objects.requireNonNull(t, str);
    }

    /* access modifiers changed from: private */
    public void handleAfterSaveInStateTransactionException(FragmentManager fragmentManager, String str) {
        if (FragmentationMagician.isStateSaved(fragmentManager)) {
            AfterSaveStateTransactionWarning afterSaveStateTransactionWarning = new AfterSaveStateTransactionWarning(str);
            if (Fragmentation.getDefault().getHandler() != null) {
                Fragmentation.getDefault().getHandler().onException(afterSaveStateTransactionWarning);
            }
        }
    }
}
