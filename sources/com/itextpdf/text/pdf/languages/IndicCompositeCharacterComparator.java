package com.itextpdf.text.pdf.languages;

import java.util.Comparator;

public class IndicCompositeCharacterComparator implements Comparator<String> {
    public int compare(String str, String str2) {
        if (str2.length() > str.length()) {
            return 1;
        }
        if (str.length() > str2.length()) {
            return -1;
        }
        return str.compareTo(str2);
    }
}
