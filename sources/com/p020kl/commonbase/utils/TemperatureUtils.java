package com.p020kl.commonbase.utils;

import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.data.SpManager;
import java.text.DecimalFormat;

/* renamed from: com.kl.commonbase.utils.TemperatureUtils */
public class TemperatureUtils {
    public static final String tempeTypeConversionStrD(double d) {
        if (SpManager.getTemperaTrueUnit() == 0) {
            return StringUtils.formatString(C1544R.string.centigrade_degree_unit, Double.valueOf(d));
        }
        return StringUtils.formatString(C1544R.string.fahrenheit_degree_unit, Double.valueOf(tempeConversionDouble(d)));
    }

    public static double tempeTypeConversionDouble(double d) {
        if (SpManager.getTemperaTrueUnit() == 0) {
            return d;
        }
        return tempeConversionDouble(d);
    }

    public static double tempeConversionDouble(double d) {
        return Double.valueOf(new DecimalFormat("0.0").format(((d * 9.0d) / 5.0d) + 32.0d)).doubleValue();
    }

    public static String tempeConversionIntUnit(int i) {
        return StringUtils.getString(i == 1 ? C1544R.string.fahrenheit_unit : C1544R.string.centigrade_unit);
    }
}
