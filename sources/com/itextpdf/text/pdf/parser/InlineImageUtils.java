package com.itextpdf.text.pdf.parser;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.exceptions.UnsupportedPdfException;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.FilterHandlers;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentParser;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class InlineImageUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(InlineImageUtils.class.getName());
    private static final Map<PdfName, PdfName> inlineImageColorSpaceAbbreviationMap;
    private static final Map<PdfName, PdfName> inlineImageEntryAbbreviationMap;
    private static final Map<PdfName, PdfName> inlineImageFilterAbbreviationMap;

    static {
        HashMap hashMap = new HashMap();
        inlineImageEntryAbbreviationMap = hashMap;
        hashMap.put(PdfName.BITSPERCOMPONENT, PdfName.BITSPERCOMPONENT);
        hashMap.put(PdfName.COLORSPACE, PdfName.COLORSPACE);
        hashMap.put(PdfName.DECODE, PdfName.DECODE);
        hashMap.put(PdfName.DECODEPARMS, PdfName.DECODEPARMS);
        hashMap.put(PdfName.FILTER, PdfName.FILTER);
        hashMap.put(PdfName.HEIGHT, PdfName.HEIGHT);
        hashMap.put(PdfName.IMAGEMASK, PdfName.IMAGEMASK);
        hashMap.put(PdfName.INTENT, PdfName.INTENT);
        hashMap.put(PdfName.INTERPOLATE, PdfName.INTERPOLATE);
        hashMap.put(PdfName.WIDTH, PdfName.WIDTH);
        hashMap.put(new PdfName("BPC"), PdfName.BITSPERCOMPONENT);
        hashMap.put(new PdfName("CS"), PdfName.COLORSPACE);
        hashMap.put(new PdfName("D"), PdfName.DECODE);
        hashMap.put(new PdfName("DP"), PdfName.DECODEPARMS);
        hashMap.put(new PdfName("F"), PdfName.FILTER);
        hashMap.put(new PdfName("H"), PdfName.HEIGHT);
        hashMap.put(new PdfName("IM"), PdfName.IMAGEMASK);
        hashMap.put(new PdfName("I"), PdfName.INTERPOLATE);
        hashMap.put(new PdfName(ExifInterface.LONGITUDE_WEST), PdfName.WIDTH);
        HashMap hashMap2 = new HashMap();
        inlineImageColorSpaceAbbreviationMap = hashMap2;
        hashMap2.put(new PdfName("G"), PdfName.DEVICEGRAY);
        hashMap2.put(new PdfName("RGB"), PdfName.DEVICERGB);
        hashMap2.put(new PdfName("CMYK"), PdfName.DEVICECMYK);
        hashMap2.put(new PdfName("I"), PdfName.INDEXED);
        HashMap hashMap3 = new HashMap();
        inlineImageFilterAbbreviationMap = hashMap3;
        hashMap3.put(new PdfName("AHx"), PdfName.ASCIIHEXDECODE);
        hashMap3.put(new PdfName("A85"), PdfName.ASCII85DECODE);
        hashMap3.put(new PdfName("LZW"), PdfName.LZWDECODE);
        hashMap3.put(new PdfName("Fl"), PdfName.FLATEDECODE);
        hashMap3.put(new PdfName("RL"), PdfName.RUNLENGTHDECODE);
        hashMap3.put(new PdfName("CCF"), PdfName.CCITTFAXDECODE);
        hashMap3.put(new PdfName("DCT"), PdfName.DCTDECODE);
    }

    private InlineImageUtils() {
    }

    public static class InlineImageParseException extends IOException {
        private static final long serialVersionUID = 233760879000268548L;

        public InlineImageParseException(String str) {
            super(str);
        }
    }

    public static InlineImageInfo parseInlineImage(PdfContentParser pdfContentParser, PdfDictionary pdfDictionary) throws IOException {
        PdfDictionary parseInlineImageDictionary = parseInlineImageDictionary(pdfContentParser);
        return new InlineImageInfo(parseInlineImageSamples(parseInlineImageDictionary, pdfDictionary, pdfContentParser), parseInlineImageDictionary);
    }

    private static PdfDictionary parseInlineImageDictionary(PdfContentParser pdfContentParser) throws IOException {
        PdfDictionary pdfDictionary = new PdfDictionary();
        while (true) {
            PdfObject readPRObject = pdfContentParser.readPRObject();
            if (readPRObject == null || "ID".equals(readPRObject.toString())) {
                int read = pdfContentParser.getTokeniser().read();
            } else {
                PdfObject readPRObject2 = pdfContentParser.readPRObject();
                PdfName pdfName = inlineImageEntryAbbreviationMap.get(readPRObject);
                if (pdfName == null) {
                    pdfName = (PdfName) readPRObject;
                }
                pdfDictionary.put(pdfName, getAlternateValue(pdfName, readPRObject2));
            }
        }
        int read2 = pdfContentParser.getTokeniser().read();
        if (PRTokeniser.isWhitespace(read2)) {
            return pdfDictionary;
        }
        throw new IOException("Unexpected character " + read2 + " found after ID in inline image");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        r4 = inlineImageColorSpaceAbbreviationMap.get(r5);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.text.pdf.PdfObject getAlternateValue(com.itextpdf.text.pdf.PdfName r4, com.itextpdf.text.pdf.PdfObject r5) {
        /*
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.FILTER
            if (r4 != r0) goto L_0x0034
            boolean r0 = r5 instanceof com.itextpdf.text.pdf.PdfName
            if (r0 == 0) goto L_0x0013
            java.util.Map<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfName> r4 = inlineImageFilterAbbreviationMap
            java.lang.Object r4 = r4.get(r5)
            com.itextpdf.text.pdf.PdfName r4 = (com.itextpdf.text.pdf.PdfName) r4
            if (r4 == 0) goto L_0x0043
            return r4
        L_0x0013:
            boolean r0 = r5 instanceof com.itextpdf.text.pdf.PdfArray
            if (r0 == 0) goto L_0x0043
            com.itextpdf.text.pdf.PdfArray r5 = (com.itextpdf.text.pdf.PdfArray) r5
            com.itextpdf.text.pdf.PdfArray r0 = new com.itextpdf.text.pdf.PdfArray
            r0.<init>()
            int r1 = r5.size()
            r2 = 0
        L_0x0023:
            if (r2 >= r1) goto L_0x0033
            com.itextpdf.text.pdf.PdfObject r3 = r5.getPdfObject(r2)
            com.itextpdf.text.pdf.PdfObject r3 = getAlternateValue(r4, r3)
            r0.add((com.itextpdf.text.pdf.PdfObject) r3)
            int r2 = r2 + 1
            goto L_0x0023
        L_0x0033:
            return r0
        L_0x0034:
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.COLORSPACE
            if (r4 != r0) goto L_0x0043
            java.util.Map<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfName> r4 = inlineImageColorSpaceAbbreviationMap
            java.lang.Object r4 = r4.get(r5)
            com.itextpdf.text.pdf.PdfName r4 = (com.itextpdf.text.pdf.PdfName) r4
            if (r4 == 0) goto L_0x0043
            return r4
        L_0x0043:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.parser.InlineImageUtils.getAlternateValue(com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfObject):com.itextpdf.text.pdf.PdfObject");
    }

    private static int getComponentsPerPixel(PdfName pdfName, PdfDictionary pdfDictionary) {
        if (pdfName == null || pdfName.equals(PdfName.DEVICEGRAY)) {
            return 1;
        }
        if (pdfName.equals(PdfName.DEVICERGB)) {
            return 3;
        }
        if (pdfName.equals(PdfName.DEVICECMYK)) {
            return 4;
        }
        if (pdfDictionary != null) {
            PdfArray asArray = pdfDictionary.getAsArray(pdfName);
            if (asArray == null) {
                PdfName asName = pdfDictionary.getAsName(pdfName);
                if (asName != null) {
                    return getComponentsPerPixel(asName, pdfDictionary);
                }
            } else if (PdfName.INDEXED.equals(asArray.getAsName(0))) {
                return 1;
            }
        }
        throw new IllegalArgumentException("Unexpected color space " + pdfName);
    }

    private static int computeBytesPerRow(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2) {
        PdfNumber asNumber = pdfDictionary.getAsNumber(PdfName.WIDTH);
        PdfNumber asNumber2 = pdfDictionary.getAsNumber(PdfName.BITSPERCOMPONENT);
        return (((asNumber.intValue() * (asNumber2 != null ? asNumber2.intValue() : 1)) * getComponentsPerPixel(pdfDictionary.getAsName(PdfName.COLORSPACE), pdfDictionary2)) + 7) / 8;
    }

    private static byte[] parseUnfilteredSamples(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2, PdfContentParser pdfContentParser) throws IOException {
        if (!pdfDictionary.contains(PdfName.FILTER)) {
            int computeBytesPerRow = computeBytesPerRow(pdfDictionary, pdfDictionary2) * pdfDictionary.getAsNumber(PdfName.HEIGHT).intValue();
            byte[] bArr = new byte[computeBytesPerRow];
            PRTokeniser tokeniser = pdfContentParser.getTokeniser();
            int read = tokeniser.read();
            int i = 0;
            if (!PRTokeniser.isWhitespace(read) || read == 0) {
                bArr[0] = (byte) read;
                i = 1;
            }
            while (i < computeBytesPerRow) {
                int read2 = tokeniser.read();
                if (read2 != -1) {
                    bArr[i] = (byte) read2;
                    i++;
                } else {
                    throw new InlineImageParseException("End of content stream reached before end of image data");
                }
            }
            if (pdfContentParser.readPRObject().toString().equals("EI") || pdfContentParser.readPRObject().toString().equals("EI")) {
                return bArr;
            }
            throw new InlineImageParseException("EI not found after end of image data");
        }
        throw new IllegalArgumentException("Dictionary contains filters");
    }

    private static byte[] parseInlineImageSamples(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2, PdfContentParser pdfContentParser) throws IOException {
        int read;
        if (!pdfDictionary.contains(PdfName.FILTER)) {
            return parseUnfilteredSamples(pdfDictionary, pdfDictionary2, pdfContentParser);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        PRTokeniser tokeniser = pdfContentParser.getTokeniser();
        while (true) {
            int i = 0;
            while (true) {
                read = tokeniser.read();
                if (read == -1) {
                    throw new InlineImageParseException("Could not find image data or EI");
                } else if (i == 0 && PRTokeniser.isWhitespace(read)) {
                    i++;
                    byteArrayOutputStream2.write(read);
                } else if (i == 1 && read == 69) {
                    i++;
                    byteArrayOutputStream2.write(read);
                } else if (i == 1 && PRTokeniser.isWhitespace(read)) {
                    byteArrayOutputStream.write(byteArrayOutputStream2.toByteArray());
                    byteArrayOutputStream2.reset();
                    byteArrayOutputStream2.write(read);
                } else if (i == 2 && read == 73) {
                    i++;
                    byteArrayOutputStream2.write(read);
                }
            }
            if (i != 3 || !PRTokeniser.isWhitespace(read)) {
                byteArrayOutputStream.write(byteArrayOutputStream2.toByteArray());
                byteArrayOutputStream2.reset();
                byteArrayOutputStream.write(read);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                if (inlineImageStreamBytesAreComplete(byteArray, pdfDictionary)) {
                    return byteArray;
                }
                byteArrayOutputStream.write(byteArrayOutputStream2.toByteArray());
                byteArrayOutputStream2.reset();
                byteArrayOutputStream.write(read);
            }
        }
    }

    private static boolean inlineImageStreamBytesAreComplete(byte[] bArr, PdfDictionary pdfDictionary) {
        try {
            PdfReader.decodeBytes(bArr, pdfDictionary, FilterHandlers.getDefaultFilterHandlers());
            return true;
        } catch (UnsupportedPdfException e) {
            LOGGER.warn(e.getMessage());
            return true;
        } catch (IOException unused) {
            return false;
        }
    }
}
