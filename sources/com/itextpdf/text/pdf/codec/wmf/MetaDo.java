package com.itextpdf.text.pdf.codec.wmf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.codec.BmpImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MetaDo {
    public static final int META_ANIMATEPALETTE = 1078;
    public static final int META_ARC = 2071;
    public static final int META_BITBLT = 2338;
    public static final int META_CHORD = 2096;
    public static final int META_CREATEBRUSHINDIRECT = 764;
    public static final int META_CREATEFONTINDIRECT = 763;
    public static final int META_CREATEPALETTE = 247;
    public static final int META_CREATEPATTERNBRUSH = 505;
    public static final int META_CREATEPENINDIRECT = 762;
    public static final int META_CREATEREGION = 1791;
    public static final int META_DELETEOBJECT = 496;
    public static final int META_DIBBITBLT = 2368;
    public static final int META_DIBCREATEPATTERNBRUSH = 322;
    public static final int META_DIBSTRETCHBLT = 2881;
    public static final int META_ELLIPSE = 1048;
    public static final int META_ESCAPE = 1574;
    public static final int META_EXCLUDECLIPRECT = 1045;
    public static final int META_EXTFLOODFILL = 1352;
    public static final int META_EXTTEXTOUT = 2610;
    public static final int META_FILLREGION = 552;
    public static final int META_FLOODFILL = 1049;
    public static final int META_FRAMEREGION = 1065;
    public static final int META_INTERSECTCLIPRECT = 1046;
    public static final int META_INVERTREGION = 298;
    public static final int META_LINETO = 531;
    public static final int META_MOVETO = 532;
    public static final int META_OFFSETCLIPRGN = 544;
    public static final int META_OFFSETVIEWPORTORG = 529;
    public static final int META_OFFSETWINDOWORG = 527;
    public static final int META_PAINTREGION = 299;
    public static final int META_PATBLT = 1565;
    public static final int META_PIE = 2074;
    public static final int META_POLYGON = 804;
    public static final int META_POLYLINE = 805;
    public static final int META_POLYPOLYGON = 1336;
    public static final int META_REALIZEPALETTE = 53;
    public static final int META_RECTANGLE = 1051;
    public static final int META_RESIZEPALETTE = 313;
    public static final int META_RESTOREDC = 295;
    public static final int META_ROUNDRECT = 1564;
    public static final int META_SAVEDC = 30;
    public static final int META_SCALEVIEWPORTEXT = 1042;
    public static final int META_SCALEWINDOWEXT = 1040;
    public static final int META_SELECTCLIPREGION = 300;
    public static final int META_SELECTOBJECT = 301;
    public static final int META_SELECTPALETTE = 564;
    public static final int META_SETBKCOLOR = 513;
    public static final int META_SETBKMODE = 258;
    public static final int META_SETDIBTODEV = 3379;
    public static final int META_SETMAPMODE = 259;
    public static final int META_SETMAPPERFLAGS = 561;
    public static final int META_SETPALENTRIES = 55;
    public static final int META_SETPIXEL = 1055;
    public static final int META_SETPOLYFILLMODE = 262;
    public static final int META_SETRELABS = 261;
    public static final int META_SETROP2 = 260;
    public static final int META_SETSTRETCHBLTMODE = 263;
    public static final int META_SETTEXTALIGN = 302;
    public static final int META_SETTEXTCHAREXTRA = 264;
    public static final int META_SETTEXTCOLOR = 521;
    public static final int META_SETTEXTJUSTIFICATION = 522;
    public static final int META_SETVIEWPORTEXT = 526;
    public static final int META_SETVIEWPORTORG = 525;
    public static final int META_SETWINDOWEXT = 524;
    public static final int META_SETWINDOWORG = 523;
    public static final int META_STRETCHBLT = 2851;
    public static final int META_STRETCHDIB = 3907;
    public static final int META_TEXTOUT = 1313;
    int bottom;

    /* renamed from: cb */
    public PdfContentByte f726cb;

    /* renamed from: in */
    public InputMeta f727in;
    int inch;
    int left;
    int right;
    MetaState state = new MetaState();

    /* renamed from: top  reason: collision with root package name */
    int f2172top;

    public MetaDo(InputStream inputStream, PdfContentByte pdfContentByte) {
        this.f726cb = pdfContentByte;
        this.f727in = new InputMeta(inputStream);
    }

    public void readAll() throws IOException, DocumentException {
        int i;
        String str;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        String str2;
        int i7;
        MetaDo metaDo = this;
        int i8 = 0;
        if (metaDo.f727in.readInt() == -1698247209) {
            metaDo.f727in.readWord();
            metaDo.left = metaDo.f727in.readShort();
            metaDo.f2172top = metaDo.f727in.readShort();
            metaDo.right = metaDo.f727in.readShort();
            metaDo.bottom = metaDo.f727in.readShort();
            int readWord = metaDo.f727in.readWord();
            metaDo.inch = readWord;
            metaDo.state.setScalingX((((float) (metaDo.right - metaDo.left)) / ((float) readWord)) * 72.0f);
            metaDo.state.setScalingY((((float) (metaDo.bottom - metaDo.f2172top)) / ((float) metaDo.inch)) * 72.0f);
            metaDo.state.setOffsetWx(metaDo.left);
            metaDo.state.setOffsetWy(metaDo.f2172top);
            metaDo.state.setExtentWx(metaDo.right - metaDo.left);
            metaDo.state.setExtentWy(metaDo.bottom - metaDo.f2172top);
            metaDo.f727in.readInt();
            metaDo.f727in.readWord();
            metaDo.f727in.skip(18);
            boolean z = true;
            metaDo.f726cb.setLineCap(1);
            metaDo.f726cb.setLineJoin(1);
            while (true) {
                int length = metaDo.f727in.getLength();
                int readInt = metaDo.f727in.readInt();
                if (readInt < 3) {
                    metaDo.state.cleanup(metaDo.f726cb);
                    return;
                }
                int readWord2 = metaDo.f727in.readWord();
                switch (readWord2) {
                    case 30:
                        i = length;
                        metaDo.state.saveState(metaDo.f726cb);
                        continue;
                    case 247:
                    case 322:
                    case 1791:
                        i = length;
                        metaDo.state.addMetaObject(new MetaObject());
                        continue;
                    case 258:
                        i = length;
                        metaDo.state.setBackgroundMode(metaDo.f727in.readWord());
                        continue;
                    case 262:
                        i = length;
                        metaDo.state.setPolyFillMode(metaDo.f727in.readWord());
                        continue;
                    case 295:
                        i = length;
                        metaDo.state.restoreState(metaDo.f727in.readShort(), metaDo.f726cb);
                        continue;
                    case 301:
                        i = length;
                        metaDo.state.selectMetaObject(metaDo.f727in.readWord(), metaDo.f726cb);
                        continue;
                    case 302:
                        i = length;
                        metaDo.state.setTextAlign(metaDo.f727in.readWord());
                        continue;
                    case 496:
                        i = length;
                        metaDo.state.deleteMetaObject(metaDo.f727in.readWord());
                        continue;
                    case 513:
                        i = length;
                        metaDo.state.setCurrentBackgroundColor(metaDo.f727in.readColor());
                        continue;
                    case 521:
                        i = length;
                        metaDo.state.setCurrentTextColor(metaDo.f727in.readColor());
                        continue;
                    case 523:
                        i = length;
                        metaDo.state.setOffsetWy(metaDo.f727in.readShort());
                        metaDo.state.setOffsetWx(metaDo.f727in.readShort());
                        continue;
                    case 524:
                        i = length;
                        metaDo.state.setExtentWy(metaDo.f727in.readShort());
                        metaDo.state.setExtentWx(metaDo.f727in.readShort());
                        continue;
                    case 531:
                        i = length;
                        int readShort = metaDo.f727in.readShort();
                        int readShort2 = metaDo.f727in.readShort();
                        Point currentPoint = metaDo.state.getCurrentPoint();
                        metaDo.f726cb.moveTo(metaDo.state.transformX(currentPoint.f728x), metaDo.state.transformY(currentPoint.f729y));
                        metaDo.f726cb.lineTo(metaDo.state.transformX(readShort2), metaDo.state.transformY(readShort));
                        metaDo.f726cb.stroke();
                        metaDo.state.setCurrentPoint(new Point(readShort2, readShort));
                        continue;
                    case 532:
                        i = length;
                        metaDo.state.setCurrentPoint(new Point(metaDo.f727in.readShort(), metaDo.f727in.readShort()));
                        continue;
                    case 762:
                        i = length;
                        MetaPen metaPen = new MetaPen();
                        metaPen.init(metaDo.f727in);
                        metaDo.state.addMetaObject(metaPen);
                        continue;
                    case 763:
                        i = length;
                        MetaFont metaFont = new MetaFont();
                        metaFont.init(metaDo.f727in);
                        metaDo.state.addMetaObject(metaFont);
                        continue;
                    case 764:
                        i = length;
                        MetaBrush metaBrush = new MetaBrush();
                        metaBrush.init(metaDo.f727in);
                        metaDo.state.addMetaObject(metaBrush);
                        continue;
                    case 804:
                        i = length;
                        if (metaDo.isNullStrokeFill(false)) {
                            break;
                        } else {
                            int readWord3 = metaDo.f727in.readWord();
                            int readShort3 = metaDo.f727in.readShort();
                            int readShort4 = metaDo.f727in.readShort();
                            metaDo.f726cb.moveTo(metaDo.state.transformX(readShort3), metaDo.state.transformY(readShort4));
                            for (int i9 = 1; i9 < readWord3; i9++) {
                                metaDo.f726cb.lineTo(metaDo.state.transformX(metaDo.f727in.readShort()), metaDo.state.transformY(metaDo.f727in.readShort()));
                            }
                            metaDo.f726cb.lineTo(metaDo.state.transformX(readShort3), metaDo.state.transformY(readShort4));
                            strokeAndFill();
                            continue;
                        }
                    case 805:
                        i = length;
                        metaDo.state.setLineJoinPolygon(metaDo.f726cb);
                        int readWord4 = metaDo.f727in.readWord();
                        metaDo.f726cb.moveTo(metaDo.state.transformX(metaDo.f727in.readShort()), metaDo.state.transformY(metaDo.f727in.readShort()));
                        for (int i10 = 1; i10 < readWord4; i10++) {
                            metaDo.f726cb.lineTo(metaDo.state.transformX(metaDo.f727in.readShort()), metaDo.state.transformY(metaDo.f727in.readShort()));
                        }
                        metaDo.f726cb.stroke();
                        continue;
                    case 1046:
                        i = length;
                        float transformY = metaDo.state.transformY(metaDo.f727in.readShort());
                        float transformX = metaDo.state.transformX(metaDo.f727in.readShort());
                        float transformY2 = metaDo.state.transformY(metaDo.f727in.readShort());
                        float transformX2 = metaDo.state.transformX(metaDo.f727in.readShort());
                        metaDo.f726cb.rectangle(transformX2, transformY, transformX - transformX2, transformY2 - transformY);
                        metaDo.f726cb.eoClip();
                        metaDo.f726cb.newPath();
                        continue;
                    case 1048:
                        i = length;
                        if (metaDo.isNullStrokeFill(metaDo.state.getLineNeutral())) {
                            break;
                        } else {
                            int readShort5 = metaDo.f727in.readShort();
                            int readShort6 = metaDo.f727in.readShort();
                            int readShort7 = metaDo.f727in.readShort();
                            int readShort8 = metaDo.f727in.readShort();
                            metaDo.f726cb.arc(metaDo.state.transformX(readShort8), metaDo.state.transformY(readShort5), metaDo.state.transformX(readShort6), metaDo.state.transformY(readShort7), 0.0f, 360.0f);
                            strokeAndFill();
                            continue;
                        }
                    case 1051:
                        i = length;
                        if (metaDo.isNullStrokeFill(true)) {
                            break;
                        } else {
                            float transformY3 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX3 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY4 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX4 = metaDo.state.transformX(metaDo.f727in.readShort());
                            metaDo.f726cb.rectangle(transformX4, transformY3, transformX3 - transformX4, transformY4 - transformY3);
                            strokeAndFill();
                            continue;
                        }
                    case 1055:
                        i = length;
                        BaseColor readColor = metaDo.f727in.readColor();
                        int readShort9 = metaDo.f727in.readShort();
                        int readShort10 = metaDo.f727in.readShort();
                        metaDo.f726cb.saveState();
                        metaDo.f726cb.setColorFill(readColor);
                        metaDo.f726cb.rectangle(metaDo.state.transformX(readShort10), metaDo.state.transformY(readShort9), 0.2f, 0.2f);
                        metaDo.f726cb.fill();
                        metaDo.f726cb.restoreState();
                        break;
                    case 1313:
                        i = length;
                        int readWord5 = metaDo.f727in.readWord();
                        byte[] bArr = new byte[readWord5];
                        int i11 = 0;
                        while (i11 < readWord5) {
                            byte readByte = (byte) metaDo.f727in.readByte();
                            if (readByte != 0) {
                                bArr[i11] = readByte;
                                i11++;
                            }
                        }
                        try {
                            i2 = 0;
                            try {
                                str = new String(bArr, 0, i11, "Cp1252");
                            } catch (UnsupportedEncodingException unused) {
                            }
                        } catch (UnsupportedEncodingException unused2) {
                            i2 = 0;
                            str = new String(bArr, i2, i11);
                            metaDo.f727in.skip(((readWord5 + 1) & 65534) - i11);
                            int readShort11 = metaDo.f727in.readShort();
                            outputText(metaDo.f727in.readShort(), readShort11, 0, 0, 0, 0, 0, str);
                            InputMeta inputMeta = metaDo.f727in;
                            inputMeta.skip((readInt * 2) - (inputMeta.getLength() - i));
                            i8 = 0;
                            z = true;
                        }
                        metaDo.f727in.skip(((readWord5 + 1) & 65534) - i11);
                        int readShort112 = metaDo.f727in.readShort();
                        outputText(metaDo.f727in.readShort(), readShort112, 0, 0, 0, 0, 0, str);
                    case 1336:
                        i = length;
                        if (!metaDo.isNullStrokeFill(false)) {
                            int readWord6 = metaDo.f727in.readWord();
                            int[] iArr = new int[readWord6];
                            for (int i12 = 0; i12 < readWord6; i12++) {
                                iArr[i12] = metaDo.f727in.readWord();
                            }
                            for (int i13 = 0; i13 < readWord6; i13++) {
                                int i14 = iArr[i13];
                                int readShort12 = metaDo.f727in.readShort();
                                int readShort13 = metaDo.f727in.readShort();
                                metaDo.f726cb.moveTo(metaDo.state.transformX(readShort12), metaDo.state.transformY(readShort13));
                                for (int i15 = 1; i15 < i14; i15++) {
                                    metaDo.f726cb.lineTo(metaDo.state.transformX(metaDo.f727in.readShort()), metaDo.state.transformY(metaDo.f727in.readShort()));
                                }
                                metaDo.f726cb.lineTo(metaDo.state.transformX(readShort12), metaDo.state.transformY(readShort13));
                            }
                            strokeAndFill();
                            break;
                        }
                        break;
                    case 1564:
                        i = length;
                        if (!metaDo.isNullStrokeFill(true)) {
                            float transformY5 = metaDo.state.transformY(0) - metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX5 = metaDo.state.transformX(metaDo.f727in.readShort()) - metaDo.state.transformX(0);
                            float transformY6 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX6 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY7 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX7 = metaDo.state.transformX(metaDo.f727in.readShort());
                            metaDo.f726cb.roundRectangle(transformX7, transformY6, transformX6 - transformX7, transformY7 - transformY6, (transformY5 + transformX5) / 4.0f);
                            strokeAndFill();
                            break;
                        }
                        break;
                    case 2071:
                        i = length;
                        if (!metaDo.isNullStrokeFill(metaDo.state.getLineNeutral())) {
                            float transformY8 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX8 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY9 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX9 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY10 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX10 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY11 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX11 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float f = (transformX10 + transformX11) / 2.0f;
                            float f2 = (transformY11 + transformY10) / 2.0f;
                            float arc = getArc(f, f2, transformX9, transformY9);
                            float arc2 = getArc(f, f2, transformX8, transformY8) - arc;
                            if (arc2 <= 0.0f) {
                                arc2 += 360.0f;
                            }
                            metaDo.f726cb.arc(transformX11, transformY10, transformX10, transformY11, arc, arc2);
                            metaDo.f726cb.stroke();
                            break;
                        }
                        break;
                    case 2074:
                        i = length;
                        if (!metaDo.isNullStrokeFill(metaDo.state.getLineNeutral())) {
                            float transformY12 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX12 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY13 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX13 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY14 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX14 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY15 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX15 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float f3 = (transformX14 + transformX15) / 2.0f;
                            float f4 = (transformY15 + transformY14) / 2.0f;
                            double arc3 = (double) getArc(f3, f4, transformX13, transformY13);
                            double arc4 = ((double) getArc(f3, f4, transformX12, transformY12)) - arc3;
                            if (arc4 <= 0.0d) {
                                arc4 += 360.0d;
                            }
                            double d = (double) transformY14;
                            ArrayList<double[]> bezierArc = PdfContentByte.bezierArc((double) transformX15, d, (double) transformX14, (double) transformY15, arc3, arc4);
                            if (!bezierArc.isEmpty()) {
                                double[] dArr = bezierArc.get(0);
                                metaDo.f726cb.moveTo(f3, f4);
                                metaDo.f726cb.lineTo(dArr[0], dArr[1]);
                                for (int i16 = 0; i16 < bezierArc.size(); i16++) {
                                    double[] dArr2 = bezierArc.get(i16);
                                    metaDo.f726cb.curveTo(dArr2[2], dArr2[3], dArr2[4], dArr2[5], dArr2[6], dArr2[7]);
                                }
                                metaDo.f726cb.lineTo(f3, f4);
                                strokeAndFill();
                                break;
                            }
                        }
                        break;
                    case 2096:
                        if (!metaDo.isNullStrokeFill(metaDo.state.getLineNeutral())) {
                            float transformY16 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX16 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY17 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX17 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY18 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX18 = metaDo.state.transformX(metaDo.f727in.readShort());
                            float transformY19 = metaDo.state.transformY(metaDo.f727in.readShort());
                            float transformX19 = metaDo.state.transformX(metaDo.f727in.readShort());
                            i = length;
                            double d2 = (double) ((transformX18 + transformX19) / 2.0f);
                            double d3 = (double) ((transformY19 + transformY18) / 2.0f);
                            double arc5 = getArc(d2, d3, (double) transformX17, (double) transformY17);
                            double arc6 = getArc(d2, d3, (double) transformX16, (double) transformY16) - arc5;
                            if (arc6 <= 0.0d) {
                                arc6 += 360.0d;
                            }
                            double d4 = (double) transformY18;
                            ArrayList<double[]> bezierArc2 = PdfContentByte.bezierArc((double) transformX19, d4, (double) transformX18, (double) transformY19, arc5, arc6);
                            if (!bezierArc2.isEmpty()) {
                                double[] dArr3 = bezierArc2.get(0);
                                double d5 = dArr3[0];
                                double d6 = dArr3[1];
                                metaDo = this;
                                metaDo.f726cb.moveTo(d5, d6);
                                for (int i17 = 0; i17 < bezierArc2.size(); i17++) {
                                    double[] dArr4 = bezierArc2.get(i17);
                                    metaDo.f726cb.curveTo(dArr4[2], dArr4[3], dArr4[4], dArr4[5], dArr4[6], dArr4[7]);
                                }
                                metaDo.f726cb.lineTo(d5, d6);
                                strokeAndFill();
                                break;
                            } else {
                                metaDo = this;
                                continue;
                            }
                        }
                        break;
                    case 2610:
                        int readShort14 = metaDo.f727in.readShort();
                        int readShort15 = metaDo.f727in.readShort();
                        int readWord7 = metaDo.f727in.readWord();
                        int readWord8 = metaDo.f727in.readWord();
                        if ((readWord8 & 6) != 0) {
                            i6 = metaDo.f727in.readShort();
                            i5 = metaDo.f727in.readShort();
                            i4 = metaDo.f727in.readShort();
                            i3 = metaDo.f727in.readShort();
                        } else {
                            i6 = 0;
                            i5 = 0;
                            i4 = 0;
                            i3 = 0;
                        }
                        byte[] bArr2 = new byte[readWord7];
                        int i18 = 0;
                        while (i18 < readWord7) {
                            byte readByte2 = (byte) metaDo.f727in.readByte();
                            if (readByte2 != 0) {
                                bArr2[i18] = readByte2;
                                i18++;
                            }
                        }
                        try {
                            i7 = 0;
                            try {
                                str2 = new String(bArr2, 0, i18, "Cp1252");
                            } catch (UnsupportedEncodingException unused3) {
                            }
                        } catch (UnsupportedEncodingException unused4) {
                            i7 = 0;
                            str2 = new String(bArr2, i7, i18);
                            outputText(readShort15, readShort14, readWord8, i6, i5, i4, i3, str2);
                            i = length;
                            InputMeta inputMeta2 = metaDo.f727in;
                            inputMeta2.skip((readInt * 2) - (inputMeta2.getLength() - i));
                            i8 = 0;
                            z = true;
                        }
                        outputText(readShort15, readShort14, readWord8, i6, i5, i4, i3, str2);
                    case 2881:
                    case 3907:
                        metaDo.f727in.readInt();
                        if (readWord2 == 3907) {
                            metaDo.f727in.readWord();
                        }
                        int readShort16 = metaDo.f727in.readShort();
                        int readShort17 = metaDo.f727in.readShort();
                        int readShort18 = metaDo.f727in.readShort();
                        int readShort19 = metaDo.f727in.readShort();
                        float transformY20 = metaDo.state.transformY(metaDo.f727in.readShort()) - metaDo.state.transformY(i8);
                        float transformX20 = metaDo.state.transformX(metaDo.f727in.readShort()) - metaDo.state.transformX(i8);
                        float transformY21 = metaDo.state.transformY(metaDo.f727in.readShort());
                        float transformX21 = metaDo.state.transformX(metaDo.f727in.readShort());
                        int length2 = (readInt * 2) - (metaDo.f727in.getLength() - length);
                        byte[] bArr3 = new byte[length2];
                        for (int i19 = 0; i19 < length2; i19++) {
                            bArr3[i19] = (byte) metaDo.f727in.readByte();
                        }
                        try {
                            Image image = BmpImage.getImage(new ByteArrayInputStream(bArr3), z, length2);
                            metaDo.f726cb.saveState();
                            metaDo.f726cb.rectangle(transformX21, transformY21, transformX20, transformY20);
                            metaDo.f726cb.clip();
                            metaDo.f726cb.newPath();
                            float f5 = (float) readShort17;
                            float f6 = (float) readShort16;
                            image.scaleAbsolute((image.getWidth() * transformX20) / f5, ((-transformY20) * image.getHeight()) / f6);
                            image.setAbsolutePosition(transformX21 - ((transformX20 * ((float) readShort19)) / f5), (transformY21 + ((transformY20 * ((float) readShort18)) / f6)) - image.getScaledHeight());
                            metaDo.f726cb.addImage(image);
                            metaDo.f726cb.restoreState();
                            break;
                        } catch (Exception unused5) {
                            break;
                        }
                }
                i = length;
                InputMeta inputMeta22 = metaDo.f727in;
                inputMeta22.skip((readInt * 2) - (inputMeta22.getLength() - i));
                i8 = 0;
                z = true;
            }
        } else {
            throw new DocumentException(MessageLocalization.getComposedMessage("not.a.placeable.windows.metafile", new Object[0]));
        }
    }

    public void outputText(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) {
        String str2 = str;
        MetaFont currentFont = this.state.getCurrentFont();
        float transformX = this.state.transformX(i);
        float transformY = this.state.transformY(i2);
        double transformAngle = (double) this.state.transformAngle(currentFont.getAngle());
        float sin = (float) Math.sin(transformAngle);
        float cos = (float) Math.cos(transformAngle);
        float fontSize = currentFont.getFontSize(this.state);
        BaseFont font = currentFont.getFont();
        int textAlign = this.state.getTextAlign();
        float widthPoint = font.getWidthPoint(str2, fontSize);
        float fontDescriptor = font.getFontDescriptor(3, fontSize);
        float fontDescriptor2 = font.getFontDescriptor(8, fontSize);
        this.f726cb.saveState();
        this.f726cb.concatCTM(cos, sin, -sin, cos, transformX, transformY);
        float f = 0.0f;
        float f2 = (textAlign & 6) == 6 ? (-widthPoint) / 2.0f : (textAlign & 2) == 2 ? -widthPoint : 0.0f;
        if ((textAlign & 24) != 24) {
            f = (textAlign & 8) == 8 ? -fontDescriptor : -fontDescriptor2;
        }
        if (this.state.getBackgroundMode() == 2) {
            this.f726cb.setColorFill(this.state.getCurrentBackgroundColor());
            this.f726cb.rectangle(f2, f + fontDescriptor, widthPoint, fontDescriptor2 - fontDescriptor);
            this.f726cb.fill();
        }
        this.f726cb.setColorFill(this.state.getCurrentTextColor());
        this.f726cb.beginText();
        this.f726cb.setFontAndSize(font, fontSize);
        this.f726cb.setTextMatrix(f2, f);
        this.f726cb.showText(str2);
        this.f726cb.endText();
        if (currentFont.isUnderline()) {
            this.f726cb.rectangle(f2, f - (fontSize / 4.0f), widthPoint, fontSize / 15.0f);
            this.f726cb.fill();
        }
        if (currentFont.isStrikeout()) {
            this.f726cb.rectangle(f2, f + (fontSize / 3.0f), widthPoint, fontSize / 15.0f);
            this.f726cb.fill();
        }
        this.f726cb.restoreState();
    }

    public boolean isNullStrokeFill(boolean z) {
        MetaPen currentPen = this.state.getCurrentPen();
        MetaBrush currentBrush = this.state.getCurrentBrush();
        boolean z2 = true;
        boolean z3 = currentPen.getStyle() == 5;
        int style = currentBrush.getStyle();
        boolean z4 = style == 0 || (style == 2 && this.state.getBackgroundMode() == 2);
        if (!z3 || z4) {
            z2 = false;
        }
        if (!z3) {
            if (z) {
                this.state.setLineJoinRectangle(this.f726cb);
            } else {
                this.state.setLineJoinPolygon(this.f726cb);
            }
        }
        return z2;
    }

    public void strokeAndFill() {
        MetaPen currentPen = this.state.getCurrentPen();
        MetaBrush currentBrush = this.state.getCurrentBrush();
        int style = currentPen.getStyle();
        int style2 = currentBrush.getStyle();
        if (style == 5) {
            this.f726cb.closePath();
            if (this.state.getPolyFillMode() == 1) {
                this.f726cb.eoFill();
            } else {
                this.f726cb.fill();
            }
        } else {
            if (!(style2 == 0 || (style2 == 2 && this.state.getBackgroundMode() == 2))) {
                this.f726cb.closePathStroke();
            } else if (this.state.getPolyFillMode() == 1) {
                this.f726cb.closePathEoFillStroke();
            } else {
                this.f726cb.closePathFillStroke();
            }
        }
    }

    static float getArc(float f, float f2, float f3, float f4) {
        return (float) getArc((double) f, (double) f2, (double) f3, (double) f4);
    }

    static double getArc(double d, double d2, double d3, double d4) {
        double atan2 = Math.atan2(d4 - d2, d3 - d);
        if (atan2 < 0.0d) {
            atan2 += 6.283185307179586d;
        }
        return (double) ((float) ((atan2 / 3.141592653589793d) * 180.0d));
    }

    public static byte[] wrapBMP(Image image) throws IOException {
        byte[] bArr;
        if (image.getOriginalType() == 4) {
            if (image.getOriginalData() == null) {
                InputStream openStream = image.getUrl().openStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int read = openStream.read();
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(read);
                }
                openStream.close();
                bArr = byteArrayOutputStream.toByteArray();
            } else {
                bArr = image.getOriginalData();
            }
            int length = ((bArr.length - 14) + 1) >>> 1;
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            writeWord(byteArrayOutputStream2, 1);
            writeWord(byteArrayOutputStream2, 9);
            writeWord(byteArrayOutputStream2, 768);
            writeDWord(byteArrayOutputStream2, length + 36 + 3);
            writeWord(byteArrayOutputStream2, 1);
            writeDWord(byteArrayOutputStream2, length + 14);
            writeWord(byteArrayOutputStream2, 0);
            writeDWord(byteArrayOutputStream2, 4);
            writeWord(byteArrayOutputStream2, 259);
            writeWord(byteArrayOutputStream2, 8);
            writeDWord(byteArrayOutputStream2, 5);
            writeWord(byteArrayOutputStream2, 523);
            writeWord(byteArrayOutputStream2, 0);
            writeWord(byteArrayOutputStream2, 0);
            writeDWord(byteArrayOutputStream2, 5);
            writeWord(byteArrayOutputStream2, 524);
            writeWord(byteArrayOutputStream2, (int) image.getHeight());
            writeWord(byteArrayOutputStream2, (int) image.getWidth());
            writeDWord(byteArrayOutputStream2, length + 13);
            writeWord(byteArrayOutputStream2, 2881);
            writeDWord(byteArrayOutputStream2, 13369376);
            writeWord(byteArrayOutputStream2, (int) image.getHeight());
            writeWord(byteArrayOutputStream2, (int) image.getWidth());
            writeWord(byteArrayOutputStream2, 0);
            writeWord(byteArrayOutputStream2, 0);
            writeWord(byteArrayOutputStream2, (int) image.getHeight());
            writeWord(byteArrayOutputStream2, (int) image.getWidth());
            writeWord(byteArrayOutputStream2, 0);
            writeWord(byteArrayOutputStream2, 0);
            byteArrayOutputStream2.write(bArr, 14, bArr.length - 14);
            if ((bArr.length & 1) == 1) {
                byteArrayOutputStream2.write(0);
            }
            writeDWord(byteArrayOutputStream2, 3);
            writeWord(byteArrayOutputStream2, 0);
            byteArrayOutputStream2.close();
            return byteArrayOutputStream2.toByteArray();
        }
        throw new IOException(MessageLocalization.getComposedMessage("only.bmp.can.be.wrapped.in.wmf", new Object[0]));
    }

    public static void writeWord(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >>> 8) & 255);
    }

    public static void writeDWord(OutputStream outputStream, int i) throws IOException {
        writeWord(outputStream, i & 65535);
        writeWord(outputStream, (i >>> 16) & 65535);
    }
}
