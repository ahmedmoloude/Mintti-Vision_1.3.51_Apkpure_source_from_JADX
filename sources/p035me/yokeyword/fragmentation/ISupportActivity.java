package p035me.yokeyword.fragmentation;

import android.view.MotionEvent;
import p035me.yokeyword.fragmentation.anim.FragmentAnimator;

/* renamed from: me.yokeyword.fragmentation.ISupportActivity */
public interface ISupportActivity {
    boolean dispatchTouchEvent(MotionEvent motionEvent);

    ExtraTransaction extraTransaction();

    FragmentAnimator getFragmentAnimator();

    SupportActivityDelegate getSupportDelegate();

    void onBackPressed();

    void onBackPressedSupport();

    FragmentAnimator onCreateFragmentAnimator();

    void post(Runnable runnable);

    void setFragmentAnimator(FragmentAnimator fragmentAnimator);
}
