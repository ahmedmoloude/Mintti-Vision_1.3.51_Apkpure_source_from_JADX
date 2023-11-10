package com.itextpdf.text.html.simpleparser;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.html.HtmlUtilities;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Deprecated
public class StyleSheet {
    protected Map<String, Map<String, String>> classMap = new HashMap();
    protected Map<String, Map<String, String>> tagMap = new HashMap();

    public void loadTagStyle(String str, Map<String, String> map) {
        this.tagMap.put(str.toLowerCase(), map);
    }

    public void loadTagStyle(String str, String str2, String str3) {
        String lowerCase = str.toLowerCase();
        Map map = this.tagMap.get(lowerCase);
        if (map == null) {
            map = new HashMap();
            this.tagMap.put(lowerCase, map);
        }
        map.put(str2, str3);
    }

    public void loadStyle(String str, HashMap<String, String> hashMap) {
        this.classMap.put(str.toLowerCase(), hashMap);
    }

    public void loadStyle(String str, String str2, String str3) {
        String lowerCase = str.toLowerCase();
        Map map = this.classMap.get(lowerCase);
        if (map == null) {
            map = new HashMap();
            this.classMap.put(lowerCase, map);
        }
        map.put(str2, str3);
    }

    public void applyStyle(String str, Map<String, String> map) {
        Map map2;
        Map map3 = this.tagMap.get(str.toLowerCase());
        if (map3 != null) {
            HashMap hashMap = new HashMap(map3);
            hashMap.putAll(map);
            map.putAll(hashMap);
        }
        String str2 = map.get("class");
        if (str2 != null && (map2 = this.classMap.get(str2.toLowerCase())) != null) {
            map.remove("class");
            HashMap hashMap2 = new HashMap(map2);
            hashMap2.putAll(map);
            map.putAll(hashMap2);
        }
    }

    public static void resolveStyleAttribute(Map<String, String> map, ChainedProperties chainedProperties) {
        String str = map.get(HtmlTags.STYLE);
        if (str != null) {
            Properties parseAttributes = HtmlUtilities.parseAttributes(str);
            for (String str2 : parseAttributes.keySet()) {
                if (str2.equals(HtmlTags.FONTFAMILY)) {
                    map.put(HtmlTags.FACE, parseAttributes.getProperty(str2));
                } else {
                    float f = 12.0f;
                    if (str2.equals(HtmlTags.FONTSIZE)) {
                        float parseLength = HtmlUtilities.parseLength(chainedProperties.getProperty(HtmlTags.SIZE), 12.0f);
                        if (parseLength > 0.0f) {
                            f = parseLength;
                        }
                        map.put(HtmlTags.SIZE, Float.toString(HtmlUtilities.parseLength(parseAttributes.getProperty(str2), f)) + "pt");
                    } else if (str2.equals(HtmlTags.FONTSTYLE)) {
                        String lowerCase = parseAttributes.getProperty(str2).trim().toLowerCase();
                        if (lowerCase.equals(HtmlTags.ITALIC) || lowerCase.equals(HtmlTags.OBLIQUE)) {
                            map.put(HtmlTags.f486I, (Object) null);
                        }
                    } else if (str2.equals(HtmlTags.FONTWEIGHT)) {
                        String lowerCase2 = parseAttributes.getProperty(str2).trim().toLowerCase();
                        if (lowerCase2.equals(HtmlTags.BOLD) || lowerCase2.equals("700") || lowerCase2.equals("800") || lowerCase2.equals("900")) {
                            map.put(HtmlTags.f476B, (Object) null);
                        }
                    } else if (str2.equals(HtmlTags.TEXTDECORATION)) {
                        if (parseAttributes.getProperty(str2).trim().toLowerCase().equals(HtmlTags.UNDERLINE)) {
                            map.put(HtmlTags.f494U, (Object) null);
                        }
                    } else if (str2.equals("color")) {
                        BaseColor decodeColor = HtmlUtilities.decodeColor(parseAttributes.getProperty(str2));
                        if (decodeColor != null) {
                            String str3 = "000000" + Integer.toHexString(decodeColor.getRGB());
                            map.put("color", "#" + str3.substring(str3.length() - 6));
                        }
                    } else if (str2.equals(HtmlTags.LINEHEIGHT)) {
                        String trim = parseAttributes.getProperty(str2).trim();
                        float parseLength2 = HtmlUtilities.parseLength(chainedProperties.getProperty(HtmlTags.SIZE), 12.0f);
                        if (parseLength2 > 0.0f) {
                            f = parseLength2;
                        }
                        float parseLength3 = HtmlUtilities.parseLength(parseAttributes.getProperty(str2), f);
                        if (trim.endsWith("%")) {
                            map.put(HtmlTags.LEADING, "0," + (parseLength3 / 100.0f));
                            return;
                        } else if (HtmlTags.NORMAL.equalsIgnoreCase(trim)) {
                            map.put(HtmlTags.LEADING, "0,1.5");
                            return;
                        } else {
                            map.put(HtmlTags.LEADING, parseLength3 + ",0");
                        }
                    } else if (str2.equals(HtmlTags.TEXTALIGN)) {
                        map.put(HtmlTags.ALIGN, parseAttributes.getProperty(str2).trim().toLowerCase());
                    } else if (str2.equals(HtmlTags.PADDINGLEFT)) {
                        map.put(HtmlTags.INDENT, Float.toString(HtmlUtilities.parseLength(parseAttributes.getProperty(str2).trim().toLowerCase())));
                    }
                }
            }
        }
    }
}
