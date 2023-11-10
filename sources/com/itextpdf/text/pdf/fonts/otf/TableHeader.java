package com.itextpdf.text.pdf.fonts.otf;

public class TableHeader {
    public int featureListOffset;
    public int lookupListOffset;
    public int scriptListOffset;
    public int version;

    public TableHeader(int i, int i2, int i3, int i4) {
        this.version = i;
        this.scriptListOffset = i2;
        this.featureListOffset = i3;
        this.lookupListOffset = i4;
    }
}
