package com.itextpdf.text.pdf.internal;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ExtendedColor;
import com.itextpdf.text.pdf.PatternColor;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfXConformanceException;
import com.itextpdf.text.pdf.ShadingColor;
import com.itextpdf.text.pdf.SpotColor;
import com.itextpdf.text.pdf.interfaces.PdfXConformance;

public class PdfXConformanceImp implements PdfXConformance {
    protected int pdfxConformance = 0;
    protected PdfWriter writer;

    public PdfXConformanceImp(PdfWriter pdfWriter) {
        this.writer = pdfWriter;
    }

    public void setPDFXConformance(int i) {
        this.pdfxConformance = i;
    }

    public int getPDFXConformance() {
        return this.pdfxConformance;
    }

    public boolean isPdfIso() {
        return isPdfX();
    }

    public boolean isPdfX() {
        return this.pdfxConformance != 0;
    }

    public boolean isPdfX1A2001() {
        return this.pdfxConformance == 1;
    }

    public boolean isPdfX32002() {
        return this.pdfxConformance == 2;
    }

    public void checkPdfIsoConformance(int i, Object obj) {
        PdfObject pdfObject;
        PdfWriter pdfWriter = this.writer;
        if (pdfWriter != null && pdfWriter.isPdfX()) {
            int pDFXConformance = this.writer.getPDFXConformance();
            if (i != 1) {
                if (i != 3) {
                    if (i == 4) {
                        BaseFont baseFont = (BaseFont) obj;
                        if (!baseFont.isEmbedded()) {
                            throw new PdfXConformanceException(MessageLocalization.getComposedMessage("all.the.fonts.must.be.embedded.this.one.isn.t.1", baseFont.getPostscriptFontName()));
                        }
                    } else if (i == 5) {
                        PdfImage pdfImage = (PdfImage) obj;
                        if (pdfImage.get(PdfName.SMASK) != null) {
                            throw new PdfXConformanceException(MessageLocalization.getComposedMessage("the.smask.key.is.not.allowed.in.images", new Object[0]));
                        } else if (pDFXConformance != 1 || (pdfObject = pdfImage.get(PdfName.COLORSPACE)) == null) {
                        } else {
                            if (pdfObject.isName()) {
                                if (PdfName.DEVICERGB.equals(pdfObject)) {
                                    throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.rgb.is.not.allowed", new Object[0]));
                                }
                            } else if (pdfObject.isArray() && PdfName.CALRGB.equals(((PdfArray) pdfObject).getPdfObject(0))) {
                                throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.calrgb.is.not.allowed", new Object[0]));
                            }
                        }
                    } else if (i == 6) {
                        PdfDictionary pdfDictionary = (PdfDictionary) obj;
                        if (pdfDictionary != null) {
                            PdfObject pdfObject2 = pdfDictionary.get(PdfName.f555BM);
                            if (pdfObject2 == null || PdfGState.BM_NORMAL.equals(pdfObject2) || PdfGState.BM_COMPATIBLE.equals(pdfObject2)) {
                                PdfObject pdfObject3 = pdfDictionary.get(PdfName.f560CA);
                                if (pdfObject3 != null) {
                                    double doubleValue = ((PdfNumber) pdfObject3).doubleValue();
                                    if (doubleValue != 1.0d) {
                                        throw new PdfXConformanceException(MessageLocalization.getComposedMessage("transparency.is.not.allowed.ca.eq.1", String.valueOf(doubleValue)));
                                    }
                                }
                                PdfObject pdfObject4 = pdfDictionary.get(PdfName.f677ca);
                                if (pdfObject4 != null) {
                                    double doubleValue2 = ((PdfNumber) pdfObject4).doubleValue();
                                    if (doubleValue2 != 1.0d) {
                                        throw new PdfXConformanceException(MessageLocalization.getComposedMessage("transparency.is.not.allowed.ca.eq.1", String.valueOf(doubleValue2)));
                                    }
                                    return;
                                }
                                return;
                            }
                            throw new PdfXConformanceException(MessageLocalization.getComposedMessage("blend.mode.1.not.allowed", pdfObject2.toString()));
                        }
                    } else if (i == 7) {
                        throw new PdfXConformanceException(MessageLocalization.getComposedMessage("layers.are.not.allowed", new Object[0]));
                    }
                } else if (pDFXConformance == 1) {
                    throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.rgb.is.not.allowed", new Object[0]));
                }
            } else if (pDFXConformance == 1) {
                if (obj instanceof ExtendedColor) {
                    ExtendedColor extendedColor = (ExtendedColor) obj;
                    int type = extendedColor.getType();
                    if (type == 0) {
                        throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.rgb.is.not.allowed", new Object[0]));
                    } else if (type == 3) {
                        checkPdfIsoConformance(1, ((SpotColor) extendedColor).getPdfSpotColor().getAlternativeCS());
                    } else if (type == 4) {
                        checkPdfIsoConformance(1, ((PatternColor) extendedColor).getPainter().getDefaultColor());
                    } else if (type == 5) {
                        checkPdfIsoConformance(1, ((ShadingColor) extendedColor).getPdfShadingPattern().getShading().getColorSpace());
                    }
                } else if (obj instanceof BaseColor) {
                    throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.rgb.is.not.allowed", new Object[0]));
                }
            }
        }
    }
}
