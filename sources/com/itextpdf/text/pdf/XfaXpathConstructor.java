package com.itextpdf.text.pdf;

import com.itextpdf.text.pdf.security.XpathConstructor;
import com.itextpdf.text.xml.xmp.PdfSchema;
import com.itextpdf.xmp.XMPConst;

public class XfaXpathConstructor implements XpathConstructor {
    private final String CONFIG;
    private final String CONNECTIONSET;
    private final String DATASETS;
    private final String LOCALESET;
    private final String PDF;
    private final String SOURCESET;
    private final String STYLESHEET;
    private final String TEMPLATE;
    private final String XDC;
    private final String XFDF;
    private final String XMPMETA;
    private String xpathExpression;

    public enum XdpPackage {
        Config,
        ConnectionSet,
        Datasets,
        LocaleSet,
        Pdf,
        SourceSet,
        Stylesheet,
        Template,
        Xdc,
        Xfdf,
        Xmpmeta
    }

    public XfaXpathConstructor() {
        this.CONFIG = "config";
        this.CONNECTIONSET = "connectionSet";
        this.DATASETS = "datasets";
        this.LOCALESET = "localeSet";
        this.PDF = PdfSchema.DEFAULT_XPATH_ID;
        this.SOURCESET = "sourceSet";
        this.STYLESHEET = "stylesheet";
        this.TEMPLATE = "template";
        this.XDC = "xdc";
        this.XFDF = "xfdf";
        this.XMPMETA = XMPConst.TAG_XMPMETA;
        this.xpathExpression = "";
    }

    public XfaXpathConstructor(XdpPackage xdpPackage) {
        String str = "config";
        this.CONFIG = str;
        this.CONNECTIONSET = "connectionSet";
        this.DATASETS = "datasets";
        this.LOCALESET = "localeSet";
        this.PDF = PdfSchema.DEFAULT_XPATH_ID;
        this.SOURCESET = "sourceSet";
        this.STYLESHEET = "stylesheet";
        this.TEMPLATE = "template";
        this.XDC = "xdc";
        this.XFDF = "xfdf";
        this.XMPMETA = XMPConst.TAG_XMPMETA;
        switch (C15241.$SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage[xdpPackage.ordinal()]) {
            case 1:
                break;
            case 2:
                str = "connectionSet";
                break;
            case 3:
                str = "datasets";
                break;
            case 4:
                str = "localeSet";
                break;
            case 5:
                str = PdfSchema.DEFAULT_XPATH_ID;
                break;
            case 6:
                str = "sourceSet";
                break;
            case 7:
                str = "stylesheet";
                break;
            case 8:
                str = "template";
                break;
            case 9:
                str = "xdc";
                break;
            case 10:
                str = "xfdf";
                break;
            case 11:
                str = XMPConst.TAG_XMPMETA;
                break;
            default:
                this.xpathExpression = "";
                return;
        }
        this.xpathExpression = "/xdp:xdp/*[local-name()='" + str + "']";
    }

    /* renamed from: com.itextpdf.text.pdf.XfaXpathConstructor$1 */
    static /* synthetic */ class C15241 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage;

        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|(3:21|22|24)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage[] r0 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage = r0
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Config     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.ConnectionSet     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Datasets     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.LocaleSet     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x003e }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Pdf     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.SourceSet     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Stylesheet     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Template     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x006c }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Xdc     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Xfdf     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$XfaXpathConstructor$XdpPackage     // Catch:{ NoSuchFieldError -> 0x0084 }
                com.itextpdf.text.pdf.XfaXpathConstructor$XdpPackage r1 = com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Xmpmeta     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.XfaXpathConstructor.C15241.<clinit>():void");
        }
    }

    public String getXpathExpression() {
        return this.xpathExpression;
    }
}
