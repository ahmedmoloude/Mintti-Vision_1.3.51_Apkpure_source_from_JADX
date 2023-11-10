package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Version;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.p018io.RASInputStream;
import com.itextpdf.text.p018io.RandomAccessSource;
import com.itextpdf.text.p018io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.security.SecurityConstants;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.cert.Certificate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

public class PdfSignatureAppearance {
    public static final int CERTIFIED_FORM_FILLING = 2;
    public static final int CERTIFIED_FORM_FILLING_AND_ANNOTATIONS = 3;
    public static final int CERTIFIED_NO_CHANGES_ALLOWED = 1;
    private static final float MARGIN = 2.0f;
    public static final int NOT_CERTIFIED = 0;
    private static final float TOP_SECTION = 0.3f;
    public static final String questionMark = "% DSUnknown\nq\n1 G\n1 g\n0.1 0 0 0.1 9 0 cm\n0 J 0 j 4 M []0 d\n1 i \n0 g\n313 292 m\n313 404 325 453 432 529 c\n478 561 504 597 504 645 c\n504 736 440 760 391 760 c\n286 760 271 681 265 626 c\n265 625 l\n100 625 l\n100 828 253 898 381 898 c\n451 898 679 878 679 650 c\n679 555 628 499 538 435 c\n488 399 467 376 467 292 c\n313 292 l\nh\n308 214 170 -164 re\nf\n0.44 G\n1.2 w\n1 1 0.4 rg\n287 318 m\n287 430 299 479 406 555 c\n451 587 478 623 478 671 c\n478 762 414 786 365 786 c\n260 786 245 707 239 652 c\n239 651 l\n74 651 l\n74 854 227 924 355 924 c\n425 924 653 904 653 676 c\n653 581 602 525 512 461 c\n462 425 441 402 441 318 c\n287 318 l\nh\n282 240 170 -164 re\nB\nQ\n";
    private boolean acro6Layers = true;
    private PdfTemplate[] app = new PdfTemplate[5];
    private byte[] bout;
    private int boutLen;
    private int certificationLevel = 0;
    private String contact;
    private PdfDictionary cryptoDictionary;
    private HashMap<PdfName, PdfLiteral> exclusionLocations;
    private PdfSigLockDictionary fieldLock;
    private String fieldName;
    private PdfTemplate frm;
    private Image image;
    private float imageScale;
    private Font layer2Font;
    private String layer2Text;
    private String layer4Text;
    private String location;
    private String locationCaption = "Location: ";
    private OutputStream originalout;
    private int page = 1;
    private Rectangle pageRect;
    private boolean preClosed = false;
    private RandomAccessFile raf;
    private long[] range;
    private String reason;
    private String reasonCaption = "Reason: ";
    private Rectangle rect;
    private RenderingMode renderingMode = RenderingMode.DESCRIPTION;
    private boolean reuseAppearance = false;
    private int runDirection = 1;
    private Certificate signCertificate;
    private Calendar signDate;
    private String signatureCreator;
    private SignatureEvent signatureEvent;
    private Image signatureGraphic = null;
    private ByteBuffer sigout;
    private PdfStamper stamper;
    private File tempFile;
    private PdfStamperImp writer;

    public enum RenderingMode {
        DESCRIPTION,
        NAME_AND_DESCRIPTION,
        GRAPHIC_AND_DESCRIPTION,
        GRAPHIC
    }

    public interface SignatureEvent {
        void getSignatureDictionary(PdfDictionary pdfDictionary);
    }

    PdfSignatureAppearance(PdfStamperImp pdfStamperImp) {
        this.writer = pdfStamperImp;
        this.signDate = new GregorianCalendar();
        this.fieldName = getNewSigName();
        this.signatureCreator = Version.getInstance().getVersion();
    }

    public void setCertificationLevel(int i) {
        this.certificationLevel = i;
    }

    public int getCertificationLevel() {
        return this.certificationLevel;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public void setReasonCaption(String str) {
        this.reasonCaption = str;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String str) {
        this.location = str;
    }

    public void setLocationCaption(String str) {
        this.locationCaption = str;
    }

    public String getSignatureCreator() {
        return this.signatureCreator;
    }

    public void setSignatureCreator(String str) {
        this.signatureCreator = str;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String str) {
        this.contact = str;
    }

    public Calendar getSignDate() {
        return this.signDate;
    }

    public void setSignDate(Calendar calendar) {
        this.signDate = calendar;
    }

    public InputStream getRangeStream() throws IOException {
        return new RASInputStream(new RandomAccessSourceFactory().createRanged(getUnderlyingSource(), this.range));
    }

    private RandomAccessSource getUnderlyingSource() throws IOException {
        RandomAccessSourceFactory randomAccessSourceFactory = new RandomAccessSourceFactory();
        RandomAccessFile randomAccessFile = this.raf;
        return randomAccessFile == null ? randomAccessSourceFactory.createSource(this.bout) : randomAccessSourceFactory.createSource(randomAccessFile);
    }

    public void addDeveloperExtension(PdfDeveloperExtension pdfDeveloperExtension) {
        this.writer.addDeveloperExtension(pdfDeveloperExtension);
    }

    public PdfDictionary getCryptoDictionary() {
        return this.cryptoDictionary;
    }

    public void setCryptoDictionary(PdfDictionary pdfDictionary) {
        this.cryptoDictionary = pdfDictionary;
    }

    public void setCertificate(Certificate certificate) {
        this.signCertificate = certificate;
    }

    public Certificate getCertificate() {
        return this.signCertificate;
    }

    public SignatureEvent getSignatureEvent() {
        return this.signatureEvent;
    }

    public void setSignatureEvent(SignatureEvent signatureEvent2) {
        this.signatureEvent = signatureEvent2;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getNewSigName() {
        AcroFields acroFields = this.writer.getAcroFields();
        boolean z = false;
        int i = 0;
        while (!z) {
            i++;
            String str = SecurityConstants.Signature + i;
            if (acroFields.getFieldItem(str) == null) {
                String str2 = str + ".";
                Iterator<String> it = acroFields.getFields().keySet().iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().startsWith(str2)) {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
            }
        }
        return SecurityConstants.Signature + i;
    }

    public int getPage() {
        return this.page;
    }

    public Rectangle getRect() {
        return this.rect;
    }

    public Rectangle getPageRect() {
        return this.pageRect;
    }

    public boolean isInvisible() {
        Rectangle rectangle = this.rect;
        return rectangle == null || rectangle.getWidth() == 0.0f || this.rect.getHeight() == 0.0f;
    }

    public void setVisibleSignature(Rectangle rectangle, int i, String str) {
        if (str != null) {
            if (str.indexOf(46) >= 0) {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("field.names.cannot.contain.a.dot", new Object[0]));
            } else if (this.writer.getAcroFields().getFieldItem(str) == null) {
                this.fieldName = str;
            } else {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.field.1.already.exists", str));
            }
        }
        if (i < 1 || i > this.writer.reader.getNumberOfPages()) {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.page.number.1", i));
        }
        Rectangle rectangle2 = new Rectangle(rectangle);
        this.pageRect = rectangle2;
        rectangle2.normalize();
        this.rect = new Rectangle(this.pageRect.getWidth(), this.pageRect.getHeight());
        this.page = i;
    }

    public void setVisibleSignature(String str) {
        AcroFields.Item fieldItem = this.writer.getAcroFields().getFieldItem(str);
        if (fieldItem != null) {
            PdfDictionary merged = fieldItem.getMerged(0);
            if (PdfName.SIG.equals(PdfReader.getPdfObject(merged.get(PdfName.f588FT)))) {
                this.fieldName = str;
                PdfArray asArray = merged.getAsArray(PdfName.RECT);
                Rectangle rectangle = new Rectangle(asArray.getAsNumber(0).floatValue(), asArray.getAsNumber(1).floatValue(), asArray.getAsNumber(2).floatValue(), asArray.getAsNumber(3).floatValue());
                this.pageRect = rectangle;
                rectangle.normalize();
                this.page = fieldItem.getPage(0).intValue();
                int pageRotation = this.writer.reader.getPageRotation(this.page);
                Rectangle pageSizeWithRotation = this.writer.reader.getPageSizeWithRotation(this.page);
                if (pageRotation == 90) {
                    this.pageRect = new Rectangle(this.pageRect.getBottom(), pageSizeWithRotation.getTop() - this.pageRect.getLeft(), this.pageRect.getTop(), pageSizeWithRotation.getTop() - this.pageRect.getRight());
                } else if (pageRotation == 180) {
                    this.pageRect = new Rectangle(pageSizeWithRotation.getRight() - this.pageRect.getLeft(), pageSizeWithRotation.getTop() - this.pageRect.getBottom(), pageSizeWithRotation.getRight() - this.pageRect.getRight(), pageSizeWithRotation.getTop() - this.pageRect.getTop());
                } else if (pageRotation == 270) {
                    this.pageRect = new Rectangle(pageSizeWithRotation.getRight() - this.pageRect.getBottom(), this.pageRect.getLeft(), pageSizeWithRotation.getRight() - this.pageRect.getTop(), this.pageRect.getRight());
                }
                if (pageRotation != 0) {
                    this.pageRect.normalize();
                }
                this.rect = new Rectangle(this.pageRect.getWidth(), this.pageRect.getHeight());
                return;
            }
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.field.1.is.not.a.signature.field", str));
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.field.1.does.not.exist", str));
    }

    public RenderingMode getRenderingMode() {
        return this.renderingMode;
    }

    public void setRenderingMode(RenderingMode renderingMode2) {
        this.renderingMode = renderingMode2;
    }

    public Image getSignatureGraphic() {
        return this.signatureGraphic;
    }

    public void setSignatureGraphic(Image image2) {
        this.signatureGraphic = image2;
    }

    public boolean isAcro6Layers() {
        return this.acro6Layers;
    }

    public void setAcro6Layers(boolean z) {
        this.acro6Layers = z;
    }

    public PdfTemplate getLayer(int i) {
        if (i < 0) {
            return null;
        }
        PdfTemplate[] pdfTemplateArr = this.app;
        if (i >= pdfTemplateArr.length) {
            return null;
        }
        PdfTemplate pdfTemplate = pdfTemplateArr[i];
        if (pdfTemplate != null) {
            return pdfTemplate;
        }
        PdfTemplate pdfTemplate2 = new PdfTemplate(this.writer);
        pdfTemplateArr[i] = pdfTemplate2;
        pdfTemplate2.setBoundingBox(this.rect);
        PdfStamperImp pdfStamperImp = this.writer;
        pdfStamperImp.addDirectTemplateSimple(pdfTemplate2, new PdfName("n" + i));
        return pdfTemplate2;
    }

    public void setReuseAppearance(boolean z) {
        this.reuseAppearance = z;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image2) {
        this.image = image2;
    }

    public float getImageScale() {
        return this.imageScale;
    }

    public void setImageScale(float f) {
        this.imageScale = f;
    }

    public void setLayer2Text(String str) {
        this.layer2Text = str;
    }

    public String getLayer2Text() {
        return this.layer2Text;
    }

    public Font getLayer2Font() {
        return this.layer2Font;
    }

    public void setLayer2Font(Font font) {
        this.layer2Font = font;
    }

    public void setRunDirection(int i) {
        if (i < 0 || i > 3) {
            throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.run.direction.1", i));
        }
        this.runDirection = i;
    }

    public int getRunDirection() {
        return this.runDirection;
    }

    public void setLayer4Text(String str) {
        this.layer4Text = str;
    }

    public String getLayer4Text() {
        return this.layer4Text;
    }

    public PdfTemplate getTopLayer() {
        if (this.frm == null) {
            PdfTemplate pdfTemplate = new PdfTemplate(this.writer);
            this.frm = pdfTemplate;
            pdfTemplate.setBoundingBox(this.rect);
            this.writer.addDirectTemplateSimple(this.frm, new PdfName("FRM"));
        }
        return this.frm;
    }

    /* JADX WARNING: Removed duplicated region for block: B:130:0x05cf  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x05dd  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x0602  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.PdfTemplate getAppearance() throws com.itextpdf.text.DocumentException {
        /*
            r34 = this;
            r0 = r34
            boolean r1 = r34.isInvisible()
            r2 = 0
            r3 = 0
            if (r1 == 0) goto L_0x001f
            com.itextpdf.text.pdf.PdfTemplate r1 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfStamperImp r4 = r0.writer
            r1.<init>(r4)
            com.itextpdf.text.Rectangle r4 = new com.itextpdf.text.Rectangle
            r4.<init>(r3, r3)
            r1.setBoundingBox(r4)
            com.itextpdf.text.pdf.PdfStamperImp r3 = r0.writer
            r3.addDirectTemplateSimple(r1, r2)
            return r1
        L_0x001f:
            com.itextpdf.text.pdf.PdfTemplate[] r1 = r0.app
            r4 = 0
            r1 = r1[r4]
            if (r1 != 0) goto L_0x002d
            boolean r1 = r0.reuseAppearance
            if (r1 != 0) goto L_0x002d
            r34.createBlankN0()
        L_0x002d:
            com.itextpdf.text.pdf.PdfTemplate[] r1 = r0.app
            r5 = 1
            r6 = r1[r5]
            r7 = 1120403456(0x42c80000, float:100.0)
            if (r6 != 0) goto L_0x005c
            boolean r6 = r0.acro6Layers
            if (r6 != 0) goto L_0x005c
            com.itextpdf.text.pdf.PdfTemplate r6 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfStamperImp r8 = r0.writer
            r6.<init>(r8)
            r1[r5] = r6
            com.itextpdf.text.Rectangle r1 = new com.itextpdf.text.Rectangle
            r1.<init>(r7, r7)
            r6.setBoundingBox(r1)
            com.itextpdf.text.pdf.PdfStamperImp r1 = r0.writer
            com.itextpdf.text.pdf.PdfName r8 = new com.itextpdf.text.pdf.PdfName
            java.lang.String r9 = "n1"
            r8.<init>((java.lang.String) r9)
            r1.addDirectTemplateSimple(r6, r8)
            java.lang.String r1 = "% DSUnknown\nq\n1 G\n1 g\n0.1 0 0 0.1 9 0 cm\n0 J 0 j 4 M []0 d\n1 i \n0 g\n313 292 m\n313 404 325 453 432 529 c\n478 561 504 597 504 645 c\n504 736 440 760 391 760 c\n286 760 271 681 265 626 c\n265 625 l\n100 625 l\n100 828 253 898 381 898 c\n451 898 679 878 679 650 c\n679 555 628 499 538 435 c\n488 399 467 376 467 292 c\n313 292 l\nh\n308 214 170 -164 re\nf\n0.44 G\n1.2 w\n1 1 0.4 rg\n287 318 m\n287 430 299 479 406 555 c\n451 587 478 623 478 671 c\n478 762 414 786 365 786 c\n260 786 245 707 239 652 c\n239 651 l\n74 651 l\n74 854 227 924 355 924 c\n425 924 653 904 653 676 c\n653 581 602 525 512 461 c\n462 425 441 402 441 318 c\n287 318 l\nh\n282 240 170 -164 re\nB\nQ\n"
            r6.setLiteral((java.lang.String) r1)
        L_0x005c:
            com.itextpdf.text.pdf.PdfTemplate[] r1 = r0.app
            r6 = 2
            r1 = r1[r6]
            r9 = 1060320051(0x3f333333, float:0.7)
            r11 = 1073741824(0x40000000, float:2.0)
            if (r1 != 0) goto L_0x03fd
            java.lang.String r1 = r0.layer2Text
            java.lang.String r12 = "E"
            java.lang.String r13 = ""
            java.lang.String r14 = "CN"
            if (r1 != 0) goto L_0x00e1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r15 = "Digitally signed by "
            r1.append(r15)
            java.security.cert.Certificate r15 = r0.signCertificate
            java.security.cert.X509Certificate r15 = (java.security.cert.X509Certificate) r15
            com.itextpdf.text.pdf.security.CertificateInfo$X500Name r15 = com.itextpdf.text.pdf.security.CertificateInfo.getSubjectFields(r15)
            if (r15 == 0) goto L_0x0091
            java.lang.String r16 = r15.getField(r14)
            if (r16 != 0) goto L_0x0093
            java.lang.String r16 = r15.getField(r12)
            goto L_0x0093
        L_0x0091:
            r16 = r2
        L_0x0093:
            if (r16 != 0) goto L_0x0097
            r15 = r13
            goto L_0x0099
        L_0x0097:
            r15 = r16
        L_0x0099:
            r1.append(r15)
            r15 = 10
            r1.append(r15)
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat
            java.lang.String r7 = "yyyy.MM.dd HH:mm:ss z"
            r2.<init>(r7)
            java.lang.String r7 = "Date: "
            r1.append(r7)
            java.util.Calendar r7 = r0.signDate
            java.util.Date r7 = r7.getTime()
            java.lang.String r2 = r2.format(r7)
            r1.append(r2)
            java.lang.String r2 = r0.reason
            if (r2 == 0) goto L_0x00cc
            r1.append(r15)
            java.lang.String r2 = r0.reasonCaption
            r1.append(r2)
            java.lang.String r2 = r0.reason
            r1.append(r2)
        L_0x00cc:
            java.lang.String r2 = r0.location
            if (r2 == 0) goto L_0x00dd
            r1.append(r15)
            java.lang.String r2 = r0.locationCaption
            r1.append(r2)
            java.lang.String r2 = r0.location
            r1.append(r2)
        L_0x00dd:
            java.lang.String r1 = r1.toString()
        L_0x00e1:
            com.itextpdf.text.pdf.PdfTemplate[] r2 = r0.app
            com.itextpdf.text.pdf.PdfTemplate r7 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfStamperImp r15 = r0.writer
            r7.<init>(r15)
            r2[r6] = r7
            com.itextpdf.text.Rectangle r2 = r0.rect
            r7.setBoundingBox(r2)
            com.itextpdf.text.pdf.PdfStamperImp r2 = r0.writer
            com.itextpdf.text.pdf.PdfName r15 = new com.itextpdf.text.pdf.PdfName
            java.lang.String r8 = "n2"
            r15.<init>((java.lang.String) r8)
            r2.addDirectTemplateSimple(r7, r15)
            com.itextpdf.text.Image r2 = r0.image
            if (r2 == 0) goto L_0x0176
            float r8 = r0.imageScale
            int r15 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r15 != 0) goto L_0x0123
            com.itextpdf.text.Rectangle r8 = r0.rect
            float r19 = r8.getWidth()
            r20 = 0
            r21 = 0
            com.itextpdf.text.Rectangle r8 = r0.rect
            float r22 = r8.getHeight()
            r23 = 0
            r24 = 0
            r17 = r7
            r18 = r2
            r17.addImage((com.itextpdf.text.Image) r18, (float) r19, (float) r20, (float) r21, (float) r22, (float) r23, (float) r24)
            goto L_0x0176
        L_0x0123:
            int r2 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x0145
            com.itextpdf.text.Rectangle r2 = r0.rect
            float r2 = r2.getWidth()
            com.itextpdf.text.Image r8 = r0.image
            float r8 = r8.getWidth()
            float r2 = r2 / r8
            com.itextpdf.text.Rectangle r8 = r0.rect
            float r8 = r8.getHeight()
            com.itextpdf.text.Image r15 = r0.image
            float r15 = r15.getHeight()
            float r8 = r8 / r15
            float r8 = java.lang.Math.min(r2, r8)
        L_0x0145:
            com.itextpdf.text.Image r2 = r0.image
            float r2 = r2.getWidth()
            float r19 = r2 * r8
            com.itextpdf.text.Image r2 = r0.image
            float r2 = r2.getHeight()
            float r22 = r2 * r8
            com.itextpdf.text.Rectangle r2 = r0.rect
            float r2 = r2.getWidth()
            float r2 = r2 - r19
            float r23 = r2 / r11
            com.itextpdf.text.Rectangle r2 = r0.rect
            float r2 = r2.getHeight()
            float r2 = r2 - r22
            float r24 = r2 / r11
            com.itextpdf.text.Image r2 = r0.image
            r20 = 0
            r21 = 0
            r17 = r7
            r18 = r2
            r17.addImage((com.itextpdf.text.Image) r18, (float) r19, (float) r20, (float) r21, (float) r22, (float) r23, (float) r24)
        L_0x0176:
            com.itextpdf.text.Font r2 = r0.layer2Font
            if (r2 != 0) goto L_0x0180
            com.itextpdf.text.Font r2 = new com.itextpdf.text.Font
            r2.<init>()
            goto L_0x0186
        L_0x0180:
            com.itextpdf.text.Font r8 = new com.itextpdf.text.Font
            r8.<init>((com.itextpdf.text.Font) r2)
            r2 = r8
        L_0x0186:
            float r8 = r2.getSize()
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r15 = r0.renderingMode
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r3 = com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.NAME_AND_DESCRIPTION
            if (r15 == r3) goto L_0x01e1
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r3 = r0.renderingMode
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r15 = com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION
            if (r3 != r15) goto L_0x019b
            com.itextpdf.text.Image r3 = r0.signatureGraphic
            if (r3 == 0) goto L_0x019b
            goto L_0x01e1
        L_0x019b:
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r3 = r0.renderingMode
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r15 = com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.GRAPHIC
            if (r3 != r15) goto L_0x01c9
            com.itextpdf.text.Image r3 = r0.signatureGraphic
            if (r3 == 0) goto L_0x01bb
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r15 = r0.rect
            float r15 = r15.getWidth()
            float r15 = r15 - r11
            com.itextpdf.text.Rectangle r10 = r0.rect
            float r10 = r10.getHeight()
            float r10 = r10 - r11
            r3.<init>(r11, r11, r15, r10)
            r4 = 0
            goto L_0x0253
        L_0x01bb:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.Object[] r2 = new java.lang.Object[r4]
            java.lang.String r3 = "a.signature.image.should.be.present.when.rendering.mode.is.graphic.only"
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r2)
            r1.<init>(r2)
            throw r1
        L_0x01c9:
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r10 = r0.rect
            float r10 = r10.getWidth()
            float r10 = r10 - r11
            com.itextpdf.text.Rectangle r15 = r0.rect
            float r15 = r15.getHeight()
            float r15 = r15 * r9
            float r15 = r15 - r11
            r3.<init>(r11, r11, r10, r15)
            r4 = r3
            r3 = 0
            goto L_0x0253
        L_0x01e1:
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r10 = r0.rect
            float r10 = r10.getWidth()
            float r10 = r10 / r11
            float r10 = r10 - r11
            com.itextpdf.text.Rectangle r15 = r0.rect
            float r15 = r15.getHeight()
            float r15 = r15 - r11
            r3.<init>(r11, r11, r10, r15)
            com.itextpdf.text.Rectangle r10 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r15 = r0.rect
            float r15 = r15.getWidth()
            float r15 = r15 / r11
            r19 = 1065353216(0x3f800000, float:1.0)
            float r15 = r15 + r19
            com.itextpdf.text.Rectangle r9 = r0.rect
            float r9 = r9.getWidth()
            float r9 = r9 - r19
            com.itextpdf.text.Rectangle r4 = r0.rect
            float r4 = r4.getHeight()
            float r4 = r4 - r11
            r10.<init>(r15, r11, r9, r4)
            com.itextpdf.text.Rectangle r4 = r0.rect
            float r4 = r4.getHeight()
            com.itextpdf.text.Rectangle r9 = r0.rect
            float r9 = r9.getWidth()
            int r4 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r4 <= 0) goto L_0x0252
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r4 = r0.rect
            float r4 = r4.getHeight()
            float r4 = r4 / r11
            com.itextpdf.text.Rectangle r9 = r0.rect
            float r9 = r9.getWidth()
            float r9 = r9 - r11
            com.itextpdf.text.Rectangle r10 = r0.rect
            float r10 = r10.getHeight()
            r3.<init>(r11, r4, r9, r10)
            com.itextpdf.text.Rectangle r4 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r9 = r0.rect
            float r9 = r9.getWidth()
            float r9 = r9 - r11
            com.itextpdf.text.Rectangle r10 = r0.rect
            float r10 = r10.getHeight()
            float r10 = r10 / r11
            float r10 = r10 - r11
            r4.<init>(r11, r11, r9, r10)
            goto L_0x0253
        L_0x0252:
            r4 = r10
        L_0x0253:
            int[] r9 = com.itextpdf.text.pdf.PdfSignatureAppearance.C15221.f684x1c57ccf9
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r10 = r0.renderingMode
            int r10 = r10.ordinal()
            r9 = r9[r10]
            if (r9 == r5) goto L_0x0350
            if (r9 == r6) goto L_0x02c8
            r10 = 3
            if (r9 == r10) goto L_0x0266
            goto L_0x03b0
        L_0x0266:
            com.itextpdf.text.pdf.ColumnText r9 = new com.itextpdf.text.pdf.ColumnText
            r9.<init>(r7)
            int r10 = r0.runDirection
            r9.setRunDirection(r10)
            float r27 = r3.getLeft()
            float r28 = r3.getBottom()
            float r29 = r3.getRight()
            float r30 = r3.getTop()
            r31 = 0
            r32 = 2
            r26 = r9
            r26.setSimpleColumn(r27, r28, r29, r30, r31, r32)
            com.itextpdf.text.Image r10 = r0.signatureGraphic
            com.itextpdf.text.Image r10 = com.itextpdf.text.Image.getInstance((com.itextpdf.text.Image) r10)
            float r12 = r3.getWidth()
            float r13 = r3.getHeight()
            r10.scaleToFit(r12, r13)
            com.itextpdf.text.Paragraph r12 = new com.itextpdf.text.Paragraph
            float r13 = r3.getHeight()
            r12.<init>((float) r13)
            float r13 = r3.getWidth()
            float r14 = r10.getScaledWidth()
            float r13 = r13 - r14
            float r13 = r13 / r11
            float r3 = r3.getHeight()
            float r14 = r10.getScaledHeight()
            float r3 = r3 - r14
            float r3 = r3 / r11
            com.itextpdf.text.Chunk r14 = new com.itextpdf.text.Chunk
            r15 = 0
            r14.<init>(r10, r13, r3, r15)
            r12.add((com.itextpdf.text.Element) r14)
            r9.addElement(r12)
            r9.mo20965go()
            goto L_0x03b0
        L_0x02c8:
            com.itextpdf.text.Image r9 = r0.signatureGraphic
            if (r9 == 0) goto L_0x0341
            com.itextpdf.text.pdf.ColumnText r9 = new com.itextpdf.text.pdf.ColumnText
            r9.<init>(r7)
            int r10 = r0.runDirection
            r9.setRunDirection(r10)
            float r27 = r3.getLeft()
            float r28 = r3.getBottom()
            float r29 = r3.getRight()
            float r30 = r3.getTop()
            r31 = 0
            r32 = 2
            r26 = r9
            r26.setSimpleColumn(r27, r28, r29, r30, r31, r32)
            com.itextpdf.text.Image r10 = r0.signatureGraphic
            com.itextpdf.text.Image r10 = com.itextpdf.text.Image.getInstance((com.itextpdf.text.Image) r10)
            float r12 = r3.getWidth()
            float r13 = r3.getHeight()
            r10.scaleToFit(r12, r13)
            com.itextpdf.text.Paragraph r12 = new com.itextpdf.text.Paragraph
            r12.<init>()
            float r13 = r10.getScaledHeight()
            float r13 = -r13
            r14 = 1097859072(0x41700000, float:15.0)
            float r13 = r13 + r14
            float r14 = r3.getWidth()
            float r15 = r10.getScaledWidth()
            float r14 = r14 - r15
            float r14 = r14 / r11
            r15 = 0
            float r14 = r14 + r15
            float r15 = r3.getHeight()
            float r21 = r10.getScaledHeight()
            float r15 = r15 - r21
            float r15 = r15 / r11
            float r13 = r13 - r15
            com.itextpdf.text.Chunk r15 = new com.itextpdf.text.Chunk
            float r3 = r3.getWidth()
            float r21 = r10.getScaledWidth()
            float r3 = r3 - r21
            float r3 = r3 / r11
            float r14 = r14 + r3
            r3 = 0
            r15.<init>(r10, r14, r13, r3)
            r12.add((com.itextpdf.text.Element) r15)
            r9.addElement(r12)
            r9.mo20965go()
            goto L_0x03b0
        L_0x0341:
            r3 = 0
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.Object[] r2 = new java.lang.Object[r3]
            java.lang.String r3 = "a.signature.image.should.be.present.when.rendering.mode.is.graphic.and.description"
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r2)
            r1.<init>(r2)
            throw r1
        L_0x0350:
            java.security.cert.Certificate r9 = r0.signCertificate
            java.security.cert.X509Certificate r9 = (java.security.cert.X509Certificate) r9
            com.itextpdf.text.pdf.security.CertificateInfo$X500Name r9 = com.itextpdf.text.pdf.security.CertificateInfo.getSubjectFields(r9)
            java.lang.String r9 = r9.getField(r14)
            if (r9 != 0) goto L_0x036a
            java.security.cert.Certificate r9 = r0.signCertificate
            java.security.cert.X509Certificate r9 = (java.security.cert.X509Certificate) r9
            com.itextpdf.text.pdf.security.CertificateInfo$X500Name r9 = com.itextpdf.text.pdf.security.CertificateInfo.getSubjectFields(r9)
            java.lang.String r9 = r9.getField(r12)
        L_0x036a:
            if (r9 != 0) goto L_0x036d
            goto L_0x036e
        L_0x036d:
            r13 = r9
        L_0x036e:
            com.itextpdf.text.Rectangle r9 = new com.itextpdf.text.Rectangle
            float r10 = r3.getWidth()
            float r10 = r10 - r11
            float r12 = r3.getHeight()
            float r12 = r12 - r11
            r9.<init>(r10, r12)
            r10 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r12 = r0.runDirection
            float r32 = com.itextpdf.text.pdf.ColumnText.fitText(r2, r13, r9, r10, r12)
            com.itextpdf.text.pdf.ColumnText r9 = new com.itextpdf.text.pdf.ColumnText
            r9.<init>(r7)
            int r10 = r0.runDirection
            r9.setRunDirection(r10)
            com.itextpdf.text.Phrase r10 = new com.itextpdf.text.Phrase
            r10.<init>((java.lang.String) r13, (com.itextpdf.text.Font) r2)
            float r28 = r3.getLeft()
            float r29 = r3.getBottom()
            float r30 = r3.getRight()
            float r31 = r3.getTop()
            r33 = 0
            r26 = r9
            r27 = r10
            r26.setSimpleColumn(r27, r28, r29, r30, r31, r32, r33)
            r9.mo20965go()
        L_0x03b0:
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r3 = r0.renderingMode
            com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r9 = com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.GRAPHIC
            if (r3 == r9) goto L_0x03fd
            r3 = 0
            int r9 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r9 > 0) goto L_0x03d0
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            float r8 = r4.getWidth()
            float r9 = r4.getHeight()
            r3.<init>(r8, r9)
            r8 = 1094713344(0x41400000, float:12.0)
            int r9 = r0.runDirection
            float r8 = com.itextpdf.text.pdf.ColumnText.fitText(r2, r1, r3, r8, r9)
        L_0x03d0:
            r32 = r8
            com.itextpdf.text.pdf.ColumnText r3 = new com.itextpdf.text.pdf.ColumnText
            r3.<init>(r7)
            int r7 = r0.runDirection
            r3.setRunDirection(r7)
            com.itextpdf.text.Phrase r7 = new com.itextpdf.text.Phrase
            r7.<init>((java.lang.String) r1, (com.itextpdf.text.Font) r2)
            float r28 = r4.getLeft()
            float r29 = r4.getBottom()
            float r30 = r4.getRight()
            float r31 = r4.getTop()
            r33 = 0
            r26 = r3
            r27 = r7
            r26.setSimpleColumn(r27, r28, r29, r30, r31, r32, r33)
            r3.mo20965go()
        L_0x03fd:
            com.itextpdf.text.pdf.PdfTemplate[] r1 = r0.app
            r2 = 3
            r3 = r1[r2]
            if (r3 != 0) goto L_0x042c
            boolean r3 = r0.acro6Layers
            if (r3 != 0) goto L_0x042c
            com.itextpdf.text.pdf.PdfTemplate r3 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfStamperImp r4 = r0.writer
            r3.<init>(r4)
            r1[r2] = r3
            com.itextpdf.text.Rectangle r1 = new com.itextpdf.text.Rectangle
            r2 = 1120403456(0x42c80000, float:100.0)
            r1.<init>(r2, r2)
            r3.setBoundingBox(r1)
            com.itextpdf.text.pdf.PdfStamperImp r1 = r0.writer
            com.itextpdf.text.pdf.PdfName r2 = new com.itextpdf.text.pdf.PdfName
            java.lang.String r4 = "n3"
            r2.<init>((java.lang.String) r4)
            r1.addDirectTemplateSimple(r3, r2)
            java.lang.String r1 = "% DSBlank\n"
            r3.setLiteral((java.lang.String) r1)
        L_0x042c:
            com.itextpdf.text.pdf.PdfTemplate[] r1 = r0.app
            r2 = 4
            r3 = r1[r2]
            if (r3 != 0) goto L_0x04d4
            boolean r3 = r0.acro6Layers
            if (r3 != 0) goto L_0x04d4
            com.itextpdf.text.pdf.PdfTemplate r3 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfStamperImp r4 = r0.writer
            r3.<init>(r4)
            r1[r2] = r3
            com.itextpdf.text.Rectangle r1 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r4 = r0.rect
            float r4 = r4.getHeight()
            r7 = 1060320051(0x3f333333, float:0.7)
            float r4 = r4 * r7
            com.itextpdf.text.Rectangle r7 = r0.rect
            float r7 = r7.getRight()
            com.itextpdf.text.Rectangle r8 = r0.rect
            float r8 = r8.getTop()
            r9 = 0
            r1.<init>(r9, r4, r7, r8)
            r3.setBoundingBox(r1)
            com.itextpdf.text.pdf.PdfStamperImp r1 = r0.writer
            com.itextpdf.text.pdf.PdfName r4 = new com.itextpdf.text.pdf.PdfName
            java.lang.String r7 = "n4"
            r4.<init>((java.lang.String) r7)
            r1.addDirectTemplateSimple(r3, r4)
            com.itextpdf.text.Font r1 = r0.layer2Font
            if (r1 != 0) goto L_0x0476
            com.itextpdf.text.Font r1 = new com.itextpdf.text.Font
            r1.<init>()
            goto L_0x047c
        L_0x0476:
            com.itextpdf.text.Font r4 = new com.itextpdf.text.Font
            r4.<init>((com.itextpdf.text.Font) r1)
            r1 = r4
        L_0x047c:
            java.lang.String r4 = r0.layer4Text
            if (r4 == 0) goto L_0x0481
            goto L_0x0483
        L_0x0481:
            java.lang.String r4 = "Signature Not Verified"
        L_0x0483:
            com.itextpdf.text.Rectangle r7 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r8 = r0.rect
            float r8 = r8.getWidth()
            r9 = 1082130432(0x40800000, float:4.0)
            float r8 = r8 - r9
            com.itextpdf.text.Rectangle r10 = r0.rect
            float r10 = r10.getHeight()
            r12 = 1050253722(0x3e99999a, float:0.3)
            float r10 = r10 * r12
            float r10 = r10 - r9
            r7.<init>(r8, r10)
            int r8 = r0.runDirection
            r9 = 1097859072(0x41700000, float:15.0)
            float r26 = com.itextpdf.text.pdf.ColumnText.fitText(r1, r4, r7, r9, r8)
            com.itextpdf.text.pdf.ColumnText r7 = new com.itextpdf.text.pdf.ColumnText
            r7.<init>(r3)
            int r3 = r0.runDirection
            r7.setRunDirection(r3)
            com.itextpdf.text.Phrase r3 = new com.itextpdf.text.Phrase
            r3.<init>((java.lang.String) r4, (com.itextpdf.text.Font) r1)
            r22 = 1073741824(0x40000000, float:2.0)
            r23 = 0
            com.itextpdf.text.Rectangle r1 = r0.rect
            float r1 = r1.getWidth()
            float r24 = r1 - r11
            com.itextpdf.text.Rectangle r1 = r0.rect
            float r1 = r1.getHeight()
            float r25 = r1 - r11
            r27 = 0
            r20 = r7
            r21 = r3
            r20.setSimpleColumn(r21, r22, r23, r24, r25, r26, r27)
            r7.mo20965go()
        L_0x04d4:
            com.itextpdf.text.pdf.PdfStamperImp r1 = r0.writer
            com.itextpdf.text.pdf.PdfReader r1 = r1.reader
            int r3 = r0.page
            int r1 = r1.getPageRotation((int) r3)
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r4 = r0.rect
            r3.<init>((com.itextpdf.text.Rectangle) r4)
            r4 = r1
        L_0x04e6:
            if (r4 <= 0) goto L_0x04ef
            com.itextpdf.text.Rectangle r3 = r3.rotate()
            int r4 = r4 + -90
            goto L_0x04e6
        L_0x04ef:
            com.itextpdf.text.pdf.PdfTemplate r4 = r0.frm
            if (r4 != 0) goto L_0x0625
            com.itextpdf.text.pdf.PdfTemplate r4 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfStamperImp r7 = r0.writer
            r4.<init>(r7)
            r0.frm = r4
            r4.setBoundingBox(r3)
            com.itextpdf.text.pdf.PdfStamperImp r4 = r0.writer
            com.itextpdf.text.pdf.PdfTemplate r7 = r0.frm
            com.itextpdf.text.pdf.PdfName r8 = new com.itextpdf.text.pdf.PdfName
            java.lang.String r9 = "FRM"
            r8.<init>((java.lang.String) r9)
            r4.addDirectTemplateSimple(r7, r8)
            com.itextpdf.text.Rectangle r4 = r0.rect
            float r4 = r4.getWidth()
            com.itextpdf.text.Rectangle r7 = r0.rect
            float r7 = r7.getHeight()
            float r4 = java.lang.Math.min(r4, r7)
            r7 = 1063675494(0x3f666666, float:0.9)
            float r4 = r4 * r7
            com.itextpdf.text.Rectangle r7 = r0.rect
            float r7 = r7.getWidth()
            float r7 = r7 - r4
            float r7 = r7 / r11
            com.itextpdf.text.Rectangle r8 = r0.rect
            float r8 = r8.getHeight()
            float r8 = r8 - r4
            float r8 = r8 / r11
            r9 = 1120403456(0x42c80000, float:100.0)
            float r4 = r4 / r9
            r9 = 90
            if (r1 != r9) goto L_0x0551
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.frm
            r21 = 0
            r22 = 1065353216(0x3f800000, float:1.0)
            r23 = -1082130432(0xffffffffbf800000, float:-1.0)
            r24 = 0
            com.itextpdf.text.Rectangle r9 = r0.rect
            float r25 = r9.getHeight()
            r26 = 0
            r20 = r1
            r20.concatCTM((float) r21, (float) r22, (float) r23, (float) r24, (float) r25, (float) r26)
            goto L_0x058c
        L_0x0551:
            r9 = 180(0xb4, float:2.52E-43)
            if (r1 != r9) goto L_0x0571
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.frm
            r21 = -1082130432(0xffffffffbf800000, float:-1.0)
            r22 = 0
            r23 = 0
            r24 = -1082130432(0xffffffffbf800000, float:-1.0)
            com.itextpdf.text.Rectangle r9 = r0.rect
            float r25 = r9.getWidth()
            com.itextpdf.text.Rectangle r9 = r0.rect
            float r26 = r9.getHeight()
            r20 = r1
            r20.concatCTM((float) r21, (float) r22, (float) r23, (float) r24, (float) r25, (float) r26)
            goto L_0x058c
        L_0x0571:
            r9 = 270(0x10e, float:3.78E-43)
            if (r1 != r9) goto L_0x058c
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.frm
            r21 = 0
            r22 = -1082130432(0xffffffffbf800000, float:-1.0)
            r23 = 1065353216(0x3f800000, float:1.0)
            r24 = 0
            r25 = 0
            com.itextpdf.text.Rectangle r9 = r0.rect
            float r26 = r9.getWidth()
            r20 = r1
            r20.concatCTM((float) r21, (float) r22, (float) r23, (float) r24, (float) r25, (float) r26)
        L_0x058c:
            boolean r1 = r0.reuseAppearance
            if (r1 == 0) goto L_0x05ca
            com.itextpdf.text.pdf.PdfStamperImp r1 = r0.writer
            com.itextpdf.text.pdf.AcroFields r1 = r1.getAcroFields()
            java.lang.String r9 = r34.getFieldName()
            com.itextpdf.text.pdf.PdfIndirectReference r21 = r1.getNormalAppearance(r9)
            if (r21 == 0) goto L_0x05bd
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.frm
            com.itextpdf.text.pdf.PdfName r9 = new com.itextpdf.text.pdf.PdfName
            java.lang.String r10 = "n0"
            r9.<init>((java.lang.String) r10)
            r23 = 1065353216(0x3f800000, float:1.0)
            r24 = 0
            r25 = 0
            r26 = 1065353216(0x3f800000, float:1.0)
            r27 = 0
            r28 = 0
            r20 = r1
            r22 = r9
            r20.addTemplateReference((com.itextpdf.text.pdf.PdfIndirectReference) r21, (com.itextpdf.text.pdf.PdfName) r22, (float) r23, (float) r24, (float) r25, (float) r26, (float) r27, (float) r28)
            goto L_0x05ca
        L_0x05bd:
            r1 = 0
            r0.reuseAppearance = r1
            com.itextpdf.text.pdf.PdfTemplate[] r9 = r0.app
            r9 = r9[r1]
            if (r9 != 0) goto L_0x05cb
            r34.createBlankN0()
            goto L_0x05cb
        L_0x05ca:
            r1 = 0
        L_0x05cb:
            boolean r9 = r0.reuseAppearance
            if (r9 != 0) goto L_0x05d9
            com.itextpdf.text.pdf.PdfTemplate r9 = r0.frm
            com.itextpdf.text.pdf.PdfTemplate[] r10 = r0.app
            r1 = r10[r1]
            r10 = 0
            r9.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r1, (float) r10, (float) r10)
        L_0x05d9:
            boolean r1 = r0.acro6Layers
            if (r1 != 0) goto L_0x05f4
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.frm
            com.itextpdf.text.pdf.PdfTemplate[] r9 = r0.app
            r21 = r9[r5]
            r23 = 0
            r24 = 0
            r20 = r1
            r22 = r4
            r25 = r4
            r26 = r7
            r27 = r8
            r20.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r21, (float) r22, (float) r23, (float) r24, (float) r25, (float) r26, (float) r27)
        L_0x05f4:
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.frm
            com.itextpdf.text.pdf.PdfTemplate[] r5 = r0.app
            r5 = r5[r6]
            r6 = 0
            r1.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r5, (float) r6, (float) r6)
            boolean r1 = r0.acro6Layers
            if (r1 != 0) goto L_0x0625
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.frm
            com.itextpdf.text.pdf.PdfTemplate[] r5 = r0.app
            r6 = 3
            r21 = r5[r6]
            r23 = 0
            r24 = 0
            r20 = r1
            r22 = r4
            r25 = r4
            r26 = r7
            r27 = r8
            r20.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r21, (float) r22, (float) r23, (float) r24, (float) r25, (float) r26, (float) r27)
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.frm
            com.itextpdf.text.pdf.PdfTemplate[] r4 = r0.app
            r2 = r4[r2]
            r4 = 0
            r1.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r2, (float) r4, (float) r4)
            goto L_0x0626
        L_0x0625:
            r4 = 0
        L_0x0626:
            com.itextpdf.text.pdf.PdfTemplate r1 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfStamperImp r2 = r0.writer
            r1.<init>(r2)
            r1.setBoundingBox(r3)
            com.itextpdf.text.pdf.PdfStamperImp r2 = r0.writer
            r3 = 0
            r2.addDirectTemplateSimple(r1, r3)
            com.itextpdf.text.pdf.PdfTemplate r2 = r0.frm
            r1.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r2, (float) r4, (float) r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfSignatureAppearance.getAppearance():com.itextpdf.text.pdf.PdfTemplate");
    }

    /* renamed from: com.itextpdf.text.pdf.PdfSignatureAppearance$1 */
    static /* synthetic */ class C15221 {

        /* renamed from: $SwitchMap$com$itextpdf$text$pdf$PdfSignatureAppearance$RenderingMode */
        static final /* synthetic */ int[] f684x1c57ccf9;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode[] r0 = com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f684x1c57ccf9 = r0
                com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r1 = com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.NAME_AND_DESCRIPTION     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f684x1c57ccf9     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r1 = com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f684x1c57ccf9     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.PdfSignatureAppearance$RenderingMode r1 = com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.GRAPHIC     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfSignatureAppearance.C15221.<clinit>():void");
        }
    }

    private void createBlankN0() {
        PdfTemplate[] pdfTemplateArr = this.app;
        PdfTemplate pdfTemplate = new PdfTemplate(this.writer);
        pdfTemplateArr[0] = pdfTemplate;
        pdfTemplate.setBoundingBox(new Rectangle(100.0f, 100.0f));
        this.writer.addDirectTemplateSimple(pdfTemplate, new PdfName("n0"));
        pdfTemplate.setLiteral("% DSBlank\n");
    }

    public PdfStamper getStamper() {
        return this.stamper;
    }

    /* access modifiers changed from: package-private */
    public void setStamper(PdfStamper pdfStamper) {
        this.stamper = pdfStamper;
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer getSigout() {
        return this.sigout;
    }

    /* access modifiers changed from: package-private */
    public void setSigout(ByteBuffer byteBuffer) {
        this.sigout = byteBuffer;
    }

    /* access modifiers changed from: package-private */
    public OutputStream getOriginalout() {
        return this.originalout;
    }

    /* access modifiers changed from: package-private */
    public void setOriginalout(OutputStream outputStream) {
        this.originalout = outputStream;
    }

    public File getTempFile() {
        return this.tempFile;
    }

    /* access modifiers changed from: package-private */
    public void setTempFile(File file) {
        this.tempFile = file;
    }

    public PdfSigLockDictionary getFieldLockDict() {
        return this.fieldLock;
    }

    public void setFieldLockDict(PdfSigLockDictionary pdfSigLockDictionary) {
        this.fieldLock = pdfSigLockDictionary;
    }

    public boolean isPreClosed() {
        return this.preClosed;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:66:0x02af */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void preClose(java.util.HashMap<com.itextpdf.text.pdf.PdfName, java.lang.Integer> r14) throws java.io.IOException, com.itextpdf.text.DocumentException {
        /*
            r13 = this;
            boolean r0 = r13.preClosed
            r1 = 0
            if (r0 != 0) goto L_0x02bd
            com.itextpdf.text.pdf.PdfStamper r0 = r13.stamper
            r0.mergeVerification()
            r0 = 1
            r13.preClosed = r0
            com.itextpdf.text.pdf.PdfStamperImp r2 = r13.writer
            com.itextpdf.text.pdf.AcroFields r2 = r2.getAcroFields()
            java.lang.String r3 = r13.getFieldName()
            boolean r4 = r2.doesSignatureFieldExist(r3)
            com.itextpdf.text.pdf.PdfStamperImp r5 = r13.writer
            com.itextpdf.text.pdf.PdfIndirectReference r5 = r5.getPdfIndirectReference()
            com.itextpdf.text.pdf.PdfStamperImp r6 = r13.writer
            r7 = 3
            r6.setSigFlags(r7)
            if (r4 == 0) goto L_0x00a7
            com.itextpdf.text.pdf.AcroFields$Item r2 = r2.getFieldItem(r3)
            com.itextpdf.text.pdf.PdfDictionary r2 = r2.getWidget(r1)
            com.itextpdf.text.pdf.PdfStamperImp r3 = r13.writer
            r3.markUsed((com.itextpdf.text.pdf.PdfObject) r2)
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.LOCK
            com.itextpdf.text.pdf.PdfDictionary r3 = r2.getAsDict(r3)
            if (r3 != 0) goto L_0x0055
            com.itextpdf.text.pdf.PdfSigLockDictionary r4 = r13.fieldLock
            if (r4 == 0) goto L_0x0055
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.LOCK
            com.itextpdf.text.pdf.PdfStamperImp r4 = r13.writer
            com.itextpdf.text.pdf.PdfSigLockDictionary r6 = r13.fieldLock
            com.itextpdf.text.pdf.PdfIndirectObject r4 = r4.addToBody(r6)
            com.itextpdf.text.pdf.PdfIndirectReference r4 = r4.getIndirectReference()
            r2.put(r3, r4)
            com.itextpdf.text.pdf.PdfSigLockDictionary r3 = r13.fieldLock
        L_0x0055:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.f623P
            com.itextpdf.text.pdf.PdfStamperImp r6 = r13.writer
            int r8 = r13.getPage()
            com.itextpdf.text.pdf.PdfIndirectReference r6 = r6.getPageReference(r8)
            r2.put(r4, r6)
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.f664V
            r2.put(r4, r5)
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.f580F
            com.itextpdf.text.pdf.PdfObject r4 = r2.get(r4)
            com.itextpdf.text.pdf.PdfObject r4 = com.itextpdf.text.pdf.PdfReader.getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r4)
            if (r4 == 0) goto L_0x0082
            boolean r6 = r4.isNumber()
            if (r6 == 0) goto L_0x0082
            com.itextpdf.text.pdf.PdfNumber r4 = (com.itextpdf.text.pdf.PdfNumber) r4
            int r4 = r4.intValue()
            goto L_0x0083
        L_0x0082:
            r4 = 0
        L_0x0083:
            r4 = r4 | 128(0x80, float:1.794E-43)
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.f580F
            com.itextpdf.text.pdf.PdfNumber r8 = new com.itextpdf.text.pdf.PdfNumber
            r8.<init>((int) r4)
            r2.put(r6, r8)
            com.itextpdf.text.pdf.PdfDictionary r4 = new com.itextpdf.text.pdf.PdfDictionary
            r4.<init>()
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.f610N
            com.itextpdf.text.pdf.PdfTemplate r8 = r13.getAppearance()
            com.itextpdf.text.pdf.PdfIndirectReference r8 = r8.getIndirectReference()
            r4.put(r6, r8)
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.f549AP
            r2.put(r6, r4)
            goto L_0x0100
        L_0x00a7:
            com.itextpdf.text.pdf.PdfStamperImp r2 = r13.writer
            com.itextpdf.text.pdf.PdfFormField r2 = com.itextpdf.text.pdf.PdfFormField.createSignature(r2)
            r2.setFieldName(r3)
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.f664V
            r2.put(r3, r5)
            r3 = 132(0x84, float:1.85E-43)
            r2.setFlags(r3)
            com.itextpdf.text.pdf.PdfSigLockDictionary r3 = r13.fieldLock
            r4 = 0
            if (r3 == 0) goto L_0x00d3
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.LOCK
            com.itextpdf.text.pdf.PdfStamperImp r6 = r13.writer
            com.itextpdf.text.pdf.PdfSigLockDictionary r8 = r13.fieldLock
            com.itextpdf.text.pdf.PdfIndirectObject r6 = r6.addToBody(r8)
            com.itextpdf.text.pdf.PdfIndirectReference r6 = r6.getIndirectReference()
            r2.put(r3, r6)
            com.itextpdf.text.pdf.PdfSigLockDictionary r3 = r13.fieldLock
            goto L_0x00d4
        L_0x00d3:
            r3 = r4
        L_0x00d4:
            int r6 = r13.getPage()
            boolean r8 = r13.isInvisible()
            if (r8 != 0) goto L_0x00e6
            com.itextpdf.text.Rectangle r8 = r13.getPageRect()
            r2.setWidget(r8, r4)
            goto L_0x00ef
        L_0x00e6:
            com.itextpdf.text.Rectangle r8 = new com.itextpdf.text.Rectangle
            r9 = 0
            r8.<init>(r9, r9)
            r2.setWidget(r8, r4)
        L_0x00ef:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfAnnotation.APPEARANCE_NORMAL
            com.itextpdf.text.pdf.PdfTemplate r8 = r13.getAppearance()
            r2.setAppearance(r4, r8)
            r2.setPage(r6)
            com.itextpdf.text.pdf.PdfStamperImp r4 = r13.writer
            r4.addAnnotation((com.itextpdf.text.pdf.PdfAnnotation) r2, (int) r6)
        L_0x0100:
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r13.exclusionLocations = r2
            com.itextpdf.text.pdf.PdfDictionary r2 = r13.cryptoDictionary
            if (r2 == 0) goto L_0x02b5
            com.itextpdf.text.pdf.PdfLiteral r2 = new com.itextpdf.text.pdf.PdfLiteral
            r4 = 80
            r2.<init>((int) r4)
            java.util.HashMap<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfLiteral> r4 = r13.exclusionLocations
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.BYTERANGE
            r4.put(r6, r2)
            com.itextpdf.text.pdf.PdfDictionary r4 = r13.cryptoDictionary
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.BYTERANGE
            r4.put(r6, r2)
            java.util.Set r14 = r14.entrySet()
            java.util.Iterator r14 = r14.iterator()
        L_0x0128:
            boolean r2 = r14.hasNext()
            if (r2 == 0) goto L_0x0154
            java.lang.Object r2 = r14.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r4 = r2.getKey()
            com.itextpdf.text.pdf.PdfName r4 = (com.itextpdf.text.pdf.PdfName) r4
            java.lang.Object r2 = r2.getValue()
            java.lang.Integer r2 = (java.lang.Integer) r2
            com.itextpdf.text.pdf.PdfLiteral r6 = new com.itextpdf.text.pdf.PdfLiteral
            int r2 = r2.intValue()
            r6.<init>((int) r2)
            java.util.HashMap<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfLiteral> r2 = r13.exclusionLocations
            r2.put(r4, r6)
            com.itextpdf.text.pdf.PdfDictionary r2 = r13.cryptoDictionary
            r2.put(r4, r6)
            goto L_0x0128
        L_0x0154:
            int r14 = r13.certificationLevel
            if (r14 <= 0) goto L_0x015d
            com.itextpdf.text.pdf.PdfDictionary r14 = r13.cryptoDictionary
            r13.addDocMDP(r14)
        L_0x015d:
            if (r3 == 0) goto L_0x0164
            com.itextpdf.text.pdf.PdfDictionary r14 = r13.cryptoDictionary
            r13.addFieldMDP(r14, r3)
        L_0x0164:
            com.itextpdf.text.pdf.PdfSignatureAppearance$SignatureEvent r14 = r13.signatureEvent
            if (r14 == 0) goto L_0x016d
            com.itextpdf.text.pdf.PdfDictionary r2 = r13.cryptoDictionary
            r14.getSignatureDictionary(r2)
        L_0x016d:
            com.itextpdf.text.pdf.PdfStamperImp r14 = r13.writer
            com.itextpdf.text.pdf.PdfDictionary r2 = r13.cryptoDictionary
            r14.addToBody((com.itextpdf.text.pdf.PdfObject) r2, (com.itextpdf.text.pdf.PdfIndirectReference) r5, (boolean) r1)
            int r14 = r13.certificationLevel
            if (r14 <= 0) goto L_0x0199
            com.itextpdf.text.pdf.PdfDictionary r14 = new com.itextpdf.text.pdf.PdfDictionary
            r14.<init>()
            com.itextpdf.text.pdf.PdfName r2 = new com.itextpdf.text.pdf.PdfName
            java.lang.String r3 = "DocMDP"
            r2.<init>((java.lang.String) r3)
            r14.put(r2, r5)
            com.itextpdf.text.pdf.PdfStamperImp r2 = r13.writer
            com.itextpdf.text.pdf.PdfReader r2 = r2.reader
            com.itextpdf.text.pdf.PdfDictionary r2 = r2.getCatalog()
            com.itextpdf.text.pdf.PdfName r3 = new com.itextpdf.text.pdf.PdfName
            java.lang.String r4 = "Perms"
            r3.<init>((java.lang.String) r4)
            r2.put(r3, r14)
        L_0x0199:
            com.itextpdf.text.pdf.PdfStamperImp r14 = r13.writer
            com.itextpdf.text.pdf.PdfStamper r2 = r13.stamper
            java.util.Map r2 = r2.getMoreInfo()
            r14.close(r2)
            java.util.HashMap<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfLiteral> r14 = r13.exclusionLocations
            int r14 = r14.size()
            int r14 = r14 * 2
            long[] r14 = new long[r14]
            r13.range = r14
            java.util.HashMap<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfLiteral> r14 = r13.exclusionLocations
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.BYTERANGE
            java.lang.Object r14 = r14.get(r2)
            com.itextpdf.text.pdf.PdfLiteral r14 = (com.itextpdf.text.pdf.PdfLiteral) r14
            long r2 = r14.getPosition()
            java.util.HashMap<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfLiteral> r14 = r13.exclusionLocations
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.BYTERANGE
            r14.remove(r4)
            java.util.HashMap<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.PdfLiteral> r14 = r13.exclusionLocations
            java.util.Collection r14 = r14.values()
            java.util.Iterator r14 = r14.iterator()
            r4 = 1
        L_0x01d0:
            boolean r5 = r14.hasNext()
            if (r5 == 0) goto L_0x01f1
            java.lang.Object r5 = r14.next()
            com.itextpdf.text.pdf.PdfLiteral r5 = (com.itextpdf.text.pdf.PdfLiteral) r5
            long r8 = r5.getPosition()
            long[] r6 = r13.range
            int r10 = r4 + 1
            r6[r4] = r8
            int r4 = r10 + 1
            int r5 = r5.getPosLength()
            long r11 = (long) r5
            long r11 = r11 + r8
            r6[r10] = r11
            goto L_0x01d0
        L_0x01f1:
            long[] r14 = r13.range
            int r4 = r14.length
            int r4 = r4 - r0
            java.util.Arrays.sort(r14, r0, r4)
        L_0x01f8:
            long[] r14 = r13.range
            int r4 = r14.length
            int r4 = r4 + -2
            if (r7 >= r4) goto L_0x020b
            r4 = r14[r7]
            int r6 = r7 + -1
            r8 = r14[r6]
            long r4 = r4 - r8
            r14[r7] = r4
            int r7 = r7 + 2
            goto L_0x01f8
        L_0x020b:
            java.io.File r14 = r13.tempFile
            r4 = 32
            r5 = 93
            r6 = 91
            if (r14 != 0) goto L_0x025e
            com.itextpdf.text.pdf.ByteBuffer r14 = r13.sigout
            byte[] r14 = r14.getBuffer()
            r13.bout = r14
            com.itextpdf.text.pdf.ByteBuffer r14 = r13.sigout
            int r14 = r14.size()
            r13.boutLen = r14
            long[] r7 = r13.range
            int r8 = r7.length
            int r8 = r8 - r0
            long r9 = (long) r14
            int r14 = r7.length
            int r14 = r14 + -2
            r11 = r7[r14]
            long r9 = r9 - r11
            r7[r8] = r9
            com.itextpdf.text.pdf.ByteBuffer r14 = new com.itextpdf.text.pdf.ByteBuffer
            r14.<init>()
            r14.append((char) r6)
            r0 = 0
        L_0x023b:
            long[] r6 = r13.range
            int r7 = r6.length
            if (r0 >= r7) goto L_0x024c
            r7 = r6[r0]
            com.itextpdf.text.pdf.ByteBuffer r6 = r14.append((long) r7)
            r6.append((char) r4)
            int r0 = r0 + 1
            goto L_0x023b
        L_0x024c:
            r14.append((char) r5)
            byte[] r0 = r14.getBuffer()
            byte[] r4 = r13.bout
            int r3 = (int) r2
            int r14 = r14.size()
            java.lang.System.arraycopy(r0, r1, r4, r3, r14)
            goto L_0x02a8
        L_0x025e:
            java.io.RandomAccessFile r14 = new java.io.RandomAccessFile     // Catch:{ IOException -> 0x02a9 }
            java.io.File r7 = r13.tempFile     // Catch:{ IOException -> 0x02a9 }
            java.lang.String r8 = "rw"
            r14.<init>(r7, r8)     // Catch:{ IOException -> 0x02a9 }
            r13.raf = r14     // Catch:{ IOException -> 0x02a9 }
            long r7 = r14.length()     // Catch:{ IOException -> 0x02a9 }
            long[] r14 = r13.range     // Catch:{ IOException -> 0x02a9 }
            int r9 = r14.length     // Catch:{ IOException -> 0x02a9 }
            int r9 = r9 - r0
            int r0 = r14.length     // Catch:{ IOException -> 0x02a9 }
            int r0 = r0 + -2
            r10 = r14[r0]     // Catch:{ IOException -> 0x02a9 }
            long r7 = r7 - r10
            r14[r9] = r7     // Catch:{ IOException -> 0x02a9 }
            com.itextpdf.text.pdf.ByteBuffer r14 = new com.itextpdf.text.pdf.ByteBuffer     // Catch:{ IOException -> 0x02a9 }
            r14.<init>()     // Catch:{ IOException -> 0x02a9 }
            r14.append((char) r6)     // Catch:{ IOException -> 0x02a9 }
            r0 = 0
        L_0x0282:
            long[] r6 = r13.range     // Catch:{ IOException -> 0x02a9 }
            int r7 = r6.length     // Catch:{ IOException -> 0x02a9 }
            if (r0 >= r7) goto L_0x0293
            r7 = r6[r0]     // Catch:{ IOException -> 0x02a9 }
            com.itextpdf.text.pdf.ByteBuffer r6 = r14.append((long) r7)     // Catch:{ IOException -> 0x02a9 }
            r6.append((char) r4)     // Catch:{ IOException -> 0x02a9 }
            int r0 = r0 + 1
            goto L_0x0282
        L_0x0293:
            r14.append((char) r5)     // Catch:{ IOException -> 0x02a9 }
            java.io.RandomAccessFile r0 = r13.raf     // Catch:{ IOException -> 0x02a9 }
            r0.seek(r2)     // Catch:{ IOException -> 0x02a9 }
            java.io.RandomAccessFile r0 = r13.raf     // Catch:{ IOException -> 0x02a9 }
            byte[] r2 = r14.getBuffer()     // Catch:{ IOException -> 0x02a9 }
            int r14 = r14.size()     // Catch:{ IOException -> 0x02a9 }
            r0.write(r2, r1, r14)     // Catch:{ IOException -> 0x02a9 }
        L_0x02a8:
            return
        L_0x02a9:
            r14 = move-exception
            java.io.RandomAccessFile r0 = r13.raf     // Catch:{ Exception -> 0x02af }
            r0.close()     // Catch:{ Exception -> 0x02af }
        L_0x02af:
            java.io.File r0 = r13.tempFile     // Catch:{ Exception -> 0x02b4 }
            r0.delete()     // Catch:{ Exception -> 0x02b4 }
        L_0x02b4:
            throw r14
        L_0x02b5:
            com.itextpdf.text.DocumentException r14 = new com.itextpdf.text.DocumentException
            java.lang.String r0 = "No crypto dictionary defined."
            r14.<init>((java.lang.String) r0)
            throw r14
        L_0x02bd:
            com.itextpdf.text.DocumentException r14 = new com.itextpdf.text.DocumentException
            java.lang.Object[] r0 = new java.lang.Object[r1]
            java.lang.String r1 = "document.already.pre.closed"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r1, (java.lang.Object[]) r0)
            r14.<init>((java.lang.String) r0)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfSignatureAppearance.preClose(java.util.HashMap):void");
    }

    private void addDocMDP(PdfDictionary pdfDictionary) {
        PdfDictionary pdfDictionary2 = new PdfDictionary();
        PdfDictionary pdfDictionary3 = new PdfDictionary();
        pdfDictionary3.put(PdfName.f623P, new PdfNumber(this.certificationLevel));
        pdfDictionary3.put(PdfName.f664V, new PdfName("1.2"));
        pdfDictionary3.put(PdfName.TYPE, PdfName.TRANSFORMPARAMS);
        pdfDictionary2.put(PdfName.TRANSFORMMETHOD, PdfName.DOCMDP);
        pdfDictionary2.put(PdfName.TYPE, PdfName.SIGREF);
        pdfDictionary2.put(PdfName.TRANSFORMPARAMS, pdfDictionary3);
        if (this.writer.getPdfVersion().getVersion() < '6') {
            pdfDictionary2.put(new PdfName(SecurityConstants.DigestValue), new PdfString("aa"));
            PdfArray pdfArray = new PdfArray();
            pdfArray.add((PdfObject) new PdfNumber(0));
            pdfArray.add((PdfObject) new PdfNumber(0));
            pdfDictionary2.put(new PdfName("DigestLocation"), pdfArray);
            pdfDictionary2.put(new PdfName(SecurityConstants.DigestMethod), new PdfName("MD5"));
        }
        pdfDictionary2.put(PdfName.DATA, this.writer.reader.getTrailer().get(PdfName.ROOT));
        PdfArray pdfArray2 = new PdfArray();
        pdfArray2.add((PdfObject) pdfDictionary2);
        pdfDictionary.put(PdfName.REFERENCE, pdfArray2);
    }

    private void addFieldMDP(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2) {
        PdfDictionary pdfDictionary3 = new PdfDictionary();
        PdfDictionary pdfDictionary4 = new PdfDictionary();
        pdfDictionary4.putAll(pdfDictionary2);
        pdfDictionary4.put(PdfName.TYPE, PdfName.TRANSFORMPARAMS);
        pdfDictionary4.put(PdfName.f664V, new PdfName("1.2"));
        pdfDictionary3.put(PdfName.TRANSFORMMETHOD, PdfName.FIELDMDP);
        pdfDictionary3.put(PdfName.TYPE, PdfName.SIGREF);
        pdfDictionary3.put(PdfName.TRANSFORMPARAMS, pdfDictionary4);
        pdfDictionary3.put(new PdfName(SecurityConstants.DigestValue), new PdfString("aa"));
        PdfArray pdfArray = new PdfArray();
        pdfArray.add((PdfObject) new PdfNumber(0));
        pdfArray.add((PdfObject) new PdfNumber(0));
        pdfDictionary3.put(new PdfName("DigestLocation"), pdfArray);
        pdfDictionary3.put(new PdfName(SecurityConstants.DigestMethod), new PdfName("MD5"));
        pdfDictionary3.put(PdfName.DATA, this.writer.reader.getTrailer().get(PdfName.ROOT));
        PdfArray asArray = pdfDictionary.getAsArray(PdfName.REFERENCE);
        if (asArray == null) {
            asArray = new PdfArray();
        }
        asArray.add((PdfObject) pdfDictionary3);
        pdfDictionary.put(PdfName.REFERENCE, asArray);
    }

    public void close(PdfDictionary pdfDictionary) throws IOException, DocumentException {
        try {
            if (this.preClosed) {
                ByteBuffer byteBuffer = new ByteBuffer();
                for (PdfName next : pdfDictionary.getKeys()) {
                    PdfObject pdfObject = pdfDictionary.get(next);
                    PdfLiteral pdfLiteral = this.exclusionLocations.get(next);
                    if (pdfLiteral != null) {
                        byteBuffer.reset();
                        pdfObject.toPdf((PdfWriter) null, byteBuffer);
                        if (byteBuffer.size() > pdfLiteral.getPosLength()) {
                            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.key.1.is.too.big.is.2.reserved.3", next.toString(), String.valueOf(byteBuffer.size()), String.valueOf(pdfLiteral.getPosLength())));
                        } else if (this.tempFile == null) {
                            System.arraycopy(byteBuffer.getBuffer(), 0, this.bout, (int) pdfLiteral.getPosition(), byteBuffer.size());
                        } else {
                            this.raf.seek(pdfLiteral.getPosition());
                            this.raf.write(byteBuffer.getBuffer(), 0, byteBuffer.size());
                        }
                    } else {
                        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.key.1.didn.t.reserve.space.in.preclose", next.toString()));
                    }
                }
                if (pdfDictionary.size() == this.exclusionLocations.size()) {
                    if (this.tempFile == null) {
                        this.originalout.write(this.bout, 0, this.boutLen);
                    } else if (this.originalout != null) {
                        this.raf.seek(0);
                        long length = this.raf.length();
                        byte[] bArr = new byte[8192];
                        while (length > 0) {
                            int read = this.raf.read(bArr, 0, (int) Math.min((long) 8192, length));
                            if (read >= 0) {
                                this.originalout.write(bArr, 0, read);
                                length -= (long) read;
                            } else {
                                throw new EOFException(MessageLocalization.getComposedMessage("unexpected.eof", new Object[0]));
                            }
                        }
                    }
                } else {
                    throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.update.dictionary.has.less.keys.than.required", new Object[0]));
                }
            } else {
                throw new DocumentException(MessageLocalization.getComposedMessage("preclose.must.be.called.first", new Object[0]));
            }
        } finally {
            this.writer.reader.close();
            if (this.tempFile != null) {
                try {
                    this.raf.close();
                } catch (Exception unused) {
                }
                if (this.originalout != null) {
                    try {
                        this.tempFile.delete();
                    } catch (Exception unused2) {
                    }
                }
            }
            OutputStream outputStream = this.originalout;
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception unused3) {
                }
            }
        }
    }
}
