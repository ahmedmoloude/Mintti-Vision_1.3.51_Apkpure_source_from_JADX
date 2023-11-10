package com.itextpdf.text.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.util.ArrayList;

public class FontSelector {
    protected Font currentFont = null;
    protected ArrayList<Font> fonts = new ArrayList<>();

    public void addFont(Font font) {
        if (font.getBaseFont() != null) {
            this.fonts.add(font);
            return;
        }
        this.fonts.add(new Font(font.getCalculatedBaseFont(true), font.getSize(), font.getCalculatedStyle(), font.getColor()));
    }

    public Phrase process(String str) {
        if (this.fonts.size() != 0) {
            char[] charArray = str.toCharArray();
            int length = charArray.length;
            StringBuffer stringBuffer = new StringBuffer();
            Phrase phrase = new Phrase();
            this.currentFont = null;
            for (int i = 0; i < length; i++) {
                Chunk processChar = processChar(charArray, i, stringBuffer);
                if (processChar != null) {
                    phrase.add((Element) processChar);
                }
            }
            if (stringBuffer.length() > 0) {
                String stringBuffer2 = stringBuffer.toString();
                Font font = this.currentFont;
                if (font == null) {
                    font = this.fonts.get(0);
                }
                phrase.add((Element) new Chunk(stringBuffer2, font));
            }
            return phrase;
        }
        throw new IndexOutOfBoundsException(MessageLocalization.getComposedMessage("no.font.is.defined", new Object[0]));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008f, code lost:
        if (r8.currentFont == r10) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0095, code lost:
        if (r11.length() <= 0) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0099, code lost:
        if (r8.currentFont == null) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009b, code lost:
        r1 = new com.itextpdf.text.Chunk(r11.toString(), r8.currentFont);
        r11.setLength(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a9, code lost:
        r8.currentFont = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ab, code lost:
        r11.append(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Chunk processChar(char[] r9, int r10, java.lang.StringBuffer r11) {
        /*
            r8 = this;
            char r0 = r9[r10]
            r1 = 0
            r2 = 10
            if (r0 == r2) goto L_0x00af
            r2 = 13
            if (r0 != r2) goto L_0x000d
            goto L_0x00af
        L_0x000d:
            boolean r2 = com.itextpdf.text.Utilities.isSurrogatePair((char[]) r9, (int) r10)
            r3 = 16
            r4 = 0
            if (r2 == 0) goto L_0x0068
            int r2 = com.itextpdf.text.Utilities.convertToUtf32((char[]) r9, (int) r10)
            r5 = 0
        L_0x001b:
            java.util.ArrayList<com.itextpdf.text.Font> r6 = r8.fonts
            int r6 = r6.size()
            if (r5 >= r6) goto L_0x00b2
            java.util.ArrayList<com.itextpdf.text.Font> r6 = r8.fonts
            java.lang.Object r6 = r6.get(r5)
            com.itextpdf.text.Font r6 = (com.itextpdf.text.Font) r6
            com.itextpdf.text.pdf.BaseFont r7 = r6.getBaseFont()
            boolean r7 = r7.charExists(r2)
            if (r7 != 0) goto L_0x003f
            int r7 = java.lang.Character.getType(r2)
            if (r7 != r3) goto L_0x003c
            goto L_0x003f
        L_0x003c:
            int r5 = r5 + 1
            goto L_0x001b
        L_0x003f:
            com.itextpdf.text.Font r2 = r8.currentFont
            if (r2 == r6) goto L_0x005d
            int r2 = r11.length()
            if (r2 <= 0) goto L_0x005b
            com.itextpdf.text.Font r2 = r8.currentFont
            if (r2 == 0) goto L_0x005b
            com.itextpdf.text.Chunk r1 = new com.itextpdf.text.Chunk
            java.lang.String r2 = r11.toString()
            com.itextpdf.text.Font r3 = r8.currentFont
            r1.<init>((java.lang.String) r2, (com.itextpdf.text.Font) r3)
            r11.setLength(r4)
        L_0x005b:
            r8.currentFont = r6
        L_0x005d:
            r11.append(r0)
            int r10 = r10 + 1
            char r9 = r9[r10]
            r11.append(r9)
            goto L_0x00b2
        L_0x0068:
            r9 = 0
        L_0x0069:
            java.util.ArrayList<com.itextpdf.text.Font> r10 = r8.fonts
            int r10 = r10.size()
            if (r9 >= r10) goto L_0x00b2
            java.util.ArrayList<com.itextpdf.text.Font> r10 = r8.fonts
            java.lang.Object r10 = r10.get(r9)
            com.itextpdf.text.Font r10 = (com.itextpdf.text.Font) r10
            com.itextpdf.text.pdf.BaseFont r2 = r10.getBaseFont()
            boolean r2 = r2.charExists(r0)
            if (r2 != 0) goto L_0x008d
            int r2 = java.lang.Character.getType(r0)
            if (r2 != r3) goto L_0x008a
            goto L_0x008d
        L_0x008a:
            int r9 = r9 + 1
            goto L_0x0069
        L_0x008d:
            com.itextpdf.text.Font r9 = r8.currentFont
            if (r9 == r10) goto L_0x00ab
            int r9 = r11.length()
            if (r9 <= 0) goto L_0x00a9
            com.itextpdf.text.Font r9 = r8.currentFont
            if (r9 == 0) goto L_0x00a9
            com.itextpdf.text.Chunk r1 = new com.itextpdf.text.Chunk
            java.lang.String r9 = r11.toString()
            com.itextpdf.text.Font r2 = r8.currentFont
            r1.<init>((java.lang.String) r9, (com.itextpdf.text.Font) r2)
            r11.setLength(r4)
        L_0x00a9:
            r8.currentFont = r10
        L_0x00ab:
            r11.append(r0)
            goto L_0x00b2
        L_0x00af:
            r11.append(r0)
        L_0x00b2:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.FontSelector.processChar(char[], int, java.lang.StringBuffer):com.itextpdf.text.Chunk");
    }
}
