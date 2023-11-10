package com.p020kl.commonbase.utils;

import com.p020kl.commonbase.data.SpManager;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* renamed from: com.kl.commonbase.utils.UnitUtil */
public class UnitUtil {
    public static double getBgValue(double d) {
        if (SpManager.getBgUnit() != 0) {
            return new BigDecimal(d * 18.0d).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        return new BigDecimal(d).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }
}
