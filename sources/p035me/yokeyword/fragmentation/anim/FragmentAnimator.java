package p035me.yokeyword.fragmentation.anim;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: me.yokeyword.fragmentation.anim.FragmentAnimator */
public class FragmentAnimator implements Parcelable {
    public static final Parcelable.Creator<FragmentAnimator> CREATOR = new Parcelable.Creator<FragmentAnimator>() {
        public FragmentAnimator createFromParcel(Parcel parcel) {
            return new FragmentAnimator(parcel);
        }

        public FragmentAnimator[] newArray(int i) {
            return new FragmentAnimator[i];
        }
    };
    protected int enter;
    protected int exit;
    protected int popEnter;
    protected int popExit;

    public int describeContents() {
        return 0;
    }

    public FragmentAnimator() {
    }

    public FragmentAnimator(int i, int i2) {
        this.enter = i;
        this.exit = i2;
    }

    public FragmentAnimator(int i, int i2, int i3, int i4) {
        this.enter = i;
        this.exit = i2;
        this.popEnter = i3;
        this.popExit = i4;
    }

    public FragmentAnimator copy() {
        return new FragmentAnimator(getEnter(), getExit(), getPopEnter(), getPopExit());
    }

    protected FragmentAnimator(Parcel parcel) {
        this.enter = parcel.readInt();
        this.exit = parcel.readInt();
        this.popEnter = parcel.readInt();
        this.popExit = parcel.readInt();
    }

    public int getEnter() {
        return this.enter;
    }

    public FragmentAnimator setEnter(int i) {
        this.enter = i;
        return this;
    }

    public int getExit() {
        return this.exit;
    }

    public FragmentAnimator setExit(int i) {
        this.exit = i;
        return this;
    }

    public int getPopEnter() {
        return this.popEnter;
    }

    public FragmentAnimator setPopEnter(int i) {
        this.popEnter = i;
        return this;
    }

    public int getPopExit() {
        return this.popExit;
    }

    public FragmentAnimator setPopExit(int i) {
        this.popExit = i;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.enter);
        parcel.writeInt(this.exit);
        parcel.writeInt(this.popEnter);
        parcel.writeInt(this.popExit);
    }
}
