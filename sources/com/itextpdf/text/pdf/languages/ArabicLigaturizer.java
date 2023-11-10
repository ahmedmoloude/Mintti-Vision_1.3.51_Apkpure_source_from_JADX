package com.itextpdf.text.pdf.languages;

import com.itextpdf.text.pdf.BidiLine;
import com.itextpdf.text.pdf.BidiOrder;
import java.util.HashMap;

public class ArabicLigaturizer implements LanguageProcessor {
    private static final char ALEF = 'ا';
    private static final char ALEFHAMZA = 'أ';
    private static final char ALEFHAMZABELOW = 'إ';
    private static final char ALEFMADDA = 'آ';
    private static final char ALEFMAKSURA = 'ى';
    private static final char DAMMA = 'ُ';
    public static final int DIGITS_AN2EN = 64;
    public static final int DIGITS_EN2AN = 32;
    public static final int DIGITS_EN2AN_INIT_AL = 128;
    public static final int DIGITS_EN2AN_INIT_LR = 96;
    public static final int DIGITS_MASK = 224;
    private static final int DIGITS_RESERVED = 160;
    public static final int DIGIT_TYPE_AN = 0;
    public static final int DIGIT_TYPE_AN_EXTENDED = 256;
    public static final int DIGIT_TYPE_MASK = 256;
    private static final char FARSIYEH = 'ی';
    private static final char FATHA = 'َ';
    private static final char HAMZA = 'ء';
    private static final char HAMZAABOVE = 'ٔ';
    private static final char HAMZABELOW = 'ٕ';
    private static final char KASRA = 'ِ';
    private static final char LAM = 'ل';
    private static final char LAM_ALEF = 'ﻻ';
    private static final char LAM_ALEFHAMZA = 'ﻷ';
    private static final char LAM_ALEFHAMZABELOW = 'ﻹ';
    private static final char LAM_ALEFMADDA = 'ﻵ';
    private static final char MADDA = 'ٓ';
    private static final char SHADDA = 'ّ';
    private static final char TATWEEL = 'ـ';
    private static final char WAW = 'و';
    private static final char WAWHAMZA = 'ؤ';
    private static final char YEH = 'ي';
    private static final char YEHHAMZA = 'ئ';
    private static final char ZWJ = '‍';
    public static final int ar_composedtashkeel = 4;
    public static final int ar_lig = 8;
    public static final int ar_nothing = 0;
    public static final int ar_novowel = 1;
    private static final char[][] chartable;
    private static final HashMap<Character, char[]> maptable = new HashMap<>();
    private static final HashMap<Character, Character> reverseLigatureMapTable = new HashMap<>();
    protected int options = 0;
    protected int runDirection = 3;

    static boolean isVowel(char c) {
        return (c >= 1611 && c <= 1621) || c == 1648;
    }

    public boolean isRTL() {
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x02fd  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x031d A[SYNTHETIC] */
    static {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            maptable = r0
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            reverseLigatureMapTable = r0
            r0 = 76
            char[][] r0 = new char[r0][]
            r1 = 2
            char[] r2 = new char[r1]
            r2 = {1569, -384} // fill-array
            r3 = 0
            r0[r3] = r2
            r2 = 3
            char[] r4 = new char[r2]
            r4 = {1570, -383, -382} // fill-array
            r5 = 1
            r0[r5] = r4
            char[] r4 = new char[r2]
            r4 = {1571, -381, -380} // fill-array
            r0[r1] = r4
            char[] r4 = new char[r2]
            r4 = {1572, -379, -378} // fill-array
            r0[r2] = r4
            char[] r4 = new char[r2]
            r4 = {1573, -377, -376} // fill-array
            r6 = 4
            r0[r6] = r4
            r4 = 5
            char[] r7 = new char[r4]
            r7 = {1574, -375, -374, -373, -372} // fill-array
            r0[r4] = r7
            char[] r7 = new char[r2]
            r7 = {1575, -371, -370} // fill-array
            r8 = 6
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1576, -369, -368, -367, -366} // fill-array
            r8 = 7
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1577, -365, -364} // fill-array
            r8 = 8
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1578, -363, -362, -361, -360} // fill-array
            r8 = 9
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1579, -359, -358, -357, -356} // fill-array
            r8 = 10
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1580, -355, -354, -353, -352} // fill-array
            r8 = 11
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1581, -351, -350, -349, -348} // fill-array
            r8 = 12
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1582, -347, -346, -345, -344} // fill-array
            r8 = 13
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1583, -343, -342} // fill-array
            r8 = 14
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1584, -341, -340} // fill-array
            r8 = 15
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1585, -339, -338} // fill-array
            r8 = 16
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1586, -337, -336} // fill-array
            r8 = 17
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1587, -335, -334, -333, -332} // fill-array
            r8 = 18
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1588, -331, -330, -329, -328} // fill-array
            r8 = 19
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1589, -327, -326, -325, -324} // fill-array
            r8 = 20
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1590, -323, -322, -321, -320} // fill-array
            r8 = 21
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1591, -319, -318, -317, -316} // fill-array
            r8 = 22
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1592, -315, -314, -313, -312} // fill-array
            r8 = 23
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1593, -311, -310, -309, -308} // fill-array
            r8 = 24
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1594, -307, -306, -305, -304} // fill-array
            r8 = 25
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1600, 1600, 1600, 1600, 1600} // fill-array
            r8 = 26
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1601, -303, -302, -301, -300} // fill-array
            r8 = 27
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1602, -299, -298, -297, -296} // fill-array
            r8 = 28
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1603, -295, -294, -293, -292} // fill-array
            r8 = 29
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1604, -291, -290, -289, -288} // fill-array
            r8 = 30
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1605, -287, -286, -285, -284} // fill-array
            r8 = 31
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1606, -283, -282, -281, -280} // fill-array
            r8 = 32
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1607, -279, -278, -277, -276} // fill-array
            r8 = 33
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1608, -275, -274} // fill-array
            r8 = 34
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1609, -273, -272, -1048, -1047} // fill-array
            r8 = 35
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1610, -271, -270, -269, -268} // fill-array
            r8 = 36
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1649, -1200, -1199} // fill-array
            r8 = 37
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1657, -1178, -1177, -1176, -1175} // fill-array
            r8 = 38
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1658, -1186, -1185, -1184, -1183} // fill-array
            r8 = 39
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1659, -1198, -1197, -1196, -1195} // fill-array
            r8 = 40
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1662, -1194, -1193, -1192, -1191} // fill-array
            r8 = 41
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1663, -1182, -1181, -1180, -1179} // fill-array
            r8 = 42
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1664, -1190, -1189, -1188, -1187} // fill-array
            r8 = 43
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1667, -1162, -1161, -1160, -1159} // fill-array
            r8 = 44
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1668, -1166, -1165, -1164, -1163} // fill-array
            r8 = 45
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1670, -1158, -1157, -1156, -1155} // fill-array
            r8 = 46
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1671, -1154, -1153, -1152, -1151} // fill-array
            r8 = 47
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1672, -1144, -1143} // fill-array
            r8 = 48
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1676, -1148, -1147} // fill-array
            r8 = 49
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1677, -1150, -1149} // fill-array
            r8 = 50
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1678, -1146, -1145} // fill-array
            r8 = 51
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1681, -1140, -1139} // fill-array
            r8 = 52
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1688, -1142, -1141} // fill-array
            r8 = 53
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1700, -1174, -1173, -1172, -1171} // fill-array
            r8 = 54
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1702, -1170, -1169, -1168, -1167} // fill-array
            r8 = 55
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1705, -1138, -1137, -1136, -1135} // fill-array
            r8 = 56
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1709, -1069, -1068, -1067, -1066} // fill-array
            r8 = 57
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1711, -1134, -1133, -1132, -1131} // fill-array
            r8 = 58
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1713, -1126, -1125, -1124, -1123} // fill-array
            r8 = 59
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1715, -1130, -1129, -1128, -1127} // fill-array
            r8 = 60
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1722, -1122, -1121} // fill-array
            r8 = 61
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1723, -1120, -1119, -1118, -1117} // fill-array
            r8 = 62
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1726, -1110, -1109, -1108, -1107} // fill-array
            r8 = 63
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1728, -1116, -1115} // fill-array
            r8 = 64
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1729, -1114, -1113, -1112, -1111} // fill-array
            r8 = 65
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1733, -1056, -1055} // fill-array
            r8 = 66
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1734, -1063, -1062} // fill-array
            r8 = 67
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1735, -1065, -1064} // fill-array
            r8 = 68
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1736, -1061, -1060} // fill-array
            r8 = 69
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1737, -1054, -1053} // fill-array
            r8 = 70
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1739, -1058, -1057} // fill-array
            r8 = 71
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1740, -1028, -1027, -1026, -1025} // fill-array
            r8 = 72
            r0[r8] = r7
            char[] r7 = new char[r4]
            r7 = {1744, -1052, -1051, -1050, -1049} // fill-array
            r8 = 73
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1746, -1106, -1105} // fill-array
            r8 = 74
            r0[r8] = r7
            char[] r7 = new char[r2]
            r7 = {1747, -1104, -1103} // fill-array
            r8 = 75
            r0[r8] = r7
            chartable = r0
            int r7 = r0.length
            r8 = 0
        L_0x02ba:
            if (r8 >= r7) goto L_0x0320
            r9 = r0[r8]
            java.util.HashMap<java.lang.Character, char[]> r10 = maptable
            char r11 = r9[r3]
            java.lang.Character r11 = java.lang.Character.valueOf(r11)
            r10.put(r11, r9)
            int r10 = r9.length
            if (r10 == r2) goto L_0x02e0
            if (r10 == r4) goto L_0x02cf
            goto L_0x02f1
        L_0x02cf:
            java.util.HashMap<java.lang.Character, java.lang.Character> r10 = reverseLigatureMapTable
            char r11 = r9[r6]
            java.lang.Character r11 = java.lang.Character.valueOf(r11)
            char r12 = r9[r2]
            java.lang.Character r12 = java.lang.Character.valueOf(r12)
            r10.put(r11, r12)
        L_0x02e0:
            java.util.HashMap<java.lang.Character, java.lang.Character> r10 = reverseLigatureMapTable
            char r11 = r9[r1]
            java.lang.Character r11 = java.lang.Character.valueOf(r11)
            char r12 = r9[r5]
            java.lang.Character r12 = java.lang.Character.valueOf(r12)
            r10.put(r11, r12)
        L_0x02f1:
            char r10 = r9[r3]
            r11 = 1591(0x637, float:2.23E-42)
            if (r10 == r11) goto L_0x02fd
            char r10 = r9[r3]
            r11 = 1592(0x638, float:2.231E-42)
            if (r10 != r11) goto L_0x031d
        L_0x02fd:
            java.util.HashMap<java.lang.Character, java.lang.Character> r10 = reverseLigatureMapTable
            char r11 = r9[r6]
            java.lang.Character r11 = java.lang.Character.valueOf(r11)
            char r12 = r9[r5]
            java.lang.Character r12 = java.lang.Character.valueOf(r12)
            r10.put(r11, r12)
            char r11 = r9[r2]
            java.lang.Character r11 = java.lang.Character.valueOf(r11)
            char r9 = r9[r5]
            java.lang.Character r9 = java.lang.Character.valueOf(r9)
            r10.put(r11, r9)
        L_0x031d:
            int r8 = r8 + 1
            goto L_0x02ba
        L_0x0320:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.languages.ArabicLigaturizer.<clinit>():void");
    }

    static char charshape(char c, int i) {
        if (c < 1569 || c > 1747) {
            return (c < 65269 || c > 65275) ? c : (char) (c + i);
        }
        char[] cArr = maptable.get(Character.valueOf(c));
        return cArr != null ? cArr[i + 1] : c;
    }

    static int shapecount(char c) {
        if (c >= 1569 && c <= 1747 && !isVowel(c)) {
            char[] cArr = maptable.get(Character.valueOf(c));
            if (cArr != null) {
                return cArr.length - 1;
            }
        } else if (c == 8205) {
            return 4;
        }
        return 1;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int ligature(char r12, com.itextpdf.text.pdf.languages.ArabicLigaturizer.charstruct r13) {
        /*
            char r0 = r13.basechar
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            boolean r0 = isVowel(r12)
            r2 = 65273(0xfef9, float:9.1467E-41)
            r3 = 65271(0xfef7, float:9.1464E-41)
            r4 = 1573(0x625, float:2.204E-42)
            r5 = 1571(0x623, float:2.201E-42)
            r6 = 1570(0x622, float:2.2E-42)
            r7 = 65275(0xfefb, float:9.147E-41)
            r8 = 1575(0x627, float:2.207E-42)
            r9 = 1
            r10 = 2
            if (r0 == 0) goto L_0x007c
            char r0 = r13.vowel
            r11 = 1617(0x651, float:2.266E-42)
            if (r0 == 0) goto L_0x0029
            if (r12 == r11) goto L_0x0029
            r0 = 2
            goto L_0x002a
        L_0x0029:
            r0 = 1
        L_0x002a:
            switch(r12) {
                case 1617: goto L_0x006b;
                case 1618: goto L_0x002d;
                case 1619: goto L_0x0063;
                case 1620: goto L_0x0041;
                case 1621: goto L_0x0030;
                default: goto L_0x002d;
            }
        L_0x002d:
            r13.vowel = r12
            goto L_0x0073
        L_0x0030:
            char r12 = r13.basechar
            if (r12 == r8) goto L_0x003e
            if (r12 == r7) goto L_0x003b
            r12 = 1621(0x655, float:2.272E-42)
            r13.mark1 = r12
            goto L_0x0073
        L_0x003b:
            r13.basechar = r2
            goto L_0x0074
        L_0x003e:
            r13.basechar = r4
            goto L_0x0074
        L_0x0041:
            char r12 = r13.basechar
            if (r12 == r8) goto L_0x0060
            r1 = 1740(0x6cc, float:2.438E-42)
            if (r12 == r1) goto L_0x005b
            if (r12 == r7) goto L_0x0058
            switch(r12) {
                case 1608: goto L_0x0053;
                case 1609: goto L_0x005b;
                case 1610: goto L_0x005b;
                default: goto L_0x004e;
            }
        L_0x004e:
            r12 = 1620(0x654, float:2.27E-42)
            r13.mark1 = r12
            goto L_0x0073
        L_0x0053:
            r12 = 1572(0x624, float:2.203E-42)
            r13.basechar = r12
            goto L_0x0074
        L_0x0058:
            r13.basechar = r3
            goto L_0x0074
        L_0x005b:
            r12 = 1574(0x626, float:2.206E-42)
            r13.basechar = r12
            goto L_0x0074
        L_0x0060:
            r13.basechar = r5
            goto L_0x0074
        L_0x0063:
            char r12 = r13.basechar
            if (r12 == r8) goto L_0x0068
            goto L_0x0073
        L_0x0068:
            r13.basechar = r6
            goto L_0x0074
        L_0x006b:
            char r12 = r13.mark1
            if (r12 != 0) goto L_0x0072
            r13.mark1 = r11
            goto L_0x0073
        L_0x0072:
            return r1
        L_0x0073:
            r10 = r0
        L_0x0074:
            if (r10 != r9) goto L_0x007b
            int r12 = r13.lignum
            int r12 = r12 + r9
            r13.lignum = r12
        L_0x007b:
            return r10
        L_0x007c:
            char r0 = r13.vowel
            if (r0 == 0) goto L_0x0081
            return r1
        L_0x0081:
            char r0 = r13.basechar
            if (r0 == 0) goto L_0x00ac
            r9 = 1604(0x644, float:2.248E-42)
            if (r0 == r9) goto L_0x008a
            goto L_0x00b5
        L_0x008a:
            r0 = 3
            if (r12 == r6) goto L_0x00a3
            if (r12 == r5) goto L_0x009e
            if (r12 == r4) goto L_0x0099
            if (r12 == r8) goto L_0x0094
            goto L_0x00b5
        L_0x0094:
            r13.basechar = r7
            r13.numshapes = r10
            goto L_0x00aa
        L_0x0099:
            r13.basechar = r2
            r13.numshapes = r10
            goto L_0x00aa
        L_0x009e:
            r13.basechar = r3
            r13.numshapes = r10
            goto L_0x00aa
        L_0x00a3:
            r12 = 65269(0xfef5, float:9.1461E-41)
            r13.basechar = r12
            r13.numshapes = r10
        L_0x00aa:
            r1 = 3
            goto L_0x00b5
        L_0x00ac:
            r13.basechar = r12
            int r12 = shapecount(r12)
            r13.numshapes = r12
            r1 = 1
        L_0x00b5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.languages.ArabicLigaturizer.ligature(char, com.itextpdf.text.pdf.languages.ArabicLigaturizer$charstruct):int");
    }

    static void copycstostring(StringBuffer stringBuffer, charstruct charstruct2, int i) {
        if (charstruct2.basechar != 0) {
            stringBuffer.append(charstruct2.basechar);
            charstruct2.lignum--;
            if (charstruct2.mark1 != 0) {
                if ((i & 1) == 0) {
                    stringBuffer.append(charstruct2.mark1);
                    charstruct2.lignum--;
                } else {
                    charstruct2.lignum--;
                }
            }
            if (charstruct2.vowel == 0) {
                return;
            }
            if ((i & 1) == 0) {
                stringBuffer.append(charstruct2.vowel);
                charstruct2.lignum--;
                return;
            }
            charstruct2.lignum--;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003b, code lost:
        if (r10.charAt(r2) == 1617) goto L_0x0053;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0042, code lost:
        if (r10.charAt(r2) == 1617) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        r6 = 64609;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
        if (r10.charAt(r2) == 1617) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004e, code lost:
        r6 = 64608;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void doublelig(java.lang.StringBuffer r10, int r11) {
        /*
            int r0 = r10.length()
            r1 = 0
            r2 = 1
            r3 = r0
            r4 = 0
        L_0x0008:
            if (r2 >= r0) goto L_0x013b
            r5 = r11 & 4
            r6 = 64610(0xfc62, float:9.0538E-41)
            r7 = 64609(0xfc61, float:9.0536E-41)
            r8 = 64608(0xfc60, float:9.0535E-41)
            if (r5 == 0) goto L_0x0052
            char r5 = r10.charAt(r4)
            r9 = 1617(0x651, float:2.266E-42)
            switch(r5) {
                case 1614: goto L_0x0048;
                case 1615: goto L_0x003e;
                case 1616: goto L_0x0037;
                case 1617: goto L_0x0021;
                default: goto L_0x0020;
            }
        L_0x0020:
            goto L_0x0052
        L_0x0021:
            char r5 = r10.charAt(r2)
            switch(r5) {
                case 1612: goto L_0x0030;
                case 1613: goto L_0x0029;
                case 1614: goto L_0x004e;
                case 1615: goto L_0x0044;
                case 1616: goto L_0x0053;
                default: goto L_0x0028;
            }
        L_0x0028:
            goto L_0x0052
        L_0x0029:
            r5 = 64607(0xfc5f, float:9.0534E-41)
            r6 = 64607(0xfc5f, float:9.0534E-41)
            goto L_0x0053
        L_0x0030:
            r5 = 64606(0xfc5e, float:9.0532E-41)
            r6 = 64606(0xfc5e, float:9.0532E-41)
            goto L_0x0053
        L_0x0037:
            char r5 = r10.charAt(r2)
            if (r5 != r9) goto L_0x0052
            goto L_0x0053
        L_0x003e:
            char r5 = r10.charAt(r2)
            if (r5 != r9) goto L_0x0052
        L_0x0044:
            r6 = 64609(0xfc61, float:9.0536E-41)
            goto L_0x0053
        L_0x0048:
            char r5 = r10.charAt(r2)
            if (r5 != r9) goto L_0x0052
        L_0x004e:
            r6 = 64608(0xfc60, float:9.0535E-41)
            goto L_0x0053
        L_0x0052:
            r6 = 0
        L_0x0053:
            r5 = r11 & 8
            if (r5 == 0) goto L_0x0126
            char r5 = r10.charAt(r4)
            r7 = 65192(0xfea8, float:9.1353E-41)
            r8 = 65188(0xfea4, float:9.1348E-41)
            r9 = 65184(0xfea0, float:9.1342E-41)
            switch(r5) {
                case 65169: goto L_0x0110;
                case 65175: goto L_0x00f9;
                case 65235: goto L_0x00eb;
                case 65247: goto L_0x00c1;
                case 65251: goto L_0x00a4;
                case 65255: goto L_0x0089;
                case 65256: goto L_0x0069;
                default: goto L_0x0067;
            }
        L_0x0067:
            goto L_0x0126
        L_0x0069:
            char r5 = r10.charAt(r2)
            r7 = 65198(0xfeae, float:9.1362E-41)
            if (r5 == r7) goto L_0x0081
            r7 = 65200(0xfeb0, float:9.1365E-41)
            if (r5 == r7) goto L_0x0079
            goto L_0x0126
        L_0x0079:
            r5 = 64651(0xfc8b, float:9.0595E-41)
            r6 = 64651(0xfc8b, float:9.0595E-41)
            goto L_0x0126
        L_0x0081:
            r5 = 64650(0xfc8a, float:9.0594E-41)
            r6 = 64650(0xfc8a, float:9.0594E-41)
            goto L_0x0126
        L_0x0089:
            char r5 = r10.charAt(r2)
            if (r5 == r9) goto L_0x009f
            if (r5 == r8) goto L_0x009a
            if (r5 == r7) goto L_0x0095
            goto L_0x0126
        L_0x0095:
            r6 = 64724(0xfcd4, float:9.0698E-41)
            goto L_0x0126
        L_0x009a:
            r6 = 64723(0xfcd3, float:9.0696E-41)
            goto L_0x0126
        L_0x009f:
            r6 = 64722(0xfcd2, float:9.0695E-41)
            goto L_0x0126
        L_0x00a4:
            char r5 = r10.charAt(r2)
            switch(r5) {
                case 65184: goto L_0x00bc;
                case 65188: goto L_0x00b7;
                case 65192: goto L_0x00b2;
                case 65252: goto L_0x00ad;
                default: goto L_0x00ab;
            }
        L_0x00ab:
            goto L_0x0126
        L_0x00ad:
            r6 = 64721(0xfcd1, float:9.0693E-41)
            goto L_0x0126
        L_0x00b2:
            r6 = 64720(0xfcd0, float:9.0692E-41)
            goto L_0x0126
        L_0x00b7:
            r6 = 64719(0xfccf, float:9.069E-41)
            goto L_0x0126
        L_0x00bc:
            r6 = 64718(0xfcce, float:9.0689E-41)
            goto L_0x0126
        L_0x00c1:
            char r5 = r10.charAt(r2)
            switch(r5) {
                case 65182: goto L_0x00e7;
                case 65184: goto L_0x00e3;
                case 65186: goto L_0x00df;
                case 65188: goto L_0x00db;
                case 65190: goto L_0x00d7;
                case 65192: goto L_0x00d3;
                case 65250: goto L_0x00cf;
                case 65252: goto L_0x00ca;
                default: goto L_0x00c8;
            }
        L_0x00c8:
            goto L_0x0126
        L_0x00ca:
            r6 = 64716(0xfccc, float:9.0686E-41)
            goto L_0x0126
        L_0x00cf:
            r6 = 64578(0xfc42, float:9.0493E-41)
            goto L_0x0126
        L_0x00d3:
            r6 = 64715(0xfccb, float:9.0685E-41)
            goto L_0x0126
        L_0x00d7:
            r6 = 64577(0xfc41, float:9.0492E-41)
            goto L_0x0126
        L_0x00db:
            r6 = 64714(0xfcca, float:9.0684E-41)
            goto L_0x0126
        L_0x00df:
            r6 = 64576(0xfc40, float:9.049E-41)
            goto L_0x0126
        L_0x00e3:
            r6 = 64713(0xfcc9, float:9.0682E-41)
            goto L_0x0126
        L_0x00e7:
            r6 = 64575(0xfc3f, float:9.0489E-41)
            goto L_0x0126
        L_0x00eb:
            char r5 = r10.charAt(r2)
            r7 = 65266(0xfef2, float:9.1457E-41)
            if (r5 == r7) goto L_0x00f5
            goto L_0x0126
        L_0x00f5:
            r6 = 64562(0xfc32, float:9.047E-41)
            goto L_0x0126
        L_0x00f9:
            char r5 = r10.charAt(r2)
            if (r5 == r9) goto L_0x010c
            if (r5 == r8) goto L_0x0108
            if (r5 == r7) goto L_0x0104
            goto L_0x0126
        L_0x0104:
            r6 = 64675(0xfca3, float:9.0629E-41)
            goto L_0x0126
        L_0x0108:
            r6 = 64674(0xfca2, float:9.0628E-41)
            goto L_0x0126
        L_0x010c:
            r6 = 64673(0xfca1, float:9.0626E-41)
            goto L_0x0126
        L_0x0110:
            char r5 = r10.charAt(r2)
            if (r5 == r9) goto L_0x0123
            if (r5 == r8) goto L_0x011f
            if (r5 == r7) goto L_0x011b
            goto L_0x0126
        L_0x011b:
            r6 = 64670(0xfc9e, float:9.0622E-41)
            goto L_0x0126
        L_0x011f:
            r6 = 64669(0xfc9d, float:9.062E-41)
            goto L_0x0126
        L_0x0123:
            r6 = 64668(0xfc9c, float:9.0619E-41)
        L_0x0126:
            if (r6 == 0) goto L_0x012e
            r10.setCharAt(r4, r6)
            int r3 = r3 + -1
            goto L_0x0137
        L_0x012e:
            int r4 = r4 + 1
            char r5 = r10.charAt(r2)
            r10.setCharAt(r4, r5)
        L_0x0137:
            int r2 = r2 + 1
            goto L_0x0008
        L_0x013b:
            r10.setLength(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.languages.ArabicLigaturizer.doublelig(java.lang.StringBuffer, int):void");
    }

    static boolean connects_to_left(charstruct charstruct2) {
        return charstruct2.numshapes > 2;
    }

    static void shape(char[] cArr, StringBuffer stringBuffer, int i) {
        charstruct charstruct2 = new charstruct();
        charstruct charstruct3 = new charstruct();
        int i2 = 0;
        while (i2 < cArr.length) {
            int i3 = i2 + 1;
            char c = cArr[i2];
            if (ligature(c, charstruct3) == 0) {
                int shapecount = shapecount(c);
                int i4 = shapecount == 1 ? 0 : 2;
                if (connects_to_left(charstruct2)) {
                    i4++;
                }
                charstruct3.basechar = charshape(charstruct3.basechar, i4 % charstruct3.numshapes);
                copycstostring(stringBuffer, charstruct2, i);
                charstruct charstruct4 = new charstruct();
                charstruct4.basechar = c;
                charstruct4.numshapes = shapecount;
                charstruct4.lignum++;
                i2 = i3;
                charstruct charstruct5 = charstruct3;
                charstruct3 = charstruct4;
                charstruct2 = charstruct5;
            } else {
                i2 = i3;
            }
        }
        charstruct3.basechar = charshape(charstruct3.basechar, (connects_to_left(charstruct2) ? 1 : 0) % charstruct3.numshapes);
        copycstostring(stringBuffer, charstruct2, i);
        copycstostring(stringBuffer, charstruct3, i);
    }

    public static int arabic_shape(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4, int i5) {
        char[] cArr3 = new char[i2];
        for (int i6 = (i2 + i) - 1; i6 >= i; i6--) {
            cArr3[i6 - i] = cArr[i6];
        }
        StringBuffer stringBuffer = new StringBuffer(i2);
        shape(cArr3, stringBuffer, i5);
        if ((i5 & 12) != 0) {
            doublelig(stringBuffer, i5);
        }
        System.arraycopy(stringBuffer.toString().toCharArray(), 0, cArr2, i3, stringBuffer.length());
        return stringBuffer.length();
    }

    public static void processNumbers(char[] cArr, int i, int i2, int i3) {
        int i4 = i + i2;
        int i5 = i3 & 224;
        if (i5 != 0) {
            int i6 = i3 & 256;
            char c = i6 != 0 ? i6 != 256 ? '0' : 1776 : 1632;
            if (i5 == 32) {
                int i7 = c - '0';
                while (i < i4) {
                    char c2 = cArr[i];
                    if (c2 <= '9' && c2 >= '0') {
                        cArr[i] = (char) (cArr[i] + i7);
                    }
                    i++;
                }
            } else if (i5 == 64) {
                char c3 = (char) (c + 9);
                int i8 = '0' - c;
                while (i < i4) {
                    char c4 = cArr[i];
                    if (c4 <= c3 && c4 >= c) {
                        cArr[i] = (char) (cArr[i] + i8);
                    }
                    i++;
                }
            } else if (i5 == 96) {
                shapeToArabicDigitsWithContext(cArr, 0, i2, c, false);
            } else if (i5 == 128) {
                shapeToArabicDigitsWithContext(cArr, 0, i2, c, true);
            }
        }
    }

    static void shapeToArabicDigitsWithContext(char[] cArr, int i, int i2, char c, boolean z) {
        char c2 = (char) (c - '0');
        int i3 = i2 + i;
        while (i < i3) {
            char c3 = cArr[i];
            byte direction = BidiOrder.getDirection(c3);
            if (direction != 0) {
                if (direction != 8) {
                    if (direction != 3) {
                        if (direction == 4) {
                            z = true;
                        }
                    }
                } else if (z && c3 <= '9') {
                    cArr[i] = (char) (c3 + c2);
                }
                i++;
            }
            z = false;
            i++;
        }
    }

    public static Character getReverseMapping(char c) {
        return reverseLigatureMapTable.get(Character.valueOf(c));
    }

    static class charstruct {
        char basechar;
        int lignum;
        char mark1;
        int numshapes = 1;
        char vowel;

        charstruct() {
        }
    }

    public ArabicLigaturizer() {
    }

    public ArabicLigaturizer(int i, int i2) {
        this.runDirection = i;
        this.options = i2;
    }

    public String process(String str) {
        return BidiLine.processLTR(str, this.runDirection, this.options);
    }
}
