package androidx.test.espresso.action;

import androidx.test.espresso.UiController;
import androidx.test.espresso.action.Swiper;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import java.lang.reflect.Array;

public enum Swipe implements Swiper {
    FAST {
        public Swiper.Status sendSwipe(UiController uiController, float[] fArr, float[] fArr2, float[] fArr3) {
            return Swipe.sendLinearSwipe(uiController, fArr, fArr2, fArr3, 150);
        }
    },
    SLOW {
        public Swiper.Status sendSwipe(UiController uiController, float[] fArr, float[] fArr2, float[] fArr3) {
            return Swipe.sendLinearSwipe(uiController, fArr, fArr2, fArr3, 1500);
        }
    };
    
    private static final int SWIPE_EVENT_COUNT = 10;
    private static final int SWIPE_FAST_DURATION_MS = 150;
    private static final int SWIPE_SLOW_DURATION_MS = 1500;
    private static final String TAG = null;

    static {
        TAG = Swipe.class.getSimpleName();
    }

    private static float[][] interpolate(float[] fArr, float[] fArr2, int i) {
        Preconditions.checkElementIndex(1, fArr.length);
        Preconditions.checkElementIndex(1, fArr2.length);
        int[] iArr = new int[2];
        iArr[1] = 2;
        iArr[0] = i;
        float[][] fArr3 = (float[][]) Array.newInstance(float.class, iArr);
        for (int i2 = 1; i2 < i + 1; i2++) {
            int i3 = i2 - 1;
            float f = (float) i2;
            float f2 = ((float) i) + 2.0f;
            fArr3[i3][0] = fArr[0] + (((fArr2[0] - fArr[0]) * f) / f2);
            fArr3[i3][1] = fArr[1] + (((fArr2[1] - fArr[1]) * f) / f2);
        }
        return fArr3;
    }

    /* access modifiers changed from: private */
    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public static androidx.test.espresso.action.Swiper.Status sendLinearSwipe(androidx.test.espresso.UiController r10, float[] r11, float[] r12, float[] r13, int r14) {
        /*
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r10)
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r11)
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r12)
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r13)
            r0 = 10
            float[][] r0 = interpolate(r11, r12, r0)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.view.MotionEvent r11 = androidx.test.espresso.action.MotionEvents.obtainDownEvent(r11, r13)
            r1.add(r11)
            int r13 = r0.length     // Catch:{ Exception -> 0x006b }
            int r14 = r14 / r13
            long r13 = (long) r14     // Catch:{ Exception -> 0x006b }
            long r2 = r11.getDownTime()     // Catch:{ Exception -> 0x006b }
            int r4 = r0.length     // Catch:{ Exception -> 0x006b }
            r5 = 0
            r6 = 0
        L_0x0028:
            if (r6 >= r4) goto L_0x003b
            r7 = r0[r6]     // Catch:{ Exception -> 0x006b }
            long r2 = r2 + r13
            long r8 = r11.getDownTime()     // Catch:{ Exception -> 0x006b }
            android.view.MotionEvent r7 = androidx.test.espresso.action.MotionEvents.obtainMovement(r8, r2, r7)     // Catch:{ Exception -> 0x006b }
            r1.add(r7)     // Catch:{ Exception -> 0x006b }
            int r6 = r6 + 1
            goto L_0x0028
        L_0x003b:
            long r13 = r13 + r2
            long r2 = r11.getDownTime()     // Catch:{ Exception -> 0x006b }
            r6 = 1
            r7 = r12[r5]     // Catch:{ Exception -> 0x006b }
            r11 = 1
            r8 = r12[r11]     // Catch:{ Exception -> 0x006b }
            r9 = 0
            r4 = r13
            android.view.MotionEvent r11 = android.view.MotionEvent.obtain(r2, r4, r6, r7, r8, r9)     // Catch:{ Exception -> 0x006b }
            r1.add(r11)     // Catch:{ Exception -> 0x006b }
            r10.injectMotionEventSequence(r1)     // Catch:{ Exception -> 0x006b }
            java.util.Iterator r10 = r1.iterator()
        L_0x0056:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x0066
            java.lang.Object r11 = r10.next()
            android.view.MotionEvent r11 = (android.view.MotionEvent) r11
            r11.recycle()
            goto L_0x0056
        L_0x0066:
            androidx.test.espresso.action.Swiper$Status r10 = androidx.test.espresso.action.Swiper.Status.SUCCESS
            return r10
        L_0x0069:
            r10 = move-exception
            goto L_0x0082
        L_0x006b:
            androidx.test.espresso.action.Swiper$Status r10 = androidx.test.espresso.action.Swiper.Status.FAILURE     // Catch:{ all -> 0x0069 }
            java.util.Iterator r11 = r1.iterator()
        L_0x0071:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0081
            java.lang.Object r12 = r11.next()
            android.view.MotionEvent r12 = (android.view.MotionEvent) r12
            r12.recycle()
            goto L_0x0071
        L_0x0081:
            return r10
        L_0x0082:
            java.util.Iterator r11 = r1.iterator()
        L_0x0086:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0096
            java.lang.Object r12 = r11.next()
            android.view.MotionEvent r12 = (android.view.MotionEvent) r12
            r12.recycle()
            goto L_0x0086
        L_0x0096:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.action.Swipe.sendLinearSwipe(androidx.test.espresso.UiController, float[], float[], float[], int):androidx.test.espresso.action.Swiper$Status");
    }
}
