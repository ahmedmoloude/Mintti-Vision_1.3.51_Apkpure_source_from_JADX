package com.itextpdf.text.pdf.languages;

public abstract class IndicLigaturizer implements LanguageProcessor {
    public static final int HALANTA = 10;
    public static final int LETTER_A = 6;
    public static final int LETTER_AU = 7;
    public static final int LETTER_HA = 9;
    public static final int LETTER_KA = 8;
    public static final int MATRA_AA = 0;
    public static final int MATRA_AI = 3;
    public static final int MATRA_E = 2;
    public static final int MATRA_HLR = 4;
    public static final int MATRA_HLRR = 5;
    public static final int MATRA_I = 1;
    protected char[] langTable;

    public boolean isRTL() {
        return false;
    }

    public String process(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (IsVyanjana(charAt) || IsSwaraLetter(charAt)) {
                sb.append(charAt);
            } else if (IsSwaraMatra(charAt)) {
                int length = sb.length() - 1;
                if (length >= 0) {
                    if (sb.charAt(length) == this.langTable[10]) {
                        sb.deleteCharAt(length);
                    }
                    sb.append(charAt);
                    int length2 = sb.length() - 2;
                    if (charAt == this.langTable[1] && length2 >= 0) {
                        swap(sb, length2, sb.length() - 1);
                    }
                } else {
                    sb.append(charAt);
                }
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean IsSwaraLetter(char c) {
        char[] cArr = this.langTable;
        return c >= cArr[6] && c <= cArr[7];
    }

    /* access modifiers changed from: protected */
    public boolean IsSwaraMatra(char c) {
        char[] cArr = this.langTable;
        return (c >= cArr[0] && c <= cArr[3]) || c == cArr[4] || c == cArr[5];
    }

    /* access modifiers changed from: protected */
    public boolean IsVyanjana(char c) {
        char[] cArr = this.langTable;
        return c >= cArr[8] && c <= cArr[9];
    }

    private static void swap(StringBuilder sb, int i, int i2) {
        char charAt = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(i2));
        sb.setCharAt(i2, charAt);
    }
}
