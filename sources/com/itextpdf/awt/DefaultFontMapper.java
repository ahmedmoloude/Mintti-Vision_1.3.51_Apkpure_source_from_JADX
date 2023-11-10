package com.itextpdf.awt;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.pdf.BaseFont;
import java.awt.Font;
import java.io.File;
import java.util.HashMap;

public class DefaultFontMapper implements FontMapper {
    private HashMap<String, String> aliases = new HashMap<>();
    private HashMap<String, BaseFontParameters> mapper = new HashMap<>();

    public static class BaseFontParameters {
        public boolean cached = true;
        public boolean embedded = true;
        public String encoding = "Cp1252";
        public String fontName;
        public byte[] pfb;
        public byte[] ttfAfm;

        public BaseFontParameters(String str) {
            this.fontName = str;
        }
    }

    public BaseFont awtToPdf(Font font) {
        try {
            BaseFontParameters baseFontParameters = getBaseFontParameters(font.getFontName());
            if (baseFontParameters != null) {
                return BaseFont.createFont(baseFontParameters.fontName, baseFontParameters.encoding, baseFontParameters.embedded, baseFontParameters.cached, baseFontParameters.ttfAfm, baseFontParameters.pfb);
            }
            String name = font.getName();
            String str = "Courier";
            if (!name.equalsIgnoreCase("DialogInput")) {
                if (!name.equalsIgnoreCase("Monospaced")) {
                    if (!name.equalsIgnoreCase(str)) {
                        if (!name.equalsIgnoreCase("Serif")) {
                            if (!name.equalsIgnoreCase("TimesRoman")) {
                                str = font.isItalic() ? font.isBold() ? "Helvetica-BoldOblique" : "Helvetica-Oblique" : font.isBold() ? "Helvetica-Bold" : "Helvetica";
                                return BaseFont.createFont(str, "Cp1252", false);
                            }
                        }
                        str = font.isItalic() ? font.isBold() ? "Times-BoldItalic" : "Times-Italic" : font.isBold() ? "Times-Bold" : "Times-Roman";
                        return BaseFont.createFont(str, "Cp1252", false);
                    }
                }
            }
            if (font.isItalic()) {
                str = font.isBold() ? "Courier-BoldOblique" : "Courier-Oblique";
            } else if (font.isBold()) {
                str = "Courier-Bold";
            }
            return BaseFont.createFont(str, "Cp1252", false);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public Font pdfToAwt(BaseFont baseFont, int i) {
        String[][] fullFontName = baseFont.getFullFontName();
        if (fullFontName.length == 1) {
            return new Font(fullFontName[0][3], 0, i);
        }
        String str = null;
        String str2 = null;
        int i2 = 0;
        while (true) {
            if (i2 >= fullFontName.length) {
                break;
            }
            String[] strArr = fullFontName[i2];
            if (strArr[0].equals("1") && strArr[1].equals("0")) {
                str2 = strArr[3];
            } else if (strArr[2].equals("1033")) {
                str = strArr[3];
                break;
            }
            i2++;
        }
        if (str != null) {
            str2 = str;
        }
        if (str2 == null) {
            str2 = fullFontName[0][3];
        }
        return new Font(str2, 0, i);
    }

    public void putName(String str, BaseFontParameters baseFontParameters) {
        this.mapper.put(str, baseFontParameters);
    }

    public void putAlias(String str, String str2) {
        this.aliases.put(str, str2);
    }

    public BaseFontParameters getBaseFontParameters(String str) {
        String str2 = this.aliases.get(str);
        if (str2 == null) {
            return this.mapper.get(str);
        }
        BaseFontParameters baseFontParameters = this.mapper.get(str2);
        return baseFontParameters == null ? this.mapper.get(str) : baseFontParameters;
    }

    public void insertNames(Object[] objArr, String str) {
        String str2;
        String[][] strArr = objArr[2];
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                str2 = null;
                break;
            }
            String[] strArr2 = strArr[i];
            if (strArr2[2].equals("1033")) {
                str2 = strArr2[3];
                break;
            }
            i++;
        }
        if (str2 == null) {
            str2 = strArr[0][3];
        }
        this.mapper.put(str2, new BaseFontParameters(str));
        for (String[] strArr3 : strArr) {
            this.aliases.put(strArr3[3], str2);
        }
        this.aliases.put(objArr[0], str2);
    }

    public int insertFile(File file) {
        String lowerCase = file.getPath().toLowerCase();
        try {
            if (!lowerCase.endsWith(".ttf")) {
                if (!lowerCase.endsWith(".otf")) {
                    if (!lowerCase.endsWith(".afm")) {
                        if (lowerCase.endsWith(".ttc")) {
                            String[] enumerateTTCNames = BaseFont.enumerateTTCNames(file.getPath());
                            for (int i = 0; i < enumerateTTCNames.length; i++) {
                                String str = file.getPath() + "," + i;
                                insertNames(BaseFont.getAllFontNames(str, "Cp1252", (byte[]) null), str);
                            }
                            return 1;
                        }
                        return 0;
                    }
                }
            }
            insertNames(BaseFont.getAllFontNames(file.getPath(), "Cp1252", (byte[]) null), file.getPath());
            return 1;
        } catch (Exception unused) {
        }
    }

    public int insertDirectory(String str) {
        File[] listFiles;
        File file = new File(str);
        if (!file.exists() || !file.isDirectory() || (listFiles = file.listFiles()) == null) {
            return 0;
        }
        int i = 0;
        for (File insertFile : listFiles) {
            i += insertFile(insertFile);
        }
        return i;
    }

    public HashMap<String, BaseFontParameters> getMapper() {
        return this.mapper;
    }

    public HashMap<String, String> getAliases() {
        return this.aliases;
    }
}
