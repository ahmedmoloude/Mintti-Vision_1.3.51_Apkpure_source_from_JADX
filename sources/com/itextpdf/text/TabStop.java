package com.itextpdf.text;

import com.itextpdf.text.pdf.draw.DrawInterface;
import p028io.jsonwebtoken.JwtParser;

public class TabStop {
    protected Alignment alignment;
    protected char anchorChar;
    protected DrawInterface leader;
    protected float position;

    public enum Alignment {
        LEFT,
        RIGHT,
        CENTER,
        ANCHOR
    }

    public static TabStop newInstance(float f, float f2) {
        float round = ((float) Math.round(f * 1000.0f)) / 1000.0f;
        float round2 = ((float) Math.round(f2 * 1000.0f)) / 1000.0f;
        return new TabStop((round + round2) - (round % round2));
    }

    public TabStop(float f) {
        this(f, Alignment.LEFT);
    }

    public TabStop(float f, DrawInterface drawInterface) {
        this(f, drawInterface, Alignment.LEFT);
    }

    public TabStop(float f, Alignment alignment2) {
        this(f, (DrawInterface) null, alignment2);
    }

    public TabStop(float f, Alignment alignment2, char c) {
        this(f, (DrawInterface) null, alignment2, c);
    }

    public TabStop(float f, DrawInterface drawInterface, Alignment alignment2) {
        this(f, drawInterface, alignment2, JwtParser.SEPARATOR_CHAR);
    }

    public TabStop(float f, DrawInterface drawInterface, Alignment alignment2, char c) {
        this.alignment = Alignment.LEFT;
        this.anchorChar = JwtParser.SEPARATOR_CHAR;
        this.position = f;
        this.leader = drawInterface;
        this.alignment = alignment2;
        this.anchorChar = c;
    }

    public TabStop(TabStop tabStop) {
        this(tabStop.getPosition(), tabStop.getLeader(), tabStop.getAlignment(), tabStop.getAnchorChar());
    }

    public float getPosition() {
        return this.position;
    }

    public void setPosition(float f) {
        this.position = f;
    }

    public Alignment getAlignment() {
        return this.alignment;
    }

    public void setAlignment(Alignment alignment2) {
        this.alignment = alignment2;
    }

    public DrawInterface getLeader() {
        return this.leader;
    }

    public void setLeader(DrawInterface drawInterface) {
        this.leader = drawInterface;
    }

    public char getAnchorChar() {
        return this.anchorChar;
    }

    public void setAnchorChar(char c) {
        this.anchorChar = c;
    }

    public float getPosition(float f, float f2, float f3) {
        float f4;
        float f5 = this.position;
        float f6 = f2 - f;
        int i = C14971.$SwitchMap$com$itextpdf$text$TabStop$Alignment[this.alignment.ordinal()];
        if (i == 1) {
            f4 = this.position;
            if (f + f6 >= f4) {
                return f;
            }
        } else if (i == 2) {
            f6 /= 2.0f;
            f4 = this.position;
            if (f + f6 >= f4) {
                return f;
            }
        } else if (i != 3) {
            return f5;
        } else {
            if (!Float.isNaN(f3)) {
                float f7 = this.position;
                if (f3 < f7) {
                    return f7 - (f3 - f);
                }
                return f;
            }
            f4 = this.position;
            if (f + f6 >= f4) {
                return f;
            }
        }
        return f4 - f6;
    }

    /* renamed from: com.itextpdf.text.TabStop$1 */
    static /* synthetic */ class C14971 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$TabStop$Alignment;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.itextpdf.text.TabStop$Alignment[] r0 = com.itextpdf.text.TabStop.Alignment.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$TabStop$Alignment = r0
                com.itextpdf.text.TabStop$Alignment r1 = com.itextpdf.text.TabStop.Alignment.RIGHT     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$itextpdf$text$TabStop$Alignment     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.TabStop$Alignment r1 = com.itextpdf.text.TabStop.Alignment.CENTER     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$itextpdf$text$TabStop$Alignment     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.TabStop$Alignment r1 = com.itextpdf.text.TabStop.Alignment.ANCHOR     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.TabStop.C14971.<clinit>():void");
        }
    }
}
