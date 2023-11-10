package com.itextpdf.text.pdf;

public class Glyph {
    public final String chars;
    public final int code;
    public final int width;

    public Glyph(int i, int i2, String str) {
        this.code = i;
        this.width = i2;
        this.chars = str;
    }

    public int hashCode() {
        String str = this.chars;
        return (((((str == null ? 0 : str.hashCode()) + 31) * 31) + this.code) * 31) + this.width;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Glyph glyph = (Glyph) obj;
        String str = this.chars;
        if (str == null) {
            if (glyph.chars != null) {
                return false;
            }
        } else if (!str.equals(glyph.chars)) {
            return false;
        }
        return this.code == glyph.code && this.width == glyph.width;
    }

    public String toString() {
        return Glyph.class.getSimpleName() + " [id=" + this.code + ", width=" + this.width + ", chars=" + this.chars + "]";
    }
}
