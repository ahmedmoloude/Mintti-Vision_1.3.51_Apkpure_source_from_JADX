package com.itextpdf.text.xml.simpleparser.handler;

import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.simpleparser.NewLineHandler;
import java.util.HashSet;
import java.util.Set;

public class HTMLNewLineHandler implements NewLineHandler {
    private final Set<String> newLineTags;

    public HTMLNewLineHandler() {
        HashSet hashSet = new HashSet();
        this.newLineTags = hashSet;
        hashSet.add(HtmlTags.f489P);
        hashSet.add(HtmlTags.BLOCKQUOTE);
        hashSet.add(HtmlTags.f477BR);
    }

    public boolean isNewLineTag(String str) {
        return this.newLineTags.contains(str);
    }
}
