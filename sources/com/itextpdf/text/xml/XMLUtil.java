package com.itextpdf.text.xml;

import com.itextpdf.text.xml.xmp.XmpWriter;
import kotlin.UByte;

public class XMLUtil {
    public static boolean isValidCharacterValue(int i) {
        return i == 9 || i == 10 || i == 13 || (i >= 32 && i <= 55295) || ((i >= 57344 && i <= 65533) || (i >= 65536 && i <= 1114111));
    }

    public static String escapeXML(String str, boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        for (char c : str.toCharArray()) {
            if (c == '\"') {
                stringBuffer.append("&quot;");
            } else if (c == '<') {
                stringBuffer.append("&lt;");
            } else if (c == '>') {
                stringBuffer.append("&gt;");
            } else if (c == '&') {
                stringBuffer.append("&amp;");
            } else if (c == '\'') {
                stringBuffer.append("&apos;");
            } else if (isValidCharacterValue((int) c)) {
                if (!z || c <= 127) {
                    stringBuffer.append((char) c);
                } else {
                    stringBuffer.append("&#");
                    stringBuffer.append(c);
                    stringBuffer.append(';');
                }
            }
        }
        return stringBuffer.toString();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: char} */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r3v0, types: [char] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String unescapeXML(java.lang.String r9) {
        /*
            char[] r9 = r9.toCharArray()
            int r0 = r9.length
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            r2 = 0
        L_0x000b:
            if (r2 >= r0) goto L_0x0052
            char r3 = r9[r2]
            r4 = 38
            r5 = 1
            if (r3 != r4) goto L_0x004a
            r4 = 59
            int r6 = r2 + 3
            int r4 = findInArray(r4, r9, r6)
            r6 = -1
            if (r4 <= r6) goto L_0x004a
            java.lang.String r6 = new java.lang.String
            int r7 = r2 + 1
            int r8 = r4 - r2
            int r8 = r8 - r5
            r6.<init>(r9, r7, r8)
            java.lang.String r7 = "#"
            boolean r7 = r6.startsWith(r7)
            if (r7 == 0) goto L_0x0042
            java.lang.String r2 = r6.substring(r5)
            boolean r3 = isValidCharacterValue((java.lang.String) r2)
            if (r3 == 0) goto L_0x004f
            int r2 = java.lang.Integer.parseInt(r2)
            char r3 = (char) r2
            r2 = r4
            goto L_0x004a
        L_0x0042:
            int r6 = unescape(r6)
            if (r6 <= 0) goto L_0x004a
            r2 = r4
            r3 = r6
        L_0x004a:
            char r3 = (char) r3
            r1.append(r3)
            r4 = r2
        L_0x004f:
            int r2 = r4 + 1
            goto L_0x000b
        L_0x0052:
            java.lang.String r9 = r1.toString()
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.xml.XMLUtil.unescapeXML(java.lang.String):java.lang.String");
    }

    public static int unescape(String str) {
        if ("apos".equals(str)) {
            return 39;
        }
        if ("quot".equals(str)) {
            return 34;
        }
        if ("lt".equals(str)) {
            return 60;
        }
        if ("gt".equals(str)) {
            return 62;
        }
        return "amp".equals(str) ? 38 : -1;
    }

    public static boolean isValidCharacterValue(String str) {
        try {
            return isValidCharacterValue(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static int findInArray(char c, char[] cArr, int i) {
        while (i < cArr.length) {
            if (cArr[i] == ';') {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static String getEncodingName(byte[] bArr) {
        byte b = bArr[0] & UByte.MAX_VALUE;
        byte b2 = bArr[1] & UByte.MAX_VALUE;
        if (b == 254 && b2 == 255) {
            return XmpWriter.UTF16BE;
        }
        if (b == 255 && b2 == 254) {
            return XmpWriter.UTF16LE;
        }
        byte b3 = bArr[2] & UByte.MAX_VALUE;
        if (b == 239 && b2 == 187 && b3 == 191) {
            return "UTF-8";
        }
        byte b4 = bArr[3] & UByte.MAX_VALUE;
        if (b == 0 && b2 == 0 && b3 == 0 && b4 == 60) {
            return "ISO-10646-UCS-4";
        }
        if (b == 60 && b2 == 0 && b3 == 0 && b4 == 0) {
            return "ISO-10646-UCS-4";
        }
        if (b == 0 && b2 == 0 && b3 == 60 && b4 == 0) {
            return "ISO-10646-UCS-4";
        }
        if (b == 0 && b2 == 60 && b3 == 0 && b4 == 0) {
            return "ISO-10646-UCS-4";
        }
        if (b == 0 && b2 == 60 && b3 == 0 && b4 == 63) {
            return XmpWriter.UTF16BE;
        }
        if (b == 60 && b2 == 0 && b3 == 63 && b4 == 0) {
            return XmpWriter.UTF16LE;
        }
        if (b == 76 && b2 == 111 && b3 == 167 && b4 == 148) {
            return "CP037";
        }
        return "UTF-8";
    }
}
