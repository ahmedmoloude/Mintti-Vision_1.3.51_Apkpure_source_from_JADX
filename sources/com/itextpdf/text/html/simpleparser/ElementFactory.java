package com.itextpdf.text.html.simpleparser;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocListener;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.html.HtmlUtilities;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.HyphenationEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Deprecated
public class ElementFactory {
    private FontProvider provider = FontFactory.getFontImp();

    public void setFontProvider(FontProvider fontProvider) {
        this.provider = fontProvider;
    }

    public FontProvider getFontProvider() {
        return this.provider;
    }

    public Font getFont(ChainedProperties chainedProperties) {
        String str;
        String property = chainedProperties.getProperty(HtmlTags.FACE);
        if (property == null || property.trim().length() == 0) {
            property = chainedProperties.getProperty(HtmlTags.FONTFAMILY);
        }
        int i = 0;
        if (str != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
            while (stringTokenizer.hasMoreTokens()) {
                str = stringTokenizer.nextToken().trim();
                if (str.startsWith("\"")) {
                    str = str.substring(1);
                }
                if (str.endsWith("\"")) {
                    str = str.substring(0, str.length() - 1);
                }
                if (this.provider.isRegistered(str)) {
                    break;
                }
            }
        }
        String str2 = str;
        String property2 = chainedProperties.getProperty(HtmlTags.ENCODING);
        if (property2 == null) {
            property2 = "Cp1252";
        }
        String str3 = property2;
        String property3 = chainedProperties.getProperty(HtmlTags.SIZE);
        float parseFloat = property3 != null ? Float.parseFloat(property3) : 12.0f;
        String property4 = chainedProperties.getProperty(HtmlTags.TEXTDECORATION);
        if (!(property4 == null || property4.trim().length() == 0)) {
            if (HtmlTags.UNDERLINE.equals(property4)) {
                i = 4;
            } else if (HtmlTags.LINETHROUGH.equals(property4)) {
                i = 8;
            }
        }
        if (chainedProperties.hasProperty(HtmlTags.f486I)) {
            i |= 2;
        }
        if (chainedProperties.hasProperty(HtmlTags.f476B)) {
            i |= 1;
        }
        if (chainedProperties.hasProperty(HtmlTags.f494U)) {
            i |= 4;
        }
        return this.provider.getFont(str2, str3, true, parseFloat, chainedProperties.hasProperty(HtmlTags.f490S) ? i | 8 : i, HtmlUtilities.decodeColor(chainedProperties.getProperty("color")));
    }

    public Chunk createChunk(String str, ChainedProperties chainedProperties) {
        Font font = getFont(chainedProperties);
        Chunk chunk = new Chunk(str, font);
        if (chainedProperties.hasProperty("sub")) {
            chunk.setTextRise((-font.getSize()) / 2.0f);
        } else if (chainedProperties.hasProperty(HtmlTags.SUP)) {
            chunk.setTextRise(font.getSize() / 2.0f);
        }
        chunk.setHyphenation(getHyphenation(chainedProperties));
        return chunk;
    }

    public Paragraph createParagraph(ChainedProperties chainedProperties) {
        Paragraph paragraph = new Paragraph();
        updateElement(paragraph, chainedProperties);
        return paragraph;
    }

    public ListItem createListItem(ChainedProperties chainedProperties) {
        ListItem listItem = new ListItem();
        updateElement(listItem, chainedProperties);
        return listItem;
    }

    /* access modifiers changed from: protected */
    public void updateElement(Paragraph paragraph, ChainedProperties chainedProperties) {
        paragraph.setAlignment(HtmlUtilities.alignmentValue(chainedProperties.getProperty(HtmlTags.ALIGN)));
        paragraph.setHyphenation(getHyphenation(chainedProperties));
        setParagraphLeading(paragraph, chainedProperties.getProperty(HtmlTags.LEADING));
        String property = chainedProperties.getProperty(HtmlTags.AFTER);
        if (property != null) {
            try {
                paragraph.setSpacingBefore(Float.parseFloat(property));
            } catch (Exception unused) {
            }
        }
        String property2 = chainedProperties.getProperty(HtmlTags.AFTER);
        if (property2 != null) {
            try {
                paragraph.setSpacingAfter(Float.parseFloat(property2));
            } catch (Exception unused2) {
            }
        }
        String property3 = chainedProperties.getProperty(HtmlTags.EXTRAPARASPACE);
        if (property3 != null) {
            try {
                paragraph.setExtraParagraphSpace(Float.parseFloat(property3));
            } catch (Exception unused3) {
            }
        }
        String property4 = chainedProperties.getProperty(HtmlTags.INDENT);
        if (property4 != null) {
            try {
                paragraph.setIndentationLeft(Float.parseFloat(property4));
            } catch (Exception unused4) {
            }
        }
    }

    protected static void setParagraphLeading(Paragraph paragraph, String str) {
        if (str == null) {
            paragraph.setLeading(0.0f, 1.5f);
            return;
        }
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(str, " ,");
            float parseFloat = Float.parseFloat(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                paragraph.setLeading(parseFloat, 0.0f);
            } else {
                paragraph.setLeading(parseFloat, Float.parseFloat(stringTokenizer.nextToken()));
            }
        } catch (Exception unused) {
            paragraph.setLeading(0.0f, 1.5f);
        }
    }

    public HyphenationEvent getHyphenation(ChainedProperties chainedProperties) {
        int i;
        String property = chainedProperties.getProperty(HtmlTags.HYPHENATION);
        if (property == null || property.length() == 0) {
            return null;
        }
        int indexOf = property.indexOf(95);
        int i2 = 2;
        if (indexOf == -1) {
            return new HyphenationAuto(property, (String) null, 2, 2);
        }
        String substring = property.substring(0, indexOf);
        String substring2 = property.substring(indexOf + 1);
        int indexOf2 = substring2.indexOf(44);
        if (indexOf2 == -1) {
            return new HyphenationAuto(substring, substring2, 2, 2);
        }
        String substring3 = substring2.substring(indexOf2 + 1);
        String substring4 = substring2.substring(0, indexOf2);
        int indexOf3 = substring3.indexOf(44);
        if (indexOf3 == -1) {
            i = Integer.parseInt(substring3);
        } else {
            i = Integer.parseInt(substring3.substring(0, indexOf3));
            i2 = Integer.parseInt(substring3.substring(indexOf3 + 1));
        }
        return new HyphenationAuto(substring, substring4, i, i2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.draw.LineSeparator createLineSeparator(java.util.Map<java.lang.String, java.lang.String> r10, float r11) {
        /*
            r9 = this;
            java.lang.String r0 = "size"
            java.lang.Object r0 = r10.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            r1 = 0
            r2 = 1094713344(0x41400000, float:12.0)
            if (r0 == 0) goto L_0x0018
            float r0 = com.itextpdf.text.html.HtmlUtilities.parseLength(r0, r2)
            int r3 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r3 <= 0) goto L_0x0018
            r4 = r0
            goto L_0x001c
        L_0x0018:
            r0 = 1065353216(0x3f800000, float:1.0)
            r4 = 1065353216(0x3f800000, float:1.0)
        L_0x001c:
            java.lang.String r0 = "width"
            java.lang.Object r0 = r10.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            r3 = 1120403456(0x42c80000, float:100.0)
            if (r0 == 0) goto L_0x003f
            float r2 = com.itextpdf.text.html.HtmlUtilities.parseLength(r0, r2)
            int r1 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            r2 = 1120403456(0x42c80000, float:100.0)
        L_0x0034:
            java.lang.String r1 = "%"
            boolean r0 = r0.endsWith(r1)
            if (r0 != 0) goto L_0x003d
            goto L_0x003f
        L_0x003d:
            r5 = r2
            goto L_0x0041
        L_0x003f:
            r5 = 1120403456(0x42c80000, float:100.0)
        L_0x0041:
            r6 = 0
            java.lang.String r0 = "align"
            java.lang.Object r10 = r10.get(r0)
            java.lang.String r10 = (java.lang.String) r10
            int r7 = com.itextpdf.text.html.HtmlUtilities.alignmentValue(r10)
            com.itextpdf.text.pdf.draw.LineSeparator r10 = new com.itextpdf.text.pdf.draw.LineSeparator
            r3 = r10
            r8 = r11
            r3.<init>(r4, r5, r6, r7, r8)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.html.simpleparser.ElementFactory.createLineSeparator(java.util.Map, float):com.itextpdf.text.pdf.draw.LineSeparator");
    }

    public Image createImage(String str, Map<String, String> map, ChainedProperties chainedProperties, DocListener docListener, ImageProvider imageProvider, HashMap<String, Image> hashMap, String str2) throws DocumentException, IOException {
        Image image;
        Image image2 = imageProvider != null ? imageProvider.getImage(str, map, chainedProperties, docListener) : null;
        if (!(image2 != null || hashMap == null || (image = hashMap.get(str)) == null)) {
            image2 = Image.getInstance(image);
        }
        if (image2 != null) {
            return image2;
        }
        if (!str.startsWith("http") && str2 != null) {
            str = str2 + str;
        } else if (image2 == null && !str.startsWith("http")) {
            String property = chainedProperties.getProperty(HtmlTags.IMAGEPATH);
            if (property == null) {
                property = "";
            }
            str = new File(property, str).getPath();
        }
        Image instance = Image.getInstance(str);
        if (instance == null) {
            return null;
        }
        float f = 12.0f;
        float parseLength = HtmlUtilities.parseLength(chainedProperties.getProperty(HtmlTags.SIZE), 12.0f);
        if (parseLength > 0.0f) {
            f = parseLength;
        }
        float parseLength2 = HtmlUtilities.parseLength(map.get(HtmlTags.WIDTH), f);
        float parseLength3 = HtmlUtilities.parseLength(map.get(HtmlTags.HEIGHT), f);
        int i = (parseLength2 > 0.0f ? 1 : (parseLength2 == 0.0f ? 0 : -1));
        if (i > 0 && parseLength3 > 0.0f) {
            instance.scaleAbsolute(parseLength2, parseLength3);
        } else if (i > 0) {
            instance.scaleAbsolute(parseLength2, (instance.getHeight() * parseLength2) / instance.getWidth());
        } else if (parseLength3 > 0.0f) {
            instance.scaleAbsolute((instance.getWidth() * parseLength3) / instance.getHeight(), parseLength3);
        }
        String property2 = chainedProperties.getProperty(HtmlTags.BEFORE);
        if (property2 != null) {
            instance.setSpacingBefore(Float.parseFloat(property2));
        }
        String property3 = chainedProperties.getProperty(HtmlTags.AFTER);
        if (property3 != null) {
            instance.setSpacingAfter(Float.parseFloat(property3));
        }
        instance.setWidthPercentage(0.0f);
        return instance;
    }

    public List createList(String str, ChainedProperties chainedProperties) {
        List list;
        if (HtmlTags.f495UL.equalsIgnoreCase(str)) {
            list = new List(false);
            list.setListSymbol("• ");
        } else {
            list = new List(true);
        }
        try {
            list.setIndentationLeft(new Float(chainedProperties.getProperty(HtmlTags.INDENT)).floatValue());
        } catch (Exception unused) {
            list.setAutoindent(true);
        }
        return list;
    }
}
