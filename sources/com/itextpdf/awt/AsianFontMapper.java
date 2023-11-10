package com.itextpdf.awt;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.pdf.BaseFont;
import java.awt.Font;

public class AsianFontMapper extends DefaultFontMapper {
    public static final String ChineseSimplifiedEncoding_H = "UniGB-UCS2-H";
    public static final String ChineseSimplifiedEncoding_V = "UniGB-UCS2-V";
    public static final String ChineseSimplifiedFont = "STSong-Light";
    public static final String ChineseTraditionalEncoding_H = "UniCNS-UCS2-H";
    public static final String ChineseTraditionalEncoding_V = "UniCNS-UCS2-V";
    public static final String ChineseTraditionalFont_MHei = "MHei-Medium";
    public static final String ChineseTraditionalFont_MSung = "MSung-Light";
    public static final String JapaneseEncoding_H = "UniJIS-UCS2-H";
    public static final String JapaneseEncoding_HW_H = "UniJIS-UCS2-HW-H";
    public static final String JapaneseEncoding_HW_V = "UniJIS-UCS2-HW-V";
    public static final String JapaneseEncoding_V = "UniJIS-UCS2-V";
    public static final String JapaneseFont_Go = "HeiseiKakuGo-W5";
    public static final String JapaneseFont_Min = "HeiseiMin-W3";
    public static final String KoreanEncoding_H = "UniKS-UCS2-H";
    public static final String KoreanEncoding_V = "UniKS-UCS2-V";
    public static final String KoreanFont_GoThic = "HYGoThic-Medium";
    public static final String KoreanFont_SMyeongJo = "HYSMyeongJo-Medium";
    private final String defaultFont;
    private final String encoding;

    public AsianFontMapper(String str, String str2) {
        this.defaultFont = str;
        this.encoding = str2;
    }

    public BaseFont awtToPdf(Font font) {
        try {
            DefaultFontMapper.BaseFontParameters baseFontParameters = getBaseFontParameters(font.getFontName());
            if (baseFontParameters != null) {
                return BaseFont.createFont(baseFontParameters.fontName, baseFontParameters.encoding, baseFontParameters.embedded, baseFontParameters.cached, baseFontParameters.ttfAfm, baseFontParameters.pfb);
            }
            return BaseFont.createFont(this.defaultFont, this.encoding, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
