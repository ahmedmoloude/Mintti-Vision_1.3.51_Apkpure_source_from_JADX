package com.itextpdf.text.pdf;

import com.itextpdf.text.Image;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.html.HtmlTags;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfImage extends PdfStream {
    static final int TRANSFERSIZE = 4096;
    protected Image image = null;
    protected PdfName name = null;

    public PdfImage(Image image2, String str, PdfIndirectReference pdfIndirectReference) throws BadPdfFormatException {
        String str2;
        InputStream inputStream = null;
        this.image = image2;
        if (str == null) {
            generateImgResName(image2);
        } else {
            this.name = new PdfName(str);
        }
        put(PdfName.TYPE, PdfName.XOBJECT);
        put(PdfName.SUBTYPE, PdfName.IMAGE);
        put(PdfName.WIDTH, new PdfNumber(image2.getWidth()));
        put(PdfName.HEIGHT, new PdfNumber(image2.getHeight()));
        if (image2.getLayer() != null) {
            put(PdfName.f618OC, image2.getLayer().getRef());
        }
        if (image2.isMask() && (image2.getBpc() == 1 || image2.getBpc() > 255)) {
            put(PdfName.IMAGEMASK, PdfBoolean.PDFTRUE);
        }
        if (pdfIndirectReference != null) {
            if (image2.isSmask()) {
                put(PdfName.SMASK, pdfIndirectReference);
            } else {
                put(PdfName.MASK, pdfIndirectReference);
            }
        }
        if (image2.isMask() && image2.isInverted()) {
            put(PdfName.DECODE, new PdfLiteral("[1 0]"));
        }
        if (image2.isInterpolation()) {
            put(PdfName.INTERPOLATE, PdfBoolean.PDFTRUE);
        }
        try {
            int[] transparency = image2.getTransparency();
            if (transparency != null && !image2.isMask() && pdfIndirectReference == null) {
                StringBuilder sb = new StringBuilder("[");
                for (int append : transparency) {
                    sb.append(append);
                    sb.append(" ");
                }
                sb.append("]");
                put(PdfName.MASK, new PdfLiteral(sb.toString()));
            }
            if (image2.isImgRaw()) {
                int colorspace = image2.getColorspace();
                this.bytes = image2.getRawData();
                put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
                int bpc = image2.getBpc();
                if (bpc > 255) {
                    if (!image2.isMask()) {
                        put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
                    }
                    put(PdfName.BITSPERCOMPONENT, new PdfNumber(1));
                    put(PdfName.FILTER, PdfName.CCITTFAXDECODE);
                    int i = bpc - 257;
                    PdfDictionary pdfDictionary = new PdfDictionary();
                    if (i != 0) {
                        pdfDictionary.put(PdfName.f605K, new PdfNumber(i));
                    }
                    if ((colorspace & 1) != 0) {
                        pdfDictionary.put(PdfName.BLACKIS1, PdfBoolean.PDFTRUE);
                    }
                    if ((colorspace & 2) != 0) {
                        pdfDictionary.put(PdfName.ENCODEDBYTEALIGN, PdfBoolean.PDFTRUE);
                    }
                    if ((colorspace & 4) != 0) {
                        pdfDictionary.put(PdfName.ENDOFLINE, PdfBoolean.PDFTRUE);
                    }
                    if ((colorspace & 8) != 0) {
                        pdfDictionary.put(PdfName.ENDOFBLOCK, PdfBoolean.PDFFALSE);
                    }
                    pdfDictionary.put(PdfName.COLUMNS, new PdfNumber(image2.getWidth()));
                    pdfDictionary.put(PdfName.ROWS, new PdfNumber(image2.getHeight()));
                    put(PdfName.DECODEPARMS, pdfDictionary);
                    return;
                }
                if (colorspace == 1) {
                    put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
                    if (image2.isInverted()) {
                        put(PdfName.DECODE, new PdfLiteral("[1 0]"));
                    }
                } else if (colorspace != 3) {
                    put(PdfName.COLORSPACE, PdfName.DEVICECMYK);
                    if (image2.isInverted()) {
                        put(PdfName.DECODE, new PdfLiteral("[1 0 1 0 1 0 1 0]"));
                    }
                } else {
                    put(PdfName.COLORSPACE, PdfName.DEVICERGB);
                    if (image2.isInverted()) {
                        put(PdfName.DECODE, new PdfLiteral("[1 0 1 0 1 0]"));
                    }
                }
                PdfDictionary additional = image2.getAdditional();
                if (additional != null) {
                    putAll(additional);
                }
                if (image2.isMask() && (image2.getBpc() == 1 || image2.getBpc() > 8)) {
                    remove(PdfName.COLORSPACE);
                }
                put(PdfName.BITSPERCOMPONENT, new PdfNumber(image2.getBpc()));
                if (image2.isDeflated()) {
                    put(PdfName.FILTER, PdfName.FLATEDECODE);
                } else {
                    flateCompress(image2.getCompressionLevel());
                }
            } else {
                if (image2.getRawData() == null) {
                    inputStream = image2.getUrl().openStream();
                    str2 = image2.getUrl().toString();
                } else {
                    inputStream = new ByteArrayInputStream(image2.getRawData());
                    str2 = "Byte array";
                }
                int type = image2.type();
                if (type == 32) {
                    put(PdfName.FILTER, PdfName.DCTDECODE);
                    if (image2.getColorTransform() == 0) {
                        PdfDictionary pdfDictionary2 = new PdfDictionary();
                        pdfDictionary2.put(PdfName.COLORTRANSFORM, new PdfNumber(0));
                        put(PdfName.DECODEPARMS, pdfDictionary2);
                    }
                    int colorspace2 = image2.getColorspace();
                    if (colorspace2 == 1) {
                        put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
                    } else if (colorspace2 != 3) {
                        put(PdfName.COLORSPACE, PdfName.DEVICECMYK);
                        if (image2.isInverted()) {
                            put(PdfName.DECODE, new PdfLiteral("[1 0 1 0 1 0 1 0]"));
                        }
                    } else {
                        put(PdfName.COLORSPACE, PdfName.DEVICERGB);
                    }
                    put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
                    if (image2.getRawData() != null) {
                        this.bytes = image2.getRawData();
                        put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                                return;
                            } catch (Exception unused) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        this.streamBytes = new ByteArrayOutputStream();
                        transferBytes(inputStream, this.streamBytes, -1);
                    }
                } else if (type == 33) {
                    put(PdfName.FILTER, PdfName.JPXDECODE);
                    if (image2.getColorspace() > 0) {
                        int colorspace3 = image2.getColorspace();
                        if (colorspace3 == 1) {
                            put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
                        } else if (colorspace3 != 3) {
                            put(PdfName.COLORSPACE, PdfName.DEVICECMYK);
                        } else {
                            put(PdfName.COLORSPACE, PdfName.DEVICERGB);
                        }
                        put(PdfName.BITSPERCOMPONENT, new PdfNumber(image2.getBpc()));
                    }
                    if (image2.getRawData() != null) {
                        this.bytes = image2.getRawData();
                        put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                                return;
                            } catch (Exception unused2) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        this.streamBytes = new ByteArrayOutputStream();
                        transferBytes(inputStream, this.streamBytes, -1);
                    }
                } else if (type == 36) {
                    put(PdfName.FILTER, PdfName.JBIG2DECODE);
                    put(PdfName.COLORSPACE, PdfName.DEVICEGRAY);
                    put(PdfName.BITSPERCOMPONENT, new PdfNumber(1));
                    if (image2.getRawData() != null) {
                        this.bytes = image2.getRawData();
                        put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                                return;
                            } catch (Exception unused3) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        this.streamBytes = new ByteArrayOutputStream();
                        transferBytes(inputStream, this.streamBytes, -1);
                    }
                } else {
                    throw new BadPdfFormatException(MessageLocalization.getComposedMessage("1.is.an.unknown.image.format", str2));
                }
                if (image2.getCompressionLevel() > 0) {
                    flateCompress(image2.getCompressionLevel());
                }
                put(PdfName.LENGTH, new PdfNumber(this.streamBytes.size()));
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception unused4) {
                    }
                }
            }
        } catch (IOException e) {
            throw new BadPdfFormatException(e.getMessage());
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception unused5) {
                }
            }
            throw th;
        }
    }

    public PdfName name() {
        return this.name;
    }

    public Image getImage() {
        return this.image;
    }

    static void transferBytes(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[4096];
        if (i < 0) {
            i = 2147418112;
        }
        while (i != 0) {
            int read = inputStream.read(bArr, 0, Math.min(i, 4096));
            if (read >= 0) {
                outputStream.write(bArr, 0, read);
                i -= read;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void importAll(PdfImage pdfImage) {
        this.name = pdfImage.name;
        this.compressed = pdfImage.compressed;
        this.compressionLevel = pdfImage.compressionLevel;
        this.streamBytes = pdfImage.streamBytes;
        this.bytes = pdfImage.bytes;
        this.hashMap = pdfImage.hashMap;
    }

    private void generateImgResName(Image image2) {
        this.name = new PdfName(HtmlTags.IMG + Long.toHexString(image2.getMySerialId().longValue()));
    }
}
