package com.itextpdf.text.pdf;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.exceptions.InvalidPdfException;
import com.itextpdf.text.p018io.RandomAccessSourceFactory;
import java.io.IOException;

public class PRTokeniser {
    static final String EMPTY = "";
    public static final boolean[] delims = {true, true, false, false, false, false, false, false, false, false, true, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, true, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private final RandomAccessFileOrArray file;
    protected int generation;
    protected boolean hexString;
    private final StringBuilder outBuf = new StringBuilder();
    protected int reference;
    protected String stringValue;
    protected TokenType type;

    public enum TokenType {
        NUMBER,
        STRING,
        NAME,
        COMMENT,
        START_ARRAY,
        END_ARRAY,
        START_DIC,
        END_DIC,
        REF,
        OTHER,
        ENDOFFILE
    }

    public static int getHex(int i) {
        if (i >= 48 && i <= 57) {
            return i - 48;
        }
        int i2 = 65;
        if (i < 65 || i > 70) {
            i2 = 97;
            if (i < 97 || i > 102) {
                return -1;
            }
        }
        return (i - i2) + 10;
    }

    public static final boolean isDelimiter(int i) {
        return i == 40 || i == 41 || i == 60 || i == 62 || i == 91 || i == 93 || i == 47 || i == 37;
    }

    public static final boolean isWhitespace(int i, boolean z) {
        return (z && i == 0) || i == 9 || i == 10 || i == 12 || i == 13 || i == 32;
    }

    public PRTokeniser(RandomAccessFileOrArray randomAccessFileOrArray) {
        this.file = randomAccessFileOrArray;
    }

    public void seek(long j) throws IOException {
        this.file.seek(j);
    }

    public long getFilePointer() throws IOException {
        return this.file.getFilePointer();
    }

    public void close() throws IOException {
        this.file.close();
    }

    public long length() throws IOException {
        return this.file.length();
    }

    public int read() throws IOException {
        return this.file.read();
    }

    public RandomAccessFileOrArray getSafeFile() {
        return new RandomAccessFileOrArray(this.file);
    }

    public RandomAccessFileOrArray getFile() {
        return this.file;
    }

    public String readString(int i) throws IOException {
        int read;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i2 = i - 1;
            if (i > 0 && (read = read()) != -1) {
                sb.append((char) read);
                i = i2;
            }
        }
        return sb.toString();
    }

    public static final boolean isWhitespace(int i) {
        return isWhitespace(i, true);
    }

    public static final boolean isDelimiterWhitespace(int i) {
        return delims[i + 1];
    }

    public TokenType getTokenType() {
        return this.type;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public int getReference() {
        return this.reference;
    }

    public int getGeneration() {
        return this.generation;
    }

    public void backOnePosition(int i) {
        if (i != -1) {
            this.file.pushBack((byte) i);
        }
    }

    public void throwError(String str) throws IOException {
        throw new InvalidPdfException(MessageLocalization.getComposedMessage("1.at.file.pointer.2", str, String.valueOf(this.file.getFilePointer())));
    }

    public int getHeaderOffset() throws IOException {
        String readString = readString(1024);
        int indexOf = readString.indexOf("%PDF-");
        if (indexOf >= 0 || (indexOf = readString.indexOf("%FDF-")) >= 0) {
            return indexOf;
        }
        throw new InvalidPdfException(MessageLocalization.getComposedMessage("pdf.header.not.found", new Object[0]));
    }

    public char checkPdfHeader() throws IOException {
        this.file.seek(0);
        String readString = readString(1024);
        if (readString.indexOf("%PDF-") == 0) {
            return readString.charAt(7);
        }
        throw new InvalidPdfException(MessageLocalization.getComposedMessage("pdf.header.not.found", new Object[0]));
    }

    public void checkFdfHeader() throws IOException {
        this.file.seek(0);
        if (readString(1024).indexOf("%FDF-") != 0) {
            throw new InvalidPdfException(MessageLocalization.getComposedMessage("fdf.header.not.found", new Object[0]));
        }
    }

    public long getStartxref() throws IOException {
        long j = (long) 1024;
        long length = this.file.length() - j;
        if (length < 1) {
            length = 1;
        }
        while (length > 0) {
            this.file.seek(length);
            int lastIndexOf = readString(1024).lastIndexOf("startxref");
            if (lastIndexOf >= 0) {
                return length + ((long) lastIndexOf);
            }
            length = (length - j) + 9;
        }
        throw new InvalidPdfException(MessageLocalization.getComposedMessage("pdf.startxref.not.found", new Object[0]));
    }

    public void nextValidToken() throws IOException {
        String str = null;
        String str2 = null;
        long j = 0;
        int i = 0;
        while (nextToken()) {
            if (this.type != TokenType.COMMENT) {
                if (i != 0) {
                    if (i != 1) {
                        if (this.type != TokenType.OTHER || !this.stringValue.equals("R")) {
                            this.file.seek(j);
                            this.type = TokenType.NUMBER;
                            this.stringValue = str;
                            return;
                        }
                        this.type = TokenType.REF;
                        this.reference = Integer.parseInt(str);
                        this.generation = Integer.parseInt(str2);
                        return;
                    } else if (this.type != TokenType.NUMBER) {
                        this.file.seek(j);
                        this.type = TokenType.NUMBER;
                        this.stringValue = str;
                        return;
                    } else {
                        str2 = this.stringValue;
                    }
                } else if (this.type == TokenType.NUMBER) {
                    j = this.file.getFilePointer();
                    str = this.stringValue;
                } else {
                    return;
                }
                i++;
            }
        }
        if (i == 1) {
            this.type = TokenType.NUMBER;
        }
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public boolean nextToken() throws java.io.IOException {
        /*
            r12 = this;
        L_0x0000:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            r1 = -1
            if (r0 == r1) goto L_0x000f
            boolean r2 = isWhitespace(r0)
            if (r2 != 0) goto L_0x0000
        L_0x000f:
            r2 = 0
            if (r0 != r1) goto L_0x0017
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.ENDOFFILE
            r12.type = r0
            return r2
        L_0x0017:
            java.lang.StringBuilder r3 = r12.outBuf
            r3.setLength(r2)
            java.lang.String r3 = ""
            r12.stringValue = r3
            r3 = 37
            r4 = 13
            r5 = 10
            r6 = 1
            if (r0 == r3) goto L_0x0269
            java.lang.String r3 = "error.reading.string"
            r7 = 40
            r8 = 48
            if (r0 == r7) goto L_0x01a0
            r4 = 47
            if (r0 == r4) goto L_0x0162
            r4 = 60
            r5 = 62
            if (r0 == r4) goto L_0x00ee
            if (r0 == r5) goto L_0x00d5
            r3 = 91
            if (r0 == r3) goto L_0x00cf
            r3 = 93
            if (r0 == r3) goto L_0x00c9
            java.lang.StringBuilder r3 = r12.outBuf
            r3.setLength(r2)
            r3 = 57
            r4 = 46
            r5 = 45
            if (r0 == r5) goto L_0x0076
            r7 = 43
            if (r0 == r7) goto L_0x0076
            if (r0 == r4) goto L_0x0076
            if (r0 < r8) goto L_0x005d
            if (r0 > r3) goto L_0x005d
            goto L_0x0076
        L_0x005d:
            com.itextpdf.text.pdf.PRTokeniser$TokenType r2 = com.itextpdf.text.pdf.PRTokeniser.TokenType.OTHER
            r12.type = r2
        L_0x0061:
            java.lang.StringBuilder r2 = r12.outBuf
            char r0 = (char) r0
            r2.append(r0)
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            boolean[] r2 = delims
            int r3 = r0 + 1
            boolean r2 = r2[r3]
            if (r2 == 0) goto L_0x0061
            goto L_0x00c2
        L_0x0076:
            com.itextpdf.text.pdf.PRTokeniser$TokenType r7 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NUMBER
            r12.type = r7
            if (r0 != r5) goto L_0x008e
            r0 = 0
        L_0x007d:
            int r0 = r0 + r6
            com.itextpdf.text.pdf.RandomAccessFileOrArray r7 = r12.file
            int r7 = r7.read()
            if (r7 == r5) goto L_0x007d
            java.lang.StringBuilder r9 = r12.outBuf
            r9.append(r5)
            r5 = r0
            r0 = r7
            goto L_0x009b
        L_0x008e:
            java.lang.StringBuilder r5 = r12.outBuf
            char r0 = (char) r0
            r5.append(r0)
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            r5 = 0
        L_0x009b:
            r7 = 0
        L_0x009c:
            if (r0 == r1) goto L_0x00b4
            if (r0 < r8) goto L_0x00a2
            if (r0 <= r3) goto L_0x00a4
        L_0x00a2:
            if (r0 != r4) goto L_0x00b4
        L_0x00a4:
            if (r0 != r4) goto L_0x00a7
            r7 = 1
        L_0x00a7:
            java.lang.StringBuilder r9 = r12.outBuf
            char r0 = (char) r0
            r9.append(r0)
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            goto L_0x009c
        L_0x00b4:
            if (r5 <= r6) goto L_0x00c2
            if (r7 != 0) goto L_0x00c2
            java.lang.StringBuilder r3 = r12.outBuf
            r3.setLength(r2)
            java.lang.StringBuilder r2 = r12.outBuf
            r2.append(r8)
        L_0x00c2:
            if (r0 == r1) goto L_0x0279
            r12.backOnePosition(r0)
            goto L_0x0279
        L_0x00c9:
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.END_ARRAY
            r12.type = r0
            goto L_0x0279
        L_0x00cf:
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.START_ARRAY
            r12.type = r0
            goto L_0x0279
        L_0x00d5:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            if (r0 == r5) goto L_0x00e8
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r1 = "greaterthan.not.expected"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r1, (java.lang.Object[]) r0)
            r12.throwError(r0)
        L_0x00e8:
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.END_DIC
            r12.type = r0
            goto L_0x0279
        L_0x00ee:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            if (r0 != r4) goto L_0x00fc
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.START_DIC
            r12.type = r0
            goto L_0x0279
        L_0x00fc:
            java.lang.StringBuilder r1 = r12.outBuf
            r1.setLength(r2)
            com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.STRING
            r12.type = r1
            r12.hexString = r6
            r1 = 0
        L_0x0108:
            boolean r4 = isWhitespace(r0)
            if (r4 == 0) goto L_0x0115
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            goto L_0x0108
        L_0x0115:
            if (r0 != r5) goto L_0x0118
            goto L_0x0143
        L_0x0118:
            int r0 = getHex(r0)
            if (r0 >= 0) goto L_0x011f
            goto L_0x0143
        L_0x011f:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r1 = r12.file
            int r1 = r1.read()
        L_0x0125:
            boolean r4 = isWhitespace(r1)
            if (r4 == 0) goto L_0x0132
            com.itextpdf.text.pdf.RandomAccessFileOrArray r1 = r12.file
            int r1 = r1.read()
            goto L_0x0125
        L_0x0132:
            if (r1 != r5) goto L_0x013d
            int r4 = r0 << 4
            java.lang.StringBuilder r5 = r12.outBuf
            char r4 = (char) r4
            r5.append(r4)
            goto L_0x0143
        L_0x013d:
            int r1 = getHex(r1)
            if (r1 >= 0) goto L_0x0152
        L_0x0143:
            if (r0 < 0) goto L_0x0147
            if (r1 >= 0) goto L_0x0279
        L_0x0147:
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r0)
            r12.throwError(r0)
            goto L_0x0279
        L_0x0152:
            int r0 = r0 << 4
            int r0 = r0 + r1
            java.lang.StringBuilder r4 = r12.outBuf
            char r0 = (char) r0
            r4.append(r0)
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            goto L_0x0108
        L_0x0162:
            java.lang.StringBuilder r0 = r12.outBuf
            r0.setLength(r2)
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NAME
            r12.type = r0
        L_0x016b:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            boolean[] r1 = delims
            int r2 = r0 + 1
            boolean r1 = r1[r2]
            if (r1 == 0) goto L_0x017e
            r12.backOnePosition(r0)
            goto L_0x0279
        L_0x017e:
            r1 = 35
            if (r0 != r1) goto L_0x0199
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            int r0 = getHex(r0)
            int r0 = r0 << 4
            com.itextpdf.text.pdf.RandomAccessFileOrArray r1 = r12.file
            int r1 = r1.read()
            int r1 = getHex(r1)
            int r0 = r0 + r1
        L_0x0199:
            java.lang.StringBuilder r1 = r12.outBuf
            char r0 = (char) r0
            r1.append(r0)
            goto L_0x016b
        L_0x01a0:
            java.lang.StringBuilder r0 = r12.outBuf
            r0.setLength(r2)
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.STRING
            r12.type = r0
            r12.hexString = r2
            r0 = 0
        L_0x01ac:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r9 = r12.file
            int r9 = r9.read()
            if (r9 != r1) goto L_0x01b6
            goto L_0x0255
        L_0x01b6:
            if (r9 != r7) goto L_0x01bc
            int r0 = r0 + 1
            goto L_0x0253
        L_0x01bc:
            r10 = 41
            if (r9 != r10) goto L_0x01c4
            int r0 = r0 + -1
            goto L_0x0253
        L_0x01c4:
            r11 = 92
            if (r9 != r11) goto L_0x0241
            com.itextpdf.text.pdf.RandomAccessFileOrArray r9 = r12.file
            int r9 = r9.read()
            if (r9 == r5) goto L_0x0239
            if (r9 == r4) goto L_0x022e
            if (r9 == r11) goto L_0x022c
            r11 = 98
            if (r9 == r11) goto L_0x022a
            r11 = 102(0x66, float:1.43E-43)
            if (r9 == r11) goto L_0x0227
            r11 = 110(0x6e, float:1.54E-43)
            if (r9 == r11) goto L_0x0224
            r11 = 114(0x72, float:1.6E-43)
            if (r9 == r11) goto L_0x0221
            r11 = 116(0x74, float:1.63E-43)
            if (r9 == r11) goto L_0x021e
            if (r9 == r7) goto L_0x022c
            if (r9 == r10) goto L_0x022c
            if (r9 < r8) goto L_0x022c
            r10 = 55
            if (r9 <= r10) goto L_0x01f3
            goto L_0x022c
        L_0x01f3:
            int r9 = r9 + -48
            com.itextpdf.text.pdf.RandomAccessFileOrArray r11 = r12.file
            int r11 = r11.read()
            if (r11 < r8) goto L_0x021a
            if (r11 <= r10) goto L_0x0200
            goto L_0x021a
        L_0x0200:
            int r9 = r9 << 3
            int r9 = r9 + r11
            int r9 = r9 - r8
            com.itextpdf.text.pdf.RandomAccessFileOrArray r11 = r12.file
            int r11 = r11.read()
            if (r11 < r8) goto L_0x0216
            if (r11 <= r10) goto L_0x020f
            goto L_0x0216
        L_0x020f:
            int r9 = r9 << 3
            int r9 = r9 + r11
            int r9 = r9 - r8
            r9 = r9 & 255(0xff, float:3.57E-43)
            goto L_0x022c
        L_0x0216:
            r12.backOnePosition(r11)
            goto L_0x022c
        L_0x021a:
            r12.backOnePosition(r11)
            goto L_0x022c
        L_0x021e:
            r9 = 9
            goto L_0x022c
        L_0x0221:
            r9 = 13
            goto L_0x022c
        L_0x0224:
            r9 = 10
            goto L_0x022c
        L_0x0227:
            r9 = 12
            goto L_0x022c
        L_0x022a:
            r9 = 8
        L_0x022c:
            r10 = 0
            goto L_0x023a
        L_0x022e:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r9 = r12.file
            int r9 = r9.read()
            if (r9 == r5) goto L_0x0239
            r12.backOnePosition(r9)
        L_0x0239:
            r10 = 1
        L_0x023a:
            if (r10 == 0) goto L_0x023e
            goto L_0x01ac
        L_0x023e:
            if (r9 >= 0) goto L_0x0253
            goto L_0x0255
        L_0x0241:
            if (r9 != r4) goto L_0x0253
            com.itextpdf.text.pdf.RandomAccessFileOrArray r9 = r12.file
            int r9 = r9.read()
            if (r9 >= 0) goto L_0x024c
            goto L_0x0255
        L_0x024c:
            if (r9 == r5) goto L_0x0253
            r12.backOnePosition(r9)
            r9 = 10
        L_0x0253:
            if (r0 != r1) goto L_0x0261
        L_0x0255:
            if (r9 != r1) goto L_0x0279
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r0)
            r12.throwError(r0)
            goto L_0x0279
        L_0x0261:
            java.lang.StringBuilder r10 = r12.outBuf
            char r9 = (char) r9
            r10.append(r9)
            goto L_0x01ac
        L_0x0269:
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.COMMENT
            r12.type = r0
        L_0x026d:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r12.file
            int r0 = r0.read()
            if (r0 == r1) goto L_0x0279
            if (r0 == r4) goto L_0x0279
            if (r0 != r5) goto L_0x026d
        L_0x0279:
            java.lang.StringBuilder r0 = r12.outBuf
            if (r0 == 0) goto L_0x0283
            java.lang.String r0 = r0.toString()
            r12.stringValue = r0
        L_0x0283:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PRTokeniser.nextToken():boolean");
    }

    public long longValue() {
        return Long.parseLong(this.stringValue);
    }

    public int intValue() {
        return Integer.parseInt(this.stringValue);
    }

    public boolean readLineSegment(byte[] bArr) throws IOException {
        return readLineSegment(bArr, true);
    }

    public boolean readLineSegment(byte[] bArr, boolean z) throws IOException {
        boolean z2;
        int i;
        int length = bArr.length;
        if (length > 0) {
            do {
                i = read();
            } while (isWhitespace(i, z));
            z2 = false;
        } else {
            z2 = false;
            i = -1;
        }
        int i2 = 0;
        while (!z2 && i2 < length) {
            if (!(i == -1 || i == 10)) {
                if (i != 13) {
                    bArr[i2] = (byte) i;
                    i2++;
                    if (z2 || length <= i2) {
                        break;
                    }
                    i = read();
                } else {
                    long filePointer = getFilePointer();
                    if (read() != 10) {
                        seek(filePointer);
                    }
                }
            }
            z2 = true;
            i = read();
        }
        if (i2 >= length) {
            boolean z3 = false;
            while (!z3) {
                i = read();
                if (!(i == -1 || i == 10)) {
                    if (i == 13) {
                        long filePointer2 = getFilePointer();
                        if (read() != 10) {
                            seek(filePointer2);
                        }
                    }
                }
                z3 = true;
            }
        }
        if (i == -1 && i2 == 0) {
            return false;
        }
        if (i2 + 2 <= length) {
            bArr[i2] = DocWriter.SPACE;
            bArr[i2 + 1] = 88;
        }
        return true;
    }

    public static long[] checkObjectStart(byte[] bArr) {
        try {
            PRTokeniser pRTokeniser = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(bArr)));
            if (pRTokeniser.nextToken()) {
                if (pRTokeniser.getTokenType() == TokenType.NUMBER) {
                    int intValue = pRTokeniser.intValue();
                    if (pRTokeniser.nextToken()) {
                        if (pRTokeniser.getTokenType() == TokenType.NUMBER) {
                            int intValue2 = pRTokeniser.intValue();
                            if (!pRTokeniser.nextToken() || !pRTokeniser.getStringValue().equals("obj")) {
                                return null;
                            }
                            return new long[]{(long) intValue, (long) intValue2};
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public boolean isHexString() {
        return this.hexString;
    }
}
