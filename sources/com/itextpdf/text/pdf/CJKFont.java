package com.itextpdf.text.pdf;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.p018io.StreamUtil;
import com.itextpdf.text.pdf.fonts.cmaps.CMapCache;
import com.itextpdf.text.pdf.fonts.cmaps.CMapCidByte;
import com.itextpdf.text.pdf.fonts.cmaps.CMapCidUni;
import com.itextpdf.text.pdf.fonts.cmaps.CMapUniCid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

class CJKFont extends BaseFont {
    private static final int BRACKET = 1;
    static final String CJK_ENCODING = "UnicodeBigUnmarked";
    private static final int FIRST = 0;
    public static final String RESOURCE_PATH_CMAP = "com/itextpdf/text/pdf/fonts/cmaps/";
    private static final int SERIAL = 2;
    private static final int V1Y = 880;
    private static final HashMap<String, HashMap<String, Object>> allFonts = new HashMap<>();
    static Properties cjkEncodings = new Properties();
    static Properties cjkFonts = new Properties();
    private static boolean propertiesLoaded = false;
    private static final HashMap<String, Set<String>> registryNames = new HashMap<>();
    private String CMap;
    private CMapCidByte cidByte;
    private boolean cidDirect = false;
    private CMapCidUni cidUni;
    private HashMap<String, Object> fontDesc;
    private String fontName;
    private IntHashtable hMetrics;
    private String style = "";
    private CMapUniCid uniCid;
    private String uniMap;
    private IntHashtable vMetrics;

    public int[] getCharBBox(int i) {
        return null;
    }

    public PdfStream getFullFontStream() {
        return null;
    }

    public int getKerning(int i, int i2) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int[] getRawCharBBox(int i, String str) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getRawWidth(int i, String str) {
        return 0;
    }

    public boolean hasKernPairs() {
        return false;
    }

    public boolean setCharAdvance(int i, int i2) {
        return false;
    }

    public boolean setKerning(int i, int i2, int i3) {
        return false;
    }

    private static void loadProperties() {
        if (!propertiesLoaded) {
            synchronized (allFonts) {
                if (!propertiesLoaded) {
                    try {
                        loadRegistry();
                        for (String str : registryNames.get("fonts")) {
                            allFonts.put(str, readFontProperties(str));
                        }
                    } catch (Exception unused) {
                    }
                    propertiesLoaded = true;
                }
            }
        }
    }

    private static void loadRegistry() throws IOException {
        InputStream resourceStream = StreamUtil.getResourceStream("com/itextpdf/text/pdf/fonts/cmaps/cjk_registry.properties");
        Properties properties = new Properties();
        properties.load(resourceStream);
        resourceStream.close();
        for (String str : properties.keySet()) {
            String[] split = properties.getProperty(str).split(" ");
            HashSet hashSet = new HashSet();
            for (String str2 : split) {
                if (str2.length() > 0) {
                    hashSet.add(str2);
                }
            }
            registryNames.put(str, hashSet);
        }
    }

    CJKFont(String str, String str2, boolean z) throws DocumentException {
        loadProperties();
        this.fontType = 2;
        String baseName = getBaseName(str);
        if (isCJKFont(baseName, str2)) {
            if (baseName.length() < str.length()) {
                this.style = str.substring(baseName.length());
                str = baseName;
            }
            this.fontName = str;
            this.encoding = CJK_ENCODING;
            this.vertical = str2.endsWith(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
            this.CMap = str2;
            if (str2.equals(BaseFont.IDENTITY_H) || str2.equals(BaseFont.IDENTITY_V)) {
                this.cidDirect = true;
            }
            loadCMaps();
            return;
        }
        throw new DocumentException(MessageLocalization.getComposedMessage("font.1.with.2.encoding.is.not.a.cjk.font", str, str2));
    }

    /* access modifiers changed from: package-private */
    public String getUniMap() {
        return this.uniMap;
    }

    private void loadCMaps() throws DocumentException {
        try {
            HashMap<String, Object> hashMap = allFonts.get(this.fontName);
            this.fontDesc = hashMap;
            this.hMetrics = (IntHashtable) hashMap.get(ExifInterface.LONGITUDE_WEST);
            this.vMetrics = (IntHashtable) this.fontDesc.get("W2");
            this.uniMap = "";
            HashMap<String, Set<String>> hashMap2 = registryNames;
            Iterator it = hashMap2.get(((String) this.fontDesc.get("Registry")) + "_Uni").iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String str = (String) it.next();
                this.uniMap = str;
                if (!str.endsWith(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) || !this.vertical) {
                    if (!str.endsWith(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) && !this.vertical) {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (this.cidDirect) {
                this.cidUni = CMapCache.getCachedCMapCidUni(this.uniMap);
                return;
            }
            this.uniCid = CMapCache.getCachedCMapUniCid(this.uniMap);
            this.cidByte = CMapCache.getCachedCMapCidByte(this.CMap);
        } catch (Exception e) {
            throw new DocumentException(e);
        }
    }

    public static String GetCompatibleFont(String str) {
        loadProperties();
        for (Map.Entry next : registryNames.entrySet()) {
            if (((Set) next.getValue()).contains(str)) {
                String str2 = (String) next.getKey();
                for (Map.Entry next2 : allFonts.entrySet()) {
                    if (str2.equals(((HashMap) next2.getValue()).get("Registry"))) {
                        return (String) next2.getKey();
                    }
                }
                continue;
            }
        }
        return null;
    }

    public static boolean isCJKFont(String str, String str2) {
        loadProperties();
        HashMap<String, Set<String>> hashMap = registryNames;
        if (!hashMap.containsKey("fonts") || !hashMap.get("fonts").contains(str)) {
            return false;
        }
        if (str2.equals(BaseFont.IDENTITY_H) || str2.equals(BaseFont.IDENTITY_V)) {
            return true;
        }
        Set set = hashMap.get((String) allFonts.get(str).get("Registry"));
        if (set == null || !set.contains(str2)) {
            return false;
        }
        return true;
    }

    public int getWidth(int i) {
        int i2;
        if (!this.cidDirect) {
            i = this.uniCid.lookup(i);
        }
        if (this.vertical) {
            i2 = this.vMetrics.get(i);
        } else {
            i2 = this.hMetrics.get(i);
        }
        if (i2 > 0) {
            return i2;
        }
        return 1000;
    }

    public int getWidth(String str) {
        int i;
        int i2;
        int i3 = 0;
        if (this.cidDirect) {
            i = 0;
            while (i3 < str.length()) {
                i += getWidth((int) str.charAt(i3));
                i3++;
            }
        } else {
            int i4 = 0;
            while (i3 < str.length()) {
                if (Utilities.isSurrogatePair(str, i3)) {
                    i2 = Utilities.convertToUtf32(str, i3);
                    i3++;
                } else {
                    i2 = str.charAt(i3);
                }
                i4 = i + getWidth(i2);
                i3++;
            }
        }
        return i;
    }

    private PdfDictionary getFontDescriptor() {
        PdfDictionary pdfDictionary = new PdfDictionary(PdfName.FONTDESCRIPTOR);
        pdfDictionary.put(PdfName.ASCENT, new PdfLiteral((String) this.fontDesc.get("Ascent")));
        pdfDictionary.put(PdfName.CAPHEIGHT, new PdfLiteral((String) this.fontDesc.get("CapHeight")));
        pdfDictionary.put(PdfName.DESCENT, new PdfLiteral((String) this.fontDesc.get("Descent")));
        pdfDictionary.put(PdfName.FLAGS, new PdfLiteral((String) this.fontDesc.get("Flags")));
        pdfDictionary.put(PdfName.FONTBBOX, new PdfLiteral((String) this.fontDesc.get("FontBBox")));
        PdfName pdfName = PdfName.FONTNAME;
        pdfDictionary.put(pdfName, new PdfName(this.fontName + this.style));
        pdfDictionary.put(PdfName.ITALICANGLE, new PdfLiteral((String) this.fontDesc.get("ItalicAngle")));
        pdfDictionary.put(PdfName.STEMV, new PdfLiteral((String) this.fontDesc.get("StemV")));
        PdfDictionary pdfDictionary2 = new PdfDictionary();
        pdfDictionary2.put(PdfName.PANOSE, new PdfString((String) this.fontDesc.get("Panose"), (String) null));
        pdfDictionary.put(PdfName.STYLE, pdfDictionary2);
        return pdfDictionary;
    }

    private PdfDictionary getCIDFont(PdfIndirectReference pdfIndirectReference, IntHashtable intHashtable) {
        PdfDictionary pdfDictionary = new PdfDictionary(PdfName.FONT);
        pdfDictionary.put(PdfName.SUBTYPE, PdfName.CIDFONTTYPE0);
        PdfName pdfName = PdfName.BASEFONT;
        pdfDictionary.put(pdfName, new PdfName(this.fontName + this.style));
        pdfDictionary.put(PdfName.FONTDESCRIPTOR, pdfIndirectReference);
        int[] orderedKeys = intHashtable.toOrderedKeys();
        String convertToHCIDMetrics = convertToHCIDMetrics(orderedKeys, this.hMetrics);
        if (convertToHCIDMetrics != null) {
            pdfDictionary.put(PdfName.f668W, new PdfLiteral(convertToHCIDMetrics));
        }
        if (this.vertical) {
            String convertToVCIDMetrics = convertToVCIDMetrics(orderedKeys, this.vMetrics, this.hMetrics);
            if (convertToVCIDMetrics != null) {
                pdfDictionary.put(PdfName.f669W2, new PdfLiteral(convertToVCIDMetrics));
            }
        } else {
            pdfDictionary.put(PdfName.f577DW, new PdfNumber(1000));
        }
        PdfDictionary pdfDictionary2 = new PdfDictionary();
        if (this.cidDirect) {
            pdfDictionary2.put(PdfName.REGISTRY, new PdfString(this.cidUni.getRegistry(), (String) null));
            pdfDictionary2.put(PdfName.ORDERING, new PdfString(this.cidUni.getOrdering(), (String) null));
            pdfDictionary2.put(PdfName.SUPPLEMENT, new PdfNumber(this.cidUni.getSupplement()));
        } else {
            pdfDictionary2.put(PdfName.REGISTRY, new PdfString(this.cidByte.getRegistry(), (String) null));
            pdfDictionary2.put(PdfName.ORDERING, new PdfString(this.cidByte.getOrdering(), (String) null));
            pdfDictionary2.put(PdfName.SUPPLEMENT, new PdfNumber(this.cidByte.getSupplement()));
        }
        pdfDictionary.put(PdfName.CIDSYSTEMINFO, pdfDictionary2);
        return pdfDictionary;
    }

    private PdfDictionary getFontBaseType(PdfIndirectReference pdfIndirectReference) {
        PdfDictionary pdfDictionary = new PdfDictionary(PdfName.FONT);
        pdfDictionary.put(PdfName.SUBTYPE, PdfName.TYPE0);
        String str = this.fontName;
        if (this.style.length() > 0) {
            str = str + "-" + this.style.substring(1);
        }
        pdfDictionary.put(PdfName.BASEFONT, new PdfName(str + "-" + this.CMap));
        pdfDictionary.put(PdfName.ENCODING, new PdfName(this.CMap));
        pdfDictionary.put(PdfName.DESCENDANTFONTS, new PdfArray((PdfObject) pdfIndirectReference));
        return pdfDictionary;
    }

    /* access modifiers changed from: package-private */
    public void writeFont(PdfWriter pdfWriter, PdfIndirectReference pdfIndirectReference, Object[] objArr) throws DocumentException, IOException {
        IntHashtable intHashtable = objArr[0];
        PdfDictionary fontDescriptor = getFontDescriptor();
        PdfIndirectReference indirectReference = fontDescriptor != null ? pdfWriter.addToBody(fontDescriptor).getIndirectReference() : null;
        PdfDictionary cIDFont = getCIDFont(indirectReference, intHashtable);
        if (cIDFont != null) {
            indirectReference = pdfWriter.addToBody(cIDFont).getIndirectReference();
        }
        pdfWriter.addToBody((PdfObject) getFontBaseType(indirectReference), pdfIndirectReference);
    }

    private float getDescNumber(String str) {
        return (float) Integer.parseInt((String) this.fontDesc.get(str));
    }

    private float getBBox(int i) {
        StringTokenizer stringTokenizer = new StringTokenizer((String) this.fontDesc.get("FontBBox"), " []\r\n\t\f");
        String nextToken = stringTokenizer.nextToken();
        for (int i2 = 0; i2 < i; i2++) {
            nextToken = stringTokenizer.nextToken();
        }
        return (float) Integer.parseInt(nextToken);
    }

    public float getFontDescriptor(int i, float f) {
        float bBox;
        switch (i) {
            case 1:
            case 9:
                return (getDescNumber("Ascent") * f) / 1000.0f;
            case 2:
                return (getDescNumber("CapHeight") * f) / 1000.0f;
            case 3:
            case 10:
                return (getDescNumber("Descent") * f) / 1000.0f;
            case 4:
                return getDescNumber("ItalicAngle");
            case 5:
                bBox = getBBox(0);
                break;
            case 6:
                bBox = getBBox(1);
                break;
            case 7:
                bBox = getBBox(2);
                break;
            case 8:
                bBox = getBBox(3);
                break;
            case 12:
                bBox = getBBox(2) - getBBox(0);
                break;
            default:
                return 0.0f;
        }
        return (f * bBox) / 1000.0f;
    }

    public String getPostscriptFontName() {
        return this.fontName;
    }

    public String[][] getFullFontName() {
        return new String[][]{new String[]{"", "", "", this.fontName}};
    }

    public String[][] getAllNameEntries() {
        return new String[][]{new String[]{"4", "", "", "", this.fontName}};
    }

    public String[][] getFamilyFontName() {
        return getFullFontName();
    }

    static IntHashtable createMetric(String str) {
        IntHashtable intHashtable = new IntHashtable();
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        while (stringTokenizer.hasMoreTokens()) {
            intHashtable.put(Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()));
        }
        return intHashtable;
    }

    static String convertToHCIDMetrics(int[] iArr, IntHashtable intHashtable) {
        if (iArr.length == 0) {
            return null;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i >= iArr.length) {
                break;
            }
            i2 = iArr[i];
            i3 = intHashtable.get(i2);
            if (i3 != 0) {
                i++;
                break;
            }
            i++;
        }
        if (i3 == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(i2);
        char c = 0;
        while (i < iArr.length) {
            int i4 = iArr[i];
            int i5 = intHashtable.get(i4);
            if (i5 != 0) {
                if (c != 0) {
                    if (c != 1) {
                        if (c == 2 && !(i4 == i2 + 1 && i5 == i3)) {
                            sb.append(' ');
                            sb.append(i2);
                            sb.append(' ');
                            sb.append(i3);
                            sb.append(' ');
                            sb.append(i4);
                        }
                        i2 = i4;
                        i3 = i5;
                    } else {
                        int i6 = i2 + 1;
                        if (i4 == i6 && i5 == i3) {
                            sb.append(']');
                            sb.append(i2);
                        } else if (i4 == i6) {
                            sb.append(' ');
                            sb.append(i3);
                            i2 = i4;
                            i3 = i5;
                        } else {
                            sb.append(' ');
                            sb.append(i3);
                            sb.append(']');
                            sb.append(i4);
                        }
                    }
                    c = 0;
                    i2 = i4;
                    i3 = i5;
                } else {
                    int i7 = i2 + 1;
                    if (!(i4 == i7 && i5 == i3)) {
                        if (i4 == i7) {
                            sb.append('[');
                            sb.append(i3);
                            c = 1;
                        } else {
                            sb.append('[');
                            sb.append(i3);
                            sb.append(']');
                            sb.append(i4);
                        }
                        i2 = i4;
                        i3 = i5;
                    }
                }
                c = 2;
                i2 = i4;
                i3 = i5;
            }
            i++;
        }
        if (c == 0) {
            sb.append('[');
            sb.append(i3);
            sb.append("]]");
        } else if (c == 1) {
            sb.append(' ');
            sb.append(i3);
            sb.append("]]");
        } else if (c == 2) {
            sb.append(' ');
            sb.append(i2);
            sb.append(' ');
            sb.append(i3);
            sb.append(']');
        }
        return sb.toString();
    }

    static String convertToVCIDMetrics(int[] iArr, IntHashtable intHashtable, IntHashtable intHashtable2) {
        int[] iArr2 = iArr;
        IntHashtable intHashtable3 = intHashtable;
        IntHashtable intHashtable4 = intHashtable2;
        if (iArr2.length == 0) {
            return null;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i >= iArr2.length) {
                break;
            }
            i4 = iArr2[i];
            i2 = intHashtable3.get(i4);
            if (i2 != 0) {
                i++;
                break;
            }
            i3 = intHashtable4.get(i4);
            i++;
        }
        if (i2 == 0) {
            return null;
        }
        if (i3 == 0) {
            i3 = 1000;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(i4);
        char c = 0;
        while (i < iArr2.length) {
            int i5 = iArr2[i];
            int i6 = intHashtable3.get(i5);
            if (i6 != 0) {
                int i7 = intHashtable4.get(i4);
                int i8 = i7 == 0 ? 1000 : i7;
                if (c != 0) {
                    if (c == 2 && !(i5 == i4 + 1 && i6 == i2 && i8 == i3)) {
                        sb.append(' ');
                        sb.append(i4);
                        sb.append(' ');
                        sb.append(-i2);
                        sb.append(' ');
                        sb.append(i3 / 2);
                        sb.append(' ');
                        sb.append(880);
                        sb.append(' ');
                        sb.append(i5);
                        c = 0;
                    }
                } else if (i5 == i4 + 1 && i6 == i2 && i8 == i3) {
                    c = 2;
                } else {
                    sb.append(' ');
                    sb.append(i4);
                    sb.append(' ');
                    sb.append(-i2);
                    sb.append(' ');
                    sb.append(i3 / 2);
                    sb.append(' ');
                    sb.append(880);
                    sb.append(' ');
                    sb.append(i5);
                }
                i3 = i8;
                i4 = i5;
                i2 = i6;
            }
            i++;
        }
        sb.append(' ');
        sb.append(i4);
        sb.append(' ');
        sb.append(-i2);
        sb.append(' ');
        sb.append(i3 / 2);
        sb.append(' ');
        sb.append(880);
        sb.append(" ]");
        return sb.toString();
    }

    private static HashMap<String, Object> readFontProperties(String str) throws IOException {
        InputStream resourceStream = StreamUtil.getResourceStream(RESOURCE_PATH_CMAP + (str + ".properties"));
        Properties properties = new Properties();
        properties.load(resourceStream);
        resourceStream.close();
        IntHashtable createMetric = createMetric(properties.getProperty(ExifInterface.LONGITUDE_WEST));
        properties.remove(ExifInterface.LONGITUDE_WEST);
        IntHashtable createMetric2 = createMetric(properties.getProperty("W2"));
        properties.remove("W2");
        HashMap<String, Object> hashMap = new HashMap<>();
        Enumeration keys = properties.keys();
        while (keys.hasMoreElements()) {
            String str2 = (String) keys.nextElement();
            hashMap.put(str2, properties.getProperty(str2));
        }
        hashMap.put(ExifInterface.LONGITUDE_WEST, createMetric);
        hashMap.put("W2", createMetric2);
        return hashMap;
    }

    public int getUnicodeEquivalent(int i) {
        if (!this.cidDirect) {
            return i;
        }
        if (i == 32767) {
            return 10;
        }
        return this.cidUni.lookup(i);
    }

    public int getCidCode(int i) {
        if (this.cidDirect) {
            return i;
        }
        return this.uniCid.lookup(i);
    }

    public boolean charExists(int i) {
        if (!this.cidDirect && this.cidByte.lookup(this.uniCid.lookup(i)).length <= 0) {
            return false;
        }
        return true;
    }

    public void setPostscriptFontName(String str) {
        this.fontName = str;
    }

    public byte[] convertToBytes(String str) {
        int i;
        if (this.cidDirect) {
            return super.convertToBytes(str);
        }
        try {
            int i2 = 0;
            if (str.length() == 1) {
                return convertToBytes((int) str.charAt(0));
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (i2 < str.length()) {
                if (Utilities.isSurrogatePair(str, i2)) {
                    i = Utilities.convertToUtf32(str, i2);
                    i2++;
                } else {
                    i = str.charAt(i2);
                }
                byteArrayOutputStream.write(convertToBytes(i));
                i2++;
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] convertToBytes(int i) {
        if (this.cidDirect) {
            return super.convertToBytes(i);
        }
        return this.cidByte.lookup(this.uniCid.lookup(i));
    }

    public boolean isIdentity() {
        return this.cidDirect;
    }
}
