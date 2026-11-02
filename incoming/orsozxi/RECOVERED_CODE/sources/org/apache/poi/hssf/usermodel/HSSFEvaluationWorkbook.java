package org.apache.poi.hssf.usermodel;

import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.record.NameRecord;
import org.apache.poi.hssf.record.aggregates.FormulaRecordAggregate;
import org.apache.poi.hssf.record.formula.NamePtg;
import org.apache.poi.hssf.record.formula.NameXPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.EvaluationCell;
import org.apache.poi.ss.formula.EvaluationName;
import org.apache.poi.ss.formula.EvaluationSheet;
import org.apache.poi.ss.formula.EvaluationWorkbook;
import org.apache.poi.ss.formula.FormulaParsingWorkbook;
import org.apache.poi.ss.formula.FormulaRenderingWorkbook;

/* loaded from: classes3.dex */
public final class HSSFEvaluationWorkbook implements FormulaRenderingWorkbook, EvaluationWorkbook, FormulaParsingWorkbook {
    private final InternalWorkbook _iBook;
    private final HSSFWorkbook _uBook;

    public static HSSFEvaluationWorkbook create(HSSFWorkbook hSSFWorkbook) {
        if (hSSFWorkbook == null) {
            return null;
        }
        return new HSSFEvaluationWorkbook(hSSFWorkbook);
    }

    private HSSFEvaluationWorkbook(HSSFWorkbook hSSFWorkbook) {
        this._uBook = hSSFWorkbook;
        this._iBook = hSSFWorkbook.getWorkbook();
    }

    @Override // org.apache.poi.ss.formula.FormulaParsingWorkbook
    public int getExternalSheetIndex(String str) {
        return this._iBook.checkExternSheet(this._uBook.getSheetIndex(str));
    }

    @Override // org.apache.poi.ss.formula.FormulaParsingWorkbook
    public int getExternalSheetIndex(String str, String str2) {
        return this._iBook.getExternalSheetIndex(str, str2);
    }

    @Override // org.apache.poi.ss.formula.FormulaParsingWorkbook
    public NameXPtg getNameXPtg(String str) {
        return this._iBook.getNameXPtg(str);
    }

    @Override // org.apache.poi.ss.formula.FormulaParsingWorkbook
    public EvaluationName getName(String str, int i) {
        for (int i2 = 0; i2 < this._iBook.getNumNames(); i2++) {
            NameRecord nameRecord = this._iBook.getNameRecord(i2);
            if (nameRecord.getSheetNumber() == i + 1 && str.equalsIgnoreCase(nameRecord.getNameText())) {
                return new Name(nameRecord, i2);
            }
        }
        if (i == -1) {
            return null;
        }
        return getName(str, -1);
    }

    @Override // org.apache.poi.ss.formula.EvaluationWorkbook
    public int getSheetIndex(EvaluationSheet evaluationSheet) {
        return this._uBook.getSheetIndex(((HSSFEvaluationSheet) evaluationSheet).getHSSFSheet());
    }

    @Override // org.apache.poi.ss.formula.EvaluationWorkbook
    public int getSheetIndex(String str) {
        return this._uBook.getSheetIndex(str);
    }

    @Override // org.apache.poi.ss.formula.EvaluationWorkbook
    public String getSheetName(int i) {
        return this._uBook.getSheetName(i);
    }

    @Override // org.apache.poi.ss.formula.EvaluationWorkbook
    public EvaluationSheet getSheet(int i) {
        return new HSSFEvaluationSheet(this._uBook.getSheetAt(i));
    }

    @Override // org.apache.poi.ss.formula.EvaluationWorkbook
    public int convertFromExternSheetIndex(int i) {
        return this._iBook.getSheetIndexFromExternSheetIndex(i);
    }

    @Override // org.apache.poi.ss.formula.FormulaRenderingWorkbook, org.apache.poi.ss.formula.EvaluationWorkbook
    public EvaluationWorkbook.ExternalSheet getExternalSheet(int i) {
        return this._iBook.getExternalSheet(i);
    }

    @Override // org.apache.poi.ss.formula.EvaluationWorkbook
    public EvaluationWorkbook.ExternalName getExternalName(int i, int i2) {
        return this._iBook.getExternalName(i, i2);
    }

    @Override // org.apache.poi.ss.formula.FormulaRenderingWorkbook, org.apache.poi.ss.formula.EvaluationWorkbook
    public String resolveNameXText(NameXPtg nameXPtg) {
        return this._iBook.resolveNameXText(nameXPtg.getSheetRefIndex(), nameXPtg.getNameIndex());
    }

    @Override // org.apache.poi.ss.formula.FormulaRenderingWorkbook
    public String getSheetNameByExternSheet(int i) {
        return this._iBook.findSheetNameFromExternSheet(i);
    }

    @Override // org.apache.poi.ss.formula.FormulaRenderingWorkbook
    public String getNameText(NamePtg namePtg) {
        return this._iBook.getNameRecord(namePtg.getIndex()).getNameText();
    }

    @Override // org.apache.poi.ss.formula.EvaluationWorkbook
    public EvaluationName getName(NamePtg namePtg) {
        int index = namePtg.getIndex();
        return new Name(this._iBook.getNameRecord(index), index);
    }

    @Override // org.apache.poi.ss.formula.EvaluationWorkbook
    public Ptg[] getFormulaTokens(EvaluationCell evaluationCell) {
        return ((FormulaRecordAggregate) ((HSSFEvaluationCell) evaluationCell).getHSSFCell().getCellValueRecord()).getFormulaTokens();
    }

    private static final class Name implements EvaluationName {
        private final int _index;
        private final NameRecord _nameRecord;

        public Name(NameRecord nameRecord, int i) {
            this._nameRecord = nameRecord;
            this._index = i;
        }

        @Override // org.apache.poi.ss.formula.EvaluationName
        public Ptg[] getNameDefinition() {
            return this._nameRecord.getNameDefinition();
        }

        @Override // org.apache.poi.ss.formula.EvaluationName
        public String getNameText() {
            return this._nameRecord.getNameText();
        }

        @Override // org.apache.poi.ss.formula.EvaluationName
        public boolean hasFormula() {
            return this._nameRecord.hasFormula();
        }

        @Override // org.apache.poi.ss.formula.EvaluationName
        public boolean isFunctionName() {
            return this._nameRecord.isFunctionName();
        }

        @Override // org.apache.poi.ss.formula.EvaluationName
        public boolean isRange() {
            return this._nameRecord.hasFormula();
        }

        @Override // org.apache.poi.ss.formula.EvaluationName
        public NamePtg createPtg() {
            return new NamePtg(this._index);
        }
    }

    @Override // org.apache.poi.ss.formula.FormulaParsingWorkbook
    public SpreadsheetVersion getSpreadsheetVersion() {
        return SpreadsheetVersion.EXCEL97;
    }
}
