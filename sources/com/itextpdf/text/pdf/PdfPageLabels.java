package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.factories.RomanAlphabetFactory;
import com.itextpdf.text.factories.RomanNumberFactory;
import com.itextpdf.text.html.HtmlTags;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class PdfPageLabels {
    public static final int DECIMAL_ARABIC_NUMERALS = 0;
    public static final int EMPTY = 5;
    public static final int LOWERCASE_LETTERS = 4;
    public static final int LOWERCASE_ROMAN_NUMERALS = 2;
    public static final int UPPERCASE_LETTERS = 3;
    public static final int UPPERCASE_ROMAN_NUMERALS = 1;
    static PdfName[] numberingStyle = {PdfName.f567D, PdfName.f632R, new PdfName("r"), PdfName.f545A, new PdfName((String) HtmlTags.f475A)};
    private HashMap<Integer, PdfDictionary> map = new HashMap<>();

    public PdfPageLabels() {
        addPageLabel(1, 0, (String) null, 1);
    }

    public void addPageLabel(int i, int i2, String str, int i3) {
        if (i < 1 || i3 < 1) {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("in.a.page.label.the.page.numbers.must.be.greater.or.equal.to.1", new Object[0]));
        }
        PdfDictionary pdfDictionary = new PdfDictionary();
        if (i2 >= 0 && i2 < numberingStyle.length) {
            pdfDictionary.put(PdfName.f642S, numberingStyle[i2]);
        }
        if (str != null) {
            pdfDictionary.put(PdfName.f623P, new PdfString(str, PdfObject.TEXT_UNICODE));
        }
        if (i3 != 1) {
            pdfDictionary.put(PdfName.f644ST, new PdfNumber(i3));
        }
        this.map.put(Integer.valueOf(i - 1), pdfDictionary);
    }

    public void addPageLabel(int i, int i2, String str) {
        addPageLabel(i, i2, str, 1);
    }

    public void addPageLabel(int i, int i2) {
        addPageLabel(i, i2, (String) null, 1);
    }

    public void addPageLabel(PdfPageLabelFormat pdfPageLabelFormat) {
        addPageLabel(pdfPageLabelFormat.physicalPage, pdfPageLabelFormat.numberStyle, pdfPageLabelFormat.prefix, pdfPageLabelFormat.logicalPage);
    }

    public void removePageLabel(int i) {
        if (i > 1) {
            this.map.remove(Integer.valueOf(i - 1));
        }
    }

    public PdfDictionary getDictionary(PdfWriter pdfWriter) {
        try {
            return PdfNumberTree.writeTree(this.map, pdfWriter);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    public static String[] getPageLabels(PdfReader pdfReader) {
        int i;
        int numberOfPages = pdfReader.getNumberOfPages();
        PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObjectRelease(pdfReader.getCatalog().get(PdfName.PAGELABELS));
        if (pdfDictionary == null) {
            return null;
        }
        String[] strArr = new String[numberOfPages];
        HashMap<Integer, PdfObject> readTree = PdfNumberTree.readTree(pdfDictionary);
        char c = 'D';
        String str = "";
        int i2 = 1;
        for (int i3 = 0; i3 < numberOfPages; i3++) {
            Integer valueOf = Integer.valueOf(i3);
            if (readTree.containsKey(valueOf)) {
                PdfDictionary pdfDictionary2 = (PdfDictionary) PdfReader.getPdfObjectRelease(readTree.get(valueOf));
                i = pdfDictionary2.contains(PdfName.f644ST) ? ((PdfNumber) pdfDictionary2.get(PdfName.f644ST)).intValue() : 1;
                if (pdfDictionary2.contains(PdfName.f623P)) {
                    str = ((PdfString) pdfDictionary2.get(PdfName.f623P)).toUnicodeString();
                } else {
                    str = "";
                }
                c = pdfDictionary2.contains(PdfName.f642S) ? ((PdfName) pdfDictionary2.get(PdfName.f642S)).toString().charAt(1) : Barcode128.CODE_BC_TO_A;
            }
            if (c == 'A') {
                strArr[i3] = str + RomanAlphabetFactory.getUpperCaseString(i);
            } else if (c == 'R') {
                strArr[i3] = str + RomanNumberFactory.getUpperCaseString(i);
            } else if (c == 'a') {
                strArr[i3] = str + RomanAlphabetFactory.getLowerCaseString(i);
            } else if (c == 'e') {
                strArr[i3] = str;
            } else if (c != 'r') {
                strArr[i3] = str + i;
            } else {
                strArr[i3] = str + RomanNumberFactory.getLowerCaseString(i);
            }
            i2 = i + 1;
        }
        return strArr;
    }

    public static PdfPageLabelFormat[] getPageLabelFormats(PdfReader pdfReader) {
        int i;
        PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObjectRelease(pdfReader.getCatalog().get(PdfName.PAGELABELS));
        if (pdfDictionary == null) {
            return null;
        }
        HashMap<Integer, PdfObject> readTree = PdfNumberTree.readTree(pdfDictionary);
        Integer[] numArr = (Integer[]) readTree.keySet().toArray(new Integer[readTree.size()]);
        Arrays.sort(numArr);
        PdfPageLabelFormat[] pdfPageLabelFormatArr = new PdfPageLabelFormat[readTree.size()];
        for (int i2 = 0; i2 < numArr.length; i2++) {
            Integer num = numArr[i2];
            PdfDictionary pdfDictionary2 = (PdfDictionary) PdfReader.getPdfObjectRelease(readTree.get(num));
            int intValue = pdfDictionary2.contains(PdfName.f644ST) ? ((PdfNumber) pdfDictionary2.get(PdfName.f644ST)).intValue() : 1;
            String unicodeString = pdfDictionary2.contains(PdfName.f623P) ? ((PdfString) pdfDictionary2.get(PdfName.f623P)).toUnicodeString() : "";
            if (pdfDictionary2.contains(PdfName.f642S)) {
                char charAt = ((PdfName) pdfDictionary2.get(PdfName.f642S)).toString().charAt(1);
                i = charAt != 'A' ? charAt != 'R' ? charAt != 'a' ? charAt != 'r' ? 0 : 2 : 4 : 1 : 3;
            } else {
                i = 5;
            }
            pdfPageLabelFormatArr[i2] = new PdfPageLabelFormat(num.intValue() + 1, i, unicodeString, intValue);
        }
        return pdfPageLabelFormatArr;
    }

    public static class PdfPageLabelFormat {
        public int logicalPage;
        public int numberStyle;
        public int physicalPage;
        public String prefix;

        public PdfPageLabelFormat(int i, int i2, String str, int i3) {
            this.physicalPage = i;
            this.numberStyle = i2;
            this.prefix = str;
            this.logicalPage = i3;
        }
    }
}
