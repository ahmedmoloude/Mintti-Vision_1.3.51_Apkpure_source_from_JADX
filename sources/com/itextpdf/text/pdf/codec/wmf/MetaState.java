package com.itextpdf.text.pdf.codec.wmf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import java.util.ArrayList;
import java.util.Stack;

public class MetaState {
    public static final int ALTERNATE = 1;
    public static final int OPAQUE = 2;
    public static final int TA_BASELINE = 24;
    public static final int TA_BOTTOM = 8;
    public static final int TA_CENTER = 6;
    public static final int TA_LEFT = 0;
    public static final int TA_NOUPDATECP = 0;
    public static final int TA_RIGHT = 2;
    public static final int TA_TOP = 0;
    public static final int TA_UPDATECP = 1;
    public static final int TRANSPARENT = 1;
    public static final int WINDING = 2;
    public ArrayList<MetaObject> MetaObjects;
    public int backgroundMode;
    public BaseColor currentBackgroundColor;
    public MetaBrush currentBrush;
    public MetaFont currentFont;
    public MetaPen currentPen;
    public Point currentPoint;
    public BaseColor currentTextColor;
    public int extentWx;
    public int extentWy;
    public int lineJoin;
    public int offsetWx;
    public int offsetWy;
    public int polyFillMode;
    public Stack<MetaState> savedStates;
    public float scalingX;
    public float scalingY;
    public int textAlign;

    public MetaState() {
        this.currentBackgroundColor = BaseColor.WHITE;
        this.currentTextColor = BaseColor.BLACK;
        this.backgroundMode = 2;
        this.polyFillMode = 1;
        this.lineJoin = 1;
        this.savedStates = new Stack<>();
        this.MetaObjects = new ArrayList<>();
        this.currentPoint = new Point(0, 0);
        this.currentPen = new MetaPen();
        this.currentBrush = new MetaBrush();
        this.currentFont = new MetaFont();
    }

    public MetaState(MetaState metaState) {
        this.currentBackgroundColor = BaseColor.WHITE;
        this.currentTextColor = BaseColor.BLACK;
        this.backgroundMode = 2;
        this.polyFillMode = 1;
        this.lineJoin = 1;
        setMetaState(metaState);
    }

    public void setMetaState(MetaState metaState) {
        this.savedStates = metaState.savedStates;
        this.MetaObjects = metaState.MetaObjects;
        this.currentPoint = metaState.currentPoint;
        this.currentPen = metaState.currentPen;
        this.currentBrush = metaState.currentBrush;
        this.currentFont = metaState.currentFont;
        this.currentBackgroundColor = metaState.currentBackgroundColor;
        this.currentTextColor = metaState.currentTextColor;
        this.backgroundMode = metaState.backgroundMode;
        this.polyFillMode = metaState.polyFillMode;
        this.textAlign = metaState.textAlign;
        this.lineJoin = metaState.lineJoin;
        this.offsetWx = metaState.offsetWx;
        this.offsetWy = metaState.offsetWy;
        this.extentWx = metaState.extentWx;
        this.extentWy = metaState.extentWy;
        this.scalingX = metaState.scalingX;
        this.scalingY = metaState.scalingY;
    }

    public void addMetaObject(MetaObject metaObject) {
        for (int i = 0; i < this.MetaObjects.size(); i++) {
            if (this.MetaObjects.get(i) == null) {
                this.MetaObjects.set(i, metaObject);
                return;
            }
        }
        this.MetaObjects.add(metaObject);
    }

    public void selectMetaObject(int i, PdfContentByte pdfContentByte) {
        MetaObject metaObject = this.MetaObjects.get(i);
        if (metaObject != null) {
            int type = metaObject.getType();
            if (type == 1) {
                MetaPen metaPen = (MetaPen) metaObject;
                this.currentPen = metaPen;
                int style = metaPen.getStyle();
                if (style != 5) {
                    pdfContentByte.setColorStroke(this.currentPen.getColor());
                    pdfContentByte.setLineWidth(Math.abs((((float) this.currentPen.getPenWidth()) * this.scalingX) / ((float) this.extentWx)));
                    if (style == 1) {
                        pdfContentByte.setLineDash(18.0f, 6.0f, 0.0f);
                    } else if (style == 2) {
                        pdfContentByte.setLineDash(3.0f, 0.0f);
                    } else if (style == 3) {
                        pdfContentByte.setLiteral("[9 6 3 6]0 d\n");
                    } else if (style != 4) {
                        pdfContentByte.setLineDash(0.0f);
                    } else {
                        pdfContentByte.setLiteral("[9 3 3 3 3 3]0 d\n");
                    }
                }
            } else if (type == 2) {
                MetaBrush metaBrush = (MetaBrush) metaObject;
                this.currentBrush = metaBrush;
                int style2 = metaBrush.getStyle();
                if (style2 == 0) {
                    pdfContentByte.setColorFill(this.currentBrush.getColor());
                } else if (style2 == 2) {
                    pdfContentByte.setColorFill(this.currentBackgroundColor);
                }
            } else if (type == 3) {
                this.currentFont = (MetaFont) metaObject;
            }
        }
    }

    public void deleteMetaObject(int i) {
        this.MetaObjects.set(i, (Object) null);
    }

    public void saveState(PdfContentByte pdfContentByte) {
        pdfContentByte.saveState();
        this.savedStates.push(new MetaState(this));
    }

    public void restoreState(int i, PdfContentByte pdfContentByte) {
        int i2;
        if (i < 0) {
            i2 = Math.min(-i, this.savedStates.size());
        } else {
            i2 = Math.max(this.savedStates.size() - i, 0);
        }
        if (i2 != 0) {
            MetaState metaState = null;
            while (true) {
                int i3 = i2 - 1;
                if (i2 != 0) {
                    pdfContentByte.restoreState();
                    metaState = this.savedStates.pop();
                    i2 = i3;
                } else {
                    setMetaState(metaState);
                    return;
                }
            }
        }
    }

    public void cleanup(PdfContentByte pdfContentByte) {
        int size = this.savedStates.size();
        while (true) {
            int i = size - 1;
            if (size > 0) {
                pdfContentByte.restoreState();
                size = i;
            } else {
                return;
            }
        }
    }

    public float transformX(int i) {
        return ((((float) i) - ((float) this.offsetWx)) * this.scalingX) / ((float) this.extentWx);
    }

    public float transformY(int i) {
        return (1.0f - ((((float) i) - ((float) this.offsetWy)) / ((float) this.extentWy))) * this.scalingY;
    }

    public void setScalingX(float f) {
        this.scalingX = f;
    }

    public void setScalingY(float f) {
        this.scalingY = f;
    }

    public void setOffsetWx(int i) {
        this.offsetWx = i;
    }

    public void setOffsetWy(int i) {
        this.offsetWy = i;
    }

    public void setExtentWx(int i) {
        this.extentWx = i;
    }

    public void setExtentWy(int i) {
        this.extentWy = i;
    }

    public float transformAngle(float f) {
        if (this.scalingY < 0.0f) {
            f = -f;
        }
        return (float) (this.scalingX < 0.0f ? 3.141592653589793d - ((double) f) : (double) f);
    }

    public void setCurrentPoint(Point point) {
        this.currentPoint = point;
    }

    public Point getCurrentPoint() {
        return this.currentPoint;
    }

    public MetaBrush getCurrentBrush() {
        return this.currentBrush;
    }

    public MetaPen getCurrentPen() {
        return this.currentPen;
    }

    public MetaFont getCurrentFont() {
        return this.currentFont;
    }

    public BaseColor getCurrentBackgroundColor() {
        return this.currentBackgroundColor;
    }

    public void setCurrentBackgroundColor(BaseColor baseColor) {
        this.currentBackgroundColor = baseColor;
    }

    public BaseColor getCurrentTextColor() {
        return this.currentTextColor;
    }

    public void setCurrentTextColor(BaseColor baseColor) {
        this.currentTextColor = baseColor;
    }

    public int getBackgroundMode() {
        return this.backgroundMode;
    }

    public void setBackgroundMode(int i) {
        this.backgroundMode = i;
    }

    public int getTextAlign() {
        return this.textAlign;
    }

    public void setTextAlign(int i) {
        this.textAlign = i;
    }

    public int getPolyFillMode() {
        return this.polyFillMode;
    }

    public void setPolyFillMode(int i) {
        this.polyFillMode = i;
    }

    public void setLineJoinRectangle(PdfContentByte pdfContentByte) {
        if (this.lineJoin != 0) {
            this.lineJoin = 0;
            pdfContentByte.setLineJoin(0);
        }
    }

    public void setLineJoinPolygon(PdfContentByte pdfContentByte) {
        if (this.lineJoin == 0) {
            this.lineJoin = 1;
            pdfContentByte.setLineJoin(1);
        }
    }

    public boolean getLineNeutral() {
        return this.lineJoin == 0;
    }
}
