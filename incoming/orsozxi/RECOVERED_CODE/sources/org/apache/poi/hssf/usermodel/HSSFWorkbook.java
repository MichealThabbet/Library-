package org.apache.poi.hssf.usermodel;

import com.ibm.icu.lang.UCharacterEnums;
import it.feio.android.springpadimporter.utils.Constants;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.poi.POIDocument;
import org.apache.poi.ddf.EscherBSERecord;
import org.apache.poi.ddf.EscherBitmapBlip;
import org.apache.poi.ddf.EscherBlipRecord;
import org.apache.poi.ddf.EscherRecord;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.model.InternalSheet;
import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.model.RecordStream;
import org.apache.poi.hssf.record.AbstractEscherHolderRecord;
import org.apache.poi.hssf.record.DrawingGroupRecord;
import org.apache.poi.hssf.record.EmbeddedObjectRefSubRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NameRecord;
import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RecordFactory;
import org.apache.poi.hssf.record.RecordFormatException;
import org.apache.poi.hssf.record.SubRecord;
import org.apache.poi.hssf.record.UnknownRecord;
import org.apache.poi.hssf.record.aggregates.RecordAggregate;
import org.apache.poi.hssf.record.common.UnicodeString;
import org.apache.poi.hssf.record.formula.Area3DPtg;
import org.apache.poi.hssf.record.formula.MemFuncPtg;
import org.apache.poi.hssf.record.formula.NameXPtg;
import org.apache.poi.hssf.record.formula.OperandPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.Ref3DPtg;
import org.apache.poi.hssf.record.formula.SheetNameFormatter;
import org.apache.poi.hssf.record.formula.UnionPtg;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

/* loaded from: classes3.dex */
public final class HSSFWorkbook extends POIDocument implements Workbook {
    public static final int INITIAL_CAPACITY = 3;
    private static final short MAX_COLUMN = 255;
    private static final int MAX_ROW = 65535;
    protected List _sheets;
    private Hashtable fonts;
    private HSSFDataFormat formatter;
    private Row.MissingCellPolicy missingCellPolicy;
    private ArrayList names;
    private boolean preserveNodes;
    private InternalWorkbook workbook;
    private static final Pattern COMMA_PATTERN = Pattern.compile(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
    private static final int DEBUG = POILogger.DEBUG;
    private static POILogger log = POILogFactory.getLogger(HSSFWorkbook.class);
    private static final String[] WORKBOOK_DIR_ENTRY_NAMES = {"Workbook", "WORKBOOK"};

    private static byte[] newUID() {
        return new byte[16];
    }

    public static HSSFWorkbook create(InternalWorkbook internalWorkbook) {
        return new HSSFWorkbook(internalWorkbook);
    }

    public HSSFWorkbook() {
        this(InternalWorkbook.createWorkbook());
    }

    private HSSFWorkbook(InternalWorkbook internalWorkbook) {
        super(null, null);
        this.missingCellPolicy = HSSFRow.RETURN_NULL_AND_BLANK;
        this.workbook = internalWorkbook;
        this._sheets = new ArrayList(3);
        this.names = new ArrayList(3);
    }

    public HSSFWorkbook(POIFSFileSystem pOIFSFileSystem) throws IOException {
        this(pOIFSFileSystem, true);
    }

    public HSSFWorkbook(POIFSFileSystem pOIFSFileSystem, boolean z) throws IOException {
        this(pOIFSFileSystem.getRoot(), pOIFSFileSystem, z);
    }

    private static String getWorkbookDirEntryName(DirectoryNode directoryNode) {
        for (String str : WORKBOOK_DIR_ENTRY_NAMES) {
            try {
                directoryNode.getEntry(str);
                return str;
            } catch (FileNotFoundException unused) {
            }
        }
        try {
            directoryNode.getEntry(Constants.TYPE_BOOK);
            throw new OldExcelFormatException("The supplied spreadsheet seems to be Excel 5.0/7.0 (BIFF5) format. POI only supports BIFF8 format (from Excel versions 97/2000/XP/2003)");
        } catch (FileNotFoundException unused2) {
            throw new IllegalArgumentException("The supplied POIFSFileSystem does not contain a BIFF8 'Workbook' entry. Is it really an excel file?");
        }
    }

    public HSSFWorkbook(DirectoryNode directoryNode, POIFSFileSystem pOIFSFileSystem, boolean z) throws RecordFormatException, IOException {
        super(directoryNode, pOIFSFileSystem);
        this.missingCellPolicy = HSSFRow.RETURN_NULL_AND_BLANK;
        String workbookDirEntryName = getWorkbookDirEntryName(directoryNode);
        this.preserveNodes = z;
        if (!z) {
            this.filesystem = null;
            this.directory = null;
        }
        this._sheets = new ArrayList(3);
        this.names = new ArrayList(3);
        List<Record> listCreateRecords = RecordFactory.createRecords(directoryNode.createDocumentInputStream(workbookDirEntryName));
        InternalWorkbook internalWorkbookCreateWorkbook = InternalWorkbook.createWorkbook(listCreateRecords);
        this.workbook = internalWorkbookCreateWorkbook;
        setPropertiesFromWorkbook(internalWorkbookCreateWorkbook);
        int numRecords = this.workbook.getNumRecords();
        convertLabelRecords(listCreateRecords, numRecords);
        RecordStream recordStream = new RecordStream(listCreateRecords, numRecords);
        while (recordStream.hasNext()) {
            this._sheets.add(new HSSFSheet(this, InternalSheet.createSheet(recordStream)));
        }
        for (int i = 0; i < this.workbook.getNumNames(); i++) {
            NameRecord nameRecord = this.workbook.getNameRecord(i);
            this.names.add(new HSSFName(this, nameRecord, this.workbook.getNameCommentRecord(nameRecord)));
        }
    }

    public HSSFWorkbook(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    public HSSFWorkbook(InputStream inputStream, boolean z) throws IOException {
        this(new POIFSFileSystem(inputStream), z);
    }

    private void setPropertiesFromWorkbook(InternalWorkbook internalWorkbook) {
        this.workbook = internalWorkbook;
    }

    private void convertLabelRecords(List list, int i) {
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, "convertLabelRecords called");
        }
        while (i < list.size()) {
            Record record = (Record) list.get(i);
            if (record.getSid() == 516) {
                LabelRecord labelRecord = (LabelRecord) record;
                list.remove(i);
                LabelSSTRecord labelSSTRecord = new LabelSSTRecord();
                int iAddSSTString = this.workbook.addSSTString(new UnicodeString(labelRecord.getValue()));
                labelSSTRecord.setRow(labelRecord.getRow());
                labelSSTRecord.setColumn(labelRecord.getColumn());
                labelSSTRecord.setXFIndex(labelRecord.getXFIndex());
                labelSSTRecord.setSSTIndex(iAddSSTString);
                list.add(i, labelSSTRecord);
            }
            i++;
        }
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, "convertLabelRecords exit");
        }
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public Row.MissingCellPolicy getMissingCellPolicy() {
        return this.missingCellPolicy;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setMissingCellPolicy(Row.MissingCellPolicy missingCellPolicy) {
        this.missingCellPolicy = missingCellPolicy;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setSheetOrder(String str, int i) {
        List list = this._sheets;
        list.add(i, list.remove(getSheetIndex(str)));
        this.workbook.setSheetOrder(str, i);
    }

    private void validateSheetIndex(int i) {
        int size = this._sheets.size() - 1;
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("Sheet index (" + i + ") is out of range (0.." + size + ")");
        }
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setSelectedTab(int i) {
        validateSheetIndex(i);
        int size = this._sheets.size();
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i2 < size) {
                HSSFSheet sheetAt = getSheetAt(i2);
                if (i2 != i) {
                    z = false;
                }
                sheetAt.setSelected(z);
                i2++;
            } else {
                this.workbook.getWindowOne().setNumSelectedTabs((short) 1);
                return;
            }
        }
    }

    public void setSelectedTab(short s) {
        setSelectedTab((int) s);
    }

    public void setSelectedTabs(int[] iArr) {
        boolean z;
        for (int i : iArr) {
            validateSheetIndex(i);
        }
        int size = this._sheets.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = 0;
            while (true) {
                if (i3 >= iArr.length) {
                    z = false;
                    break;
                } else {
                    if (iArr[i3] == i2) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
            getSheetAt(i2).setSelected(z);
        }
        this.workbook.getWindowOne().setNumSelectedTabs((short) iArr.length);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setActiveSheet(int i) {
        validateSheetIndex(i);
        int size = this._sheets.size();
        int i2 = 0;
        while (i2 < size) {
            getSheetAt(i2).setActive(i2 == i);
            i2++;
        }
        this.workbook.getWindowOne().setActiveSheetIndex(i);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public int getActiveSheetIndex() {
        return this.workbook.getWindowOne().getActiveSheetIndex();
    }

    public short getSelectedTab() {
        return (short) getActiveSheetIndex();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setFirstVisibleTab(int i) {
        this.workbook.getWindowOne().setFirstVisibleTab(i);
    }

    public void setDisplayedTab(short s) {
        setFirstVisibleTab(s);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public int getFirstVisibleTab() {
        return this.workbook.getWindowOne().getFirstVisibleTab();
    }

    public short getDisplayedTab() {
        return (short) getFirstVisibleTab();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setSheetName(int i, String str) {
        if (this.workbook.doesContainsSheetName(str, i)) {
            throw new IllegalArgumentException("The workbook already contains a sheet with this name");
        }
        validateSheetIndex(i);
        this.workbook.setSheetName(i, str);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public String getSheetName(int i) {
        validateSheetIndex(i);
        return this.workbook.getSheetName(i);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public boolean isHidden() {
        return this.workbook.getWindowOne().getHidden();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setHidden(boolean z) {
        this.workbook.getWindowOne().setHidden(z);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public boolean isSheetHidden(int i) {
        validateSheetIndex(i);
        return this.workbook.isSheetHidden(i);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public boolean isSheetVeryHidden(int i) {
        validateSheetIndex(i);
        return this.workbook.isSheetVeryHidden(i);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setSheetHidden(int i, boolean z) {
        validateSheetIndex(i);
        this.workbook.setSheetHidden(i, z);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setSheetHidden(int i, int i2) {
        validateSheetIndex(i);
        WorkbookUtil.validateSheetState(i2);
        this.workbook.setSheetHidden(i, i2);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public int getSheetIndex(String str) {
        return this.workbook.getSheetIndex(str);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public int getSheetIndex(Sheet sheet) {
        for (int i = 0; i < this._sheets.size(); i++) {
            if (this._sheets.get(i) == sheet) {
                return i;
            }
        }
        return -1;
    }

    public int getExternalSheetIndex(int i) {
        return this.workbook.checkExternSheet(i);
    }

    public String findSheetNameFromExternSheet(int i) {
        return this.workbook.findSheetNameFromExternSheet(i);
    }

    public String resolveNameXText(int i, int i2) {
        return this.workbook.resolveNameXText(i, i2);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFSheet createSheet() {
        HSSFSheet hSSFSheet = new HSSFSheet(this);
        this._sheets.add(hSSFSheet);
        this.workbook.setSheetName(this._sheets.size() - 1, "Sheet" + (this._sheets.size() - 1));
        boolean z = this._sheets.size() == 1;
        hSSFSheet.setSelected(z);
        hSSFSheet.setActive(z);
        return hSSFSheet;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFSheet cloneSheet(int i) {
        validateSheetIndex(i);
        HSSFSheet hSSFSheet = (HSSFSheet) this._sheets.get(i);
        String sheetName = this.workbook.getSheetName(i);
        HSSFSheet hSSFSheetCloneSheet = hSSFSheet.cloneSheet(this);
        hSSFSheetCloneSheet.setSelected(false);
        hSSFSheetCloneSheet.setActive(false);
        String uniqueSheetName = getUniqueSheetName(sheetName);
        int size = this._sheets.size();
        this._sheets.add(hSSFSheetCloneSheet);
        this.workbook.setSheetName(size, uniqueSheetName);
        int iFindExistingBuiltinNameRecordIdx = findExistingBuiltinNameRecordIdx(i, (byte) 13);
        if (iFindExistingBuiltinNameRecordIdx >= 0) {
            NameRecord nameRecord = this.workbook.getNameRecord(iFindExistingBuiltinNameRecordIdx);
            short sCheckExternSheet = this.workbook.checkExternSheet(size);
            Ptg[] nameDefinition = nameRecord.getNameDefinition();
            for (int i2 = 0; i2 < nameDefinition.length; i2++) {
                Ptg ptg = nameDefinition[i2];
                if (ptg instanceof Area3DPtg) {
                    Area3DPtg area3DPtg = (Area3DPtg) ((OperandPtg) ptg).copy();
                    area3DPtg.setExternSheetIndex(sCheckExternSheet);
                    nameDefinition[i2] = area3DPtg;
                } else if (ptg instanceof Ref3DPtg) {
                    Ref3DPtg ref3DPtg = (Ref3DPtg) ((OperandPtg) ptg).copy();
                    ref3DPtg.setExternSheetIndex(sCheckExternSheet);
                    nameDefinition[i2] = ref3DPtg;
                }
            }
            NameRecord nameRecordCreateBuiltInName = this.workbook.createBuiltInName((byte) 13, size + 1);
            nameRecordCreateBuiltInName.setNameDefinition(nameDefinition);
            nameRecordCreateBuiltInName.setHidden(true);
            this.names.add(new HSSFName(this, nameRecordCreateBuiltInName));
            this.workbook.cloneDrawings(hSSFSheetCloneSheet.getSheet());
        }
        return hSSFSheetCloneSheet;
    }

    private String getUniqueSheetName(String str) {
        int i;
        String str2;
        int iLastIndexOf = str.lastIndexOf(40);
        if (iLastIndexOf <= 0 || !str.endsWith(")")) {
            i = 2;
        } else {
            try {
                i = Integer.parseInt(str.substring(iLastIndexOf + 1, str.length() - 1).trim()) + 1;
                try {
                    str = str.substring(0, iLastIndexOf).trim();
                } catch (NumberFormatException unused) {
                }
            } catch (NumberFormatException unused2) {
            }
        }
        while (true) {
            int i2 = i + 1;
            String string = Integer.toString(i);
            if (str.length() + string.length() + 2 < 31) {
                str2 = str + " (" + string + ")";
            } else {
                str2 = str.substring(0, (31 - string.length()) - 2) + "(" + string + ")";
            }
            if (this.workbook.getSheetIndex(str2) == -1) {
                return str2;
            }
            i = i2;
        }
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFSheet createSheet(String str) {
        if (this.workbook.doesContainsSheetName(str, this._sheets.size())) {
            throw new IllegalArgumentException("The workbook already contains a sheet of this name");
        }
        HSSFSheet hSSFSheet = new HSSFSheet(this);
        this.workbook.setSheetName(this._sheets.size(), str);
        this._sheets.add(hSSFSheet);
        boolean z = this._sheets.size() == 1;
        hSSFSheet.setSelected(z);
        hSSFSheet.setActive(z);
        return hSSFSheet;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public int getNumberOfSheets() {
        return this._sheets.size();
    }

    public int getSheetIndexFromExternSheetIndex(int i) {
        return this.workbook.getSheetIndexFromExternSheetIndex(i);
    }

    private HSSFSheet[] getSheets() {
        HSSFSheet[] hSSFSheetArr = new HSSFSheet[this._sheets.size()];
        this._sheets.toArray(hSSFSheetArr);
        return hSSFSheetArr;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFSheet getSheetAt(int i) {
        validateSheetIndex(i);
        return (HSSFSheet) this._sheets.get(i);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFSheet getSheet(String str) {
        HSSFSheet hSSFSheet = null;
        for (int i = 0; i < this._sheets.size(); i++) {
            if (this.workbook.getSheetName(i).equalsIgnoreCase(str)) {
                hSSFSheet = (HSSFSheet) this._sheets.get(i);
            }
        }
        return hSSFSheet;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void removeSheetAt(int i) {
        validateSheetIndex(i);
        boolean zIsActive = getSheetAt(i).isActive();
        boolean zIsSelected = getSheetAt(i).isSelected();
        this._sheets.remove(i);
        this.workbook.removeSheet(i);
        int size = this._sheets.size();
        boolean z = true;
        if (size < 1) {
            return;
        }
        if (i >= size) {
            i = size - 1;
        }
        if (zIsActive) {
            setActiveSheet(i);
        }
        if (zIsSelected) {
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    z = false;
                    break;
                } else if (getSheetAt(i2).isSelected()) {
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                return;
            }
            setSelectedTab(i);
        }
    }

    public void setBackupFlag(boolean z) {
        this.workbook.getBackupRecord().setBackup(z ? (short) 1 : (short) 0);
    }

    public boolean getBackupFlag() {
        return this.workbook.getBackupRecord().getBackup() != 0;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setRepeatingRowsAndColumns(int i, int i2, int i3, int i4, int i5) {
        NameRecord nameRecord;
        boolean z;
        ArrayList arrayList;
        NameRecord nameRecord2;
        boolean z2;
        ArrayList arrayList2;
        if (i2 == -1 && i3 != -1) {
            throw new IllegalArgumentException("Invalid column range specification");
        }
        if (i4 == -1 && i5 != -1) {
            throw new IllegalArgumentException("Invalid row range specification");
        }
        if (i2 < -1 || i2 >= 255) {
            throw new IllegalArgumentException("Invalid column range specification");
        }
        if (i3 < -1 || i3 >= 255) {
            throw new IllegalArgumentException("Invalid column range specification");
        }
        if (i4 < -1 || i4 > 65535) {
            throw new IllegalArgumentException("Invalid row range specification");
        }
        if (i5 < -1 || i5 > 65535) {
            throw new IllegalArgumentException("Invalid row range specification");
        }
        if (i2 > i3) {
            throw new IllegalArgumentException("Invalid column range specification");
        }
        if (i4 > i5) {
            throw new IllegalArgumentException("Invalid row range specification");
        }
        HSSFSheet sheetAt = getSheetAt(i);
        short sCheckExternSheet = getWorkbook().checkExternSheet(i);
        boolean z3 = (i2 == -1 || i3 == -1 || i4 == -1 || i5 == -1) ? false : true;
        boolean z4 = i2 == -1 && i3 == -1 && i4 == -1 && i5 == -1;
        int iFindExistingBuiltinNameRecordIdx = findExistingBuiltinNameRecordIdx(i, (byte) 7);
        if (z4) {
            if (iFindExistingBuiltinNameRecordIdx >= 0) {
                this.workbook.removeName(iFindExistingBuiltinNameRecordIdx);
                return;
            }
            return;
        }
        if (iFindExistingBuiltinNameRecordIdx < 0) {
            nameRecord = this.workbook.createBuiltInName((byte) 7, i + 1);
            z = true;
        } else {
            nameRecord = this.workbook.getNameRecord(iFindExistingBuiltinNameRecordIdx);
            z = false;
        }
        ArrayList arrayList3 = new ArrayList();
        if (z3) {
            arrayList3.add(new MemFuncPtg(23));
        }
        if (i2 >= 0) {
            nameRecord2 = nameRecord;
            z2 = true;
            arrayList = arrayList3;
            arrayList.add(new Area3DPtg(0, 65535, i2, i3, false, false, false, false, sCheckExternSheet));
        } else {
            arrayList = arrayList3;
            nameRecord2 = nameRecord;
            z2 = true;
        }
        if (i4 >= 0) {
            arrayList2 = arrayList;
            arrayList2.add(new Area3DPtg(i4, i5, 0, 255, false, false, false, false, sCheckExternSheet));
        } else {
            arrayList2 = arrayList;
        }
        if (z3) {
            arrayList2.add(UnionPtg.instance);
        }
        Ptg[] ptgArr = new Ptg[arrayList2.size()];
        arrayList2.toArray(ptgArr);
        NameRecord nameRecord3 = nameRecord2;
        nameRecord3.setNameDefinition(ptgArr);
        if (z) {
            this.names.add(new HSSFName(this, nameRecord3, nameRecord3.isBuiltInName() ? null : this.workbook.getNameCommentRecord(nameRecord3)));
        }
        sheetAt.getPrintSetup().setValidSettings(false);
        sheetAt.setActive(z2);
    }

    private int findExistingBuiltinNameRecordIdx(int i, byte b) {
        for (int i2 = 0; i2 < this.names.size(); i2++) {
            NameRecord nameRecord = this.workbook.getNameRecord(i2);
            if (nameRecord == null) {
                throw new RuntimeException("Unable to find all defined names to iterate over");
            }
            if (nameRecord.isBuiltInName() && nameRecord.getBuiltInName() == b && nameRecord.getSheetNumber() - 1 == i) {
                return i2;
            }
        }
        return -1;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFFont createFont() {
        this.workbook.createNewFont();
        short numberOfFonts = (short) (getNumberOfFonts() - 1);
        if (numberOfFonts > 3) {
            numberOfFonts = (short) (numberOfFonts + 1);
        }
        if (numberOfFonts == Short.MAX_VALUE) {
            throw new IllegalArgumentException("Maximum number of fonts was exceeded");
        }
        return getFontAt(numberOfFonts);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFFont findFont(short s, short s2, short s3, String str, boolean z, boolean z2, short s4, byte b) {
        for (short s5 = 0; s5 <= getNumberOfFonts(); s5 = (short) (s5 + 1)) {
            if (s5 != 4) {
                HSSFFont fontAt = getFontAt(s5);
                if (fontAt.getBoldweight() == s && fontAt.getColor() == s2 && fontAt.getFontHeight() == s3 && fontAt.getFontName().equals(str) && fontAt.getItalic() == z && fontAt.getStrikeout() == z2 && fontAt.getTypeOffset() == s4 && fontAt.getUnderline() == b) {
                    return fontAt;
                }
            }
        }
        return null;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public short getNumberOfFonts() {
        return (short) this.workbook.getNumberOfFontRecords();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFFont getFontAt(short s) {
        if (this.fonts == null) {
            this.fonts = new Hashtable();
        }
        Short shValueOf = Short.valueOf(s);
        if (this.fonts.containsKey(shValueOf)) {
            return (HSSFFont) this.fonts.get(shValueOf);
        }
        HSSFFont hSSFFont = new HSSFFont(s, this.workbook.getFontRecordAt(s));
        this.fonts.put(shValueOf, hSSFFont);
        return hSSFFont;
    }

    protected void resetFontCache() {
        this.fonts = new Hashtable();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFCellStyle createCellStyle() {
        return new HSSFCellStyle((short) (getNumCellStyles() - 1), this.workbook.createCellXF(), this);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public short getNumCellStyles() {
        return (short) this.workbook.getNumExFormats();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFCellStyle getCellStyleAt(short s) {
        return new HSSFCellStyle(s, this.workbook.getExFormatAt(s), this);
    }

    @Override // org.apache.poi.POIDocument
    public void write(OutputStream outputStream) throws IOException, ArrayStoreException {
        byte[] bytes = getBytes();
        POIFSFileSystem pOIFSFileSystem = new POIFSFileSystem();
        ArrayList arrayList = new ArrayList(1);
        pOIFSFileSystem.createDocument(new ByteArrayInputStream(bytes), "Workbook");
        writeProperties(pOIFSFileSystem, arrayList);
        if (this.preserveNodes) {
            arrayList.add("Workbook");
            arrayList.add("WORKBOOK");
            POIFSFileSystem pOIFSFileSystem2 = this.filesystem;
            copyNodes(pOIFSFileSystem2, pOIFSFileSystem, arrayList);
            pOIFSFileSystem.getRoot().setStorageClsid(pOIFSFileSystem2.getRoot().getStorageClsid());
        }
        pOIFSFileSystem.writeFilesystem(outputStream);
    }

    private static final class SheetRecordCollector implements RecordAggregate.RecordVisitor {
        private int _totalSize = 0;
        private List _list = new ArrayList(128);

        public int getTotalSize() {
            return this._totalSize;
        }

        @Override // org.apache.poi.hssf.record.aggregates.RecordAggregate.RecordVisitor
        public void visitRecord(Record record) {
            this._list.add(record);
            this._totalSize += record.getRecordSize();
        }

        public int serialize(int i, byte[] bArr) {
            int size = this._list.size();
            int iSerialize = 0;
            for (int i2 = 0; i2 < size; i2++) {
                iSerialize += ((Record) this._list.get(i2)).serialize(i + iSerialize, bArr);
            }
            return iSerialize;
        }
    }

    public byte[] getBytes() {
        if (log.check(POILogger.DEBUG)) {
            log.log(DEBUG, "HSSFWorkbook.getBytes()");
        }
        HSSFSheet[] sheets = getSheets();
        int length = sheets.length;
        for (HSSFSheet hSSFSheet : sheets) {
            hSSFSheet.getSheet().preSerialize();
        }
        int size = this.workbook.getSize();
        SheetRecordCollector[] sheetRecordCollectorArr = new SheetRecordCollector[length];
        for (int i = 0; i < length; i++) {
            this.workbook.setSheetBof(i, size);
            SheetRecordCollector sheetRecordCollector = new SheetRecordCollector();
            sheets[i].getSheet().visitContainedRecords(sheetRecordCollector, size);
            size += sheetRecordCollector.getTotalSize();
            sheetRecordCollectorArr[i] = sheetRecordCollector;
        }
        byte[] bArr = new byte[size];
        int iSerialize = this.workbook.serialize(0, bArr);
        for (int i2 = 0; i2 < length; i2++) {
            SheetRecordCollector sheetRecordCollector2 = sheetRecordCollectorArr[i2];
            int iSerialize2 = sheetRecordCollector2.serialize(iSerialize, bArr);
            if (iSerialize2 != sheetRecordCollector2.getTotalSize()) {
                throw new IllegalStateException("Actual serialized sheet size (" + iSerialize2 + ") differs from pre-calculated size (" + sheetRecordCollector2.getTotalSize() + ") for sheet (" + i2 + ")");
            }
            iSerialize += iSerialize2;
        }
        return bArr;
    }

    public int addSSTString(String str) {
        return this.workbook.addSSTString(new UnicodeString(str));
    }

    public String getSSTString(int i) {
        return this.workbook.getSSTString(i).getString();
    }

    InternalWorkbook getWorkbook() {
        return this.workbook;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public int getNumberOfNames() {
        return this.names.size();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFName getName(String str) {
        int nameIndex = getNameIndex(str);
        if (nameIndex < 0) {
            return null;
        }
        return (HSSFName) this.names.get(nameIndex);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFName getNameAt(int i) {
        int size = this.names.size();
        if (size < 1) {
            throw new IllegalStateException("There are no defined names in this workbook");
        }
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("Specified name index " + i + " is outside the allowable range (0.." + (size - 1) + ").");
        }
        return (HSSFName) this.names.get(i);
    }

    public NameRecord getNameRecord(int i) {
        return getWorkbook().getNameRecord(i);
    }

    public String getNameName(int i) {
        return getNameAt(i).getNameName();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setPrintArea(int i, String str) {
        int i2 = i + 1;
        NameRecord specificBuiltinRecord = this.workbook.getSpecificBuiltinRecord((byte) 6, i2);
        if (specificBuiltinRecord == null) {
            specificBuiltinRecord = this.workbook.createBuiltInName((byte) 6, i2);
        }
        String[] strArrSplit = COMMA_PATTERN.split(str);
        StringBuffer stringBuffer = new StringBuffer(32);
        for (int i3 = 0; i3 < strArrSplit.length; i3++) {
            if (i3 > 0) {
                stringBuffer.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
            }
            SheetNameFormatter.appendFormat(stringBuffer, getSheetName(i));
            stringBuffer.append("!");
            stringBuffer.append(strArrSplit[i3]);
        }
        specificBuiltinRecord.setNameDefinition(HSSFFormulaParser.parse(stringBuffer.toString(), this, 0, i));
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void setPrintArea(int i, int i2, int i3, int i4, int i5) {
        setPrintArea(i, new CellReference(i4, i2, true, true).formatAsString() + ":" + new CellReference(i5, i3, true, true).formatAsString());
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public String getPrintArea(int i) {
        NameRecord specificBuiltinRecord = this.workbook.getSpecificBuiltinRecord((byte) 6, i + 1);
        if (specificBuiltinRecord == null) {
            return null;
        }
        return HSSFFormulaParser.toFormulaString(this, specificBuiltinRecord.getNameDefinition());
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void removePrintArea(int i) {
        getWorkbook().removeBuiltinRecord((byte) 6, i + 1);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFName createName() {
        HSSFName hSSFName = new HSSFName(this, this.workbook.createName());
        this.names.add(hSSFName);
        return hSSFName;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public int getNameIndex(String str) {
        for (int i = 0; i < this.names.size(); i++) {
            if (getNameName(i).equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void removeName(int i) {
        this.names.remove(i);
        this.workbook.removeName(i);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public HSSFDataFormat createDataFormat() {
        if (this.formatter == null) {
            this.formatter = new HSSFDataFormat(this.workbook);
        }
        return this.formatter;
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public void removeName(String str) {
        removeName(getNameIndex(str));
    }

    public HSSFPalette getCustomPalette() {
        return new HSSFPalette(this.workbook.getCustomPalette());
    }

    public void insertChartRecord() {
        this.workbook.getRecords().add(this.workbook.findFirstRecordLocBySid((short) 252), new UnknownRecord(235, new byte[]{15, 0, 0, -16, 82, 0, 0, 0, 0, 0, 6, -16, UCharacterEnums.ECharacterCategory.MATH_SYMBOL, 0, 0, 0, 1, 8, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 51, 0, 11, -16, 18, 0, 0, 0, -65, 0, 8, 0, 8, 0, -127, 1, 9, 0, 0, 8, -64, 1, Ptg.CLASS_ARRAY, 0, 0, 8, Ptg.CLASS_ARRAY, 0, 30, -15, 16, 0, 0, 0, 13, 0, 0, 8, 12, 0, 0, 8, 23, 0, 0, 8, -9, 0, 0, 16}));
    }

    public void dumpDrawingGroupRecords(boolean z) {
        DrawingGroupRecord drawingGroupRecord = (DrawingGroupRecord) this.workbook.findFirstRecordBySid(DrawingGroupRecord.sid);
        drawingGroupRecord.decode();
        List<EscherRecord> escherRecords = drawingGroupRecord.getEscherRecords();
        PrintWriter printWriter = new PrintWriter(System.out);
        for (EscherRecord escherRecord : escherRecords) {
            if (z) {
                System.out.println(escherRecord.toString());
            } else {
                escherRecord.display(printWriter, 0);
            }
        }
        printWriter.flush();
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public int addPicture(byte[] bArr, int i) {
        byte[] bArrNewUID = newUID();
        EscherBitmapBlip escherBitmapBlip = new EscherBitmapBlip();
        escherBitmapBlip.setRecordId((short) (i - 4072));
        switch (i) {
            case 2:
                escherBitmapBlip.setOptions((short) 15680);
                break;
            case 3:
                escherBitmapBlip.setOptions((short) 8544);
                break;
            case 4:
                escherBitmapBlip.setOptions((short) 21536);
                break;
            case 5:
                escherBitmapBlip.setOptions(HSSFPictureData.MSOBI_JPEG);
                break;
            case 6:
                escherBitmapBlip.setOptions(HSSFPictureData.MSOBI_PNG);
                break;
            case 7:
                escherBitmapBlip.setOptions(HSSFPictureData.MSOBI_DIB);
                break;
        }
        escherBitmapBlip.setUID(bArrNewUID);
        escherBitmapBlip.setMarker((byte) -1);
        escherBitmapBlip.setPictureData(bArr);
        EscherBSERecord escherBSERecord = new EscherBSERecord();
        escherBSERecord.setRecordId(EscherBSERecord.RECORD_ID);
        escherBSERecord.setOptions((short) ((i << 4) | 2));
        byte b = (byte) i;
        escherBSERecord.setBlipTypeMacOS(b);
        escherBSERecord.setBlipTypeWin32(b);
        escherBSERecord.setUid(bArrNewUID);
        escherBSERecord.setTag((short) 255);
        escherBSERecord.setSize(bArr.length + 25);
        escherBSERecord.setRef(1);
        escherBSERecord.setOffset(0);
        escherBSERecord.setBlipRecord(escherBitmapBlip);
        return this.workbook.addBSERecord(escherBSERecord);
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public List<HSSFPictureData> getAllPictures() {
        ArrayList arrayList = new ArrayList();
        for (Record record : this.workbook.getRecords()) {
            if (record instanceof AbstractEscherHolderRecord) {
                AbstractEscherHolderRecord abstractEscherHolderRecord = (AbstractEscherHolderRecord) record;
                abstractEscherHolderRecord.decode();
                searchForPictures(abstractEscherHolderRecord.getEscherRecords(), arrayList);
            }
        }
        return arrayList;
    }

    private void searchForPictures(List<EscherRecord> list, List<HSSFPictureData> list2) {
        EscherBlipRecord blipRecord;
        for (EscherRecord escherRecord : list) {
            if ((escherRecord instanceof EscherBSERecord) && (blipRecord = ((EscherBSERecord) escherRecord).getBlipRecord()) != null) {
                list2.add(new HSSFPictureData(blipRecord));
            }
            searchForPictures(escherRecord.getChildRecords(), list2);
        }
    }

    public boolean isWriteProtected() {
        return this.workbook.isWriteProtected();
    }

    public void writeProtectWorkbook(String str, String str2) {
        this.workbook.writeProtectWorkbook(str, str2);
    }

    public void unwriteProtectWorkbook() {
        this.workbook.unwriteProtectWorkbook();
    }

    public List<HSSFObjectData> getAllEmbeddedObjects() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getNumberOfSheets(); i++) {
            getAllEmbeddedObjects(getSheetAt(i).getSheet().getRecords(), arrayList);
        }
        return arrayList;
    }

    private void getAllEmbeddedObjects(List list, List<HSSFObjectData> list2) {
        for (Object obj : list) {
            if (obj instanceof ObjRecord) {
                ObjRecord objRecord = (ObjRecord) obj;
                Iterator<SubRecord> it2 = objRecord.getSubRecords().iterator();
                while (it2.hasNext()) {
                    if (it2.next() instanceof EmbeddedObjectRefSubRecord) {
                        list2.add(new HSSFObjectData(objRecord, this.filesystem));
                    }
                }
            }
        }
    }

    @Override // org.apache.poi.ss.usermodel.Workbook
    public CreationHelper getCreationHelper() {
        return new HSSFCreationHelper(this);
    }

    public NameXPtg getNameXPtg(String str) {
        return this.workbook.getNameXPtg(str);
    }
}
