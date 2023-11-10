package com.kongzue.dialogx.util;

public class TextInfo {
    private boolean bold = false;
    private int fontColor = 1;
    private int fontSize = -1;
    private FONT_SIZE_UNIT fontSizeUnit = FONT_SIZE_UNIT.DP;
    private int gravity = -1;
    private int maxLines = -1;
    private boolean showEllipsis = false;

    public enum FONT_SIZE_UNIT {
        DP,
        PX,
        SP
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public int getFontSizeComplexUnit() {
        if (this.fontSizeUnit == null) {
            return 1;
        }
        int i = C21241.$SwitchMap$com$kongzue$dialogx$util$TextInfo$FONT_SIZE_UNIT[this.fontSizeUnit.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            return 1;
        }
        return 2;
    }

    /* renamed from: com.kongzue.dialogx.util.TextInfo$1 */
    static /* synthetic */ class C21241 {
        static final /* synthetic */ int[] $SwitchMap$com$kongzue$dialogx$util$TextInfo$FONT_SIZE_UNIT;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                com.kongzue.dialogx.util.TextInfo$FONT_SIZE_UNIT[] r0 = com.kongzue.dialogx.util.TextInfo.FONT_SIZE_UNIT.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$kongzue$dialogx$util$TextInfo$FONT_SIZE_UNIT = r0
                com.kongzue.dialogx.util.TextInfo$FONT_SIZE_UNIT r1 = com.kongzue.dialogx.util.TextInfo.FONT_SIZE_UNIT.PX     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$kongzue$dialogx$util$TextInfo$FONT_SIZE_UNIT     // Catch:{ NoSuchFieldError -> 0x001d }
                com.kongzue.dialogx.util.TextInfo$FONT_SIZE_UNIT r1 = com.kongzue.dialogx.util.TextInfo.FONT_SIZE_UNIT.SP     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.util.TextInfo.C21241.<clinit>():void");
        }
    }

    public TextInfo setFontSize(int i) {
        this.fontSize = i;
        return this;
    }

    public int getGravity() {
        return this.gravity;
    }

    public TextInfo setGravity(int i) {
        this.gravity = i;
        return this;
    }

    public int getFontColor() {
        return this.fontColor;
    }

    public TextInfo setFontColor(int i) {
        this.fontColor = i;
        return this;
    }

    public boolean isBold() {
        return this.bold;
    }

    public TextInfo setBold(boolean z) {
        this.bold = z;
        return this;
    }

    public int getMaxLines() {
        return this.maxLines;
    }

    public TextInfo setMaxLines(int i) {
        this.maxLines = i;
        return this;
    }

    public boolean isShowEllipsis() {
        return this.showEllipsis;
    }

    public TextInfo setShowEllipsis(boolean z) {
        this.showEllipsis = z;
        return this;
    }

    public FONT_SIZE_UNIT getFontSizeUnit() {
        return this.fontSizeUnit;
    }

    public TextInfo setFontSizeUnit(FONT_SIZE_UNIT font_size_unit) {
        this.fontSizeUnit = font_size_unit;
        return this;
    }

    public String toString() {
        return "TextInfo{fontSize=" + this.fontSize + ", gravity=" + this.gravity + ", fontColor=" + this.fontColor + ", bold=" + this.bold + ", maxLines=" + this.maxLines + ", showEllipsis=" + this.showEllipsis + '}';
    }
}
