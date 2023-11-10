package com.itextpdf.text.pdf;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.ExceptionConverter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.text.Typography;
import p028io.jsonwebtoken.JwtParser;

public class PdfEncodings {
    static HashMap<String, ExtraEncoding> extraEncodings = new HashMap<>();
    static final IntHashtable pdfEncoding = new IntHashtable();
    static final char[] pdfEncodingByteToChar = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, ' ', '!', Typography.quote, '#', Typography.dollar, '%', Typography.amp, '\'', '(', ')', '*', '+', ',', '-', JwtParser.SEPARATOR_CHAR, '/', '0', '1', PdfWriter.VERSION_1_2, PdfWriter.VERSION_1_3, PdfWriter.VERSION_1_4, PdfWriter.VERSION_1_5, PdfWriter.VERSION_1_6, PdfWriter.VERSION_1_7, '8', '9', ':', ';', Typography.less, '=', Typography.greater, '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', Barcode128.CODE_AB_TO_C, Barcode128.CODE_AC_TO_B, Barcode128.CODE_BC_TO_A, Barcode128.FNC1_INDEX, Barcode128.START_A, Barcode128.START_B, Barcode128.START_C, 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', 127, Typography.bullet, Typography.dagger, Typography.doubleDagger, Typography.ellipsis, Typography.mdash, Typography.ndash, 402, 8260, 8249, 8250, 8722, 8240, Typography.lowDoubleQuote, Typography.leftDoubleQuote, Typography.rightDoubleQuote, Typography.leftSingleQuote, Typography.rightSingleQuote, Typography.lowSingleQuote, Typography.f2134tm, 64257, 64258, 321, 338, 352, 376, 381, 305, 322, 339, 353, 382, 65533, Typography.euro, 161, Typography.cent, Typography.pound, 164, 165, 166, Typography.section, 168, Typography.copyright, 170, 171, 172, 173, Typography.registered, 175, Typography.degree, Typography.plusMinus, 178, 179, 180, 181, Typography.paragraph, Typography.middleDot, 184, 185, 186, 187, 188, Typography.half, 190, 191, 192, 193, 194, Barcode128.DEL, Barcode128.FNC3, Barcode128.FNC2, Barcode128.SHIFT, Barcode128.CODE_C, 200, 201, Barcode128.FNC1, Barcode128.STARTA, Barcode128.STARTB, Barcode128.STARTC, 206, 207, 208, 209, 210, 211, 212, 213, 214, Typography.times, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255};
    static final IntHashtable winansi = new IntHashtable();
    static final char[] winansiByteToChar = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, ' ', '!', Typography.quote, '#', Typography.dollar, '%', Typography.amp, '\'', '(', ')', '*', '+', ',', '-', JwtParser.SEPARATOR_CHAR, '/', '0', '1', PdfWriter.VERSION_1_2, PdfWriter.VERSION_1_3, PdfWriter.VERSION_1_4, PdfWriter.VERSION_1_5, PdfWriter.VERSION_1_6, PdfWriter.VERSION_1_7, '8', '9', ':', ';', Typography.less, '=', Typography.greater, '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', Barcode128.CODE_AB_TO_C, Barcode128.CODE_AC_TO_B, Barcode128.CODE_BC_TO_A, Barcode128.FNC1_INDEX, Barcode128.START_A, Barcode128.START_B, Barcode128.START_C, 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', 127, Typography.euro, 65533, Typography.lowSingleQuote, 402, Typography.lowDoubleQuote, Typography.ellipsis, Typography.dagger, Typography.doubleDagger, 710, 8240, 352, 8249, 338, 65533, 381, 65533, 65533, Typography.leftSingleQuote, Typography.rightSingleQuote, Typography.leftDoubleQuote, Typography.rightDoubleQuote, Typography.bullet, Typography.ndash, Typography.mdash, 732, Typography.f2134tm, 353, 8250, 339, 65533, 382, 376, Typography.nbsp, 161, Typography.cent, Typography.pound, 164, 165, 166, Typography.section, 168, Typography.copyright, 170, 171, 172, 173, Typography.registered, 175, Typography.degree, Typography.plusMinus, 178, 179, 180, 181, Typography.paragraph, Typography.middleDot, 184, 185, 186, 187, 188, Typography.half, 190, 191, 192, 193, 194, Barcode128.DEL, Barcode128.FNC3, Barcode128.FNC2, Barcode128.SHIFT, Barcode128.CODE_C, 200, 201, Barcode128.FNC1, Barcode128.STARTA, Barcode128.STARTB, Barcode128.STARTC, 206, 207, 208, 209, 210, 211, 212, 213, 214, Typography.times, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255};

    static {
        for (int i = 128; i < 161; i++) {
            char c = winansiByteToChar[i];
            if (c != 65533) {
                winansi.put(c, i);
            }
        }
        for (int i2 = 128; i2 < 161; i2++) {
            char c2 = pdfEncodingByteToChar[i2];
            if (c2 != 65533) {
                pdfEncoding.put(c2, i2);
            }
        }
        addExtraEncoding("Wingdings", new WingdingsConversion());
        addExtraEncoding("Symbol", new SymbolConversion(true));
        addExtraEncoding("ZapfDingbats", new SymbolConversion(false));
        addExtraEncoding("SymbolTT", new SymbolTTConversion());
        addExtraEncoding("Cp437", new Cp437Conversion());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: char} */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
        if (r6 <= 255) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0057, code lost:
        r6 = r1.get(r6);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final byte[] convertToBytes(java.lang.String r8, java.lang.String r9) {
        /*
            r0 = 0
            if (r8 != 0) goto L_0x0006
            byte[] r8 = new byte[r0]
            return r8
        L_0x0006:
            if (r9 == 0) goto L_0x00cc
            int r1 = r9.length()
            if (r1 != 0) goto L_0x0010
            goto L_0x00cc
        L_0x0010:
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.ExtraEncoding> r1 = extraEncodings
            java.lang.String r2 = r9.toLowerCase()
            java.lang.Object r1 = r1.get(r2)
            com.itextpdf.text.pdf.ExtraEncoding r1 = (com.itextpdf.text.pdf.ExtraEncoding) r1
            if (r1 == 0) goto L_0x0025
            byte[] r1 = r1.charToByte((java.lang.String) r8, (java.lang.String) r9)
            if (r1 == 0) goto L_0x0025
            return r1
        L_0x0025:
            r1 = 0
            java.lang.String r2 = "Cp1252"
            boolean r2 = r9.equals(r2)
            if (r2 == 0) goto L_0x0031
            com.itextpdf.text.pdf.IntHashtable r1 = winansi
            goto L_0x003b
        L_0x0031:
            java.lang.String r2 = "PDF"
            boolean r2 = r9.equals(r2)
            if (r2 == 0) goto L_0x003b
            com.itextpdf.text.pdf.IntHashtable r1 = pdfEncoding
        L_0x003b:
            r2 = 255(0xff, float:3.57E-43)
            if (r1 == 0) goto L_0x006f
            char[] r8 = r8.toCharArray()
            int r9 = r8.length
            byte[] r3 = new byte[r9]
            r4 = 0
            r5 = 0
        L_0x0048:
            if (r4 >= r9) goto L_0x0066
            char r6 = r8[r4]
            r7 = 128(0x80, float:1.794E-43)
            if (r6 < r7) goto L_0x005b
            r7 = 160(0xa0, float:2.24E-43)
            if (r6 <= r7) goto L_0x0057
            if (r6 > r2) goto L_0x0057
            goto L_0x005b
        L_0x0057:
            int r6 = r1.get(r6)
        L_0x005b:
            if (r6 == 0) goto L_0x0063
            int r7 = r5 + 1
            byte r6 = (byte) r6
            r3[r5] = r6
            r5 = r7
        L_0x0063:
            int r4 = r4 + 1
            goto L_0x0048
        L_0x0066:
            if (r5 != r9) goto L_0x0069
            return r3
        L_0x0069:
            byte[] r8 = new byte[r5]
            java.lang.System.arraycopy(r3, r0, r8, r0, r5)
            return r8
        L_0x006f:
            java.lang.String r1 = "UnicodeBig"
            boolean r1 = r9.equals(r1)
            if (r1 == 0) goto L_0x009f
            char[] r8 = r8.toCharArray()
            int r9 = r8.length
            int r1 = r8.length
            r3 = 2
            int r1 = r1 * 2
            int r1 = r1 + r3
            byte[] r1 = new byte[r1]
            r4 = -2
            r1[r0] = r4
            r4 = -1
            r5 = 1
            r1[r5] = r4
        L_0x008a:
            if (r0 >= r9) goto L_0x009e
            char r4 = r8[r0]
            int r5 = r3 + 1
            int r6 = r4 >> 8
            byte r6 = (byte) r6
            r1[r3] = r6
            int r3 = r5 + 1
            r4 = r4 & r2
            byte r4 = (byte) r4
            r1[r5] = r4
            int r0 = r0 + 1
            goto L_0x008a
        L_0x009e:
            return r1
        L_0x009f:
            java.nio.charset.Charset r9 = java.nio.charset.Charset.forName(r9)     // Catch:{ IOException -> 0x00c5 }
            java.nio.charset.CharsetEncoder r9 = r9.newEncoder()     // Catch:{ IOException -> 0x00c5 }
            java.nio.charset.CodingErrorAction r0 = java.nio.charset.CodingErrorAction.IGNORE     // Catch:{ IOException -> 0x00c5 }
            r9.onUnmappableCharacter(r0)     // Catch:{ IOException -> 0x00c5 }
            char[] r8 = r8.toCharArray()     // Catch:{ IOException -> 0x00c5 }
            java.nio.CharBuffer r8 = java.nio.CharBuffer.wrap(r8)     // Catch:{ IOException -> 0x00c5 }
            java.nio.ByteBuffer r8 = r9.encode(r8)     // Catch:{ IOException -> 0x00c5 }
            r8.rewind()     // Catch:{ IOException -> 0x00c5 }
            int r9 = r8.limit()     // Catch:{ IOException -> 0x00c5 }
            byte[] r9 = new byte[r9]     // Catch:{ IOException -> 0x00c5 }
            r8.get(r9)     // Catch:{ IOException -> 0x00c5 }
            return r9
        L_0x00c5:
            r8 = move-exception
            com.itextpdf.text.ExceptionConverter r9 = new com.itextpdf.text.ExceptionConverter
            r9.<init>(r8)
            throw r9
        L_0x00cc:
            int r9 = r8.length()
            byte[] r1 = new byte[r9]
        L_0x00d2:
            if (r0 >= r9) goto L_0x00de
            char r2 = r8.charAt(r0)
            byte r2 = (byte) r2
            r1[r0] = r2
            int r0 = r0 + 1
            goto L_0x00d2
        L_0x00de:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfEncodings.convertToBytes(java.lang.String, java.lang.String):byte[]");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: char} */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        if (r4 <= 255) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        r4 = r2.get(r4);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final byte[] convertToBytes(char r4, java.lang.String r5) {
        /*
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L_0x009f
            int r2 = r5.length()
            if (r2 != 0) goto L_0x000c
            goto L_0x009f
        L_0x000c:
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.ExtraEncoding> r2 = extraEncodings
            java.lang.String r3 = r5.toLowerCase()
            java.lang.Object r2 = r2.get(r3)
            com.itextpdf.text.pdf.ExtraEncoding r2 = (com.itextpdf.text.pdf.ExtraEncoding) r2
            if (r2 == 0) goto L_0x0021
            byte[] r2 = r2.charToByte((char) r4, (java.lang.String) r5)
            if (r2 == 0) goto L_0x0021
            return r2
        L_0x0021:
            r2 = 0
            java.lang.String r3 = "Cp1252"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x002d
            com.itextpdf.text.pdf.IntHashtable r2 = winansi
            goto L_0x0037
        L_0x002d:
            java.lang.String r3 = "PDF"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x0037
            com.itextpdf.text.pdf.IntHashtable r2 = pdfEncoding
        L_0x0037:
            r3 = 255(0xff, float:3.57E-43)
            if (r2 == 0) goto L_0x0055
            r5 = 128(0x80, float:1.794E-43)
            if (r4 < r5) goto L_0x004a
            r5 = 160(0xa0, float:2.24E-43)
            if (r4 <= r5) goto L_0x0046
            if (r4 > r3) goto L_0x0046
            goto L_0x004a
        L_0x0046:
            int r4 = r2.get(r4)
        L_0x004a:
            if (r4 == 0) goto L_0x0052
            byte[] r5 = new byte[r0]
            byte r4 = (byte) r4
            r5[r1] = r4
            return r5
        L_0x0052:
            byte[] r4 = new byte[r1]
            return r4
        L_0x0055:
            java.lang.String r2 = "UnicodeBig"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x0072
            r5 = 4
            byte[] r5 = new byte[r5]
            r2 = -2
            r5[r1] = r2
            r1 = -1
            r5[r0] = r1
            r0 = 2
            int r1 = r4 >> 8
            byte r1 = (byte) r1
            r5[r0] = r1
            r0 = 3
            r4 = r4 & r3
            byte r4 = (byte) r4
            r5[r0] = r4
            return r5
        L_0x0072:
            java.nio.charset.Charset r5 = java.nio.charset.Charset.forName(r5)     // Catch:{ IOException -> 0x0098 }
            java.nio.charset.CharsetEncoder r5 = r5.newEncoder()     // Catch:{ IOException -> 0x0098 }
            java.nio.charset.CodingErrorAction r2 = java.nio.charset.CodingErrorAction.IGNORE     // Catch:{ IOException -> 0x0098 }
            r5.onUnmappableCharacter(r2)     // Catch:{ IOException -> 0x0098 }
            char[] r0 = new char[r0]     // Catch:{ IOException -> 0x0098 }
            r0[r1] = r4     // Catch:{ IOException -> 0x0098 }
            java.nio.CharBuffer r4 = java.nio.CharBuffer.wrap(r0)     // Catch:{ IOException -> 0x0098 }
            java.nio.ByteBuffer r4 = r5.encode(r4)     // Catch:{ IOException -> 0x0098 }
            r4.rewind()     // Catch:{ IOException -> 0x0098 }
            int r5 = r4.limit()     // Catch:{ IOException -> 0x0098 }
            byte[] r5 = new byte[r5]     // Catch:{ IOException -> 0x0098 }
            r4.get(r5)     // Catch:{ IOException -> 0x0098 }
            return r5
        L_0x0098:
            r4 = move-exception
            com.itextpdf.text.ExceptionConverter r5 = new com.itextpdf.text.ExceptionConverter
            r5.<init>(r4)
            throw r5
        L_0x009f:
            byte[] r5 = new byte[r0]
            byte r4 = (byte) r4
            r5[r1] = r4
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfEncodings.convertToBytes(char, java.lang.String):byte[]");
    }

    public static final String convertToString(byte[] bArr, String str) {
        String byteToChar;
        if (bArr == null) {
            return "";
        }
        int i = 0;
        if (str == null || str.length() == 0) {
            char[] cArr = new char[bArr.length];
            while (i < bArr.length) {
                cArr[i] = (char) (bArr[i] & UByte.MAX_VALUE);
                i++;
            }
            return new String(cArr);
        }
        ExtraEncoding extraEncoding = extraEncodings.get(str.toLowerCase());
        if (extraEncoding != null && (byteToChar = extraEncoding.byteToChar(bArr, str)) != null) {
            return byteToChar;
        }
        char[] cArr2 = null;
        if (str.equals("Cp1252")) {
            cArr2 = winansiByteToChar;
        } else if (str.equals(PdfObject.TEXT_PDFDOCENCODING)) {
            cArr2 = pdfEncodingByteToChar;
        }
        if (cArr2 != null) {
            int length = bArr.length;
            char[] cArr3 = new char[length];
            while (i < length) {
                cArr3[i] = cArr2[bArr[i] & UByte.MAX_VALUE];
                i++;
            }
            return new String(cArr3);
        }
        try {
            return new String(bArr, str);
        } catch (UnsupportedEncodingException e) {
            throw new ExceptionConverter(e);
        }
    }

    public static boolean isPdfDocEncoding(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt >= 128 && ((charAt <= 160 || charAt > 255) && !pdfEncoding.containsKey(charAt))) {
                return false;
            }
        }
        return true;
    }

    public static void addExtraEncoding(String str, ExtraEncoding extraEncoding) {
        synchronized (extraEncodings) {
            HashMap<String, ExtraEncoding> hashMap = (HashMap) extraEncodings.clone();
            hashMap.put(str.toLowerCase(), extraEncoding);
            extraEncodings = hashMap;
        }
    }

    private static class WingdingsConversion implements ExtraEncoding {
        private static final byte[] table = {0, 35, DocWriter.QUOTE, 0, 0, 0, 41, DocWriter.f437GT, 81, 42, 0, 0, 65, 63, 0, 0, 0, 0, 0, -4, 0, 0, 0, -5, 0, 0, 0, 0, 0, 0, 86, 0, 88, 89, 0, 0, 0, 0, 0, 0, 0, 0, -75, 0, 0, 0, 0, 0, -74, 0, 0, 0, -83, -81, -84, 0, 0, 0, 0, 0, 0, 0, 0, 124, 123, 0, 0, 0, 84, 0, 0, 0, 0, 0, 0, 0, 0, -90, 0, 0, 0, 113, 114, 0, 0, 0, 117, 0, 0, 0, 0, 0, 0, 125, 126, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -116, -115, -114, -113, -112, -111, -110, -109, -108, -107, -127, -126, -125, -124, -123, -122, -121, -120, -119, -118, -116, -115, -114, -113, -112, -111, -110, -109, -108, -107, -24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -24, -40, 0, 0, -60, -58, 0, 0, -16, 0, 0, 0, 0, 0, 0, 0, 0, 0, -36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        public String byteToChar(byte[] bArr, String str) {
            return null;
        }

        private WingdingsConversion() {
        }

        public byte[] charToByte(char c, String str) {
            byte b;
            if (c == ' ') {
                return new byte[]{(byte) c};
            } else if (c < 9985 || c > 10174 || (b = table[c - 9984]) == 0) {
                return new byte[0];
            } else {
                return new byte[]{b};
            }
        }

        public byte[] charToByte(String str, String str2) {
            byte b;
            int i;
            char[] charArray = str.toCharArray();
            byte[] bArr = new byte[charArray.length];
            int i2 = 0;
            for (char c : charArray) {
                if (c == ' ') {
                    i = i2 + 1;
                    bArr[i2] = (byte) c;
                } else {
                    if (c >= 9985 && c <= 10174 && (b = table[c - 9984]) != 0) {
                        i = i2 + 1;
                        bArr[i2] = b;
                    }
                }
                i2 = i;
            }
            if (i2 == r0) {
                return bArr;
            }
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            return bArr2;
        }
    }

    private static class Cp437Conversion implements ExtraEncoding {
        private static IntHashtable c2b = new IntHashtable();
        private static final char[] table = {Barcode128.CODE_C, 252, 233, 226, 228, 224, 229, 231, 234, 235, 232, 239, 238, 236, Barcode128.FNC3, Barcode128.FNC2, 201, 230, Barcode128.SHIFT, 244, 246, 242, 251, 249, 255, 214, 220, Typography.cent, Typography.pound, 165, 8359, 402, 225, 237, 243, 250, 241, 209, 170, 186, 191, 8976, 172, Typography.half, 188, 161, 171, 187, 9617, 9618, 9619, 9474, 9508, 9569, 9570, 9558, 9557, 9571, 9553, 9559, 9565, 9564, 9563, 9488, 9492, 9524, 9516, 9500, 9472, 9532, 9566, 9567, 9562, 9556, 9577, 9574, 9568, 9552, 9580, 9575, 9576, 9572, 9573, 9561, 9560, 9554, 9555, 9579, 9578, 9496, 9484, 9608, 9604, 9612, 9616, 9600, 945, 223, 915, 960, 931, 963, 181, 964, 934, 920, 937, 948, 8734, 966, 949, 8745, 8801, Typography.plusMinus, Typography.greaterOrEqual, Typography.lessOrEqual, 8992, 8993, 247, Typography.almostEqual, Typography.degree, 8729, Typography.middleDot, 8730, 8319, 178, 9632, Typography.nbsp};

        private Cp437Conversion() {
        }

        static {
            int i = 0;
            while (true) {
                char[] cArr = table;
                if (i < cArr.length) {
                    c2b.put(cArr[i], i + 128);
                    i++;
                } else {
                    return;
                }
            }
        }

        public byte[] charToByte(String str, String str2) {
            int i;
            char[] charArray = str.toCharArray();
            byte[] bArr = new byte[charArray.length];
            int i2 = 0;
            for (char c : charArray) {
                if (c < 128) {
                    i = i2 + 1;
                    bArr[i2] = (byte) c;
                } else {
                    byte b = (byte) c2b.get(c);
                    if (b != 0) {
                        i = i2 + 1;
                        bArr[i2] = b;
                    }
                }
                i2 = i;
            }
            if (i2 == r0) {
                return bArr;
            }
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            return bArr2;
        }

        public byte[] charToByte(char c, String str) {
            if (c < 128) {
                return new byte[]{(byte) c};
            }
            byte b = (byte) c2b.get(c);
            if (b == 0) {
                return new byte[0];
            }
            return new byte[]{b};
        }

        public String byteToChar(byte[] bArr, String str) {
            int i;
            char[] cArr = new char[r8];
            int i2 = 0;
            for (byte b : bArr) {
                byte b2 = b & UByte.MAX_VALUE;
                if (b2 >= 32) {
                    if (b2 < 128) {
                        i = i2 + 1;
                        cArr[i2] = (char) b2;
                    } else {
                        i = i2 + 1;
                        cArr[i2] = table[b2 + ByteCompanionObject.MIN_VALUE];
                    }
                    i2 = i;
                }
            }
            return new String(cArr, 0, i2);
        }
    }

    private static class SymbolConversion implements ExtraEncoding {

        /* renamed from: t1 */
        private static final IntHashtable f542t1 = new IntHashtable();

        /* renamed from: t2 */
        private static final IntHashtable f543t2 = new IntHashtable();
        private static final char[] table1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ' ', '!', 8704, '#', 8707, '%', Typography.amp, 8715, '(', ')', '*', '+', ',', '-', JwtParser.SEPARATOR_CHAR, '/', '0', '1', PdfWriter.VERSION_1_2, PdfWriter.VERSION_1_3, PdfWriter.VERSION_1_4, PdfWriter.VERSION_1_5, PdfWriter.VERSION_1_6, PdfWriter.VERSION_1_7, '8', '9', ':', ';', Typography.less, '=', Typography.greater, '?', 8773, 913, 914, 935, 916, 917, 934, 915, 919, 921, 977, 922, 923, 924, 925, 927, 928, 920, 929, 931, 932, 933, 962, 937, 926, 936, 918, '[', 8756, ']', 8869, '_', 773, 945, 946, 967, 948, 949, 981, 947, 951, 953, 966, 954, 955, 956, 957, 959, 960, 952, 961, 963, 964, 965, 982, 969, 958, 968, 950, '{', '|', '}', '~', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Typography.euro, 978, Typography.prime, Typography.lessOrEqual, 8260, 8734, 402, 9827, 9830, 9829, 9824, 8596, 8592, 8593, 8594, 8595, Typography.degree, Typography.plusMinus, Typography.doublePrime, Typography.greaterOrEqual, Typography.times, 8733, 8706, Typography.bullet, 247, Typography.notEqual, 8801, Typography.almostEqual, Typography.ellipsis, 9474, 9472, 8629, 8501, 8465, 8476, 8472, 8855, 8853, 8709, 8745, 8746, 8835, 8839, 8836, 8834, 8838, 8712, 8713, 8736, 8711, Typography.registered, Typography.copyright, Typography.f2134tm, 8719, 8730, 8901, 172, 8743, 8744, 8660, 8656, 8657, 8658, 8659, 9674, 9001, 0, 0, 0, 8721, 9115, 9116, 9117, 9121, 9122, 9123, 9127, 9128, 9129, 9130, 0, 9002, 8747, 8992, 9134, 8993, 9118, 9119, 9120, 9124, 9125, 9126, 9131, 9132, 9133, 0};
        private static final char[] table2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ' ', 9985, 9986, 9987, 9988, 9742, 9990, 9991, 9992, 9993, 9755, 9758, 9996, 9997, 9998, 9999, 10000, 10001, 10002, 10003, 10004, 10005, 10006, 10007, 10008, 10009, 10010, 10011, 10012, 10013, 10014, 10015, 10016, 10017, 10018, 10019, 10020, 10021, 10022, 10023, 9733, 10025, 10026, 10027, 10028, 10029, 10030, 10031, 10032, 10033, 10034, 10035, 10036, 10037, 10038, 10039, 10040, 10041, 10042, 10043, 10044, 10045, 10046, 10047, 10048, 10049, 10050, 10051, 10052, 10053, 10054, 10055, 10056, 10057, 10058, 10059, 9679, 10061, 9632, 10063, 10064, 10065, 10066, 9650, 9660, 9670, 10070, 9687, 10072, 10073, 10074, 10075, 10076, 10077, 10078, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10081, 10082, 10083, 10084, 10085, 10086, 10087, 9827, 9830, 9829, 9824, 9312, 9313, 9314, 9315, 9316, 9317, 9318, 9319, 9320, 9321, 10102, 10103, 10104, 10105, 10106, 10107, 10108, 10109, 10110, 10111, 10112, 10113, 10114, 10115, 10116, 10117, 10118, 10119, 10120, 10121, 10122, 10123, 10124, 10125, 10126, 10127, 10128, 10129, 10130, 10131, 10132, 8594, 8596, 8597, 10136, 10137, 10138, 10139, 10140, 10141, 10142, 10143, 10144, 10145, 10146, 10147, 10148, 10149, 10150, 10151, 10152, 10153, 10154, 10155, 10156, 10157, 10158, 10159, 0, 10161, 10162, 10163, 10164, 10165, 10166, 10167, 10168, 10169, 10170, 10171, 10172, 10173, 10174, 0};
        private final char[] byteToChar;
        private IntHashtable translation;

        static {
            for (int i = 0; i < 256; i++) {
                char c = table1[i];
                if (c != 0) {
                    f542t1.put(c, i);
                }
            }
            for (int i2 = 0; i2 < 256; i2++) {
                char c2 = table2[i2];
                if (c2 != 0) {
                    f543t2.put(c2, i2);
                }
            }
        }

        SymbolConversion(boolean z) {
            if (z) {
                this.translation = f542t1;
                this.byteToChar = table1;
                return;
            }
            this.translation = f543t2;
            this.byteToChar = table2;
        }

        public byte[] charToByte(String str, String str2) {
            char[] charArray = str.toCharArray();
            byte[] bArr = new byte[charArray.length];
            int i = 0;
            for (char c : charArray) {
                byte b = (byte) this.translation.get(c);
                if (b != 0) {
                    bArr[i] = b;
                    i++;
                }
            }
            if (i == r0) {
                return bArr;
            }
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, i);
            return bArr2;
        }

        public byte[] charToByte(char c, String str) {
            byte b = (byte) this.translation.get(c);
            if (b == 0) {
                return new byte[0];
            }
            return new byte[]{b};
        }

        public String byteToChar(byte[] bArr, String str) {
            int length = bArr.length;
            char[] cArr = new char[length];
            int i = 0;
            int i2 = 0;
            while (i < length) {
                cArr[i2] = this.byteToChar[bArr[i] & UByte.MAX_VALUE];
                i++;
                i2++;
            }
            return new String(cArr, 0, i2);
        }
    }

    private static class SymbolTTConversion implements ExtraEncoding {
        public String byteToChar(byte[] bArr, String str) {
            return null;
        }

        public byte[] charToByte(char c, String str) {
            char c2 = 65280 & c;
            if (c2 != 0 && c2 != 61440) {
                return new byte[0];
            }
            return new byte[]{(byte) c};
        }

        private SymbolTTConversion() {
        }

        public byte[] charToByte(String str, String str2) {
            char[] charArray = str.toCharArray();
            byte[] bArr = new byte[charArray.length];
            int i = 0;
            for (char c : charArray) {
                char c2 = 65280 & c;
                if (c2 == 0 || c2 == 61440) {
                    bArr[i] = (byte) c;
                    i++;
                }
            }
            if (i == r0) {
                return bArr;
            }
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, i);
            return bArr2;
        }
    }
}
