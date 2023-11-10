package com.itextpdf.text;

import com.itextpdf.text.factories.GreekAlphabetFactory;

public class GreekList extends List {
    public GreekList() {
        super(true);
        setGreekFont();
    }

    public GreekList(int i) {
        super(true, (float) i);
        setGreekFont();
    }

    public GreekList(boolean z, int i) {
        super(true, (float) i);
        this.lowercase = z;
        setGreekFont();
    }

    /* access modifiers changed from: protected */
    public void setGreekFont() {
        this.symbol.setFont(FontFactory.getFont("Symbol", this.symbol.getFont().getSize(), 0));
    }

    public boolean add(Element element) {
        if (element instanceof ListItem) {
            ListItem listItem = (ListItem) element;
            Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
            chunk.setAttributes(this.symbol.getAttributes());
            chunk.append(GreekAlphabetFactory.getString(this.first + this.list.size(), this.lowercase));
            chunk.append(this.postSymbol);
            listItem.setListSymbol(chunk);
            listItem.setIndentationLeft(this.symbolIndent, this.autoindent);
            listItem.setIndentationRight(0.0f);
            this.list.add(listItem);
            return false;
        } else if (!(element instanceof List)) {
            return false;
        } else {
            List list = (List) element;
            list.setIndentationLeft(list.getIndentationLeft() + this.symbolIndent);
            this.first--;
            return this.list.add(list);
        }
    }

    public List cloneShallow() {
        GreekList greekList = new GreekList();
        populateProperties(greekList);
        return greekList;
    }
}
