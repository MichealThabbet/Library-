package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class MissingArgPtg extends ScalarConstantPtg {
    private static final int SIZE = 1;
    public static final Ptg instance = new MissingArgPtg();
    public static final byte sid = 22;

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return 1;
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public String toFormulaString() {
        return " ";
    }

    private MissingArgPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 22);
    }
}
