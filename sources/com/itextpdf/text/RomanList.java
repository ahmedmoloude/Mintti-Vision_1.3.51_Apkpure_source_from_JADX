package com.itextpdf.text;

import com.itextpdf.text.factories.RomanNumberFactory;

public class RomanList extends List {
    public RomanList() {
        super(true);
    }

    public RomanList(int i) {
        super(true, (float) i);
    }

    public RomanList(boolean z, int i) {
        super(true, (float) i);
        this.lowercase = z;
    }

    public boolean add(Element element) {
        if (element instanceof ListItem) {
            ListItem listItem = (ListItem) element;
            Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
            chunk.setAttributes(this.symbol.getAttributes());
            chunk.append(RomanNumberFactory.getString(this.first + this.list.size(), this.lowercase));
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
        RomanList romanList = new RomanList();
        populateProperties(romanList);
        return romanList;
    }
}
