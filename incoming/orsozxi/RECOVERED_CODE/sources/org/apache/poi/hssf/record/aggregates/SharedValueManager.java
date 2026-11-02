package org.apache.poi.hssf.record.aggregates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.record.ArrayRecord;
import org.apache.poi.hssf.record.SharedFormulaRecord;
import org.apache.poi.hssf.record.SharedValueRecordBase;
import org.apache.poi.hssf.record.TableRecord;
import org.apache.poi.hssf.util.CellRangeAddress8Bit;
import org.apache.poi.ss.util.CellReference;

/* loaded from: classes3.dex */
public final class SharedValueManager {
    private static final Comparator<SharedFormulaGroup> SVGComparator = new Comparator<SharedFormulaGroup>() { // from class: org.apache.poi.hssf.record.aggregates.SharedValueManager.1
        @Override // java.util.Comparator
        public int compare(SharedFormulaGroup sharedFormulaGroup, SharedFormulaGroup sharedFormulaGroup2) {
            CellRangeAddress8Bit range = sharedFormulaGroup.getSFR().getRange();
            CellRangeAddress8Bit range2 = sharedFormulaGroup2.getSFR().getRange();
            int firstRow = range.getFirstRow() - range2.getFirstRow();
            if (firstRow != 0) {
                return firstRow;
            }
            int firstColumn = range.getFirstColumn() - range2.getFirstColumn();
            if (firstColumn != 0) {
                return firstColumn;
            }
            return 0;
        }
    };
    private final List<ArrayRecord> _arrayRecords;
    private SharedFormulaGroup[] _groups;
    private final Map<SharedFormulaRecord, SharedFormulaGroup> _groupsBySharedFormulaRecord;
    private final TableRecord[] _tableRecords;

    private static final class SharedFormulaGroup {
        private final CellReference _firstCell;
        private final FormulaRecordAggregate[] _frAggs;
        private int _numberOfFormulas;
        private final SharedFormulaRecord _sfr;

        public SharedFormulaGroup(SharedFormulaRecord sharedFormulaRecord, CellReference cellReference) {
            if (!sharedFormulaRecord.isInRange(cellReference.getRow(), cellReference.getCol())) {
                throw new IllegalArgumentException("First formula cell " + cellReference.formatAsString() + " is not shared formula range " + sharedFormulaRecord.getRange().toString() + ".");
            }
            this._sfr = sharedFormulaRecord;
            this._firstCell = cellReference;
            this._frAggs = new FormulaRecordAggregate[((sharedFormulaRecord.getLastColumn() - sharedFormulaRecord.getFirstColumn()) + 1) * ((sharedFormulaRecord.getLastRow() - sharedFormulaRecord.getFirstRow()) + 1)];
            this._numberOfFormulas = 0;
        }

        public void add(FormulaRecordAggregate formulaRecordAggregate) {
            if (this._numberOfFormulas == 0 && (this._firstCell.getRow() != formulaRecordAggregate.getRow() || this._firstCell.getCol() != formulaRecordAggregate.getColumn())) {
                throw new IllegalStateException("shared formula coding error");
            }
            int i = this._numberOfFormulas;
            FormulaRecordAggregate[] formulaRecordAggregateArr = this._frAggs;
            if (i >= formulaRecordAggregateArr.length) {
                throw new RuntimeException("Too many formula records for shared formula group");
            }
            this._numberOfFormulas = i + 1;
            formulaRecordAggregateArr[i] = formulaRecordAggregate;
        }

        public void unlinkSharedFormulas() {
            for (int i = 0; i < this._numberOfFormulas; i++) {
                this._frAggs[i].unlinkSharedFormula();
            }
        }

        public SharedFormulaRecord getSFR() {
            return this._sfr;
        }

        public final String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(this._sfr.getRange().toString());
            stringBuffer.append("]");
            return stringBuffer.toString();
        }

        public boolean isFirstCell(int i, int i2) {
            return this._firstCell.getRow() == i && this._firstCell.getCol() == i2;
        }
    }

    public static SharedValueManager createEmpty() {
        return new SharedValueManager(new SharedFormulaRecord[0], new CellReference[0], new ArrayRecord[0], new TableRecord[0]);
    }

    private SharedValueManager(SharedFormulaRecord[] sharedFormulaRecordArr, CellReference[] cellReferenceArr, ArrayRecord[] arrayRecordArr, TableRecord[] tableRecordArr) {
        int length = sharedFormulaRecordArr.length;
        if (length != cellReferenceArr.length) {
            throw new IllegalArgumentException("array sizes don't match: " + length + "!=" + cellReferenceArr.length + ".");
        }
        this._arrayRecords = toList(arrayRecordArr);
        this._tableRecords = tableRecordArr;
        HashMap map = new HashMap((length * 3) / 2);
        for (int i = 0; i < length; i++) {
            SharedFormulaRecord sharedFormulaRecord = sharedFormulaRecordArr[i];
            map.put(sharedFormulaRecord, new SharedFormulaGroup(sharedFormulaRecord, cellReferenceArr[i]));
        }
        this._groupsBySharedFormulaRecord = map;
    }

    private static <Z> List<Z> toList(Z[] zArr) {
        ArrayList arrayList = new ArrayList(zArr.length);
        for (Z z : zArr) {
            arrayList.add(z);
        }
        return arrayList;
    }

    public static SharedValueManager create(SharedFormulaRecord[] sharedFormulaRecordArr, CellReference[] cellReferenceArr, ArrayRecord[] arrayRecordArr, TableRecord[] tableRecordArr) {
        if (sharedFormulaRecordArr.length + cellReferenceArr.length + arrayRecordArr.length + tableRecordArr.length < 1) {
            return createEmpty();
        }
        return new SharedValueManager(sharedFormulaRecordArr, cellReferenceArr, arrayRecordArr, tableRecordArr);
    }

    public SharedFormulaRecord linkSharedFormulaRecord(CellReference cellReference, FormulaRecordAggregate formulaRecordAggregate) {
        SharedFormulaGroup sharedFormulaGroupFindFormulaGroup = findFormulaGroup(getGroups(), cellReference);
        sharedFormulaGroupFindFormulaGroup.add(formulaRecordAggregate);
        return sharedFormulaGroupFindFormulaGroup.getSFR();
    }

    private static SharedFormulaGroup findFormulaGroup(SharedFormulaGroup[] sharedFormulaGroupArr, CellReference cellReference) {
        int row = cellReference.getRow();
        short col = cellReference.getCol();
        for (SharedFormulaGroup sharedFormulaGroup : sharedFormulaGroupArr) {
            if (sharedFormulaGroup.isFirstCell(row, col)) {
                return sharedFormulaGroup;
            }
        }
        throw new RuntimeException("Failed to find a matching shared formula record");
    }

    private SharedFormulaGroup[] getGroups() {
        if (this._groups == null) {
            SharedFormulaGroup[] sharedFormulaGroupArr = new SharedFormulaGroup[this._groupsBySharedFormulaRecord.size()];
            this._groupsBySharedFormulaRecord.values().toArray(sharedFormulaGroupArr);
            Arrays.sort(sharedFormulaGroupArr, SVGComparator);
            this._groups = sharedFormulaGroupArr;
        }
        return this._groups;
    }

    public SharedValueRecordBase getRecordForFirstCell(FormulaRecordAggregate formulaRecordAggregate) {
        org.apache.poi.hssf.util.CellReference expReference = formulaRecordAggregate.getFormulaRecord().getFormula().getExpReference();
        if (expReference == null) {
            return null;
        }
        int row = expReference.getRow();
        short col = expReference.getCol();
        if (formulaRecordAggregate.getRow() == row && formulaRecordAggregate.getColumn() == col) {
            for (SharedFormulaGroup sharedFormulaGroup : getGroups()) {
                if (sharedFormulaGroup.isFirstCell(row, col)) {
                    return sharedFormulaGroup.getSFR();
                }
            }
            for (TableRecord tableRecord : this._tableRecords) {
                if (tableRecord.isFirstCell(row, col)) {
                    return tableRecord;
                }
            }
            for (ArrayRecord arrayRecord : this._arrayRecords) {
                if (arrayRecord.isFirstCell(row, col)) {
                    return arrayRecord;
                }
            }
        }
        return null;
    }

    public void unlink(SharedFormulaRecord sharedFormulaRecord) {
        SharedFormulaGroup sharedFormulaGroupRemove = this._groupsBySharedFormulaRecord.remove(sharedFormulaRecord);
        if (sharedFormulaGroupRemove == null) {
            throw new IllegalStateException("Failed to find formulas for shared formula");
        }
        this._groups = null;
        sharedFormulaGroupRemove.unlinkSharedFormulas();
    }

    public void addArrayRecord(ArrayRecord arrayRecord) {
        this._arrayRecords.add(arrayRecord);
    }

    public CellRangeAddress8Bit removeArrayFormula(int i, int i2) {
        for (ArrayRecord arrayRecord : this._arrayRecords) {
            if (arrayRecord.isInRange(i, i2)) {
                this._arrayRecords.remove(arrayRecord);
                return arrayRecord.getRange();
            }
        }
        throw new IllegalArgumentException("Specified cell " + new CellReference(i, i2, false, false).formatAsString() + " is not part of an array formula.");
    }

    public ArrayRecord getArrayRecord(int i, int i2) {
        for (ArrayRecord arrayRecord : this._arrayRecords) {
            if (arrayRecord.isFirstCell(i, i2)) {
                return arrayRecord;
            }
        }
        return null;
    }
}
