package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.p020kl.healthmonitor.C1633R2;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class Barcode128 extends Barcode {
    private static final byte[][] BARS = {new byte[]{2, 1, 2, 2, 2, 2}, new byte[]{2, 2, 2, 1, 2, 2}, new byte[]{2, 2, 2, 2, 2, 1}, new byte[]{1, 2, 1, 2, 2, 3}, new byte[]{1, 2, 1, 3, 2, 2}, new byte[]{1, 3, 1, 2, 2, 2}, new byte[]{1, 2, 2, 2, 1, 3}, new byte[]{1, 2, 2, 3, 1, 2}, new byte[]{1, 3, 2, 2, 1, 2}, new byte[]{2, 2, 1, 2, 1, 3}, new byte[]{2, 2, 1, 3, 1, 2}, new byte[]{2, 3, 1, 2, 1, 2}, new byte[]{1, 1, 2, 2, 3, 2}, new byte[]{1, 2, 2, 1, 3, 2}, new byte[]{1, 2, 2, 2, 3, 1}, new byte[]{1, 1, 3, 2, 2, 2}, new byte[]{1, 2, 3, 1, 2, 2}, new byte[]{1, 2, 3, 2, 2, 1}, new byte[]{2, 2, 3, 2, 1, 1}, new byte[]{2, 2, 1, 1, 3, 2}, new byte[]{2, 2, 1, 2, 3, 1}, new byte[]{2, 1, 3, 2, 1, 2}, new byte[]{2, 2, 3, 1, 1, 2}, new byte[]{3, 1, 2, 1, 3, 1}, new byte[]{3, 1, 1, 2, 2, 2}, new byte[]{3, 2, 1, 1, 2, 2}, new byte[]{3, 2, 1, 2, 2, 1}, new byte[]{3, 1, 2, 2, 1, 2}, new byte[]{3, 2, 2, 1, 1, 2}, new byte[]{3, 2, 2, 2, 1, 1}, new byte[]{2, 1, 2, 1, 2, 3}, new byte[]{2, 1, 2, 3, 2, 1}, new byte[]{2, 3, 2, 1, 2, 1}, new byte[]{1, 1, 1, 3, 2, 3}, new byte[]{1, 3, 1, 1, 2, 3}, new byte[]{1, 3, 1, 3, 2, 1}, new byte[]{1, 1, 2, 3, 1, 3}, new byte[]{1, 3, 2, 1, 1, 3}, new byte[]{1, 3, 2, 3, 1, 1}, new byte[]{2, 1, 1, 3, 1, 3}, new byte[]{2, 3, 1, 1, 1, 3}, new byte[]{2, 3, 1, 3, 1, 1}, new byte[]{1, 1, 2, 1, 3, 3}, new byte[]{1, 1, 2, 3, 3, 1}, new byte[]{1, 3, 2, 1, 3, 1}, new byte[]{1, 1, 3, 1, 2, 3}, new byte[]{1, 1, 3, 3, 2, 1}, new byte[]{1, 3, 3, 1, 2, 1}, new byte[]{3, 1, 3, 1, 2, 1}, new byte[]{2, 1, 1, 3, 3, 1}, new byte[]{2, 3, 1, 1, 3, 1}, new byte[]{2, 1, 3, 1, 1, 3}, new byte[]{2, 1, 3, 3, 1, 1}, new byte[]{2, 1, 3, 1, 3, 1}, new byte[]{3, 1, 1, 1, 2, 3}, new byte[]{3, 1, 1, 3, 2, 1}, new byte[]{3, 3, 1, 1, 2, 1}, new byte[]{3, 1, 2, 1, 1, 3}, new byte[]{3, 1, 2, 3, 1, 1}, new byte[]{3, 3, 2, 1, 1, 1}, new byte[]{3, 1, 4, 1, 1, 1}, new byte[]{2, 2, 1, 4, 1, 1}, new byte[]{4, 3, 1, 1, 1, 1}, new byte[]{1, 1, 1, 2, 2, 4}, new byte[]{1, 1, 1, 4, 2, 2}, new byte[]{1, 2, 1, 1, 2, 4}, new byte[]{1, 2, 1, 4, 2, 1}, new byte[]{1, 4, 1, 1, 2, 2}, new byte[]{1, 4, 1, 2, 2, 1}, new byte[]{1, 1, 2, 2, 1, 4}, new byte[]{1, 1, 2, 4, 1, 2}, new byte[]{1, 2, 2, 1, 1, 4}, new byte[]{1, 2, 2, 4, 1, 1}, new byte[]{1, 4, 2, 1, 1, 2}, new byte[]{1, 4, 2, 2, 1, 1}, new byte[]{2, 4, 1, 2, 1, 1}, new byte[]{2, 2, 1, 1, 1, 4}, new byte[]{4, 1, 3, 1, 1, 1}, new byte[]{2, 4, 1, 1, 1, 2}, new byte[]{1, 3, 4, 1, 1, 1}, new byte[]{1, 1, 1, 2, 4, 2}, new byte[]{1, 2, 1, 1, 4, 2}, new byte[]{1, 2, 1, 2, 4, 1}, new byte[]{1, 1, 4, 2, 1, 2}, new byte[]{1, 2, 4, 1, 1, 2}, new byte[]{1, 2, 4, 2, 1, 1}, new byte[]{4, 1, 1, 2, 1, 2}, new byte[]{4, 2, 1, 1, 1, 2}, new byte[]{4, 2, 1, 2, 1, 1}, new byte[]{2, 1, 2, 1, 4, 1}, new byte[]{2, 1, 4, 1, 2, 1}, new byte[]{4, 1, 2, 1, 2, 1}, new byte[]{1, 1, 1, 1, 4, 3}, new byte[]{1, 1, 1, 3, 4, 1}, new byte[]{1, 3, 1, 1, 4, 1}, new byte[]{1, 1, 4, 1, 1, 3}, new byte[]{1, 1, 4, 3, 1, 1}, new byte[]{4, 1, 1, 1, 1, 3}, new byte[]{4, 1, 1, 3, 1, 1}, new byte[]{1, 1, 3, 1, 4, 1}, new byte[]{1, 1, 4, 1, 3, 1}, new byte[]{3, 1, 1, 1, 4, 1}, new byte[]{4, 1, 1, 1, 3, 1}, new byte[]{2, 1, 1, 4, 1, 2}, new byte[]{2, 1, 1, 2, 1, 4}, new byte[]{2, 1, 1, 2, 3, 2}};
    private static final byte[] BARS_STOP = {2, 3, 3, 1, 1, 1, 2};
    public static final char CODE_A = 'È';
    public static final char CODE_AB_TO_C = 'c';
    public static final char CODE_AC_TO_B = 'd';
    public static final char CODE_BC_TO_A = 'e';
    public static final char CODE_C = 'Ç';
    public static final char DEL = 'Ã';
    public static final char FNC1 = 'Ê';
    public static final char FNC1_INDEX = 'f';
    public static final char FNC2 = 'Å';
    public static final char FNC3 = 'Ä';
    public static final char FNC4 = 'È';
    public static final char SHIFT = 'Æ';
    public static final char STARTA = 'Ë';
    public static final char STARTB = 'Ì';
    public static final char STARTC = 'Í';
    public static final char START_A = 'g';
    public static final char START_B = 'h';
    public static final char START_C = 'i';
    private static final IntHashtable ais;
    private Barcode128CodeSet codeSet = Barcode128CodeSet.AUTO;

    static {
        IntHashtable intHashtable = new IntHashtable();
        ais = intHashtable;
        intHashtable.put(0, 20);
        intHashtable.put(1, 16);
        intHashtable.put(2, 16);
        intHashtable.put(10, -1);
        intHashtable.put(11, 9);
        intHashtable.put(12, 8);
        intHashtable.put(13, 8);
        intHashtable.put(15, 8);
        intHashtable.put(17, 8);
        intHashtable.put(20, 4);
        intHashtable.put(21, -1);
        intHashtable.put(22, -1);
        intHashtable.put(23, -1);
        intHashtable.put(240, -1);
        intHashtable.put(241, -1);
        intHashtable.put(250, -1);
        intHashtable.put(251, -1);
        intHashtable.put(252, -1);
        intHashtable.put(30, -1);
        for (int i = 3100; i < 3700; i++) {
            ais.put(i, 10);
        }
        ais.put(37, -1);
        for (int i2 = 3900; i2 < 3940; i2++) {
            ais.put(i2, -1);
        }
        IntHashtable intHashtable2 = ais;
        intHashtable2.put(400, -1);
        intHashtable2.put(401, -1);
        intHashtable2.put(402, 20);
        intHashtable2.put(403, -1);
        for (int i3 = 410; i3 < 416; i3++) {
            ais.put(i3, 16);
        }
        IntHashtable intHashtable3 = ais;
        intHashtable3.put(420, -1);
        intHashtable3.put(421, -1);
        intHashtable3.put(422, 6);
        intHashtable3.put(423, -1);
        intHashtable3.put(424, 6);
        intHashtable3.put(425, 6);
        intHashtable3.put(426, 6);
        intHashtable3.put(C1633R2.styleable.NavigationView_elevation, 17);
        intHashtable3.put(C1633R2.styleable.NavigationView_headerLayout, -1);
        for (int i4 = C1633R2.styleable.OnSwipe_maxVelocity; i4 < 7040; i4++) {
            ais.put(i4, -1);
        }
        IntHashtable intHashtable4 = ais;
        intHashtable4.put(8001, 18);
        intHashtable4.put(8002, -1);
        intHashtable4.put(8003, -1);
        intHashtable4.put(8004, -1);
        intHashtable4.put(8005, 10);
        intHashtable4.put(8006, 22);
        intHashtable4.put(8007, -1);
        intHashtable4.put(8008, -1);
        intHashtable4.put(8018, 22);
        intHashtable4.put(8020, -1);
        intHashtable4.put(8100, 10);
        intHashtable4.put(8101, 14);
        intHashtable4.put(8102, 6);
        for (int i5 = 90; i5 < 100; i5++) {
            ais.put(i5, -1);
        }
    }

    public Barcode128() {
        try {
            this.f504x = 0.8f;
            this.font = BaseFont.createFont("Helvetica", "winansi", false);
            this.size = 8.0f;
            this.baseline = this.size;
            this.barHeight = this.size * 3.0f;
            this.textAlignment = 1;
            this.codeType = 9;
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* renamed from: com.itextpdf.text.pdf.Barcode128$1 */
    static /* synthetic */ class C15141 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$pdf$Barcode128$Barcode128CodeSet;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet[] r0 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$pdf$Barcode128$Barcode128CodeSet = r0
                com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r1 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.A     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$Barcode128$Barcode128CodeSet     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r1 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.B     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$Barcode128$Barcode128CodeSet     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r1 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.C     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.Barcode128.C15141.<clinit>():void");
        }
    }

    public enum Barcode128CodeSet {
        A,
        B,
        C,
        AUTO;

        public char getStartSymbol() {
            int i = C15141.$SwitchMap$com$itextpdf$text$pdf$Barcode128$Barcode128CodeSet[ordinal()];
            if (i != 1) {
                return i != 3 ? Barcode128.START_B : Barcode128.START_C;
            }
            return Barcode128.START_A;
        }
    }

    public void setCodeSet(Barcode128CodeSet barcode128CodeSet) {
        this.codeSet = barcode128CodeSet;
    }

    public Barcode128CodeSet getCodeSet() {
        return this.codeSet;
    }

    public static String removeFNC1(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt >= ' ' && charAt <= '~') {
                stringBuffer.append(charAt);
            }
        }
        return stringBuffer.toString();
    }

    public static String getHumanReadableUCCEAN(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        String valueOf = String.valueOf(FNC1);
        while (true) {
            try {
                if (str.startsWith(valueOf)) {
                    str = str.substring(1);
                } else {
                    int i = 2;
                    int i2 = 0;
                    while (true) {
                        if (i >= 5) {
                            break;
                        } else if (str.length() < i) {
                            break;
                        } else {
                            i2 = ais.get(Integer.parseInt(str.substring(0, i)));
                            if (i2 != 0) {
                                break;
                            }
                            i++;
                        }
                    }
                    i = 0;
                    if (i == 0) {
                        break;
                    }
                    stringBuffer.append('(');
                    stringBuffer.append(str.substring(0, i));
                    stringBuffer.append(')');
                    str = str.substring(i);
                    if (i2 > 0) {
                        int i3 = i2 - i;
                        if (str.length() <= i3) {
                            break;
                        }
                        stringBuffer.append(removeFNC1(str.substring(0, i3)));
                        str = str.substring(i3);
                    } else {
                        int indexOf = str.indexOf(202);
                        if (indexOf < 0) {
                            break;
                        }
                        stringBuffer.append(str.substring(0, indexOf));
                        str = str.substring(indexOf + 1);
                    }
                }
            } catch (Exception unused) {
            }
        }
        stringBuffer.append(removeFNC1(str));
        return stringBuffer.toString();
    }

    static boolean isNextDigits(String str, int i, int i2) {
        int length = str.length();
        loop0:
        while (i < length && i2 > 0) {
            if (str.charAt(i) == 202) {
                i++;
            } else {
                int min = Math.min(2, i2);
                if (i + min > length) {
                    return false;
                }
                while (true) {
                    int i3 = min - 1;
                    if (min <= 0) {
                        continue;
                        break;
                    }
                    int i4 = i + 1;
                    char charAt = str.charAt(i);
                    if (charAt < '0' || charAt > '9') {
                        return false;
                    }
                    i2--;
                    i = i4;
                    min = i3;
                }
                return false;
            }
        }
        if (i2 == 0) {
            return true;
        }
        return false;
    }

    static String getPackedRawDigits(String str, int i, int i2) {
        StringBuilder sb = new StringBuilder("");
        int i3 = i;
        while (i2 > 0) {
            if (str.charAt(i3) == 202) {
                sb.append(FNC1_INDEX);
                i3++;
            } else {
                i2 -= 2;
                int i4 = i3 + 1;
                sb.append((char) (((str.charAt(i3) - '0') * 10) + (str.charAt(i4) - '0')));
                i3 = i4 + 1;
            }
        }
        return ((char) (i3 - i)) + sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0228, code lost:
        r9 = START_A;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0276, code lost:
        r9 = START_C;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x02b8, code lost:
        r9 = START_B;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x02e5, code lost:
        if (r1 == com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.AUTO) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x02eb, code lost:
        if (r9 != r18.getStartSymbol()) goto L_0x02ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x02fc, code lost:
        throw new java.lang.RuntimeException(com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage("there.are.illegal.characters.for.barcode.128.in.1", r0));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getRawText(java.lang.String r16, boolean r17, com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet r18) {
        /*
            r0 = r16
            r1 = r18
            int r2 = r16.length()
            r3 = 102(0x66, float:1.43E-43)
            if (r2 != 0) goto L_0x0033
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = ""
            r0.append(r2)
            char r1 = r18.getStartSymbol()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            if (r17 == 0) goto L_0x0032
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            r1.append(r3)
            java.lang.String r0 = r1.toString()
        L_0x0032:
            return r0
        L_0x0033:
            r4 = 0
            r5 = 0
        L_0x0035:
            java.lang.String r6 = "there.are.illegal.characters.for.barcode.128.in.1"
            r7 = 202(0xca, float:2.83E-43)
            r8 = 1
            if (r5 >= r2) goto L_0x0059
            char r9 = r0.charAt(r5)
            r10 = 127(0x7f, float:1.78E-43)
            if (r9 <= r10) goto L_0x0056
            if (r9 != r7) goto L_0x0048
            goto L_0x0056
        L_0x0048:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.Object[] r2 = new java.lang.Object[r8]
            r2[r4] = r0
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r6, (java.lang.Object[]) r2)
            r1.<init>(r0)
            throw r1
        L_0x0056:
            int r5 = r5 + 1
            goto L_0x0035
        L_0x0059:
            char r5 = r0.charAt(r4)
            com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r9 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.AUTO
            r13 = 2
            r14 = 32
            if (r1 == r9) goto L_0x0068
            com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r9 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.C
            if (r1 != r9) goto L_0x00a0
        L_0x0068:
            boolean r9 = isNextDigits(r0, r4, r13)
            if (r9 == 0) goto L_0x00a0
            java.lang.String r5 = "i"
            if (r17 == 0) goto L_0x0081
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            r9.append(r3)
            java.lang.String r5 = r9.toString()
        L_0x0081:
            java.lang.String r9 = getPackedRawDigits(r0, r4, r13)
            char r15 = r9.charAt(r4)
            int r15 = r15 + r4
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r5)
            java.lang.String r5 = r9.substring(r8)
            r10.append(r5)
            java.lang.String r5 = r10.toString()
            r9 = 105(0x69, float:1.47E-43)
            goto L_0x0104
        L_0x00a0:
            if (r5 >= r14) goto L_0x00cb
            java.lang.String r9 = "g"
            if (r17 == 0) goto L_0x00b5
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            r10.append(r3)
            java.lang.String r9 = r10.toString()
        L_0x00b5:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            int r5 = r5 + 64
            char r5 = (char) r5
            r10.append(r5)
            java.lang.String r5 = r10.toString()
            r9 = 103(0x67, float:1.44E-43)
        L_0x00c9:
            r15 = 1
            goto L_0x0104
        L_0x00cb:
            java.lang.String r9 = "h"
            if (r17 == 0) goto L_0x00de
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            r10.append(r3)
            java.lang.String r9 = r10.toString()
        L_0x00de:
            if (r5 != r7) goto L_0x00f0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r9)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            goto L_0x0101
        L_0x00f0:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            int r5 = r5 - r14
            char r5 = (char) r5
            r10.append(r5)
            java.lang.String r5 = r10.toString()
        L_0x0101:
            r9 = 104(0x68, float:1.46E-43)
            goto L_0x00c9
        L_0x0104:
            com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r10 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.AUTO
            if (r1 == r10) goto L_0x011d
            char r10 = r18.getStartSymbol()
            if (r9 != r10) goto L_0x010f
            goto L_0x011d
        L_0x010f:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.Object[] r2 = new java.lang.Object[r8]
            r2[r4] = r0
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r6, (java.lang.Object[]) r2)
            r1.<init>(r0)
            throw r1
        L_0x011d:
            if (r15 >= r2) goto L_0x02fd
            r10 = 101(0x65, float:1.42E-43)
            r11 = 100
            r12 = 4
            switch(r9) {
                case 103: goto L_0x023f;
                case 104: goto L_0x01b1;
                case 105: goto L_0x0129;
                default: goto L_0x0127;
            }
        L_0x0127:
            goto L_0x02e3
        L_0x0129:
            boolean r12 = isNextDigits(r0, r15, r13)
            if (r12 == 0) goto L_0x014d
            java.lang.String r10 = getPackedRawDigits(r0, r15, r13)
            char r11 = r10.charAt(r4)
            int r15 = r15 + r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r5)
            java.lang.String r5 = r10.substring(r8)
            r11.append(r5)
            java.lang.String r5 = r11.toString()
            goto L_0x02e3
        L_0x014d:
            int r12 = r15 + 1
            char r15 = r0.charAt(r15)
            if (r15 != r7) goto L_0x0167
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r5)
            r10.append(r3)
            java.lang.String r5 = r10.toString()
            r15 = r12
            goto L_0x02e3
        L_0x0167:
            if (r15 >= r14) goto L_0x018d
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            r9.append(r10)
            java.lang.String r5 = r9.toString()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            int r15 = r15 + 64
            char r5 = (char) r15
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            r15 = r12
            goto L_0x0228
        L_0x018d:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            r9.append(r11)
            java.lang.String r5 = r9.toString()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            int r15 = r15 + -32
            char r5 = (char) r15
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            r15 = r12
            goto L_0x02b8
        L_0x01b1:
            com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r11 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.AUTO
            if (r1 != r11) goto L_0x01ea
            boolean r11 = isNextDigits(r0, r15, r12)
            if (r11 == 0) goto L_0x01ea
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            r5 = 99
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            java.lang.String r9 = getPackedRawDigits(r0, r15, r12)
            char r10 = r9.charAt(r4)
            int r15 = r15 + r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r5)
            java.lang.String r5 = r9.substring(r8)
            r10.append(r5)
            java.lang.String r5 = r10.toString()
            goto L_0x0276
        L_0x01ea:
            int r11 = r15 + 1
            char r12 = r0.charAt(r15)
            if (r12 != r7) goto L_0x0204
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r5)
            r10.append(r3)
            java.lang.String r5 = r10.toString()
        L_0x0201:
            r15 = r11
            goto L_0x02e3
        L_0x0204:
            if (r12 >= r14) goto L_0x022c
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            r9.append(r10)
            java.lang.String r5 = r9.toString()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            int r12 = r12 + 64
            char r5 = (char) r12
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            r15 = r11
        L_0x0228:
            r9 = 103(0x67, float:1.44E-43)
            goto L_0x02e3
        L_0x022c:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r5)
            int r12 = r12 + -32
            char r5 = (char) r12
            r10.append(r5)
            java.lang.String r5 = r10.toString()
            goto L_0x0201
        L_0x023f:
            com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r10 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.AUTO
            if (r1 != r10) goto L_0x0279
            boolean r10 = isNextDigits(r0, r15, r12)
            if (r10 == 0) goto L_0x0279
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            r5 = 99
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            java.lang.String r9 = getPackedRawDigits(r0, r15, r12)
            char r10 = r9.charAt(r4)
            int r15 = r15 + r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r5)
            java.lang.String r5 = r9.substring(r8)
            r10.append(r5)
            java.lang.String r5 = r10.toString()
        L_0x0276:
            r9 = 105(0x69, float:1.47E-43)
            goto L_0x02e3
        L_0x0279:
            int r10 = r15 + 1
            char r12 = r0.charAt(r15)
            if (r12 != r7) goto L_0x0292
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r5)
            r11.append(r3)
            java.lang.String r5 = r11.toString()
        L_0x0290:
            r15 = r10
            goto L_0x02e3
        L_0x0292:
            r15 = 95
            if (r12 <= r15) goto L_0x02bb
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            r9.append(r11)
            java.lang.String r5 = r9.toString()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r5)
            int r12 = r12 + -32
            char r5 = (char) r12
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            r15 = r10
        L_0x02b8:
            r9 = 104(0x68, float:1.46E-43)
            goto L_0x02e3
        L_0x02bb:
            if (r12 >= r14) goto L_0x02d0
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r5)
            int r12 = r12 + 64
            char r5 = (char) r12
            r11.append(r5)
            java.lang.String r5 = r11.toString()
            goto L_0x0290
        L_0x02d0:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r5)
            int r12 = r12 + -32
            char r5 = (char) r12
            r11.append(r5)
            java.lang.String r5 = r11.toString()
            goto L_0x0290
        L_0x02e3:
            com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r10 = com.itextpdf.text.pdf.Barcode128.Barcode128CodeSet.AUTO
            if (r1 == r10) goto L_0x011d
            char r10 = r18.getStartSymbol()
            if (r9 != r10) goto L_0x02ef
            goto L_0x011d
        L_0x02ef:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.Object[] r2 = new java.lang.Object[r8]
            r2[r4] = r0
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r6, (java.lang.Object[]) r2)
            r1.<init>(r0)
            throw r1
        L_0x02fd:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.Barcode128.getRawText(java.lang.String, boolean, com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet):java.lang.String");
    }

    public static String getRawText(String str, boolean z) {
        return getRawText(str, z, Barcode128CodeSet.AUTO);
    }

    public static byte[] getBarsCode128Raw(String str) {
        int indexOf = str.indexOf(65535);
        if (indexOf >= 0) {
            str = str.substring(0, indexOf);
        }
        int charAt = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            charAt += str.charAt(i) * i;
        }
        String str2 = str + ((char) (charAt % 103));
        byte[] bArr = new byte[(((str2.length() + 1) * 6) + 7)];
        int i2 = 0;
        while (i2 < str2.length()) {
            System.arraycopy(BARS[str2.charAt(i2)], 0, bArr, i2 * 6, 6);
            i2++;
        }
        System.arraycopy(BARS_STOP, 0, bArr, i2 * 6, 7);
        return bArr;
    }

    public Rectangle getBarcodeSize() {
        float f;
        String str;
        float f2;
        String str2;
        boolean z = true;
        float f3 = 0.0f;
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                f2 = this.baseline - this.font.getFontDescriptor(3, this.size);
            } else {
                f2 = (-this.baseline) + this.size;
            }
            float f4 = f2;
            if (this.codeType == 11) {
                int indexOf = this.code.indexOf(65535);
                if (indexOf < 0) {
                    str2 = "";
                } else {
                    str2 = this.code.substring(indexOf + 1);
                }
            } else if (this.codeType == 10) {
                str2 = getHumanReadableUCCEAN(this.code);
            } else {
                str2 = removeFNC1(this.code);
            }
            BaseFont baseFont = this.font;
            if (this.altText != null) {
                str2 = this.altText;
            }
            float f5 = f4;
            f3 = baseFont.getWidthPoint(str2, this.size);
            f = f5;
        } else {
            f = 0.0f;
        }
        if (this.codeType == 11) {
            int indexOf2 = this.code.indexOf(65535);
            if (indexOf2 >= 0) {
                str = this.code.substring(0, indexOf2);
            } else {
                str = this.code;
            }
        } else {
            String str3 = this.code;
            if (this.codeType != 10) {
                z = false;
            }
            str = getRawText(str3, z, this.codeSet);
        }
        return new Rectangle(Math.max((((float) ((str.length() + 2) * 11)) * this.f504x) + (this.f504x * 2.0f), f3), this.barHeight + f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00e7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Rectangle placeBarcode(com.itextpdf.text.pdf.PdfContentByte r12, com.itextpdf.text.BaseColor r13, com.itextpdf.text.BaseColor r14) {
        /*
            r11 = this;
            int r0 = r11.codeType
            r1 = 65535(0xffff, float:9.1834E-41)
            r2 = 10
            r3 = 11
            r4 = 1
            if (r0 != r3) goto L_0x001f
            java.lang.String r0 = r11.code
            int r0 = r0.indexOf(r1)
            if (r0 >= 0) goto L_0x0017
            java.lang.String r0 = ""
            goto L_0x0030
        L_0x0017:
            java.lang.String r5 = r11.code
            int r0 = r0 + r4
            java.lang.String r0 = r5.substring(r0)
            goto L_0x0030
        L_0x001f:
            int r0 = r11.codeType
            if (r0 != r2) goto L_0x002a
            java.lang.String r0 = r11.code
            java.lang.String r0 = getHumanReadableUCCEAN(r0)
            goto L_0x0030
        L_0x002a:
            java.lang.String r0 = r11.code
            java.lang.String r0 = removeFNC1(r0)
        L_0x0030:
            com.itextpdf.text.pdf.BaseFont r5 = r11.font
            r6 = 0
            if (r5 == 0) goto L_0x0044
            com.itextpdf.text.pdf.BaseFont r5 = r11.font
            java.lang.String r7 = r11.altText
            if (r7 == 0) goto L_0x003d
            java.lang.String r0 = r11.altText
        L_0x003d:
            float r7 = r11.size
            float r5 = r5.getWidthPoint((java.lang.String) r0, (float) r7)
            goto L_0x0045
        L_0x0044:
            r5 = 0
        L_0x0045:
            int r7 = r11.codeType
            r8 = 0
            if (r7 != r3) goto L_0x005c
            java.lang.String r2 = r11.code
            int r1 = r2.indexOf(r1)
            if (r1 < 0) goto L_0x0059
            java.lang.String r2 = r11.code
            java.lang.String r1 = r2.substring(r8, r1)
            goto L_0x006b
        L_0x0059:
            java.lang.String r1 = r11.code
            goto L_0x006b
        L_0x005c:
            java.lang.String r1 = r11.code
            int r7 = r11.codeType
            if (r7 != r2) goto L_0x0064
            r2 = 1
            goto L_0x0065
        L_0x0064:
            r2 = 0
        L_0x0065:
            com.itextpdf.text.pdf.Barcode128$Barcode128CodeSet r7 = r11.codeSet
            java.lang.String r1 = getRawText(r1, r2, r7)
        L_0x006b:
            int r2 = r1.length()
            r7 = 2
            int r2 = r2 + r7
            int r2 = r2 * 11
            float r2 = (float) r2
            float r3 = r11.f504x
            float r2 = r2 * r3
            float r3 = r11.f504x
            r9 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 * r9
            float r2 = r2 + r3
            int r3 = r11.textAlignment
            if (r3 == 0) goto L_0x0098
            if (r3 == r7) goto L_0x008f
            int r3 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x008c
            float r5 = r5 - r2
            float r5 = r5 / r9
            goto L_0x0094
        L_0x008c:
            float r2 = r2 - r5
            float r2 = r2 / r9
            goto L_0x0099
        L_0x008f:
            int r3 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x0096
            float r5 = r5 - r2
        L_0x0094:
            r2 = 0
            goto L_0x009a
        L_0x0096:
            float r2 = r2 - r5
            goto L_0x0099
        L_0x0098:
            r2 = 0
        L_0x0099:
            r5 = 0
        L_0x009a:
            com.itextpdf.text.pdf.BaseFont r3 = r11.font
            if (r3 == 0) goto L_0x00bb
            float r3 = r11.baseline
            int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r3 > 0) goto L_0x00aa
            float r3 = r11.barHeight
            float r7 = r11.baseline
            float r3 = r3 - r7
            goto L_0x00bc
        L_0x00aa:
            com.itextpdf.text.pdf.BaseFont r3 = r11.font
            r6 = 3
            float r7 = r11.size
            float r3 = r3.getFontDescriptor(r6, r7)
            float r6 = -r3
            float r3 = r11.baseline
            float r3 = r3 + r6
            r10 = r6
            r6 = r3
            r3 = r10
            goto L_0x00bc
        L_0x00bb:
            r3 = 0
        L_0x00bc:
            byte[] r1 = getBarsCode128Raw(r1)
            if (r13 == 0) goto L_0x00c5
            r12.setColorFill(r13)
        L_0x00c5:
            int r13 = r1.length
            if (r8 >= r13) goto L_0x00e0
            byte r13 = r1[r8]
            float r13 = (float) r13
            float r7 = r11.f504x
            float r13 = r13 * r7
            if (r4 == 0) goto L_0x00da
            float r7 = r11.inkSpreading
            float r7 = r13 - r7
            float r9 = r11.barHeight
            r12.rectangle((float) r5, (float) r6, (float) r7, (float) r9)
        L_0x00da:
            r4 = r4 ^ 1
            float r5 = r5 + r13
            int r8 = r8 + 1
            goto L_0x00c5
        L_0x00e0:
            r12.fill()
            com.itextpdf.text.pdf.BaseFont r13 = r11.font
            if (r13 == 0) goto L_0x00ff
            if (r14 == 0) goto L_0x00ec
            r12.setColorFill(r14)
        L_0x00ec:
            r12.beginText()
            com.itextpdf.text.pdf.BaseFont r13 = r11.font
            float r14 = r11.size
            r12.setFontAndSize(r13, r14)
            r12.setTextMatrix(r2, r3)
            r12.showText((java.lang.String) r0)
            r12.endText()
        L_0x00ff:
            com.itextpdf.text.Rectangle r12 = r11.getBarcodeSize()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.Barcode128.placeBarcode(com.itextpdf.text.pdf.PdfContentByte, com.itextpdf.text.BaseColor, com.itextpdf.text.BaseColor):com.itextpdf.text.Rectangle");
    }

    public void setCode(String str) {
        if (getCodeType() != 10 || !str.startsWith("(")) {
            super.setCode(str);
            return;
        }
        StringBuilder sb = new StringBuilder("");
        int i = 0;
        while (i >= 0) {
            int indexOf = str.indexOf(41, i);
            if (indexOf >= 0) {
                String substring = str.substring(i + 1, indexOf);
                if (substring.length() >= 2) {
                    int parseInt = Integer.parseInt(substring);
                    int i2 = ais.get(parseInt);
                    if (i2 != 0) {
                        String valueOf = String.valueOf(parseInt);
                        if (valueOf.length() == 1) {
                            valueOf = "0" + valueOf;
                        }
                        int indexOf2 = str.indexOf(40, indexOf);
                        int length = indexOf2 < 0 ? str.length() : indexOf2;
                        sb.append(valueOf);
                        sb.append(str.substring(indexOf + 1, length));
                        if (i2 < 0) {
                            if (indexOf2 >= 0) {
                                sb.append(FNC1);
                            }
                        } else if (((length - indexOf) - 1) + valueOf.length() != i2) {
                            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.ai.length.1", valueOf));
                        }
                        i = indexOf2;
                    } else {
                        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("ai.not.found.1", substring));
                    }
                } else {
                    throw new IllegalArgumentException(MessageLocalization.getComposedMessage("ai.too.short.1", substring));
                }
            } else {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("badly.formed.ucc.string.1", str));
            }
        }
        super.setCode(sb.toString());
    }

    public Image createAwtImage(Color color, Color color2) {
        String str;
        int rgb = color.getRGB();
        int rgb2 = color2.getRGB();
        Canvas canvas = new Canvas();
        boolean z = true;
        if (this.codeType == 11) {
            int indexOf = this.code.indexOf(65535);
            if (indexOf >= 0) {
                str = this.code.substring(0, indexOf);
            } else {
                str = this.code;
            }
        } else {
            str = getRawText(this.code, this.codeType == 10);
        }
        int length = ((str.length() + 2) * 11) + 2;
        byte[] barsCode128Raw = getBarsCode128Raw(str);
        int i = (int) this.barHeight;
        int i2 = length * i;
        int[] iArr = new int[i2];
        int i3 = 0;
        for (byte b : barsCode128Raw) {
            int i4 = z ? rgb : rgb2;
            z = !z;
            int i5 = 0;
            while (i5 < b) {
                iArr[i3] = i4;
                i5++;
                i3++;
            }
        }
        for (int i6 = length; i6 < i2; i6 += length) {
            System.arraycopy(iArr, 0, iArr, i6, length);
        }
        return canvas.createImage(new MemoryImageSource(length, i, iArr, 0, length));
    }
}
