package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public abstract class ValueOperatorPtg extends OperationPtg {
    @Override // org.apache.poi.hssf.record.formula.OperationPtg, org.apache.poi.hssf.record.formula.Ptg
    public final byte getDefaultOperandClass() {
        return (byte) 32;
    }

    protected abstract byte getSid();

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final int getSize() {
        return 1;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final boolean isBaseToken() {
        return true;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getSid());
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public final String toFormulaString() {
        throw new RuntimeException("toFormulaString(String[] operands) should be used for subclasses of OperationPtgs");
    }
}
