package com.itextpdf.awt;

import com.itextpdf.awt.geom.PolylineShape;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ByteBuffer;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPatternPainter;
import com.itextpdf.text.pdf.PdfShading;
import com.itextpdf.text.pdf.PdfShadingPattern;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.RenderableImage;
import java.io.ByteArrayOutputStream;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

public class PdfGraphics2D extends Graphics2D {
    public static final int AFM_DIVISOR = 1000;
    private static final int CLIP = 3;
    private static final int FILL = 1;
    private static final AffineTransform IDENTITY = new AffineTransform();
    private static final int STROKE = 2;
    private float alpha;
    protected Color background;
    protected BaseFont baseFont;
    protected HashMap<String, BaseFont> baseFonts;

    /* renamed from: cb */
    protected PdfContentByte f358cb;
    protected Area clip;
    private Composite composite;
    private boolean convertImagesToJPEG;
    protected int currentFillGState;
    protected int currentStrokeGState;
    private Graphics2D dg2;
    protected boolean disposeCalled;
    protected PdfGState[] fillGState;
    protected Font font;
    protected FontMapper fontMapper;
    protected float fontSize;
    protected float height;
    private float jpegQuality;
    private boolean kid;
    private ArrayList<Kid> kids;
    private MediaTracker mediaTracker;
    private Stroke oldStroke;
    private boolean onlyShapes;
    protected Stroke originalStroke;
    protected Paint paint;
    private Paint paintFill;
    private Paint paintStroke;
    private Paint realPaint;
    protected RenderingHints rhints;
    protected boolean strikethrough;
    protected Stroke stroke;
    protected PdfGState[] strokeGState;
    private BasicStroke strokeOne;
    protected AffineTransform transform;
    protected boolean underline;
    protected float width;

    public static double asPoints(double d, int i) {
        return (d * ((double) i)) / 1000.0d;
    }

    public void copyArea(int i, int i2, int i3, int i4, int i5, int i6) {
    }

    public void setPaintMode() {
    }

    public void setXORMode(Color color) {
    }

    private static final class Kid {
        final PdfGraphics2D graphics;
        final int pos;

        Kid(int i, PdfGraphics2D pdfGraphics2D) {
            this.pos = i;
            this.graphics = pdfGraphics2D;
        }
    }

    private Graphics2D getDG2() {
        if (this.dg2 == null) {
            Graphics2D createGraphics = new BufferedImage(2, 2, 1).createGraphics();
            this.dg2 = createGraphics;
            createGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            setRenderingHint(HyperLinkKey.KEY_INSTANCE, HyperLinkKey.VALUE_HYPERLINKKEY_OFF);
        }
        return this.dg2;
    }

    private PdfGraphics2D() {
        this.strokeOne = new BasicStroke(1.0f);
        this.rhints = new RenderingHints((Map) null);
        this.disposeCalled = false;
        this.kid = false;
        this.onlyShapes = false;
        this.currentFillGState = 255;
        this.currentStrokeGState = 255;
        this.convertImagesToJPEG = false;
        this.jpegQuality = 0.95f;
    }

    public PdfGraphics2D(PdfContentByte pdfContentByte, float f, float f2) {
        this(pdfContentByte, f, f2, (FontMapper) null, false, false, 0.0f);
    }

    public PdfGraphics2D(PdfContentByte pdfContentByte, float f, float f2, FontMapper fontMapper2) {
        this(pdfContentByte, f, f2, fontMapper2, false, false, 0.0f);
    }

    public PdfGraphics2D(PdfContentByte pdfContentByte, float f, float f2, boolean z) {
        this(pdfContentByte, f, f2, (FontMapper) null, z, false, 0.0f);
    }

    public PdfGraphics2D(PdfContentByte pdfContentByte, float f, float f2, FontMapper fontMapper2, boolean z, boolean z2, float f3) {
        this.strokeOne = new BasicStroke(1.0f);
        this.rhints = new RenderingHints((Map) null);
        this.disposeCalled = false;
        this.kid = false;
        this.onlyShapes = false;
        this.currentFillGState = 255;
        this.currentStrokeGState = 255;
        this.convertImagesToJPEG = false;
        this.jpegQuality = 0.95f;
        this.fillGState = new PdfGState[256];
        this.strokeGState = new PdfGState[256];
        this.convertImagesToJPEG = z2;
        this.jpegQuality = f3;
        this.onlyShapes = z;
        this.transform = new AffineTransform();
        this.baseFonts = new HashMap<>();
        if (!z) {
            this.fontMapper = fontMapper2;
            if (fontMapper2 == null) {
                this.fontMapper = new DefaultFontMapper();
            }
        }
        this.paint = Color.black;
        this.background = Color.white;
        setFont(new Font("sanserif", 0, 12));
        this.f358cb = pdfContentByte;
        pdfContentByte.saveState();
        this.width = f;
        this.height = f2;
        Area area = new Area(new Rectangle2D.Float(0.0f, 0.0f, f, f2));
        this.clip = area;
        clip(area);
        BasicStroke basicStroke = this.strokeOne;
        this.oldStroke = basicStroke;
        this.stroke = basicStroke;
        this.originalStroke = basicStroke;
        setStrokeDiff(basicStroke, (Stroke) null);
        pdfContentByte.saveState();
    }

    public void draw(Shape shape) {
        followPath(shape, 2);
    }

    public boolean drawImage(Image image, AffineTransform affineTransform, ImageObserver imageObserver) {
        return drawImage(image, (Image) null, affineTransform, (Color) null, imageObserver);
    }

    public void drawImage(BufferedImage bufferedImage, BufferedImageOp bufferedImageOp, int i, int i2) {
        if (bufferedImageOp != null) {
            bufferedImage = bufferedImageOp.filter(bufferedImage, bufferedImageOp.createCompatibleDestImage(bufferedImage, bufferedImage.getColorModel()));
        }
        drawImage((Image) bufferedImage, i, i2, (ImageObserver) null);
    }

    public void drawRenderedImage(RenderedImage renderedImage, AffineTransform affineTransform) {
        Image image;
        if (renderedImage instanceof BufferedImage) {
            image = (BufferedImage) renderedImage;
        } else {
            ColorModel colorModel = renderedImage.getColorModel();
            WritableRaster createCompatibleWritableRaster = colorModel.createCompatibleWritableRaster(renderedImage.getWidth(), renderedImage.getHeight());
            boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
            Hashtable hashtable = new Hashtable();
            String[] propertyNames = renderedImage.getPropertyNames();
            if (propertyNames != null) {
                for (String str : propertyNames) {
                    hashtable.put(str, renderedImage.getProperty(str));
                }
            }
            Image bufferedImage = new BufferedImage(colorModel, createCompatibleWritableRaster, isAlphaPremultiplied, hashtable);
            renderedImage.copyData(createCompatibleWritableRaster);
            image = bufferedImage;
        }
        drawImage(image, affineTransform, (ImageObserver) null);
    }

    public void drawRenderableImage(RenderableImage renderableImage, AffineTransform affineTransform) {
        drawRenderedImage(renderableImage.createDefaultRendering(), affineTransform);
    }

    public void drawString(String str, int i, int i2) {
        drawString(str, (float) i, (float) i2);
    }

    /* access modifiers changed from: protected */
    public void doAttributes(AttributedCharacterIterator attributedCharacterIterator) {
        this.underline = false;
        this.strikethrough = false;
        for (AttributedCharacterIterator.Attribute next : attributedCharacterIterator.getAttributes().keySet()) {
            if (next instanceof TextAttribute) {
                TextAttribute textAttribute = (TextAttribute) next;
                if (textAttribute.equals(TextAttribute.FONT)) {
                    setFont((Font) attributedCharacterIterator.getAttributes().get(textAttribute));
                } else if (textAttribute.equals(TextAttribute.UNDERLINE)) {
                    if (attributedCharacterIterator.getAttributes().get(textAttribute) == TextAttribute.UNDERLINE_ON) {
                        this.underline = true;
                    }
                } else if (textAttribute.equals(TextAttribute.STRIKETHROUGH)) {
                    if (attributedCharacterIterator.getAttributes().get(textAttribute) == TextAttribute.STRIKETHROUGH_ON) {
                        this.strikethrough = true;
                    }
                } else if (textAttribute.equals(TextAttribute.SIZE)) {
                    Object obj = attributedCharacterIterator.getAttributes().get(textAttribute);
                    if (obj instanceof Integer) {
                        setFont(getFont().deriveFont(getFont().getStyle(), (float) ((Integer) obj).intValue()));
                    } else if (obj instanceof Float) {
                        setFont(getFont().deriveFont(getFont().getStyle(), ((Float) obj).floatValue()));
                    }
                } else if (textAttribute.equals(TextAttribute.FOREGROUND)) {
                    setColor((Color) attributedCharacterIterator.getAttributes().get(textAttribute));
                } else if (textAttribute.equals(TextAttribute.FAMILY)) {
                    Font font2 = getFont();
                    Map attributes = font2.getAttributes();
                    attributes.put(TextAttribute.FAMILY, attributedCharacterIterator.getAttributes().get(textAttribute));
                    setFont(font2.deriveFont(attributes));
                } else if (textAttribute.equals(TextAttribute.POSTURE)) {
                    Font font3 = getFont();
                    Map attributes2 = font3.getAttributes();
                    attributes2.put(TextAttribute.POSTURE, attributedCharacterIterator.getAttributes().get(textAttribute));
                    setFont(font3.deriveFont(attributes2));
                } else if (textAttribute.equals(TextAttribute.WEIGHT)) {
                    Font font4 = getFont();
                    Map attributes3 = font4.getAttributes();
                    attributes3.put(TextAttribute.WEIGHT, attributedCharacterIterator.getAttributes().get(textAttribute));
                    setFont(font4.deriveFont(attributes3));
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x01e7  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x027d  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0288  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x02a9  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x02b9  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x02c2  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x02d6  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x030c  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0314  */
    /* JADX WARNING: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawString(java.lang.String r33, float r34, float r35) {
        /*
            r32 = this;
            r0 = r32
            r1 = r33
            r2 = r34
            r3 = r35
            int r4 = r33.length()
            if (r4 != 0) goto L_0x000f
            return
        L_0x000f:
            r32.setFillPaint()
            boolean r4 = r0.onlyShapes
            if (r4 == 0) goto L_0x002f
            java.awt.Font r5 = r0.font
            java.awt.font.FontRenderContext r6 = r32.getFontRenderContext()
            char[] r7 = r33.toCharArray()
            r8 = 0
            int r9 = r33.length()
            r10 = 0
            java.awt.font.GlyphVector r1 = r5.layoutGlyphVector(r6, r7, r8, r9, r10)
            r0.drawGlyphVector(r1, r2, r3)
            goto L_0x034e
        L_0x002f:
            java.awt.geom.AffineTransform r4 = r32.getTransform()
            java.awt.geom.AffineTransform r5 = r32.getTransform()
            double r13 = (double) r2
            double r2 = (double) r3
            r5.translate(r13, r2)
            java.awt.Font r6 = r0.font
            java.awt.geom.AffineTransform r6 = r6.getTransform()
            r5.concatenate(r6)
            r0.setTransform(r5)
            java.awt.geom.AffineTransform r5 = r32.normalizeMatrix()
            r6 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r8 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            java.awt.geom.AffineTransform r6 = java.awt.geom.AffineTransform.getScaleInstance(r6, r8)
            r5.concatenate(r6)
            com.itextpdf.text.pdf.PdfContentByte r6 = r0.f358cb
            r6.beginText()
            com.itextpdf.text.pdf.PdfContentByte r6 = r0.f358cb
            com.itextpdf.text.pdf.BaseFont r7 = r0.baseFont
            float r8 = r0.fontSize
            r6.setFontAndSize(r7, r8)
            java.awt.Font r6 = r0.font
            boolean r6 = r6.isItalic()
            r7 = 4
            r8 = 1148846080(0x447a0000, float:1000.0)
            r9 = 0
            if (r6 == 0) goto L_0x00d0
            com.itextpdf.text.pdf.BaseFont r6 = r0.baseFont
            float r6 = r6.getFontDescriptor(r7, r8)
            java.awt.Font r10 = r0.font
            float r10 = r10.getItalicAngle()
            java.awt.Font r11 = r0.font
            java.lang.String r11 = r11.getFontName()
            java.awt.Font r12 = r0.font
            java.lang.String r12 = r12.getName()
            boolean r11 = r11.equals(r12)
            if (r11 != 0) goto L_0x0097
            int r11 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r11 != 0) goto L_0x00d0
            int r11 = (r10 > r9 ? 1 : (r10 == r9 ? 0 : -1))
            if (r11 != 0) goto L_0x00d0
        L_0x0097:
            int r11 = (r10 > r9 ? 1 : (r10 == r9 ? 0 : -1))
            if (r11 != 0) goto L_0x009e
            r10 = 1092616192(0x41200000, float:10.0)
            goto L_0x009f
        L_0x009e:
            float r10 = -r10
        L_0x009f:
            int r6 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r6 != 0) goto L_0x00d0
            java.awt.geom.AffineTransform r6 = new java.awt.geom.AffineTransform
            r6.<init>()
            r16 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r18 = 0
            double r10 = (double) r10
            r20 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r10 = r10 * r20
            r20 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r10 = r10 / r20
            double r10 = java.lang.Math.tan(r10)
            float r10 = (float) r10
            double r10 = (double) r10
            r22 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r24 = 0
            r26 = 0
            r15 = r6
            r20 = r10
            r15.setTransform(r16, r18, r20, r22, r24, r26)
            r5.concatenate(r6)
        L_0x00d0:
            r6 = 6
            double[] r6 = new double[r6]
            r5.getMatrix(r6)
            com.itextpdf.text.pdf.PdfContentByte r15 = r0.f358cb
            r5 = 0
            r10 = r6[r5]
            float r10 = (float) r10
            r11 = 1
            r8 = r6[r11]
            float r8 = (float) r8
            r9 = 2
            r11 = r6[r9]
            float r11 = (float) r11
            r12 = 3
            r16 = r10
            r9 = r6[r12]
            float r9 = (float) r9
            r24 = r13
            r12 = r6[r7]
            float r7 = (float) r12
            r10 = 5
            r12 = r6[r10]
            float r6 = (float) r12
            r17 = r8
            r18 = r11
            r19 = r9
            r20 = r7
            r21 = r6
            r15.setTextMatrix(r16, r17, r18, r19, r20, r21)
            java.awt.Font r6 = r0.font
            java.util.Map r6 = r6.getAttributes()
            java.awt.font.TextAttribute r7 = java.awt.font.TextAttribute.WIDTH
            java.lang.Object r6 = r6.get(r7)
            java.lang.Float r6 = (java.lang.Float) r6
            if (r6 != 0) goto L_0x0112
            java.lang.Float r6 = java.awt.font.TextAttribute.WIDTH_REGULAR
        L_0x0112:
            java.lang.Float r7 = java.awt.font.TextAttribute.WIDTH_REGULAR
            boolean r7 = r7.equals(r6)
            r8 = 1120403456(0x42c80000, float:100.0)
            if (r7 != 0) goto L_0x0127
            com.itextpdf.text.pdf.PdfContentByte r7 = r0.f358cb
            float r9 = r6.floatValue()
            float r9 = r8 / r9
            r7.setHorizontalScaling(r9)
        L_0x0127:
            com.itextpdf.text.pdf.BaseFont r7 = r0.baseFont
            java.lang.String r7 = r7.getPostscriptFontName()
            java.lang.String r7 = r7.toLowerCase()
            java.lang.String r9 = "bold"
            int r7 = r7.indexOf(r9)
            if (r7 >= 0) goto L_0x01d9
            java.awt.Font r7 = r0.font
            java.util.Map r7 = r7.getAttributes()
            java.awt.font.TextAttribute r9 = java.awt.font.TextAttribute.WEIGHT
            java.lang.Object r7 = r7.get(r9)
            java.lang.Float r7 = (java.lang.Float) r7
            if (r7 != 0) goto L_0x0156
            java.awt.Font r7 = r0.font
            boolean r7 = r7.isBold()
            if (r7 == 0) goto L_0x0154
            java.lang.Float r7 = java.awt.font.TextAttribute.WEIGHT_BOLD
            goto L_0x0156
        L_0x0154:
            java.lang.Float r7 = java.awt.font.TextAttribute.WEIGHT_REGULAR
        L_0x0156:
            java.awt.Font r9 = r0.font
            boolean r9 = r9.isBold()
            if (r9 == 0) goto L_0x01d9
            float r9 = r7.floatValue()
            java.lang.Float r10 = java.awt.font.TextAttribute.WEIGHT_SEMIBOLD
            float r10 = r10.floatValue()
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 >= 0) goto L_0x017e
            java.awt.Font r9 = r0.font
            java.lang.String r9 = r9.getFontName()
            java.awt.Font r10 = r0.font
            java.lang.String r10 = r10.getName()
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x01d9
        L_0x017e:
            java.awt.Font r9 = r0.font
            float r9 = r9.getSize2D()
            float r7 = r7.floatValue()
            java.lang.Float r10 = java.awt.font.TextAttribute.WEIGHT_REGULAR
            float r10 = r10.floatValue()
            float r7 = r7 - r10
            float r9 = r9 * r7
            r7 = 1101004800(0x41a00000, float:20.0)
            float r9 = r9 / r7
            java.awt.Paint r7 = r0.realPaint
            boolean r7 = r7 instanceof java.awt.Color
            if (r7 == 0) goto L_0x01d9
            com.itextpdf.text.pdf.PdfContentByte r7 = r0.f358cb
            r10 = 2
            r7.setTextRenderingMode(r10)
            com.itextpdf.text.pdf.PdfContentByte r7 = r0.f358cb
            r7.setLineWidth((float) r9)
            java.awt.Paint r7 = r0.realPaint
            java.awt.Color r7 = (java.awt.Color) r7
            int r9 = r7.getAlpha()
            int r10 = r0.currentStrokeGState
            if (r9 == r10) goto L_0x01ce
            r0.currentStrokeGState = r9
            com.itextpdf.text.pdf.PdfGState[] r10 = r0.strokeGState
            r10 = r10[r9]
            if (r10 != 0) goto L_0x01c9
            com.itextpdf.text.pdf.PdfGState r10 = new com.itextpdf.text.pdf.PdfGState
            r10.<init>()
            float r11 = (float) r9
            r12 = 1132396544(0x437f0000, float:255.0)
            float r11 = r11 / r12
            r10.setStrokeOpacity(r11)
            com.itextpdf.text.pdf.PdfGState[] r11 = r0.strokeGState
            r11[r9] = r10
        L_0x01c9:
            com.itextpdf.text.pdf.PdfContentByte r9 = r0.f358cb
            r9.setGState(r10)
        L_0x01ce:
            com.itextpdf.text.pdf.PdfContentByte r9 = r0.f358cb
            com.itextpdf.text.BaseColor r7 = prepareColor(r7)
            r9.setColorStroke(r7)
            r7 = 1
            goto L_0x01da
        L_0x01d9:
            r7 = 0
        L_0x01da:
            r9 = 0
            java.awt.Font r11 = r0.font
            float r11 = r11.getSize2D()
            r12 = 0
            int r11 = (r11 > r12 ? 1 : (r11 == r12 ? 0 : -1))
            if (r11 <= 0) goto L_0x0210
            java.awt.Font r9 = r0.font
            float r9 = r9.getSize2D()
            r10 = 1148846080(0x447a0000, float:1000.0)
            float r9 = r10 / r9
            java.awt.Font r10 = r0.font
            double r11 = (double) r9
            java.awt.geom.AffineTransform r9 = java.awt.geom.AffineTransform.getScaleInstance(r11, r11)
            java.awt.Font r9 = r10.deriveFont(r9)
            java.awt.font.FontRenderContext r10 = r32.getFontRenderContext()
            java.awt.geom.Rectangle2D r10 = r9.getStringBounds(r1, r10)
            double r13 = r10.getWidth()
            boolean r9 = r9.isTransformed()
            if (r9 == 0) goto L_0x0211
            double r9 = r13 / r11
        L_0x0210:
            r13 = r9
        L_0x0211:
            com.itextpdf.awt.PdfGraphics2D$HyperLinkKey r9 = com.itextpdf.awt.PdfGraphics2D.HyperLinkKey.KEY_INSTANCE
            java.lang.Object r9 = r0.getRenderingHint(r9)
            if (r9 == 0) goto L_0x027d
            java.lang.Object r10 = com.itextpdf.awt.PdfGraphics2D.HyperLinkKey.VALUE_HYPERLINKKEY_OFF
            boolean r10 = r9.equals(r10)
            if (r10 != 0) goto L_0x027d
            java.awt.Font r10 = r0.font
            float r10 = r10.getSize2D()
            r11 = 1148846080(0x447a0000, float:1000.0)
            float r10 = r11 / r10
            java.awt.Font r11 = r0.font
            r12 = r6
            double r5 = (double) r10
            java.awt.geom.AffineTransform r10 = java.awt.geom.AffineTransform.getScaleInstance(r5, r5)
            java.awt.Font r10 = r11.deriveFont(r10)
            java.awt.font.FontRenderContext r11 = r32.getFontRenderContext()
            java.awt.geom.Rectangle2D r11 = r10.getStringBounds(r1, r11)
            double r15 = r11.getHeight()
            boolean r10 = r10.isTransformed()
            if (r10 == 0) goto L_0x024a
            double r15 = r15 / r5
        L_0x024a:
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.f358cb
            float r5 = r5.getXTLM()
            double r5 = (double) r5
            com.itextpdf.text.pdf.PdfContentByte r10 = r0.f358cb
            float r10 = r10.getYTLM()
            double r10 = (double) r10
            com.itextpdf.text.pdf.PdfAction r8 = new com.itextpdf.text.pdf.PdfAction
            java.lang.String r9 = r9.toString()
            r8.<init>((java.lang.String) r9)
            com.itextpdf.text.pdf.PdfContentByte r9 = r0.f358cb
            r18 = r12
            float r12 = (float) r5
            r19 = r2
            float r2 = (float) r10
            double r5 = r5 + r13
            float r3 = (float) r5
            double r10 = r10 + r15
            float r5 = (float) r10
            r26 = r9
            r27 = r8
            r28 = r12
            r29 = r2
            r30 = r3
            r31 = r5
            r26.setAction(r27, r28, r29, r30, r31)
            goto L_0x0281
        L_0x027d:
            r19 = r2
            r18 = r6
        L_0x0281:
            int r2 = r33.length()
            r3 = 1
            if (r2 <= r3) goto L_0x029e
            float r2 = (float) r13
            com.itextpdf.text.pdf.BaseFont r5 = r0.baseFont
            float r6 = r0.fontSize
            float r5 = r5.getWidthPoint((java.lang.String) r1, (float) r6)
            float r2 = r2 - r5
            int r5 = r33.length()
            int r5 = r5 - r3
            float r5 = (float) r5
            float r2 = r2 / r5
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.f358cb
            r5.setCharacterSpacing(r2)
        L_0x029e:
            com.itextpdf.text.pdf.PdfContentByte r2 = r0.f358cb
            r2.showText((java.lang.String) r1)
            int r1 = r33.length()
            if (r1 <= r3) goto L_0x02af
            com.itextpdf.text.pdf.PdfContentByte r1 = r0.f358cb
            r2 = 0
            r1.setCharacterSpacing(r2)
        L_0x02af:
            java.lang.Float r1 = java.awt.font.TextAttribute.WIDTH_REGULAR
            r6 = r18
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x02c0
            com.itextpdf.text.pdf.PdfContentByte r1 = r0.f358cb
            r2 = 1120403456(0x42c80000, float:100.0)
            r1.setHorizontalScaling(r2)
        L_0x02c0:
            if (r7 == 0) goto L_0x02c8
            com.itextpdf.text.pdf.PdfContentByte r1 = r0.f358cb
            r2 = 0
            r1.setTextRenderingMode(r2)
        L_0x02c8:
            com.itextpdf.text.pdf.PdfContentByte r1 = r0.f358cb
            r1.endText()
            r0.setTransform(r4)
            boolean r1 = r0.underline
            r2 = 50
            if (r1 == 0) goto L_0x030c
            double r3 = (double) r2
            float r1 = r0.fontSize
            int r1 = (int) r1
            double r3 = asPoints(r3, r1)
            java.awt.Stroke r1 = r0.originalStroke
            java.awt.BasicStroke r5 = new java.awt.BasicStroke
            float r6 = (float) r3
            r5.<init>(r6)
            r0.setStroke(r5)
            r5 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r3 = r3 * r5
            double r3 = r19 + r3
            float r3 = (float) r3
            java.awt.geom.Line2D$Double r4 = new java.awt.geom.Line2D$Double
            double r11 = (double) r3
            double r15 = r13 + r24
            r6 = r4
            r7 = r24
            r9 = r11
            r17 = r11
            r11 = r15
            r21 = r13
            r15 = r24
            r13 = r17
            r6.<init>(r7, r9, r11, r13)
            r0.draw(r4)
            r0.setStroke(r1)
            goto L_0x0310
        L_0x030c:
            r21 = r13
            r15 = r24
        L_0x0310:
            boolean r1 = r0.strikethrough
            if (r1 == 0) goto L_0x034e
            r1 = 350(0x15e, float:4.9E-43)
            double r2 = (double) r2
            float r4 = r0.fontSize
            int r4 = (int) r4
            double r4 = asPoints(r2, r4)
            double r6 = (double) r1
            float r1 = r0.fontSize
            int r1 = (int) r1
            double r6 = asPoints(r6, r1)
            java.awt.Stroke r1 = r0.originalStroke
            java.awt.BasicStroke r8 = new java.awt.BasicStroke
            float r4 = (float) r4
            r8.<init>(r4)
            r0.setStroke(r8)
            float r4 = r0.fontSize
            int r4 = (int) r4
            double r2 = asPoints(r2, r4)
            double r2 = r19 + r2
            float r2 = (float) r2
            java.awt.geom.Line2D$Double r3 = new java.awt.geom.Line2D$Double
            double r4 = (double) r2
            double r13 = r4 - r6
            double r11 = r21 + r15
            r6 = r3
            r7 = r15
            r9 = r13
            r6.<init>(r7, r9, r11, r13)
            r0.draw(r3)
            r0.setStroke(r1)
        L_0x034e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.awt.PdfGraphics2D.drawString(java.lang.String, float, float):void");
    }

    public void drawString(AttributedCharacterIterator attributedCharacterIterator, int i, int i2) {
        drawString(attributedCharacterIterator, (float) i, (float) i2);
    }

    public void drawString(AttributedCharacterIterator attributedCharacterIterator, float f, float f2) {
        StringBuffer stringBuffer = new StringBuffer(attributedCharacterIterator.getEndIndex());
        char first = attributedCharacterIterator.first();
        while (first != 65535) {
            if (attributedCharacterIterator.getIndex() == attributedCharacterIterator.getRunStart()) {
                if (stringBuffer.length() > 0) {
                    drawString(stringBuffer.toString(), f, f2);
                    f = (float) (((double) f) + getFontMetrics().getStringBounds(stringBuffer.toString(), this).getWidth());
                    stringBuffer.delete(0, stringBuffer.length());
                }
                doAttributes(attributedCharacterIterator);
            }
            stringBuffer.append(first);
            first = attributedCharacterIterator.next();
        }
        drawString(stringBuffer.toString(), f, f2);
        this.underline = false;
        this.strikethrough = false;
    }

    public void drawGlyphVector(GlyphVector glyphVector, float f, float f2) {
        fill(glyphVector.getOutline(f, f2));
    }

    public void fill(Shape shape) {
        followPath(shape, 1);
    }

    public boolean hit(Rectangle rectangle, Shape shape, boolean z) {
        if (z) {
            shape = this.stroke.createStrokedShape(shape);
        }
        Area area = new Area(this.transform.createTransformedShape(shape));
        Area area2 = this.clip;
        if (area2 != null) {
            area.intersect(area2);
        }
        return area.intersects((double) rectangle.x, (double) rectangle.y, (double) rectangle.width, (double) rectangle.height);
    }

    public GraphicsConfiguration getDeviceConfiguration() {
        return getDG2().getDeviceConfiguration();
    }

    public void setComposite(Composite composite2) {
        if (composite2 instanceof AlphaComposite) {
            AlphaComposite alphaComposite = (AlphaComposite) composite2;
            if (alphaComposite.getRule() == 3) {
                this.alpha = alphaComposite.getAlpha();
                this.composite = alphaComposite;
                Color color = this.realPaint;
                if (color != null && (color instanceof Color)) {
                    Color color2 = color;
                    this.paint = new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), (int) (((float) color2.getAlpha()) * this.alpha));
                    return;
                }
                return;
            }
        }
        this.composite = composite2;
        this.alpha = 1.0f;
    }

    public void setPaint(Paint paint2) {
        if (paint2 != null) {
            this.paint = paint2;
            this.realPaint = paint2;
            AlphaComposite alphaComposite = this.composite;
            if ((alphaComposite instanceof AlphaComposite) && (paint2 instanceof Color) && alphaComposite.getRule() == 3) {
                Color color = (Color) paint2;
                this.paint = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (((float) color.getAlpha()) * this.alpha));
                this.realPaint = paint2;
            }
        }
    }

    private Stroke transformStroke(Stroke stroke2) {
        if (!(stroke2 instanceof BasicStroke)) {
            return stroke2;
        }
        BasicStroke basicStroke = (BasicStroke) stroke2;
        float sqrt = (float) Math.sqrt(Math.abs(this.transform.getDeterminant()));
        float[] dashArray = basicStroke.getDashArray();
        if (dashArray != null) {
            for (int i = 0; i < dashArray.length; i++) {
                dashArray[i] = dashArray[i] * sqrt;
            }
        }
        return new BasicStroke(basicStroke.getLineWidth() * sqrt, basicStroke.getEndCap(), basicStroke.getLineJoin(), basicStroke.getMiterLimit(), dashArray, basicStroke.getDashPhase() * sqrt);
    }

    private void setStrokeDiff(Stroke stroke2, Stroke stroke3) {
        if (stroke2 != stroke3 && (stroke2 instanceof BasicStroke)) {
            BasicStroke basicStroke = (BasicStroke) stroke2;
            boolean z = stroke3 instanceof BasicStroke;
            BasicStroke basicStroke2 = null;
            if (z) {
                basicStroke2 = (BasicStroke) stroke3;
            }
            if (!z || basicStroke.getLineWidth() != basicStroke2.getLineWidth()) {
                this.f358cb.setLineWidth(basicStroke.getLineWidth());
            }
            boolean z2 = true;
            if (!z || basicStroke.getEndCap() != basicStroke2.getEndCap()) {
                int endCap = basicStroke.getEndCap();
                if (endCap == 0) {
                    this.f358cb.setLineCap(0);
                } else if (endCap != 2) {
                    this.f358cb.setLineCap(1);
                } else {
                    this.f358cb.setLineCap(2);
                }
            }
            if (!z || basicStroke.getLineJoin() != basicStroke2.getLineJoin()) {
                int lineJoin = basicStroke.getLineJoin();
                if (lineJoin == 0) {
                    this.f358cb.setLineJoin(0);
                } else if (lineJoin != 2) {
                    this.f358cb.setLineJoin(1);
                } else {
                    this.f358cb.setLineJoin(2);
                }
            }
            if (!z || basicStroke.getMiterLimit() != basicStroke2.getMiterLimit()) {
                this.f358cb.setMiterLimit(basicStroke.getMiterLimit());
            }
            if (z && (basicStroke.getDashArray() == null ? basicStroke2.getDashArray() == null : basicStroke.getDashPhase() == basicStroke2.getDashPhase() && Arrays.equals(basicStroke.getDashArray(), basicStroke2.getDashArray()))) {
                z2 = false;
            }
            if (z2) {
                float[] dashArray = basicStroke.getDashArray();
                if (dashArray == null) {
                    this.f358cb.setLiteral("[]0 d\n");
                    return;
                }
                this.f358cb.setLiteral('[');
                for (float literal : dashArray) {
                    this.f358cb.setLiteral(literal);
                    this.f358cb.setLiteral(' ');
                }
                this.f358cb.setLiteral(']');
                this.f358cb.setLiteral(basicStroke.getDashPhase());
                this.f358cb.setLiteral(" d\n");
            }
        }
    }

    public void setStroke(Stroke stroke2) {
        this.originalStroke = stroke2;
        this.stroke = transformStroke(stroke2);
    }

    public void setRenderingHint(RenderingHints.Key key, Object obj) {
        if (obj != null) {
            this.rhints.put(key, obj);
        } else if (key instanceof HyperLinkKey) {
            this.rhints.put(key, HyperLinkKey.VALUE_HYPERLINKKEY_OFF);
        } else {
            this.rhints.remove(key);
        }
    }

    public Object getRenderingHint(RenderingHints.Key key) {
        return this.rhints.get(key);
    }

    public void setRenderingHints(Map<?, ?> map) {
        this.rhints.clear();
        this.rhints.putAll(map);
    }

    public void addRenderingHints(Map<?, ?> map) {
        this.rhints.putAll(map);
    }

    public RenderingHints getRenderingHints() {
        return this.rhints;
    }

    public void translate(int i, int i2) {
        translate((double) i, (double) i2);
    }

    public void translate(double d, double d2) {
        this.transform.translate(d, d2);
    }

    public void rotate(double d) {
        this.transform.rotate(d);
    }

    public void rotate(double d, double d2, double d3) {
        this.transform.rotate(d, d2, d3);
    }

    public void scale(double d, double d2) {
        this.transform.scale(d, d2);
        this.stroke = transformStroke(this.originalStroke);
    }

    public void shear(double d, double d2) {
        this.transform.shear(d, d2);
    }

    public void transform(AffineTransform affineTransform) {
        this.transform.concatenate(affineTransform);
        this.stroke = transformStroke(this.originalStroke);
    }

    public void setTransform(AffineTransform affineTransform) {
        this.transform = new AffineTransform(affineTransform);
        this.stroke = transformStroke(this.originalStroke);
    }

    public AffineTransform getTransform() {
        return new AffineTransform(this.transform);
    }

    public Paint getPaint() {
        Paint paint2 = this.realPaint;
        if (paint2 != null) {
            return paint2;
        }
        return this.paint;
    }

    public Composite getComposite() {
        return this.composite;
    }

    public void setBackground(Color color) {
        this.background = color;
    }

    public Color getBackground() {
        return this.background;
    }

    public Stroke getStroke() {
        return this.originalStroke;
    }

    public FontRenderContext getFontRenderContext() {
        return new FontRenderContext(new AffineTransform(), RenderingHints.VALUE_TEXT_ANTIALIAS_ON.equals(getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING)), RenderingHints.VALUE_FRACTIONALMETRICS_ON.equals(getRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS)));
    }

    public Graphics create() {
        PdfGraphics2D pdfGraphics2D = new PdfGraphics2D();
        pdfGraphics2D.rhints.putAll(this.rhints);
        pdfGraphics2D.onlyShapes = this.onlyShapes;
        pdfGraphics2D.transform = new AffineTransform(this.transform);
        pdfGraphics2D.baseFonts = this.baseFonts;
        pdfGraphics2D.fontMapper = this.fontMapper;
        pdfGraphics2D.paint = this.paint;
        pdfGraphics2D.fillGState = this.fillGState;
        pdfGraphics2D.currentFillGState = this.currentFillGState;
        pdfGraphics2D.strokeGState = this.strokeGState;
        pdfGraphics2D.background = this.background;
        pdfGraphics2D.mediaTracker = this.mediaTracker;
        pdfGraphics2D.convertImagesToJPEG = this.convertImagesToJPEG;
        pdfGraphics2D.jpegQuality = this.jpegQuality;
        pdfGraphics2D.setFont(this.font);
        PdfContentByte duplicate = this.f358cb.getDuplicate();
        pdfGraphics2D.f358cb = duplicate;
        duplicate.saveState();
        pdfGraphics2D.width = this.width;
        pdfGraphics2D.height = this.height;
        pdfGraphics2D.followPath(new Area(new Rectangle2D.Float(0.0f, 0.0f, this.width, this.height)), 3);
        if (this.clip != null) {
            pdfGraphics2D.clip = new Area(this.clip);
        }
        pdfGraphics2D.composite = this.composite;
        pdfGraphics2D.stroke = this.stroke;
        pdfGraphics2D.originalStroke = this.originalStroke;
        BasicStroke transformStroke = pdfGraphics2D.transformStroke(pdfGraphics2D.strokeOne);
        pdfGraphics2D.strokeOne = transformStroke;
        pdfGraphics2D.oldStroke = transformStroke;
        pdfGraphics2D.setStrokeDiff(transformStroke, (Stroke) null);
        pdfGraphics2D.f358cb.saveState();
        Area area = pdfGraphics2D.clip;
        if (area != null) {
            pdfGraphics2D.followPath(area, 3);
        }
        pdfGraphics2D.kid = true;
        if (this.kids == null) {
            this.kids = new ArrayList<>();
        }
        this.kids.add(new Kid(this.f358cb.getInternalBuffer().size(), pdfGraphics2D));
        return pdfGraphics2D;
    }

    public PdfContentByte getContent() {
        return this.f358cb;
    }

    public Color getColor() {
        Color color = this.paint;
        if (color instanceof Color) {
            return color;
        }
        return Color.black;
    }

    public void setColor(Color color) {
        setPaint(color);
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font2) {
        if (font2 != null) {
            if (this.onlyShapes) {
                this.font = font2;
            } else if (font2 != this.font) {
                this.font = font2;
                this.fontSize = font2.getSize2D();
                this.baseFont = getCachedBaseFont(font2);
            }
        }
    }

    private BaseFont getCachedBaseFont(Font font2) {
        BaseFont baseFont2;
        synchronized (this.baseFonts) {
            baseFont2 = this.baseFonts.get(font2.getFontName());
            if (baseFont2 == null) {
                baseFont2 = this.fontMapper.awtToPdf(font2);
                this.baseFonts.put(font2.getFontName(), baseFont2);
            }
        }
        return baseFont2;
    }

    public FontMetrics getFontMetrics(Font font2) {
        return getDG2().getFontMetrics(font2);
    }

    public Rectangle getClipBounds() {
        if (this.clip == null) {
            return null;
        }
        return getClip().getBounds();
    }

    public void clipRect(int i, int i2, int i3, int i4) {
        clip(new Rectangle2D.Double((double) i, (double) i2, (double) i3, (double) i4));
    }

    public void setClip(int i, int i2, int i3, int i4) {
        setClip(new Rectangle2D.Double((double) i, (double) i2, (double) i3, (double) i4));
    }

    public void clip(Shape shape) {
        if (shape == null) {
            setClip((Shape) null);
            return;
        }
        Shape createTransformedShape = this.transform.createTransformedShape(shape);
        Area area = this.clip;
        if (area == null) {
            this.clip = new Area(createTransformedShape);
        } else {
            area.intersect(new Area(createTransformedShape));
        }
        followPath(createTransformedShape, 3);
    }

    public Shape getClip() {
        try {
            return this.transform.createInverse().createTransformedShape(this.clip);
        } catch (NoninvertibleTransformException unused) {
            return null;
        }
    }

    public void setClip(Shape shape) {
        this.f358cb.restoreState();
        this.f358cb.saveState();
        if (shape != null) {
            shape = this.transform.createTransformedShape(shape);
        }
        if (shape == null) {
            this.clip = null;
        } else {
            this.clip = new Area(shape);
            followPath(shape, 3);
        }
        this.paintStroke = null;
        this.paintFill = null;
        this.currentStrokeGState = -1;
        this.currentFillGState = -1;
        this.oldStroke = this.strokeOne;
    }

    public void drawLine(int i, int i2, int i3, int i4) {
        draw(new Line2D.Double((double) i, (double) i2, (double) i3, (double) i4));
    }

    public void drawRect(int i, int i2, int i3, int i4) {
        draw(new Rectangle(i, i2, i3, i4));
    }

    public void fillRect(int i, int i2, int i3, int i4) {
        fill(new Rectangle(i, i2, i3, i4));
    }

    public void clearRect(int i, int i2, int i3, int i4) {
        Paint paint2 = this.paint;
        setPaint(this.background);
        fillRect(i, i2, i3, i4);
        setPaint(paint2);
    }

    public void drawRoundRect(int i, int i2, int i3, int i4, int i5, int i6) {
        draw(new RoundRectangle2D.Double((double) i, (double) i2, (double) i3, (double) i4, (double) i5, (double) i6));
    }

    public void fillRoundRect(int i, int i2, int i3, int i4, int i5, int i6) {
        fill(new RoundRectangle2D.Double((double) i, (double) i2, (double) i3, (double) i4, (double) i5, (double) i6));
    }

    public void drawOval(int i, int i2, int i3, int i4) {
        draw(new Ellipse2D.Float((float) i, (float) i2, (float) i3, (float) i4));
    }

    public void fillOval(int i, int i2, int i3, int i4) {
        fill(new Ellipse2D.Float((float) i, (float) i2, (float) i3, (float) i4));
    }

    public void drawArc(int i, int i2, int i3, int i4, int i5, int i6) {
        draw(new Arc2D.Double((double) i, (double) i2, (double) i3, (double) i4, (double) i5, (double) i6, 0));
    }

    public void fillArc(int i, int i2, int i3, int i4, int i5, int i6) {
        fill(new Arc2D.Double((double) i, (double) i2, (double) i3, (double) i4, (double) i5, (double) i6, 2));
    }

    public void drawPolyline(int[] iArr, int[] iArr2, int i) {
        draw(new PolylineShape(iArr, iArr2, i));
    }

    public void drawPolygon(int[] iArr, int[] iArr2, int i) {
        draw(new Polygon(iArr, iArr2, i));
    }

    public void fillPolygon(int[] iArr, int[] iArr2, int i) {
        Polygon polygon = new Polygon();
        for (int i2 = 0; i2 < i; i2++) {
            polygon.addPoint(iArr[i2], iArr2[i2]);
        }
        fill(polygon);
    }

    public boolean drawImage(Image image, int i, int i2, ImageObserver imageObserver) {
        return drawImage(image, i, i2, (Color) null, imageObserver);
    }

    public boolean drawImage(Image image, int i, int i2, int i3, int i4, ImageObserver imageObserver) {
        return drawImage(image, i, i2, i3, i4, (Color) null, imageObserver);
    }

    public boolean drawImage(Image image, int i, int i2, Color color, ImageObserver imageObserver) {
        waitForImage(image);
        return drawImage(image, i, i2, image.getWidth(imageObserver), image.getHeight(imageObserver), color, imageObserver);
    }

    public boolean drawImage(Image image, int i, int i2, int i3, int i4, Color color, ImageObserver imageObserver) {
        ImageObserver imageObserver2 = imageObserver;
        waitForImage(image);
        double width2 = ((double) i3) / ((double) image.getWidth(imageObserver2));
        double height2 = ((double) i4) / ((double) image.getHeight(imageObserver2));
        AffineTransform translateInstance = AffineTransform.getTranslateInstance((double) i, (double) i2);
        translateInstance.scale(width2, height2);
        return drawImage(image, (Image) null, translateInstance, color, imageObserver2);
    }

    public boolean drawImage(Image image, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ImageObserver imageObserver) {
        return drawImage(image, i, i2, i3, i4, i5, i6, i7, i8, (Color) null, imageObserver);
    }

    public boolean drawImage(Image image, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Color color, ImageObserver imageObserver) {
        Image image2 = image;
        int i9 = i6;
        ImageObserver imageObserver2 = imageObserver;
        waitForImage(image);
        double d = (double) i;
        double d2 = ((double) i3) - d;
        double d3 = (double) i2;
        double d4 = ((double) i4) - d3;
        double d5 = (double) i5;
        double d6 = ((double) i7) - d5;
        double d7 = d3;
        double d8 = (double) i9;
        double d9 = ((double) i8) - d8;
        if (!(d2 == 0.0d || d4 == 0.0d || d6 == 0.0d || d9 == 0.0d)) {
            double d10 = d2 / d6;
            double d11 = d4 / d9;
            AffineTransform translateInstance = AffineTransform.getTranslateInstance(d - (d5 * d10), d7 - (d8 * d11));
            translateInstance.scale(d10, d11);
            Image image3 = image;
            BufferedImage bufferedImage = new BufferedImage(image3.getWidth(imageObserver2), image3.getHeight(imageObserver2), 12);
            Graphics graphics = bufferedImage.getGraphics();
            graphics.fillRect(i5, i9, (int) d6, (int) d9);
            drawImage(image, (Image) bufferedImage, translateInstance, (Color) null, imageObserver);
            graphics.dispose();
        }
        return true;
    }

    public void dispose() {
        if (!this.kid && !this.disposeCalled) {
            this.disposeCalled = true;
            this.f358cb.restoreState();
            this.f358cb.restoreState();
            Graphics2D graphics2D = this.dg2;
            if (graphics2D != null) {
                graphics2D.dispose();
                this.dg2 = null;
            }
            if (this.kids != null) {
                ByteBuffer byteBuffer = new ByteBuffer();
                internalDispose(byteBuffer);
                ByteBuffer internalBuffer = this.f358cb.getInternalBuffer();
                internalBuffer.reset();
                internalBuffer.append(byteBuffer);
            }
        }
    }

    private void internalDispose(ByteBuffer byteBuffer) {
        ByteBuffer internalBuffer = this.f358cb.getInternalBuffer();
        ArrayList<Kid> arrayList = this.kids;
        int i = 0;
        if (arrayList != null) {
            Iterator<Kid> it = arrayList.iterator();
            while (it.hasNext()) {
                Kid next = it.next();
                int i2 = next.pos;
                PdfGraphics2D pdfGraphics2D = next.graphics;
                pdfGraphics2D.f358cb.restoreState();
                pdfGraphics2D.f358cb.restoreState();
                byteBuffer.append(internalBuffer.getBuffer(), i, i2 - i);
                Graphics2D graphics2D = pdfGraphics2D.dg2;
                if (graphics2D != null) {
                    graphics2D.dispose();
                    pdfGraphics2D.dg2 = null;
                }
                pdfGraphics2D.internalDispose(byteBuffer);
                i = i2;
            }
        }
        byteBuffer.append(internalBuffer.getBuffer(), i, internalBuffer.size() - i);
    }

    private void followPath(Shape shape, int i) {
        PathIterator pathIterator;
        Shape shape2 = shape;
        int i2 = i;
        if (shape2 != null) {
            if (i2 == 2) {
                Stroke stroke2 = this.stroke;
                if (!(stroke2 instanceof BasicStroke)) {
                    followPath(stroke2.createStrokedShape(shape2), 1);
                    return;
                }
            }
            if (i2 == 2) {
                setStrokeDiff(this.stroke, this.oldStroke);
                this.oldStroke = this.stroke;
                setStrokePaint();
            } else if (i2 == 1) {
                setFillPaint();
            }
            if (i2 == 3) {
                pathIterator = shape2.getPathIterator(IDENTITY);
            } else {
                pathIterator = shape2.getPathIterator(this.transform);
            }
            float[] fArr = new float[6];
            double[] dArr = new double[6];
            int i3 = 0;
            while (!pathIterator.isDone()) {
                i3++;
                int currentSegment = pathIterator.currentSegment(dArr);
                int i4 = currentSegment == 4 ? 0 : currentSegment == 2 ? 2 : currentSegment == 3 ? 3 : 1;
                for (int i5 = 0; i5 < i4 * 2; i5++) {
                    fArr[i5] = (float) dArr[i5];
                }
                normalizeY(fArr);
                if (currentSegment == 0) {
                    this.f358cb.moveTo(fArr[0], fArr[1]);
                } else if (currentSegment == 1) {
                    this.f358cb.lineTo(fArr[0], fArr[1]);
                } else if (currentSegment == 2) {
                    this.f358cb.curveTo(fArr[0], fArr[1], fArr[2], fArr[3]);
                } else if (currentSegment == 3) {
                    this.f358cb.curveTo(fArr[0], fArr[1], fArr[2], fArr[3], fArr[4], fArr[5]);
                } else if (currentSegment == 4) {
                    this.f358cb.closePath();
                }
                pathIterator.next();
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i3 == 0) {
                        this.f358cb.rectangle(0.0f, 0.0f, 0.0f, 0.0f);
                    }
                    if (pathIterator.getWindingRule() == 0) {
                        this.f358cb.eoClip();
                    } else {
                        this.f358cb.clip();
                    }
                    this.f358cb.newPath();
                } else if (i3 > 0) {
                    this.f358cb.stroke();
                }
            } else if (i3 <= 0) {
            } else {
                if (pathIterator.getWindingRule() == 0) {
                    this.f358cb.eoFill();
                } else {
                    this.f358cb.fill();
                }
            }
        }
    }

    private float normalizeY(float f) {
        return this.height - f;
    }

    private void normalizeY(float[] fArr) {
        fArr[1] = normalizeY(fArr[1]);
        fArr[3] = normalizeY(fArr[3]);
        fArr[5] = normalizeY(fArr[5]);
    }

    /* access modifiers changed from: protected */
    public AffineTransform normalizeMatrix() {
        double[] dArr = new double[6];
        AffineTransform.getTranslateInstance(0.0d, 0.0d).getMatrix(dArr);
        dArr[3] = -1.0d;
        dArr[5] = (double) this.height;
        AffineTransform affineTransform = new AffineTransform(dArr);
        affineTransform.concatenate(this.transform);
        return affineTransform;
    }

    private boolean drawImage(Image image, Image image2, AffineTransform affineTransform, Color color, ImageObserver imageObserver) {
        AffineTransform affineTransform2;
        com.itextpdf.text.Image instance;
        Image image3 = image;
        Image image4 = image2;
        AffineTransform affineTransform3 = affineTransform;
        ImageObserver imageObserver2 = imageObserver;
        if (affineTransform3 == null) {
            affineTransform2 = new AffineTransform();
        } else {
            affineTransform2 = new AffineTransform(affineTransform3);
        }
        affineTransform2.translate(0.0d, (double) image3.getHeight(imageObserver2));
        affineTransform2.scale((double) image3.getWidth(imageObserver2), (double) image3.getHeight(imageObserver2));
        AffineTransform normalizeMatrix = normalizeMatrix();
        AffineTransform scaleInstance = AffineTransform.getScaleInstance(1.0d, -1.0d);
        normalizeMatrix.concatenate(affineTransform2);
        normalizeMatrix.concatenate(scaleInstance);
        double[] dArr = new double[6];
        normalizeMatrix.getMatrix(dArr);
        if (this.currentFillGState != 255) {
            PdfGState pdfGState = this.fillGState[255];
            if (pdfGState == null) {
                pdfGState = new PdfGState();
                pdfGState.setFillOpacity(1.0f);
                this.fillGState[255] = pdfGState;
            }
            this.f358cb.setGState(pdfGState);
        }
        try {
            if (!this.convertImagesToJPEG) {
                instance = com.itextpdf.text.Image.getInstance(image3, color);
            } else {
                BufferedImage bufferedImage = new BufferedImage(image3.getWidth((ImageObserver) null), image3.getHeight((ImageObserver) null), 1);
                Graphics2D createGraphics = bufferedImage.createGraphics();
                createGraphics.drawImage(image, 0, 0, image3.getWidth((ImageObserver) null), image3.getHeight((ImageObserver) null), (ImageObserver) null);
                createGraphics.dispose();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JPEGImageWriteParam jPEGImageWriteParam = new JPEGImageWriteParam(Locale.getDefault());
                jPEGImageWriteParam.setCompressionMode(2);
                jPEGImageWriteParam.setCompressionQuality(this.jpegQuality);
                ImageWriter imageWriter = (ImageWriter) ImageIO.getImageWritersByFormatName("jpg").next();
                ImageOutputStream createImageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);
                imageWriter.setOutput(createImageOutputStream);
                imageWriter.write((IIOMetadata) null, new IIOImage(bufferedImage, (List) null, (IIOMetadata) null), jPEGImageWriteParam);
                imageWriter.dispose();
                createImageOutputStream.close();
                bufferedImage.flush();
                instance = com.itextpdf.text.Image.getInstance(byteArrayOutputStream.toByteArray());
            }
            com.itextpdf.text.Image image5 = instance;
            if (image4 != null) {
                com.itextpdf.text.Image instance2 = com.itextpdf.text.Image.getInstance(image4, (Color) null, true);
                instance2.makeMask();
                instance2.setInverted(true);
                image5.setImageMask(instance2);
            }
            this.f358cb.addImage(image5, (float) dArr[0], (float) dArr[1], (float) dArr[2], (float) dArr[3], (float) dArr[4], (float) dArr[5]);
            Object renderingHint = getRenderingHint(HyperLinkKey.KEY_INSTANCE);
            if (renderingHint != null && !renderingHint.equals(HyperLinkKey.VALUE_HYPERLINKKEY_OFF)) {
                this.f358cb.setAction(new PdfAction(renderingHint.toString()), (float) dArr[4], (float) dArr[5], (float) (dArr[0] + dArr[4]), (float) (dArr[3] + dArr[5]));
            }
            int i = this.currentFillGState;
            if (i >= 0 && i != 255) {
                this.f358cb.setGState(this.fillGState[i]);
            }
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean checkNewPaint(Paint paint2) {
        Paint paint3 = this.paint;
        if (paint3 == paint2) {
            return false;
        }
        if (!(paint3 instanceof Color) || !paint3.equals(paint2)) {
            return true;
        }
        return false;
    }

    private void setFillPaint() {
        if (checkNewPaint(this.paintFill)) {
            this.paintFill = this.paint;
            setPaint(false, 0.0d, 0.0d, true);
        }
    }

    private void setStrokePaint() {
        if (checkNewPaint(this.paintStroke)) {
            this.paintStroke = this.paint;
            setPaint(false, 0.0d, 0.0d, false);
        }
    }

    public static BaseColor prepareColor(Color color) {
        if (color.getColorSpace().getType() != 9) {
            return new BaseColor(color.getRGB());
        }
        float[] colorComponents = color.getColorComponents((float[]) null);
        return new CMYKColor(colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);
    }

    private void setPaint(boolean z, double d, double d2, boolean z2) {
        Color color;
        Color color2 = this.paint;
        if (color2 instanceof Color) {
            Color color3 = color2;
            int alpha2 = color3.getAlpha();
            if (z2) {
                if (alpha2 != this.currentFillGState) {
                    this.currentFillGState = alpha2;
                    PdfGState pdfGState = this.fillGState[alpha2];
                    if (pdfGState == null) {
                        pdfGState = new PdfGState();
                        pdfGState.setFillOpacity(((float) alpha2) / 255.0f);
                        this.fillGState[alpha2] = pdfGState;
                    }
                    this.f358cb.setGState(pdfGState);
                }
                this.f358cb.setColorFill(prepareColor(color3));
                return;
            }
            if (alpha2 != this.currentStrokeGState) {
                this.currentStrokeGState = alpha2;
                PdfGState pdfGState2 = this.strokeGState[alpha2];
                if (pdfGState2 == null) {
                    pdfGState2 = new PdfGState();
                    pdfGState2.setStrokeOpacity(((float) alpha2) / 255.0f);
                    this.strokeGState[alpha2] = pdfGState2;
                }
                this.f358cb.setGState(pdfGState2);
            }
            this.f358cb.setColorStroke(prepareColor(color3));
        } else if (color2 instanceof GradientPaint) {
            GradientPaint gradientPaint = (GradientPaint) color2;
            Point2D point1 = gradientPaint.getPoint1();
            this.transform.transform(point1, point1);
            Point2D point2 = gradientPaint.getPoint2();
            this.transform.transform(point2, point2);
            PdfShadingPattern pdfShadingPattern = new PdfShadingPattern(PdfShading.simpleAxial(this.f358cb.getPdfWriter(), (float) point1.getX(), normalizeY((float) point1.getY()), (float) point2.getX(), normalizeY((float) point2.getY()), new BaseColor(gradientPaint.getColor1().getRGB()), new BaseColor(gradientPaint.getColor2().getRGB())));
            if (z2) {
                this.f358cb.setShadingFill(pdfShadingPattern);
            } else {
                this.f358cb.setShadingStroke(pdfShadingPattern);
            }
        } else {
            int i = 5;
            if (color2 instanceof TexturePaint) {
                try {
                    TexturePaint texturePaint = (TexturePaint) color2;
                    BufferedImage image = texturePaint.getImage();
                    Rectangle2D anchorRect = texturePaint.getAnchorRect();
                    com.itextpdf.text.Image instance = com.itextpdf.text.Image.getInstance((Image) image, (Color) null);
                    PdfPatternPainter createPattern = this.f358cb.createPattern(instance.getWidth(), instance.getHeight());
                    AffineTransform normalizeMatrix = normalizeMatrix();
                    normalizeMatrix.translate(anchorRect.getX(), anchorRect.getY());
                    normalizeMatrix.scale(anchorRect.getWidth() / ((double) instance.getWidth()), (-anchorRect.getHeight()) / ((double) instance.getHeight()));
                    double[] dArr = new double[6];
                    normalizeMatrix.getMatrix(dArr);
                    createPattern.setPatternMatrix((float) dArr[0], (float) dArr[1], (float) dArr[2], (float) dArr[3], (float) dArr[4], (float) dArr[5]);
                    instance.setAbsolutePosition(0.0f, 0.0f);
                    createPattern.addImage(instance);
                    if (z2) {
                        this.f358cb.setPatternFill(createPattern);
                    } else {
                        this.f358cb.setPatternStroke(createPattern);
                    }
                } catch (Exception unused) {
                    if (z2) {
                        this.f358cb.setColorFill(BaseColor.GRAY);
                    } else {
                        this.f358cb.setColorStroke(BaseColor.GRAY);
                    }
                }
            } else {
                try {
                    if (color2.getTransparency() != 1) {
                        i = 6;
                    }
                    BufferedImage bufferedImage = new BufferedImage((int) this.width, (int) this.height, i);
                    Graphics2D graphics = bufferedImage.getGraphics();
                    graphics.transform(this.transform);
                    Shape createTransformedShape = this.transform.createInverse().createTransformedShape(new Rectangle2D.Double(0.0d, 0.0d, (double) bufferedImage.getWidth(), (double) bufferedImage.getHeight()));
                    graphics.setPaint(this.paint);
                    graphics.fill(createTransformedShape);
                    if (z) {
                        AffineTransform affineTransform = new AffineTransform();
                        affineTransform.scale(1.0d, -1.0d);
                        affineTransform.translate(-d, -d2);
                        color = null;
                        graphics.drawImage(bufferedImage, affineTransform, (ImageObserver) null);
                    } else {
                        color = null;
                    }
                    graphics.dispose();
                    com.itextpdf.text.Image instance2 = com.itextpdf.text.Image.getInstance((Image) bufferedImage, color);
                    PdfPatternPainter createPattern2 = this.f358cb.createPattern(this.width, this.height);
                    instance2.setAbsolutePosition(0.0f, 0.0f);
                    createPattern2.addImage(instance2);
                    if (z2) {
                        this.f358cb.setPatternFill(createPattern2);
                    } else {
                        this.f358cb.setPatternStroke(createPattern2);
                    }
                } catch (Exception unused2) {
                    if (z2) {
                        this.f358cb.setColorFill(BaseColor.GRAY);
                    } else {
                        this.f358cb.setColorStroke(BaseColor.GRAY);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:1|2|(1:4)|5|6|7|8|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void waitForImage(java.awt.Image r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.awt.MediaTracker r0 = r3.mediaTracker     // Catch:{ all -> 0x0024 }
            if (r0 != 0) goto L_0x0012
            java.awt.MediaTracker r0 = new java.awt.MediaTracker     // Catch:{ all -> 0x0024 }
            com.itextpdf.awt.PdfGraphics2D$FakeComponent r1 = new com.itextpdf.awt.PdfGraphics2D$FakeComponent     // Catch:{ all -> 0x0024 }
            r2 = 0
            r1.<init>()     // Catch:{ all -> 0x0024 }
            r0.<init>(r1)     // Catch:{ all -> 0x0024 }
            r3.mediaTracker = r0     // Catch:{ all -> 0x0024 }
        L_0x0012:
            java.awt.MediaTracker r0 = r3.mediaTracker     // Catch:{ all -> 0x0024 }
            r1 = 0
            r0.addImage(r4, r1)     // Catch:{ all -> 0x0024 }
            java.awt.MediaTracker r0 = r3.mediaTracker     // Catch:{ InterruptedException -> 0x001d }
            r0.waitForID(r1)     // Catch:{ InterruptedException -> 0x001d }
        L_0x001d:
            java.awt.MediaTracker r0 = r3.mediaTracker     // Catch:{ all -> 0x0024 }
            r0.removeImage(r4)     // Catch:{ all -> 0x0024 }
            monitor-exit(r3)
            return
        L_0x0024:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.awt.PdfGraphics2D.waitForImage(java.awt.Image):void");
    }

    private static class FakeComponent extends Component {
        private static final long serialVersionUID = 6450197945596086638L;

        private FakeComponent() {
        }
    }

    public static class HyperLinkKey extends RenderingHints.Key {
        public static final HyperLinkKey KEY_INSTANCE = new HyperLinkKey(9999);
        public static final Object VALUE_HYPERLINKKEY_OFF = "0";

        public boolean isCompatibleValue(Object obj) {
            return true;
        }

        public String toString() {
            return "HyperLinkKey";
        }

        protected HyperLinkKey(int i) {
            super(i);
        }
    }
}
