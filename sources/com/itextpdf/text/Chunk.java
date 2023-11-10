package com.itextpdf.text;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.HyphenationEvent;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.draw.DrawInterface;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chunk implements Element, IAccessibleElement {
    public static final String ACTION = "ACTION";
    public static final String BACKGROUND = "BACKGROUND";
    public static final String CHAR_SPACING = "CHAR_SPACING";
    public static final String COLOR = "COLOR";
    public static final String ENCODING = "ENCODING";
    public static final String GENERICTAG = "GENERICTAG";
    public static final String HSCALE = "HSCALE";
    public static final String HYPHENATION = "HYPHENATION";
    public static final String IMAGE = "IMAGE";
    public static final String LINEHEIGHT = "LINEHEIGHT";
    public static final String LOCALDESTINATION = "LOCALDESTINATION";
    public static final String LOCALGOTO = "LOCALGOTO";
    public static final Chunk NEWLINE;
    public static final String NEWPAGE = "NEWPAGE";
    public static final Chunk NEXTPAGE;
    public static final String OBJECT_REPLACEMENT_CHARACTER = "￼";
    public static final String PDFANNOTATION = "PDFANNOTATION";
    public static final String REMOTEGOTO = "REMOTEGOTO";
    public static final String SEPARATOR = "SEPARATOR";
    public static final String SKEW = "SKEW";
    public static final Chunk SPACETABBING;
    public static final String SPLITCHARACTER = "SPLITCHARACTER";
    public static final String SUBSUPSCRIPT = "SUBSUPSCRIPT";
    public static final String TAB = "TAB";
    public static final Chunk TABBING;
    public static final String TABSETTINGS = "TABSETTINGS";
    public static final String TEXTRENDERMODE = "TEXTRENDERMODE";
    public static final String UNDERLINE = "UNDERLINE";
    public static final String WHITESPACE = "WHITESPACE";
    public static final String WORD_SPACING = "WORD_SPACING";
    protected HashMap<PdfName, PdfObject> accessibleAttributes;
    protected HashMap<String, Object> attributes;
    protected StringBuffer content;
    private String contentWithNoTabs;
    protected Font font;

    /* renamed from: id */
    private AccessibleElementId f436id;
    protected PdfName role;

    public boolean isContent() {
        return true;
    }

    public boolean isInline() {
        return true;
    }

    public boolean isNestable() {
        return true;
    }

    public int type() {
        return 10;
    }

    static {
        Chunk chunk = new Chunk("\n");
        NEWLINE = chunk;
        chunk.setRole(PdfName.f623P);
        Chunk chunk2 = new Chunk("");
        NEXTPAGE = chunk2;
        chunk2.setNewPage();
        Float valueOf = Float.valueOf(Float.NaN);
        TABBING = new Chunk(valueOf, false);
        SPACETABBING = new Chunk(valueOf, true);
    }

    public Chunk() {
        this.content = null;
        this.font = null;
        this.attributes = null;
        this.role = null;
        this.accessibleAttributes = null;
        this.f436id = null;
        this.contentWithNoTabs = null;
        this.content = new StringBuffer();
        this.font = new Font();
        this.role = PdfName.SPAN;
    }

    public Chunk(Chunk chunk) {
        this.content = null;
        this.font = null;
        this.attributes = null;
        this.role = null;
        this.accessibleAttributes = null;
        this.f436id = null;
        this.contentWithNoTabs = null;
        StringBuffer stringBuffer = chunk.content;
        if (stringBuffer != null) {
            this.content = new StringBuffer(stringBuffer.toString());
        }
        Font font2 = chunk.font;
        if (font2 != null) {
            this.font = new Font(font2);
        }
        if (chunk.attributes != null) {
            this.attributes = new HashMap<>(chunk.attributes);
        }
        this.role = chunk.role;
        if (chunk.accessibleAttributes != null) {
            this.accessibleAttributes = new HashMap<>(chunk.accessibleAttributes);
        }
        this.f436id = chunk.getId();
    }

    public Chunk(String str, Font font2) {
        this.content = null;
        this.font = null;
        this.attributes = null;
        this.role = null;
        this.accessibleAttributes = null;
        this.f436id = null;
        this.contentWithNoTabs = null;
        this.content = new StringBuffer(str);
        this.font = font2;
        this.role = PdfName.SPAN;
    }

    public Chunk(String str) {
        this(str, new Font());
    }

    public Chunk(char c, Font font2) {
        this.content = null;
        this.font = null;
        this.attributes = null;
        this.role = null;
        this.accessibleAttributes = null;
        this.f436id = null;
        this.contentWithNoTabs = null;
        StringBuffer stringBuffer = new StringBuffer();
        this.content = stringBuffer;
        stringBuffer.append(c);
        this.font = font2;
        this.role = PdfName.SPAN;
    }

    public Chunk(char c) {
        this(c, new Font());
    }

    public Chunk(Image image, float f, float f2) {
        this(OBJECT_REPLACEMENT_CHARACTER, new Font());
        Image instance = Image.getInstance(image);
        instance.setAbsolutePosition(Float.NaN, Float.NaN);
        setAttribute(IMAGE, new Object[]{instance, new Float(f), new Float(f2), Boolean.FALSE});
        this.role = null;
    }

    public Chunk(DrawInterface drawInterface) {
        this(drawInterface, false);
    }

    public Chunk(DrawInterface drawInterface, boolean z) {
        this(OBJECT_REPLACEMENT_CHARACTER, new Font());
        setAttribute(SEPARATOR, new Object[]{drawInterface, Boolean.valueOf(z)});
        this.role = null;
    }

    @Deprecated
    public Chunk(DrawInterface drawInterface, float f) {
        this(drawInterface, f, false);
    }

    @Deprecated
    public Chunk(DrawInterface drawInterface, float f, boolean z) {
        this(OBJECT_REPLACEMENT_CHARACTER, new Font());
        if (f >= 0.0f) {
            setAttribute(TAB, new Object[]{drawInterface, new Float(f), Boolean.valueOf(z), new Float(0.0f)});
            this.role = PdfName.ARTIFACT;
            return;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("a.tab.position.may.not.be.lower.than.0.yours.is.1", String.valueOf(f)));
    }

    private Chunk(Float f, boolean z) {
        this(OBJECT_REPLACEMENT_CHARACTER, new Font());
        if (f.floatValue() >= 0.0f) {
            setAttribute(TAB, new Object[]{f, Boolean.valueOf(z)});
            setAttribute(SPLITCHARACTER, TabSplitCharacter.TAB);
            setAttribute(TABSETTINGS, (Object) null);
            this.role = PdfName.ARTIFACT;
            return;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("a.tab.position.may.not.be.lower.than.0.yours.is.1", String.valueOf(f)));
    }

    public Chunk(Image image, float f, float f2, boolean z) {
        this(OBJECT_REPLACEMENT_CHARACTER, new Font());
        setAttribute(IMAGE, new Object[]{image, new Float(f), new Float(f2), Boolean.valueOf(z)});
        this.role = PdfName.ARTIFACT;
    }

    public boolean process(ElementListener elementListener) {
        try {
            return elementListener.add(this);
        } catch (DocumentException unused) {
            return false;
        }
    }

    public List<Chunk> getChunks() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this);
        return arrayList;
    }

    public StringBuffer append(String str) {
        this.contentWithNoTabs = null;
        StringBuffer stringBuffer = this.content;
        stringBuffer.append(str);
        return stringBuffer;
    }

    public void setFont(Font font2) {
        this.font = font2;
    }

    public Font getFont() {
        return this.font;
    }

    public String getContent() {
        if (this.contentWithNoTabs == null) {
            this.contentWithNoTabs = this.content.toString().replaceAll("\t", "");
        }
        return this.contentWithNoTabs;
    }

    public String toString() {
        return getContent();
    }

    public boolean isEmpty() {
        return this.content.toString().trim().length() == 0 && this.content.toString().indexOf("\n") == -1 && this.attributes == null;
    }

    public float getWidthPoint() {
        if (getImage() != null) {
            return getImage().getScaledWidth();
        }
        return this.font.getCalculatedBaseFont(true).getWidthPoint(getContent(), this.font.getCalculatedSize()) * getHorizontalScaling();
    }

    public boolean hasAttributes() {
        HashMap<String, Object> hashMap = this.attributes;
        return hashMap != null && !hashMap.isEmpty();
    }

    public boolean hasAccessibleAttributes() {
        HashMap<PdfName, PdfObject> hashMap = this.accessibleAttributes;
        return hashMap != null && !hashMap.isEmpty();
    }

    public HashMap<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(HashMap<String, Object> hashMap) {
        this.attributes = hashMap;
    }

    private Chunk setAttribute(String str, Object obj) {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }
        this.attributes.put(str, obj);
        return this;
    }

    public Chunk setHorizontalScaling(float f) {
        return setAttribute(HSCALE, new Float(f));
    }

    public float getHorizontalScaling() {
        Float f;
        HashMap<String, Object> hashMap = this.attributes;
        if (hashMap == null || (f = (Float) hashMap.get(HSCALE)) == null) {
            return 1.0f;
        }
        return f.floatValue();
    }

    public Chunk setUnderline(float f, float f2) {
        return setUnderline((BaseColor) null, f, 0.0f, f2, 0.0f, 0);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Chunk setUnderline(com.itextpdf.text.BaseColor r4, float r5, float r6, float r7, float r8, int r9) {
        /*
            r3 = this;
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r3.attributes
            if (r0 != 0) goto L_0x000b
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r3.attributes = r0
        L_0x000b:
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r4
            r4 = 5
            float[] r4 = new float[r4]
            r4[r2] = r5
            r5 = 1
            r4[r5] = r6
            r4[r0] = r7
            r6 = 3
            r4[r6] = r8
            r6 = 4
            float r7 = (float) r9
            r4[r6] = r7
            r1[r5] = r4
            java.util.HashMap<java.lang.String, java.lang.Object> r4 = r3.attributes
            java.lang.String r5 = "UNDERLINE"
            java.lang.Object r4 = r4.get(r5)
            java.lang.Object[][] r4 = (java.lang.Object[][]) r4
            java.lang.Object[][] r4 = (java.lang.Object[][]) r4
            java.lang.Object[][] r4 = com.itextpdf.text.Utilities.addToArray(r4, r1)
            com.itextpdf.text.Chunk r4 = r3.setAttribute(r5, r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Chunk.setUnderline(com.itextpdf.text.BaseColor, float, float, float, float, int):com.itextpdf.text.Chunk");
    }

    public Chunk setTextRise(float f) {
        return setAttribute(SUBSUPSCRIPT, new Float(f));
    }

    public float getTextRise() {
        HashMap<String, Object> hashMap = this.attributes;
        if (hashMap == null || !hashMap.containsKey(SUBSUPSCRIPT)) {
            return 0.0f;
        }
        return ((Float) this.attributes.get(SUBSUPSCRIPT)).floatValue();
    }

    public Chunk setSkew(float f, float f2) {
        return setAttribute(SKEW, new float[]{(float) Math.tan((((double) f) * 3.141592653589793d) / 180.0d), (float) Math.tan((((double) f2) * 3.141592653589793d) / 180.0d)});
    }

    public Chunk setBackground(BaseColor baseColor) {
        return setBackground(baseColor, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Chunk setBackground(com.itextpdf.text.BaseColor r4, float r5, float r6, float r7, float r8) {
        /*
            r3 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r4
            r4 = 4
            float[] r4 = new float[r4]
            r4[r2] = r5
            r5 = 1
            r4[r5] = r6
            r4[r0] = r7
            r6 = 3
            r4[r6] = r8
            r1[r5] = r4
            java.lang.String r4 = "BACKGROUND"
            com.itextpdf.text.Chunk r4 = r3.setAttribute(r4, r1)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Chunk.setBackground(com.itextpdf.text.BaseColor, float, float, float, float):com.itextpdf.text.Chunk");
    }

    public Chunk setTextRenderMode(int i, float f, BaseColor baseColor) {
        return setAttribute(TEXTRENDERMODE, new Object[]{Integer.valueOf(i), new Float(f), baseColor});
    }

    public Chunk setSplitCharacter(SplitCharacter splitCharacter) {
        return setAttribute(SPLITCHARACTER, splitCharacter);
    }

    public Chunk setHyphenation(HyphenationEvent hyphenationEvent) {
        return setAttribute(HYPHENATION, hyphenationEvent);
    }

    public Chunk setRemoteGoto(String str, String str2) {
        return setAttribute(REMOTEGOTO, new Object[]{str, str2});
    }

    public Chunk setRemoteGoto(String str, int i) {
        return setAttribute(REMOTEGOTO, new Object[]{str, Integer.valueOf(i)});
    }

    public Chunk setLocalGoto(String str) {
        return setAttribute(LOCALGOTO, str);
    }

    public Chunk setLocalDestination(String str) {
        return setAttribute(LOCALDESTINATION, str);
    }

    public Chunk setGenericTag(String str) {
        return setAttribute(GENERICTAG, str);
    }

    public Chunk setLineHeight(float f) {
        return setAttribute(LINEHEIGHT, Float.valueOf(f));
    }

    public Image getImage() {
        Object[] objArr;
        HashMap<String, Object> hashMap = this.attributes;
        if (hashMap == null || (objArr = (Object[]) hashMap.get(IMAGE)) == null) {
            return null;
        }
        return (Image) objArr[0];
    }

    public Chunk setAction(PdfAction pdfAction) {
        setRole(PdfName.LINK);
        return setAttribute(ACTION, pdfAction);
    }

    public Chunk setAnchor(URL url) {
        setRole(PdfName.LINK);
        String externalForm = url.toExternalForm();
        setAccessibleAttribute(PdfName.ALT, new PdfString(externalForm));
        return setAttribute(ACTION, new PdfAction(externalForm));
    }

    public Chunk setAnchor(String str) {
        setRole(PdfName.LINK);
        setAccessibleAttribute(PdfName.ALT, new PdfString(str));
        return setAttribute(ACTION, new PdfAction(str));
    }

    public Chunk setNewPage() {
        return setAttribute(NEWPAGE, (Object) null);
    }

    public Chunk setAnnotation(PdfAnnotation pdfAnnotation) {
        return setAttribute(PDFANNOTATION, pdfAnnotation);
    }

    public HyphenationEvent getHyphenation() {
        HashMap<String, Object> hashMap = this.attributes;
        if (hashMap == null) {
            return null;
        }
        return (HyphenationEvent) hashMap.get(HYPHENATION);
    }

    public Chunk setCharacterSpacing(float f) {
        return setAttribute(CHAR_SPACING, new Float(f));
    }

    public float getCharacterSpacing() {
        HashMap<String, Object> hashMap = this.attributes;
        if (hashMap == null || !hashMap.containsKey(CHAR_SPACING)) {
            return 0.0f;
        }
        return ((Float) this.attributes.get(CHAR_SPACING)).floatValue();
    }

    public Chunk setWordSpacing(float f) {
        return setAttribute(WORD_SPACING, new Float(f));
    }

    public float getWordSpacing() {
        HashMap<String, Object> hashMap = this.attributes;
        if (hashMap == null || !hashMap.containsKey(WORD_SPACING)) {
            return 0.0f;
        }
        return ((Float) this.attributes.get(WORD_SPACING)).floatValue();
    }

    public static Chunk createWhitespace(String str) {
        return createWhitespace(str, false);
    }

    public static Chunk createWhitespace(String str, boolean z) {
        if (z) {
            return new Chunk(str);
        }
        Chunk chunk = new Chunk(' ');
        chunk.setAttribute(WHITESPACE, str);
        return chunk;
    }

    public boolean isWhitespace() {
        HashMap<String, Object> hashMap = this.attributes;
        return hashMap != null && hashMap.containsKey(WHITESPACE);
    }

    @Deprecated
    public static Chunk createTabspace() {
        return createTabspace(60.0f);
    }

    @Deprecated
    public static Chunk createTabspace(float f) {
        return new Chunk(Float.valueOf(f), true);
    }

    @Deprecated
    public boolean isTabspace() {
        HashMap<String, Object> hashMap = this.attributes;
        return hashMap != null && hashMap.containsKey(TAB);
    }

    public PdfObject getAccessibleAttribute(PdfName pdfName) {
        if (getImage() != null) {
            return getImage().getAccessibleAttribute(pdfName);
        }
        HashMap<PdfName, PdfObject> hashMap = this.accessibleAttributes;
        if (hashMap != null) {
            return hashMap.get(pdfName);
        }
        return null;
    }

    public void setAccessibleAttribute(PdfName pdfName, PdfObject pdfObject) {
        if (getImage() != null) {
            getImage().setAccessibleAttribute(pdfName, pdfObject);
            return;
        }
        if (this.accessibleAttributes == null) {
            this.accessibleAttributes = new HashMap<>();
        }
        this.accessibleAttributes.put(pdfName, pdfObject);
    }

    public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
        if (getImage() != null) {
            return getImage().getAccessibleAttributes();
        }
        return this.accessibleAttributes;
    }

    public PdfName getRole() {
        if (getImage() != null) {
            return getImage().getRole();
        }
        return this.role;
    }

    public void setRole(PdfName pdfName) {
        if (getImage() != null) {
            getImage().setRole(pdfName);
        } else {
            this.role = pdfName;
        }
    }

    public AccessibleElementId getId() {
        if (this.f436id == null) {
            this.f436id = new AccessibleElementId();
        }
        return this.f436id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f436id = accessibleElementId;
    }

    public String getTextExpansion() {
        PdfObject accessibleAttribute = getAccessibleAttribute(PdfName.f578E);
        if (accessibleAttribute instanceof PdfString) {
            return ((PdfString) accessibleAttribute).toUnicodeString();
        }
        return null;
    }

    public void setTextExpansion(String str) {
        setAccessibleAttribute(PdfName.f578E, new PdfString(str));
    }
}
