package com.itextpdf.text.pdf.parser;

import java.util.ArrayList;
import java.util.List;

public class MultiFilteredRenderListener implements RenderListener {
    private final List<RenderListener> delegates = new ArrayList();
    private final List<RenderFilter[]> filters = new ArrayList();

    public <E extends RenderListener> E attachRenderListener(E e, RenderFilter... renderFilterArr) {
        this.delegates.add(e);
        this.filters.add(renderFilterArr);
        return e;
    }

    public void beginTextBlock() {
        for (RenderListener beginTextBlock : this.delegates) {
            beginTextBlock.beginTextBlock();
        }
    }

    public void renderText(TextRenderInfo textRenderInfo) {
        boolean z;
        for (int i = 0; i < this.delegates.size(); i++) {
            RenderFilter[] renderFilterArr = this.filters.get(i);
            int length = renderFilterArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = true;
                    break;
                } else if (!renderFilterArr[i2].allowText(textRenderInfo)) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                this.delegates.get(i).renderText(textRenderInfo);
            }
        }
    }

    public void endTextBlock() {
        for (RenderListener endTextBlock : this.delegates) {
            endTextBlock.endTextBlock();
        }
    }

    public void renderImage(ImageRenderInfo imageRenderInfo) {
        boolean z;
        for (int i = 0; i < this.delegates.size(); i++) {
            RenderFilter[] renderFilterArr = this.filters.get(i);
            int length = renderFilterArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = true;
                    break;
                } else if (!renderFilterArr[i2].allowImage(imageRenderInfo)) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                this.delegates.get(i).renderImage(imageRenderInfo);
            }
        }
    }
}
