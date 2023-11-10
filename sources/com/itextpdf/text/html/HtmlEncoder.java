package com.itextpdf.text.html;

import com.itextpdf.text.BaseColor;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public final class HtmlEncoder {
    private static final String[] HTML_CODE = new String[256];
    private static final Set<String> NEWLINETAGS;

    public static String getAlignment(int i) {
        switch (i) {
            case 0:
                return HtmlTags.ALIGN_LEFT;
            case 1:
                return HtmlTags.ALIGN_CENTER;
            case 2:
                return HtmlTags.ALIGN_RIGHT;
            case 3:
            case 8:
                return HtmlTags.ALIGN_JUSTIFY;
            case 4:
                return HtmlTags.ALIGN_TOP;
            case 5:
                return HtmlTags.ALIGN_MIDDLE;
            case 6:
                return HtmlTags.ALIGN_BOTTOM;
            case 7:
                return HtmlTags.ALIGN_BASELINE;
            default:
                return "";
        }
    }

    private HtmlEncoder() {
    }

    static {
        int i;
        int i2;
        for (int i3 = 0; i3 < 10; i3++) {
            String[] strArr = HTML_CODE;
            strArr[i3] = "&#00" + i3 + ";";
        }
        int i4 = 10;
        while (true) {
            i = 32;
            if (i4 >= 32) {
                break;
            }
            String[] strArr2 = HTML_CODE;
            strArr2[i4] = "&#0" + i4 + ";";
            i4++;
        }
        while (true) {
            if (i >= 128) {
                break;
            }
            HTML_CODE[i] = String.valueOf((char) i);
            i++;
        }
        String[] strArr3 = HTML_CODE;
        strArr3[9] = "\t";
        strArr3[10] = "<br />\n";
        strArr3[34] = "&quot;";
        strArr3[38] = "&amp;";
        strArr3[60] = "&lt;";
        strArr3[62] = "&gt;";
        for (i2 = 128; i2 < 256; i2++) {
            String[] strArr4 = HTML_CODE;
            strArr4[i2] = "&#" + i2 + ";";
        }
        HashSet hashSet = new HashSet();
        NEWLINETAGS = hashSet;
        hashSet.add(HtmlTags.f489P);
        hashSet.add(HtmlTags.BLOCKQUOTE);
        hashSet.add(HtmlTags.f477BR);
    }

    public static String encode(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < 256) {
                stringBuffer.append(HTML_CODE[charAt]);
            } else {
                stringBuffer.append("&#");
                stringBuffer.append(charAt);
                stringBuffer.append(';');
            }
        }
        return stringBuffer.toString();
    }

    public static String encode(BaseColor baseColor) {
        StringBuffer stringBuffer = new StringBuffer("#");
        if (baseColor.getRed() < 16) {
            stringBuffer.append('0');
        }
        stringBuffer.append(Integer.toString(baseColor.getRed(), 16));
        if (baseColor.getGreen() < 16) {
            stringBuffer.append('0');
        }
        stringBuffer.append(Integer.toString(baseColor.getGreen(), 16));
        if (baseColor.getBlue() < 16) {
            stringBuffer.append('0');
        }
        stringBuffer.append(Integer.toString(baseColor.getBlue(), 16));
        return stringBuffer.toString();
    }

    public static boolean isNewLineTag(String str) {
        return NEWLINETAGS.contains(str);
    }
}
