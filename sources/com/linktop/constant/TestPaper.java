package com.linktop.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.NoSuchElementException;

public class TestPaper {
    private final String mCode;
    private final String mManufacturer;

    public static class Code {
        public static final String C00 = "C00";
        public static final String C01 = "C01";
        public static final String C02 = "C02";
        public static final String C03 = "C03";
        public static final String C04 = "C04";
        public static final String C05 = "C05";
        public static final String C06 = "C06";
        public static final String C07 = "C07";
        public static final String C08 = "C08";
        public static final String C09 = "C09";
        public static final String C10 = "C10";
        public static final String C11 = "C11";
        public static final String C12 = "C12";
        public static final String C13 = "C13";
        public static final String C14 = "C14";
        public static final String C15 = "C15";
        public static final String C16 = "C16";
        public static final String C17 = "C17";
        public static final String C18 = "C18";
        public static final String C19 = "C19";
        public static final String C20 = "C20";
        public static final String C21 = "C21";
        public static final String C22 = "C22";
        public static final String C23 = "C23";
        public static final String C24 = "C24";
        public static final String C25 = "C25";
        public static final String C26 = "C26";
        public static final String C27 = "C27";
        public static final String C28 = "C28";
        public static final String C29 = "C29";
        public static final String C30 = "C30";
        public static final String C31 = "C31";
        public static final String C32 = "C32";
        public static final String C33 = "C33";
        public static final String C34 = "C34";
        public static final String C35 = "C35";
        public static final String C3E2 = "C3E2";
        public static final String C5F2 = "C5F2";
        public static final String CHECK3 = "CHECK3";
        public static final String _9CC3 = "9CC3";
        public static final String b0f3 = "b0f3";
        public static final String b141 = "b141";
        private static final String[] testPaperCode_HMD_bg = {CHECK3};
        private static final String[] testPaperCode_bene_bg = {_9CC3, b0f3};
        private static final String[] testPaperCode_bene_chol = {C3E2, C5F2};
        private static final String[] testPaperCode_bene_ua = {b141};
        private static final String[] testPaperCode_yc_bg = {C00, C01, C02, C03, C04, C05, C06, C07, C08, C09, C10, C11, C12, C13, C14, C15, C16, C17, C18, C19, C20, C21, C22, C23, C24, C25, C26, C27, C28, C29, C30, C31, C32, C33, C34, C35};

        @Retention(RetentionPolicy.SOURCE)
        public @interface Value {
        }

        public static int indexOf(String[] strArr, String str) {
            for (int i = 0; i < strArr.length; i++) {
                if (strArr[i].equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        public static String[] values(String str, int i) {
            str.hashCode();
            str.hashCode();
            char c = 65535;
            switch (str.hashCode()) {
                case -1585252925:
                    if (str.equals(Manufacturer.BENE_CHECK)) {
                        c = 0;
                        break;
                    }
                    break;
                case 71647:
                    if (str.equals(Manufacturer.HMD)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1974747242:
                    if (str.equals(Manufacturer.YI_CHENG)) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    if (i == 3) {
                        return testPaperCode_bene_bg;
                    }
                    if (i == 8) {
                        return testPaperCode_bene_ua;
                    }
                    if (i == 9) {
                        return testPaperCode_bene_chol;
                    }
                    break;
                case 1:
                    if (i == 3) {
                        return testPaperCode_HMD_bg;
                    }
                    break;
                case 2:
                    if (i == 3) {
                        return testPaperCode_yc_bg;
                    }
                    break;
            }
            throw new NoSuchElementException("No such test paper collection for manufacturer = " + str + ", measureType = " + i);
        }
    }

    public static class Manufacturer {
        public static final String BENE_CHECK = "Bene_Check";
        public static final String HMD = "HMD";
        public static final String YI_CHENG = "Yi_Cheng";
        private static final String[] values = {BENE_CHECK, HMD, YI_CHENG};

        @Retention(RetentionPolicy.SOURCE)
        public @interface Name {
        }

        public static int indexOf(String str) {
            int i = 0;
            while (true) {
                String[] strArr = values;
                if (i >= strArr.length) {
                    return -1;
                }
                if (strArr[i].equals(str)) {
                    return i;
                }
                i++;
            }
        }

        public static String[] values() {
            return values;
        }
    }

    private TestPaper(String str, String str2) {
        this.mManufacturer = str;
        this.mCode = str2;
    }

    public static TestPaper create(String str, String str2) {
        return new TestPaper(str, str2);
    }

    public String getCode() {
        return this.mCode;
    }

    public String getManufacturer() {
        return this.mManufacturer;
    }

    public String toString() {
        return "TestPaper{manufacturer='" + this.mManufacturer + '\'' + ", code='" + this.mCode + '\'' + '}';
    }
}
