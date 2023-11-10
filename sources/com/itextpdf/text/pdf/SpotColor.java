package com.itextpdf.text.pdf;

public class SpotColor extends ExtendedColor {
    private static final long serialVersionUID = -6257004582113248079L;
    PdfSpotColor spot;
    float tint;

    public SpotColor(PdfSpotColor pdfSpotColor, float f) {
        super(3, (((((float) pdfSpotColor.getAlternativeCS().getRed()) / 255.0f) - 1.0f) * f) + 1.0f, (((((float) pdfSpotColor.getAlternativeCS().getGreen()) / 255.0f) - 1.0f) * f) + 1.0f, (((((float) pdfSpotColor.getAlternativeCS().getBlue()) / 255.0f) - 1.0f) * f) + 1.0f);
        this.spot = pdfSpotColor;
        this.tint = f;
    }

    public PdfSpotColor getPdfSpotColor() {
        return this.spot;
    }

    public float getTint() {
        return this.tint;
    }

    public boolean equals(Object obj) {
        if (obj instanceof SpotColor) {
            SpotColor spotColor = (SpotColor) obj;
            return spotColor.spot.equals(this.spot) && spotColor.tint == this.tint;
        }
    }

    public int hashCode() {
        return this.spot.hashCode() ^ Float.floatToIntBits(this.tint);
    }
}
