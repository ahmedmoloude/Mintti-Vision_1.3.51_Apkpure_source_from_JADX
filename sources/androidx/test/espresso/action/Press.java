package androidx.test.espresso.action;

public enum Press implements PrecisionDescriber {
    PINPOINT {
        public float[] describePrecision() {
            return new float[]{1.0f, 1.0f};
        }
    },
    FINGER {
        public float[] describePrecision() {
            return new float[]{16.0f, 16.0f};
        }
    },
    THUMB {
        public float[] describePrecision() {
            return new float[]{25.0f, 25.0f};
        }
    }
}
