package org.apache.poi.hssf.usermodel;

import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.PrintWriter;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.apache.poi.ddf.EscherRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.model.InternalSheet;
import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.record.AutoFilterInfoRecord;
import org.apache.poi.hssf.record.CellValueRecordInterface;
import org.apache.poi.hssf.record.EscherAggregate;
import org.apache.poi.hssf.record.NameRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SCLRecord;
import org.apache.poi.hssf.record.WSBoolRecord;
import org.apache.poi.hssf.record.aggregates.FormulaRecordAggregate;
import org.apache.poi.hssf.record.aggregates.WorksheetProtectionBlock;
import org.apache.poi.hssf.record.formula.Area3DPtg;
import org.apache.poi.hssf.record.formula.FormulaShifter;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.util.PaneInformation;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.Region;
import org.apache.poi.ss.util.SSCellRange;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

/* loaded from: classes3.dex */
public final class HSSFSheet implements Sheet {
    public static final int INITIAL_CAPACITY = 20;
    protected final InternalWorkbook _book;
    private int _firstrow;
    private int _lastrow;
    private HSSFPatriarch _patriarch;
    private final TreeMap<Integer, HSSFRow> _rows;
    private final InternalSheet _sheet;
    protected final HSSFWorkbook _workbook;
    private static final POILogger log = POILogFactory.getLogger(HSSFSheet.class);
    private static final int DEBUG = POILogger.DEBUG;

    protected HSSFSheet(HSSFWorkbook hSSFWorkbook) {
        this._sheet = InternalSheet.createSheet();
        this._rows = new TreeMap<>();
        this._workbook = hSSFWorkbook;
        this._book = hSSFWorkbook.getWorkbook();
    }

    protected HSSFSheet(HSSFWorkbook hSSFWorkbook, InternalSheet internalSheet) {
        this._sheet = internalSheet;
        this._rows = new TreeMap<>();
        this._workbook = hSSFWorkbook;
        this._book = hSSFWorkbook.getWorkbook();
        setPropertiesFromSheet(internalSheet);
    }

    HSSFSheet cloneSheet(HSSFWorkbook hSSFWorkbook) {
        return new HSSFSheet(hSSFWorkbook, this._sheet.cloneSheet());
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFWorkbook getWorkbook() {
        return this._workbook;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0077 A[PHI: r6
      0x0077: PHI (r6v14 org.apache.poi.hssf.usermodel.HSSFRow) = (r6v16 org.apache.poi.hssf.usermodel.HSSFRow), (r6v17 org.apache.poi.hssf.usermodel.HSSFRow) binds: [B:19:0x0053, B:17:0x0049] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setPropertiesFromSheet(org.apache.poi.hssf.model.InternalSheet r18) {
        /*
            r17 = this;
            r0 = r17
            org.apache.poi.hssf.record.RowRecord r1 = r18.getNextRow()
            r2 = 0
            if (r1 == 0) goto Lb
            r3 = 1
            goto Lc
        Lb:
            r3 = 0
        Lc:
            if (r1 == 0) goto L16
            r0.createRowFromRecord(r1)
            org.apache.poi.hssf.record.RowRecord r1 = r18.getNextRow()
            goto Lc
        L16:
            org.apache.poi.hssf.record.CellValueRecordInterface[] r1 = r18.getValueRecords()
            long r4 = java.lang.System.currentTimeMillis()
            org.apache.poi.util.POILogger r6 = org.apache.poi.hssf.usermodel.HSSFSheet.log
            int r7 = org.apache.poi.util.POILogger.DEBUG
            boolean r6 = r6.check(r7)
            if (r6 == 0) goto L35
            org.apache.poi.util.POILogger r6 = org.apache.poi.hssf.usermodel.HSSFSheet.log
            int r7 = org.apache.poi.hssf.usermodel.HSSFSheet.DEBUG
            java.lang.Long r8 = java.lang.Long.valueOf(r4)
            java.lang.String r9 = "Time at start of cell creating in HSSF sheet = "
            r6.log(r7, r9, r8)
        L35:
            r6 = 0
        L36:
            int r7 = r1.length
            if (r2 >= r7) goto Lcb
            r7 = r1[r2]
            long r8 = java.lang.System.currentTimeMillis()
            if (r6 == 0) goto L4b
            int r10 = r6.getRowNum()
            int r11 = r7.getRow()
            if (r10 == r11) goto L77
        L4b:
            int r6 = r7.getRow()
            org.apache.poi.hssf.usermodel.HSSFRow r6 = r0.getRow(r6)
            if (r6 != 0) goto L77
            if (r3 != 0) goto L6f
            org.apache.poi.hssf.record.RowRecord r10 = new org.apache.poi.hssf.record.RowRecord
            int r11 = r7.getRow()
            r10.<init>(r11)
            r11 = r18
            r11.addRow(r10)
            org.apache.poi.hssf.usermodel.HSSFRow r10 = r0.createRowFromRecord(r10)
            r16 = r10
            r10 = r6
            r6 = r16
            goto L7a
        L6f:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Unexpected missing row when some rows already present"
            r1.<init>(r2)
            throw r1
        L77:
            r11 = r18
            r10 = r6
        L7a:
            org.apache.poi.util.POILogger r12 = org.apache.poi.hssf.usermodel.HSSFSheet.log
            int r13 = org.apache.poi.util.POILogger.DEBUG
            boolean r12 = r12.check(r13)
            if (r12 == 0) goto La7
            org.apache.poi.util.POILogger r12 = org.apache.poi.hssf.usermodel.HSSFSheet.log
            int r13 = org.apache.poi.hssf.usermodel.HSSFSheet.DEBUG
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "record id = "
            r14.append(r15)
            r15 = r7
            org.apache.poi.hssf.record.Record r15 = (org.apache.poi.hssf.record.Record) r15
            short r15 = r15.getSid()
            java.lang.String r15 = java.lang.Integer.toHexString(r15)
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            r12.log(r13, r14)
        La7:
            r6.createCellFromRecord(r7)
            org.apache.poi.util.POILogger r6 = org.apache.poi.hssf.usermodel.HSSFSheet.log
            int r7 = org.apache.poi.util.POILogger.DEBUG
            boolean r6 = r6.check(r7)
            if (r6 == 0) goto Lc6
            org.apache.poi.util.POILogger r6 = org.apache.poi.hssf.usermodel.HSSFSheet.log
            int r7 = org.apache.poi.hssf.usermodel.HSSFSheet.DEBUG
            long r12 = java.lang.System.currentTimeMillis()
            long r12 = r12 - r8
            java.lang.Long r8 = java.lang.Long.valueOf(r12)
            java.lang.String r9 = "record took "
            r6.log(r7, r9, r8)
        Lc6:
            int r2 = r2 + 1
            r6 = r10
            goto L36
        Lcb:
            org.apache.poi.util.POILogger r1 = org.apache.poi.hssf.usermodel.HSSFSheet.log
            int r2 = org.apache.poi.util.POILogger.DEBUG
            boolean r1 = r1.check(r2)
            if (r1 == 0) goto Le7
            org.apache.poi.util.POILogger r1 = org.apache.poi.hssf.usermodel.HSSFSheet.log
            int r2 = org.apache.poi.hssf.usermodel.HSSFSheet.DEBUG
            long r6 = java.lang.System.currentTimeMillis()
            long r6 = r6 - r4
            java.lang.Long r3 = java.lang.Long.valueOf(r6)
            java.lang.String r4 = "total sheet cell creation took "
            r1.log(r2, r4, r3)
        Le7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.hssf.usermodel.HSSFSheet.setPropertiesFromSheet(org.apache.poi.hssf.model.InternalSheet):void");
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFRow createRow(int i) {
        HSSFRow hSSFRow = new HSSFRow(this._workbook, this, i);
        addRow(hSSFRow, true);
        return hSSFRow;
    }

    private HSSFRow createRowFromRecord(RowRecord rowRecord) {
        HSSFRow hSSFRow = new HSSFRow(this._workbook, this, rowRecord);
        addRow(hSSFRow, false);
        return hSSFRow;
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void removeRow(Row row) {
        HSSFRow hSSFRow = (HSSFRow) row;
        if (row.getSheet() != this) {
            throw new IllegalArgumentException("Specified row does not belong to this sheet");
        }
        Iterator<Cell> it2 = row.iterator();
        while (it2.hasNext()) {
            HSSFCell hSSFCell = (HSSFCell) it2.next();
            if (hSSFCell.isPartOfArrayFormulaGroup()) {
                hSSFCell.notifyArrayFormulaChanging("Row[rownum=" + row.getRowNum() + "] contains cell(s) included in a multi-cell array formula. You cannot change part of an array.");
            }
        }
        if (this._rows.size() > 0) {
            if (this._rows.remove(Integer.valueOf(row.getRowNum())) != row) {
                throw new IllegalArgumentException("Specified row does not belong to this sheet");
            }
            if (hSSFRow.getRowNum() == getLastRowNum()) {
                this._lastrow = findLastRow(this._lastrow);
            }
            if (hSSFRow.getRowNum() == getFirstRowNum()) {
                this._firstrow = findFirstRow(this._firstrow);
            }
            this._sheet.removeRow(hSSFRow.getRowRecord());
        }
    }

    private int findLastRow(int i) {
        if (i < 1) {
            return 0;
        }
        int i2 = i - 1;
        HSSFRow row = getRow(i2);
        while (row == null && i2 > 0) {
            i2--;
            row = getRow(i2);
        }
        if (row == null) {
            return 0;
        }
        return i2;
    }

    private int findFirstRow(int i) {
        int i2 = i + 1;
        HSSFRow row = getRow(i2);
        while (row == null && i2 <= getLastRowNum()) {
            i2++;
            row = getRow(i2);
        }
        if (i2 > getLastRowNum()) {
            return 0;
        }
        return i2;
    }

    private void addRow(HSSFRow hSSFRow, boolean z) {
        this._rows.put(Integer.valueOf(hSSFRow.getRowNum()), hSSFRow);
        if (z) {
            this._sheet.addRow(hSSFRow.getRowRecord());
        }
        boolean z2 = this._rows.size() == 1;
        if (hSSFRow.getRowNum() > getLastRowNum() || z2) {
            this._lastrow = hSSFRow.getRowNum();
        }
        if (hSSFRow.getRowNum() < getFirstRowNum() || z2) {
            this._firstrow = hSSFRow.getRowNum();
        }
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFRow getRow(int i) {
        return this._rows.get(Integer.valueOf(i));
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int getPhysicalNumberOfRows() {
        return this._rows.size();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int getFirstRowNum() {
        return this._firstrow;
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int getLastRowNum() {
        return this._lastrow;
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void addValidationData(DataValidation dataValidation) {
        if (dataValidation == null) {
            throw new IllegalArgumentException("objValidation must not be null");
        }
        this._sheet.getOrCreateDataValidityTable().addDataValidation(((HSSFDataValidation) dataValidation).createDVRecord(this));
    }

    public void setColumnHidden(short s, boolean z) {
        setColumnHidden(s & 65535, z);
    }

    public boolean isColumnHidden(short s) {
        return isColumnHidden(s & 65535);
    }

    public void setColumnWidth(short s, short s2) {
        setColumnWidth(s & 65535, s2 & 65535);
    }

    public short getColumnWidth(short s) {
        return (short) getColumnWidth(s & 65535);
    }

    public void setDefaultColumnWidth(short s) {
        setDefaultColumnWidth(s & 65535);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setColumnHidden(int i, boolean z) {
        this._sheet.setColumnHidden(i, z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isColumnHidden(int i) {
        return this._sheet.isColumnHidden(i);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setColumnWidth(int i, int i2) {
        this._sheet.setColumnWidth(i, i2);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int getColumnWidth(int i) {
        return this._sheet.getColumnWidth(i);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int getDefaultColumnWidth() {
        return this._sheet.getDefaultColumnWidth();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDefaultColumnWidth(int i) {
        this._sheet.setDefaultColumnWidth(i);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public short getDefaultRowHeight() {
        return this._sheet.getDefaultRowHeight();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public float getDefaultRowHeightInPoints() {
        return this._sheet.getDefaultRowHeight() / 20.0f;
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDefaultRowHeight(short s) {
        this._sheet.setDefaultRowHeight(s);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDefaultRowHeightInPoints(float f) {
        this._sheet.setDefaultRowHeight((short) (f * 20.0f));
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFCellStyle getColumnStyle(int i) {
        short xFIndexForColAt = this._sheet.getXFIndexForColAt((short) i);
        if (xFIndexForColAt == 15) {
            return null;
        }
        return new HSSFCellStyle(xFIndexForColAt, this._book.getExFormatAt(xFIndexForColAt), this._book);
    }

    public boolean isGridsPrinted() {
        return this._sheet.isGridsPrinted();
    }

    public void setGridsPrinted(boolean z) {
        this._sheet.setGridsPrinted(z);
    }

    public int addMergedRegion(Region region) {
        return this._sheet.addMergedRegion(region.getRowFrom(), region.getColumnFrom(), region.getRowTo(), region.getColumnTo());
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int addMergedRegion(CellRangeAddress cellRangeAddress) {
        cellRangeAddress.validate(SpreadsheetVersion.EXCEL97);
        validateArrayFormulas(cellRangeAddress);
        return this._sheet.addMergedRegion(cellRangeAddress.getFirstRow(), cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastRow(), cellRangeAddress.getLastColumn());
    }

    private void validateArrayFormulas(CellRangeAddress cellRangeAddress) {
        HSSFCell cell;
        int firstColumn = cellRangeAddress.getFirstColumn();
        int lastRow = cellRangeAddress.getLastRow();
        int lastColumn = cellRangeAddress.getLastColumn();
        for (int firstRow = cellRangeAddress.getFirstRow(); firstRow <= lastRow; firstRow++) {
            for (int i = firstColumn; i <= lastColumn; i++) {
                HSSFRow row = getRow(firstRow);
                if (row != null && (cell = row.getCell(i)) != null && cell.isPartOfArrayFormulaGroup()) {
                    CellRangeAddress arrayFormulaRange = cell.getArrayFormulaRange();
                    if (arrayFormulaRange.getNumberOfCells() > 1 && (arrayFormulaRange.isInRange(cellRangeAddress.getFirstRow(), cellRangeAddress.getFirstColumn()) || arrayFormulaRange.isInRange(cellRangeAddress.getFirstRow(), cellRangeAddress.getFirstColumn()))) {
                        throw new IllegalStateException("The range " + cellRangeAddress.formatAsString() + " intersects with a multi-cell array formula. You cannot merge cells of an array.");
                    }
                }
            }
        }
    }

    public void setForceFormulaRecalculation(boolean z) {
        this._sheet.setUncalced(z);
    }

    public boolean getForceFormulaRecalculation() {
        return this._sheet.getUncalced();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setVerticallyCenter(boolean z) {
        this._sheet.getPageSettings().getVCenter().setVCenter(z);
    }

    public boolean getVerticallyCenter(boolean z) {
        return getVerticallyCenter();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getVerticallyCenter() {
        return this._sheet.getPageSettings().getVCenter().getVCenter();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setHorizontallyCenter(boolean z) {
        this._sheet.getPageSettings().getHCenter().setHCenter(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getHorizontallyCenter() {
        return this._sheet.getPageSettings().getHCenter().getHCenter();
    }

    public void setRightToLeft(boolean z) {
        this._sheet.getWindowTwo().setArabic(z);
    }

    public boolean isRightToLeft() {
        return this._sheet.getWindowTwo().getArabic();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void removeMergedRegion(int i) {
        this._sheet.removeMergedRegion(i);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int getNumMergedRegions() {
        return this._sheet.getNumMergedRegions();
    }

    public org.apache.poi.hssf.util.Region getMergedRegionAt(int i) {
        CellRangeAddress mergedRegion = getMergedRegion(i);
        return new org.apache.poi.hssf.util.Region(mergedRegion.getFirstRow(), (short) mergedRegion.getFirstColumn(), mergedRegion.getLastRow(), (short) mergedRegion.getLastColumn());
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public CellRangeAddress getMergedRegion(int i) {
        return this._sheet.getMergedRegionAt(i);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public Iterator<Row> rowIterator() {
        return this._rows.values().iterator();
    }

    @Override // java.lang.Iterable
    public Iterator<Row> iterator() {
        return rowIterator();
    }

    InternalSheet getSheet() {
        return this._sheet;
    }

    public void setAlternativeExpression(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).setAlternateExpression(z);
    }

    public void setAlternativeFormula(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).setAlternateFormula(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setAutobreaks(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).setAutobreaks(z);
    }

    public void setDialog(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).setDialog(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDisplayGuts(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).setDisplayGuts(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setFitToPage(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).setFitToPage(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setRowSumsBelow(boolean z) {
        WSBoolRecord wSBoolRecord = (WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129);
        wSBoolRecord.setRowSumsBelow(z);
        wSBoolRecord.setAlternateExpression(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setRowSumsRight(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).setRowSumsRight(z);
    }

    public boolean getAlternateExpression() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).getAlternateExpression();
    }

    public boolean getAlternateFormula() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).getAlternateFormula();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getAutobreaks() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).getAutobreaks();
    }

    public boolean getDialog() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).getDialog();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getDisplayGuts() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).getDisplayGuts();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isDisplayZeros() {
        return this._sheet.getWindowTwo().getDisplayZeros();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDisplayZeros(boolean z) {
        this._sheet.getWindowTwo().setDisplayZeros(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getFitToPage() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).getFitToPage();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getRowSumsBelow() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).getRowSumsBelow();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getRowSumsRight() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid((short) 129)).getRowSumsRight();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isPrintGridlines() {
        return getSheet().getPrintGridlines().getPrintGridlines();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setPrintGridlines(boolean z) {
        getSheet().getPrintGridlines().setPrintGridlines(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFPrintSetup getPrintSetup() {
        return new HSSFPrintSetup(this._sheet.getPageSettings().getPrintSetup());
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFHeader getHeader() {
        return new HSSFHeader(this._sheet.getPageSettings());
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFFooter getFooter() {
        return new HSSFFooter(this._sheet.getPageSettings());
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isSelected() {
        return getSheet().getWindowTwo().getSelected();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setSelected(boolean z) {
        getSheet().getWindowTwo().setSelected(z);
    }

    public boolean isActive() {
        return getSheet().getWindowTwo().isActive();
    }

    public void setActive(boolean z) {
        getSheet().getWindowTwo().setActive(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public double getMargin(short s) {
        return this._sheet.getPageSettings().getMargin(s);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setMargin(short s, double d) {
        this._sheet.getPageSettings().setMargin(s, d);
    }

    private WorksheetProtectionBlock getProtectionBlock() {
        return this._sheet.getProtectionBlock();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getProtect() {
        return getProtectionBlock().isSheetProtected();
    }

    public short getPassword() {
        return (short) getProtectionBlock().getPasswordHash();
    }

    public boolean getObjectProtect() {
        return getProtectionBlock().isObjectProtected();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean getScenarioProtect() {
        return getProtectionBlock().isScenarioProtected();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void protectSheet(String str) {
        getProtectionBlock().protectSheet(str, true, true);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setZoom(int i, int i2) {
        if (i < 1 || i > 65535) {
            throw new IllegalArgumentException("Numerator must be greater than 1 and less than 65536");
        }
        if (i2 < 1 || i2 > 65535) {
            throw new IllegalArgumentException("Denominator must be greater than 1 and less than 65536");
        }
        SCLRecord sCLRecord = new SCLRecord();
        sCLRecord.setNumerator((short) i);
        sCLRecord.setDenominator((short) i2);
        getSheet().setSCLRecord(sCLRecord);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public short getTopRow() {
        return this._sheet.getTopRow();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public short getLeftCol() {
        return this._sheet.getLeftCol();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void showInPane(short s, short s2) {
        this._sheet.setTopRow(s);
        this._sheet.setLeftCol(s2);
    }

    protected void shiftMerged(int i, int i2, int i3, boolean z) {
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        while (i4 < getNumMergedRegions()) {
            CellRangeAddress mergedRegion = getMergedRegion(i4);
            boolean z2 = mergedRegion.getFirstRow() >= i || mergedRegion.getLastRow() >= i;
            boolean z3 = mergedRegion.getFirstRow() <= i2 || mergedRegion.getLastRow() <= i2;
            if (z2 && z3 && !containsCell(mergedRegion, i - 1, 0) && !containsCell(mergedRegion, i2 + 1, 0)) {
                mergedRegion.setFirstRow(mergedRegion.getFirstRow() + i3);
                mergedRegion.setLastRow(mergedRegion.getLastRow() + i3);
                arrayList.add(mergedRegion);
                removeMergedRegion(i4);
                i4--;
            }
            i4++;
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            addMergedRegion((CellRangeAddress) it2.next());
        }
    }

    private static boolean containsCell(CellRangeAddress cellRangeAddress, int i, int i2) {
        return cellRangeAddress.getFirstRow() <= i && cellRangeAddress.getLastRow() >= i && cellRangeAddress.getFirstColumn() <= i2 && cellRangeAddress.getLastColumn() >= i2;
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void shiftRows(int i, int i2, int i3) {
        shiftRows(i, i2, i3, false, false);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void shiftRows(int i, int i2, int i3, boolean z, boolean z2) {
        shiftRows(i, i2, i3, z, z2, true);
    }

    public void shiftRows(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        int i4;
        int i5;
        NoteRecord[] noteRecords;
        HSSFComment cellComment;
        if (i3 < 0) {
            i5 = i;
            i4 = 1;
        } else {
            i4 = -1;
            i5 = i2;
        }
        if (z3) {
            noteRecords = this._sheet.getNoteRecords();
        } else {
            noteRecords = NoteRecord.EMPTY_ARRAY;
        }
        shiftMerged(i, i2, i3, true);
        this._sheet.getPageSettings().shiftRowBreaks(i, i2, i3);
        while (i5 >= i && i5 <= i2 && i5 >= 0 && i5 < 65536) {
            HSSFRow row = getRow(i5);
            if (row != null) {
                notifyRowShifting(row);
            }
            int i6 = i5 + i3;
            HSSFRow row2 = getRow(i6);
            if (row2 == null) {
                row2 = createRow(i6);
            }
            row2.removeAllCells();
            if (row != null) {
                if (z) {
                    row2.setHeight(row.getHeight());
                }
                if (z2) {
                    row.setHeight((short) 255);
                }
                Iterator<Cell> itCellIterator = row.cellIterator();
                while (itCellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) itCellIterator.next();
                    row.removeCell(hSSFCell);
                    CellValueRecordInterface cellValueRecord = hSSFCell.getCellValueRecord();
                    cellValueRecord.setRow(i6);
                    row2.createCellFromRecord(cellValueRecord);
                    this._sheet.addValueRecord(i6, cellValueRecord);
                    HSSFHyperlink hyperlink = hSSFCell.getHyperlink();
                    if (hyperlink != null) {
                        hyperlink.setFirstRow(hyperlink.getFirstRow() + i3);
                        hyperlink.setLastRow(hyperlink.getLastRow() + i3);
                    }
                }
                row.removeAllCells();
                if (z3) {
                    for (int length = noteRecords.length - 1; length >= 0; length--) {
                        NoteRecord noteRecord = noteRecords[length];
                        if (noteRecord.getRow() == i5 && (cellComment = getCellComment(i5, noteRecord.getColumn())) != null) {
                            cellComment.setRow(i6);
                        }
                    }
                }
            }
            i5 += i4;
        }
        int i7 = this._lastrow;
        if (i2 == i7 || i2 + i3 > i7) {
            this._lastrow = Math.min(i2 + i3, SpreadsheetVersion.EXCEL97.getLastRowIndex());
        }
        int i8 = this._firstrow;
        if (i == i8 || i + i3 < i8) {
            this._firstrow = Math.max(i + i3, 0);
        }
        short sCheckExternSheet = this._book.checkExternSheet(this._workbook.getSheetIndex(this));
        FormulaShifter formulaShifterCreateForRowShift = FormulaShifter.createForRowShift(sCheckExternSheet, i, i2, i3);
        this._sheet.updateFormulasAfterCellShift(formulaShifterCreateForRowShift, sCheckExternSheet);
        int numberOfSheets = this._workbook.getNumberOfSheets();
        for (int i9 = 0; i9 < numberOfSheets; i9++) {
            InternalSheet sheet = this._workbook.getSheetAt(i9).getSheet();
            if (sheet != this._sheet) {
                sheet.updateFormulasAfterCellShift(formulaShifterCreateForRowShift, this._book.checkExternSheet(i9));
            }
        }
        this._workbook.getWorkbook().updateNamesAfterCellShift(formulaShifterCreateForRowShift);
    }

    protected void insertChartRecords(List<Record> list) {
        this._sheet.getRecords().addAll(this._sheet.findFirstRecordLocBySid((short) 574), list);
    }

    private void notifyRowShifting(HSSFRow hSSFRow) {
        String str = "Row[rownum=" + hSSFRow.getRowNum() + "] contains cell(s) included in a multi-cell array formula. You cannot change part of an array.";
        Iterator<Cell> it2 = hSSFRow.iterator();
        while (it2.hasNext()) {
            HSSFCell hSSFCell = (HSSFCell) it2.next();
            if (hSSFCell.isPartOfArrayFormulaGroup()) {
                hSSFCell.notifyArrayFormulaChanging(str);
            }
        }
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void createFreezePane(int i, int i2, int i3, int i4) {
        validateColumn(i);
        validateRow(i2);
        if (i3 < i) {
            throw new IllegalArgumentException("leftmostColumn parameter must not be less than colSplit parameter");
        }
        if (i4 < i2) {
            throw new IllegalArgumentException("topRow parameter must not be less than leftmostColumn parameter");
        }
        getSheet().createFreezePane(i, i2, i4, i3);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void createFreezePane(int i, int i2) {
        createFreezePane(i, i2, i, i2);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void createSplitPane(int i, int i2, int i3, int i4, int i5) {
        getSheet().createSplitPane(i, i2, i4, i3, i5);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public PaneInformation getPaneInformation() {
        return getSheet().getPaneInformation();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDisplayGridlines(boolean z) {
        this._sheet.setDisplayGridlines(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isDisplayGridlines() {
        return this._sheet.isDisplayGridlines();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDisplayFormulas(boolean z) {
        this._sheet.setDisplayFormulas(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isDisplayFormulas() {
        return this._sheet.isDisplayFormulas();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDisplayRowColHeadings(boolean z) {
        this._sheet.setDisplayRowColHeadings(z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isDisplayRowColHeadings() {
        return this._sheet.isDisplayRowColHeadings();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setRowBreak(int i) {
        validateRow(i);
        this._sheet.getPageSettings().setRowBreak(i, (short) 0, (short) 255);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isRowBroken(int i) {
        return this._sheet.getPageSettings().isRowBroken(i);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void removeRowBreak(int i) {
        this._sheet.getPageSettings().removeRowBreak(i);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int[] getRowBreaks() {
        return this._sheet.getPageSettings().getRowBreaks();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public int[] getColumnBreaks() {
        return this._sheet.getPageSettings().getColumnBreaks();
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setColumnBreak(int i) {
        short s = (short) i;
        validateColumn(s);
        this._sheet.getPageSettings().setColumnBreak(s, (short) 0, (short) SpreadsheetVersion.EXCEL97.getLastRowIndex());
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public boolean isColumnBroken(int i) {
        return this._sheet.getPageSettings().isColumnBroken(i);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void removeColumnBreak(int i) {
        this._sheet.getPageSettings().removeColumnBreak(i);
    }

    protected void validateRow(int i) {
        int lastRowIndex = SpreadsheetVersion.EXCEL97.getLastRowIndex();
        if (i <= lastRowIndex) {
            if (i < 0) {
                throw new IllegalArgumentException("Minumum row number is 0");
            }
        } else {
            throw new IllegalArgumentException("Maximum row number is " + lastRowIndex);
        }
    }

    protected void validateColumn(int i) {
        int lastColumnIndex = SpreadsheetVersion.EXCEL97.getLastColumnIndex();
        if (i <= lastColumnIndex) {
            if (i < 0) {
                throw new IllegalArgumentException("Minimum column number is 0");
            }
        } else {
            throw new IllegalArgumentException("Maximum column number is " + lastColumnIndex);
        }
    }

    public void dumpDrawingRecords(boolean z) {
        this._sheet.aggregateDrawingRecords(this._book.getDrawingManager(), false);
        List<EscherRecord> escherRecords = ((EscherAggregate) getSheet().findFirstRecordBySid(EscherAggregate.sid)).getEscherRecords();
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

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFPatriarch createDrawingPatriarch() {
        if (this._patriarch == null) {
            this._book.createDrawingGroup();
            this._sheet.aggregateDrawingRecords(this._book.getDrawingManager(), true);
            EscherAggregate escherAggregate = (EscherAggregate) this._sheet.findFirstRecordBySid(EscherAggregate.sid);
            this._patriarch = new HSSFPatriarch(this, escherAggregate);
            escherAggregate.clear();
            escherAggregate.setPatriarch(this._patriarch);
        }
        return this._patriarch;
    }

    public EscherAggregate getDrawingEscherAggregate() {
        this._book.findDrawingGroup();
        if (this._book.getDrawingManager() == null || this._sheet.aggregateDrawingRecords(this._book.getDrawingManager(), false) == -1) {
            return null;
        }
        return (EscherAggregate) this._sheet.findFirstRecordBySid(EscherAggregate.sid);
    }

    public HSSFPatriarch getDrawingPatriarch() {
        HSSFPatriarch hSSFPatriarch = this._patriarch;
        if (hSSFPatriarch != null) {
            return hSSFPatriarch;
        }
        EscherAggregate drawingEscherAggregate = getDrawingEscherAggregate();
        if (drawingEscherAggregate == null) {
            return null;
        }
        HSSFPatriarch hSSFPatriarch2 = new HSSFPatriarch(this, drawingEscherAggregate);
        this._patriarch = hSSFPatriarch2;
        drawingEscherAggregate.setPatriarch(hSSFPatriarch2);
        drawingEscherAggregate.convertRecordsToUserModel();
        return this._patriarch;
    }

    public void setColumnGroupCollapsed(short s, boolean z) {
        setColumnGroupCollapsed(s & 65535, z);
    }

    public void groupColumn(short s, short s2) {
        groupColumn(s & 65535, s2 & 65535);
    }

    public void ungroupColumn(short s, short s2) {
        ungroupColumn(s & 65535, s2 & 65535);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setColumnGroupCollapsed(int i, boolean z) {
        this._sheet.setColumnGroupCollapsed(i, z);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void groupColumn(int i, int i2) {
        this._sheet.groupColumnRange(i, i2, true);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void ungroupColumn(int i, int i2) {
        this._sheet.groupColumnRange(i, i2, false);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void groupRow(int i, int i2) {
        this._sheet.groupRowRange(i, i2, true);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void ungroupRow(int i, int i2) {
        this._sheet.groupRowRange(i, i2, false);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setRowGroupCollapsed(int i, boolean z) {
        if (z) {
            this._sheet.getRowsAggregate().collapseRow(i);
        } else {
            this._sheet.getRowsAggregate().expandRow(i);
        }
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void setDefaultColumnStyle(int i, CellStyle cellStyle) {
        this._sheet.setDefaultColumnStyle(i, ((HSSFCellStyle) cellStyle).getIndex());
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void autoSizeColumn(int i) {
        autoSizeColumn(i, false);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public void autoSizeColumn(int i, boolean z) {
        HSSFCell hSSFCell;
        String strValueOf;
        HSSFDataFormatter hSSFDataFormatter;
        FontRenderContext fontRenderContext;
        double dMax;
        FontRenderContext fontRenderContext2;
        HSSFRichTextString hSSFRichTextString;
        HSSFCellStyle hSSFCellStyle;
        String[] strArr;
        int i2 = i;
        int i3 = 1;
        FontRenderContext fontRenderContext3 = new FontRenderContext((AffineTransform) null, true, true);
        HSSFWorkbook hSSFWorkbookCreate = HSSFWorkbook.create(this._book);
        HSSFDataFormatter hSSFDataFormatter2 = new HSSFDataFormatter();
        HSSFFont fontAt = hSSFWorkbookCreate.getFontAt((short) 0);
        AttributedString attributedString = new AttributedString("0");
        copyAttributes(fontAt, attributedString, 0, 1);
        int advance = (int) new TextLayout(attributedString.getIterator(), fontRenderContext3).getAdvance();
        Iterator<Row> itRowIterator = rowIterator();
        double d = -1.0d;
        while (itRowIterator.hasNext()) {
            HSSFRow hSSFRow = (HSSFRow) itRowIterator.next();
            HSSFCell cell = hSSFRow.getCell(i2);
            if (cell == null) {
                break;
            }
            HSSFCell hSSFCell2 = cell;
            int lastColumn = 1;
            for (int i4 = 0; i4 < getNumMergedRegions(); i4++) {
                CellRangeAddress mergedRegion = getMergedRegion(i4);
                if (containsCell(mergedRegion, hSSFRow.getRowNum(), i2)) {
                    if (!z) {
                        break;
                        break;
                    } else {
                        HSSFCell cell2 = hSSFRow.getCell(mergedRegion.getFirstColumn());
                        lastColumn = (mergedRegion.getLastColumn() + i3) - mergedRegion.getFirstColumn();
                        hSSFCell2 = cell2;
                    }
                }
            }
            HSSFCellStyle cellStyle = hSSFCell2.getCellStyle();
            int cellType = hSSFCell2.getCellType();
            if (cellType == 2) {
                cellType = hSSFCell2.getCachedFormulaResultType();
            }
            HSSFFont fontAt2 = hSSFWorkbookCreate.getFontAt(cellStyle.getFontIndex());
            HSSFWorkbook hSSFWorkbook = hSSFWorkbookCreate;
            double d2 = d;
            if (cellType == 1) {
                HSSFRichTextString richStringCellValue = hSSFCell2.getRichStringCellValue();
                String[] strArrSplit = richStringCellValue.getString().split("\\n");
                double dMax2 = d2;
                int i5 = 0;
                while (i5 < strArrSplit.length) {
                    String str = strArrSplit[i5] + '0';
                    AttributedString attributedString2 = new AttributedString(str);
                    copyAttributes(fontAt2, attributedString2, 0, str.length());
                    if (richStringCellValue.numFormattingRuns() > 0) {
                        int i6 = 0;
                        while (i6 < strArrSplit[i5].length()) {
                            short fontAtIndex = richStringCellValue.getFontAtIndex(i6);
                            if (fontAtIndex != 0) {
                                strArr = strArrSplit;
                                copyAttributes(hSSFWorkbook.getFontAt(fontAtIndex), attributedString2, i6, i6 + 1);
                            } else {
                                strArr = strArrSplit;
                            }
                            i6++;
                            strArrSplit = strArr;
                        }
                    }
                    String[] strArr2 = strArrSplit;
                    TextLayout textLayout = new TextLayout(attributedString2.getIterator(), fontRenderContext3);
                    if (cellStyle.getRotation() != 0) {
                        AffineTransform affineTransform = new AffineTransform();
                        fontRenderContext2 = fontRenderContext3;
                        affineTransform.concatenate(AffineTransform.getRotateInstance(((cellStyle.getRotation() * 2.0d) * 3.141592653589793d) / 360.0d));
                        hSSFRichTextString = richStringCellValue;
                        affineTransform.concatenate(AffineTransform.getScaleInstance(1.0d, 2.0d));
                        dMax2 = Math.max(dMax2, ((textLayout.getOutline(affineTransform).getBounds().getWidth() / lastColumn) / advance) + hSSFCell2.getCellStyle().getIndention());
                        hSSFCellStyle = cellStyle;
                    } else {
                        fontRenderContext2 = fontRenderContext3;
                        hSSFRichTextString = richStringCellValue;
                        hSSFCellStyle = cellStyle;
                        dMax2 = Math.max(dMax2, ((textLayout.getBounds().getWidth() / lastColumn) / advance) + hSSFCell2.getCellStyle().getIndention());
                    }
                    i5++;
                    cellStyle = hSSFCellStyle;
                    strArrSplit = strArr2;
                    fontRenderContext3 = fontRenderContext2;
                    richStringCellValue = hSSFRichTextString;
                }
                fontRenderContext = fontRenderContext3;
                hSSFDataFormatter = hSSFDataFormatter2;
                d = dMax2;
            } else {
                FontRenderContext fontRenderContext4 = fontRenderContext3;
                if (cellType == 0) {
                    hSSFCell = hSSFCell2;
                    try {
                        strValueOf = hSSFDataFormatter2.formatCellValue(hSSFCell);
                    } catch (Exception unused) {
                        strValueOf = "" + hSSFCell.getNumericCellValue();
                    }
                } else {
                    hSSFCell = hSSFCell2;
                    strValueOf = cellType == 4 ? String.valueOf(hSSFCell.getBooleanCellValue()) : null;
                }
                if (strValueOf != null) {
                    String str2 = strValueOf + '0';
                    AttributedString attributedString3 = new AttributedString(str2);
                    copyAttributes(fontAt2, attributedString3, 0, str2.length());
                    fontRenderContext = fontRenderContext4;
                    TextLayout textLayout2 = new TextLayout(attributedString3.getIterator(), fontRenderContext);
                    if (cellStyle.getRotation() != 0) {
                        AffineTransform affineTransform2 = new AffineTransform();
                        hSSFDataFormatter = hSSFDataFormatter2;
                        affineTransform2.concatenate(AffineTransform.getRotateInstance(((cellStyle.getRotation() * 2.0d) * 3.141592653589793d) / 360.0d));
                        affineTransform2.concatenate(AffineTransform.getScaleInstance(1.0d, 2.0d));
                        dMax = Math.max(d2, ((textLayout2.getOutline(affineTransform2).getBounds().getWidth() / lastColumn) / advance) + hSSFCell.getCellStyle().getIndention());
                    } else {
                        hSSFDataFormatter = hSSFDataFormatter2;
                        dMax = Math.max(d2, ((textLayout2.getBounds().getWidth() / lastColumn) / advance) + hSSFCell.getCellStyle().getIndention());
                    }
                    d = dMax;
                    i2 = i;
                    fontRenderContext3 = fontRenderContext;
                    hSSFDataFormatter2 = hSSFDataFormatter;
                    hSSFWorkbookCreate = hSSFWorkbook;
                    i3 = 1;
                } else {
                    hSSFDataFormatter = hSSFDataFormatter2;
                    d = d2;
                    fontRenderContext = fontRenderContext4;
                }
            }
            i2 = i;
            fontRenderContext3 = fontRenderContext;
            hSSFDataFormatter2 = hSSFDataFormatter;
            hSSFWorkbookCreate = hSSFWorkbook;
            i3 = 1;
        }
        if (d != -1.0d) {
            double d3 = d * 256.0d;
            if (d3 > 32767.0d) {
                d3 = 32767.0d;
            }
            this._sheet.setColumnWidth(i, (short) d3);
        }
    }

    private void copyAttributes(HSSFFont hSSFFont, AttributedString attributedString, int i, int i2) {
        attributedString.addAttribute(TextAttribute.FAMILY, hSSFFont.getFontName(), i, i2);
        attributedString.addAttribute(TextAttribute.SIZE, new Float(hSSFFont.getFontHeightInPoints()));
        if (hSSFFont.getBoldweight() == 700) {
            attributedString.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, i, i2);
        }
        if (hSSFFont.getItalic()) {
            attributedString.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, i, i2);
        }
        if (hSSFFont.getUnderline() == 1) {
            attributedString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, i, i2);
        }
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFComment getCellComment(int i, int i2) {
        HSSFRow row = getRow(i);
        if (row == null) {
            return null;
        }
        HSSFCell cell = row.getCell(i2);
        if (cell != null) {
            return cell.getCellComment();
        }
        return HSSFCell.findCellComment(this._sheet, i, i2);
    }

    public HSSFSheetConditionalFormatting getSheetConditionalFormatting() {
        return new HSSFSheetConditionalFormatting(this);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public String getSheetName() {
        HSSFWorkbook workbook = getWorkbook();
        return workbook.getSheetName(workbook.getSheetIndex(this));
    }

    private CellRange<HSSFCell> getCellRange(CellRangeAddress cellRangeAddress) {
        int firstRow = cellRangeAddress.getFirstRow();
        int firstColumn = cellRangeAddress.getFirstColumn();
        int lastRow = cellRangeAddress.getLastRow();
        int lastColumn = cellRangeAddress.getLastColumn();
        int i = (lastRow - firstRow) + 1;
        int i2 = (lastColumn - firstColumn) + 1;
        ArrayList arrayList = new ArrayList(i * i2);
        for (int i3 = firstRow; i3 <= lastRow; i3++) {
            for (int i4 = firstColumn; i4 <= lastColumn; i4++) {
                HSSFRow row = getRow(i3);
                if (row == null) {
                    row = createRow(i3);
                }
                HSSFCell cell = row.getCell(i4);
                if (cell == null) {
                    cell = row.createCell(i4);
                }
                arrayList.add(cell);
            }
        }
        return SSCellRange.create(firstRow, firstColumn, i, i2, arrayList, HSSFCell.class);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public CellRange<HSSFCell> setArrayFormula(String str, CellRangeAddress cellRangeAddress) throws FormulaParseException {
        Ptg[] ptgArr = HSSFFormulaParser.parse(str, this._workbook, 2, this._workbook.getSheetIndex(this));
        CellRange<HSSFCell> cellRange = getCellRange(cellRangeAddress);
        Iterator<C> it2 = cellRange.iterator();
        while (it2.hasNext()) {
            ((HSSFCell) it2.next()).setCellArrayFormula(cellRangeAddress);
        }
        ((FormulaRecordAggregate) ((HSSFCell) cellRange.getTopLeftCell()).getCellValueRecord()).setArrayFormula(cellRangeAddress, ptgArr);
        return cellRange;
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public CellRange<HSSFCell> removeArrayFormula(Cell cell) {
        if (cell.getSheet() != this) {
            throw new IllegalArgumentException("Specified cell does not belong to this sheet.");
        }
        CellValueRecordInterface cellValueRecord = ((HSSFCell) cell).getCellValueRecord();
        if (!(cellValueRecord instanceof FormulaRecordAggregate)) {
            throw new IllegalArgumentException("Cell " + new CellReference(cell).formatAsString() + " is not part of an array formula.");
        }
        CellRange<HSSFCell> cellRange = getCellRange(((FormulaRecordAggregate) cellValueRecord).removeArrayFormula(cell.getRowIndex(), cell.getColumnIndex()));
        Iterator<C> it2 = cellRange.iterator();
        while (it2.hasNext()) {
            ((HSSFCell) it2.next()).setCellType(3);
        }
        return cellRange;
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public DataValidationHelper getDataValidationHelper() {
        return new HSSFDataValidationHelper(this);
    }

    @Override // org.apache.poi.ss.usermodel.Sheet
    public HSSFAutoFilter setAutoFilter(CellRangeAddress cellRangeAddress) {
        InternalWorkbook workbook = this._workbook.getWorkbook();
        int sheetIndex = this._workbook.getSheetIndex(this);
        int i = sheetIndex + 1;
        NameRecord specificBuiltinRecord = workbook.getSpecificBuiltinRecord((byte) 13, i);
        if (specificBuiltinRecord == null) {
            specificBuiltinRecord = workbook.createBuiltInName((byte) 13, i);
        }
        specificBuiltinRecord.setNameDefinition(new Ptg[]{new Area3DPtg(cellRangeAddress.getFirstRow(), cellRangeAddress.getLastRow(), cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn(), false, false, false, false, sheetIndex)});
        AutoFilterInfoRecord autoFilterInfoRecord = new AutoFilterInfoRecord();
        autoFilterInfoRecord.setNumEntries((short) ((cellRangeAddress.getLastColumn() + 1) - cellRangeAddress.getFirstColumn()));
        this._sheet.getRecords().add(this._sheet.findFirstRecordLocBySid((short) 512), autoFilterInfoRecord);
        HSSFPatriarch hSSFPatriarchCreateDrawingPatriarch = createDrawingPatriarch();
        int firstColumn = cellRangeAddress.getFirstColumn();
        while (firstColumn <= cellRangeAddress.getLastColumn()) {
            firstColumn++;
            hSSFPatriarchCreateDrawingPatriarch.createComboBox(new HSSFClientAnchor(0, 0, 0, 0, (short) firstColumn, cellRangeAddress.getFirstRow(), (short) firstColumn, cellRangeAddress.getFirstRow() + 1));
        }
        return new HSSFAutoFilter(this);
    }
}
