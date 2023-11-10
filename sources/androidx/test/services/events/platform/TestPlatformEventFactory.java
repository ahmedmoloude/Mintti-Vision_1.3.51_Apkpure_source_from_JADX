package androidx.test.services.events.platform;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.services.events.platform.TestPlatformEvent;

public final class TestPlatformEventFactory implements Parcelable.Creator<TestPlatformEvent> {

    /* renamed from: androidx.test.services.events.platform.TestPlatformEventFactory$1 */
    static /* synthetic */ class C08421 {

        /* renamed from: $SwitchMap$androidx$test$services$events$platform$TestPlatformEvent$EventType */
        static final /* synthetic */ int[] f216xc4d5d4b0;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                androidx.test.services.events.platform.TestPlatformEvent$EventType[] r0 = androidx.test.services.events.platform.TestPlatformEvent.EventType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f216xc4d5d4b0 = r0
                androidx.test.services.events.platform.TestPlatformEvent$EventType r1 = androidx.test.services.events.platform.TestPlatformEvent.EventType.TEST_RUN_STARTED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f216xc4d5d4b0     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.test.services.events.platform.TestPlatformEvent$EventType r1 = androidx.test.services.events.platform.TestPlatformEvent.EventType.TEST_RUN_ERROR     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f216xc4d5d4b0     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.test.services.events.platform.TestPlatformEvent$EventType r1 = androidx.test.services.events.platform.TestPlatformEvent.EventType.TEST_CASE_STARTED     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = f216xc4d5d4b0     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.test.services.events.platform.TestPlatformEvent$EventType r1 = androidx.test.services.events.platform.TestPlatformEvent.EventType.TEST_CASE_ERROR     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = f216xc4d5d4b0     // Catch:{ NoSuchFieldError -> 0x003e }
                androidx.test.services.events.platform.TestPlatformEvent$EventType r1 = androidx.test.services.events.platform.TestPlatformEvent.EventType.TEST_CASE_FINISHED     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = f216xc4d5d4b0     // Catch:{ NoSuchFieldError -> 0x0049 }
                androidx.test.services.events.platform.TestPlatformEvent$EventType r1 = androidx.test.services.events.platform.TestPlatformEvent.EventType.TEST_RUN_FINISHED     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.services.events.platform.TestPlatformEventFactory.C08421.<clinit>():void");
        }
    }

    public TestPlatformEvent createFromParcel(Parcel parcel) {
        TestPlatformEvent.EventType valueOf = TestPlatformEvent.EventType.valueOf(parcel.readString());
        switch (C08421.f216xc4d5d4b0[valueOf.ordinal()]) {
            case 1:
                return new TestRunStartedEvent(parcel);
            case 2:
                return new TestRunErrorEvent(parcel);
            case 3:
                return new TestCaseStartedEvent(parcel);
            case 4:
                return new TestCaseErrorEvent(parcel);
            case 5:
                return new TestCaseFinishedEvent(parcel);
            case 6:
                return new TestRunFinishedEvent(parcel);
            default:
                String valueOf2 = String.valueOf(valueOf);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 22);
                sb.append("Unhandled event type: ");
                sb.append(valueOf2);
                throw new IllegalArgumentException(sb.toString());
        }
    }

    public TestPlatformEvent[] newArray(int i) {
        return new TestPlatformEvent[i];
    }
}
