package p035me.yokeyword.fragmentation;

import android.os.Bundle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import p035me.yokeyword.fragmentation.anim.FragmentAnimator;

/* renamed from: me.yokeyword.fragmentation.ISupportFragment */
public interface ISupportFragment {
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;
    public static final int SINGLETASK = 2;
    public static final int SINGLETOP = 1;
    public static final int STANDARD = 0;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: me.yokeyword.fragmentation.ISupportFragment$LaunchMode */
    public @interface LaunchMode {
    }

    void enqueueAction(Runnable runnable);

    ExtraTransaction extraTransaction();

    FragmentAnimator getFragmentAnimator();

    SupportFragmentDelegate getSupportDelegate();

    boolean isSupportVisible();

    boolean onBackPressedSupport();

    FragmentAnimator onCreateFragmentAnimator();

    void onEnterAnimationEnd(Bundle bundle);

    void onFragmentResult(int i, int i2, Bundle bundle);

    void onLazyInitView(Bundle bundle);

    void onNewBundle(Bundle bundle);

    void onSupportInvisible();

    void onSupportVisible();

    void post(Runnable runnable);

    void putNewBundle(Bundle bundle);

    void setFragmentAnimator(FragmentAnimator fragmentAnimator);

    void setFragmentResult(int i, Bundle bundle);
}
