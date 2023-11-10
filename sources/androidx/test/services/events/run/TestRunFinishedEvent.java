package androidx.test.services.events.run;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.FailureInfo;
import androidx.test.services.events.run.TestRunEvent;
import java.util.ArrayList;
import java.util.List;

public class TestRunFinishedEvent extends TestRunEvent {
    public final int count;
    public final List<FailureInfo> failures;
    public final int ignoreCount;
    public final long runTime;

    public TestRunFinishedEvent(int i, int i2, long j, List<FailureInfo> list) {
        Checks.checkNotNull(list, "failures cannot be null");
        this.count = i;
        this.ignoreCount = i2;
        this.runTime = j;
        this.failures = list;
    }

    TestRunFinishedEvent(Parcel parcel) {
        this.count = parcel.readInt();
        this.ignoreCount = parcel.readInt();
        this.runTime = parcel.readLong();
        this.failures = new ArrayList();
        for (Parcelable parcelable : parcel.readParcelableArray(FailureInfo[].class.getClassLoader())) {
            this.failures.add((FailureInfo) parcelable);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.count);
        parcel.writeInt(this.ignoreCount);
        parcel.writeLong(this.runTime);
        parcel.writeParcelableArray((FailureInfo[]) this.failures.toArray(new FailureInfo[0]), i);
    }

    /* access modifiers changed from: package-private */
    public TestRunEvent.EventType instanceType() {
        return TestRunEvent.EventType.FINISHED;
    }
}
