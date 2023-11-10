package com.itextpdf.text;

import com.itextpdf.text.error_messages.MessageLocalization;

public class RectangleReadOnly extends Rectangle {
    public RectangleReadOnly(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
    }

    public RectangleReadOnly(float f, float f2, float f3, float f4, int i) {
        super(f, f2, f3, f4);
        super.setRotation(i);
    }

    public RectangleReadOnly(float f, float f2) {
        super(0.0f, 0.0f, f, f2);
    }

    public RectangleReadOnly(float f, float f2, int i) {
        super(0.0f, 0.0f, f, f2);
        super.setRotation(i);
    }

    public RectangleReadOnly(Rectangle rectangle) {
        super(rectangle.llx, rectangle.lly, rectangle.urx, rectangle.ury);
        super.cloneNonPositionParameters(rectangle);
    }

    private void throwReadOnlyError() {
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("rectanglereadonly.this.rectangle.is.read.only", new Object[0]));
    }

    public void setRotation(int i) {
        throwReadOnlyError();
    }

    public void setLeft(float f) {
        throwReadOnlyError();
    }

    public void setRight(float f) {
        throwReadOnlyError();
    }

    public void setTop(float f) {
        throwReadOnlyError();
    }

    public void setBottom(float f) {
        throwReadOnlyError();
    }

    public void normalize() {
        throwReadOnlyError();
    }

    public void setBackgroundColor(BaseColor baseColor) {
        throwReadOnlyError();
    }

    public void setGrayFill(float f) {
        throwReadOnlyError();
    }

    public void setBorder(int i) {
        throwReadOnlyError();
    }

    public void setUseVariableBorders(boolean z) {
        throwReadOnlyError();
    }

    public void enableBorderSide(int i) {
        throwReadOnlyError();
    }

    public void disableBorderSide(int i) {
        throwReadOnlyError();
    }

    public void setBorderWidth(float f) {
        throwReadOnlyError();
    }

    public void setBorderWidthLeft(float f) {
        throwReadOnlyError();
    }

    public void setBorderWidthRight(float f) {
        throwReadOnlyError();
    }

    public void setBorderWidthTop(float f) {
        throwReadOnlyError();
    }

    public void setBorderWidthBottom(float f) {
        throwReadOnlyError();
    }

    public void setBorderColor(BaseColor baseColor) {
        throwReadOnlyError();
    }

    public void setBorderColorLeft(BaseColor baseColor) {
        throwReadOnlyError();
    }

    public void setBorderColorRight(BaseColor baseColor) {
        throwReadOnlyError();
    }

    public void setBorderColorTop(BaseColor baseColor) {
        throwReadOnlyError();
    }

    public void setBorderColorBottom(BaseColor baseColor) {
        throwReadOnlyError();
    }

    public void cloneNonPositionParameters(Rectangle rectangle) {
        throwReadOnlyError();
    }

    public void softCloneNonPositionParameters(Rectangle rectangle) {
        throwReadOnlyError();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("RectangleReadOnly: ");
        stringBuffer.append(getWidth());
        stringBuffer.append('x');
        stringBuffer.append(getHeight());
        stringBuffer.append(" (rot: ");
        stringBuffer.append(this.rotation);
        stringBuffer.append(" degrees)");
        return stringBuffer.toString();
    }
}
