package p035me.yokeyword.fragmentation.anim;

import android.os.Parcel;
import android.os.Parcelable;
import p035me.yokeyword.fragmentation.C2466R;

/* renamed from: me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator */
public class DefaultHorizontalAnimator extends FragmentAnimator implements Parcelable {
    public static final Parcelable.Creator<DefaultHorizontalAnimator> CREATOR = new Parcelable.Creator<DefaultHorizontalAnimator>() {
        public DefaultHorizontalAnimator createFromParcel(Parcel parcel) {
            return new DefaultHorizontalAnimator(parcel);
        }

        public DefaultHorizontalAnimator[] newArray(int i) {
            return new DefaultHorizontalAnimator[i];
        }
    };

    public int describeContents() {
        return 0;
    }

    public DefaultHorizontalAnimator() {
        this.enter = C2466R.C2467anim.h_fragment_enter;
        this.exit = C2466R.C2467anim.h_fragment_exit;
        this.popEnter = C2466R.C2467anim.h_fragment_pop_enter;
        this.popExit = C2466R.C2467anim.h_fragment_pop_exit;
    }

    protected DefaultHorizontalAnimator(Parcel parcel) {
        super(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
