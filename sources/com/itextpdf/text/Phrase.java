package com.itextpdf.text;

import com.itextpdf.text.Font;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.HyphenationEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Phrase extends ArrayList<Element> implements TextElementArray {
    private static final long serialVersionUID = 2643594602455068231L;
    protected Font font;
    protected HyphenationEvent hyphenation;
    protected float leading;
    protected float multipliedLeading;
    protected TabSettings tabSettings;

    public boolean isContent() {
        return true;
    }

    public boolean isNestable() {
        return true;
    }

    public int type() {
        return 11;
    }

    public Phrase() {
        this(16.0f);
    }

    public Phrase(Phrase phrase) {
        this.leading = Float.NaN;
        this.multipliedLeading = 0.0f;
        this.hyphenation = null;
        this.tabSettings = null;
        addAll(phrase);
        setLeading(phrase.getLeading(), phrase.getMultipliedLeading());
        this.font = phrase.getFont();
        this.tabSettings = phrase.getTabSettings();
        setHyphenation(phrase.getHyphenation());
    }

    public Phrase(float f) {
        this.leading = Float.NaN;
        this.multipliedLeading = 0.0f;
        this.hyphenation = null;
        this.tabSettings = null;
        this.leading = f;
        this.font = new Font();
    }

    public Phrase(Chunk chunk) {
        this.leading = Float.NaN;
        this.multipliedLeading = 0.0f;
        this.hyphenation = null;
        this.tabSettings = null;
        super.add(chunk);
        this.font = chunk.getFont();
        setHyphenation(chunk.getHyphenation());
    }

    public Phrase(float f, Chunk chunk) {
        this.leading = Float.NaN;
        this.multipliedLeading = 0.0f;
        this.hyphenation = null;
        this.tabSettings = null;
        this.leading = f;
        super.add(chunk);
        this.font = chunk.getFont();
        setHyphenation(chunk.getHyphenation());
    }

    public Phrase(String str) {
        this(Float.NaN, str, new Font());
    }

    public Phrase(String str, Font font2) {
        this(Float.NaN, str, font2);
    }

    public Phrase(float f, String str) {
        this(f, str, new Font());
    }

    public Phrase(float f, String str, Font font2) {
        this.leading = Float.NaN;
        this.multipliedLeading = 0.0f;
        this.hyphenation = null;
        this.tabSettings = null;
        this.leading = f;
        this.font = font2;
        if (str != null && str.length() != 0) {
            super.add(new Chunk(str, font2));
        }
    }

    public boolean process(ElementListener elementListener) {
        try {
            Iterator it = iterator();
            while (it.hasNext()) {
                elementListener.add((Element) it.next());
            }
            return true;
        } catch (DocumentException unused) {
            return false;
        }
    }

    public List<Chunk> getChunks() {
        ArrayList arrayList = new ArrayList();
        Iterator it = iterator();
        while (it.hasNext()) {
            arrayList.addAll(((Element) it.next()).getChunks());
        }
        return arrayList;
    }

    public void add(int i, Element element) {
        if (element != null) {
            int type = element.type();
            if (!(type == 14 || type == 17 || type == 23 || type == 29 || type == 37 || type == 50 || type == 55 || type == 666)) {
                switch (type) {
                    case 10:
                        Chunk chunk = (Chunk) element;
                        if (!this.font.isStandardFont()) {
                            chunk.setFont(this.font.difference(chunk.getFont()));
                        }
                        if (this.hyphenation != null && chunk.getHyphenation() == null && !chunk.isEmpty()) {
                            chunk.setHyphenation(this.hyphenation);
                        }
                        super.add(i, chunk);
                        return;
                    case 11:
                    case 12:
                        break;
                    default:
                        throw new ClassCastException(MessageLocalization.getComposedMessage("insertion.of.illegal.element.1", element.getClass().getName()));
                }
            }
            super.add(i, element);
        }
    }

    public boolean add(String str) {
        if (str == null) {
            return false;
        }
        return super.add(new Chunk(str, this.font));
    }

    public boolean add(Element element) {
        boolean z;
        if (element == null) {
            return false;
        }
        try {
            int type = element.type();
            if (type == 14 || type == 17 || type == 23 || type == 29 || type == 37 || type == 50 || type == 55 || type == 666) {
                return super.add(element);
            }
            switch (type) {
                case 10:
                    return addChunk((Chunk) element);
                case 11:
                case 12:
                    Iterator it = ((Phrase) element).iterator();
                    boolean z2 = true;
                    while (it.hasNext()) {
                        Element element2 = (Element) it.next();
                        if (element2 instanceof Chunk) {
                            z = addChunk((Chunk) element2);
                        } else {
                            z = add(element2);
                        }
                        z2 &= z;
                    }
                    return z2;
                default:
                    throw new ClassCastException(String.valueOf(element.type()));
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(MessageLocalization.getComposedMessage("insertion.of.illegal.element.1", e.getMessage()));
        }
    }

    public boolean addAll(Collection<? extends Element> collection) {
        for (Element add : collection) {
            add(add);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean addChunk(Chunk chunk) {
        Font font2 = chunk.getFont();
        String content = chunk.getContent();
        Font font3 = this.font;
        if (font3 != null && !font3.isStandardFont()) {
            font2 = this.font.difference(chunk.getFont());
        }
        if (size() > 0 && !chunk.hasAttributes()) {
            try {
                Chunk chunk2 = (Chunk) get(size() - 1);
                if (!chunk2.hasAttributes() && !chunk.hasAccessibleAttributes() && ((font2 == null || font2.compareTo(chunk2.getFont()) == 0) && !"".equals(chunk2.getContent().trim()) && !"".equals(content.trim()))) {
                    chunk2.append(content);
                    return true;
                }
            } catch (ClassCastException unused) {
            }
        }
        Chunk chunk3 = new Chunk(content, font2);
        chunk3.setAttributes(chunk.getAttributes());
        chunk3.role = chunk.getRole();
        chunk3.accessibleAttributes = chunk.getAccessibleAttributes();
        if (this.hyphenation != null && chunk3.getHyphenation() == null && !chunk3.isEmpty()) {
            chunk3.setHyphenation(this.hyphenation);
        }
        return super.add(chunk3);
    }

    /* access modifiers changed from: protected */
    public void addSpecial(Element element) {
        super.add(element);
    }

    public void setLeading(float f, float f2) {
        this.leading = f;
        this.multipliedLeading = f2;
    }

    public void setLeading(float f) {
        this.leading = f;
        this.multipliedLeading = 0.0f;
    }

    public void setMultipliedLeading(float f) {
        this.leading = 0.0f;
        this.multipliedLeading = f;
    }

    public void setFont(Font font2) {
        this.font = font2;
    }

    public float getLeading() {
        Font font2;
        if (!Float.isNaN(this.leading) || (font2 = this.font) == null) {
            return this.leading;
        }
        return font2.getCalculatedLeading(1.5f);
    }

    public float getMultipliedLeading() {
        return this.multipliedLeading;
    }

    public float getTotalLeading() {
        Font font2 = this.font;
        float calculatedLeading = font2 == null ? this.multipliedLeading * 12.0f : font2.getCalculatedLeading(this.multipliedLeading);
        if (calculatedLeading <= 0.0f || hasLeading()) {
            return getLeading() + calculatedLeading;
        }
        return calculatedLeading;
    }

    public boolean hasLeading() {
        return !Float.isNaN(this.leading);
    }

    public Font getFont() {
        return this.font;
    }

    public String getContent() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Chunk chunk : getChunks()) {
            stringBuffer.append(chunk.toString());
        }
        return stringBuffer.toString();
    }

    public boolean isEmpty() {
        int size = size();
        if (size == 0) {
            return true;
        }
        if (size != 1) {
            return false;
        }
        Element element = (Element) get(0);
        return element.type() == 10 && ((Chunk) element).isEmpty();
    }

    public HyphenationEvent getHyphenation() {
        return this.hyphenation;
    }

    public void setHyphenation(HyphenationEvent hyphenationEvent) {
        this.hyphenation = hyphenationEvent;
    }

    public TabSettings getTabSettings() {
        return this.tabSettings;
    }

    public void setTabSettings(TabSettings tabSettings2) {
        this.tabSettings = tabSettings2;
    }

    private Phrase(boolean z) {
        this.leading = Float.NaN;
        this.multipliedLeading = 0.0f;
        this.hyphenation = null;
        this.tabSettings = null;
    }

    public static final Phrase getInstance(String str) {
        return getInstance(16, str, new Font());
    }

    public static final Phrase getInstance(int i, String str) {
        return getInstance(i, str, new Font());
    }

    public static final Phrase getInstance(int i, String str, Font font2) {
        Phrase phrase = new Phrase(true);
        phrase.setLeading((float) i);
        phrase.font = font2;
        if (font2.getFamily() != Font.FontFamily.SYMBOL && font2.getFamily() != Font.FontFamily.ZAPFDINGBATS && font2.getBaseFont() == null) {
            while (true) {
                int index = SpecialSymbol.index(str);
                if (index <= -1) {
                    break;
                }
                if (index > 0) {
                    phrase.add((Element) new Chunk(str.substring(0, index), font2));
                    str = str.substring(index);
                }
                Font font3 = new Font(Font.FontFamily.SYMBOL, font2.getSize(), font2.getStyle(), font2.getColor());
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(SpecialSymbol.getCorrespondingSymbol(str.charAt(0)));
                str = str.substring(1);
                while (SpecialSymbol.index(str) == 0) {
                    stringBuffer.append(SpecialSymbol.getCorrespondingSymbol(str.charAt(0)));
                    str = str.substring(1);
                }
                phrase.add((Element) new Chunk(stringBuffer.toString(), font3));
            }
        }
        if (!(str == null || str.length() == 0)) {
            phrase.add((Element) new Chunk(str, font2));
        }
        return phrase;
    }

    public boolean trim() {
        while (size() > 0) {
            Element element = (Element) get(0);
            if (!(element instanceof Chunk) || !((Chunk) element).isWhitespace()) {
                break;
            }
            remove(element);
        }
        while (size() > 0) {
            Element element2 = (Element) get(size() - 1);
            if (!(element2 instanceof Chunk) || !((Chunk) element2).isWhitespace()) {
                break;
            }
            remove(element2);
        }
        if (size() > 0) {
            return true;
        }
        return false;
    }
}
