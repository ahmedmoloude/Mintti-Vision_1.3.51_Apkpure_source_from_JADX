package p035me.yokeyword.fragmentation.anim;

import android.os.Parcel;
import android.os.Parcelable;
import p035me.yokeyword.fragmentation.C2466R;

/* renamed from: me.yokeyword.fragmentation.anim.DefaultVerticalAnimator */
public class DefaultVerticalAnimator extends FragmentAnimator implements Parcelable {
    public static final Parcelable.Creator<DefaultVerticalAnimator> CREATOR = new Parcelable.Creator<DefaultVerticalAnimator>() {
        public DefaultVerticalAnimator createFromParcel(Parcel parcel) {
            return new DefaultVerticalAnimator(parcel);
        }

        public DefaultVerticalAnimator[] newArray(int i) {
            return new DefaultVerticalAnimator[i];
        }
    };

    public int describeContents() {
        return 0;
    }

    public DefaultVerticalAnimator() {
        this.enter = C2466R.C2467anim.v_fragment_enter;
        this.exit = C2466R.C2467anim.v_fragment_exit;
        this.popEnter = C2466R.C2467anim.v_fragment_pop_enter;
        this.popExit = C2466R.C2467anim.v_fragment_pop_exit;
    }

    protected DefaultVerticalAnimator(Parcel parcel) {
        super(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
