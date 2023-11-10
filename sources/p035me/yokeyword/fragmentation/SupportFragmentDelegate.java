package p035me.yokeyword.fragmentation;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import p035me.yokeyword.fragmentation.ExtraTransaction;
import p035me.yokeyword.fragmentation.anim.FragmentAnimator;
import p035me.yokeyword.fragmentation.helper.internal.AnimatorHelper;
import p035me.yokeyword.fragmentation.helper.internal.ResultRecord;
import p035me.yokeyword.fragmentation.helper.internal.TransactionRecord;
import p035me.yokeyword.fragmentation.helper.internal.VisibleDelegate;

/* renamed from: me.yokeyword.fragmentation.SupportFragmentDelegate */
public class SupportFragmentDelegate {
    private static final long NOT_FOUND_ANIM_TIME = 300;
    static final int STATUS_ROOT_ANIM_DISABLE = 1;
    static final int STATUS_ROOT_ANIM_ENABLE = 2;
    static final int STATUS_UN_ROOT = 0;
    protected FragmentActivity _mActivity;
    boolean mAnimByActivity = true;
    AnimatorHelper mAnimHelper;
    int mContainerId;
    private int mCustomEnterAnim = Integer.MIN_VALUE;
    private int mCustomExitAnim = Integer.MIN_VALUE;
    private int mCustomPopExitAnim = Integer.MIN_VALUE;
    EnterAnimListener mEnterAnimListener;
    private boolean mFirstCreateView = true;
    /* access modifiers changed from: private */
    public Fragment mFragment;
    FragmentAnimator mFragmentAnimator;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private boolean mIsHidden = true;
    private boolean mIsSharedElement;
    boolean mLockAnim;
    Bundle mNewBundle;
    private Runnable mNotifyEnterAnimEndRunnable = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0021, code lost:
            r0 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.access$200(r7.this$0).getView();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r7 = this;
                me.yokeyword.fragmentation.SupportFragmentDelegate r0 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.this
                androidx.fragment.app.Fragment r0 = r0.mFragment
                if (r0 != 0) goto L_0x0009
                return
            L_0x0009:
                me.yokeyword.fragmentation.SupportFragmentDelegate r0 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.this
                me.yokeyword.fragmentation.ISupportFragment r0 = r0.mSupportF
                me.yokeyword.fragmentation.SupportFragmentDelegate r1 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.this
                android.os.Bundle r1 = r1.mSaveInstanceState
                r0.onEnterAnimationEnd(r1)
                me.yokeyword.fragmentation.SupportFragmentDelegate r0 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.this
                boolean r0 = r0.mRootViewClickable
                if (r0 == 0) goto L_0x0021
                return
            L_0x0021:
                me.yokeyword.fragmentation.SupportFragmentDelegate r0 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.this
                androidx.fragment.app.Fragment r0 = r0.mFragment
                android.view.View r0 = r0.getView()
                if (r0 != 0) goto L_0x002e
                return
            L_0x002e:
                me.yokeyword.fragmentation.SupportFragmentDelegate r1 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.this
                androidx.fragment.app.Fragment r1 = r1.mFragment
                me.yokeyword.fragmentation.ISupportFragment r1 = p035me.yokeyword.fragmentation.SupportHelper.getPreFragment(r1)
                if (r1 != 0) goto L_0x003b
                return
            L_0x003b:
                me.yokeyword.fragmentation.SupportFragmentDelegate r1 = r1.getSupportDelegate()
                long r1 = r1.getPopExitAnimDuration()
                me.yokeyword.fragmentation.SupportFragmentDelegate r3 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.this
                long r3 = r3.getEnterAnimDuration()
                me.yokeyword.fragmentation.SupportFragmentDelegate r5 = p035me.yokeyword.fragmentation.SupportFragmentDelegate.this
                android.os.Handler r5 = r5.mHandler
                me.yokeyword.fragmentation.SupportFragmentDelegate$3$1 r6 = new me.yokeyword.fragmentation.SupportFragmentDelegate$3$1
                r6.<init>(r0)
                long r1 = r1 - r3
                r5.postDelayed(r6, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p035me.yokeyword.fragmentation.SupportFragmentDelegate.C24743.run():void");
        }
    };
    private boolean mReplaceMode;
    private int mRootStatus = 0;
    /* access modifiers changed from: private */
    public boolean mRootViewClickable;
    /* access modifiers changed from: private */
    public Bundle mSaveInstanceState;
    /* access modifiers changed from: private */
    public ISupportActivity mSupport;
    /* access modifiers changed from: private */
    public ISupportFragment mSupportF;
    private TransactionDelegate mTransactionDelegate;
    TransactionRecord mTransactionRecord;
    private VisibleDelegate mVisibleDelegate;

    /* renamed from: me.yokeyword.fragmentation.SupportFragmentDelegate$EnterAnimListener */
    interface EnterAnimListener {
        void onEnterAnimStart();
    }

    public boolean onBackPressedSupport() {
        return false;
    }

    public void onEnterAnimationEnd(Bundle bundle) {
    }

    public void onFragmentResult(int i, int i2, Bundle bundle) {
    }

    public void onLazyInitView(Bundle bundle) {
    }

    public void onNewBundle(Bundle bundle) {
    }

    public void onSupportInvisible() {
    }

    public void onSupportVisible() {
    }

    public SupportFragmentDelegate(ISupportFragment iSupportFragment) {
        if (iSupportFragment instanceof Fragment) {
            this.mSupportF = iSupportFragment;
            this.mFragment = (Fragment) iSupportFragment;
            return;
        }
        throw new RuntimeException("Must extends Fragment");
    }

    public ExtraTransaction extraTransaction() {
        TransactionDelegate transactionDelegate = this.mTransactionDelegate;
        if (transactionDelegate != null) {
            return new ExtraTransaction.ExtraTransactionImpl((FragmentActivity) this.mSupport, this.mSupportF, transactionDelegate, false);
        }
        throw new RuntimeException(this.mFragment.getClass().getSimpleName() + " not attach!");
    }

    public void onAttach(Activity activity) {
        if (activity instanceof ISupportActivity) {
            ISupportActivity iSupportActivity = (ISupportActivity) activity;
            this.mSupport = iSupportActivity;
            this._mActivity = (FragmentActivity) activity;
            this.mTransactionDelegate = iSupportActivity.getSupportDelegate().getTransactionDelegate();
            return;
        }
        throw new RuntimeException(activity.getClass().getSimpleName() + " must impl ISupportActivity!");
    }

    public void onCreate(Bundle bundle) {
        getVisibleDelegate().onCreate(bundle);
        Bundle arguments = this.mFragment.getArguments();
        if (arguments != null) {
            this.mRootStatus = arguments.getInt("fragmentation_arg_root_status", 0);
            this.mIsSharedElement = arguments.getBoolean("fragmentation_arg_is_shared_element", false);
            this.mContainerId = arguments.getInt("fragmentation_arg_container");
            this.mReplaceMode = arguments.getBoolean("fragmentation_arg_replace", false);
            this.mCustomEnterAnim = arguments.getInt("fragmentation_arg_custom_enter_anim", Integer.MIN_VALUE);
            this.mCustomExitAnim = arguments.getInt("fragmentation_arg_custom_exit_anim", Integer.MIN_VALUE);
            this.mCustomPopExitAnim = arguments.getInt("fragmentation_arg_custom_pop_exit_anim", Integer.MIN_VALUE);
        }
        if (bundle == null) {
            getFragmentAnimator();
        } else {
            bundle.setClassLoader(getClass().getClassLoader());
            this.mSaveInstanceState = bundle;
            this.mFragmentAnimator = (FragmentAnimator) bundle.getParcelable("fragmentation_state_save_animator");
            this.mIsHidden = bundle.getBoolean("fragmentation_state_save_status");
            this.mContainerId = bundle.getInt("fragmentation_arg_container");
        }
        this.mAnimHelper = new AnimatorHelper(this._mActivity.getApplicationContext(), this.mFragmentAnimator);
        final Animation enterAnim = getEnterAnim();
        if (enterAnim != null) {
            getEnterAnim().setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    SupportFragmentDelegate.this.mSupport.getSupportDelegate().mFragmentClickable = false;
                    SupportFragmentDelegate.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            SupportFragmentDelegate.this.mSupport.getSupportDelegate().mFragmentClickable = true;
                        }
                    }, enterAnim.getDuration());
                }
            });
        }
    }

    public Animation onCreateAnimation(int i, boolean z, int i2) {
        if (this.mSupport.getSupportDelegate().mPopMultipleNoAnim || this.mLockAnim) {
            if (i != 8194 || !z) {
                return this.mAnimHelper.getNoneAnim();
            }
            return this.mAnimHelper.getNoneAnimFixed();
        } else if (i == 4097) {
            if (!z) {
                return this.mAnimHelper.popExitAnim;
            }
            if (this.mRootStatus == 1) {
                return this.mAnimHelper.getNoneAnim();
            }
            Animation animation = this.mAnimHelper.enterAnim;
            fixAnimationListener(animation);
            return animation;
        } else if (i == 8194) {
            AnimatorHelper animatorHelper = this.mAnimHelper;
            return z ? animatorHelper.popEnterAnim : animatorHelper.exitAnim;
        } else {
            if (this.mIsSharedElement && z) {
                compatSharedElements();
            }
            if (!z) {
                return this.mAnimHelper.compatChildFragmentExitAnim(this.mFragment);
            }
            return null;
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        getVisibleDelegate().onSaveInstanceState(bundle);
        bundle.putParcelable("fragmentation_state_save_animator", this.mFragmentAnimator);
        bundle.putBoolean("fragmentation_state_save_status", this.mFragment.isHidden());
        bundle.putInt("fragmentation_arg_container", this.mContainerId);
    }

    public void onActivityCreated(Bundle bundle) {
        getVisibleDelegate().onActivityCreated(bundle);
        View view = this.mFragment.getView();
        if (view != null) {
            this.mRootViewClickable = view.isClickable();
            view.setClickable(true);
            setBackground(view);
        }
        if (bundle != null || this.mRootStatus == 1 || ((this.mFragment.getTag() != null && this.mFragment.getTag().startsWith("android:switcher:")) || (this.mReplaceMode && !this.mFirstCreateView))) {
            notifyEnterAnimEnd();
        } else {
            int i = this.mCustomEnterAnim;
            if (i != Integer.MIN_VALUE) {
                fixAnimationListener(i == 0 ? this.mAnimHelper.getNoneAnim() : AnimationUtils.loadAnimation(this._mActivity, i));
            }
        }
        if (this.mFirstCreateView) {
            this.mFirstCreateView = false;
        }
    }

    public void onResume() {
        getVisibleDelegate().onResume();
    }

    public void onPause() {
        getVisibleDelegate().onPause();
    }

    public void onDestroyView() {
        this.mSupport.getSupportDelegate().mFragmentClickable = true;
        getVisibleDelegate().onDestroyView();
        getHandler().removeCallbacks(this.mNotifyEnterAnimEndRunnable);
    }

    public void onDestroy() {
        this.mTransactionDelegate.handleResultRecord(this.mFragment);
    }

    public void onHiddenChanged(boolean z) {
        getVisibleDelegate().onHiddenChanged(z);
    }

    public void setUserVisibleHint(boolean z) {
        getVisibleDelegate().setUserVisibleHint(z);
    }

    @Deprecated
    public void enqueueAction(Runnable runnable) {
        post(runnable);
    }

    public void post(Runnable runnable) {
        this.mTransactionDelegate.post(runnable);
    }

    public final boolean isSupportVisible() {
        return getVisibleDelegate().isSupportVisible();
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return this.mSupport.getFragmentAnimator();
    }

    public FragmentAnimator getFragmentAnimator() {
        if (this.mSupport != null) {
            if (this.mFragmentAnimator == null) {
                FragmentAnimator onCreateFragmentAnimator = this.mSupportF.onCreateFragmentAnimator();
                this.mFragmentAnimator = onCreateFragmentAnimator;
                if (onCreateFragmentAnimator == null) {
                    this.mFragmentAnimator = this.mSupport.getFragmentAnimator();
                }
            }
            return this.mFragmentAnimator;
        }
        throw new RuntimeException("Fragment has not been attached to Activity!");
    }

    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mFragmentAnimator = fragmentAnimator;
        AnimatorHelper animatorHelper = this.mAnimHelper;
        if (animatorHelper != null) {
            animatorHelper.notifyChanged(fragmentAnimator);
        }
        this.mAnimByActivity = false;
    }

    public void setFragmentResult(int i, Bundle bundle) {
        ResultRecord resultRecord;
        Bundle arguments = this.mFragment.getArguments();
        if (arguments != null && arguments.containsKey("fragment_arg_result_record") && (resultRecord = (ResultRecord) arguments.getParcelable("fragment_arg_result_record")) != null) {
            resultRecord.resultCode = i;
            resultRecord.resultBundle = bundle;
        }
    }

    public void putNewBundle(Bundle bundle) {
        this.mNewBundle = bundle;
    }

    public void hideSoftInput() {
        FragmentActivity activity = this.mFragment.getActivity();
        if (activity != null) {
            SupportHelper.hideSoftInput(activity.getWindow().getDecorView());
        }
    }

    public void showSoftInput(View view) {
        SupportHelper.showSoftInput(view);
    }

    public void loadRootFragment(int i, ISupportFragment iSupportFragment) {
        loadRootFragment(i, iSupportFragment, true, false);
    }

    public void loadRootFragment(int i, ISupportFragment iSupportFragment, boolean z, boolean z2) {
        this.mTransactionDelegate.loadRootTransaction(getChildFragmentManager(), i, iSupportFragment, z, z2);
    }

    public void loadMultipleRootFragment(int i, int i2, ISupportFragment... iSupportFragmentArr) {
        this.mTransactionDelegate.loadMultipleRootTransaction(getChildFragmentManager(), i, i2, iSupportFragmentArr);
    }

    public void showHideFragment(ISupportFragment iSupportFragment) {
        showHideFragment(iSupportFragment, (ISupportFragment) null);
    }

    public void showHideFragment(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        this.mTransactionDelegate.showHideFragment(getChildFragmentManager(), iSupportFragment, iSupportFragment2);
    }

    public void start(ISupportFragment iSupportFragment) {
        start(iSupportFragment, 0);
    }

    public void start(ISupportFragment iSupportFragment, int i) {
        this.mTransactionDelegate.dispatchStartTransaction(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment, 0, i, 0);
    }

    public void startForResult(ISupportFragment iSupportFragment, int i) {
        this.mTransactionDelegate.dispatchStartTransaction(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment, i, 0, 1);
    }

    public void startWithPop(ISupportFragment iSupportFragment) {
        this.mTransactionDelegate.startWithPop(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment);
    }

    public void startWithPopTo(ISupportFragment iSupportFragment, Class<?> cls, boolean z) {
        this.mTransactionDelegate.startWithPopTo(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment, cls.getName(), z);
    }

    public void replaceFragment(ISupportFragment iSupportFragment, boolean z) {
        this.mTransactionDelegate.dispatchStartTransaction(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment, 0, 0, z ? 10 : 11);
    }

    public void startChild(ISupportFragment iSupportFragment) {
        startChild(iSupportFragment, 0);
    }

    public void startChild(ISupportFragment iSupportFragment, int i) {
        this.mTransactionDelegate.dispatchStartTransaction(getChildFragmentManager(), getTopFragment(), iSupportFragment, 0, i, 0);
    }

    public void startChildForResult(ISupportFragment iSupportFragment, int i) {
        this.mTransactionDelegate.dispatchStartTransaction(getChildFragmentManager(), getTopFragment(), iSupportFragment, i, 0, 1);
    }

    public void startChildWithPop(ISupportFragment iSupportFragment) {
        this.mTransactionDelegate.startWithPop(getChildFragmentManager(), getTopFragment(), iSupportFragment);
    }

    public void replaceChildFragment(ISupportFragment iSupportFragment, boolean z) {
        this.mTransactionDelegate.dispatchStartTransaction(getChildFragmentManager(), getTopFragment(), iSupportFragment, 0, 0, z ? 10 : 11);
    }

    public void pop() {
        this.mTransactionDelegate.pop(this.mFragment.getFragmentManager());
    }

    public void popChild() {
        this.mTransactionDelegate.pop(getChildFragmentManager());
    }

    public void popTo(Class<?> cls, boolean z) {
        popTo(cls, z, (Runnable) null);
    }

    public void popTo(Class<?> cls, boolean z, Runnable runnable) {
        popTo(cls, z, runnable, Integer.MAX_VALUE);
    }

    public void popTo(Class<?> cls, boolean z, Runnable runnable, int i) {
        this.mTransactionDelegate.popTo(cls.getName(), z, runnable, this.mFragment.getFragmentManager(), i);
    }

    public void popToChild(Class<?> cls, boolean z) {
        popToChild(cls, z, (Runnable) null);
    }

    public void popToChild(Class<?> cls, boolean z, Runnable runnable) {
        popToChild(cls, z, runnable, Integer.MAX_VALUE);
    }

    public void popToChild(Class<?> cls, boolean z, Runnable runnable, int i) {
        this.mTransactionDelegate.popTo(cls.getName(), z, runnable, getChildFragmentManager(), i);
    }

    public void popQuiet() {
        this.mTransactionDelegate.popQuiet(this.mFragment.getFragmentManager(), this.mFragment);
    }

    private FragmentManager getChildFragmentManager() {
        return this.mFragment.getChildFragmentManager();
    }

    private ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getChildFragmentManager());
    }

    private void fixAnimationListener(Animation animation) {
        getHandler().postDelayed(this.mNotifyEnterAnimEndRunnable, animation.getDuration());
        this.mSupport.getSupportDelegate().mFragmentClickable = true;
        if (this.mEnterAnimListener != null) {
            getHandler().post(new Runnable() {
                public void run() {
                    SupportFragmentDelegate.this.mEnterAnimListener.onEnterAnimStart();
                    SupportFragmentDelegate.this.mEnterAnimListener = null;
                }
            });
        }
    }

    private void compatSharedElements() {
        notifyEnterAnimEnd();
    }

    public void setBackground(View view) {
        if ((this.mFragment.getTag() == null || !this.mFragment.getTag().startsWith("android:switcher:")) && this.mRootStatus == 0 && view.getBackground() == null) {
            int defaultFragmentBackground = this.mSupport.getSupportDelegate().getDefaultFragmentBackground();
            if (defaultFragmentBackground == 0) {
                view.setBackgroundResource(getWindowBackground());
            } else {
                view.setBackgroundResource(defaultFragmentBackground);
            }
        }
    }

    private int getWindowBackground() {
        TypedArray obtainStyledAttributes = this._mActivity.getTheme().obtainStyledAttributes(new int[]{16842836});
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        return resourceId;
    }

    private void notifyEnterAnimEnd() {
        getHandler().post(this.mNotifyEnterAnimEndRunnable);
        this.mSupport.getSupportDelegate().mFragmentClickable = true;
    }

    private Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    public VisibleDelegate getVisibleDelegate() {
        if (this.mVisibleDelegate == null) {
            this.mVisibleDelegate = new VisibleDelegate(this.mSupportF);
        }
        return this.mVisibleDelegate;
    }

    public FragmentActivity getActivity() {
        return this._mActivity;
    }

    private Animation getEnterAnim() {
        int i = this.mCustomEnterAnim;
        if (i == Integer.MIN_VALUE) {
            AnimatorHelper animatorHelper = this.mAnimHelper;
            if (animatorHelper == null || animatorHelper.enterAnim == null) {
                return null;
            }
            return this.mAnimHelper.enterAnim;
        }
        try {
            return AnimationUtils.loadAnimation(this._mActivity, i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    public long getEnterAnimDuration() {
        Animation enterAnim = getEnterAnim();
        if (enterAnim != null) {
            return enterAnim.getDuration();
        }
        return 300;
    }

    public long getExitAnimDuration() {
        int i = this.mCustomExitAnim;
        if (i == Integer.MIN_VALUE) {
            AnimatorHelper animatorHelper = this.mAnimHelper;
            if (animatorHelper == null || animatorHelper.exitAnim == null) {
                return 300;
            }
            return this.mAnimHelper.exitAnim.getDuration();
        }
        try {
            return AnimationUtils.loadAnimation(this._mActivity, i).getDuration();
        } catch (Exception e) {
            e.printStackTrace();
            return 300;
        }
    }

    /* access modifiers changed from: private */
    public long getPopExitAnimDuration() {
        int i = this.mCustomPopExitAnim;
        if (i == Integer.MIN_VALUE) {
            AnimatorHelper animatorHelper = this.mAnimHelper;
            if (animatorHelper == null || animatorHelper.popExitAnim == null) {
                return 300;
            }
            return this.mAnimHelper.popExitAnim.getDuration();
        }
        try {
            return AnimationUtils.loadAnimation(this._mActivity, i).getDuration();
        } catch (Exception e) {
            e.printStackTrace();
            return 300;
        }
    }

    /* access modifiers changed from: package-private */
    public Animation getExitAnim() {
        int i = this.mCustomExitAnim;
        if (i == Integer.MIN_VALUE) {
            AnimatorHelper animatorHelper = this.mAnimHelper;
            if (animatorHelper == null || animatorHelper.exitAnim == null) {
                return null;
            }
            return this.mAnimHelper.exitAnim;
        }
        try {
            return AnimationUtils.loadAnimation(this._mActivity, i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
