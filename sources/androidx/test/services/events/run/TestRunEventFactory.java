package androidx.test.services.events.run;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.services.events.run.TestRunEvent;

final class TestRunEventFactory implements Parcelable.Creator<TestRunEvent> {
    TestRunEventFactory() {
    }

    /* renamed from: androidx.test.services.events.run.TestRunEventFactory$1 */
    static /* synthetic */ class C08431 {

        /* renamed from: $SwitchMap$androidx$test$services$events$run$TestRunEvent$EventType */
        static final /* synthetic */ int[] f217xe62b46e6;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                androidx.test.services.events.run.TestRunEvent$EventType[] r0 = androidx.test.services.events.run.TestRunEvent.EventType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f217xe62b46e6 = r0
                androidx.test.services.events.run.TestRunEvent$EventType r1 = androidx.test.services.events.run.TestRunEvent.EventType.STARTED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f217xe62b46e6     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.test.services.events.run.TestRunEvent$EventType r1 = androidx.test.services.events.run.TestRunEvent.EventType.TEST_STARTED     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f217xe62b46e6     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.test.services.events.run.TestRunEvent$EventType r1 = androidx.test.services.events.run.TestRunEvent.EventType.TEST_FINISHED     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = f217xe62b46e6     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.test.services.events.run.TestRunEvent$EventType r1 = androidx.test.services.events.run.TestRunEvent.EventType.TEST_ASSUMPTION_FAILURE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = f217xe62b46e6     // Catch:{ NoSuchFieldError -> 0x003e }
                androidx.test.services.events.run.TestRunEvent$EventType r1 = androidx.test.services.events.run.TestRunEvent.EventType.TEST_FAILURE     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = f217xe62b46e6     // Catch:{ NoSuchFieldError -> 0x0049 }
                androidx.test.services.events.run.TestRunEvent$EventType r1 = androidx.test.services.events.run.TestRunEvent.EventType.TEST_IGNORED     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = f217xe62b46e6     // Catch:{ NoSuchFieldError -> 0x0054 }
                androidx.test.services.events.run.TestRunEvent$EventType r1 = androidx.test.services.events.run.TestRunEvent.EventType.FINISHED     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.services.events.run.TestRunEventFactory.C08431.<clinit>():void");
        }
    }

    public TestRunEvent createFromParcel(Parcel parcel) {
        TestRunEvent.EventType valueOf = TestRunEvent.EventType.valueOf(parcel.readString());
        switch (C08431.f217xe62b46e6[valueOf.ordinal()]) {
            case 1:
                return new TestRunStartedEvent(parcel);
            case 2:
                return new TestStartedEvent(parcel);
            case 3:
                return new TestFinishedEvent(parcel);
            case 4:
                return new TestAssumptionFailureEvent(parcel);
            case 5:
                return new TestFailureEvent(parcel);
            case 6:
                return new TestIgnoredEvent(parcel);
            case 7:
                return new TestRunFinishedEvent(parcel);
            default:
                String valueOf2 = String.valueOf(valueOf);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 22);
                sb.append("Unhandled event type: ");
                sb.append(valueOf2);
                throw new IllegalArgumentException(sb.toString());
        }
    }

    public TestRunEvent[] newArray(int i) {
        return new TestRunEvent[i];
    }
}
