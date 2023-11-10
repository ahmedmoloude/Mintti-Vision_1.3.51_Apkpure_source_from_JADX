package com.itextpdf.text;

import com.itextpdf.text.api.Indentable;
import com.itextpdf.text.factories.RomanAlphabetFactory;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class List implements TextElementArray, Indentable, IAccessibleElement {
    public static final boolean ALPHABETICAL = true;
    public static final boolean LOWERCASE = true;
    public static final boolean NUMERICAL = false;
    public static final boolean ORDERED = true;
    public static final boolean UNORDERED = false;
    public static final boolean UPPERCASE = false;
    protected HashMap<PdfName, PdfObject> accessibleAttributes;
    protected boolean alignindent;
    protected boolean autoindent;
    protected int first;

    /* renamed from: id */
    private AccessibleElementId f451id;
    protected float indentationLeft;
    protected float indentationRight;
    protected boolean lettered;
    protected ArrayList<Element> list;
    protected boolean lowercase;
    protected boolean numbered;
    protected String postSymbol;
    protected String preSymbol;
    protected PdfName role;
    protected Chunk symbol;
    protected float symbolIndent;

    public boolean isContent() {
        return true;
    }

    public boolean isInline() {
        return false;
    }

    public boolean isNestable() {
        return true;
    }

    public int type() {
        return 14;
    }

    public List() {
        this(false, false);
    }

    public List(float f) {
        this.list = new ArrayList<>();
        this.numbered = false;
        this.lettered = false;
        this.lowercase = false;
        this.autoindent = false;
        this.alignindent = false;
        this.first = 1;
        this.symbol = new Chunk("- ");
        this.preSymbol = "";
        this.postSymbol = ". ";
        this.indentationLeft = 0.0f;
        this.indentationRight = 0.0f;
        this.symbolIndent = 0.0f;
        this.role = PdfName.f606L;
        this.accessibleAttributes = null;
        this.f451id = null;
        this.symbolIndent = f;
    }

    public List(boolean z) {
        this(z, false);
    }

    public List(boolean z, boolean z2) {
        this.list = new ArrayList<>();
        this.numbered = false;
        this.lettered = false;
        this.lowercase = false;
        this.autoindent = false;
        this.alignindent = false;
        this.first = 1;
        this.symbol = new Chunk("- ");
        this.preSymbol = "";
        this.postSymbol = ". ";
        this.indentationLeft = 0.0f;
        this.indentationRight = 0.0f;
        this.symbolIndent = 0.0f;
        this.role = PdfName.f606L;
        this.accessibleAttributes = null;
        this.f451id = null;
        this.numbered = z;
        this.lettered = z2;
        this.autoindent = true;
        this.alignindent = true;
    }

    public List(boolean z, float f) {
        this(z, false, f);
    }

    public List(boolean z, boolean z2, float f) {
        this.list = new ArrayList<>();
        this.numbered = false;
        this.lettered = false;
        this.lowercase = false;
        this.autoindent = false;
        this.alignindent = false;
        this.first = 1;
        this.symbol = new Chunk("- ");
        this.preSymbol = "";
        this.postSymbol = ". ";
        this.indentationLeft = 0.0f;
        this.indentationRight = 0.0f;
        this.symbolIndent = 0.0f;
        this.role = PdfName.f606L;
        this.accessibleAttributes = null;
        this.f451id = null;
        this.numbered = z;
        this.lettered = z2;
        this.symbolIndent = f;
    }

    public boolean process(ElementListener elementListener) {
        try {
            Iterator<Element> it = this.list.iterator();
            while (it.hasNext()) {
                elementListener.add(it.next());
            }
            return true;
        } catch (DocumentException unused) {
            return false;
        }
    }

    public java.util.List<Chunk> getChunks() {
        ArrayList arrayList = new ArrayList();
        Iterator<Element> it = this.list.iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getChunks());
        }
        return arrayList;
    }

    public boolean add(String str) {
        if (str != null) {
            return add((Element) new ListItem(str));
        }
        return false;
    }

    public boolean add(Element element) {
        if (element instanceof ListItem) {
            ListItem listItem = (ListItem) element;
            if (this.numbered || this.lettered) {
                Chunk chunk = new Chunk(this.preSymbol, this.symbol.getFont());
                chunk.setAttributes(this.symbol.getAttributes());
                int size = this.first + this.list.size();
                if (this.lettered) {
                    chunk.append(RomanAlphabetFactory.getString(size, this.lowercase));
                } else {
                    chunk.append(String.valueOf(size));
                }
                chunk.append(this.postSymbol);
                listItem.setListSymbol(chunk);
            } else {
                listItem.setListSymbol(this.symbol);
            }
            listItem.setIndentationLeft(this.symbolIndent, this.autoindent);
            listItem.setIndentationRight(0.0f);
            return this.list.add(listItem);
        } else if (!(element instanceof List)) {
            return false;
        } else {
            List list2 = (List) element;
            list2.setIndentationLeft(list2.getIndentationLeft() + this.symbolIndent);
            this.first--;
            return this.list.add(list2);
        }
    }

    public List cloneShallow() {
        List list2 = new List();
        populateProperties(list2);
        return list2;
    }

    /* access modifiers changed from: protected */
    public void populateProperties(List list2) {
        list2.indentationLeft = this.indentationLeft;
        list2.indentationRight = this.indentationRight;
        list2.autoindent = this.autoindent;
        list2.alignindent = this.alignindent;
        list2.symbolIndent = this.symbolIndent;
        list2.symbol = this.symbol;
    }

    public void normalizeIndentation() {
        Iterator<Element> it = this.list.iterator();
        float f = 0.0f;
        while (it.hasNext()) {
            Element next = it.next();
            if (next instanceof ListItem) {
                f = Math.max(f, ((ListItem) next).getIndentationLeft());
            }
        }
        Iterator<Element> it2 = this.list.iterator();
        while (it2.hasNext()) {
            Element next2 = it2.next();
            if (next2 instanceof ListItem) {
                ((ListItem) next2).setIndentationLeft(f);
            }
        }
    }

    public void setNumbered(boolean z) {
        this.numbered = z;
    }

    public void setLettered(boolean z) {
        this.lettered = z;
    }

    public void setLowercase(boolean z) {
        this.lowercase = z;
    }

    public void setAutoindent(boolean z) {
        this.autoindent = z;
    }

    public void setAlignindent(boolean z) {
        this.alignindent = z;
    }

    public void setFirst(int i) {
        this.first = i;
    }

    public void setListSymbol(Chunk chunk) {
        this.symbol = chunk;
    }

    public void setListSymbol(String str) {
        this.symbol = new Chunk(str);
    }

    public void setIndentationLeft(float f) {
        this.indentationLeft = f;
    }

    public void setIndentationRight(float f) {
        this.indentationRight = f;
    }

    public void setSymbolIndent(float f) {
        this.symbolIndent = f;
    }

    public ArrayList<Element> getItems() {
        return this.list;
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public float getTotalLeading() {
        if (this.list.size() < 1) {
            return -1.0f;
        }
        return ((ListItem) this.list.get(0)).getTotalLeading();
    }

    public boolean isNumbered() {
        return this.numbered;
    }

    public boolean isLettered() {
        return this.lettered;
    }

    public boolean isLowercase() {
        return this.lowercase;
    }

    public boolean isAutoindent() {
        return this.autoindent;
    }

    public boolean isAlignindent() {
        return this.alignindent;
    }

    public int getFirst() {
        return this.first;
    }

    public Chunk getSymbol() {
        return this.symbol;
    }

    public float getIndentationLeft() {
        return this.indentationLeft;
    }

    public float getIndentationRight() {
        return this.indentationRight;
    }

    public float getSymbolIndent() {
        return this.symbolIndent;
    }

    public String getPostSymbol() {
        return this.postSymbol;
    }

    public void setPostSymbol(String str) {
        this.postSymbol = str;
    }

    public String getPreSymbol() {
        return this.preSymbol;
    }

    public void setPreSymbol(String str) {
        this.preSymbol = str;
    }

    public ListItem getFirstItem() {
        Element element = this.list.size() > 0 ? this.list.get(0) : null;
        if (element != null) {
            if (element instanceof ListItem) {
                return (ListItem) element;
            }
            if (element instanceof List) {
                return ((List) element).getFirstItem();
            }
        }
        return null;
    }

    public ListItem getLastItem() {
        Element element;
        if (this.list.size() > 0) {
            ArrayList<Element> arrayList = this.list;
            element = arrayList.get(arrayList.size() - 1);
        } else {
            element = null;
        }
        if (element != null) {
            if (element instanceof ListItem) {
                return (ListItem) element;
            }
            if (element instanceof List) {
                return ((List) element).getLastItem();
            }
        }
        return null;
    }

    public PdfObject getAccessibleAttribute(PdfName pdfName) {
        HashMap<PdfName, PdfObject> hashMap = this.accessibleAttributes;
        if (hashMap != null) {
            return hashMap.get(pdfName);
        }
        return null;
    }

    public void setAccessibleAttribute(PdfName pdfName, PdfObject pdfObject) {
        if (this.accessibleAttributes == null) {
            this.accessibleAttributes = new HashMap<>();
        }
        this.accessibleAttributes.put(pdfName, pdfObject);
    }

    public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
        return this.accessibleAttributes;
    }

    public PdfName getRole() {
        return this.role;
    }

    public void setRole(PdfName pdfName) {
        this.role = pdfName;
    }

    public AccessibleElementId getId() {
        if (this.f451id == null) {
            this.f451id = new AccessibleElementId();
        }
        return this.f451id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f451id = accessibleElementId;
    }
}
