package com.itextpdf.text.api;

public interface Spaceable {
    float getPaddingTop();

    float getSpacingAfter();

    float getSpacingBefore();

    void setPaddingTop(float f);

    void setSpacingAfter(float f);

    void setSpacingBefore(float f);
}
