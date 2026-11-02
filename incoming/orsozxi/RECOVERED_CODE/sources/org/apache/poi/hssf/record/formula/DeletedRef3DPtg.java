package org.apache.poi.hssf.record.formula;

import org.apache.poi.hssf.usermodel.HSSFErrorConstants;
import org.apache.poi.ss.formula.FormulaRenderingWorkbook;
import org.apache.poi.ss.formula.WorkbookDependentFormula;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class DeletedRef3DPtg extends OperandPtg implements WorkbookDependentFormula {
    public static final byte sid = 60;
    private final int field_1_index_extern_sheet;
    private final int unused1;

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public byte getDefaultOperandClass() {
        return (byte) 0;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return 7;
    }

    public DeletedRef3DPtg(LittleEndianInput littleEndianInput) {
        this.field_1_index_extern_sheet = littleEndianInput.readUShort();
        this.unused1 = littleEndianInput.readInt();
    }

    public DeletedRef3DPtg(int i) {
        this.field_1_index_extern_sheet = i;
        this.unused1 = 0;
    }

    @Override // org.apache.poi.ss.formula.WorkbookDependentFormula
    public String toFormulaString(FormulaRenderingWorkbook formulaRenderingWorkbook) {
        return ExternSheetNameResolver.prependSheetName(formulaRenderingWorkbook, this.field_1_index_extern_sheet, HSSFErrorConstants.getText(23));
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public String toFormulaString() {
        throw new RuntimeException("3D references need a workbook to determine formula text");
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + sid);
        littleEndianOutput.writeShort(this.field_1_index_extern_sheet);
        littleEndianOutput.writeInt(this.unused1);
    }
}
