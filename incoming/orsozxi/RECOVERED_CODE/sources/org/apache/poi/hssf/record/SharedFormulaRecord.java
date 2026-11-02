package org.apache.poi.hssf.record;

import it.piapostolos.android.pinotes.R2;
import org.apache.poi.hssf.record.formula.AreaPtg;
import org.apache.poi.hssf.record.formula.AreaPtgBase;
import org.apache.poi.hssf.record.formula.OperandPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.RefPtg;
import org.apache.poi.hssf.record.formula.RefPtgBase;
import org.apache.poi.hssf.util.CellRangeAddress8Bit;
import org.apache.poi.ss.formula.Formula;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class SharedFormulaRecord extends SharedValueRecordBase {
    public static final short sid = 1212;
    private int field_5_reserved;
    private Formula field_7_parsed_expr;

    private static int fixupRelativeColumn(int i, int i2, boolean z) {
        return z ? (i2 + i) & 255 : i2;
    }

    private static int fixupRelativeRow(int i, int i2, boolean z) {
        return z ? 65535 & (i2 + i) : i2;
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return sid;
    }

    public SharedFormulaRecord() {
        this(new CellRangeAddress8Bit(0, 0, 0, 0));
    }

    private SharedFormulaRecord(CellRangeAddress8Bit cellRangeAddress8Bit) {
        super(cellRangeAddress8Bit);
        this.field_7_parsed_expr = Formula.create(Ptg.EMPTY_PTG_ARRAY);
    }

    public SharedFormulaRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
        this.field_5_reserved = recordInputStream.readShort();
        this.field_7_parsed_expr = Formula.read(recordInputStream.readShort(), recordInputStream, recordInputStream.available());
    }

    @Override // org.apache.poi.hssf.record.SharedValueRecordBase
    protected void serializeExtraData(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_5_reserved);
        this.field_7_parsed_expr.serialize(littleEndianOutput);
    }

    @Override // org.apache.poi.hssf.record.SharedValueRecordBase
    protected int getExtraDataSize() {
        return this.field_7_parsed_expr.getEncodedSize() + 2;
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SHARED FORMULA (");
        stringBuffer.append(HexDump.intToHex(R2.bool.mtrl_btn_textappearance_all_caps));
        stringBuffer.append("]\n");
        stringBuffer.append("    .range      = ");
        stringBuffer.append(getRange().toString());
        stringBuffer.append("\n");
        stringBuffer.append("    .reserved    = ");
        stringBuffer.append(HexDump.shortToHex(this.field_5_reserved));
        stringBuffer.append("\n");
        Ptg[] tokens = this.field_7_parsed_expr.getTokens();
        for (int i = 0; i < tokens.length; i++) {
            stringBuffer.append("Formula[");
            stringBuffer.append(i);
            stringBuffer.append("]");
            Ptg ptg = tokens[i];
            stringBuffer.append(ptg.toString());
            stringBuffer.append(ptg.getRVAType());
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/SHARED FORMULA]\n");
        return stringBuffer.toString();
    }

    public static Ptg[] convertSharedFormulas(Ptg[] ptgArr, int i, int i2) {
        Ptg[] ptgArr2 = ptgArr;
        Ptg[] ptgArr3 = new Ptg[ptgArr2.length];
        int i3 = 0;
        while (i3 < ptgArr2.length) {
            Ptg ptgCopy = ptgArr2[i3];
            byte ptgClass = ptgCopy.isBaseToken() ? (byte) -1 : ptgCopy.getPtgClass();
            if (ptgCopy instanceof RefPtgBase) {
                RefPtgBase refPtgBase = (RefPtgBase) ptgCopy;
                RefPtg refPtg = new RefPtg(fixupRelativeRow(i, refPtgBase.getRow(), refPtgBase.isRowRelative()), fixupRelativeColumn(i2, refPtgBase.getColumn(), refPtgBase.isColRelative()), refPtgBase.isRowRelative(), refPtgBase.isColRelative());
                refPtg.setClass(ptgClass);
                ptgCopy = refPtg;
            } else if (ptgCopy instanceof AreaPtgBase) {
                AreaPtgBase areaPtgBase = (AreaPtgBase) ptgCopy;
                AreaPtg areaPtg = new AreaPtg(fixupRelativeRow(i, areaPtgBase.getFirstRow(), areaPtgBase.isFirstRowRelative()), fixupRelativeRow(i, areaPtgBase.getLastRow(), areaPtgBase.isLastRowRelative()), fixupRelativeColumn(i2, areaPtgBase.getFirstColumn(), areaPtgBase.isFirstColRelative()), fixupRelativeColumn(i2, areaPtgBase.getLastColumn(), areaPtgBase.isLastColRelative()), areaPtgBase.isFirstRowRelative(), areaPtgBase.isLastRowRelative(), areaPtgBase.isFirstColRelative(), areaPtgBase.isLastColRelative());
                areaPtg.setClass(ptgClass);
                ptgCopy = areaPtg;
            } else if (ptgCopy instanceof OperandPtg) {
                ptgCopy = ((OperandPtg) ptgCopy).copy();
            }
            ptgArr3[i3] = ptgCopy;
            i3++;
            ptgArr2 = ptgArr;
        }
        return ptgArr3;
    }

    public Ptg[] getFormulaTokens(FormulaRecord formulaRecord) {
        int row = formulaRecord.getRow();
        short column = formulaRecord.getColumn();
        if (!isInRange(row, column)) {
            throw new RuntimeException("Shared Formula Conversion: Coding Error");
        }
        return convertSharedFormulas(this.field_7_parsed_expr.getTokens(), row, column);
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        SharedFormulaRecord sharedFormulaRecord = new SharedFormulaRecord(getRange());
        sharedFormulaRecord.field_5_reserved = this.field_5_reserved;
        sharedFormulaRecord.field_7_parsed_expr = this.field_7_parsed_expr.copy();
        return sharedFormulaRecord;
    }

    public boolean isFormulaSame(SharedFormulaRecord sharedFormulaRecord) {
        return this.field_7_parsed_expr.isSame(sharedFormulaRecord.field_7_parsed_expr);
    }
}
