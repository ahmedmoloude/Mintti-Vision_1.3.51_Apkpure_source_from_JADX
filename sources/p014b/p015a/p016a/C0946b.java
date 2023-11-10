package p014b.p015a.p016a;

import java.math.BigDecimal;
import java.math.RoundingMode;

/* renamed from: b.a.a.b */
public class C0946b {
    /* renamed from: a */
    public static double m88a(int i, double d) {
        return BigDecimal.valueOf(d).setScale(i, RoundingMode.HALF_UP).doubleValue();
    }
}
