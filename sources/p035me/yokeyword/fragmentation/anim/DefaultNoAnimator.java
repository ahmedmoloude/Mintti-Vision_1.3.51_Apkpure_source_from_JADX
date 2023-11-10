package p035me.yokeyword.fragmentation.anim;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: me.yokeyword.fragmentation.anim.DefaultNoAnimator */
public class DefaultNoAnimator extends FragmentAnimator implements Parcelable {
    public static final Parcelable.Creator<DefaultNoAnimator> CREATOR = new Parcelable.Creator<DefaultNoAnimator>() {
        public DefaultNoAnimator createFromParcel(Parcel parcel) {
            return new DefaultNoAnimator(parcel);
        }

        public DefaultNoAnimator[] newArray(int i) {
            return new DefaultNoAnimator[i];
        }
    };

    public int describeContents() {
        return 0;
    }

    public DefaultNoAnimator() {
        this.enter = 0;
        this.exit = 0;
        this.popEnter = 0;
        this.popExit = 0;
    }

    protected DefaultNoAnimator(Parcel parcel) {
        super(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
