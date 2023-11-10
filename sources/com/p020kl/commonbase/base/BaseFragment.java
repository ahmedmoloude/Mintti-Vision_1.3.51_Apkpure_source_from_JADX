package com.p020kl.commonbase.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.gyf.immersionbar.ImmersionBar;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.views.CommonDialog;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p035me.yokeyword.fragmentation.SupportFragment;
import p035me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import p035me.yokeyword.fragmentation.anim.FragmentAnimator;

/* renamed from: com.kl.commonbase.base.BaseFragment */
public abstract class BaseFragment extends SupportFragment {
    protected String TAG = getClass().getSimpleName();
    protected boolean isViewBind = false;
    protected Activity mActivity;
    protected CommonDialog mHintDialog;
    ImageView mIvRight;
    LinearLayout mLLRight;
    LinearLayout mLlBack;
    protected ProgressDialog mProgressDialog;
    TextView mToolbarTitle;
    TextView mTvRight;
    private Unbinder mUnbinder;

    /* access modifiers changed from: protected */
    public String getRigthText() {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean isEventBusRegister() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isImmersionBarEnabled() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isShowRightImg() {
        return false;
    }

    public abstract void onBindView(Bundle bundle, View view);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Event event) {
    }

    /* access modifiers changed from: protected */
    public void onRightClicked() {
    }

    public abstract Object setLayout();

    /* access modifiers changed from: protected */
    public int setRightImage() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int setStatusBarView() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int setTitleBar() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return null;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isEventBusRegister() && !EventBusUtil.isRegister(this)) {
            EventBusUtil.register(this);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view;
        if (setLayout() instanceof Integer) {
            view = layoutInflater.inflate(((Integer) setLayout()).intValue(), viewGroup, false);
        } else if (setLayout() instanceof View) {
            view = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        this.mUnbinder = ButterKnife.bind((Object) this, view);
        this.isViewBind = true;
        onBindView(bundle, view);
        initView(view);
        return view;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        View findViewById = view.findViewById(setTitleBar());
        ImmersionBar.setTitleBar(this.mActivity, findViewById);
        View findViewById2 = view.findViewById(setStatusBarView());
        ImmersionBar.setStatusBarView(this.mActivity, findViewById2);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mLlBack = (LinearLayout) view.findViewById(C1544R.C1548id.ll_back);
        this.mToolbarTitle = (TextView) view.findViewById(C1544R.C1548id.tv_toolbar_title);
        this.mLLRight = (LinearLayout) view.findViewById(C1544R.C1548id.ll_right);
        this.mIvRight = (ImageView) view.findViewById(C1544R.C1548id.iv_right);
        this.mTvRight = (TextView) view.findViewById(C1544R.C1548id.tv_right);
        if (this.mLlBack != null) {
            if (isShowBack()) {
                this.mLlBack.setVisibility(0);
                this.mLlBack.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        BaseFragment.this.backClick();
                    }
                });
            } else {
                this.mLlBack.setVisibility(8);
            }
        }
        if (this.mToolbarTitle != null) {
            if (setToolbarTitle() == null) {
                this.mToolbarTitle.setVisibility(8);
            } else if (setToolbarTitle() instanceof String) {
                this.mToolbarTitle.setText((String) setToolbarTitle());
            } else if (setToolbarTitle() instanceof Integer) {
                this.mToolbarTitle.setText(((Integer) setToolbarTitle()).intValue());
            }
        }
        if (this.mLLRight == null) {
            return;
        }
        if (!isShowRightImg()) {
            this.mLLRight.setVisibility(8);
        } else if (setRightImage() != 0) {
            this.mIvRight.setImageResource(setRightImage());
            this.mLLRight.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    BaseFragment.this.onRightClicked();
                }
            });
        } else {
            this.mLLRight.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    public void backClick() {
        pop();
    }

    /* access modifiers changed from: protected */
    public void showRightText() {
        this.mIvRight.setVisibility(8);
        this.mLLRight.setVisibility(0);
        this.mTvRight.setVisibility(0);
        this.mTvRight.setText(getRigthText());
        this.mTvRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BaseFragment.this.onRightClicked();
            }
        });
    }

    /* access modifiers changed from: protected */
    public String getTitle() {
        return (String) this.mToolbarTitle.getText();
    }

    /* access modifiers changed from: protected */
    public void showProgressDialog(String str, boolean z) {
        if (!TextUtils.isEmpty(str) && isAdded()) {
            disProgressDialog();
            getActivity().runOnUiThread(new Runnable(str, z) {
                public final /* synthetic */ String f$1;
                public final /* synthetic */ boolean f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    BaseFragment.this.lambda$showProgressDialog$0$BaseFragment(this.f$1, this.f$2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$showProgressDialog$0$BaseFragment(String str, boolean z) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        this.mProgressDialog = progressDialog;
        progressDialog.getWindow().clearFlags(2);
        this.mProgressDialog.setMessage(str);
        this.mProgressDialog.setCanceledOnTouchOutside(z);
        this.mProgressDialog.show();
    }

    /* access modifiers changed from: protected */
    public void disProgressDialog() {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    /* access modifiers changed from: protected */
    public void showHint(Object obj) {
        getActivity().runOnUiThread(new Runnable(obj) {
            public final /* synthetic */ Object f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                BaseFragment.this.lambda$showHint$1$BaseFragment(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$showHint$1$BaseFragment(Object obj) {
        String str;
        if (this.mHintDialog == null) {
            this.mHintDialog = new CommonDialog.Builder(getContext()).setCanceledOnTouchOutside(false).setHeight(-2).setWidth(-2).setView(C1544R.layout.common_dialog_hint).setCanceledOnTouchOutside(true).build();
        }
        if (obj instanceof Integer) {
            str = StringUtils.getString(((Integer) obj).intValue());
        } else {
            str = obj instanceof String ? (String) obj : "";
        }
        if (!TextUtils.isEmpty(str)) {
            this.mHintDialog.show();
            ((TextView) this.mHintDialog.findViewById(C1544R.C1548id.tv_hint_title)).setText(str);
        }
    }

    /* access modifiers changed from: protected */
    public void dismissHint() {
        CommonDialog commonDialog = this.mHintDialog;
        if (commonDialog != null && commonDialog.isShowing()) {
            this.mHintDialog.cancel();
        }
    }

    /* access modifiers changed from: protected */
    public void showBottomDialog(View view) {
        Dialog dialog = new Dialog(getContext(), C1544R.C1551style.dialog_bottom_full);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(-1, -2);
        window.setGravity(80);
        window.setWindowAnimations(C1544R.C1551style.Animation_Popup);
        dialog.show();
    }

    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.isViewBind = false;
        Unbinder unbinder = this.mUnbinder;
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (isEventBusRegister()) {
            EventBusUtil.unRegister(this);
        }
        disProgressDialog();
        dismissHint();
    }

    public void onSupportVisible() {
        super.onSupportVisible();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    public void initImmersionBar() {
        ImmersionBar.with((Fragment) this).fitsSystemWindows(true).statusBarColor(C1544R.C1546color.colorPrimary).init();
    }
}
