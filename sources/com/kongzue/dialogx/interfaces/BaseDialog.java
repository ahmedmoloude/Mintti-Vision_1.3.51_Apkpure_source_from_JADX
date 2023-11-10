package com.kongzue.dialogx.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.impl.ActivityLifecycleImpl;
import com.kongzue.dialogx.impl.DialogFragmentImpl;
import com.kongzue.dialogx.util.ActivityRunnable;
import com.kongzue.dialogx.util.DialogListBuilder;
import com.kongzue.dialogx.util.DialogXFloatingWindowActivity;
import com.kongzue.dialogx.util.TextInfo;
import com.kongzue.dialogx.util.WindowUtil;
import com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BaseDialog implements LifecycleOwner {
    private static WeakReference<Activity> activityWeakReference;
    static WeakReference<Handler> mMainHandler;
    /* access modifiers changed from: private */
    public static WeakReference<FrameLayout> rootFrameLayout;
    private static List<BaseDialog> runningDialogList;
    private static Thread uiThread;
    private static Map<String, ActivityRunnable> waitRunDialogX;
    protected static WindowInsets windowInsets;
    /* access modifiers changed from: protected */
    public boolean autoShowInputKeyboard;
    /* access modifiers changed from: protected */
    public int backgroundColor;
    protected boolean cancelable;
    protected DialogX.IMPL_MODE dialogImplMode;
    private WeakReference<DialogListBuilder> dialogListBuilder;
    private WeakReference<View> dialogView;
    /* access modifiers changed from: protected */
    public boolean dismissAnimFlag;
    /* access modifiers changed from: protected */
    public long enterAnimDuration;
    /* access modifiers changed from: protected */
    public long exitAnimDuration;
    protected WeakReference<DialogXFloatingWindowActivity> floatingWindowActivity;
    /* access modifiers changed from: protected */
    public boolean isShow;
    /* access modifiers changed from: protected */
    public LifecycleRegistry lifecycle;
    protected int maxWidth;
    protected WeakReference<Activity> ownActivity;
    protected WeakReference<DialogFragmentImpl> ownDialogFragmentImpl;
    /* access modifiers changed from: protected */
    public boolean preShow;
    /* access modifiers changed from: protected */
    public DialogXStyle style;
    protected DialogX.THEME theme;

    public enum BOOLEAN {
        TRUE,
        FALSE
    }

    public abstract String dialogKey();

    public abstract boolean isCancelable();

    /* access modifiers changed from: protected */
    public void onDialogInit() {
    }

    /* access modifiers changed from: protected */
    public void onDialogRefreshUI() {
    }

    /* access modifiers changed from: protected */
    public void onDialogShow() {
    }

    public abstract void restartDialog();

    public abstract <D extends BaseDialog> D show();

    /* access modifiers changed from: protected */
    public abstract void shutdown();

    public static void init(Context context) {
        if (context == null) {
            context = ActivityLifecycleImpl.getTopActivity();
        }
        if (context instanceof Activity) {
            initActivityContext((Activity) context);
        }
        ActivityLifecycleImpl.init(context, new ActivityLifecycleImpl.onActivityResumeCallBack() {
            public void getActivity(Activity activity) {
                BaseDialog.initActivityContext(activity);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void initActivityContext(Activity activity) {
        try {
            uiThread = Looper.getMainLooper().getThread();
            activityWeakReference = new WeakReference<>(activity);
            rootFrameLayout = new WeakReference<>((FrameLayout) activity.getWindow().getDecorView());
            if (Build.VERSION.SDK_INT >= 23) {
                publicWindowInsets(((FrameLayout) rootFrameLayout.get()).getRootWindowInsets());
            }
        } catch (Exception e) {
            e.printStackTrace();
            error("DialogX.init: 初始化异常，找不到Activity的根布局");
        }
    }

    /* access modifiers changed from: protected */
    public static void log(Object obj) {
        if (DialogX.DEBUGMODE) {
            Log.i(">>>", obj.toString());
        }
    }

    protected static void error(Object obj) {
        if (DialogX.DEBUGMODE) {
            Log.e(">>>", obj.toString());
        }
    }

    public static void onActivityResume(Activity activity) {
        if (runningDialogList != null) {
            CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList(runningDialogList);
            for (int size = copyOnWriteArrayList.size() - 1; size >= 0; size--) {
                BaseDialog baseDialog = (BaseDialog) copyOnWriteArrayList.get(size);
                if (baseDialog.getOwnActivity() == activity && baseDialog.isShow && baseDialog.getDialogView() != null) {
                    View findViewById = baseDialog.getDialogView().findViewById(C1903R.C1907id.box_root);
                    if ((findViewById instanceof DialogXBaseRelativeLayout) && ((DialogXBaseRelativeLayout) findViewById).isBaseFocusable()) {
                        findViewById.requestFocus();
                        return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void requestDialogFocus() {
        if (getTopActivity() instanceof Activity) {
            onActivityResume(getTopActivity());
        }
    }

    /* access modifiers changed from: protected */
    public static void show(final View view) {
        BaseDialog baseDialog;
        if (view != null && (baseDialog = (BaseDialog) view.getTag()) != null) {
            if (!baseDialog.isShow) {
                baseDialog.ownActivity = new WeakReference<>(getTopActivity());
                baseDialog.dialogView = new WeakReference<>(view);
                log(baseDialog.dialogKey() + ".show");
                addDialogToRunningList(baseDialog);
                int i = C20798.$SwitchMap$com$kongzue$dialogx$DialogX$IMPL_MODE[baseDialog.dialogImplMode.ordinal()];
                if (i == 1) {
                    WindowUtil.show(getTopActivity(), view, !(baseDialog instanceof NoTouchInterface));
                } else if (i == 2) {
                    DialogFragmentImpl dialogFragmentImpl = new DialogFragmentImpl(baseDialog, view);
                    dialogFragmentImpl.show(getSupportFragmentManager(getTopActivity()), "DialogX");
                    baseDialog.ownDialogFragmentImpl = new WeakReference<>(dialogFragmentImpl);
                } else if (i != 3) {
                    WeakReference<FrameLayout> weakReference = rootFrameLayout;
                    if (weakReference != null && weakReference.get() != null) {
                        runOnMain(new Runnable() {
                            public void run() {
                                if (view.getParent() == BaseDialog.rootFrameLayout.get()) {
                                    BaseDialog.error(((BaseDialog) view.getTag()).dialogKey() + "已处于显示状态，请勿重复执行 show() 指令。");
                                    return;
                                }
                                if (view.getParent() != null) {
                                    ((ViewGroup) view.getParent()).removeView(view);
                                }
                                ((FrameLayout) BaseDialog.rootFrameLayout.get()).addView(view);
                            }
                        });
                    }
                } else {
                    if (waitRunDialogX == null) {
                        waitRunDialogX = new HashMap();
                    }
                    waitRunDialogX.put(baseDialog.dialogKey(), new ActivityRunnable(baseDialog) {
                        final /* synthetic */ BaseDialog val$baseDialog;

                        {
                            this.val$baseDialog = r1;
                        }

                        public void run(Activity activity) {
                            this.val$baseDialog.floatingWindowActivity = new WeakReference<>((DialogXFloatingWindowActivity) activity);
                            final FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView();
                            if (frameLayout != null) {
                                BaseDialog.runOnMain(new Runnable() {
                                    public void run() {
                                        if (view.getParent() == BaseDialog.rootFrameLayout.get()) {
                                            BaseDialog.error(((BaseDialog) view.getTag()).dialogKey() + "已处于显示状态，请勿重复执行 show() 指令。");
                                            return;
                                        }
                                        if (view.getParent() != null) {
                                            ((ViewGroup) view.getParent()).removeView(view);
                                        }
                                        frameLayout.addView(view);
                                    }
                                });
                            }
                        }
                    });
                    DialogXFloatingWindowActivity dialogXFloatingWindowActivity = DialogXFloatingWindowActivity.getDialogXFloatingWindowActivity();
                    if (dialogXFloatingWindowActivity == null || !dialogXFloatingWindowActivity.isSameFrom(getTopActivity().hashCode())) {
                        Intent intent = new Intent(getContext(), DialogXFloatingWindowActivity.class);
                        if (getTopActivity() == null) {
                            intent.addFlags(268435456);
                        }
                        intent.putExtra("dialogXKey", baseDialog.dialogKey());
                        intent.putExtra("fromActivityUiStatus", getTopActivity() == null ? 0 : getTopActivity().getWindow().getDecorView().getSystemUiVisibility());
                        intent.putExtra(TypedValues.TransitionType.S_FROM, getContext().hashCode());
                        getContext().startActivity(intent);
                        if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() > 5 && getTopActivity() != null) {
                            getTopActivity().overridePendingTransition(0, 0);
                            return;
                        }
                        return;
                    }
                    dialogXFloatingWindowActivity.showDialogX(baseDialog.dialogKey());
                }
            } else if (baseDialog.getDialogView() != null) {
                baseDialog.getDialogView().setVisibility(0);
            } else {
                error(((BaseDialog) view.getTag()).dialogKey() + "已处于显示状态，请勿重复执行 show() 指令。");
            }
        }
    }

    /* renamed from: com.kongzue.dialogx.interfaces.BaseDialog$8 */
    static /* synthetic */ class C20798 {
        static final /* synthetic */ int[] $SwitchMap$com$kongzue$dialogx$DialogX$IMPL_MODE;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.kongzue.dialogx.DialogX$IMPL_MODE[] r0 = com.kongzue.dialogx.DialogX.IMPL_MODE.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$kongzue$dialogx$DialogX$IMPL_MODE = r0
                com.kongzue.dialogx.DialogX$IMPL_MODE r1 = com.kongzue.dialogx.DialogX.IMPL_MODE.WINDOW     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$kongzue$dialogx$DialogX$IMPL_MODE     // Catch:{ NoSuchFieldError -> 0x001d }
                com.kongzue.dialogx.DialogX$IMPL_MODE r1 = com.kongzue.dialogx.DialogX.IMPL_MODE.DIALOG_FRAGMENT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$kongzue$dialogx$DialogX$IMPL_MODE     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.kongzue.dialogx.DialogX$IMPL_MODE r1 = com.kongzue.dialogx.DialogX.IMPL_MODE.FLOATING_ACTIVITY     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.interfaces.BaseDialog.C20798.<clinit>():void");
        }
    }

    private static FragmentManager getSupportFragmentManager(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            return ((AppCompatActivity) activity).getSupportFragmentManager();
        }
        return null;
    }

    public static ActivityRunnable getActivityRunnable(String str) {
        if (str == null) {
            return null;
        }
        return waitRunDialogX.get(str);
    }

    /* access modifiers changed from: protected */
    public static void show(Activity activity, final View view) {
        BaseDialog baseDialog;
        if (activity != null && view != null && (baseDialog = (BaseDialog) view.getTag()) != null) {
            if (baseDialog.getDialogView() != null) {
                baseDialog.getDialogView().setVisibility(0);
            }
            if (baseDialog.isShow) {
                error(((BaseDialog) view.getTag()).dialogKey() + "已处于显示状态，请勿重复执行 show() 指令。");
            } else if (activity.isDestroyed()) {
                error(((BaseDialog) view.getTag()).dialogKey() + ".show ERROR: activity is Destroyed.");
            } else {
                baseDialog.ownActivity = new WeakReference<>(activity);
                baseDialog.dialogView = new WeakReference<>(view);
                log(baseDialog + ".show");
                addDialogToRunningList(baseDialog);
                int i = C20798.$SwitchMap$com$kongzue$dialogx$DialogX$IMPL_MODE[baseDialog.dialogImplMode.ordinal()];
                if (i == 1) {
                    WindowUtil.show(activity, view, !(baseDialog instanceof NoTouchInterface));
                } else if (i == 2) {
                    DialogFragmentImpl dialogFragmentImpl = new DialogFragmentImpl(baseDialog, view);
                    dialogFragmentImpl.show(getSupportFragmentManager(activity), "DialogX");
                    baseDialog.ownDialogFragmentImpl = new WeakReference<>(dialogFragmentImpl);
                } else if (i != 3) {
                    final FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView();
                    if (frameLayout != null) {
                        runOnMain(new Runnable() {
                            public void run() {
                                if (view.getParent() == BaseDialog.rootFrameLayout.get()) {
                                    BaseDialog.error(((BaseDialog) view.getTag()).dialogKey() + "已处于显示状态，请勿重复执行 show() 指令。");
                                    return;
                                }
                                if (view.getParent() != null) {
                                    ((ViewGroup) view.getParent()).removeView(view);
                                }
                                frameLayout.addView(view);
                            }
                        });
                    }
                } else {
                    if (waitRunDialogX == null) {
                        waitRunDialogX = new HashMap();
                    }
                    waitRunDialogX.put(baseDialog.dialogKey(), new ActivityRunnable(baseDialog) {
                        final /* synthetic */ BaseDialog val$baseDialog;

                        {
                            this.val$baseDialog = r1;
                        }

                        public void run(Activity activity) {
                            this.val$baseDialog.floatingWindowActivity = new WeakReference<>((DialogXFloatingWindowActivity) activity);
                            final FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView();
                            if (frameLayout != null) {
                                BaseDialog.runOnMain(new Runnable() {
                                    public void run() {
                                        if (view.getParent() == BaseDialog.rootFrameLayout.get()) {
                                            BaseDialog.error(((BaseDialog) view.getTag()).dialogKey() + "已处于显示状态，请勿重复执行 show() 指令。");
                                            return;
                                        }
                                        if (view.getParent() != null) {
                                            ((ViewGroup) view.getParent()).removeView(view);
                                        }
                                        frameLayout.addView(view);
                                    }
                                });
                            }
                        }
                    });
                    DialogXFloatingWindowActivity dialogXFloatingWindowActivity = DialogXFloatingWindowActivity.getDialogXFloatingWindowActivity();
                    if (dialogXFloatingWindowActivity == null || !dialogXFloatingWindowActivity.isSameFrom(activity.hashCode())) {
                        Intent intent = new Intent(activity, DialogXFloatingWindowActivity.class);
                        intent.putExtra("dialogXKey", baseDialog.dialogKey());
                        intent.putExtra(TypedValues.TransitionType.S_FROM, activity.hashCode());
                        intent.putExtra("fromActivityUiStatus", activity.getWindow().getDecorView().getSystemUiVisibility());
                        activity.startActivity(intent);
                        if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() > 5) {
                            activity.overridePendingTransition(0, 0);
                            return;
                        }
                        return;
                    }
                    dialogXFloatingWindowActivity.showDialogX(baseDialog.dialogKey());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public static void dismiss(final View view) {
        if (view != null) {
            BaseDialog baseDialog = (BaseDialog) view.getTag();
            log(baseDialog.dialogKey() + ".dismiss");
            removeDialogToRunningList(baseDialog);
            WeakReference<View> weakReference = baseDialog.dialogView;
            if (weakReference != null) {
                weakReference.clear();
            }
            int i = C20798.$SwitchMap$com$kongzue$dialogx$DialogX$IMPL_MODE[baseDialog.dialogImplMode.ordinal()];
            if (i == 1) {
                WindowUtil.dismiss(view);
            } else if (i == 2) {
                WeakReference<DialogFragmentImpl> weakReference2 = baseDialog.ownDialogFragmentImpl;
                if (!(weakReference2 == null || weakReference2.get() == null)) {
                    ((DialogFragmentImpl) baseDialog.ownDialogFragmentImpl.get()).dismiss();
                }
            } else if (i != 3) {
                runOnMain(new Runnable() {
                    public void run() {
                        if (view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        } else if (BaseDialog.rootFrameLayout != null) {
                            ((FrameLayout) BaseDialog.rootFrameLayout.get()).removeView(view);
                        } else {
                            return;
                        }
                        BaseDialog.requestDialogFocus();
                    }
                }, true);
            } else {
                WeakReference<DialogXFloatingWindowActivity> weakReference3 = baseDialog.floatingWindowActivity;
                if (!(weakReference3 == null || weakReference3.get() == null)) {
                    FrameLayout frameLayout = (FrameLayout) ((DialogXFloatingWindowActivity) baseDialog.floatingWindowActivity.get()).getWindow().getDecorView();
                    if (frameLayout != null) {
                        frameLayout.removeView(view);
                    }
                    ((DialogXFloatingWindowActivity) baseDialog.floatingWindowActivity.get()).finish(baseDialog.dialogKey());
                    requestDialogFocus();
                }
            }
            if (baseDialog.getDialogListBuilder() != null && !baseDialog.getDialogListBuilder().isEmpty()) {
                baseDialog.getDialogListBuilder().showNext();
            }
        }
    }

    private static void addDialogToRunningList(BaseDialog baseDialog) {
        if (runningDialogList == null) {
            runningDialogList = new CopyOnWriteArrayList();
        }
        runningDialogList.add(baseDialog);
    }

    private static void removeDialogToRunningList(BaseDialog baseDialog) {
        List<BaseDialog> list = runningDialogList;
        if (list != null) {
            list.remove(baseDialog);
        }
    }

    public static Activity getTopActivity() {
        WeakReference<Activity> weakReference = activityWeakReference;
        if (weakReference != null) {
            return (Activity) weakReference.get();
        }
        init((Context) null);
        WeakReference<Activity> weakReference2 = activityWeakReference;
        if (weakReference2 == null) {
            return ActivityLifecycleImpl.getTopActivity();
        }
        return (Activity) weakReference2.get();
    }

    public static Context getContext() {
        Activity topActivity = getTopActivity();
        if (topActivity != null) {
            return topActivity;
        }
        Context applicationContext = getApplicationContext();
        if (applicationContext != null) {
            return applicationContext;
        }
        error("DialogX 未初始化。\n请检查是否在启动对话框前进行初始化操作，使用以下代码进行初始化：\nDialogX.init(context);\n\n另外建议您前往查看 DialogX 的文档进行使用：https://github.com/kongzue/DialogX");
        return null;
    }

    public static Context getApplicationContext() {
        return ActivityLifecycleImpl.getApplicationContext();
    }

    public static void cleanContext() {
        WeakReference<Activity> weakReference = activityWeakReference;
        if (weakReference != null) {
            weakReference.clear();
        }
        activityWeakReference = null;
        System.gc();
    }

    public BaseDialog() {
        this.dialogImplMode = DialogX.implIMPLMode;
        this.lifecycle = new LifecycleRegistry(this);
        this.cancelable = true;
        this.backgroundColor = -1;
        this.enterAnimDuration = -1;
        this.exitAnimDuration = -1;
        this.cancelable = DialogX.cancelable;
        this.style = DialogX.globalStyle;
        this.theme = DialogX.globalTheme;
        this.enterAnimDuration = DialogX.enterAnimDuration;
        this.exitAnimDuration = DialogX.exitAnimDuration;
        this.autoShowInputKeyboard = DialogX.autoShowInputKeyboard;
    }

    public View createView(int i) {
        if (getApplicationContext() != null) {
            return LayoutInflater.from(getApplicationContext()).inflate(i, (ViewGroup) null);
        }
        error("DialogX 未初始化。\n请检查是否在启动对话框前进行初始化操作，使用以下代码进行初始化：\nDialogX.init(context);\n\n另外建议您前往查看 DialogX 的文档进行使用：https://github.com/kongzue/DialogX");
        return null;
    }

    public boolean isShow() {
        return this.isShow;
    }

    public DialogXStyle getStyle() {
        return this.style;
    }

    public DialogX.THEME getTheme() {
        return this.theme;
    }

    public static void useTextInfo(TextView textView, TextInfo textInfo) {
        if (textInfo != null && textView != null) {
            if (textInfo.getFontSize() > 0) {
                textView.setTextSize(textInfo.getFontSizeComplexUnit(), (float) textInfo.getFontSize());
            }
            if (textInfo.getFontColor() != 1) {
                textView.setTextColor(textInfo.getFontColor());
            }
            if (textInfo.getGravity() != -1) {
                textView.setGravity(textInfo.getGravity());
            }
            if (textInfo.isShowEllipsis()) {
                textView.setEllipsize(TextUtils.TruncateAt.END);
            } else {
                textView.setEllipsize((TextUtils.TruncateAt) null);
            }
            if (textInfo.getMaxLines() != -1) {
                textView.setMaxLines(textInfo.getMaxLines());
            } else {
                textView.setMaxLines(Integer.MAX_VALUE);
            }
            textView.getPaint().setFakeBoldText(textInfo.isBold());
        }
    }

    /* access modifiers changed from: protected */
    public void showText(TextView textView, CharSequence charSequence) {
        if (textView != null) {
            if (isNull(charSequence)) {
                textView.setVisibility(8);
                textView.setText("");
                return;
            }
            textView.setVisibility(0);
            textView.setText(charSequence);
        }
    }

    public static boolean isNull(String str) {
        return str == null || str.trim().isEmpty() || "null".equals(str) || "(null)".equals(str);
    }

    public static boolean isNull(CharSequence charSequence) {
        String valueOf = String.valueOf(charSequence);
        return charSequence == null || valueOf.trim().isEmpty() || "null".equals(valueOf) || "(null)".equals(valueOf);
    }

    public Resources getResources() {
        if (getApplicationContext() == null) {
            return Resources.getSystem();
        }
        return getApplicationContext().getResources();
    }

    public int dip2px(float f) {
        return (int) ((f * getResources().getDisplayMetrics().density) + 0.5f);
    }

    public boolean isLightTheme() {
        if (this.theme == DialogX.THEME.AUTO) {
            if (getApplicationContext() == null) {
                if (this.theme == DialogX.THEME.LIGHT) {
                    return true;
                }
                return false;
            } else if ((getResources().getConfiguration().uiMode & 48) == 16) {
                return true;
            } else {
                return false;
            }
        } else if (this.theme == DialogX.THEME.LIGHT) {
            return true;
        } else {
            return false;
        }
    }

    public static FrameLayout getRootFrameLayout() {
        WeakReference<FrameLayout> weakReference = rootFrameLayout;
        if (weakReference != null) {
            return (FrameLayout) weakReference.get();
        }
        error("DialogX 未初始化。\n请检查是否在启动对话框前进行初始化操作，使用以下代码进行初始化：\nDialogX.init(context);\n\n另外建议您前往查看 DialogX 的文档进行使用：https://github.com/kongzue/DialogX");
        return null;
    }

    public void tintColor(View view, int i) {
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            view.setBackgroundTintList(ColorStateList.valueOf(i));
        }
    }

    /* access modifiers changed from: protected */
    public void beforeShow() {
        this.preShow = true;
        this.dismissAnimFlag = false;
        if (getTopActivity() == null) {
            init((Context) null);
            if (getTopActivity() == null) {
                error("DialogX 未初始化。\n请检查是否在启动对话框前进行初始化操作，使用以下代码进行初始化：\nDialogX.init(context);\n\n另外建议您前往查看 DialogX 的文档进行使用：https://github.com/kongzue/DialogX");
                return;
            }
        }
        if (this.dialogImplMode != DialogX.IMPL_MODE.VIEW && (getTopActivity() instanceof LifecycleOwner)) {
            ((LifecycleOwner) getTopActivity()).getLifecycle().addObserver(new LifecycleEventObserver() {
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        BaseDialog.recycleDialog(BaseDialog.getTopActivity());
                    }
                }
            });
        }
        View currentFocus = getTopActivity().getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getTopActivity().getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
        }
    }

    /* access modifiers changed from: protected */
    public String getString(int i) {
        if (getApplicationContext() != null) {
            return getResources().getString(i);
        }
        error("DialogX 未初始化。\n请检查是否在启动对话框前进行初始化操作，使用以下代码进行初始化：\nDialogX.init(context);\n\n另外建议您前往查看 DialogX 的文档进行使用：https://github.com/kongzue/DialogX");
        return null;
    }

    /* access modifiers changed from: protected */
    public int getColor(int i) {
        if (getApplicationContext() != null) {
            return getResources().getColor(i);
        }
        error("DialogX 未初始化。\n请检查是否在启动对话框前进行初始化操作，使用以下代码进行初始化：\nDialogX.init(context);\n\n另外建议您前往查看 DialogX 的文档进行使用：https://github.com/kongzue/DialogX");
        return ViewCompat.MEASURED_STATE_MASK;
    }

    /* access modifiers changed from: protected */
    public static void runOnMain(Runnable runnable) {
        if (!DialogX.autoRunOnUIThread || (getUiThread() != null && Thread.currentThread() == getUiThread())) {
            runnable.run();
        } else {
            runOnMain(runnable, true);
        }
    }

    protected static Thread getUiThread() {
        if (uiThread == null) {
            uiThread = Looper.getMainLooper().getThread();
        }
        return uiThread;
    }

    protected static void runOnMain(Runnable runnable, boolean z) {
        getMainHandler().post(runnable);
    }

    /* access modifiers changed from: protected */
    public static void runOnMainDelay(Runnable runnable, long j) {
        if (j >= 0) {
            if (!DialogX.autoRunOnUIThread) {
                runnable.run();
            }
            getMainHandler().postDelayed(runnable, j);
        }
    }

    public View getDialogView() {
        WeakReference<View> weakReference = this.dialogView;
        if (weakReference == null) {
            return null;
        }
        return (View) weakReference.get();
    }

    public Activity getOwnActivity() {
        WeakReference<Activity> weakReference = this.ownActivity;
        if (weakReference == null) {
            return null;
        }
        return (Activity) weakReference.get();
    }

    /* access modifiers changed from: protected */
    public void cleanActivityContext() {
        WeakReference<Activity> weakReference = this.ownActivity;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.ownActivity = null;
    }

    public static void cleanAll() {
        if (runningDialogList != null) {
            Iterator it = new CopyOnWriteArrayList(runningDialogList).iterator();
            while (it.hasNext()) {
                BaseDialog baseDialog = (BaseDialog) it.next();
                if (baseDialog.isShow()) {
                    baseDialog.shutdown();
                }
                baseDialog.cleanActivityContext();
                runningDialogList.remove(baseDialog);
            }
        }
    }

    public static void recycleDialog(Activity activity) {
        WeakReference<View> weakReference;
        WeakReference<DialogFragmentImpl> weakReference2;
        int i = C20798.$SwitchMap$com$kongzue$dialogx$DialogX$IMPL_MODE[DialogX.implIMPLMode.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (!(i == 3 || runningDialogList == null)) {
                    Iterator it = new CopyOnWriteArrayList(runningDialogList).iterator();
                    while (it.hasNext()) {
                        BaseDialog baseDialog = (BaseDialog) it.next();
                        if (baseDialog.getOwnActivity() == activity) {
                            baseDialog.cleanActivityContext();
                            runningDialogList.remove(baseDialog);
                        }
                    }
                }
            } else if (runningDialogList != null) {
                Iterator it2 = new CopyOnWriteArrayList(runningDialogList).iterator();
                while (it2.hasNext()) {
                    BaseDialog baseDialog2 = (BaseDialog) it2.next();
                    if (!(baseDialog2.getOwnActivity() != activity || (weakReference2 = baseDialog2.ownDialogFragmentImpl) == null || weakReference2.get() == null)) {
                        ((DialogFragmentImpl) baseDialog2.ownDialogFragmentImpl.get()).dismiss();
                    }
                }
            }
        } else if (runningDialogList != null) {
            Iterator it3 = new CopyOnWriteArrayList(runningDialogList).iterator();
            while (it3.hasNext()) {
                BaseDialog baseDialog3 = (BaseDialog) it3.next();
                if (baseDialog3.getOwnActivity() == activity && (weakReference = baseDialog3.dialogView) != null) {
                    WindowUtil.dismiss((View) weakReference.get());
                }
            }
        }
        if (activity == getTopActivity()) {
            cleanContext();
        }
    }

    public static List<BaseDialog> getRunningDialogList() {
        if (runningDialogList == null) {
            return new ArrayList();
        }
        return new CopyOnWriteArrayList(runningDialogList);
    }

    /* access modifiers changed from: protected */
    public void imeShow(EditText editText, boolean z) {
        if (getTopActivity() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getTopActivity().getSystemService("input_method");
            if (z) {
                inputMethodManager.showSoftInput(editText, 0);
            } else {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
    }

    public int getMaxWidth() {
        int i = this.maxWidth;
        return i == 0 ? DialogX.dialogMaxWidth : i;
    }

    public DialogX.IMPL_MODE getDialogImplMode() {
        return this.dialogImplMode;
    }

    public static WindowInsets publicWindowInsets() {
        return windowInsets;
    }

    public static void publicWindowInsets(WindowInsets windowInsets2) {
        if (windowInsets2 != null) {
            windowInsets = windowInsets2;
        }
        if (runningDialogList != null) {
            CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList(runningDialogList);
            for (int size = copyOnWriteArrayList.size() - 1; size >= 0; size--) {
                BaseDialog baseDialog = (BaseDialog) copyOnWriteArrayList.get(size);
                if (baseDialog.isShow && baseDialog.getDialogView() != null) {
                    View findViewById = baseDialog.getDialogView().findViewById(C1903R.C1907id.box_root);
                    if (findViewById instanceof DialogXBaseRelativeLayout) {
                        log("publicWindowInsets");
                        ((DialogXBaseRelativeLayout) findViewById).paddingView(windowInsets2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindFloatingActivity(DialogXFloatingWindowActivity dialogXFloatingWindowActivity) {
        this.floatingWindowActivity = new WeakReference<>(dialogXFloatingWindowActivity);
    }

    private static Handler getMainHandler() {
        WeakReference<Handler> weakReference = mMainHandler;
        if (weakReference != null && weakReference.get() != null) {
            return (Handler) mMainHandler.get();
        }
        WeakReference<Handler> weakReference2 = new WeakReference<>(new Handler(Looper.getMainLooper()));
        mMainHandler = weakReference2;
        return (Handler) weakReference2.get();
    }

    public DialogListBuilder getDialogListBuilder() {
        WeakReference<DialogListBuilder> weakReference = this.dialogListBuilder;
        if (weakReference == null) {
            return null;
        }
        return (DialogListBuilder) weakReference.get();
    }

    public void setDialogListBuilder(DialogListBuilder dialogListBuilder2) {
        this.dialogListBuilder = new WeakReference<>(dialogListBuilder2);
    }

    public void cleanDialogList() {
        this.dialogListBuilder = null;
    }

    public boolean isPreShow() {
        return this.preShow;
    }

    public Lifecycle getLifecycle() {
        return this.lifecycle;
    }
}
