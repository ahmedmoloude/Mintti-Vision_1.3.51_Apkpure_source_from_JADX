package com.itextpdf.text.xml.simpleparser;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.xml.xmp.XmpWriter;
import java.util.HashMap;
import java.util.Map;

public class IanaEncodings {
    private static final Map<String, String> MAP;

    static {
        HashMap hashMap = new HashMap();
        MAP = hashMap;
        hashMap.put("BIG5", "Big5");
        hashMap.put("CSBIG5", "Big5");
        hashMap.put("CP037", "CP037");
        hashMap.put("IBM037", "CP037");
        hashMap.put("CSIBM037", "CP037");
        hashMap.put("EBCDIC-CP-US", "CP037");
        hashMap.put("EBCDIC-CP-CA", "CP037");
        hashMap.put("EBCDIC-CP-NL", "CP037");
        hashMap.put("EBCDIC-CP-WT", "CP037");
        hashMap.put("IBM277", "CP277");
        hashMap.put("CP277", "CP277");
        hashMap.put("CSIBM277", "CP277");
        hashMap.put("EBCDIC-CP-DK", "CP277");
        hashMap.put("EBCDIC-CP-NO", "CP277");
        hashMap.put("IBM278", "CP278");
        hashMap.put("CP278", "CP278");
        hashMap.put("CSIBM278", "CP278");
        hashMap.put("EBCDIC-CP-FI", "CP278");
        hashMap.put("EBCDIC-CP-SE", "CP278");
        hashMap.put("IBM280", "CP280");
        hashMap.put("CP280", "CP280");
        hashMap.put("CSIBM280", "CP280");
        hashMap.put("EBCDIC-CP-IT", "CP280");
        hashMap.put("IBM284", "CP284");
        hashMap.put("CP284", "CP284");
        hashMap.put("CSIBM284", "CP284");
        hashMap.put("EBCDIC-CP-ES", "CP284");
        hashMap.put("EBCDIC-CP-GB", "CP285");
        hashMap.put("IBM285", "CP285");
        hashMap.put("CP285", "CP285");
        hashMap.put("CSIBM285", "CP285");
        hashMap.put("EBCDIC-CP-FR", "CP297");
        hashMap.put("IBM297", "CP297");
        hashMap.put("CP297", "CP297");
        hashMap.put("CSIBM297", "CP297");
        hashMap.put("EBCDIC-CP-AR1", "CP420");
        hashMap.put("IBM420", "CP420");
        hashMap.put("CP420", "CP420");
        hashMap.put("CSIBM420", "CP420");
        hashMap.put("EBCDIC-CP-HE", "CP424");
        hashMap.put("IBM424", "CP424");
        hashMap.put("CP424", "CP424");
        hashMap.put("CSIBM424", "CP424");
        hashMap.put("EBCDIC-CP-CH", "CP500");
        hashMap.put("IBM500", "CP500");
        hashMap.put("CP500", "CP500");
        hashMap.put("CSIBM500", "CP500");
        hashMap.put("EBCDIC-CP-CH", "CP500");
        hashMap.put("EBCDIC-CP-BE", "CP500");
        hashMap.put("IBM868", "CP868");
        hashMap.put("CP868", "CP868");
        hashMap.put("CSIBM868", "CP868");
        hashMap.put("CP-AR", "CP868");
        hashMap.put("IBM869", "CP869");
        hashMap.put("CP869", "CP869");
        hashMap.put("CSIBM869", "CP869");
        hashMap.put("CP-GR", "CP869");
        hashMap.put("IBM870", "CP870");
        hashMap.put("CP870", "CP870");
        hashMap.put("CSIBM870", "CP870");
        hashMap.put("EBCDIC-CP-ROECE", "CP870");
        hashMap.put("EBCDIC-CP-YU", "CP870");
        hashMap.put("IBM871", "CP871");
        hashMap.put("CP871", "CP871");
        hashMap.put("CSIBM871", "CP871");
        hashMap.put("EBCDIC-CP-IS", "CP871");
        hashMap.put("IBM918", "CP918");
        hashMap.put("CP918", "CP918");
        hashMap.put("CSIBM918", "CP918");
        hashMap.put("EBCDIC-CP-AR2", "CP918");
        hashMap.put("EUC-JP", "EUCJIS");
        hashMap.put("CSEUCPkdFmtJapanese", "EUCJIS");
        hashMap.put("EUC-KR", "KSC5601");
        hashMap.put("GB2312", "GB2312");
        hashMap.put("CSGB2312", "GB2312");
        hashMap.put("ISO-2022-JP", "JIS");
        hashMap.put("CSISO2022JP", "JIS");
        hashMap.put("ISO-2022-KR", "ISO2022KR");
        hashMap.put("CSISO2022KR", "ISO2022KR");
        hashMap.put("ISO-2022-CN", "ISO2022CN");
        hashMap.put("X0201", "JIS0201");
        hashMap.put("CSISO13JISC6220JP", "JIS0201");
        hashMap.put("X0208", "JIS0208");
        hashMap.put("ISO-IR-87", "JIS0208");
        hashMap.put("X0208dbiJIS_X0208-1983", "JIS0208");
        hashMap.put("CSISO87JISX0208", "JIS0208");
        hashMap.put("X0212", "JIS0212");
        hashMap.put("ISO-IR-159", "JIS0212");
        hashMap.put("CSISO159JISX02121990", "JIS0212");
        hashMap.put("SHIFT_JIS", "SJIS");
        hashMap.put("CSSHIFT_JIS", "SJIS");
        hashMap.put("MS_Kanji", "SJIS");
        hashMap.put("WINDOWS-1250", BaseFont.CP1250);
        hashMap.put("WINDOWS-1251", "Cp1251");
        hashMap.put("WINDOWS-1252", "Cp1252");
        hashMap.put("WINDOWS-1253", "Cp1253");
        hashMap.put("WINDOWS-1254", "Cp1254");
        hashMap.put("WINDOWS-1255", "Cp1255");
        hashMap.put("WINDOWS-1256", "Cp1256");
        hashMap.put("WINDOWS-1257", BaseFont.CP1257);
        hashMap.put("WINDOWS-1258", "Cp1258");
        hashMap.put("TIS-620", "TIS620");
        hashMap.put("ISO-8859-1", "ISO8859_1");
        hashMap.put("ISO-IR-100", "ISO8859_1");
        hashMap.put("ISO_8859-1", "ISO8859_1");
        hashMap.put("LATIN1", "ISO8859_1");
        hashMap.put("CSISOLATIN1", "ISO8859_1");
        hashMap.put("L1", "ISO8859_1");
        hashMap.put("IBM819", "ISO8859_1");
        hashMap.put("CP819", "ISO8859_1");
        hashMap.put("ISO-8859-2", "ISO8859_2");
        hashMap.put("ISO-IR-101", "ISO8859_2");
        hashMap.put("ISO_8859-2", "ISO8859_2");
        hashMap.put("LATIN2", "ISO8859_2");
        hashMap.put("CSISOLATIN2", "ISO8859_2");
        hashMap.put("L2", "ISO8859_2");
        hashMap.put("ISO-8859-3", "ISO8859_3");
        hashMap.put("ISO-IR-109", "ISO8859_3");
        hashMap.put("ISO_8859-3", "ISO8859_3");
        hashMap.put("LATIN3", "ISO8859_3");
        hashMap.put("CSISOLATIN3", "ISO8859_3");
        hashMap.put("L3", "ISO8859_3");
        hashMap.put("ISO-8859-4", "ISO8859_4");
        hashMap.put("ISO-IR-110", "ISO8859_4");
        hashMap.put("ISO_8859-4", "ISO8859_4");
        hashMap.put("LATIN4", "ISO8859_4");
        hashMap.put("CSISOLATIN4", "ISO8859_4");
        hashMap.put("L4", "ISO8859_4");
        hashMap.put("ISO-8859-5", "ISO8859_5");
        hashMap.put("ISO-IR-144", "ISO8859_5");
        hashMap.put("ISO_8859-5", "ISO8859_5");
        hashMap.put("CYRILLIC", "ISO8859_5");
        hashMap.put("CSISOLATINCYRILLIC", "ISO8859_5");
        hashMap.put("ISO-8859-6", "ISO8859_6");
        hashMap.put("ISO-IR-127", "ISO8859_6");
        hashMap.put("ISO_8859-6", "ISO8859_6");
        hashMap.put("ECMA-114", "ISO8859_6");
        hashMap.put("ASMO-708", "ISO8859_6");
        hashMap.put("ARABIC", "ISO8859_6");
        hashMap.put("CSISOLATINARABIC", "ISO8859_6");
        hashMap.put("ISO-8859-7", "ISO8859_7");
        hashMap.put("ISO-IR-126", "ISO8859_7");
        hashMap.put("ISO_8859-7", "ISO8859_7");
        hashMap.put("ELOT_928", "ISO8859_7");
        hashMap.put("ECMA-118", "ISO8859_7");
        hashMap.put("GREEK", "ISO8859_7");
        hashMap.put("CSISOLATINGREEK", "ISO8859_7");
        hashMap.put("GREEK8", "ISO8859_7");
        hashMap.put("ISO-8859-8", "ISO8859_8");
        hashMap.put("ISO-8859-8-I", "ISO8859_8");
        hashMap.put("ISO-IR-138", "ISO8859_8");
        hashMap.put("ISO_8859-8", "ISO8859_8");
        hashMap.put("HEBREW", "ISO8859_8");
        hashMap.put("CSISOLATINHEBREW", "ISO8859_8");
        hashMap.put("ISO-8859-9", "ISO8859_9");
        hashMap.put("ISO-IR-148", "ISO8859_9");
        hashMap.put("ISO_8859-9", "ISO8859_9");
        hashMap.put("LATIN5", "ISO8859_9");
        hashMap.put("CSISOLATIN5", "ISO8859_9");
        hashMap.put("L5", "ISO8859_9");
        hashMap.put("KOI8-R", "KOI8_R");
        hashMap.put("CSKOI8-R", "KOI8_R");
        hashMap.put("US-ASCII", "ASCII");
        hashMap.put("ISO-IR-6", "ASCII");
        hashMap.put("ANSI_X3.4-1986", "ASCII");
        hashMap.put("ISO_646.IRV:1991", "ASCII");
        hashMap.put("ASCII", "ASCII");
        hashMap.put("CSASCII", "ASCII");
        hashMap.put("ISO646-US", "ASCII");
        hashMap.put("US", "ASCII");
        hashMap.put("IBM367", "ASCII");
        hashMap.put("CP367", "ASCII");
        hashMap.put("UTF-8", "UTF8");
        hashMap.put(XmpWriter.UTF16, "Unicode");
        hashMap.put(XmpWriter.UTF16BE, PdfObject.TEXT_UNICODE);
        hashMap.put(XmpWriter.UTF16LE, "UnicodeLittle");
    }

    public static String getJavaEncoding(String str) {
        String str2 = MAP.get(str.toUpperCase());
        return str2 == null ? str : str2;
    }
}
