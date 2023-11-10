package com.itextpdf.text;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.log.Level;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class FontFactoryImp implements FontProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) FontFactoryImp.class);
    private static String[] TTFamilyOrder = {ExifInterface.GPS_MEASUREMENT_3D, "1", "1033", ExifInterface.GPS_MEASUREMENT_3D, "0", "1033", "1", "0", "0", "0", ExifInterface.GPS_MEASUREMENT_3D, "0"};
    public boolean defaultEmbedding = false;
    public String defaultEncoding = "Cp1252";
    private final Hashtable<String, ArrayList<String>> fontFamilies;
    private final Hashtable<String, String> trueTypeFonts;

    public FontFactoryImp() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        this.trueTypeFonts = hashtable;
        Hashtable<String, ArrayList<String>> hashtable2 = new Hashtable<>();
        this.fontFamilies = hashtable2;
        hashtable.put("Courier".toLowerCase(), "Courier");
        hashtable.put("Courier-Bold".toLowerCase(), "Courier-Bold");
        hashtable.put("Courier-Oblique".toLowerCase(), "Courier-Oblique");
        hashtable.put("Courier-BoldOblique".toLowerCase(), "Courier-BoldOblique");
        hashtable.put("Helvetica".toLowerCase(), "Helvetica");
        hashtable.put("Helvetica-Bold".toLowerCase(), "Helvetica-Bold");
        hashtable.put("Helvetica-Oblique".toLowerCase(), "Helvetica-Oblique");
        hashtable.put("Helvetica-BoldOblique".toLowerCase(), "Helvetica-BoldOblique");
        hashtable.put("Symbol".toLowerCase(), "Symbol");
        hashtable.put("Times-Roman".toLowerCase(), "Times-Roman");
        hashtable.put("Times-Bold".toLowerCase(), "Times-Bold");
        hashtable.put("Times-Italic".toLowerCase(), "Times-Italic");
        hashtable.put("Times-BoldItalic".toLowerCase(), "Times-BoldItalic");
        hashtable.put("ZapfDingbats".toLowerCase(), "ZapfDingbats");
        ArrayList arrayList = new ArrayList();
        arrayList.add("Courier");
        arrayList.add("Courier-Bold");
        arrayList.add("Courier-Oblique");
        arrayList.add("Courier-BoldOblique");
        hashtable2.put("Courier".toLowerCase(), arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("Helvetica");
        arrayList2.add("Helvetica-Bold");
        arrayList2.add("Helvetica-Oblique");
        arrayList2.add("Helvetica-BoldOblique");
        hashtable2.put("Helvetica".toLowerCase(), arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("Symbol");
        hashtable2.put("Symbol".toLowerCase(), arrayList3);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add("Times-Roman");
        arrayList4.add("Times-Bold");
        arrayList4.add("Times-Italic");
        arrayList4.add("Times-BoldItalic");
        hashtable2.put(FontFactory.TIMES.toLowerCase(), arrayList4);
        hashtable2.put("Times-Roman".toLowerCase(), arrayList4);
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add("ZapfDingbats");
        hashtable2.put("ZapfDingbats".toLowerCase(), arrayList5);
    }

    public Font getFont(String str, String str2, boolean z, float f, int i, BaseColor baseColor) {
        return getFont(str, str2, z, f, i, baseColor, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0063 A[LOOP:0: B:13:0x002c->B:29:0x0063, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0061 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Font getFont(java.lang.String r15, java.lang.String r16, boolean r17, float r18, int r19, com.itextpdf.text.BaseColor r20, boolean r21) {
        /*
            r14 = this;
            r1 = r14
            r0 = r18
            r2 = r19
            r3 = r20
            if (r15 != 0) goto L_0x0011
            com.itextpdf.text.Font r4 = new com.itextpdf.text.Font
            com.itextpdf.text.Font$FontFamily r5 = com.itextpdf.text.Font.FontFamily.UNDEFINED
            r4.<init>((com.itextpdf.text.Font.FontFamily) r5, (float) r0, (int) r2, (com.itextpdf.text.BaseColor) r3)
            return r4
        L_0x0011:
            java.lang.String r4 = r15.toLowerCase()
            java.util.Hashtable<java.lang.String, java.util.ArrayList<java.lang.String>> r5 = r1.fontFamilies
            java.lang.Object r4 = r5.get(r4)
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            if (r4 == 0) goto L_0x0072
            monitor-enter(r4)
            r5 = 0
            r6 = -1
            if (r2 != r6) goto L_0x0026
            r7 = 0
            goto L_0x0027
        L_0x0026:
            r7 = r2
        L_0x0027:
            java.util.Iterator r8 = r4.iterator()     // Catch:{ all -> 0x006f }
            r9 = 0
        L_0x002c:
            boolean r10 = r8.hasNext()     // Catch:{ all -> 0x006f }
            r11 = 1
            if (r10 == 0) goto L_0x0065
            java.lang.Object r9 = r8.next()     // Catch:{ all -> 0x006f }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x006f }
            java.lang.String r10 = r9.toLowerCase()     // Catch:{ all -> 0x006f }
            java.lang.String r12 = "bold"
            int r12 = r10.indexOf(r12)     // Catch:{ all -> 0x006f }
            if (r12 == r6) goto L_0x0047
            r12 = 1
            goto L_0x0048
        L_0x0047:
            r12 = 0
        L_0x0048:
            java.lang.String r13 = "italic"
            int r13 = r10.indexOf(r13)     // Catch:{ all -> 0x006f }
            if (r13 != r6) goto L_0x005b
            java.lang.String r13 = "oblique"
            int r10 = r10.indexOf(r13)     // Catch:{ all -> 0x006f }
            if (r10 == r6) goto L_0x0059
            goto L_0x005b
        L_0x0059:
            r10 = r12
            goto L_0x005d
        L_0x005b:
            r10 = r12 | 2
        L_0x005d:
            r12 = r7 & 3
            if (r12 != r10) goto L_0x0063
            r5 = 1
            goto L_0x0067
        L_0x0063:
            r9 = r10
            goto L_0x002c
        L_0x0065:
            r10 = r9
            r9 = r15
        L_0x0067:
            if (r2 == r6) goto L_0x006d
            if (r5 == 0) goto L_0x006d
            int r5 = ~r10     // Catch:{ all -> 0x006f }
            r2 = r2 & r5
        L_0x006d:
            monitor-exit(r4)     // Catch:{ all -> 0x006f }
            goto L_0x0073
        L_0x006f:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x006f }
            throw r0
        L_0x0072:
            r9 = r15
        L_0x0073:
            r4 = r16
            r5 = r17
            r6 = r21
            com.itextpdf.text.pdf.BaseFont r4 = r14.getBaseFont(r9, r4, r5, r6)     // Catch:{ DocumentException -> 0x009d, IOException -> 0x0095, NullPointerException -> 0x008d }
            if (r4 != 0) goto L_0x0087
            com.itextpdf.text.Font r4 = new com.itextpdf.text.Font     // Catch:{ DocumentException -> 0x009d, IOException -> 0x0095, NullPointerException -> 0x008d }
            com.itextpdf.text.Font$FontFamily r5 = com.itextpdf.text.Font.FontFamily.UNDEFINED     // Catch:{ DocumentException -> 0x009d, IOException -> 0x0095, NullPointerException -> 0x008d }
            r4.<init>((com.itextpdf.text.Font.FontFamily) r5, (float) r0, (int) r2, (com.itextpdf.text.BaseColor) r3)     // Catch:{ DocumentException -> 0x009d, IOException -> 0x0095, NullPointerException -> 0x008d }
            return r4
        L_0x0087:
            com.itextpdf.text.Font r5 = new com.itextpdf.text.Font
            r5.<init>((com.itextpdf.text.pdf.BaseFont) r4, (float) r0, (int) r2, (com.itextpdf.text.BaseColor) r3)
            return r5
        L_0x008d:
            com.itextpdf.text.Font r4 = new com.itextpdf.text.Font
            com.itextpdf.text.Font$FontFamily r5 = com.itextpdf.text.Font.FontFamily.UNDEFINED
            r4.<init>((com.itextpdf.text.Font.FontFamily) r5, (float) r0, (int) r2, (com.itextpdf.text.BaseColor) r3)
            return r4
        L_0x0095:
            com.itextpdf.text.Font r4 = new com.itextpdf.text.Font
            com.itextpdf.text.Font$FontFamily r5 = com.itextpdf.text.Font.FontFamily.UNDEFINED
            r4.<init>((com.itextpdf.text.Font.FontFamily) r5, (float) r0, (int) r2, (com.itextpdf.text.BaseColor) r3)
            return r4
        L_0x009d:
            r0 = move-exception
            com.itextpdf.text.ExceptionConverter r2 = new com.itextpdf.text.ExceptionConverter
            r2.<init>(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.FontFactoryImp.getFont(java.lang.String, java.lang.String, boolean, float, int, com.itextpdf.text.BaseColor, boolean):com.itextpdf.text.Font");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000f, code lost:
        r1 = r7.trueTypeFonts.get(r8.toLowerCase());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.BaseFont getBaseFont(java.lang.String r8, java.lang.String r9, boolean r10, boolean r11) throws java.io.IOException, com.itextpdf.text.DocumentException {
        /*
            r7 = this;
            r4 = 0
            r5 = 0
            r6 = 1
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r11
            com.itextpdf.text.pdf.BaseFont r0 = com.itextpdf.text.pdf.BaseFont.createFont(r0, r1, r2, r3, r4, r5, r6)     // Catch:{ DocumentException -> 0x000c }
            goto L_0x000d
        L_0x000c:
            r0 = 0
        L_0x000d:
            if (r0 != 0) goto L_0x0027
            java.util.Hashtable<java.lang.String, java.lang.String> r1 = r7.trueTypeFonts
            java.lang.String r8 = r8.toLowerCase()
            java.lang.Object r8 = r1.get(r8)
            r1 = r8
            java.lang.String r1 = (java.lang.String) r1
            if (r1 == 0) goto L_0x0027
            r5 = 0
            r6 = 0
            r2 = r9
            r3 = r10
            r4 = r11
            com.itextpdf.text.pdf.BaseFont r0 = com.itextpdf.text.pdf.BaseFont.createFont(r1, r2, r3, r4, r5, r6)
        L_0x0027:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.FontFactoryImp.getBaseFont(java.lang.String, java.lang.String, boolean, boolean):com.itextpdf.text.pdf.BaseFont");
    }

    public Font getFont(String str, String str2, boolean z, float f, int i) {
        return getFont(str, str2, z, f, i, (BaseColor) null);
    }

    public Font getFont(String str, String str2, boolean z, float f) {
        return getFont(str, str2, z, f, -1, (BaseColor) null);
    }

    public Font getFont(String str, String str2, boolean z) {
        return getFont(str, str2, z, -1.0f, -1, (BaseColor) null);
    }

    public Font getFont(String str, String str2, float f, int i, BaseColor baseColor) {
        return getFont(str, str2, this.defaultEmbedding, f, i, baseColor);
    }

    public Font getFont(String str, String str2, float f, int i) {
        return getFont(str, str2, this.defaultEmbedding, f, i, (BaseColor) null);
    }

    public Font getFont(String str, String str2, float f) {
        return getFont(str, str2, this.defaultEmbedding, f, -1, (BaseColor) null);
    }

    public Font getFont(String str, float f, BaseColor baseColor) {
        return getFont(str, this.defaultEncoding, this.defaultEmbedding, f, -1, baseColor);
    }

    public Font getFont(String str, String str2) {
        return getFont(str, str2, this.defaultEmbedding, -1.0f, -1, (BaseColor) null);
    }

    public Font getFont(String str, float f, int i, BaseColor baseColor) {
        return getFont(str, this.defaultEncoding, this.defaultEmbedding, f, i, baseColor);
    }

    public Font getFont(String str, float f, int i) {
        return getFont(str, this.defaultEncoding, this.defaultEmbedding, f, i, (BaseColor) null);
    }

    public Font getFont(String str, float f) {
        return getFont(str, this.defaultEncoding, this.defaultEmbedding, f, -1, (BaseColor) null);
    }

    public Font getFont(String str) {
        return getFont(str, this.defaultEncoding, this.defaultEmbedding, -1.0f, -1, (BaseColor) null);
    }

    public void registerFamily(String str, String str2, String str3) {
        ArrayList arrayList;
        boolean z;
        if (str3 != null) {
            this.trueTypeFonts.put(str2, str3);
        }
        synchronized (this.fontFamilies) {
            arrayList = this.fontFamilies.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.fontFamilies.put(str, arrayList);
            }
        }
        synchronized (arrayList) {
            if (!arrayList.contains(str2)) {
                int length = str2.length();
                int i = 0;
                while (true) {
                    if (i >= arrayList.size()) {
                        z = false;
                        break;
                    } else if (((String) arrayList.get(i)).length() >= length) {
                        arrayList.add(i, str2);
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    arrayList.add(str2);
                    String lowerCase = str2.toLowerCase();
                    if (lowerCase.endsWith("regular")) {
                        arrayList.add(0, str2.substring(0, lowerCase.substring(0, lowerCase.length() - 7).trim().length()));
                    }
                }
            }
        }
    }

    public void register(String str) {
        register(str, (String) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:70:0x019d A[Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void register(java.lang.String r14, java.lang.String r15) {
        /*
            r13 = this;
            java.lang.String r0 = r14.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r1 = ".ttf"
            boolean r0 = r0.endsWith(r1)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r1 = "Cp1252"
            r2 = 0
            r3 = 3
            r4 = 1
            r5 = 0
            if (r0 != 0) goto L_0x00ae
            java.lang.String r0 = r14.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r6 = ".otf"
            boolean r0 = r0.endsWith(r6)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r0 != 0) goto L_0x00ae
            java.lang.String r0 = r14.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r6 = ".ttc,"
            int r0 = r0.indexOf(r6)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r0 <= 0) goto L_0x002c
            goto L_0x00ae
        L_0x002c:
            java.lang.String r0 = r14.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r6 = ".ttc"
            boolean r0 = r0.endsWith(r6)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r0 == 0) goto L_0x0063
            if (r15 == 0) goto L_0x0041
            com.itextpdf.text.log.Logger r15 = LOGGER     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r0 = "You can't define an alias for a true type collection."
            r15.error(r0)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
        L_0x0041:
            java.lang.String[] r15 = com.itextpdf.text.pdf.BaseFont.enumerateTTCNames((java.lang.String) r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r0 = 0
        L_0x0046:
            int r1 = r15.length     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r0 >= r1) goto L_0x0193
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1.<init>()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1.append(r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r2 = ","
            r1.append(r2)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1.append(r0)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r1 = r1.toString()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r13.register(r1)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r0 = r0 + 1
            goto L_0x0046
        L_0x0063:
            java.lang.String r15 = r14.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r0 = ".afm"
            boolean r15 = r15.endsWith(r0)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r15 != 0) goto L_0x007b
            java.lang.String r15 = r14.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r0 = ".pfm"
            boolean r15 = r15.endsWith(r0)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r15 == 0) goto L_0x0193
        L_0x007b:
            com.itextpdf.text.pdf.BaseFont r15 = com.itextpdf.text.pdf.BaseFont.createFont(r14, r1, r5)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[][] r0 = r15.getFullFontName()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r0 = r0[r5]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r0 = r0[r3]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r0 = r0.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[][] r1 = r15.getFamilyFontName()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1 = r1[r5]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1 = r1[r3]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r15 = r15.getPostscriptFontName()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r15 = r15.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r13.registerFamily(r1, r0, r2)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.util.Hashtable<java.lang.String, java.lang.String> r1 = r13.trueTypeFonts     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1.put(r15, r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.util.Hashtable<java.lang.String, java.lang.String> r15 = r13.trueTypeFonts     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r15.put(r0, r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            goto L_0x0193
        L_0x00ae:
            java.lang.Object[] r0 = com.itextpdf.text.pdf.BaseFont.getAllFontNames(r14, r1, r2)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.util.Hashtable<java.lang.String, java.lang.String> r1 = r13.trueTypeFonts     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r6 = r0[r5]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r6 = r6.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1.put(r6, r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r1 = "regular"
            if (r15 == 0) goto L_0x00d5
            java.lang.String r15 = r15.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.util.Hashtable<java.lang.String, java.lang.String> r6 = r13.trueTypeFonts     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r6.put(r15, r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r6 = r15.endsWith(r1)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r6 == 0) goto L_0x00d5
            r13.saveCopyOfRegularFont(r15, r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
        L_0x00d5:
            r15 = 2
            r6 = r0[r15]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[][] r6 = (java.lang.String[][]) r6     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[][] r6 = (java.lang.String[][]) r6     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r7 = r6.length     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r8 = 0
        L_0x00de:
            if (r8 >= r7) goto L_0x00f9
            r9 = r6[r8]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r9 = r9[r3]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r9 = r9.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.util.Hashtable<java.lang.String, java.lang.String> r10 = r13.trueTypeFonts     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r10.put(r9, r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r10 = r9.endsWith(r1)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r10 == 0) goto L_0x00f6
            r13.saveCopyOfRegularFont(r9, r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
        L_0x00f6:
            int r8 = r8 + 1
            goto L_0x00de
        L_0x00f9:
            r1 = r0[r4]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[][] r1 = (java.lang.String[][]) r1     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[][] r1 = (java.lang.String[][]) r1     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r7 = r2
            r6 = 0
        L_0x0101:
            java.lang.String[] r8 = TTFamilyOrder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r8 = r8.length     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r6 >= r8) goto L_0x0143
            int r8 = r1.length     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r9 = 0
        L_0x0108:
            if (r9 >= r8) goto L_0x0141
            r10 = r1[r9]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[] r11 = TTFamilyOrder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r11 = r11[r6]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r12 = r10[r5]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r11 = r11.equals(r12)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r11 == 0) goto L_0x013e
            java.lang.String[] r11 = TTFamilyOrder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r12 = r6 + 1
            r11 = r11[r12]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r12 = r10[r4]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r11 = r11.equals(r12)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r11 == 0) goto L_0x013e
            java.lang.String[] r11 = TTFamilyOrder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r12 = r6 + 2
            r11 = r11[r12]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r12 = r10[r15]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r11 = r11.equals(r12)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r11 == 0) goto L_0x013e
            r6 = r10[r3]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r7 = r6.toLowerCase()     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[] r6 = TTFamilyOrder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r6 = r6.length     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            goto L_0x0141
        L_0x013e:
            int r9 = r9 + 1
            goto L_0x0108
        L_0x0141:
            int r6 = r6 + r3
            goto L_0x0101
        L_0x0143:
            if (r7 == 0) goto L_0x0193
            java.lang.String r1 = ""
            r0 = r0[r15]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[][] r0 = (java.lang.String[][]) r0     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String[][] r0 = (java.lang.String[][]) r0     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r6 = r0.length     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r8 = 0
        L_0x014f:
            if (r8 >= r6) goto L_0x0193
            r9 = r0[r8]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r10 = 0
        L_0x0154:
            java.lang.String[] r11 = TTFamilyOrder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r12 = r11.length     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r10 >= r12) goto L_0x0190
            r11 = r11[r10]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r12 = r9[r5]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r11 = r11.equals(r12)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r11 == 0) goto L_0x018d
            java.lang.String[] r11 = TTFamilyOrder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r12 = r10 + 1
            r11 = r11[r12]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r12 = r9[r4]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r11 = r11.equals(r12)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r11 == 0) goto L_0x018d
            java.lang.String[] r11 = TTFamilyOrder     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            int r12 = r10 + 2
            r11 = r11[r12]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r12 = r9[r15]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r11 = r11.equals(r12)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r11 == 0) goto L_0x018d
            r11 = r9[r3]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r12 = r11.equals(r1)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r12 == 0) goto L_0x0188
            goto L_0x018d
        L_0x0188:
            r13.registerFamily(r7, r11, r2)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1 = r11
            goto L_0x0190
        L_0x018d:
            int r10 = r10 + 3
            goto L_0x0154
        L_0x0190:
            int r8 = r8 + 1
            goto L_0x014f
        L_0x0193:
            com.itextpdf.text.log.Logger r15 = LOGGER     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            com.itextpdf.text.log.Level r0 = com.itextpdf.text.log.Level.TRACE     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            boolean r0 = r15.isLogging(r0)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            if (r0 == 0) goto L_0x01aa
            java.lang.String r0 = "Registered %s"
            java.lang.Object[] r1 = new java.lang.Object[r4]     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r1[r5] = r14     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            java.lang.String r14 = java.lang.String.format(r0, r1)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
            r15.trace(r14)     // Catch:{ DocumentException -> 0x01b2, IOException -> 0x01ab }
        L_0x01aa:
            return
        L_0x01ab:
            r14 = move-exception
            com.itextpdf.text.ExceptionConverter r15 = new com.itextpdf.text.ExceptionConverter
            r15.<init>(r14)
            throw r15
        L_0x01b2:
            r14 = move-exception
            com.itextpdf.text.ExceptionConverter r15 = new com.itextpdf.text.ExceptionConverter
            r15.<init>(r14)
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.FontFactoryImp.register(java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    public boolean saveCopyOfRegularFont(String str, String str2) {
        String trim = str.substring(0, str.length() - 7).trim();
        if (this.trueTypeFonts.containsKey(trim)) {
            return false;
        }
        this.trueTypeFonts.put(trim, str2);
        return true;
    }

    public int registerDirectory(String str) {
        return registerDirectory(str, false);
    }

    public int registerDirectory(String str, boolean z) {
        Logger logger = LOGGER;
        int i = 0;
        if (logger.isLogging(Level.DEBUG)) {
            logger.debug(String.format("Registering directory %s, looking for fonts", new Object[]{str}));
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                if (file.isDirectory()) {
                    String[] list = file.list();
                    if (list == null) {
                        return 0;
                    }
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 < list.length) {
                        try {
                            try {
                                File file2 = new File(str, list[i2]);
                                if (!file2.isDirectory()) {
                                    String path = file2.getPath();
                                    String lowerCase = path.length() < 4 ? null : path.substring(path.length() - 4).toLowerCase();
                                    if (!".afm".equals(lowerCase)) {
                                        if (!".pfm".equals(lowerCase)) {
                                            if (".ttf".equals(lowerCase) || ".otf".equals(lowerCase) || ".ttc".equals(lowerCase)) {
                                                register(path, (String) null);
                                                i3++;
                                            }
                                        }
                                    }
                                    if (new File(path.substring(0, path.length() - 4) + ".pfb").exists()) {
                                        register(path, (String) null);
                                        i3++;
                                    }
                                } else if (z) {
                                    i3 += registerDirectory(file2.getAbsolutePath(), true);
                                }
                            } catch (Exception unused) {
                            }
                            i2++;
                        } catch (Exception unused2) {
                            i = i3;
                            return i;
                        }
                    }
                    return i3;
                }
            }
            return 0;
        } catch (Exception unused3) {
            return i;
        }
    }

    public int registerDirectories() {
        String str = System.getenv("windir");
        String property = System.getProperty("file.separator");
        int i = 0;
        if (!(str == null || property == null)) {
            i = 0 + registerDirectory(str + property + "fonts");
        }
        return i + registerDirectory("/usr/share/X11/fonts", true) + registerDirectory("/usr/X/lib/X11/fonts", true) + registerDirectory("/usr/openwin/lib/X11/fonts", true) + registerDirectory("/usr/share/fonts", true) + registerDirectory("/usr/X11R6/lib/X11/fonts", true) + registerDirectory("/Library/Fonts") + registerDirectory("/System/Library/Fonts");
    }

    public Set<String> getRegisteredFonts() {
        return this.trueTypeFonts.keySet();
    }

    public Set<String> getRegisteredFamilies() {
        return this.fontFamilies.keySet();
    }

    public boolean isRegistered(String str) {
        return this.trueTypeFonts.containsKey(str.toLowerCase());
    }
}
