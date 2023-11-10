package com.itextpdf.text.pdf;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.xml.xmp.PdfProperties;
import com.p020kl.commonbase.constants.Constants;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import kotlin.UByte;

public class PdfName extends PdfObject implements Comparable<PdfName> {

    /* renamed from: A */
    public static final PdfName f545A = new PdfName(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
    public static final PdfName A85 = new PdfName("A85");

    /* renamed from: AA */
    public static final PdfName f546AA = new PdfName("AA");
    public static final PdfName ABSOLUTECOLORIMETRIC = new PdfName("AbsoluteColorimetric");

    /* renamed from: AC */
    public static final PdfName f547AC = new PdfName("AC");
    public static final PdfName ACROFORM = new PdfName("AcroForm");
    public static final PdfName ACTION = new PdfName("Action");
    public static final PdfName ACTIVATION = new PdfName("Activation");
    public static final PdfName ACTUALTEXT = new PdfName("ActualText");
    public static final PdfName ADBE = new PdfName("ADBE");
    public static final PdfName ADBE_PKCS7_DETACHED = new PdfName("adbe.pkcs7.detached");
    public static final PdfName ADBE_PKCS7_S4 = new PdfName("adbe.pkcs7.s4");
    public static final PdfName ADBE_PKCS7_S5 = new PdfName("adbe.pkcs7.s5");
    public static final PdfName ADBE_PKCS7_SHA1 = new PdfName("adbe.pkcs7.sha1");
    public static final PdfName ADBE_X509_RSA_SHA1 = new PdfName("adbe.x509.rsa_sha1");
    public static final PdfName ADOBE_PPKLITE = new PdfName("Adobe.PPKLite");
    public static final PdfName ADOBE_PPKMS = new PdfName("Adobe.PPKMS");
    public static final PdfName AESV2 = new PdfName("AESV2");
    public static final PdfName AESV3 = new PdfName("AESV3");

    /* renamed from: AF */
    public static final PdfName f548AF = new PdfName("AF");
    public static final PdfName AFRELATIONSHIP = new PdfName("AFRelationship");
    public static final PdfName AHX = new PdfName("AHx");
    public static final PdfName AIS = new PdfName("AIS");
    public static final PdfName ALL = new PdfName("All");
    public static final PdfName ALLPAGES = new PdfName("AllPages");
    public static final PdfName ALT = new PdfName("Alt");
    public static final PdfName ALTERNATE = new PdfName("Alternate");
    public static final PdfName ALTERNATEPRESENTATION = new PdfName("AlternatePresentations");
    public static final PdfName ALTERNATES = new PdfName("Alternates");
    public static final PdfName AND = new PdfName("And");
    public static final PdfName ANIMATION = new PdfName("Animation");
    public static final PdfName ANNOT = new PdfName("Annot");
    public static final PdfName ANNOTS = new PdfName("Annots");
    public static final PdfName ANTIALIAS = new PdfName("AntiAlias");

    /* renamed from: AP */
    public static final PdfName f549AP = new PdfName("AP");
    public static final PdfName APP = new PdfName("App");
    public static final PdfName APPDEFAULT = new PdfName("AppDefault");
    public static final PdfName ART = new PdfName("Art");
    public static final PdfName ARTBOX = new PdfName("ArtBox");
    public static final PdfName ARTIFACT = new PdfName("Artifact");

    /* renamed from: AS */
    public static final PdfName f550AS = new PdfName("AS");
    public static final PdfName ASCENT = new PdfName("Ascent");
    public static final PdfName ASCII85DECODE = new PdfName("ASCII85Decode");
    public static final PdfName ASCIIHEXDECODE = new PdfName("ASCIIHexDecode");
    public static final PdfName ASSET = new PdfName("Asset");
    public static final PdfName ASSETS = new PdfName("Assets");
    public static final PdfName ATTACHED = new PdfName("Attached");
    public static final PdfName AUTHEVENT = new PdfName("AuthEvent");
    public static final PdfName AUTHOR = new PdfName("Author");

    /* renamed from: B */
    public static final PdfName f551B = new PdfName("B");
    public static final PdfName BACKGROUND = new PdfName("Background");
    public static final PdfName BACKGROUNDCOLOR = new PdfName("BackgroundColor");
    public static final PdfName BASEENCODING = new PdfName("BaseEncoding");
    public static final PdfName BASEFONT = new PdfName("BaseFont");
    public static final PdfName BASEVERSION = new PdfName("BaseVersion");
    public static final PdfName BBOX = new PdfName("BBox");

    /* renamed from: BC */
    public static final PdfName f552BC = new PdfName("BC");

    /* renamed from: BG */
    public static final PdfName f553BG = new PdfName(Constants.f837BG);
    public static final PdfName BIBENTRY = new PdfName("BibEntry");
    public static final PdfName BIGFIVE = new PdfName("BigFive");
    public static final PdfName BINDING = new PdfName("Binding");
    public static final PdfName BINDINGMATERIALNAME = new PdfName("BindingMaterialName");
    public static final PdfName BITSPERCOMPONENT = new PdfName("BitsPerComponent");
    public static final PdfName BITSPERSAMPLE = new PdfName(ExifInterface.TAG_BITS_PER_SAMPLE);

    /* renamed from: BL */
    public static final PdfName f554BL = new PdfName("Bl");
    public static final PdfName BLACKIS1 = new PdfName("BlackIs1");
    public static final PdfName BLACKPOINT = new PdfName("BlackPoint");
    public static final PdfName BLEEDBOX = new PdfName("BleedBox");
    public static final PdfName BLINDS = new PdfName("Blinds");
    public static final PdfName BLOCKQUOTE = new PdfName("BlockQuote");

    /* renamed from: BM */
    public static final PdfName f555BM = new PdfName("BM");
    public static final PdfName BORDER = new PdfName("Border");
    public static final PdfName BOTH = new PdfName("Both");
    public static final PdfName BOUNDS = new PdfName("Bounds");
    public static final PdfName BOX = new PdfName("Box");

    /* renamed from: BS */
    public static final PdfName f556BS = new PdfName("BS");
    public static final PdfName BTN = new PdfName("Btn");
    public static final PdfName BYTERANGE = new PdfName("ByteRange");

    /* renamed from: C */
    public static final PdfName f557C = new PdfName("C");

    /* renamed from: C0 */
    public static final PdfName f558C0 = new PdfName("C0");

    /* renamed from: C1 */
    public static final PdfName f559C1 = new PdfName("C1");

    /* renamed from: CA */
    public static final PdfName f560CA = new PdfName("CA");
    public static final PdfName CALGRAY = new PdfName("CalGray");
    public static final PdfName CALRGB = new PdfName("CalRGB");
    public static final PdfName CAPHEIGHT = new PdfName("CapHeight");
    public static final PdfName CAPTION = new PdfName("Caption");
    public static final PdfName CARET = new PdfName("Caret");
    public static final PdfName CATALOG = new PdfName("Catalog");
    public static final PdfName CATEGORY = new PdfName("Category");

    /* renamed from: CB */
    public static final PdfName f561CB = new PdfName("cb");
    public static final PdfName CCITTFAXDECODE = new PdfName("CCITTFaxDecode");
    public static final PdfName CENTER = new PdfName("Center");
    public static final PdfName CENTERWINDOW = new PdfName("CenterWindow");
    public static final PdfName CERT = new PdfName("Cert");
    public static final PdfName CERTS = new PdfName("Certs");

    /* renamed from: CF */
    public static final PdfName f562CF = new PdfName("CF");
    public static final PdfName CFM = new PdfName("CFM");

    /* renamed from: CH */
    public static final PdfName f563CH = new PdfName("Ch");
    public static final PdfName CHARPROCS = new PdfName("CharProcs");
    public static final PdfName CHECKSUM = new PdfName("CheckSum");

    /* renamed from: CI */
    public static final PdfName f564CI = new PdfName("CI");
    public static final PdfName CIDFONTTYPE0 = new PdfName("CIDFontType0");
    public static final PdfName CIDFONTTYPE2 = new PdfName("CIDFontType2");
    public static final PdfName CIDSET = new PdfName("CIDSet");
    public static final PdfName CIDSYSTEMINFO = new PdfName("CIDSystemInfo");
    public static final PdfName CIDTOGIDMAP = new PdfName("CIDToGIDMap");
    public static final PdfName CIRCLE = new PdfName("Circle");
    public static final PdfName CLASSMAP = new PdfName("ClassMap");
    public static final PdfName CLOUD = new PdfName("Cloud");
    public static final PdfName CMD = new PdfName("CMD");

    /* renamed from: CO */
    public static final PdfName f565CO = new PdfName("CO");
    public static final PdfName CODE = new PdfName("Code");
    public static final PdfName COLLECTION = new PdfName("Collection");
    public static final PdfName COLLECTIONFIELD = new PdfName("CollectionField");
    public static final PdfName COLLECTIONITEM = new PdfName("CollectionItem");
    public static final PdfName COLLECTIONSCHEMA = new PdfName("CollectionSchema");
    public static final PdfName COLLECTIONSORT = new PdfName("CollectionSort");
    public static final PdfName COLLECTIONSUBITEM = new PdfName("CollectionSubitem");
    public static final PdfName COLOR = new PdfName("Color");
    public static final PdfName COLORANTS = new PdfName("Colorants");
    public static final PdfName COLORS = new PdfName("Colors");
    public static final PdfName COLORSPACE = new PdfName(ExifInterface.TAG_COLOR_SPACE);
    public static final PdfName COLORTRANSFORM = new PdfName("ColorTransform");
    public static final PdfName COLSPAN = new PdfName("ColSpan");
    public static final PdfName COLUMN = new PdfName("Column");
    public static final PdfName COLUMNS = new PdfName("Columns");
    public static final PdfName CONDITION = new PdfName("Condition");
    public static final PdfName CONFIGS = new PdfName("Configs");
    public static final PdfName CONFIGURATION = new PdfName("Configuration");
    public static final PdfName CONFIGURATIONS = new PdfName("Configurations");
    public static final PdfName CONTACTINFO = new PdfName("ContactInfo");
    public static final PdfName CONTENT = new PdfName("Content");
    public static final PdfName CONTENTS = new PdfName("Contents");
    public static final PdfName COORDS = new PdfName("Coords");
    public static final PdfName COUNT = new PdfName("Count");
    public static final PdfName COURIER = new PdfName("Courier");
    public static final PdfName COURIER_BOLD = new PdfName("Courier-Bold");
    public static final PdfName COURIER_BOLDOBLIQUE = new PdfName("Courier-BoldOblique");
    public static final PdfName COURIER_OBLIQUE = new PdfName("Courier-Oblique");
    public static final PdfName CREATIONDATE = new PdfName("CreationDate");
    public static final PdfName CREATOR = new PdfName("Creator");
    public static final PdfName CREATORINFO = new PdfName("CreatorInfo");
    public static final PdfName CRL = new PdfName("CRL");
    public static final PdfName CRLS = new PdfName("CRLs");
    public static final PdfName CROPBOX = new PdfName("CropBox");
    public static final PdfName CRYPT = new PdfName("Crypt");

    /* renamed from: CS */
    public static final PdfName f566CS = new PdfName("CS");
    public static final PdfName CUEPOINT = new PdfName("CuePoint");
    public static final PdfName CUEPOINTS = new PdfName("CuePoints");
    public static final PdfName CYX = new PdfName("CYX");

    /* renamed from: D */
    public static final PdfName f567D = new PdfName("D");

    /* renamed from: DA */
    public static final PdfName f568DA = new PdfName("DA");
    public static final PdfName DATA = new PdfName("Data");

    /* renamed from: DC */
    public static final PdfName f569DC = new PdfName("DC");
    public static final PdfName DCS = new PdfName("DCS");
    public static final PdfName DCTDECODE = new PdfName("DCTDecode");
    public static final PdfName DEACTIVATION = new PdfName("Deactivation");
    public static final PdfName DECIMAL = new PdfName("Decimal");
    public static final PdfName DECODE = new PdfName("Decode");
    public static final PdfName DECODEPARMS = new PdfName("DecodeParms");
    public static final PdfName DEFAULT = new PdfName("Default");
    public static final PdfName DEFAULTCMYK = new PdfName("DefaultCMYK");
    public static final PdfName DEFAULTCRYPTFILTER = new PdfName("DefaultCryptFilter");
    public static final PdfName DEFAULTGRAY = new PdfName("DefaultGray");
    public static final PdfName DEFAULTRGB = new PdfName("DefaultRGB");
    public static final PdfName DESC = new PdfName("Desc");
    public static final PdfName DESCENDANTFONTS = new PdfName("DescendantFonts");
    public static final PdfName DESCENT = new PdfName("Descent");
    public static final PdfName DEST = new PdfName("Dest");
    public static final PdfName DESTOUTPUTPROFILE = new PdfName("DestOutputProfile");
    public static final PdfName DESTS = new PdfName("Dests");
    public static final PdfName DEVICECMYK = new PdfName("DeviceCMYK");
    public static final PdfName DEVICEGRAY = new PdfName("DeviceGray");
    public static final PdfName DEVICEN = new PdfName("DeviceN");
    public static final PdfName DEVICERGB = new PdfName("DeviceRGB");

    /* renamed from: DI */
    public static final PdfName f570DI = new PdfName("Di");
    public static final PdfName DIFFERENCES = new PdfName("Differences");
    public static final PdfName DIRECTION = new PdfName("Direction");
    public static final PdfName DISPLAYDOCTITLE = new PdfName("DisplayDocTitle");
    public static final PdfName DISSOLVE = new PdfName("Dissolve");
    public static final PdfName DIV = new PdfName("Div");

    /* renamed from: DL */
    public static final PdfName f571DL = new PdfName("DL");

    /* renamed from: DM */
    public static final PdfName f572DM = new PdfName("Dm");
    public static final PdfName DOCMDP = new PdfName("DocMDP");
    public static final PdfName DOCOPEN = new PdfName("DocOpen");
    public static final PdfName DOCTIMESTAMP = new PdfName("DocTimeStamp");
    public static final PdfName DOCUMENT = new PdfName("Document");
    public static final PdfName DOMAIN = new PdfName("Domain");
    public static final PdfName DOS = new PdfName("DOS");

    /* renamed from: DP */
    public static final PdfName f573DP = new PdfName("DP");

    /* renamed from: DR */
    public static final PdfName f574DR = new PdfName("DR");

    /* renamed from: DS */
    public static final PdfName f575DS = new PdfName("DS");
    public static final PdfName DSS = new PdfName("DSS");
    public static final PdfName DUPLEX = new PdfName("Duplex");
    public static final PdfName DUPLEXFLIPLONGEDGE = new PdfName("DuplexFlipLongEdge");
    public static final PdfName DUPLEXFLIPSHORTEDGE = new PdfName("DuplexFlipShortEdge");
    public static final PdfName DUR = new PdfName("Dur");

    /* renamed from: DV */
    public static final PdfName f576DV = new PdfName("DV");

    /* renamed from: DW */
    public static final PdfName f577DW = new PdfName("DW");

    /* renamed from: E */
    public static final PdfName f578E = new PdfName(ExifInterface.LONGITUDE_EAST);
    public static final PdfName EARLYCHANGE = new PdfName("EarlyChange");

    /* renamed from: EF */
    public static final PdfName f579EF = new PdfName("EF");
    public static final PdfName EFF = new PdfName("EFF");
    public static final PdfName EFOPEN = new PdfName("EFOpen");
    public static final PdfName EMBEDDED = new PdfName("Embedded");
    public static final PdfName EMBEDDEDFILE = new PdfName("EmbeddedFile");
    public static final PdfName EMBEDDEDFILES = new PdfName("EmbeddedFiles");
    public static final PdfName ENCODE = new PdfName("Encode");
    public static final PdfName ENCODEDBYTEALIGN = new PdfName("EncodedByteAlign");
    public static final PdfName ENCODING = new PdfName("Encoding");
    public static final PdfName ENCRYPT = new PdfName("Encrypt");
    public static final PdfName ENCRYPTMETADATA = new PdfName("EncryptMetadata");
    public static final PdfName END = new PdfName("End");
    public static final PdfName ENDINDENT = new PdfName("EndIndent");
    public static final PdfName ENDOFBLOCK = new PdfName("EndOfBlock");
    public static final PdfName ENDOFLINE = new PdfName("EndOfLine");
    public static final PdfName EPSG = new PdfName("EPSG");
    public static final PdfName ESIC = new PdfName("ESIC");
    public static final PdfName ETSI_CADES_DETACHED = new PdfName("ETSI.CAdES.detached");
    public static final PdfName ETSI_RFC3161 = new PdfName("ETSI.RFC3161");
    public static final PdfName EVENT = new PdfName("Event");
    public static final PdfName EXCLUDE = new PdfName("Exclude");
    public static final PdfName EXPORT = new PdfName("Export");
    public static final PdfName EXPORTSTATE = new PdfName("ExportState");
    public static final PdfName EXTEND = new PdfName("Extend");
    public static final PdfName EXTENSIONLEVEL = new PdfName("ExtensionLevel");
    public static final PdfName EXTENSIONS = new PdfName("Extensions");
    public static final PdfName EXTGSTATE = new PdfName("ExtGState");

    /* renamed from: F */
    public static final PdfName f580F = new PdfName("F");
    public static final PdfName FAR = new PdfName("Far");

    /* renamed from: FB */
    public static final PdfName f581FB = new PdfName("FB");

    /* renamed from: FD */
    public static final PdfName f582FD = new PdfName("FD");
    public static final PdfName FDECODEPARMS = new PdfName("FDecodeParms");
    public static final PdfName FDF = new PdfName("FDF");

    /* renamed from: FF */
    public static final PdfName f583FF = new PdfName("Ff");
    public static final PdfName FFILTER = new PdfName("FFilter");

    /* renamed from: FG */
    public static final PdfName f584FG = new PdfName("FG");
    public static final PdfName FIELDMDP = new PdfName("FieldMDP");
    public static final PdfName FIELDS = new PdfName("Fields");
    public static final PdfName FIGURE = new PdfName("Figure");
    public static final PdfName FILEATTACHMENT = new PdfName("FileAttachment");
    public static final PdfName FILESPEC = new PdfName("Filespec");
    public static final PdfName FILTER = new PdfName("Filter");
    public static final PdfName FIRST = new PdfName("First");
    public static final PdfName FIRSTCHAR = new PdfName("FirstChar");
    public static final PdfName FIRSTPAGE = new PdfName("FirstPage");
    public static final PdfName FIT = new PdfName("Fit");
    public static final PdfName FITB = new PdfName("FitB");
    public static final PdfName FITBH = new PdfName("FitBH");
    public static final PdfName FITBV = new PdfName("FitBV");
    public static final PdfName FITH = new PdfName("FitH");
    public static final PdfName FITR = new PdfName("FitR");
    public static final PdfName FITV = new PdfName("FitV");
    public static final PdfName FITWINDOW = new PdfName("FitWindow");

    /* renamed from: FL */
    public static final PdfName f585FL = new PdfName("Fl");
    public static final PdfName FLAGS = new PdfName("Flags");
    public static final PdfName FLASH = new PdfName(ExifInterface.TAG_FLASH);
    public static final PdfName FLASHVARS = new PdfName("FlashVars");
    public static final PdfName FLATEDECODE = new PdfName("FlateDecode");

    /* renamed from: FO */
    public static final PdfName f586FO = new PdfName("Fo");
    public static final PdfName FONT = new PdfName("Font");
    public static final PdfName FONTBBOX = new PdfName("FontBBox");
    public static final PdfName FONTDESCRIPTOR = new PdfName("FontDescriptor");
    public static final PdfName FONTFAMILY = new PdfName("FontFamily");
    public static final PdfName FONTFILE = new PdfName("FontFile");
    public static final PdfName FONTFILE2 = new PdfName("FontFile2");
    public static final PdfName FONTFILE3 = new PdfName("FontFile3");
    public static final PdfName FONTMATRIX = new PdfName("FontMatrix");
    public static final PdfName FONTNAME = new PdfName("FontName");
    public static final PdfName FONTWEIGHT = new PdfName("FontWeight");
    public static final PdfName FOREGROUND = new PdfName("Foreground");
    public static final PdfName FORM = new PdfName("Form");
    public static final PdfName FORMTYPE = new PdfName("FormType");
    public static final PdfName FORMULA = new PdfName("Formula");
    public static final PdfName FREETEXT = new PdfName("FreeText");
    public static final PdfName FRM = new PdfName("FRM");

    /* renamed from: FS */
    public static final PdfName f587FS = new PdfName("FS");

    /* renamed from: FT */
    public static final PdfName f588FT = new PdfName("FT");
    public static final PdfName FULLSCREEN = new PdfName("FullScreen");
    public static final PdfName FUNCTION = new PdfName("Function");
    public static final PdfName FUNCTIONS = new PdfName("Functions");
    public static final PdfName FUNCTIONTYPE = new PdfName("FunctionType");
    public static final PdfName GAMMA = new PdfName(ExifInterface.TAG_GAMMA);
    public static final PdfName GBK = new PdfName("GBK");
    public static final PdfName GCS = new PdfName("GCS");
    public static final PdfName GEO = new PdfName("GEO");
    public static final PdfName GEOGCS = new PdfName("GEOGCS");
    public static final PdfName GLITTER = new PdfName("Glitter");
    public static final PdfName GOTO = new PdfName("GoTo");
    public static final PdfName GOTO3DVIEW = new PdfName("GoTo3DView");
    public static final PdfName GOTOE = new PdfName("GoToE");
    public static final PdfName GOTOR = new PdfName("GoToR");
    public static final PdfName GPTS = new PdfName("GPTS");
    public static final PdfName GROUP = new PdfName("Group");
    public static final PdfName GTS_PDFA1 = new PdfName("GTS_PDFA1");
    public static final PdfName GTS_PDFX = new PdfName("GTS_PDFX");
    public static final PdfName GTS_PDFXVERSION = new PdfName("GTS_PDFXVersion");

    /* renamed from: H */
    public static final PdfName f589H = new PdfName("H");

    /* renamed from: H1 */
    public static final PdfName f590H1 = new PdfName("H1");

    /* renamed from: H2 */
    public static final PdfName f591H2 = new PdfName("H2");

    /* renamed from: H3 */
    public static final PdfName f592H3 = new PdfName("H3");

    /* renamed from: H4 */
    public static final PdfName f593H4 = new PdfName("H4");

    /* renamed from: H5 */
    public static final PdfName f594H5 = new PdfName("H5");

    /* renamed from: H6 */
    public static final PdfName f595H6 = new PdfName("H6");
    public static final PdfName HALFTONENAME = new PdfName("HalftoneName");
    public static final PdfName HALFTONETYPE = new PdfName("HalftoneType");
    public static final PdfName HALIGN = new PdfName("HAlign");
    public static final PdfName HEADERS = new PdfName("Headers");
    public static final PdfName HEIGHT = new PdfName("Height");
    public static final PdfName HELV = new PdfName("Helv");
    public static final PdfName HELVETICA = new PdfName("Helvetica");
    public static final PdfName HELVETICA_BOLD = new PdfName("Helvetica-Bold");
    public static final PdfName HELVETICA_BOLDOBLIQUE = new PdfName("Helvetica-BoldOblique");
    public static final PdfName HELVETICA_OBLIQUE = new PdfName("Helvetica-Oblique");

    /* renamed from: HF */
    public static final PdfName f596HF = new PdfName("HF");
    public static final PdfName HID = new PdfName("Hid");
    public static final PdfName HIDE = new PdfName("Hide");
    public static final PdfName HIDEMENUBAR = new PdfName("HideMenubar");
    public static final PdfName HIDETOOLBAR = new PdfName("HideToolbar");
    public static final PdfName HIDEWINDOWUI = new PdfName("HideWindowUI");
    public static final PdfName HIGHLIGHT = new PdfName("Highlight");
    public static final PdfName HOFFSET = new PdfName("HOffset");

    /* renamed from: HT */
    public static final PdfName f597HT = new PdfName("HT");
    public static final PdfName HTP = new PdfName("HTP");

    /* renamed from: I */
    public static final PdfName f598I = new PdfName("I");

    /* renamed from: IC */
    public static final PdfName f599IC = new PdfName("IC");
    public static final PdfName ICCBASED = new PdfName("ICCBased");

    /* renamed from: ID */
    public static final PdfName f600ID = new PdfName("ID");
    public static final PdfName IDENTITY = new PdfName("Identity");
    public static final PdfName IDTREE = new PdfName("IDTree");

    /* renamed from: IF */
    public static final PdfName f601IF = new PdfName("IF");

    /* renamed from: IM */
    public static final PdfName f602IM = new PdfName("IM");
    public static final PdfName IMAGE = new PdfName("Image");
    public static final PdfName IMAGEB = new PdfName("ImageB");
    public static final PdfName IMAGEC = new PdfName("ImageC");
    public static final PdfName IMAGEI = new PdfName("ImageI");
    public static final PdfName IMAGEMASK = new PdfName("ImageMask");
    public static final PdfName IMPORTDATA = new PdfName("ImportData");
    public static final PdfName INCLUDE = new PdfName("Include");
    public static final PdfName IND = new PdfName("Ind");
    public static final PdfName INDEX = new PdfName("Index");
    public static final PdfName INDEXED = new PdfName("Indexed");
    public static final PdfName INFO = new PdfName("Info");
    public static final PdfName INK = new PdfName("Ink");
    public static final PdfName INKLIST = new PdfName("InkList");
    public static final PdfName INSTANCES = new PdfName("Instances");
    public static final PdfName INTENT = new PdfName("Intent");
    public static final PdfName INTERPOLATE = new PdfName("Interpolate");
    public static final PdfName IRT = new PdfName("IRT");
    public static final PdfName ISMAP = new PdfName("IsMap");
    public static final PdfName ITALICANGLE = new PdfName("ItalicAngle");
    public static final PdfName ITXT = new PdfName("ITXT");

    /* renamed from: IX */
    public static final PdfName f603IX = new PdfName("IX");
    public static final PdfName JAVASCRIPT = new PdfName("JavaScript");
    public static final PdfName JBIG2DECODE = new PdfName("JBIG2Decode");
    public static final PdfName JBIG2GLOBALS = new PdfName("JBIG2Globals");
    public static final PdfName JPXDECODE = new PdfName("JPXDecode");

    /* renamed from: JS */
    public static final PdfName f604JS = new PdfName("JS");
    public static final PdfName JUSTIFY = new PdfName("Justify");

    /* renamed from: K */
    public static final PdfName f605K = new PdfName("K");
    public static final PdfName KEYWORDS = new PdfName(PdfProperties.KEYWORDS);
    public static final PdfName KIDS = new PdfName("Kids");

    /* renamed from: L */
    public static final PdfName f606L = new PdfName("L");
    public static final PdfName L2R = new PdfName("L2R");
    public static final PdfName LAB = new PdfName("Lab");
    public static final PdfName LANG = new PdfName("Lang");
    public static final PdfName LANGUAGE = new PdfName("Language");
    public static final PdfName LAST = new PdfName("Last");
    public static final PdfName LASTCHAR = new PdfName("LastChar");
    public static final PdfName LASTPAGE = new PdfName("LastPage");
    public static final PdfName LAUNCH = new PdfName("Launch");
    public static final PdfName LAYOUT = new PdfName("Layout");
    public static final PdfName LBL = new PdfName("Lbl");
    public static final PdfName LBODY = new PdfName("LBody");
    public static final PdfName LENGTH = new PdfName("Length");
    public static final PdfName LENGTH1 = new PdfName("Length1");

    /* renamed from: LI */
    public static final PdfName f607LI = new PdfName("LI");
    public static final PdfName LIMITS = new PdfName("Limits");
    public static final PdfName LINE = new PdfName("Line");
    public static final PdfName LINEAR = new PdfName("Linear");
    public static final PdfName LINEHEIGHT = new PdfName("LineHeight");
    public static final PdfName LINK = new PdfName("Link");
    public static final PdfName LIST = new PdfName("List");
    public static final PdfName LISTMODE = new PdfName("ListMode");
    public static final PdfName LISTNUMBERING = new PdfName("ListNumbering");
    public static final PdfName LOCATION = new PdfName("Location");
    public static final PdfName LOCK = new PdfName("Lock");
    public static final PdfName LOCKED = new PdfName("Locked");
    public static final PdfName LOWERALPHA = new PdfName("LowerAlpha");
    public static final PdfName LOWERROMAN = new PdfName("LowerRoman");
    public static final PdfName LPTS = new PdfName("LPTS");
    public static final PdfName LZWDECODE = new PdfName("LZWDecode");

    /* renamed from: M */
    public static final PdfName f608M = new PdfName("M");
    public static final PdfName MAC = new PdfName("Mac");
    public static final PdfName MAC_EXPERT_ENCODING = new PdfName("MacExpertEncoding");
    public static final PdfName MAC_ROMAN_ENCODING = new PdfName("MacRomanEncoding");
    public static final PdfName MARKED = new PdfName("Marked");
    public static final PdfName MARKINFO = new PdfName("MarkInfo");
    public static final PdfName MASK = new PdfName("Mask");
    public static final PdfName MATERIAL = new PdfName("Material");
    public static final PdfName MATRIX = new PdfName("Matrix");
    public static final PdfName MAXLEN = new PdfName("MaxLen");
    public static final PdfName MAX_CAMEL_CASE = new PdfName("Max");
    public static final PdfName MAX_LOWER_CASE = new PdfName("max");
    public static final PdfName MCID = new PdfName("MCID");
    public static final PdfName MCR = new PdfName("MCR");
    public static final PdfName MEASURE = new PdfName("Measure");
    public static final PdfName MEDIABOX = new PdfName("MediaBox");
    public static final PdfName METADATA = new PdfName("Metadata");
    public static final PdfName MIN_CAMEL_CASE = new PdfName("Min");
    public static final PdfName MIN_LOWER_CASE = new PdfName("min");

    /* renamed from: MK */
    public static final PdfName f609MK = new PdfName("MK");
    public static final PdfName MMTYPE1 = new PdfName("MMType1");
    public static final PdfName MODDATE = new PdfName("ModDate");
    public static final PdfName MOVIE = new PdfName("Movie");

    /* renamed from: N */
    public static final PdfName f610N = new PdfName("N");

    /* renamed from: N0 */
    public static final PdfName f611N0 = new PdfName("n0");

    /* renamed from: N1 */
    public static final PdfName f612N1 = new PdfName("n1");

    /* renamed from: N2 */
    public static final PdfName f613N2 = new PdfName("n2");

    /* renamed from: N3 */
    public static final PdfName f614N3 = new PdfName("n3");

    /* renamed from: N4 */
    public static final PdfName f615N4 = new PdfName("n4");
    public static final PdfName NAME = new PdfName("Name");
    public static final PdfName NAMED = new PdfName("Named");
    public static final PdfName NAMES = new PdfName("Names");
    public static final PdfName NAVIGATION = new PdfName("Navigation");
    public static final PdfName NAVIGATIONPANE = new PdfName("NavigationPane");
    public static final PdfName NCHANNEL = new PdfName("NChannel");
    public static final PdfName NEAR = new PdfName("Near");
    public static final PdfName NEEDAPPEARANCES = new PdfName("NeedAppearances");
    public static final PdfName NEEDRENDERING = new PdfName("NeedsRendering");
    public static final PdfName NEWWINDOW = new PdfName("NewWindow");
    public static final PdfName NEXT = new PdfName("Next");
    public static final PdfName NEXTPAGE = new PdfName("NextPage");

    /* renamed from: NM */
    public static final PdfName f616NM = new PdfName("NM");
    public static final PdfName NONE = new PdfName("None");
    public static final PdfName NONFULLSCREENPAGEMODE = new PdfName("NonFullScreenPageMode");
    public static final PdfName NONSTRUCT = new PdfName("NonStruct");
    public static final PdfName NOT = new PdfName("Not");
    public static final PdfName NOTE = new PdfName("Note");
    public static final PdfName NUMBERFORMAT = new PdfName("NumberFormat");
    public static final PdfName NUMCOPIES = new PdfName("NumCopies");
    public static final PdfName NUMS = new PdfName("Nums");

    /* renamed from: O */
    public static final PdfName f617O = new PdfName("O");
    public static final PdfName OBJ = new PdfName("Obj");
    public static final PdfName OBJR = new PdfName("OBJR");
    public static final PdfName OBJSTM = new PdfName("ObjStm");

    /* renamed from: OC */
    public static final PdfName f618OC = new PdfName("OC");
    public static final PdfName OCG = new PdfName("OCG");
    public static final PdfName OCGS = new PdfName("OCGs");
    public static final PdfName OCMD = new PdfName("OCMD");
    public static final PdfName OCPROPERTIES = new PdfName("OCProperties");
    public static final PdfName OCSP = new PdfName("OCSP");
    public static final PdfName OCSPS = new PdfName("OCSPs");

    /* renamed from: OE */
    public static final PdfName f619OE = new PdfName("OE");
    public static final PdfName OFF = new PdfName("OFF");

    /* renamed from: ON */
    public static final PdfName f620ON = new PdfName("ON");
    public static final PdfName ONECOLUMN = new PdfName("OneColumn");

    /* renamed from: OP */
    public static final PdfName f621OP = new PdfName("OP");
    public static final PdfName OPEN = new PdfName("Open");
    public static final PdfName OPENACTION = new PdfName("OpenAction");
    public static final PdfName OPI = new PdfName("OPI");
    public static final PdfName OPM = new PdfName("OPM");
    public static final PdfName OPT = new PdfName("Opt");

    /* renamed from: OR */
    public static final PdfName f622OR = new PdfName("Or");
    public static final PdfName ORDER = new PdfName("Order");
    public static final PdfName ORDERING = new PdfName("Ordering");
    public static final PdfName ORG = new PdfName("Org");
    public static final PdfName OSCILLATING = new PdfName("Oscillating");
    public static final PdfName OUTLINES = new PdfName("Outlines");
    public static final PdfName OUTPUTCONDITION = new PdfName("OutputCondition");
    public static final PdfName OUTPUTCONDITIONIDENTIFIER = new PdfName("OutputConditionIdentifier");
    public static final PdfName OUTPUTINTENT = new PdfName("OutputIntent");
    public static final PdfName OUTPUTINTENTS = new PdfName("OutputIntents");
    public static final PdfName OVERLAYTEXT = new PdfName("OverlayText");
    public static final PdfName Off = new PdfName("Off");

    /* renamed from: P */
    public static final PdfName f623P = new PdfName("P");
    public static final PdfName PAGE = new PdfName("Page");
    public static final PdfName PAGEELEMENT = new PdfName("PageElement");
    public static final PdfName PAGELABELS = new PdfName("PageLabels");
    public static final PdfName PAGELAYOUT = new PdfName("PageLayout");
    public static final PdfName PAGEMODE = new PdfName("PageMode");
    public static final PdfName PAGES = new PdfName("Pages");
    public static final PdfName PAINTTYPE = new PdfName("PaintType");
    public static final PdfName PANOSE = new PdfName("Panose");
    public static final PdfName PARAMS = new PdfName("Params");
    public static final PdfName PARENT = new PdfName("Parent");
    public static final PdfName PARENTTREE = new PdfName("ParentTree");
    public static final PdfName PARENTTREENEXTKEY = new PdfName("ParentTreeNextKey");
    public static final PdfName PART = new PdfName("Part");
    public static final PdfName PASSCONTEXTCLICK = new PdfName("PassContextClick");
    public static final PdfName PATTERN = new PdfName("Pattern");
    public static final PdfName PATTERNTYPE = new PdfName("PatternType");

    /* renamed from: PB */
    public static final PdfName f624PB = new PdfName("pb");

    /* renamed from: PC */
    public static final PdfName f625PC = new PdfName("PC");
    public static final PdfName PDF = new PdfName(PdfObject.TEXT_PDFDOCENCODING);
    public static final PdfName PDFDOCENCODING = new PdfName("PDFDocEncoding");
    public static final PdfName PDU = new PdfName("PDU");
    public static final PdfName PERCEPTUAL = new PdfName("Perceptual");
    public static final PdfName PERMS = new PdfName("Perms");

    /* renamed from: PG */
    public static final PdfName f626PG = new PdfName("Pg");

    /* renamed from: PI */
    public static final PdfName f627PI = new PdfName("PI");
    public static final PdfName PICKTRAYBYPDFSIZE = new PdfName("PickTrayByPDFSize");
    public static final PdfName PIECEINFO = new PdfName("PieceInfo");
    public static final PdfName PLAYCOUNT = new PdfName("PlayCount");

    /* renamed from: PO */
    public static final PdfName f628PO = new PdfName("PO");
    public static final PdfName POLYGON = new PdfName("Polygon");
    public static final PdfName POLYLINE = new PdfName("PolyLine");
    public static final PdfName POPUP = new PdfName("Popup");
    public static final PdfName POSITION = new PdfName("Position");
    public static final PdfName PREDICTOR = new PdfName("Predictor");
    public static final PdfName PREFERRED = new PdfName("Preferred");
    public static final PdfName PRESENTATION = new PdfName("Presentation");
    public static final PdfName PRESERVERB = new PdfName("PreserveRB");
    public static final PdfName PRESSTEPS = new PdfName("PresSteps");
    public static final PdfName PREV = new PdfName("Prev");
    public static final PdfName PREVPAGE = new PdfName("PrevPage");
    public static final PdfName PRINT = new PdfName("Print");
    public static final PdfName PRINTAREA = new PdfName("PrintArea");
    public static final PdfName PRINTCLIP = new PdfName("PrintClip");
    public static final PdfName PRINTERMARK = new PdfName("PrinterMark");
    public static final PdfName PRINTFIELD = new PdfName("PrintField");
    public static final PdfName PRINTPAGERANGE = new PdfName("PrintPageRange");
    public static final PdfName PRINTSCALING = new PdfName("PrintScaling");
    public static final PdfName PRINTSTATE = new PdfName("PrintState");
    public static final PdfName PRIVATE = new PdfName("Private");
    public static final PdfName PROCSET = new PdfName("ProcSet");
    public static final PdfName PRODUCER = new PdfName(PdfProperties.PRODUCER);
    public static final PdfName PROJCS = new PdfName("PROJCS");
    public static final PdfName PROPERTIES = new PdfName("Properties");
    public static final PdfName PROP_BUILD = new PdfName("Prop_Build");

    /* renamed from: PS */
    public static final PdfName f629PS = new PdfName("PS");
    public static final PdfName PTDATA = new PdfName("PtData");
    public static final PdfName PUBSEC = new PdfName("Adobe.PubSec");

    /* renamed from: PV */
    public static final PdfName f630PV = new PdfName("PV");

    /* renamed from: Q */
    public static final PdfName f631Q = new PdfName("Q");
    public static final PdfName QUADPOINTS = new PdfName("QuadPoints");
    public static final PdfName QUOTE = new PdfName("Quote");

    /* renamed from: R */
    public static final PdfName f632R = new PdfName("R");
    public static final PdfName R2L = new PdfName("R2L");
    public static final PdfName RANGE = new PdfName("Range");

    /* renamed from: RB */
    public static final PdfName f633RB = new PdfName("RB");
    public static final PdfName RBGROUPS = new PdfName("RBGroups");

    /* renamed from: RC */
    public static final PdfName f634RC = new PdfName("RC");

    /* renamed from: RD */
    public static final PdfName f635RD = new PdfName("RD");
    public static final PdfName REASON = new PdfName("Reason");
    public static final PdfName RECIPIENTS = new PdfName("Recipients");
    public static final PdfName RECT = new PdfName("Rect");
    public static final PdfName REDACT = new PdfName("Redact");
    public static final PdfName REFERENCE = new PdfName("Reference");
    public static final PdfName REGISTRY = new PdfName("Registry");
    public static final PdfName REGISTRYNAME = new PdfName("RegistryName");
    public static final PdfName RELATIVECOLORIMETRIC = new PdfName("RelativeColorimetric");
    public static final PdfName RENDITION = new PdfName("Rendition");
    public static final PdfName REPEAT = new PdfName("Repeat");
    public static final PdfName REQUIREMENTS = new PdfName("Requirements");
    public static final PdfName RESETFORM = new PdfName("ResetForm");
    public static final PdfName RESOURCES = new PdfName("Resources");
    public static final PdfName REVERSEDCHARS = new PdfName("ReversedChars");

    /* renamed from: RI */
    public static final PdfName f636RI = new PdfName("RI");
    public static final PdfName RICHMEDIA = new PdfName("RichMedia");
    public static final PdfName RICHMEDIAACTIVATION = new PdfName("RichMediaActivation");
    public static final PdfName RICHMEDIAANIMATION = new PdfName("RichMediaAnimation");
    public static final PdfName RICHMEDIACOMMAND = new PdfName("RichMediaCommand");
    public static final PdfName RICHMEDIACONFIGURATION = new PdfName("RichMediaConfiguration");
    public static final PdfName RICHMEDIACONTENT = new PdfName("RichMediaContent");
    public static final PdfName RICHMEDIADEACTIVATION = new PdfName("RichMediaDeactivation");
    public static final PdfName RICHMEDIAEXECUTE = new PdfName("RichMediaExecute");
    public static final PdfName RICHMEDIAINSTANCE = new PdfName("RichMediaInstance");
    public static final PdfName RICHMEDIAPARAMS = new PdfName("RichMediaParams");
    public static final PdfName RICHMEDIAPOSITION = new PdfName("RichMediaPosition");
    public static final PdfName RICHMEDIAPRESENTATION = new PdfName("RichMediaPresentation");
    public static final PdfName RICHMEDIASETTINGS = new PdfName("RichMediaSettings");
    public static final PdfName RICHMEDIAWINDOW = new PdfName("RichMediaWindow");

    /* renamed from: RL */
    public static final PdfName f637RL = new PdfName("RL");

    /* renamed from: RO */
    public static final PdfName f638RO = new PdfName("RO");
    public static final PdfName ROLE = new PdfName("Role");
    public static final PdfName ROLEMAP = new PdfName("RoleMap");
    public static final PdfName ROOT = new PdfName("Root");
    public static final PdfName ROTATE = new PdfName("Rotate");
    public static final PdfName ROW = new PdfName("Row");
    public static final PdfName ROWS = new PdfName("Rows");
    public static final PdfName ROWSPAN = new PdfName("RowSpan");

    /* renamed from: RP */
    public static final PdfName f639RP = new PdfName("RP");

    /* renamed from: RT */
    public static final PdfName f640RT = new PdfName("RT");
    public static final PdfName RUBY = new PdfName("Ruby");
    public static final PdfName RUNLENGTHDECODE = new PdfName("RunLengthDecode");

    /* renamed from: RV */
    public static final PdfName f641RV = new PdfName("RV");

    /* renamed from: S */
    public static final PdfName f642S = new PdfName(ExifInterface.LATITUDE_SOUTH);
    public static final PdfName SATURATION = new PdfName(ExifInterface.TAG_SATURATION);
    public static final PdfName SCHEMA = new PdfName("Schema");
    public static final PdfName SCOPE = new PdfName("Scope");
    public static final PdfName SCREEN = new PdfName("Screen");
    public static final PdfName SCRIPTS = new PdfName("Scripts");
    public static final PdfName SECT = new PdfName("Sect");
    public static final PdfName SEPARATION = new PdfName("Separation");
    public static final PdfName SETOCGSTATE = new PdfName("SetOCGState");
    public static final PdfName SETTINGS = new PdfName("Settings");
    public static final PdfName SHADING = new PdfName("Shading");
    public static final PdfName SHADINGTYPE = new PdfName("ShadingType");
    public static final PdfName SHIFT_JIS = new PdfName("Shift-JIS");
    public static final PdfName SIG = new PdfName("Sig");
    public static final PdfName SIGFIELDLOCK = new PdfName("SigFieldLock");
    public static final PdfName SIGFLAGS = new PdfName("SigFlags");
    public static final PdfName SIGREF = new PdfName("SigRef");
    public static final PdfName SIMPLEX = new PdfName("Simplex");
    public static final PdfName SINGLEPAGE = new PdfName("SinglePage");
    public static final PdfName SIZE = new PdfName("Size");
    public static final PdfName SMASK = new PdfName("SMask");
    public static final PdfName SMASKINDATA = new PdfName("SMaskInData");
    public static final PdfName SORT = new PdfName("Sort");
    public static final PdfName SOUND = new PdfName("Sound");
    public static final PdfName SPACEAFTER = new PdfName("SpaceAfter");
    public static final PdfName SPACEBEFORE = new PdfName("SpaceBefore");
    public static final PdfName SPAN = new PdfName("Span");
    public static final PdfName SPEED = new PdfName("Speed");
    public static final PdfName SPLIT = new PdfName("Split");
    public static final PdfName SQUARE = new PdfName("Square");
    public static final PdfName SQUIGGLY = new PdfName("Squiggly");

    /* renamed from: SS */
    public static final PdfName f643SS = new PdfName("SS");

    /* renamed from: ST */
    public static final PdfName f644ST = new PdfName("St");
    public static final PdfName STAMP = new PdfName("Stamp");
    public static final PdfName STANDARD = new PdfName("Standard");
    public static final PdfName START = new PdfName("Start");
    public static final PdfName STARTINDENT = new PdfName("StartIndent");
    public static final PdfName STATE = new PdfName("State");
    public static final PdfName STATUS = new PdfName("Status");
    public static final PdfName STDCF = new PdfName("StdCF");
    public static final PdfName STEMV = new PdfName("StemV");
    public static final PdfName STMF = new PdfName("StmF");
    public static final PdfName STRF = new PdfName("StrF");
    public static final PdfName STRIKEOUT = new PdfName("StrikeOut");
    public static final PdfName STRUCTELEM = new PdfName("StructElem");
    public static final PdfName STRUCTPARENT = new PdfName("StructParent");
    public static final PdfName STRUCTPARENTS = new PdfName("StructParents");
    public static final PdfName STRUCTTREEROOT = new PdfName("StructTreeRoot");
    public static final PdfName STYLE = new PdfName("Style");
    public static final PdfName SUBFILTER = new PdfName("SubFilter");
    public static final PdfName SUBJECT = new PdfName("Subject");
    public static final PdfName SUBMITFORM = new PdfName("SubmitForm");
    public static final PdfName SUBTYPE = new PdfName("Subtype");
    public static final PdfName SUMMARY = new PdfName("Summary");
    public static final PdfName SUPPLEMENT = new PdfName("Supplement");

    /* renamed from: SV */
    public static final PdfName f645SV = new PdfName("SV");

    /* renamed from: SW */
    public static final PdfName f646SW = new PdfName("SW");
    public static final PdfName SYMBOL = new PdfName("Symbol");

    /* renamed from: T */
    public static final PdfName f647T = new PdfName(ExifInterface.GPS_DIRECTION_TRUE);

    /* renamed from: TA */
    public static final PdfName f648TA = new PdfName("TA");
    public static final PdfName TABLE = new PdfName("Table");
    public static final PdfName TABLEROW = new PdfName("TR");
    public static final PdfName TABS = new PdfName("Tabs");
    public static final PdfName TBODY = new PdfName("TBody");

    /* renamed from: TD */
    public static final PdfName f649TD = new PdfName("TD");
    public static final PdfName TEXT = new PdfName("Text");
    public static final PdfName TEXTALIGN = new PdfName("TextAlign");
    public static final PdfName TEXTDECORATIONCOLOR = new PdfName("TextDecorationColor");
    public static final PdfName TEXTDECORATIONTHICKNESS = new PdfName("TextDecorationThickness");
    public static final PdfName TEXTDECORATIONTYPE = new PdfName("TextDecorationType");
    public static final PdfName TEXTINDENT = new PdfName("TextIndent");
    public static final PdfName TFOOT = new PdfName("TFoot");

    /* renamed from: TH */
    public static final PdfName f650TH = new PdfName("TH");
    public static final PdfName THEAD = new PdfName("THead");
    public static final PdfName THREADS = new PdfName("Threads");
    public static final PdfName THUMB = new PdfName("Thumb");

    /* renamed from: TI */
    public static final PdfName f651TI = new PdfName("TI");
    public static final PdfName TILINGTYPE = new PdfName("TilingType");
    public static final PdfName TIME = new PdfName("Time");
    public static final PdfName TIMES_BOLD = new PdfName("Times-Bold");
    public static final PdfName TIMES_BOLDITALIC = new PdfName("Times-BoldItalic");
    public static final PdfName TIMES_ITALIC = new PdfName("Times-Italic");
    public static final PdfName TIMES_ROMAN = new PdfName("Times-Roman");
    public static final PdfName TITLE = new PdfName("Title");

    /* renamed from: TK */
    public static final PdfName f652TK = new PdfName("TK");

    /* renamed from: TM */
    public static final PdfName f653TM = new PdfName("TM");
    public static final PdfName TOC = new PdfName("TOC");
    public static final PdfName TOCI = new PdfName("TOCI");
    public static final PdfName TOGGLE = new PdfName("Toggle");
    public static final PdfName TOOLBAR = new PdfName("Toolbar");
    public static final PdfName TOUNICODE = new PdfName("ToUnicode");

    /* renamed from: TP */
    public static final PdfName f654TP = new PdfName("TP");

    /* renamed from: TR */
    public static final PdfName f655TR = new PdfName("TR");
    public static final PdfName TR2 = new PdfName("TR2");
    public static final PdfName TRANS = new PdfName("Trans");
    public static final PdfName TRANSFORMMETHOD = new PdfName("TransformMethod");
    public static final PdfName TRANSFORMPARAMS = new PdfName("TransformParams");
    public static final PdfName TRANSPARENCY = new PdfName("Transparency");
    public static final PdfName TRANSPARENT = new PdfName("Transparent");
    public static final PdfName TRAPNET = new PdfName("TrapNet");
    public static final PdfName TRAPPED = new PdfName("Trapped");
    public static final PdfName TRIMBOX = new PdfName("TrimBox");
    public static final PdfName TRUETYPE = new PdfName("TrueType");

    /* renamed from: TS */
    public static final PdfName f656TS = new PdfName("TS");
    public static final PdfName TTL = new PdfName("Ttl");

    /* renamed from: TU */
    public static final PdfName f657TU = new PdfName("TU");

    /* renamed from: TV */
    public static final PdfName f658TV = new PdfName("tv");
    public static final PdfName TWOCOLUMNLEFT = new PdfName("TwoColumnLeft");
    public static final PdfName TWOCOLUMNRIGHT = new PdfName("TwoColumnRight");
    public static final PdfName TWOPAGELEFT = new PdfName("TwoPageLeft");
    public static final PdfName TWOPAGERIGHT = new PdfName("TwoPageRight");

    /* renamed from: TX */
    public static final PdfName f659TX = new PdfName("Tx");
    public static final PdfName TYPE = new PdfName("Type");
    public static final PdfName TYPE0 = new PdfName("Type0");
    public static final PdfName TYPE1 = new PdfName("Type1");
    public static final PdfName TYPE3 = new PdfName("Type3");

    /* renamed from: U */
    public static final PdfName f660U = new PdfName("U");

    /* renamed from: UE */
    public static final PdfName f661UE = new PdfName("UE");

    /* renamed from: UF */
    public static final PdfName f662UF = new PdfName("UF");
    public static final PdfName UHC = new PdfName("UHC");
    public static final PdfName UNDERLINE = new PdfName("Underline");
    public static final PdfName UNIX = new PdfName("Unix");
    public static final PdfName UPPERALPHA = new PdfName("UpperAlpha");
    public static final PdfName UPPERROMAN = new PdfName("UpperRoman");

    /* renamed from: UR */
    public static final PdfName f663UR = new PdfName("UR");
    public static final PdfName UR3 = new PdfName("UR3");
    public static final PdfName URI = new PdfName("URI");
    public static final PdfName URL = new PdfName("URL");
    public static final PdfName USAGE = new PdfName("Usage");
    public static final PdfName USEATTACHMENTS = new PdfName("UseAttachments");
    public static final PdfName USENONE = new PdfName("UseNone");
    public static final PdfName USEOC = new PdfName("UseOC");
    public static final PdfName USEOUTLINES = new PdfName("UseOutlines");
    public static final PdfName USER = new PdfName("User");
    public static final PdfName USERPROPERTIES = new PdfName("UserProperties");
    public static final PdfName USERUNIT = new PdfName("UserUnit");
    public static final PdfName USETHUMBS = new PdfName("UseThumbs");
    public static final PdfName UTF_8 = new PdfName("utf_8");

    /* renamed from: V */
    public static final PdfName f664V = new PdfName(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);

    /* renamed from: V2 */
    public static final PdfName f665V2 = new PdfName("V2");
    public static final PdfName VALIGN = new PdfName("VAlign");

    /* renamed from: VE */
    public static final PdfName f666VE = new PdfName("VE");
    public static final PdfName VERISIGN_PPKVS = new PdfName("VeriSign.PPKVS");
    public static final PdfName VERSION = new PdfName("Version");
    public static final PdfName VERTICES = new PdfName("Vertices");
    public static final PdfName VIDEO = new PdfName("Video");
    public static final PdfName VIEW = new PdfName("View");
    public static final PdfName VIEWAREA = new PdfName("ViewArea");
    public static final PdfName VIEWCLIP = new PdfName("ViewClip");
    public static final PdfName VIEWERPREFERENCES = new PdfName("ViewerPreferences");
    public static final PdfName VIEWPORT = new PdfName("Viewport");
    public static final PdfName VIEWS = new PdfName("Views");
    public static final PdfName VIEWSTATE = new PdfName("ViewState");
    public static final PdfName VISIBLEPAGES = new PdfName("VisiblePages");
    public static final PdfName VOFFSET = new PdfName("VOffset");

    /* renamed from: VP */
    public static final PdfName f667VP = new PdfName("VP");
    public static final PdfName VRI = new PdfName("VRI");

    /* renamed from: W */
    public static final PdfName f668W = new PdfName(ExifInterface.LONGITUDE_WEST);

    /* renamed from: W2 */
    public static final PdfName f669W2 = new PdfName("W2");
    public static final PdfName WARICHU = new PdfName("Warichu");
    public static final PdfName WATERMARK = new PdfName("Watermark");

    /* renamed from: WC */
    public static final PdfName f670WC = new PdfName("WC");
    public static final PdfName WHITEPOINT = new PdfName(ExifInterface.TAG_WHITE_POINT);
    public static final PdfName WIDGET = new PdfName("Widget");
    public static final PdfName WIDTH = new PdfName("Width");
    public static final PdfName WIDTHS = new PdfName("Widths");
    public static final PdfName WIN = new PdfName("Win");
    public static final PdfName WINDOW = new PdfName("Window");
    public static final PdfName WINDOWED = new PdfName("Windowed");
    public static final PdfName WIN_ANSI_ENCODING = new PdfName("WinAnsiEncoding");
    public static final PdfName WIPE = new PdfName("Wipe");
    public static final PdfName WKT = new PdfName("WKT");

    /* renamed from: WP */
    public static final PdfName f671WP = new PdfName("WP");

    /* renamed from: WS */
    public static final PdfName f672WS = new PdfName("WS");

    /* renamed from: WT */
    public static final PdfName f673WT = new PdfName("WT");

    /* renamed from: X */
    public static final PdfName f674X = new PdfName("X");

    /* renamed from: XA */
    public static final PdfName f675XA = new PdfName("XA");

    /* renamed from: XD */
    public static final PdfName f676XD = new PdfName("XD");
    public static final PdfName XFA = new PdfName("XFA");
    public static final PdfName XML = new PdfName("XML");
    public static final PdfName XOBJECT = new PdfName("XObject");
    public static final PdfName XPTS = new PdfName("XPTS");
    public static final PdfName XREF = new PdfName("XRef");
    public static final PdfName XREFSTM = new PdfName("XRefStm");
    public static final PdfName XSTEP = new PdfName("XStep");
    public static final PdfName XYZ = new PdfName("XYZ");
    public static final PdfName YSTEP = new PdfName("YStep");
    public static final PdfName ZADB = new PdfName("ZaDb");
    public static final PdfName ZAPFDINGBATS = new PdfName("ZapfDingbats");
    public static final PdfName ZOOM = new PdfName("Zoom");
    public static final PdfName _3D = new PdfName("3D");

    /* renamed from: ca */
    public static final PdfName f677ca = new PdfName("ca");

    /* renamed from: op */
    public static final PdfName f678op = new PdfName("op");

    /* renamed from: rb */
    public static final PdfName f679rb = new PdfName("rb");
    public static Map<String, PdfName> staticNames;
    private int hash;

    static {
        Class<PdfName> cls = PdfName.class;
        Field[] declaredFields = cls.getDeclaredFields();
        staticNames = new HashMap(declaredFields.length);
        int i = 0;
        while (i < declaredFields.length) {
            try {
                Field field = declaredFields[i];
                if ((field.getModifiers() & 25) == 25 && field.getType().equals(cls)) {
                    PdfName pdfName = (PdfName) field.get((Object) null);
                    staticNames.put(decodeName(pdfName.toString()), pdfName);
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public PdfName(String str) {
        this(str, true);
    }

    public PdfName(String str, boolean z) {
        super(4);
        this.hash = 0;
        int length = str.length();
        if (!z || length <= 127) {
            this.bytes = encodeName(str);
        } else {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.name.1.is.too.long.2.characters", str, String.valueOf(length)));
        }
    }

    public PdfName(byte[] bArr) {
        super(4, bArr);
        this.hash = 0;
    }

    public int compareTo(PdfName pdfName) {
        byte[] bArr = this.bytes;
        byte[] bArr2 = pdfName.bytes;
        int min = Math.min(bArr.length, bArr2.length);
        for (int i = 0; i < min; i++) {
            if (bArr[i] > bArr2[i]) {
                return 1;
            }
            if (bArr[i] < bArr2[i]) {
                return -1;
            }
        }
        if (bArr.length < bArr2.length) {
            return -1;
        }
        return bArr.length > bArr2.length ? 1 : 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PdfName) || compareTo((PdfName) obj) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = this.hash;
        if (i == 0) {
            int length = this.bytes.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                i = (i * 31) + (this.bytes[i3] & UByte.MAX_VALUE);
                i2++;
                i3++;
            }
            this.hash = i;
        }
        return i;
    }

    public static byte[] encodeName(String str) {
        int length = str.length();
        ByteBuffer byteBuffer = new ByteBuffer(length + 20);
        byteBuffer.append('/');
        char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            char c = (char) (charArray[i] & 255);
            if (c == ' ' || c == '#' || c == '%' || c == '/' || c == '<' || c == '>' || c == '[' || c == ']' || c == '{' || c == '}' || c == '(' || c == ')') {
                byteBuffer.append('#');
                byteBuffer.append(Integer.toString(c, 16));
            } else if (c < ' ' || c > '~') {
                byteBuffer.append('#');
                if (c < 16) {
                    byteBuffer.append('0');
                }
                byteBuffer.append(Integer.toString(c, 16));
            } else {
                byteBuffer.append(c);
            }
        }
        return byteBuffer.toByteArray();
    }

    public static String decodeName(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            int length = str.length();
            int i = 1;
            while (i < length) {
                char charAt = str.charAt(i);
                if (charAt == '#') {
                    char charAt2 = str.charAt(i + 1);
                    i += 2;
                    charAt = (char) ((PRTokeniser.getHex(charAt2) << 4) + PRTokeniser.getHex(str.charAt(i)));
                }
                stringBuffer.append(charAt);
                i++;
            }
        } catch (IndexOutOfBoundsException unused) {
        }
        return stringBuffer.toString();
    }
}
