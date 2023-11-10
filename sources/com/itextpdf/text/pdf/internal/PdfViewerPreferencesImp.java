package com.itextpdf.text.pdf.internal;

import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;

public class PdfViewerPreferencesImp implements PdfViewerPreferences {
    public static final PdfName[] DIRECTION_PREFERENCES = {PdfName.L2R, PdfName.R2L};
    public static final PdfName[] DUPLEX_PREFERENCES = {PdfName.SIMPLEX, PdfName.DUPLEXFLIPSHORTEDGE, PdfName.DUPLEXFLIPLONGEDGE};
    public static final PdfName[] NONFULLSCREENPAGEMODE_PREFERENCES = {PdfName.USENONE, PdfName.USEOUTLINES, PdfName.USETHUMBS, PdfName.USEOC};
    public static final PdfName[] PAGE_BOUNDARIES = {PdfName.MEDIABOX, PdfName.CROPBOX, PdfName.BLEEDBOX, PdfName.TRIMBOX, PdfName.ARTBOX};
    public static final PdfName[] PRINTSCALING_PREFERENCES = {PdfName.APPDEFAULT, PdfName.NONE};
    public static final PdfName[] VIEWER_PREFERENCES = {PdfName.HIDETOOLBAR, PdfName.HIDEMENUBAR, PdfName.HIDEWINDOWUI, PdfName.FITWINDOW, PdfName.CENTERWINDOW, PdfName.DISPLAYDOCTITLE, PdfName.NONFULLSCREENPAGEMODE, PdfName.DIRECTION, PdfName.VIEWAREA, PdfName.VIEWCLIP, PdfName.PRINTAREA, PdfName.PRINTCLIP, PdfName.PRINTSCALING, PdfName.DUPLEX, PdfName.PICKTRAYBYPDFSIZE, PdfName.PRINTPAGERANGE, PdfName.NUMCOPIES};
    private static final int viewerPreferencesMask = 16773120;
    private int pageLayoutAndMode = 0;
    private PdfDictionary viewerPreferences = new PdfDictionary();

    public int getPageLayoutAndMode() {
        return this.pageLayoutAndMode;
    }

    public PdfDictionary getViewerPreferences() {
        return this.viewerPreferences;
    }

    public void setViewerPreferences(int i) {
        int i2 = this.pageLayoutAndMode | i;
        this.pageLayoutAndMode = i2;
        if ((viewerPreferencesMask & i) != 0) {
            this.pageLayoutAndMode = i2 & -16773121;
            if ((i & 4096) != 0) {
                this.viewerPreferences.put(PdfName.HIDETOOLBAR, PdfBoolean.PDFTRUE);
            }
            if ((i & 8192) != 0) {
                this.viewerPreferences.put(PdfName.HIDEMENUBAR, PdfBoolean.PDFTRUE);
            }
            if ((i & 16384) != 0) {
                this.viewerPreferences.put(PdfName.HIDEWINDOWUI, PdfBoolean.PDFTRUE);
            }
            if ((32768 & i) != 0) {
                this.viewerPreferences.put(PdfName.FITWINDOW, PdfBoolean.PDFTRUE);
            }
            if ((65536 & i) != 0) {
                this.viewerPreferences.put(PdfName.CENTERWINDOW, PdfBoolean.PDFTRUE);
            }
            if ((131072 & i) != 0) {
                this.viewerPreferences.put(PdfName.DISPLAYDOCTITLE, PdfBoolean.PDFTRUE);
            }
            if ((262144 & i) != 0) {
                this.viewerPreferences.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USENONE);
            } else if ((524288 & i) != 0) {
                this.viewerPreferences.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USEOUTLINES);
            } else if ((1048576 & i) != 0) {
                this.viewerPreferences.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USETHUMBS);
            } else if ((2097152 & i) != 0) {
                this.viewerPreferences.put(PdfName.NONFULLSCREENPAGEMODE, PdfName.USEOC);
            }
            if ((4194304 & i) != 0) {
                this.viewerPreferences.put(PdfName.DIRECTION, PdfName.L2R);
            } else if ((8388608 & i) != 0) {
                this.viewerPreferences.put(PdfName.DIRECTION, PdfName.R2L);
            }
            if ((i & 16777216) != 0) {
                this.viewerPreferences.put(PdfName.PRINTSCALING, PdfName.NONE);
            }
        }
    }

    private int getIndex(PdfName pdfName) {
        int i = 0;
        while (true) {
            PdfName[] pdfNameArr = VIEWER_PREFERENCES;
            if (i >= pdfNameArr.length) {
                return -1;
            }
            if (pdfNameArr[i].equals(pdfName)) {
                return i;
            }
            i++;
        }
    }

    private boolean isPossibleValue(PdfName pdfName, PdfName[] pdfNameArr) {
        for (PdfName equals : pdfNameArr) {
            if (equals.equals(pdfName)) {
                return true;
            }
        }
        return false;
    }

    public void addViewerPreference(PdfName pdfName, PdfObject pdfObject) {
        switch (getIndex(pdfName)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 14:
                if (pdfObject instanceof PdfBoolean) {
                    this.viewerPreferences.put(pdfName, pdfObject);
                    return;
                }
                return;
            case 6:
                if ((pdfObject instanceof PdfName) && isPossibleValue((PdfName) pdfObject, NONFULLSCREENPAGEMODE_PREFERENCES)) {
                    this.viewerPreferences.put(pdfName, pdfObject);
                    return;
                }
                return;
            case 7:
                if ((pdfObject instanceof PdfName) && isPossibleValue((PdfName) pdfObject, DIRECTION_PREFERENCES)) {
                    this.viewerPreferences.put(pdfName, pdfObject);
                    return;
                }
                return;
            case 8:
            case 9:
            case 10:
            case 11:
                if ((pdfObject instanceof PdfName) && isPossibleValue((PdfName) pdfObject, PAGE_BOUNDARIES)) {
                    this.viewerPreferences.put(pdfName, pdfObject);
                    return;
                }
                return;
            case 12:
                if ((pdfObject instanceof PdfName) && isPossibleValue((PdfName) pdfObject, PRINTSCALING_PREFERENCES)) {
                    this.viewerPreferences.put(pdfName, pdfObject);
                    return;
                }
                return;
            case 13:
                if ((pdfObject instanceof PdfName) && isPossibleValue((PdfName) pdfObject, DUPLEX_PREFERENCES)) {
                    this.viewerPreferences.put(pdfName, pdfObject);
                    return;
                }
                return;
            case 15:
                if (pdfObject instanceof PdfArray) {
                    this.viewerPreferences.put(pdfName, pdfObject);
                    return;
                }
                return;
            case 16:
                if (pdfObject instanceof PdfNumber) {
                    this.viewerPreferences.put(pdfName, pdfObject);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void addToCatalog(PdfDictionary pdfDictionary) {
        pdfDictionary.remove(PdfName.PAGELAYOUT);
        int i = this.pageLayoutAndMode;
        if ((i & 1) != 0) {
            pdfDictionary.put(PdfName.PAGELAYOUT, PdfName.SINGLEPAGE);
        } else if ((i & 2) != 0) {
            pdfDictionary.put(PdfName.PAGELAYOUT, PdfName.ONECOLUMN);
        } else if ((i & 4) != 0) {
            pdfDictionary.put(PdfName.PAGELAYOUT, PdfName.TWOCOLUMNLEFT);
        } else if ((i & 8) != 0) {
            pdfDictionary.put(PdfName.PAGELAYOUT, PdfName.TWOCOLUMNRIGHT);
        } else if ((i & 16) != 0) {
            pdfDictionary.put(PdfName.PAGELAYOUT, PdfName.TWOPAGELEFT);
        } else if ((i & 32) != 0) {
            pdfDictionary.put(PdfName.PAGELAYOUT, PdfName.TWOPAGERIGHT);
        }
        pdfDictionary.remove(PdfName.PAGEMODE);
        int i2 = this.pageLayoutAndMode;
        if ((i2 & 64) != 0) {
            pdfDictionary.put(PdfName.PAGEMODE, PdfName.USENONE);
        } else if ((i2 & 128) != 0) {
            pdfDictionary.put(PdfName.PAGEMODE, PdfName.USEOUTLINES);
        } else if ((i2 & 256) != 0) {
            pdfDictionary.put(PdfName.PAGEMODE, PdfName.USETHUMBS);
        } else if ((i2 & 512) != 0) {
            pdfDictionary.put(PdfName.PAGEMODE, PdfName.FULLSCREEN);
        } else if ((i2 & 1024) != 0) {
            pdfDictionary.put(PdfName.PAGEMODE, PdfName.USEOC);
        } else if ((i2 & 2048) != 0) {
            pdfDictionary.put(PdfName.PAGEMODE, PdfName.USEATTACHMENTS);
        }
        pdfDictionary.remove(PdfName.VIEWERPREFERENCES);
        if (this.viewerPreferences.size() > 0) {
            pdfDictionary.put(PdfName.VIEWERPREFERENCES, this.viewerPreferences);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00cb A[LOOP:0: B:50:0x00c6->B:52:0x00cb, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00dd A[EDGE_INSN: B:54:0x00dd->B:53:0x00dd ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp getViewerPreferences(com.itextpdf.text.pdf.PdfDictionary r5) {
        /*
            com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp r0 = new com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp
            r0.<init>()
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.PAGELAYOUT
            com.itextpdf.text.pdf.PdfObject r1 = r5.get(r1)
            com.itextpdf.text.pdf.PdfObject r1 = com.itextpdf.text.pdf.PdfReader.getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r1)
            r2 = 0
            if (r1 == 0) goto L_0x0059
            boolean r3 = r1.isName()
            if (r3 == 0) goto L_0x0059
            com.itextpdf.text.pdf.PdfName r1 = (com.itextpdf.text.pdf.PdfName) r1
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.SINGLEPAGE
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0024
            r1 = 1
            goto L_0x005a
        L_0x0024:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.ONECOLUMN
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x002e
            r1 = 2
            goto L_0x005a
        L_0x002e:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.TWOCOLUMNLEFT
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0038
            r1 = 4
            goto L_0x005a
        L_0x0038:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.TWOCOLUMNRIGHT
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0043
            r1 = 8
            goto L_0x005a
        L_0x0043:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.TWOPAGELEFT
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x004e
            r1 = 16
            goto L_0x005a
        L_0x004e:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.TWOPAGERIGHT
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0059
            r1 = 32
            goto L_0x005a
        L_0x0059:
            r1 = 0
        L_0x005a:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.PAGEMODE
            com.itextpdf.text.pdf.PdfObject r3 = r5.get(r3)
            com.itextpdf.text.pdf.PdfObject r3 = com.itextpdf.text.pdf.PdfReader.getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r3)
            if (r3 == 0) goto L_0x00af
            boolean r4 = r3.isName()
            if (r4 == 0) goto L_0x00af
            com.itextpdf.text.pdf.PdfName r3 = (com.itextpdf.text.pdf.PdfName) r3
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.USENONE
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0079
            r1 = r1 | 64
            goto L_0x00af
        L_0x0079:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.USEOUTLINES
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0084
            r1 = r1 | 128(0x80, float:1.794E-43)
            goto L_0x00af
        L_0x0084:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.USETHUMBS
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x008f
            r1 = r1 | 256(0x100, float:3.59E-43)
            goto L_0x00af
        L_0x008f:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.FULLSCREEN
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x009a
            r1 = r1 | 512(0x200, float:7.175E-43)
            goto L_0x00af
        L_0x009a:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.USEOC
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x00a5
            r1 = r1 | 1024(0x400, float:1.435E-42)
            goto L_0x00af
        L_0x00a5:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.USEATTACHMENTS
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x00af
            r1 = r1 | 2048(0x800, float:2.87E-42)
        L_0x00af:
            r0.setViewerPreferences(r1)
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.VIEWERPREFERENCES
            com.itextpdf.text.pdf.PdfObject r5 = r5.get(r1)
            com.itextpdf.text.pdf.PdfObject r5 = com.itextpdf.text.pdf.PdfReader.getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r5)
            if (r5 == 0) goto L_0x00dd
            boolean r1 = r5.isDictionary()
            if (r1 == 0) goto L_0x00dd
            com.itextpdf.text.pdf.PdfDictionary r5 = (com.itextpdf.text.pdf.PdfDictionary) r5
        L_0x00c6:
            com.itextpdf.text.pdf.PdfName[] r1 = VIEWER_PREFERENCES
            int r3 = r1.length
            if (r2 >= r3) goto L_0x00dd
            r3 = r1[r2]
            com.itextpdf.text.pdf.PdfObject r3 = r5.get(r3)
            com.itextpdf.text.pdf.PdfObject r3 = com.itextpdf.text.pdf.PdfReader.getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r3)
            r1 = r1[r2]
            r0.addViewerPreference(r1, r3)
            int r2 = r2 + 1
            goto L_0x00c6
        L_0x00dd:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp.getViewerPreferences(com.itextpdf.text.pdf.PdfDictionary):com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp");
    }
}
