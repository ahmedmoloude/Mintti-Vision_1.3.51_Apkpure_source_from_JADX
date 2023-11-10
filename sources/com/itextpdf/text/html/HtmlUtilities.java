package com.itextpdf.text.html;

import com.itextpdf.text.BaseColor;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

@Deprecated
public class HtmlUtilities {
    public static final float DEFAULT_FONT_SIZE = 12.0f;
    public static final int[] FONTSIZES = {8, 10, 12, 14, 18, 24, 36};
    private static HashMap<String, Float> sizes;

    static {
        HashMap<String, Float> hashMap = new HashMap<>();
        sizes = hashMap;
        hashMap.put("xx-small", new Float(4.0f));
        sizes.put("x-small", new Float(6.0f));
        sizes.put("small", new Float(8.0f));
        sizes.put("medium", new Float(10.0f));
        sizes.put("large", new Float(13.0f));
        sizes.put("x-large", new Float(18.0f));
        sizes.put("xx-large", new Float(26.0f));
    }

    public static float parseLength(String str) {
        return parseLength(str, 12.0f);
    }

    public static float parseLength(String str, float f) {
        if (str == null) {
            return 0.0f;
        }
        Float f2 = sizes.get(str.toLowerCase());
        if (f2 != null) {
            return f2.floatValue();
        }
        int length = str.length();
        boolean z = true;
        int i = 0;
        while (z && i < length) {
            switch (str.charAt(i)) {
                case '+':
                case '-':
                case '.':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    i++;
                    break;
                default:
                    z = false;
                    break;
            }
        }
        if (i == 0) {
            return 0.0f;
        }
        if (i == length) {
            return Float.parseFloat(str + "f");
        }
        float parseFloat = Float.parseFloat(str.substring(0, i) + "f");
        String substring = str.substring(i);
        if (substring.startsWith("in")) {
            return parseFloat * 72.0f;
        }
        if (substring.startsWith("cm")) {
            return (parseFloat / 2.54f) * 72.0f;
        }
        if (substring.startsWith("mm")) {
            return (parseFloat / 25.4f) * 72.0f;
        }
        if (substring.startsWith("pc")) {
            return parseFloat * 12.0f;
        }
        if (substring.startsWith(HtmlTags.f478EM)) {
            return parseFloat * f;
        }
        return substring.startsWith("ex") ? (parseFloat * f) / 2.0f : parseFloat;
    }

    public static BaseColor decodeColor(String str) {
        if (str == null) {
            return null;
        }
        try {
            return WebColors.getRGBColor(str.toLowerCase().trim());
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public static Properties parseAttributes(String str) {
        Properties properties = new Properties();
        if (str == null) {
            return properties;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
        while (stringTokenizer.hasMoreTokens()) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), ":");
            if (stringTokenizer2.hasMoreTokens()) {
                String trim = stringTokenizer2.nextToken().trim();
                if (stringTokenizer2.hasMoreTokens()) {
                    String trim2 = stringTokenizer2.nextToken().trim();
                    if (trim2.startsWith("\"")) {
                        trim2 = trim2.substring(1);
                    }
                    if (trim2.endsWith("\"")) {
                        trim2 = trim2.substring(0, trim2.length() - 1);
                    }
                    properties.setProperty(trim.toLowerCase(), trim2);
                }
            }
        }
        return properties;
    }

    public static String removeComment(String str, String str2, String str3) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = str3.length();
        int i = 0;
        int indexOf = str.indexOf(str2, 0);
        while (indexOf > -1) {
            stringBuffer.append(str.substring(i, indexOf));
            i = str.indexOf(str3, indexOf) + length;
            indexOf = str.indexOf(str2, i);
        }
        stringBuffer.append(str.substring(i));
        return stringBuffer.toString();
    }

    public static String eliminateWhiteSpace(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt != 9) {
                if (charAt != 10) {
                    if (charAt != 13) {
                        if (charAt != ' ') {
                            stringBuffer.append(charAt);
                            z = false;
                        } else if (!z) {
                            stringBuffer.append(charAt);
                        }
                    }
                } else if (i > 0) {
                    stringBuffer.append(' ');
                    z = true;
                }
            }
        }
        return stringBuffer.toString();
    }

    public static int getIndexedFontSize(String str, String str2) {
        int i;
        int i2 = 0;
        if (str.startsWith("+") || str.startsWith("-")) {
            if (str2 == null) {
                str2 = "12";
            }
            int parseFloat = (int) Float.parseFloat(str2);
            int length = FONTSIZES.length - 1;
            while (true) {
                if (length < 0) {
                    length = 0;
                    break;
                } else if (parseFloat >= FONTSIZES[length]) {
                    break;
                } else {
                    length--;
                }
            }
            if (str.startsWith("+")) {
                str = str.substring(1);
            }
            i = Integer.parseInt(str) + length;
        } else {
            try {
                i = Integer.parseInt(str) - 1;
            } catch (NumberFormatException unused) {
                i = 0;
            }
        }
        if (i >= 0) {
            int[] iArr = FONTSIZES;
            i2 = i >= iArr.length ? iArr.length - 1 : i;
        }
        return FONTSIZES[i2];
    }

    public static int alignmentValue(String str) {
        if (str == null) {
            return -1;
        }
        if (HtmlTags.ALIGN_CENTER.equalsIgnoreCase(str)) {
            return 1;
        }
        if (HtmlTags.ALIGN_LEFT.equalsIgnoreCase(str)) {
            return 0;
        }
        if (HtmlTags.ALIGN_RIGHT.equalsIgnoreCase(str)) {
            return 2;
        }
        if (HtmlTags.ALIGN_JUSTIFY.equalsIgnoreCase(str)) {
            return 3;
        }
        if (HtmlTags.ALIGN_JUSTIFIED_ALL.equalsIgnoreCase(str)) {
            return 8;
        }
        if (HtmlTags.ALIGN_TOP.equalsIgnoreCase(str)) {
            return 4;
        }
        if (HtmlTags.ALIGN_MIDDLE.equalsIgnoreCase(str)) {
            return 5;
        }
        if (HtmlTags.ALIGN_BOTTOM.equalsIgnoreCase(str)) {
            return 6;
        }
        if (HtmlTags.ALIGN_BASELINE.equalsIgnoreCase(str)) {
            return 7;
        }
        return -1;
    }
}
