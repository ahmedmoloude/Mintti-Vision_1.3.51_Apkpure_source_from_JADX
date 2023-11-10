package com.itextpdf.text;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.codec.wmf.InputMeta;
import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImgWMF extends Image {
    ImgWMF(Image image) {
        super(image);
    }

    public ImgWMF(URL url) throws BadElementException, IOException {
        super(url);
        processParameters();
    }

    public ImgWMF(String str) throws BadElementException, MalformedURLException, IOException {
        this(Utilities.toURL(str));
    }

    public ImgWMF(byte[] bArr) throws BadElementException, IOException {
        super((URL) null);
        this.rawData = bArr;
        this.originalData = bArr;
        processParameters();
    }

    private void processParameters() throws BadElementException, IOException {
        String str;
        this.type = 35;
        this.originalType = 6;
        InputStream inputStream = null;
        try {
            if (this.rawData == null) {
                inputStream = this.url.openStream();
                str = this.url.toString();
            } else {
                str = "Byte array";
                inputStream = new ByteArrayInputStream(this.rawData);
            }
            InputMeta inputMeta = new InputMeta(inputStream);
            if (inputMeta.readInt() == -1698247209) {
                inputMeta.readWord();
                int readShort = inputMeta.readShort();
                int readShort2 = inputMeta.readShort();
                int readShort3 = inputMeta.readShort();
                int readShort4 = inputMeta.readShort();
                int readWord = inputMeta.readWord();
                this.dpiX = 72;
                this.dpiY = 72;
                float f = (float) readWord;
                this.scaledHeight = (((float) (readShort4 - readShort2)) / f) * 72.0f;
                setTop(this.scaledHeight);
                this.scaledWidth = (((float) (readShort3 - readShort)) / f) * 72.0f;
                setRight(this.scaledWidth);
                return;
            }
            throw new BadElementException(MessageLocalization.getComposedMessage("1.is.not.a.valid.placeable.windows.metafile", str));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            this.plainWidth = getWidth();
            this.plainHeight = getHeight();
        }
    }

    public void readWMF(PdfTemplate pdfTemplate) throws IOException, DocumentException {
        InputStream byteArrayInputStream;
        setTemplateData(pdfTemplate);
        pdfTemplate.setWidth(getWidth());
        pdfTemplate.setHeight(getHeight());
        InputStream inputStream = null;
        try {
            if (this.rawData == null) {
                byteArrayInputStream = this.url.openStream();
            } else {
                byteArrayInputStream = new ByteArrayInputStream(this.rawData);
            }
            new MetaDo(inputStream, pdfTemplate).readAll();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
