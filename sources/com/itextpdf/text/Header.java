package com.itextpdf.text;

public class Header extends Meta {
    private StringBuffer name;

    public Header(String str, String str2) {
        super(0, str2);
        this.name = new StringBuffer(str);
    }

    public String getName() {
        return this.name.toString();
    }
}
