package com.itextpdf.text.pdf.parser;

import com.itextpdf.text.Version;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.exceptions.UnsupportedPdfException;
import com.itextpdf.text.pdf.FilterHandlers;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.codec.PngWriter;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.itextpdf.text.pdf.codec.TiffWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import kotlin.UByte;

public class PdfImageObject {
    private int bpc;
    private PdfDictionary colorSpaceDic;
    private PdfDictionary dictionary;
    private int height;
    private byte[] icc;
    private byte[] imageBytes;
    private byte[] palette;
    private int pngBitDepth;
    private int pngColorType;
    private ImageBytesType streamContentType;
    private int stride;
    private int width;

    public enum ImageBytesType {
        PNG("png"),
        JPG("jpg"),
        JP2("jp2"),
        CCITT("tif"),
        JBIG2("jbig2");
        
        private final String fileExtension;

        private ImageBytesType(String str) {
            this.fileExtension = str;
        }

        public String getFileExtension() {
            return this.fileExtension;
        }
    }

    private static class TrackingFilter implements FilterHandlers.FilterHandler {
        public PdfName lastFilterName;

        private TrackingFilter() {
            this.lastFilterName = null;
        }

        public byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            this.lastFilterName = pdfName;
            return bArr;
        }
    }

    public String getFileType() {
        return this.streamContentType.getFileExtension();
    }

    public ImageBytesType getImageBytesType() {
        return this.streamContentType;
    }

    public PdfImageObject(PRStream pRStream) throws IOException {
        this(pRStream, PdfReader.getStreamBytesRaw(pRStream), (PdfDictionary) null);
    }

    public PdfImageObject(PRStream pRStream, PdfDictionary pdfDictionary) throws IOException {
        this(pRStream, PdfReader.getStreamBytesRaw(pRStream), pdfDictionary);
    }

    protected PdfImageObject(PdfDictionary pdfDictionary, byte[] bArr, PdfDictionary pdfDictionary2) throws IOException {
        this.pngColorType = -1;
        this.streamContentType = null;
        this.dictionary = pdfDictionary;
        this.colorSpaceDic = pdfDictionary2;
        TrackingFilter trackingFilter = new TrackingFilter();
        HashMap hashMap = new HashMap(FilterHandlers.getDefaultFilterHandlers());
        hashMap.put(PdfName.JBIG2DECODE, trackingFilter);
        hashMap.put(PdfName.DCTDECODE, trackingFilter);
        hashMap.put(PdfName.JPXDECODE, trackingFilter);
        this.imageBytes = PdfReader.decodeBytes(bArr, pdfDictionary, hashMap);
        if (trackingFilter.lastFilterName == null) {
            decodeImageBytes();
        } else if (PdfName.JBIG2DECODE.equals(trackingFilter.lastFilterName)) {
            this.streamContentType = ImageBytesType.JBIG2;
        } else if (PdfName.DCTDECODE.equals(trackingFilter.lastFilterName)) {
            this.streamContentType = ImageBytesType.JPG;
        } else if (PdfName.JPXDECODE.equals(trackingFilter.lastFilterName)) {
            this.streamContentType = ImageBytesType.JP2;
        }
    }

    public PdfObject get(PdfName pdfName) {
        return this.dictionary.get(pdfName);
    }

    public PdfDictionary getDictionary() {
        return this.dictionary;
    }

    private void findColorspace(PdfObject pdfObject, boolean z) throws IOException {
        int i;
        if (pdfObject == null && (i = this.bpc) == 1) {
            this.stride = ((this.width * i) + 7) / 8;
            this.pngColorType = 0;
        } else if (PdfName.DEVICEGRAY.equals(pdfObject)) {
            this.stride = ((this.width * this.bpc) + 7) / 8;
            this.pngColorType = 0;
        } else if (PdfName.DEVICERGB.equals(pdfObject)) {
            int i2 = this.bpc;
            if (i2 == 8 || i2 == 16) {
                this.stride = (((this.width * i2) * 3) + 7) / 8;
                this.pngColorType = 2;
            }
        } else if (pdfObject instanceof PdfArray) {
            PdfArray pdfArray = (PdfArray) pdfObject;
            PdfObject directObject = pdfArray.getDirectObject(0);
            if (PdfName.CALGRAY.equals(directObject)) {
                this.stride = ((this.width * this.bpc) + 7) / 8;
                this.pngColorType = 0;
            } else if (PdfName.CALRGB.equals(directObject)) {
                int i3 = this.bpc;
                if (i3 == 8 || i3 == 16) {
                    this.stride = (((this.width * i3) * 3) + 7) / 8;
                    this.pngColorType = 2;
                }
            } else if (PdfName.ICCBASED.equals(directObject)) {
                PRStream pRStream = (PRStream) pdfArray.getDirectObject(1);
                int intValue = pRStream.getAsNumber(PdfName.f610N).intValue();
                if (intValue == 1) {
                    this.stride = ((this.width * this.bpc) + 7) / 8;
                    this.pngColorType = 0;
                    this.icc = PdfReader.getStreamBytes(pRStream);
                } else if (intValue == 3) {
                    this.stride = (((this.width * this.bpc) * 3) + 7) / 8;
                    this.pngColorType = 2;
                    this.icc = PdfReader.getStreamBytes(pRStream);
                }
            } else if (z && PdfName.INDEXED.equals(directObject)) {
                findColorspace(pdfArray.getDirectObject(1), false);
                if (this.pngColorType == 2) {
                    PdfObject directObject2 = pdfArray.getDirectObject(3);
                    if (directObject2 instanceof PdfString) {
                        this.palette = ((PdfString) directObject2).getBytes();
                    } else if (directObject2 instanceof PRStream) {
                        this.palette = PdfReader.getStreamBytes((PRStream) directObject2);
                    }
                    this.stride = ((this.width * this.bpc) + 7) / 8;
                    this.pngColorType = 3;
                }
            }
        }
    }

    private void decodeImageBytes() throws IOException {
        PdfDictionary pdfDictionary;
        PdfObject directObject;
        if (this.streamContentType == null) {
            this.pngColorType = -1;
            PdfArray asArray = this.dictionary.getAsArray(PdfName.DECODE);
            this.width = this.dictionary.getAsNumber(PdfName.WIDTH).intValue();
            this.height = this.dictionary.getAsNumber(PdfName.HEIGHT).intValue();
            int intValue = this.dictionary.getAsNumber(PdfName.BITSPERCOMPONENT).intValue();
            this.bpc = intValue;
            this.pngBitDepth = intValue;
            PdfObject directObject2 = this.dictionary.getDirectObject(PdfName.COLORSPACE);
            if (!(!(directObject2 instanceof PdfName) || (pdfDictionary = this.colorSpaceDic) == null || (directObject = pdfDictionary.getDirectObject((PdfName) directObject2)) == null)) {
                directObject2 = directObject;
            }
            this.palette = null;
            this.icc = null;
            this.stride = 0;
            findColorspace(directObject2, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (this.pngColorType >= 0) {
                PngWriter pngWriter = new PngWriter(byteArrayOutputStream);
                if (asArray != null && this.pngBitDepth == 1 && asArray.getAsNumber(0).intValue() == 1 && asArray.getAsNumber(1).intValue() == 0) {
                    int length = this.imageBytes.length;
                    for (int i = 0; i < length; i++) {
                        byte[] bArr = this.imageBytes;
                        bArr[i] = (byte) (bArr[i] ^ UByte.MAX_VALUE);
                    }
                }
                pngWriter.writeHeader(this.width, this.height, this.pngBitDepth, this.pngColorType);
                byte[] bArr2 = this.icc;
                if (bArr2 != null) {
                    pngWriter.writeIccProfile(bArr2);
                }
                byte[] bArr3 = this.palette;
                if (bArr3 != null) {
                    pngWriter.writePalette(bArr3);
                }
                pngWriter.writeData(this.imageBytes, this.stride);
                pngWriter.writeEnd();
                this.streamContentType = ImageBytesType.PNG;
                this.imageBytes = byteArrayOutputStream.toByteArray();
            } else if (this.bpc == 8) {
                if (!PdfName.DEVICECMYK.equals(directObject2)) {
                    if (directObject2 instanceof PdfArray) {
                        PdfArray pdfArray = (PdfArray) directObject2;
                        if (PdfName.ICCBASED.equals(pdfArray.getDirectObject(0))) {
                            PRStream pRStream = (PRStream) pdfArray.getDirectObject(1);
                            int intValue2 = pRStream.getAsNumber(PdfName.f610N).intValue();
                            if (intValue2 == 4) {
                                this.icc = PdfReader.getStreamBytes(pRStream);
                            } else {
                                throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("N.value.1.is.not.supported", intValue2));
                            }
                        } else {
                            throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.color.space.1.is.not.supported", directObject2));
                        }
                    } else {
                        throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.color.space.1.is.not.supported", directObject2));
                    }
                }
                this.stride = this.width * 4;
                TiffWriter tiffWriter = new TiffWriter();
                tiffWriter.addField(new TiffWriter.FieldShort(277, 4));
                tiffWriter.addField(new TiffWriter.FieldShort(258, new int[]{8, 8, 8, 8}));
                tiffWriter.addField(new TiffWriter.FieldShort(262, 5));
                tiffWriter.addField(new TiffWriter.FieldLong(256, this.width));
                tiffWriter.addField(new TiffWriter.FieldLong(257, this.height));
                tiffWriter.addField(new TiffWriter.FieldShort(259, 5));
                tiffWriter.addField(new TiffWriter.FieldShort(317, 2));
                tiffWriter.addField(new TiffWriter.FieldLong(278, this.height));
                tiffWriter.addField(new TiffWriter.FieldRational(282, new int[]{300, 1}));
                tiffWriter.addField(new TiffWriter.FieldRational(283, new int[]{300, 1}));
                tiffWriter.addField(new TiffWriter.FieldShort(296, 2));
                tiffWriter.addField(new TiffWriter.FieldAscii(305, Version.getInstance().getVersion()));
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                TiffWriter.compressLZW(byteArrayOutputStream2, 2, this.imageBytes, this.height, 4, this.stride);
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                tiffWriter.addField(new TiffWriter.FieldImage(byteArray));
                tiffWriter.addField(new TiffWriter.FieldLong(279, byteArray.length));
                byte[] bArr4 = this.icc;
                if (bArr4 != null) {
                    tiffWriter.addField(new TiffWriter.FieldUndefined(TIFFConstants.TIFFTAG_ICCPROFILE, bArr4));
                }
                tiffWriter.writeFile(byteArrayOutputStream);
                this.streamContentType = ImageBytesType.CCITT;
                this.imageBytes = byteArrayOutputStream.toByteArray();
            } else {
                throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.color.depth.1.is.not.supported", this.bpc));
            }
        } else {
            throw new IllegalStateException(MessageLocalization.getComposedMessage("Decoding.can't.happen.on.this.type.of.stream.(.1.)", this.streamContentType));
        }
    }

    public byte[] getImageAsBytes() {
        return this.imageBytes;
    }

    public BufferedImage getBufferedImage() throws IOException {
        byte[] imageAsBytes = getImageAsBytes();
        if (imageAsBytes == null) {
            return null;
        }
        return ImageIO.read(new ByteArrayInputStream(imageAsBytes));
    }
}
