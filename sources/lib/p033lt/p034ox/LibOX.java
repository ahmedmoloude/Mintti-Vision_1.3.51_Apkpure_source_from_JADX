package lib.p033lt.p034ox;

/* renamed from: lib.lt.ox.LibOX */
public class LibOX {
    static {
        System.loadLibrary("libox");
    }

    public static native double[] calSpO2(double d, double d2, double d3);

    public static native void init();

    public static native double lowPassFilter125(int[] iArr);

    public static native double lowPassFilter250(int[] iArr);
}
