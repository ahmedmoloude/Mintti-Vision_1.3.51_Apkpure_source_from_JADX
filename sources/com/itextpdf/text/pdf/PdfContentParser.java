package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.PRTokeniser;
import java.io.IOException;
import java.util.ArrayList;

public class PdfContentParser {
    public static final int COMMAND_TYPE = 200;
    private PRTokeniser tokeniser;

    public PdfContentParser(PRTokeniser pRTokeniser) {
        this.tokeniser = pRTokeniser;
    }

    public ArrayList<PdfObject> parse(ArrayList<PdfObject> arrayList) throws IOException {
        PdfObject readPRObject;
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        } else {
            arrayList.clear();
        }
        do {
            readPRObject = readPRObject();
            if (readPRObject == null) {
                break;
            }
            arrayList.add(readPRObject);
        } while (readPRObject.type() != 200);
        return arrayList;
    }

    public PRTokeniser getTokeniser() {
        return this.tokeniser;
    }

    public void setTokeniser(PRTokeniser pRTokeniser) {
        this.tokeniser = pRTokeniser;
    }

    public PdfDictionary readDictionary() throws IOException {
        PdfDictionary pdfDictionary = new PdfDictionary();
        while (nextValidToken()) {
            if (this.tokeniser.getTokenType() == PRTokeniser.TokenType.END_DIC) {
                return pdfDictionary;
            }
            if (this.tokeniser.getTokenType() != PRTokeniser.TokenType.OTHER || !"def".equals(this.tokeniser.getStringValue())) {
                if (this.tokeniser.getTokenType() == PRTokeniser.TokenType.NAME) {
                    PdfName pdfName = new PdfName(this.tokeniser.getStringValue(), false);
                    PdfObject readPRObject = readPRObject();
                    int i = -readPRObject.type();
                    if (i == PRTokeniser.TokenType.END_DIC.ordinal()) {
                        throw new IOException(MessageLocalization.getComposedMessage("unexpected.gt.gt", new Object[0]));
                    } else if (i != PRTokeniser.TokenType.END_ARRAY.ordinal()) {
                        pdfDictionary.put(pdfName, readPRObject);
                    } else {
                        throw new IOException(MessageLocalization.getComposedMessage("unexpected.close.bracket", new Object[0]));
                    }
                } else {
                    throw new IOException(MessageLocalization.getComposedMessage("dictionary.key.1.is.not.a.name", this.tokeniser.getStringValue()));
                }
            }
        }
        throw new IOException(MessageLocalization.getComposedMessage("unexpected.end.of.file", new Object[0]));
    }

    public PdfArray readArray() throws IOException {
        PdfArray pdfArray = new PdfArray();
        while (true) {
            PdfObject readPRObject = readPRObject();
            int i = -readPRObject.type();
            if (i == PRTokeniser.TokenType.END_ARRAY.ordinal()) {
                return pdfArray;
            }
            if (i != PRTokeniser.TokenType.END_DIC.ordinal()) {
                pdfArray.add(readPRObject);
            } else {
                throw new IOException(MessageLocalization.getComposedMessage("unexpected.gt.gt", new Object[0]));
            }
        }
    }

    public PdfObject readPRObject() throws IOException {
        if (!nextValidToken()) {
            return null;
        }
        PRTokeniser.TokenType tokenType = this.tokeniser.getTokenType();
        switch (C15191.$SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType[tokenType.ordinal()]) {
            case 1:
                return readDictionary();
            case 2:
                return readArray();
            case 3:
                return new PdfString(this.tokeniser.getStringValue(), (String) null).setHexWriting(this.tokeniser.isHexString());
            case 4:
                return new PdfName(this.tokeniser.getStringValue(), false);
            case 5:
                return new PdfNumber(this.tokeniser.getStringValue());
            case 6:
                return new PdfLiteral(200, this.tokeniser.getStringValue());
            default:
                return new PdfLiteral(-tokenType.ordinal(), this.tokeniser.getStringValue());
        }
    }

    /* renamed from: com.itextpdf.text.pdf.PdfContentParser$1 */
    static /* synthetic */ class C15191 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.itextpdf.text.pdf.PRTokeniser$TokenType[] r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType = r0
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.START_DIC     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.START_ARRAY     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.STRING     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NAME     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NUMBER     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.OTHER     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfContentParser.C15191.<clinit>():void");
        }
    }

    public boolean nextValidToken() throws IOException {
        while (this.tokeniser.nextToken()) {
            if (this.tokeniser.getTokenType() != PRTokeniser.TokenType.COMMENT) {
                return true;
            }
        }
        return false;
    }
}
