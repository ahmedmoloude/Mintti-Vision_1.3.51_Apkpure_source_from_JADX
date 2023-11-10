package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.XMLUtil;
import com.itextpdf.text.xml.simpleparser.IanaEncodings;
import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

public final class SimpleBookmark implements SimpleXMLDocHandler {
    private final Stack<HashMap<String, Object>> attr = new Stack<>();
    private ArrayList<HashMap<String, Object>> topList;

    public void endDocument() {
    }

    public void startDocument() {
    }

    private SimpleBookmark() {
    }

    private static List<HashMap<String, Object>> bookmarkDepth(PdfReader pdfReader, PdfDictionary pdfDictionary, IntHashtable intHashtable, boolean z) {
        IntHashtable intHashtable2 = intHashtable;
        ArrayList arrayList = new ArrayList();
        PdfDictionary pdfDictionary2 = pdfDictionary;
        while (pdfDictionary2 != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("Title", ((PdfString) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.TITLE))).toUnicodeString());
            PdfArray pdfArray = (PdfArray) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.f557C));
            if (pdfArray != null && pdfArray.size() == 3) {
                ByteBuffer byteBuffer = new ByteBuffer();
                byteBuffer.append(pdfArray.getAsNumber(0).floatValue()).append(' ');
                byteBuffer.append(pdfArray.getAsNumber(1).floatValue()).append(' ');
                byteBuffer.append(pdfArray.getAsNumber(2).floatValue());
                hashMap.put("Color", PdfEncodings.convertToString(byteBuffer.toByteArray(), (String) null));
            }
            PdfNumber pdfNumber = (PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.f580F));
            if (pdfNumber != null) {
                int intValue = pdfNumber.intValue();
                String str = "";
                if ((intValue & 1) != 0) {
                    str = str + "italic ";
                }
                if ((intValue & 2) != 0) {
                    str = str + "bold ";
                }
                String trim = str.trim();
                if (trim.length() != 0) {
                    hashMap.put("Style", trim);
                }
            }
            PdfNumber pdfNumber2 = (PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.COUNT));
            if (pdfNumber2 != null && pdfNumber2.intValue() < 0) {
                hashMap.put("Open", PdfBoolean.FALSE);
            }
            try {
                PdfObject pdfObjectRelease = PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.DEST));
                if (pdfObjectRelease != null) {
                    mapGotoBookmark(hashMap, pdfObjectRelease, intHashtable2);
                } else {
                    PdfDictionary pdfDictionary3 = (PdfDictionary) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.f545A));
                    if (pdfDictionary3 != null) {
                        if (PdfName.GOTO.equals(PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f642S)))) {
                            PdfObject pdfObjectRelease2 = PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f567D));
                            if (pdfObjectRelease2 != null) {
                                mapGotoBookmark(hashMap, pdfObjectRelease2, intHashtable2);
                            }
                        } else if (PdfName.URI.equals(PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f642S)))) {
                            hashMap.put("Action", "URI");
                            hashMap.put("URI", ((PdfString) PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.URI))).toUnicodeString());
                        } else if (PdfName.JAVASCRIPT.equals(PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f642S)))) {
                            hashMap.put("Action", "JS");
                            hashMap.put("Code", PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f604JS)).toString());
                        } else if (PdfName.GOTOR.equals(PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f642S)))) {
                            PdfObject pdfObjectRelease3 = PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f567D));
                            if (pdfObjectRelease3 != null) {
                                if (pdfObjectRelease3.isString()) {
                                    hashMap.put("Named", pdfObjectRelease3.toString());
                                } else if (pdfObjectRelease3.isName()) {
                                    hashMap.put("NamedN", PdfName.decodeName(pdfObjectRelease3.toString()));
                                } else if (pdfObjectRelease3.isArray()) {
                                    PdfArray pdfArray2 = (PdfArray) pdfObjectRelease3;
                                    StringBuffer stringBuffer = new StringBuffer();
                                    stringBuffer.append(pdfArray2.getPdfObject(0).toString());
                                    stringBuffer.append(' ');
                                    stringBuffer.append(pdfArray2.getPdfObject(1).toString());
                                    for (int i = 2; i < pdfArray2.size(); i++) {
                                        stringBuffer.append(' ');
                                        stringBuffer.append(pdfArray2.getPdfObject(i).toString());
                                    }
                                    hashMap.put("Page", stringBuffer.toString());
                                }
                            }
                            hashMap.put("Action", "GoToR");
                            PdfObject pdfObjectRelease4 = PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f580F));
                            if (pdfObjectRelease4 != null) {
                                if (pdfObjectRelease4.isString()) {
                                    hashMap.put("File", ((PdfString) pdfObjectRelease4).toUnicodeString());
                                } else if (pdfObjectRelease4.isDictionary()) {
                                    PdfObject pdfObject = PdfReader.getPdfObject(((PdfDictionary) pdfObjectRelease4).get(PdfName.f580F));
                                    if (pdfObject.isString()) {
                                        hashMap.put("File", ((PdfString) pdfObject).toUnicodeString());
                                    }
                                }
                            }
                            PdfObject pdfObjectRelease5 = PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.NEWWINDOW));
                            if (pdfObjectRelease5 != null) {
                                hashMap.put("NewWindow", pdfObjectRelease5.toString());
                            }
                        } else if (PdfName.LAUNCH.equals(PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f642S)))) {
                            hashMap.put("Action", "Launch");
                            PdfObject pdfObjectRelease6 = PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.f580F));
                            if (pdfObjectRelease6 == null) {
                                pdfObjectRelease6 = PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.WIN));
                            }
                            if (pdfObjectRelease6 != null) {
                                if (pdfObjectRelease6.isString()) {
                                    hashMap.put("File", ((PdfString) pdfObjectRelease6).toUnicodeString());
                                } else if (pdfObjectRelease6.isDictionary()) {
                                    PdfObject pdfObjectRelease7 = PdfReader.getPdfObjectRelease(((PdfDictionary) pdfObjectRelease6).get(PdfName.f580F));
                                    if (pdfObjectRelease7.isString()) {
                                        hashMap.put("File", ((PdfString) pdfObjectRelease7).toUnicodeString());
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception unused) {
            }
            PdfDictionary pdfDictionary4 = (PdfDictionary) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.FIRST));
            PdfReader pdfReader2 = pdfReader;
            if (pdfDictionary4 != null) {
                hashMap.put("Kids", bookmarkDepth(pdfReader2, pdfDictionary4, intHashtable2, false));
            }
            arrayList.add(hashMap);
            pdfDictionary2 = !z ? (PdfDictionary) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.NEXT)) : null;
        }
        return arrayList;
    }

    private static void mapGotoBookmark(HashMap<String, Object> hashMap, PdfObject pdfObject, IntHashtable intHashtable) {
        if (pdfObject.isString()) {
            hashMap.put("Named", pdfObject.toString());
        } else if (pdfObject.isName()) {
            hashMap.put("Named", PdfName.decodeName(pdfObject.toString()));
        } else if (pdfObject.isArray()) {
            hashMap.put("Page", makeBookmarkParam((PdfArray) pdfObject, intHashtable));
        }
        hashMap.put("Action", "GoTo");
    }

    private static String makeBookmarkParam(PdfArray pdfArray, IntHashtable intHashtable) {
        StringBuffer stringBuffer = new StringBuffer();
        PdfObject pdfObject = pdfArray.getPdfObject(0);
        if (pdfObject.isNumber()) {
            stringBuffer.append(((PdfNumber) pdfObject).intValue() + 1);
        } else {
            stringBuffer.append(intHashtable.get(getNumber((PdfIndirectReference) pdfObject)));
        }
        stringBuffer.append(' ');
        stringBuffer.append(pdfArray.getPdfObject(1).toString().substring(1));
        for (int i = 2; i < pdfArray.size(); i++) {
            stringBuffer.append(' ');
            stringBuffer.append(pdfArray.getPdfObject(i).toString());
        }
        return stringBuffer.toString();
    }

    private static int getNumber(PdfIndirectReference pdfIndirectReference) {
        PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObjectRelease((PdfObject) pdfIndirectReference);
        if (pdfDictionary.contains(PdfName.TYPE) && pdfDictionary.get(PdfName.TYPE).equals(PdfName.PAGES) && pdfDictionary.contains(PdfName.KIDS)) {
            pdfIndirectReference = (PdfIndirectReference) ((PdfArray) pdfDictionary.get(PdfName.KIDS)).getPdfObject(0);
        }
        return pdfIndirectReference.getNumber();
    }

    public static List<HashMap<String, Object>> getBookmark(PdfReader pdfReader) {
        PdfObject pdfObjectRelease = PdfReader.getPdfObjectRelease(pdfReader.getCatalog().get(PdfName.OUTLINES));
        if (pdfObjectRelease == null || !pdfObjectRelease.isDictionary()) {
            return null;
        }
        return getBookmark(pdfReader, (PdfDictionary) pdfObjectRelease, false);
    }

    public static List<HashMap<String, Object>> getBookmark(PdfReader pdfReader, PdfDictionary pdfDictionary, boolean z) {
        pdfReader.getCatalog();
        if (pdfDictionary == null) {
            return null;
        }
        IntHashtable intHashtable = new IntHashtable();
        int numberOfPages = pdfReader.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            intHashtable.put(pdfReader.getPageOrigRef(i).getNumber(), i);
            pdfReader.releasePage(i);
        }
        if (z) {
            return bookmarkDepth(pdfReader, pdfDictionary, intHashtable, true);
        }
        return bookmarkDepth(pdfReader, (PdfDictionary) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.FIRST)), intHashtable, false);
    }

    public static void eliminatePages(List<HashMap<String, Object>> list, int[] iArr) {
        String str;
        int i;
        if (list != null) {
            ListIterator<HashMap<String, Object>> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                HashMap next = listIterator.next();
                boolean z = false;
                if ("GoTo".equals(next.get("Action")) && (str = (String) next.get("Page")) != null) {
                    String trim = str.trim();
                    int indexOf = trim.indexOf(32);
                    if (indexOf < 0) {
                        i = Integer.parseInt(trim);
                    } else {
                        i = Integer.parseInt(trim.substring(0, indexOf));
                    }
                    int length = iArr.length & -2;
                    int i2 = 0;
                    while (true) {
                        if (i2 < length) {
                            if (i >= iArr[i2] && i <= iArr[i2 + 1]) {
                                z = true;
                                break;
                            }
                            i2 += 2;
                        } else {
                            break;
                        }
                    }
                }
                List list2 = (List) next.get("Kids");
                if (list2 != null) {
                    eliminatePages(list2, iArr);
                    if (list2.isEmpty()) {
                        next.remove("Kids");
                        list2 = null;
                    }
                }
                if (z) {
                    if (list2 == null) {
                        listIterator.remove();
                    } else {
                        next.remove("Action");
                        next.remove("Page");
                        next.remove("Named");
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0060  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void shiftPageNumbers(java.util.List<java.util.HashMap<java.lang.String, java.lang.Object>> r10, int r11, int[] r12) {
        /*
            if (r10 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.ListIterator r10 = r10.listIterator()
        L_0x0007:
            boolean r0 = r10.hasNext()
            if (r0 == 0) goto L_0x008e
            java.lang.Object r0 = r10.next()
            java.util.HashMap r0 = (java.util.HashMap) r0
            java.lang.String r1 = "Action"
            java.lang.Object r1 = r0.get(r1)
            java.lang.String r2 = "GoTo"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x007f
            java.lang.String r1 = "Page"
            java.lang.Object r2 = r0.get(r1)
            java.lang.String r2 = (java.lang.String) r2
            if (r2 == 0) goto L_0x007f
            java.lang.String r2 = r2.trim()
            r3 = 32
            int r3 = r2.indexOf(r3)
            r4 = 0
            if (r3 >= 0) goto L_0x003d
            int r5 = java.lang.Integer.parseInt(r2)
            goto L_0x0045
        L_0x003d:
            java.lang.String r5 = r2.substring(r4, r3)
            int r5 = java.lang.Integer.parseInt(r5)
        L_0x0045:
            r6 = 1
            if (r12 != 0) goto L_0x004a
        L_0x0048:
            r4 = 1
            goto L_0x005e
        L_0x004a:
            int r7 = r12.length
            r7 = r7 & -2
            r8 = 0
        L_0x004e:
            if (r8 >= r7) goto L_0x005e
            r9 = r12[r8]
            if (r5 < r9) goto L_0x005b
            int r9 = r8 + 1
            r9 = r12[r9]
            if (r5 > r9) goto L_0x005b
            goto L_0x0048
        L_0x005b:
            int r8 = r8 + 2
            goto L_0x004e
        L_0x005e:
            if (r4 == 0) goto L_0x007c
            if (r3 >= 0) goto L_0x0068
            int r5 = r5 + r11
            java.lang.String r2 = java.lang.Integer.toString(r5)
            goto L_0x007c
        L_0x0068:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            int r5 = r5 + r11
            r4.append(r5)
            java.lang.String r2 = r2.substring(r3)
            r4.append(r2)
            java.lang.String r2 = r4.toString()
        L_0x007c:
            r0.put(r1, r2)
        L_0x007f:
            java.lang.String r1 = "Kids"
            java.lang.Object r0 = r0.get(r1)
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x0007
            shiftPageNumbers(r0, r11, r12)
            goto L_0x0007
        L_0x008e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.SimpleBookmark.shiftPageNumbers(java.util.List, int, int[]):void");
    }

    static void createOutlineAction(PdfDictionary pdfDictionary, HashMap<String, Object> hashMap, PdfWriter pdfWriter, boolean z) {
        String str;
        PdfDictionary pdfDictionary2 = pdfDictionary;
        HashMap<String, Object> hashMap2 = hashMap;
        PdfWriter pdfWriter2 = pdfWriter;
        try {
            String str2 = (String) hashMap2.get("Action");
            if ("GoTo".equals(str2)) {
                String str3 = (String) hashMap2.get("Named");
                if (str3 == null) {
                    String str4 = (String) hashMap2.get("Page");
                    if (str4 != null) {
                        PdfArray pdfArray = new PdfArray();
                        StringTokenizer stringTokenizer = new StringTokenizer(str4);
                        pdfArray.add((PdfObject) pdfWriter2.getPageReference(Integer.parseInt(stringTokenizer.nextToken())));
                        if (!stringTokenizer.hasMoreTokens()) {
                            pdfArray.add((PdfObject) PdfName.XYZ);
                            pdfArray.add(new float[]{0.0f, 10000.0f, 0.0f});
                        } else {
                            String nextToken = stringTokenizer.nextToken();
                            if (nextToken.startsWith("/")) {
                                nextToken = nextToken.substring(1);
                            }
                            pdfArray.add((PdfObject) new PdfName(nextToken));
                            for (int i = 0; i < 4 && stringTokenizer.hasMoreTokens(); i++) {
                                String nextToken2 = stringTokenizer.nextToken();
                                if (nextToken2.equals("null")) {
                                    pdfArray.add((PdfObject) PdfNull.PDFNULL);
                                } else {
                                    pdfArray.add((PdfObject) new PdfNumber(nextToken2));
                                }
                            }
                        }
                        pdfDictionary2.put(PdfName.DEST, pdfArray);
                    }
                } else if (z) {
                    pdfDictionary2.put(PdfName.DEST, new PdfName(str3));
                } else {
                    pdfDictionary2.put(PdfName.DEST, new PdfString(str3, (String) null));
                }
            } else if ("GoToR".equals(str2)) {
                PdfDictionary pdfDictionary3 = new PdfDictionary();
                String str5 = (String) hashMap2.get("Named");
                if (str5 != null) {
                    pdfDictionary3.put(PdfName.f567D, new PdfString(str5, (String) null));
                } else {
                    String str6 = (String) hashMap2.get("NamedN");
                    if (str6 != null) {
                        pdfDictionary3.put(PdfName.f567D, new PdfName(str6));
                    } else {
                        String str7 = (String) hashMap2.get("Page");
                        if (str7 != null) {
                            PdfArray pdfArray2 = new PdfArray();
                            StringTokenizer stringTokenizer2 = new StringTokenizer(str7);
                            pdfArray2.add((PdfObject) new PdfNumber(stringTokenizer2.nextToken()));
                            if (!stringTokenizer2.hasMoreTokens()) {
                                pdfArray2.add((PdfObject) PdfName.XYZ);
                                pdfArray2.add(new float[]{0.0f, 10000.0f, 0.0f});
                            } else {
                                String nextToken3 = stringTokenizer2.nextToken();
                                if (nextToken3.startsWith("/")) {
                                    nextToken3 = nextToken3.substring(1);
                                }
                                pdfArray2.add((PdfObject) new PdfName(nextToken3));
                                for (int i2 = 0; i2 < 4 && stringTokenizer2.hasMoreTokens(); i2++) {
                                    String nextToken4 = stringTokenizer2.nextToken();
                                    if (nextToken4.equals("null")) {
                                        pdfArray2.add((PdfObject) PdfNull.PDFNULL);
                                    } else {
                                        pdfArray2.add((PdfObject) new PdfNumber(nextToken4));
                                    }
                                }
                            }
                            pdfDictionary3.put(PdfName.f567D, pdfArray2);
                        }
                    }
                }
                String str8 = (String) hashMap2.get("File");
                if (pdfDictionary3.size() > 0 && str8 != null) {
                    pdfDictionary3.put(PdfName.f642S, PdfName.GOTOR);
                    pdfDictionary3.put(PdfName.f580F, new PdfString(str8));
                    String str9 = (String) hashMap2.get("NewWindow");
                    if (str9 != null) {
                        if (str9.equals(PdfBoolean.TRUE)) {
                            pdfDictionary3.put(PdfName.NEWWINDOW, PdfBoolean.PDFTRUE);
                        } else if (str9.equals(PdfBoolean.FALSE)) {
                            pdfDictionary3.put(PdfName.NEWWINDOW, PdfBoolean.PDFFALSE);
                        }
                    }
                    pdfDictionary2.put(PdfName.f545A, pdfDictionary3);
                }
            } else if ("URI".equals(str2)) {
                String str10 = (String) hashMap2.get("URI");
                if (str10 != null) {
                    PdfDictionary pdfDictionary4 = new PdfDictionary();
                    pdfDictionary4.put(PdfName.f642S, PdfName.URI);
                    pdfDictionary4.put(PdfName.URI, new PdfString(str10));
                    pdfDictionary2.put(PdfName.f545A, pdfDictionary4);
                }
            } else if ("JS".equals(str2)) {
                String str11 = (String) hashMap2.get("Code");
                if (str11 != null) {
                    pdfDictionary2.put(PdfName.f545A, PdfAction.javaScript(str11, pdfWriter2));
                }
            } else if ("Launch".equals(str2) && (str = (String) hashMap2.get("File")) != null) {
                PdfDictionary pdfDictionary5 = new PdfDictionary();
                pdfDictionary5.put(PdfName.f642S, PdfName.LAUNCH);
                pdfDictionary5.put(PdfName.f580F, new PdfString(str));
                pdfDictionary2.put(PdfName.f545A, pdfDictionary5);
            }
        } catch (Exception unused) {
        }
    }

    public static Object[] iterateOutlines(PdfWriter pdfWriter, PdfIndirectReference pdfIndirectReference, List<HashMap<String, Object>> list, boolean z) throws IOException {
        PdfWriter pdfWriter2 = pdfWriter;
        boolean z2 = z;
        int size = list.size();
        PdfIndirectReference[] pdfIndirectReferenceArr = new PdfIndirectReference[size];
        char c = 0;
        for (int i = 0; i < size; i++) {
            pdfIndirectReferenceArr[i] = pdfWriter.getPdfIndirectReference();
        }
        ListIterator<HashMap<String, Object>> listIterator = list.listIterator();
        int i2 = 0;
        int i3 = 0;
        while (listIterator.hasNext()) {
            HashMap next = listIterator.next();
            Object[] objArr = null;
            List list2 = (List) next.get("Kids");
            if (list2 != null && !list2.isEmpty()) {
                objArr = iterateOutlines(pdfWriter2, pdfIndirectReferenceArr[i3], list2, z2);
            }
            PdfDictionary pdfDictionary = new PdfDictionary();
            i2++;
            if (objArr != null) {
                pdfDictionary.put(PdfName.FIRST, (PdfIndirectReference) objArr[c]);
                pdfDictionary.put(PdfName.LAST, (PdfIndirectReference) objArr[1]);
                int intValue = ((Integer) objArr[2]).intValue();
                if (PdfBoolean.FALSE.equals(next.get("Open"))) {
                    pdfDictionary.put(PdfName.COUNT, new PdfNumber(-intValue));
                } else {
                    pdfDictionary.put(PdfName.COUNT, new PdfNumber(intValue));
                    i2 += intValue;
                }
            }
            pdfDictionary.put(PdfName.PARENT, pdfIndirectReference);
            if (i3 > 0) {
                pdfDictionary.put(PdfName.PREV, pdfIndirectReferenceArr[i3 - 1]);
            }
            if (i3 < size - 1) {
                pdfDictionary.put(PdfName.NEXT, pdfIndirectReferenceArr[i3 + 1]);
            }
            pdfDictionary.put(PdfName.TITLE, new PdfString((String) next.get("Title"), PdfObject.TEXT_UNICODE));
            String str = (String) next.get("Color");
            if (str != null) {
                try {
                    PdfArray pdfArray = new PdfArray();
                    StringTokenizer stringTokenizer = new StringTokenizer(str);
                    for (int i4 = 0; i4 < 3; i4++) {
                        float parseFloat = Float.parseFloat(stringTokenizer.nextToken());
                        if (parseFloat < 0.0f) {
                            parseFloat = 0.0f;
                        }
                        if (parseFloat > 1.0f) {
                            parseFloat = 1.0f;
                        }
                        pdfArray.add((PdfObject) new PdfNumber(parseFloat));
                    }
                    pdfDictionary.put(PdfName.f557C, pdfArray);
                } catch (Exception unused) {
                }
            }
            String str2 = (String) next.get("Style");
            if (str2 != null) {
                String lowerCase = str2.toLowerCase();
                int i5 = lowerCase.indexOf(HtmlTags.ITALIC) >= 0 ? 1 : 0;
                if (lowerCase.indexOf(HtmlTags.BOLD) >= 0) {
                    i5 |= 2;
                }
                if (i5 != 0) {
                    pdfDictionary.put(PdfName.f580F, new PdfNumber(i5));
                }
            }
            createOutlineAction(pdfDictionary, next, pdfWriter2, z2);
            pdfWriter2.addToBody((PdfObject) pdfDictionary, pdfIndirectReferenceArr[i3]);
            i3++;
            c = 0;
        }
        return new Object[]{pdfIndirectReferenceArr[0], pdfIndirectReferenceArr[size - 1], Integer.valueOf(i2)};
    }

    public static void exportToXMLNode(List<HashMap<String, Object>> list, Writer writer, int i, boolean z) throws IOException {
        String str;
        if (i != -1) {
            str = "";
            for (int i2 = 0; i2 < i; i2++) {
                str = str + "  ";
            }
        } else {
            str = "";
        }
        for (HashMap<String, Object> entrySet : list) {
            writer.write(str);
            writer.write("<Title ");
            String str2 = null;
            List list2 = null;
            for (Map.Entry entry : entrySet.entrySet()) {
                String str3 = (String) entry.getKey();
                if (str3.equals("Title")) {
                    str2 = (String) entry.getValue();
                } else if (str3.equals("Kids")) {
                    list2 = (List) entry.getValue();
                } else {
                    writer.write(str3);
                    writer.write("=\"");
                    String str4 = (String) entry.getValue();
                    if (str3.equals("Named") || str3.equals("NamedN")) {
                        str4 = SimpleNamedDestination.escapeBinaryString(str4);
                    }
                    writer.write(XMLUtil.escapeXML(str4, z));
                    writer.write("\" ");
                }
            }
            writer.write(">");
            if (str2 == null) {
                str2 = "";
            }
            writer.write(XMLUtil.escapeXML(str2, z));
            if (list2 != null) {
                writer.write("\n");
                exportToXMLNode(list2, writer, i == -1 ? i : i + 1, z);
                writer.write(str);
            }
            writer.write("</Title>\n");
        }
    }

    public static void exportToXML(List<HashMap<String, Object>> list, OutputStream outputStream, String str, boolean z) throws IOException {
        exportToXML(list, (Writer) new BufferedWriter(new OutputStreamWriter(outputStream, IanaEncodings.getJavaEncoding(str))), str, z);
    }

    public static void exportToXML(List<HashMap<String, Object>> list, Writer writer, String str, boolean z) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"");
        writer.write(XMLUtil.escapeXML(str, z));
        writer.write("\"?>\n<Bookmark>\n");
        exportToXMLNode(list, writer, 1, z);
        writer.write("</Bookmark>\n");
        writer.flush();
    }

    public static List<HashMap<String, Object>> importFromXML(InputStream inputStream) throws IOException {
        SimpleBookmark simpleBookmark = new SimpleBookmark();
        SimpleXMLParser.parse((SimpleXMLDocHandler) simpleBookmark, inputStream);
        return simpleBookmark.topList;
    }

    public static List<HashMap<String, Object>> importFromXML(Reader reader) throws IOException {
        SimpleBookmark simpleBookmark = new SimpleBookmark();
        SimpleXMLParser.parse((SimpleXMLDocHandler) simpleBookmark, reader);
        return simpleBookmark.topList;
    }

    public void endElement(String str) {
        if (str.equals("Bookmark")) {
            if (!this.attr.isEmpty()) {
                throw new RuntimeException(MessageLocalization.getComposedMessage("bookmark.end.tag.out.of.place", new Object[0]));
            }
        } else if (str.equals("Title")) {
            HashMap pop = this.attr.pop();
            pop.put("Title", ((String) pop.get("Title")).trim());
            String str2 = (String) pop.get("Named");
            if (str2 != null) {
                pop.put("Named", SimpleNamedDestination.unEscapeBinaryString(str2));
            }
            String str3 = (String) pop.get("NamedN");
            if (str3 != null) {
                pop.put("NamedN", SimpleNamedDestination.unEscapeBinaryString(str3));
            }
            if (this.attr.isEmpty()) {
                this.topList.add(pop);
                return;
            }
            HashMap peek = this.attr.peek();
            List list = (List) peek.get("Kids");
            if (list == null) {
                list = new ArrayList();
                peek.put("Kids", list);
            }
            list.add(pop);
        } else {
            throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.end.tag.1", str));
        }
    }

    public void startElement(String str, Map<String, String> map) {
        if (this.topList == null) {
            if (str.equals("Bookmark")) {
                this.topList = new ArrayList<>();
            } else {
                throw new RuntimeException(MessageLocalization.getComposedMessage("root.element.is.not.bookmark.1", str));
            }
        } else if (str.equals("Title")) {
            HashMap hashMap = new HashMap(map);
            hashMap.put("Title", "");
            hashMap.remove("Kids");
            this.attr.push(hashMap);
        } else {
            throw new RuntimeException(MessageLocalization.getComposedMessage("tag.1.not.allowed", str));
        }
    }

    public void text(String str) {
        if (!this.attr.isEmpty()) {
            HashMap peek = this.attr.peek();
            peek.put("Title", ((String) peek.get("Title")) + str);
        }
    }
}
