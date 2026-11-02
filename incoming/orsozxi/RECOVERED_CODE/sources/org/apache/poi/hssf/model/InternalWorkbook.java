package org.apache.poi.hssf.model;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.ddf.EscherBSERecord;
import org.apache.poi.ddf.EscherBoolProperty;
import org.apache.poi.ddf.EscherContainerRecord;
import org.apache.poi.ddf.EscherDgRecord;
import org.apache.poi.ddf.EscherDggRecord;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperties;
import org.apache.poi.ddf.EscherRGBProperty;
import org.apache.poi.ddf.EscherRecord;
import org.apache.poi.ddf.EscherSpRecord;
import org.apache.poi.ddf.EscherSplitMenuColorsRecord;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BackupRecord;
import org.apache.poi.hssf.record.BookBoolRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.CodepageRecord;
import org.apache.poi.hssf.record.CountryRecord;
import org.apache.poi.hssf.record.DSFRecord;
import org.apache.poi.hssf.record.DateWindow1904Record;
import org.apache.poi.hssf.record.DrawingGroupRecord;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.EscherAggregate;
import org.apache.poi.hssf.record.ExtSSTRecord;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.FileSharingRecord;
import org.apache.poi.hssf.record.FnGroupCountRecord;
import org.apache.poi.hssf.record.FontRecord;
import org.apache.poi.hssf.record.FormatRecord;
import org.apache.poi.hssf.record.HideObjRecord;
import org.apache.poi.hssf.record.HyperlinkRecord;
import org.apache.poi.hssf.record.InterfaceEndRecord;
import org.apache.poi.hssf.record.InterfaceHdrRecord;
import org.apache.poi.hssf.record.MMSRecord;
import org.apache.poi.hssf.record.NameCommentRecord;
import org.apache.poi.hssf.record.NameRecord;
import org.apache.poi.hssf.record.PaletteRecord;
import org.apache.poi.hssf.record.PasswordRecord;
import org.apache.poi.hssf.record.PasswordRev4Record;
import org.apache.poi.hssf.record.PrecisionRecord;
import org.apache.poi.hssf.record.ProtectRecord;
import org.apache.poi.hssf.record.ProtectionRev4Record;
import org.apache.poi.hssf.record.RecalcIdRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RefreshAllRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StyleRecord;
import org.apache.poi.hssf.record.TabIdRecord;
import org.apache.poi.hssf.record.UseSelFSRecord;
import org.apache.poi.hssf.record.WindowOneRecord;
import org.apache.poi.hssf.record.WindowProtectRecord;
import org.apache.poi.hssf.record.WriteAccessRecord;
import org.apache.poi.hssf.record.WriteProtectRecord;
import org.apache.poi.hssf.record.common.UnicodeString;
import org.apache.poi.hssf.record.formula.FormulaShifter;
import org.apache.poi.hssf.record.formula.NameXPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.formula.EvaluationWorkbook;
import org.apache.poi.util.Internal;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

@Internal
/* loaded from: classes3.dex */
public final class InternalWorkbook {
    private static final short CODEPAGE = 1200;
    private static final int MAX_SENSITIVE_SHEET_NAME_LEN = 31;
    private DrawingManager2 drawingManager;
    private FileSharingRecord fileShare;
    private LinkTable linkTable;
    protected SSTRecord sst;
    private WindowOneRecord windowOne;
    private WriteAccessRecord writeAccess;
    private WriteProtectRecord writeProtect;
    private static final POILogger log = POILogFactory.getLogger(InternalWorkbook.class);
    private static final int DEBUG = POILogger.DEBUG;
    private final WorkbookRecordList records = new WorkbookRecordList();
    private final List<BoundSheetRecord> boundsheets = new ArrayList();
    private final List<FormatRecord> formats = new ArrayList();
    private final List<HyperlinkRecord> hyperlinks = new ArrayList();
    private int numxfs = 0;
    private int numfonts = 0;
    private int maxformatid = -1;
    private boolean uses1904datewindowing = false;
    private List<EscherBSERecord> escherBSERecords = new ArrayList();
    private final Map<String, NameCommentRecord> commentRecords = new LinkedHashMap();

    private InternalWorkbook() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0374, code lost:
    
        if (r0.windowOne != null) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0376, code lost:
    
        r0.windowOne = createWindowOne();
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0384, code lost:
    
        if (org.apache.poi.hssf.model.InternalWorkbook.log.check(org.apache.poi.util.POILogger.DEBUG) == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0386, code lost:
    
        org.apache.poi.hssf.model.InternalWorkbook.log.log(org.apache.poi.hssf.model.InternalWorkbook.DEBUG, "exit create workbook from existing file function");
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x038f, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0357, code lost:
    
        if (r3 >= r11.size()) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0359, code lost:
    
        r1 = r11.get(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0365, code lost:
    
        if (r1.getSid() == 440) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0368, code lost:
    
        r0.hyperlinks.add((org.apache.poi.hssf.record.HyperlinkRecord) r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x036f, code lost:
    
        r3 = r3 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.apache.poi.hssf.model.InternalWorkbook createWorkbook(java.util.List<org.apache.poi.hssf.record.Record> r11) {
        /*
            Method dump skipped, instructions count: 986
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.hssf.model.InternalWorkbook.createWorkbook(java.util.List):org.apache.poi.hssf.model.InternalWorkbook");
    }

    public static InternalWorkbook createWorkbook() {
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "creating new workbook from scratch");
        }
        InternalWorkbook internalWorkbook = new InternalWorkbook();
        ArrayList arrayList = new ArrayList(30);
        internalWorkbook.records.setRecords(arrayList);
        List<FormatRecord> list = internalWorkbook.formats;
        arrayList.add(createBOF());
        arrayList.add(new InterfaceHdrRecord(1200));
        arrayList.add(createMMS());
        arrayList.add(InterfaceEndRecord.instance);
        arrayList.add(createWriteAccess());
        arrayList.add(createCodepage());
        arrayList.add(createDSF());
        arrayList.add(createTabId());
        internalWorkbook.records.setTabpos(arrayList.size() - 1);
        arrayList.add(createFnGroupCount());
        arrayList.add(createWindowProtect());
        arrayList.add(createProtect());
        internalWorkbook.records.setProtpos(arrayList.size() - 1);
        arrayList.add(createPassword());
        arrayList.add(createProtectionRev4());
        arrayList.add(createPasswordRev4());
        WindowOneRecord windowOneRecordCreateWindowOne = createWindowOne();
        internalWorkbook.windowOne = windowOneRecordCreateWindowOne;
        arrayList.add(windowOneRecordCreateWindowOne);
        arrayList.add(createBackup());
        internalWorkbook.records.setBackuppos(arrayList.size() - 1);
        arrayList.add(createHideObj());
        arrayList.add(createDateWindow1904());
        arrayList.add(createPrecision());
        arrayList.add(createRefreshAll());
        arrayList.add(createBookBool());
        arrayList.add(createFont());
        arrayList.add(createFont());
        arrayList.add(createFont());
        arrayList.add(createFont());
        internalWorkbook.records.setFontpos(arrayList.size() - 1);
        internalWorkbook.numfonts = 4;
        for (int i = 0; i <= 7; i++) {
            FormatRecord formatRecordCreateFormat = createFormat(i);
            internalWorkbook.maxformatid = internalWorkbook.maxformatid >= formatRecordCreateFormat.getIndexCode() ? internalWorkbook.maxformatid : formatRecordCreateFormat.getIndexCode();
            list.add(formatRecordCreateFormat);
            arrayList.add(formatRecordCreateFormat);
        }
        for (int i2 = 0; i2 < 21; i2++) {
            arrayList.add(createExtendedFormat(i2));
            internalWorkbook.numxfs++;
        }
        internalWorkbook.records.setXfpos(arrayList.size() - 1);
        for (int i3 = 0; i3 < 6; i3++) {
            arrayList.add(createStyle(i3));
        }
        arrayList.add(createUseSelFS());
        for (int i4 = 0; i4 < 1; i4++) {
            BoundSheetRecord boundSheetRecordCreateBoundSheet = createBoundSheet(i4);
            arrayList.add(boundSheetRecordCreateBoundSheet);
            internalWorkbook.boundsheets.add(boundSheetRecordCreateBoundSheet);
            internalWorkbook.records.setBspos(arrayList.size() - 1);
        }
        arrayList.add(createCountry());
        for (int i5 = 0; i5 < 1; i5++) {
            internalWorkbook.getOrCreateLinkTable().checkExternSheet(i5);
        }
        SSTRecord sSTRecord = new SSTRecord();
        internalWorkbook.sst = sSTRecord;
        arrayList.add(sSTRecord);
        arrayList.add(createExtendedSST());
        arrayList.add(EOFRecord.instance);
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "exit create new workbook from scratch");
        }
        return internalWorkbook;
    }

    public NameRecord getSpecificBuiltinRecord(byte b, int i) {
        return getOrCreateLinkTable().getSpecificBuiltinRecord(b, i);
    }

    public void removeBuiltinRecord(byte b, int i) {
        this.linkTable.removeBuiltinRecord(b, i);
    }

    public int getNumRecords() {
        return this.records.size();
    }

    public FontRecord getFontRecordAt(int i) {
        int i2 = i > 4 ? i - 1 : i;
        if (i2 > this.numfonts - 1) {
            throw new ArrayIndexOutOfBoundsException("There are only " + this.numfonts + " font records, you asked for " + i);
        }
        WorkbookRecordList workbookRecordList = this.records;
        return (FontRecord) workbookRecordList.get((workbookRecordList.getFontpos() - (this.numfonts - 1)) + i2);
    }

    public int getFontIndex(FontRecord fontRecord) {
        int i = 0;
        while (i <= this.numfonts) {
            WorkbookRecordList workbookRecordList = this.records;
            if (((FontRecord) workbookRecordList.get((workbookRecordList.getFontpos() - (this.numfonts - 1)) + i)) == fontRecord) {
                return i > 3 ? i + 1 : i;
            }
            i++;
        }
        throw new IllegalArgumentException("Could not find that font!");
    }

    public FontRecord createNewFont() {
        FontRecord fontRecordCreateFont = createFont();
        WorkbookRecordList workbookRecordList = this.records;
        workbookRecordList.add(workbookRecordList.getFontpos() + 1, fontRecordCreateFont);
        WorkbookRecordList workbookRecordList2 = this.records;
        workbookRecordList2.setFontpos(workbookRecordList2.getFontpos() + 1);
        this.numfonts++;
        return fontRecordCreateFont;
    }

    public void removeFontRecord(FontRecord fontRecord) {
        this.records.remove(fontRecord);
        this.numfonts--;
    }

    public int getNumberOfFontRecords() {
        return this.numfonts;
    }

    public void setSheetBof(int i, int i2) {
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "setting bof for sheetnum =", Integer.valueOf(i), " at pos=", Integer.valueOf(i2));
        }
        checkSheets(i);
        getBoundSheetRec(i).setPositionOfBof(i2);
    }

    private BoundSheetRecord getBoundSheetRec(int i) {
        return this.boundsheets.get(i);
    }

    public BackupRecord getBackupRecord() {
        WorkbookRecordList workbookRecordList = this.records;
        return (BackupRecord) workbookRecordList.get(workbookRecordList.getBackuppos());
    }

    public void setSheetName(int i, String str) {
        checkSheets(i);
        this.boundsheets.get(i).setSheetname(str);
    }

    public boolean doesContainsSheetName(String str, int i) {
        if (str.length() > 31) {
            str = str.substring(0, 31);
        }
        for (int i2 = 0; i2 < this.boundsheets.size(); i2++) {
            BoundSheetRecord boundSheetRec = getBoundSheetRec(i2);
            if (i != i2) {
                String sheetname = boundSheetRec.getSheetname();
                if (sheetname.length() > 31) {
                    sheetname = sheetname.substring(0, 31);
                }
                if (str.equalsIgnoreCase(sheetname)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setSheetOrder(String str, int i) {
        int sheetIndex = getSheetIndex(str);
        List<BoundSheetRecord> list = this.boundsheets;
        list.add(i, list.remove(sheetIndex));
    }

    public String getSheetName(int i) {
        return getBoundSheetRec(i).getSheetname();
    }

    public boolean isSheetHidden(int i) {
        return getBoundSheetRec(i).isHidden();
    }

    public boolean isSheetVeryHidden(int i) {
        return getBoundSheetRec(i).isVeryHidden();
    }

    public void setSheetHidden(int i, boolean z) {
        getBoundSheetRec(i).setHidden(z);
    }

    public void setSheetHidden(int i, int i2) {
        BoundSheetRecord boundSheetRec = getBoundSheetRec(i);
        boolean z = false;
        boolean z2 = true;
        if (i2 == 0) {
            z2 = false;
        } else if (i2 == 1) {
            z = true;
            z2 = false;
        } else if (i2 != 2) {
            throw new IllegalArgumentException("Invalid hidden flag " + i2 + " given, must be 0, 1 or 2");
        }
        boundSheetRec.setHidden(z);
        boundSheetRec.setVeryHidden(z2);
    }

    public int getSheetIndex(String str) {
        for (int i = 0; i < this.boundsheets.size(); i++) {
            if (getSheetName(i).equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }

    private void checkSheets(int i) {
        if (this.boundsheets.size() <= i) {
            if (this.boundsheets.size() + 1 <= i) {
                throw new RuntimeException("Sheet number out of bounds!");
            }
            BoundSheetRecord boundSheetRecordCreateBoundSheet = createBoundSheet(i);
            WorkbookRecordList workbookRecordList = this.records;
            workbookRecordList.add(workbookRecordList.getBspos() + 1, boundSheetRecordCreateBoundSheet);
            WorkbookRecordList workbookRecordList2 = this.records;
            workbookRecordList2.setBspos(workbookRecordList2.getBspos() + 1);
            this.boundsheets.add(boundSheetRecordCreateBoundSheet);
            getOrCreateLinkTable().checkExternSheet(i);
            fixTabIdRecord();
            return;
        }
        if (this.records.getTabpos() > 0) {
            WorkbookRecordList workbookRecordList3 = this.records;
            if (((TabIdRecord) workbookRecordList3.get(workbookRecordList3.getTabpos()))._tabids.length < this.boundsheets.size()) {
                fixTabIdRecord();
            }
        }
    }

    public void removeSheet(int i) {
        if (this.boundsheets.size() > i) {
            WorkbookRecordList workbookRecordList = this.records;
            workbookRecordList.remove((workbookRecordList.getBspos() - (this.boundsheets.size() - 1)) + i);
            this.boundsheets.remove(i);
            fixTabIdRecord();
        }
        int i2 = i + 1;
        for (int i3 = 0; i3 < getNumNames(); i3++) {
            NameRecord nameRecord = getNameRecord(i3);
            if (nameRecord.getSheetNumber() == i2) {
                nameRecord.setSheetNumber(0);
            } else if (nameRecord.getSheetNumber() > i2) {
                nameRecord.setSheetNumber(nameRecord.getSheetNumber() - 1);
            }
        }
    }

    private void fixTabIdRecord() {
        WorkbookRecordList workbookRecordList = this.records;
        TabIdRecord tabIdRecord = (TabIdRecord) workbookRecordList.get(workbookRecordList.getTabpos());
        int size = this.boundsheets.size();
        short[] sArr = new short[size];
        for (short s = 0; s < size; s = (short) (s + 1)) {
            sArr[s] = s;
        }
        tabIdRecord.setTabIdArray(sArr);
    }

    public int getNumSheets() {
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "getNumSheets=", Integer.valueOf(this.boundsheets.size()));
        }
        return this.boundsheets.size();
    }

    public int getNumExFormats() {
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "getXF=", Integer.valueOf(this.numxfs));
        }
        return this.numxfs;
    }

    public ExtendedFormatRecord getExFormatAt(int i) {
        return (ExtendedFormatRecord) this.records.get((this.records.getXfpos() - (this.numxfs - 1)) + i);
    }

    public void removeExFormatRecord(ExtendedFormatRecord extendedFormatRecord) {
        this.records.remove(extendedFormatRecord);
        this.numxfs--;
    }

    public ExtendedFormatRecord createCellXF() {
        ExtendedFormatRecord extendedFormatRecordCreateExtendedFormat = createExtendedFormat();
        WorkbookRecordList workbookRecordList = this.records;
        workbookRecordList.add(workbookRecordList.getXfpos() + 1, extendedFormatRecordCreateExtendedFormat);
        WorkbookRecordList workbookRecordList2 = this.records;
        workbookRecordList2.setXfpos(workbookRecordList2.getXfpos() + 1);
        this.numxfs++;
        return extendedFormatRecordCreateExtendedFormat;
    }

    public StyleRecord getStyleRecord(int i) {
        for (int xfpos = this.records.getXfpos(); xfpos < this.records.size(); xfpos++) {
            Record record = this.records.get(xfpos);
            if (!(record instanceof ExtendedFormatRecord) && (record instanceof StyleRecord)) {
                StyleRecord styleRecord = (StyleRecord) record;
                if (styleRecord.getXFIndex() == i) {
                    return styleRecord;
                }
            }
        }
        return null;
    }

    public StyleRecord createStyleRecord(int i) {
        StyleRecord styleRecord = new StyleRecord();
        styleRecord.setXFIndex(i);
        int i2 = -1;
        for (int xfpos = this.records.getXfpos(); xfpos < this.records.size() && i2 == -1; xfpos++) {
            Record record = this.records.get(xfpos);
            if (!(record instanceof ExtendedFormatRecord) && !(record instanceof StyleRecord)) {
                i2 = xfpos;
            }
        }
        if (i2 == -1) {
            throw new IllegalStateException("No XF Records found!");
        }
        this.records.add(i2, styleRecord);
        return styleRecord;
    }

    public int addSSTString(UnicodeString unicodeString) {
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "insert to sst string='", unicodeString);
        }
        if (this.sst == null) {
            insertSST();
        }
        return this.sst.addString(unicodeString);
    }

    public UnicodeString getSSTString(int i) {
        if (this.sst == null) {
            insertSST();
        }
        UnicodeString string = this.sst.getString(i);
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "Returning SST for index=", Integer.valueOf(i), " String= ", string);
        }
        return string;
    }

    public void insertSST() {
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "creating new SST via insertSST!");
        }
        this.sst = new SSTRecord();
        this.records.add(r0.size() - 1, createExtendedSST());
        this.records.add(r0.size() - 2, this.sst);
    }

    public int serialize(int i, byte[] bArr) {
        int iSerialize;
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "Serializing Workbook with offsets");
        }
        SSTRecord sSTRecord = null;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        for (int i4 = 0; i4 < this.records.size(); i4++) {
            Record recordCreateExtSSTRecord = this.records.get(i4);
            if (recordCreateExtSSTRecord.getSid() != 449 || ((RecalcIdRecord) recordCreateExtSSTRecord).isNeeded()) {
                if (recordCreateExtSSTRecord instanceof SSTRecord) {
                    sSTRecord = (SSTRecord) recordCreateExtSSTRecord;
                    i3 = i2;
                }
                if (recordCreateExtSSTRecord.getSid() == 255 && sSTRecord != null) {
                    recordCreateExtSSTRecord = sSTRecord.createExtSSTRecord(i3 + i);
                }
                if (!(recordCreateExtSSTRecord instanceof BoundSheetRecord)) {
                    iSerialize = recordCreateExtSSTRecord.serialize(i2 + i, bArr);
                } else if (z) {
                    iSerialize = 0;
                } else {
                    iSerialize = 0;
                    for (int i5 = 0; i5 < this.boundsheets.size(); i5++) {
                        iSerialize += getBoundSheetRec(i5).serialize(i2 + i + iSerialize, bArr);
                    }
                    z = true;
                }
                i2 += iSerialize;
            }
        }
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "Exiting serialize workbook");
        }
        return i2;
    }

    public int getSize() {
        int recordSize;
        SSTRecord sSTRecord = null;
        int i = 0;
        for (int i2 = 0; i2 < this.records.size(); i2++) {
            Record record = this.records.get(i2);
            if (record.getSid() != 449 || ((RecalcIdRecord) record).isNeeded()) {
                if (record instanceof SSTRecord) {
                    sSTRecord = (SSTRecord) record;
                }
                if (record.getSid() == 255 && sSTRecord != null) {
                    recordSize = sSTRecord.calcExtSSTRecordSize();
                } else {
                    recordSize = record.getRecordSize();
                }
                i += recordSize;
            }
        }
        return i;
    }

    private static BOFRecord createBOF() {
        BOFRecord bOFRecord = new BOFRecord();
        bOFRecord.setVersion(1536);
        bOFRecord.setType(5);
        bOFRecord.setBuild(4307);
        bOFRecord.setBuildYear(1996);
        bOFRecord.setHistoryBitMask(65);
        bOFRecord.setRequiredVersion(6);
        return bOFRecord;
    }

    private static MMSRecord createMMS() {
        MMSRecord mMSRecord = new MMSRecord();
        mMSRecord.setAddMenuCount((byte) 0);
        mMSRecord.setDelMenuCount((byte) 0);
        return mMSRecord;
    }

    private static WriteAccessRecord createWriteAccess() {
        WriteAccessRecord writeAccessRecord = new WriteAccessRecord();
        try {
            writeAccessRecord.setUsername(System.getProperty("user.name"));
        } catch (AccessControlException unused) {
            writeAccessRecord.setUsername("POI");
        }
        return writeAccessRecord;
    }

    private static CodepageRecord createCodepage() {
        CodepageRecord codepageRecord = new CodepageRecord();
        codepageRecord.setCodepage((short) 1200);
        return codepageRecord;
    }

    private static DSFRecord createDSF() {
        return new DSFRecord(false);
    }

    private static TabIdRecord createTabId() {
        return new TabIdRecord();
    }

    private static FnGroupCountRecord createFnGroupCount() {
        FnGroupCountRecord fnGroupCountRecord = new FnGroupCountRecord();
        fnGroupCountRecord.setCount((short) 14);
        return fnGroupCountRecord;
    }

    private static WindowProtectRecord createWindowProtect() {
        return new WindowProtectRecord(false);
    }

    private static ProtectRecord createProtect() {
        return new ProtectRecord(false);
    }

    private static PasswordRecord createPassword() {
        return new PasswordRecord(0);
    }

    private static ProtectionRev4Record createProtectionRev4() {
        return new ProtectionRev4Record(false);
    }

    private static PasswordRev4Record createPasswordRev4() {
        return new PasswordRev4Record(0);
    }

    private static WindowOneRecord createWindowOne() {
        WindowOneRecord windowOneRecord = new WindowOneRecord();
        windowOneRecord.setHorizontalHold((short) 360);
        windowOneRecord.setVerticalHold(EscherProperties.BLIP__PICTURELINE);
        windowOneRecord.setWidth((short) 14940);
        windowOneRecord.setHeight((short) 9150);
        windowOneRecord.setOptions((short) 56);
        windowOneRecord.setActiveSheetIndex(0);
        windowOneRecord.setFirstVisibleTab(0);
        windowOneRecord.setNumSelectedTabs((short) 1);
        windowOneRecord.setTabWidthRatio((short) 600);
        return windowOneRecord;
    }

    private static BackupRecord createBackup() {
        BackupRecord backupRecord = new BackupRecord();
        backupRecord.setBackup((short) 0);
        return backupRecord;
    }

    private static HideObjRecord createHideObj() {
        HideObjRecord hideObjRecord = new HideObjRecord();
        hideObjRecord.setHideObj((short) 0);
        return hideObjRecord;
    }

    private static DateWindow1904Record createDateWindow1904() {
        DateWindow1904Record dateWindow1904Record = new DateWindow1904Record();
        dateWindow1904Record.setWindowing((short) 0);
        return dateWindow1904Record;
    }

    private static PrecisionRecord createPrecision() {
        PrecisionRecord precisionRecord = new PrecisionRecord();
        precisionRecord.setFullPrecision(true);
        return precisionRecord;
    }

    private static RefreshAllRecord createRefreshAll() {
        return new RefreshAllRecord(false);
    }

    private static BookBoolRecord createBookBool() {
        BookBoolRecord bookBoolRecord = new BookBoolRecord();
        bookBoolRecord.setSaveLinkValues((short) 0);
        return bookBoolRecord;
    }

    private static FontRecord createFont() {
        FontRecord fontRecord = new FontRecord();
        fontRecord.setFontHeight(EscherAggregate.ST_ACTIONBUTTONMOVIE);
        fontRecord.setAttributes((short) 0);
        fontRecord.setColorPaletteIndex(Short.MAX_VALUE);
        fontRecord.setBoldWeight((short) 400);
        fontRecord.setFontName(HSSFFont.FONT_ARIAL);
        return fontRecord;
    }

    private static FormatRecord createFormat(int i) {
        switch (i) {
            case 0:
                return new FormatRecord(5, "\"$\"#,##0_);\\(\"$\"#,##0\\)");
            case 1:
                return new FormatRecord(6, "\"$\"#,##0_);[Red]\\(\"$\"#,##0\\)");
            case 2:
                return new FormatRecord(7, "\"$\"#,##0.00_);\\(\"$\"#,##0.00\\)");
            case 3:
                return new FormatRecord(8, "\"$\"#,##0.00_);[Red]\\(\"$\"#,##0.00\\)");
            case 4:
                return new FormatRecord(42, "_(\"$\"* #,##0_);_(\"$\"* \\(#,##0\\);_(\"$\"* \"-\"_);_(@_)");
            case 5:
                return new FormatRecord(41, "_(* #,##0_);_(* \\(#,##0\\);_(* \"-\"_);_(@_)");
            case 6:
                return new FormatRecord(44, "_(\"$\"* #,##0.00_);_(\"$\"* \\(#,##0.00\\);_(\"$\"* \"-\"??_);_(@_)");
            case 7:
                return new FormatRecord(43, "_(* #,##0.00_);_(* \\(#,##0.00\\);_(* \"-\"??_);_(@_)");
            default:
                throw new IllegalArgumentException("Unexpected id " + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static ExtendedFormatRecord createExtendedFormat(int i) {
        ExtendedFormatRecord extendedFormatRecord = new ExtendedFormatRecord();
        switch (i) {
            case 0:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) 0);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 1:
                extendedFormatRecord.setFontIndex((short) 1);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 2:
                extendedFormatRecord.setFontIndex((short) 1);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 3:
                extendedFormatRecord.setFontIndex((short) 2);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 4:
                extendedFormatRecord.setFontIndex((short) 2);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 5:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 6:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 7:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 8:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 9:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 10:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 11:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 12:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 13:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 14:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -3072);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 15:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) 1);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) 0);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 16:
                extendedFormatRecord.setFontIndex((short) 1);
                extendedFormatRecord.setFormatIndex((short) 43);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -2048);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 17:
                extendedFormatRecord.setFontIndex((short) 1);
                extendedFormatRecord.setFormatIndex((short) 41);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -2048);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 18:
                extendedFormatRecord.setFontIndex((short) 1);
                extendedFormatRecord.setFormatIndex((short) 44);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -2048);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 19:
                extendedFormatRecord.setFontIndex((short) 1);
                extendedFormatRecord.setFormatIndex((short) 42);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -2048);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 20:
                extendedFormatRecord.setFontIndex((short) 1);
                extendedFormatRecord.setFormatIndex((short) 9);
                extendedFormatRecord.setCellOptions((short) -11);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) -2048);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 21:
                extendedFormatRecord.setFontIndex((short) 5);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) 1);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) 2048);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 22:
                extendedFormatRecord.setFontIndex((short) 6);
                extendedFormatRecord.setFormatIndex((short) 0);
                extendedFormatRecord.setCellOptions((short) 1);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) 23552);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 23:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 49);
                extendedFormatRecord.setCellOptions((short) 1);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) 23552);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 24:
                extendedFormatRecord.setFontIndex((short) 0);
                extendedFormatRecord.setFormatIndex((short) 8);
                extendedFormatRecord.setCellOptions((short) 1);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) 23552);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            case 25:
                extendedFormatRecord.setFontIndex((short) 6);
                extendedFormatRecord.setFormatIndex((short) 8);
                extendedFormatRecord.setCellOptions((short) 1);
                extendedFormatRecord.setAlignmentOptions((short) 32);
                extendedFormatRecord.setIndentionOptions((short) 23552);
                extendedFormatRecord.setBorderOptions((short) 0);
                extendedFormatRecord.setPaletteOptions((short) 0);
                extendedFormatRecord.setAdtlPaletteOptions((short) 0);
                extendedFormatRecord.setFillPaletteOptions((short) 8384);
                return extendedFormatRecord;
            default:
                return extendedFormatRecord;
        }
    }

    private static ExtendedFormatRecord createExtendedFormat() {
        ExtendedFormatRecord extendedFormatRecord = new ExtendedFormatRecord();
        extendedFormatRecord.setFontIndex((short) 0);
        extendedFormatRecord.setFormatIndex((short) 0);
        extendedFormatRecord.setCellOptions((short) 1);
        extendedFormatRecord.setAlignmentOptions((short) 32);
        extendedFormatRecord.setIndentionOptions((short) 0);
        extendedFormatRecord.setBorderOptions((short) 0);
        extendedFormatRecord.setPaletteOptions((short) 0);
        extendedFormatRecord.setAdtlPaletteOptions((short) 0);
        extendedFormatRecord.setFillPaletteOptions((short) 8384);
        extendedFormatRecord.setTopBorderPaletteIdx((short) 8);
        extendedFormatRecord.setBottomBorderPaletteIdx((short) 8);
        extendedFormatRecord.setLeftBorderPaletteIdx((short) 8);
        extendedFormatRecord.setRightBorderPaletteIdx((short) 8);
        return extendedFormatRecord;
    }

    private static StyleRecord createStyle(int i) {
        StyleRecord styleRecord = new StyleRecord();
        if (i == 0) {
            styleRecord.setXFIndex(16);
            styleRecord.setBuiltinStyle(3);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 1) {
            styleRecord.setXFIndex(17);
            styleRecord.setBuiltinStyle(6);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 2) {
            styleRecord.setXFIndex(18);
            styleRecord.setBuiltinStyle(4);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 3) {
            styleRecord.setXFIndex(19);
            styleRecord.setBuiltinStyle(7);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 4) {
            styleRecord.setXFIndex(0);
            styleRecord.setBuiltinStyle(0);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 5) {
            styleRecord.setXFIndex(20);
            styleRecord.setBuiltinStyle(5);
            styleRecord.setOutlineStyleLevel(-1);
        }
        return styleRecord;
    }

    private static PaletteRecord createPalette() {
        return new PaletteRecord();
    }

    private static UseSelFSRecord createUseSelFS() {
        return new UseSelFSRecord(false);
    }

    private static BoundSheetRecord createBoundSheet(int i) {
        return new BoundSheetRecord("Sheet" + (i + 1));
    }

    private static CountryRecord createCountry() {
        CountryRecord countryRecord = new CountryRecord();
        countryRecord.setDefaultCountry((short) 1);
        if (Locale.getDefault().toString().equals("ru_RU")) {
            countryRecord.setCurrentCountry((short) 7);
        } else {
            countryRecord.setCurrentCountry((short) 1);
        }
        return countryRecord;
    }

    private static ExtSSTRecord createExtendedSST() {
        ExtSSTRecord extSSTRecord = new ExtSSTRecord();
        extSSTRecord.setNumStringsPerBucket((short) 8);
        return extSSTRecord;
    }

    private LinkTable getOrCreateLinkTable() {
        if (this.linkTable == null) {
            this.linkTable = new LinkTable((short) getNumSheets(), this.records);
        }
        return this.linkTable;
    }

    public String findSheetNameFromExternSheet(int i) {
        int indexToInternalSheet = this.linkTable.getIndexToInternalSheet(i);
        return (indexToInternalSheet >= 0 && indexToInternalSheet < this.boundsheets.size()) ? getSheetName(indexToInternalSheet) : "";
    }

    public EvaluationWorkbook.ExternalSheet getExternalSheet(int i) {
        String[] externalBookAndSheetName = this.linkTable.getExternalBookAndSheetName(i);
        if (externalBookAndSheetName == null) {
            return null;
        }
        return new EvaluationWorkbook.ExternalSheet(externalBookAndSheetName[0], externalBookAndSheetName[1]);
    }

    public EvaluationWorkbook.ExternalName getExternalName(int i, int i2) {
        String strResolveNameXText = this.linkTable.resolveNameXText(i, i2);
        if (strResolveNameXText == null) {
            return null;
        }
        return new EvaluationWorkbook.ExternalName(strResolveNameXText, i2, this.linkTable.resolveNameXIx(i, i2));
    }

    public int getSheetIndexFromExternSheetIndex(int i) {
        return this.linkTable.getSheetIndexFromExternSheetIndex(i);
    }

    public short checkExternSheet(int i) {
        return (short) getOrCreateLinkTable().checkExternSheet(i);
    }

    public int getExternalSheetIndex(String str, String str2) {
        return getOrCreateLinkTable().getExternalSheetIndex(str, str2);
    }

    public int getNumNames() {
        LinkTable linkTable = this.linkTable;
        if (linkTable == null) {
            return 0;
        }
        return linkTable.getNumNames();
    }

    public NameRecord getNameRecord(int i) {
        return this.linkTable.getNameRecord(i);
    }

    public NameCommentRecord getNameCommentRecord(NameRecord nameRecord) {
        return this.commentRecords.get(nameRecord.getNameText());
    }

    public NameRecord createName() {
        return addName(new NameRecord());
    }

    public NameRecord addName(NameRecord nameRecord) {
        getOrCreateLinkTable().addName(nameRecord);
        return nameRecord;
    }

    public NameRecord createBuiltInName(byte b, int i) {
        if (i < 0 || i + 1 > 32767) {
            throw new IllegalArgumentException("Sheet number [" + i + "]is not valid ");
        }
        NameRecord nameRecord = new NameRecord(b, i);
        if (this.linkTable.nameAlreadyExists(nameRecord)) {
            throw new RuntimeException("Builtin (" + ((int) b) + ") already exists for sheet (" + i + ")");
        }
        addName(nameRecord);
        return nameRecord;
    }

    public void removeName(int i) {
        if (this.linkTable.getNumNames() > i) {
            this.records.remove(findFirstRecordLocBySid((short) 24) + i);
            this.linkTable.removeName(i);
        }
    }

    public void updateNameCommentRecordCache(NameCommentRecord nameCommentRecord) {
        if (this.commentRecords.containsValue(nameCommentRecord)) {
            Iterator<Map.Entry<String, NameCommentRecord>> it2 = this.commentRecords.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Map.Entry<String, NameCommentRecord> next = it2.next();
                if (next.getValue().equals(nameCommentRecord)) {
                    this.commentRecords.remove(next.getKey());
                    break;
                }
            }
        }
        this.commentRecords.put(nameCommentRecord.getNameText(), nameCommentRecord);
    }

    public short getFormat(String str, boolean z) {
        int iCreateFormat;
        Iterator<FormatRecord> it2 = this.formats.iterator();
        while (true) {
            if (it2.hasNext()) {
                FormatRecord next = it2.next();
                if (next.getFormatString().equals(str)) {
                    iCreateFormat = next.getIndexCode();
                    break;
                }
            } else {
                if (!z) {
                    return (short) -1;
                }
                iCreateFormat = createFormat(str);
            }
        }
        return (short) iCreateFormat;
    }

    public List<FormatRecord> getFormats() {
        return this.formats;
    }

    public int createFormat(String str) {
        int i = this.maxformatid;
        int i2 = i >= 164 ? i + 1 : 164;
        this.maxformatid = i2;
        FormatRecord formatRecord = new FormatRecord(i2, str);
        int i3 = 0;
        while (i3 < this.records.size() && this.records.get(i3).getSid() != 1054) {
            i3++;
        }
        int size = i3 + this.formats.size();
        this.formats.add(formatRecord);
        this.records.add(size, formatRecord);
        return this.maxformatid;
    }

    public Record findFirstRecordBySid(short s) {
        Iterator<Record> it2 = this.records.iterator();
        while (it2.hasNext()) {
            Record next = it2.next();
            if (next.getSid() == s) {
                return next;
            }
        }
        return null;
    }

    public int findFirstRecordLocBySid(short s) {
        Iterator<Record> it2 = this.records.iterator();
        int i = 0;
        while (it2.hasNext()) {
            if (it2.next().getSid() == s) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Record findNextRecordBySid(short s, int i) {
        Iterator<Record> it2 = this.records.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            Record next = it2.next();
            if (next.getSid() == s) {
                int i3 = i2 + 1;
                if (i2 == i) {
                    return next;
                }
                i2 = i3;
            }
        }
        return null;
    }

    public List<HyperlinkRecord> getHyperlinks() {
        return this.hyperlinks;
    }

    public List<Record> getRecords() {
        return this.records.getRecords();
    }

    public boolean isUsing1904DateWindowing() {
        return this.uses1904datewindowing;
    }

    public PaletteRecord getCustomPalette() {
        int palettepos = this.records.getPalettepos();
        if (palettepos != -1) {
            Record record = this.records.get(palettepos);
            if (record instanceof PaletteRecord) {
                return (PaletteRecord) record;
            }
            throw new RuntimeException("InternalError: Expected PaletteRecord but got a '" + record + "'");
        }
        PaletteRecord paletteRecordCreatePalette = createPalette();
        this.records.add(1, paletteRecordCreatePalette);
        this.records.setPalettepos(1);
        return paletteRecordCreatePalette;
    }

    public void findDrawingGroup() {
        if (this.drawingManager != null) {
            return;
        }
        Iterator<Record> it2 = this.records.iterator();
        while (true) {
            EscherDggRecord escherDggRecord = null;
            if (it2.hasNext()) {
                Record next = it2.next();
                if (next instanceof DrawingGroupRecord) {
                    DrawingGroupRecord drawingGroupRecord = (DrawingGroupRecord) next;
                    drawingGroupRecord.processChildRecords();
                    EscherContainerRecord escherContainer = drawingGroupRecord.getEscherContainer();
                    if (escherContainer == null) {
                        continue;
                    } else {
                        Iterator<EscherRecord> childIterator = escherContainer.getChildIterator();
                        while (childIterator.hasNext()) {
                            EscherRecord next2 = childIterator.next();
                            if (next2 instanceof EscherDggRecord) {
                                escherDggRecord = (EscherDggRecord) next2;
                            }
                        }
                        if (escherDggRecord != null) {
                            this.drawingManager = new DrawingManager2(escherDggRecord);
                            return;
                        }
                    }
                }
            } else {
                int iFindFirstRecordLocBySid = findFirstRecordLocBySid(DrawingGroupRecord.sid);
                if (iFindFirstRecordLocBySid != -1) {
                    for (EscherRecord escherRecord : ((DrawingGroupRecord) this.records.get(iFindFirstRecordLocBySid)).getEscherRecords()) {
                        if (escherRecord instanceof EscherDggRecord) {
                            escherDggRecord = (EscherDggRecord) escherRecord;
                        }
                    }
                    if (escherDggRecord != null) {
                        this.drawingManager = new DrawingManager2(escherDggRecord);
                        return;
                    }
                    return;
                }
                return;
            }
        }
    }

    public void createDrawingGroup() {
        if (this.drawingManager == null) {
            EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
            EscherDggRecord escherDggRecord = new EscherDggRecord();
            EscherOptRecord escherOptRecord = new EscherOptRecord();
            EscherSplitMenuColorsRecord escherSplitMenuColorsRecord = new EscherSplitMenuColorsRecord();
            escherContainerRecord.setRecordId(EscherContainerRecord.DGG_CONTAINER);
            escherContainerRecord.setOptions((short) 15);
            escherDggRecord.setRecordId(EscherDggRecord.RECORD_ID);
            escherDggRecord.setOptions((short) 0);
            escherDggRecord.setShapeIdMax(1024);
            escherDggRecord.setNumShapesSaved(0);
            escherDggRecord.setDrawingsSaved(0);
            escherDggRecord.setFileIdClusters(new EscherDggRecord.FileIdCluster[0]);
            this.drawingManager = new DrawingManager2(escherDggRecord);
            EscherContainerRecord escherContainerRecord2 = null;
            if (this.escherBSERecords.size() > 0) {
                escherContainerRecord2 = new EscherContainerRecord();
                escherContainerRecord2.setRecordId(EscherContainerRecord.BSTORE_CONTAINER);
                escherContainerRecord2.setOptions((short) (15 | (this.escherBSERecords.size() << 4)));
                Iterator<EscherBSERecord> it2 = this.escherBSERecords.iterator();
                while (it2.hasNext()) {
                    escherContainerRecord2.addChildRecord(it2.next());
                }
            }
            escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
            escherOptRecord.setOptions((short) 51);
            escherOptRecord.addEscherProperty(new EscherBoolProperty((short) 191, 524296));
            escherOptRecord.addEscherProperty(new EscherRGBProperty(EscherProperties.FILL__FILLCOLOR, 134217793));
            escherOptRecord.addEscherProperty(new EscherRGBProperty(EscherProperties.LINESTYLE__COLOR, 134217792));
            escherSplitMenuColorsRecord.setRecordId(EscherSplitMenuColorsRecord.RECORD_ID);
            escherSplitMenuColorsRecord.setOptions((short) 64);
            escherSplitMenuColorsRecord.setColor1(134217741);
            escherSplitMenuColorsRecord.setColor2(134217740);
            escherSplitMenuColorsRecord.setColor3(134217751);
            escherSplitMenuColorsRecord.setColor4(268435703);
            escherContainerRecord.addChildRecord(escherDggRecord);
            if (escherContainerRecord2 != null) {
                escherContainerRecord.addChildRecord(escherContainerRecord2);
            }
            escherContainerRecord.addChildRecord(escherOptRecord);
            escherContainerRecord.addChildRecord(escherSplitMenuColorsRecord);
            int iFindFirstRecordLocBySid = findFirstRecordLocBySid(DrawingGroupRecord.sid);
            if (iFindFirstRecordLocBySid == -1) {
                DrawingGroupRecord drawingGroupRecord = new DrawingGroupRecord();
                drawingGroupRecord.addEscherRecord(escherContainerRecord);
                getRecords().add(findFirstRecordLocBySid((short) 140) + 1, drawingGroupRecord);
                return;
            }
            DrawingGroupRecord drawingGroupRecord2 = new DrawingGroupRecord();
            drawingGroupRecord2.addEscherRecord(escherContainerRecord);
            getRecords().set(iFindFirstRecordLocBySid, drawingGroupRecord2);
        }
    }

    public WindowOneRecord getWindowOne() {
        return this.windowOne;
    }

    public EscherBSERecord getBSERecord(int i) {
        return this.escherBSERecords.get(i - 1);
    }

    public int addBSERecord(EscherBSERecord escherBSERecord) {
        EscherContainerRecord escherContainerRecord;
        createDrawingGroup();
        this.escherBSERecords.add(escherBSERecord);
        EscherContainerRecord escherContainerRecord2 = (EscherContainerRecord) ((DrawingGroupRecord) getRecords().get(findFirstRecordLocBySid(DrawingGroupRecord.sid))).getEscherRecord(0);
        if (escherContainerRecord2.getChild(1).getRecordId() == -4095) {
            escherContainerRecord = (EscherContainerRecord) escherContainerRecord2.getChild(1);
        } else {
            EscherContainerRecord escherContainerRecord3 = new EscherContainerRecord();
            escherContainerRecord3.setRecordId(EscherContainerRecord.BSTORE_CONTAINER);
            List<EscherRecord> childRecords = escherContainerRecord2.getChildRecords();
            childRecords.add(1, escherContainerRecord3);
            escherContainerRecord2.setChildRecords(childRecords);
            escherContainerRecord = escherContainerRecord3;
        }
        escherContainerRecord.setOptions((short) ((this.escherBSERecords.size() << 4) | 15));
        escherContainerRecord.addChildRecord(escherBSERecord);
        return this.escherBSERecords.size();
    }

    public DrawingManager2 getDrawingManager() {
        return this.drawingManager;
    }

    public WriteProtectRecord getWriteProtect() {
        if (this.writeProtect == null) {
            this.writeProtect = new WriteProtectRecord();
            int i = 0;
            while (i < this.records.size() && !(this.records.get(i) instanceof BOFRecord)) {
                i++;
            }
            this.records.add(i + 1, this.writeProtect);
        }
        return this.writeProtect;
    }

    public WriteAccessRecord getWriteAccess() {
        if (this.writeAccess == null) {
            this.writeAccess = createWriteAccess();
            int i = 0;
            while (i < this.records.size() && !(this.records.get(i) instanceof InterfaceEndRecord)) {
                i++;
            }
            this.records.add(i + 1, this.writeAccess);
        }
        return this.writeAccess;
    }

    public FileSharingRecord getFileSharing() {
        if (this.fileShare == null) {
            this.fileShare = new FileSharingRecord();
            int i = 0;
            while (i < this.records.size() && !(this.records.get(i) instanceof WriteAccessRecord)) {
                i++;
            }
            this.records.add(i + 1, this.fileShare);
        }
        return this.fileShare;
    }

    public boolean isWriteProtected() {
        return this.fileShare != null && getFileSharing().getReadOnly() == 1;
    }

    public void writeProtectWorkbook(String str, String str2) {
        FileSharingRecord fileSharing = getFileSharing();
        WriteAccessRecord writeAccess = getWriteAccess();
        getWriteProtect();
        fileSharing.setReadOnly((short) 1);
        fileSharing.setPassword(FileSharingRecord.hashPassword(str));
        fileSharing.setUsername(str2);
        writeAccess.setUsername(str2);
    }

    public void unwriteProtectWorkbook() {
        this.records.remove(this.fileShare);
        this.records.remove(this.writeProtect);
        this.fileShare = null;
        this.writeProtect = null;
    }

    public String resolveNameXText(int i, int i2) {
        return this.linkTable.resolveNameXText(i, i2);
    }

    public NameXPtg getNameXPtg(String str) {
        return getOrCreateLinkTable().getNameXPtg(str);
    }

    public void cloneDrawings(InternalSheet internalSheet) {
        EscherContainerRecord escherContainer;
        findDrawingGroup();
        DrawingManager2 drawingManager2 = this.drawingManager;
        if (drawingManager2 == null || internalSheet.aggregateDrawingRecords(drawingManager2, false) == -1 || (escherContainer = ((EscherAggregate) internalSheet.findFirstRecordBySid(EscherAggregate.sid)).getEscherContainer()) == null) {
            return;
        }
        EscherDggRecord dgg = this.drawingManager.getDgg();
        short sFindNewDrawingGroupId = this.drawingManager.findNewDrawingGroupId();
        dgg.addCluster(sFindNewDrawingGroupId, 0);
        dgg.setDrawingsSaved(dgg.getDrawingsSaved() + 1);
        EscherDgRecord escherDgRecord = null;
        Iterator<EscherRecord> childIterator = escherContainer.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next instanceof EscherDgRecord) {
                EscherDgRecord escherDgRecord2 = (EscherDgRecord) next;
                escherDgRecord2.setOptions((short) (sFindNewDrawingGroupId << 4));
                escherDgRecord = escherDgRecord2;
            } else if (next instanceof EscherContainerRecord) {
                ArrayList<EscherSpRecord> arrayList = new ArrayList();
                ((EscherContainerRecord) next).getRecordsById(EscherSpRecord.RECORD_ID, arrayList);
                for (EscherSpRecord escherSpRecord : arrayList) {
                    int iAllocateShapeId = this.drawingManager.allocateShapeId(sFindNewDrawingGroupId, escherDgRecord);
                    escherDgRecord.setNumShapes(escherDgRecord.getNumShapes() - 1);
                    escherSpRecord.setShapeId(iAllocateShapeId);
                }
            }
        }
    }

    public void updateNamesAfterCellShift(FormulaShifter formulaShifter) {
        for (int i = 0; i < getNumNames(); i++) {
            NameRecord nameRecord = getNameRecord(i);
            Ptg[] nameDefinition = nameRecord.getNameDefinition();
            if (formulaShifter.adjustFormula(nameDefinition, nameRecord.getSheetNumber())) {
                nameRecord.setNameDefinition(nameDefinition);
            }
        }
    }
}
