package com.itextpdf.text.pdf.languages;

import com.itextpdf.text.pdf.Glyph;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BanglaGlyphRepositioner extends IndicGlyphRepositioner {
    private static final String[] CHARCTERS_TO_BE_SHIFTED_LEFT_BY_1 = {"ি", "ে", "ৈ"};
    private final Map<Integer, int[]> cmap31;
    private final Map<String, Glyph> glyphSubstitutionMap;

    public BanglaGlyphRepositioner(Map<Integer, int[]> map, Map<String, Glyph> map2) {
        this.cmap31 = map;
        this.glyphSubstitutionMap = map2;
    }

    public void repositionGlyphs(List<Glyph> list) {
        for (int i = 0; i < list.size(); i++) {
            Glyph glyph = list.get(i);
            if (glyph.chars.equals("ো")) {
                handleOKaarAndOUKaar(i, list, 2503, 2494);
            } else if (glyph.chars.equals("ৌ")) {
                handleOKaarAndOUKaar(i, list, 2503, 2519);
            }
        }
        super.repositionGlyphs(list);
    }

    public List<String> getCharactersToBeShiftedLeftByOnePosition() {
        return Arrays.asList(CHARCTERS_TO_BE_SHIFTED_LEFT_BY_1);
    }

    private void handleOKaarAndOUKaar(int i, List<Glyph> list, char c, char c2) {
        Glyph glyph = getGlyph(c);
        Glyph glyph2 = getGlyph(c2);
        list.set(i, glyph);
        list.add(i + 1, glyph2);
    }

    private Glyph getGlyph(char c) {
        Glyph glyph = this.glyphSubstitutionMap.get(String.valueOf(c));
        if (glyph != null) {
            return glyph;
        }
        int[] iArr = this.cmap31.get(Integer.valueOf(c));
        return new Glyph(iArr[0], iArr[1], String.valueOf(c));
    }
}
