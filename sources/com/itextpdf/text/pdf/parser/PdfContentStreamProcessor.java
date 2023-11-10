package com.itextpdf.text.pdf.parser;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.p018io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.CMapAwareDocumentFont;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentParser;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfLiteral;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfStream;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.p020kl.commonbase.constants.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

public class PdfContentStreamProcessor {
    public static final String DEFAULTOPERATOR = "DefaultOperator";
    private final Map<Integer, CMapAwareDocumentFont> cachedFonts = new HashMap();
    /* access modifiers changed from: private */
    public final Stack<GraphicsState> gsStack = new Stack<>();
    private final Stack<MarkedContentInfo> markedContentStack = new Stack<>();
    private final Map<String, ContentOperator> operators;
    /* access modifiers changed from: private */
    public final RenderListener renderListener;
    /* access modifiers changed from: private */
    public ResourceDictionary resources;
    /* access modifiers changed from: private */
    public Matrix textLineMatrix;
    /* access modifiers changed from: private */
    public Matrix textMatrix;
    private final Map<PdfName, XObjectDoHandler> xobjectDoHandlers;

    public PdfContentStreamProcessor(RenderListener renderListener2) {
        this.renderListener = renderListener2;
        this.operators = new HashMap();
        populateOperators();
        this.xobjectDoHandlers = new HashMap();
        populateXObjectDoHandlers();
        reset();
    }

    private void populateXObjectDoHandlers() {
        registerXObjectDoHandler(PdfName.DEFAULT, new IgnoreXObjectDoHandler());
        registerXObjectDoHandler(PdfName.FORM, new FormXObjectDoHandler());
        registerXObjectDoHandler(PdfName.IMAGE, new ImageXObjectDoHandler());
    }

    public XObjectDoHandler registerXObjectDoHandler(PdfName pdfName, XObjectDoHandler xObjectDoHandler) {
        return this.xobjectDoHandlers.put(pdfName, xObjectDoHandler);
    }

    /* access modifiers changed from: private */
    public CMapAwareDocumentFont getFont(PRIndirectReference pRIndirectReference) {
        Integer valueOf = Integer.valueOf(pRIndirectReference.getNumber());
        CMapAwareDocumentFont cMapAwareDocumentFont = this.cachedFonts.get(valueOf);
        if (cMapAwareDocumentFont != null) {
            return cMapAwareDocumentFont;
        }
        CMapAwareDocumentFont cMapAwareDocumentFont2 = new CMapAwareDocumentFont(pRIndirectReference);
        this.cachedFonts.put(valueOf, cMapAwareDocumentFont2);
        return cMapAwareDocumentFont2;
    }

    /* access modifiers changed from: private */
    public CMapAwareDocumentFont getFont(PdfDictionary pdfDictionary) {
        return new CMapAwareDocumentFont(pdfDictionary);
    }

    private void populateOperators() {
        registerContentOperator(DEFAULTOPERATOR, new IgnoreOperatorContentOperator());
        registerContentOperator("q", new PushGraphicsState());
        registerContentOperator("Q", new PopGraphicsState());
        registerContentOperator("g", new SetGrayFill());
        registerContentOperator("G", new SetGrayStroke());
        registerContentOperator("rg", new SetRGBFill());
        registerContentOperator("RG", new SetRGBStroke());
        registerContentOperator("k", new SetCMYKFill());
        registerContentOperator("K", new SetCMYKStroke());
        registerContentOperator("cs", new SetColorSpaceFill());
        registerContentOperator("CS", new SetColorSpaceStroke());
        registerContentOperator("sc", new SetColorFill());
        registerContentOperator("SC", new SetColorStroke());
        registerContentOperator("scn", new SetColorFill());
        registerContentOperator("SCN", new SetColorStroke());
        registerContentOperator("cm", new ModifyCurrentTransformationMatrix());
        registerContentOperator("gs", new ProcessGraphicsStateResource());
        SetTextCharacterSpacing setTextCharacterSpacing = new SetTextCharacterSpacing();
        registerContentOperator("Tc", setTextCharacterSpacing);
        SetTextWordSpacing setTextWordSpacing = new SetTextWordSpacing();
        registerContentOperator("Tw", setTextWordSpacing);
        registerContentOperator("Tz", new SetTextHorizontalScaling());
        SetTextLeading setTextLeading = new SetTextLeading();
        registerContentOperator("TL", setTextLeading);
        registerContentOperator("Tf", new SetTextFont());
        registerContentOperator("Tr", new SetTextRenderMode());
        registerContentOperator("Ts", new SetTextRise());
        registerContentOperator(Constants.f839BT, new BeginText());
        registerContentOperator("ET", new EndText());
        registerContentOperator("BMC", new BeginMarkedContent());
        registerContentOperator("BDC", new BeginMarkedContentDictionary());
        registerContentOperator("EMC", new EndMarkedContent());
        TextMoveStartNextLine textMoveStartNextLine = new TextMoveStartNextLine();
        registerContentOperator("Td", textMoveStartNextLine);
        registerContentOperator("TD", new TextMoveStartNextLineWithLeading(textMoveStartNextLine, setTextLeading));
        registerContentOperator("Tm", new TextSetTextMatrix());
        TextMoveNextLine textMoveNextLine = new TextMoveNextLine(textMoveStartNextLine);
        registerContentOperator("T*", textMoveNextLine);
        ShowText showText = new ShowText();
        registerContentOperator("Tj", showText);
        MoveNextLineAndShowText moveNextLineAndShowText = new MoveNextLineAndShowText(textMoveNextLine, showText);
        registerContentOperator("'", moveNextLineAndShowText);
        registerContentOperator("\"", new MoveNextLineAndShowTextWithSpacing(setTextWordSpacing, setTextCharacterSpacing, moveNextLineAndShowText));
        registerContentOperator("TJ", new ShowTextArray());
        registerContentOperator("Do", new C1527Do());
        registerContentOperator("w", new SetLineWidth());
        registerContentOperator("J", new SetLineCap());
        registerContentOperator("j", new SetLineJoin());
        registerContentOperator("M", new SetMiterLimit());
        registerContentOperator("d", new SetLineDashPattern());
        if (this.renderListener instanceof ExtRenderListener) {
            registerContentOperator("m", new MoveTo());
            registerContentOperator("l", new LineTo());
            registerContentOperator("c", new Curve());
            registerContentOperator("v", new CurveFirstPointDuplicated());
            registerContentOperator("y", new CurveFourhPointDuplicated());
            registerContentOperator("h", new CloseSubpath());
            registerContentOperator("re", new Rectangle());
            registerContentOperator(ExifInterface.LATITUDE_SOUTH, new PaintPath(1, -1, false));
            registerContentOperator(HtmlTags.f490S, new PaintPath(1, -1, true));
            registerContentOperator("f", new PaintPath(2, 1, false));
            registerContentOperator("F", new PaintPath(2, 1, false));
            registerContentOperator("f*", new PaintPath(2, 2, false));
            registerContentOperator("B", new PaintPath(3, 1, false));
            registerContentOperator("B*", new PaintPath(3, 2, false));
            registerContentOperator(HtmlTags.f476B, new PaintPath(3, 1, true));
            registerContentOperator("b*", new PaintPath(3, 2, true));
            registerContentOperator("n", new PaintPath(0, -1, false));
            registerContentOperator(ExifInterface.LONGITUDE_WEST, new ClipPath(1));
            registerContentOperator("W*", new ClipPath(2));
        }
    }

    public ContentOperator registerContentOperator(String str, ContentOperator contentOperator) {
        return this.operators.put(str, contentOperator);
    }

    public Collection<String> getRegisteredOperatorStrings() {
        return new ArrayList(this.operators.keySet());
    }

    public void reset() {
        this.gsStack.removeAllElements();
        this.gsStack.add(new GraphicsState());
        this.textMatrix = null;
        this.textLineMatrix = null;
        this.resources = new ResourceDictionary();
    }

    /* renamed from: gs */
    public GraphicsState mo23677gs() {
        return this.gsStack.peek();
    }

    private void invokeOperator(PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
        ContentOperator contentOperator = this.operators.get(pdfLiteral.toString());
        if (contentOperator == null) {
            contentOperator = this.operators.get(DEFAULTOPERATOR);
        }
        contentOperator.invoke(this, pdfLiteral, arrayList);
    }

    /* access modifiers changed from: private */
    public void beginMarkedContent(PdfName pdfName, PdfDictionary pdfDictionary) {
        this.markedContentStack.push(new MarkedContentInfo(pdfName, pdfDictionary));
    }

    /* access modifiers changed from: private */
    public void endMarkedContent() {
        this.markedContentStack.pop();
    }

    /* access modifiers changed from: private */
    public void beginText() {
        this.renderListener.beginTextBlock();
    }

    /* access modifiers changed from: private */
    public void endText() {
        this.renderListener.endTextBlock();
    }

    /* access modifiers changed from: private */
    public void displayPdfString(PdfString pdfString) {
        TextRenderInfo textRenderInfo = new TextRenderInfo(pdfString, mo23677gs(), this.textMatrix, this.markedContentStack);
        this.renderListener.renderText(textRenderInfo);
        this.textMatrix = new Matrix(textRenderInfo.getUnscaledWidth(), 0.0f).multiply(this.textMatrix);
    }

    /* access modifiers changed from: private */
    public void displayXObject(PdfName pdfName) throws IOException {
        PdfDictionary asDict = this.resources.getAsDict(PdfName.XOBJECT);
        PdfObject directObject = asDict.getDirectObject(pdfName);
        PdfStream pdfStream = (PdfStream) directObject;
        PdfName asName = pdfStream.getAsName(PdfName.SUBTYPE);
        if (directObject.isStream()) {
            XObjectDoHandler xObjectDoHandler = this.xobjectDoHandlers.get(asName);
            if (xObjectDoHandler == null) {
                xObjectDoHandler = this.xobjectDoHandlers.get(PdfName.DEFAULT);
            }
            xObjectDoHandler.handleXObject(this, pdfStream, asDict.getAsIndirectObject(pdfName));
            return;
        }
        throw new IllegalStateException(MessageLocalization.getComposedMessage("XObject.1.is.not.a.stream", pdfName));
    }

    /* access modifiers changed from: private */
    public void paintPath(int i, int i2, boolean z) {
        if (z) {
            modifyPath(6, (List<Float>) null);
        }
        ((ExtRenderListener) this.renderListener).renderPath(new PathPaintingRenderInfo(i, i2, mo23677gs()));
    }

    /* access modifiers changed from: private */
    public void modifyPath(int i, List<Float> list) {
        ((ExtRenderListener) this.renderListener).modifyPath(new PathConstructionRenderInfo(i, list, mo23677gs().getCtm()));
    }

    /* access modifiers changed from: private */
    public void clipPath(int i) {
        ((ExtRenderListener) this.renderListener).clipPath(i);
    }

    /* access modifiers changed from: private */
    public void applyTextAdjust(float f) {
        this.textMatrix = new Matrix(((-f) / 1000.0f) * mo23677gs().fontSize * mo23677gs().horizontalScaling, 0.0f).multiply(this.textMatrix);
    }

    public void processContent(byte[] bArr, PdfDictionary pdfDictionary) {
        this.resources.push(pdfDictionary);
        try {
            PdfContentParser pdfContentParser = new PdfContentParser(new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(bArr))));
            ArrayList arrayList = new ArrayList();
            while (pdfContentParser.parse(arrayList).size() > 0) {
                PdfLiteral pdfLiteral = (PdfLiteral) arrayList.get(arrayList.size() - 1);
                if ("BI".equals(pdfLiteral.toString())) {
                    PdfDictionary asDict = pdfDictionary != null ? pdfDictionary.getAsDict(PdfName.COLORSPACE) : null;
                    handleInlineImage(InlineImageUtils.parseInlineImage(pdfContentParser, asDict), asDict);
                } else {
                    invokeOperator(pdfLiteral, arrayList);
                }
            }
            this.resources.pop();
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: protected */
    public void handleInlineImage(InlineImageInfo inlineImageInfo, PdfDictionary pdfDictionary) {
        this.renderListener.renderImage(ImageRenderInfo.createForEmbeddedImage(mo23677gs(), inlineImageInfo, pdfDictionary));
    }

    private static class ResourceDictionary extends PdfDictionary {
        private final List<PdfDictionary> resourcesStack = new ArrayList();

        public void push(PdfDictionary pdfDictionary) {
            this.resourcesStack.add(pdfDictionary);
        }

        public void pop() {
            List<PdfDictionary> list = this.resourcesStack;
            list.remove(list.size() - 1);
        }

        public PdfObject getDirectObject(PdfName pdfName) {
            PdfObject directObject;
            for (int size = this.resourcesStack.size() - 1; size >= 0; size--) {
                PdfDictionary pdfDictionary = this.resourcesStack.get(size);
                if (pdfDictionary != null && (directObject = pdfDictionary.getDirectObject(pdfName)) != null) {
                    return directObject;
                }
            }
            return super.getDirectObject(pdfName);
        }
    }

    private static class IgnoreOperatorContentOperator implements ContentOperator {
        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
        }

        private IgnoreOperatorContentOperator() {
        }
    }

    private static class ShowTextArray implements ContentOperator {
        private ShowTextArray() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            ListIterator<PdfObject> listIterator = ((PdfArray) arrayList.get(0)).listIterator();
            while (listIterator.hasNext()) {
                PdfObject next = listIterator.next();
                if (next instanceof PdfString) {
                    pdfContentStreamProcessor.displayPdfString((PdfString) next);
                } else {
                    pdfContentStreamProcessor.applyTextAdjust(((PdfNumber) next).floatValue());
                }
            }
        }
    }

    private static class MoveNextLineAndShowTextWithSpacing implements ContentOperator {
        private final MoveNextLineAndShowText moveNextLineAndShowText;
        private final SetTextCharacterSpacing setTextCharacterSpacing;
        private final SetTextWordSpacing setTextWordSpacing;

        public MoveNextLineAndShowTextWithSpacing(SetTextWordSpacing setTextWordSpacing2, SetTextCharacterSpacing setTextCharacterSpacing2, MoveNextLineAndShowText moveNextLineAndShowText2) {
            this.setTextWordSpacing = setTextWordSpacing2;
            this.setTextCharacterSpacing = setTextCharacterSpacing2;
            this.moveNextLineAndShowText = moveNextLineAndShowText2;
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            ArrayList arrayList2 = new ArrayList(1);
            arrayList2.add(0, (PdfNumber) arrayList.get(0));
            this.setTextWordSpacing.invoke(pdfContentStreamProcessor, (PdfLiteral) null, arrayList2);
            ArrayList arrayList3 = new ArrayList(1);
            arrayList3.add(0, (PdfNumber) arrayList.get(1));
            this.setTextCharacterSpacing.invoke(pdfContentStreamProcessor, (PdfLiteral) null, arrayList3);
            ArrayList arrayList4 = new ArrayList(1);
            arrayList4.add(0, (PdfString) arrayList.get(2));
            this.moveNextLineAndShowText.invoke(pdfContentStreamProcessor, (PdfLiteral) null, arrayList4);
        }
    }

    private static class MoveNextLineAndShowText implements ContentOperator {
        private final ShowText showText;
        private final TextMoveNextLine textMoveNextLine;

        public MoveNextLineAndShowText(TextMoveNextLine textMoveNextLine2, ShowText showText2) {
            this.textMoveNextLine = textMoveNextLine2;
            this.showText = showText2;
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            this.textMoveNextLine.invoke(pdfContentStreamProcessor, (PdfLiteral) null, new ArrayList(0));
            this.showText.invoke(pdfContentStreamProcessor, (PdfLiteral) null, arrayList);
        }
    }

    private static class ShowText implements ContentOperator {
        private ShowText() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.displayPdfString((PdfString) arrayList.get(0));
        }
    }

    private static class TextMoveNextLine implements ContentOperator {
        private final TextMoveStartNextLine moveStartNextLine;

        public TextMoveNextLine(TextMoveStartNextLine textMoveStartNextLine) {
            this.moveStartNextLine = textMoveStartNextLine;
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            ArrayList arrayList2 = new ArrayList(2);
            arrayList2.add(0, new PdfNumber(0));
            arrayList2.add(1, new PdfNumber(-pdfContentStreamProcessor.mo23677gs().leading));
            this.moveStartNextLine.invoke(pdfContentStreamProcessor, (PdfLiteral) null, arrayList2);
        }
    }

    private static class TextSetTextMatrix implements ContentOperator {
        private TextSetTextMatrix() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            Matrix unused = pdfContentStreamProcessor.textLineMatrix = new Matrix(((PdfNumber) arrayList.get(0)).floatValue(), ((PdfNumber) arrayList.get(1)).floatValue(), ((PdfNumber) arrayList.get(2)).floatValue(), ((PdfNumber) arrayList.get(3)).floatValue(), ((PdfNumber) arrayList.get(4)).floatValue(), ((PdfNumber) arrayList.get(5)).floatValue());
            Matrix unused2 = pdfContentStreamProcessor.textMatrix = pdfContentStreamProcessor.textLineMatrix;
        }
    }

    private static class TextMoveStartNextLineWithLeading implements ContentOperator {
        private final TextMoveStartNextLine moveStartNextLine;
        private final SetTextLeading setTextLeading;

        public TextMoveStartNextLineWithLeading(TextMoveStartNextLine textMoveStartNextLine, SetTextLeading setTextLeading2) {
            this.moveStartNextLine = textMoveStartNextLine;
            this.setTextLeading = setTextLeading2;
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            float floatValue = ((PdfNumber) arrayList.get(1)).floatValue();
            ArrayList arrayList2 = new ArrayList(1);
            arrayList2.add(0, new PdfNumber(-floatValue));
            this.setTextLeading.invoke(pdfContentStreamProcessor, (PdfLiteral) null, arrayList2);
            this.moveStartNextLine.invoke(pdfContentStreamProcessor, (PdfLiteral) null, arrayList);
        }
    }

    private static class TextMoveStartNextLine implements ContentOperator {
        private TextMoveStartNextLine() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            Matrix unused = pdfContentStreamProcessor.textMatrix = new Matrix(((PdfNumber) arrayList.get(0)).floatValue(), ((PdfNumber) arrayList.get(1)).floatValue()).multiply(pdfContentStreamProcessor.textLineMatrix);
            Matrix unused2 = pdfContentStreamProcessor.textLineMatrix = pdfContentStreamProcessor.textMatrix;
        }
    }

    private static class SetTextFont implements ContentOperator {
        private SetTextFont() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            CMapAwareDocumentFont cMapAwareDocumentFont;
            float floatValue = ((PdfNumber) arrayList.get(1)).floatValue();
            PdfObject pdfObject = pdfContentStreamProcessor.resources.getAsDict(PdfName.FONT).get((PdfName) arrayList.get(0));
            if (pdfObject instanceof PdfDictionary) {
                cMapAwareDocumentFont = pdfContentStreamProcessor.getFont((PdfDictionary) pdfObject);
            } else {
                cMapAwareDocumentFont = pdfContentStreamProcessor.getFont((PRIndirectReference) pdfObject);
            }
            pdfContentStreamProcessor.mo23677gs().font = cMapAwareDocumentFont;
            pdfContentStreamProcessor.mo23677gs().fontSize = floatValue;
        }
    }

    private static class SetTextRenderMode implements ContentOperator {
        private SetTextRenderMode() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().renderMode = ((PdfNumber) arrayList.get(0)).intValue();
        }
    }

    private static class SetTextRise implements ContentOperator {
        private SetTextRise() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().rise = ((PdfNumber) arrayList.get(0)).floatValue();
        }
    }

    private static class SetTextLeading implements ContentOperator {
        private SetTextLeading() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().leading = ((PdfNumber) arrayList.get(0)).floatValue();
        }
    }

    private static class SetTextHorizontalScaling implements ContentOperator {
        private SetTextHorizontalScaling() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().horizontalScaling = ((PdfNumber) arrayList.get(0)).floatValue() / 100.0f;
        }
    }

    private static class SetTextCharacterSpacing implements ContentOperator {
        private SetTextCharacterSpacing() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().characterSpacing = ((PdfNumber) arrayList.get(0)).floatValue();
        }
    }

    private static class SetTextWordSpacing implements ContentOperator {
        private SetTextWordSpacing() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().wordSpacing = ((PdfNumber) arrayList.get(0)).floatValue();
        }
    }

    private static class ProcessGraphicsStateResource implements ContentOperator {
        private ProcessGraphicsStateResource() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            PdfName pdfName = (PdfName) arrayList.get(0);
            PdfDictionary asDict = pdfContentStreamProcessor.resources.getAsDict(PdfName.EXTGSTATE);
            if (asDict != null) {
                PdfDictionary asDict2 = asDict.getAsDict(pdfName);
                if (asDict2 != null) {
                    PdfArray asArray = asDict2.getAsArray(PdfName.FONT);
                    if (asArray != null) {
                        CMapAwareDocumentFont access$5300 = pdfContentStreamProcessor.getFont((PRIndirectReference) asArray.getPdfObject(0));
                        float floatValue = asArray.getAsNumber(1).floatValue();
                        pdfContentStreamProcessor.mo23677gs().font = access$5300;
                        pdfContentStreamProcessor.mo23677gs().fontSize = floatValue;
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.is.an.unknown.graphics.state.dictionary", pdfName));
            }
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("resources.do.not.contain.extgstate.entry.unable.to.process.operator.1", pdfLiteral));
        }
    }

    private static class PushGraphicsState implements ContentOperator {
        private PushGraphicsState() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.gsStack.push(new GraphicsState((GraphicsState) pdfContentStreamProcessor.gsStack.peek()));
        }
    }

    private static class ModifyCurrentTransformationMatrix implements ContentOperator {
        private ModifyCurrentTransformationMatrix() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            Matrix matrix = new Matrix(((PdfNumber) arrayList.get(0)).floatValue(), ((PdfNumber) arrayList.get(1)).floatValue(), ((PdfNumber) arrayList.get(2)).floatValue(), ((PdfNumber) arrayList.get(3)).floatValue(), ((PdfNumber) arrayList.get(4)).floatValue(), ((PdfNumber) arrayList.get(5)).floatValue());
            GraphicsState graphicsState = (GraphicsState) pdfContentStreamProcessor.gsStack.peek();
            graphicsState.ctm = matrix.multiply(graphicsState.ctm);
        }
    }

    /* access modifiers changed from: private */
    public static BaseColor getColor(PdfName pdfName, List<PdfObject> list) {
        if (PdfName.DEVICEGRAY.equals(pdfName)) {
            return getColor(1, list);
        }
        if (PdfName.DEVICERGB.equals(pdfName)) {
            return getColor(3, list);
        }
        if (PdfName.DEVICECMYK.equals(pdfName)) {
            return getColor(4, list);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static BaseColor getColor(int i, List<PdfObject> list) {
        float[] fArr = new float[i];
        for (int i2 = 0; i2 < i; i2++) {
            fArr[i2] = ((PdfNumber) list.get(i2)).floatValue();
            if (fArr[i2] > 1.0f) {
                fArr[i2] = 1.0f;
            } else if (fArr[i2] < 0.0f) {
                fArr[i2] = 0.0f;
            }
        }
        if (i == 1) {
            return new GrayColor(fArr[0]);
        }
        if (i == 3) {
            return new BaseColor(fArr[0], fArr[1], fArr[2]);
        }
        if (i != 4) {
            return null;
        }
        return new CMYKColor(fArr[0], fArr[1], fArr[2], fArr[3]);
    }

    private static class SetGrayFill implements ContentOperator {
        private SetGrayFill() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().fillColor = PdfContentStreamProcessor.getColor(1, (List<PdfObject>) arrayList);
        }
    }

    private static class SetGrayStroke implements ContentOperator {
        private SetGrayStroke() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().strokeColor = PdfContentStreamProcessor.getColor(1, (List<PdfObject>) arrayList);
        }
    }

    private static class SetRGBFill implements ContentOperator {
        private SetRGBFill() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().fillColor = PdfContentStreamProcessor.getColor(3, (List<PdfObject>) arrayList);
        }
    }

    private static class SetRGBStroke implements ContentOperator {
        private SetRGBStroke() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().strokeColor = PdfContentStreamProcessor.getColor(3, (List<PdfObject>) arrayList);
        }
    }

    private static class SetCMYKFill implements ContentOperator {
        private SetCMYKFill() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().fillColor = PdfContentStreamProcessor.getColor(4, (List<PdfObject>) arrayList);
        }
    }

    private static class SetCMYKStroke implements ContentOperator {
        private SetCMYKStroke() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().strokeColor = PdfContentStreamProcessor.getColor(4, (List<PdfObject>) arrayList);
        }
    }

    private static class SetColorSpaceFill implements ContentOperator {
        private SetColorSpaceFill() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().colorSpaceFill = (PdfName) arrayList.get(0);
        }
    }

    private static class SetColorSpaceStroke implements ContentOperator {
        private SetColorSpaceStroke() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().colorSpaceStroke = (PdfName) arrayList.get(0);
        }
    }

    private static class SetColorFill implements ContentOperator {
        private SetColorFill() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().fillColor = PdfContentStreamProcessor.getColor(pdfContentStreamProcessor.mo23677gs().colorSpaceFill, (List<PdfObject>) arrayList);
        }
    }

    private static class SetColorStroke implements ContentOperator {
        private SetColorStroke() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().strokeColor = PdfContentStreamProcessor.getColor(pdfContentStreamProcessor.mo23677gs().colorSpaceStroke, (List<PdfObject>) arrayList);
        }
    }

    private static class PopGraphicsState implements ContentOperator {
        private PopGraphicsState() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.gsStack.pop();
        }
    }

    private static class BeginText implements ContentOperator {
        private BeginText() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            Matrix unused = pdfContentStreamProcessor.textMatrix = new Matrix();
            Matrix unused2 = pdfContentStreamProcessor.textLineMatrix = pdfContentStreamProcessor.textMatrix;
            pdfContentStreamProcessor.beginText();
        }
    }

    private static class EndText implements ContentOperator {
        private EndText() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            Matrix unused = pdfContentStreamProcessor.textMatrix = null;
            Matrix unused2 = pdfContentStreamProcessor.textLineMatrix = null;
            pdfContentStreamProcessor.endText();
        }
    }

    private static class BeginMarkedContent implements ContentOperator {
        private BeginMarkedContent() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.beginMarkedContent((PdfName) arrayList.get(0), new PdfDictionary());
        }
    }

    private static class BeginMarkedContentDictionary implements ContentOperator {
        private BeginMarkedContentDictionary() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.beginMarkedContent((PdfName) arrayList.get(0), getPropertiesDictionary(arrayList.get(1), pdfContentStreamProcessor.resources));
        }

        private PdfDictionary getPropertiesDictionary(PdfObject pdfObject, ResourceDictionary resourceDictionary) {
            if (pdfObject.isDictionary()) {
                return (PdfDictionary) pdfObject;
            }
            return resourceDictionary.getAsDict((PdfName) pdfObject);
        }
    }

    private static class EndMarkedContent implements ContentOperator {
        private EndMarkedContent() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.endMarkedContent();
        }
    }

    /* renamed from: com.itextpdf.text.pdf.parser.PdfContentStreamProcessor$Do */
    private static class C1527Do implements ContentOperator {
        private C1527Do() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws IOException {
            pdfContentStreamProcessor.displayXObject((PdfName) arrayList.get(0));
        }
    }

    private static class SetLineWidth implements ContentOperator {
        private SetLineWidth() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().setLineWidth(((PdfNumber) arrayList.get(0)).floatValue());
        }
    }

    private class SetLineCap implements ContentOperator {
        private SetLineCap() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().setLineCapStyle(((PdfNumber) arrayList.get(0)).intValue());
        }
    }

    private class SetLineJoin implements ContentOperator {
        private SetLineJoin() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().setLineJoinStyle(((PdfNumber) arrayList.get(0)).intValue());
        }
    }

    private class SetMiterLimit implements ContentOperator {
        private SetMiterLimit() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().setMiterLimit(((PdfNumber) arrayList.get(0)).floatValue());
        }
    }

    private class SetLineDashPattern implements ContentOperator {
        private SetLineDashPattern() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) {
            pdfContentStreamProcessor.mo23677gs().setLineDashPattern(new LineDashPattern((PdfArray) arrayList.get(0), ((PdfNumber) arrayList.get(1)).floatValue()));
        }
    }

    private static class MoveTo implements ContentOperator {
        private MoveTo() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.modifyPath(1, Arrays.asList(new Float[]{Float.valueOf(((PdfNumber) arrayList.get(0)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(1)).floatValue())}));
        }
    }

    private static class LineTo implements ContentOperator {
        private LineTo() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.modifyPath(2, Arrays.asList(new Float[]{Float.valueOf(((PdfNumber) arrayList.get(0)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(1)).floatValue())}));
        }
    }

    private static class Curve implements ContentOperator {
        private Curve() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.modifyPath(3, Arrays.asList(new Float[]{Float.valueOf(((PdfNumber) arrayList.get(0)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(1)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(2)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(3)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(4)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(5)).floatValue())}));
        }
    }

    private static class CurveFirstPointDuplicated implements ContentOperator {
        private CurveFirstPointDuplicated() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.modifyPath(4, Arrays.asList(new Float[]{Float.valueOf(((PdfNumber) arrayList.get(0)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(1)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(2)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(3)).floatValue())}));
        }
    }

    private static class CurveFourhPointDuplicated implements ContentOperator {
        private CurveFourhPointDuplicated() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.modifyPath(5, Arrays.asList(new Float[]{Float.valueOf(((PdfNumber) arrayList.get(0)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(1)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(2)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(3)).floatValue())}));
        }
    }

    private static class CloseSubpath implements ContentOperator {
        private CloseSubpath() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.modifyPath(6, (List<Float>) null);
        }
    }

    private static class Rectangle implements ContentOperator {
        private Rectangle() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.modifyPath(7, Arrays.asList(new Float[]{Float.valueOf(((PdfNumber) arrayList.get(0)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(1)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(2)).floatValue()), Float.valueOf(((PdfNumber) arrayList.get(3)).floatValue())}));
        }
    }

    private static class PaintPath implements ContentOperator {
        private boolean close;
        private int operation;
        private int rule;

        public PaintPath(int i, int i2, boolean z) {
            this.operation = i;
            this.rule = i2;
            this.close = z;
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.paintPath(this.operation, this.rule, this.close);
        }
    }

    private static class ClipPath implements ContentOperator {
        private int rule;

        public ClipPath(int i) {
            this.rule = i;
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.clipPath(this.rule);
        }
    }

    private static class EndPath implements ContentOperator {
        private EndPath() {
        }

        public void invoke(PdfContentStreamProcessor pdfContentStreamProcessor, PdfLiteral pdfLiteral, ArrayList<PdfObject> arrayList) throws Exception {
            pdfContentStreamProcessor.paintPath(0, -1, false);
        }
    }

    private static class FormXObjectDoHandler implements XObjectDoHandler {
        private FormXObjectDoHandler() {
        }

        public void handleXObject(PdfContentStreamProcessor pdfContentStreamProcessor, PdfStream pdfStream, PdfIndirectReference pdfIndirectReference) {
            PdfDictionary asDict = pdfStream.getAsDict(PdfName.RESOURCES);
            try {
                byte[] contentBytesFromContentObject = ContentByteUtils.getContentBytesFromContentObject(pdfStream);
                PdfArray asArray = pdfStream.getAsArray(PdfName.MATRIX);
                new PushGraphicsState().invoke(pdfContentStreamProcessor, (PdfLiteral) null, (ArrayList<PdfObject>) null);
                if (asArray != null) {
                    Matrix matrix = new Matrix(asArray.getAsNumber(0).floatValue(), asArray.getAsNumber(1).floatValue(), asArray.getAsNumber(2).floatValue(), asArray.getAsNumber(3).floatValue(), asArray.getAsNumber(4).floatValue(), asArray.getAsNumber(5).floatValue());
                    pdfContentStreamProcessor.mo23677gs().ctm = matrix.multiply(pdfContentStreamProcessor.mo23677gs().ctm);
                }
                pdfContentStreamProcessor.processContent(contentBytesFromContentObject, asDict);
                new PopGraphicsState().invoke(pdfContentStreamProcessor, (PdfLiteral) null, (ArrayList<PdfObject>) null);
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            }
        }
    }

    private static class ImageXObjectDoHandler implements XObjectDoHandler {
        private ImageXObjectDoHandler() {
        }

        public void handleXObject(PdfContentStreamProcessor pdfContentStreamProcessor, PdfStream pdfStream, PdfIndirectReference pdfIndirectReference) {
            pdfContentStreamProcessor.renderListener.renderImage(ImageRenderInfo.createForXObject(pdfContentStreamProcessor.mo23677gs(), pdfIndirectReference, pdfContentStreamProcessor.resources.getAsDict(PdfName.COLORSPACE)));
        }
    }

    private static class IgnoreXObjectDoHandler implements XObjectDoHandler {
        public void handleXObject(PdfContentStreamProcessor pdfContentStreamProcessor, PdfStream pdfStream, PdfIndirectReference pdfIndirectReference) {
        }

        private IgnoreXObjectDoHandler() {
        }
    }
}
