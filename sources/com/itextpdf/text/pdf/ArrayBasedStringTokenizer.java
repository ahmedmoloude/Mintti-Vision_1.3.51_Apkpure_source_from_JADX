package com.itextpdf.text.pdf;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayBasedStringTokenizer {
    private final Pattern regex;

    public ArrayBasedStringTokenizer(String[] strArr) {
        this.regex = Pattern.compile(getRegexFromTokens(strArr));
    }

    public String[] tokenize(String str) {
        ArrayList arrayList = new ArrayList();
        Matcher matcher = this.regex.matcher(str);
        int i = 0;
        while (matcher.find()) {
            String substring = str.substring(i, matcher.start());
            if (substring.length() > 0) {
                arrayList.add(substring);
            }
            arrayList.add(matcher.group());
            i = matcher.end();
        }
        String substring2 = str.substring(i, str.length());
        if (substring2.length() > 0) {
            arrayList.add(substring2);
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private String getRegexFromTokens(String[] strArr) {
        StringBuilder sb = new StringBuilder(100);
        for (String append : strArr) {
            sb.append("(");
            sb.append(append);
            sb.append(")|");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
