package com.itextpdf.text;

import java.util.List;
import java.util.Properties;

public class MarkedObject implements Element {
    protected Element element;
    protected Properties markupAttributes;

    public boolean isContent() {
        return true;
    }

    public boolean isNestable() {
        return true;
    }

    public int type() {
        return 50;
    }

    protected MarkedObject() {
        this.markupAttributes = new Properties();
        this.element = null;
    }

    public MarkedObject(Element element2) {
        this.markupAttributes = new Properties();
        this.element = element2;
    }

    public List<Chunk> getChunks() {
        return this.element.getChunks();
    }

    public boolean process(ElementListener elementListener) {
        try {
            return elementListener.add(this.element);
        } catch (DocumentException unused) {
            return false;
        }
    }

    public Properties getMarkupAttributes() {
        return this.markupAttributes;
    }

    public void setMarkupAttribute(String str, String str2) {
        this.markupAttributes.setProperty(str, str2);
    }
}
