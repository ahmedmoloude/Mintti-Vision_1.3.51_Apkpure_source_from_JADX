package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;

public class DeviceNColor extends ExtendedColor {
    PdfDeviceNColor pdfDeviceNColor;
    float[] tints;

    public DeviceNColor(PdfDeviceNColor pdfDeviceNColor2, float[] fArr) {
        super(6);
        if (pdfDeviceNColor2.getSpotColors().length == fArr.length) {
            this.pdfDeviceNColor = pdfDeviceNColor2;
            this.tints = fArr;
            return;
        }
        throw new RuntimeException(MessageLocalization.getComposedMessage("devicen.color.shall.have.the.same.number.of.colorants.as.the.destination.DeviceN.color.space", new Object[0]));
    }

    public PdfDeviceNColor getPdfDeviceNColor() {
        return this.pdfDeviceNColor;
    }

    public float[] getTints() {
        return this.tints;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DeviceNColor) {
            DeviceNColor deviceNColor = (DeviceNColor) obj;
            int length = deviceNColor.tints.length;
            float[] fArr = this.tints;
            if (length == fArr.length) {
                int i = 0;
                for (float f : fArr) {
                    if (f != deviceNColor.tints[i]) {
                        return false;
                    }
                    i++;
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.pdfDeviceNColor.hashCode();
        for (float valueOf : this.tints) {
            hashCode ^= Float.valueOf(valueOf).hashCode();
        }
        return hashCode;
    }
}
